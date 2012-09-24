package com.hildebrando.legal.mb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;
import com.hildebrando.legal.modelo.*;
import com.hildebrando.legal.service.RegistroExpedienteService;
import com.hildebrando.legal.view.*;

@ManagedBean(name = "registExpe")
@SessionScoped
public class RegistroExpedienteMB {

	public static Logger logger = Logger.getLogger(RegistroExpedienteMB.class);

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
	private Recurrencia recurrencia;
	private Abogado abogado;
	private Estudio estudio;
	private AbogadoDataModel abogadoDataModel;
	private List<TipoHonorario> tipoHonorarios;
	private List<String> tipoHonorariosString;
	private Honorario honorario;
	private List<Cuota> cuotas;
	private List<Moneda> monedas;
	private List<String> monedasString;
	private List<SituacionHonorario> situacionHonorarios;
	private List<String> situacionHonorariosString;
	private List<SituacionCuota> situacionCuotas;
	private List<String> situacionCuotasString;
	private Involucrado involucrado;
	private Persona persona;
	private List<RolInvolucrado> rolInvolucrados;
	private List<String> rolInvolucradosString;
	private List<TipoInvolucrado> tipoInvolucrados;
	private List<String> tipoInvolucradosString;
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
	private List<String> situacionInculpadosString;
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
			if (tipo.getDescripcion() == getHonorario().getTipoHonorario()
					.getDescripcion()) {
				honorario.setTipoHonorario(tipo);
				break;
			}
		}
		for (Moneda moneda : getMonedas()) {
			if (moneda.getSimbolo() == getHonorario().getMoneda().getSimbolo()) {
				honorario.setMoneda(moneda);
				break;
			}

		}
		for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
			if (situacionHonorario.getDescripcion() == getHonorario()
					.getSituacionHonorario().getDescripcion()) {
				honorario.setSituacionHonorario(situacionHonorario);
				break;
			}

		}

		double importe = getHonorario().getMonto()
				/ getHonorario().getCantidad().intValue();

		// situacion de cuotas pendiente

		SituacionCuota situacionCuota = getSituacionCuotas().get(0);

		honorario.setMontoPagado(0.0);
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
			cuota.setSituacionCuota(new SituacionCuota());
			cuota.getSituacionCuota().setIdSituacionCuota(
					situacionCuota.getIdSituacionCuota());
			cuota.getSituacionCuota().setDescripcion(
					situacionCuota.getDescripcion());
			honorario.addCuota(cuota);

		}
		honorarios.add(honorario);

		honorario = new Honorario();
		honorario.setCantidad(0);
		honorario.setMonto(0.0);
	}

	public void agregarAnexo(ActionEvent en) {

		if (getAnexos() == null) {
			setAnexos(new ArrayList<Anexo>());
		}

		getAnexos().add(getAnexo());
		setAnexo(new Anexo());

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

		organos = expedienteService.buscarOrganos(getOrgano());

		if (organo.getEntidad().getIdEntidad() == 0 && organo.getNombre() == ""
				&& organo.getTerritorio().getIdTerritorio() == 0) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Ingresar un campo", "Ingresar un campo");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} else {

			if (organo.getEntidad().getIdEntidad() != 0
					&& organo.getNombre() == ""
					&& organo.getTerritorio().getIdTerritorio() == 0) {

				for (Organo orgn : organos) {

					if (orgn.getEntidad().getIdEntidad() == organo.getEntidad()
							.getIdEntidad()) {
						sublistOrgano.add(orgn);
					}
				}

			} else {
				if (organo.getEntidad().getIdEntidad() == 0
						&& organo.getNombre() != ""
						&& organo.getTerritorio().getIdTerritorio() == 0) {

					for (Organo orgn : organos) {

						if (orgn.getNombre().equalsIgnoreCase(
								organo.getNombre())) {
							sublistOrgano.add(orgn);
						}
					}

				} else {

					if (organo.getEntidad().getIdEntidad() == 0
							&& organo.getNombre() == ""
							&& organo.getTerritorio().getIdTerritorio() != 0) {

						for (Organo orgn : organos) {

							if (orgn.getTerritorio()
									.getDistrito()
									.equalsIgnoreCase(
											organo.getTerritorio()
													.getDistrito())) {
								sublistOrgano.add(orgn);
							}
						}

					} else {

						if (organo.getEntidad().getIdEntidad() != 0
								&& organo.getNombre() != ""
								&& organo.getTerritorio().getIdTerritorio() == 0) {

							for (Organo orgn : organos) {

								if (orgn.getEntidad().getIdEntidad() == organo
										.getEntidad().getIdEntidad()
										&& orgn.getNombre().equalsIgnoreCase(
												organo.getNombre())) {
									sublistOrgano.add(orgn);
								}
							}

						} else {
							if (organo.getEntidad().getIdEntidad() != 0
									&& organo.getNombre() == ""
									&& organo.getTerritorio().getIdTerritorio() != 0) {
								for (Organo orgn : organos) {

									if (orgn.getEntidad().getIdEntidad() == organo
											.getEntidad().getIdEntidad()
											&& orgn.getTerritorio()
													.getDistrito()
													.equalsIgnoreCase(
															organo.getTerritorio()
																	.getDistrito())) {
										sublistOrgano.add(orgn);
									}
								}
							} else {
								if (organo.getEntidad().getIdEntidad() == 0
										&& organo.getNombre() != ""
										&& organo.getTerritorio()
												.getIdTerritorio() != 0) {
									for (Organo orgn : organos) {

										if (orgn.getNombre().equalsIgnoreCase(
												organo.getNombre())
												&& orgn.getTerritorio()
														.getDistrito()
														.equalsIgnoreCase(
																organo.getTerritorio()
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
		List<Territorio> territorios = new ArrayList<Territorio>();

		organos = expedienteService.buscarOrganos(getOrgano());

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

				Organo organo = new Organo();
				organo.setEntidad(getOrgano().getEntidad());
				organo.setNombre(getOrgano().getNombre());

				territorios = expedienteService
						.buscarTerritorios(getDescripcionDistrito());

				organo.setTerritorio(territorios.get(0));

				expedienteService.insertarOrgano(organo);

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
			if (m.getSimbolo().equals(getCuantia().getMoneda().getSimbolo())) {
				cuantia.setMoneda(m);
				break;
			}

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

	public String agregarInvolucrado(ActionEvent e) {

		for (RolInvolucrado rol : getRolInvolucrados()) {
			if (rol.getNombre() == getInvolucrado().getRolInvolucrado()
					.getNombre()) {
				involucrado.setRolInvolucrado(rol);
				break;
			}
		}

		for (TipoInvolucrado tipo : getTipoInvolucrados()) {
			if (tipo.getNombre() == getInvolucrado().getTipoInvolucrado()
					.getNombre()) {
				involucrado.setTipoInvolucrado(tipo);
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
			if (moneda.getSimbolo().equals(
					getInculpado().getMoneda().getSimbolo()))
				inculpado.setMoneda(moneda);
		}

		for (SituacionInculpado situac : getSituacionInculpados()) {
			if (situac.getNombre().equals(
					getInculpado().getSituacionInculpado().getNombre()))
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

	public void limpiarAnexo(ActionEvent e) {

		setAnexo(new Anexo());

	}

	public void limpiarCuantia(ActionEvent e) {

		setCuantia(new Cuantia());
	}

	public void limpiar(ActionEvent e) {

		logger.debug("limpiando los valores de la pantalla principal del expediente");

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

		// expedienteService.registrar(expediente);

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		EstadoExpediente estadoExpedientebd = new EstadoExpediente();
		Instancia instanciabd = new Instancia();
		TipoExpediente tipoExpedientebd = new TipoExpediente();
		Calificacion calificacionbd = new Calificacion();
		try {
			estadoExpedientebd = estadoExpedienteDAO.buscarById(
					EstadoExpediente.class, getEstado());
			instanciabd = instanciaDAO.buscarById(Instancia.class,
					getInstancia());
			tipoExpedientebd = tipoExpedienteDAO.buscarById(
					TipoExpediente.class, getTipo());
			calificacionbd = calificacionDAO.buscarById(Calificacion.class,
					getCalificacion());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		expediente.setNumeroExpediente(getNroExpeOficial());
		expediente.setFechaInicioProceso(getInicioProceso());
		expediente.setEstadoExpediente(estadoExpedientebd);
		expediente.setInstancia(instanciabd);
		expediente.setUsuario(getResponsable());
		expediente.setOficina(getOficina());
		expediente.setTipoExpediente(tipoExpedientebd);
		expediente.setOrgano(getOrgano1());
		expediente.setSecretario(getSecretario());
		expediente.setCalificacion(calificacionbd);
		expediente.setRecurrencia(getRecurrencia());

		List<Honorario> honorarios = getHonorarios();
		expediente.setHonorarios(new ArrayList<Honorario>());
		for (Honorario honorario : honorarios) {
			if (honorario != null) {
				for (TipoHonorario tipo : getTipoHonorarios()) {
					if (honorario.getTipoHonorario().getDescripcion()
							.equals(tipo.getDescripcion())) {
						honorario.setTipoHonorario(tipo);
						break;
					}
				}

				for (Moneda moneda : getMonedas()) {
					if (honorario.getMoneda().getSimbolo()
							.equals(moneda.getSimbolo())) {
						honorario.setMoneda(moneda);
						break;
					}

				}

				for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
					if (honorario.getSituacionHonorario().getDescripcion()
							.equals(situacionHonorario.getDescripcion())) {
						honorario.setSituacionHonorario(situacionHonorario);
						break;
					}

				}

				expediente.addHonorario(honorario);
			}

		}

		List<Involucrado> involucrados = (List<Involucrado>) getInvolucradoDataModel()
				.getWrappedData();
		expediente.setInvolucrados(new ArrayList<Involucrado>());
		for (Involucrado involucrado : involucrados) {

			if (involucrado != null) {

				for (RolInvolucrado rol : getRolInvolucrados()) {
					if (rol.getNombre().equals(involucrado.getRolInvolucrado().getNombre())) {
						involucrado.setRolInvolucrado(rol);
						break;
					}
				}

				for (TipoInvolucrado tipo : getTipoInvolucrados()) {
					if (tipo.getNombre().equals(involucrado.getTipoInvolucrado().getNombre())) {
						involucrado.setTipoInvolucrado(tipo);
						break;
					}
				}

				expediente.addInvolucrado(involucrado);
			}
		}

		List<Cuantia> cuantias = (List<Cuantia>) getCuantiaDataModel()
				.getWrappedData();
		expediente.setCuantias(new ArrayList<Cuantia>());
		for (Cuantia cuantia : cuantias) {
			if (cuantia != null) {

				for (Moneda m : getMonedas()) {
					if (m.getSimbolo().equals(cuantia.getMoneda().getSimbolo())) {
						cuantia.setMoneda(m);
						break;
					}

				}

				expediente.addCuantia(cuantia);
			}
		}

		List<Inculpado> inculpados = getInculpados();
		expediente.setInculpados(new ArrayList<Inculpado>());
		for (Inculpado inculpado : inculpados) {
			if (inculpado != null) {

				for (Moneda moneda : getMonedas()) {
					if (moneda.getSimbolo().equals(inculpado.getMoneda().getSimbolo())){
						inculpado.setMoneda(moneda);
						break;
					}
						
				}

				for (SituacionInculpado s : getSituacionInculpados()) {
					if (s.getNombre().equals(inculpado.getSituacionInculpado().getNombre())){
						inculpado.setSituacionInculpado(s);
						break;
					}
						
				}

				expediente.addInculpado(inculpado);
			}
		}

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Moneda monedabd = new Moneda();
		TipoCautelar tipoCautelarbd = new TipoCautelar();
		ContraCautela contraCautelabd = new ContraCautela();
		EstadoCautelar estadoCautelarbd = new EstadoCautelar();

		try {
			monedabd = monedaDAO.buscarById(Moneda.class, getMoneda());
			tipoCautelarbd = tipoCautelarDAO.buscarById(TipoCautelar.class,
					getTipoCautelar());
			contraCautelabd = contraCautelaDAO.buscarById(ContraCautela.class,
					getContraCautela());
			estadoCautelarbd = estadoCautelarDAO.buscarById(
					EstadoCautelar.class, getEstadoCautelar());
		} catch (Exception e) {

		}

		expediente.setMoneda(monedabd);
		expediente.setMontoCautelar(getMontoCautelar());
		expediente.setTipoCautelar(tipoCautelarbd);
		expediente.setDescripcionCautelar(getDescripcionCautelar());
		expediente.setContraCautela(contraCautelabd);
		expediente.setImporteCautelar(getImporteCautelar());
		expediente.setEstadoCautelar(estadoCautelarbd);
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
		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Actividad.class);

		Riesgo riesgobd = new Riesgo();
		List<Actividad> actividades = new ArrayList<Actividad>();
		SituacionActProc situacionActProc = new SituacionActProc();
		Proceso procesobd = new Proceso();
		Etapa etapabd= new Etapa();

		try {
			riesgobd = riesgoDAO.buscarById(Riesgo.class, getRiesgo());
			actividades = actividadDAO.buscarDinamico(filtro);
			situacionActProc = situacionActProcDAO.buscarById(SituacionActProc.class, 1);
			procesobd = procesoDAO.buscarById(Proceso.class, getProceso());
			etapabd = etapaDAO.buscarById(Etapa.class,1);
			
		} catch (Exception e) {

		}

		expediente.setRiesgo(riesgobd);
		expediente.setActividadProcesals(new ArrayList<ActividadProcesal>());

		// si es un proceso civil
		if (procesobd.getIdProceso() == 1) {

			if (actividades != null) {

				for (Actividad actividad : actividades) {

					ActividadProcesal actividadProcesal = new ActividadProcesal();
					actividadProcesal.setSituacionActProc(situacionActProc);
					actividadProcesal.setEtapa(etapabd);
					actividadProcesal.setFechaActividad(getInicioProceso());
					
					Calendar calendar = Calendar.getInstance();  
					calendar.setTime(getInicioProceso());  
					
					
					if (actividad.getIdActividad() == 1) {
						
						actividadProcesal.setActividad(actividad);
						actividadProcesal.setPlazoLey("5");
			
						calendar.add(Calendar.DAY_OF_MONTH, 5);

						actividadProcesal.setFechaVencimiento(calendar.getTime());
						expediente.addActividadProcesal(actividadProcesal);
					}
					if (actividad.getIdActividad() == 2) {

						actividadProcesal.setActividad(actividad);		
						actividadProcesal.setPlazoLey("9");
						
						calendar.add(Calendar.DAY_OF_MONTH, 9);
						
						actividadProcesal.setFechaVencimiento(calendar.getTime());
						expediente.addActividadProcesal(actividadProcesal);
					}
					if ( actividad.getIdActividad() == 4) {

						actividadProcesal.setActividad(actividad);
						actividadProcesal.setPlazoLey("7");
						
						calendar.add(Calendar.DAY_OF_MONTH, 7);
						
						actividadProcesal.setFechaVencimiento(calendar.getTime());
						expediente.addActividadProcesal(actividadProcesal);
					}

				}
			}

		}

		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			expedienteDAO.insertar(expediente);
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
			logger.debug("Registro el expediente exitosamente!");
			return "consultaExpediente.xhtml?faces-redirect=true";

		} catch (Exception e) {

			e.printStackTrace();
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("No registro",
									"No registro el expediente"));
			logger.debug("No registro el expediente!");

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
			if (rec.getNombre().toUpperCase().startsWith(query.toUpperCase())) {
				results.add(rec);
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
			if (pers.getNombres().toUpperCase().startsWith(query.toUpperCase())
					|| pers.getApellidoPaterno().toUpperCase()
							.startsWith(query.toUpperCase())
					|| pers.getApellidoMaterno().toUpperCase()
							.startsWith(query.toUpperCase())) {

				pers.setNombreCompletoMayuscula(pers.getNombres().toUpperCase()
						+ " " + pers.getApellidoPaterno().toUpperCase() + " "
						+ pers.getApellidoMaterno().toUpperCase());

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

			String texto = oficina.getCodigo() + " "
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

			if (abog.getNombres().toUpperCase().startsWith(query.toUpperCase())
					|| abog.getApellidoPaterno().toUpperCase()
							.startsWith(query.toUpperCase())
					|| abog.getApellidoMaterno().toUpperCase()
							.startsWith(query.toUpperCase())) {

				abog.setNombreCompletoMayuscula(abog.getNombres().toUpperCase()
						+ " " + abog.getApellidoPaterno().toUpperCase() + " "
						+ abog.getApellidoMaterno().toUpperCase());

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
			String descripcion = organo.getNombre().toUpperCase() + " ("
					+ organo.getTerritorio().getDistrito().toUpperCase() + ", "
					+ organo.getTerritorio().getProvincia().toUpperCase()
					+ ", "
					+ organo.getTerritorio().getDepartamento().toUpperCase()
					+ ")";

			if (descripcion.toUpperCase().contains(query.toUpperCase())) {

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
			String texto = terr.getDistrito() + ", " + terr.getProvincia()
					+ ", " + terr.getDepartamento();

			if (texto.toUpperCase().contains(query.toUpperCase())) {

				results.add(texto);
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

			if (usuario.getNombres().toUpperCase()
					.startsWith(query.toUpperCase())
					|| usuario.getApellidoPaterno().toUpperCase()
							.startsWith(query.toUpperCase())
					|| usuario.getApellidoMaterno().toUpperCase()
							.startsWith(query.toUpperCase())) {

				usuario.setNombreCompletoMayuscula(usuario.getNombres()
						.toUpperCase()
						+ " "
						+ usuario.getApellidoPaterno().toUpperCase()
						+ " "
						+ usuario.getApellidoMaterno().toUpperCase());

				results.add(usuario);
			}
		}

		return results;
	}

	// listener cada vez que se modifica el proceso
	public void cambioProceso() {

		setTabAsigEstExt(false);
		setTabCuanMat(false);
		setTabCaucion(false);

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

			instancias = new ArrayList<Instancia>();

		} else {
			vias = new ArrayList<Via>();

		}

	}

	// listener cada vez que se modifica la via
	public void cambioVia() {

		if (getVia() != 0) {

			GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Instancia.class);
			filtro.add(Expression.like("via.idVia", getVia()));

			try {
				instancias = instanciaDao.buscarDinamico(filtro);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			instancias = new ArrayList<Instancia>();

		}

	}

	public RegistroExpedienteMB() {
		logger.debug("Inicializando valores registro expediente");

		inicializarValores();

		cargarCombos();

	}

	@SuppressWarnings("unchecked")
	private void cargarCombos() {

		// SpringInit.openSession();
		logger.debug("Cargando combos para registro expediente");
		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstadoExpediente = Busqueda
				.forClass(EstadoExpediente.class);

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoExpediente = Busqueda.forClass(TipoExpediente.class);

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEntidad = Busqueda.forClass(Entidad.class);

		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroCalificacion = Busqueda.forClass(Calificacion.class);

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoHonorario = Busqueda.forClass(TipoHonorario.class);

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroMoneda = Busqueda.forClass(Moneda.class);

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionHonorario = Busqueda
				.forClass(SituacionHonorario.class);

		GenericDao<SituacionCuota, Object> situacionCuotasDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionCuota = Busqueda.forClass(SituacionCuota.class);

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionInculpado = Busqueda
				.forClass(SituacionInculpado.class);

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRolInvolucrado = Busqueda.forClass(RolInvolucrado.class);

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoInvolucrado = Busqueda
				.forClass(TipoInvolucrado.class);

		GenericDao<Clase, Object> claseDAO = (GenericDao<Clase, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroClase = Busqueda.forClass(Clase.class);

		GenericDao<TipoDocumento, Object> tipoDocumentoDAO = (GenericDao<TipoDocumento, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoDocumento = Busqueda.forClass(TipoDocumento.class);

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoCautelar = Busqueda.forClass(TipoCautelar.class);

		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroContraCautela = Busqueda.forClass(ContraCautela.class);

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstadoCautelar = Busqueda.forClass(EstadoCautelar.class);

		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRiesgo = Busqueda.forClass(Riesgo.class);

		try {
			estados = estadosExpedienteDAO
					.buscarDinamico(filtroEstadoExpediente);
			procesos = procesoDAO.buscarDinamico(filtroProceso);
			tipos = tipoExpedienteDAO.buscarDinamico(filtroTipoExpediente);
			entidades = entidadDAO.buscarDinamico(filtroEntidad);
			calificaciones = calificacionDAO.buscarDinamico(filtroCalificacion);
			tipoHonorarios = tipoHonorarioDAO
					.buscarDinamico(filtroTipoHonorario);
			monedas = monedaDAO.buscarDinamico(filtroMoneda);
			situacionHonorarios = situacionHonorarioDAO
					.buscarDinamico(filtroSituacionHonorario);
			situacionCuotas = situacionCuotasDAO
					.buscarDinamico(filtroSituacionCuota);
			situacionInculpados = situacionInculpadoDAO
					.buscarDinamico(filtroSituacionInculpado);
			rolInvolucrados = rolInvolucradoDAO
					.buscarDinamico(filtroRolInvolucrado);
			tipoInvolucrados = tipoInvolucradoDAO
					.buscarDinamico(filtroTipoInvolucrado);
			clases = claseDAO.buscarDinamico(filtroClase);
			tipoDocumentos = tipoDocumentoDAO
					.buscarDinamico(filtroTipoDocumento);
			tipoCautelares = tipoCautelarDAO.buscarDinamico(filtroTipoCautelar);
			contraCautelas = contraCautelaDAO
					.buscarDinamico(filtroContraCautela);
			estadosCautelares = estadoCautelarDAO
					.buscarDinamico(filtroEstadoCautelar);
			riesgos = riesgoDAO.buscarDinamico(filtroRiesgo);

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("error al cargar combos en registro de expediente");
		}

		vias = new ArrayList<Via>();
		instancias = new ArrayList<Instancia>();

		tipoHonorariosString = new ArrayList<String>();
		for (TipoHonorario t : tipoHonorarios)
			tipoHonorariosString.add(t.getDescripcion());

		monedasString = new ArrayList<String>();
		for (Moneda m : monedas)
			monedasString.add(m.getSimbolo());

		situacionHonorariosString = new ArrayList<String>();
		for (SituacionHonorario s : situacionHonorarios)
			situacionHonorariosString.add(s.getDescripcion());

		situacionCuotasString = new ArrayList<String>();
		for (SituacionCuota s : situacionCuotas)
			situacionCuotasString.add(s.getDescripcion());

		situacionInculpadosString = new ArrayList<String>();
		for (SituacionInculpado s : situacionInculpados)
			situacionInculpadosString.add(s.getNombre());

		rolInvolucradosString = new ArrayList<String>();
		for (RolInvolucrado r : rolInvolucrados)
			rolInvolucradosString.add(r.getNombre());

		tipoInvolucradosString = new ArrayList<String>();
		for (TipoInvolucrado t : tipoInvolucrados)
			tipoInvolucradosString.add(t.getNombre());

	}

	private void inicializarValores() {

		Calendar cal = Calendar.getInstance();
		inicioProceso = cal.getTime();
		fechaResumen = cal.getTime();

		selectedOrgano = new Organo();
		oficina = new Oficina();

		honorario = new Honorario();
		honorario.setCantidad(0);
		honorario.setMonto(0.0);
		setHonorarios(new ArrayList<Honorario>());

		involucrado = new Involucrado();
		involucradoDataModel = new InvolucradoDataModel(
				new ArrayList<Involucrado>());

		cuantia = new Cuantia();
		cuantia.setPretendido(0.0);
		cuantiaDataModel = new CuantiaDataModel(new ArrayList<Cuantia>());

		inculpado = new Inculpado();
		inculpado.setMonto(0.0);
		inculpado.setNrocupon(0000);
		inculpados = new ArrayList<Inculpado>();

		anexo = new Anexo();
		anexos = new ArrayList<Anexo>();

		organo = new Organo();
		organoDataModel = new OrganoDataModel(new ArrayList<Organo>());

		persona = new Persona();
		selectPersona = new Persona();

		abogado = new Abogado();
		estudio = new Estudio();
		abogadoDataModel = new AbogadoDataModel(new ArrayList<Abogado>());

		personaDataModelBusq = new PersonaDataModel(new ArrayList<Persona>());
	}

	public void editHonor(RowEditEvent event) {

		Honorario honorario = ((Honorario) event.getObject());

	}

	public void editDetHonor(RowEditEvent event) {

		Cuota cuota = ((Cuota) event.getObject());
		// 2 indica el estado pagado
		if (cuota.getSituacionCuota().getDescripcion().equals("Pagado")
				|| cuota.getSituacionCuota().getDescripcion().equals("Baja")) {

			for (Honorario honorario : honorarios) {
				if (cuota.getHonorario().getIdHonorario() == honorario
						.getIdHonorario()) {
					double importe = cuota.getImporte();
					honorario.setMonto(honorario.getMonto() - importe);
					honorario.setMontoPagado(honorario.getMontoPagado()
							+ importe);
				}
			}

		}

		FacesMessage msg = new FacesMessage("Honorario Editado",
				"Honorario Editado al pagar una cuota");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Cancelado",
				"Honorario Cancelado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<Inculpado> getInculpados() {
		return inculpados;
	}

	public void setInculpados(List<Inculpado> inculpados) {
		this.inculpados = inculpados;
	}

	public CuantiaDataModel getCuantiaDataModel() {
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
		return selectedOrgano;
	}

	public void setSelectedOrgano(Organo selectedOrgano) {
		this.selectedOrgano = selectedOrgano;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Persona getSelectPersona() {
		return selectPersona;
	}

	public void setSelectPersona(Persona selectPersona) {
		this.selectPersona = selectPersona;
	}

	public Inculpado getInculpado() {
		return inculpado;
	}

	public void setInculpado(Inculpado inculpado) {
		this.inculpado = inculpado;
	}

	public Abogado getAbogado() {
		return abogado;
	}

	public void setAbogado(Abogado abogado) {
		this.abogado = abogado;
	}

	public AbogadoDataModel getAbogadoDataModel() {
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
		return cuantia;
	}

	public void setCuantia(Cuantia cuantia) {
		this.cuantia = cuantia;
	}

	public Organo getOrgano() {
		return organo;
	}

	public void setOrgano(Organo organo) {
		this.organo = organo;
	}

	public OrganoDataModel getOrganoDataModel() {
		return organoDataModel;
	}

	public void setOrganoDataModel(OrganoDataModel organoDataModel) {
		this.organoDataModel = organoDataModel;
	}

	public PersonaDataModel getPersonaDataModelBusq() {
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

	public Honorario getSelectedHonorario() {
		return selectedHonorario;
	}

	public void setSelectedHonorario(Honorario selectedHonorario) {
		this.selectedHonorario = selectedHonorario;
	}

	public List<Honorario> getHonorarios() {
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
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public List<Anexo> getAnexos() {
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

	public List<SituacionHonorario> getSituacionHonorarios() {
		return situacionHonorarios;
	}

	public void setSituacionHonorarios(
			List<SituacionHonorario> situacionHonorarios) {
		this.situacionHonorarios = situacionHonorarios;
	}

	public List<String> getSituacionHonorariosString() {
		return situacionHonorariosString;
	}

	public void setSituacionHonorariosString(
			List<String> situacionHonorariosString) {
		this.situacionHonorariosString = situacionHonorariosString;
	}

	public List<String> getMonedasString() {
		return monedasString;
	}

	public void setMonedasString(List<String> monedasString) {
		this.monedasString = monedasString;
	}

	public List<String> getTipoHonorariosString() {
		return tipoHonorariosString;
	}

	public void setTipoHonorariosString(List<String> tipoHonorariosString) {
		this.tipoHonorariosString = tipoHonorariosString;
	}

	public List<String> getSituacionCuotasString() {
		return situacionCuotasString;
	}

	public void setSituacionCuotasString(List<String> situacionCuotasString) {
		this.situacionCuotasString = situacionCuotasString;
	}

	public List<String> getRolInvolucradosString() {
		return rolInvolucradosString;
	}

	public void setRolInvolucradosString(List<String> rolInvolucradosString) {
		this.rolInvolucradosString = rolInvolucradosString;
	}

	public List<String> getTipoInvolucradosString() {
		return tipoInvolucradosString;
	}

	public void setTipoInvolucradosString(List<String> tipoInvolucradosString) {
		this.tipoInvolucradosString = tipoInvolucradosString;
	}

	public List<String> getSituacionInculpadosString() {
		return situacionInculpadosString;
	}

	public void setSituacionInculpadosString(
			List<String> situacionInculpadosString) {
		this.situacionInculpadosString = situacionInculpadosString;
	}

}
