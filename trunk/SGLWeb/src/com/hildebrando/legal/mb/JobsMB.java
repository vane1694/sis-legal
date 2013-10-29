package com.hildebrando.legal.mb;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.general.entities.Centro;
import com.bbva.general.entities.Feriado;
import com.bbva.general.entities.Tipo_Cambio;
import com.bbva.general.entities.Ubigeo;
import com.bbva.general.service.TablaGeneral;
import com.bbva.general.service.TablaGeneralServiceLocator;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;
import com.grupobbva.bc.per.tele.ldap.conexion.Conexion;
import com.grupobbva.bc.per.tele.ldap.serializable.IILDPeUsuario;
import com.hildebrando.legal.modelo.GrupoBanca;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.TipoCambio;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.util.SglConstantes;

@ManagedBean(name = "admJobs")
@SessionScoped
public class JobsMB 
{
	public static Logger logger = Logger.getLogger(JobsMB.class);
	
	/**
	 * Metodo que se encarga de verificar la existencia de una moneda
	 * @param divisa Representa la divisa Ej: USD, PEN, EUR
	 * */
	public Moneda retornarMoneda(String divisa) throws Exception{
		GenericDaoImpl<Moneda, Integer> monedaDAO = (GenericDaoImpl<Moneda, Integer>) SpringInit.getApplicationContext().getBean("genericoDao");
	    Moneda moneda = new Moneda();
		List<Moneda> lstMoneda = new ArrayList<Moneda>();
		Busqueda filtro = Busqueda.forClass(Moneda.class);
	    filtro.add(Restrictions.eq("divisa", divisa));
	    
	    lstMoneda=monedaDAO.buscarDinamico(filtro);
	    
	    if(lstMoneda.size()==0){
	    	 moneda= null;
	    }else{
	    	 moneda= lstMoneda.get(0);
	    }
	    return moneda;
	}
	
	/**
	 * Metodo que se encarga de verificar la existencia de un tipo de cambio en base de datos
	 * @param tipoCambio Representa el tipo de cambio a validar
	 * @return boolean true/false Si existe o no algun registro igual
	 * */
	public boolean existeTipoCambio(TipoCambio tipoCambio ) throws Exception{
		boolean retorno=false;
		GenericDaoImpl<TipoCambio, Integer> tipoCambioDAO = (GenericDaoImpl<TipoCambio, Integer>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		List<TipoCambio> lstTipoCambio = new ArrayList<TipoCambio>();
		Busqueda filtro = Busqueda.forClass(TipoCambio.class);
	    filtro.add(Restrictions.eq("estado", tipoCambio.getEstado()));
	    filtro.add(Restrictions.eq("fecha", tipoCambio.getFecha()));
	    filtro.add(Restrictions.eq("moneda", tipoCambio.getMoneda()));
	    //filtro.add(Restrictions.eq("valorTipoCambio", tipoCambio.getValorTipoCambio()));
	    lstTipoCambio=tipoCambioDAO.buscarDinamico(filtro);
	    logger.info("lstTipoCambio.size() :: " +lstTipoCambio.size());
	    if(lstTipoCambio.size()>0){
	    	retorno=true;
	    }
	    return retorno;
	}
	
	/**
	 * Metodo que se encarga de insertar/actualizar el tipo de cambio, previamente
	 * se realiza la invocación al método: getTipoCambioListado() del servicio
	 * web de Tablas Generales.
	 * @param fecha Representa la fecha del dia que se pretende consultar información
	 * @param tipo Tipo de "Tipo de cambio" Ejm: S - 
	 * @param divisa Divisa actual Ejm: PEN, EUR, USD
	 * **/
	public void cargarTipoCambio(String fecha, String tipo, String divisa){
		logger.info("===== cargarTipoCambio ======");
		try {
			logger.info("[Job-TipoCambio]-fecha:"+fecha);
			logger.info("[Job-TipoCambio]-tipo:"+tipo);
			logger.info("[Job-TipoCambio]-divisa:"+divisa);
			
			Tipo_Cambio[] listadoTipoCambio= obtenerDatosWebService().getTipoCambioListado(fecha, tipo, divisa);
			//Tipo_Cambio[] listadoTipoCambio= obtenerListadoTipoCambio();
			if(listadoTipoCambio!=null){
				logger.info(SglConstantes.MSJ_TAMANHIO_LISTA+"Tipo de Cambio (WebService)es:[" +listadoTipoCambio.length+"].");	
			}
			
			//List<TipoCambio> results = new ArrayList<TipoCambio>();
			GenericDaoImpl<TipoCambio, Integer> tipoCambioDAO = (GenericDaoImpl<TipoCambio, Integer>) SpringInit.getApplicationContext().getBean("genericoDao");
		
			TipoCambio tipoCambio = null;
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat(SglConstantes.formatoFecha);
			
			for (Tipo_Cambio tipoCambioArray : listadoTipoCambio) {
				if(retornarMoneda(tipoCambioArray.getDivisa())!=null){
					tipoCambio = new TipoCambio();
					tipoCambio.setMoneda(retornarMoneda(tipoCambioArray.getDivisa()));
					tipoCambio.setFecha(formatoDeFecha.parse(fecha));
					tipoCambio.setValorTipoCambio(new BigDecimal(Float.parseFloat(tipoCambioArray.getTcPromedio())));
			        tipoCambio.setEstado(SglConstantes.ACTIVO);
			        //Se actualiza o inserta según sea el caso
			        if(existeTipoCambio(tipoCambio)){
			        	try{
			        		tipoCambioDAO.modificar(tipoCambio);
			        		logger.info(SglConstantes.MSJ_EXITO_REGISTRO+"el tipo Cambio.");
			        	}catch (Exception e) {
			        		logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el tipo de cambio: ",e);
						}
			        }else{
			        	try{
			        		tipoCambioDAO.insertar(tipoCambio);	
			        		logger.info(SglConstantes.MSJ_EXITO_REGISTRO+"el tipo Cambio.");
			        	}catch(Exception e){
			        		logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el tipo de cambio: ",e);
			        	}
			        }
				}else{
					logger.info("La moneda es nula/vacia. ");
				}			        
			}		
		
		} catch (RemoteException e) {
			logger.error(SglConstantes.MSJ_ERROR_CONEX_WEB_SERVICE+"getTipoCambioListado: ",e);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+" TipoCambio del WebService:",e);
		}
		logger.debug("====== saliendo de cargarTipoCambio() ======");
	}

