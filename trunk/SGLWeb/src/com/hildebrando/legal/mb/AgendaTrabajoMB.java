package com.hildebrando.legal.mb;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.ActividadProcesal;
import com.hildebrando.legal.modelo.ActividadxExpediente;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Feriado;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Rol;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.util.Util;
import com.hildebrando.legal.util.Utilitarios;
import com.hildebrando.legal.view.InvolucradoDataModel;

/**
 * Clase encargada de manejar la Agenda de actividades procesales, muestra la
 * información de las actividades procesales diferenciados por colores (rojo, 
 * verde, amarillo) en un calendario.
 * @author hildebrando
 * @version 1.0
 */

//@ManagedBean(name = "agendaTrab")
//@SessionScoped
public class AgendaTrabajoMB {
	private List<Organo> organos;
	// private BusquedaActProcesal selectedBusquedaActProcesal;
	private ScheduleModel agendaModel;
	private ScheduleEvent evento = new DefaultScheduleEvent();
	public static Logger logger = Logger.getLogger(AgendaTrabajoMB.class);
	private String color;
	private String busNroExpe;
	private Date fechaActualDate;
	private List<ActividadxExpediente> resultado;
	private Usuario responsable;
	private String nroExpediente;
	
	private String actividad;
	private String instancia;
	private int idOrgano;
	private String idPrioridad;
//	private int idResponsable;
	private String observacion = "";
	private Involucrado demandante;
	private Boolean mostrarListaResp;
	private Boolean mostrarControles;
	private Organo organo;
	private List<Involucrado> involucradosTodos;
	
	
	private int territorio;
	private List<Territorio> territorios;
	private Oficina oficina;
	private List<RolInvolucrado> rolInvolucrados;
	private List<String> rolInvolucradosString;
	private Involucrado involucrado;
	private Persona persona;
	private ConsultaService consultaService;
	private InvolucradoDataModel involucradoDataModel;
	private int rol;
	
	private String contador;
	
	private int proceso;
	private List<Proceso> procesos;
	private int via;
	private List<Via> vias;
	private Recurrencia recurrencia;
	private Materia materia;
	private int estado;
	private List<EstadoExpediente> estados;
	
	
	
	
	
	
	public int getProceso() {
		return proceso;
	}
	public void setProceso(int proceso) {
		this.proceso = proceso;
	}
	public List<Proceso> getProcesos() {
		return procesos;
	}
	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}
	public int getVia() {
		return via;
	}
	public void setVia(int via) {
		this.via = via;
	}
	public List<Via> getVias() {
		return vias;
	}
	public void setVias(List<Via> vias) {
		this.vias = vias;
	}
	public Recurrencia getRecurrencia() {
		return recurrencia;
	}
	public void setRecurrencia(Recurrencia recurrencia) {
		this.recurrencia = recurrencia;
	}
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public List<EstadoExpediente> getEstados() {
		return estados;
	}
	public void setEstados(List<EstadoExpediente> estados) {
		this.estados = estados;
	}
	/**
	 * Metodo que se ejecuta al seleccionar el proceso en el formulario de 
	 * consulta de expedientes.
	 * */
	public void cambioProceso() {
		try{
			if (getProceso() != 0) {
				vias = consultaService.getViasByProceso(getProceso());
			} 
			else {	
				vias = new ArrayList<Via>();
			}
		}catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en cambioProceso(): ",e);
		}
	}
	
	/**
	 * Metodo usado para mostrar un filtro autocompletable de materias
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Materia}
	 * **/
	public List<Materia> completeMaterias(String query) {	
		List<Materia> results = new ArrayList<Materia>();
		List<Materia> materias = consultaService.getMaterias();
		for (Materia mat : materias) 
		{	
			String descripcion = " " + mat.getDescripcion();			
			if (descripcion.toLowerCase().contains(query.toLowerCase())) 
			{
				results.add(mat);
			}
		}
		return results;
	}
	/**
	 * Metodo usado para mostrar un filtro autocompletable de recurrencias
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Recurrencia}
	 * **/
	public List<Recurrencia> completeRecurrencia(String query) {
		List<Recurrencia> recurrencias = consultaService.getRecurrencias();
		List<Recurrencia> results = new ArrayList<Recurrencia>();
		for (Recurrencia rec : recurrencias) 
		{
			if (rec.getNombre().toUpperCase().contains(query.toUpperCase())) 
			{
				results.add(rec);
			}
		}
		return results;
	}
	
	public String getContador() {
		return contador;
	}

	public void setContador(String contador) {
		this.contador = contador;
	}
	
	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	
	public List<ActividadxExpediente> getResultado() {
		return resultado;
	}

	public void setResultado(List<ActividadxExpediente> resultado) {
		this.resultado = resultado;
	}

	public int getTerritorio() {
		return territorio;
	}

	public void setTerritorio(int territorio) {
		this.territorio = territorio;
	}

	public List<Territorio> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(List<Territorio> territorios) {
		this.territorios = territorios;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public List<RolInvolucrado> getRolInvolucrados() {
		return rolInvolucrados;
	}

	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
	}

	public List<String> getRolInvolucradosString() {
		return rolInvolucradosString;
	}

	public void setRolInvolucradosString(List<String> rolInvolucradosString) {
		this.rolInvolucradosString = rolInvolucradosString;
	}

	public Involucrado getInvolucrado() {
		return involucrado;
	}

	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public ConsultaService getConsultaService() {
		return consultaService;
	}

	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}

	public InvolucradoDataModel getInvolucradoDataModel() {
		return involucradoDataModel;
	}

	public void setInvolucradoDataModel(InvolucradoDataModel involucradoDataModel) {
		this.involucradoDataModel = involucradoDataModel;
	}

	
	public AgendaTrabajoMB() 
	{
//		super();
		
	}
	
	@PostConstruct
	public void inicializar(){
		agendaModel = new DefaultScheduleModel();		
		involucradosTodos = new ArrayList<Involucrado>();		
		llenarAgenda();
		
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los Organos:",ex);
		}		
