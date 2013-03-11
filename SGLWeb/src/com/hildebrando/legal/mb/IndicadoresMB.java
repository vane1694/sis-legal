package com.hildebrando.legal.mb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.AbogadoEstudio;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.ActividadProcesal;
import com.hildebrando.legal.modelo.Anexo;
import com.hildebrando.legal.modelo.BusquedaActProcesal;
import com.hildebrando.legal.modelo.Cuantia;
import com.hildebrando.legal.modelo.Cuota;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Etapa;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.ExpedienteVista;
import com.hildebrando.legal.modelo.Honorario;
import com.hildebrando.legal.modelo.Inculpado;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Provision;
import com.hildebrando.legal.modelo.Resumen;
import com.hildebrando.legal.modelo.Rol;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.SituacionActProc;
import com.hildebrando.legal.modelo.SituacionCuota;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.TipoInvolucrado;
import com.hildebrando.legal.modelo.TipoProvision;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.view.BusquedaActividadProcesalDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.InvolucradoDataModel;

@ManagedBean(name = "indicadoresReg")
@SessionScoped
public class IndicadoresMB {

	public static Logger logger = Logger.getLogger(IndicadoresMB.class);

	private BusquedaActProcesal busquedaProcesal;
	private BusquedaActProcesal busquedaProcesal2;
	private BusquedaActividadProcesalDataModel resultadoBusqueda;
	private Involucrado demandante;
	private String busNroExpe;
	private Organo organo;
	private Usuario responsable;
	private String idPrioridad;
	private List<Instancia> instanciasProximas;
	private boolean tabCaucion;
	private List<ExpedienteVista> expedienteVistas;
	private ExpedienteVista expedienteVista;
	private String estadoExpediente;
	private String nombreProceso;
	private String nombreVia;
	private String tipoExpediente;
	private String calificacionExpediente;
	private String descMoneda;
	private String tipoMedidaCautelar;
	private String contraCautela;
	private String estadoCautelar;
	private Boolean mostrarListaResp;
	private Boolean mostrarControles;
	private String descripcionRiesgo;
	
	private Date fechaActualDate;
	private String observacion = "";
	
	

	public Boolean getMostrarListaResp() {
		return mostrarListaResp;
	}

	public void setMostrarListaResp(Boolean mostrarListaResp) {
		this.mostrarListaResp = mostrarListaResp;
	}

	public BusquedaActProcesal getBusquedaProcesal() {
		return busquedaProcesal;
	}

	public void setBusquedaProcesal(BusquedaActProcesal busquedaProcesal) {
		
		try {

			logger.debug("id Expediente: " + busquedaProcesal.getId_expediente());

			ExpedienteVista expedienteVistaNuevo = new ExpedienteVista();
			expedienteVistaNuevo.setFlagDeshabilitadoGeneral(true);

			GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Expediente.class);
			
			Expediente expediente = new Expediente();
			
			try {
				expediente = expedienteDAO.buscarById(Expediente.class, busquedaProcesal.getId_expediente());
			} catch (Exception e2) {
				logger.debug("Error al obtener los datos de expediente");
			}
			
			if(expediente != null){
				
				logger.debug("--------------------------------------------------");
				logger.debug("Datos a mostrar");
				logger.debug("IdExpediente: " + expediente.getIdExpediente());
				logger.debug("Expediente:" + expediente.getNumeroExpediente());
				logger.debug("Instancia: " + expediente.getInstancia().getNombre());
				logger.debug("--------------------------------------------------");
				actualizarDatosPagina(expedienteVistaNuevo, expediente);
				setExpedienteVista(expedienteVistaNuevo);
				
			}else{
				
				FacesContext context = FacesContext.getCurrentInstance(); 
		        context.addMessage(null, new FacesMessage("Info", "Seleccione una opcion!"));
			}
			
		} catch (Exception ee) {
			//e.printStackTrace();
			logger.debug("Error al obtener los datos de expediente");
			FacesContext context = FacesContext.getCurrentInstance(); 
	        context.addMessage(null, new FacesMessage("Info", "Seleccione una opcion!"));
		}
		
