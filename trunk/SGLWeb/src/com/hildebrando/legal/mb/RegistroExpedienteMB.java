package com.hildebrando.legal.mb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.hildebrando.legal.domains.Abogado;
import com.hildebrando.legal.domains.Cuantia;
import com.hildebrando.legal.domains.Estudio;
import com.hildebrando.legal.domains.Honorario;
import com.hildebrando.legal.domains.Inculpado;
import com.hildebrando.legal.domains.Oficina;
import com.hildebrando.legal.domains.Organo;
import com.hildebrando.legal.domains.Persona;
import com.hildebrando.legal.domains.Usuario;
import com.hildebrando.legal.service.RegistroExpedienteService;
import com.hildebrando.legal.view.AbogadoDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.EstudioDataModel;
import com.hildebrando.legal.view.OrganoDataModel;
import com.hildebrando.legal.view.PersonaDataModel;

@ManagedBean(name = "registExpe")
public class RegistroExpedienteMB {

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
	private String organotxt;
	private Map<String, String> tipos;
	private String secretario;
	private String calificacion;
	private Map<String, String> calificaciones;
	private String recurrencia;
	private Abogado abogado;
	private AbogadoDataModel abogadoDataModel;
	private List<String> monedas;
	private List<String> listSituacion;
	private Persona persona;
	private PersonaDataModel personaDataModel;
	private Cuantia cuantia;
	private Cuantia selectCuantia;
	private CuantiaDataModel cuantiaDataModel;
	private Inculpado inculpado;
	private List<Inculpado> inculpadosDataModel;
	private String moneda;
	private String monto;
	private String slctTipo;
	private List<String> tipoCautelar;
	private String slctDescripcion;
	private String slctContraCuatela;
	private String importe;
	private String estadoCautelar;
	private List<String> estadosCautelares;
	private String resumen;
	private Date fechaResumen;
	private String todoResumen;
	private String riesgo;
	private List<String> riesgos;
	private Organo organo;
	private OrganoDataModel organoDataModel;
	private Organo selectedOrgano;
	
	private EstudioDataModel estudioDataModel;
	private List<Honorario> honorarioDetalle;
	private Persona selectPersona;
	private Persona selectInculpado;
	private List<String> contraCautelas;
	private boolean tabAsigEstExt;
	private boolean tabCaucion;
	private boolean tabCuanMat;
	
	@ManagedProperty(value="#{registroService}")
	private RegistroExpedienteService expedienteService;
	
	public void setExpedienteService(RegistroExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}
	
	public void agregarTodoResumen(ActionEvent e) {
		setTodoResumen((getResumen() + "\n" + getFechaResumen()));
	}

	public void iniDetalleHonorario() {
//		Map<String, String> paramMap = FacesContext.getCurrentInstance()
//				.getExternalContext().getRequestParameterMap();
//		String registroCA = paramMap.get("registroCA");

		honorarioDetalle = new ArrayList<Honorario>();
		honorarioDetalle.add(new Honorario("1", "123-001", "S/.", "440",
				"30/06/2012", "Sin Pagar"));
		honorarioDetalle.add(new Honorario("2", "123-002", "S/.", "440",
				"30/07/2012", "Pagado"));
	}

	public String buscarAbogado(ActionEvent e) {
		List<Abogado> results = new ArrayList<Abogado>();

		List<Abogado> abogadosBD = new ArrayList<Abogado>();
		abogadosBD = new ArrayList<Abogado>();
		abogadosBD.add(new Abogado(new Estudio("123", "estudio1", "direccion1",
				"telefono1", "correo1"), "234", "44886421", "Luis", "Suarez",
				"Peña", "4644242", "lsuarez@hotmail.com"));
		abogadosBD.add(new Abogado(new Estudio("124", "estudio2", "direccion2",
				"telefono2", "correo2"), "235", "44886421", "Anthony",
				"Cabrera", "Barrios", "4644243", "lsuarez@hotmail.com"));

		abogadosBD.add(new Abogado(new Estudio("125", "estudio3", "direccion3",
				"telefono3", "correo3"), "236", "44886421", "Gian", "Guerrero",
				"Garcia", "4644244", "lsuarez@hotmail.com"));

		abogadosBD.add(new Abogado(new Estudio("126", "estudio4", "direccion4",
				"telefono4", "correo4"), "237", "44886421", "Pedro", "Pizarro",
				"Lopez", "4644245", "lsuarez@hotmail.com"));

		for (Abogado abogado : abogadosBD) {

			if (abogado.getRegistroCA().equals(getAbogado().getRegistroCA())) {
				results.add(abogado);

			}

		}
		abogadoDataModel = new AbogadoDataModel(results);

		return null;
	}

