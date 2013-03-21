package com.hildebrando.legal.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.RowEditEvent;

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
import com.hildebrando.legal.modelo.Expediente;
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
import com.hildebrando.legal.modelo.Rol;
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
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.view.ExpedienteDataModel;

public class MantenimientoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 344950332481955688L;

	public static Logger logger = Logger.getLogger(MantenimientoMB.class);

	private int idRol;
	private List<Rol> rols;
	private List<String> rolsString;
	private List<String> procesosString;
	private char[] estados;
	
	private String nroExpeOficial;
	private String nombreUsuario;
	private String apPatUsuario;
	private String apMatUsuario;
	private String correoUsuario;
	private String codigoUsuario;
	private List<Usuario> usuarios;
	
	private int idProceso;
	private String nombreProceso;
	private String abrevProceso;
	private List<Proceso> procesos;
	private List<Proceso> procesos2;

	private String nombreVia;
	private List<Via> vias;
	
	private String nombreInstancia;
	private List<Instancia> instancias;
	
	private String nombreActividad;
	private List<Actividad> actividads;
	
	private String nombreMoneda;
	private List<Moneda> monedas;
	
	private String abrevMoneda;
	private Long rucEstudio;
	private String nombreEstudio;
	private String direccionEstudio;
	private String telefEstudio;
	private String correoEstudio;
	private List<Estudio> estudios;
	
	private String nombreEstCaut;
	private List<EstadoCautelar> estadosCautelars;
	
	private String nombreEstExpe;
	private List<EstadoExpediente> estadoExpedientes;
	
	private String nombreEtapa;
	private List<Etapa> etapas;
	
	private String nombreEntidad;
	private List<Entidad> entidads;
	
	private String nombreFormConc;
	private List<FormaConclusion> formaConclusions;
	
	private String nombreRecurrencia;
	private List<Recurrencia> recurrencias;
		
	private String nombreSitActPro;
	private List<SituacionActProc> situacionActProcs;
	
	private String nombreSitCuota;
	private List<SituacionCuota> situacionCuotas;
	
	private String nombreSitHonor;
	private List<SituacionHonorario> situacionHonorarios;
	
	private String nombreSitIncul;
	private List<SituacionInculpado> situacionInculpados;
	
	private String nombreTipoCaut;
	private List<TipoCautelar> tipoCautelars;
	
	private String nombreTipoExpe;
	private List<TipoExpediente> tipoExpedientes;
	
	private String nombreTipoHonor;
	private List<TipoHonorario> tipoHonorarios;
	
	private String nombreTipoInv;
	private List<TipoInvolucrado> tipoInvolucrados;
	
	private String nombreTipoPro;
	private List<TipoProvision> tipoProvisions;
	
	private String nombreRolInvol;
	private List<RolInvolucrado> rolInvolucrados;
	
	private int idProceso2;
	private String nombreRol;
	private List<Rol> rols2;
	
	private String nombreMateria;
	private String nombreRiesgo;
	private String tipoDocumento;
	private String descrCalificacion;
	private FormaConclusion formaConclusion;
	private String descrFormaConclusion;
	private String codigoDistrito;
	private String nomDistrito;
	private String codigoProvincia;
	private String codigoDepartamento;
	private String nomDepartamento;
	private String nomGrupoBanca;
	private String codTerritorio;
	private String nomTerritorio;
	private String nomProvincia;
	private List<GrupoBanca> lstGrupoBanca;
	private int idGrupoBanca;
	private Date fechaInicio;
	private Date fechaFin;
	private List<Organo> lstOrgano;
	private List<Ubigeo> lstUbigeo;
	private int idOrganos;
	private boolean flagMostrarOrg;
	private boolean flagMostrarCal;
	private boolean flagDeshUbigeos;
	private Date fechaInLine;
	private String idUbigeo;
	private String nombreFeriado;
	private String nombreFeriadoOrg;
	private String tipoFeriado;
	private Character indFeriado;
	private Character indEscenario;
	private Oficina oficina;
	private List<Territorio> lstTerritorio;
	private String codigoOficina;
	private String nomOficina;
	private List<Via> lstVias;
	private List<Via> lstViasNuevo;
	private List<Actividad> lstActividad;
	private List<Aviso> lstAviso;
	private List<Materia> lstMateria;
	private List<Riesgo> lstRiesgo;
	private List<TipoDocumento> lstTipoDoc;
	private List<Calificacion> lstCalificacion;
	private List<Oficina> lstOficina;
	private String idUbigeoLst;
	private List<Feriado> lstFeriado;
	private int idVias;
	private int idViasLst;
	private int idActividad;
	private int idActividadLst;
	private int numDiasRojoEst1;
	private int numNaraEst1;
	private int numAmaEst1;
	private int idProcesoEstado;
	

	private int idEstadoSelected;
	
	private Aviso objAviso;
	private ConsultaService consultaService;
	private Usuario responsable;
	private Usuario nuevoResponsable;
	
	private ExpedienteDataModel expedientes;
	private Expediente[] selectedExpediente;
	private int tabActivado;
	
	public Expediente[] getSelectedExpediente() {
		return selectedExpediente;
	}

	public void setSelectedExpediente(Expediente[] selectedExpediente) {
		this.selectedExpediente = selectedExpediente;
	}

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
		Territorio newTerr = new Territorio();
		newTerr.setGrupoBanca(new GrupoBanca());
		setNumDiasRojoEst1(0);
		setNumNaraEst1(0);
		setNumAmaEst1(0);
		
		setTabActivado(0);
		setIndEscenario(new Character('C'));
		
		setFlagMostrarCal(false);
		setFlagMostrarOrg(true);
		setFlagDeshUbigeos(true);
		 
		expedientes = new ExpedienteDataModel(new ArrayList<Expediente>());
		responsable= new Usuario();
		
		
		setNombreActividad("");
		setActividads(new ArrayList<Actividad>());
		
		setDescrCalificacion("");
		setLstCalificacion(new ArrayList<Calificacion>());
		
		setNombreEntidad("");
		setEntidads(new ArrayList<Entidad>());
		
		setNombreEstCaut("");
		setEstadosCautelars(new ArrayList<EstadoCautelar>());
		
		setNombreEstExpe("");
		setEstadoExpedientes(new ArrayList<EstadoExpediente>());
		
		setRucEstudio(null);
		setNombreEstudio("");
		setDireccionEstudio("");
		setTelefEstudio("");
		setCorreoEstudio("");
		setEstudios(new ArrayList<Estudio>());
		
		setNombreEtapa("");
		setEtapas(new ArrayList<Etapa>());
		
		setIdOrganos(0);
		setFechaInLine(new Date());
		
		setIndFeriado('T');
		setNombreFeriado("");
		setIdUbigeo("");
		setFechaInicio(null);
		setFechaFin(null);
		setLstFeriado(new ArrayList<Feriado>());
		
		setNombreFormConc("");
		setFormaConclusions(new ArrayList<FormaConclusion>());
		
		setNomGrupoBanca("");
		setLstGrupoBanca(new ArrayList<GrupoBanca>());
		
		setNombreInstancia("");
		setInstancias(new ArrayList<Instancia>());
		
		setIdProceso(0);
		setIdVias(0);
		setIdActividad(0);
		setLstAviso(new ArrayList<Aviso>());
		
		setIdProcesoEstado(0);
		setIdViasLst(0);
		setIdActividadLst(0);
		
		setNombreMateria("");
		setLstMateria(new ArrayList<Materia>());
		
		setNombreMoneda("");
		setAbrevMoneda("");
		setMonedas(new ArrayList<Moneda>());
		
		setCodigoOficina("");
		setNomOficina("");
		setCodTerritorio("");
		setIdUbigeo("");
		setLstOficina(new ArrayList<Oficina>());
		
		setNombreProceso("");
		setAbrevProceso("");
		setProcesos2(new ArrayList<Proceso>());
		
		setNombreRecurrencia("");
		setRecurrencias(new ArrayList<Recurrencia>());
		
		setNombreRiesgo("");
		setLstRiesgo(new ArrayList<Riesgo>());
		
		setIdProceso2(0);
		setNombreRol("");
		setRols2(new ArrayList<Rol>());
		
		setNombreRolInvol("");
		setRolInvolucrados(new ArrayList<RolInvolucrado>());
		
		setNombreSitActPro("");
		setSituacionActProcs(new ArrayList<SituacionActProc>());
		
		setNombreSitCuota("");
		setSituacionCuotas(new ArrayList<SituacionCuota>());
		
		setNombreSitHonor("");
		setSituacionHonorarios(new ArrayList<SituacionHonorario>());
		
		setNombreSitIncul("");
		setSituacionInculpados(new ArrayList<SituacionInculpado>());
		
		setCodTerritorio("");
		setNomTerritorio("");
		setIdGrupoBanca(0);
		setLstTerritorio(new ArrayList<Territorio>());
		
		setNombreTipoCaut("");
		setTipoCautelars(new ArrayList<TipoCautelar>());
		
		setTipoDocumento("");
		setLstTipoDoc(new ArrayList<TipoDocumento>());
		
		setNombreTipoExpe("");
		setTipoExpedientes(new ArrayList<TipoExpediente>());
		
		setNombreTipoHonor("");
		setTipoHonorarios(new ArrayList<TipoHonorario>());
		
		setNombreTipoInv("");
		setTipoInvolucrados(new ArrayList<TipoInvolucrado>());
		
		setNombreTipoPro("");
		setTipoProvisions(new ArrayList<TipoProvision>());
		
		setCodigoDepartamento("");
		setNomDepartamento("");
		setCodigoProvincia("");
		setNomProvincia("");
		setCodigoDistrito("");
		setNomDistrito("");
		setLstUbigeo(new ArrayList<Ubigeo>());
		
		setIdRol(0);
		setNombreUsuario("");
		setApPatUsuario("");
		setApMatUsuario("");
		setCorreoUsuario("");
		setCodigoUsuario("");
		setUsuarios(new ArrayList<Usuario>());
		
		setVias(new ArrayList<Via>());
		
		setIdEstadoSelected(0);
		setIdProceso(0);
		setIdVias(0);
		setOficina(new Oficina());
		setNroExpeOficial("");
		setResponsable(new Usuario());
		setNuevoResponsable(new Usuario());
		
		setRucEstudio(null);
		
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
		setCodTerritorio("Seleccione");
		setIdUbigeo("Seleccione");
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

		
		if(getIndEscenario().compareTo('C')==0){

			setIndFeriado('T');
			setNombreFeriado("");
			setIdUbigeo("");
			setFechaInicio(null);
			setFechaFin(null);
			//setLstFeriado(new ArrayList<Feriado>());
			
			setFlagMostrarCal(false);
			setFlagMostrarOrg(true);
			setTabActivado(0);
			
		}else{
		
			setIdOrganos(0);
			setFechaInLine(null);
			//setLstFeriado(new ArrayList<Feriado>());
			setNombreFeriadoOrg("");
			
			setFlagMostrarCal(true);
			setFlagMostrarOrg(false);
			setTabActivado(1);
		}
		
		
		
	}

	public void limpiarEstados(ActionEvent e) {
		setNumDiasRojoEst1(0);
		setNumNaraEst1(0);
		setNumAmaEst1(0);
		setIdProcesoEstado(0);
		setIdActividadLst(0);
		setIdViasLst(0);
	}
	
	public void limpiarFiltrosEstados(ActionEvent e) {
		setIdProceso(0);
		setIdActividad(0);
		setIdVias(0);
	}
	
	public List<Oficina> completeOficina(String query) {

		List<Oficina> results = new ArrayList<Oficina>();
		List<Oficina> oficinas = consultaService.getOficinas();

		for (Oficina oficina : oficinas) {

			if (oficina.getUbigeo() != null) {

				String texto = oficina.getCodigo() + " "
						+ oficina.getNombre().toUpperCase() + " ("
						+ oficina.getUbigeo().getDepartamento().toUpperCase()
						+ ")";

				if (texto.contains(query.toUpperCase())) {
					oficina.setNombreDetallado(texto);
					results.add(oficina);
				}

			}

		}

		return results;
	}
	
	public List<Usuario> completeResponsable(String query) {
		List<Usuario> results = new ArrayList<Usuario>();

		List<Usuario> usuarios = consultaService.getUsuarios();

		for (Usuario usuario : usuarios) {

			if (usuario.getNombres().toUpperCase()
					.contains(query.toUpperCase())
					|| usuario.getApellidoPaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getApellidoMaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getCodigo().toUpperCase()
							.contains(query.toUpperCase())) {

				usuario.setNombreDescripcion(usuario.getCodigo() + " - "
						+ usuario.getNombres() + " "
						+ usuario.getApellidoPaterno() + " "
						+ usuario.getApellidoMaterno());

				results.add(usuario);
			}

		}

		return results;
	}
	
	@SuppressWarnings("unchecked")
	public void buscarExpedientes(ActionEvent e)
	{	
		logger.debug("Buscando expedientes...");
		
		List<Expediente> expedientesTMP = new ArrayList<Expediente>();
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Expediente.class);
		filtro.add(Restrictions.isNull("expediente.idExpediente")).addOrder(Order.desc("idExpediente"));
		
		if (getNroExpeOficial().compareTo("")!=0){
				logger.debug("filtro "+ getNroExpeOficial()  +" expedientes - numero expediente");
				filtro.add(Restrictions.like("numeroExpediente", "%" + getNroExpeOficial().trim() + "%").ignoreCase());
		}

		if(getIdProceso()!=0){
			
			logger.debug("filtro " + getIdProceso() + "expedientes - proceso");
			filtro.add(Restrictions.eq("proceso.idProceso", getIdProceso()));
		}
		
		if(getIdVias()!=0){
			
			logger.debug("filtro "+ getIdVias() +" expedientes - via");				
			filtro.add(Restrictions.eq("via.idVia", getIdVias()));
		}
		
		if(getIdEstadoSelected()!=0){
			
			logger.debug("filtro "+ getIdEstadoSelected()  +" expedientes - estado");	
			filtro.add(Restrictions.eq("estadoExpediente.idEstadoExpediente", getIdEstadoSelected()));
		}
		
		if (getOficina()!=null)
		{
			logger.debug("filtro "+ getOficina().getIdOficina()   +" expedientes - oficina");
			filtro.add(Restrictions.eq("oficina", getOficina()));
		}
		
		if (getResponsable()!=null)
		{
			logger.debug("filtro "+ getResponsable().getIdUsuario()   +" expedientes - usuario responsable");
			filtro.add(Restrictions.eq("usuario", getResponsable()));
		}
		
		try {
			
			expedientesTMP = expedienteDAO.buscarDinamico(filtro);
			
			logger.debug("total de expedientes encontrados: "+ expedientesTMP.size());
			
		} catch (Exception e1) {
			
			//e1.printStackTrace();
			logger.debug("error al buscar expedientes: "+ e1.toString());
			
		}

		expedientes = new ExpedienteDataModel(expedientesTMP);	
	}
	
	public void cambioProceso() 
	{
		if (getIdProceso() != 0) 
		{
			lstVias = consultaService.getViasByProceso(getIdProceso());
		} else {
			lstVias = new ArrayList<Via>();
		}

	}
	
	public void cambioProcesoNuevo() 
	{
		if (getIdProcesoEstado() != 0) 
		{
			lstViasNuevo = consultaService.getViasByProceso(getIdProcesoEstado());
		} else {
			lstViasNuevo = new ArrayList<Via>();
		}

	}
	
	private void cargarCombos() 
	{
		// Carga Estado Expediente
		GenericDao<EstadoExpediente, Object> estDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstPro = Busqueda.forClass(EstadoExpediente.class);
		filtroEstPro.add(Restrictions.eq("estado", 'A'));

		try {
			estadoExpedientes = estDAO.buscarDinamico(filtroEstPro);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"estadoExpedientes:"+e);
		}
		
		
		// Carga Proceso
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		filtroProceso.add(Restrictions.eq("estado", 'A'));

		try {
			procesosString = new ArrayList<String>();
			procesos = procesoDAO.buscarDinamico(filtroProceso);
			for(Proceso p:procesos)
				procesosString.add(p.getNombre());
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"procesos:"+e);
		}

		// Carga Grupo Banca
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca = Busqueda.forClass(GrupoBanca.class);
		filtroGrupoBanca.add(Restrictions.eq("estado", 'A'));

		try {
			lstGrupoBanca = grupoBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstGrupoBanca:"+e);
		}

		// Carga Organos
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOrgano = Busqueda.forClass(Organo.class);
		
		try {
			lstOrgano = organoDAO.buscarDinamico(filtroOrgano);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstOrgano:"+e);
		}

		// Carga Ubigeos
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbigeo = Busqueda.forClass(Ubigeo.class);
		filtroUbigeo.add(Restrictions.eq("estado", 'A'));
		filtroUbigeo.setMaxResults(SglConstantes.CANTIDAD_UBIGEOS);
		filtroUbigeo.addOrder(Order.asc("codDist"));
		
		try {
			lstUbigeo = ubiDAO.buscarDinamico(filtroUbigeo);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstUbigeo:"+e);
		}

		// Carga Territorio
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerr = Busqueda.forClass(Territorio.class);
		filtroTerr.add(Restrictions.eq("estado", 'A'));

		try {
			lstTerritorio = terrDAO.buscarDinamico(filtroTerr);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstTerritorio:"+e);
		}

		// Carga Vias
		GenericDao<Via, Object> viasDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class);
		filtroVia.add(Restrictions.eq("estado", 'A'));

		try {
			lstVias = viasDAO.buscarDinamico(filtroVia);
			lstViasNuevo = viasDAO.buscarDinamico(filtroVia);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstVias:"+e);
		}

		// Carga Actividades
		GenericDao<Actividad, Object> actDAO = (GenericDao<Actividad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAct = Busqueda.forClass(Actividad.class);
		filtroAct.add(Restrictions.eq("estado", 'A'));

		try {
			lstActividad = actDAO.buscarDinamico(filtroAct);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstActividad:"+e);
		}
		//Carga Aviso
		GenericDao<Aviso, Object> avisDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAv = Busqueda.forClass(Aviso.class);
		filtroAv.add(Restrictions.eq("estado", 'A'));
		
		try {
			lstAviso =  avisDAO.buscarDinamico(filtroAv);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstAviso:"+e);
		}
		
		//Carga Materia
		GenericDao<Materia, Object> matDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroMat = Busqueda.forClass(Materia.class);
		filtroMat.add(Restrictions.eq("estado", 'A'));
		filtroMat.addOrder(Order.asc("idMateria"));
				
		try {
			lstMateria =  matDAO.buscarDinamico(filtroMat);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstMateria:"+e);
		}
		
		//Carga Riesgos
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRiesgo = Busqueda.forClass(Riesgo.class);
		filtroRiesgo.add(Restrictions.eq("estado", 'A'));
		filtroRiesgo.addOrder(Order.asc("idRiesgo"));
		
		try {
			lstRiesgo =  riesgoDAO.buscarDinamico(filtroRiesgo);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstRiesgo:"+e);
		}
		
		//Carga Tipos de Documento
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoDoc = Busqueda.forClass(TipoDocumento.class);
		filtroTipoDoc.add(Restrictions.eq("estado", 'A'));
		filtroTipoDoc.addOrder(Order.asc("idTipoDocumento"));
		
		try {
			lstTipoDoc =  tipoDocDAO.buscarDinamico(filtroTipoDoc);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstTipoDoc:"+e);
		}
		
		//Carga Calificacion
		GenericDao<Calificacion, Object> califDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroCalif = Busqueda.forClass(Calificacion.class);
		filtroCalif.add(Restrictions.eq("estado", 'A'));
		filtroCalif.addOrder(Order.asc("idCalificacion"));
		
		try {
			lstCalificacion =  califDAO.buscarDinamico(filtroCalif);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstCalificacion:"+e);
		}
		
		//Carga Oficinas
		GenericDao<Oficina, Object> oficDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOfc= Busqueda.forClass(Oficina.class);
		filtroOfc.add(Restrictions.eq("estado", 'A'));
		filtroOfc.addOrder(Order.asc("idOficina"));
		
		try {
			lstOficina =  oficDAO.buscarDinamico(filtroOfc);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstOficina:"+e);
		}
		
		//Carga Feriados
		//GenericDao<Feriado, Object> ferDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		//Busqueda filtroFer= Busqueda.forClass(Feriado.class);
		//filtroFer.addOrder(Order.asc("idFeriado"));
		
		//try {
		//	lstFeriado=  ferDAO.buscarDinamico(filtroFer);
		//} catch (Exception e) {
		//	logger.debug("Error al cargar el listado de feriados");
		//}
		
		//Carga Rols
		GenericDao<Rol, Object> rolDAO = (GenericDao<Rol, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRol= Busqueda.forClass(Rol.class);
		filtroRol.add(Restrictions.eq("estado", 'A'));
		
		try {
			rols=  rolDAO.buscarDinamico(filtroRol.addOrder(Order.asc("descripcion")));
			rolsString = new ArrayList<String>();
			for (Rol r : rols)
				rolsString.add(r.getDescripcion());
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"rols:"+e);
		}
		
		
		estados=  new char[2];
		estados[0] = 'A';
		estados[1] = 'I';
		
		
		
		setIndFeriado('T');
	}

	public void buscarMateria(ActionEvent e)
	{
		logger.debug("[BUSQ_MATERIA]-NombreMateria:"+getNombreMateria());

		GenericDao<Materia, Object> matDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroMat = Busqueda.forClass(Materia.class);
		String filtroNuevo="%" + getNombreMateria().concat("%");
		filtroMat.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstMateria =  matDAO.buscarDinamico(filtroMat);
			if(lstMateria!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"materias encontradas es:["+lstMateria.size()+"].");
			}
		} catch (Exception ex) {
			logger.debug(SglConstantes.MSJ_ERROR_CONSULTAR+"materias: "+ex);
		}
	}
	
	public void editarMateria(RowEditEvent event)
	{
		Materia mat = ((Materia) event.getObject());
		logger.debug("modificando materia " + mat.getDescripcion());
		
		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			materiaDAO.modificar(mat);
			logger.debug("actualizo la materia exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la materia:"+e);
		}
	}
	
	public void editFormaConclusion(RowEditEvent event)
	{
		FormaConclusion fc = ((FormaConclusion) event.getObject());
		logger.debug("modificando FormaConclusion " + fc.getDescripcion());
		
		GenericDao<FormaConclusion, Object> fcDAO = (GenericDao<FormaConclusion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			fcDAO.modificar(fc);
			logger.debug("actualizo la forma de conclusion exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la formaConclusion:"+e);
		}
	}

	public Organo buscarOrgano(int idOrgano) {
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOrgano = Busqueda.forClass(Organo.class);
		filtroOrgano.add(Restrictions.eq("idOrgano", idOrgano));
		Organo tmpOrg = new Organo();

		try {
			lstOrgano = organoDAO.buscarDinamico(filtroOrgano);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"lstOrgano:"+e);
		}

		for (Organo org : lstOrgano) {
			if (lstOrgano.size() == 1) {
				tmpOrg.setIdOrgano(org.getIdOrgano());
				tmpOrg.setNombre(org.getNombre());
			}
		}

		return tmpOrg;
	}

	public void buscarViasPorProceso(ValueChangeEvent e) {
		logger.debug("Buscando vias por proceso: " + e.getNewValue());
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class).createAlias("proceso", "pro");
		filtroVia.add(Restrictions.eq("pro.idProceso", e.getNewValue()));
		
		try {
			lstVias = viaDAO.buscarDinamico(filtroVia);
		} catch (Exception exp) {
			logger.debug("No se pudo encontrar las vias del proceso seleccionado");
		}

	}
	
	public void buscarViasPorProcesoNuevo(ValueChangeEvent e) {
		logger.debug("Buscando vias por proceso: " + e.getNewValue());
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class).createAlias("proceso", "pro");
		filtroVia.add(Restrictions.eq("pro.idProceso", e.getNewValue()));
		
		try {
			lstViasNuevo = viaDAO.buscarDinamico(filtroVia);
		} catch (Exception exp) {
			logger.debug("No se pudo encontrar las vias del proceso seleccionado");
		}

	}


	public Ubigeo buscarUbigeo(String ubigeo) {
		logger.debug("=== buscarUbigeo() ====");
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbigeo = Busqueda.forClass(Ubigeo.class);
		filtroUbigeo.add(Restrictions.eq("codDist", ubigeo));
		Ubigeo tmpUbi = new Ubigeo();

		try {
			lstUbigeo = ubiDAO.buscarDinamico(filtroUbigeo);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al buscar ubigeos");
		}

		for (Ubigeo tmpUbigeo : lstUbigeo) {
			if (lstUbigeo.size() == 1) {
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

	public GrupoBanca buscarGrupoBanca(int idGrupoBanca) {
		GenericDao<GrupoBanca, Object> gBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca = Busqueda.forClass(GrupoBanca.class);
		filtroGrupoBanca.add(Restrictions.eq("idGrupoBanca", idGrupoBanca));
		GrupoBanca tmpGBanca = new GrupoBanca();

		try {
			lstGrupoBanca = gBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al buscar registros de grupo banca");
		}

		for (GrupoBanca tmpGrupoBanca : lstGrupoBanca) {
			if (lstGrupoBanca.size() == 1) {
				tmpGBanca.setIdGrupoBanca(tmpGrupoBanca.getIdGrupoBanca());
				tmpGBanca.setDescripcion(tmpGrupoBanca.getDescripcion());
			}
		}

		return tmpGBanca;
	}

	public Territorio buscarTerritorio(String codTerr) {
		GenericDao<Territorio, Object> ubiDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerritorio = Busqueda.forClass(Territorio.class);
		filtroTerritorio.add(Restrictions.eq("codigo", codTerr));
		Territorio tmpTerr = new Territorio();

		try {
			lstTerritorio = ubiDAO.buscarDinamico(filtroTerritorio);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al buscar territorios");
		}

		for (Territorio terr : lstTerritorio) {
			if (lstTerritorio.size() == 1) {
				tmpTerr.setCodigo(terr.getCodigo());
				tmpTerr.setDescripcion(terr.getDescripcion());
			}
		}

		return tmpTerr;
	}

	public Proceso buscarProceso(int codProceso) {
		GenericDao<Proceso, Object> ubiDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Proceso tmpProc = new Proceso();

		try {
			tmpProc = ubiDAO.buscarById(Proceso.class, codProceso);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al buscar proceso");
		}

		return tmpProc;
	}

	public Via buscarVia(int codVia) {
		GenericDao<Via, Object> ubiDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Via tmpVi = new Via();

		try {
			tmpVi = ubiDAO.buscarById(Via.class, codVia);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al buscar vias");
		}
		
		return tmpVi;
	}

	public Actividad buscarActividad(int codAct) {
		GenericDao<Actividad, Object> actDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Actividad tmpAct = new Actividad();

		try {
			tmpAct = actDAO.buscarById(Actividad.class, codAct);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al buscar actividades");
		}
		
		
		return tmpAct;
	}

	public void agregarMateria(ActionEvent e) 
	{
		List<Materia> materias = new ArrayList<Materia>();
		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Materia.class);
		Busqueda filtro2 = Busqueda.forClass(Materia.class);

		
		if ( getNombreMateria().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreMateria()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				materias = materiaDAO.buscarDinamico(filtro);

				if (materias.size() == 0) 
				{
					Materia mat = new Materia();
					mat.setDescripcion(getNombreMateria());
					mat.setEstado('A');
					
					try {
						materiaDAO.insertar(mat);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego la materia"));
						logger.debug("guardo la materia exitosamente");
						
						lstMateria = materiaDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego la materia"));
						logger.debug("no guardo la materia por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Materia Existente", "Materia Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si materia existe en BD");
			}
		}
	}
	
	
	public void buscarRiesgo(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getNombreRiesgo());
		
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRiesgo = Busqueda.forClass(Riesgo.class);
		String filtroNuevo="%" + getNombreRiesgo().concat("%");
		filtroRiesgo.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstRiesgo =  riesgoDAO.buscarDinamico(filtroRiesgo);
		} catch (Exception ex) {
			logger.debug("Error al buscar los riesgos");
		}
	}
	
	public void editarRiesgo(RowEditEvent event)
	{
		logger.debug("== editarRiesgo() ==");
		Riesgo riesgo = ((Riesgo) event.getObject());
		logger.debug("[EDIT_RIESG]-RiesgoDescripcion:" + riesgo.getDescripcion());
		
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			riesgoDAO.modificar(riesgo);
			logger.debug(SglConstantes.MSJ_EXITO_ACTUALIZ+"el riesgo.");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el riesgo:"+e);
		}
	}
	
	public void agregarRiesgo(ActionEvent e) 
	{
		List<Riesgo> riesgos = new ArrayList<Riesgo>();
		GenericDao<Riesgo, Object> riesgosDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Riesgo.class);
		Busqueda filtro2 = Busqueda.forClass(Riesgo.class);

		
		if ( getNombreRiesgo().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreRiesgo()).ignoreCase());
				
				riesgos = riesgosDAO.buscarDinamico(filtro);

				if (riesgos.size() == 0) 
				{
					Riesgo riesg = new Riesgo();
					riesg.setDescripcion(getNombreRiesgo());
					riesg.setEstado('A');
					
					try {
						riesgosDAO.insertar(riesg);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego el riesgo"));
						logger.debug("guardo el riesgo exitosamente");
						
						lstRiesgo = riesgosDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego el riesgo"));
						logger.debug("no guardo el riesgo por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Riesgo Existente", "Riesgo Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si riesgo existe en BD");
			}
		}
	}

	public void agregarTipoDocumento(ActionEvent e) {
		List<TipoDocumento> tipoDocs = new ArrayList<TipoDocumento>();
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(TipoDocumento.class);
		Busqueda filtro2 = Busqueda.forClass(TipoDocumento.class);

		
		if ( getTipoDocumento().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Descripcion", "Datos Requeridos: Descripcion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getTipoDocumento()).ignoreCase());
				
				tipoDocs = tipoDocDAO.buscarDinamico(filtro);

				if (tipoDocs.size() == 0) 
				{
					TipoDocumento tipoDoc = new TipoDocumento();
					tipoDoc.setDescripcion(getTipoDocumento());
					tipoDoc.setEstado('A');
					
					try {
						tipoDocDAO.insertar(tipoDoc);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego el tipo de documento"));
						logger.debug("guardo el tipo de documento exitosamente");
						
						lstTipoDoc = tipoDocDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego el tipo de documento"));
						logger.debug("no guardo el tipo de documento por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Tipo de Documento Existente", "Tipo de Documento Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si tipo de documento existe en BD");
			}
		}
	}
	
	public void buscarTipoDoc(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getTipoDocumento());
		
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoDoc = Busqueda.forClass(TipoDocumento.class);
		String filtroNuevo="%" + getTipoDocumento().concat("%");
		filtroTipoDoc.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstTipoDoc =  tipoDocDAO.buscarDinamico(filtroTipoDoc);
		} catch (Exception ex) {
			logger.debug("Error al buscar el tipo de documento");
		}
	}
	
	public void editarTipoDoc(RowEditEvent event)
	{
		TipoDocumento tipoDoc = ((TipoDocumento) event.getObject());
		logger.debug("modificando tipo de documento " + tipoDoc.getDescripcion());
		
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoDocDAO.modificar(tipoDoc);
			logger.debug("actualizo el tipo de documento exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"tipoDocumento:"+e);
		}
	}

	public void limpiarTipoDocumento(ActionEvent e) {
		setTipoDocumento("");

	}

	public void agregarCalificacion(ActionEvent e) 
	{
		List<Calificacion> califs = new ArrayList<Calificacion>();
		GenericDao<Calificacion, Object> califDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Calificacion.class);
		Busqueda filtro2 = Busqueda.forClass(Calificacion.class);

		
		if ( getDescrCalificacion().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre Calificacion", "Datos Requeridos: Nombre Calificacion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getDescrCalificacion()).ignoreCase());
				
				califs = califDAO.buscarDinamico(filtro);

				if (califs.size() == 0) 
				{
					Calificacion calif = new Calificacion();
					calif.setNombre(getDescrCalificacion());
					calif.setEstado('A');
					
					try {
						califDAO.insertar(calif);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego la calificacion"));
						logger.debug("guardo la calificacion exitosamente");
						
						lstCalificacion = califDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego la calificacion"));
						logger.debug("no guardo la calificacion por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Calificacion Existente", "Calificacion Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si calificacion existe en BD");
			}
		}
	}
	
	public void buscarCalificacion(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getDescrCalificacion());
		
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroCalif = Busqueda.forClass(Calificacion.class);
		String filtroNuevo="%" + getDescrCalificacion().concat("%");
		filtroCalif.add(Restrictions.sqlRestriction("lower({alias}.nombre) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstCalificacion =  calificacionDAO.buscarDinamico(filtroCalif);
		} catch (Exception ex) {
			logger.debug("Error al buscar calificacion");
		}
	}
	
	public void editarCalificacion(RowEditEvent event)
	{
		Calificacion calif = ((Calificacion) event.getObject());
		logger.debug("modificando calificacion: " + calif.getNombre());
		
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			calificacionDAO.modificar(calif);
			logger.debug("actualizo calificacion exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"calificacion:"+e);
		}
	}

	public void limpiarCalificacion(ActionEvent e) {

		setDescrCalificacion("");

	}
	
	public void buscarFormaConclusion(ActionEvent e) {

		logger.debug("entro al buscar forma conclusion");

		GenericDao<FormaConclusion, Object> formaConclusionDAO = 
				(GenericDao<FormaConclusion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(FormaConclusion.class);

		if (getNombreFormConc().compareTo("") != 0) {

			logger.debug("filtro " + getNombreFormConc() + " forma - nombre");
			filtro.add(Restrictions.like("descripcion","%" + getNombreFormConc().toUpperCase() + "%").ignoreCase());
		}

		try {
			formaConclusions = formaConclusionDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar formas");
		}

		logger.debug("trajo .." + formaConclusions.size());

	}

	public void agregarFormaConclusion(ActionEvent e) {
		GenericDao<FormaConclusion, Object> formaDAO = (GenericDao<FormaConclusion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		/*FormaConclusion fc = new FormaConclusion();
		fc.setDescripcion(getNombreFormConc().toUpperCase());
		fc.setEstado('A');

		try {
			formaDAO.insertar(fc);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
							"Agrego la forma de conclusion"));
			
			logger.debug("guardo la forma de conclusion exitosamente");

		} catch (Exception ex) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
							"No Agrego la forma de conclusion"));
			logger.debug("no guardo la forma de conclusion por "
					+ ex.getMessage());
		}*/
		
		List<FormaConclusion> formas = new ArrayList<FormaConclusion>();
		GenericDao<FormaConclusion, Object> formConDAO = (GenericDao<FormaConclusion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(FormaConclusion.class);
		Busqueda filtro2 = Busqueda.forClass(FormaConclusion.class);

		
		if ( getNombreFormConc().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Ingrese nombre de forma de conclusi�n", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreFormConc()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				formas = formConDAO.buscarDinamico(filtro);

				if (formas.size() == 0) 
				{
					FormaConclusion fc = new FormaConclusion();
					fc.setDescripcion(getNombreFormConc());
					fc.setEstado('A');
					
					try {
						formConDAO.insertar(fc);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso: Agreg� la forma de conclusi�n", ""));
						logger.debug("guardo la forma de conclusi�n exitosamente");
						
						formaConclusions = formConDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego la forma de conclusi�n"));
						logger.debug("no guardo la forma de conclusi�n por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Forma de conclusi�n Existente", ""));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si forma de conclusi�n existe en BD");
			}
		}
	}

	public void limpiarFormaConclusion(ActionEvent e) {
		setNombreFormConc("");
	}

	public void agregarGrupoBanca(ActionEvent e) {
		List<GrupoBanca> gb = new ArrayList<GrupoBanca>();
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(GrupoBanca.class);
		Busqueda filtro2 = Busqueda.forClass(GrupoBanca.class);

		
		if ( getNomGrupoBanca().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Descripcion", "Datos Requeridos: Descripcion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNomGrupoBanca()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				gb = grupoBancaDAO.buscarDinamico(filtro);

				if (gb.size() == 0) 
				{
					GrupoBanca gBanca = new GrupoBanca();
					gBanca.setDescripcion(getNomGrupoBanca());
					gBanca.setEstado('A');
					
					try {
						grupoBancaDAO.insertar(gBanca);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego grupo banca"));
						logger.debug("guardo grupo banca exitosamente");
						
						lstGrupoBanca = grupoBancaDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego grupo banca"));
						logger.debug("no guardo grupo banca por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Grupo Banca Existente", "Grupo Banca Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si grupo banca existe en BD");
			}
		}
	}
	
	public void buscarGrupoBanca(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getNomGrupoBanca());
		
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca= Busqueda.forClass(GrupoBanca.class);
		String filtroNuevo="%" + getNomGrupoBanca().concat("%");
		filtroGrupoBanca.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstGrupoBanca =  grupoBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception ex) {
			logger.debug("Error al buscar grupo banca");
		}
	}
	
	public void editarGrupoBanca(RowEditEvent event)
	{
		GrupoBanca gb = ((GrupoBanca) event.getObject());
		logger.debug("modificando grupo banca: " + gb.getDescripcion());
		
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			grupoBancaDAO.modificar(gb);
			logger.debug("actualizo grupo banca exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"grupoBanca:"+e);
		}
	}

	public void agregarTerritorio(ActionEvent e) {
		logger.debug("=== agregarTerritorio() === ");
		List<Territorio> terr = new ArrayList<Territorio>();
		GenericDao<Territorio, Object> terraDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Territorio.class);
		Busqueda filtro2 = Busqueda.forClass(Territorio.class);

		
		if ( getNomTerritorio().compareTo("") == 0 || getCodTerritorio().compareTo("")==0 || getIdGrupoBanca()==0) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: C�digo Territorio, Descripci�n, Grupo Banca", "Datos Requeridos: C�digo Territorio, Descripci�n, Grupo Banca");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNomTerritorio()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				terr = terraDAO.buscarDinamico(filtro);
				
				if(terr!=null){
					logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"territorios es:["+terr.size()+"]");
				}
				
				if (terr.size() == 0) 
				{
					Territorio terri = new Territorio();
					terri.setCodigo(getCodTerritorio());
					terri.setDescripcion(getNomTerritorio());
					terri.setGrupoBanca(buscarGrupoBanca(getIdGrupoBanca()));
					terri.setEstado('A');
					
					if(logger.isDebugEnabled()){
						logger.debug("getCodTerritorio(): "+getCodTerritorio());
						logger.debug("getNomTerritorio(): "+getNomTerritorio());
						logger.debug("getIdGrupoBanca(): "+getIdGrupoBanca());
					}
					
					try {
						terraDAO.insertar(terri);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Se agreg� el territorio exitosamente."));
						logger.debug(SglConstantes.MSJ_EXITO_REGISTRO+"el territorio.");
						
						lstTerritorio = terraDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);
						
						if(lstTerritorio!=null){
							logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"territorios nuevos es:["+lstTerritorio.size()+"]");
						}
						
					} catch (Exception ex) {
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No se pudo agregar el territorio"));
						logger.error(SglConstantes.MSJ_ERROR_REGISTR+"el territorio: "+ex);
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Territorio Existente", "Territorio Existente"));
				}

			} catch (Exception ex) {
				logger.error("Error al buscar si territorio existe en BD: "+ex);
			}
		}
		
	}
	
	public void busquedaTerritorio(ActionEvent e)
	{
		logger.debug("=== busquedaTerritorio() ===");
		
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerr= Busqueda.forClass(Territorio.class);
		
		if (getNomTerritorio().compareTo("")!=0)
		{
			logger.debug("[BUSQ_TERR]-NombreTerritorio: " + getNomTerritorio());
			String filtroNuevo="%" + getNomTerritorio().concat("%");
			filtroTerr.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodTerritorio().compareTo("")!=0)
		{
			logger.debug("[BUSQ_TERR]-IdTerritorio: " + getCodTerritorio());
			filtroTerr.add(Restrictions.eq("codigo", getCodTerritorio()));
		}
		
		if (getIdGrupoBanca()!=0)
		{
			filtroTerr.createAlias("grupoBanca", "gb");
			filtroTerr.add(Restrictions.eq("gb.idGrupoBanca", getIdGrupoBanca()));
			logger.debug("[BUSQ_TERR]-Grupo_Banca:" + getIdGrupoBanca());	
		}
		
		try {
			lstTerritorio =  terrDAO.buscarDinamico(filtroTerr);
		
			if(lstTerritorio!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"territorios encontrados es:["+lstTerritorio.size()+"].");
			}
			
		} catch (Exception ex) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"territorios: "+ex);
		}
		
	}
	
	
	
	public void editarTerritorio(RowEditEvent event)
	{
		Territorio terr = ((Territorio) event.getObject());
		logger.debug("modificando territorio: " + terr.getDescripcion());
		
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			terrDAO.modificar(terr);
			logger.debug("actualizo territorio exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el territorio:"+e);
		}
	}

	public void agregarFeriado(ActionEvent e) 
	{
		List<Feriado> fer = new ArrayList<Feriado>();
		
		GenericDao<Feriado, Object> ferDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Feriado.class);
		Busqueda filtro2 = Busqueda.forClass(Feriado.class);
		filtro2.addOrder(Order.desc("idFeriado"));
		
		logger.debug("Parametros a grabar:");
		logger.debug("Fecha Inicio: " + getFechaInicio());
		logger.debug("Fecha Fin: " + getFechaFin());
		logger.debug("Ubigeo: " + getIdUbigeo());
		
		
		if(getIndEscenario().compareTo('C')==0){
			
			if (  getIndFeriado().compareTo('T')==0  ) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Requerido", "Escoger Tipo Nacional o Local");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			
			}else{
				
				if (  getNombreFeriado().compareTo("")==0 || getFechaInicio()==null|| getFechaFin()==null ) {
					
					if (getNombreFeriado().compareTo("")==0 && getFechaInicio()==null && getFechaFin()==null )
					{
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos", "Ingrese Nombre, Fecha Inicio y Fecha Fin");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
					else if (getFechaInicio()==null || getFechaFin()==null)
					{
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos", "Ingrese Fecha Inicio y/o Fecha Fin");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				
				}else{
					
					try {
						
						filtro.add(Restrictions.eq("tipo", getIndEscenario()));
						filtro.add(Restrictions.eq("indicador", getIndFeriado()));
						filtro.add(Restrictions.eq("nombre", getNombreFeriado().toUpperCase()));
						filtro.add(Restrictions.between("fecha", getFechaInicio(), getFechaFin()));
						
						if(getIndFeriado().compareTo('L')==0){

							filtro.add(Restrictions.eq("ubigeo.codDist", getIdUbigeo()));
							
						}
						
						fer = ferDAO.buscarDinamico(filtro);

						if (fer.size() == 0)
						{
							Calendar fechaInicioTemp = new GregorianCalendar();
							fechaInicioTemp.setTimeInMillis(getFechaInicio().getTime());
							
							Calendar fechaFinTemp = new GregorianCalendar();
							fechaFinTemp.setTimeInMillis(getFechaFin().getTime());
							
							logger.debug("Datos de feriado a grabar:");
							logger.debug("Fecha Inicio: " + fechaInicioTemp);
							logger.debug("Fecha Fin: " + fechaFinTemp);
							logger.debug("Indicador: " + getIndFeriado());
							logger.debug("Nombre Feriado: " + getNombreFeriado());
							
							 while (fechaInicioTemp.before(fechaFinTemp) || fechaInicioTemp.equals(fechaFinTemp)) {
								 
								 	Feriado tmpFer = new Feriado();
									tmpFer.setFecha(fechaInicioTemp.getTime());
									tmpFer.setNombre(getNombreFeriado().toUpperCase());
									
									if(getIndFeriado().compareTo('L')==0){
										
										tmpFer.setUbigeo(buscarUbigeo(getIdUbigeo()));
									}
									
									tmpFer.setEstado('A');
									tmpFer.setTipo('C');
									tmpFer.setIndicador(getIndFeriado());
									
									try {
										ferDAO.insertar(tmpFer);
										FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Se ha registrado el feriado."));
										logger.debug(SglConstantes.MSJ_EXITO_REGISTRO+"el feriado.");
									} catch (Exception ex) {
										ex.printStackTrace();
										FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No se pudo agregar el feriado"));
										logger.debug(SglConstantes.MSJ_ERROR_REGISTR+"el feriado: "+ex);
									}
									
									//Limpiar datos
									setIndFeriado('T');
									setNombreFeriado("");
									setIdUbigeo("");
									setFechaInicio(null);
									setFechaFin(null);
									setFlagMostrarCal(false);
									setFlagMostrarOrg(true);
									setTabActivado(0);									
				               
									fechaInicioTemp.add(Calendar.DATE, 1);
									
									break;
				  
				             }
								
							lstFeriado = ferDAO.buscarDinamico(filtro2);
								
							
						} else {
							logger.debug("El Feriado ya existe en la base de datos.");
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Feriado Existente", "Feriado Existente"));
						}

					} catch (Exception ex) {
						logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"feriados:"+ex);
					}
				}
				
			}
			
		}else{
			
			if ( getIdOrganos() == 0 || getFechaInLine() == null || getNombreFeriadoOrg() == null) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos","Ingrese Nombre de Feriado, �rgano y Fecha v�lidos");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			
			}else{
				
				try {
					
					logger.debug("Datos de feriado a grabar Organo:");
					logger.debug("Fecha Inline: " + getFechaInLine());
					logger.debug("Indicador: " + getIndFeriado());
					logger.debug("Nombre Feriado: " + getNombreFeriadoOrg());
					
					
					filtro.add(Restrictions.eq("nombre", getNombreFeriadoOrg()));
					filtro.add(Restrictions.eq("organo.idOrgano", getIdOrganos()));
					filtro.add(Restrictions.eq("fecha", getFechaInLine()));

					fer = ferDAO.buscarDinamico(filtro);

					if (fer.size() == 0)
					{
						
						Feriado tmpFer = new Feriado();
						tmpFer.setFecha(getFechaInLine());
						tmpFer.setEstado('A');
						tmpFer.setOrgano(buscarOrgano(getIdOrganos()));
						tmpFer.setTipo('O');
						tmpFer.setIndicador('L');
						tmpFer.setNombre(getNombreFeriadoOrg().toUpperCase());
						
						try {
							ferDAO.insertar(tmpFer);
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agreg� feriado"));
							logger.debug("guard� feriado exitosamente");
							
							lstFeriado = ferDAO.buscarDinamico(filtro2);

						} catch (Exception ex) {

							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agreg� feriado"));
							logger.debug("no guard� feriado por " + ex.getMessage());
						}
						
						//Limpiar datos
						setIdOrganos(0);
						setFechaInLine(null);
						//setLstFeriado(new ArrayList<Feriado>());
						
						setFlagMostrarCal(true);
						setFlagMostrarOrg(false);
						setTabActivado(1);
						setNombreFeriadoOrg("");
						
					} else {
						logger.debug("Feriado ya existe en BD");
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Feriado Existente", "Feriado Existente"));
					}

				} catch (Exception ex) {
					logger.debug("Error al buscar si feriado existe en BD");
				}
				
			}
			
		}
		limpiarFeriado(e);
	}
	
	public void cambioEscenario(){
		
		
		if(getIndEscenario().compareTo('C')==0){
			
			setFlagMostrarCal(false);
			setFlagMostrarOrg(true);
			setTabActivado(0);
			
		}else{

				setFlagMostrarCal(true);
				setFlagMostrarOrg(false);
				setTabActivado(1);
							
		}
		
	}
	
	public void cambioIndFeriado(){
		
		if(getIndFeriado().compareTo('N')==0 || getIndFeriado().compareTo('T')==0){
			setFlagDeshUbigeos(true);
			setIdUbigeo(null);
		}else{
			setFlagDeshUbigeos(false);
			// Carga Ubigeos
			GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			Busqueda filtroUbigeo = Busqueda.forClass(Ubigeo.class);
			filtroUbigeo.setMaxResults(SglConstantes.CANTIDAD_UBIGEOS);
			filtroUbigeo.addOrder(Order.asc("codDist"));

			try {
				lstUbigeo = ubiDAO.buscarDinamico(filtroUbigeo);
			} catch (Exception e) {
				logger.debug("Error al cargar el listado de ubigeos");
			}
		}
		
	}
	
	public void busquedaFeriado(ActionEvent e)
	{
		GenericDao<Feriado, Object> ubiDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroFer= Busqueda.forClass(Feriado.class);
		boolean buscar=true;
		
		logger.debug("[BUSQ_FERIAD]-IndicadorFeriado: " + getIndFeriado());
		logger.debug("[BUSQ_FERIAD]-FechInicio: " + getFechaInicio());
		logger.debug("[BUSQ_FERIAD]-FechFin: " + getFechaFin());
		logger.debug("[BUSQ_FERIAD]-IdUbigeo:" + getIdUbigeo());
		logger.debug("[BUSQ_FERIAD]-IndicadEscenario: " + getIndEscenario());
		
		//if (getFechaInicio()!=null && getFechaFin()!=null)
		//{
			
				if(getIndEscenario().compareTo('C')==0)
				{
					logger.debug("-- Busqueda por Calendario --");
					filtroFer.add(Restrictions.eq("tipo", getIndEscenario()));
					
					if (!getIndFeriado().equals('X'))
					{
						if (!getIndFeriado().equals('T'))
						{
							filtroFer.add(Restrictions.eq("indicador", getIndFeriado()));
						}
					}
					System.out.println("getNombreFeriado(): " + getNombreFeriado());
					if (getNombreFeriado().compareTo("")!=0)
					{
						logger.debug("Entr� getNombreFeriado(): " + getNombreFeriado());
						String filtroNuevo = SglConstantes.SIMBOLO_PORCENTAJE + getNombreFeriado().toUpperCase().concat(SglConstantes.SIMBOLO_PORCENTAJE);
						System.out.println("filtro Nuevo: " + filtroNuevo);
						filtroFer.add(Restrictions.like("nombre", filtroNuevo));
					}
					
					if (getIdUbigeo() != null)
					{
						if (getIdUbigeo().compareTo("")!=0)
						{
							logger.debug("Entro getIdUbigeo(): " + getIdUbigeo());
							filtroFer.createAlias("ubigeo", "ubi");
							filtroFer.add(Restrictions.eq("ubi.codDist", getIdUbigeo()));
						}	
					}
					
					
					if (getFechaInicio()!=null || getFechaFin()!=null)
					{
						if (getFechaInicio()!=null && getFechaFin()!=null)
						{
							if (getFechaFin().after(getFechaInicio()))
							{
								filtroFer.add(Restrictions.between("fecha", getFechaInicio(), getFechaFin()));
							}
							else
							{
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Advertencia", "La fecha de fin no puede ser menor a la fecha de inicio");
								FacesContext.getCurrentInstance().addMessage(null, msg);
								logger.debug("Error al validar las fechas. La fecha de fin no puede ser menor a la fecha de inicio");	
								buscar=false;
							}
							
						}
						else
						{
							filtroFer.add(Restrictions.ge("fecha", getFechaInicio()));
						}
					}
					
					
				}else{
					
					if(getIndEscenario().compareTo('O')==0){
						logger.debug("-- Busqueda por Organo --");

						filtroFer.add(Restrictions.eq("tipo", getIndEscenario()));
						
						if (getFechaInLine()!=null)
						{
							filtroFer.add(Restrictions.eq("fecha", getFechaInLine()));
						}
						
						if (getIdOrganos()!=0)
						{
							filtroFer.add(Restrictions.eq("organo.idOrgano", getIdOrganos()));
						}
						
						if (getNombreFeriadoOrg().compareTo("")!=0)
						{
							logger.debug("Entr� getNombreFeriadoOrg(): " + getNombreFeriadoOrg());
							String filtroNuevo = SglConstantes.SIMBOLO_PORCENTAJE + getNombreFeriadoOrg().toUpperCase().concat(SglConstantes.SIMBOLO_PORCENTAJE);
							System.out.println("filtro Nuevo: " + filtroNuevo);
							filtroFer.add(Restrictions.like("nombre", filtroNuevo));
						}
					
					}
					
				}
				
				if(buscar)
				{
					try {
						lstFeriado=  ubiDAO.buscarDinamico(filtroFer);
						if(lstFeriado!=null){
							logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"feriados consultados es:["+lstFeriado.size()+"].");
						}
						
					} catch (Exception ex) {			
						logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"feriados: "+ex);
					}
				}
				
			}
	//}
	
	public void editarFeriado(RowEditEvent event)
	{
		logger.debug("=== editarFeriado() ====");
		Feriado ferNuev = ((Feriado) event.getObject());
		logger.debug("[EDIT_FERIAD]-IdFeriado:" + ferNuev.getIdFeriado());
		logger.debug("[EDIT_FERIAD]-Nombre:" + ferNuev.getNombre());
		
		GenericDao<Feriado, Object> ferDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			
			Feriado ferAnt = ferDAO.buscarById(Feriado.class, ferNuev.getIdFeriado());
			
			//if(ferNuev.getFechaInicio().compareTo(ferAnt.getFechaInicio())!=0 ||
				//	ferNuev.getFechaFin().compareTo(ferAnt.getFechaFin())!=0){
				
			
				//envioMail.enviarcorreo(ferAnt, ferNuev);
			//}
		
			ferDAO.modificar(ferNuev);			
			logger.debug(SglConstantes.MSJ_EXITO_ACTUALIZ+"el feriado.");
			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el feriado: "+e);
		}
	}

	public void agregarOficina(ActionEvent e) 
	{
		List<Oficina> ofi = new ArrayList<Oficina>();
		GenericDao<Oficina, Object> ofiDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Oficina.class);
		Busqueda filtro2 = Busqueda.forClass(Oficina.class);
		
		if ( getNomOficina().compareTo("") == 0 || getCodigoOficina().compareTo("")==0 || getIdUbigeo().compareTo("")==0) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Codigo Oficina, Nombre, Ubigeo", "Datos Requeridos: Codigo Oficina, Nombre, Ubigeo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNomOficina()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				ofi = ofiDAO.buscarDinamico(filtro);

				if (ofi.size() == 0) 
				{
					Oficina ofic = new Oficina();
					ofic.setCodigo(getCodigoOficina());
					ofic.setNombre(getNomOficina());
					if (getCodTerritorio()!=null)
					{
						ofic.setTerritorio(buscarTerritorio(getCodTerritorio()));
					}
					else
					{
						ofic.setTerritorio(null);
					}
					ofic.setUbigeo(buscarUbigeo(getIdUbigeo()));
					ofic.setEstado('A');
					
					try {
						ofiDAO.insertar(ofic);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego oficina"));
						logger.debug("guardo oficina exitosamente");
						
						lstOficina = ofiDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego oficina"));
						logger.debug("no guardo oficina por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Oficina Existente", "Oficina Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si oficina existe en BD");
			}
		}
	}
	
	public void busquedaOficina(ActionEvent e)
	{
		GenericDao<Oficina, Object> oficDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOfi= Busqueda.forClass(Oficina.class);
		
		if (getNomOficina().compareTo("")!=0)
		{
			String filtroNuevo="%" + getNomOficina().concat("%");
			filtroOfi.add(Restrictions.sqlRestriction("lower({alias}.nombre) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodigoOficina().compareTo("")!=0)
		{
			filtroOfi.add(Restrictions.eq("codigo", getCodigoOficina()));
		}
		
		if (getCodTerritorio().compareTo("")!=0)
		{
			filtroOfi.createAlias("territorio", "terr");
			logger.debug("Codigo Territorio:" + getCodTerritorio());
			filtroOfi.add(Restrictions.eq("terr.codigo", getCodTerritorio()));
		}
		
		if (getIdUbigeo().compareTo("")!=0)
		{
			filtroOfi.createAlias("ubigeo", "ubi");
			logger.debug("Codigo Ubigeo:" + getIdUbigeo());
			filtroOfi.add(Restrictions.eq("ubi.codDist", getIdUbigeo()));
		}
		
		try {
			lstOficina =  oficDAO.buscarDinamico(filtroOfi);
		} catch (Exception ex) {
			logger.debug("Error al buscar oficina");
		}
	}
	
	public void editarOficina(RowEditEvent event)
	{
		logger.debug("=== editarOficina() ===");
		Oficina ofi = ((Oficina) event.getObject());
		logger.debug("[EDIT_OFIC]-NombreOficina:" + ofi.getNombre());
		logger.debug("[EDIT_OFIC]-UbigeoCodDistrito:" + ofi.getUbigeo().getCodDist());
		
		GenericDao<Oficina, Object> ofiDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			ofiDAO.modificar(ofi);
			logger.debug(SglConstantes.MSJ_EXITO_ACTUALIZ+"la oficina");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la oficina: "+e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void reasignarUsuario(ActionEvent e)
	{
		int ind=0;
		
		if(getNuevoResponsable()==null){
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Requerido", "Ingrese Usuario Responsable"));
			
		}else{
			
			for (Expediente tmp: selectedExpediente)
			{
				ind++;
				
				List<Expediente> lstExp = new ArrayList<Expediente>();
				GenericDao<Expediente, Object> expDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
				Busqueda filtro = Busqueda.forClass(Expediente.class);
				
				try {

					filtro.add(Restrictions.eq("idExpediente", tmp.getIdExpediente()));
					
					lstExp = expDAO.buscarDinamico(filtro);
					
					if (lstExp.size() == 1) 
					{
						lstExp.get(0).setUsuario(getNuevoResponsable());
						
						for (Expediente exp: lstExp)
						{
							expDAO.modificar(exp);
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Se cambi� el responsable del expediente"));
							logger.debug("Cambio el responsable del expediente");
						}
						
					}
					
				} catch (Exception ex) {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No exitoso", "No cambi� el responsable del expediente"));
					logger.debug("no guardo ubigeo por " + ex.getMessage());
				}
			}
			
			if (selectedExpediente.length>0)
			{
				selectedExpediente = new Expediente[0];
				buscarExpedientes(e);
			}
		}
		
		
	}
	
	public void limpiarReasignacion(ActionEvent e)
	{
		setIdEstadoSelected(0);
		setIdProceso(0);
		setIdVias(0);
		setOficina(new Oficina());
		setNroExpeOficial("");
		setResponsable(new Usuario());
		setNuevoResponsable(new Usuario());
		
		
		
		//selectedExpediente = new Expediente[0];
	}

	public void agregarUbigeo(ActionEvent e) 
	{
		List<Ubigeo> ubi = new ArrayList<Ubigeo>();
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Ubigeo.class);
		Busqueda filtro2 = Busqueda.forClass(Ubigeo.class);

		
		if ( getCodigoDistrito().compareTo("") == 0 || getNomDistrito().compareTo("")==0 || getCodigoProvincia().compareTo("")==0 ||
			 getNomProvincia().compareTo("")==0 || getCodigoDepartamento().compareTo("")==0 || getNomDepartamento().compareTo("")==0 ) 
		{
			
			FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,"Datos Requeridos: " +
								"Codigo Distrito, Distrito, Codigo Provincia, Provincia, Codigo Departamento, Departamento", 
								"Datos Requeridos: Codigo Distrito, Distrito, Codigo Provincia, Provincia, Codigo Departamento, Departamento");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else
		{
			try {

				filtro.add(Restrictions.eq("codDist", getCodigoDistrito()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				ubi = ubiDAO.buscarDinamico(filtro);

				if (ubi.size() == 0) 
				{
					Ubigeo ubig = new Ubigeo();
					ubig.setCodDist(getCodigoDistrito());
					ubig.setDistrito(getNomDistrito());
					ubig.setCodDep(getCodigoDepartamento());
					ubig.setDepartamento(getNomDepartamento());
					ubig.setCodProv(getCodigoProvincia());
					ubig.setProvincia(getNomProvincia());
					ubig.setEstado('A');
					
					try {
						ubiDAO.insertar(ubig);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego ubigeo"));
						logger.debug("guardo ubigeo exitosamente");
						
						lstUbigeo = ubiDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego ubigeo"));
						logger.debug("no guardo ubigeo por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Ubigeo Existente", "Ubigeo Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si ubigeo existe en BD");
			}
		}
	}
	
	public void busquedaUbigeo(ActionEvent e)
	{
		logger.debug("=== busquedaUbigeo() ===");
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbi= Busqueda.forClass(Ubigeo.class);
		
		if (getCodigoDistrito().compareTo("")!=0)
		{
			logger.debug("[BUSQ_UBIG]-CodDistrito:"+getCodigoDistrito());
			String filtroNuevo="%" + getCodigoDistrito().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.cod_dist) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getNomDistrito().compareTo("")!=0)
		{
			logger.debug("[BUSQ_UBIG]-getNomDistrito:"+getNomDistrito());
			String filtroNuevo="%" + getNomDistrito().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.distrito) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodigoProvincia().compareTo("")!=0)
		{
			logger.debug("[BUSQ_UBIG]-getCodigoProvincia:"+getCodigoProvincia());
			String filtroNuevo="%" + getCodigoProvincia().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.cod_prov) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getNomProvincia().compareTo("")!=0)
		{
			logger.debug("[BUSQ_UBIG]-getNomProvincia:"+getNomProvincia());
			String filtroNuevo="%" + getNomProvincia().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.provincia) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodigoDepartamento().compareTo("")!=0)
		{
			logger.debug("[BUSQ_UBIG]-getCodigoDepartamento:"+getCodigoDepartamento());
			String filtroNuevo="%" + getCodigoDepartamento().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.cod_dep) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getNomDepartamento().compareTo("")!=0)
		{
			logger.debug("[BUSQ_UBIG]-getNomDepartamento:"+getNomDepartamento());
			String filtroNuevo="%" + getNomDepartamento().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.departamento) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		try {
			lstUbigeo =  ubiDAO.buscarDinamico(filtroUbi);
			
			if(lstUbigeo!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"ubigeos encontrados es:["+lstUbigeo.size()+"].");
			}
		} catch (Exception ex) {
			logger.debug(SglConstantes.MSJ_ERROR_CONSULTAR+"ubigeos: "+ex);
		}
	}
	
	public void editarUbigeo(RowEditEvent event)
	{
		logger.debug("=== editarUbigeo() ====");
		Ubigeo ubi = ((Ubigeo) event.getObject());
		logger.debug("[EDIT_UBIG]:DescripcionDistrito:"+ubi.getDescripcionDistrito());
		
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			ubiDAO.modificar(ubi);
			logger.debug(SglConstantes.MSJ_EXITO_ACTUALIZ+"el Ubigeo.");
			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el Ubigeo: "+e);
		}
		logger.debug("=== saliendo de editarUbigeo() ====");
	}

	public void buscarProceso(ActionEvent e) {
		logger.debug("=== buscarProceso() ===");
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Proceso.class);

		if (getNombreProceso().compareTo("") != 0) {
			logger.debug("[BUSQ_PROCES]-getNombreProceso:"+getNombreProceso());
			filtro.add(Restrictions.like("nombre","%" + getNombreProceso() + "%").ignoreCase());
		}

		if (getAbrevProceso().compareTo("") != 0) {
			logger.debug("[BUSQ_PROCES]-getAbrevProceso:"+getAbrevProceso());
			filtro.add(Restrictions.like("abreviatura","%" + getAbrevProceso() + "%").ignoreCase());
		}

		try {
			procesos2 = procesoDAO.buscarDinamico(filtro);
			if(procesos2!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"procesos encontrados es:["+procesos2.size()+"].");
			}
		} catch (Exception e2) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"procesos: "+e2);
		}

		logger.debug("=== saliendo de buscarProceso() ====");

	}

	public void agregarProceso(ActionEvent e) {

		List<Proceso> procesos_ = new ArrayList<Proceso>();
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Proceso.class);
		Busqueda filtro2 = Busqueda.forClass(Proceso.class);

		
		if ( getNombreProceso().compareTo("") == 0  || getAbrevProceso().compareTo("") ==  0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre Proceso, Abreviatura", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			Proceso proceso = new Proceso();
			proceso.setNombre(getNombreProceso());
			proceso.setAbreviatura(getAbrevProceso());
			proceso.setEstado('A');
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreProceso()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				procesos_ = procesoDAO.buscarDinamico(filtro);

				if (procesos_.size() == 0) {

					try {

						Proceso procesobd = new Proceso();
						procesobd = procesoDAO.insertar(proceso);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Exitoso", "Agrego el proceso"));
						logger.debug("guardo el proceso exitosamente");
						
						
						procesos2 = procesoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"No Exitoso", "No Agrego el proceso"));
						logger.debug("no guardo el proceso por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Proceso Existente", "Proceso Existente"));
				}

			} catch (Exception ex) {

			}
			
			
		}

		

	}

	public void limpiarProceso(ActionEvent e) {

		setNombreProceso("");
		setAbrevProceso("");
		procesos2 = new ArrayList<Proceso>();

	}

	public void editProceso(RowEditEvent event) {

		Proceso proceso = ((Proceso) event.getObject());
		logger.debug("modificando proceso " + proceso.getNombre());
		
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			procesoDAO.modificar(proceso);
			logger.debug("actualizo el proceso exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el proceso:"+e);
		}
	}

	public void buscarVia(ActionEvent e) {

		logger.debug("entro al buscar via");
		
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Via.class);

		if (getIdProceso() != 0) {

			logger.debug("filtro " + getIdProceso() + " proceso - idproceso");
			filtro.add(Restrictions.eq("proceso.idProceso",getIdProceso()));
		}

		if (getNombreVia().compareTo("") != 0) {

			logger.debug("filtro " + getNombreVia() + " proceso - via");
			filtro.add(Restrictions.like("nombre","%" + getNombreVia() + "%").ignoreCase());
		}

		try {
			vias = viaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar vias");
		}

		logger.debug("trajo .." + vias.size());

	}
	
	public void agregarVia(ActionEvent e) {

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		List<Via> vias_= new ArrayList<Via>();
		
		Busqueda filtro = Busqueda.forClass(Via.class);
		Busqueda filtro2 = Busqueda.forClass(Via.class);
		
		if ( getIdProceso() == 0  || getNombreVia().compareTo("") ==  0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Proceso, Via", "Datos Requeridos: Proceso, Via");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreVia()).ignoreCase());
				filtro.add(Restrictions.eq("proceso.idProceso",getIdProceso()));

				vias_ = viaDAO.buscarDinamico(filtro);

				if (vias_.size() == 0) {

					try {
						Via via = new Via();
						via.setNombre(getNombreVia());
						via.setEstado('A');
						via.setProceso(procesoDAO.buscarById(Proceso.class, getIdProceso()));
						
						viaDAO.insertar(via);
						FacesContext.getCurrentInstance().addMessage(null,
															new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso","Agrego la via"));
						logger.debug("guardo la via exitosamente");
						
						vias = viaDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,
															new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso","No Agrego la via"));
						logger.debug("no guardo la via por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Via Existente", "Via Existente"));
				}

			} catch (Exception ex) {

			}

			
			
		}
		
		

	}

	public void limpiarVia(ActionEvent e) {
		setNombreVia("");
		setIdProceso(0);
		
		vias= new ArrayList<Via>();
	}
	
	public void editVia(RowEditEvent event) {

		Via via = ((Via) event.getObject());
		logger.debug("modificando la via " + via.getNombre());
		
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			viaDAO.modificar(via);
			logger.debug("actualizo la via exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Via:"+e);
		}
	}
	
	public void buscarRol(ActionEvent e) {

		logger.debug("entro al buscar rol");

		GenericDao<Rol, Object> rolDAO = (GenericDao<Rol, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Rol.class);
		
		if (getIdProceso2() != 0) {

			logger.debug("filtro " + getIdProceso2() + " proceso - id");
			filtro.add(Restrictions.eq("proceso.idProceso",getIdProceso2()));
		}
		
		if (getNombreRol().compareTo("") != 0) {

			logger.debug("filtro " + getNombreRol() + " rol - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreRol() + "%").ignoreCase());
		}

		try {
			rols2 = rolDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar los roles en BD");
		}

		logger.debug("trajo .." + rols2.size());

	}
	
	public void agregarRol(ActionEvent e) {

		GenericDao<Rol, Object> rolDAO = (GenericDao<Rol, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Rol.class);
		Busqueda filtro2 = Busqueda.forClass(Rol.class);
		
		List<Rol> rols_=new ArrayList<Rol>();

		if ( getIdProceso2() == 0  || getNombreRol().compareTo("") ==  0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Proceso, Nombre", "Datos Requeridos: Proceso, Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreRol()).ignoreCase());

				rols_ = rolDAO.buscarDinamico(filtro);

				if (rols_.size() == 0) {
					
					Rol rol= new Rol();
					rol.setDescripcion(getNombreRol());
						Proceso proceso= new Proceso();
						proceso.setIdProceso(getIdProceso2());
					rol.setProceso(proceso);
					rol.setEstado('A');
					
					try {
						rolDAO.insertar(rol);
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el rol"));
						logger.debug("guardo el rol exitosamente");
			
						rols2 = rolDAO.buscarDinamico(filtro2);
						
					} catch (Exception ex) {
			
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el rol"));
						logger.debug("no guardo el rol por " + ex.getMessage());
					}
					
				}else{
					

					FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Rol Existente", "Rol Existente"));
					
				}
			

			} catch (Exception ex) {
	
				
			}
	
		}
			

	}
	
	public void editarRol(RowEditEvent event) {

		Rol rol= ((Rol) event.getObject());
		logger.debug("modificando rol " + rol.getDescripcion());
		
		GenericDao<Rol, Object> rolDAO = (GenericDao<Rol, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			rolDAO.modificar(rol);
			logger.debug("actualizo el rol exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el Rol:"+e);
		}
	}
	
	public void limpiarRol(ActionEvent e) {
		setNombreRol("");
		setIdProceso2(0);
		
		rols2 = new ArrayList<Rol>();
	}
	

	public void busquedaNotificacion(ActionEvent e)
	{	
		logger.debug("ingreso al busqueda de notificacion ");
		
		GenericDao<Aviso, Object> avisDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAv= Busqueda.forClass(Aviso.class);
		
		if (getIdProceso()!=0)
		{
			filtroAv.createAlias("proceso", "pro");
			filtroAv.add(Restrictions.eq("pro.idProceso", getIdProceso()));
		}
		
		if (getIdVias()!=0)
		{
			filtroAv.createAlias("via", "vi");
			filtroAv.add(Restrictions.eq("vi.idVia", getIdVias()));
		}
		
		if (getIdActividad()!=0)
		{
			filtroAv.createAlias("actividad", "act");
			filtroAv.add(Restrictions.eq("act.idActividad", getIdActividad()));
		}
		
		try {
			lstAviso =  avisDAO.buscarDinamico(filtroAv);
		} catch (Exception ex) {
			logger.debug("Error al buscar notificaciones");
		}
	}
	
	
	public void agregarNotificacion(ActionEvent e) 
	{
		GenericDao<Aviso, Object> avisoDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		List<Aviso> avisos = new ArrayList<Aviso>();
		Busqueda filtro = Busqueda.forClass(Aviso.class);
		Busqueda filtro2 = Busqueda.forClass(Aviso.class);
		int tipoEstado=validarTipoEstado();
		
		if (tipoEstado==-1)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Seleccionar por lo menos una opci�n.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else
		{
			logger.debug("Opcion estado escogida luego de validacion:" + validarTipoEstadoStr(tipoEstado));
			if ( getIdProcesoEstado() == 0 && getIdViasLst() ==0 && getIdActividadLst()==0) 
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje", "Debe seleccionar al menos una actividad, un proceso o v�a a configurar");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			else
			{
				try 
				{	
					if (tipoEstado==1)
					{
						filtro.createAlias("proceso", "pro");
						filtro.add(Restrictions.eq("pro.idProceso", getIdProcesoEstado()));
					}
					
					if (tipoEstado==2)
					{
						filtro.createAlias("proceso", "pro");
						filtro.add(Restrictions.eq("pro.idProceso", getIdProcesoEstado()));
						
						filtro.createAlias("via", "vi");
						filtro.add(Restrictions.eq("vi.idVia", getIdViasLst()));
					}
					
					if (tipoEstado==3 || tipoEstado==4)
					{
						filtro.createAlias("proceso", "pro");
						filtro.add(Restrictions.eq("pro.idProceso", getIdProcesoEstado()));
						
						filtro.createAlias("via", "vi");
						filtro.add(Restrictions.eq("vi.idVia", getIdViasLst()));
						
						filtro.createAlias("actividad", "act");
						filtro.add(Restrictions.eq("act.idActividad", getIdActividadLst()));
					}
					
					avisos = avisoDAO.buscarDinamico(filtro);
					
					if (avisos.size() == 0) 
					{
						int numDiasRojoEst1 = getNumDiasRojoEst1();
						int numDiasAmaEst1 = getNumAmaEst1();
						int numDiasNaraEst1 = getNumNaraEst1();
						
						logger.debug("Parametros ingresados: Por proceso o V�a o Actividad");
						logger.debug("D�as en Rojo: " + numDiasRojoEst1);
						logger.debug("D�as en Naranja: " + numDiasNaraEst1);
						logger.debug("D�as en Amarillo: " + numDiasAmaEst1);
						
						//validar numero de dias por TipoEstado
						
						if (validarDiasTipoEstado())
						{
							//Validacion de estado de color Rojo
							Aviso avis = new Aviso();
							if (getIdProcesoEstado()!=0)
							{
								Proceso proceso= new Proceso();
								proceso.setIdProceso(getIdProcesoEstado());
								//buscarProceso(getIdProcesoEstado()
								avis.setProceso(proceso);
							}
							else
							{
								avis.setProceso(null);
							}
							
							if (getIdViasLst()!=0)
							{
								Via via= new Via();
								via.setIdVia(getIdViasLst());
								//buscarVia(getIdViasLst())
								avis.setVia(via);
							}
							else
							{
								avis.setVia(null);
							}
							
							if (getIdActividadLst()!=0)
							{
								Actividad actividad= new Actividad();
								actividad.setIdActividad(getIdActividadLst());
								//buscarActividad(getIdActividadLst())
								avis.setActividad(actividad);
							}
							else
							{
								avis.setActividad(null);
							}
							
							avis.setColor('R');
							avis.setDias(numDiasRojoEst1);
							avis.setEstado('A');
						
							try {
								avisoDAO.insertar(avis);
								FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agreg� configuraci�n de notificaciones para color Rojo"));
								logger.debug("guard� configuraci�n de notificaciones");
								//lstAviso = avisoDAO.buscarDinamico(filtro2);
	
							} catch (Exception ex) {
								FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agreg� la configuraci�n de notificaciones"));
								logger.debug("no guard� la configuraci�n de notificaciones por "+ ex.toString());
							}
							
							//Validacion de estado de color Naranja
							Aviso avis2 = new Aviso();
							if (getIdProcesoEstado()!=0)
							{
								avis2.setProceso(buscarProceso(getIdProcesoEstado()));
							}
							else
							{
								avis2.setProceso(null);
							}
							
							if (getIdViasLst()!=0)
							{
								avis2.setVia(buscarVia(getIdViasLst()));
							}
							else
							{
								avis2.setVia(null);
							}
							
							if (getIdActividadLst()!=0)
							{
								avis2.setActividad(buscarActividad(getIdActividadLst()));
							}
							else
							{
								avis2.setActividad(null);
							}
							
							avis2.setColor('N');
							avis2.setDias(numDiasNaraEst1);
							avis2.setEstado('A');
													
							try {
								avisoDAO.insertar(avis2);
								FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agreg� configuraci�n de notificaciones para color Naranja"));
								logger.debug("guard� configuraci�n de notificaciones");
								//lstAviso = avisoDAO.buscarDinamico(filtro2);
	
							} catch (Exception ex) {
								FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agreg� la configuraci�n de notificaciones"));
								logger.debug("no guard� la configuraci�n de notificaciones por "+ ex.getMessage());
							}
							
							//Validacion de estado de color Amarillo
							Aviso avis3 = new Aviso();
							if (getIdProcesoEstado()!=0)
							{
								avis3.setProceso(buscarProceso(getIdProcesoEstado()));
							}
							else
							{
								avis3.setProceso(null);
							}
							
							if (getIdViasLst()!=0)
							{
								avis3.setVia(buscarVia(getIdViasLst()));
							}
							else
							{
								avis3.setVia(null);
							}
							
							if (getIdActividadLst()!=0)
							{
								avis3.setActividad(buscarActividad(getIdActividadLst()));
							}
							else
							{
								avis3.setActividad(null);
							}
							
							avis3.setColor('A');
							avis3.setDias(numDiasAmaEst1);
							avis3.setEstado('A');
							
							try {
								avisoDAO.insertar(avis3);
								FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agreg� configuraci�n de notificaciones para color Amarillo"));
								logger.debug("guardo configuracion de notificaciones");
								//lstAviso = avisoDAO.buscarDinamico(filtro2);
	
							} catch (Exception ex) {
								FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agreg� la configuraci�n de notificaciones"));
								logger.debug("no guard� la configuraci�n de notificaciones por "+ ex.getMessage());
							}
						}
						else
						{
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Configuraci�n err�nea","Configuraci�n de d�as incorrecta!!"));
							logger.debug("Configuraci�n de d�as incorrecta. El numero de d�as de color rojo debe ser menor al n�mero de d�as de color naranja y este menor al de amarillo");
						}
						
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Configuraci�n Existente" ,"Configuraci�n Existente en base de datos"));
						logger.debug("Configuraci�n de proceso, v�a o actividad ya existente en BD");
					}
					
				} catch (Exception e1) {
					logger.debug("Error al buscar si la configuraci�n de notificaciones existe en BD");
				}
			}
		}
		
		try {
			lstAviso=avisoDAO.buscarDinamico(filtro2);
		} catch (Exception e1) {
			logger.debug("Error al recuperar las configuraciones de notificaciones!!");
		}
	}
	
	public int validarTipoEstado()
	{
		int eleccion=-1;
		
		if (getIdProcesoEstado()!=0 && getIdViasLst()!=0 && getIdActividadLst()!=0)
		{
			eleccion = 3;
		}
		
		if (getIdProcesoEstado()!=0 && getIdViasLst()!=0 && getIdActividadLst()==0)
		{
			eleccion = 2;
		}
		
		if (getIdProcesoEstado()!=0 && getIdViasLst()==0 && getIdActividadLst()==0)
		{
			eleccion = 1;
		}
		
		if (getIdProcesoEstado()==0 && getIdViasLst()==0 && getIdActividadLst()!=0)
		{
			eleccion = 4;
		}
		
		return eleccion;
	}
	
	public String validarTipoEstadoStr(int eleccion)
	{
		String result ="";
		
		switch(eleccion)
		{
		case 1: result="Por proceso";
		case 2: result="Por via";
		case 3: result="Por actividad";
		case 4: result="Solo actividad";
		}
		return result;
	}
	
	public boolean validarDiasTipoEstado()
	{
		boolean exito=false;
		
		int numDiasRojoEst1 = getNumDiasRojoEst1();
		int numDiasAmaEst1 = getNumAmaEst1();
		int numDiasNaraEst1 = getNumNaraEst1();
		
		if (numDiasRojoEst1<numDiasNaraEst1 && numDiasNaraEst1<numDiasAmaEst1)
		{
			exito=true;
		}
		return exito;
	}
	
	public void editarNotificacion(RowEditEvent event)
	{
		Aviso av = ((Aviso) event.getObject());
		logger.debug("modificando aviso para la actividad: " + av.getIdAviso());
		
		GenericDao<Aviso, Object> avDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			avDAO.modificar(av);
			logger.debug("actualizo el aviso exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Notificacion:"+e);
		}
	}
	
	

	public void buscarInstancia(ActionEvent e) {
		logger.debug("==== buscarInstancia() =====");
		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Instancia.class);

		if (getNombreInstancia().compareTo("") != 0) {
			logger.debug("[BUSQ_INST]-NombreInstancia:"+getNombreInstancia());
			filtro.add(Restrictions.like("nombre","%" + getNombreInstancia() + "%").ignoreCase());
		}

		try {
			instancias = instanciaDAO.buscarDinamico(filtro);
			if(instancias!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"instancias encontradas es:["+instancias.size()+"].");
			}
		} catch (Exception e2) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"instancias: "+e2);
		}

		logger.debug("== saliendo de buscarInstancia() ===");

	}
	
	
	public void agregarInstancia(ActionEvent e) {

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Instancia.class);
		Busqueda filtro2 = Busqueda.forClass(Instancia.class);
		
		List<Instancia> instancias_=new ArrayList<Instancia>();

		if ( getNombreInstancia().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Instancia", "Datos Requeridos: Instancia");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreInstancia()).ignoreCase());

				instancias_ = instanciaDAO.buscarDinamico(filtro);

				if (instancias_.size() == 0) {
					
					Instancia instancia = new Instancia();
					instancia.setNombre(getNombreInstancia());
					instancia.setEstado('A');
					
					try {
						instanciaDAO.insertar(instancia);
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la instancia"));
						logger.debug("guardo la instancia exitosamente");
			
						instancias = instanciaDAO.buscarDinamico(filtro2);
						
					} catch (Exception ex) {
			
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la instancia"));
						logger.debug("no guardo la instancia por " + ex.getMessage());
					}
					
				}else{
					

					FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Instancia Existente", "Instancia Existente"));
					
				}
			

			} catch (Exception ex) {
	
				
			}
	
		}
			

	}
	
	public void editInstancia(RowEditEvent event) {

		Instancia instancia = ((Instancia) event.getObject());
		logger.debug("modificando instancia " + instancia.getNombre());
		
		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			instanciaDAO.modificar(instancia);
			logger.debug("actualizo la instancia exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Instancia:"+e);
		}
	}

	public void limpiarInstancia(ActionEvent e) {
		setNombreInstancia("");
		
		//instancias = new ArrayList<Instancia>();
	}
	
	
	public void buscarUsuario(ActionEvent e) {
		logger.debug("=== inicia buscarUsuario() ====");

		GenericDao<Usuario, Object> usuarioDAO = 
				(GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Usuario.class);

		if (getIdRol() != 0) {
			logger.debug("[BUSQ_USU]-Rol:"+getIdRol());
			filtro.add(Restrictions.eq("rol.idRol",getIdRol()));
		}

		if (getNombreUsuario().compareTo("") != 0) {
			logger.debug("[BUSQ_USU]-Nombre:" + getNombreUsuario());
			filtro.add(Restrictions.like("nombres","%" + getNombreUsuario() + "%").ignoreCase());
		}
		
		if (getApPatUsuario().compareTo("") != 0) {
			logger.debug("[BUSQ_USU]-ApePat:" + getApPatUsuario());
			filtro.add(Restrictions.like("apellidoPaterno","%" + getApPatUsuario() + "%").ignoreCase());
		}
		
		if (getApMatUsuario().compareTo("") != 0) {
			logger.debug("[BUSQ_USU]-ApeMat:" + getApMatUsuario());
			filtro.add(Restrictions.like("apellidoMaterno","%" + getApMatUsuario() + "%").ignoreCase());
		}
		
		if (getCorreoUsuario().compareTo("") != 0) {
			logger.debug("[BUSQ_USU]-Correo:" + getCorreoUsuario());
			filtro.add(Restrictions.like("correo","%" + getCorreoUsuario() + "%").ignoreCase());
		}
		
		//TODO 19-03 [DIMCO] - Busqueda por codigo de usuario.
		if (getCodigoUsuario().compareTo("") != 0) {
			logger.debug("[BUSQ_USU]-CodigoUsuario:" + getCodigoUsuario());
			filtro.add(Restrictions.like("codigo","%" + getCodigoUsuario() + "%").ignoreCase());
		}

		try {
			usuarios = usuarioDAO.buscarDinamico(filtro.addOrder(Order.asc("nombres")));
		} catch (Exception e2) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"usuarios: "+e);
		}
		
		if(usuarios!=null){
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"de usuarios es: ["+usuarios.size()+"]");
		}
		
		logger.debug("=== saliendo de buscarUsuario() ====");
	}
	
	public void editUsuario(RowEditEvent event) {
		logger.debug("==== inicia editUsuario() ===");

		Usuario usuario = ((Usuario) event.getObject());
		if(usuario!=null){
			logger.debug("[EDIT-USU]-Nombre:"+usuario.getNombres());
		}
		
		for (Rol rol : getRols()) {
			if (usuario.getRol().getDescripcion().equalsIgnoreCase(rol.getDescripcion())) {
				usuario.setRol(rol);
				break;
			}
		}
			
		
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			usuarioDAO.modificar(usuario);
			logger.debug("Se ha actualizado correctamente el usuario. ");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el usuario: "+e);
		}
	}
	
	public void limpiarUsuario(ActionEvent e) {

		setIdRol(0);
		setNombreUsuario("");
		setApPatUsuario("");
		setApMatUsuario("");
		setCorreoUsuario("");
		setCodigoUsuario("");
		
		usuarios = new ArrayList<Usuario>();
	}
	
	public void buscarActividad(ActionEvent e) {

		logger.debug("entro al buscar actividad");

		GenericDao<Actividad, Object> actividadDAO = 
				(GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Actividad.class);

		if (getNombreActividad().compareTo("") != 0) {

			logger.debug("filtro " + getNombreActividad() + " actividad - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreActividad() + "%").ignoreCase());
		}

		try {
			actividads = actividadDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar actividades");
		}
		
		setNombreActividad("");
		logger.debug("trajo .." + actividads.size());

	}
	
	public void agregarActividad(ActionEvent e) {

		GenericDao<Actividad, Object> actividadDAO = 
				(GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Actividad.class);
		Busqueda filtro2 = Busqueda.forClass(Actividad.class);
		
		List<Actividad> actividads_ = new ArrayList<Actividad>();
		

		if ( getNombreActividad().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Actividad", "Datos Requeridos: Actividad");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreActividad()).ignoreCase());

				actividads_ = actividadDAO.buscarDinamico(filtro);

				if (actividads_.size() == 0) {
					
					Actividad actividad = new Actividad();
					actividad.setNombre(getNombreActividad());
					actividad.setEstado('A');

					try {
						actividadDAO.insertar(actividad);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la actividad"));
						logger.debug("guardo la actividad exitosamente");
						
						actividads = actividadDAO.buscarDinamico(filtro2);
						
						
					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la actividad"));
						logger.debug("no guardo la actividad por " + ex.getMessage());
					}
					
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Actividad Existente", "Actividad Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		
	}

	public void editActividad(RowEditEvent event) {

		Actividad actividad = ((Actividad) event.getObject());
		logger.debug("modificando actividad " + actividad.getNombre());
		
		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			actividadDAO.modificar(actividad);
			logger.debug("actualizo la actividad exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Actividad:"+e);
		}
	}
	
	public void limpiarActividad(ActionEvent e) {
		setNombreActividad("");
		
		//actividads = new ArrayList<Actividad>();
	}
	
	
	public void buscarMoneda(ActionEvent e) {
		logger.debug("==== buscarMoneda() =====");

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Moneda.class);

		if (getNombreMoneda().compareTo("") != 0) {
			logger.debug("[BUSQ_MONED]-NombreMoneda:"+getNombreMoneda());
			filtro.add(Restrictions.like("descripcion",	"%" + getNombreMoneda() + "%").ignoreCase());
		}

		if (getAbrevMoneda().compareTo("") != 0) {
			logger.debug("[BUSQ_MONED]-Abreviatura:"+getAbrevMoneda());
			filtro.add(Restrictions.like("simbolo","%" + getAbrevMoneda() + "%").ignoreCase());
		}

		try {
			monedas = monedaDAO.buscarDinamico(filtro);
			if(monedas!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"monedas encontradas es:["+monedas.size()+"].");
			}
			
		} catch (Exception e2) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"monedas: "+e2);
		}

	}
	
	public void agregarMoneda(ActionEvent e) {
		logger.debug("=== inicia agregarMoneda() ===");

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Moneda.class);
		Busqueda filtro2 = Busqueda.forClass(Moneda.class);
		
		List<Moneda> monedas_= new ArrayList<Moneda>();
		
		if ( getNombreMoneda().compareTo("") == 0  || getAbrevMoneda().compareTo("") ==  0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Descripci�n, Abreviatura", "Datos Requeridos: Descripcion, Abreviatura");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreMoneda()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevMoneda()));

				monedas_ = monedaDAO.buscarDinamico(filtro);

				if (monedas_.size() == 0) {					
				
					Moneda moneda = new Moneda();
					moneda.setDescripcion(getNombreMoneda());
					moneda.setSimbolo(getAbrevMoneda());
					moneda.setEstado('A');

					try {
						monedaDAO.insertar(moneda);
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Se agreg� la moneda correctamente."));
						logger.debug(SglConstantes.MSJ_EXITO_REGISTRO+"la moneda.");
						
						monedas = monedaDAO.buscarDinamico(filtro2);
						
					} catch (Exception ex) {
						FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
							"No se pudo agregar la moneda"));
						logger.debug(SglConstantes.MSJ_ERROR_REGISTR+"la moneda:"+ex);
					}
					
				}else{
					
				
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Moneda Existente", "Moneda Existente"));
				}
				
			} catch (Exception ex) {

			}

		}		
					
		

	}

	public void editMoneda(RowEditEvent event) {
		logger.debug("=== editMoneda() ===");
		Moneda moneda = ((Moneda) event.getObject());
		logger.debug("[EDIT_MONED]-MonedaDescripcion:" + moneda.getDescripcion());
		
		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			monedaDAO.modificar(moneda);
			logger.debug(SglConstantes.MSJ_EXITO_ACTUALIZ+"la moneda.");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la moneda:"+e);
		}
	}
	
	
	public void limpiarMoneda(ActionEvent e) {
		setNombreMoneda("");
		setAbrevMoneda("");
		
		monedas = new ArrayList<Moneda>();
	}
	
	public void buscarEstudio(ActionEvent e) {

		logger.debug("entro al buscar estudio");

		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Estudio.class);

		if (getRucEstudio() != 0) {

			logger.debug("filtro " + getRucEstudio() + " estudio - ruc");
			filtro.add(Restrictions.eq("ruc",getRucEstudio()));
		}

		if (getNombreEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEstudio() + " estudio - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEstudio() + "%").ignoreCase());
		}
		if (getDireccionEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getDireccionEstudio() + " estudio - direccion");
			filtro.add(Restrictions.like("direccion","%" + getDireccionEstudio() + "%").ignoreCase());
		}
		if (getTelefEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getTelefEstudio() + " estudio - telefono");
			filtro.add(Restrictions.like("telefono","%" + getTelefEstudio() + "%").ignoreCase());
		}
		if (getCorreoEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getCorreoEstudio() + " estudio - correo");
			filtro.add(Restrictions.like("correo","%" + getCorreoEstudio() + "%").ignoreCase());
		}

		try {
			estudios = estudioDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar estudios");
		}

		logger.debug("trajo .." + estudios.size());

	}
	
	public void agregarEstudio(ActionEvent e) {

		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		List<Estudio> estudios_= new ArrayList<Estudio>();
		
		Busqueda filtro = Busqueda.forClass(Estudio.class);
		Busqueda filtro2 = Busqueda.forClass(Estudio.class);

		
		if ( getRucEstudio() == 0  
				|| getNombreEstudio().compareTo("") ==  0
					|| getDireccionEstudio().compareTo("") ==  0
						|| getTelefEstudio().compareTo("") ==  0
							|| getCorreoEstudio().compareTo("") ==  0) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Ruc, Nombre, Direccion, Telefono, Correo", "Datos Requeridos: Ruc, Nombre, Direccion, Telefono, Correo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEstudio()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				estudios_ = estudioDAO.buscarDinamico(filtro);

				if (estudios_.size() == 0) {
					
					Estudio estudio = new Estudio();

					estudio.setRuc(getRucEstudio());
					estudio.setCorreo(getCorreoEstudio());
					estudio.setDireccion(getDireccionEstudio());
					estudio.setNombre(getNombreEstudio());
					estudio.setTelefono(getTelefEstudio());
					estudio.setEstado('A');
					
					try {
						estudioDAO.insertar(estudio);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el estudio"));
						logger.debug("guardo el estudio exitosamente");
						
						estudios = estudioDAO.buscarDinamico(filtro2);
						
					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el estudio"));
						logger.debug("no guardo el estudio por " + ex.getMessage());
					}
					
				} else {

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Estudio Existente", "Estudio Existente"));
				}

			} catch (Exception ex) {

			}

		
		}
						
		

	}
	
	public void editEstudio(RowEditEvent event) {

		Estudio estudio = ((Estudio) event.getObject());
		logger.debug("modificando estudio " + estudio.getNombre());
		
		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			estudioDAO.modificar(estudio);
			logger.debug("actualizo el estudio exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el Estudio:"+e);
		}
	}
	
	

	
	public void limpiarEstudio(ActionEvent e) {

		setRucEstudio(null);
		setCorreoEstudio("");
		setDireccionEstudio("");
		setNombreEstudio("");
		setTelefEstudio("");
		
		estudios= new ArrayList<Estudio>();
	}

	
	public void buscarRolInv(ActionEvent e) {

		logger.debug("entro al buscar rol inv");

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = 
				(GenericDao<RolInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(RolInvolucrado.class);

		if (getNombreRolInvol().compareTo("") != 0) {

			logger.debug("filtro " + getNombreRolInvol() + " rol inv - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreRolInvol() + "%").ignoreCase());
		}

		try {
			rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar los roles de los involucrados");
		}

		logger.debug("trajo .." + rolInvolucrados.size());

	}
	
	public void agregarRolInv(ActionEvent e) {

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(RolInvolucrado.class);
		Busqueda filtro2 = Busqueda.forClass(RolInvolucrado.class);
		
		List<RolInvolucrado> rolInvolucrados_ = new ArrayList<RolInvolucrado>();
		

		if ( getNombreRolInvol().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreRolInvol()).ignoreCase());

				rolInvolucrados_ = rolInvolucradoDAO.buscarDinamico(filtro);

				if (rolInvolucrados_.size() == 0) {
					
					RolInvolucrado rolInvolucrado = new RolInvolucrado();
					rolInvolucrado.setNombre(getNombreRolInvol());
					rolInvolucrado.setEstado('A');
					
					try {
						rolInvolucradoDAO.insertar(rolInvolucrado);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el rol involucrado"));
						logger.debug("guardo el rol involucrado exitosamente");
						
						rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el rol involucrado"));
						logger.debug("no guardo el rol involucrado por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Rol Inv Existente", "Rol Inv Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}							
					
		

	}
	
	public void editRolInv(RowEditEvent event) {

		RolInvolucrado rolInvolucrado = ((RolInvolucrado) event.getObject());
		logger.debug("modificando Rol Involucrado " + rolInvolucrado.getNombre());
		
		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			rolInvolucradoDAO.modificar(rolInvolucrado);
			logger.debug("actualizo el rol inv exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el RolInvolucrado:"+e);
		}
	}
	
	public void limpiarRolInv(ActionEvent e) {

		setNombreRolInvol("");
		
		rolInvolucrados = new ArrayList<RolInvolucrado>();
	}
	
	public void buscarEstCaut(ActionEvent e) {

		logger.debug("entro al buscar est caut");

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = 
				(GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(EstadoCautelar.class);

		if (getNombreEstCaut().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEstCaut() + " est caut - nombre");
			filtro.add(Restrictions.like("descripcion","%" + getNombreEstCaut() + "%").ignoreCase());
		}

		try {
			estadosCautelars = estadoCautelarDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar los registros de estado cautelar");
		}

		logger.debug("trajo .." + estadosCautelars.size());

	}

	public void agregarEstCaut(ActionEvent e) {

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(EstadoCautelar.class);
		Busqueda filtro2 = Busqueda.forClass(EstadoCautelar.class);
		
		List<EstadoCautelar> estadoCautelars_ = new ArrayList<EstadoCautelar>();
		

		if ( getNombreEstCaut().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreEstCaut()).ignoreCase());

				estadoCautelars_ = estadoCautelarDAO.buscarDinamico(filtro);

				if (estadoCautelars_.size() == 0) {
					
					EstadoCautelar estadoCautelar = new EstadoCautelar();
					estadoCautelar.setDescripcion(getNombreEstCaut());
					estadoCautelar.setEstado('A');
					try {
						estadoCautelarDAO.insertar(estadoCautelar);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el estado cautelar"));
						logger.debug("guardo el estado cautelar exitosamente");
						
						estadosCautelars = estadoCautelarDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el estado cautelar"));
						logger.debug("no guardo el estado cautelar por " + ex.getMessage());
					}
					
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Est Caut Existente", "Est Caut Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}	

	}
	

	public void editEstCaut(RowEditEvent event) {

		EstadoCautelar estadoCautelar = ((EstadoCautelar) event.getObject());
		logger.debug("modificando estadoCautelar " + estadoCautelar.getDescripcion());
		
		GenericDao<EstadoCautelar, Object> estadoCautelardao = (GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			estadoCautelardao.modificar(estadoCautelar);
			logger.debug("actualizo el estadoCautelar exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el EstadoCautelar:"+e);
		}
	}

	public void limpiarEstCaut(ActionEvent e) {

		setNombreEstCaut("");
		//estadosCautelars = new ArrayList<EstadoCautelar>();
	}

	
	public void buscarEstExpe(ActionEvent e) {

		logger.debug("entro al buscar est expe");

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = 
				(GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(EstadoExpediente.class);

		if (getNombreEstExpe().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEstExpe() + " est expe - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEstExpe() + "%").ignoreCase());
		}

		try {
			estadoExpedientes = estadoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar los estados de los expedientes");
		}

		logger.debug("trajo .." + estadoExpedientes.size());

	}


	public void agregarEstExpe(ActionEvent e) {

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(EstadoExpediente.class);
		Busqueda filtro2 = Busqueda.forClass(EstadoExpediente.class);
		
		List<EstadoExpediente> estadoExpedientes_ = new ArrayList<EstadoExpediente>();
		
		if ( getNombreEstExpe().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEstExpe()).ignoreCase());

				estadoExpedientes_ = estadoExpedienteDAO.buscarDinamico(filtro);

				if (estadoExpedientes_.size() == 0) {
					
					EstadoExpediente estadoExpediente = new EstadoExpediente();
					estadoExpediente.setNombre(getNombreEstExpe());
					estadoExpediente.setEstado('A');
					try {
						estadoExpedienteDAO.insertar(estadoExpediente);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el estado expediente"));
						logger.debug("guardo el estado expediente exitosamente");
						
						estadoExpedientes = estadoExpedienteDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el estado expediente"));
						logger.debug("no guardo el estado expediente por "
								+ ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Est Expe Existente", "Est Expe Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}	
		
		
		

	}
	
	public void editEstExpe(RowEditEvent event) {

		EstadoExpediente estadoExpediente= ((EstadoExpediente) event.getObject());
		logger.debug("modificando estadoCautelar " + estadoExpediente.getNombre());
		
		GenericDao<EstadoExpediente, Object> estadoExpedientedao = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			estadoExpedientedao.modificar(estadoExpediente);
			logger.debug("actualizo el estadoExpediente exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"el Estado Expediente:"+e);
		}
	}

	public void limpiarEstExpe(ActionEvent e) {
		setNombreEstExpe("");

	}

	
	public void buscarEtapa(ActionEvent e) {

		logger.debug("entro al buscar etapa");

		GenericDao<Etapa, Object> estadoExpedienteDAO = 
				(GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Etapa.class);

		if (getNombreEtapa().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEtapa() + " etapa - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEtapa() + "%").ignoreCase());
		}

		try {
			etapas = estadoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar las etapas");
		}

		logger.debug("trajo .." + etapas.size());

	}
	
	public void agregarEtapa(ActionEvent e) {

		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Etapa.class);
		Busqueda filtro2 = Busqueda.forClass(Etapa.class);
		
		List<Etapa> etapas_ = new ArrayList<Etapa>();
		

		if ( getNombreEtapa().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEtapa()).ignoreCase());
				etapas_ = etapaDAO.buscarDinamico(filtro);

				if (etapas_.size() == 0) {
					
					Etapa etapa = new Etapa();
					etapa.setNombre(getNombreEtapa());
					etapa.setEstado('A');

					try {
						etapaDAO.insertar(etapa);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la etapa"));
						logger.debug("guardo la etapa exitosamente");
						
						etapas = etapaDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la etapa"));
						logger.debug("no guardo la etapa por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Etapa Existente", "Etapa Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
				
		

	}
	
	public void editEtapa(RowEditEvent event) {

		Etapa etapa = ((Etapa) event.getObject());
		logger.debug("modificando etapa " + etapa.getNombre());
		
		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			etapaDAO.modificar(etapa);
			logger.debug("actualizo la etapa exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Etapa:"+e);
		}
	}
	
	public void limpiarEtapa(ActionEvent e) {
		setNombreEtapa("");
		
		//etapas = new ArrayList<Etapa>();
	}
	
	public void buscarEntidad(ActionEvent e) {

		logger.debug("entro al buscar entidad");

		GenericDao<Entidad, Object> entidadDAO = 
				(GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Entidad.class);

		if (getNombreEntidad().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEntidad() + " entidad - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEntidad() + "%").ignoreCase());
		}

		try {
			entidads = entidadDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar las entidades");
		}

		logger.debug("trajo .." + entidads.size());

	}
	
	
	public void agregarEntidad(ActionEvent e) {

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Entidad.class);
		Busqueda filtro2 = Busqueda.forClass(Entidad.class);
		
		List<Entidad> entidads_ = new ArrayList<Entidad>();
		

		if ( getNombreEntidad().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEntidad()).ignoreCase());

				entidads_ = entidadDAO.buscarDinamico(filtro);

				if (entidads_.size() == 0) {
					
					Entidad entidad = new Entidad();
					entidad.setNombre(getNombreEntidad());
					entidad.setEstado('A');

					try {
						entidadDAO.insertar(entidad);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso:Agreg� la entidad",
										""));
						logger.debug("guardo la entidad exitosamente");
						
						entidads = entidadDAO.buscarDinamico(filtro2);
						
						setNombreEntidad("");

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la entidad"));
						logger.debug("no guardo la entidad por " + ex.getMessage());
					}
					
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Entidad Existente", "Entidad Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}	

	}
	
	public void editEntidad(RowEditEvent event) {

		Entidad entidad = ((Entidad) event.getObject());
		logger.debug("modificando entidad " + entidad.getNombre());
		
		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			entidadDAO.modificar(entidad);
			logger.debug("actualizo la entidad exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Entidad:"+e);
		}
	}

	public void limpiarEntidad(ActionEvent e) {
		setNombreEntidad("");
		//entidads= new ArrayList<Entidad>();
	}
	
	public void buscarRecurrencia(ActionEvent e) {

		logger.debug("entro al buscar recurrencia");

		GenericDao<Recurrencia, Object> recurrenciaDAO = 
				(GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Recurrencia.class);

		if (getNombreRecurrencia().compareTo("") != 0) {

			logger.debug("filtro " + getNombreRecurrencia() + " recurrencia - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreRecurrencia() + "%").ignoreCase());
		}

		try {
			recurrencias = recurrenciaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar la recurrencias");
		}

		logger.debug("trajo .." + recurrencias.size());

	}

	public void agregarRecurrencia(ActionEvent e) {

		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Recurrencia.class);
		Busqueda filtro2 = Busqueda.forClass(Recurrencia.class);
		
		List<Recurrencia> recurrencias_ = new ArrayList<Recurrencia>();
		

		if ( getNombreRecurrencia().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreRecurrencia()).ignoreCase());

				recurrencias_ = recurrenciaDAO.buscarDinamico(filtro);

				if (recurrencias_.size() == 0) {
					
				
					Recurrencia recurrencia = new Recurrencia();
					recurrencia.setNombre(getNombreRecurrencia());
					recurrencia.setEstado('A');

					try {
						recurrenciaDAO.insertar(recurrencia);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la recurrencia"));
						logger.debug("guardo la recurrencia exitosamente");
						
						recurrencias =  recurrenciaDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la recurrencia"));
						logger.debug("no guardo la recurrencia por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Recurrencia Existente", "Recurrencia Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarRecurrencia(ActionEvent e) {
		setNombreRecurrencia("");
		recurrencias = new ArrayList<Recurrencia>();
	}
	

	public void editRecurrencia(RowEditEvent event) {

		Recurrencia recurrencia = ((Recurrencia) event.getObject());
		logger.debug("modificando recurrencia " + recurrencia.getNombre());
		
		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			recurrenciaDAO.modificar(recurrencia);
			logger.debug("actualizo la recurrencia exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Recurrencia:"+e);
		}
	}
	
	public void buscarSitActPro(ActionEvent e) {

		logger.debug("entro al buscar sit act pro");

		GenericDao<SituacionActProc, Object> situacionActProcDAO = 
				(GenericDao<SituacionActProc, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionActProc.class);

		if (getNombreSitActPro().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitActPro() + " sit act - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreSitActPro() + "%").ignoreCase());
		}

		try {
			situacionActProcs = situacionActProcDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar registros para las situaciones de actividades procesales");
		}

		logger.debug("trajo .." + situacionActProcs.size());

	}
	
	public void agregarSitActPro(ActionEvent e) {

		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionActProc.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionActProc.class);
		
		List<SituacionActProc> situacionActProcs_ = new ArrayList<SituacionActProc>();
		

		if ( getNombreSitActPro().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Sit Act Pro", "Datos Requeridos: Sit Act Pro");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreSitActPro()).ignoreCase());

				situacionActProcs_ = situacionActProcDAO.buscarDinamico(filtro);

				if (situacionActProcs_.size() == 0) {
					
					SituacionActProc situacionActProc = new SituacionActProc();
					situacionActProc.setNombre(getNombreSitActPro());
					situacionActProc.setEstado('A');
					
					try {
						situacionActProcDAO.insertar(situacionActProc);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac act pro"));
						logger.debug("guardo la situac act pro exitosamente");
						
						situacionActProcs = situacionActProcDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac act pro"));
						logger.debug("no guardo la situac act pro por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Act Pro Existente", "Sit Act Pro Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarSitActPro(ActionEvent e) {
		setNombreSitActPro("");
		//situacionActProcs = new ArrayList<SituacionActProc>();
	}
	

	public void editSitActPro(RowEditEvent event) {

		SituacionActProc situacionActProc = ((SituacionActProc) event.getObject());
		logger.debug("modificando situacionActProc " + situacionActProc.getNombre());
		
		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionActProcDAO.modificar(situacionActProc);
			logger.debug("actualizo la situacionActProc exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la SituacionActProcesal:"+e);
		}
	}
	
	
	public void buscarSitCuota(ActionEvent e) {

		logger.debug("entro al buscar sit cuota");

		GenericDao<SituacionCuota, Object> situacionCuotaDAO = 
				(GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionCuota.class);

		if (getNombreSitCuota().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitCuota() + " sit cuota - nombre");
			filtro.add(Restrictions.like("descripcion","%" + getNombreSitCuota() + "%").ignoreCase());
		}

		try {
			situacionCuotas = situacionCuotaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar registros de la situacion de las cuotas");
		}

		logger.debug("trajo .." + situacionCuotas.size());

	}
	
	public void agregarSitCuota(ActionEvent e) {

		GenericDao<SituacionCuota, Object> situacionCuotaDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionCuota.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionCuota.class);
		
		List<SituacionCuota> situacionCuotas_ = new ArrayList<SituacionCuota>();
		

		if ( getNombreSitCuota().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreSitCuota()).ignoreCase());

				situacionCuotas_ = situacionCuotaDAO.buscarDinamico(filtro);

				if (situacionCuotas_.size() == 0) {
					
					SituacionCuota situacionCuota = new SituacionCuota();
					situacionCuota.setDescripcion(getNombreSitCuota());
					situacionCuota.setEstado('A');

					try {
						situacionCuotaDAO.insertar(situacionCuota);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac cuota"));
						logger.debug("guardo la situac cuota exitosamente");
						
						situacionCuotas = situacionCuotaDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac cuota"));
						logger.debug("no guardo la situac cuota por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Cuota Existente", "Sit Cuota Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
				
		

	}

	public void limpiarSitCuota(ActionEvent e) {
		setNombreSitCuota("");
		//situacionCuotas = new ArrayList<SituacionCuota>();
	}

	public void editSitCuota(RowEditEvent event) {

		SituacionCuota situacionCuota = ((SituacionCuota) event.getObject());
		logger.debug("modificando situacionCuota " + situacionCuota.getDescripcion());
		
		GenericDao<SituacionCuota, Object> situacionCuotaDAO = (GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionCuotaDAO.modificar(situacionCuota);
			logger.debug("actualizo la situacionCuota exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la Situacion Cuota:"+e);
		}
	}

	
	public void buscarSitHonor(ActionEvent e) {

		logger.debug("entro al buscar sit honor");

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = 
				(GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionHonorario.class);

		if (getNombreSitHonor().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitHonor() + " sit honor - nombre");
			filtro.add(Restrictions.like("descripcion","%" + getNombreSitHonor() + "%").ignoreCase());
		}

		try {
			situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar la situacion de honorarios");
		}

		logger.debug("trajo .." + situacionHonorarios.size());

	}
	
	public void agregarSitHonor(ActionEvent e) {

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionHonorario.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionHonorario.class);
		
		List<SituacionHonorario> situacionHonorarios_ = new ArrayList<SituacionHonorario>();
		

		if ( getNombreSitHonor().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreSitHonor()).ignoreCase());

				situacionHonorarios_ = situacionHonorarioDAO.buscarDinamico(filtro);

				if (situacionHonorarios_.size() == 0) {
					
				
					SituacionHonorario situacionHonorario = new SituacionHonorario();
					situacionHonorario.setDescripcion(getNombreSitHonor());
					situacionHonorario.setEstado('A');

					try {
						situacionHonorarioDAO.insertar(situacionHonorario);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac honor"));
						logger.debug("guardo la situac honor exitosamente");
						
						situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac honor"));
						logger.debug("no guardo la situac honor por " + ex.getMessage());
					}
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Honor Existente", "Sit Honor Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		

	}

	public void limpiarSitHonor(ActionEvent e) {
		setNombreSitHonor("");
		//situacionHonorarios = new ArrayList<SituacionHonorario>();
	}
	
	public void editSitHonor(RowEditEvent event) {

		SituacionHonorario situacionHonorario = ((SituacionHonorario) event.getObject());
		logger.debug("modificando situacionHonorario " + situacionHonorario.getDescripcion());
		
		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionHonorarioDAO.modificar(situacionHonorario);
			logger.debug("actualizo la situacionHonorario exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la situacionHonor:"+e);
		}
	}
	
	public void buscarSitIncul(ActionEvent e) {

		logger.debug("entro al buscar sit inc");

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = 
				(GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionInculpado.class);

		if (getNombreSitIncul().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitIncul() + " sit inc - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreSitIncul() + "%").ignoreCase());
		}

		try {
			situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar la situacion de inculpados");
		}

		logger.debug("trajo .." + situacionInculpados.size());

	}
	
	public void agregarSitIncul(ActionEvent e) {

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionInculpado.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionInculpado.class);
		
		List<SituacionInculpado> situacionInculpados_ = new ArrayList<SituacionInculpado>();
		

		if ( getNombreSitIncul().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreSitIncul()).ignoreCase());

				situacionInculpados_ = situacionInculpadoDAO.buscarDinamico(filtro);

				if (situacionInculpados_.size() == 0) {
			
					SituacionInculpado situacionInculpado = new SituacionInculpado();
					situacionInculpado.setNombre(getNombreSitIncul());
					situacionInculpado.setEstado('A');

					try {
						situacionInculpadoDAO.insertar(situacionInculpado);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac inculp"));
						logger.debug("guardo la situac inculp exitosamente");
						
						situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac inculp"));
						logger.debug("no guardo la situac inculp por " + ex.getMessage());
					}
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Inc Existente", "Sit Inc Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarSitIncul(ActionEvent e) {
		setNombreSitIncul("");
		
		//situacionInculpados = new ArrayList<SituacionInculpado>();
		
	}
	
	public void editSitIncul(RowEditEvent event) {

		SituacionInculpado situacionInculpado = ((SituacionInculpado) event.getObject());
		logger.debug("modificando situacionInculpado " + situacionInculpado.getNombre());
		
		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionInculpadoDAO.modificar(situacionInculpado);
			logger.debug("actualizo la situacionInculpado exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"la situacion Inculpado:"+e);
		}
	}
	
	public void buscarTipoCaut(ActionEvent e) {

		logger.debug("entro al buscar TipoCaut");

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = 
				(GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoCautelar.class);

		if (getNombreTipoCaut().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoCaut() + " TipoCaut - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreTipoCaut() + "%").ignoreCase());
		}

		try {
			tipoCautelars = tipoCautelarDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar registros de los tipos de medida cautelar");
		}

		logger.debug("trajo .." + tipoCautelars.size());

	}

	public void agregarTipoCaut(ActionEvent e) {

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoCautelar.class);
		Busqueda filtro2 = Busqueda.forClass(TipoCautelar.class);
		
		List<TipoCautelar> tipoCautelars_ = new ArrayList<TipoCautelar>();
		

		if ( getNombreTipoCaut().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreTipoCaut()).ignoreCase());

				tipoCautelars_ = tipoCautelarDAO.buscarDinamico(filtro);

				if (tipoCautelars_.size() == 0) {
					
					TipoCautelar tipoCautelar = new TipoCautelar();
					tipoCautelar.setDescripcion(getNombreTipoCaut());
					tipoCautelar.setEstado('A');
					try {
						tipoCautelarDAO.insertar(tipoCautelar);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo cautelar"));
						logger.debug("guardo el tipo cautelar exitosamente");
						
						tipoCautelars = tipoCautelarDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo cautelar"));
						logger.debug("no guardo el tipo cautelar por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Caut Existente", "Tip Caut Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
		

	}

	public void limpiarTipoCaut(ActionEvent e) {
		setNombreTipoCaut("");
		tipoCautelars = new ArrayList<TipoCautelar>();
	}
	
	public void editTipCaut(RowEditEvent event) {

		TipoCautelar tipoCautelar = ((TipoCautelar) event.getObject());
		logger.debug("modificando tipoCautelar " + tipoCautelar.getDescripcion());
		
		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoCautelarDAO.modificar(tipoCautelar);
			logger.debug("actualizo el tipoCautelar exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"tipo Cautelar:"+e);
		}
	}

	public void buscarTipoExpe(ActionEvent e) {

		logger.debug("entro al buscar tip expe");

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = 
				(GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoExpediente.class);

		if (getNombreTipoExpe().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoExpe() + " TipoExpe - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreTipoExpe() + "%").ignoreCase());
		}

		try {
			tipoExpedientes = tipoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar los tipos de expedientes");
		}

		logger.debug("trajo .." + tipoExpedientes.size());

	}
	
	public void agregarTipoExpe(ActionEvent e) {

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoExpediente.class);
		Busqueda filtro2 = Busqueda.forClass(TipoExpediente.class);
		
		List<TipoExpediente> tipoExpedientes_ = new ArrayList<TipoExpediente>();
		

		if ( getNombreTipoExpe().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreTipoExpe()).ignoreCase());

				tipoExpedientes_ = tipoExpedienteDAO.buscarDinamico(filtro);

				if (tipoExpedientes_.size() == 0) {
					
					TipoExpediente tipoExpediente = new TipoExpediente();
					tipoExpediente.setNombre(getNombreTipoExpe());
					tipoExpediente.setEstado('A');

					try {
						tipoExpedienteDAO.insertar(tipoExpediente);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo expediente"));
						logger.debug("guardo el tipo expediente exitosamente");
					
						tipoExpedientes = tipoExpedienteDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo expediente"));
						logger.debug("no guardo el tipo expediente por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Expe Existente", "Tip Expe Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
				
		

	}

	public void limpiarTipoExpe(ActionEvent e) {
		setNombreTipoExpe("");
		tipoExpedientes = new ArrayList<TipoExpediente>();
	}
	
	public void editTipoExpe(RowEditEvent event) {

		TipoExpediente tipoExpediente = ((TipoExpediente) event.getObject());
		logger.debug("modificando tipoExpediente " + tipoExpediente.getNombre());
		
		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoExpedienteDAO.modificar(tipoExpediente);
			logger.debug("actualizo el tipoExpediente exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"tipo Exp:"+e);
		}
	}
	public void buscarTipoHonor(ActionEvent e) {

		logger.debug("entro al buscar TipoHonor");

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = 
				(GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoHonorario.class);

		if (getNombreTipoHonor().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoHonor() + " TipoHonor - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreTipoHonor() + "%").ignoreCase());
		}

		try {
			tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar tipos de honorarios");
		}

		logger.debug("trajo .." + tipoHonorarios.size());

	}

	
	public void agregarTipoHonor(ActionEvent e) {

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoHonorario.class);
		Busqueda filtro2 = Busqueda.forClass(TipoHonorario.class);
		
		List<TipoHonorario> tipoHonorarios_ = new ArrayList<TipoHonorario>();
		

		if ( getNombreTipoHonor().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreTipoHonor()).ignoreCase());

				tipoHonorarios_ = tipoHonorarioDAO.buscarDinamico(filtro);

				if (tipoHonorarios_.size() == 0) {
					
					TipoHonorario tipoHonorario = new TipoHonorario();
					tipoHonorario.setDescripcion(getNombreTipoHonor());
					tipoHonorario.setEstado('A');

					try {
						tipoHonorarioDAO.insertar(tipoHonorario);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo honorario"));
						logger.debug("guardo el tipo honorario exitosamente");
						
						tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo honorario"));
						logger.debug("no guardo el tipo honorario por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Honor Existente", "Tip Honor Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarTipoHonor(ActionEvent e) {

		setNombreTipoHonor("");
		tipoHonorarios = new ArrayList<TipoHonorario>();
	}
	
	public void editTipoHonor(RowEditEvent event) {

		TipoHonorario tipoHonorario = ((TipoHonorario) event.getObject());
		logger.debug("modificando tipoHonorario " + tipoHonorario.getDescripcion());
		
		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoHonorarioDAO.modificar(tipoHonorario);
			logger.debug("actualizo el tipoHonorario exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"tipo Honor:"+e);
		}
	}
	public void buscarTipInv(ActionEvent e) {

		logger.debug("==== buscarTipInv() =====");

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = 
				(GenericDao<TipoInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoInvolucrado.class);

		if (getNombreTipoInv().compareTo("") != 0) {
			logger.debug("[BUSQ_TIPINVOLUCR]-NombreTipInv:" + getNombreTipoInv());
			filtro.add(Restrictions.like("nombre","%" + getNombreTipoInv() + "%").ignoreCase());
		}

		try {
			tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtro);
			if(tipoInvolucrados!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"tipoInvolucrados es:["+tipoInvolucrados.size()+"].");
			}
		} catch (Exception e2) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"los tipos de involucrados:"+e2);
		}

		logger.debug("=== saliendo de buscarTipInv() ====");

	}

	public void agregarTipoInv(ActionEvent e) {

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoInvolucrado.class);
		Busqueda filtro2 = Busqueda.forClass(TipoInvolucrado.class);
		
		List<TipoInvolucrado> tipoInvolucrados_ = new ArrayList<TipoInvolucrado>();
		

		if ( getNombreTipoInv().compareTo("") == 0 ) {			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreTipoInv()).ignoreCase());

				tipoInvolucrados_ = tipoInvolucradoDAO.buscarDinamico(filtro);

				if (tipoInvolucrados_.size() == 0) {
					
					TipoInvolucrado tipoInvolucrado = new TipoInvolucrado();
					tipoInvolucrado.setNombre(getNombreTipoInv());
					tipoInvolucrado.setEstado('A');
					try {
						tipoInvolucradoDAO.insertar(tipoInvolucrado);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo honorario"));
						logger.debug("guardo el tipo honorario exitosamente");
						
						tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo honorario"));
						logger.debug("no guardo el tipo honorario por " + ex.getMessage());
					}

					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tipo Involucrado Existente", "Tipo Involucrado Existente"));
				}
				
			} catch (Exception ex) {
				logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al agregar TipoInvolucrado: "+ex);
			}
				
		}		

	
	}

	public void limpiarTipoInv(ActionEvent e) {
		setNombreTipoInv("");
		//tipoInvolucrados = new ArrayList<TipoInvolucrado>();
	}
	
	public void editTipInv(RowEditEvent event) {

		TipoInvolucrado tipoInvolucrado = ((TipoInvolucrado) event.getObject());
		logger.debug("modificando tipoInvolucrado " + tipoInvolucrado.getNombre());
		
		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoInvolucradoDAO.modificar(tipoInvolucrado);
			logger.debug("actualizo el tipoInvolucrado exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"tipo Involucrado:"+e);
		}
	}
	
	public void buscarTipoPro(ActionEvent e) {

		logger.debug("entro al buscar TipPro");

		GenericDao<TipoProvision, Object> tipoProvisionDAO = 
				(GenericDao<TipoProvision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoProvision.class);

		if (getNombreTipoPro().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoPro() + " TipoPro - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreTipoPro() + "%").ignoreCase());
		}

		try { 
			tipoProvisions = tipoProvisionDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al buscar los tipos de provisiones");
		}

		logger.debug("trajo .." + tipoProvisions.size());

	}
	
	public void agregarTipoPro(ActionEvent e) {

		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoProvision.class);
		Busqueda filtro2 = Busqueda.forClass(TipoProvision.class);
		
		List<TipoProvision> tipoProvisions_ = new ArrayList<TipoProvision>();
		

		if ( getNombreTipoPro().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreTipoPro()).ignoreCase());

				tipoProvisions_ = tipoProvisionDAO.buscarDinamico(filtro);

				if (tipoProvisions_.size() == 0) {
					
					TipoProvision tipoProvision = new TipoProvision();
					tipoProvision.setDescripcion(getNombreTipoPro());
					tipoProvision.setEstado('A');
					
					try {
						tipoProvisionDAO.insertar(tipoProvision);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo provision"));
						logger.debug("guardo el tipo provision exitosamente");
						
						tipoProvisions = tipoProvisionDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo provision"));
						logger.debug("no guardo el tipo provision por " + ex.getMessage());
					}

				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Prov Existente", "Tip Prov Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		
	}

	public void limpiarTipoPro(ActionEvent e) {
		setNombreTipoPro("");
		//tipoProvisions = new ArrayList<TipoProvision>();
	}
	
	public void editTipPro(RowEditEvent event) {

		TipoProvision tipoProvision = ((TipoProvision) event.getObject());
		logger.debug("modificando tipoInvolucrado " + tipoProvision.getDescripcion());
		
		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoProvisionDAO.modificar(tipoProvision);
			logger.debug("actualizo el tipoProvision exitosamente");
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_ACTUALIZ+"tipo Provision:"+e);
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

	public int getIdProcesoEstado() {
		return idProcesoEstado;
	}

	public void setIdProcesoEstado(int idProcesoEstado) {
		this.idProcesoEstado = idProcesoEstado;
	}

	public List<Via> getVias() {
		return vias;
	}

	public void setVias(List<Via> vias) {
		this.vias = vias;
	}

	public List<Instancia> getInstancias() {
		return instancias;
	}

	public void setInstancias(List<Instancia> instancias) {
		this.instancias = instancias;
	}

	public List<Actividad> getActividads() {
		return actividads;
	}

	public void setActividads(List<Actividad> actividads) {
		this.actividads = actividads;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}
	
	public List<Estudio> getEstudios() {
		return estudios;
	}

	public void setEstudios(List<Estudio> estudios) {
		this.estudios = estudios;
	}

	public List<EstadoCautelar> getEstadosCautelars() {
		return estadosCautelars;
	}

	public void setEstadosCautelars(List<EstadoCautelar> estadosCautelars) {
		this.estadosCautelars = estadosCautelars;
	}

	public List<EstadoExpediente> getEstadoExpedientes() {
		return estadoExpedientes;
	}

	public void setEstadoExpedientes(List<EstadoExpediente> estadoExpedientes) {
		this.estadoExpedientes = estadoExpedientes;
	}

	public List<Etapa> getEtapas() {
		return etapas;
	}

	public void setEtapas(List<Etapa> etapas) {
		this.etapas = etapas;
	}

	public List<Entidad> getEntidads() {
		return entidads;
	}

	public void setEntidads(List<Entidad> entidads) {
		this.entidads = entidads;
	}

	public List<FormaConclusion> getFormaConclusions() {
		return formaConclusions;
	}

	public void setFormaConclusions(List<FormaConclusion> formaConclusions) {
		this.formaConclusions = formaConclusions;
	}

	public List<Recurrencia> getRecurrencias() {
		return recurrencias;
	}

	public void setRecurrencias(List<Recurrencia> recurrencias) {
		this.recurrencias = recurrencias;
	}

	public List<SituacionActProc> getSituacionActProcs() {
		return situacionActProcs;
	}

	public void setSituacionActProcs(List<SituacionActProc> situacionActProcs) {
		this.situacionActProcs = situacionActProcs;
	}
	
	public List<SituacionCuota> getSituacionCuotas() {
		return situacionCuotas;
	}

	public void setSituacionCuotas(List<SituacionCuota> situacionCuotas) {
		this.situacionCuotas = situacionCuotas;
	}

	public List<SituacionHonorario> getSituacionHonorarios() {
		return situacionHonorarios;
	}

	public void setSituacionHonorarios(List<SituacionHonorario> situacionHonorarios) {
		this.situacionHonorarios = situacionHonorarios;
	}

	public List<SituacionInculpado> getSituacionInculpados() {
		return situacionInculpados;
	}

	public void setSituacionInculpados(List<SituacionInculpado> situacionInculpados) {
		this.situacionInculpados = situacionInculpados;
	}

	public List<TipoCautelar> getTipoCautelars() {
		return tipoCautelars;
	}

	public void setTipoCautelars(List<TipoCautelar> tipoCautelars) {
		this.tipoCautelars = tipoCautelars;
	}

	public List<TipoExpediente> getTipoExpedientes() {
		return tipoExpedientes;
	}

	public void setTipoExpedientes(List<TipoExpediente> tipoExpedientes) {
		this.tipoExpedientes = tipoExpedientes;
	}

	public List<TipoHonorario> getTipoHonorarios() {
		return tipoHonorarios;
	}

	public void setTipoHonorarios(List<TipoHonorario> tipoHonorarios) {
		this.tipoHonorarios = tipoHonorarios;
	}

	public List<TipoInvolucrado> getTipoInvolucrados() {
		return tipoInvolucrados;
	}

	public void setTipoInvolucrados(List<TipoInvolucrado> tipoInvolucrados) {
		this.tipoInvolucrados = tipoInvolucrados;
	}

	public List<TipoProvision> getTipoProvisions() {
		return tipoProvisions;
	}

	public void setTipoProvisions(List<TipoProvision> tipoProvisions) {
		this.tipoProvisions = tipoProvisions;
	}

	public List<RolInvolucrado> getRolInvolucrados() {
		return rolInvolucrados;
	}

	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
	}

	public List<Proceso> getProcesos2() {
		return procesos2;
	}

	public void setProcesos2(List<Proceso> procesos2) {
		this.procesos2 = procesos2;
	}

	public Long getRucEstudio() {
		return rucEstudio;
	}

	public void setRucEstudio(Long rucEstudio) {
		this.rucEstudio = rucEstudio;
	}

	public Aviso getObjAviso() {
		return objAviso;
	}

	public void setObjAviso(Aviso objAviso) {
		this.objAviso = objAviso;
	}

	public Character getIndFeriado() {
		return indFeriado;
	}

	public void setIndFeriado(Character indFeriado) {
		this.indFeriado = indFeriado;
	}

	public List<Aviso> getLstAviso() {
		return lstAviso;
	}

	public void setLstAviso(List<Aviso> lstAviso) {
		this.lstAviso = lstAviso;
	}

	public List<Materia> getLstMateria() {
		return lstMateria;
	}

	public void setLstMateria(List<Materia> lstMateria) {
		this.lstMateria = lstMateria;
	}

	public List<Riesgo> getLstRiesgo() {
		return lstRiesgo;
	}

	public void setLstRiesgo(List<Riesgo> lstRiesgo) {
		this.lstRiesgo = lstRiesgo;
	}

	public List<TipoDocumento> getLstTipoDoc() {
		return lstTipoDoc;
	}

	public void setLstTipoDoc(List<TipoDocumento> lstTipoDoc) {
		this.lstTipoDoc = lstTipoDoc;
	}

	public List<Calificacion> getLstCalificacion() {
		return lstCalificacion;
	}

	public void setLstCalificacion(List<Calificacion> lstCalificacion) {
		this.lstCalificacion = lstCalificacion;
	}

	public List<Oficina> getLstOficina() {
		return lstOficina;
	}

	public void setLstOficina(List<Oficina> lstOficina) {
		this.lstOficina = lstOficina;
	}

	public String getIdUbigeoLst() {
		return idUbigeoLst;
	}

	public void setIdUbigeoLst(String idUbigeoLst) {
		this.idUbigeoLst = idUbigeoLst;
	}

	public List<Feriado> getLstFeriado() {
		return lstFeriado;
	}

	public void setLstFeriado(List<Feriado> lstFeriado) {
		this.lstFeriado = lstFeriado;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public List<Rol> getRols() {
		return rols;
	}

	public void setRols(List<Rol> rols) {
		this.rols = rols;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApPatUsuario() {
		return apPatUsuario;
	}

	public void setApPatUsuario(String apPatUsuario) {
		this.apPatUsuario = apPatUsuario;
	}

	public String getApMatUsuario() {
		return apMatUsuario;
	}

	public void setApMatUsuario(String apMatUsuario) {
		this.apMatUsuario = apMatUsuario;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<String> getRolsString() {
		return rolsString;
	}

	public void setRolsString(List<String> rolsString) {
		this.rolsString = rolsString;
	}

	public List<String> getProcesosString() {
		return procesosString;
	}

	public void setProcesosString(List<String> procesosString) {
		this.procesosString = procesosString;
	}

	public int getIdViasLst() {
		return idViasLst;
	}

	public void setIdViasLst(int idViasLst) {
		this.idViasLst = idViasLst;
	}

	public List<Via> getLstViasNuevo() {
		return lstViasNuevo;
	}

	public void setLstViasNuevo(List<Via> lstViasNuevo) {
		this.lstViasNuevo = lstViasNuevo;
	}

	public int getIdActividadLst() {
		return idActividadLst;
	}

	public void setIdActividadLst(int idActividadLst) {
		this.idActividadLst = idActividadLst;
	}

	public char[] getEstados() {
		return estados;
	}

	public void setEstados(char[] estados) {
		this.estados = estados;
	}

	public int getIdProceso2() {
		return idProceso2;
	}

	public void setIdProceso2(int idProceso2) {
		this.idProceso2 = idProceso2;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public List<Rol> getRols2() {
		return rols2;
	}

	public void setRols2(List<Rol> rols2) {
		this.rols2 = rols2;
	}

	public int getIdVias() {
		return idVias;
	}

	public void setIdVias(int idVias) {
		this.idVias = idVias;
	}

	public Character getIndEscenario() {
		return indEscenario;
	}

	public void setIndEscenario(Character indEscenario) {
		this.indEscenario = indEscenario;
	}

	public String getNombreFeriado() {
		return nombreFeriado;
	}

	public void setNombreFeriado(String nombreFeriado) {
		this.nombreFeriado = nombreFeriado;
	}

	public Date getFechaInLine() {
		return fechaInLine;
	}

	public void setFechaInLine(Date fechaInLine) {
		this.fechaInLine = fechaInLine;
	}

	public boolean isFlagMostrarOrg() {
		return flagMostrarOrg;
	}

	public void setFlagMostrarOrg(boolean flagMostrarOrg) {
		this.flagMostrarOrg = flagMostrarOrg;
	}

	public boolean isFlagMostrarCal() {
		return flagMostrarCal;
	}

	public void setFlagMostrarCal(boolean flagMostrarCal) {
		this.flagMostrarCal = flagMostrarCal;
	}

	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}

	public int getIdEstadoSelected() {
		return idEstadoSelected;
	}

	public void setIdEstadoSelected(int idEstadoSelected) {
		this.idEstadoSelected = idEstadoSelected;
	}

	public String getNroExpeOficial() {
		return nroExpeOficial;
	}

	public void setNroExpeOficial(String nroExpeOficial) {
		this.nroExpeOficial = nroExpeOficial;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}
	
	public ExpedienteDataModel getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(ExpedienteDataModel expedientes) {
		this.expedientes = expedientes;
	}

	public Usuario getNuevoResponsable() {
		return nuevoResponsable;
	}

	public void setNuevoResponsable(Usuario nuevoResponsable) {
		this.nuevoResponsable = nuevoResponsable;
	}

	public boolean isFlagDeshUbigeos() {
		return flagDeshUbigeos;
	}

	public void setFlagDeshUbigeos(boolean flagDeshUbigeos) {
		this.flagDeshUbigeos = flagDeshUbigeos;
	}

	public int isTabActivado() {
		return tabActivado;
	}

	public void setTabActivado(int tabActivado) {
		this.tabActivado = tabActivado;
	}

	public int getTabActivado() {
		return tabActivado;
	}

	public String getNombreFeriadoOrg() {
		return nombreFeriadoOrg;
	}

	public void setNombreFeriadoOrg(String nombreFeriadoOrg) {
		this.nombreFeriadoOrg = nombreFeriadoOrg;
	}
}