		this.busquedaProcesal = busquedaProcesal;
	}

	public BusquedaActividadProcesalDataModel getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	public void setResultadoBusqueda(
			BusquedaActividadProcesalDataModel resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}

	public Involucrado getDemandante() {
		return demandante;
	}

	public void setDemandante(Involucrado demandante) {
		this.demandante = demandante;
	}

	public String getBusNroExpe() {
		return busNroExpe;
	}

	public void setBusNroExpe(String busNroExpe) {
		this.busNroExpe = busNroExpe;
	}

	public String getIdPrioridad() {
		return idPrioridad;
	}

	public void setIdPrioridad(String idPrioridad) {
		this.idPrioridad = idPrioridad;
	}

	public List<Instancia> getInstanciasProximas() {
		return instanciasProximas;
	}

	public void setInstanciasProximas(List<Instancia> instanciasProximas) {
		this.instanciasProximas = instanciasProximas;
	}

	public boolean isTabCaucion() {
		return tabCaucion;
	}

	public void setTabCaucion(boolean tabCaucion) {
		this.tabCaucion = tabCaucion;
	}

	public List<ExpedienteVista> getExpedienteVistas() {
		return expedienteVistas;
	}

	public void setExpedienteVistas(List<ExpedienteVista> expedienteVistas) {
		this.expedienteVistas = expedienteVistas;
	}

	public ExpedienteVista getExpedienteVista() {
		return expedienteVista;
	}

	public void setExpedienteVista(ExpedienteVista expedienteVista) {
		this.expedienteVista = expedienteVista;
	}

	public String getEstadoExpediente() {
		return estadoExpediente;
	}

	public void setEstadoExpediente(String estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getTipoExpediente() {
		return tipoExpediente;
	}

	public void setTipoExpediente(String tipoExpediente) {
		this.tipoExpediente = tipoExpediente;
	}

	public String getCalificacionExpediente() {
		return calificacionExpediente;
	}

	public void setCalificacionExpediente(String calificacionExpediente) {
		this.calificacionExpediente = calificacionExpediente;
	}

	public String getDescMoneda() {
		return descMoneda;
	}

	public void setDescMoneda(String descMoneda) {
		this.descMoneda = descMoneda;
	}

	public String getTipoMedidaCautelar() {
		return tipoMedidaCautelar;
	}

	public void setTipoMedidaCautelar(String tipoMedidaCautelar) {
		this.tipoMedidaCautelar = tipoMedidaCautelar;
	}

	public String getContraCautela() {
		return contraCautela;
	}

	public void setContraCautela(String contraCautela) {
		this.contraCautela = contraCautela;
	}

	public String getEstadoCautelar() {
		return estadoCautelar;
	}

	public void setEstadoCautelar(String estadoCautelar) {
		this.estadoCautelar = estadoCautelar;
	}

	public IndicadoresMB() {
		
	}

	@PostConstruct
	public void inicializar() {
		
		setOrgano(new Organo());
		setIdPrioridad("");
		setBusNroExpe("");
		setResponsable(new Usuario());
		
		expedienteVista = new ExpedienteVista();
		expedienteVista.setHonorarios(new ArrayList<Honorario>());
		expedienteVista.setAnexos(new ArrayList<Anexo>());
		expedienteVista.setActividadProcesales(new ArrayList<ActividadProcesal>());
		expedienteVista.setInculpados(new ArrayList<Inculpado>());
		expedienteVista.setInstancias(new ArrayList<Instancia>());
		expedienteVista.setProvisiones(new ArrayList<Provision>());
		expedienteVista.setVias(new ArrayList<Via>());
		
		expedienteVista.setHonorario(new Honorario());
		expedienteVista.setAnexo(new Anexo());
		expedienteVista.setActividadProcesal(new ActividadProcesal());
		expedienteVista.setInculpado(new Inculpado());
		expedienteVista.setInstancia(0);
		expedienteVista.setProvision(new Provision());
		expedienteVista.setVia(0);
		demandante = new Involucrado();
		Persona persona = new Persona();
		demandante.setPersona(persona);
		Rol rol = new Rol();
		Usuario usu = new Usuario();
		usu.setRol(rol);

		busquedaProcesal = new BusquedaActProcesal();
		//limpiarSessionUsuario();
		// Inicializar el modelo usado en resultado de la busqueda de indicadores
		resultadoBusqueda = new BusquedaActividadProcesalDataModel(new ArrayList<BusquedaActProcesal>());
		resultadoBusqueda=buscarExpedientexResponsable();
		
		
	}

	public void buscarExpediente() 
	{
		//Cambiar propiedades Usuario, Organo, Involucrado, Demandante
		
		logger.debug("Buscando expedientes...");
		
		List<BusquedaActProcesal> expedientes = new ArrayList<BusquedaActProcesal>();
		GenericDao<BusquedaActProcesal, Object> expedienteDAO = (GenericDao<BusquedaActProcesal, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(BusquedaActProcesal.class);
		
		if (demandante != null) {
		
			logger.debug("Parametro Busqueda IdDemandante: "  + demandante.getIdInvolucrado());
			filtro.add(Restrictions.like("id_demandante",demandante.getIdInvolucrado()));
			filtro.add(Restrictions.eq("id_rol_involucrado", 2));
			
		}

		// Se aplica filtro a la busqueda por Numero de Expediente
		if(getBusNroExpe().compareTo("")!=0){
			String nroExpd= getBusNroExpe() ;
			logger.debug("Parametro Busqueda Expediente: " + nroExpd);
			filtro.add(Restrictions.like("nroExpediente","%" + nroExpd + "%").ignoreCase());
		}

		// Se aplica filtro a la busqueda por Organo
		if(getOrgano()!=null){
			
			if(getOrgano().getIdOrgano()!=0)
			{
				logger.debug("Parametro Busqueda Organo: " +getOrgano().getIdOrgano());
				filtro.add(Restrictions.eq("id_organo",Integer.valueOf(getOrgano().getIdOrgano())));
			}
			
		}
		
		if(getIdPrioridad().compareTo("")!=0)
		{
			
			String color = getIdPrioridad();
			logger.debug("Parametro Busqueda Color: " +color);
			filtro.add(Restrictions.eq("colorFila",color));
		}
		
		if (!mostrarListaResp)
		{
			//Buscando usuario obtenido de BBVA
			FacesContext fc = FacesContext.getCurrentInstance(); 
			ExternalContext exc = fc.getExternalContext(); 
			HttpSession session1 = (HttpSession) exc.getSession(true);
			
			logger.debug("Recuperando usuario..");
			com.grupobbva.seguridad.client.domain.Usuario usuario= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
			
			GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro2 = Busqueda.forClass(Usuario.class);
			filtro2.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
			List<Usuario> usuarios= new ArrayList<Usuario>();
					
			try {
				usuarios = usuarioDAO.buscarDinamico(filtro2);
			} catch (Exception exp) {
				//exp.printStackTrace();
				logger.debug("Error al obtener los datos de usuario de la session");
			}
	
			if(usuarios!= null)
			{
				if(usuarios.size()>=0){
					filtro.add(Restrictions.eq("id_responsable",usuarios.get(0).getIdUsuario()));
				}
							
			}
		}else{
			if(getResponsable()!= null){
				if (getResponsable().getIdUsuario() != 0) 
				{
					filtro.add(Restrictions.eq("id_responsable",getResponsable().getIdUsuario()));
				}
				
			}
			
		}
		
	
		try {
			
			expedientes = expedienteDAO.buscarDinamico(filtro);
			
			logger.debug("Total de expedientes encontrados: "+ expedientes.size());
			
		} catch (Exception e1) {
			
			logger.debug("Error al buscar expedientes en Modulo Indicadores: "+ e1.toString());
					
		}

		resultadoBusqueda = new BusquedaActividadProcesalDataModel(expedientes);
	}
	
	private void limpiarSessionUsuario()
	{
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	@SuppressWarnings("unchecked")
	public BusquedaActividadProcesalDataModel buscarExpedientexResponsable()
	{
		
		mostrarListaResp=true;
		mostrarControles=true;
		GenericDao<BusquedaActProcesal, Object> busqDAO = (GenericDao<BusquedaActProcesal, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(BusquedaActProcesal.class);
		
		//Buscando usuario obtenido de BBVA
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		com.grupobbva.seguridad.client.domain.Usuario usuarioAux= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
		
		if (usuarioAux!=null)
		{
			logger.debug("Buscando usuario: "+usuarioAux.getUsuarioId());
			
			GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro2 = Busqueda.forClass(Usuario.class);
			filtro2.add(Restrictions.eq("codigo", usuarioAux.getUsuarioId()));
			List<Usuario> usuarios= new ArrayList<Usuario>();
					
			try {
				usuarios = usuarioDAO.buscarDinamico(filtro2);
			} catch (Exception e) {
				//e.printStackTrace();
				logger.debug("Error al obtener los datos de usuario de la session");
			}
	
			if(usuarios!= null && usuarios.size()>0)
			{
				
				if(!usuarioAux.getPerfil().getNombre().equalsIgnoreCase("Administrador")){

					logger.debug("Parametro usuario encontrado:" + usuarioAux.getPerfil().getNombre());
					mostrarListaResp=false;
					filtro.add(Restrictions.eq("id_responsable",usuarios.get(0).getCodigo()));		
					
				}
								
				List<BusquedaActProcesal> resultado = new ArrayList<BusquedaActProcesal>();		
				try {
					resultado = busqDAO.buscarDinamico(filtro);
				} catch (Exception ex) {
					//ex.printStackTrace();
					logger.debug("Error al obtener los datos de busqueda");
				}
				resultadoBusqueda = new BusquedaActividadProcesalDataModel(resultado);
			}
			else
			{
				logger.debug("No se encontro el usuario logueado en la Base de datos SGL. Verificar credenciales!!");
				mostrarControles=false;
				mostrarListaResp=false;
			}
			
		}
		else
		{
			logger.debug("El usuario no es valido. Verificar credenciales!!");
			mostrarControles=false;
			mostrarListaResp=false;
		}
		
		return resultadoBusqueda;
	}
	
	@SuppressWarnings("unchecked")
	public List<Organo> completeOrgano(String query) {
		List<Organo> results = new ArrayList<Organo>();
		List<Organo> organos = new ArrayList<Organo>();

		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.debug("Error al obtener los datos de organos de la session");
		}

		for (Organo organo : organos) {
			String descripcion = organo.getNombre().toUpperCase() + " ("
					+ organo.getUbigeo().getDistrito().toUpperCase() + ", "
					+ organo.getUbigeo().getProvincia().toUpperCase() + ", "
					+ organo.getUbigeo().getDepartamento().toUpperCase() + ")";

			if (descripcion.toUpperCase().contains(query.toUpperCase())) {

				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}

		return results;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> completeResponsable(String query) {
		
		List<Usuario> results = new ArrayList<Usuario>();
		List<Usuario> responsables = new ArrayList<Usuario>();
		
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Usuario.class);

		try {
			responsables = usuarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al obtener los datos de responsables");
		}
		
		
		for (Usuario usuario : responsables) {

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
	public List<Involucrado> completeDemandante(String query) {
		List<Involucrado> results = new ArrayList<Involucrado>();

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Involucrado.class);
		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al obtener los datos de involucrados");
		}

		for (Involucrado inv : involucrados) {

			if (inv.getPersona().getNombreCompleto().toUpperCase()
					.contains(query.toUpperCase())
					&& inv.getRolInvolucrado().getIdRolInvolucrado() == 2) {
				results.add(inv);
			}
		}

		return results;
	}
	
	@SuppressWarnings("unchecked")
	public void actualizarFechaAtencion() 
	{
		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		GenericDao<ActividadProcesal, Object> actividadDAO = (GenericDao<ActividadProcesal, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		ActividadProcesal actProcesal = new ActividadProcesal();

		try {
			actProcesal = actividadDAO.buscarById(ActividadProcesal.class, busquedaProcesal2.getId_actividad_procesal());
			
			if(actProcesal.getFechaAtencion()== null){
				
				actProcesal.setFechaAtencion(getFechaActualDate());
				actProcesal.setObservacion(getObservacion());
				
				SituacionActProc estadoSituacionActProcAtendido = situacionActProcDAO.buscarById(SituacionActProc.class, 2);
				
				actProcesal.setSituacionActProc(estadoSituacionActProcAtendido);
				setFechaActualDate(null);
				setObservacion("");
				
				logger.debug("--------------------------------------------");
				logger.debug("-------------Datos a actualizar-------------");
				logger.debug("Fecha Atencion: " + actProcesal.getFechaAtencion().toString());
				logger.debug("Observacion: " + actProcesal.getObservacion());
				logger.debug("--------------------------------------------");
				
				actividadDAO.modificar(actProcesal);
				logger.debug("Actualizo la fecha de atencion de la actividad procesal exitosamente!");
				
			}else{
				
				logger.debug("Fecha de Atencion ya actualizada");
				FacesContext.getCurrentInstance().addMessage("growl",
						new FacesMessage("Actualizada","Fecha de Atencion ya actualizada!"));

			}
			
		} catch (Exception e) {
			
			logger.debug("Error al actualizar la act procesal" + e.toString());
			FacesContext.getCurrentInstance()
			.addMessage("growl" ,new FacesMessage("No registro","No se actualizo la fecha de atencion de la actividad procesal"));

		}
		
		buscarExpediente();
		
		
	}
	
	public void limpiarDatos() {
		fechaActualDate = modifDate(0);
		setObservacion("");
	}
	
	public Date modifDate(int dias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dias);

		return cal.getTime();

	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void leerExpediente(ActionEvent e) 
	{
		try {

			logger.debug(""+ ((List<BusquedaActProcesal>) getResultadoBusqueda().getWrappedData()).size());
			logger.debug("id Expediente: " + getBusquedaProcesal().getId_expediente());

			ExpedienteVista expedienteVistaNuevo = new ExpedienteVista();
			expedienteVistaNuevo.setFlagDeshabilitadoGeneral(true);

			GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Expediente.class);
	
			Expediente expediente = new Expediente();
			
			try {
				expediente = expedienteDAO.buscarById(Expediente.class, getBusquedaProcesal().getId_expediente());
			} catch (Exception e2) {
				//e2.printStackTrace();
				logger.debug("Error al obtener los datos de expediente");
			}
			
			if(expediente != null){
				
				logger.debug("--------------------------------------------------");
				logger.debug("Datos a mostrar");
				logger.debug("IdExpediente: " + expediente.getIdExpediente());
				logger.debug("Expediente:" + expediente.getNumeroExpediente());
				logger.debug("Instancia: " + expediente.getInstancia().getNombre());
				logger.debug("--------------------------------------------------");
				actualizarDatosPagina(expedienteVistaNuevo, expediente);
				setExpedienteVista(expedienteVistaNuevo);
		
				
			}else{
				
				FacesContext context = FacesContext.getCurrentInstance(); 
		        context.addMessage(null, new FacesMessage("Info", "Seleccione una opcion!"));
		        
			}

		} catch (Exception ee) {
			//e.printStackTrace();
			logger.debug("Error al obtener los datos de expediente");
			FacesContext context = FacesContext.getCurrentInstance(); 
	        context.addMessage(null, new FacesMessage("Info", "Seleccione una opcion!"));
	   
		}

	}

	@SuppressWarnings({ "unchecked" })
	public void actualizarDatosPagina(ExpedienteVista ex, Expediente e) {
		
		ex.setNroExpeOficial(e.getNumeroExpediente());
		ex.setInicioProceso(e.getFechaInicioProceso());
		if(e.getEstadoExpediente() != null)
			ex.setEstado(e.getEstadoExpediente().getIdEstadoExpediente());
		setEstadoExpediente(e.getEstadoExpediente().getNombre());
		
		
		if(e.getInstancia() != null){
			ex.setProceso(e.getInstancia().getVia().getProceso().getIdProceso());

			GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Via.class);
			filtro.add(Restrictions.like("proceso.idProceso", ex.getProceso()));
			filtro.add(Restrictions.eq("estado", SglConstantes.ACTIVO));

			try {
				ex.setVias(viaDao.buscarDinamico(filtro));
			} catch (Exception exc) {
				//exc.printStackTrace();
				logger.debug("Error al obtener los datos de vias");
			}

			ex.setVia(e.getInstancia().getVia().getIdVia());

			GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			filtro = Busqueda.forClass(Instancia.class);
			filtro.add(Restrictions.like("via.idVia", ex.getVia()));
			filtro.add(Restrictions.eq("estado", SglConstantes.ACTIVO));

			try {
				ex.setInstancias(instanciaDao.buscarDinamico(filtro));
				setInstanciasProximas(ex.getInstancias());
			} catch (Exception exc) {
				//exc.printStackTrace();
				logger.debug("Error al obtener los datos de instancias");
			}

			ex.setInstancia(e.getInstancia().getIdInstancia());
			ex.setNombreInstancia(e.getInstancia().getNombre());
			
			setNombreProceso(e.getInstancia().getVia().getProceso().getNombre());
			setNombreVia(e.getInstancia().getVia().getNombre());
			
			
		}
		
		if(e.getUsuario() != null)
			
			e.getUsuario().setNombreDescripcion(
					e.getUsuario().getCodigo() + " - " +
					e.getUsuario().getNombres() + " " +
					e.getUsuario().getApellidoPaterno() + " " +
					e.getUsuario().getApellidoMaterno());
		
			ex.setResponsable(e.getUsuario());

		if(e.getOficina() != null){
			String texto = e.getOficina().getCodigo()
				+ " "
				+ e.getOficina().getNombre().toUpperCase()
				+ " ("
				+ e.getOficina().getUbigeo().getDepartamento()
						.toUpperCase() + ")";
			e.getOficina().setNombreDetallado(texto);
			ex.setOficina(e.getOficina());
		}

		
		if(e.getTipoExpediente() != null)
			ex.setTipo(e.getTipoExpediente().getIdTipoExpediente());
		setTipoExpediente(e.getTipoExpediente().getNombre());
		
		if(e.getOrgano() != null ){
			
			String descripcion = e.getOrgano().getNombre().toUpperCase() + " ("
					+ e.getOrgano().getUbigeo().getDistrito().toUpperCase()
					+ ", "
					+ e.getOrgano().getUbigeo().getProvincia().toUpperCase()
					+ ", "
					+ e.getOrgano().getUbigeo().getDepartamento().toUpperCase()
					+ ")";
			e.getOrgano().setNombreDetallado(descripcion);
			ex.setOrgano1(e.getOrgano());
		}
		

		if(e.getCalificacion() != null)
			ex.setCalificacion(e.getCalificacion().getIdCalificacion());
		setCalificacionExpediente(e.getCalificacion().getNombre());
		
		if(e.getRecurrencia() != null)
			ex.setRecurrencia(e.getRecurrencia());

		ex.setSecretario(e.getSecretario());
		
		if(e.getFormaConclusion() != null)
			ex.setFormaConclusion(e.getFormaConclusion());
		
		if(e.getFechaFinProceso() != null)
			ex.setFinProceso(e.getFechaFinProceso());

		ex.setHonorario(new Honorario());
		ex.getHonorario().setCantidad(0);
		ex.getHonorario().setMonto(0.0);
		ex.getHonorario().setMontoPagado(0.0);

		List<Honorario> honorarios = new ArrayList<Honorario>();
		List<Cuota> cuotas;
		List<AbogadoEstudio> abogadoEstudios= new ArrayList<AbogadoEstudio>();
		
		GenericDao<Honorario, Object> honorarioDAO = (GenericDao<Honorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Cuota, Object> cuotaDAO = (GenericDao<Cuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		

		Busqueda filtro = Busqueda.forClass(Honorario.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente()));
		
		try {
			honorarios = honorarioDAO.buscarDinamico(filtro);

			for (Honorario h : honorarios) {
				cuotas = new ArrayList<Cuota>();
				
				Busqueda filtro2 = Busqueda.forClass(Cuota.class);
				filtro2.add(Restrictions.like("honorario.idHonorario",h.getIdHonorario()));
				cuotas = cuotaDAO.buscarDinamico(filtro2);
				
				int i=1;
				for (Cuota cuota:cuotas) {
					cuota.setNumero(i);
					cuota.setMoneda(h.getMoneda().getSimbolo());
					
					SituacionCuota situacionCuota= cuota.getSituacionCuota();
					cuota.setSituacionCuota(new SituacionCuota());
					cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
					cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
					i++;
				}
				
				h.setCuotas(cuotas);
				
				Busqueda filtro3 = Busqueda.forClass(AbogadoEstudio.class);
				filtro3.add(Restrictions.like("estado", 'A'));
				filtro3.add(Restrictions.like("abogado", h.getAbogado()));
				abogadoEstudios = abogadoEstudioDAO.buscarDinamico(filtro3);
				
				h.setEstudio(abogadoEstudios.get(0).getEstudio().getNombre());
			
			}

		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de honorarios");
		}

		ex.setHonorarios(honorarios);

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Involucrado.class);
		filtro.add(Restrictions.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de involucrados");
		}

		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(
				involucrados);
		ex.setInvolucradoDataModel(involucradoDataModel);
		ex.setInvolucrado(new Involucrado(new TipoInvolucrado(),new RolInvolucrado(), new Persona()));

		List<Cuantia> cuantias = new ArrayList<Cuantia>();
		GenericDao<Cuantia, Object> cuantiaDAO = (GenericDao<Cuantia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Cuantia.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente()));

		try {
			cuantias = cuantiaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de cuantias");
		}

		CuantiaDataModel cuantiaDataModel = new CuantiaDataModel(cuantias);
		ex.setCuantiaDataModel(cuantiaDataModel);
		ex.setCuantia(new Cuantia(new Moneda(), new Materia()));

		List<Inculpado> inculpados = new ArrayList<Inculpado>();
		GenericDao<Inculpado, Object> inculpadoDAO = (GenericDao<Inculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Inculpado.class);
		filtro.add(Restrictions.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			inculpados = inculpadoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de inculpados");
		}
		ex.setInculpados(inculpados);
		ex.setInculpado(new Inculpado(new SituacionInculpado(), new Moneda(),
				new Persona()));

		
		if(e.getMoneda() != null){
			ex.setMoneda(e.getMoneda().getIdMoneda());
			setDescMoneda(e.getMoneda().getDescripcion());
		}
		
		ex.setMontoCautelar(e.getMontoCautelar());

		if(e.getTipoCautelar() != null){
			ex.setTipoCautelar(e.getTipoCautelar().getIdTipoCautelar());
			setTipoMedidaCautelar(e.getTipoCautelar().getDescripcion());
		}
		
		ex.setDescripcionCautelar(e.getDescripcionCautelar());
		
		if(e.getContraCautela() != null){
			ex.setContraCautela(e.getContraCautela().getIdContraCautela());
			setContraCautela(e.getContraCautela().getDescripcion());
		}
		
		ex.setImporteCautelar(e.getImporteCautelar());
		
		if(e.getEstadoCautelar() != null){
			ex.setEstadoCautelar(e.getEstadoCautelar().getIdEstadoCautelar());
			setEstadoCautelar(e.getEstadoCautelar().getDescripcion());
		}
		
		List<Provision> provisions = new ArrayList<Provision>();
		GenericDao<Provision, Object> provisionDAO = (GenericDao<Provision, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Provision.class);
		filtro.add(Restrictions.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			provisions = provisionDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de provisiones");
		}
		ex.setProvisiones(provisions);
		ex.setProvision(new Provision(new Moneda(), new TipoProvision()));

		List<Resumen> resumens = new ArrayList<Resumen>();
		GenericDao<Resumen, Object> resumenDAO = (GenericDao<Resumen, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Resumen.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente())).addOrder(Order.asc("idResumen"));

		
		try {
			resumens = resumenDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de resumenes");
		}
		ex.setResumens(resumens);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		/*
		if(resumens!=null){
			if(resumens.size()!=0){
				for(Resumen res: resumens){
					
					if(res != null){
						
						if(ex.getTodoResumen()==null){
							
							ex.setTodoResumen( "Jorge Guzman"+ "\n" +
											"\t" + res.getTexto() + "\n" +
											"\t" + "\t" + "\t" + "\t" + "\t" +
											"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(res.getFecha()));
							
						}else{
							
							ex.setTodoResumen( "Jorge Guzman" + "\n" +
											"\t" + res.getTexto() + "\n" +
											"\t" + "\t" + "\t" + "\t" + "\t" +
											"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(res.getFecha()) + "\n" +
											"---------------------------------------------------------------------------" + "\n" +
											ex.getTodoResumen());
							
							
						}
					}
					
					
				}
			}	
		}*/
		
		//ex.setFechaResumen(e.getFechaResumen());
		//ex.setResumen(e.getTextoResumen());
		// setTodoResumen(getSelectedExpediente().get)

		List<ActividadProcesal> actividadProcesals = new ArrayList<ActividadProcesal>();
		GenericDao<ActividadProcesal, Object> actividadProcesalDAO = (GenericDao<ActividadProcesal, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(ActividadProcesal.class);
		filtro.add(Restrictions.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			actividadProcesals = actividadProcesalDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de actividades procesales");
		}
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		int numero=0;
		
		for(ActividadProcesal actividadProcesal:actividadProcesals){
			
			if(actividadProcesal.getFechaActividad() != null)
			actividadProcesal.setFechaActividadToString(dateFormat.format(actividadProcesal.getFechaActividad()));
			actividadProcesal.setFechaActividadAux(actividadProcesal.getFechaActividad());
			
			if(actividadProcesal.getFechaVencimiento() != null)
			actividadProcesal.setFechaVencimientoToString(dateFormat.format(actividadProcesal.getFechaVencimiento()));
			actividadProcesal.setFechaVencimientoAux(actividadProcesal.getFechaVencimiento());
			
			if(actividadProcesal.getFechaAtencion() != null)
			actividadProcesal.setFechaAtencionToString(dateFormat2.format(actividadProcesal.getFechaAtencion()));
			
			SituacionActProc situacionActProc= new SituacionActProc();
			situacionActProc.setNombre(actividadProcesal.getSituacionActProc().getNombre());
			situacionActProc.setIdSituacionActProc(actividadProcesal.getSituacionActProc().getIdSituacionActProc());
			situacionActProc.setEstado(actividadProcesal.getSituacionActProc().getEstado());
			
			actividadProcesal.setSituacionActProc(null);
			actividadProcesal.setSituacionActProc(situacionActProc);
			
			numero++;
			actividadProcesal.setNumero(numero);
			
		}
		
		ex.setActividadProcesales(actividadProcesals);
		ex.setActividadProcesal(new ActividadProcesal(new Etapa(),
				new SituacionActProc(), new Actividad()));

		List<Anexo> anexos = new ArrayList<Anexo>();
		GenericDao<Anexo, Object> anexoDAO = (GenericDao<Anexo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Anexo.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente()));

		try {
			anexos = anexoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			//e2.printStackTrace();
			logger.debug("Error al obtener los datos de anexos");
		}
		
		ex.setAnexos(anexos);
		ex.setAnexo(new Anexo());
		
		if(e.getRiesgo()!=null){
			ex.setRiesgo(e.getRiesgo().getIdRiesgo());
			setDescripcionRiesgo(e.getRiesgo().getDescripcion());
		}

		setTabCaucion(false);
		
		if (ex.getProceso() == 1 || ex.getProceso() == 3) {
			setTabCaucion(true);
		}
		
		if(e.getOrgano() != null && e.getOficina() != null ){
			
			ex.setDescripcionTitulo("Fecha Inicio de Proceso: "
					+ e.getFechaInicioProceso() + "\n" + "Organo: "
					+ e.getOrgano().getNombre() + "\n" + "Oficina: "
					+ e.getOficina().getNombre());
		}
	
	}

	public static int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		try {
			fechaInicial = df.parse(fechaInicioString);
		} catch (ParseException ex) {
		}

		String fechaFinalString = df.format(fechaFinal);

		try {
			fechaFinal = df.parse(fechaFinalString);
		} catch (ParseException ex) {
		}

		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ((int) dias);
	}

	public static String getFechaActual() {
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		return formateador.format(ahora);
	}

	public static synchronized java.util.Date deStringToDate(String fecha) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaEnviar = null;
		try {
			fechaEnviar = formatoDelTexto.parse(fecha);
			return fechaEnviar;
		} catch (ParseException ex) {
			//ex.printStackTrace();
			logger.debug("Error al convertir la fecha de string a date");
			return null;
		}
	}

	public static Date sumaDias(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias + 1);
		return cal.getTime();
	}

	public static synchronized java.util.Date deStringToDate(String fecha,
			String formato) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat(formato);
		// formatoDelTexto.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		Date fechaEnviar = null;
		try {
			fechaEnviar = formatoDelTexto.parse(fecha);
			return fechaEnviar;
		} catch (ParseException ex) {
			//ex.printStackTrace();
			logger.debug("Error al convertir la fecha de String a Date");
			return null;
		}
	}

	public Boolean getMostrarControles() {
		return mostrarControles;
	}

	public void setMostrarControles(Boolean mostrarControles) {
		this.mostrarControles = mostrarControles;
	}

	public Date getFechaActualDate() {
		return fechaActualDate;
	}

	public void setFechaActualDate(Date fechaActualDate) {
		this.fechaActualDate = fechaActualDate;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getDescripcionRiesgo() {
		return descripcionRiesgo;
	}

	public void setDescripcionRiesgo(String descripcionRiesgo) {
		this.descripcionRiesgo = descripcionRiesgo;
	}

	public BusquedaActProcesal getBusquedaProcesal2() {
		return busquedaProcesal2;
	}
	
	public void setBusquedaProcesal2(BusquedaActProcesal busquedaProcesal2) {
		fechaActualDate = modifDate(0);
		setObservacion("");
		this.busquedaProcesal2 = busquedaProcesal2;
	}

	public Organo getOrgano() {
		return organo;
	}

	public void setOrgano(Organo organo) {
		this.organo = organo;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}
	
}