	public String agregarEstudio(ActionEvent e) {

		List<Estudio> estudios = new ArrayList<Estudio>();
		estudios.add(getAbogado().getEstudio());

		estudioDataModel = new EstudioDataModel(estudios);

		return null;
	}

	public String agregarAbogado(ActionEvent e) {
		
		List<Abogado> abogadosBD = new ArrayList<Abogado>();
		abogadoDataModel = new AbogadoDataModel(abogadosBD);
		
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Abogado Agregado",
				"Abogado Agregado");

		FacesContext.getCurrentInstance().addMessage(
				null, msg);
		

		return null;
	}

	public String buscarPersona(ActionEvent e) {

		List<Persona> personas = new ArrayList<Persona>();
		List<Persona> listPersonaBD = new ArrayList<Persona>();

		listPersonaBD
				.add(new Persona("001", "Demandado", "12345", "clase1", "dni",
						"123", "Jhonattan", "Saldana", "Camacho",
						"Referencia 1"));

		listPersonaBD.add(new Persona("002", "Demandado", "12346", "clase1",
				"ruc", "345", "Jose", "Salazar", "Campos", "Referencia 2"));

		listPersonaBD.add(new Persona("003", "Demandado", "12347", "clase1",
				"dni", "234", "Pedro", "Ramos", "Pizarro", "Referencia 3"));

		listPersonaBD.add(new Persona("004", "Demandante", "12348", "clase1",
				"dni", "256", "Luis", "Zaravia", "Torres", "Referencia 1"));
		listPersonaBD.add(new Persona("005", "Demandante", "12349", "clase1",
				"ruc", "157", "Lucas", "Vargas", "Campos", "Referencia 2"));

		listPersonaBD.add(new Persona("006", "Demandante", "12340", "clase1",
				"dni", "999", "Felipe", "Mesi", "Pizarro", "Referencia 3"));

		if (!getPersona().getClase().equalsIgnoreCase("")
				&& !getPersona().getTipoDoc().equalsIgnoreCase("")) {

			for (Persona pers : listPersonaBD) {

				if (pers.getCodCliente().trim()
						.equalsIgnoreCase(getPersona().getCodCliente().trim())
						|| pers.getNroDoc()
								.trim()
								.equalsIgnoreCase(
										getPersona().getNroDoc().trim())) {
					personas.add(pers);
				}

			}

		}
		personaDataModel = new PersonaDataModel(personas);

		return null;

	}

	public String buscarOrganos(ActionEvent e) {
		
		expedienteService.registrar();

		List<Organo> sublistOrgano = new ArrayList<Organo>();

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

		if (!getOrgano().getEntidad().equalsIgnoreCase("")
				&& !getOrgano().getNombre().equalsIgnoreCase("")
				&& !getOrgano().getDescripcion2().equalsIgnoreCase("")) {

			for (Organo orgn : listOrganoBD) {

				if (orgn.getNombre().trim()
						.equalsIgnoreCase(getOrgano().getNombre())
						&& orgn.getEntidad().trim()
								.equalsIgnoreCase(getOrgano().getEntidad())
						&& orgn.getDescripcion2()
								.trim()
								.equalsIgnoreCase(getOrgano().getDescripcion2())) {
					sublistOrgano.add(orgn);
				}
			}

		}

		organoDataModel = new OrganoDataModel(sublistOrgano);

		return null;

	}

	public String agregarOrgano(ActionEvent e) {

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

		if (getOrgano().getEntidad().equalsIgnoreCase("")
				|| getOrgano().getNombre().equalsIgnoreCase("")
				|| getOrgano().getDescripcion2().equalsIgnoreCase("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Datos Requeridos", "Datos requeridos");

			FacesContext.getCurrentInstance().addMessage(
					null, msg);
			return null;
		}

		for (Organo orgn : listOrganoBD) {

			if (orgn.getNombre().equalsIgnoreCase(getOrgano().getNombre())) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Organo Existente",
						"Organo Existente");

				FacesContext.getCurrentInstance().addMessage(
						null, msg);
				return null;

			}
		}

		//agregar  a la bd getOrgano()
		
		FacesMessage msg = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Organo Agregado",
				"Organo Agregado");

		FacesContext.getCurrentInstance().addMessage(
				null, msg);

		List<Organo> listOrganoAux = new ArrayList<Organo>();
		organoDataModel = new OrganoDataModel(listOrganoAux);

		return null;

	}

	public String agregarCuantia(ActionEvent e) {

		List<Cuantia> cuantias = new ArrayList<Cuantia>();

		if (!getCuantia().getMateria().equalsIgnoreCase("")
				&& !getCuantia().getPretendido().equalsIgnoreCase("")
				&& !getCuantia().getMoneda().equalsIgnoreCase("")) {

			cuantias.add(cuantia);
		}

		cuantiaDataModel = new CuantiaDataModel(cuantias);

		return null;
	}

	// public String agregarOficina(ActionEvent e) {
	//
	// if (selectOficinaOLP.getTerritorio().equalsIgnoreCase("")) {
	//
	// FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	// "Territorio vacio", "Territorio vacio");
	//
	// FacesContext.getCurrentInstance().addMessage(
	// ":frmRegExp:somTerritorio", msg);
	//
	// }
	//
	// if (selectOficinaOLP.getNombre().equalsIgnoreCase("")) {
	//
	// FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	// "Oficina vacia", "Oficina vacia");
	//
	// FacesContext.getCurrentInstance().addMessage(
	// ":frmRegExp:txtNomOficina", msg);
	//
	// }
	//
	// if (!selectOficinaOLP.getTerritorio().equalsIgnoreCase("")
	// && !selectOficinaOLP.getNombre().equalsIgnoreCase("")) {
	// FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
	// "Oficina Guardada", "Oficina Guardada");
	//
	// FacesContext.getCurrentInstance().addMessage(
	// "frmRegExp:cbtnAgregarOficina", msg);
	//
	// }
	//
	// return null;
	//
	// }

	public String agregarPersona(ActionEvent e) {

		List<Persona> list = new ArrayList<Persona>();

		list.add(getPersona());

		personaDataModel = new PersonaDataModel(list);

		return null;

	}

	public String agregarInculpado(ActionEvent e) {

		List<Inculpado> list = new ArrayList<Inculpado>();

		// validaer inculpadoOLP
		list.add(getInculpado());

		inculpadosDataModel = list;

		return null;

	}

	public String agregarDetallePersona(ActionEvent e) {
		
		if (getPersona().getClase() != "") {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Clase vacia", "Clase vacia");

			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
		
		
		
		return null;

	}

	public String agregarDetalleInculpado(ActionEvent e) {
		List<Persona> personas = new ArrayList<Persona>();

		if (getInculpado().getPersona().getClase() != "") {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Clase vacia", "Clase vacia");

			FacesContext.getCurrentInstance().addMessage(null, msg);

		}

		personas.add(getInculpado().getPersona());

		personaDataModel = new PersonaDataModel(personas);

		return null;

	}

	public String addPersona(ActionEvent e) {

		if (getPersona().getCodCliente().equalsIgnoreCase("")) {

			return null;
		}

		getPersona().setNombreCompleto(
				getPersona().getRazonSocial() + " "
						+ getPersona().getApellidoPaterno() + " "
						+ getPersona().getApellidoMaterno());

		if (getPersona().getTipoDoc().equalsIgnoreCase("dni")) {
			getPersona().setTipo("Natural");

		}

		if (getPersona().getTipoDoc().equalsIgnoreCase("ruc")) {
			getPersona().setTipo("Juridico");

		}

		return null;

	}

	public String guardar() {

		return "registroExpediente2";

	}
	
	public List<String> completeRecurrencia(String query) {
		List<String> results = new ArrayList<String>();

		List<String> listRecurrencia = new ArrayList<String>();
		listRecurrencia.add("reclamo");
		listRecurrencia.add("demora en pago");
		listRecurrencia.add("revision de caso");
		
		for (String rec : listRecurrencia) {
			if (rec.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(rec.toUpperCase());
			}
		}

		return results;
	}

	public List<String> completeMaterias(String query) {
		List<String> results = new ArrayList<String>();

		List<String> listMateriasBD = new ArrayList<String>();
		listMateriasBD.add("materia 1");
		listMateriasBD.add("materia 2");
		listMateriasBD.add("materia 3");
		listMateriasBD.add("materia 4");

		for (String mat : listMateriasBD) {
			if (mat.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(mat.toUpperCase());
			}
		}

		return results;
	}

	public List<String> completeInculpado(String query) {
		List<String> results = new ArrayList<String>();

		List<Persona> listPersonaBD = new ArrayList<Persona>();

		listPersonaBD
				.add(new Persona("001", "Demandado", "12345", "clase1", "dni",
						"123", "Jhonattan", "Saldana", "Camacho",
						"Referencia 1"));

		listPersonaBD.add(new Persona("002", "Demandado", "12346", "clase1",
				"ruc", "345", "Jose", "Salazar", "Campos", "Referencia 2"));

		listPersonaBD.add(new Persona("003", "Demandado", "12347", "clase1",
				"dni", "234", "Pedro", "Ramos", "Pizarro", "Referencia 3"));

		listPersonaBD.add(new Persona("004", "Demandante", "12348", "clase1",
				"dni", "256", "Luis", "Zaravia", "Torres", "Referencia 1"));
		listPersonaBD.add(new Persona("005", "Demandante", "12349", "clase1",
				"ruc", "157", "Lucas", "Vargas", "Campos", "Referencia 2"));

		listPersonaBD.add(new Persona("006", "Demandante", "12340", "clase1",
				"dni", "999", "Felipe", "Mesi", "Pizarro", "Referencia 3"));

		List<String> listInculpadoBD = new ArrayList<String>();

		for (Persona per : listPersonaBD) {
			listInculpadoBD.add(per.getNombreCompleto());
		}

		for (String incul : listInculpadoBD) {
			if (incul.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(incul.toUpperCase());
			}
		}

		return results;
	}

	public List<String> completePersona(String query) {
		List<String> results = new ArrayList<String>();

		List<Persona> listPersonaBD = new ArrayList<Persona>();

		listPersonaBD
				.add(new Persona("001", "Demandado", "12345", "clase1", "dni",
						"123", "Jhonattan", "Saldana", "Camacho",
						"Referencia 1"));

		listPersonaBD.add(new Persona("002", "Demandado", "12346", "clase1",
				"ruc", "345", "Jose", "Salazar", "Campos", "Referencia 2"));

		listPersonaBD.add(new Persona("003", "Demandado", "12347", "clase1",
				"dni", "234", "Pedro", "Ramos", "Pizarro", "Referencia 3"));

		listPersonaBD.add(new Persona("004", "Demandante", "12348", "clase1",
				"dni", "256", "Luis", "Zaravia", "Torres", "Referencia 1"));
		listPersonaBD.add(new Persona("005", "Demandante", "12349", "clase1",
				"ruc", "157", "Lucas", "Vargas", "Campos", "Referencia 2"));

		listPersonaBD.add(new Persona("006", "Demandante", "12340", "clase1",
				"dni", "999", "Felipe", "Mesi", "Pizarro", "Referencia 3"));

		for (Persona pers : listPersonaBD) {
			if (pers.getRazonSocial().toLowerCase()
					.startsWith(query.toLowerCase())
					|| pers.getApellidoPaterno().toLowerCase()
							.startsWith(query.toLowerCase())
					|| pers.getApellidoMaterno().toLowerCase()
							.startsWith(query.toLowerCase())) {
				results.add(pers.getNombreCompleto().toUpperCase());
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
				results.add(new Oficina(ofic.getTerritorio().toUpperCase(),ofic.getCodigo().toUpperCase(),ofic.getNombre().toUpperCase()));
			}
		}

		return results;
	}

	public List<String> completeEstudio(String query) {
		List<String> results = new ArrayList<String>();

		List<Estudio> estudiosBD = new ArrayList<Estudio>();
		estudiosBD.add(new Estudio("123456789", "Saragoza", "direccion1",
				"telefono1", "correo1"));
		estudiosBD.add(new Estudio("456243455", "San Lorenzo", "direccion2",
				"telefono2", "correo2"));
		estudiosBD.add(new Estudio("521333444", "Peña S.A.", "direccion3",
				"telefono3", "correo3"));
		estudiosBD.add(new Estudio("345444444", "Andres Socrates S.A.", "direccion4",
				"telefono4", "correo4"));

		for (Estudio est : estudiosBD) {
			if (est.getNombre().toLowerCase().startsWith(query.toLowerCase())) {
				results.add(est.getNombre().toUpperCase());
			}
		}

		return results;
	}

	public List<String> completeAbogado(String query) {
		List<String> results = new ArrayList<String>();
		List<Abogado> abogadosBD = new ArrayList<Abogado>();
		abogadosBD = new ArrayList<Abogado>();
		abogadosBD.add(new Abogado(new Estudio("123", "estudio1", "direccion1",
				"telefono1", "correo1"), "234", "44886421", "Luis", "Suarez",
				"Peña", "4644242", "lsuarez@hotmail.com"));
		abogadosBD.add(new Abogado(new Estudio("124", "estudio2", "direccion2",
				"telefono2", "correo2"), "235", "44886421", "Anthony",
				"Cabrera", "Barrios", "4644243", "lsuarez@hotmail.com"));

		abogadosBD.add(new Abogado(new Estudio("125", "estudio3", "direccion3",
				"telefono3", "correo3"), "236", "44886421", "Gian", "Guerrero",
				"Garcia", "4644244", "lsuarez@hotmail.com"));

		abogadosBD.add(new Abogado(new Estudio("126", "estudio4", "direccion4",
				"telefono4", "correo4"), "237", "44886421", "Pedro", "Pizarro",
				"Lopez", "4644245", "lsuarez@hotmail.com"));
		
		for (Abogado abog : abogadosBD) {
			if (abog.getNombre().toLowerCase().startsWith(query.toLowerCase())
					|| abog.getApellidoPaterno().toLowerCase().startsWith(query.toLowerCase())
							|| abog.getApellidoMaterno().toLowerCase().startsWith(query.toLowerCase())	) {
				results.add(abog.getNombreCompleto().toUpperCase());
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

		List<String> listDistrito = new ArrayList<String>();
		listDistrito.add("Comas,Lima,Lima");
		listDistrito.add("San Miguel,Lima,Lima");
		listDistrito.add("Callao,Lima,Lima");
		listDistrito.add("Surco,Lima,Lima");

		for (String distr : listDistrito) {
			if (distr.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(distr.toUpperCase());
			}
		}

		return results;
	}

	// autocompletes
	public List<Usuario> completeResponsable(String query) {

		List<Usuario> results = new ArrayList<Usuario>();
		List<Usuario> listUsuarioBD = new ArrayList<Usuario>();
		listUsuarioBD.add(new Usuario("001", "Legal", "Sandoval Sierra","Marco"));
		listUsuarioBD.add(new Usuario("002", "Legal", "Perez Landeo", "Juan"));
		listUsuarioBD.add(new Usuario("003", "Legal", "Lucar Zuñiga", "Luis"));
		listUsuarioBD.add(new Usuario("004", "Ilegal", "Cabrera Barrios","Eder"));
		listUsuarioBD.add(new Usuario("005", "Ilegal", "Leon Payano", "Mauro"));
		listUsuarioBD.add(new Usuario("006", "Ilegal", "Ñahui Salazar","Jorge"));

		for (Usuario usuario : listUsuarioBD) {
			if (usuario.getNombres().toLowerCase()
					.startsWith(query.toLowerCase())
					|| usuario.getApellidos().toLowerCase()
							.startsWith(query.toLowerCase())
					|| usuario.getCodigo().startsWith(query)) {
				results.add(new Usuario(usuario.getCodigo(), usuario.getTipo().toUpperCase(), usuario.getApellidos().toUpperCase(), usuario.getNombres().toUpperCase()));
			}
		}

		return results;
	}

	// listener cada vez que se modifica el proceso
	public void cambioProceso() {

		if (getProceso() != null && !getProceso().equals("")) {
			
			if(getProceso().equals("001") || getProceso().equals("003")){

				setTabCaucion(true);
			}
			
			if(getProceso().equals("002")){

				setTabAsigEstExt(true);
				setTabCuanMat(true);
			}
			
			
			Map<String, String> vias1BD = new HashMap<String, String>();
			vias1BD.put("Abreviado", "001");
			vias1BD.put("Conocimiento", "002");
			vias1BD.put("Sumarisimo", "003");

			Map<String, String> vias2BD = new HashMap<String, String>();
			vias2BD.put("Conocimiento", "002");
			vias2BD.put("Sumarisimo", "003");

			Map<String, String> vias3BD = new HashMap<String, String>();
			vias3BD.put("Conocimiento", "002");
			vias3BD.put("Sumarisimo", "003");

			Map<String, Map<String, String>> viaBD = new HashMap<String, Map<String, String>>();
			viaBD.put("001", vias1BD);
			viaBD.put("002", vias2BD);
			viaBD.put("003", vias3BD);
			
			setVias(viaBD.get(getProceso()));
			
			Map<String, String> instancia1BD = new HashMap<String, String>();
			instancia1BD.put("1ra Instancia", "001");
			
			Map<String, String> instancia2BD = new HashMap<String, String>();
			instancia2BD.put("1ra Instancia", "001");
			instancia2BD.put("2da Instancia Adm.", "002");
			instancia2BD.put("Casacion", "003");
			instancia2BD.put("Queja de derecho", "004");
			
			Map<String, String> instancia3BD = new HashMap<String, String>();
			instancia3BD.put("1ra Instancia", "001");
			instancia3BD.put("2da Instancia Adm.", "002");
			
			
			Map<String, Map<String, String>> instanciaBD = new HashMap<String, Map<String, String>>();
			instanciaBD.put("001", instancia1BD);
			instanciaBD.put("002", instancia2BD);
			instanciaBD.put("003", instancia3BD);

			setInstancias(instanciaBD.get(getProceso()));

		} else {
			vias = new HashMap<String, String>();
			
		}

	}

	public RegistroExpedienteMB()  {

	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public List<Inculpado> getInculpadosDataModel() {

		if (inculpadosDataModel == null) {
			inculpadosDataModel = new ArrayList<Inculpado>();
		}

		return inculpadosDataModel;
	}

	public void setInculpadosDataModel(List<Inculpado> inculpadosDataModel) {
		this.inculpadosDataModel = inculpadosDataModel;
	}

	public Cuantia getSelectCuantia() {
		return selectCuantia;
	}

	public void setSelectCuantia(Cuantia selectCuantia) {
		this.selectCuantia = selectCuantia;
	}

	public CuantiaDataModel getCuantiaDataModel() {

		if (cuantiaDataModel == null) {
			List<Cuantia> cuantias = new ArrayList<Cuantia>();
			cuantiaDataModel = new CuantiaDataModel(cuantias);

		}

		return cuantiaDataModel;
	}

	public void setCuantiaDataModel(CuantiaDataModel cuantiaDataModel) {
		this.cuantiaDataModel = cuantiaDataModel;
	}

	public String getNroExpeOficial() {
		return nroExpeOficial;
	}

	public void setNroExpeOficial(String nroExpeOficial) {
		this.nroExpeOficial = nroExpeOficial;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
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

	public String getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
	}

	public String getSlctTipo() {
		return slctTipo;
	}

	public void setSlctTipo(String slctTipo) {
		this.slctTipo = slctTipo;
	}

	public String getSlctDescripcion() {
		return slctDescripcion;
	}

	public void setSlctDescripcion(String slctDescripcion) {
		this.slctDescripcion = slctDescripcion;
	}

	public String getSlctContraCuatela() {
		return slctContraCuatela;
	}

	public void setSlctContraCuatela(String slctContraCuatela) {
		this.slctContraCuatela = slctContraCuatela;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public Organo getSelectedOrgano() {
		if(selectedOrgano ==  null){
			selectedOrgano = new Organo();
		}
		
		return selectedOrgano;
	}

	public void setSelectedOrgano(Organo selectedOrgano) {
		this.selectedOrgano = selectedOrgano;
	}

	public List<String> getMonedas() {
		if (monedas == null) {
			monedas = new ArrayList<String>();
			monedas.add("S/.");
			monedas.add("$");
			monedas.add("€");
		}

		return monedas;
	}

	public void setMonedas(List<String> monedas) {
		this.monedas = monedas;
	}

	public List<String> getContraCautelas() {
		if(contraCautelas == null){
			contraCautelas = new ArrayList<String>();
			contraCautelas.add("Personal");
			contraCautelas.add("Real");
		}
		
		
		return contraCautelas;
	}

	public void setContraCautelas(List<String> contraCautelas) {
		this.contraCautelas = contraCautelas;
	}

	public List<String> getTipoCautelar() {
		
		if(tipoCautelar == null){
			tipoCautelar = new ArrayList<String>();
			tipoCautelar.add("Generico");
			tipoCautelar.add("Embargo");
		}
		
		return tipoCautelar;
	}

	public void setTipoCautelar(List<String> tipoCautelar) {
		this.tipoCautelar = tipoCautelar;
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

	public Map<String, String> getVias() {
		
		if(vias== null){
			
			vias = new HashMap<String, String>();
		}
		
		Set<String> set = vias.keySet();

	    Iterator<String> itr = set.iterator();
	    
	    while (itr.hasNext()) {
	      String str = itr.next();
	      System.out.println(str + ": " + vias.get(str));
	    }
				
		return vias;
	}

	public void setVias(Map<String, String> vias) {
		this.vias = vias;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
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

	public Map<String, String> getTipos() {
		
		if(tipos == null){
			tipos = new HashMap<String, String>();
			tipos.put("Favor del Banco", "001");
			tipos.put("Contra del banco", "002");
		}
		
		
		return tipos;
	}

	public void setTipos(Map<String, String> tipos) {
		this.tipos = tipos;
	}

	public Map<String, String> getInstancias() {
		if (instancias == null) {
			
			instancias = new HashMap<String, String>();
		}
		
		
		Set<String> set = instancias.keySet();

	    Iterator<String> itr = set.iterator();
	    
	    while (itr.hasNext()) {
	      String str = itr.next();
	      System.out.println(str + ": " + instancias.get(str));
	    }
		
		return instancias;
	}

	public void setInstancias(Map<String, String> instancias) {
		this.instancias = instancias;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
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

	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

	public List<String> getRiesgos() {
		if (riesgos == null) {
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

	public String getEstadoCautelar() {
		return estadoCautelar;
	}

	public void setEstadoCautelar(String estadoCautelar) {
		this.estadoCautelar = estadoCautelar;
	}

	public List<String> getEstadosCautelares() {
		
		if(estadosCautelares == null){
			estadosCautelares = new ArrayList<String>();
			estadosCautelares.add("Iniciado");
			estadosCautelares.add("En Tramite");
			estadosCautelares.add("Terminado");
		}
		
		return estadosCautelares;
	}

	public void setEstadosCautelares(List<String> estadosCautelares) {
		this.estadosCautelares = estadosCautelares;
	}

	public Persona getPersona() {
		if(persona==null){
			persona = new Persona();
		}
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public PersonaDataModel getPersonaDataModel() {

		if (personaDataModel == null) {
			List<Persona> listAuxPersona = new ArrayList<Persona>();
			personaDataModel = new PersonaDataModel(listAuxPersona);
		}
		return personaDataModel;
	}

	public void setPersonaDataModel(PersonaDataModel personaDataModel) {
		this.personaDataModel = personaDataModel;
	}

	public Persona getSelectPersona() {
		if (selectPersona == null) {
			selectPersona = new Persona();
		}
		return selectPersona;
	}

	public void setSelectPersona(Persona selectPersona) {
		this.selectPersona = selectPersona;
	}

	public List<String> getListSituacion() {

		if (listSituacion == null) {

			listSituacion = new ArrayList<String>();
			listSituacion.add("Situacion 1");
			listSituacion.add("Situacion 2");
			listSituacion.add("Situacion 3");
			listSituacion.add("Situacion 4");
		}
		return listSituacion;
	}

	public void setListSituacion(List<String> listSituacion) {
		this.listSituacion = listSituacion;
	}

	public Inculpado getInculpado() {
		
		if(inculpado == null){
			inculpado = new Inculpado();
		}
		return inculpado;
	}

	public void setInculpado(Inculpado inculpado) {
		this.inculpado = inculpado;
	}

	public Abogado getAbogado() {

		if (abogado == null) {
			abogado = new Abogado();
		}
		return abogado;
	}

	public void setAbogado(Abogado abogado) {
		this.abogado = abogado;
	}

	public AbogadoDataModel getAbogadoDataModel() {
		if (abogadoDataModel == null) {

			List<Abogado> abogadosBD = new ArrayList<Abogado>();
			abogadosBD = new ArrayList<Abogado>();
			abogadosBD
					.add(new Abogado(new Estudio("123", "estudio1",
							"direccion1", "telefono1", "correo1"), "234",
							"44886421", "luis", "suarez", "peña", "4644242",
							"lsuarez@hotmail.com"));
			abogadosBD.add(new Abogado(new Estudio("124", "estudio2",
					"direccion2", "telefono2", "correo2"), "235", "44886421",
					"anthony", "cabrera", "barrios", "4644243",
					"lsuarez@hotmail.com"));

			abogadosBD.add(new Abogado(new Estudio("125", "estudio3",
					"direccion3", "telefono3", "correo3"), "236", "44886421",
					"gian", "guerrero", "garcia", "4644244",
					"lsuarez@hotmail.com"));

			abogadosBD.add(new Abogado(new Estudio("126", "estudio4",
					"direccion4", "telefono4", "correo4"), "237", "44886421",
					"pedro", "pizarro", "lopez", "4644245",
					"lsuarez@hotmail.com"));
			abogadoDataModel = new AbogadoDataModel(abogadosBD);

		}

		return abogadoDataModel;
	}

	public void setAbogadoDataModel(AbogadoDataModel abogadoDataModel) {
		this.abogadoDataModel = abogadoDataModel;
	}


	public EstudioDataModel getEstudioDataModel() {
		return estudioDataModel;
	}

	public void setEstudioDataModel(EstudioDataModel estudioDataModel) {
		this.estudioDataModel = estudioDataModel;
	}

	public boolean isTabAsigEstExt() {
		return tabAsigEstExt;
	}

	public void setTabAsigEstExt(boolean tabAsigEstExt) {
		this.tabAsigEstExt = tabAsigEstExt;
	}

	public boolean isTabCaucion() {
		return tabCaucion;
	}

	public void setTabCaucion(boolean tabCaucion) {
		this.tabCaucion = tabCaucion;
	}

	public boolean isTabCuanMat() {
		return tabCuanMat;
	}

	public void setTabCuanMat(boolean tabCuanMat) {
		this.tabCuanMat = tabCuanMat;
	}

	public String getTodoResumen() {
		return todoResumen;
	}

	public void setTodoResumen(String todoResumen) {
		this.todoResumen = todoResumen;
	}

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSecretario() {
		return secretario;
	}

	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}

	public Date getFechaResumen() {
		if(fechaResumen == null){
			Calendar cal = Calendar.getInstance();
			fechaResumen = cal.getTime();
		}
		return fechaResumen;
	}

	public void setFechaResumen(Date fechaResumen) {
		this.fechaResumen = fechaResumen;
	}

	public Cuantia getCuantia() {
		if (cuantia == null) {
			cuantia = new Cuantia();

		}
		return cuantia;
	}

	public void setCuantia(Cuantia cuantia) {
		this.cuantia = cuantia;
	}

	public List<Honorario> getHonorarioDetalle() {
		if (honorarioDetalle == null) {

			honorarioDetalle = new ArrayList<Honorario>();

		}
		return honorarioDetalle;
	}

	public void setHonorarioDetalle(List<Honorario> honorarioDetalle) {
		this.honorarioDetalle = honorarioDetalle;
	}

	public String getOrganotxt() {
		return organotxt;
	}

	public void setOrganotxt(String organotxt) {
		this.organotxt = organotxt;
	}

	public Organo getOrgano() {
		if (organo == null) {
			organo = new Organo();

		}
		return organo;
	}

	public void setOrgano(Organo organo) {
		this.organo = organo;
	}

	public OrganoDataModel getOrganoDataModel() {

		if (organoDataModel == null) {

			List<Organo> listOrganoBD = new ArrayList<Organo>();

			listOrganoBD.add(new Organo("Comisaria PNP de PRO", "Comas",
					"Lima", "Lima", "2345", "Ministerio"));
			listOrganoBD.add(new Organo("Juzgado Penal del Callao", "Callao",
					"Lima", "Lima", "4323", "Poder Judicial"));
			listOrganoBD.add(new Organo("Comisaria de San Miguel",
					"San Miguel", "Lima", "Lima", "4567", "Ministerio"));
			listOrganoBD.add(new Organo("Comisaria de Magdalena del Mar",
					"Magdalena del Mar", "Lima", "Lima", "3452", "Ministerio"));
			listOrganoBD.add(new Organo("Juzgado Penal de Chorrillos",
					"Chorrillos", "Lima", "Lima", "2343", "Poder Judicial"));

			organoDataModel = new OrganoDataModel(listOrganoBD);

		}

		return organoDataModel;
	}

	public void setOrganoDataModel(OrganoDataModel organoDataModel) {
		this.organoDataModel = organoDataModel;
	}

	public Persona getSelectInculpado() {
		if (selectInculpado == null) {
			selectInculpado = new Persona();

		}
		return selectInculpado;
	}

	public void setSelectInculpado(Persona selectInculpado) {
		this.selectInculpado = selectInculpado;
	}

	

}
