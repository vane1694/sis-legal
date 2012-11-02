package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.Aviso;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoCautelar;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Etapa;
import com.hildebrando.legal.modelo.Feriado;
import com.hildebrando.legal.modelo.FormaConclusion;
import com.hildebrando.legal.modelo.GrupoBanca;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Riesgo;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.SituacionActProc;
import com.hildebrando.legal.modelo.SituacionCuota;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.TipoCautelar;
import com.hildebrando.legal.modelo.TipoDocumento;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.TipoInvolucrado;
import com.hildebrando.legal.modelo.TipoProvision;
import com.hildebrando.legal.modelo.Ubigeo;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.view.OrganoDataModel;

@ManagedBean(name = "mnt")
@SessionScoped
public class MantenimientoMB {
	
	public static Logger logger = Logger.getLogger(MantenimientoMB.class);
	
	private int idProceso;
	private String nombreProceso;
	private String abrevProceso;
	private List<Proceso> procesos;
	private String nombreVia;
	private String nombreInstancia;
	private String nombreActividad;
	private String nombreMoneda;
	private String abrevMoneda;
	private String rucEstudio;
	private String nombreEstudio;
	private String direccionEstudio;
	private String telefEstudio;
	private String correoEstudio;
	private String nombreEstCaut;
	private String nombreEstExpe;
	private String nombreEtapa;
	private String nombreEntidad;
	private String nombreFormConc;
	private String nombreRecurrencia;
	private String nombreSitActPro;
	private String nombreSitCuota;
	private String nombreSitHonor;
	private String nombreSitIncul;
	private String nombreTipoCaut;
	private String nombreTipoExpe;
	private String nombreTipoHonor;
	private String nombreTipoInv;
	private String nombreTipoPro;
	private String nombreRolInvol;
	private String nombreMateria;
	private String nombreRiesgo;
	private String tipoDocumento;
	private String descrCalificacion;
	private FormaConclusion formaConclusion;
	private String descrFormaConclusion;
	private String codigoDistrito;
	private String nomDistrito;
	private String codigoProvincia;
	private String nomProvincia;
	private String codigoDepartamento;
	private String nomDepartamento;
	private String nomGrupoBanca;
	private String codTerritorio;
	private String nomTerritorio;
	private List<GrupoBanca> lstGrupoBanca;
	private int idGrupoBanca;
	private Date fechaInicio;
	private Date fechaFin;
	private List<Organo> lstOrgano;
	private List<Ubigeo> lstUbigeo;
	private int idOrganos;
	private String idUbigeo;
	private String tipoFeriado;
	private String indFeriado;
	private Oficina oficina;
	private List<Territorio> lstTerritorio;
	private String codigoOficina;
	private String nomOficina;
	private List<Via> lstVias;
	private List<Actividad>	lstActividad;
	private int idVia;
	private int idActividad;
	private int numDiasRojoEst1;
	private int numNaraEst1;
	private int numAmaEst1;
	private int numDiasRojoEst2;
	private int numNaraEst2;
	private int numAmaEst2;
	private int numDiasRojoEst3;
	private int numNaraEst3;
	private int numAmaEst3;
	private int idProcesoEstado;
	
	
	public MantenimientoMB() {

		logger.debug("Inicializando Valores..");
		inicializarValores();
		
		cargarCombos();
	}
	
	
	private void inicializarValores() {
		
		setNombreProceso("");
		setAbrevProceso("");
		setNombreVia("");
		setNombreMateria("");
		setCodigoOficina("");
		setNomOficina("");
		
	}
	
	public void limpiarMateria(ActionEvent e) {

		setNombreMateria("");
	}
	
	public void limpiarRiesgo(ActionEvent e) {

		setNombreRiesgo("");
	}
	
	public void limpiarTipoDoc(ActionEvent e) {

		setTipoDocumento("");
	}
	
	public void limpiarCalificacion(ActionEvent e) {

		setDescrCalificacion("");
	}
	
	public void limpiarTerritorio(ActionEvent e) {

		setCodTerritorio("");
		setNomTerritorio("");
		
		setIdGrupoBanca(0);
	}
	
	public void limpiarGrupoBanca(ActionEvent e) {

		setNomGrupoBanca("");
	}
	
	public void limpiarOficina(ActionEvent e) {

		setCodigoOficina("");
		setNomOficina("");
		setCodTerritorio("");
		setIdUbigeo("");
	}
	
	public void limpiarUbigeo(ActionEvent e) {

		setCodigoDepartamento("");
		setNomDepartamento("");
		setCodigoProvincia("");
		setNomProvincia("");
		setCodigoDistrito("");
		setNomDistrito("");
	}
	
	public void limpiarFeriado(ActionEvent e) {

		setIdOrganos(0);
		setIdUbigeo("");
		setFechaInicio(null);
		setFechaFin(null);
	}
	
	public void limpiarEstados(ActionEvent e) {
		setNumDiasRojoEst1(0);
		setNumNaraEst1(0);
		setNumAmaEst1(0);
		setNumDiasRojoEst2(0);
		setNumNaraEst2(0);
		setNumAmaEst2(0);
		setNumDiasRojoEst3(0);
		setNumNaraEst3(0);
		setNumAmaEst3(0);
		setIdProcesoEstado(0);
		setIdActividad(0);
		setIdVia(0);
	}

