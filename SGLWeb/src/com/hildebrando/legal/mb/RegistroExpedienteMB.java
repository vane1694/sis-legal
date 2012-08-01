package com.hildebrando.legal.mb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Expression;
import org.primefaces.event.RowEditEvent;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;

import com.hildebrando.legal.domains.Cuantia;
import com.hildebrando.legal.domains.Inculpado;
import com.hildebrando.legal.domains.Persona;

import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Honorario;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;

import com.hildebrando.legal.service.RegistroExpedienteService;
import com.hildebrando.legal.view.AbogadoDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.HonorarioDataModel;
import com.hildebrando.legal.view.OrganoDataModel;
import com.hildebrando.legal.view.PersonaDataModel;

@ManagedBean(name = "registExpe")
@SessionScoped
public class RegistroExpedienteMB {

	public static Logger logger = Logger.getLogger(RegistroExpedienteMB.class);

	private int proceso;
	private List<Proceso> procesos;
	private String via;
	private List<Via> vias;
	private String instancia;
	private List<Instancia> instancias;
	private Usuario responsable;
	private String nroExpeOficial;
	private Date iniProceso;
	private String estado;
	private List<EstadoExpediente> estados;
	private Oficina oficina;
	private String tipo;
	private Organo organo1;
	private List<TipoExpediente> tipos;
	private List<Entidad> entidades;
	private String secretario;
	private String calificacion;
	private List<Calificacion> calificaciones;
	private Recurrencia recurrencia;
	private Abogado abogado;
	private AbogadoDataModel abogadoDataModel;
	private List<TipoHonorario> tipoHonorarios; 
	private Honorario honorario;
	private List<Moneda> monedas;
	private List<SituacionHonorario> situacionHonorarios;
	private Persona persona;
	private PersonaDataModel personaDataModel;
	private Persona selectedPersona;
	private PersonaDataModel personaDataModelBusq;
	private Cuantia cuantia;
	private Cuantia selectedCuantia;
	private CuantiaDataModel cuantiaDataModel;
	private Inculpado inculpado;
	private Inculpado selectedInculpado;
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
	
	private List<Honorario> honorarios;
	private Honorario selectedHonorario;
	private Persona selectPersona;
	private Persona selectInculpado;
	private List<String> contraCautelas;
	private boolean tabAsigEstExt;
	private boolean tabCaucion;
	private boolean tabCuanMat;

	@ManagedProperty(value = "#{registroService}")
	private RegistroExpedienteService expedienteService;

	public void setExpedienteService(RegistroExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public void agregarTodoResumen(ActionEvent e) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		setTodoResumen((getResumen() + "\n" + format.format(getFechaResumen())));
		
	}

	public void deleteHonorario() {

		honorarios.remove(getSelectedHonorario());
		
	}

	public void deletePersona() {

		List<Persona> personas = (List<Persona>) getPersonaDataModel()
				.getWrappedData();
		personas.remove(getSelectedPersona());

		personaDataModel = new PersonaDataModel(personas);
	}

	public void deleteCuantia() {

		List<Cuantia> cuantias = (List<Cuantia>) getCuantiaDataModel()
				.getWrappedData();
		cuantias.remove(getSelectedCuantia());

		cuantiaDataModel = new CuantiaDataModel(cuantias);
	}

	public void deleteInculpado() {

		inculpadosDataModel.remove(getSelectedInculpado());

	}

	// public void iniDetalleHonorario() {
	// Map<String, String> paramMap = FacesContext.getCurrentInstance()
	// .getExternalContext().getRequestParameterMap();
	// int cuotas = Integer.parseInt(paramMap.get("cuotas"));
	//
	// honorarioDetalle = new ArrayList<Honorario>();
	// honorarioDetalle.add(new Honorario("1", "123-001", "S/.", "440",
	// "30/06/2012", "Sin Pagar"));
	// honorarioDetalle.add(new Honorario("2", "123-002", "S/.", "440",
	// "30/07/2012", "Pagado"));
	// }