//		llenarResponsables();
		setObservacion("");		
		Rol rol = new Rol();
		Usuario usu = new Usuario();
		usu.setRol(rol);
		setResponsable(new Usuario());
		
		involucradosTodos = new ArrayList<Involucrado>();		
		procesos = consultaService.getProcesos();
		estados = consultaService.getEstadoExpedientes();
		territorios = consultaService.getTerritorios();
		involucrado = new Involucrado();
		involucradoDataModel = new InvolucradoDataModel(new ArrayList<Involucrado>());
		rolInvolucrados = consultaService.getRolInvolucrados();
	}

	/**
	 * Metodo usado para mostrar un filtro autocompletable de demandantes
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Involucrado}
	 * **/
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> completeDemandante(String query) 
	{
		List<Involucrado> resultsInvs = new ArrayList<Involucrado>();
		List<Persona> resultsPers = new ArrayList<Persona>();
		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Involucrado.class);
		filtro.add(Restrictions.eq("rolInvolucrado.idRolInvolucrado", SglConstantes.COD_ROL_INVOLUCRADO_DEMANDANTE));
		
		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los datos de involucrados/responsables para filtro autocompletable: ",e);
		}

		for (Involucrado inv : involucrados) 
		{
			if (inv.getPersona().getNombreCompleto().toUpperCase().contains(query.toUpperCase()) ) 
			{	
				involucradosTodos.add(inv);				
				if(!resultsPers.contains(inv.getPersona()))
				{	
					resultsInvs.add(inv);
					resultsPers.add(inv.getPersona());		
				}
			}
		}
		return resultsInvs;
	}
	
	
	//agregado al homologar busqueda
	public void limpiarCampos(ActionEvent ae){		
		setBusNroExpe("");
		setProceso(0);
		setVia(0);
		setDemandante(null);
		setOrgano(null);
		setEstado(0);
		setTerritorio(0);
		setResponsable(null);
		setOficina(null);
		setOrgano(null);
		setRol(0);
		setRecurrencia(null);
		setMateria(null);
		setRolInvolucrados(null);
		setResponsable(null);
		setTerritorio(0);
		setOficina(null);
		setRol(0);
	}
	
	/**
	 * Metodo usado para consultar un listado de organos que serán
	 * utilizados para mostrar un filtro autocompletable de organos
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Organo}
	 * **/
	
	@SuppressWarnings("unchecked")
	public List<Organo> completeOrgano(String query) 
	{
		List<Organo> results = new ArrayList<Organo>();
		List<Organo> organos = new ArrayList<Organo>();

		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);		
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los organos para filtro autocompletable:", ex);
		}

		for (Organo organo : organos) 
		{
			String descripcion = organo.getNombre().toUpperCase() + " (" + organo.getUbigeo().getDistrito().toUpperCase() + ", "
					+ organo.getUbigeo().getProvincia().toUpperCase() + ", " + organo.getUbigeo().getDepartamento().toUpperCase() + ")";

			if (descripcion.toUpperCase().contains(query.toUpperCase())) 
			{
				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}
		return results;		
	}
	
	/**
	 * Metodo usado para mostrar un filtro autocompletable de Responsable
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Usuario} responsables
	 * **/
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
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los responsables para filtro autocompletable:"+e);
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
	
	
	/**
	 * Metodo encargado de consultar la lista de oficinas
	 * para ser mostrados en un campo autocompletable
	 * @param query Valor a consultar
	 * @return results Lista de resultado del tipo {@link Oficina}
	 * **/	
	public List<Oficina> completeOficina(String query) {
		List<Oficina> results = new ArrayList<Oficina>();
		List<Oficina> oficinas = consultaService.getOficinas(null);

		if (oficinas != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "oficinas es:["+ oficinas.size() + "]. ");
		}

		for (Oficina oficina : oficinas) {
			if (oficina.getTerritorio() != null) {
				String texto = oficina.getCodigo()
						.concat(" ").concat(oficina.getNombre() != null ? oficina.getNombre().toUpperCase() : "").concat(" (")
						.concat(oficina.getTerritorio().getDescripcion() != null ? oficina.getTerritorio().getDescripcion().toUpperCase(): "").concat(")");
			
				if (texto.contains(query.toUpperCase())) {
					oficina.setNombreDetallado(texto);
					results.add(oficina);
				}
			}
		}
		return results;
	}
	
	

	/**
	 * Metodo encargado de consultar la lista de personas
	 * para ser mostrados en un campo autocompletable
	 * @param query Valor a consultar
	 * @return results Lista de resultado del tipo {@link Persona}
	 * **/
	public List<Persona> completePersona(String query) {
		logger.debug("=== completePersona ===");
		List<Persona> results = new ArrayList<Persona>();
		List<Persona> personas = consultaService.getPersonas();
		if (personas != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "personas es:["+ personas.size() + "]. ");
		}
		
		for (Persona pers : personas) {
			String nombreCompletoMayuscula = ""
					.concat(pers.getNombres() != null ? pers.getNombres().toUpperCase() : "").concat(" ")
					.concat(pers.getApellidoPaterno() != null ? pers.getApellidoPaterno().toUpperCase() : "")
					.concat(" ").concat(pers.getApellidoMaterno() != null ? pers.getApellidoMaterno().toUpperCase() : "");

			if (nombreCompletoMayuscula.contains(query.toUpperCase())) {
				pers.setNombreCompletoMayuscula(nombreCompletoMayuscula);
				results.add(pers);
			}
		}
		return results;
	}
	
	

	@SuppressWarnings("unchecked")
	public ScheduleModel llenarAgenda() 
	{
		agendaModel = new DefaultScheduleModel();
		DefaultScheduleEvent defaultEvent = null;
		String newFecha = null;
		Date newFecha2= null;
		Date fechaValid = null;
		String textoEvento = "";
		mostrarListaResp=true;
		mostrarControles=true;
		
		logger.debug("Se obtiene los campos 'sabado' y 'domingo' del properties para validar si se toman en cuenta en la agenda");
		
		boolean validarSabado = Boolean.valueOf(Util.getMessage("sabado"));
		boolean validarDomingo = Boolean.valueOf(Util.getMessage("domingo"));		

		if (agendaModel != null) 
		{
			Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
			logger.debug("INICIA PROCESO CARGA AGENDA: " + tstInicio);

			GenericDao<ActividadxExpediente, Object> busqDAO = (GenericDao<ActividadxExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

			Busqueda filtro = Busqueda.forClass(ActividadxExpediente.class);
			List<ActividadxExpediente> resultado = new ArrayList<ActividadxExpediente>();	
			
			//Buscando usuario obtenido de BBVA
			FacesContext fc = FacesContext.getCurrentInstance(); 
			ExternalContext exc = fc.getExternalContext(); 
			HttpSession session1 = (HttpSession) exc.getSession(true);
			
			com.grupobbva.seguridad.client.domain.Usuario usuarioAux= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
			
			if (usuarioAux!=null)
			{
				GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
				Busqueda filtro2 = Busqueda.forClass(Usuario.class);
				filtro2.add(Restrictions.eq("codigo", usuarioAux.getUsuarioId()));
				List<Usuario> usuarios= new ArrayList<Usuario>();
						
				try {
					usuarios = usuarioDAO.buscarDinamico(filtro2);
				} catch (Exception e) {
					logger.error("Error al obtener los datos de usuario de la session:",e);
				}

				if(usuarios!= null&& usuarios.size()>0)
				{	
					if(!usuarioAux.getPerfil().getNombre().equalsIgnoreCase(SglConstantes.ADMINISTRADOR))
					{
						logger.debug("Parametro usuario encontrado:" + usuarios.get(0).getIdUsuario());
						filtro.add(Restrictions.eq("id_responsable",usuarios.get(0).getIdUsuario()));
						mostrarListaResp=false;
					}
									
					//Buscar eventos de agenda
					try {
						resultado = busqDAO.buscarDinamico(filtro);
					} catch (Exception ex) {
						logger.error("Error al obtener los resultados de la busqueda de eventos de la agenda:",ex);
					}
					
					Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
					logger.debug("TERMINA PROCESO CARGA AGENDA: " + tstFin);

					double segundosUtilizados = restarFechas(tstInicio, tstFin);
					logger.debug("PROCESO CARGA AGENDA REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
					if(resultado!=null){
						logger.debug("Tamaño lista resultados: " + resultado.size());
					}

					for (final ActividadxExpediente act : resultado) 
					{
						textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha de Vencimiento: " + act.getFechaVencimiento() + 
									  "\nOrgano: " + act.getOrgano() + "\nExpediente: " + act.getNroExpediente() + 
									  "\nInstancia: " + act.getInstancia();

						logger.debug("------------------------------------------------------");
						logger.debug("Creando los elementos para el calendario (Inicio)--------------");
						logger.debug("Nro Expediente: " + act.getNroExpediente());
						logger.debug("Instancia: " + act.getInstancia());
						logger.debug("Actividad: " + act.getActividad());
						logger.debug("Fecha Actividad: " + act.getFechaActividad());
						logger.debug("Fecha Vencimiento: " + act.getFechaVencimiento());
						logger.debug("Texto Evento: " + textoEvento);
						logger.debug("Color Fila: " + act.getColorFila());
						logger.debug("------------------------------------------------------");

						SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
						sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

						try {							
							newFecha = sf1.format(act.getFechaVencimiento());
							newFecha2 = sf1.parse(newFecha);
							
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(newFecha2);
							
							logger.debug("Se valida que el dia de la actividad sea sabado o domingo");
							
							if (Utilitarios.esSabado(calendar))
							{
								if (validarSabado) //Se toma en cuenta el sabado como dia valido
								{
									logger.debug("Se toma en cuenta el sabado como dia valido para mostra la actividad");
									fechaValid = newFecha2;
								}
								else
								{
									if (validarDomingo) //Se toma en cuenta el domingo como dia valido
									{
										logger.debug("Se valida que el flag domingo este activo para saber si se toma en cuenta para mostra la actividad");
										fechaValid = Utilitarios.sumaTiempo(newFecha2, Calendar.DAY_OF_MONTH, 1);
									}
									else
									{
										logger.debug("Al no estar activo el flag domingo ni sabado se procede a aumentar 2 dias a la fecha de la actividad");
										fechaValid = Utilitarios.sumaTiempo(newFecha2, Calendar.DAY_OF_MONTH, 2);
									}
								}
							}
							else if (Utilitarios.esDomingo(calendar))
							{
								if (validarDomingo)
								{
									logger.debug("Se toma en cuenta el domingo como dia valido para mostra la actividad");
									fechaValid = newFecha2;
								}
								else
								{
									logger.debug("Al no estar activo el flag domingo se procede a aumentar 1 dias a la fecha de la actividad");
									fechaValid = Utilitarios.sumaTiempo(newFecha2, Calendar.DAY_OF_MONTH, 1);
								}
							}
							else
							{
								logger.debug("Si es un dia diferente a sabado o domingo se toma la fecha de la actividad");
								fechaValid = newFecha2;
							}
							
							logger.debug("De string a Date: " + fechaValid);
						} catch (ParseException e) {
							logger.debug("Error al convertir la fecha de String a Date: ",e);
						}
	
						if (fechaValid != null) 
						{
							logger.debug("Fecha a evaluar: " + fechaValid);
							defaultEvent = new DefaultScheduleEvent(textoEvento, fechaValid, fechaValid);
						}
							
						if (act.getColorFila()!=null)
						{
							if (act.getColorFila().equals(SglConstantes.COLOR_VERDE)) {
								defaultEvent.setStyleClass("eventoVerde");
							}
							if (act.getColorFila().equals(SglConstantes.COLOR_AMARILLO)) {
								defaultEvent.setStyleClass("eventoAmarillo");
							}
							if (act.getColorFila().equals(SglConstantes.COLOR_NARANJA)) {
								defaultEvent.setStyleClass("eventoNaranja");
							}
							if (act.getColorFila().equals(SglConstantes.COLOR_ROJO)) {
								defaultEvent.setStyleClass("eventoRojo");
							}
							agendaModel.addEvent(defaultEvent);
						
						}
						
						logger.debug("-----------------------------------------------------------");
					}
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
		}
		return agendaModel;
	}

	private static double restarFechas(Timestamp fhInicial, Timestamp fhFinal) 
	{
		long fhInicialms = fhInicial.getTime();
		long fhFinalms = fhFinal.getTime();
		long diferencia = fhFinalms - fhInicialms;
		double a = (double) diferencia / (double) (1000);

		return a;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean validarFechaFeriado(Date fecha) 
	{
		List<Feriado> resultado = new ArrayList<Feriado>();
		
		boolean bFechaFeriado = false;
		
		SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy");
		String fechaTMP = sf1.format(fecha);
		Date fechaFin=new Date();
		try {
			fechaFin = sf1.parse(fechaTMP);
		} catch (ParseException e) {
			logger.error(SglConstantes.MSJ_ERROR_CONVERTIR+"fecha en validarFechaFeriado(): "+e);
		}
		
		GenericDao<Feriado, Object> feriadoDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Feriado.class);
		filtro.add(Restrictions.eq("fechaInicio", fechaFin));
		
		try {
			resultado = feriadoDAO.buscarDinamico(filtro);
		} catch (Exception e1) {			
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"feriados: ",e1);
		}

		if (resultado.size()>0){
			bFechaFeriado=true;
		}
		return bFechaFeriado;
	}

	public String obtenerAnio(Date date) {
		if ( date  == null) {
			return "0";
		} else {
			String formato = "YYYY";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			return dateFormat.format(date);
		}
	}
	
	public void addEvent(ActionEvent actionEvent) {
		if (evento.getId() == null)
			agendaModel.addEvent(evento);
		else
			agendaModel.updateEvent(evento);
	}

	@SuppressWarnings({ "unchecked" })
	public void buscarEventosAgenda(ActionEvent e) 
	{
		agendaModel = new DefaultScheduleModel();
		DefaultScheduleEvent defaultEvent = null;
		String newFecha = null;
		Date newFecha2 = null;
		Date fechaValid = null;
		String textoEvento = "";
		
		boolean validarSabado = Boolean.valueOf(Util.getMessage("sabado"));
		boolean validarDomingo = Boolean.valueOf(Util.getMessage("domingo"));
		
		logger.debug("Buscando expedientes...");
		
		Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
		logger.debug("INICIA PROCESO BUSQUEDA AGENDA: " + tstInicio);
		
		List<ActividadxExpediente> expedientes = new ArrayList<ActividadxExpediente>();
		GenericDao<ActividadxExpediente, Object> busqDAO = (GenericDao<ActividadxExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(ActividadxExpediente.class);

//		if (demandante != null) 
//		{				
//			List<Integer> idInvolucradosEscojidos = new ArrayList<Integer>();
//			
//			for(Involucrado inv: involucradosTodos){
//				
//				if(inv.getPersona().getIdPersona() == demandante.getPersona().getIdPersona()){
//					
//					idInvolucradosEscojidos.add(inv.getIdInvolucrado());
//				}
//				
//			}
//			
//			filtro.add(Restrictions.in("id_demandante",idInvolucradosEscojidos));
//			filtro.add(Restrictions.eq("id_rol_involucrado", 2));
//		}

		// Se aplica filtro a la busqueda por Numero de Expediente
		if(getBusNroExpe().compareTo("")!=0)
		{	
			String nroExpd= getBusNroExpe() ;
			logger.debug("[BUSQ_AGENDA]- NroExp: " + nroExpd);
			filtro.add(Restrictions.like("nroExpediente",nroExpd).ignoreCase());
		}
		
		if(getProceso()!=0)
		{
			logger.debug("[BUSQ_EXP]-Proceso: " + getProceso());
			filtro.add(Restrictions.eq("id_proceso", getProceso()));
		}
		//Via
		if(getVia()!=0)
		{
			logger.debug("[BUSQ_EXP]-Via: "+ getVia());				
			filtro.add(Restrictions.eq("id_via", getVia()));
		}
		
		//Responsable
		if(getResponsable()!=null){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getResponsable()="+getResponsable());
			filtro.add(Restrictions.eq("id_responsable",getResponsable().getIdUsuario()));
		}
		//Territorio
		Busqueda filtroOficina = Busqueda.forClass(Oficina.class);
		GenericDao<Oficina, Object> oficinaDao = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		if(getTerritorio()!=0){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getTerritorio()="+getTerritorio());
			filtroOficina.add(Restrictions.eq("territorio.idTerritorio",getTerritorio()));
			List<Oficina> oficinas = new ArrayList<Oficina>();
			try{
				oficinas = oficinaDao.buscarDinamico(filtroOficina);
			}catch(Exception e3){
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Oficinas-Territorios: ",e3);
			}
			if(oficinas.size()>0){
				List<Integer> idOficinas = new ArrayList<Integer>();
				for (Oficina ofic: oficinas) {
					idOficinas.add(ofic.getIdOficina());
				}
				filtro.add(Restrictions.in("id_oficina", idOficinas));
			}
		}
		
		//Oficina
		if(getOficina()!=null){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getOficina()="+getOficina().getIdOficina());
			filtro.add(Restrictions.eq("id_oficina",getOficina().getIdOficina()));
		}
		
		// Se aplica filtro a la busqueda por Organo
		if(getOrgano()!=null)
		{
			logger.debug("[BUSQ_AGENDA]-Organo: " +getOrgano().getIdOrgano());
			filtro.add(Restrictions.eq("id_organo",Integer.valueOf(getOrgano().getIdOrgano())));
		}
		
		//RolInvolucrado
		Busqueda filtroRolInv = Busqueda.forClass(Involucrado.class);
		GenericDao<Involucrado, Object> usuarioDao = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		if(getRol()!=0){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getRolInvolucrados() ="+getRol());
			filtroRolInv.add(Restrictions.eq("rolInvolucrado.idRolInvolucrado",getRol()));
			List<Involucrado> involucrados = new ArrayList<Involucrado>();
			try{
				involucrados = usuarioDao.buscarDinamico(filtroRolInv);
			}catch(Exception e4){
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Usuario-Rol: ",e4);
			}
			if(involucrados.size()>0){
				List<Long> idExpe= new ArrayList<Long>();
				for (Involucrado inv: involucrados) {
					idExpe.add(inv.getExpediente().getIdExpediente());
				}
				filtro.add(Restrictions.in("idExpediente", idExpe));
			}
		}
		
		
		//Persona
		if(getPersona()!=null){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getPersona() ="+getPersona().toString());
			filtro.add(Restrictions.like("usuario",getPersona().getNombreCompleto()));
		}
		
		
		
		
		
		
		
		
		

		

		// Se aplica filtro a la busqueda por Prioridad: Rojo, Amarillo, Naranja
		// y Verde
//		if(getIdPrioridad().compareTo("")!=0)
//		{
//			String color = getIdPrioridad();
//			logger.debug("[BUSQ_AGENDA]-Color: " +color);
//			filtro.add(Restrictions.eq("colorFila",color));
//		}
		
		if (!mostrarListaResp)
		{
			//Buscando usuario obtenido de BBVA
			FacesContext fc = FacesContext.getCurrentInstance(); 
			ExternalContext exc = fc.getExternalContext(); 
			HttpSession session1 = (HttpSession) exc.getSession(true);
			
			logger.debug("== Recuperando usuario sesion ==");
			com.grupobbva.seguridad.client.domain.Usuario usuario= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
			
			GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro2 = Busqueda.forClass(Usuario.class);
			filtro2.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
			List<Usuario> usuarios= new ArrayList<Usuario>();
					
			try {
				usuarios = usuarioDAO.buscarDinamico(filtro2);
			} catch (Exception exp) {
				logger.debug(SglConstantes.MSJ_ERROR_OBTENER+"datos de usuario sesion: "+exp);
			}

			if(usuarios!= null)
			{
				if(usuarios.size()>=0){
					filtro.add(Restrictions.eq("id_responsable",usuarios.get(0).getCodigo()));	
				}
						
			}
		}else{
			
//			if (getIdResponsable() != 0) 
//			{
//				filtro.add(Restrictions.eq("id_responsable",getIdResponsable()));
//			}
		}
		
		try {
			expedientes = busqDAO.buscarDinamico(filtro);
			contador = String.valueOf(expedientes.size());
			contador += " Actividad(es) Encontrada(s)";
		} catch (Exception ex) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los resultados de busqueda eventos de Agenda: "+ex);
		}
		
		Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
		logger.debug("TERMINA PROCESO BUSQUEDA AGENDA: " + tstFin);

		double segundosUtilizados = restarFechas(tstInicio, tstFin);
		logger.debug("PROCESO BUSQUEDA AGENDA REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
		
		for (final ActividadxExpediente act : expedientes) 
		{
			textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha de Vencimiento: "
					+ act.getFechaVencimiento() + "\nOrgano: " + act.getOrgano() + "\nExpediente: " + act.getNroExpediente()
					+ "\nInstancia: " + act.getInstancia();
			
			logger.debug("------------------------------------------------------");
			logger.debug("Creando los elementos para el calendario--------------");
			logger.debug("Nro Expediente: " + act.getNroExpediente());
			logger.debug("Instancia: " + act.getInstancia());
			logger.debug("Actividad: " + act.getActividad());
			logger.debug("Fecha Actividad: " + act.getFechaActividad());
			logger.debug("Fecha Vencimiento: " + act.getFechaVencimiento());
			logger.debug("Tamanio Texto Evento: " + textoEvento.length());
			logger.debug("Color Fila: " + act.getColorFila());
			logger.debug("------------------------------------------------------");

			SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

			try {
				newFecha = sf1.format(act.getFechaVencimiento());
				newFecha2 = sf1.parse(newFecha);
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(newFecha2);
				
				if (Utilitarios.esSabado(calendar))
				{
					if (validarSabado)
					{
						fechaValid = newFecha2;
					}
					else
					{
						if (validarDomingo)
						{
							fechaValid = Utilitarios.sumaTiempo(newFecha2, Calendar.DAY_OF_MONTH, 1);
						}
						else
						{
							fechaValid = Utilitarios.sumaTiempo(newFecha2, Calendar.DAY_OF_MONTH, 2);
						}
					}
				}
				else if (Utilitarios.esDomingo(calendar))
				{
					if (validarDomingo)
					{
						fechaValid = newFecha2;
					}
					else
					{
						fechaValid = Utilitarios.sumaTiempo(newFecha2, Calendar.DAY_OF_MONTH, 1);
					}
				}
				else
				{
					fechaValid = newFecha2;
				}
				
				logger.debug("De string a Date: " + fechaValid);
			} catch (ParseException ex) {
				logger.debug(SglConstantes.MSJ_ERROR_EXCEPTION+"al convertir la fecha de String a Date" +ex);
			}

			if (fechaValid != null) 
			{
				logger.debug("Fecha a evaluar: " + fechaValid);
				defaultEvent = new DefaultScheduleEvent(textoEvento,fechaValid, fechaValid);
			}
				
			if (act.getColorFila()!=null)
			{
				if (act.getColorFila().equals(SglConstantes.COLOR_VERDE)) {
					defaultEvent.setStyleClass("eventoVerde");
				}
				if (act.getColorFila().equals(SglConstantes.COLOR_AMARILLO)) {
					defaultEvent.setStyleClass("eventoAmarillo");
				}
				if (act.getColorFila().equals(SglConstantes.COLOR_NARANJA)) {
					defaultEvent.setStyleClass("eventoNaranja");
				}
				if (act.getColorFila().equals(SglConstantes.COLOR_ROJO)) {
					defaultEvent.setStyleClass("eventoRojo");
				}
				agendaModel.addEvent(defaultEvent);
				
			}

		}
		
		logger.debug("Lista eventos despues de buscar:" + agendaModel.getEvents().size());
	}
	
	private void limpiarSessionUsuario()
	{
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		com.grupobbva.seguridad.client.domain.Usuario usuarioAux= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(true);
		session.setAttribute("usuario", usuarioAux);
	}

	public Date modifDate(int dias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dias);

		return cal.getTime();

	}

	/**
	 * Metodo encargado de actualizar la fecha de atencion de una actividad
	 * procesal, para esto se debe elegir una fecha en el popup fecha e incluir
	 * un comentario si así se desea.
	 * **/
	@SuppressWarnings("unchecked")
	public void actualizarFechaAtencion() 
	{
		if (nroExpediente != null && nroExpediente != "" && actividad != null && actividad != "") 
		{
			// Busqueda de idExpediente por el numero de expediente del evento seleccionado
			long idExpediente = 0;
			List<Expediente> result = new ArrayList<Expediente>();
			GenericDao<Expediente, Object> expDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Expediente.class);

			try {
				result = expDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los datos de expediente:"+e);
			}

			for (Expediente expd : result) 
			{
				if (expd.getNumeroExpediente().equals(nroExpediente)) {
					idExpediente = expd.getIdExpediente();
					break;
				}
			}

			// Busqueda de idActividad por la actividad del evento seleccionado
			long idActividad = 0;
			List<Actividad> result2 = new ArrayList<Actividad>();
			GenericDao<Actividad, Object> actProDAO = (GenericDao<Actividad, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			Busqueda filtro2 = Busqueda.forClass(Actividad.class);

			try {
				result2 = actProDAO.buscarDinamico(filtro2);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_OBTENER+"las actividades procesales:"+e);
			}

			for (Actividad soloAct : result2) 
			{
				if (soloAct.getNombre().equals(actividad)) 
				{
					idActividad = soloAct.getIdActividad();
					break;
				}
			}

			GenericDao<ActividadProcesal, Object> actividadDAO = (GenericDao<ActividadProcesal, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			List<ActividadProcesal> result3 = new ArrayList<ActividadProcesal>();
			Busqueda filtro3 = Busqueda.forClass(ActividadProcesal.class);

			try {
				result3 = actividadDAO.buscarDinamico(filtro3);
			} catch (Exception e) {
				logger.error(SglConstantes.MSJ_ERROR_OBTENER+"las actividades procesales:"+e);
			}

			for (ActividadProcesal actProcesal : result3) 
			{
				if (actProcesal.getActividad().getIdActividad() == idActividad && actProcesal.getExpediente().getIdExpediente() == idExpediente) 
				{
					actProcesal.setFechaAtencion(fechaActualDate);
					actProcesal.setObservacion(observacion);

					logger.debug("-------------------------------------------");
					logger.debug("Parametros configurados:");
					logger.debug("-------------------------------------------");
					logger.debug("IdExpediente: " + actProcesal.getExpediente().getIdExpediente());
					logger.debug("IdActividad: " + actProcesal.getActividad().getIdActividad());
					logger.debug("-------------------------------------------");

					logger.debug("--------------------------------------------");
					logger.debug("-------------Datos a actualizar-------------");
					logger.debug("Fecha Atencion: " + actProcesal.getFechaAtencion().toString());
					logger.debug("Observacion: " + actProcesal.getObservacion());
					logger.debug("--------------------------------------------");

					try {
						actividadDAO.modificar(actProcesal);
						logger.debug("Actualizo la fecha de atencion de la actividad procesal exitosamente!");
						logger.debug("Cargando nuevamente la agenda de eventos");
						llenarAgenda();
					} catch (Exception e) {

						logger.debug("Error al obtener los resultados de busqueda de las actividades procesales");
						FacesContext.getCurrentInstance()
								.addMessage(null,new FacesMessage("No registro","No se actualizó la fecha de atención de la actividad procesal"));
						logger.debug("No se actualizo la actividad procesal! " +e);
					}

					break;
				}
			}
		}
	}

	public void limpiarDatos() {
		fechaActualDate = modifDate(0);
		setObservacion("");
		setNroExpediente("");
		setActividad("");
		setTerritorio(0);
		setResponsable(null);
		setOficina(null);
		setOrgano(null);
		setRol(0);
	}

	/** Metodo encargado de obtener los responsables de los expedientes, 
	 * para esto se consulta a la tabla usuarios.
	 * **/
//	@SuppressWarnings("unchecked")
//	public void llenarResponsables() 
//	{
//		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//
//		Busqueda filtro = Busqueda.forClass(Usuario.class);
//
//		try {
//			responsables = usuarioDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los datos de responsables para filtro autocompletable:"+e);
//		}
//	}

	public static int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) 
	{
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

	public static String getFechaActual() 
	{
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		return formateador.format(ahora);
	}

	public static synchronized java.util.Date deStringToDate(String fecha) 
	{
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaEnviar = null;
		try {
			fechaEnviar = formatoDelTexto.parse(fecha);
			return fechaEnviar;
		} catch (ParseException ex) {
			logger.debug(SglConstantes.MSJ_ERROR_CONVERTIR+"fecha de String a Date: "+ex);
			return null;
		}
	}

	public static Date sumaDias(Date fecha, int dias) 
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias + 1);
		return cal.getTime();
	}

	public static synchronized java.util.Date deStringToDate(String fecha,String formato) 
	{
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat(formato);
		
		Date fechaEnviar = null;
		try {
			fechaEnviar = formatoDelTexto.parse(fecha);
			return fechaEnviar;
		} catch (ParseException ex) {
			logger.debug("Error al convertir la fecha de String a Date");
			return null;
		}
	}

	public Date getFechaActualDate() 
	{
		return deStringToDate(getFechaActual());
	}

	public void setFechaActualDate(Date fechaActualDate) 
	{
		this.fechaActualDate = fechaActualDate;
	}

	public String getNroExpediente() 
	{
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) 
	{
		this.nroExpediente = nroExpediente;
	}

	public String getActividad() 
	{
		return actividad;
	}

	public void setActividad(String actividad) 
	{
		this.actividad = actividad;
	}

	public String getInstancia() 
	{
		return instancia;
	}

	public void setInstancia(String instancia) 
	{
		this.instancia = instancia;
	}

	public Involucrado getDemandante() 
	{
		return demandante;
	}

	public void setDemandante(Involucrado demandante) 
	{
		this.demandante = demandante;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public int getIdOrgano() {
		return idOrgano;
	}

	public void setIdOrgano(int idOrgano) {
		this.idOrgano = idOrgano;
	}

	public List<Organo> getOrganos() {
		return organos;
	}

	public void setOrganos(List<Organo> organos) {
		this.organos = organos;
	}

//	public List<Usuario> getResponsables() {
//		return responsables;
//	}

//	public void setResponsables(List<Usuario> responsables) {
//		this.responsables = responsables;
//	}

//	public int getIdResponsable() {
//		return idResponsable;
//	}
//
//	public void setIdResponsable(int idResponsable) {
//		this.idResponsable = idResponsable;
//	}

	public ScheduleModel getAgendaModel() {
		return agendaModel;
	}

	public void setAgendaModel(ScheduleModel agendaModel) {
		this.agendaModel = agendaModel;
	}

	public ScheduleEvent getEvento() {
		return evento;
	}

	public void setEvento(ScheduleEvent evento) {
		this.evento = evento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public Boolean getMostrarListaResp() {
		return mostrarListaResp;
	}

	public void setMostrarListaResp(Boolean mostrarListaResp) {
		this.mostrarListaResp = mostrarListaResp;
	}

	public Boolean getMostrarControles() {
		return mostrarControles;
	}

	public void setMostrarControles(Boolean mostrarControles) {
		this.mostrarControles = mostrarControles;
	}

	public List<Involucrado> getInvolucradosTodos() {
		return involucradosTodos;
	}

	public void setInvolucradosTodos(List<Involucrado> involucradosTodos) {
		this.involucradosTodos = involucradosTodos;
	}

	public Organo getOrgano() {
		return organo;
	}

	public void setOrgano(Organo organo) {
		this.organo = organo;
	}	
}