	private void cargarCombos() {
		
		//Carga Proceso
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		
		try {
			procesos = procesoDAO.buscarDinamico(filtroProceso);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de procesos");
		}
		
		//Carga Grupo Banca
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca = Busqueda.forClass(GrupoBanca.class);
		
		try {
			lstGrupoBanca =  grupoBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de grupo banca");
		}
		
		//Carga Organos
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOrgano = Busqueda.forClass(Organo.class);
		
		try {
			lstOrgano =  organoDAO.buscarDinamico(filtroOrgano);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de organos");
		}
		
		//Carga Ubigeos
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbigeo = Busqueda.forClass(Ubigeo.class);
		
		try {
			lstUbigeo =  ubiDAO.buscarDinamico(filtroUbigeo);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de ubigeos");
		}
		
		//Carga Territorio
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerr = Busqueda.forClass(Territorio.class);
		
		try {
			lstTerritorio =  terrDAO.buscarDinamico(filtroTerr);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de territorio");
		}
		
		//Carga Vias
		GenericDao<Via, Object> viasDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class);
		
		try {
			lstVias =  viasDAO.buscarDinamico(filtroVia);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de vias");
		}
		
		//Carga Actividades
		GenericDao<Actividad, Object> actDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAct = Busqueda.forClass(Actividad.class);
		