	public String buscarAbogado(ActionEvent e) {
		List<Abogado> results = new ArrayList<Abogado>();

		List<Abogado> abogadosBD = new ArrayList<Abogado>();
		abogadosBD = new ArrayList<Abogado>();

		for (Abogado abogado : abogadosBD) {

			if (abogado.getRegistroca().equals(getAbogado().getRegistroca())) {
				results.add(abogado);

			}

		}
		abogadoDataModel = new AbogadoDataModel(results);

		return null;
	}

	public void agregarHonorario(ActionEvent e) {

		
		honorarios.add(getHonorario());

//		Set<Cuota> cuotas = new HashSet<Cuota>();
//		 for (int i = 1; i <= getHonorario().getCuotas(); i++) {
//			 Calendar cal = Calendar.getInstance();
//			 iniProceso = cal.getTime();
//				
//			 Cuota cuota= new Cuota();
//			 cuota.setFechaPago(fechaPago);
//			 
//			 
//		 honorarioDetalleNuevo.add(new Honorario(i + "", "123-001", "S/.",
//		 "", "30/06/2012", "Sin Pagar"));
//		
//		 }
//
//		setHonorarioDetalle(honorarioDetalleNuevo);

		abogado = new Abogado();

	}

	public String agregarAbogado(ActionEvent e) {

		logger.info("Ingreso al agregarAbogado..");
		List<Abogado> abogadosBD = new ArrayList<Abogado>();
		abogadoDataModel = new AbogadoDataModel(abogadosBD);

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Abogado Agregado", "Abogado Agregado");

		FacesContext.getCurrentInstance().addMessage(null, msg);

		return null;
	}

	public String buscarPersona(ActionEvent e) {

		List<Persona> personas = new ArrayList<Persona>();
		List<Persona> listPersonaBD = new ArrayList<Persona>();

		listPersonaBD
				.add(new Persona("001", "Demandado", "12345", "clase1", "dni",
						"123", "Jhonattan", "Saldana", "Camacho",
						"Referencia 1"));

		listPersonaBD
				.add(new Persona("002", "Demandado", "12346", "clase1", "ruc",
						"34534534", "Jose", "Salazar", "Campos", "Referencia 2"));

		listPersonaBD
				.add(new Persona("003", "Demandado", "12347", "clase1", "dni",
						"23423423", "Pedro", "Ramos", "Pizarro", "Referencia 3"));

		listPersonaBD
				.add(new Persona("004", "Demandante", "12348", "clase1", "dni",
						"25625625", "Luis", "Zaravia", "Torres", "Referencia 1"));
		listPersonaBD
				.add(new Persona("005", "Demandante", "12349", "clase1", "ruc",
						"15715715", "Lucas", "Vargas", "Campos", "Referencia 2"));

		listPersonaBD
				.add(new Persona("006", "Demandante", "12340", "clase1", "dni",
						"99988877", "Felipe", "Mesi", "Pizarro", "Referencia 3"));

		for (Persona pers : listPersonaBD) {

			if (pers.getCodCliente().trim()
					.equalsIgnoreCase(getPersona().getCodCliente().trim())
					&& pers.getNroDoc().trim()
							.equalsIgnoreCase(getPersona().getNroDoc().trim())) {
				personas.add(pers);
			}

		}
		personaDataModelBusq = new PersonaDataModel(personas);

		return null;

	}

