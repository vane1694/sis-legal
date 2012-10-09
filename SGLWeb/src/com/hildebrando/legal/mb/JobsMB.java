package com.hildebrando.legal.mb;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.general.entities.Centro;
import com.bbva.general.entities.Feriado;
import com.bbva.general.entities.Ubigeo;
import com.bbva.general.service.TablaGeneral;
import com.bbva.general.service.TablaGeneralServiceLocator;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;
import com.hildebrando.legal.modelo.Territorio;

@SuppressWarnings("deprecation")
@ManagedBean(name = "admJobs")
@SessionScoped
public class JobsMB {
	public static Logger logger = Logger.getLogger(JobsMB.class);

	public static void cargarOficinas() {
		try {

			Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
			logger.debug("INICIA PROCESO CARGA OFICINAS: " + tstInicio);
			
			Centro[] resultado = obtenerDatosWebService().getCentroListado("");	
			
			Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
			double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
			//logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
			logger.debug("SE DEMORO EN OBTENER LOS DATOS DEL WEB SERVICE DE OFICINAS: " + segundosUtilizadosLat + " SEGUNDOS");
			
			for (Centro cent : resultado) 
			{
				if (cent.getTipoOficinaCentro()!=null)
				{
					if (cent.getTipoOficinaCentro().equalsIgnoreCase("O"))
					{
						logger.debug("-----------------OFICINAS---------------------");
						logger.debug("Codigo Oficina: " + cent.getCodigoOficina());
						logger.debug("Descripcion: " + cent.getNombre());
						logger.debug("----------------------------------------------");
						
						//Seteo de atributos de oficina
						com.hildebrando.legal.modelo.Oficina oficina = new com.hildebrando.legal.modelo.Oficina();
						oficina.setCodigo(cent.getCodigoOficina());
						oficina.setNombre(cent.getNombre());
									
						if (!validarSiExiste("oficina",oficina))
						{
							
							Timestamp tstInicioTerr = new Timestamp(new java.util.Date().getTime());
							logger.debug("INICIA SUBPROCESO BUSQUEDA TERRITORIOS: " + tstInicioTerr);
							
							//Buscar territorio en base al ubigeo de la oficina
							List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
							results = buscarTerritorio(cent.getDistrito().getIDUbigeo());
							
							logger.debug("Resultados encontrados: " + results.size());
							
							if (results.size()==1)
							{
								for (com.hildebrando.legal.modelo.Territorio territ: results)
								{
									logger.debug("------------Territorio----------------------");
									logger.debug("Codigo: " + territ.getIdTerritorio());
									logger.debug("Ubigeo: " + territ.getUbigeo());
									logger.debug("Departamento: " + territ.getDepartamento());
									logger.debug("Provincia: " + territ.getProvincia());
									logger.debug("Distrito: " + territ.getDistrito());
									logger.debug("--------------------------------------------");
									oficina.setTerritorio(territ);
								}
								
								Timestamp tstFinTerr = new Timestamp(new java.util.Date().getTime());
								logger.debug("TERMINA SUBPROCESO BUSQUEDA TERRITORIOS: " + tstFinTerr);
								
								double segundosUtilizadosTerr = restarFechas(tstInicioTerr, tstFinTerr);
								logger.debug("PROCESO SUBPROCESO BUSQUEDA TERRITORIOS REALIZADO EN: " + segundosUtilizadosTerr + " SEGUNDOS");
								grabarOficina(oficina);
							}						
						}
					}
				}
			}
			Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
			//logger.info("TERMINA PROCESO CARGA OFICINAS A LAS " + tstFin);
			logger.debug("TERMINA PROCESO CARGA OFICINAS: " + tstFin);
			
			double segundosUtilizados = restarFechas(tstInicio, tstFin);
			//logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
			logger.debug("PROCESO CARGA OFICINAS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static double restarFechas(Timestamp fhInicial, Timestamp fhFinal) {
		long fhInicialms = fhInicial.getTime();
		long fhFinalms = fhFinal.getTime();
		long diferencia = fhFinalms - fhInicialms;
		double a = (double) diferencia / (double) (1000);
		
		return a;
	}
	
	@SuppressWarnings({ "unchecked"})
	public static List<com.hildebrando.legal.modelo.Territorio> buscarTerritorio(String ubigeo)
	{
		logger.debug("Buscando ubigeo: " + ubigeo);
		List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
		
		GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) 
				SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Territorio.class);
		filtro.add(Expression.eq("ubigeo",ubigeo));
		
		try {
			results = territorioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	public static void cargarTerritorios() {
		Ubigeo[] resUbigeoD = null;
		Ubigeo[] resUbigeoP = null;
		Ubigeo[] resUbigeoDis = null;

		String cadena = "";
		int i = 0;
		
		Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
		logger.debug("INICIA PROCESO CARGA TERRITORIOS: " + tstInicio);
		
		try {
			resUbigeoD = obtenerDatosWebService().getDepartamentoListado();
			resUbigeoP = obtenerDatosWebService().getProvinciaListado();
			resUbigeoDis = obtenerDatosWebService().getDistritoListado();
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.debug("Error al obtener listado de departamentos, provincias y/o distritos.");
		}
		
		Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
		double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
		//logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
		logger.debug("SE DEMORO EN OBTENER LOS DATOS DE LOS WEB SERVICE DE DEPARTAMENTOS, PROVINCIAS Y DISTRITOS: " + segundosUtilizadosLat + " SEGUNDOS");
		
		for (Ubigeo ubiDep : resUbigeoD) 
		{	
			i++;

			if (ubiDep.getIDUbigeo().substring(2, ubiDep.getIDUbigeo().length()).equals("00000")) 
			{
				if (cadena.length() > 0) {
					cadena += "\n[Renglon " + i + "]: " + ubiDep.getIDUbigeo() + ","
							+ ubiDep.getDescripcion() + ","
							+ ubiDep.getDescripcion() + ","
							+ ubiDep.getDescripcion();
				} else {
					cadena += "[Renglon " + i + "]: "+ubiDep.getIDUbigeo() + ","
							+ ubiDep.getDescripcion() + ","
							+ ubiDep.getDescripcion();
				}
								
				com.hildebrando.legal.modelo.Territorio territ = new com.hildebrando.legal.modelo.Territorio();
				territ.setUbigeo(ubiDep.getIDUbigeo());
				territ.setDepartamento(ubiDep.getDescripcion());
				territ.setProvincia(ubiDep.getDescripcion());
				territ.setDistrito(ubiDep.getDescripcion());
				
				if (!validarSiExiste("territorio",territ))
				{
					grabarTerritorio(territ);
				}
				
			}
			for (Ubigeo ubiProv : resUbigeoP) 
			{
				if (ubiDep.getIDUbigeo().substring(0, 2).equalsIgnoreCase(ubiProv.getIDUbigeo().substring(0, 2))
						&& !ubiProv.getIDUbigeo().substring(2, ubiProv.getIDUbigeo().length()).equals("00000")) 
				{
					if (cadena.length() > 0) 
					{
						cadena += "\n[Renglon " + i + "]: " + ubiProv.getIDUbigeo() + ","
								+ ubiDep.getDescripcion() + ","
								+ ubiProv.getDescripcion() + ","
								+ ubiProv.getDescripcion();
					} 
					else 
					{
						cadena += "[Renglon " + i + "]: "+ubiProv.getIDUbigeo() + ","
								+ ubiDep.getDescripcion() + ","
								+ ubiProv.getDescripcion() + ","
								+ ubiProv.getDescripcion();
					}
					
					com.hildebrando.legal.modelo.Territorio territ = new com.hildebrando.legal.modelo.Territorio();
					territ.setUbigeo(ubiProv.getIDUbigeo());
					territ.setDepartamento(ubiDep.getDescripcion());
					territ.setProvincia(ubiProv.getDescripcion());
					territ.setDistrito(ubiProv.getDescripcion());
					
					if (!validarSiExiste("territorio",territ))
					{
						grabarTerritorio(territ);
					}
				}

				for (Ubigeo ubiDis : resUbigeoDis) 
				{
					if (ubiDep.getIDUbigeo().substring(0, 2).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(0, 2))
							&& ubiProv.getIDUbigeo().substring(2, 4).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(2, 4))
							&& ubiProv.getIDUbigeo().substring(0, 2).equalsIgnoreCase(ubiDis.getIDUbigeo().substring(0, 2))) 
					{
						if (cadena.length() > 0) 
						{
							cadena += "\n[Renglon " + i + "]: " + ubiDis.getIDUbigeo() + ","
									+ ubiDep.getDescripcion() + ","
									+ ubiProv.getDescripcion() + ","
									+ ubiDis.getDescripcion();
						} 
						else 
						{
							cadena += "[Renglon " + i + "]: "+ubiDis.getIDUbigeo() + ","
									+ ubiDep.getDescripcion() + ","
									+ ubiProv.getDescripcion() + ","
									+ ubiDis.getDescripcion();
						}
						
						com.hildebrando.legal.modelo.Territorio territ = new com.hildebrando.legal.modelo.Territorio();
						territ.setUbigeo(ubiDis.getIDUbigeo());
						territ.setDepartamento(ubiDep.getDescripcion());
						territ.setProvincia(ubiProv.getDescripcion());
						territ.setDistrito(ubiDis.getDescripcion());
						
						
						if (!validarSiExiste("territorio",territ))
						{
							grabarTerritorio(territ);
						}
					}
				}
			}

		}
		
		Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
		//logger.info("TERMINA PROCESO CARGA OFICINAS A LAS " + tstFin);
		logger.debug("TERMINA PROCESO CARGA TERRITORIOS: " + tstFin);
		
		double segundosUtilizados = restarFechas(tstInicio, tstFin);
		//logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
		logger.debug("PROCESO CARGA TERRITORIOS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
		
		logger.debug(cadena);
	}

	@SuppressWarnings("unchecked")
	public static void grabarTerritorio(com.hildebrando.legal.modelo.Territorio territ) {
		GenericDao<com.hildebrando.legal.modelo.Territorio, Object> territorioDAO = (GenericDao<com.hildebrando.legal.modelo.Territorio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			if (validarSiExiste("territorio","ubigeo",territ.getUbigeo()))
			{
				territorioDAO.modificar(territ);
			}
			else
			{
				territorioDAO.insertar(territ);
			}
			//FacesContext.getCurrentInstance().getExternalContext()
			//		.invalidateSession();
			logger.debug("Se inserto territorio exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registro el territorio desde el webservice"));
			logger.debug("No registro el territorio!");
		}
	}
	
