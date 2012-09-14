package com.hildebrando.legal.mb;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.LoadQueryInfluencers;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.SessionImpl;
import org.hibernate.loader.OuterJoinLoader;
import org.hibernate.loader.criteria.CriteriaLoader;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Aviso;
import com.hildebrando.legal.modelo.BusquedaActProcesal;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.view.BusquedaActividadProcesalDataModel;

@ManagedBean(name = "agendaTrab")
public class AgendaTrabajoMB {

	private List<Organo> organos;
	// private Expediente expediente;
	// private ExpedienteDataModel expedienteDataModel;
	private BusquedaActProcesal busquedaProcesal;
	private BusquedaActividadProcesalDataModel resultadoBusqueda;
	private ScheduleModel agendaModel;
	private ScheduleEvent evento;
	public static Logger logger = Logger.getLogger(AgendaTrabajoMB.class);
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@SuppressWarnings("unchecked")
	public List<Organo> getOrganos() {

		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Organo.class);

		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return organos;
	}

	public void setOrganos(List<Organo> organos) {
		this.organos = organos;
	}

	/*
	 * public ExpedienteDataModel getExpedienteDataModel() { if
	 * (expedienteDataModel == null) { List<Expediente> expediente = new
	 * ArrayList<Expediente>(); expedienteDataModel = new
	 * ExpedienteDataModel(expediente);
	 * 
	 * } return expedienteDataModel; }
	 * 
	 * public void setExpedienteDataModel(ExpedienteDataModel
	 * expedienteDataModel) { this.expedienteDataModel = expedienteDataModel; }
	 */

	public ScheduleModel getAgendaModel() {
		if (agendaModel == null) {

			agendaModel = new DefaultScheduleModel();

			agendaModel.addEvent(new DefaultScheduleEvent("Contestacion ",
					modifDate(-1), modifDate(-1)));
			agendaModel.addEvent(new DefaultScheduleEvent(
					"Audiencia Proceso Penal", modifDate(0), modifDate(0)));
			agendaModel.addEvent(new DefaultScheduleEvent("Sistema Gestion",
					modifDate(1), modifDate(1)));
			agendaModel.addEvent(new DefaultScheduleEvent(
					"Tachas y Excepciones", modifDate(1), modifDate(3)));

		}

		return agendaModel;

	}

	/*
	 * public Expediente getExpediente() { if (expediente == null) { expediente
	 * = new Expediente(); } return expediente; }
	 * 
	 * public void setExpediente(Expediente expediente) { this.expediente =
	 * expediente; }
	 */

	public void addEvent(ActionEvent actionEvent) {
		if (evento.getId() == null)
			agendaModel.addEvent(evento);
		else
			agendaModel.updateEvent(evento);

		evento = new DefaultScheduleEvent();
	}

	public BusquedaActProcesal getBusquedaProcesal() {
		if (busquedaProcesal == null) {
			busquedaProcesal = new BusquedaActProcesal();
		}
		return busquedaProcesal;
	}

	public void setBusquedaProcesal(BusquedaActProcesal busquedaProcesal) {
		this.busquedaProcesal = busquedaProcesal;
	}

	public BusquedaActividadProcesalDataModel getResultadoBusqueda() {
		if (resultadoBusqueda == null) {
			List<BusquedaActProcesal> resultado = new ArrayList<BusquedaActProcesal>();
			resultadoBusqueda = new BusquedaActividadProcesalDataModel(
					resultado);
		}
		return resultadoBusqueda;
	}

	public void setResultadoBusqueda(
			BusquedaActividadProcesalDataModel resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		evento = selectEvent.getScheduleEvent();
	}

	public void onDateSelect(DateSelectEvent selectEvent) {
		evento = new DefaultScheduleEvent("", selectEvent.getDate(),
				selectEvent.getDate());
	}

	public void setAgendaModel(ScheduleModel agendaModel) {
		this.agendaModel = agendaModel;
	}

	public ScheduleEvent getEvento() {
		if (evento == null) {
			evento = new DefaultScheduleEvent();

		}
		return evento;
	}

	public void setEvento(ScheduleEvent evento) {
		this.evento = evento;
	}

	/*
	 * public void buscarActProc(ActionEvent e) { List<ActividadProcesal>
	 * listProcesalBD = new ArrayList<ActividadProcesal>();
	 * 
	 * listProcesalBD .add(new ActividadProcesal("001", "EXP-1234-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "1")); listProcesalBD .add(new
	 * ActividadProcesal("002", "EXP-1235-2012", "Julio Correa Mellado",
	 * "Comisaria 1", "15:00", "Tachas u Excepciones", "12/04/2012",
	 * "18/04/2012", "", "", "2"));
	 * 
	 * listProcesalBD .add(new ActividadProcesal("003", "EXP-1236-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "3"));
	 * 
	 * listProcesalBD .add(new ActividadProcesal("004", "EXP-1237-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "1"));
	 * 
	 * listProcesalBD .add(new ActividadProcesal("005", "EXP-1238-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "2"));
	 * 
	 * listProcesalBD .add(new ActividadProcesal("006", "EXP-1239-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "3"));
	 * 
	 * listProcesalBD .add(new ActividadProcesal("007", "EXP-1240-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "1"));
	 * 
	 * listProcesalBD .add(new ActividadProcesal("008", "EXP-1241-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "2"));
	 * 
	 * listProcesalBD .add(new ActividadProcesal("009", "EXP-1242-2012",
	 * "Julio Correa Mellado", "Comisaria 1", "15:00", "Tachas u Excepciones",
	 * "12/04/2012", "18/04/2012", "", "", "3"));
	 * 
	 * procesalDataModel = new ActividadProcesalDataModel(listProcesalBD);
	 * 
	 * }
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	public void buscarExpediente(ActionEvent e) {
		SpringInit.openSession();

		/* Se buscan los registros de las actividades procesales por expediente. */
		/*
		 * List<BusquedaActProcesal> resultado = new
		 * ArrayList<BusquedaActProcesal>(); GenericDao<BusquedaActProcesal,
		 * Object> busActivProcesalDAO = (GenericDao<BusquedaActProcesal,
		 * Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		 * 
		 * Busqueda filtroActProc =
		 * Busqueda.forClass(BusquedaActProcesal.class);
		 * 
		 * try { resultado = busActivProcesalDAO.buscarDinamico(filtroActProc);
		 * } catch (Exception ex) { ex.printStackTrace(); }
		 */
		String hql = "select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID,"
				+ "c.numero_expediente,per.nombre_completo as Demandante,"
				+ "org.nombre as Organo,a.hora,act.nombre as Actividad,a.fecha_actividad,"
				+ "a.fecha_vencimiento,a.fecha_atencion,a.observacion,pro.id_proceso,vi.id_via,"
				+ "act.id_actividad,c.id_instancia,a.plazo_ley "
				+ "from actividad_procesal a "
				+ "left outer join hito b on a.id_hito=b.id_hito "
				+ "left outer join expediente c on b.id_expediente=c.id_expediente "
				+ "left outer join involucrado inv on c.id_expediente=inv.id_expediente "
				+ "left outer join persona per on inv.id_persona=per.id_persona "
				+ "left outer join organo org on c.id_organo = org.id_organo "
				+ "left outer join actividad act on a.id_actividad = act.id_actividad "
				+ "left outer join instancia ins on c.id_instancia=ins.id_instancia "
				+ "left outer join via vi on ins.id_via = vi.id_via "
				+ "left outer join proceso pro on vi.id_proceso = pro.id_proceso "
				+ "where c.id_usuario =2 " + "order by c.numero_expediente";

		Query query = SpringInit.devolverSession().createSQLQuery(hql)
				.addEntity(BusquedaActProcesal.class);
		List<BusquedaActProcesal> resultado = new ArrayList<BusquedaActProcesal>();
		resultado.clear();
		resultado = query.list();

		/*
		 * for (BusquedaActProcesal bus : resultado) { Se buscan los colores a
		 * pintar por fila en la agenda en la tabla Aviso. List<Aviso> aviso =
		 * new ArrayList<Aviso>(); GenericDao<Aviso, Object> avisoDAO =
		 * (GenericDao<Aviso, Object>)
		 * SpringInit.getApplicationContext().getBean("genericoDao"); int i=0;
		 * 
		 * Busqueda filtroAviso =
		 * Busqueda.forClass(Aviso.class).createAlias("actividad", "act")
		 * .add(Restrictions.eq("act.nombre", bus.getActividad()));
		 * 
		 * filtroAviso.createAlias("via",
		 * "via").add(Restrictions.eq("via.idVia", bus.getId_via()));
		 * filtroAviso.createAlias("proceso",
		 * "proc").add(Restrictions.eq("proc.idProceso", bus.getId_proceso()));
		 * 
		 * try { aviso = avisoDAO.buscarDinamico(filtroAviso); } catch
		 * (Exception ex) { ex.printStackTrace(); }
		 * 
		 * for (Aviso avis: aviso) { System.out.println("Codigo Actividad [" + i
		 * + "]" + avis.getActividad().getIdActividad());
		 * System.out.println("Color de Actividad [" + i + "]" +
		 * avis.getColor()); i++; } }
		 */

		/*
		 * List<Expediente> expedientes = new ArrayList<Expediente>();
		 * List<Involucrado> invs = new ArrayList<Involucrado>(); List<Hito>
		 * hitos = new ArrayList<Hito>(); List<ActividadProcesal> activProc =
		 * new ArrayList<ActividadProcesal>();
		 * 
		 * GenericDao<Expediente, Object> expedienteDAO =
		 * (GenericDao<Expediente, Object>)
		 * SpringInit.getApplicationContext().getBean("genericoDao");
		 * GenericDao<Involucrado, Object> involucradoDAO =
		 * (GenericDao<Involucrado, Object>)
		 * SpringInit.getApplicationContext().getBean("genericoDao");
		 * GenericDao<Hito, Object> hitoDAO = (GenericDao<Hito, Object>)
		 * SpringInit.getApplicationContext().getBean("genericoDao");
		 * GenericDao<ActividadProcesal, Object> activProcesalDAO =
		 * (GenericDao<ActividadProcesal, Object>)
		 * SpringInit.getApplicationContext().getBean("genericoDao");
		 */
		// Busqueda filtro = Busqueda.forClass(Expediente.class);

		/*
		 * .createCriteria(Hito.class.getName(), Busqueda.INNER_JOIN)
		 * .createCriteria(Expediente.class.getName(), Busqueda.INNER_JOIN)
		 * .add(Restrictions.eq("usuario.codigo", usu.getCodigo()))
		 * .setResultTransformer(Busqueda.DISTINCT_ROOT_ENTITY);
		 */

		/*
		 * try { expedientes = expedienteDAO.buscarDinamico(filtro); } catch
		 * (Exception ex) { ex.printStackTrace(); }
		 */

		/*
		 * List<Expediente> sublistExpe = new ArrayList<Expediente>();
		 * //List<Involucrado> filtroInv = new ArrayList<Involucrado>(); String
		 * nombres =""; String actividadesProc ="";
		 */

		/*
		 * String hql = "select c.numero_expediente, " +
		 * " per.nombre_completo as Demandante, org.nombre as Organo, a.hora,act.nombre as Actividad,a.fecha_actividad,a.fecha_vencimiento,"
		 * + " a.fecha_atencion,a.observacion " + " from actividad_procesal a" +
		 * " left outer join hito b on a.id_hito=b.id_hito" +
		 * " left outer join expediente c on b.id_expediente=c.id_expediente" +
		 * " left outer join involucrado inv on c.id_expediente=inv.id_expediente"
		 * + " left outer join persona per on inv.id_persona=per.id_persona" +
		 * " left outer join organo org on c.id_organo = org.id_organo" +
		 * " left outer join actividad act on a.id_actividad = act.id_actividad"
		 * + " where c.id_usuario =2" + " order by c.numero_expediente";
		 * 
		 * Query query =
		 * SpringInit.devolverSession().createSQLQuery(hql).addEntity
		 * (BusquedaActProcesal.class);
		 */

		// List<BusquedaActProcesal> resultado = query.list();
		// int i=0;
		List<Aviso> aviso = new ArrayList<Aviso>();
		List<Instancia> ins = new ArrayList<Instancia>();
		GenericDao<Aviso, Object> avisoDAO = (GenericDao<Aviso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Instancia, Object> insDAO = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		int i = 0;

		for (BusquedaActProcesal bus : resultado) {
			System.out
					.println("-------------------Filtrando por expediente--------------------------");
			System.out.println("Numero Expediente: " + bus.getNroExpediente());
			Busqueda filtroAviso = Busqueda
					.forClass(Aviso.class)
					.createAlias("actividad", "act")
					.add(Restrictions.eq("act.idActividad",
							bus.getId_actividad()));
			System.out
					.println("---------------------------------------------------------------------");
			// filtroAviso.createAlias("via",
			// "via").add(Restrictions.eq("via.idVia", bus.getId_via()));
			// filtroAviso.createAlias("proceso",
			// "proc").add(Restrictions.eq("proc.idProceso",
			// bus.getId_proceso()));

			try {
				aviso.clear();
				aviso = avisoDAO.buscarDinamico(filtroAviso);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (aviso.size() > 1) {
				System.out
						.println("-------------------Filtrando instancias----------------------------");
				// System.out.println("Parametros de busqueda Instancia: " +
				// bus.getId_instancia());
				Busqueda filtroInstancia = Busqueda.forClass(Instancia.class)
						.add(Restrictions.eq("idInstancia",
								bus.getId_instancia()));

				try {
					ins.clear();
					ins = insDAO.buscarDinamico(filtroInstancia);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				if (ins.size() == 1) {
					System.out
							.println("-------------------Filtrando vias------------------------------");
					filtroAviso.createAlias("via", "via").add(
							Restrictions.eq("via.idVia", ins.get(0).getVia()
									.getIdVia()));

					try {
						aviso.clear();
						aviso = avisoDAO.buscarDinamico(filtroAviso);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				// System.out.println("Tamaño final de lista luego del filtrado:"
				// + aviso.size());
				System.out.println("---------------------Resultado final del filtro---------------------");
				for (Aviso av : aviso) {
					int diasxVencer = 0;
					int diasAlerta = 0;
					int diasVencid = 0;
					if (av.getColor().toString().trim().equals("N")) {
						diasxVencer = av.getDias();
					}
					if (av.getColor().toString().trim().equals("A")) {
						diasAlerta = av.getDias();
					}
					if (av.getColor().toString().trim().equals("R")) {
						diasVencid = av.getDias();
					}
					String colorAPintar = definirColorFila(
							bus.getFechaVencimiento(), bus.getFechaActividad(),
							Integer.valueOf(bus.getPlazo_ley()), diasxVencer,
							diasAlerta, diasVencid);

					System.out.println("Id Actividad :" + bus.getId_actividad());
					System.out.println("Color definido por BD: "+ av.getColor());
					System.out.println("Valor: " + av.getDias());
					System.out.println("Color calculado por fila: " + colorAPintar);
					bus.setColorFila(colorAPintar);
				}
				System.out.println("------------------------------------------------------------");

			}
		}

		resultadoBusqueda = new BusquedaActividadProcesalDataModel(resultado);
		// resultadoBusqueda=null;
		/*
		 * for (Expediente exp : expedientes) {
		 * 
		 * 
		 * Busqueda filtro2 = Busqueda.forClass(Involucrado.class).
		 * createCriteria(Expediente.class.getName(),Busqueda.INNER_JOIN).
		 * setResultTransformer(Busqueda.DISTINCT_ROOT_ENTITY);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Busqueda filtro2 = Busqueda.forClass(Involucrado.class).
		 * add(Restrictions.eq("expediente.idExpediente",
		 * exp.getIdExpediente()));
		 * 
		 * try { invs = involucradoDAO.buscarDinamico(filtro2); } catch
		 * (Exception ex) { ex.printStackTrace(); }
		 * 
		 * for (Involucrado inv: invs) { //System.out.println("Involucrados: " +
		 * inv.getPersona().getNombreCompleto());
		 * nombres+=inv.getPersona().getNombreCompleto()+"-";
		 * exp.setNombresInvolucrados(nombres);
		 * 
		 * }
		 * 
		 * nombres="";
		 * 
		 * Busqueda filtro3 = Busqueda.forClass(Hito.class).
		 * add(Restrictions.eq("expediente.idExpediente",
		 * exp.getIdExpediente()));
		 * 
		 * try { hitos = hitoDAO.buscarDinamico(filtro3); } catch (Exception ex)
		 * { ex.printStackTrace(); }
		 * 
		 * for (Hito hit: hitos) { Busqueda filtro4 =
		 * Busqueda.forClass(ActividadProcesal.class).
		 * add(Restrictions.eq("ID_HITO", hit.getIdHito()));
		 * 
		 * try { activProc = activProcesalDAO.buscarDinamico(filtro4); } catch
		 * (Exception ex) { ex.printStackTrace(); }
		 * 
		 * for (ActividadProcesal act: activProc) {
		 * actividadesProc+=act.getActividad().getNombre()+"-";
		 * exp.setActividadesProcesales(actividadesProc); }
		 * 
		 * }
		 * 
		 * 
		 * sublistExpe.add(exp); }
		 * 
		 * expedienteDataModel = new ExpedienteDataModel(sublistExpe);
		 */
	}

	public static String conversion(Session session, Criteria criteria) {
		// Criteria criteria = session.createCriteria(User.class);
		String sql = "";

		try {
			CriteriaImpl c = (CriteriaImpl) criteria;
			SessionImpl s = (SessionImpl) c.getSession();
			SessionFactoryImplementor factory = (SessionFactoryImplementor) s
					.getSessionFactory();
			String[] implementors = factory.getImplementors(c
					.getEntityOrClassName());
			CriteriaLoader loader = new CriteriaLoader(
					(OuterJoinLoadable) factory
							.getEntityPersister(implementors[0]),
					factory, c, implementors[0], (LoadQueryInfluencers) s
							.getEnabledFilters());

			Field f = OuterJoinLoader.class.getDeclaredField("sql");
			f.setAccessible(true);
			sql = (String) f.get(loader);
			// System.out.println(sql);
			return sql;

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Date modifDate(int dias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dias);

		return cal.getTime();

	}

	public String definirColorFila(Date fechaVencimiento, Date fechaInicio,
			int plazoLey, int numeroDiasxVencer, int numeroDiasAlerta,
			int numeroDiasRojo) {
		
		String colorFila="";
		System.out.println("--------------------------------------------------");
		System.out.println("Parametros ingresados al metodo <definirColorFila>");
		System.out.println("--------------------------------------------------");
		System.out.println("Fec Ven: " + fechaVencimiento);
		System.out.println("Fec Ini: " + fechaInicio);
		System.out.println("Plazo Ley: " + plazoLey);
		System.out.println("Dias x Vencer: " + numeroDiasxVencer);
		System.out.println("Dias Alerta: " + numeroDiasAlerta);
		System.out.println("Dias Vencidas: " + numeroDiasRojo);
		

		long diferenciaVen = fechasDiferenciaEnDias(deStringToDate(getFechaActual()),fechaVencimiento);
		System.out.println("Diferencia en dias con fecha vencida: " + diferenciaVen);
		long diferenciaInic = fechasDiferenciaEnDias(deStringToDate(getFechaActual()),fechaInicio);
		System.out.println("Diferencia en dias con fecha inicio: " + diferenciaInic);

		if (diferenciaVen == 0) {
			colorFila = "";
		}
		// parametro definido en el mantenimiento de estados
		if (diferenciaVen > 0 && diferenciaVen <= plazoLey) 
		{
			colorFila = "R";
		}
		if (diferenciaVen == numeroDiasxVencer) {
			colorFila = "N";
		}
		if (diferenciaVen > 0 && diferenciaVen > plazoLey) {
			colorFila = "V";
		}
		if (diferenciaVen == numeroDiasAlerta) {
			colorFila = "A";
		}
		System.out.println("--------------------------------------------------");
		
		return colorFila;
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
}
