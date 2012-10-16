package com.hildebrando.legal.mb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.primefaces.event.SelectEvent;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.ActividadProcesal;
import com.hildebrando.legal.modelo.Anexo;
import com.hildebrando.legal.modelo.Aviso;
import com.hildebrando.legal.modelo.BusquedaActProcesal;
import com.hildebrando.legal.modelo.Cuantia;
import com.hildebrando.legal.modelo.Cuota;
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
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.SituacionActProc;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.TipoInvolucrado;
import com.hildebrando.legal.modelo.TipoProvision;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.view.BusquedaActividadProcesalDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.InvolucradoDataModel;

@ManagedBean(name = "indicadoresReg")
@SessionScoped
public class IndicadoresMB {

	public static Logger logger = Logger.getLogger(IndicadoresMB.class);

	private BusquedaActProcesal busquedaProcesal;
	private BusquedaActividadProcesalDataModel resultadoBusqueda;
	private List<Organo> organos;
	private List<Usuario> responsables;
	private Involucrado demandante;
	private String busNroExpe;
	private String idOrgano;
	private int idResponsable;
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

	public BusquedaActProcesal getBusquedaProcesal() {
		return busquedaProcesal;
	}

	public void setBusquedaProcesal(BusquedaActProcesal busquedaProcesal) {
		this.busquedaProcesal = busquedaProcesal;
	}

	public BusquedaActividadProcesalDataModel getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	public void setResultadoBusqueda(
			BusquedaActividadProcesalDataModel resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
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

	public String getIdOrgano() {
		return idOrgano;
	}

	public void setIdOrgano(String idOrgano) {
		this.idOrgano = idOrgano;
	}

	public int getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
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
		super();

		// Se abre la session en caso de este cerrada
		/*if (!SpringInit.devolverSession().isOpen()) {
			SpringInit.openSession();
		}*/

		expedienteVista = new ExpedienteVista();
		InicializarListas();
		InicializarObjetos();
		InicializarCombos();
	}

	public void InicializarListas() {
		expedienteVista.setHonorarios(new ArrayList<Honorario>());
		expedienteVista.setAnexos(new ArrayList<Anexo>());
		expedienteVista.setActividadProcesales(new ArrayList<ActividadProcesal>());
		expedienteVista.setInculpados(new ArrayList<Inculpado>());
		expedienteVista.setInstancias(new ArrayList<Instancia>());
		expedienteVista.setProvisiones(new ArrayList<Provision>());
		expedienteVista.setVias(new ArrayList<Via>());
	}

	public void InicializarObjetos() 
	{
		expedienteVista.setHonorario(new Honorario());
		expedienteVista.setAnexo(new Anexo());
		expedienteVista.setActividadProcesal(new ActividadProcesal());
		expedienteVista.setInculpado(new Inculpado());
		expedienteVista.setInstancia(0);
		expedienteVista.setProvision(new Provision());
		expedienteVista.setVia(0);

		busquedaProcesal = new BusquedaActProcesal();

		// Inicializar el modelo usado en resultado de la busqueda de indicadores
		resultadoBusqueda = new BusquedaActividadProcesalDataModel(new ArrayList<BusquedaActProcesal>());
		resultadoBusqueda=buscarExpedientexResponsable();
	}

	public void InicializarCombos() {
		// Aqui se llena el combo de organos
		llenarOrganos();

		// Aqui se llena el combo de responsables
		llenarResponsables();
	}

