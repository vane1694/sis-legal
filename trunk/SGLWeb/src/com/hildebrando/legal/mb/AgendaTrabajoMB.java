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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.context.annotation.Scope;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.ActividadProcesal;
import com.hildebrando.legal.modelo.ActividadxExpediente;
import com.hildebrando.legal.modelo.BusquedaActProcesal;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Feriado;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Rol;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.view.BusquedaActividadProcesalDataModel;

@ManagedBean(name = "agendaTrab")
@SessionScoped
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
	private List<Usuario> responsables;
	private String nroExpediente;
	private String actividad;
	private String instancia;
	private int idOrgano;
	private String idPrioridad;
	private int idResponsable;
	private String observacion = "";
	private Involucrado demandante;
	private Boolean mostrarListaResp;
	private Boolean mostrarControles;

	private List<Involucrado> involucradosTodos;
	
	@SuppressWarnings("unchecked")
	public AgendaTrabajoMB() {
		super();

		// Aqui se inicia el modelo de la agenda.
		agendaModel = new DefaultScheduleModel();
		
		involucradosTodos = new ArrayList<Involucrado>();
		
		llenarAgenda();

		// Aqui se llena el combo de organos
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Organo.class);

		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.debug("Error al obtener los datos de organos");
		}

		// Aqui se llena el combo de responsables
		llenarResponsables();
		setObservacion("");
		
		Rol rol = new Rol();
		Usuario usu = new Usuario();
		usu.setRol(rol);
	}

	@SuppressWarnings("unchecked")
	public List<Involucrado> completeDemandante(String query) {
		List<Involucrado> resultsInvs = new ArrayList<Involucrado>();
		List<Persona> resultsPers = new ArrayList<Persona>();

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Involucrado.class);
		filtro.add(Restrictions.eq("rolInvolucrado.idRolInvolucrado", SglConstantes.COD_ROL_INVOLUCRADO_DEMANDANTE));
		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al obtener los datos de involucrados");
		}

		for (Involucrado inv : involucrados) {

			if (inv.getPersona().getNombreCompleto().toUpperCase().contains(query.toUpperCase()) ) {
				
				involucradosTodos.add(inv);
				
				if(!resultsPers.contains(inv.getPersona())){
					
					resultsInvs.add(inv);
					resultsPers.add(inv.getPersona());
					
				}
			}
		}

		return resultsInvs;
	}

	@SuppressWarnings("unchecked")
	public ScheduleModel llenarAgenda() 
	{
		agendaModel = new DefaultScheduleModel();
		Date fechaNueva = null;
		DefaultScheduleEvent defaultEvent = null;
		int diferencia = 0;
		int diferenciaFin = 0;
		String newFecha = null;
		Date newFecha2= null;
		String textoEvento = "";
		mostrarListaResp=true;
		mostrarControles=true;

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
			
			/*FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession session = (HttpSession) context.getSession(true);
			session.setAttribute("usuario", usuarioAux);*/
			
			if (usuarioAux!=null)
			{
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

				if(usuarios!= null&& usuarios.size()>0)
				{
					
					if(!usuarioAux.getPerfil().getNombre().equalsIgnoreCase("Administrador")){
						logger.debug("Parametro usuario encontrado:" + usuarios.get(0).getIdUsuario());
						filtro.add(Restrictions.eq("id_responsable",usuarios.get(0).getIdUsuario()));
						mostrarListaResp=false;
					}
									
					//Buscar eventos de agenda
					try {
						resultado = busqDAO.buscarDinamico(filtro);
					} catch (Exception ex) {
						//ex.printStackTrace();
						logger.debug("Error al obtener los resultados de la busqueda de eventos de la agenda");
					}
					
					Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
					logger.debug("TERMINA PROCESO CARGA AGENDA: " + tstFin);

					double segundosUtilizados = restarFechas(tstInicio, tstFin);
					logger.debug("PROCESO CARGA AGENDA REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
					
					//logger.debug("Query eventos agenda onLoad(): " + queryActividad);

					logger.debug("Tama�o lista resultados: " + resultado.size());

					for (final ActividadxExpediente act : resultado) 
					{
						textoEvento = "\nAsunto: " + act.getActividad() +
									  "\nFecha de Vencimiento: " + act.getFechaVencimiento() + 
									  "\nOrgano: " + act.getOrgano() + 
									  "\nExpediente: " + act.getNroExpediente() + 
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

						/*if (sf1.format(act.getFechaVencimiento()).indexOf("00:00:00") == -1) 
						{*/
							try {
								newFecha = sf1.format(act.getFechaActividad());
								newFecha2 = sf1.parse(newFecha);
								logger.debug("De string a Date: " + newFecha2);
							} catch (ParseException e) {
								logger.debug("Error al convertir la fecha de String a Date");
							}

							if (newFecha2 != null) 
							{
//								diferencia = fechasDiferenciaEnDias(act.getFechaActividad(),deStringToDate(getFechaActual()));
//
//								diferenciaFin = fechasDiferenciaEnDias(deStringToDate(getFechaActual()),act.getFechaVencimiento());
//								
//								logger.debug("Diferencia fecha actividad con fecha actual:" + diferencia);
//								logger.debug("Diferencia fecha vencimiento con fecha actual: " + diferenciaFin);
//								
//								if (diferencia > 0 && diferenciaFin > 0) 
//								{
//									Calendar cal = Calendar.getInstance();
//									cal.setTime(newFecha2);
//									cal.add(Calendar.DAY_OF_YEAR, diferencia);
//									/*
//									 * TimerTask timerTask = new TimerTask() { public
//									 * void run() {
//									 * 
//									 * } };
//									 */
//									fechaNueva = cal.getTime();
//									/*
//									 * Timer timer = new Timer();
//									 * timer.scheduleAtFixedRate
//									 * (timerTask,getTomorrowMorning12am(),
//									 * fONCE_PER_DAY);
//									 */
//									logger.debug("Fecha a evaluar: " + fechaNueva);
//									defaultEvent = new DefaultScheduleEvent(textoEvento, aumentarFechaxFeriado(fechaNueva), aumentarFechaxFeriado(fechaNueva));
//								} 
//								else 
//								{
									logger.debug("Fecha a evaluar: " + newFecha2);
									//defaultEvent = new DefaultScheduleEvent(textoEvento, aumentarFechaxFeriado(newFecha2), aumentarFechaxFeriado(newFecha2));
									defaultEvent = new DefaultScheduleEvent(textoEvento, newFecha2, newFecha2);
								}
								
								if (act.getColorFila()!=null)
								{
									if (act.getColorFila().equals("V")) {
										defaultEvent.setStyleClass("eventoVerde");
									}
									if (act.getColorFila().equals("A")) {
										defaultEvent.setStyleClass("eventoAmarillo");
									}
									if (act.getColorFila().equals("N")) {
										defaultEvent.setStyleClass("eventoNaranja");
									}
									if (act.getColorFila().equals("R")) {
										defaultEvent.setStyleClass("eventoRojo");
									}
									agendaModel.addEvent(defaultEvent);
								
								}
							/*}else {
								
							logger.debug("Formato de hora incorrecto. La hora no puede ser 00:00:00!!");
							}*/

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
			
			//filtro.add(Restrictions.isNull("fechaAtencion"));
			
			
			//setbConDatos(true);
		}
		return agendaModel;
	}

	private static double restarFechas(Timestamp fhInicial, Timestamp fhFinal) {
		long fhInicialms = fhInicial.getTime();
		long fhFinalms = fhFinal.getTime();
		long diferencia = fhFinalms - fhInicialms;
		double a = (double) diferencia / (double) (1000);

		return a;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean validarFechaFeriado(Date fecha) {
		List<Feriado> resultado = new ArrayList<Feriado>();
		
		boolean bFechaFeriado = false;
		
		SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy");
		String fechaTMP = sf1.format(fecha);
		Date fechaFin=new Date();
		try {
			fechaFin = sf1.parse(fechaTMP);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GenericDao<Feriado, Object> feriadoDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Feriado.class);
		filtro.add(Restrictions.eq("fechaInicio", fechaFin));
		
		try {
			
			resultado = feriadoDAO.buscarDinamico(filtro);
			
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
			
		}

		if (resultado.size()>0)
		{
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
	
	/*public Date aumentarFechaxFeriado(Date fecha)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		//cal.add(Calendar.DAY_OF_YEAR, 1);
		
		int dia=cal.get(Calendar.DAY_OF_WEEK);
		int numeroDias = 0;
		Date nuevaFecha = cal.getTime();
		
		while (true)
		{
			switch (dia)
			{
				//Si dia=6 entonces evaluar Viernes
				case 6: numeroDias=3;
				//Si dia=7 entonces evaluar Sabado
				case 7: numeroDias=2;
				//Si dia=1 entonces evaluar Domingo
				case 1: numeroDias=1;
			}
						
			if (validarFechaFeriado(nuevaFecha))
			{
				if (dia==6 || dia==7 || dia==1)
				{
					cal.add(Calendar.DAY_OF_YEAR, numeroDias);
					nuevaFecha = cal.getTime();
					if (!validarFechaFeriado(nuevaFecha))
					{
						break;
					}
				}
				else
				{
					cal.add(Calendar.DAY_OF_YEAR, 1);
					nuevaFecha = cal.getTime();
				}
			}
			else
			{
				break;
			}
		}
		return nuevaFecha;
	}*/

	public void addEvent(ActionEvent actionEvent) {
		if (evento.getId() == null)
			agendaModel.addEvent(evento);
		else
			agendaModel.updateEvent(evento);

		// evento = new DefaultScheduleEvent();
	}

	/*public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		evento = selectEvent.getScheduleEvent();

		if (evento != null) {
			if (!evento.getTitle().equals("")) {
				for (ActividadxExpediente act : resultado) {
					if (evento.getTitle().contains(act.getNroExpediente())
							&& evento.getTitle().contains(act.getActividad())) {
						nroExpediente = act.getNroExpediente();
						actividad = act.getActividad();
					}
				}
			}
		} else {
			logger.debug("Evento nulo!!!!!!!!!!!!!!!!!!!");
		}

		// evento = selectEvent.getScheduleEvent();
	}*/

	@SuppressWarnings({ "unchecked" })
	public void buscarEventosAgenda(ActionEvent e) 
	{
		agendaModel = new DefaultScheduleModel();
		//String filtro = "";
		//setbConDatos(true);
		int diferencia = 0;
		int diferenciaFin = 0;
		Date fechaNueva = null;
		DefaultScheduleEvent defaultEvent = null;
		String newFecha = null;
		Date newFecha2 = null;
		String textoEvento = "";
		
		logger.debug("Buscando expedientes...");
		
		Timestamp tstInicio = new Timestamp(new java.util.Date().getTime());
		logger.debug("INICIA PROCESO BUSQUEDA AGENDA: " + tstInicio);
		
		List<ActividadxExpediente> expedientes = new ArrayList<ActividadxExpediente>();
		GenericDao<ActividadxExpediente, Object> busqDAO = (GenericDao<ActividadxExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(ActividadxExpediente.class);

		if (demandante != null) {
			/*if (filtro.length() > 0) {
				filtro += " and inv.id_involucrado = "
						+ demandante.getIdInvolucrado()
						+ " and inv.id_rol_involucrado=2";
			} else {
				filtro += "where inv.id_involucrado = "
						+ demandante.getIdInvolucrado()
						+ " and inv.id_rol_involucrado=2";
			}*/
			
			List<Integer> idInvolucradosEscojidos = new ArrayList<Integer>();
			
			for(Involucrado inv: involucradosTodos){
				
				if(inv.getPersona().getIdPersona() == demandante.getPersona().getIdPersona()){
					
					idInvolucradosEscojidos.add(inv.getIdInvolucrado());
				}
				
			}
			
			filtro.add(Restrictions.in("id_demandante",idInvolucradosEscojidos));
			filtro.add(Restrictions.eq("id_rol_involucrado", 2));
		}

		// Se aplica filtro a la busqueda por Numero de Expediente
		if(getBusNroExpe().compareTo("")!=0){
			/*if (filtro.length() > 0) {
				filtro += " and c.numero_expediente = " + "'" + getBusNroExpe()
						+ "'";
			} else {
				filtro += "where c.numero_expediente = " + "'"
						+ getBusNroExpe() + "'";
			}*/
			String nroExpd= getBusNroExpe() ;
			logger.debug("[BUSQ_AGENDA]- NroExp: " + nroExpd);
			filtro.add(Restrictions.like("nroExpediente",nroExpd).ignoreCase());
		}

		// Se aplica filtro a la busqueda por Organo
		if(getIdOrgano()!=0)
		{
			/*if (filtro.length() > 0) {
				filtro += " and org.codigo=" + getIdOrgano();
			} else {
				filtro += "where org.codigo=" + getIdOrgano();
			}*/
			logger.debug("[BUSQ_AGENDA]-Organo: " +getIdOrgano());
			filtro.add(Restrictions.eq("id_organo",Integer.valueOf(getIdOrgano())));
		}

		// Se aplica filtro a la busqueda por Responsable
		/*if (getIdResponsable() != 0) {
			if (filtro.length() > 0) {
				filtro += " and c.id_usuario = " + getIdResponsable();
			} else {
				filtro += "where c.id_usuario = " + getIdResponsable();
			}
			logger.debug("Parametro Busqueda Responsable: " +getIdResponsable());
			filtro.add(Restrictions.eq("id_responsable",getIdResponsable()));
		}*/
		
		// Se aplica filtro a la busqueda por Prioridad: Rojo, Amarillo, Naranja
		// y Verde
		if(getIdPrioridad().compareTo("")!=0)
		{
			/*if (filtro.length() > 0) {
				filtro += " and " + queryColor(2) + "'" + getIdPrioridad()
						+ "'";
			} else {
				filtro += "where " + queryColor(2) + "'" + getIdPrioridad()
						+ "'";
			}*/
			
			String color = getIdPrioridad();
			logger.debug("[BUSQ_AGENDA]-Color: " +color);
			filtro.add(Restrictions.eq("colorFila",color));
		}
		
		//filtro.add(Restrictions.isNull("fechaAtencion"));
		
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
			
			if (getIdResponsable() != 0) 
			{
				
				filtro.add(Restrictions.eq("id_responsable",getIdResponsable()));
				
			}
		}
		
		try {
			expedientes = busqDAO.buscarDinamico(filtro);
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
					+ act.getFechaVencimiento() + "\nOrgano: " + act.getOrgano()
					+ "\nExpediente: " + act.getNroExpediente()
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

			/*if (sf1.format(act.getFechaVencimiento()).indexOf("00:00:00") == -1) 
			{*/
				try {
					newFecha = sf1.format(act.getFechaActividad());
					newFecha2 = sf1.parse(newFecha);
					logger.debug("De string a Date: " + newFecha2);
				} catch (ParseException ex) {
					logger.debug(SglConstantes.MSJ_ERROR_EXCEPTION+"al convertir la fecha de String a Date" +ex);
				}

				if (newFecha2 != null) 
				{
						logger.debug("Fecha a evaluar: " + newFecha2);
						defaultEvent = new DefaultScheduleEvent(textoEvento,newFecha2, newFecha2);
					}
					
					if (act.getColorFila()!=null)
					{
						if (act.getColorFila().equals("V")) {
							defaultEvent.setStyleClass("eventoVerde");
						}
						if (act.getColorFila().equals("A")) {
							defaultEvent.setStyleClass("eventoAmarillo");
						}
						if (act.getColorFila().equals("N")) {
							defaultEvent.setStyleClass("eventoNaranja");
						}
						if (act.getColorFila().equals("R")) {
							defaultEvent.setStyleClass("eventoRojo");
						}
						agendaModel.addEvent(defaultEvent);
						
					}
				
			/*} 
			else 
			{
				logger.debug("Formato de hora incorrecto. La hora no puede ser 00:00:00!!");
			}*/

		}
		logger.debug("Lista eventos despues de buscar:" + agendaModel.getEvents().size());
		//limpiarSessionUsuario();
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

	@SuppressWarnings("unchecked")
	public void actualizarFechaAtencion() 
	{
		if (nroExpediente != null && nroExpediente != "" && actividad != null
				&& actividad != "") {
			// Busqueda de idExpediente por el numero de expediente del evento
			// seleccionado
			long idExpediente = 0;
			List<Expediente> result = new ArrayList<Expediente>();
			GenericDao<Expediente, Object> expDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Expediente.class);

			try {
				result = expDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				//e.printStackTrace();
				logger.debug("Error al obtener los datos de expediente");
			}

			for (Expediente expd : result) {

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
				//e.printStackTrace();
				logger.debug("Error al obtener los datos de las actividades procesales");
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
				//e.printStackTrace();
				logger.debug("Error al obtener los datos de las actividades procesales");
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

						//e.printStackTrace();
						logger.debug("Error al obtener los resultados de busqueda de las actividades procesales");
						FacesContext.getCurrentInstance()
								.addMessage(null,new FacesMessage("No registro","No se actualizo la fecha de atencion de la actividad procesal"));
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
	}

	/*
	 * @SuppressWarnings("unchecked") public String editarExpediente(SelectEvent
	 * e) {
	 * 
	 * // SpringInit.openSession(); logger.debug("Lista de resultado: " +
	 * ((List<BusquedaActProcesal>) getResultadoBusqueda()
	 * .getWrappedData()).size()); // FacesContext context =
	 * FacesContext.getCurrentInstance(); ExternalContext context =
	 * FacesContext.getCurrentInstance() .getExternalContext(); // String bus=
	 * (String) context.getRequestMap().get("busNroExp"); // BusquedaActProcesal
	 * item = resultadoBusqueda.getRowData(); int id = (Integer) context.getr;
	 * logger.debug("id: " + id); HttpSession session = (HttpSession)
	 * context.getSession(true); session.setAttribute("selectedExpediente", id);
	 * 
	 * logger.debug("SelectedExpediente: " + id);
	 * 
	 * return "BusExpedienteReadOnly"; }
	 */

	/*
	 * public String definirColorFila(Date fechaVencimiento, Date fechaInicio,
	 * int plazoLey, int numeroDiasxVencer, int numeroDiasAlerta, int
	 * numeroDiasRojo) {
	 * 
	 * String colorFila = "";
	 * 
	 * logger.debug("--------------------------------------------------");
	 * logger.debug("Parametros ingresados al metodo <definirColorFila>");
	 * logger.debug("--------------------------------------------------");
	 * logger.debug("Fec Ven: " + fechaVencimiento); logger.debug("Fec Ini: " +
	 * fechaInicio); logger.debug("Plazo Ley: " + plazoLey);
	 * logger.debug("Dias x Vencer: " + numeroDiasxVencer);
	 * logger.debug("Dias Alerta: " + numeroDiasAlerta);
	 * logger.debug("Dias Vencidas: " + numeroDiasRojo);
	 * 
	 * int diferenciaVen = fechasDiferenciaEnDias(
	 * deStringToDate(getFechaActual()),fechaVencimiento);
	 * logger.debug("Diferencia en dias con fecha vencida: " + diferenciaVen);
	 * int diferenciaInic = fechasDiferenciaEnDias(
	 * fechaInicio,deStringToDate(getFechaActual()));
	 * logger.debug("Diferencia en dias con fecha inicio: " + diferenciaInic);
	 * 
	 * if (numeroDiasxVencer==0 && numeroDiasAlerta==0 && numeroDiasRojo==0) {
	 * int mitadTranscurrida=Integer.valueOf((plazoLey/2)); SimpleDateFormat sf1
	 * = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	 * sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
	 * 
	 * //logger.debug("Fecha Inicio Emplazada:" +
	 * sf1.format(sumaDias(fechaInicio, plazoLey)));
	 * logger.debug("Mitad transcurrida: " + mitadTranscurrida);
	 * 
	 * // parametro definido en el mantenimiento de estados if (diferenciaVen <=
	 * 0 && (diferenciaVen*-1) <= plazoLey && (diferenciaVen*-1) <= 3) {
	 * colorFila = "R"; } if (diferenciaVen <= 3 && diferenciaVen<=plazoLey) {
	 * colorFila = "N"; } if (diferenciaInic==0 && diferenciaVen<=plazoLey) {
	 * colorFila = "V"; } if (diferenciaInic <= mitadTranscurrida &&
	 * diferenciaVen<=plazoLey) { colorFila = "A"; } } else { if (diferenciaVen
	 * <= 0 && (diferenciaVen*-1) <= plazoLey && (diferenciaVen*-1) <=
	 * numeroDiasRojo) { colorFila = "R"; } if (diferenciaVen <=
	 * numeroDiasxVencer && diferenciaVen<=plazoLey) { colorFila = "N"; } if
	 * (diferenciaInic==0 && diferenciaVen<=plazoLey) { colorFila = "V"; } if
	 * (diferenciaVen > numeroDiasxVencer && diferenciaVen <= numeroDiasAlerta
	 * && diferenciaVen<=plazoLey) { colorFila = "A"; }
	 * 
	 * } logger.debug("--------------------------------------------------");
	 * 
	 * return colorFila; }
	 */

	@SuppressWarnings("unchecked")
	public void llenarResponsables() {
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Usuario.class);

		try {
			responsables = usuarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Error al obtener los datos de responsables");
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
			logger.debug(SglConstantes.MSJ_ERROR_CONVERTIR+"fecha de String a Date: "+ex);
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

	public Date getFechaActualDate() {
		return deStringToDate(getFechaActual());
	}

	public void setFechaActualDate(Date fechaActualDate) {
		this.fechaActualDate = fechaActualDate;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	/*public Boolean getbConDatos() {
		return bConDatos;
	}

	public void setbConDatos(Boolean bConDatos) {
		this.bConDatos = bConDatos;
	}*/

	public Involucrado getDemandante() {
		return demandante;
	}

	public void setDemandante(Involucrado demandante) {
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

	public List<Usuario> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Usuario> responsables) {
		this.responsables = responsables;
	}

	public int getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
	}

	public ScheduleModel getAgendaModel() {
		return agendaModel;
	}

	/*public void onDateSelect(DateSelectEvent selectEvent) {
		evento = new DefaultScheduleEvent("", selectEvent.getDate(),
				selectEvent.getDate());
	}*/

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
}
