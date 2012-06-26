package com.hildebrando.legal.mb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;

import com.hildebrando.legal.domains.ActividadProcesal;
import com.hildebrando.legal.domains.Documento;
import com.hildebrando.legal.domains.Oficina;
import com.hildebrando.legal.domains.Organo;
import com.hildebrando.legal.domains.Usuario;
import com.hildebrando.legal.view.ActividadProcesalDataModel;
import com.hildebrando.legal.view.OrganoDataModel;

@SessionScoped
@ManagedBean(name = "notificacion")
public class NotificacionesMB {

	// datos generales
	private String nroExpeOficial;
	private Date iniProceso;
	private String estado;
	private Map<String, String> estados;
	private String proceso;
	private Map<String, String> procesos;
	private String via;
	private Map<String, String> vias;
	private String instancia;
	private Map<String, String> instancias;
	private Usuario responsable;
	private Oficina oficina;
	private String tipo;
	private Map<String, String> tipos;
	private String organo;
	private Organo organoMant;
	private OrganoDataModel organoDataModel;
	private Organo organoSelec;
	private String secretario;
	private String calificacion;
	private String recurrencia;
	private Map<String, String> calificaciones;
	private String resumen;
	private Date fechaResumen;
	private String todoResumen;
	private List<Organo> organos;
	private String descripNotif;
	private ActividadProcesal procesal;
	private ActividadProcesalDataModel procesalDataModel;
	private ScheduleModel agendaModel;
	private ScheduleEvent evento;
	private String documentoAnexo;
	private UploadedFile file;
	private Documento documento;
	private String riesgo;
	private List<String> riesgos;
	

	public String guardar() {

		return null;
	}