	@SuppressWarnings("unchecked")
	public void llenarOrganos()
	{
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Organo.class);

		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void buscarExpediente(ActionEvent e) 
	{
		String filtro = "";
		logger.debug("Entro al metodo");

		if (demandante != null) {
			if (filtro.length() > 0) {
				filtro += " and inv.id_involucrado = "
						+ demandante.getIdInvolucrado()
						+ " and inv.id_rol_involucrado=2 ";
			} else {
				filtro += "where inv.id_involucrado = "
						+ demandante.getIdInvolucrado()
						+ " and inv.id_rol_involucrado=2 ";
			}
		}

		// Se aplica filtro a la busqueda por Numero de Expediente
		if (getBusNroExpe() != null && !getBusNroExpe().equals("")) {
			if (filtro.length() > 0) {
				filtro += " and c.numero_expediente = " + "'" + getBusNroExpe()
						+ "'";
			} else {
				filtro += "where c.numero_expediente = " + "'"
						+ getBusNroExpe() + "'";
			}
		}

		// Se aplica filtro a la busqueda por Organo
		if (getIdOrgano() != null && !getIdOrgano().equals("")) {
			if (filtro.length() > 0) {
				filtro += " and org.codigo=" + getIdOrgano();
			} else {
				filtro += "where org.codigo=" + getIdOrgano();
			}
		}

		// Se aplica filtro a la busqueda por Responsable
		if (getIdResponsable() != 0) {
			if (filtro.length() > 0) {
				filtro += " and c.id_usuario = " + getIdResponsable();
			} else {
				filtro += "where c.id_usuario = " + getIdResponsable();
			}
		}

		// Se aplica filtro a la busqueda por Prioridad: Rojo, Amarillo, Naranja
		// y Verde
		if (getIdPrioridad() != null && getIdPrioridad() != "") {
			if (filtro.length() > 0) {
				filtro += " and " + queryColor(2) + "'" + getIdPrioridad()
						+ "'";
			} else {
				filtro += "where " + queryColor(2) + "'" + getIdPrioridad()
						+ "'";
			}
		}

		logger.debug("Filtro adicional: " + filtro);

		String hql = "select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID,"
				+ "c.numero_expediente,per.nombre_completo as Demandante,"
				+ "org.nombre as Organo,a.hora,act.nombre as Actividad,usu.nombre_completo as Responsable,a.fecha_actividad,"
				+ "a.fecha_vencimiento,a.fecha_atencion,a.observacion,pro.id_proceso,vi.id_via,"
				+ "act.id_actividad,c.id_instancia,c.id_expediente,a.plazo_ley, "
				+ queryColor(1)
				+ "from actividad_procesal a "
				+ "left outer join expediente c on a.id_expediente=c.id_expediente "
				+ "left outer join involucrado inv on c.id_expediente=inv.id_expediente "
				+ "left outer join persona per on inv.id_persona=per.id_persona "
				+ "left outer join organo org on c.id_organo = org.id_organo "
				+ "left outer join actividad act on a.id_actividad = act.id_actividad "
				+ "left outer join instancia ins on c.id_instancia=ins.id_instancia "
				+ "left outer join via vi on ins.id_via = vi.id_via "
				+ "left outer join proceso pro on vi.id_proceso = pro.id_proceso "
				+ "left outer join usuario usu on c.id_usuario=usu.id_usuario "
				+ filtro + " order by c.numero_expediente,per.nombre_completo";

		logger.debug("Query Busqueda: " + hql);

		Query query3 = SpringInit.devolverSession().createSQLQuery(hql)
				.addEntity(BusquedaActProcesal.class);
		List<BusquedaActProcesal> resultado = new ArrayList<BusquedaActProcesal>();
		resultado.clear();
		resultado = query3.list();

		resultadoBusqueda = new BusquedaActividadProcesalDataModel(resultado);
		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	@SuppressWarnings("unchecked")
	public BusquedaActividadProcesalDataModel buscarExpedientexResponsable()
	{
		String hql = "select ROW_NUMBER() OVER (ORDER BY  c.numero_expediente) as ROW_ID,"
				+ "c.numero_expediente,per.nombre_completo as Demandante,"
				+ "org.nombre as Organo,a.hora,act.nombre as Actividad,usu.nombre_completo as Responsable,a.fecha_actividad,"
				+ "a.fecha_vencimiento,a.fecha_atencion,a.observacion,pro.id_proceso,vi.id_via,"
				+ "act.id_actividad,c.id_instancia,c.id_expediente,a.plazo_ley, "
				+ queryColor(1)
				+ "from actividad_procesal a "
				+ "left outer join expediente c on a.id_expediente=c.id_expediente "
				+ "left outer join involucrado inv on c.id_expediente=inv.id_expediente "
				+ "left outer join persona per on inv.id_persona=per.id_persona "
				+ "left outer join organo org on c.id_organo = org.id_organo "
				+ "left outer join actividad act on a.id_actividad = act.id_actividad "
				+ "left outer join instancia ins on c.id_instancia=ins.id_instancia "
				+ "left outer join via vi on ins.id_via = vi.id_via "
				+ "left outer join proceso pro on vi.id_proceso = pro.id_proceso "
				+ "left outer join usuario usu on c.id_usuario=usu.id_usuario "
				+ "order by c.numero_expediente,per.nombre_completo";
		
		System.out.println("Query Busqueda: " + hql);
		
		logger.debug("Query Busqueda: " + hql);

		Query query = SpringInit.devolverSession().createSQLQuery(hql)
				.addEntity(BusquedaActProcesal.class);
		List<BusquedaActProcesal> resultado = new ArrayList<BusquedaActProcesal>();
		resultado.clear();
		resultado = query.list();
		resultadoBusqueda = new BusquedaActividadProcesalDataModel(resultado);
		return resultadoBusqueda;
	}
	
	@SuppressWarnings("unchecked")
	public void llenarResponsables() {
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Usuario.class);

		try {
			responsables = usuarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			e.printStackTrace();
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

	private String queryColor(int modo) {
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
					+ "END END END AS COLOR ";
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
					+ "END END END  = ";
		}
		return cadena;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void leerExpediente(SelectEvent event) 
	{
		try {

			logger.debug(""
					+ ((List<BusquedaActProcesal>) getResultadoBusqueda()
							.getWrappedData()).size());
			logger.debug("id Expediente: " + getBusquedaProcesal().getId_expediente());

			ExpedienteVista expedienteVistaNuevo = new ExpedienteVista();
			expedienteVistaNuevo.setFlagDeshabilitadoGeneral(true);

			GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Expediente.class);
			filtro.add(Expression.like("numeroExpediente",
					getBusquedaProcesal().getNroExpediente()));
			//filtro.add((Criterion) Projections.max("idExpediente"));
			//filtro.add(new Projection(nullSafe, pos, expression));
			//filtro.add(Projections.max("idExpediente"));
			//filtro.setProjection(Projections.max("idExpediente"));
			
			List<Expediente> expedientes = new ArrayList<Expediente>();
			
			try {
				expedientes = expedienteDAO.buscarDinamico(filtro);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			logger.debug("Cantidad de registros encontrados: " + expedientes.size());
			
			for (Expediente result : expedientes) 
			{
				logger.debug("--------------------------------------------------");
				logger.debug("Datos a mostrar");
				logger.debug("IdExpediente: " + result.getIdExpediente());
				logger.debug("Expediente:" + result.getNumeroExpediente());
				logger.debug("Instancia: " + result.getInstancia().getNombre());
				logger.debug("--------------------------------------------------");
				
				actualizarDatosPagina(expedienteVistaNuevo, result);
				setExpedienteVista(expedienteVistaNuevo);
				if (expedientes.size()>0) 
				{
					break;
				}
			}

			// FACESCONTEXT
			// .GETCURRENTINSTANCE()
			// .GETEXTERNALCONTEXT()
			// .REDIRECT(
			// "BUSEXPEDIENTEREADONLY.XHTML?ID="
			// + GETBUSQUEDAPROCESAL().GETID_EXPEDIENTE());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void actualizarDatosPagina(ExpedienteVista ex, Expediente e) {
		ex.setNroExpeOficial(e.getNumeroExpediente());
		ex.setInicioProceso(e.getFechaInicioProceso());
		
		//Se obtiene el nombre de estado
		ex.setEstado(e.getEstadoExpediente().getIdEstadoExpediente());
		setEstadoExpediente(e.getEstadoExpediente().getNombre());
		
		//Se obtiene el nombre de proceso
		ex.setProceso(e.getInstancia().getVia().getProceso().getIdProceso());
		setNombreProceso(e.getInstancia().getVia().getProceso().getNombre());
		
		//Se obtiene el nombre de la via
		GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Via.class);
		filtro.add(Expression.like("proceso.idProceso", ex.getProceso()));

		try {
			ex.setVias(viaDao.buscarDinamico(filtro));
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		ex.setVia(e.getInstancia().getVia().getIdVia());
		setNombreVia(e.getInstancia().getVia().getNombre());
		
		//Se obtiene el nombre de instancia
		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Instancia.class);
		filtro.add(Expression.like("via.idVia", ex.getVia()));

		try {
			ex.setInstancias(instanciaDao.buscarDinamico(filtro));
			setInstanciasProximas(ex.getInstancias());
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		ex.setInstancia(e.getInstancia().getIdInstancia());
		ex.setNombreInstancia(e.getInstancia().getNombre());

		ex.setResponsable(e.getUsuario());

		String texto = e.getOficina().getCodigo()
				+ " "
				+ e.getOficina().getNombre().toUpperCase()
				+ " ("
				+ e.getOficina().getTerritorio().getDepartamento()
						.toUpperCase() + ")";

		e.getOficina().setNombreDetallado(texto);

		ex.setOficina(e.getOficina());
		ex.setTipo(e.getTipoExpediente().getIdTipoExpediente());
		setTipoExpediente(e.getTipoExpediente().getNombre());
		
		String descripcion = e.getOrgano().getNombre().toUpperCase() + " ("
				+ e.getOrgano().getTerritorio().getDistrito().toUpperCase()
				+ ", "
				+ e.getOrgano().getTerritorio().getProvincia().toUpperCase()
				+ ", "
				+ e.getOrgano().getTerritorio().getDepartamento().toUpperCase()
				+ ")";

		e.getOrgano().setNombreDetallado(descripcion);

		ex.setOrgano1(e.getOrgano());

		ex.setSecretario(e.getSecretario());
		ex.setCalificacion(e.getCalificacion().getIdCalificacion());
		setCalificacionExpediente(e.getCalificacion().getNombre());
		ex.setRecurrencia(e.getRecurrencia());

		ex.setHonorario(new Honorario());
		ex.getHonorario().setAbogado(new Abogado());
		ex.getHonorario().setMoneda(new Moneda());
		ex.getHonorario().setTipoHonorario(new TipoHonorario());
		ex.getHonorario().setCantidad(0);
		ex.getHonorario().setMonto(0.0);
		ex.getHonorario().setMontoPagado(0.0);
		ex.getHonorario().setSituacionHonorario(new SituacionHonorario());

		List<Honorario> honorarios = new ArrayList<Honorario>();
		List<Cuota> cuotas;

		GenericDao<Honorario, Object> honorarioDAO = (GenericDao<Honorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Cuota, Object> cuotaDAO = (GenericDao<Cuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		filtro = Busqueda.forClass(Honorario.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			honorarios = honorarioDAO.buscarDinamico(filtro);

			for (Honorario h : honorarios) {
				cuotas = new ArrayList<Cuota>();
				filtro = Busqueda.forClass(Cuota.class);
				filtro.add(Expression.like("honorario.idHonorario",
						h.getIdHonorario()));
				cuotas = cuotaDAO.buscarDinamico(filtro);
				h.setCuotas(cuotas);
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

		ex.setHonorarios(honorarios);

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Involucrado.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(
				involucrados);
		ex.setInvolucradoDataModel(involucradoDataModel);
		ex.setInvolucrado(new Involucrado(new TipoInvolucrado(),
				new RolInvolucrado(), new Persona()));

		List<Cuantia> cuantias = new ArrayList<Cuantia>();
		GenericDao<Cuantia, Object> cuantiaDAO = (GenericDao<Cuantia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Cuantia.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			cuantias = cuantiaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		CuantiaDataModel cuantiaDataModel = new CuantiaDataModel(cuantias);
		ex.setCuantiaDataModel(cuantiaDataModel);
		ex.setCuantia(new Cuantia(new Moneda(), new Materia()));

		List<Inculpado> inculpados = new ArrayList<Inculpado>();
		GenericDao<Inculpado, Object> inculpadoDAO = (GenericDao<Inculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Inculpado.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			inculpados = inculpadoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setInculpados(inculpados);
		ex.setInculpado(new Inculpado(new SituacionInculpado(), new Moneda(),
				new Persona()));

		ex.setMoneda(e.getMoneda().getIdMoneda());
		setDescMoneda(e.getMoneda().getDescripcion());
		ex.setMontoCautelar(e.getMontoCautelar());
		ex.setTipoCautelar(e.getTipoCautelar().getIdTipoCautelar());
		setTipoMedidaCautelar(e.getTipoCautelar().getDescripcion());
		ex.setDescripcionCautelar(e.getDescripcionCautelar());
		ex.setContraCautela(e.getContraCautela().getIdContraCautela());
		setContraCautela(e.getContraCautela().getDescripcion());
		ex.setImporteCautelar(e.getImporteCautelar());
		ex.setEstadoCautelar(e.getEstadoCautelar().getIdEstadoCautelar());
		setEstadoCautelar(e.getEstadoCautelar().getDescripcion());

		List<Provision> provisions = new ArrayList<Provision>();
		GenericDao<Provision, Object> provisionDAO = (GenericDao<Provision, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Provision.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			provisions = provisionDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setProvisiones(provisions);
		ex.setProvision(new Provision(new Moneda(), new TipoProvision()));

		//ex.setFechaResumen(e.getFechaResumen());
		//ex.setResumen(e.getTextoResumen());
		// setTodoResumen(getSelectedExpediente().get)

		List<ActividadProcesal> actividadProcesals = new ArrayList<ActividadProcesal>();
		GenericDao<ActividadProcesal, Object> actividadProcesalDAO = (GenericDao<ActividadProcesal, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(ActividadProcesal.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			actividadProcesals = actividadProcesalDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setActividadProcesales(actividadProcesals);
		ex.setActividadProcesal(new ActividadProcesal(new Etapa(),
				new SituacionActProc(), new Actividad()));

		List<Anexo> anexos = new ArrayList<Anexo>();
		GenericDao<Anexo, Object> anexoDAO = (GenericDao<Anexo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Anexo.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			anexos = anexoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setAnexos(anexos);
		ex.setAnexo(new Anexo());
		ex.setRiesgo(e.getRiesgo().getIdRiesgo());

		setTabCaucion(false);
		if (ex.getProceso() == 1 || ex.getProceso() == 3) {
			setTabCaucion(true);
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
			ex.printStackTrace();
			return null;
		}
	}

}
