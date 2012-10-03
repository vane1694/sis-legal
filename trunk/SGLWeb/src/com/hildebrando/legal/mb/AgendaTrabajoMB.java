package com.hildebrando.legal.mb;

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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.Query;
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
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Usuario;

@ManagedBean(name = "agendaTrab")
@SessionScoped
public class AgendaTrabajoMB {
	private List<Organo> organos;
	// private BusquedaActProcesal selectedBusquedaActProcesal;
	private ScheduleModel agendaModel;
	private ScheduleEvent evento= new DefaultScheduleEvent();
	public static Logger logger = Logger.getLogger(AgendaTrabajoMB.class);
	private String color;
	private String busNroExpe;
	private Date fechaActualDate;
	private List<ActividadxExpediente> resultado;
	private List<Usuario> responsables;
	private String nroExpediente;
	private String actividad;
	private String instancia;
	private String idOrgano;
	private String idPrioridad;
	private int idResponsable;
	private String observacion="";
	private Involucrado demandante;
	private Boolean bConDatos;
	
	@SuppressWarnings("unchecked")
	public AgendaTrabajoMB() 
	{
		super();
		
		//Se abre la session en caso de este cerrada
		if (!SpringInit.devolverSession().isOpen())
		{
			SpringInit.openSession();
		}
		
		//Aqui se inicia el modelo de la agenda.
		agendaModel = new DefaultScheduleModel();
		
		setbConDatos(false);
		llenarAgenda();
		
		
		//Aqui se llena el combo de organos
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Organo.class);

		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//Aqui se llena el combo de responsables
		llenarResponsables();
		setObservacion("");
	}
	
	public List<Involucrado> completeDemandante(String query) 
	{
		List<Involucrado> results = new ArrayList<Involucrado>();

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Involucrado.class);
		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Involucrado inv : involucrados) {

			if (inv.getPersona().getNombreCompleto().toUpperCase()
					.contains(query.toUpperCase()) && inv.getRolInvolucrado().getIdRolInvolucrado()==2) {
				results.add(inv);
			}
		}

		return results;
	}


	public ScheduleModel llenarAgenda()
	{
		agendaModel = new DefaultScheduleModel();
		if (agendaModel != null && !bConDatos) 
		{
			//"where c.id_usuario = 4 " +
			String queryActividad="select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID," +
					 "c.numero_expediente,ins.nombre as instancia,concat(concat(fecha_actividad,' '),to_char(hora,'HH24:MI:SS')) as hora," +
					 "org.nombre as organo, act.nombre as Actividad,a.fecha_actividad,a.fecha_vencimiento,a.fecha_atencion, " +  queryColor(1) +
					 "from actividad_procesal a " +
					 "join expediente c on a.id_expediente=c.id_expediente " +
					 "join involucrado inv on c.id_expediente=inv.id_expediente and inv.id_rol_involucrado=2" +
					 "join persona per on inv.id_persona=per.id_persona " +
					 "join organo org on c.id_organo = org.id_organo " +
					 "join actividad act on a.id_actividad = act.id_actividad " +
					 "join instancia ins on c.id_instancia=ins.id_instancia " +
					 "join via vi on ins.id_via = vi.id_via " +
					 "join proceso pro on vi.id_proceso = pro.id_proceso " +
					 "join usuario usu on c.id_usuario=usu.id_usuario " +
					 "where a.fecha_atencion is null "+
					 "order by c.numero_expediente";
			
			Query query = SpringInit.devolverSession().createSQLQuery(queryActividad)
					.addEntity(ActividadxExpediente.class);
			
			logger.debug("------------------------------------------------------");
			resultado = query.list();
			
			logger.debug("Query eventos agenda onLoad(): " + queryActividad);
			
			logger.debug("Tamaño lista resultados: " + resultado.size());
			
			String textoEvento="";
			for (ActividadxExpediente act : resultado)
			{
				
				textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha: " +act.getHora() + "\nOrgano: " + act.getOrgano() +
						"\nExpediente: " + act.getNroExpediente() + "\nInstancia: " + act.getInstancia();
				logger.debug("------------------------------------------------------");
				logger.debug("Creando los elementos para el calendario (Inicio)--------------");
				logger.debug("Nro Expediente: " + act.getNroExpediente());
				logger.debug("Instancia: " + act.getInstancia());
				logger.debug("Actividad: " + act.getActividad());
				logger.debug("Fecha Actividad: " + act.getFechaActividad());
				logger.debug("Fecha Vencimiento: " + act.getFechaVencimiento());
				logger.debug("Texto Evento: " + textoEvento);
				logger.debug("Hora Actividad: " +  act.getHora());
				logger.debug("Color Fila: " + act.getColorFila());
				logger.debug("------------------------------------------------------");
				
				SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		        sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		        
		        Date newFecha=null;
			    try {
					newFecha = sf1.parse(act.getHora());
					logger.debug("De string a Date: " + newFecha);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		        
			       
			    logger.debug("------------------------------------------------------");
		        
		        if (newFecha!=null)
		        {
		        	DefaultScheduleEvent defaultEvent = new DefaultScheduleEvent(textoEvento,newFecha, newFecha);
		        	defaultEvent.setStyleClass("custom");
		        	agendaModel.addEvent(defaultEvent);
		        }
		     
			}
			setbConDatos(true);
		}
		return agendaModel;
	}
	
	public void addEvent(ActionEvent actionEvent) {
		if (evento.getId() == null)
			agendaModel.addEvent(evento);
		else
			agendaModel.updateEvent(evento);

		//evento = new DefaultScheduleEvent();
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		evento=selectEvent.getScheduleEvent();
		
		if (evento!=null)
		{
			if (!evento.getTitle().equals(""))
			{
				for (ActividadxExpediente act : resultado)
				{
					if (evento.getTitle().contains(act.getNroExpediente()) && evento.getTitle().contains(act.getActividad()))
					{
						nroExpediente=act.getNroExpediente();
						actividad=act.getActividad();
					}
				}
			}
		}
		else
		{
			logger.debug("Evento nulo!!!!!!!!!!!!!!!!!!!");
		}
		
		//evento = selectEvent.getScheduleEvent();
	}

	
	public String verExpe(){
		
		
		return null;
		
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public void buscarEventosAgenda(ActionEvent e) 
	{
		agendaModel = new DefaultScheduleModel();		
		String filtro ="";
		setbConDatos(true);
		/*logger.debug("Condicion Antes: " + bConDatos);
		logger.debug("Condicion Antes: " + bConDatos);*/
		
		if (demandante!=null)
		{
			if (filtro.length()>0)
			{
				filtro += " and inv.id_involucrado = " + demandante.getIdInvolucrado() + " and inv.id_rol_involucrado=2";
			}
			else
			{
				filtro += "where inv.id_involucrado = " + demandante.getIdInvolucrado() + " and inv.id_rol_involucrado=2";
			}
		}
		
		//Se aplica filtro a la busqueda por Numero de Expediente
		if (getBusNroExpe()!=null && !getBusNroExpe().equals(""))
		{
			if (filtro.length()>0)
			{
				filtro += " and c.numero_expediente = " + "'" + getBusNroExpe()
						+ "'";
			}
			else
			{
				filtro += "where c.numero_expediente = " + "'"
						+ getBusNroExpe() + "'";
			}
		}
		
		//Se aplica filtro a la busqueda por Organo
		if (getIdOrgano() != null && !getIdOrgano().equals(""))
		{
			if (filtro.length()>0)
			{
				filtro += " and org.codigo=" + getIdOrgano();
			}
			else
			{
				filtro += "where org.codigo=" + getIdOrgano();
			}
		}
		
		//Se aplica filtro a la busqueda por Responsable
		if (getIdResponsable()!=0)
		{
			if (filtro.length()>0)
			{
				filtro += " and c.id_usuario = " + getIdResponsable();
			}
			else
			{
				filtro += "where c.id_usuario = " + getIdResponsable();
			}
		}
		
		//Se aplica filtro a la busqueda por Prioridad: Rojo, Amarillo, Naranja y Verde
		if (getIdPrioridad()!=null && getIdPrioridad()!="")
		{
			if (filtro.length()>0)
			{
				filtro += " and " + queryColor(2) + "'"+getIdPrioridad()+"'";
			}
			else
			{
				filtro += "where " + queryColor(2) + "'"+getIdPrioridad()+"'";
			}
		}
		
		if (filtro.trim().length()>0) 
		{
			filtro +=" and a.fecha_atencion is null ";
		}
		else
		{
			filtro ="where a.fecha_atencion is null ";
		}
		
		logger.debug("Filtro: " + filtro);
		
		String queryActividad="select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID," +
				"c.numero_expediente,ins.nombre as instancia,concat(concat(fecha_actividad,' '),to_char(hora,'HH24:MI:SS')) as hora," +
				"org.nombre as organo, act.nombre as Actividad,a.fecha_actividad,a.fecha_vencimiento,a.fecha_atencion, " + 
				 queryColor(1) +
				"from actividad_procesal a " +
				"join expediente c on a.id_expediente=c.id_expediente " +
				 "join involucrado inv on c.id_expediente=inv.id_expediente " +
				 "join persona per on inv.id_persona=per.id_persona " +
				 "join organo org on c.id_organo = org.id_organo " +
				 "join actividad act on a.id_actividad = act.id_actividad " +
				 "join instancia ins on c.id_instancia=ins.id_instancia " +
				 "join via vi on ins.id_via = vi.id_via " +
				 "join proceso pro on vi.id_proceso = pro.id_proceso " +
				 "join usuario usu on c.id_usuario=usu.id_usuario " +
				 filtro + 
				 " order by c.numero_expediente";
		
		Query query = SpringInit.devolverSession().createSQLQuery(queryActividad)
				.addEntity(ActividadxExpediente.class);
		
		logger.debug("Query Eventos: " + queryActividad);
		logger.debug("------------------------------------------------------");
		resultado = query.list();
		
		String textoEvento ="";
		for (ActividadxExpediente act : resultado)
		{
			//String cad = act.getFechaActividad().toString().concat(" ").concat(act.getHora());
			//logger.debug("Lista eventos antes de depurar:" + agendaModel.getEvents().size());
			textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha: " +act.getHora() + "\nOrgano: " + act.getOrgano() +
					"\nExpediente: " + act.getNroExpediente() + "\nInstancia: " + act.getInstancia();
			logger.debug("------------------------------------------------------");
			logger.debug("Creando los elementos para el calendario--------------");
			logger.debug("Nro Expediente: " + act.getNroExpediente());
			logger.debug("Instancia: " + act.getInstancia());
			logger.debug("Actividad: " + act.getActividad());
			logger.debug("Fecha Actividad: " + act.getFechaActividad());
			logger.debug("Fecha Vencimiento: " + act.getFechaVencimiento());
			logger.debug("Tamanio Texto Evento: " + textoEvento.length());
			logger.debug("Hora Actividad: " +  act.getHora());
			logger.debug("Color Fila: " +act.getColorFila());
			logger.debug("------------------------------------------------------");
			
			SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	        sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
	        
	        //String fecha = sf1.format(deStringToDate(act.getHora(), "yyyy-MM-dd HH:mm:ss"));
	        //logger.debug("Fecha con nuevo formato: "+ sf1.format());
	        Date newFecha=null;
		    
	        try {
				newFecha = sf1.parse(act.getHora());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.debug("De string a Date: " + newFecha);
			
		       
	        logger.debug("------------------------------------------------------");
	        
	        if (newFecha!=null)
	        {
	        	DefaultScheduleEvent defaultEvent = new DefaultScheduleEvent(textoEvento,newFecha, newFecha);
	        	
	        	if (act.getColorFila().equals("V"))
	        	{
	        		defaultEvent.setStyleClass("eventoVerde");
	        	}
	        	if (act.getColorFila().equals("A"))
	        	{
	        		defaultEvent.setStyleClass("eventoAmarillo");
	        	}
	        	if (act.getColorFila().equals("N"))
	        	{
	        		defaultEvent.setStyleClass("eventoNaranja");
	        	}
	        	if (act.getColorFila().equals("R"))
	        	{
	        		defaultEvent.setStyleClass("eventoRojo");
	        	}
	        	agendaModel.addEvent(defaultEvent);
	        }
	       
		}
		logger.debug("Lista eventos despues de depurar:" + agendaModel.getEvents().size());
		//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	private String queryColor(int modo)
	{
		String cadena="";
		
		if (modo==1)
		{
			cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE " +
					"WHEN NVL( " +
				    "CASE " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				    "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				    "END,' ') = ' ' "+ 
				    "THEN " + 
				      "CASE WHEN NVL( " +
				        "CASE " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=null and id_proceso=null) THEN 'R' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				        "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+
				        "END,' ') = ' ' THEN " +
				          "CASE WHEN NVL( " +
				          "CASE " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='R' AND id_via=vi.id_via and id_proceso=null) THEN 'R' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " + 
				          "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				          "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='A' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				          "END,' ') = ' ' THEN " + 
				            "CASE WHEN NVL( "+ 
				            "CASE "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= "+  
				            "(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+  
				            "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				            "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				            "END,' ') = ' ' THEN "+  
				              "CASE WHEN NVL( "+ 
				                  "CASE "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				                    "AND  DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				                    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				                  "END,' ') = ' ' THEN "+  
				                  "CASE "+ 
				                  "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0 AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= 3 THEN 'R' "+ 
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1) >=(a.plazo_ley/2) AND "+  
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "+ 
			                      "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "+ 
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+  
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1)<=(a.plazo_ley/2) AND "+ 
			                         "DAYS(SYSDATE,a.fecha_vencimiento)<=a.plazo_ley THEN 'A' "+ 
				                  "END "+ 
				              "END "+ 
				             "END "+ 
				          "END "+ 
				    "END "+ 
				"ELSE "+ 
						"CASE "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+  
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
							"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
						"END "+ 
				"END END AS COLOR " ;
		}
		else
		{
			cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE " +
					"WHEN NVL( " +
				    "CASE " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				    "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				    "END,' ') = ' ' "+ 
				    "THEN " + 
				      "CASE WHEN NVL( " +
				        "CASE " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=null and id_proceso=null) THEN 'R' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				        "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+
				        "END,' ') = ' ' THEN " +
				          "CASE WHEN NVL( " +
				          "CASE " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='R' AND id_via=vi.id_via and id_proceso=null) THEN 'R' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " + 
				          "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				          "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='A' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				          "END,' ') = ' ' THEN " + 
				            "CASE WHEN NVL( "+ 
				            "CASE "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= "+  
				            "(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+  
				            "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				            "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				            "END,' ') = ' ' THEN "+  
				              "CASE WHEN NVL( "+ 
				                  "CASE "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				                    "AND  DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				                    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				                  "END,' ') = ' ' THEN "+  
				                  "CASE "+ 
				                  "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0 AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= 3 THEN 'R' "+ 
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1) >=(a.plazo_ley/2) AND "+  
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "+ 
			                      "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "+ 
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+  
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1)<=(a.plazo_ley/2) AND "+ 
			                         "DAYS(SYSDATE,a.fecha_vencimiento)<=a.plazo_ley THEN 'A' "+ 
				                  "END "+ 
				              "END "+ 
				             "END "+ 
				          "END "+ 
				    "END "+ 
				"ELSE "+ 
						"CASE "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+  
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
							"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
						"END "+ 
				"END END = " ;
		}
		return cadena;
	}

	public Date modifDate(int dias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dias);

		return cal.getTime();

	}
	
	@SuppressWarnings("unchecked")
	public void actualizarFechaAtencion()
	{
		if (nroExpediente!=null && nroExpediente!=""
			&& actividad!=null && actividad!="")
		{
			//Busqueda de idExpediente por el numero de expediente del evento seleccionado
			long idExpediente=0;
			ActividadProcesal actProc = new ActividadProcesal();
			
			List<Expediente> result = new ArrayList<Expediente>();
			GenericDao<Expediente, Object> expDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Expediente.class);
			
			try {
				result = expDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (Expediente expd : result) {

				if (expd.getNumeroExpediente().equals(nroExpediente)) {
					idExpediente= expd.getIdExpediente();
					break;
				}
			}

			//Busqueda de idActividad por la actividad del evento seleccionado
			long idActividad=0;
			List<Actividad> result2 = new ArrayList<Actividad>();
			GenericDao<Actividad, Object> actProDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro2 = Busqueda.forClass(Actividad.class);
			
			try {
				result2 = actProDAO.buscarDinamico(filtro2);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (Actividad soloAct : result2) {

				if (soloAct.getNombre().equals(actividad)) {
					idActividad= soloAct.getIdActividad();
					break;
				}
			}
			
			
			GenericDao<ActividadProcesal, Object> actividadDAO = (GenericDao<ActividadProcesal, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			List<ActividadProcesal> result3 = new ArrayList<ActividadProcesal>();
			Busqueda filtro3 = Busqueda.forClass(ActividadProcesal.class);
			
			try {
				result3 = actividadDAO.buscarDinamico(filtro3);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (ActividadProcesal actProcesal : result3) {

				if (actProcesal.getActividad().getIdActividad()==idActividad && actProcesal.getExpediente().getIdExpediente()==idExpediente) {
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
						
						e.printStackTrace();
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No se actualizo la fecha de atencion de la actividad procesal"));
						logger.debug("No se actualizo la actividad procesal!");
					}
					
					break;
				}
			}
		}
	}
	
	public void limpiarDatos()
	{
		fechaActualDate=modifDate(0);
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

	/*public String definirColorFila(Date fechaVencimiento, Date fechaInicio,
			int plazoLey, int numeroDiasxVencer, int numeroDiasAlerta,
			int numeroDiasRojo) {

		String colorFila = "";
		
		logger.debug("--------------------------------------------------");
		logger.debug("Parametros ingresados al metodo <definirColorFila>");
		logger.debug("--------------------------------------------------");
		logger.debug("Fec Ven: " + fechaVencimiento);
		logger.debug("Fec Ini: " + fechaInicio);
		logger.debug("Plazo Ley: " + plazoLey);
		logger.debug("Dias x Vencer: " + numeroDiasxVencer);
		logger.debug("Dias Alerta: " + numeroDiasAlerta);
		logger.debug("Dias Vencidas: " + numeroDiasRojo);
		
		int diferenciaVen = fechasDiferenciaEnDias(
				deStringToDate(getFechaActual()),fechaVencimiento);
		logger.debug("Diferencia en dias con fecha vencida: "
				+ diferenciaVen);
		int diferenciaInic = fechasDiferenciaEnDias(
				fechaInicio,deStringToDate(getFechaActual()));
		logger.debug("Diferencia en dias con fecha inicio: "
				+ diferenciaInic);

		if (numeroDiasxVencer==0 && numeroDiasAlerta==0 && numeroDiasRojo==0)
		{
			int mitadTranscurrida=Integer.valueOf((plazoLey/2));
			SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	        sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
			
			//logger.debug("Fecha Inicio Emplazada:" + sf1.format(sumaDias(fechaInicio, plazoLey)));
	        logger.debug("Mitad transcurrida: " + mitadTranscurrida);
			
			// parametro definido en el mantenimiento de estados
			if (diferenciaVen <= 0 && (diferenciaVen*-1) <= plazoLey && (diferenciaVen*-1) <= 3) {
				colorFila = "R";
			}
			if (diferenciaVen <= 3 && diferenciaVen<=plazoLey) {
				colorFila = "N";
			}
			if (diferenciaInic==0 && diferenciaVen<=plazoLey) {
				colorFila = "V";
			}
			if (diferenciaInic <= mitadTranscurrida && diferenciaVen<=plazoLey) {
				colorFila = "A";
			}
		}
		else
		{
			if (diferenciaVen <= 0 && (diferenciaVen*-1) <= plazoLey && (diferenciaVen*-1) <= numeroDiasRojo) {
				colorFila = "R";
			}
			if (diferenciaVen <= numeroDiasxVencer && diferenciaVen<=plazoLey) {
				colorFila = "N";
			}
			if (diferenciaInic==0 && diferenciaVen<=plazoLey) {
				colorFila = "V";
			}
			if (diferenciaVen > numeroDiasxVencer && diferenciaVen <= numeroDiasAlerta && diferenciaVen<=plazoLey) {
				colorFila = "A";
			}
			
		}
		logger.debug("--------------------------------------------------");

		return colorFila;
	}*/
	
	@SuppressWarnings("unchecked")
	public void llenarResponsables() 
	{			
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Usuario.class);
		
		try {
			responsables = usuarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
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
			ex.printStackTrace();
			return null;
		}
	}
	
	public static Date sumaDias(Date fecha, int dias){ 
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(fecha); 
		cal.add(Calendar.DATE, dias+1); 
		return cal.getTime(); 
	} 
	
	public static synchronized java.util.Date deStringToDate(String fecha, String formato) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat(formato);
		//formatoDelTexto.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		Date fechaEnviar = null;
		try {
			fechaEnviar = formatoDelTexto.parse(fecha);
			return fechaEnviar;
		} catch (ParseException ex) {
			ex.printStackTrace();
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
	public Boolean getbConDatos() {
		return bConDatos;
	}

	public void setbConDatos(Boolean bConDatos) {
		this.bConDatos = bConDatos;
	}

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

	public String getIdOrgano() {
		return idOrgano;
	}

	public void setIdOrgano(String idOrgano) {
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
		evento = new DefaultScheduleEvent("", selectEvent.getDate(),selectEvent.getDate());
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
}
