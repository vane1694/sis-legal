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
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
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
import com.hildebrando.legal.modelo.BusquedaActProcesal;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Feriado;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Rol;
import com.hildebrando.legal.modelo.Usuario;
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

	@SuppressWarnings("unchecked")
	public AgendaTrabajoMB() {
		super();

		// Aqui se inicia el modelo de la agenda.
		agendaModel = new DefaultScheduleModel();
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
	public ScheduleModel llenarAgenda() 
	{
		agendaModel = new DefaultScheduleModel();
		Date fechaNueva = null;
		DefaultScheduleEvent defaultEvent = null;
		int diferencia = 0;
		int diferenciaFin = 0;
		Date newFecha = null;
		String textoEvento = "";
		mostrarListaResp=true;
		mostrarControles=true;

		if (agendaModel != null) 
		{
			/*String queryActividad = "select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID,"
					+ "c.numero_expediente,ins.nombre as instancia, case when hora is null then concat(fecha_actividad,' 00:00:00') else "
					+ "concat(concat(fecha_actividad,' '), to_char(hora,'HH24:MI:SS')) end as hora,"
					+ "org.nombre as organo, act.nombre as Actividad,a.fecha_actividad,a.fecha_vencimiento,a.fecha_atencion, "
					+ queryColor(1)
					+ "from actividad_procesal a "
					+ "join expediente c on a.id_expediente=c.id_expediente "
					+ "join involucrado inv on c.id_expediente=inv.id_expediente "
					+ "join persona per on inv.id_persona=per.id_persona "
					+ "join organo org on c.id_organo = org.id_organo "
					+ "join actividad act on a.id_actividad = act.id_actividad "
					+ "join instancia ins on c.id_instancia=ins.id_instancia "
					+ "join via vi on ins.id_via = vi.id_via "
					+ "join proceso pro on vi.id_proceso = pro.id_proceso "
					+ "join usuario usu on c.id_usuario=usu.id_usuario "
					+ "where a.fecha_atencion is null "
					+ "order by c.numero_expediente";

			System.out.println("Query eventos agenda onLoad(): " + queryActividad);	
			
			Query query = SpringInit.devolverSession()
					.createSQLQuery(queryActividad)
					.addEntity(ActividadxExpediente.class);

			logger.debug("------------------------------------------------------");
				
			resultado = query.list();*/
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
					logger.debug("Parametro usuario encontrado:" + usuarios.get(0).getCodigo());
					filtro.add(Restrictions.eq("id_responsable",usuarios.get(0).getCodigo()));		
					
					if (!usuarios.get(0).getRol().getDescripcion().equalsIgnoreCase("administrador"))
					{
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

					logger.debug("Tamaño lista resultados: " + resultado.size());

					for (final ActividadxExpediente act : resultado) 
					{
						textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha: "
								+ act.getHora() + "\nOrgano: " + act.getOrgano()
								+ "\nExpediente: " + act.getNroExpediente()
								+ "\nInstancia: " + act.getInstancia();

						logger.debug("------------------------------------------------------");
						logger.debug("Creando los elementos para el calendario (Inicio)--------------");
						logger.debug("Nro Expediente: " + act.getNroExpediente());
						logger.debug("Instancia: " + act.getInstancia());
						logger.debug("Actividad: " + act.getActividad());
						logger.debug("Fecha Actividad: " + act.getFechaActividad());
						logger.debug("Fecha Vencimiento: " + act.getFechaVencimiento());
						logger.debug("Texto Evento: " + textoEvento);
						logger.debug("Hora Actividad: " + act.getHora());
						logger.debug("Color Fila: " + act.getColorFila());
						logger.debug("------------------------------------------------------");

						SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
						sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

						if (act.getHora().indexOf("00:00:00") == -1) 
						{
							try {
								newFecha = sf1.parse(act.getHora().trim());
								logger.debug("De string a Date: " + newFecha);
							} catch (ParseException e) {
								//e.printStackTrace();
								logger.debug("Error al convertir la fecha de String a Date");
							}

							if (newFecha != null) 
							{
								diferencia = fechasDiferenciaEnDias(act.getFechaActividad(),deStringToDate(getFechaActual()));

								diferenciaFin = fechasDiferenciaEnDias(deStringToDate(getFechaActual()),act.getFechaVencimiento());
								
								logger.debug("Diferencia fecha actividad con fecha actual:" + diferencia);
								logger.debug("Diferencia fecha vencimiento con fecha actual: " + diferenciaFin);
								
								if (diferencia > 0 && diferenciaFin > 0) 
								{
									Calendar cal = Calendar.getInstance();
									cal.setTime(newFecha);
									cal.add(Calendar.DAY_OF_YEAR, diferencia);
									/*
									 * TimerTask timerTask = new TimerTask() { public
									 * void run() {
									 * 
									 * } };
									 */
									fechaNueva = cal.getTime();
									/*
									 * Timer timer = new Timer();
									 * timer.scheduleAtFixedRate
									 * (timerTask,getTomorrowMorning12am(),
									 * fONCE_PER_DAY);
									 */
									logger.debug("Fecha a evaluar: " + fechaNueva);
									defaultEvent = new DefaultScheduleEvent(textoEvento, aumentarFechaxFeriado(fechaNueva), aumentarFechaxFeriado(fechaNueva));
								} 
								else 
								{
									logger.debug("Fecha a evaluar: " + newFecha);
									defaultEvent = new DefaultScheduleEvent(textoEvento, aumentarFechaxFeriado(newFecha), aumentarFechaxFeriado(newFecha));
								}

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
						} 
						else 
						{
							logger.debug("Formato de hora incorrecto. La hora no puede ser 00:00:00!!");
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
	/*	int i = 0;
		String patron = "dd/MM/yyyy";*/
		boolean bFechaFeriado = false;

		/*String queryBus = "select ROW_NUMBER() OVER (ORDER BY id_actividad_procesal) as ROW_ID,"
				+ "id_actividad_procesal,id_expediente,fecha_actividad,fecha_vencimiento "
				+ "from actividad_procesal "
				+ "where fecha_atencion is null and to_char(fecha_actividad, 'YYYY') = '2012' "
				+ "and fecha_actividad in (select fecha_inicio from feriado where to_char(fecha_inicio, 'YYYY')= '2012') "
				+ "order by 1";*/
		
		SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy");
		String fechaTMP = sf1.format(fecha);
		
		String queryBus ="select id_feriado,id_organo,fecha_inicio,fecha_fin,tipo,indicador " +
				"from feriado where to_char(fecha_inicio, 'YYYY')= '" + obtenerAnio(fecha) +  "'"  + " and fecha_inicio = '" + fechaTMP + "'" ;

		Query query = SpringInit.devolverSession().createSQLQuery(queryBus).addEntity(Feriado.class);

		resultado = query.list();
		
		if (resultado.size()>0)
		{
			bFechaFeriado=true;
		}
		
		/*
		for (ActividadProcesalxFeriado res : resultado) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(res.getFechaActividad());
			cal.add(Calendar.DAY_OF_YEAR, i++);

			SimpleDateFormat sdf = new SimpleDateFormat(patron);
			String fechaNueva = sdf.format(cal.getTime());

			String queryFer = "select to_date('01/10/2012','DD/MM/YYYY') as Fecha_Siguiente,"
					+ "to_char(to_date('01/10/2012','DD/MM/YYYY'), 'DAY', 'NLS_DATE_LANGUAGE=SPANISH') as Dia_Semana_Siguiente "
					+ "from actividad_procesal "
					+ "where fecha_atencion is null and to_char(fecha_actividad, 'YYYY') = '2012' "
					+ "and to_date('01/10/2012','DD/MM/YYYY') in (select fecha_inicio from feriado where to_char(fecha_inicio, 'YYYY')= '2012' ) "
					+ "and id_actividad_procesal = 173 " + "order by 1";

		}*/
		return bFechaFeriado;
	}

	public String obtenerAnio(Date date) {

		if (null == date) {

			return "0";

		} else {

			String formato = "yyyy";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			return dateFormat.format(date);

		}

	}
	
	public Date aumentarFechaxFeriado(Date fecha)
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
					/*if (!validarFechaFeriado(nuevaFecha))
					{
						break;
					}*/
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
	}

	public void addEvent(ActionEvent actionEvent) {
		if (evento.getId() == null)
			agendaModel.addEvent(evento);
		else
			agendaModel.updateEvent(evento);

		// evento = new DefaultScheduleEvent();
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
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
	}

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
		Date newFecha = null;
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
			logger.debug("Parametro Busqueda IdDemandante: "  + demandante.getIdInvolucrado());
			filtro.add(Restrictions.like("id_demandante",demandante.getIdInvolucrado()));
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
			logger.debug("Parametro Busqueda Expediente: " + nroExpd);
			filtro.add(Restrictions.eq("nroExpediente", nroExpd));
		}

		// Se aplica filtro a la busqueda por Organo
		if(getIdOrgano()!=0)
		{
			/*if (filtro.length() > 0) {
				filtro += " and org.codigo=" + getIdOrgano();
			} else {
				filtro += "where org.codigo=" + getIdOrgano();
			}*/
			logger.debug("Parametro Busqueda Organo: " +getIdOrgano());
			filtro.add(Restrictions.eq("id_organo",Integer.valueOf(getIdOrgano())));
		}

		// Se aplica filtro a la busqueda por Responsable
		if (getIdResponsable() != 0) {
		/*	if (filtro.length() > 0) {
				filtro += " and c.id_usuario = " + getIdResponsable();
			} else {
				filtro += "where c.id_usuario = " + getIdResponsable();
			}*/
			logger.debug("Parametro Busqueda Responsable: " +getIdResponsable());
			filtro.add(Restrictions.eq("id_responsable",getIdResponsable()));
		}
		
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
			logger.debug("Parametro Busqueda Color: " +color);
			filtro.add(Restrictions.eq("colorFila",color));
		}
		
		//filtro.add(Restrictions.isNull("fechaAtencion"));
		
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
				filtro.add(Restrictions.eq("id_responsable",usuarios.get(0).getCodigo()));			
			}
		}
				
		/*logger.debug("Filtro: " + filtro);

		String queryActividad = "select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID,"
				+ "c.numero_expediente,ins.nombre as instancia,case when hora is null then concat(fecha_actividad,' 00:00:00') else "
				+ "concat(concat(fecha_actividad,' '), to_char(hora,'HH24:MI:SS')) end as hora,"
				+ "org.nombre as organo, act.nombre as Actividad,a.fecha_actividad,a.fecha_vencimiento,a.fecha_atencion, "
				+ queryColor(1)
				+ "from actividad_procesal a "
				+ "join expediente c on a.id_expediente=c.id_expediente "
				+ "join involucrado inv on c.id_expediente=inv.id_expediente "
				+ "join persona per on inv.id_persona=per.id_persona "
				+ "join organo org on c.id_organo = org.id_organo "
				+ "join actividad act on a.id_actividad = act.id_actividad "
				+ "join instancia ins on c.id_instancia=ins.id_instancia "
				+ "join via vi on ins.id_via = vi.id_via "
				+ "join proceso pro on vi.id_proceso = pro.id_proceso "
				+ "join usuario usu on c.id_usuario=usu.id_usuario "
				+ filtro
				+ " order by c.numero_expediente";

		Query query = SpringInit.devolverSession()
				.createSQLQuery(queryActividad)
				.addEntity(ActividadxExpediente.class);

		logger.debug("Query Eventos: " + queryActividad);
		logger.debug("------------------------------------------------------");
		resultado = query.list();*/
		
		
		try {
			expedientes = busqDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.debug("Error al obtener los resultados de busqueda de eventos de agenda");
		}
		
		Timestamp tstFin = new Timestamp(new java.util.Date().getTime());
		logger.debug("TERMINA PROCESO BUSQUEDA AGENDA: " + tstFin);

		double segundosUtilizados = restarFechas(tstInicio, tstFin);
		logger.debug("PROCESO BUSQUEDA AGENDA REALIZADO EN: " + segundosUtilizados + " SEGUNDOS");
		
		for (final ActividadxExpediente act : expedientes) 
		{
			textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha: "
					+ act.getHora() + "\nOrgano: " + act.getOrgano()
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
			logger.debug("Hora Actividad: " + act.getHora());
			logger.debug("Color Fila: " + act.getColorFila());
			logger.debug("------------------------------------------------------");

			SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));

			if (act.getHora().indexOf("00:00:00") == -1) 
			{
				try {
					newFecha = sf1.parse(act.getHora().trim());
					logger.debug("De string a Date: " + newFecha);
				} catch (ParseException ex) {
					//ex.printStackTrace();
					logger.debug("Error al convertir la fecha de String a Date");
				}

				if (newFecha != null) 
				{
					diferencia = fechasDiferenciaEnDias(act.getFechaActividad(),deStringToDate(getFechaActual()));

					diferenciaFin = fechasDiferenciaEnDias(deStringToDate(getFechaActual()),act.getFechaVencimiento());
					
					logger.debug("Diferencia fecha actividad con fecha actual:" + diferencia);
					logger.debug("Diferencia fecha vencimiento con fecha actual: " + diferenciaFin);
					
					if (diferencia > 0 && diferenciaFin > 0) 
					{
						Calendar cal = Calendar.getInstance();
						cal.setTime(newFecha);
						cal.add(Calendar.DAY_OF_YEAR, diferencia);
						/*
						 * TimerTask timerTask = new TimerTask() { public void
						 * run() {
						 * 
						 * } };
						 */
						fechaNueva = cal.getTime();
						/*
						 * Timer timer = new Timer();
						 * timer.scheduleAtFixedRate(timerTask
						 * ,getTomorrowMorning12am(), fONCE_PER_DAY);
						 */
						logger.debug("Fecha a evaluar: " + fechaNueva);
						defaultEvent = new DefaultScheduleEvent(textoEvento,aumentarFechaxFeriado(fechaNueva), aumentarFechaxFeriado(fechaNueva));
					} 
					else 
					{
						logger.debug("Fecha a evaluar: " + newFecha);
						defaultEvent = new DefaultScheduleEvent(textoEvento,aumentarFechaxFeriado(newFecha), aumentarFechaxFeriado(newFecha));
					}

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
			} 
			else 
			{
				logger.debug("Formato de hora incorrecto. La hora no puede ser 00:00:00!!");
			}

		}
		logger.debug("Lista eventos despues de buscar:" + agendaModel.getEvents().size());
		limpiarSessionUsuario();
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
	
	/*private String queryColor(int modo) {
		String cadena = "";

		if (modo == 1) {
			cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE "
					+ "WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' "
					+ "THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=null and id_proceso=null) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=null and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='R' AND id_via=vi.id_via and id_proceso=null) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='A' AND id_via=vi.id_via and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= "
					+ "(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND  DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= 3 THEN 'R' "
					+ "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1) >=(a.plazo_ley/2) AND "
					+ "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "
					+ "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1)<=(a.plazo_ley/2) AND "
					+ "DAYS(SYSDATE,a.fecha_vencimiento)<=a.plazo_ley THEN 'A' "
					+ "END "
					+ "END "
					+ "END "
					+ "END "
					+ "END "
					+ "ELSE "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END " + "END END AS COLOR ";
		} else {
			cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE "
					+ "WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' "
					+ "THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=null and id_proceso=null) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=null and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='R' AND id_via=vi.id_via and id_proceso=null) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='A' AND id_via=vi.id_via and id_proceso=null) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= "
					+ "(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE WHEN NVL( "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND  DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END,' ') = ' ' THEN "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0 AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= 3 THEN 'R' "
					+ "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1) >=(a.plazo_ley/2) AND "
					+ "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "
					+ "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1)<=(a.plazo_ley/2) AND "
					+ "DAYS(SYSDATE,a.fecha_vencimiento)<=a.plazo_ley THEN 'A' "
					+ "END "
					+ "END "
					+ "END "
					+ "END "
					+ "END "
					+ "ELSE "
					+ "CASE "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "
					+ "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "
					+ "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "
					+ "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "
					+ "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "
					+ "END " + "END END = ";
		}
		return cadena;
	}*/

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
			GenericDao<Expediente, Object> expDAO = (GenericDao<Expediente, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
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
						logger.debug("No se actualizo la actividad procesal!");
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
			//ex.printStackTrace();
			logger.debug("Error al convertir la fecha de String a Date");
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

	public void onDateSelect(DateSelectEvent selectEvent) {
		evento = new DefaultScheduleEvent("", selectEvent.getDate(),
				selectEvent.getDate());
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
}
