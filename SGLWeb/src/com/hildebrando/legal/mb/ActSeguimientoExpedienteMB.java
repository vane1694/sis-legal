package com.hildebrando.legal.mb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Expression;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.ActividadProcesal;
import com.hildebrando.legal.modelo.Anexo;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Clase;
import com.hildebrando.legal.modelo.ContraCautela;
import com.hildebrando.legal.modelo.Cuantia;
import com.hildebrando.legal.modelo.Cuota;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoCautelar;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Etapa;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.FormaConclusion;
import com.hildebrando.legal.modelo.Hito;
import com.hildebrando.legal.modelo.Honorario;
import com.hildebrando.legal.modelo.Inculpado;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Provision;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Riesgo;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.SituacionActProc;
import com.hildebrando.legal.modelo.SituacionCuota;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.TipoCautelar;
import com.hildebrando.legal.modelo.TipoDocumento;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.TipoInvolucrado;
import com.hildebrando.legal.modelo.TipoProvision;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.RegistroExpedienteService;
import com.hildebrando.legal.view.AbogadoDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.InvolucradoDataModel;
import com.hildebrando.legal.view.OrganoDataModel;
import com.hildebrando.legal.view.PersonaDataModel;

@ManagedBean(name = "actSeguiExpe")
public class ActSeguimientoExpedienteMB {

	public static Logger logger = Logger
			.getLogger(ActSeguimientoExpedienteMB.class);

	private Expediente selectedExpediente;

	
	private Provision provision;
	private List<TipoProvision> tipoProvisiones;
	private List<Provision> provisiones;
	
	private String descripNotif;
	private List<Actividad> actividades;
	private List<Etapa> etapas;
	private List<SituacionActProc> situacionActProcesales;
	private List<ActividadProcesal> actividadProcesales;
	private ActividadProcesal selectedActPro;
	private ActividadProcesal actividadProcesal;

	private int proceso;
	private List<Proceso> procesos;
	private int via;
	private List<Via> vias;
	private int instancia;
	private List<Instancia> instancias;
	private Usuario responsable;
	private String nroExpeOficial;
	private Date inicioProceso;
	private int estado;
	private List<EstadoExpediente> estados;
	private Oficina oficina;
	private int tipo;
	private Organo organo1;
	private String descripcionDistrito;
	private List<TipoExpediente> tipos;
	private List<Entidad> entidades;
	private String secretario;
	private int calificacion;
	private List<Calificacion> calificaciones;
	private FormaConclusion formaConclusion;
	private Date finProceso;
	private Recurrencia recurrencia;
	private Abogado abogado;
	private Estudio estudio;
	private AbogadoDataModel abogadoDataModel;
	private List<TipoHonorario> tipoHonorarios;
	private Honorario honorario;
	private List<Cuota> cuotas;
	private List<Moneda> monedas;
	private List<SituacionHonorario> situacionHonorarios;
	private List<SituacionCuota> situacionCuotas;
	private Involucrado involucrado;
	private Persona persona;
	private List<RolInvolucrado> rolInvolucrados;
	private List<TipoInvolucrado> tipoInvolucrados;
	private InvolucradoDataModel involucradoDataModel;
	private Involucrado selectedInvolucrado;
	private List<Clase> clases;
	private List<TipoDocumento> tipoDocumentos;
	private PersonaDataModel personaDataModelBusq;
	private String descripcionCuantia;
	private Cuantia cuantia;
	private Cuantia selectedCuantia;
	private CuantiaDataModel cuantiaDataModel;
	private List<SituacionInculpado> situacionInculpados;
	private Inculpado inculpado;
	private Inculpado selectedInculpado;
	private List<Inculpado> inculpados;
	private int moneda;
	private double montoCautelar;
	private int tipoCautelar;
	private List<TipoCautelar> tipoCautelares;
	private String descripcionCautelar;
	private int contraCautela;
	private double importeCautelar;
	private int estadoCautelar;
	private List<EstadoCautelar> estadosCautelares;
	private String descripProvision;
	private String resumen;
	private Date fechaResumen;
	private String todoResumen;
	private int riesgo;
	private List<Riesgo> riesgos;

	private UploadedFile file;
	private Anexo anexo;
	private List<Anexo> anexos;
	private Anexo selectedAnexo;
	private String descripcionAnexo;

	private Organo organo;
	private OrganoDataModel organoDataModel;
	private Organo selectedOrgano;

	private List<Honorario> honorarios;
	private Honorario selectedHonorario;
	private Persona selectPersona;
	private List<ContraCautela> contraCautelas;
	private boolean tabAsigEstExt;
	private boolean tabCaucion;
	private boolean tabCuanMat;
	
	private List<Hito> hitos;

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