	public void buscarOrganos(ActionEvent actionEvent) {

		List<Organo> sublistOrgano = new ArrayList<Organo>();

		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		List<Organo> organos = (List<Organo>) paramMap.get("organos");

		if (getOrgano().getEntidad().getIdEntidad() == 0
				&& getOrgano().getNombre() == ""
				&& getOrgano().getTerritorio().getIdTerritorio() == 0) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Ingresar un campo", "Ingresar un campo");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} else {

			if (getOrgano().getEntidad().getIdEntidad() != 0
					&& getOrgano().getNombre() == ""
					&& getOrgano().getTerritorio().getIdTerritorio() == 0) {

				for (Organo orgn : organos) {

					if (orgn.getEntidad().getIdEntidad() == getOrgano()
							.getEntidad().getIdEntidad()) {
						sublistOrgano.add(orgn);
					}
				}

			} else {
				if (getOrgano().getEntidad().getIdEntidad() == 0
						&& getOrgano().getNombre() != ""
						&& getOrgano().getTerritorio().getIdTerritorio() == 0) {

					for (Organo orgn : organos) {

						if (orgn.getNombre().equalsIgnoreCase(
								getOrgano().getNombre())) {
							sublistOrgano.add(orgn);
						}
					}

				} else {

					if (getOrgano().getEntidad().getIdEntidad() == 0
							&& getOrgano().getNombre() == ""
							&& getOrgano().getTerritorio().getIdTerritorio() != 0) {

						for (Organo orgn : organos) {

							if (orgn.getTerritorio()
									.getDistrito()
									.equalsIgnoreCase(
											getOrgano().getTerritorio()
													.getDistrito())) {
								sublistOrgano.add(orgn);
							}
						}

					} else {

						if (getOrgano().getEntidad().getIdEntidad() != 0
								&& getOrgano().getNombre() != ""
								&& getOrgano().getTerritorio()
										.getIdTerritorio() == 0) {

							for (Organo orgn : organos) {

								if (orgn.getEntidad().getIdEntidad() == getOrgano()
										.getEntidad().getIdEntidad()
										&& orgn.getNombre().equalsIgnoreCase(
												getOrgano().getNombre())) {
									sublistOrgano.add(orgn);
								}
							}

						} else {
							if (getOrgano().getEntidad().getIdEntidad() != 0
									&& getOrgano().getNombre() == ""
									&& getOrgano().getTerritorio()
											.getIdTerritorio() != 0) {
								for (Organo orgn : organos) {

									if (orgn.getEntidad().getIdEntidad() == getOrgano()
											.getEntidad().getIdEntidad()
											&& orgn.getTerritorio()
													.getDistrito()
													.equalsIgnoreCase(
															getOrgano()
																	.getTerritorio()
																	.getDistrito())) {
										sublistOrgano.add(orgn);
									}
								}
							} else {
								if (getOrgano().getEntidad().getIdEntidad() == 0
										&& getOrgano().getNombre() != ""
										&& getOrgano().getTerritorio()
												.getIdTerritorio() != 0) {
									for (Organo orgn : organos) {

										if (orgn.getNombre().equalsIgnoreCase(
												getOrgano().getNombre())
												&& orgn.getTerritorio()
														.getDistrito()
														.equalsIgnoreCase(
																getOrgano()
																		.getTerritorio()
																		.getDistrito())) {
											sublistOrgano.add(orgn);
										}
									}
								}

							}
						}

					}

				}

			}

		}