	@SuppressWarnings({ "unchecked"})
	public static boolean validarSiExiste(String tabla, String campo, String valor)
	{
		boolean existe = false;
		
		//Buscar si existe territorio, oficina o feriado
		if (tabla.equals("territorio"))
		{
			/*List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
			String query="select * from " + tabla + " where " + campo + " = '" + valor + "'" ;
			Query queryTerr = SpringInit.devolverSession().
					createSQLQuery(query).addEntity(com.hildebrando.legal.modelo.Territorio.class);
			
			results = queryTerr.list();*/
			
			List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(Territorio.class);
			filtro.add(Expression.eq(campo,valor));
			
			try {
				results = territorioDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			if (results.size()>0)
			{
				existe=true;
			}
		}
		if (tabla.equals("oficina"))
		{
			/*List<com.hildebrando.legal.modelo.Oficina> results = new ArrayList<com.hildebrando.legal.modelo.Oficina>();
			String query="select * from " + tabla + " where " + campo + " = '" + valor + "'" ;
			Query queryOfic = SpringInit.devolverSession().
					createSQLQuery(query).addEntity(com.hildebrando.legal.modelo.Oficina.class);
			
			results = queryOfic.list();*/
			
			List<com.hildebrando.legal.modelo.Oficina> results = new ArrayList<com.hildebrando.legal.modelo.Oficina>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer> oficinaDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Oficina.class);
			filtro.add(Expression.eq(campo,valor));
			
			try {
				results = oficinaDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (results.size()>0)
			{
				existe=true;
			}
		}
		/*if (tabla.equals("feriado"))
		{
			List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			String query="select * from " + tabla + " where " + campo + " = '" + valor + "'" ;
			Query queryFer = SpringInit.devolverSession().
					createSQLQuery(query).addEntity(com.hildebrando.legal.modelo.Feriado.class);
			
			results = queryFer.list();
			
			List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer> feriadoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Feriado.class);
			filtro.add(Expression.eq(campo,valor));
			
			try {
				results = feriadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (results.size()>0)
			{
				existe=true;
			}
		}*/
		
		return existe;
	}
	
	@SuppressWarnings({ "unchecked"})
	public static boolean validarSiExisteFeriado(String tabla, String campo, Object valor)
	{
		boolean existe = false;
		
		if (tabla.equals("feriado"))
		{
			List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer> feriadoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Feriado.class);
			filtro.add(Expression.eq(campo,valor));
			
			try {
				results = feriadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (results.size()>0)
			{
				existe=true;
			}
		}
		
		return existe;
	}
	
	@SuppressWarnings({ "unchecked"})
	public static boolean validarSiExiste(String tabla, Object obj)
	{
		boolean existe = false;
		
		//Buscar si existe territorio, oficina o feriado
		if (tabla.equals("territorio"))
		{
			/*List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
			String query="select * from " + tabla + " where " + campo + " = '" + valor + "'" ;
			Query queryTerr = SpringInit.devolverSession().
					createSQLQuery(query).addEntity(com.hildebrando.legal.modelo.Territorio.class);
			
			results = queryTerr.list();*/
			
			com.hildebrando.legal.modelo.Territorio terrTMP = (com.hildebrando.legal.modelo.Territorio) obj;
				
			List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(Territorio.class);
			filtro.add(Expression.eq("departamento",terrTMP.getDepartamento()));
			filtro.add(Expression.eq("provincia",terrTMP.getProvincia()));
			filtro.add(Expression.eq("distrito",terrTMP.getDistrito()));
			filtro.add(Expression.eq("ubigeo",terrTMP.getUbigeo()));
			
			try {
				results = territorioDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
					
			if (results.size()>0)
			{
				existe=true;
			}
		}
		if (tabla.equals("oficina"))
		{
			/*List<com.hildebrando.legal.modelo.Oficina> results = new ArrayList<com.hildebrando.legal.modelo.Oficina>();
			String query="select * from " + tabla + " where " + campo + " = '" + valor + "'" ;
			Query queryOfic = SpringInit.devolverSession().
					createSQLQuery(query).addEntity(com.hildebrando.legal.modelo.Oficina.class);
			
			results = queryOfic.list();*/
			com.hildebrando.legal.modelo.Oficina oficTMP = (com.hildebrando.legal.modelo.Oficina) obj;
			List<com.hildebrando.legal.modelo.Oficina> results = new ArrayList<com.hildebrando.legal.modelo.Oficina>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer> oficinaDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Oficina.class);
			filtro.add(Expression.eq("codigo",oficTMP.getCodigo()));
			filtro.add(Expression.eq("nombre",oficTMP.getNombre()));
			
			filtro.addOrder(Order.asc("codigo"));
			
			try {
				results = oficinaDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (results.size()>0)
			{
				existe=true;
			}
		}
		if (tabla.equals("feriado"))
		{
			/*List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			String query="select * from " + tabla + " where " + campo + " = '" + valor + "'" ;
			Query queryFer = SpringInit.devolverSession().
					createSQLQuery(query).addEntity(com.hildebrando.legal.modelo.Feriado.class);
			
			results = queryFer.list();*/
			com.hildebrando.legal.modelo.Feriado feridTMP = (com.hildebrando.legal.modelo.Feriado) obj;
			List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer> feriadoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Feriado.class);
			filtro.add(Expression.eq("fechaInicio",feridTMP.getFechaInicio()));
			filtro.add(Expression.eq("fechaFin",feridTMP.getFechaFin()));
			filtro.add(Expression.eq("indicador",feridTMP.getIndicador()));
			
			try {
				results = feriadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (results.size()>0)
			{
				existe=true;
			}
		}
		
		return existe;
	}
	
	@SuppressWarnings({ "unchecked"})
	public static void grabarFeriado(com.hildebrando.legal.modelo.Feriado ferid) 
	{
		GenericDao<com.hildebrando.legal.modelo.Feriado, Object> feriadoDAO = (GenericDao<com.hildebrando.legal.modelo.Feriado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			if (validarSiExisteFeriado("feriado","fechaInicio",ferid.getFechaInicio()))
			{
				feriadoDAO.modificar(ferid);
			}
			else
			{
				feriadoDAO.insertar(ferid);
			}
			//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("Se inserto feriado exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registro el feriado desde el webservice"));
			logger.debug("No registro el feriado!");
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void grabarOficina(com.hildebrando.legal.modelo.Oficina ofi) {
		GenericDao<com.hildebrando.legal.modelo.Oficina, Object> oficinaDAO = (GenericDao<com.hildebrando.legal.modelo.Oficina, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			if (!validarSiExiste("oficina","codigo",ofi.getCodigo()))
			{
				logger.debug("Se inserta oficina: " + ofi.getCodigo());
				oficinaDAO.insertar(ofi);
			}
			else
			{
				logger.debug("Se actualiza informacion de oficina: " + ofi.getCodigo());
				oficinaDAO.modificar(ofi);
			}
			//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("Se inserto la oficina exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registro la oficina desde el webservice"));
			logger.debug("No registro la oficina!");
		}
	}

	public static void cargarFeriados() {
		try {

			// 2 digitos: Departamento
			// 2 digitos: Provincia
			// 3 digitos: Distrito
			
			Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
			logger.debug("INICIA PROCESO CARGA FERIADOS: " + tstInicio);

			 Feriado[] resultado = obtenerDatosWebService().getFeriadoListado();
			 
			 Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
			 double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
			 //logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
			 logger.debug("SE DEMORO EN OBTENER LOS DATOS DEL WEB SERVICE DE FERIADOS: " + segundosUtilizadosLat + " SEGUNDOS");
			 
			 for (Feriado feriado : resultado) 
			 { 
				 String annio = Integer.toString(feriado.getFecha().get(Calendar.YEAR));
				 
				 if (annio.equals("2012"))
				 {
				 	if (feriado.getUbigeo()!=null) 
					{ 
				 		logger.debug("------------------FERIADOS------------------------");
				 		logger.debug("Ind. Feriado: " + feriado.getIndicador());
				 		logger.debug("Fecha: " +	feriado.getFecha().getTime().toGMTString());
				 		logger.debug("Ubigeo: " + feriado.getUbigeo());
				 		logger.debug("Descripcion: " + feriado.getDescripcion());
				 		logger.debug("--------------------------------------------------"); 
					} 
					else 
					{ 
						logger.debug("------------------FERIADOS------------------------");
						logger.debug("Ind. Feriado: " + feriado.getIndicador());
						logger.debug("Fecha: " + feriado.getFecha().getTime().toGMTString());
						logger.debug("Descripcion: " + feriado.getDescripcion());
						logger.debug("--------------------------------------------------"); 
					}
				
				 	com.hildebrando.legal.modelo.Feriado ferid = new com.hildebrando.legal.modelo.Feriado();
				 	ferid.setTipo('C');
				 	
					if (feriado.getIndicador().equals("L"))
					{
						ferid.setIndicador('L');
					}
					else
					{
						ferid.setIndicador('N');
					}
					
					ferid.setFechaInicio(feriado.getFecha().getTime());
					ferid.setFechaFin(feriado.getFecha().getTime());
					
					if (!validarSiExiste("feriado",ferid))
					{
						if (feriado.getIndicador().equals("L"))
						{
							com.hildebrando.legal.modelo.Territorio tmpTerr = new com.hildebrando.legal.modelo.Territorio();
							Timestamp tstInicioTerr = new Timestamp(new java.util.Date().getTime());
							logger.debug("INICIA SUBPROCESO BUSQUEDA TERRITORIOS: " + tstInicioTerr);
							
							//Buscar territorio en base al ubigeo del feriado
							List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
							results = buscarTerritorio(feriado.getUbigeo());
							
							logger.debug("Resultados encontrados: " + results.size());
							
							if (results.size()==1)
							{
								for (com.hildebrando.legal.modelo.Territorio territ: results)
								{
									logger.debug("------------Territorio----------------------");
									logger.debug("Codigo: " + territ.getIdTerritorio());
									logger.debug("Ubigeo: " + territ.getUbigeo());
									logger.debug("Departamento: " + territ.getDepartamento());
									logger.debug("Provincia: " + territ.getProvincia());
									logger.debug("Distrito: " + territ.getDistrito());
									logger.debug("--------------------------------------------");
									tmpTerr.setIdTerritorio(territ.getIdTerritorio());
									ferid.setTerritorio(tmpTerr);
								}
								Timestamp tstFinTerr = new Timestamp(new java.util.Date().getTime());
								logger.debug("TERMINA SUBPROCESO BUSQUEDA TERRITORIOS: " + tstFinTerr);
								
								double segundosUtilizadosTerr = restarFechas(tstInicioTerr, tstFinTerr);
								logger.debug("PROCESO SUBPROCESO BUSQUEDA TERRITORIOS REALIZADO EN: " + segundosUtilizadosTerr + " SEGUNDOS");
								
								grabarFeriado(ferid);
							}
							else
							{
								logger.debug("No se encontro territorio para el codigo ubigeo: " +feriado.getUbigeo());
							}
						}
						else
						{
							grabarFeriado(ferid);
						}					
					}
				 }					
			}
			 
			Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
			//logger.info("TERMINA PROCESO CARGA OFICINAS A LAS " + tstFin);
			logger.debug("TERMINA PROCESO CARGA FERIADOS: " + tstFin);
			
			double segundosUtilizados = restarFechas(tstInicio, tstFin);
			//logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
			logger.debug("PROCESO CARGA FERIADOS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TablaGeneral obtenerDatosWebService() {
		TablaGeneral tbGeneralWS = null;
		try {
			TablaGeneralServiceLocator tablaGeneralServiceLocator = new TablaGeneralServiceLocator();

			tbGeneralWS = tablaGeneralServiceLocator.getTablaGeneral();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return tbGeneralWS;
	}
}