		try {
			lstActividad =  actDAO.buscarDinamico(filtroAct);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de actividades");
		}
		
	}
	
	public Organo buscarOrgano(int idOrgano)
	{
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOrgano = Busqueda.forClass(Organo.class);
		filtroOrgano.add(Restrictions.eq("idOrgano", idOrgano));
		Organo tmpOrg = new Organo();
		
		try {
			lstOrgano =  organoDAO.buscarDinamico(filtroOrgano);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Organo org:lstOrgano)
		{
			if (lstOrgano.size()==1)
			{
				tmpOrg.setIdOrgano(org.getIdOrgano());
				tmpOrg.setNombre(org.getNombre());
			}
		}
		
		return tmpOrg;
	}
	
	public Via buscarViasPorProceso(ValueChangeEvent e)
	{
		logger.debug("Buscando vias por proceso: " + e.getNewValue());
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia= Busqueda.forClass(Via.class).createAlias("proceso", "pro");
		filtroVia.add(Restrictions.eq("pro.idProceso",e.getNewValue()));
		Via tmpVia = new Via();
		
		try {
			lstVias =  viaDAO.buscarDinamico(filtroVia);
		} catch (Exception exp) {
			logger.debug("No se pudo encontrar las vias del proceso seleccionado");
		}
		
		for (Via via:lstVias)
		{
			if (lstVias.size()==1)
			{
				tmpVia.setIdVia(via.getIdVia());
				tmpVia.setNombre(via.getNombre());
			}
		}
		
		return tmpVia;
	}
	
	public Ubigeo buscarUbigeo(String ubigeo)
	{
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbigeo = Busqueda.forClass(Ubigeo.class);
		filtroUbigeo.add(Restrictions.eq("codDist", ubigeo));
		Ubigeo tmpUbi = new Ubigeo();
		
		try {
			lstUbigeo =  ubiDAO.buscarDinamico(filtroUbigeo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Ubigeo tmpUbigeo:lstUbigeo)
		{
			if (lstUbigeo.size()==1)
			{
				tmpUbi.setCodDist(tmpUbigeo.getCodDist());
				tmpUbi.setDistrito(tmpUbigeo.getDistrito());
				tmpUbi.setCodDep(tmpUbigeo.getCodDep());
				tmpUbi.setDepartamento(tmpUbigeo.getDepartamento());
				tmpUbi.setCodProv(tmpUbigeo.getCodProv());
				tmpUbi.setProvincia(tmpUbigeo.getProvincia());
			}
		}
		
		return tmpUbi;
	}
	
	public GrupoBanca buscarGrupoBanca(int idGrupoBanca)
	{
		GenericDao<GrupoBanca, Object> gBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca = Busqueda.forClass(GrupoBanca.class);
		filtroGrupoBanca.add(Restrictions.eq("idGrupoBanca", idGrupoBanca));
		GrupoBanca tmpGBanca = new GrupoBanca();
		
		try {
			lstGrupoBanca =  gBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (GrupoBanca tmpGrupoBanca:lstGrupoBanca)
		{
			if (lstGrupoBanca.size()==1)
			{
				tmpGBanca.setIdGrupoBanca(tmpGrupoBanca.getIdGrupoBanca());
				tmpGBanca.setDescripcion(tmpGrupoBanca.getDescripcion());
			}
		}
		
		return tmpGBanca;
	}
	
	public Territorio buscarTerritorio(String codTerr)
	{
		GenericDao<Territorio, Object> ubiDAO = (GenericDao<Territorio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerritorio = Busqueda.forClass(Territorio.class);
		filtroTerritorio.add(Restrictions.eq("codigo", codTerr));
		Territorio tmpTerr = new Territorio();
		
		try {
			lstTerritorio =  ubiDAO.buscarDinamico(filtroTerritorio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Territorio terr:lstTerritorio)
		{
			if (lstTerritorio.size()==1)
			{
				tmpTerr.setCodigo(terr.getCodigo());
				tmpTerr.setDescripcion(terr.getDescripcion());
			}
		}
		
		return tmpTerr;
	}
	
	public Proceso buscarProceso(int codProceso)
	{
		GenericDao<Proceso, Object> ubiDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		filtroProceso.add(Restrictions.eq("idProceso", codProceso));
		Proceso tmpProc = new Proceso();
		
		try {
			procesos =  ubiDAO.buscarDinamico(filtroProceso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Proceso pr:procesos)
		{
			if (procesos.size()==1)
			{
				tmpProc.setIdProceso(pr.getIdProceso());
				tmpProc.setNombre(pr.getNombre());
			}
		}
		
		return tmpProc;
	}
	
	public Via buscarVia(int codVia)
	{
		GenericDao<Via, Object> ubiDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class);
		filtroVia.add(Restrictions.eq("idVia", codVia));
		Via tmpVi = new Via();
		
		try {
			lstVias =  ubiDAO.buscarDinamico(filtroVia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Via vi:lstVias)
		{
			if (lstVias.size()==1)
			{
				tmpVi.setIdVia(vi.getIdVia());
				tmpVi.setNombre(vi.getNombre());
			}
		}
		
		return tmpVi;
	}
	
	public Actividad buscarActividad(int codAct)
	{
		GenericDao<Actividad, Object> actDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAct = Busqueda.forClass(Actividad.class);
		filtroAct.add(Restrictions.eq("idActividad", codAct));
		Actividad tmpAct = new Actividad();
		
		try {
			lstActividad =  actDAO.buscarDinamico(filtroAct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Actividad act:lstActividad)
		{
			if (lstActividad.size()==1)
			{
				tmpAct.setIdActividad(act.getIdActividad());
				tmpAct.setNombre(act.getNombre());
			}
		}
		
		return tmpAct;
	}
	
	public void agregarMateria(ActionEvent e) {

		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Materia mat= new Materia();
		mat.setDescripcion(getNombreMateria());
		
		try {
			materiaDAO.insertar(mat);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la materia"));
			logger.debug("guardo la materia exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la materia"));
			logger.debug("no guardo la materia por "+ ex.getMessage());
		}
		
	}
	
	public void agregarEstados(ActionEvent e) 
	{
		GenericDao<Aviso, Object> avisoDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		if (idProcesoEstado!=0)
		{
			logger.debug("Buscando configuracion para proceso: " + idProcesoEstado);
			
			int numDiasRojoEst1 = getNumDiasRojoEst1();
			int numDiasAmaEst1 = getNumAmaEst1();
			int numDiasNaraEst1 = getNumNaraEst1();
			
			logger.debug("Numero de dias en rojo: " + numDiasRojoEst1);
			logger.debug("Numero de dias en amarillo: " + numDiasAmaEst1);
			logger.debug("Numero de dias en naranja: " + numDiasNaraEst1);
			
			if (numDiasRojoEst1<numDiasNaraEst1 && numDiasNaraEst1<numDiasAmaEst1)
			{
				Aviso avis= new Aviso();
				avis.setProceso(buscarProceso(getIdProcesoEstado()));
				avis.setColor('R');
				avis.setDias(numDiasRojoEst1);
				
				try {
					avisoDAO.insertar(avis);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				

				Aviso avis2= new Aviso();
				avis2.setProceso(buscarProceso(getIdProcesoEstado()));
				avis2.setColor('N');
				avis2.setDias(numDiasNaraEst1);
				
				try {
					avisoDAO.insertar(avis2);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				
				Aviso avis3= new Aviso();
				avis3.setProceso(buscarProceso(getIdProcesoEstado()));
				avis3.setColor('A');
				avis3.setDias(numDiasAmaEst1);
				
				try {
					avisoDAO.insertar(avis3);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","No agrego configuracion de notificaciones debido a una mala configuracion de parametros"));
				logger.debug("No agrego configuracion de notificaciones debido a una mala configuracion de parametros");
			}
			
		}
		
		if (idVia!=0)
		{
			logger.debug("Buscando configuracion para via: " + idVia);
			
			int numDiasRojoEst2 = getNumDiasRojoEst2();
			int numDiasAmaEst2 = getNumAmaEst2();
			int numDiasNaraEst2 = getNumNaraEst2();
			
			logger.debug("Numero de dias en rojo: " + numDiasRojoEst2);
			logger.debug("Numero de dias en amarillo: " + numDiasAmaEst2);
			logger.debug("Numero de dias en naranja: " + numDiasNaraEst2);
			
			if (numDiasRojoEst2<numDiasNaraEst2 && numDiasNaraEst2<numDiasAmaEst2)
			{
				Aviso avis= new Aviso();
				avis.setVia(buscarVia(getIdVia()));
				avis.setColor('R');
				avis.setDias(numDiasRojoEst2);
				
				try {
					avisoDAO.insertar(avis);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				

				Aviso avis2= new Aviso();
				avis2.setVia(buscarVia(getIdVia()));
				avis2.setColor('N');
				avis2.setDias(numDiasNaraEst2);
				
				try {
					avisoDAO.insertar(avis2);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				
				Aviso avis3= new Aviso();
				avis3.setVia(buscarVia(getIdVia()));
				avis3.setColor('A');
				avis3.setDias(numDiasAmaEst2);
				
				try {
					avisoDAO.insertar(avis3);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","No agrego configuracion de notificaciones debido a una mala configuracion de parametros"));
				logger.debug("No agrego configuracion de notificaciones debido a una mala configuracion de parametros");
			}
			
		}
		
		if (idActividad!=0)
		{
			logger.debug("Buscando configuracion para actividad: " + idActividad);
			
			int numDiasRojoEst3 = getNumDiasRojoEst3();
			int numDiasAmaEst3 = getNumAmaEst3();
			int numDiasNaraEst3 = getNumNaraEst3();
			
			logger.debug("Numero de dias en rojo: " + numDiasRojoEst3);
			logger.debug("Numero de dias en amarillo: " + numDiasAmaEst3);
			logger.debug("Numero de dias en naranja: " + numDiasNaraEst3);
			
			if (numDiasRojoEst3<numDiasNaraEst3 && numDiasNaraEst3<numDiasAmaEst3)
			{
				Aviso avis= new Aviso();
				avis.setActividad(buscarActividad(getIdActividad()));
				avis.setColor('R');
				avis.setDias(numDiasRojoEst3);
				
				try {
					avisoDAO.insertar(avis);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				

				Aviso avis2= new Aviso();
				avis2.setActividad(buscarActividad(getIdActividad()));
				avis2.setColor('N');
				avis2.setDias(numDiasNaraEst3);
				
				try {
					avisoDAO.insertar(avis2);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				
				Aviso avis3= new Aviso();
				avis3.setActividad(buscarActividad(getIdActividad()));
				avis3.setColor('A');
				avis3.setDias(numDiasAmaEst3);
				
				try {
					avisoDAO.insertar(avis3);
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");
					
				} catch (Exception ex) {
					
					FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}
				
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","No agrego configuracion de notificaciones debido a una mala configuracion de parametros"));
				logger.debug("No agrego configuracion de notificaciones debido a una mala configuracion de parametros");
			}
			
		}
		
	}
	
	
	
	public void agregarRiesgo(ActionEvent e) 
	{
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Riesgo riesgo= new Riesgo();
		riesgo.setDescripcion(getNombreRiesgo());
		
		try {
			riesgoDAO.insertar(riesgo);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el riesgo"));
			logger.debug("guardo el riesgo exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el riesgo"));
			logger.debug("no guardo el riesgo por "+ ex.getMessage());
		}
	}
	
	public void agregarTipoDocumento(ActionEvent e) 
	{
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoDocumento tp= new TipoDocumento();
		tp.setDescripcion(getTipoDocumento());
		
		try {
			tipoDocDAO.insertar(tp);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo de documento"));
			logger.debug("guardo el tipo de documento exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo de documento"));
			logger.debug("no guardo el tipo de documento por "+ ex.getMessage());
		}
	}
	
	public void agregarCalificacion(ActionEvent e) 
	{
		GenericDao<Calificacion, Object> calDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Calificacion cal= new Calificacion();
		cal.setNombre(getDescrCalificacion());
		
		try {
			calDAO.insertar(cal);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la calificacion"));
			logger.debug("guardo la calificacion exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la calificacion"));
			logger.debug("no guardo la calificacion por "+ ex.getMessage());
		}
	}
	
	public void agregarFormaConclusion(ActionEvent e) 
	{
		GenericDao<FormaConclusion, Object> formaDAO = (GenericDao<FormaConclusion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		FormaConclusion fc= new FormaConclusion();
		fc.setDescripcion(getDescrFormaConclusion());
		
		try {
			formaDAO.insertar(fc);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la forma de conclusion"));
			logger.debug("guardo la forma de conclusion exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la forma de conclusion"));
			logger.debug("no guardo la forma de conclusion por "+ ex.getMessage());
		}
	}
	
	public void agregarGrupoBanca(ActionEvent e) 
	{
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		GrupoBanca gb= new GrupoBanca();
		gb.setDescripcion(getNomGrupoBanca());
		
		try {
			grupoBancaDAO.insertar(gb);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el Grupo Banca"));
			logger.debug("guardo el Grupo Banca exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el Grupo Banca"));
			logger.debug("no guardo el Grupo Banca por "+ ex.getMessage());
		}
	}
	
	public void agregarTerritorio(ActionEvent e) 
	{
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Territorio terr= new Territorio();
		terr.setCodigo(getCodTerritorio());
		terr.setDescripcion(getNomTerritorio());
		terr.setGrupoBanca(buscarGrupoBanca(getIdGrupoBanca()));
		
		try {
			terrDAO.insertar(terr);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el Territorio"));
			logger.debug("guardo el Territorio exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el Territorio"));
			logger.debug("no guardo el Territorio por "+ ex.getMessage());
		}
	}
	
	public void agregarFeriado(ActionEvent e) 
	{
		GenericDao<Feriado, Object> feriadoDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Feriado fer= new Feriado();
		fer.setFechaInicio(getFechaInicio());
		fer.setFechaFin(getFechaFin());
		
		if (getIdOrganos()==0)
		{
			fer.setOrgano(null);
			fer.setTipo('C');
		}
		else
		{
			fer.setOrgano(buscarOrgano(getIdOrganos()));
			fer.setTipo('O');
		}
		fer.setUbigeo(buscarUbigeo(getIdUbigeo()));
		
		if (indFeriado.equals("L"))
		{
			fer.setIndicador('L');
		}
		else
		{
			fer.setIndicador('N');
		}
				
		try {
			feriadoDAO.insertar(fer);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el Feriado"));
			logger.debug("guardo el Feriado exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el Feriado"));
			logger.debug("no guardo el Feriado por "+ ex.getMessage());
		}
	}
	
	public void agregarOficina(ActionEvent e) 
	{
		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		logger.debug("Parametros ingresados para insercion....");
		logger.debug("Oficina:" + getCodigoOficina());
		logger.debug("Nombre:" + getNomOficina());
		logger.debug("Cod Territorio: " + getCodTerritorio());
		logger.debug("Cod Ubigeo: " + getIdUbigeo());
		
		
		Oficina ofic= new Oficina();
		ofic.setCodigo(getCodigoOficina());
		ofic.setNombre(getNomOficina());
		
		Territorio terr = new Territorio();	
		terr= buscarTerritorio(getCodTerritorio());
		ofic.setTerritorio(terr);
		
		Ubigeo ubi = new Ubigeo();
		ubi = buscarUbigeo(getIdUbigeo());
		ofic.setUbigeo(ubi);
				
		try {
			oficinaDAO.insertar(ofic);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la oficina"));
			logger.debug("guardo la oficina exitosamente");
			
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la oficina"));
			logger.debug("no guardo la oficina por "+ ex.getMessage());
		}
	}
	
	public void agregarUbigeo(ActionEvent e) 
	{
		GenericDao<Ubigeo, Object> ubigeoDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Ubigeo ubi= new Ubigeo();
		ubi.setCodDist(getCodigoDistrito());
		ubi.setDistrito(getNomDistrito());
		ubi.setCodDep(getCodigoDepartamento());
		ubi.setDepartamento(getNomDepartamento());
		ubi.setCodProv(getCodigoProvincia());
		ubi.setProvincia(getNomProvincia());
		
		try {
			ubigeoDAO.insertar(ubi);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el ubigeo"));
			logger.debug("guardo el ubigeo exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el ubigeo"));
			logger.debug("no guardo el ubigeo por "+ ex.getMessage());
		}
	}
	
	
	public void agregarProceso(ActionEvent e) {

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Proceso proceso= new Proceso();
		proceso.setNombre(getNombreProceso());
		proceso.setAbreviatura(getAbrevProceso());
		
		try {
			procesoDAO.insertar(proceso);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el proceso"));
			logger.debug("guardo el proceso exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el proceso"));
			logger.debug("no guardo el proceso por "+ ex.getMessage());
		}
		
	}
	
	public void agregarVia(ActionEvent e) {

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Via via = new Via();
		via.setNombre(getNombreVia());
		
		try {
			
			via.setProceso(procesoDAO.buscarById(Proceso.class, getIdProceso()));
			viaDAO.insertar(via);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la via"));
			logger.debug("guardo la via exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la via"));
			logger.debug("no guardo la via por "+ ex.getMessage());
		}
		
	}
	
	public void agregarInstancia(ActionEvent e) {

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
		Instancia instancia= new Instancia();
		instancia.setNombre(getNombreInstancia());
	
		try {
			instanciaDAO.insertar(instancia);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la instancia"));
			logger.debug("guardo la instancia exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la instancia"));
			logger.debug("no guardo la instancia por "+ ex.getMessage());
		}
		
	}
	
	public void agregarActividad(ActionEvent e) {

		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Actividad actividad= new Actividad();
		actividad.setNombre(getNombreActividad());
	
		try {
			actividadDAO.insertar(actividad);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la actividad"));
			logger.debug("guardo la actividad exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la actividad"));
			logger.debug("no guardo la actividad por "+ ex.getMessage());
		}
		
	}
	
	public void agregarMoneda(ActionEvent e) {

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
		Moneda moneda= new Moneda();
		moneda.setDescripcion(getNombreMoneda());
		moneda.setSimbolo(getAbrevMoneda());
	
		try {
			monedaDAO.insertar(moneda);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo de moneda"));
			logger.debug("guardo el tipo de moneda exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo de moneda"));
			logger.debug("no guardo el tipo de moneda por "+ ex.getMessage());
		}
		
	}
	
	public void agregarEstudio(ActionEvent e) {

		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Estudio  estudio= new Estudio();
		
		estudio.setRuc(Long.parseLong(getRucEstudio()));
		estudio.setCorreo(getCorreoEstudio());
		estudio.setDireccion(getDireccionEstudio());
		estudio.setNombre(getNombreEstudio());
		estudio.setTelefono(getTelefEstudio());
		
	
		try {
			estudioDAO.insertar(estudio);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el estudio"));
			logger.debug("guardo el estudio exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el estudio"));
			logger.debug("no guardo el estudio por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarRolInv(ActionEvent e) {

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		RolInvolucrado rolInvolucrado= new RolInvolucrado();
		rolInvolucrado.setNombre(getNombreRolInvol());
		
		
		try {
			rolInvolucradoDAO.insertar(rolInvolucrado);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el rol involucrado"));
			logger.debug("guardo el rol involucrado exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el rol involucrado"));
			logger.debug("no guardo el rol involucrado por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEstCaut(ActionEvent e) {

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		EstadoCautelar estadoCautelar= new EstadoCautelar();
		
		estadoCautelar.setDescripcion(getNombreEstCaut());
		
		try {
			estadoCautelarDAO.insertar(estadoCautelar);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el estado cautelar"));
			logger.debug("guardo el estado cautelar exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el estado cautelar"));
			logger.debug("no guardo el estado cautelar por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEstExpe(ActionEvent e) {

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		EstadoExpediente estadoExpediente= new EstadoExpediente();
		
		estadoExpediente.setNombre(getNombreEstExpe());
		
		try {
			estadoExpedienteDAO.insertar(estadoExpediente);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el estado expediente"));
			logger.debug("guardo el estado expediente exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el estado expediente"));
			logger.debug("no guardo el estado expediente por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEtapa(ActionEvent e) {

		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Etapa etapa= new Etapa();
		etapa.setNombre(getNombreEtapa());
		
		try {
			etapaDAO.insertar(etapa);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la etapa"));
			logger.debug("guardo la etapa exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la etapa"));
			logger.debug("no guardo la etapa por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEntidad(ActionEvent e) {

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Entidad entidad= new Entidad();
		entidad.setNombre(getNombreEntidad());
		
		try {
			entidadDAO.insertar(entidad);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la entidad"));
			logger.debug("guardo la entidad exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la entidad"));
			logger.debug("no guardo la entidad por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarFormConc(ActionEvent e) {

		GenericDao<FormaConclusion, Object> formaConclusionDAO = (GenericDao<FormaConclusion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		FormaConclusion formaConclusion= new FormaConclusion();
		formaConclusion.setDescripcion(getNombreFormConc());
		
		try {
			formaConclusionDAO.insertar(formaConclusion);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la forma conclusion"));
			logger.debug("guardo la forma conclusion exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la forma conclusion"));
			logger.debug("no guardo la forma conclusion por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarRecurrencia(ActionEvent e) {

		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Recurrencia recurrencia= new Recurrencia();
		recurrencia.setNombre(getNombreRecurrencia());
			
		try {
			recurrenciaDAO.insertar(recurrencia);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la recurrencia"));
			logger.debug("guardo la recurrencia exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la recurrencia"));
			logger.debug("no guardo la recurrencia por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarSitActPro(ActionEvent e) {

		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		SituacionActProc situacionActProc= new SituacionActProc();
		situacionActProc.setNombre(getNombreSitActPro());
			
		try {
			situacionActProcDAO.insertar(situacionActProc);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac act pro"));
			logger.debug("guardo la situac act pro exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac act pro"));
			logger.debug("no guardo la situac act pro por "+ ex.getMessage());
		}
		
	}

	public void agregarSitCuota(ActionEvent e) {

		GenericDao<SituacionCuota, Object> situacionCuotaDAO = (GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		SituacionCuota situacionCuota= new SituacionCuota();
		situacionCuota.setDescripcion(getNombreSitCuota());
			
		try {
			situacionCuotaDAO.insertar(situacionCuota);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac cuota"));
			logger.debug("guardo la situac cuota exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac cuota"));
			logger.debug("no guardo la situac cuota por "+ ex.getMessage());
		}
		
	}
	
	public void agregarSitHonor(ActionEvent e) {

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		SituacionHonorario situacionHonorario= new SituacionHonorario();
		situacionHonorario.setDescripcion(getNombreSitHonor());
		
			
		try {
			situacionHonorarioDAO.insertar(situacionHonorario);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac honor"));
			logger.debug("guardo la situac honor exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac honor"));
			logger.debug("no guardo la situac honor por "+ ex.getMessage());
		}
		
	}
	
	public void agregarSitIncul(ActionEvent e) {

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		
		SituacionInculpado situacionInculpado= new SituacionInculpado();
		situacionInculpado.setNombre(getNombreSitIncul());
			
		try {
			situacionInculpadoDAO.insertar(situacionInculpado);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac inculp"));
			logger.debug("guardo la situac inculp exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac inculp"));
			logger.debug("no guardo la situac inculp por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoCaut(ActionEvent e) {

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoCautelar tipoCautelar= new TipoCautelar();
		tipoCautelar.setDescripcion(getNombreTipoCaut());
			
		try {
			tipoCautelarDAO.insertar(tipoCautelar);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo cautelar"));
			logger.debug("guardo el tipo cautelar exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo cautelar"));
			logger.debug("no guardo el tipo cautelar por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoExpe(ActionEvent e) {

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoExpediente tipoExpediente= new TipoExpediente();
		tipoExpediente.setNombre(getNombreTipoExpe());
			
		try {
			tipoExpedienteDAO.insertar(tipoExpediente);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo expediente"));
			logger.debug("guardo el tipo expediente exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo expediente"));
			logger.debug("no guardo el tipo expediente por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoHonor(ActionEvent e) {

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoHonorario tipoHonorario= new TipoHonorario();
		tipoHonorario.setDescripcion(getNombreTipoHonor());
			
		try {
			tipoHonorarioDAO.insertar(tipoHonorario);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo honorario"));
			logger.debug("guardo el tipo honorario exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo honorario"));
			logger.debug("no guardo el tipo honorario por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoInv(ActionEvent e) {

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoInvolucrado tipoInvolucrado= new TipoInvolucrado();
		tipoInvolucrado.setNombre(getNombreTipoInv());
		
			
		try {
			tipoInvolucradoDAO.insertar(tipoInvolucrado);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo honorario"));
			logger.debug("guardo el tipo honorario exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo honorario"));
			logger.debug("no guardo el tipo honorario por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoPro(ActionEvent e) {

		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoProvision tipoProvision= new TipoProvision();
		tipoProvision.setDescripcion(getNombreTipoPro());
		
			
		try {
			tipoProvisionDAO.insertar(tipoProvision);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo provision"));
			logger.debug("guardo el tipo provision exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo provision"));
			logger.debug("no guardo el tipo provision por "+ ex.getMessage());
		}
		
	}
	
	public String getNombreProceso() {
		return nombreProceso;
	}


	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}


	public String getAbrevProceso() {
		return abrevProceso;
	}


	public void setAbrevProceso(String abrevProceso) {
		this.abrevProceso = abrevProceso;
	}


	public List<Proceso> getProcesos() {
		return procesos;
	}


	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public String getNombreVia() {
		return nombreVia;
	}


	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}


	public String getNombreInstancia() {
		return nombreInstancia;
	}


	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}


	public String getNombreMoneda() {
		return nombreMoneda;
	}


	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}


	public String getAbrevMoneda() {
		return abrevMoneda;
	}


	public void setAbrevMoneda(String abrevMoneda) {
		this.abrevMoneda = abrevMoneda;
	}


	public String getNombreEstudio() {
		return nombreEstudio;
	}


	public void setNombreEstudio(String nombreEstudio) {
		this.nombreEstudio = nombreEstudio;
	}


	public String getDireccionEstudio() {
		return direccionEstudio;
	}


	public void setDireccionEstudio(String direccionEstudio) {
		this.direccionEstudio = direccionEstudio;
	}


	public String getTelefEstudio() {
		return telefEstudio;
	}


	public void setTelefEstudio(String telefEstudio) {
		this.telefEstudio = telefEstudio;
	}


	public String getCorreoEstudio() {
		return correoEstudio;
	}


	public void setCorreoEstudio(String correoEstudio) {
		this.correoEstudio = correoEstudio;
	}


	public String getRucEstudio() {
		return rucEstudio;
	}


	public void setRucEstudio(String rucEstudio) {
		this.rucEstudio = rucEstudio;
	}


	public String getNombreEstCaut() {
		return nombreEstCaut;
	}


	public void setNombreEstCaut(String nombreEstCaut) {
		this.nombreEstCaut = nombreEstCaut;
	}


	public String getNombreEstExpe() {
		return nombreEstExpe;
	}


	public void setNombreEstExpe(String nombreEstExpe) {
		this.nombreEstExpe = nombreEstExpe;
	}


	public String getNombreEtapa() {
		return nombreEtapa;
	}


	public void setNombreEtapa(String nombreEtapa) {
		this.nombreEtapa = nombreEtapa;
	}


	public String getNombreFormConc() {
		return nombreFormConc;
	}


	public void setNombreFormConc(String nombreFormConc) {
		this.nombreFormConc = nombreFormConc;
	}


	public String getNombreRecurrencia() {
		return nombreRecurrencia;
	}


	public void setNombreRecurrencia(String nombreRecurrencia) {
		this.nombreRecurrencia = nombreRecurrencia;
	}


	public String getNombreSitActPro() {
		return nombreSitActPro;
	}


	public void setNombreSitActPro(String nombreSitActPro) {
		this.nombreSitActPro = nombreSitActPro;
	}


	public String getNombreSitCuota() {
		return nombreSitCuota;
	}


	public void setNombreSitCuota(String nombreSitCuota) {
		this.nombreSitCuota = nombreSitCuota;
	}


	public String getNombreSitHonor() {
		return nombreSitHonor;
	}


	public void setNombreSitHonor(String nombreSitHonor) {
		this.nombreSitHonor = nombreSitHonor;
	}


	public String getNombreSitIncul() {
		return nombreSitIncul;
	}


	public void setNombreSitIncul(String nombreSitIncul) {
		this.nombreSitIncul = nombreSitIncul;
	}


	public String getNombreTipoCaut() {
		return nombreTipoCaut;
	}


	public void setNombreTipoCaut(String nombreTipoCaut) {
		this.nombreTipoCaut = nombreTipoCaut;
	}


	public String getNombreTipoExpe() {
		return nombreTipoExpe;
	}


	public void setNombreTipoExpe(String nombreTipoExpe) {
		this.nombreTipoExpe = nombreTipoExpe;
	}


	public String getNombreTipoHonor() {
		return nombreTipoHonor;
	}


	public void setNombreTipoHonor(String nombreTipoHonor) {
		this.nombreTipoHonor = nombreTipoHonor;
	}


	public String getNombreTipoInv() {
		return nombreTipoInv;
	}


	public void setNombreTipoInv(String nombreTipoInv) {
		this.nombreTipoInv = nombreTipoInv;
	}


	public String getNombreTipoPro() {
		return nombreTipoPro;
	}


	public void setNombreTipoPro(String nombreTipoPro) {
		this.nombreTipoPro = nombreTipoPro;
	}


	public String getNombreActividad() {
		return nombreActividad;
	}


	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}


	public String getNombreRolInvol() {
		return nombreRolInvol;
	}


	public void setNombreRolInvol(String nombreRolInvol) {
		this.nombreRolInvol = nombreRolInvol;
	}


	public String getNombreEntidad() {
		return nombreEntidad;
	}


	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}


	public int getIdProceso() {
		return idProceso;
	}


	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}


	public String getNombreMateria() {
		return nombreMateria;
	}


	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}


	public String getNombreRiesgo() {
		return nombreRiesgo;
	}


	public void setNombreRiesgo(String nombreRiesgo) {
		this.nombreRiesgo = nombreRiesgo;
	}


	public String getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


	public String getDescrCalificacion() {
		return descrCalificacion;
	}


	public void setDescrCalificacion(String descrCalificacion) {
		this.descrCalificacion = descrCalificacion;
	}


	public FormaConclusion getFormaConclusion() {
		return formaConclusion;
	}


	public void setFormaConclusion(FormaConclusion formaConclusion) {
		this.formaConclusion = formaConclusion;
	}


	public String getDescrFormaConclusion() {
		return descrFormaConclusion;
	}


	public void setDescrFormaConclusion(String descrFormaConclusion) {
		this.descrFormaConclusion = descrFormaConclusion;
	}


	public String getCodigoDistrito() {
		return codigoDistrito;
	}


	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}


	public String getNomDistrito() {
		return nomDistrito;
	}


	public void setNomDistrito(String nomDistrito) {
		this.nomDistrito = nomDistrito;
	}


	public String getCodigoProvincia() {
		return codigoProvincia;
	}


	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}


	public String getNomProvincia() {
		return nomProvincia;
	}


	public void setNomProvincia(String nomProvincia) {
		this.nomProvincia = nomProvincia;
	}


	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}


	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}


	public String getNomDepartamento() {
		return nomDepartamento;
	}


	public void setNomDepartamento(String nomDepartamento) {
		this.nomDepartamento = nomDepartamento;
	}


	public String getNomGrupoBanca() {
		return nomGrupoBanca;
	}


	public void setNomGrupoBanca(String nomGrupoBanca) {
		this.nomGrupoBanca = nomGrupoBanca;
	}


	public String getCodTerritorio() {
		return codTerritorio;
	}


	public void setCodTerritorio(String codTerritorio) {
		this.codTerritorio = codTerritorio;
	}


	public String getNomTerritorio() {
		return nomTerritorio;
	}


	public void setNomTerritorio(String nomTerritorio) {
		this.nomTerritorio = nomTerritorio;
	}


	public List<GrupoBanca> getLstGrupoBanca() {
		return lstGrupoBanca;
	}


	public void setLstGrupoBanca(List<GrupoBanca> lstGrupoBanca) {
		this.lstGrupoBanca = lstGrupoBanca;
	}


	public int getIdGrupoBanca() {
		return idGrupoBanca;
	}


	public void setIdGrupoBanca(int idGrupoBanca) {
		this.idGrupoBanca = idGrupoBanca;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public List<Organo> getLstOrgano() {
		return lstOrgano;
	}


	public void setLstOrgano(List<Organo> lstOrgano) {
		this.lstOrgano = lstOrgano;
	}


	public List<Ubigeo> getLstUbigeo() {
		return lstUbigeo;
	}


	public void setLstUbigeo(List<Ubigeo> lstUbigeo) {
		this.lstUbigeo = lstUbigeo;
	}


	public int getIdOrganos() {
		return idOrganos;
	}


	public void setIdOrganos(int idOrganos) {
		this.idOrganos = idOrganos;
	}


	public String getIdUbigeo() {
		return idUbigeo;
	}


	public void setIdUbigeo(String idUbigeo) {
		this.idUbigeo = idUbigeo;
	}


	public String getTipoFeriado() {
		return tipoFeriado;
	}


	public void setTipoFeriado(String tipoFeriado) {
		this.tipoFeriado = tipoFeriado;
	}


	public String getIndFeriado() {
		return indFeriado;
	}


	public void setIndFeriado(String indFeriado) {
		this.indFeriado = indFeriado;
	}


	public Oficina getOficina() {
		return oficina;
	}


	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}


	public List<Territorio> getLstTerritorio() {
		return lstTerritorio;
	}


	public void setLstTerritorio(List<Territorio> lstTerritorio) {
		this.lstTerritorio = lstTerritorio;
	}


	public String getCodigoOficina() {
		return codigoOficina;
	}


	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}


	public String getNomOficina() {
		return nomOficina;
	}


	public void setNomOficina(String nomOficina) {
		this.nomOficina = nomOficina;
	}


	public List<Via> getLstVias() {
		return lstVias;
	}


	public void setLstVias(List<Via> lstVias) {
		this.lstVias = lstVias;
	}


	public List<Actividad> getLstActividad() {
		return lstActividad;
	}


	public void setLstActividad(List<Actividad> lstActividad) {
		this.lstActividad = lstActividad;
	}


	public int getIdVia() {
		return idVia;
	}


	public void setIdVia(int idVia) {
		this.idVia = idVia;
	}


	public int getIdActividad() {
		return idActividad;
	}


	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}


	public int getNumDiasRojoEst1() {
		return numDiasRojoEst1;
	}


	public void setNumDiasRojoEst1(int numDiasRojoEst1) {
		this.numDiasRojoEst1 = numDiasRojoEst1;
	}


	public int getNumNaraEst1() {
		return numNaraEst1;
	}


	public void setNumNaraEst1(int numNaraEst1) {
		this.numNaraEst1 = numNaraEst1;
	}


	public int getNumAmaEst1() {
		return numAmaEst1;
	}


	public void setNumAmaEst1(int numAmaEst1) {
		this.numAmaEst1 = numAmaEst1;
	}


	public int getNumDiasRojoEst2() {
		return numDiasRojoEst2;
	}


	public void setNumDiasRojoEst2(int numDiasRojoEst2) {
		this.numDiasRojoEst2 = numDiasRojoEst2;
	}


	public int getNumNaraEst2() {
		return numNaraEst2;
	}


	public void setNumNaraEst2(int numNaraEst2) {
		this.numNaraEst2 = numNaraEst2;
	}


	public int getNumAmaEst2() {
		return numAmaEst2;
	}


	public void setNumAmaEst2(int numAmaEst2) {
		this.numAmaEst2 = numAmaEst2;
	}


	public int getNumDiasRojoEst3() {
		return numDiasRojoEst3;
	}


	public void setNumDiasRojoEst3(int numDiasRojoEst3) {
		this.numDiasRojoEst3 = numDiasRojoEst3;
	}


	public int getNumNaraEst3() {
		return numNaraEst3;
	}


	public void setNumNaraEst3(int numNaraEst3) {
		this.numNaraEst3 = numNaraEst3;
	}


	public int getNumAmaEst3() {
		return numAmaEst3;
	}


	public void setNumAmaEst3(int numAmaEst3) {
		this.numAmaEst3 = numAmaEst3;
	}


	public int getIdProcesoEstado() {
		return idProcesoEstado;
	}


	public void setIdProcesoEstado(int idProcesoEstado) {
		this.idProcesoEstado = idProcesoEstado;
	}
	
}