		organoDataModel = new OrganoDataModel(sublistOrgano);

	}

	public void agregarOrgano(ActionEvent e) {

		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		List<Organo> organos = (List<Organo>) paramMap.get("organos");
		int flag = 0;

		for (Organo orgn : organos) {
			if (orgn.getNombre().toUpperCase()
					.equalsIgnoreCase(getOrgano().getNombre().toUpperCase())) {
				flag = 1;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Organo Existente", "Organo Existente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				break;

			}
		}

		if (getOrgano().getEntidad().getIdEntidad() != 0
				&& getOrgano().getNombre() != ""
				&& getOrgano().getTerritorio().getIdTerritorio() != 0 && flag ==0) {
		
			   GenericDaoImpl<Organo, Integer> genericoDao=(GenericDaoImpl<Organo, Integer>) SpringInit.getApplicationContext().getBean("genericoDao");
			   Organo organo= new Organo();
			   organo.setEntidad(getOrgano().getEntidad());
			   organo.setIdOrgano(getOrgano().getIdOrgano());
			   organo.setNombre(getOrgano().getNombre());
			   
			   try {
				genericoDao.insertar(organo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Organo Agregado", "Organo Agregado"));
			

		} else {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Ingresar Campos", "Ingresar Campos"));

		}

	}

	public String agregarCuantia(ActionEvent e) {

		List<Cuantia> cuantias;
		if (cuantiaDataModel == null) {
			cuantias = new ArrayList<Cuantia>();
		} else {
			cuantias = (List<Cuantia>) cuantiaDataModel.getWrappedData();
		}

		cuantias.add(getCuantia());

		cuantiaDataModel = new CuantiaDataModel(cuantias);

		cuantia = new Cuantia();

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

		List<Persona> personas;
		if (personaDataModel == null) {
			personas = new ArrayList<Persona>();
		} else {
			personas = (List<Persona>) personaDataModel.getWrappedData();
		}

		personas.add(getPersona());
		personaDataModel = new PersonaDataModel(personas);

		persona = new Persona();

		return null;

	}

	public String agregarInculpado(ActionEvent e) {

		if (inculpadosDataModel == null) {

			inculpadosDataModel = new ArrayList<Inculpado>();
		}

		inculpadosDataModel.add(getInculpado());

		inculpado = new Inculpado();

		return null;

	}

	public String agregarDetallePersona(ActionEvent e) {

		logger.info("Ingreso a agregarDetallePersona..");

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Persona Agregada", "Persona Agregada");

		FacesContext.getCurrentInstance().addMessage(null, msg);

		return null;

	}

	public String agregarDetalleInculpado(ActionEvent e) {

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Inculpado Agregado", "Inculpado Agregado");

		FacesContext.getCurrentInstance().addMessage(null, msg);

		List<Persona> personas = new ArrayList<Persona>();
		personaDataModelBusq = new PersonaDataModel(personas);

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

		// GenericDao<Usuarios, Integer> entidadDAO = (GenericDao<Usuarios,
		// Integer>) SpringInit.getApplicationContext().getBean("genericoDao");

		return "registroExpediente2";

	}

	public List<Recurrencia> completeRecurrencia(String query) {
		List<Recurrencia> results = new ArrayList<Recurrencia>();
		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		List<Recurrencia> recurrencias = (List<Recurrencia>) paramMap
				.get("recurrencias");

		for (Recurrencia rec : recurrencias) {
			if (rec.getNombre().toLowerCase().startsWith(query.toLowerCase())) {
				results.add(rec);
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
		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		List<Oficina> oficinas = (List<Oficina>) paramMap.get("oficinas");

		for (Oficina oficina : oficinas) {

			String texto = oficina.getIdOficina() + " "
					+ oficina.getNombre().toUpperCase() + " ("
					+ oficina.getTerritorio().getDepartamento().toUpperCase()
					+ ")";

			if (texto.contains(query.toUpperCase())) {
				oficina.setNombreDetallado(texto);
				results.add(oficina);
			}
		}

		return results;
	}

	public List<String> completeEstudio(String query) {
		List<String> results = new ArrayList<String>();
		Map<String, Object> paramMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
		List<Estudio> estudios = (List<Estudio>) paramMap.get("estudios");

		for (Estudio est : estudios) {
			if (est.getNombre().toUpperCase().contains(query.toUpperCase())) {
				results.add(est.getNombre().toUpperCase());
			}
		}

		return results;
	}

	public List<String> completeAbogado(String query) {
		List<String> results = new ArrayList<String>();

		List<Abogado> abogados = new ArrayList<Abogado>();
		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		abogados = (List<Abogado>) paramMap.get("abogados");

		

		for (Abogado abog : abogados) {

			if (abog.getNombreCompleto().toUpperCase().contains(query.toUpperCase()) ) {
				results.add(abog.getNombreCompleto());
			}
		}

		return results;
	}

	public List<Organo> completeOrgano(String query) {
		List<Organo> results = new ArrayList<Organo>();

		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		List<Organo> organos = (List<Organo>) paramMap.get("organos");

		for (Organo organo : organos) {
			if (organo.getNombre().toLowerCase()
					.startsWith(query.toLowerCase())) {
				String descripcion = organo.getNombre().toUpperCase()
						+ " ("
						+ organo.getTerritorio().getDistrito().toUpperCase()
						+ ", "
						+ organo.getTerritorio().getProvincia().toUpperCase()
						+ ", "
						+ organo.getTerritorio().getDepartamento()
								.toUpperCase() + ")";
				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}

		return results;
	}

	public List<Territorio> completeDistrito(String query) {
		List<Territorio> results = new ArrayList<Territorio>();

		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		List<Territorio> territorios = (List<Territorio>) paramMap
				.get("territorios");

		for (Territorio terr : territorios) {
			if (terr.getDistrito().toUpperCase()
					.startsWith(query.toUpperCase())) {
				terr.setDescripcionDistrito(terr.getDistrito() + ", "
						+ terr.getProvincia() + ", " + terr.getDepartamento());
				results.add(terr);
			}
		}

		return results;
	}

	// autocompletes
	public List<Usuario> completeResponsable(String query) {
		List<Usuario> results = new ArrayList<Usuario>();

		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		List<Usuario> usuarios = (List<Usuario>) paramMap.get("usuarios");

		for (Usuario usuario : usuarios) {

			if (usuario.getNombreCompleto().toUpperCase()
					.contains(query.toUpperCase())) {
				results.add(usuario);
			}
		}

		return results;
	}

	// listener cada vez que se modifica el proceso
	public void cambioProceso() {

		if (getProceso() != 0) {

			if (getProceso() == 1 || getProceso() == 3) {

				setTabCaucion(true);
			}

			if (getProceso() == 2) {

				setTabAsigEstExt(true);
				setTabCuanMat(true);
			}

			GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Via.class);
			filtro.add(Expression.like("proceso.idProceso", getProceso()));

			try {
				vias = viaDao.buscarDinamico(filtro);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			vias = new ArrayList<Via>();

		}

	}

	public RegistroExpedienteMB() {
		logger.debug("Inicializando valores registro expediente");
		inicializar();
	}

	@SuppressWarnings("unchecked")
	private void inicializar() {

		Map<String, Object> paramMap = FacesContext.getCurrentInstance()
				.getExternalContext().getApplicationMap();
		procesos = (List<Proceso>) paramMap.get("procesos");

		vias = new ArrayList<Via>();
		instancias = (List<Instancia>) paramMap.get("instancias");

		Calendar cal = Calendar.getInstance();
		iniProceso = cal.getTime();

		estados = (List<EstadoExpediente>) paramMap.get("estados");
		tipos = (List<TipoExpediente>) paramMap.get("tipos");
		entidades = (List<Entidad>) paramMap.get("entidades");
		calificaciones = (List<Calificacion>) paramMap.get("calificaciones");
		
		honorario = new Honorario();
		
		tipoHonorarios= (List<TipoHonorario>) paramMap.get("tipohonorarios");
		monedas = (List<Moneda>) paramMap.get("monedas");
		
		situacionHonorarios = (List<SituacionHonorario>) paramMap.get("situacionHonorarios");

	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Editado","Honorario Editado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Cancelado","Honorario Cancelado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public Date getIniProceso() {
		return iniProceso;
	}

	public void setIniProceso(Date iniProceso) {

		this.iniProceso = iniProceso;
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
		if (selectedOrgano == null) {
			selectedOrgano = new Organo();
		}

		return selectedOrgano;
	}

	public void setSelectedOrgano(Organo selectedOrgano) {
		this.selectedOrgano = selectedOrgano;
	}

	public List<String> getContraCautelas() {
		if (contraCautelas == null) {
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

		if (tipoCautelar == null) {
			tipoCautelar = new ArrayList<String>();
			tipoCautelar.add("Generico");
			tipoCautelar.add("Embargo");
		}

		return tipoCautelar;
	}

	public void setTipoCautelar(List<String> tipoCautelar) {
		this.tipoCautelar = tipoCautelar;
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

	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

	public List<String> getRiesgos() {
		if (riesgos == null) {
			riesgos = new ArrayList<String>();
			riesgos.add("101 Errores en la operativa");
			riesgos.add("102 Controles deficientes");
			riesgos.add("103 Incumplimiento de la Normativa");
			riesgos.add("104 Errores de Gestion");
			riesgos.add("105 Errores en Documentacion");
			riesgos.add("201 Uso Fraudulento de Tarjetas");
			riesgos.add("202 Robos y Atracos");
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

		if (estadosCautelares == null) {
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
		if (persona == null) {
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

	

	public Inculpado getInculpado() {

		if (inculpado == null) {
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
			abogadoDataModel = new AbogadoDataModel(abogadosBD);

		}

		return abogadoDataModel;
	}

	public void setAbogadoDataModel(AbogadoDataModel abogadoDataModel) {
		this.abogadoDataModel = abogadoDataModel;
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
		if (fechaResumen == null) {
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

			List<Organo> listOrganoAux = new ArrayList<Organo>();
			organoDataModel = new OrganoDataModel(listOrganoAux);

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

	public PersonaDataModel getPersonaDataModelBusq() {
		if (personaDataModelBusq == null) {
			List<Persona> listAuxPersona = new ArrayList<Persona>();
			personaDataModelBusq = new PersonaDataModel(listAuxPersona);
		}
		return personaDataModelBusq;
	}

	public void setPersonaDataModelBusq(PersonaDataModel personaDataModelBusq) {
		this.personaDataModelBusq = personaDataModelBusq;
	}

	public Persona getSelectedPersona() {
		return selectedPersona;
	}

	public void setSelectedPersona(Persona selectedPersona) {
		this.selectedPersona = selectedPersona;
	}

	public Cuantia getSelectedCuantia() {
		return selectedCuantia;
	}

	public void setSelectedCuantia(Cuantia selectedCuantia) {
		this.selectedCuantia = selectedCuantia;
	}

	public Inculpado getSelectedInculpado() {
		return selectedInculpado;
	}

	public void setSelectedInculpado(Inculpado selectedInculpado) {
		this.selectedInculpado = selectedInculpado;
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public List<EstadoExpediente> getEstados() {
		return estados;
	}

	public void setEstados(List<EstadoExpediente> estados) {
		this.estados = estados;
	}

	public List<TipoExpediente> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoExpediente> tipos) {
		this.tipos = tipos;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}

	public List<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<Via> getVias() {
		return vias;
	}

	public void setVias(List<Via> vias) {
		this.vias = vias;
	}

	public List<Instancia> getInstancias() {
		return instancias;
	}

	public void setInstancias(List<Instancia> instancias) {
		this.instancias = instancias;
	}

	public int getProceso() {
		return proceso;
	}

	public void setProceso(int proceso) {
		this.proceso = proceso;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public List<Entidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidad> entidades) {
		this.entidades = entidades;
	}

	public Organo getOrgano1() {
		return organo1;
	}

	public void setOrgano1(Organo organo1) {
		this.organo1 = organo1;
	}

	public Recurrencia getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(Recurrencia recurrencia) {
		this.recurrencia = recurrencia;
	}

	public List<TipoHonorario> getTipoHonorarios() {
		return tipoHonorarios;
	}

	public void setTipoHonorarios(List<TipoHonorario> tipoHonorarios) {
		this.tipoHonorarios = tipoHonorarios;
	}

	public Honorario getHonorario() {
		
		return honorario;
	}

	public void setHonorario(Honorario honorario) {
		this.honorario = honorario;
	}

	public List<SituacionHonorario> getSituacionHonorarios() {
		return situacionHonorarios;
	}

	public void setSituacionHonorarios(List<SituacionHonorario> situacionHonorarios) {
		this.situacionHonorarios = situacionHonorarios;
	}

	

	public Honorario getSelectedHonorario() {
		return selectedHonorario;
	}

	public void setSelectedHonorario(Honorario selectedHonorario) {
		this.selectedHonorario = selectedHonorario;
	}

	public List<Honorario> getHonorarios() {
		if(honorarios == null){
			honorarios = new ArrayList<Honorario>();
		}
		return honorarios;
	}

	public void setHonorarios(List<Honorario> honorarios) {
		this.honorarios = honorarios;
	}


}