	/*
	private Tipo_Cambio[] obtenerListadoTipoCambio() {
		Tipo_Cambio[] lst ={new Tipo_Cambio("2.4", "3.6", "2.6", "USD", "S"),
		new Tipo_Cambio("2.5", "3.6", "5.88", "PEN", "S"),
		new Tipo_Cambio("2.5", "3.6", "13.5", "PEN", "S")};
		return lst;
	}*/
	
	/**
	 * Metodo encargado de realizar la carga de oficinas en la BD GESLEG, para esto recupera
	 * la lista de centros del servicio web de Tablas Generales y en base a dicha informacion
	 * actualiza (Si existe) o inserta (Si es nuevo) en la tabla Oficina.
	 * **/
	public static void cargarOficinas() 
	{
		logger.info("============= cargarOficinas() ================");
		try {

			Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
			logger.debug("[cargarOficinas]-Inicia proceso de carga: " + tstInicio);
			//Se invoca al servicio de Tablas Generales
//			Centro[] resultado = obtenerDatosWebService().getCentroListado("");
//			if(resultado!=null){
//				logger.info("\t"+SglConstantes.MSJ_TAMANHIO_LISTA+"de CentroListado (TablasGenerales) por WebService es:"+resultado.length);
//			}
			//Se calcula el tiempo que se demora en obtener la data del webservice
			Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
			double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
			logger.debug("[cargarOficinas]-Tiempo en obtener CentroListado WS: " + segundosUtilizadosLat + " segundos.");
			//Obteniendo Territorio y Ubigeo por default
			List<Territorio> terrDefecto = buscarTerritorio(SglConstantes.CAMPO_CODIGO_TERRITORIO, SglConstantes.COD_SIN_TERRITORIO);
			logger.info("[cargarOficinas]-[DEFAULT-TERRITORIO] :" +terrDefecto.get(0).getIdTerritorio() + " / "+terrDefecto.get(0).getCodigo() + " / "+terrDefecto.get(0).getDescripcion());			
			List<com.hildebrando.legal.modelo.Ubigeo> ubigDefecto = buscarUbigeo(SglConstantes.COD_UBIG_DEFAULT);
			logger.info("[cargarOficinas]-[DEFAULT-UBIGEO] :" +ubigDefecto.get(0).getCodDist()+ " / "+ubigDefecto.get(0).getDistrito());
			 
//			if(resultado!=null){
//				
//				for (Centro cent : resultado) 
//				{
//					if (cent.getTipoOficinaCentro() != null) 
//					{
//						//Si el centro es del tipo 'O' = 'Operativa'
//						if (cent.getTipoOficinaCentro().equalsIgnoreCase(SglConstantes.TIP_OFIC_OPERATIVA)) {
//							logger.debug("\t-----------------OFICINA OPERATIVA ---------");
//							logger.debug("\t[OFICINA-CODIGO]: " + cent.getCodigoOficina());
//							logger.debug("\t[OFICINA-NOMBRE]: " + cent.getNombre());
//							logger.debug("----------------------------------------------");
//
//							// Seteo de atributos de oficina
//							com.hildebrando.legal.modelo.Oficina oficina = new com.hildebrando.legal.modelo.Oficina();
//							oficina.setCodigo(cent.getCodigoOficina());
//							oficina.setNombre(cent.getNombre());
//
//							if (!validarSiExiste(SglConstantes.TABLA_OFICINA, oficina)) 
//							{
//								// Buscar territorio en base al ubigeo de la oficina
//								Timestamp tstInicioTerr = new Timestamp(new java.util.Date().getTime());
//								logger.debug("[cargOfic]-[busqTerrit]-FechaInicio: "	+ tstInicioTerr);
//
//								List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
//								com.bbva.general.entities.Territorio terrSrv = new com.bbva.general.entities.Territorio();
//								
//								if(cent.getTerritorio()==null){
//									logger.debug("\t[WS]-Como cent.getTerritorio() es NULL, se asigna [DEFAULT-TERRITORIO]: "+terrDefecto.get(0).getCodigo());
//									terrSrv.setCodigoTerritorio(terrDefecto.get(0).getCodigo());
//								}else{
//									if(cent.getTerritorio().getCodigoTerritorio()!=null && !cent.getTerritorio().getCodigoTerritorio().equalsIgnoreCase("")){
//										terrSrv.setCodigoTerritorio(cent.getTerritorio().getCodigoTerritorio());
//										logger.debug("\t[WS]-cent.getTerritorio().getCodigoTerritorio()-OK: "+cent.getTerritorio().getCodigoTerritorio());
//									}else{
//										logger.debug("\t[WS]-Se asigna [DEFAULT-TERRITORIO]: "+terrDefecto.get(0).getCodigo() +"por defecto.");
//										terrSrv.setCodigoTerritorio(terrDefecto.get(0).getCodigo());
//									}
//								}
//								
//								logger.info("[cargOfic]-Territorio Seteado:" + terrSrv.getCodigoTerritorio());
//								
//								//Si no es el territorio por default, se busca en BD.
//								if(!terrSrv.getCodigoTerritorio().equalsIgnoreCase(SglConstantes.COD_SIN_TERRITORIO)){
//									results = buscarTerritorio("codigo",terrSrv.getCodigoTerritorio());
//									logger.debug("[cargOfic]-[busqTerrit]-Lista resultados size: " + results.size());
//									if (results.size() == 1) {
//										for (com.hildebrando.legal.modelo.Territorio territ : results) {
//											logger.debug("------------Territorio Encontrado ----------------------");
//											logger.debug("  [TERR]-Codigo: " + territ.getIdTerritorio());
//											logger.debug("  [TERR]-Descripcion: " + territ.getDescripcion());
//											oficina.setTerritorio(territ);
//										}
//									}if(results.size() == 0){
//										logger.debug("Se setea Territorio 'Default', ya que no se encontro en BD.");
//										oficina.setTerritorio(terrDefecto.get(0));
//									}
//								}else{
//									oficina.setTerritorio(terrDefecto.get(0));
//									logger.debug("-Se setea Territorio 'Default' para Oficina: "+oficina.getCodigo());
//								}
//								
//								
//								Timestamp tstFinTerr = new Timestamp(new java.util.Date().getTime());
//								logger.debug("[cargOfic]-[busqTerrit]-FechaFin: " + tstFinTerr);
//								
//								double segundosUtilizadosTerr = restarFechas(tstInicioTerr, tstFinTerr);
//								logger.debug("[cargOfic]-[busqTerrit]-Tiempo Total: " + segundosUtilizadosTerr+" segundos.");
//								
//								// Busqueda del UBIGEO de la Oficina
//								Timestamp tstInicioUbi = new Timestamp(new java.util.Date().getTime());
//								logger.debug("[cargOfic]-[busqUbigeo]-FechaInicio: " + tstInicioUbi);
//
//								List<com.hildebrando.legal.modelo.Ubigeo> results2 = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();
//								com.bbva.general.entities.Ubigeo ubigSrv = new com.bbva.general.entities.Ubigeo();
//								if(cent.getDistrito()==null){
//									ubigSrv.setIDUbigeo(ubigDefecto.get(0).getCodDist());
//									logger.debug("\t[WS]-Como cent.getDistrito() es NULL, se asigna [DEFAULT-UBIGEO]: "+ubigDefecto.get(0).getCodDist());
//								}else{
//									if(cent.getDistrito().getIDUbigeo()!=null && !cent.getDistrito().getIDUbigeo().equalsIgnoreCase("")){
//										logger.debug("\t[WS]-cent.getDistrito().getIDUbigeo()-OK: "+cent.getDistrito().getIDUbigeo());
//										ubigSrv.setIDUbigeo(cent.getDistrito().getIDUbigeo());
//									}else{
//										logger.debug("\t[WS]-Se asigna [DEFAULT-UBIGEO] "+ubigDefecto.get(0).getCodDist()+" por default.");
//										//cent.getDistrito().setIDUbigeo(ubigDefecto.get(0).getCodDist());
//										ubigSrv.setIDUbigeo(ubigDefecto.get(0).getCodDist());
//									}
//								}
//								
//								logger.info("[cargOfic]-cent.getDistrito().getIDUbigeo(): " + ubigSrv.getIDUbigeo());
//								//Si no es el Ubigeo por default, se consulta en BD
//								if(!ubigSrv.getIDUbigeo().equalsIgnoreCase(SglConstantes.COD_UBIG_DEFAULT)){									
//									results2 = buscarUbigeo(ubigSrv.getIDUbigeo());
//									if(results2!=null){
//										logger.debug("[UBIGEO]-[busqUbigeo]-Lista resultados size: " + results2.size());
//										if (results2.size() == 1){
//											for (com.hildebrando.legal.modelo.Ubigeo ubi : results2) {
//												logger.debug("------------UBIGEO ENCONTRADO -------------");
//												logger.debug("  [UBIG]-Codigo: " + ubi.getCodDist());
//												logger.debug("  [UBIG]-Distrito: " + ubi.getDistrito());
//												oficina.setUbigeo(ubi);
//											}
//											Timestamp tstFinUbi = new Timestamp(new java.util.Date().getTime());
//											logger.debug("[cargOfic]-[busqUbigeo]-FechaFin: " + tstFinUbi);
//
//											double segundosUtilizadosUbi = restarFechas(tstInicioUbi, tstFinUbi);
//											logger.debug("[cargOfic]-[busqUbigeo]-Tiempo total: " + segundosUtilizadosUbi + " segundos.");
//										}
//										if(results2.size() == 0){
//											logger.debug("Se setea Ubigeo 'Default', ya que no se encontro en BD.");
//											oficina.setUbigeo(ubigDefecto.get(0));
//										}
//									}
//								}else{
//									logger.debug("Se setea Ubigeo 'Default' para Oficina: "+oficina.getCodigo());
//									oficina.setUbigeo(ubigDefecto.get(0));
//								}
//								
//								//Se grabara la oficina
//								oficina.setEstado(SglConstantes.ACTIVO);
//								grabarOficina(oficina);
//
//							}else{
//								logger.debug("[cargOfic]-No se cargara la oficina [ "+oficina.getCodigo()+"] porque validarSiExiste es true.");
//							}
//						}
//					}
//				}	
//			}
		
			Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
			logger.debug("[cargarOficinas]-Termina proceso de carga: " + tstFin);

			double segundosUtilizados = restarFechas(tstInicio, tstFin);
			logger.debug("[cargarOficinas]-Proceso de carga realizado en: " + segundosUtilizados + " segundos.");
		
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al cargar Oficinas:",e);
		}
		
		logger.debug("==== saliendo de cargarOficinas() ====");
	}

