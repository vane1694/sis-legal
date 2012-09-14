package com.hildebrando.legal.mb;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.ActividadxExpediente;
import com.hildebrando.legal.modelo.Aviso;
import com.hildebrando.legal.modelo.BusquedaActProcesal;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.view.BusquedaActividadProcesalDataModel;

@ManagedBean(name = "agendaTrab")
@SessionScoped
public class AgendaTrabajoMB {
	private List<Organo> organos;
	private BusquedaActProcesal busquedaProcesal;
	private BusquedaActividadProcesalDataModel resultadoBusqueda;
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
	private String idResponsable;
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
		busquedaProcesal = new BusquedaActProcesal();
		
		
		resultadoBusqueda = new BusquedaActividadProcesalDataModel(new ArrayList<BusquedaActProcesal>());
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
		if (agendaModel != null && !bConDatos) 
		{
			/*
			agendaModel.addEvent(new DefaultScheduleEvent("Contestacion ",
					modifDate(-1), modifDate(-1)));
			agendaModel.addEvent(new DefaultScheduleEvent(
					"Audiencia Proceso Penal", modifDate(0), modifDate(0)));
			agendaModel.addEvent(new DefaultScheduleEvent("Sistema Gestion",
					modifDate(1), modifDate(1)));
			agendaModel.addEvent(new DefaultScheduleEvent(
					"Tachas y Excepciones", modifDate(1), modifDate(3)));
			*/
			//SpringInit.openSession();
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
					"order by c.numero_expediente";
			
			Query query = SpringInit.devolverSession().createSQLQuery(queryActividad)
					.addEntity(ActividadxExpediente.class);
			
			System.out.println("------------------------------------------------------");
			resultado = query.list();
			
			System.out.println("Query eventos agenda onLoad(): " + queryActividad);
			
			System.out.println("Tamaño lista resultados: " + resultado.size());
			
			String textoEvento="";
			for (ActividadxExpediente act : resultado)
			{
				
				//String cad = act.getFechaActividad().toString().concat(" ").concat(act.getHora());
				textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha: " +act.getHora() + "\nOrgano: " + act.getOrgano() +
						"\nExpediente: " + act.getNroExpediente() + "\nInstancia: " + act.getInstancia();
				System.out.println("------------------------------------------------------");
				System.out.println("Creando los elementos para el calendario (Inicio)--------------");
				System.out.println("Nro Expediente: " + act.getNroExpediente());
				System.out.println("Instancia: " + act.getInstancia());
				System.out.println("Actividad: " + act.getActividad());
				System.out.println("Fecha Actividad: " + act.getFechaActividad());
				System.out.println("Fecha Vencimiento: " + act.getFechaVencimiento());
				System.out.println("Texto Evento: " + textoEvento);
				System.out.println("Hora Actividad: " +  act.getHora());
				System.out.println("Color Fila: " + act.getColorFila());
				System.out.println("------------------------------------------------------");
				
				SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		        sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		        
		        //String fecha = sf1.format(deStringToDate(act.getHora(), "yyyy-MM-dd HH:mm:ss"));
		        //System.out.println("Fecha con nuevo formato: "+ sf1.format());
		        Date newFecha=null;
			    try {
					newFecha = sf1.parse(act.getHora());
					System.out.println("De string a Date: " + newFecha);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		        
			       
		        System.out.println("------------------------------------------------------");
		        
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

	public BusquedaActProcesal getBusquedaProcesal() {
		return busquedaProcesal;
	}

	public void setBusquedaProcesal(BusquedaActProcesal busquedaProcesal) {
		this.busquedaProcesal = busquedaProcesal;
	}

	public BusquedaActividadProcesalDataModel getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	public void setResultadoBusqueda(BusquedaActividadProcesalDataModel resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}
 
	 
	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		evento=selectEvent.getScheduleEvent();
		
		if (evento!=null)
		{
			if (!evento.getTitle().equals(""))
			{
				System.out.println("-----------------------------------");
				System.out.println("Desde el event onEventSelect");
				System.out.println("-----------------------------------");
				System.out.println("Tamanio: " + resultado.size());
				System.out.println("-----------------------------------");
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
			System.out.println("Evento nulo!!!!!!!!!!!!!!!!!!!");
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("Parametros configurados:");
		System.out.println("-------------------------------------------");
		System.out.println("Expediente: " + nroExpediente);
		System.out.println("Instancia: " + instancia);
		System.out.println("Actividad: " + actividad);
		
		//evento = selectEvent.getScheduleEvent();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void buscarExpediente(ActionEvent e) 
	{
		String filtro ="";
		
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
				filtro += " and c.numero_expediente = " + "'"+getBusNroExpe()+"'";
			}
			else
			{
				filtro += "where c.numero_expediente = " + "'" +getBusNroExpe()+ "'";
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
		if (getIdResponsable()!=null && !getIdResponsable().equals(""))
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
		
		System.out.println("Filtro adicional: " + filtro);
		
		String hql = "select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID," +
					 "c.numero_expediente,per.nombre_completo as Demandante," +
					 "org.nombre as Organo,a.hora,act.nombre as Actividad,usu.nombre_completo as Responsable,a.fecha_actividad," +
					 "a.fecha_vencimiento,a.fecha_atencion,a.observacion,pro.id_proceso,vi.id_via," +
					 "act.id_actividad,c.id_instancia,c.id_expediente,a.plazo_ley, " +
					 queryColor(1) +
					 "from actividad_procesal a " +
					 "left outer join expediente c on a.id_expediente=c.id_expediente " +
					 "left outer join involucrado inv on c.id_expediente=inv.id_expediente " +
					 "left outer join persona per on inv.id_persona=per.id_persona " +
					 "left outer join organo org on c.id_organo = org.id_organo " +
					 "left outer join actividad act on a.id_actividad = act.id_actividad " +
					 "left outer join instancia ins on c.id_instancia=ins.id_instancia " +
					 "left outer join via vi on ins.id_via = vi.id_via " +
					 "left outer join proceso pro on vi.id_proceso = pro.id_proceso " +
					 "left outer join usuario usu on c.id_usuario=usu.id_usuario " +
					 filtro +
					 " order by c.numero_expediente,per.nombre_completo";
		
		System.out.println("Query Busqueda: " + hql);
		
		Query query3 = SpringInit.devolverSession().createSQLQuery(hql)
				.addEntity(BusquedaActProcesal.class);
		List<BusquedaActProcesal> resultado = new ArrayList<BusquedaActProcesal>();
		resultado.clear();
		resultado = query3.list();

		List<Aviso> aviso = new ArrayList<Aviso>();
		
		/*for (BusquedaActProcesal bus : resultado) 
		{
			System.out.println("-------------------Filtrando por expediente--------------------------");
			System.out.println("Expediente: " + bus.getNroExpediente());
			
			System.out.println("---------------------Resultado final del filtro---------------------");
			
			int diasxVencer = 0;
			int diasAlerta = 0;
			int diasVencid = 0;
			
			if (av.getActividad().getIdActividad()==bus.getId_actividad())
			{
				if (av.getColor().toString().trim().equals("N")) {
						diasxVencer = av.getDias();
				}
				
				if (av.getColor().toString().trim().equals("A")) {
						diasAlerta = av.getDias();
				}
					
				if (av.getColor().toString().trim().equals("R")) {
						diasVencid = av.getDias();
				}
			}	
			
			System.out.println("Id Actividad :" + bus.getId_actividad());
			
			String colorAPintar = definirColorFila(
					bus.getFechaVencimiento(), bus.getFechaActividad(),
					Integer.valueOf(bus.getPlazo_ley()), diasxVencer,
					diasAlerta, diasVencid);

			
			System.out.println("Color calculado por fila: "	+ colorAPintar);
			bus.setColorFila(colorAPintar);
			
			System.out.println("------------------------------------------------------------");

		}*/

		resultadoBusqueda = new BusquedaActividadProcesalDataModel(resultado);
		// SpringInit.closeSession();

		//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	public String verExpe(){
		
		
		return null;
		
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public void buscarEventosAgenda(ActionEvent e) 
	{
		
		agendaModel = new DefaultScheduleModel();
		
		
		String filtro ="";
		System.out.println("Condicion Antes: " + bConDatos);
		setbConDatos(true);
		System.out.println("Condicion Antes: " + bConDatos);
		
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
				filtro += " and c.numero_expediente = " + getBusNroExpe();
			}
			else
			{
				filtro += "where c.numero_expediente = " + getBusNroExpe();
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
		if (getIdResponsable()!=null && !getIdResponsable().equals(""))
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
		
		System.out.println("Filtro: " + filtro);
		
		
		String queryActividad="select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID," +
				"c.numero_expediente,ins.nombre as instancia,concat(concat(fecha_actividad,' '),to_char(hora,'HH24:MI:SS')) as hora," +
				"org.nombre as organo, act.nombre as Actividad,a.fecha_actividad,a.fecha_vencimiento,a.fecha_atencion, " + 
				 queryColor(1) +
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
				 filtro +
				 " order by c.numero_expediente";
		
		Query query = SpringInit.devolverSession().createSQLQuery(queryActividad)
				.addEntity(ActividadxExpediente.class);
		
		System.out.println("Query Eventos: " + queryActividad);
		System.out.println("------------------------------------------------------");
		resultado = query.list();
		
		String textoEvento ="";
		for (ActividadxExpediente act : resultado)
		{
			//String cad = act.getFechaActividad().toString().concat(" ").concat(act.getHora());
			//System.out.println("Lista eventos antes de depurar:" + agendaModel.getEvents().size());
			textoEvento = "\nAsunto: " + act.getActividad() + "\nFecha: " +act.getHora() + "\nOrgano: " + act.getOrgano() +
					"\nExpediente: " + act.getNroExpediente() + "\nInstancia: " + act.getInstancia();
			System.out.println("------------------------------------------------------");
			System.out.println("Creando los elementos para el calendario--------------");
			System.out.println("Nro Expediente: " + act.getNroExpediente());
			System.out.println("Instancia: " + act.getInstancia());
			System.out.println("Actividad: " + act.getActividad());
			System.out.println("Fecha Actividad: " + act.getFechaActividad());
			System.out.println("Fecha Vencimiento: " + act.getFechaVencimiento());
			System.out.println("Tamanio Texto Evento: " + textoEvento.length());
			System.out.println("Hora Actividad: " +  act.getHora());
			System.out.println("Color Fila: " +act.getColorFila());
			System.out.println("------------------------------------------------------");
			
			SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	        sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
	        
	        //String fecha = sf1.format(deStringToDate(act.getHora(), "yyyy-MM-dd HH:mm:ss"));
	        //System.out.println("Fecha con nuevo formato: "+ sf1.format());
	        Date newFecha=null;
		    
	        try {
				newFecha = sf1.parse(act.getHora());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			System.out.println("De string a Date: " + newFecha);
			
		       
	        System.out.println("------------------------------------------------------");
	        
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
		System.out.println("Lista eventos despues de depurar:" + agendaModel.getEvents().size());
		//}
		
		//return agendaModel;
	}
	
	private String queryColor(int modo)
	{
		String cadena="";
		
		if (modo==1)
		{
			cadena = "CASE " +
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
				                      "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 3 AND "+ 
				                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "+ 
				                      "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "+ 
				                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				                      "WHEN DAYS(a.fecha_actividad,SYSDATE)<=(a.plazo_ley/2) AND "+ 
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
				"END AS COLOR " ;
		}
		else
		{
			cadena = "CASE " +
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
				                      "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 3 AND "+ 
				                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "+ 
				                      "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "+ 
				                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				                      "WHEN DAYS(a.fecha_actividad,SYSDATE)<=(a.plazo_ley/2) AND "+ 
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
				"END = " ;
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
			
			//Actualizar fecha de atencion y observacion de la tabla Actividad Procesal
			String hql ="update actividad_procesal set fecha_atencion = :fecha" +
					 " and observacion = :obs"
					+ " where id_expediente = :idExpediente and id_actividad = :idActividad";
			
			System.out.println("Query Update: " + hql);
			Query quer2 = SpringInit.devolverSession().createSQLQuery(hql);
			quer2.setLong("idExpediente", idExpediente);
			quer2.setLong("idActividad",idActividad);
			quer2.setDate("fecha", fechaActualDate);
			quer2.setString("obs", observacion);
			
			int rowCount = quer2.executeUpdate();
	        System.out.println("Filas afectadas: " + rowCount);
			
			/*
			String hql = "update Supplier set name = :newName where name = :name";
	        Query query = session.createQuery(hql);
	        query.setString("name","Supplier Name 1");
	        query.setString("newName","s1");
	        int rowCount = query.executeUpdate();*/
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
	 * // SpringInit.openSession(); System.out.println("Lista de resultado: " +
	 * ((List<BusquedaActProcesal>) getResultadoBusqueda()
	 * .getWrappedData()).size()); // FacesContext context =
	 * FacesContext.getCurrentInstance(); ExternalContext context =
	 * FacesContext.getCurrentInstance() .getExternalContext(); // String bus=
	 * (String) context.getRequestMap().get("busNroExp"); // BusquedaActProcesal
	 * item = resultadoBusqueda.getRowData(); int id = (Integer) context.getr;
	 * System.out.println("id: " + id); HttpSession session = (HttpSession)
	 * context.getSession(true); session.setAttribute("selectedExpediente", id);
	 * 
	 * System.out.println("SelectedExpediente: " + id);
	 * 
	 * return "BusExpedienteReadOnly"; }
	 */

	@SuppressWarnings("unchecked")
	public void editarExpediente(SelectEvent event) {

		try {
			
			System.out.println(""+ ((List<BusquedaActProcesal>)getResultadoBusqueda().getWrappedData()).size());
			System.out.println("id: " + getBusquedaProcesal().getId_expediente());

//			FACESCONTEXT
//					.GETCURRENTINSTANCE()
//					.GETEXTERNALCONTEXT()
//					.REDIRECT(
//							"BUSEXPEDIENTEREADONLY.XHTML?ID="
//									+ GETBUSQUEDAPROCESAL().GETID_EXPEDIENTE());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String definirColorFila(Date fechaVencimiento, Date fechaInicio,
			int plazoLey, int numeroDiasxVencer, int numeroDiasAlerta,
			int numeroDiasRojo) {

		String colorFila = "";
		
		System.out.println("--------------------------------------------------");
		System.out.println("Parametros ingresados al metodo <definirColorFila>");
		System.out.println("--------------------------------------------------");
		System.out.println("Fec Ven: " + fechaVencimiento);
		System.out.println("Fec Ini: " + fechaInicio);
		System.out.println("Plazo Ley: " + plazoLey);
		System.out.println("Dias x Vencer: " + numeroDiasxVencer);
		System.out.println("Dias Alerta: " + numeroDiasAlerta);
		System.out.println("Dias Vencidas: " + numeroDiasRojo);
		
		int diferenciaVen = fechasDiferenciaEnDias(
				deStringToDate(getFechaActual()),fechaVencimiento);
		System.out.println("Diferencia en dias con fecha vencida: "
				+ diferenciaVen);
		int diferenciaInic = fechasDiferenciaEnDias(
				fechaInicio,deStringToDate(getFechaActual()));
		System.out.println("Diferencia en dias con fecha inicio: "
				+ diferenciaInic);

		if (numeroDiasxVencer==0 && numeroDiasAlerta==0 && numeroDiasRojo==0)
		{
			int mitadTranscurrida=Integer.valueOf((plazoLey/2));
			SimpleDateFormat sf1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	        sf1.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
			
			//System.out.println("Fecha Inicio Emplazada:" + sf1.format(sumaDias(fechaInicio, plazoLey)));
	        System.out.println("Mitad transcurrida: " + mitadTranscurrida);
			
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
		System.out.println("--------------------------------------------------");

		return colorFila;
	}
	
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

	public String getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(String idResponsable) {
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