		getHonorarios().remove(selectedHonorario);

	}

	public void deleteAnexo() {

		getAnexos().remove(selectedAnexo);

	}

	public void deleteInvolucrado() {

		List<Involucrado> involucrados = (List<Involucrado>) getInvolucradoDataModel()
				.getWrappedData();
		involucrados.remove(getSelectedInvolucrado());

		involucradoDataModel = new InvolucradoDataModel(involucrados);
	}

	public void deleteCuantia() {

		List<Cuantia> cuantias = (List<Cuantia>) getCuantiaDataModel()
				.getWrappedData();
		cuantias.remove(getSelectedCuantia());

		cuantiaDataModel = new CuantiaDataModel(cuantias);
	}

	public void deleteInculpado() {

		inculpados.remove(getSelectedInculpado());

	}
	
	public void deleteActividadProcesal() {

		getActividadProcesales().remove(getSelectedActPro());

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

		for (Abogado abogado : abogadosBD) {

			if (abogado.getRegistroca().equals(getAbogado().getRegistroca())) {
				results.add(abogado);

			}

		}
		abogadoDataModel = new AbogadoDataModel(results);

		return null;
	}

	public void agregarHonorario(ActionEvent en) {

		for (TipoHonorario tipo : getTipoHonorarios()) {
			if (tipo.getIdTipoHonorario() == getHonorario().getTipoHonorario()
					.getIdTipoHonorario())
				honorario.setTipoHonorario(tipo);
		}
		for (Moneda moneda : getMonedas()) {
			if (moneda.getIdMoneda() == getHonorario().getMoneda()
					.getIdMoneda())
				honorario.setMoneda(moneda);
		}
		for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
			if (situacionHonorario.getIdSituacionHonorario() == getHonorario()
					.getSituacionHonorario().getIdSituacionHonorario())
				honorario.setSituacionHonorario(situacionHonorario);
		}

		double montoPagado = getHonorario().getMonto()
				- getHonorario().getMontoPagado();

		double importe = montoPagado / getHonorario().getCantidad().intValue();

		SituacionCuota situacionCuota = getSituacionCuotas().get(0);

		honorario.setCuotas(new ArrayList<Cuota>());

		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= getHonorario().getCantidad().intValue(); i++) {
			Cuota cuota = new Cuota();
			cuota.setNumero(i);
			cuota.setNroRecibo("000" + i);
			cuota.setImporte(importe);
			cal.add(Calendar.MONTH, 1);
			Date date = cal.getTime();
			cuota.setFechaPago(date);
			cuota.setSituacionCuota(situacionCuota);

			honorario.addCuota(cuota);

		}
		honorarios.add(honorario);

		honorario = new Honorario();
		honorario.setCantidad(0);
		honorario.setMonto(0.0);
		honorario.setMontoPagado(0.0);

	}

	public void agregarAnexo(ActionEvent en) {

		getAnexos().add(getAnexo());
		setAnexo(new Anexo());

	}
	
	public void agregarActividadProcesal(ActionEvent en) {

		getActividadProcesales().add(getActividadProcesal());
		setActividadProcesal(new ActividadProcesal());

	}
	
	public void agregarProvision(ActionEvent en) {

		getProvisiones().add(getProvision());
		setProvision(new Provision());
		

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
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Persona.class);
		try {
			personas = personaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		for (Persona pers : personas) {

			if ((pers.getCodCliente() == getPersona().getCodCliente())
					&& (pers.getNumeroDocumento().intValue() == getPersona()
							.getNumeroDocumento().intValue())) {
				personas.add(pers);
			}

		}
		personaDataModelBusq = new PersonaDataModel(personas);

		return null;

	}

	public void buscarOrganos(ActionEvent actionEvent) {

		List<Organo> sublistOrgano = new ArrayList<Organo>();

		List<Organo> organos = new ArrayList<Organo>();
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		List<Organo> organos = new ArrayList<Organo>();
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		int flag = 0;

		for (Organo orgn : organos) {
			if (orgn.getNombre().toUpperCase()
					.equalsIgnoreCase(getOrgano().getNombre().toUpperCase())) {
				flag = 1;
				break;

			}
		}

		if (getOrgano().getEntidad().getIdEntidad() != 0
				&& getOrgano().getNombre() != ""
				&& getDescripcionDistrito() != "") {

			if (flag == 0) {

				GenericDaoImpl<Organo, Integer> genericoDao = (GenericDaoImpl<Organo, Integer>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				Organo organo = new Organo();
				organo.setEntidad(getOrgano().getEntidad());
				organo.setNombre(getOrgano().getNombre());

				List<Territorio> territorios = new ArrayList<Territorio>();
				GenericDaoImpl<Territorio, Integer> territorioDao = (GenericDaoImpl<Territorio, Integer>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				filtro = Busqueda.forClass(Territorio.class);
				filtro.add(Expression.like("distrito", getDescripcionDistrito()
						.split(",")[0]));
				filtro.add(Expression.like("provincia",
						getDescripcionDistrito().split(",")[1]));
				filtro.add(Expression.like("departamento",
						getDescripcionDistrito().split(",")[2]));
				try {
					territorios = territorioDao.buscarDinamico(filtro);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				organo.setTerritorio(territorios.get(0));

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
								"Organo Existente", "Organo Existente"));

			}

		} else {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Ingresar Campos", "Ingresar Campos"));

		}

	}

	public String agregarCuantia(ActionEvent e) {

		for (Moneda m : getMonedas()) {
			if (m.getIdMoneda() == getCuantia().getMoneda().getIdMoneda()) {
				cuantia.setMoneda(m);
				break;
			}

		}

		for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
			if (situacionHonorario.getIdSituacionHonorario() == getHonorario()
					.getSituacionHonorario().getIdSituacionHonorario())
				honorario.setSituacionHonorario(situacionHonorario);
		}

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

	public String agregarInvolucrado(ActionEvent e) {

		for (TipoInvolucrado tipo : getTipoInvolucrados()) {
			if (tipo.getIdTipoInvolucrado() == getInvolucrado()
					.getTipoInvolucrado().getIdTipoInvolucrado()) {
				involucrado.setTipoInvolucrado(tipo);
				break;
			}
		}

		for (RolInvolucrado rol : getRolInvolucrados()) {
			if (rol.getIdRolInvolucrado() == getInvolucrado()
					.getRolInvolucrado().getIdRolInvolucrado()) {
				involucrado.setRolInvolucrado(rol);
				break;
			}
		}

		List<Involucrado> involucrados;
		if (involucradoDataModel == null) {
			involucrados = new ArrayList<Involucrado>();
		} else {
			involucrados = (List<Involucrado>) involucradoDataModel
					.getWrappedData();
		}

		involucrados.add(getInvolucrado());
		involucradoDataModel = new InvolucradoDataModel(involucrados);

		involucrado = new Involucrado();

		return null;

	}

	public String agregarInculpado(ActionEvent e) {

		for (Moneda moneda : getMonedas()) {
			if (moneda.getIdMoneda() == getInculpado().getMoneda()
					.getIdMoneda())
				inculpado.setMoneda(moneda);
		}

		for (SituacionInculpado situac : getSituacionInculpados()) {
			if (situac.getIdSituacionInculpado() == getInculpado()
					.getSituacionInculpado().getIdSituacionInculpado())
				inculpado.setSituacionInculpado(situac);
		}

		if (inculpados == null) {

			inculpados = new ArrayList<Inculpado>();
		}

		inculpados.add(getInculpado());

		inculpado = new Inculpado();

		return null;

	}

	public String agregarPersona(ActionEvent e) {

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

	//
	// public String addPersona(ActionEvent e) {
	//
	// if (getPersona().getCodCliente().equalsIgnoreCase("")) {
	//
	// return null;
	// }
	//
	// getPersona().setNombreCompleto(
	// getPersona().getRazonSocial() + " "
	// + getPersona().getApellidoPaterno() + " "
	// + getPersona().getApellidoMaterno());
	//
	// if (getPersona().getTipoDoc().equalsIgnoreCase("dni")) {
	// getPersona().setTipo("Natural");
	//
	// }
	//
	// if (getPersona().getTipoDoc().equalsIgnoreCase("ruc")) {
	// getPersona().setTipo("Juridico");
	//
	// }
	//
	// return null;
	//
	// }

	public void limpiarAnexo(ActionEvent e) {

		setAnexo(new Anexo());

	}

	public void limpiarCuantia(ActionEvent e) {

		setCuantia(new Cuantia());
	}
	
	public void limpiarActividadProcesal(ActionEvent e) {

		setActividadProcesal(new ActividadProcesal());

	}
	
	public void limpiarProvision(ActionEvent e) {

		setProvision(new Provision());

	}

	public void limpiar(ActionEvent e) {

		// inicializar();

		setNroExpeOficial("");
		setEstado(0);
		setProceso(0);
		setVia(0);
		setInstancia(0);
		setResponsable(new Usuario());
		setOficina(new Oficina());
		setTipo(0);
		setOrgano(new Organo());
		setSecretario("");
		setCalificacion(0);
		setRecurrencia(new Recurrencia());

		setHonorario(new Honorario());
		setHonorarios(new ArrayList<Honorario>());

		setInvolucrado(new Involucrado());
		setInvolucradoDataModel(new InvolucradoDataModel());

		setCuantia(new Cuantia());
		setCuantiaDataModel(new CuantiaDataModel());

		setInculpado(new Inculpado());
		setInculpados(new ArrayList<Inculpado>());

		setMoneda(0);
		setMontoCautelar(0.0);
		setTipoCautelar(0);
		setDescripcionCautelar("");
		setContraCautela(0);
		setImporteCautelar(0.0);
		setEstadoCautelar(0);

		setResumen("");
		setTodoResumen("");

		setRiesgo(0);

	}

	@SuppressWarnings("unchecked")
	public String guardar() {

		Expediente expediente = new Expediente();

		expediente.setNumeroExpediente(getNroExpeOficial());
		expediente.setFechaInicioProceso(getInicioProceso());
		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			EstadoExpediente estadoExpedientebd = estadoExpedienteDAO
					.buscarById(EstadoExpediente.class, estado);
			expediente.setEstadoExpediente(estadoExpedientebd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			Instancia instanciabd = instanciaDAO.buscarById(Instancia.class,
					instancia);
			expediente.setInstancia(instanciabd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		expediente.setUsuario(getResponsable());
		expediente.setOficina(getOficina());
		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			TipoExpediente tipoExpedientebd = tipoExpedienteDAO.buscarById(
					TipoExpediente.class, getTipo());
			expediente.setTipoExpediente(tipoExpedientebd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		expediente.setOrgano(getOrgano1());
		expediente.setSecretario(getSecretario());
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			Calificacion calificacionbd = calificacionDAO.buscarById(
					Calificacion.class, getCalificacion());
			expediente.setCalificacion(calificacionbd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		expediente.setRecurrencia(getRecurrencia());

		List<Honorario> honorarios = getHonorarios();
		expediente.setHonorarios(new ArrayList<Honorario>());
		for (Honorario honorario : honorarios)
			if (honorario != null)
				expediente.addHonorario(honorario);

		List<Involucrado> involucrados = (List<Involucrado>) getInvolucradoDataModel()
				.getWrappedData();
		expediente.setInvolucrados(new ArrayList<Involucrado>());
		for (Involucrado involucrado : involucrados)
			if (involucrado != null)
				expediente.addInvolucrado(involucrado);

		List<Cuantia> cuantias = (List<Cuantia>) getCuantiaDataModel()
				.getWrappedData();
		expediente.setCuantias(new ArrayList<Cuantia>());
		for (Cuantia cuantia : cuantias)
			if (cuantia != null)
				expediente.addCuantia(cuantia);

		List<Inculpado> inculpados = getInculpados();
		expediente.setInculpados(new ArrayList<Inculpado>());
		for (Inculpado inculpado : inculpados)
			if (inculpado != null)
				expediente.addInculpado(inculpado);

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			Moneda monedabd = monedaDAO.buscarById(Moneda.class, getMoneda());
			expediente.setMoneda(monedabd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		expediente.setMontoCautelar(getMontoCautelar());
		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			TipoCautelar tipoCautelarbd = tipoCautelarDAO.buscarById(
					TipoCautelar.class, getTipoCautelar());
			expediente.setTipoCautelar(tipoCautelarbd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		expediente.setDescripcionCautelar(getDescripcionCautelar());

		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			ContraCautela contraCautelabd = contraCautelaDAO.buscarById(
					ContraCautela.class, getContraCautela());
			expediente.setContraCautela(contraCautelabd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		expediente.setImporteCautelar(getImporteCautelar());

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			EstadoCautelar estadoCautelarbd = estadoCautelarDAO.buscarById(
					EstadoCautelar.class, getEstadoCautelar());
			expediente.setEstadoCautelar(estadoCautelarbd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		expediente.setFechaResumen(getFechaResumen());
		expediente.setTextoResumen(getResumen());
		// expediente.setAcumuladoResumen(getTodoResumen());

		List<Anexo> anexos = getAnexos();
		expediente.setAnexos(new ArrayList<Anexo>());
		for (Anexo anexo : anexos)
			if (anexo != null)
				expediente.addAnexo(anexo);

		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		try {
			Riesgo riesgobd = riesgoDAO.buscarById(Riesgo.class, getRiesgo());
			expediente.setRiesgo(riesgobd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			expedienteDAO.insertar(expediente);
			return "registroExpediente2";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("No registro", "No registro"));
			return null;
		}

	}

	public List<Recurrencia> completeRecurrencia(String query) {

		List<Recurrencia> recurrencias = new ArrayList<Recurrencia>();
		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Recurrencia.class);
		try {
			recurrencias = recurrenciaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Recurrencia> results = new ArrayList<Recurrencia>();

		for (Recurrencia rec : recurrencias) {
			if (rec.getNombre().toLowerCase().startsWith(query.toLowerCase())) {
				results.add(rec);
			}
		}

		return results;
	}
	
	public List<FormaConclusion> completeFormaConclusion(String query) {

		List<FormaConclusion> formaConclusions = new ArrayList<FormaConclusion>();
		GenericDao<FormaConclusion, Object> formaConclusionDAO = (GenericDao<FormaConclusion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(FormaConclusion.class);
		try {
			formaConclusions = formaConclusionDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<FormaConclusion> results = new ArrayList<FormaConclusion>();

		for (FormaConclusion formaConclusion : formaConclusions) {
			if (formaConclusion.getDescripcion().toLowerCase().startsWith(query.toLowerCase())) {
				results.add(formaConclusion);
			}
		}

		return results;
	}

	public List<Materia> completeMaterias(String query) {
		List<Materia> results = new ArrayList<Materia>();

		List<Materia> listMateriasBD = new ArrayList<Materia>();
		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Materia.class);
		try {
			listMateriasBD = materiaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Materia mat : listMateriasBD) {
			if (mat.getDescripcion().toLowerCase()
					.startsWith(query.toLowerCase())) {
				results.add(mat);
			}
		}

		return results;
	}

	public List<String> completeInculpado(String query) {
		List<String> results = new ArrayList<String>();

		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Persona.class);
		try {
			personas = personaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		List<String> listInculpadoBD = new ArrayList<String>();

		for (Persona per : personas) {
			listInculpadoBD.add(per.getNombreCompleto());
		}

		for (String incul : listInculpadoBD) {
			if (incul.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(incul.toUpperCase());
			}
		}

		return results;
	}

	public List<Persona> completePersona(String query) {
		List<Persona> results = new ArrayList<Persona>();

		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Persona.class);
		try {
			personas = personaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Persona pers : personas) {
			if (pers.getNombreCompleto().toLowerCase()
					.contains(query.toLowerCase())) {
				results.add(pers);
			}
		}

		return results;
	}

	public List<Oficina> completeOficina(String query) {

		List<Oficina> results = new ArrayList<Oficina>();

		List<Oficina> oficinas = new ArrayList<Oficina>();
		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Oficina.class);
		try {
			oficinas = oficinaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		List<Estudio> estudios = new ArrayList<Estudio>();
		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Estudio.class);
		try {
			estudios = estudioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> results = new ArrayList<String>();

		for (Estudio est : estudios) {
			if (est.getNombre().toUpperCase().contains(query.toUpperCase())) {
				results.add(est.getNombre().toUpperCase());
			}
		}

		return results;
	}

	public List<Abogado> completeAbogado(String query) {

		List<Abogado> abogados = new ArrayList<Abogado>();
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		try {
			abogados = abogadoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Abogado> results = new ArrayList<Abogado>();

		for (Abogado abog : abogados) {

			if (abog.getNombreCompleto().toUpperCase()
					.contains(query.toUpperCase())) {
				results.add(abog);
			}
		}

		return results;
	}

	public List<Organo> completeOrgano(String query) {
		List<Organo> results = new ArrayList<Organo>();

		List<Organo> organos = new ArrayList<Organo>();
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public List<String> completeDistrito(String query) {
		List<String> results = new ArrayList<String>();

		List<Territorio> territorios = new ArrayList<Territorio>();
		GenericDao<Territorio, Object> territorioDAO = (GenericDao<Territorio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Territorio.class);
		try {
			territorios = territorioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Territorio terr : territorios) {
			if (terr.getDistrito().toUpperCase()
					.startsWith(query.toUpperCase())) {
				// terr.setDescripcionDistrito(terr.getDistrito() + ", "
				// + terr.getProvincia() + ", " + terr.getDepartamento());
				results.add(terr.getDistrito() + "," + terr.getProvincia()
						+ "," + terr.getDepartamento());
			}
		}

		return results;
	}

	// autocompletes
	public List<Usuario> completeResponsable(String query) {
		List<Usuario> results = new ArrayList<Usuario>();

		List<Usuario> usuarios = new ArrayList<Usuario>();
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Usuario.class);
		try {
			usuarios = usuarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Usuario usuario : usuarios) {

			if (usuario.getNombreCompleto().toUpperCase()
					.contains(query.toUpperCase())) {
				results.add(usuario);
			}
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	private void inicializar() {


		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Proceso.class);
		try {
			procesos = procesoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		vias = new ArrayList<Via>();

		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Instancia.class);
		try {
			instancias = instanciaDao.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		inicioProceso = cal.getTime();
		fechaResumen = cal.getTime();

		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(EstadoExpediente.class);
		try {
			estados = estadosExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoExpediente.class);
		try {
			tipos = tipoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Entidad.class);
		try {
			entidades = entidadDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Calificacion.class);
		try {
			calificaciones = calificacionDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		honorario = new Honorario();
		honorario.setCantidad(0);
		honorario.setMonto(0.0);
		honorario.setMontoPagado(0.0);

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoHonorario.class);
		try {
			tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Moneda.class);
		try {
			monedas = monedaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionHonorario.class);
		try {
			situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<SituacionCuota, Object> situacionCuotasDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionCuota.class);
		try {
			situacionCuotas = situacionCuotasDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionInculpado.class);
		try {
			situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(RolInvolucrado.class);
		try {
			rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoInvolucrado.class);
		try {
			tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<Clase, Object> claseDAO = (GenericDao<Clase, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Clase.class);
		try {
			clases = claseDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<TipoDocumento, Object> tipoDocumentoDAO = (GenericDao<TipoDocumento, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoDocumento.class);
		try {
			tipoDocumentos = tipoDocumentoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoCautelar.class);
		try {
			tipoCautelares = tipoCautelarDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(ContraCautela.class);
		try {
			contraCautelas = contraCautelaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(EstadoCautelar.class);
		try {
			estadosCautelares = estadoCautelarDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionActProc.class);
		try {
			situacionActProcesales = situacionActProcDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Actividad.class);
		try {
			actividades = actividadDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Etapa.class);
		try {
			etapas = etapaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Riesgo.class);
		try {
			riesgos = riesgoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Editado",
				"Honorario Editado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Cancelado",
				"Honorario Cancelado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<Inculpado> getInculpados() {

		if (inculpados == null) {
			inculpados = new ArrayList<Inculpado>();
		}

		return inculpados;
	}

	public void setInculpados(List<Inculpado> inculpados) {
		this.inculpados = inculpados;
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

	public Oficina getOficina() {
		if (oficina == null) {
			oficina = new Oficina();
		}
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
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

	public String getSecretario() {
		return secretario;
	}

	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}

	public Date getFechaResumen() {
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

	public void setSituacionHonorarios(
			List<SituacionHonorario> situacionHonorarios) {
		this.situacionHonorarios = situacionHonorarios;
	}

	public Honorario getSelectedHonorario() {
		return selectedHonorario;
	}

	public void setSelectedHonorario(Honorario selectedHonorario) {
		this.selectedHonorario = selectedHonorario;
	}

	public List<Honorario> getHonorarios() {
		if (honorarios == null) {
			honorarios = new ArrayList<Honorario>();
		}
		return honorarios;
	}

	public void setHonorarios(List<Honorario> honorarios) {
		this.honorarios = honorarios;
	}

	public List<SituacionCuota> getSituacionCuotas() {
		return situacionCuotas;
	}

	public void setSituacionCuotas(List<SituacionCuota> situacionCuotas) {
		this.situacionCuotas = situacionCuotas;
	}

	public List<SituacionInculpado> getSituacionInculpados() {
		return situacionInculpados;
	}

	public void setSituacionInculpados(
			List<SituacionInculpado> situacionInculpados) {
		this.situacionInculpados = situacionInculpados;
	}

	public List<Cuota> getCuotas() {

		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public Involucrado getInvolucrado() {
		if (involucrado == null) {
			involucrado = new Involucrado();
		}
		return involucrado;
	}

	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}

	public List<RolInvolucrado> getRolInvolucrados() {
		return rolInvolucrados;
	}

	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
	}

	public InvolucradoDataModel getInvolucradoDataModel() {
		if (involucradoDataModel == null) {
			List<Involucrado> involucrados = new ArrayList<Involucrado>();
			involucradoDataModel = new InvolucradoDataModel(involucrados);
		}
		return involucradoDataModel;
	}

	public void setInvolucradoDataModel(
			InvolucradoDataModel involucradoDataModel) {
		this.involucradoDataModel = involucradoDataModel;
	}

	public Involucrado getSelectedInvolucrado() {
		return selectedInvolucrado;
	}

	public void setSelectedInvolucrado(Involucrado selectedInvolucrado) {
		this.selectedInvolucrado = selectedInvolucrado;
	}

	public List<TipoInvolucrado> getTipoInvolucrados() {
		return tipoInvolucrados;
	}

	public void setTipoInvolucrados(List<TipoInvolucrado> tipoInvolucrados) {
		this.tipoInvolucrados = tipoInvolucrados;
	}

	public List<Clase> getClases() {
		return clases;
	}

	public void setClases(List<Clase> clases) {
		this.clases = clases;
	}

	public List<TipoDocumento> getTipoDocumentos() {
		return tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	public List<Riesgo> getRiesgos() {
		return riesgos;
	}

	public void setRiesgos(List<Riesgo> riesgos) {
		this.riesgos = riesgos;
	}

	public List<EstadoCautelar> getEstadosCautelares() {
		return estadosCautelares;
	}

	public void setEstadosCautelares(List<EstadoCautelar> estadosCautelares) {
		this.estadosCautelares = estadosCautelares;
	}

	public String getDescripcionDistrito() {
		return descripcionDistrito;
	}

	public void setDescripcionDistrito(String descripcionDistrito) {
		this.descripcionDistrito = descripcionDistrito;
	}

	public int getVia() {
		return via;
	}

	public void setVia(int via) {
		this.via = via;
	}

	public int getInstancia() {
		return instancia;
	}

	public void setInstancia(int instancia) {
		this.instancia = instancia;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public Date getInicioProceso() {
		return inicioProceso;
	}

	public void setInicioProceso(Date inicioProceso) {
		this.inicioProceso = inicioProceso;
	}

	public int getMoneda() {
		return moneda;
	}

	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}

	public double getMontoCautelar() {
		return montoCautelar;
	}

	public void setMontoCautelar(double montoCautelar) {
		this.montoCautelar = montoCautelar;
	}

	public double getImporteCautelar() {
		return importeCautelar;
	}

	public void setImporteCautelar(double importeCautelar) {
		this.importeCautelar = importeCautelar;
	}

	public String getDescripcionCautelar() {
		return descripcionCautelar;
	}

	public void setDescripcionCautelar(String descripcionCautelar) {
		this.descripcionCautelar = descripcionCautelar;
	}

	public List<TipoCautelar> getTipoCautelares() {
		return tipoCautelares;
	}

	public void setTipoCautelares(List<TipoCautelar> tipoCautelares) {
		this.tipoCautelares = tipoCautelares;
	}

	public int getTipoCautelar() {
		return tipoCautelar;
	}

	public void setTipoCautelar(int tipoCautelar) {
		this.tipoCautelar = tipoCautelar;
	}

	public List<ContraCautela> getContraCautelas() {
		return contraCautelas;
	}

	public void setContraCautelas(List<ContraCautela> contraCautelas) {
		this.contraCautelas = contraCautelas;
	}

	public int getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(int riesgo) {
		this.riesgo = riesgo;
	}

	public int getContraCautela() {
		return contraCautela;
	}

	public void setContraCautela(int contraCautela) {
		this.contraCautela = contraCautela;
	}

	public Estudio getEstudio() {
		if (estudio == null)
			estudio = new Estudio();
		return estudio;
	}

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public int getEstadoCautelar() {
		return estadoCautelar;
	}

	public void setEstadoCautelar(int estadoCautelar) {
		this.estadoCautelar = estadoCautelar;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Anexo getAnexo() {
		if (anexo == null) {
			anexo = new Anexo();
		}
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public List<Anexo> getAnexos() {
		if (anexos == null) {
			anexos = new ArrayList<Anexo>();
		}
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public Anexo getSelectedAnexo() {
		return selectedAnexo;
	}

	public void setSelectedAnexo(Anexo selectedAnexo) {
		this.selectedAnexo = selectedAnexo;
	}

	public String getDescripcionCuantia() {
		return descripcionCuantia;
	}

	public void setDescripcionCuantia(String descripcionCuantia) {
		this.descripcionCuantia = descripcionCuantia;
	}

	public String getDescripcionAnexo() {
		return descripcionAnexo;
	}

	public void setDescripcionAnexo(String descripcionAnexo) {
		this.descripcionAnexo = descripcionAnexo;
	}

	public ActSeguimientoExpedienteMB() {
		
		inicializar();
		
		logger.debug("Inicializando Actualizar Expediente");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession sessionhttp = (HttpSession) context.getSession(true);
		setSelectedExpediente((Expediente) sessionhttp.getAttribute("selectedExpediente"));
		
		setNroExpeOficial(getSelectedExpediente().getNumeroExpediente());
		setInicioProceso(getSelectedExpediente().getFechaInicioProceso());
		setEstado(getSelectedExpediente().getEstadoExpediente().getIdEstadoExpediente());
		setProceso(getSelectedExpediente().getInstancia().getVia().getProceso().getIdProceso());
		setVia(getSelectedExpediente().getInstancia().getVia().getIdVia());
		setInstancia(getSelectedExpediente().getInstancia().getIdInstancia());
		setResponsable(getSelectedExpediente().getUsuario());
		setOficina(getSelectedExpediente().getOficina());
		setTipo(getSelectedExpediente().getTipoExpediente().getIdTipoExpediente());
		setOrgano1(getSelectedExpediente().getOrgano());
		setSecretario(getSelectedExpediente().getSecretario());
		setCalificacion(getSelectedExpediente().getCalificacion().getIdCalificacion());
		setRecurrencia(getSelectedExpediente().getRecurrencia());
		setHonorarios(getSelectedExpediente().getHonorarios());
		
		List<Involucrado> involucrados= new ArrayList<Involucrado>();
		involucrados=getSelectedExpediente().getInvolucrados();
		involucradoDataModel = new InvolucradoDataModel(involucrados);
		List<Cuantia> cuantias= new ArrayList<Cuantia>();
		cuantias = getSelectedExpediente().getCuantias();
		cuantiaDataModel = new CuantiaDataModel(cuantias);
		
		if(getSelectedExpediente().getInstancia().getVia().getProceso().getIdProceso() == 2)
			setTabCaucion(true);
		
		setInculpados(getSelectedExpediente().getInculpados());
		
		setMoneda(getSelectedExpediente().getMoneda().getIdMoneda());
		setMontoCautelar(getSelectedExpediente().getMontoCautelar());
		setTipoCautelar(getSelectedExpediente().getTipoCautelar().getIdTipoCautelar());
		setDescripcionCautelar(getSelectedExpediente().getDescripcionCautelar());
		setContraCautela(getSelectedExpediente().getContraCautela().getIdContraCautela());
		setImporteCautelar(getSelectedExpediente().getImporteCautelar());
		setEstadoCautelar(getSelectedExpediente().getEstadoCautelar().getIdEstadoCautelar());
		setFechaResumen(getSelectedExpediente().getFechaResumen());
		setResumen(getSelectedExpediente().getTextoResumen());
		//setTodoResumen(getSelectedExpediente().get)
		setAnexos(getSelectedExpediente().getAnexos());
		setRiesgo(getSelectedExpediente().getRiesgo().getIdRiesgo());
		
		setHitos(getSelectedExpediente().getHitos());
		
		setActividadProcesal(new ActividadProcesal(new Etapa(),new SituacionActProc(), new Actividad()));
		setProvision(new Provision(new Moneda(),new TipoProvision()));
		
		
	}

	public Expediente getSelectedExpediente() {
		return selectedExpediente;
	}

	public void setSelectedExpediente(Expediente selectedExpediente) {
		this.selectedExpediente = selectedExpediente;
	}

	public String getDescripNotif() {
		return descripNotif;
	}

	public void setDescripNotif(String descripNotif) {
		this.descripNotif = descripNotif;
	}



	public FormaConclusion getFormaConclusion() {
		return formaConclusion;
	}

	public void setFormaConclusion(FormaConclusion formaConclusion) {
		this.formaConclusion = formaConclusion;
	}

	public Date getFinProceso() {
		return finProceso;
	}

	public void setFinProceso(Date finProceso) {
		this.finProceso = finProceso;
	}

	public List<Hito> getHitos() {
		return hitos;
	}

	public void setHitos(List<Hito> hitos) {
		this.hitos = hitos;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}


	public List<Etapa> getEtapas() {
		return etapas;
	}

	public void setEtapas(List<Etapa> etapas) {
		this.etapas = etapas;
	}

	public List<SituacionActProc> getSituacionActProcesales() {
		return situacionActProcesales;
	}

	public void setSituacionActProcesales(
			List<SituacionActProc> situacionActProcesales) {
		this.situacionActProcesales = situacionActProcesales;
	}

	public List<ActividadProcesal> getActividadProcesales() {
		return actividadProcesales;
	}

	public void setActividadProcesales(List<ActividadProcesal> actividadProcesales) {
		this.actividadProcesales = actividadProcesales;
	}

	public ActividadProcesal getSelectedActPro() {
		return selectedActPro;
	}

	public void setSelectedActPro(ActividadProcesal selectedActPro) {
		this.selectedActPro = selectedActPro;
	}

	public ActividadProcesal getActividadProcesal() {
		return actividadProcesal;
	}

	public void setActividadProcesal(ActividadProcesal actividadProcesal) {
		this.actividadProcesal = actividadProcesal;
	}

	public String getDescripProvision() {
		return descripProvision;
	}

	public void setDescripProvision(String descripProvision) {
		this.descripProvision = descripProvision;
	}

	public Provision getProvision() {
		return provision;
	}

	public void setProvision(Provision provision) {
		this.provision = provision;
	}

	public List<TipoProvision> getTipoProvisiones() {
		return tipoProvisiones;
	}

	public void setTipoProvisiones(List<TipoProvision> tipoProvisiones) {
		this.tipoProvisiones = tipoProvisiones;
	}

	public List<Provision> getProvisiones() {
		return provisiones;
	}

	public void setProvisiones(List<Provision> provisiones) {
		this.provisiones = provisiones;
	}

	
	

}