	private static double restarFechas(Timestamp fhInicial, Timestamp fhFinal) {
		long fhInicialms = fhInicial.getTime();
		long fhFinalms = fhFinal.getTime();
		long diferencia = fhFinalms - fhInicialms;
		double a = (double) diferencia / (double) (1000);
		return a;
	}

	@SuppressWarnings({ "unchecked" })
	public static List<com.hildebrando.legal.modelo.Territorio> buscarTerritorio(String campo, String valor) {
		//logger.debug("==== buscarTerritorio() === ");
		logger.debug("\t[buscTerr]-valor: " + valor);
		List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();

		GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Territorio.class);
		filtro.add(Expression.eq(campo, valor));
		try {
			results = territorioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Territorios: ",e);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public static List<com.hildebrando.legal.modelo.Ubigeo> buscarUbigeo(String ubigeo) {
		logger.debug("== buscarUbigeo() ===");
		logger.debug("[buscUbig]-valorUbigeo: " + ubigeo);
		List<com.hildebrando.legal.modelo.Ubigeo> results = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();

		GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer> ubigeoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Ubigeo.class);
		filtro.add(Expression.eq("codDist", ubigeo));

		try {
			results = ubigeoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Ubigeos: ",e);
		}
		return results;
	}

	public static void cargarTerritorios() {
		logger.debug("==== cargarTerritorios() ====");
		/*
		 * Ubigeo[] resUbigeoD = null; Ubigeo[] resUbigeoP = null; Ubigeo[]
		 * resUbigeoDis = null;
		 * 
		 * String cadena = ""; int i = 0;
		 * 
		 * Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
		 * logger.debug("INICIA PROCESO CARGA TERRITORIOS: " + tstInicio);
		 * 
		 * try { resUbigeoD = obtenerDatosWebService().getDepartamentoListado();
		 * resUbigeoP = obtenerDatosWebService().getProvinciaListado();
		 * resUbigeoDis = obtenerDatosWebService().getDistritoListado(); } catch
		 * (RemoteException e) { e.printStackTrace(); logger.debug(
		 * "Error al obtener listado de departamentos, provincias y/o distritos."
		 * ); }
		 * 
		 * Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
		 * double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
		 * //logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " +
		 * segundosUtilizados + " SEGUNDOS"); logger.debug(
		 * "SE DEMORO EN OBTENER LOS DATOS DE LOS WEB SERVICE DE DEPARTAMENTOS, PROVINCIAS Y DISTRITOS: "
		 * + segundosUtilizadosLat + " SEGUNDOS");
		 * 
		 * for (Ubigeo ubiDep : resUbigeoD) { i++;
		 * 
		 * if (ubiDep.getIDUbigeo().substring(2,
		 * ubiDep.getIDUbigeo().length()).equals("00000")) { if (cadena.length()
		 * > 0) { cadena += "\n[Renglon " + i + "]: " + ubiDep.getIDUbigeo() +
		 * "," + ubiDep.getDescripcion() + "," + ubiDep.getDescripcion() + "," +
		 * ubiDep.getDescripcion(); } else { cadena += "[Renglon " + i +
		 * "]: "+ubiDep.getIDUbigeo() + "," + ubiDep.getDescripcion() + "," +
		 * ubiDep.getDescripcion(); }
		 * 
		 * com.hildebrando.legal.modelo.Territorio territ = new
		 * com.hildebrando.legal.modelo.Territorio();
		 * territ.setUbigeo(ubiDep.getIDUbigeo());
		 * territ.setDepartamento(ubiDep.getDescripcion());
		 * territ.setProvincia(ubiDep.getDescripcion());
		 * territ.setDistrito(ubiDep.getDescripcion());
		 * 
		 * if (!validarSiExiste("territorio",territ)) {
		 * grabarTerritorio(territ); }
		 * 
		 * } for (Ubigeo ubiProv : resUbigeoP) { if
		 * (ubiDep.getIDUbigeo().substring(0,
		 * 2).equalsIgnoreCase(ubiProv.getIDUbigeo().substring(0, 2)) &&
		 * !ubiProv.getIDUbigeo().substring(2,
		 * ubiProv.getIDUbigeo().length()).equals("00000")) { if
		 * (cadena.length() > 0) { cadena += "\n[Renglon " + i + "]: " +
		 * ubiProv.getIDUbigeo() + "," + ubiDep.getDescripcion() + "," +
		 * ubiProv.getDescripcion() + "," + ubiProv.getDescripcion(); } else {
		 * cadena += "[Renglon " + i + "]: "+ubiProv.getIDUbigeo() + "," +
		 * ubiDep.getDescripcion() + "," + ubiProv.getDescripcion() + "," +
		 * ubiProv.getDescripcion(); }
		 * 
		 * com.hildebrando.legal.modelo.Territorio territ = new
		 * com.hildebrando.legal.modelo.Territorio();
		 * territ.setUbigeo(ubiProv.getIDUbigeo());
		 * territ.setDepartamento(ubiDep.getDescripcion());
		 * territ.setProvincia(ubiProv.getDescripcion());
		 * territ.setDistrito(ubiProv.getDescripcion());
		 * 
		 * if (!validarSiExiste("territorio",territ)) {
		 * grabarTerritorio(territ); } }
		 * 
		 * for (Ubigeo ubiDis : resUbigeoDis) { if
		 * (ubiDep.getIDUbigeo().substring(0,
		 * 2).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(0, 2)) &&
		 * ubiProv.getIDUbigeo().substring(2,
		 * 4).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(2, 4)) &&
		 * ubiProv.getIDUbigeo().substring(0,
		 * 2).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(0, 2))) { if
		 * (cadena.length() > 0) { cadena += "\n[Renglon " + i + "]: " +
		 * ubiDis.getIDUbigeo() + "," + ubiDep.getDescripcion() + "," +
		 * ubiProv.getDescripcion() + "," + ubiDis.getDescripcion(); } else {
		 * cadena += "[Renglon " + i + "]: "+ubiDis.getIDUbigeo() + "," +
		 * ubiDep.getDescripcion() + "," + ubiProv.getDescripcion() + "," +
		 * ubiDis.getDescripcion(); }
		 * 
		 * com.hildebrando.legal.modelo.Territorio territ = new
		 * com.hildebrando.legal.modelo.Territorio();
		 * territ.setUbigeo(ubiDis.getIDUbigeo());
		 * territ.setDepartamento(ubiDep.getDescripcion());
		 * territ.setProvincia(ubiProv.getDescripcion());
		 * territ.setDistrito(ubiDis.getDescripcion());
		 * 
		 * 
		 * if (!validarSiExiste("territorio",territ)) {
		 * grabarTerritorio(territ); } } } }
		 * 
		 * }
		 * 
		 * Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
		 * //logger.info("TERMINA PROCESO CARGA OFICINAS A LAS " + tstFin);
		 * logger.debug("TERMINA PROCESO CARGA TERRITORIOS: " + tstFin);
		 * 
		 * double segundosUtilizados = restarFechas(tstInicio, tstFin);
		 * //logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " +
		 * segundosUtilizados + " SEGUNDOS");
		 * logger.debug("PROCESO CARGA TERRITORIOS REALIZADO EN: " +
		 * segundosUtilizados + " SEGUNDOS");
		 * 
		 * logger.debug(cadena);
		 */

		com.bbva.general.entities.Territorio[] terr = null;

		Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
		logger.debug("[cargarTerrit]-Hora Inicio:" + tstInicio);

		try {
			terr = obtenerDatosWebService().getTerritorioListado();
			if(terr!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"Territorios via WebService es:"+terr.length);
			}
		} catch (RemoteException e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Territorios [RemoteException]:"+e);
		}
		catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Territorios:"+e);
		}
		GrupoBanca grupDefecto = new GrupoBanca();
		grupDefecto.setIdGrupoBanca(1);
		
		if(terr!=null){
			for (com.bbva.general.entities.Territorio terrSrv : terr) {
				com.hildebrando.legal.modelo.Territorio terrTMP = new com.hildebrando.legal.modelo.Territorio();
				
           //   if(terrSrv.get !=null){
				terrTMP.setCodigo(terrSrv.getCodigoTerritorio());
				terrTMP.setDescripcion(terrSrv.getDescripcionTerritorio());				
				terrTMP.setGrupoBanca(grupDefecto);
				terrTMP.setEstado(SglConstantes.ACTIVO);
             /* }else{
            	terrTMP.setCodigo(terrSrv.getCodigoTerritorio());
  				terrTMP.setDescripcion(SglConstantes.DESCRIPCION_TERRITORIO);
  				terrTMP.setGrupoBanca(new GrupoBanca(SglConstantes.ID_BANCA));
  				terrTMP.setEstado('A');  
            	  
              }*/
				
				if (!validarSiExiste(SglConstantes.TABLA_TERRITORIO, terrTMP)) {
					grabarTerritorio(terrTMP);
				}
				
				
			}
		}
		
		
		Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
		logger.debug("[cargarTerrit]-Hora Fin:" + tstFin);

		double segundosUtilizados = restarFechas(tstInicio, tstFin);
		logger.debug("[cargarTerrit]-Tiempo total demora: " + segundosUtilizados + " segundos.");

		logger.debug("==== saliendo de cargarTerritorios() ====");
	}

	public void cargarUbigeos() {
		Ubigeo[] resUbigeoD = null;
		Ubigeo[] resUbigeoP = null;
		Ubigeo[] resUbigeoDis = null;

		Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
		logger.debug("INICIA PROCESO CARGA UBIGEOS: " + tstInicio);

		try {
			resUbigeoD = obtenerDatosWebService().getDepartamentoListado();
			if(resUbigeoD!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"resUbigeoDistrito via WebService es:"+resUbigeoD.length);
			}
			resUbigeoP = obtenerDatosWebService().getProvinciaListado();
			if(resUbigeoP!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"resUbigeoProvincia via WebService es:"+resUbigeoP.length);
			}
			resUbigeoDis = obtenerDatosWebService().getDistritoListado();
			if(resUbigeoDis!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"resUbigeoDistrito via WebService es:"+resUbigeoDis.length);
			}
		} catch (RemoteException e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"listado de Ubigeos:[RemoteException]"+e);
		}catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"listado de Ubigeos:"+e);
		}

		Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
		double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
		logger.debug("SE DEMORO EN OBTENER LOS DATOS DE UBIGEO: " + segundosUtilizadosLat + " SEGUNDOS");

		for (Ubigeo ubiDep : resUbigeoD) 
		{
			if (ubiDep.getIDUbigeo().substring(2, ubiDep.getIDUbigeo().length()).equals("00000")) 
			{
				com.hildebrando.legal.modelo.Ubigeo ubi = new com.hildebrando.legal.modelo.Ubigeo();
				ubi.setCodDep(ubiDep.getIDUbigeo());
				ubi.setDepartamento(ubiDep.getDescripcion());
				ubi.setCodDist(ubiDep.getIDUbigeo());
				ubi.setDistrito(ubiDep.getDescripcion());
				ubi.setCodProv(ubiDep.getIDUbigeo());
				ubi.setProvincia(ubiDep.getDescripcion());

				if (!validarSiExiste(SglConstantes.TABLA_UBIGEO, ubi)) {
					grabarUbigeo(ubi);
				}
			}

			for (Ubigeo ubiProv : resUbigeoP) 
			{
				if (ubiDep.getIDUbigeo().substring(0, 2).equalsIgnoreCase(ubiProv.getIDUbigeo().substring(0, 2))
						&& !ubiProv.getIDUbigeo().substring(2, ubiProv.getIDUbigeo().length()).equals("00000")) 
				{
					com.hildebrando.legal.modelo.Ubigeo ubi = new com.hildebrando.legal.modelo.Ubigeo();
					ubi.setCodDep(ubiDep.getIDUbigeo());
					ubi.setDepartamento(ubiDep.getDescripcion());
					ubi.setCodProv(ubiProv.getIDUbigeo());
					ubi.setProvincia(ubiProv.getDescripcion());
					ubi.setCodDist(ubiProv.getIDUbigeo());
					ubi.setDistrito(ubiProv.getDescripcion());

					if (!validarSiExiste(SglConstantes.TABLA_UBIGEO, ubi)) {
						grabarUbigeo(ubi);
					}
				}

				for (Ubigeo ubiDis : resUbigeoDis) 
				{
					if (ubiDep.getIDUbigeo().substring(0, 2).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(0, 2))
							&& ubiProv.getIDUbigeo().substring(2, 4).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(2, 4))
							&& ubiProv.getIDUbigeo().substring(0, 2).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(0, 2))) 
					{
						com.hildebrando.legal.modelo.Ubigeo ubi = new com.hildebrando.legal.modelo.Ubigeo();
						ubi.setCodDep(ubiDep.getIDUbigeo());
						ubi.setDepartamento(ubiDep.getDescripcion());
						ubi.setCodDist(ubiDis.getIDUbigeo());
						ubi.setDistrito(ubiDis.getDescripcion());
						ubi.setCodProv(ubiProv.getIDUbigeo());
						ubi.setProvincia(ubiProv.getDescripcion());

						if (!validarSiExiste(SglConstantes.TABLA_UBIGEO, ubi)) {
							grabarUbigeo(ubi);
						}
					}

				}
			}
		}

		Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
		logger.debug("TERMINA PROCESO CARGA UBIGEOS: " + tstFin);

		double segundosUtilizados = restarFechas(tstInicio, tstFin);
		logger.debug("PROCESO CARGA UBIGEOS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");

	}

	@SuppressWarnings("unchecked")
	public static void grabarTerritorio(com.hildebrando.legal.modelo.Territorio territ) 
	{
		GenericDao<com.hildebrando.legal.modelo.Territorio, Object> territorioDAO = (GenericDao<com.hildebrando.legal.modelo.Territorio, Object>) 
				SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			if (validarSiExiste(SglConstantes.TABLA_TERRITORIO, "codigo", territ.getCodigo())) {
				//territorioDAO.modificar(territ);
				logger.debug(SglConstantes.MSJ_EXITO_ACTUALIZ+"el Territorio:["+territ.getCodigo()+"]");
				generarScriptsJobsGESLEG("U",territ,null);
			} else {				
				//territ.setEstado(SglConstantes.ACTIVO);
				//territorioDAO.insertar(territ);
				logger.debug(SglConstantes.MSJ_EXITO_REGISTRO+"el Territorio:["+territ.getCodigo()+"]-["+territ.getDescripcion()+"]");
				generarScriptsJobsGESLEG("I",territ,null);
			}
		} catch (Exception e) {			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registró el territorio desde el webservice"));
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"el territorio:",e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static boolean validarSiExiste(String tabla, String campo,String valor) 
	{
		boolean existe = false;

		// Buscar si existe territorio, oficina o feriado
		if (tabla.equals(SglConstantes.TABLA_TERRITORIO)) 
		{

			List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(Territorio.class);
			filtro.add(Expression.eq(campo, valor));

			try {
				results = territorioDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"territorios (validarSiExiste):",e);
			}

			if (results.size() > 0) {
				existe = true;
			}
		}
		if (tabla.equals(SglConstantes.TABLA_OFICINA)) 
		{
			List<com.hildebrando.legal.modelo.Oficina> results = new ArrayList<com.hildebrando.legal.modelo.Oficina>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer> oficinaDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Oficina.class);
			filtro.add(Expression.eq(campo, valor));

			try {
				results = oficinaDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"la oficina: ",e);
			}

			if (results.size() > 0) {
				logger.debug("Hay un resultado, por lo tanto la oficina " + valor+" ya existe en BD: ");
				existe = true;
			}
		}

		if (tabla.equals("Ubigeo")) {
			

			List<com.hildebrando.legal.modelo.Ubigeo> results = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer> ubiDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda
					.forClass(com.hildebrando.legal.modelo.Ubigeo.class);
			filtro.add(Expression.eq(campo, valor));

			try {
				results = ubiDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (results.size() > 0) {
				existe = true;
			}
		}

		return existe;
	}

	@SuppressWarnings({ "unchecked" })
	public static boolean validarSiExisteFeriado(String tabla, String campo,
			Object valor) {
		boolean existe = false;

		if (tabla.equals(SglConstantes.TABLA_FERIADO)) {
			List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer> feriadoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda
					.forClass(com.hildebrando.legal.modelo.Feriado.class);
			filtro.add(Expression.eq(campo, valor));

			try {
				results = feriadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (results.size() > 0) {
				existe = true;
			}
		}

		return existe;
	}

	@SuppressWarnings({ "unchecked" })
	public static boolean validarSiExiste(String tabla, Object obj) {
		boolean existe = false;

		// Buscar si existe territorio, oficina o feriado
		if (tabla.equals(SglConstantes.TABLA_TERRITORIO)) {

			com.hildebrando.legal.modelo.Territorio terrTMP = (com.hildebrando.legal.modelo.Territorio) obj;

			List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(Territorio.class);
			filtro.add(Expression.eq("codigo", terrTMP.getCodigo()));
			filtro.add(Expression.eq("descripcion", terrTMP.getDescripcion()));

			try {
				results = territorioDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"y validar territorio:"+e);
			}

			if (results.size() > 0) {
				logger.debug("Ya existe el territorio, no se realizara ninguna carga: "+terrTMP.getCodigo());
				existe = true;
			}
		}
		if (tabla.equals(SglConstantes.TABLA_OFICINA)) {
		
			com.hildebrando.legal.modelo.Oficina oficTMP = (com.hildebrando.legal.modelo.Oficina) obj;
			List<com.hildebrando.legal.modelo.Oficina> results = new ArrayList<com.hildebrando.legal.modelo.Oficina>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer> oficinaDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda
					.forClass(com.hildebrando.legal.modelo.Oficina.class);
			filtro.add(Expression.eq("codigo", oficTMP.getCodigo()));
			filtro.add(Expression.eq("nombre", oficTMP.getNombre()));

			filtro.addOrder(Order.asc("codigo"));

			try {
				results = oficinaDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"y validar oficina:", e);
			}

			if (results.size() > 0) {
				logger.debug("Ya existe la oficina, no se realizara ninguna carga: "+oficTMP.getCodigo());
				existe = true;
			}
		}
		if (tabla.equals(SglConstantes.TABLA_FERIADO)) {
		
			com.hildebrando.legal.modelo.Feriado feridTMP = (com.hildebrando.legal.modelo.Feriado) obj;
			List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer> feriadoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Feriado.class);
			filtro.add(Expression.eq("fecha", feridTMP.getFecha()));
			filtro.add(Expression.eq("indicador", feridTMP.getIndicador()));

			try {
				results = feriadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"y validar feriado:"+e);
			}

			if (results.size() > 0) {
				existe = true;
			}
		}
		if (tabla.equals(SglConstantes.TABLA_UBIGEO)) {
			com.hildebrando.legal.modelo.Ubigeo ubiTMP = (com.hildebrando.legal.modelo.Ubigeo) obj;

			List<com.hildebrando.legal.modelo.Ubigeo> results = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer> ubiDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(Ubigeo.class);
			filtro.add(Restrictions.eq("codDistrito", ubiTMP.getCodDist()));
			filtro.add(Expression.eq("nomDistrito", ubiTMP.getDistrito()));
			filtro.add(Expression.eq("codProvincia", ubiTMP.getCodProv()));
			filtro.add(Expression.eq("nomProvincia", ubiTMP.getProvincia()));
			filtro.add(Expression.eq("codDepartamento", ubiTMP.getCodDep()));
			filtro.add(Expression.eq("nomDepartamento",
					ubiTMP.getDepartamento()));

			try {
				results = ubiDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"y validar ubigeo:", e);
			}

			if (results.size() > 0) {
				existe = true;
			}
		}

		return existe;
	}

	@SuppressWarnings({ "unchecked" })
	public static void grabarFeriado(com.hildebrando.legal.modelo.Feriado ferid) {
		GenericDao<com.hildebrando.legal.modelo.Feriado, Object> feriadoDAO = (GenericDao<com.hildebrando.legal.modelo.Feriado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			if (validarSiExisteFeriado(SglConstantes.TABLA_FERIADO, "fecha",ferid.getFecha())) 
			{
				feriadoDAO.modificar(ferid);
			} 
			else 
			{
				feriadoDAO.insertar(ferid);
			}
			logger.debug("Se inserto feriado exitosamente!");
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro",	"No se registro el feriado desde el webservice"));
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"el feriado: "+e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void grabarUbigeo(com.hildebrando.legal.modelo.Ubigeo ubigeo) {
		GenericDao<com.hildebrando.legal.modelo.Ubigeo, Object> ubiDAO = (GenericDao<com.hildebrando.legal.modelo.Ubigeo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			if (validarSiExiste("Ubigeo", "codDist", ubigeo.getCodDist())) {
				ubiDAO.modificar(ubigeo);
			} else {
				ubigeo.setEstado(SglConstantes.ACTIVO);
				ubiDAO.insertar(ubigeo);
			}
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("Se inserto ubigeo exitosamente!");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registro el ubigeo desde el webservice"));
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"el ubigeo:",e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void grabarOficina(com.hildebrando.legal.modelo.Oficina ofi) {
		GenericDao<com.hildebrando.legal.modelo.Oficina, Object> oficinaDAO = (GenericDao<com.hildebrando.legal.modelo.Oficina, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			
			if (!validarSiExiste(SglConstantes.TABLA_OFICINA, "codigo", ofi.getCodigo())) {
				logger.debug("[grabarOficina]-codigoOficina: "+ofi.getCodigo());
				//logger.debug("Se inserta oficina: " + ofi.getCodigo());
				//oficinaDAO.insertar(ofi);
				generarScriptsJobsGESLEG("I",null,ofi);
			} else {
				//logger.debug("Se actualiza informacion de oficina: " + ofi.getCodigo());
				//oficinaDAO.modificar(ofi);
				generarScriptsJobsGESLEG("U",null,ofi);
			}
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("---- Se inserto/actualizo la oficina exitosamente! ------ \n");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registro la oficina desde el webservice"));
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"la oficina desde JobsMB:",e);
		}
	}

	public static void cargarFeriados() {
		try {

			logger.debug("==== cargarFeriados() ====");
			
			// 2 digitos: Departamento
			// 2 digitos: Provincia
			// 3 digitos: Distrito
			com.bbva.general.entities.Feriado[] resultado = null;
			Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
			logger.debug("INICIA PROCESO CARGA FERIADOS: " + tstInicio);

			try {
				resultado = obtenerDatosWebService().getFeriadoListado();
				if(resultado!=null){
					logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"de Feriados via WebService es:["+resultado.length+"].");
				}				
			} catch (RemoteException e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Feriados [RemoteException]:"+e);
			}
			catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Feriados:"+e);
			}

			Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
			double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String anio = sdf.format(date);
			logger.debug("[CARG_FERIADO]-Anio:"+anio);
			logger.debug("SE DEMORO EN OBTENER LOS DATOS DEL WEB SERVICE DE FERIADOS: "	+ segundosUtilizadosLat + " SEGUNDOS");

			if(resultado!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"de feriados via WebService es:"+resultado.length);
			
				for (Feriado feriado : resultado) 
				{
					String annio = Integer.toString(feriado.getFecha().get(Calendar.YEAR));

					if (annio.equals(anio)) 
					{
						if (feriado.getUbigeo() != null) 
						{
							logger.debug("------------------FERIADOS LOCALES------------------------");
							logger.debug("Ind. Feriado: " + feriado.getIndicador());
							logger.debug("Fecha: "+ feriado.getFecha().getTime().toGMTString());
							logger.debug("Ubigeo: " + feriado.getUbigeo());
							logger.debug("Descripcion: " + feriado.getDescripcion());
							logger.debug("--------------------------------------------------");
						} 
						else 
						{
							logger.debug("------------------FERIADOS NACIONALES------------------------");
							logger.debug("Ind. Feriado: " + feriado.getIndicador());
							logger.debug("Fecha: "+ feriado.getFecha().getTime().toGMTString());
							logger.debug("Descripcion: " + feriado.getDescripcion());
							logger.debug("--------------------------------------------------");
						}

						com.hildebrando.legal.modelo.Feriado ferid = new com.hildebrando.legal.modelo.Feriado();
						ferid.setTipo('C');
						ferid.setNombre(feriado.getDescripcion()); //Agregado para guardar el nombre del feriado
						ferid.setEstado(SglConstantes.ACTIVO);

						if (feriado.getIndicador().equals("L")) 
						{
							ferid.setIndicador('L');
						} 
						else 
						{
							ferid.setIndicador('N');
						}

						ferid.setFecha(feriado.getFecha().getTime());

						if (!validarSiExiste(SglConstantes.TABLA_FERIADO, ferid)) 
						{
							if (feriado.getIndicador().equals("L")) 
							{
								Timestamp tstInicioUbi = new Timestamp(new java.util.Date().getTime());
								logger.debug("INICIA SUBPROCESO BUSQUEDA TERRITORIOS: "	+ tstInicioUbi);

								// Buscar ubigeo del feriado
								List<com.hildebrando.legal.modelo.Ubigeo> results2 = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();
								results2 = buscarUbigeo(feriado.getUbigeo());
								if(results2!=null){
									logger.debug("Resultados [buscarUbigeo] encontrados: " + results2.size());
									
									if (results2.size() == 1) 
									{
										for (com.hildebrando.legal.modelo.Ubigeo ubi : results2) 
										{
											logger.debug("------------UBIGEOS----------------------");
											logger.debug("Codigo: " + ubi.getCodDist());
											logger.debug("Distrito: " + ubi.getDistrito());
											ferid.setUbigeo(ubi);
										}

										Timestamp tstFinUbi = new Timestamp(new java.util.Date().getTime());
										logger.debug("TERMINA SUBPROCESO BUSQUEDA UBIGEOS: " + tstFinUbi);

										double segundosUtilizadosUbi = restarFechas(tstInicioUbi, tstFinUbi);
										logger.debug("PROCESO SUBPROCESO BUSQUEDA UBIGEOS REALIZADO EN: " + segundosUtilizadosUbi + " SEGUNDOS");
										
										grabarFeriado(ferid);
									}
									else
									{
										logger.debug("No se pudo grabar registro de feriado debido a que no se encuentra ubigeo");
									}
								}
								
							}
							else 
							{
								grabarFeriado(ferid);
							}
						}
					}
				}
			}			

			Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
			logger.debug("TERMINA PROCESO CARGA FERIADOS: " + tstFin);

			double segundosUtilizados = restarFechas(tstInicio, tstFin);
			logger.debug("PROCESO CARGA FERIADOS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");

		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al cargarFeriados:"+e);
		}
		
		logger.debug("==== saliendo de cargarFeriados() ====");
	}

	public static TablaGeneral obtenerDatosWebService() {
		TablaGeneral tbGeneralWS = null;
		try {
			TablaGeneralServiceLocator tablaGeneralServiceLocator = new TablaGeneralServiceLocator();

			tbGeneralWS = tablaGeneralServiceLocator.getTablaGeneral();
		} catch (ServiceException e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al obtenerDatosWebService():"+e);
		}

		return tbGeneralWS;
	}
	
	public static void actualizarDatosUsuarios(){
		try {
			logger.debug("Inicio de Proceso de actualización de datos de usuario");
			@SuppressWarnings("unchecked")
			GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Usuario.class);
			filtro.add(Restrictions.isNotNull("codigo"));
			List<Usuario> usuarios = new ArrayList<Usuario>();
			try {
				usuarios = usuarioDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("Nro usuarios a verificar:" + usuarios.size());
			Conexion con = new Conexion();
			for (Usuario usuario : usuarios) {
				String codigoUsuario = usuario.getCodigo();				
				IILDPeUsuario usuarioIILD = con.recuperarUsuario(codigoUsuario);				
				boolean isUpdate = false;
				if (usuarioIILD != null) { //si usuario existe en ldapp
					if (!usuario.getApellidoPaterno().equalsIgnoreCase(
							usuarioIILD.getApellido1())) {
						usuario.setApellidoPaterno(usuarioIILD.getApellido1());
						isUpdate = true;
					}
					if (!usuario.getApellidoMaterno().equalsIgnoreCase(
							usuarioIILD.getApellido2())) {
						usuario.setApellidoMaterno(usuarioIILD.getApellido2());
						isUpdate = true;
					}
					if (!usuario.getNombres().equalsIgnoreCase(
							usuarioIILD.getNombre())) {
						usuario.setNombres(usuarioIILD.getNombre());
						isUpdate = true;
					}
					if (!usuario.getCorreo().equalsIgnoreCase(
							usuarioIILD.getEmail())) {
						usuario.setCorreo(usuarioIILD.getEmail());
						isUpdate = true;
					}
					String sNomCompleto = usuarioIILD.getNombre() + ' '
							+ usuarioIILD.getApellido1();
					if(!usuario.getNombreCompleto().equalsIgnoreCase(sNomCompleto)){							
						usuario.setNombreCompleto(sNomCompleto.trim());
						isUpdate = true;
					}
					try {
						if(isUpdate) { 
							usuarioDAO.modificar(usuario);
							logger.debug("Datos de usuario " + usuario.getCodigo() + " actualizado" );
						}
					} catch (Exception e) {
						logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el usuario:"+e);
					}
				} else {
					logger.debug("Usuario " + codigoUsuario + " no registrado en LDAP" );
				}
			}

		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al actualizarDatosUsuarios:"+e);
		}
	}
	
	/**
	 * Metodo que se encarga de generar los scrips de INSERT/UPDATE para los Jobs que registran y 
	 * actualizan información en BD de GESLEG.
	 * */
	public static void generarScriptsJobsGESLEG(String opc, Territorio te, com.hildebrando.legal.modelo.Oficina of){
		if(te!=null){
			//Insert into GESLEG.TERRITORIO (ID_TERRITORIO,ID_GRUPO_BANCA,CODIGO,DESCRIPCION,ESTADO) values (1,1,'0668','G.T.NORTE','A');
			String query ="";
			if(opc.equalsIgnoreCase("I")){
				query = "INSERT INTO GESLEG.TERRITORIO (ID_TERRITORIO,ID_GRUPO_BANCA,CODIGO,DESCRIPCION,ESTADO) values (";
				logger.debug("[GENERADOR_INSERT_TERRITORIO] ==>\t"+query+" ID_TERRITORIO,"+te.getGrupoBanca().getIdGrupoBanca()+",'"
						+te.getCodigo()+"','"+te.getDescripcion()+"','"+te.getEstado()+"');");
				//(1,1,'0668','G.T.NORTE','A');	
			}else{
				query= "UPDATE GESLEG.TERRITORIO SET ";
				logger.debug("[GENERADOR_UPDATE_TERRITORIO] ==>\t"+query+"ID_GRUPO_BANCA="+te.getGrupoBanca().getIdGrupoBanca()+", CODIGO='"+te.getCodigo()
						+"',DESCRIPCION = '"+te.getDescripcion()+"' WHERE ID_TERRITORIO = "+te.getIdTerritorio()+";");
			}
		}
		if(of!=null){
			//INSERT INTO GESLEG.OFICINA (ID_OFICINA,ID_TERRITORIO,NOMBRE,CODIGO,COD_DIST,ESTADO) VALUES (1109,1,'OF. REAL PLAZA PIURA','0667','2001000','A');  
			String queryOf ="";
			if(opc.equalsIgnoreCase("I")){
				queryOf="[GENERADOR_INSERT_OFICINA] ==>\tINSERT INTO GESLEG.OFICINA (ID_OFICINA,ID_TERRITORIO,NOMBRE,CODIGO,COD_DIST,ESTADO) VALUES (";
				logger.debug("[GENERADOR_INSERT_OFICINA] ==>\t"+queryOf+"ID_OFICINA, (SELECT ID_TERRITORIO FROM GESLEG.TERRITORIO WHERE CODIGO = '"+of.getTerritorio().getCodigo()+"' ), ["+of.getTerritorio().getIdTerritorio()+"] ,'"
						+of.getNombre()+"' , '"+of.getCodigo()+"' , '"+of.getUbigeo().getCodDist()+"' , '"+of.getEstado()+"'  ) ;");
				
				/*logger.debug("[GENERADOR_INSERT_OFICINA] ==>\t"+queryOf+"ID_OFICINA, "+of.getTerritorio().getIdTerritorio()+",'"
						+of.getNombre()+"' , '"+of.getCodigo()+"' , '"+of.getUbigeo().getCodDist()+"' , '"+of.getEstado()+"' );");
						*/	
			}else{
				queryOf = "[GENERADOR_INSERT_OFICINA] ==>\tUPDATE GESLEG.OFICINA SET ";
				logger.debug("[GENERADOR_UPDATE_OFICINA] ==>\t"+queryOf+"ID_TERRITORIO = "+of.getTerritorio().getIdTerritorio()+", " +
						"CODIGO='"+of.getCodigo()+"', NOMBRE = '"+of.getNombre()+"', COD_DIST ='"+of.getUbigeo().getCodDist()+"' WHERE ID_OFICINA = "+of.getIdOficina()+" ;");
			}
			
		}
	}
}
