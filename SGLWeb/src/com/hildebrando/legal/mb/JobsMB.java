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
import org.hibernate.criterion.Restrictions;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.general.entities.Centro;
import com.bbva.general.entities.Feriado;
import com.bbva.general.entities.Ubigeo;
import com.bbva.general.service.TablaGeneral;
import com.bbva.general.service.TablaGeneralServiceLocator;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;
import com.grupobbva.bc.per.tele.ldap.conexion.Conexion;
import com.grupobbva.bc.per.tele.ldap.serializable.IILDPeUsuario;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.Usuario;

@ManagedBean(name = "admJobs")
@SessionScoped
public class JobsMB 
{
	public static Logger logger = Logger.getLogger(JobsMB.class);

	public static void cargarOficinas() 
	{
		try {

			Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
			logger.debug("INICIA PROCESO CARGA OFICINAS: " + tstInicio);

			Centro[] resultado = obtenerDatosWebService().getCentroListado("");

			Timestamp tsLatencia = new Timestamp(new java.util.Date().getTime());
			double segundosUtilizadosLat = restarFechas(tstInicio, tsLatencia);
			logger.debug("SE DEMORO EN OBTENER LOS DATOS DEL WEB SERVICE DE OFICINAS: " + segundosUtilizadosLat + " SEGUNDOS");

			for (Centro cent : resultado) 
			{
				if (cent.getTipoOficinaCentro() != null) 
				{
					if (cent.getTipoOficinaCentro().equalsIgnoreCase("O")) {
						logger.debug("-----------------OFICINAS---------------------");
						logger.debug("Codigo Oficina: " + cent.getCodigoOficina());
						logger.debug("Descripcion: " + cent.getNombre());
						logger.debug("----------------------------------------------");

						// Seteo de atributos de oficina
						com.hildebrando.legal.modelo.Oficina oficina = new com.hildebrando.legal.modelo.Oficina();
						oficina.setCodigo(cent.getCodigoOficina());
						oficina.setNombre(cent.getNombre());

						if (!validarSiExiste("oficina", oficina)) 
						{
							// Buscar territorio en base al ubigeo de la oficina
							Timestamp tstInicioTerr = new Timestamp(new java.util.Date().getTime());
							logger.debug("INICIA SUBPROCESO BUSQUEDA TERRITORIOS: "	+ tstInicioTerr);

							List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
							
							com.bbva.general.entities.Territorio terrSrv = new com.bbva.general.entities.Territorio();
							
							terrSrv=cent.getTerritorio();
							
							if (terrSrv!=null)
							{
								results = buscarTerritorio("codigo",terrSrv.getCodigoTerritorio());
							
								logger.debug("Resultados encontrados: " + results.size());
	
								if (results.size() == 1) 
								{
									for (com.hildebrando.legal.modelo.Territorio territ : results) {
										logger.debug("------------Territorio----------------------");
										logger.debug("Codigo: " + territ.getIdTerritorio());
										logger.debug("Descripcion: " + territ.getDescripcion());
										oficina.setTerritorio(territ);
									}
								}
							}
							else
							{
								oficina.setTerritorio(null);
							}
							
							Timestamp tstFinTerr = new Timestamp(new java.util.Date().getTime());
							logger.debug("TERMINA SUBPROCESO BUSQUEDA TERRITORIOS: " + tstFinTerr);
							
							double segundosUtilizadosTerr = restarFechas(
									tstInicioTerr, tstFinTerr);
							logger.debug("PROCESO SUBPROCESO BUSQUEDA TERRITORIOS REALIZADO EN: " + segundosUtilizadosTerr + " SEGUNDOS");

							// Buscar ubigeo en base al ubigeo de la oficina
							Timestamp tstInicioUbi = new Timestamp(new java.util.Date().getTime());
							logger.debug("INICIA SUBPROCESO BUSQUEDA UBIGEOS: " + tstInicioUbi);

							List<com.hildebrando.legal.modelo.Ubigeo> results2 = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();
							results2 = buscarUbigeo(cent.getDistrito().getIDUbigeo());

							logger.debug("Resultados encontrados: " + results2.size());

							if (results2.size() == 1) 
							{
								for (com.hildebrando.legal.modelo.Ubigeo ubi : results2) 
								{
									logger.debug("------------UBIGEOS----------------------");
									logger.debug("Codigo: " + ubi.getCodDist());
									logger.debug("Distrito: " + ubi.getDistrito());
									oficina.setUbigeo(ubi);
								}

								Timestamp tstFinUbi = new Timestamp(new java.util.Date().getTime());
								logger.debug("TERMINA SUBPROCESO BUSQUEDA UBIGEOS: " + tstFinUbi);

								double segundosUtilizadosUbi = restarFechas(tstInicioUbi, tstFinUbi);
								logger.debug("PROCESO SUBPROCESO BUSQUEDA UBIGEOS REALIZADO EN: " + segundosUtilizadosUbi + " SEGUNDOS");
							}

							grabarOficina(oficina);

						}
					}
				}
			}
			Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
			// logger.info("TERMINA PROCESO CARGA OFICINAS A LAS " + tstFin);
			logger.debug("TERMINA PROCESO CARGA OFICINAS: " + tstFin);

			double segundosUtilizados = restarFechas(tstInicio, tstFin);
			// logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " +
			// segundosUtilizados + " SEGUNDOS");
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

	@SuppressWarnings({ "unchecked" })
	public static List<com.hildebrando.legal.modelo.Territorio> buscarTerritorio(String campo, String valor) {
		logger.debug("Buscando territorio: " + valor);
		List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();

		GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Territorio.class);
		filtro.add(Expression.eq(campo, valor));

		try {
			results = territorioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public static List<com.hildebrando.legal.modelo.Ubigeo> buscarUbigeo(
			String ubigeo) {
		logger.debug("Buscando ubigeo: " + ubigeo);
		List<com.hildebrando.legal.modelo.Ubigeo> results = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();

		GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Ubigeo, Integer>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Ubigeo.class);
		filtro.add(Expression.eq("codDist", ubigeo));

		try {
			results = territorioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public static void cargarTerritorios() {
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
		logger.debug("INICIA PROCESO CARGA TERRITORIOS: " + tstInicio);

		try {
			terr = obtenerDatosWebService().getTerritorioListado();
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.debug("Error al obtener listado de territorios.");
		}

		for (com.bbva.general.entities.Territorio terrSrv : terr) {
			com.hildebrando.legal.modelo.Territorio terrTMP = new com.hildebrando.legal.modelo.Territorio();

			terrTMP.setCodigo(terrSrv.getCodigoTerritorio());
			terrTMP.setDescripcion(terrSrv.getDescripcionTerritorio());

			if (!validarSiExiste("territorio", terrTMP)) {
				grabarTerritorio(terrTMP);
			}
		}
		
		Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
		// logger.info("TERMINA PROCESO CARGA OFICINAS A LAS " + tstFin);
		logger.debug("TERMINA PROCESO CARGA TERRITORIOS: " + tstFin);

		double segundosUtilizados = restarFechas(tstInicio, tstFin);
		// logger.info("PROCESO CARGA OFICINAS REALIZADO EN: " +
		// segundosUtilizados + " SEGUNDOS");
		logger.debug("PROCESO CARGA TERRITORIOS REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");


	}

	public void cargarUbigeos() {
		Ubigeo[] resUbigeoD = null;
		Ubigeo[] resUbigeoP = null;
		Ubigeo[] resUbigeoDis = null;

		Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
		logger.debug("INICIA PROCESO CARGA UBIGEOS: " + tstInicio);

		try {
			resUbigeoD = obtenerDatosWebService().getDepartamentoListado();
			resUbigeoP = obtenerDatosWebService().getProvinciaListado();
			resUbigeoDis = obtenerDatosWebService().getDistritoListado();
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.debug("Error al obtener listado de ubigeos.");
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

				if (!validarSiExiste("ubigeo", ubi)) {
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

					if (!validarSiExiste("ubigeo", ubi)) {
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

						if (!validarSiExiste("ubigeo", ubi)) {
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
			if (validarSiExiste("territorio", "codigo", territ.getCodigo())) {
				territorioDAO.modificar(territ);
			} else {
				territorioDAO.insertar(territ);
			}
			logger.debug("Se inserto territorio exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registro el territorio desde el webservice"));
			logger.debug("No registro el territorio!");
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static boolean validarSiExiste(String tabla, String campo,String valor) 
	{
		boolean existe = false;

		// Buscar si existe territorio, oficina o feriado
		if (tabla.equals("territorio")) 
		{
			/*
			 * List<com.hildebrando.legal.modelo.Territorio> results = new
			 * ArrayList<com.hildebrando.legal.modelo.Territorio>(); String
			 * query="select * from " + tabla + " where " + campo + " = '" +
			 * valor + "'" ; Query queryTerr = SpringInit.devolverSession().
			 * createSQLQuery
			 * (query).addEntity(com.hildebrando.legal.modelo.Territorio.class);
			 * 
			 * results = queryTerr.list();
			 */

			List<com.hildebrando.legal.modelo.Territorio> results = new ArrayList<com.hildebrando.legal.modelo.Territorio>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer> territorioDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Territorio, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(Territorio.class);
			filtro.add(Expression.eq(campo, valor));

			try {
				results = territorioDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (results.size() > 0) {
				existe = true;
			}
		}
		if (tabla.equals("oficina")) 
		{
			/*
			 * List<com.hildebrando.legal.modelo.Oficina> results = new
			 * ArrayList<com.hildebrando.legal.modelo.Oficina>(); String
			 * query="select * from " + tabla + " where " + campo + " = '" +
			 * valor + "'" ; Query queryOfic = SpringInit.devolverSession().
			 * createSQLQuery
			 * (query).addEntity(com.hildebrando.legal.modelo.Oficina.class);
			 * 
			 * results = queryOfic.list();
			 */

			List<com.hildebrando.legal.modelo.Oficina> results = new ArrayList<com.hildebrando.legal.modelo.Oficina>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer> oficinaDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Oficina, Integer>) 
					SpringInit.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda
					.forClass(com.hildebrando.legal.modelo.Oficina.class);
			filtro.add(Expression.eq(campo, valor));

			try {
				results = oficinaDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (results.size() > 0) {
				existe = true;
			}
		}

		if (tabla.equals("Ubigeo")) {
			/*
			 * List<com.hildebrando.legal.modelo.Oficina> results = new
			 * ArrayList<com.hildebrando.legal.modelo.Oficina>(); String
			 * query="select * from " + tabla + " where " + campo + " = '" +
			 * valor + "'" ; Query queryOfic = SpringInit.devolverSession().
			 * createSQLQuery
			 * (query).addEntity(com.hildebrando.legal.modelo.Oficina.class);
			 * 
			 * results = queryOfic.list();
			 */

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

		/*
		 * if (tabla.equals("feriado")) {
		 * List<com.hildebrando.legal.modelo.Feriado> results = new
		 * ArrayList<com.hildebrando.legal.modelo.Feriado>(); String
		 * query="select * from " + tabla + " where " + campo + " = '" + valor +
		 * "'" ; Query queryFer = SpringInit.devolverSession().
		 * createSQLQuery(query
		 * ).addEntity(com.hildebrando.legal.modelo.Feriado.class);
		 * 
		 * results = queryFer.list();
		 * 
		 * List<com.hildebrando.legal.modelo.Feriado> results = new
		 * ArrayList<com.hildebrando.legal.modelo.Feriado>();
		 * GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer>
		 * feriadoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Feriado,
		 * Integer>) SpringInit .getApplicationContext().getBean("genericoDao");
		 * 
		 * Busqueda filtro =
		 * Busqueda.forClass(com.hildebrando.legal.modelo.Feriado.class);
		 * filtro.add(Expression.eq(campo,valor));
		 * 
		 * try { results = feriadoDAO.buscarDinamico(filtro); } catch (Exception
		 * e) { e.printStackTrace(); }
		 * 
		 * if (results.size()>0) { existe=true; } }
		 */

		return existe;
	}

	@SuppressWarnings({ "unchecked" })
	public static boolean validarSiExisteFeriado(String tabla, String campo,
			Object valor) {
		boolean existe = false;

		if (tabla.equals("feriado")) {
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
		if (tabla.equals("territorio")) {
			/*
			 * List<com.hildebrando.legal.modelo.Territorio> results = new
			 * ArrayList<com.hildebrando.legal.modelo.Territorio>(); String
			 * query="select * from " + tabla + " where " + campo + " = '" +
			 * valor + "'" ; Query queryTerr = SpringInit.devolverSession().
			 * createSQLQuery
			 * (query).addEntity(com.hildebrando.legal.modelo.Territorio.class);
			 * 
			 * results = queryTerr.list();
			 */

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
				e.printStackTrace();
			}

			if (results.size() > 0) {
				existe = true;
			}
		}
		if (tabla.equals("oficina")) {
			/*
			 * List<com.hildebrando.legal.modelo.Oficina> results = new
			 * ArrayList<com.hildebrando.legal.modelo.Oficina>(); String
			 * query="select * from " + tabla + " where " + campo + " = '" +
			 * valor + "'" ; Query queryOfic = SpringInit.devolverSession().
			 * createSQLQuery
			 * (query).addEntity(com.hildebrando.legal.modelo.Oficina.class);
			 * 
			 * results = queryOfic.list();
			 */
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
				e.printStackTrace();
			}

			if (results.size() > 0) {
				existe = true;
			}
		}
		if (tabla.equals("feriado")) {
			/*
			 * List<com.hildebrando.legal.modelo.Feriado> results = new
			 * ArrayList<com.hildebrando.legal.modelo.Feriado>(); String
			 * query="select * from " + tabla + " where " + campo + " = '" +
			 * valor + "'" ; Query queryFer = SpringInit.devolverSession().
			 * createSQLQuery
			 * (query).addEntity(com.hildebrando.legal.modelo.Feriado.class);
			 * 
			 * results = queryFer.list();
			 */
			com.hildebrando.legal.modelo.Feriado feridTMP = (com.hildebrando.legal.modelo.Feriado) obj;
			List<com.hildebrando.legal.modelo.Feriado> results = new ArrayList<com.hildebrando.legal.modelo.Feriado>();
			GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer> feriadoDAO = (GenericDaoImpl<com.hildebrando.legal.modelo.Feriado, Integer>) SpringInit
					.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(com.hildebrando.legal.modelo.Feriado.class);
			filtro.add(Expression.eq("fechaInicio", feridTMP.getFechaInicio()));
			filtro.add(Expression.eq("fechaFin", feridTMP.getFechaFin()));
			filtro.add(Expression.eq("indicador", feridTMP.getIndicador()));

			try {
				results = feriadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (results.size() > 0) {
				existe = true;
			}
		}
		if (tabla.equals("ubigeo")) {
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
				e.printStackTrace();
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
			if (validarSiExisteFeriado("feriado", "fechaInicio",ferid.getFechaInicio())) 
			{
				feriadoDAO.modificar(ferid);
			} 
			else 
			{
				feriadoDAO.insertar(ferid);
			}
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("Se inserto feriado exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro",	"No se registro el feriado desde el webservice"));
			logger.debug("No registro el feriado!");
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
				ubiDAO.insertar(ubigeo);
			}
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("Se inserto ubigeo exitosamente!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se registro el ubigeo desde el webservice"));
			logger.debug("No registro el ubigeo!");
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void grabarOficina(com.hildebrando.legal.modelo.Oficina ofi) {
		GenericDao<com.hildebrando.legal.modelo.Oficina, Object> oficinaDAO = (GenericDao<com.hildebrando.legal.modelo.Oficina, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			if (!validarSiExiste("oficina", "codigo", ofi.getCodigo())) {
				logger.debug("Se inserta oficina: " + ofi.getCodigo());
				oficinaDAO.insertar(ofi);
			} else {
				logger.debug("Se actualiza informacion de oficina: " + ofi.getCodigo());
				oficinaDAO.modificar(ofi);
			}
			// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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
			logger.debug("SE DEMORO EN OBTENER LOS DATOS DEL WEB SERVICE DE FERIADOS: "	+ segundosUtilizadosLat + " SEGUNDOS");

			for (Feriado feriado : resultado) 
			{
				String annio = Integer.toString(feriado.getFecha().get(Calendar.YEAR));

				if (annio.equals("2012")) 
				{
					if (feriado.getUbigeo() != null) 
					{
						logger.debug("------------------FERIADOS LOCALES------------------------");
						logger.debug("Ind. Feriado: " + feriado.getIndicador());
						logger.debug("Fecha: "
								+ feriado.getFecha().getTime().toGMTString());
						logger.debug("Ubigeo: " + feriado.getUbigeo());
						logger.debug("Descripcion: " + feriado.getDescripcion());
						logger.debug("--------------------------------------------------");
					} 
					else 
					{
						logger.debug("------------------FERIADOS NACIONALES------------------------");
						logger.debug("Ind. Feriado: " + feriado.getIndicador());
						logger.debug("Fecha: "
								+ feriado.getFecha().getTime().toGMTString());
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

					if (!validarSiExiste("feriado", ferid)) 
					{
						if (feriado.getIndicador().equals("L")) 
						{
							Timestamp tstInicioUbi = new Timestamp(new java.util.Date().getTime());
							logger.debug("INICIA SUBPROCESO BUSQUEDA TERRITORIOS: "	+ tstInicioUbi);

							// Buscar ubigeo del feriado
							List<com.hildebrando.legal.modelo.Ubigeo> results2 = new ArrayList<com.hildebrando.legal.modelo.Ubigeo>();
							results2 = buscarUbigeo(feriado.getUbigeo());

							logger.debug("Resultados encontrados: " + results2.size());

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
						else 
						{
							grabarFeriado(ferid);
						}
					}
				}
			}

			Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
			logger.debug("TERMINA PROCESO CARGA FERIADOS: " + tstFin);

			double segundosUtilizados = restarFechas(tstInicio, tstFin);
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
			//System.out.println("Nro usuarios a verificar:" + usuarios.size());
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
							//System.out.println("Datos de usuario " + usuario.getCodigo() + " actualizado" );
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					logger.debug("Usuario " + codigoUsuario + " no registrado en ldapp" );
					//System.out.println("Usuario " + codigoUsuario + " no registrado en ldapp" );
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