	public void buscarActProc(ActionEvent e) {
		List<ActividadProcesal> listProcesalBD = new ArrayList<ActividadProcesal>();

		listProcesalBD
				.add(new ActividadProcesal("001", "EXP-1234-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "1"));
		listProcesalBD
				.add(new ActividadProcesal("002", "EXP-1235-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "2"));

		listProcesalBD
				.add(new ActividadProcesal("003", "EXP-1236-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "3"));

		listProcesalBD
				.add(new ActividadProcesal("004", "EXP-1237-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "1"));

		listProcesalBD
				.add(new ActividadProcesal("005", "EXP-1238-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "2"));

		listProcesalBD
				.add(new ActividadProcesal("006", "EXP-1239-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "3"));

		listProcesalBD
				.add(new ActividadProcesal("007", "EXP-1240-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "1"));

		listProcesalBD
				.add(new ActividadProcesal("008", "EXP-1241-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "2"));

		listProcesalBD
				.add(new ActividadProcesal("009", "EXP-1242-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "3"));

		procesalDataModel = new ActividadProcesalDataModel(listProcesalBD);

	}

	public void agregarTodoResumen(ActionEvent e) {

		setTodoResumen((getResumen() + "\n" + getFechaResumen()));
	}

	public void buscarOrganos(ActionEvent e) {

		List<Organo> resultados = new ArrayList<Organo>();

		List<Organo> listOrganoBD = new ArrayList<Organo>();

		listOrganoBD.add(new Organo("Comisaria PNP de PRO", "Comas", "Lima",
				"Lima", "2345", "001"));
		listOrganoBD.add(new Organo("Juzgado Penal del Callao", "Callao",
				"Lima", "Lima", "4323", "002"));
		listOrganoBD.add(new Organo("Comisaria de San Miguel", "San Miguel",
				"Lima", "Lima", "4567", "001"));
		listOrganoBD.add(new Organo("Comisaria de Magdalena del Mar",
				"Magdalena del Mar", "Lima", "Lima", "3452", "001"));
		listOrganoBD.add(new Organo("Juzgado Penal de Chorrillos",
				"Chorrillos", "Lima", "Lima", "2343", "002"));

		if (!organoMant.getEntidad().equalsIgnoreCase("")
				&& !organoMant.getNombre().equalsIgnoreCase("")
				&& !organoMant.getDescripcion2().equalsIgnoreCase("")) {

			for (Organo orgn : listOrganoBD) {

				if (orgn.getNombre().trim()
						.equalsIgnoreCase(organoMant.getNombre())
						&& orgn.getEntidad().trim()
								.equalsIgnoreCase(organoMant.getEntidad())
						&& orgn.getDescripcion2().trim()
								.equalsIgnoreCase(organoMant.getDescripcion2())) {
					resultados.add(orgn);
				}
			}

		}

		organoDataModel = new OrganoDataModel(resultados);

	}

	public String agregarOrgano() {

		List<Organo> listOrganoBD = new ArrayList<Organo>();

		listOrganoBD.add(new Organo("Comisaria PNP de PRO", "Comas", "Lima",
				"Lima", "2345", "001"));
		listOrganoBD.add(new Organo("Juzgado Penal del Callao", "Callao",
				"Lima", "Lima", "4323", "002"));
		listOrganoBD.add(new Organo("Comisaria de San Miguel", "San Miguel",
				"Lima", "Lima", "4567", "001"));
		listOrganoBD.add(new Organo("Comisaria de Magdalena del Mar",
				"Magdalena del Mar", "Lima", "Lima", "3452", "001"));
		listOrganoBD.add(new Organo("Juzgado Penal de Chorrillos",
				"Chorrillos", "Lima", "Lima", "2343", "002"));

		for (Organo orgn : listOrganoBD) {

			if (orgn.getNombre().equalsIgnoreCase(organoMant.getNombre())) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Organo Existente",
						"Organo Existente");

				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;

			}
		}

		// ingreso a la bd el organo
		organoMant.setCodigo("004");

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Organo Ingresado", "Organo Ingresado");

		FacesContext.getCurrentInstance().addMessage(null, msg);

		return null;

	}

	// listener cada vez que se modifica el proceso
	public void cambioProceso() {

		if (getProceso() != null && !getProceso().equals("")) {

			Map<String, String> vias1BD = new HashMap<String, String>();
			vias1BD.put("Abreviado", "001");
			vias1BD.put("Conocimiento", "002");
			vias1BD.put("Sumarisimo", "003");

			Map<String, String> vias2BD = new HashMap<String, String>();
			vias2BD.put("Abreviado2", "004");
			vias2BD.put("Conocimiento2", "005");
			vias2BD.put("Sumarisimo2", "006");

			Map<String, String> vias3BD = new HashMap<String, String>();
			vias3BD.put("Abreviado3", "007");
			vias3BD.put("Conocimiento3", "008");
			vias3BD.put("Sumarisimo3", "009");

			Map<String, Map<String, String>> viaBD = new HashMap<String, Map<String, String>>();
			viaBD.put("001", vias1BD);
			viaBD.put("002", vias2BD);
			viaBD.put("003", vias3BD);

			setVias(viaBD.get(getProceso()));

		} else {
			vias = new HashMap<String, String>();
		}

	}

	// autocompletes
	public List<Usuario> completeResponsable(String query) {

		List<Usuario> results = new ArrayList<Usuario>();
		List<Usuario> listUsuarioBD = new ArrayList<Usuario>();
		listUsuarioBD.add(new Usuario("001", "Legal", "Sandoval Sierra",
				"Marco"));
		listUsuarioBD.add(new Usuario("002", "Legal", "Perez Landeo", "Juan"));
		listUsuarioBD.add(new Usuario("003", "Legal", "Lucar Zuñiga", "Luis"));
		listUsuarioBD.add(new Usuario("004", "Ilegal", "Cabrera Barrios",
				"Eder"));
		listUsuarioBD.add(new Usuario("005", "Ilegal", "Leon Payano", "Mauro"));
		listUsuarioBD.add(new Usuario("006", "Ilegal", "Ñahui Salazar",
				"Jorge"));

		for (Usuario usuario : listUsuarioBD) {
			if (usuario.getNombres().toLowerCase()
					.startsWith(query.toLowerCase())
					|| usuario.getApellidos().toLowerCase()
							.startsWith(query.toLowerCase())
					|| usuario.getCodigo().startsWith(query)) {
				results.add(usuario);
			}
		}

		return results;
	}

	public List<Oficina> completeOficina(String query) {
		List<Oficina> results = new ArrayList<Oficina>();

		List<Oficina> listOficinaBD = new ArrayList<Oficina>();
		listOficinaBD.add(new Oficina("LIMA", "2578", "El Trebol PDT 11"));
		listOficinaBD.add(new Oficina("LIMA", "1235", "El Continente 1"));
		listOficinaBD.add(new Oficina("LIMA", "4567", "El Continente 2"));
		listOficinaBD.add(new Oficina("ICA", "3224", "El Trebol PDT 12"));
		listOficinaBD.add(new Oficina("ICA", "2356", "El Continente 3"));
		listOficinaBD.add(new Oficina("ICA", "1235", "El Trebol PDT 13"));
		listOficinaBD.add(new Oficina("HUARAZ", "5678", "El Continente 4"));

		for (Oficina ofic : listOficinaBD) {
			if (ofic.getCodigo().startsWith(query)
					|| ofic.getNombre().toLowerCase()
							.startsWith(query.toLowerCase())
					|| ofic.getTerritorio().toLowerCase()
							.startsWith(query.toLowerCase())) {
				results.add(ofic);
			}
		}

		return results;
	}

	public List<String> completeOrgano(String query) {
		List<String> results = new ArrayList<String>();

		List<Organo> listOrganoBD = new ArrayList<Organo>();

		listOrganoBD.add(new Organo("Comisaria PNP de PRO", "Comas", "Lima",
				"Lima", "2345", "Ministerio"));
		listOrganoBD.add(new Organo("Juzgado Penal del Callao", "Callao",
				"Lima", "Lima", "4323", "Poder Judicial"));
		listOrganoBD.add(new Organo("Comisaria de San Miguel", "San Miguel",
				"Lima", "Lima", "4567", "Ministerio"));
		listOrganoBD.add(new Organo("Comisaria de Magdalena del Mar",
				"Magdalena del Mar", "Lima", "Lima", "3452", "Ministerio"));
		listOrganoBD.add(new Organo("Juzgado Penal de Chorrillos",
				"Chorrillos", "Lima", "Lima", "2343", "Poder Judicial"));

		for (Organo orgs : listOrganoBD) {
			if (orgs.getNombre().toLowerCase().startsWith(query.toLowerCase())) {
				results.add(orgs.getDescripcion().toUpperCase());
			}
		}

		return results;
	}

	public List<String> completeDistrito(String query) {
		List<String> results = new ArrayList<String>();

		List<String> listDistritoBD = new ArrayList<String>();
		listDistritoBD.add("Comas,Lima,Lima");
		listDistritoBD.add("San Miguel,Lima,Lima");
		listDistritoBD.add("Callao,Lima,Lima");
		listDistritoBD.add("Surco,Lima,Lima");

		for (String distr : listDistritoBD) {
			if (distr.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(distr.toUpperCase());
			}
		}

		return results;
	}

	public String getNroExpeOficial() {
		return nroExpeOficial;
	}

	public void setNroExpeOficial(String nroExpeOficial) {
		this.nroExpeOficial = nroExpeOficial;
	}

	public Date getIniProceso() {
		if(iniProceso == null){
			Calendar cal = Calendar.getInstance();
			iniProceso = cal.getTime();
		}
		return iniProceso;
	}

	public void setIniProceso(Date iniProceso) {
		this.iniProceso = iniProceso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Map<String, String> getEstados() {

		if (estados == null) {
			estados = new HashMap<String, String>();
			estados.put("En giro", "001");
			estados.put("Concluido", "002");

		}

		return estados;
	}

	public void setEstados(Map<String, String> estados) {
		this.estados = estados;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public Map<String, String> getProcesos() {

		if (procesos == null) {
			procesos = new HashMap<String, String>();
			procesos.put("Civil", "001");
			procesos.put("Penal", "002");
			procesos.put("Administrativo", "003");

		}

		return procesos;
	}

	public void setProcesos(Map<String, String> procesos) {
		this.procesos = procesos;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public Map<String, String> getVias() {
		return vias;
	}

	public void setVias(Map<String, String> vias) {
		this.vias = vias;
	}

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public Map<String, String> getInstancias() {

		if (instancias == null) {
			instancias = new HashMap<String, String>();
			instancias.put("1ra Instancia", "001");
			instancias.put("Casacion", "002");
			instancias.put("2da Instancia Adm.", "003");
			instancias.put("Queja de derecho", "004");

		}

		return instancias;
	}

	public void setInstancias(Map<String, String> instancias) {
		this.instancias = instancias;
	}

	public Usuario getResponsable() {
		if (responsable == null) {
			responsable = new Usuario();
		}
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public Oficina getOficina() {
		if (oficina == null) {
			oficina = new Oficina();
		}
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Map<String, String> getTipos() {
		if (tipos == null) {
			tipos = new HashMap<String, String>();
			tipos.put("Favor del Banco", "001");
			tipos.put("Contra del banco", "002");
		}

		return tipos;
	}

	public void setTipos(Map<String, String> tipos) {
		this.tipos = tipos;
	}

	public String getOrgano() {
		return organo;
	}

	public void setOrgano(String organo) {
		this.organo = organo;
	}

	public Organo getOrganoMant() {
		if (organoMant == null) {
			organoMant = new Organo();
		}
		return organoMant;
	}

	public void setOrganoMant(Organo organoMant) {
		this.organoMant = organoMant;
	}

	public String getSecretario() {
		return secretario;
	}

	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}

	public OrganoDataModel getOrganoDataModel() {
		if (organoDataModel == null) {

			List<Organo> listOrganoBD = new ArrayList<Organo>();

			listOrganoBD.add(new Organo("Comisaria PNP de PRO", "Comas",
					"Lima", "Lima", "2345", "001"));
			listOrganoBD.add(new Organo("Juzgado Penal del Callao", "Callao",
					"Lima", "Lima", "4323", "002"));
			listOrganoBD.add(new Organo("Comisaria de San Miguel",
					"San Miguel", "Lima", "Lima", "4567", "001"));
			listOrganoBD.add(new Organo("Comisaria de Magdalena del Mar",
					"Magdalena del Mar", "Lima", "Lima", "3452", "001"));
			listOrganoBD.add(new Organo("Juzgado Penal de Chorrillos",
					"Chorrillos", "Lima", "Lima", "2343", "002"));

			organoDataModel = new OrganoDataModel(listOrganoBD);
		}
		return organoDataModel;
	}

	public void setOrganoDataModel(OrganoDataModel organoDataModel) {
		this.organoDataModel = organoDataModel;
	}

	public Organo getOrganoSelec() {
		if (organoSelec == null) {
			organoSelec = new Organo();
		}
		return organoSelec;
	}

	public void setOrganoSelec(Organo organoSelec) {
		this.organoSelec = organoSelec;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
	}

	public Map<String, String> getCalificaciones() {

		if (calificaciones == null) {
			calificaciones = new HashMap<String, String>();
			calificaciones.put("Probable", "001");
			calificaciones.put("No Probable", "002");
		}
		return calificaciones;
	}

	public void setCalificaciones(Map<String, String> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public Date getFechaResumen() {
		if(fechaResumen == null){
			Calendar cal = Calendar.getInstance();
			fechaResumen = cal.getTime();
		}
		return fechaResumen;
	}

	public void setFechaResumen(Date fechaResumen) {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

		Date fecha = null;
		try {

			fecha = formatoDelTexto.parse(dateFormat.format(fechaResumen));

		} catch (ParseException ex) {

			ex.printStackTrace();

		}

		this.fechaResumen = fecha;
	}

	public String getTodoResumen() {
		return todoResumen;
	}

	public void setTodoResumen(String todoResumen) {
		this.todoResumen = todoResumen;
	}

	public ActividadProcesalDataModel getProcesalDataModel() {

		if (procesalDataModel == null) {

			List<ActividadProcesal> listProcesalBD = new ArrayList<ActividadProcesal>();

			listProcesalBD.add(new ActividadProcesal("001", "EXP-1234-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"1"));
			listProcesalBD.add(new ActividadProcesal("002", "EXP-1235-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"2"));

			listProcesalBD.add(new ActividadProcesal("003", "EXP-1236-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"3"));

			listProcesalBD.add(new ActividadProcesal("004", "EXP-1237-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"1"));

			listProcesalBD.add(new ActividadProcesal("005", "EXP-1238-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"2"));

			listProcesalBD.add(new ActividadProcesal("006", "EXP-1239-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"3"));

			listProcesalBD.add(new ActividadProcesal("007", "EXP-1240-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"1"));

			listProcesalBD.add(new ActividadProcesal("008", "EXP-1241-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"2"));

			listProcesalBD.add(new ActividadProcesal("009", "EXP-1242-2012",
					"Julio Correa Mellado", "Comisaria 1", "15:00",
					"Tachas u Excepciones", "12/04/2012", "18/04/2012", "", "",
					"3"));

			procesalDataModel = new ActividadProcesalDataModel(listProcesalBD);
		}

		return procesalDataModel;
	}

	public void setProcesalDataModel(
			ActividadProcesalDataModel procesalDataModel) {
		this.procesalDataModel = procesalDataModel;
	}

	public List<Organo> getOrganos() {
		List<Organo> listOrganoBD = new ArrayList<Organo>();

		listOrganoBD.add(new Organo("Comisaria PNP de PRO", "Comas", "Lima",
				"Lima", "2345", "Ministerio"));
		listOrganoBD.add(new Organo("Juzgado Penal del Callao", "Callao",
				"Lima", "Lima", "4323", "Poder Judicial"));
		listOrganoBD.add(new Organo("Comisaria de San Miguel", "San Miguel",
				"Lima", "Lima", "4567", "Ministerio"));
		listOrganoBD.add(new Organo("Comisaria de Magdalena del Mar",
				"Magdalena del Mar", "Lima", "Lima", "3452", "Ministerio"));
		listOrganoBD.add(new Organo("Juzgado Penal de Chorrillos",
				"Chorrillos", "Lima", "Lima", "2343", "Poder Judicial"));

		organos = listOrganoBD;
		return organos;
	}

	public void setOrganos(List<Organo> organos) {
		this.organos = organos;
	}

	public ActividadProcesal getProcesal() {
		if (procesal == null) {
			procesal = new ActividadProcesal();
		}
		return procesal;
	}

	public void setProcesal(ActividadProcesal procesal) {
		this.procesal = procesal;
	}

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

	public Date modifDate(int dias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dias);

		return cal.getTime();

	}
	
	public void handleFileUpload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
	

	public void addEvent(ActionEvent actionEvent) {
		if (evento.getId() == null)
			agendaModel.addEvent(evento);
		else
			agendaModel.updateEvent(evento);

		evento = new DefaultScheduleEvent();
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		evento = selectEvent.getScheduleEvent();
	}

	public void onDateSelect(DateSelectEvent selectEvent) {
		evento = new DefaultScheduleEvent("",
				selectEvent.getDate(), selectEvent.getDate());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event moved", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
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

	public String getDescripNotif() {
		return descripNotif;
	}

	public void setDescripNotif(String descripNotif) {
		this.descripNotif = descripNotif;
	}

	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

	public List<String> getRiesgos() {
		
		if(riesgos == null){
			riesgos = new ArrayList<String>();
			riesgos.add("101 Favor del Banco");
			riesgos.add("102 Controles deficientes");
			riesgos.add("103 Incumplimiento de la Normativa");
			riesgos.add("104 Errores de Gestion");
			riesgos.add("105 Errores en Documentacion");
		}
		
		
		return riesgos;
	}

	public void setRiesgos(List<String> riesgos) {
		this.riesgos = riesgos;
	}

	public String getDocumentoAnexo() {
		return documentoAnexo;
	}

	public void setDocumentoAnexo(String documentoAnexo) {
		this.documentoAnexo = documentoAnexo;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
