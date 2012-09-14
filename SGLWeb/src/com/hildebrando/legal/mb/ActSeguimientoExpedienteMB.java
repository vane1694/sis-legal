package com.hildebrando.legal.mb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import javax.swing.Spring;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Expression;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
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
import com.hildebrando.legal.modelo.ExpedienteVista;
import com.hildebrando.legal.modelo.FormaConclusion;
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
@SessionScoped
public class ActSeguimientoExpedienteMB {

	public static Logger logger = Logger.getLogger(ActSeguimientoExpedienteMB.class);

	private List<Proceso> procesos;
	private List<EstadoExpediente> estados;
	private List<TipoExpediente> tipos;
	private String descripcionDistrito;
	private Organo organo;
	private OrganoDataModel organoDataModel;
	private Organo selectedOrgano;
	private List<Entidad> entidades;
	private List<Calificacion> calificaciones;
	
	private Abogado abogado;
	private Estudio estudio;
	private AbogadoDataModel abogadoDataModel;
	
	private List<TipoHonorario> tipoHonorarios;
	private List<String> tipoHonorariosString;
	private List<Moneda> monedas;
	private List<String> monedasString;
	private List<SituacionHonorario> situacionHonorarios;
	private List<String> situacionHonorariosString;
	private List<SituacionCuota> situacionCuotas;
	private List<String> situacionCuotasString;
	private List<RolInvolucrado> rolInvolucrados;
	private List<String> rolInvolucradosString;
	private List<TipoInvolucrado> tipoInvolucrados;
	private List<String> tipoInvolucradosString;
	private List<Clase> clases;
	private List<TipoDocumento> tipoDocumentos;
	
	private Persona persona;
	private Persona selectPersona;
	private PersonaDataModel personaDataModelBusq;
	
	private List<SituacionInculpado> situacionInculpados;
	private List<String> situacionInculpadosString;
	private List<TipoCautelar> tipoCautelares;
	private List<EstadoCautelar> estadosCautelares;
	private List<Riesgo> riesgos;
	
	private List<TipoProvision> tipoProvisiones;
	
	private List<Actividad> actividades;
	private List<Etapa> etapas;
	private List<SituacionActProc> situacionActProcesales;

	private List<ContraCautela> contraCautelas;
	
	private boolean tabCaucion;
	
	private int tabActivado;
	private int posiExpeVista;
	
	private Date finInstancia;
	private int instanciaProxima;
	private FormaConclusion formaConclusion2;
	private List<Instancia> instanciasProximas;
	
	private Expediente expedienteOrig;
	private ExpedienteVista expedienteVista;
	private List<ExpedienteVista> expedienteVistas; 

	@ManagedProperty(value = "#{registroService}")
	private RegistroExpedienteService expedienteService;
	
	private int newInstancia;
	private Oficina newOficina;
	private Organo newOrgano1;
	private String newSecretario;
	private Recurrencia newRecurrencia;

	public void setExpedienteService(RegistroExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public void agregarTodoResumen(ActionEvent e) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		getExpedienteVista().setTodoResumen((getExpedienteVista().getResumen() + "\n" + format.format(getExpedienteVista().getFechaResumen())));

	}

	public void crearProximaInstancia(ActionEvent e) {
		
		setExpedienteVista(getExpedienteVistas().get(getPosiExpeVista()));
		
		Expediente expediente = getExpedienteOrig();
		actualizarExpediente(expediente, getExpedienteVista());
		Expediente expedienteAux = expediente;
		expedienteAux.setIdExpediente(0);
	
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		expediente.setFechaFinProceso(getFinInstancia());
		expediente.setFormaConclusion(getFormaConclusion2());
		
		try {
			expedienteDAO.modificar(expediente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			Instancia instanciaProximaBD = instanciaDAO.buscarById(Instancia.class,getInstanciaProxima());
			expedienteAux.setInstancia(instanciaProximaBD);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	
		List<ActividadProcesal> actividadProcesals = expedienteAux.getActividadProcesals();
		expedienteAux.setActividadProcesals(new ArrayList<ActividadProcesal>());
	
		// para obtener la ultima actividad procesal
		long mayor = 0;
		int posi = 0;
		for (int i = 0; i < actividadProcesals.size(); i++) {
			if (actividadProcesals.get(i).getIdActividadProcesal() > mayor) {
				mayor = actividadProcesals.get(i).getIdActividadProcesal();
				posi = i;
			}
		}
	
		expedienteAux.addActividadProcesal(actividadProcesals.get(posi));

		guardar(expedienteAux);
		
		llenarHitos();
		
//		try {
//			expedienteDAO.insertar(expedienteAux);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

	}
	
	@SuppressWarnings("unchecked")
	public void guardar(Expediente expedienteAux) {

		Expediente expediente = new Expediente();
		
		//expedienteService.registrar(expediente);
		
		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		EstadoExpediente estadoExpedientebd= new EstadoExpediente();
		Instancia instanciabd= new Instancia();
		TipoExpediente tipoExpedientebd= new TipoExpediente();
		Calificacion calificacionbd = new Calificacion();
		try {
			estadoExpedientebd = estadoExpedienteDAO.buscarById(EstadoExpediente.class, expedienteAux.getEstadoExpediente().getIdEstadoExpediente());
			instanciabd = instanciaDAO.buscarById(Instancia.class,expedienteAux.getInstancia().getIdInstancia());
			tipoExpedientebd = tipoExpedienteDAO.buscarById(TipoExpediente.class, expedienteAux.getTipoExpediente().getIdTipoExpediente());
			calificacionbd = calificacionDAO.buscarById(Calificacion.class,expedienteAux.getCalificacion().getIdCalificacion());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		expediente.setNumeroExpediente(expedienteAux.getNumeroExpediente());
		expediente.setFechaInicioProceso(expedienteAux.getFechaInicioProceso());
		expediente.setEstadoExpediente(estadoExpedientebd);
		expediente.setInstancia(instanciabd);
		expediente.setUsuario(expedienteAux.getUsuario());
		expediente.setOficina(expedienteAux.getOficina());
		expediente.setTipoExpediente(tipoExpedientebd);
		expediente.setOrgano(expedienteAux.getOrgano());
		expediente.setSecretario(expedienteAux.getSecretario());
		expediente.setCalificacion(calificacionbd);
		expediente.setRecurrencia(expedienteAux.getRecurrencia());

		
		List<Honorario> honorarios = expedienteAux.getHonorarios();
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

		List<Involucrado> involucrados = (List<Involucrado>) expedienteAux.getInvolucrados();
		expediente.setInvolucrados(new ArrayList<Involucrado>());
		for (Involucrado involucrado : involucrados) {

			if (involucrado != null) {

				for (RolInvolucrado rol : getRolInvolucrados()) {
					if (rol.getNombre().equals(
							involucrado.getRolInvolucrado().getNombre())) {
						involucrado.setRolInvolucrado(rol);
						break;
					}
				}

				for (TipoInvolucrado tipo : getTipoInvolucrados()) {
					if (tipo.getNombre().equals(
							involucrado.getTipoInvolucrado().getNombre())) {
						involucrado.setTipoInvolucrado(tipo);
						break;
					}
				}

				expediente.addInvolucrado(involucrado);
			}
		}

		List<Cuantia> cuantias = (List<Cuantia>) expedienteAux.getCuantias();
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

		List<Inculpado> inculpados = expedienteAux.getInculpados();
		expediente.setInculpados(new ArrayList<Inculpado>());
		for (Inculpado inculpado : inculpados) {
			if (inculpado != null) {

				for (Moneda moneda : getMonedas()) {
					if (moneda.getSimbolo().equals(
							inculpado.getMoneda().getSimbolo()))
						inculpado.setMoneda(moneda);
				}

				for (SituacionInculpado s : getSituacionInculpados()) {
					if (s.getNombre().equals(
							inculpado.getSituacionInculpado().getNombre()))
						inculpado.setSituacionInculpado(s);
				}

				expediente.addInculpado(inculpado);
			}
		}

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Moneda monedabd = new Moneda();
		TipoCautelar tipoCautelarbd= new TipoCautelar();
		ContraCautela contraCautelabd= new ContraCautela();
		EstadoCautelar estadoCautelarbd= new EstadoCautelar();
		
		try {
			monedabd = monedaDAO.buscarById(Moneda.class, expedienteAux.getMoneda().getIdMoneda());
			tipoCautelarbd = tipoCautelarDAO.buscarById(TipoCautelar.class, expedienteAux.getTipoCautelar().getIdTipoCautelar());
			contraCautelabd = contraCautelaDAO.buscarById(ContraCautela.class, expedienteAux.getContraCautela().getIdContraCautela());
			estadoCautelarbd = estadoCautelarDAO.buscarById(EstadoCautelar.class, expedienteAux.getEstadoCautelar().getIdEstadoCautelar());
		} catch (Exception e) {
			
		}

		expediente.setMoneda(monedabd);
		expediente.setMontoCautelar(expedienteAux.getMontoCautelar());
		expediente.setTipoCautelar(tipoCautelarbd);
		expediente.setDescripcionCautelar(expedienteAux.getDescripcionCautelar());
		expediente.setContraCautela(contraCautelabd);
		expediente.setImporteCautelar(expedienteAux.getImporteCautelar());
		expediente.setEstadoCautelar(estadoCautelarbd);
		expediente.setFechaResumen(expedienteAux.getFechaResumen());
		expediente.setTextoResumen(expedienteAux.getTextoResumen());
		// expediente.setAcumuladoResumen(getTodoResumen());

		List<Anexo> anexos = expedienteAux.getAnexos();
		expediente.setAnexos(new ArrayList<Anexo>());
		for (Anexo anexo : anexos)
			if (anexo != null)
				expediente.addAnexo(anexo);

		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Actividad.class);
		
		
		Riesgo riesgobd= new Riesgo();
		
		try {
			riesgobd = riesgoDAO.buscarById(Riesgo.class, expedienteAux.getRiesgo().getIdRiesgo());
		} catch (Exception e) {
			
		}
		
		expediente.setRiesgo(riesgobd);
		
		expediente.setActividadProcesals(new ArrayList<ActividadProcesal>());
		List<ActividadProcesal> actividadProcesals = expedienteAux.getActividadProcesals();
		expediente.setActividadProcesals(new ArrayList<ActividadProcesal>());
		for (ActividadProcesal actividadProcesal : actividadProcesals)
			if (actividadProcesal != null)
				expediente.addActividadProcesal(actividadProcesal);

		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			expedienteDAO.save(expediente);
			logger.debug("Registro el expediente exitosamente!");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("No registro","No registro el expediente"));
			logger.debug("No registro el expediente!");
		}

	}
	
	public void cambioInstancia(ValueChangeEvent e){
		
		setNewInstancia((Integer) e.getNewValue());

    }
	public void cambioOficina(ValueChangeEvent e){
		
		setNewOficina((Oficina) e.getNewValue());

    }
	public void cambioOrgano(ValueChangeEvent e){
		
		setNewOrgano1((Organo) e.getNewValue());

    }
	public void cambioSecretario(ValueChangeEvent e){
	
		setNewSecretario((String) e.getNewValue());

	}
	public void cambioRecurrencia(ValueChangeEvent e){
	
		setNewRecurrencia((Recurrencia) e.getNewValue());
	}

	public void finalizarProceso(ActionEvent e) {

		Expediente expediente = getExpedienteOrig();
		actualizarExpediente(expediente, getExpedienteVista());
		expediente.setFechaFinProceso(getFinInstancia());
		expediente.setFormaConclusion(getFormaConclusion2());

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			EstadoExpediente estadoExpedienteConcluido = estadoExpedienteDAO.buscarById(EstadoExpediente.class, 2);
			expediente.setEstadoExpediente(estadoExpedienteConcluido);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			expedienteDAO.modificar(expediente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void guardar(ActionEvent e){
		
		Expediente expediente = getExpedienteOrig();
		actualizarExpediente(expediente, getExpedienteVista());
		
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			expedienteDAO.modificar(expediente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteHonorario() {

		getExpedienteVista().getHonorarios().remove(getExpedienteVista().getSelectedHonorario());

	}

	public void deleteAnexo() {

		getExpedienteVista().getAnexos().remove(getExpedienteVista().getSelectedAnexo());

	}

	public void deleteInvolucrado() {

		List<Involucrado> involucrados = (List<Involucrado>) getExpedienteVista().getInvolucradoDataModel().getWrappedData();
		involucrados.remove(getExpedienteVista().getSelectedInvolucrado());

		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(involucrados);
		getExpedienteVista().setInvolucradoDataModel(involucradoDataModel);
	}

	public void deleteCuantia() {

		List<Cuantia> cuantias = (List<Cuantia>) getExpedienteVista().getCuantiaDataModel().getWrappedData();
		cuantias.remove(getExpedienteVista().getSelectedCuantia());

		CuantiaDataModel cuantiaDataModel = new CuantiaDataModel(cuantias);
		getExpedienteVista().setCuantiaDataModel(cuantiaDataModel);
	}

	public void deleteInculpado() {

		 getExpedienteVista().getInculpados().remove(getExpedienteVista().getSelectedInculpado());

	}

	public void deleteActividadProcesal() {

		getExpedienteVista().getActividadProcesales().remove(getExpedienteVista().getSelectedActPro());

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
			if (tipo.getDescripcion() == getExpedienteVista().getHonorario().getTipoHonorario().getDescripcion()){
				getExpedienteVista().getHonorario().setTipoHonorario(tipo);
				break;
			}
		}
		for (Moneda moneda : getMonedas()) {
			if (moneda.getSimbolo() == getExpedienteVista().getHonorario().getMoneda().getSimbolo()){
				getExpedienteVista().getHonorario().setMoneda(moneda);
				break;
			}
				
		}
		for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
			if (situacionHonorario.getDescripcion() == getExpedienteVista().getHonorario().getSituacionHonorario().getDescripcion()){
				getExpedienteVista().getHonorario().setSituacionHonorario(situacionHonorario);
				break;
			}
				
		}

		double importe = getExpedienteVista().getHonorario().getMonto() / getExpedienteVista().getHonorario().getCantidad().intValue();

		
		SituacionCuota situacionCuota = getSituacionCuotas().get(0);

		getExpedienteVista().getHonorario().setMontoPagado(0.0);
		getExpedienteVista().getHonorario().setCuotas(new ArrayList<Cuota>());

		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= getExpedienteVista().getHonorario().getCantidad().intValue(); i++) {
				Cuota cuota = new Cuota();
				cuota.setNumero(i);
				cuota.setNroRecibo("000" + i);
				cuota.setImporte(importe);
				cal.add(Calendar.MONTH, 1);
				Date date = cal.getTime();
				cuota.setFechaPago(date);
				cuota.setSituacionCuota(new SituacionCuota());
				cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
				cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
				getExpedienteVista().getHonorario().addCuota(cuota);

		}
		
		getExpedienteVista().getHonorarios().add(getExpedienteVista().getHonorario());

		getExpedienteVista().setHonorario(new Honorario());
		getExpedienteVista().getHonorario().setCantidad(0);
		getExpedienteVista().getHonorario().setMonto(0.0);
	}
	
	public void agregarAnexo(ActionEvent en) {

		if(getExpedienteVista().getAnexos() == null){
			getExpedienteVista().setAnexos(new ArrayList<Anexo>());
		}
		
		getExpedienteVista().getAnexos().add(getExpedienteVista().getAnexo());
		getExpedienteVista().setAnexo(new Anexo());

	}

	public void agregarActividadProcesal(ActionEvent en) {
		

		for (Actividad act : getActividades()) {
			if (act.getIdActividad() == getExpedienteVista().getActividadProcesal().getActividad().getIdActividad()){
				getExpedienteVista().getActividadProcesal().setActividad(act);
				break;
			}
		}
		for (Etapa et : getEtapas()) {
			if (et.getIdEtapa() == getExpedienteVista().getActividadProcesal().getEtapa().getIdEtapa()){
				getExpedienteVista().getActividadProcesal().setEtapa(et);
				break;
			}
				
		}
		for (SituacionActProc situacionActProc : getSituacionActProcesales()) {
			if (situacionActProc.getIdSituacionActProc() == getExpedienteVista().getActividadProcesal().getSituacionActProc().getIdSituacionActProc()){
				getExpedienteVista().getActividadProcesal().setSituacionActProc(situacionActProc);
				break;
			}
				
		}

		getExpedienteVista().getActividadProcesales().add(getExpedienteVista().getActividadProcesal());
		getExpedienteVista().setActividadProcesal(new ActividadProcesal(new Etapa(),
				new SituacionActProc(), new Actividad()));

	}

	public void agregarProvision(ActionEvent en) {

		getExpedienteVista().getProvisiones().add(getExpedienteVista().getProvision());
		getExpedienteVista().setProvision(new Provision());

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
			if (m.getSimbolo().equals(getExpedienteVista().getCuantia().getMoneda().getSimbolo())) {
				getExpedienteVista().getCuantia().setMoneda(m);
				break;
			}

		}

		List<Cuantia> cuantias;
		if (getExpedienteVista().getCuantiaDataModel() == null) {
			cuantias = new ArrayList<Cuantia>();
		} else {
			cuantias = (List<Cuantia>) getExpedienteVista().getCuantiaDataModel().getWrappedData();
		}

		cuantias.add(getExpedienteVista().getCuantia());

		CuantiaDataModel cuantiaDataModel = new CuantiaDataModel(cuantias);
		
		getExpedienteVista().setCuantiaDataModel(cuantiaDataModel);

		getExpedienteVista().setCuantia(new Cuantia());

		return null;
	}

	public String agregarInvolucrado(ActionEvent e) {

		for (RolInvolucrado rol : getRolInvolucrados()) {
			if (rol.getNombre() == getExpedienteVista().getInvolucrado().getRolInvolucrado().getNombre()) {
				getExpedienteVista().getInvolucrado().setRolInvolucrado(rol);
				break;
			}
		}
		
		for (TipoInvolucrado tipo : getTipoInvolucrados()) {
			if (tipo.getNombre() == getExpedienteVista().getInvolucrado().getTipoInvolucrado().getNombre()) {
				getExpedienteVista().getInvolucrado().setTipoInvolucrado(tipo);
				break;
			}
		}

	

		List<Involucrado> involucrados;
		if (getExpedienteVista().getInvolucradoDataModel() == null) {
			involucrados = new ArrayList<Involucrado>();
		} else {
			involucrados = (List<Involucrado>) getExpedienteVista().getInvolucradoDataModel().getWrappedData();
		}

		involucrados.add(getExpedienteVista().getInvolucrado());
		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(involucrados);
		getExpedienteVista().setInvolucradoDataModel(involucradoDataModel);

		getExpedienteVista().setInvolucrado(new Involucrado());
		
		return null;

	}

	public String agregarInculpado(ActionEvent e) {

		for (Moneda moneda : getMonedas()) {
			if (moneda.getSimbolo().equals(getExpedienteVista().getInculpado().getMoneda().getSimbolo()))
				getExpedienteVista().getInculpado().setMoneda(moneda);
		}

		for (SituacionInculpado situac : getSituacionInculpados()) {
			if (situac.getNombre().equals(getExpedienteVista().getInculpado().getSituacionInculpado().getNombre()))
				getExpedienteVista().getInculpado().setSituacionInculpado(situac);
		}

		if (getExpedienteVista().getInculpados() == null) {

			getExpedienteVista().setInculpados(new ArrayList<Inculpado>()) ;
		}

		getExpedienteVista().getInculpados().add(getExpedienteVista().getInculpado());

		getExpedienteVista().setInculpado(new Inculpado());

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

		getExpedienteVista().setAnexo(new Anexo());

	}

	public void limpiarCuantia(ActionEvent e) {

		getExpedienteVista().setCuantia(new Cuantia());
	}

	public void limpiarActividadProcesal(ActionEvent e) {

		getExpedienteVista().setActividadProcesal(new ActividadProcesal());

	}

	public void limpiarProvision(ActionEvent e) {

		getExpedienteVista().setProvision(new Provision());

	}

	public void limpiar(ActionEvent e) {

		// inicializar();

		getExpedienteVista().setNroExpeOficial("");
		getExpedienteVista().setEstado(0);
		getExpedienteVista().setProceso(0);
		getExpedienteVista().setVia(0);
		getExpedienteVista().setInstancia(0);
		getExpedienteVista().setResponsable(new Usuario());
		getExpedienteVista().setOficina(new Oficina());
		getExpedienteVista().setTipo(0);
		getExpedienteVista().setOrgano1(new Organo());
		getExpedienteVista().setSecretario("");
		getExpedienteVista().setCalificacion(0);
		getExpedienteVista().setRecurrencia(new Recurrencia());

		getExpedienteVista().setHonorario(new Honorario());
		getExpedienteVista().setHonorarios(new ArrayList<Honorario>());

		getExpedienteVista().setInvolucrado(new Involucrado());
		getExpedienteVista().setInvolucradoDataModel(new InvolucradoDataModel());

		getExpedienteVista().setCuantia(new Cuantia());
		getExpedienteVista().setCuantiaDataModel(new CuantiaDataModel());

		getExpedienteVista().setInculpado(new Inculpado());
		getExpedienteVista().setInculpados(new ArrayList<Inculpado>());

		getExpedienteVista().setMoneda(0);
		getExpedienteVista().setMontoCautelar(0.0);
		getExpedienteVista().setTipoCautelar(0);
		getExpedienteVista().setDescripcionCautelar("");
		getExpedienteVista().setContraCautela(0);
		getExpedienteVista().setImporteCautelar(0.0);
		getExpedienteVista().setEstadoCautelar(0);

		getExpedienteVista().setResumen("");
		getExpedienteVista().setTodoResumen("");

		getExpedienteVista().setRiesgo(0);

	}

	// @SuppressWarnings("unchecked")
	 public void actualizarExpediente(Expediente expediente, ExpedienteVista expedienteVista) {
	
	 GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia,Object>) SpringInit.getApplicationContext().getBean("genericoDao");
	 try {
		 Instancia instanciabd = instanciaDAO.buscarById(Instancia.class , expedienteVista.getInstancia());
		 expediente.setInstancia(instanciabd);
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	
	 expediente.setOficina(expedienteVista.getOficina());
	 expediente.setOrgano(expedienteVista.getOrgano1());
	 expediente.setSecretario(expedienteVista.getSecretario());
	 expediente.setRecurrencia(expedienteVista.getRecurrencia());
	
	 List<Honorario> honorarios = expedienteVista.getHonorarios();
	 expediente.setHonorarios(new ArrayList<Honorario>());
	 for (Honorario honorario : honorarios)
		 if (honorario != null)
			 expediente.addHonorario(honorario);
	
	 List<Involucrado> involucrados = (List<Involucrado>)expedienteVista.getInvolucradoDataModel().getWrappedData();
	 expediente.setInvolucrados(new ArrayList<Involucrado>());
	 for (Involucrado involucrado : involucrados)
		 if (involucrado != null)
			 expediente.addInvolucrado(involucrado);
	
	 List<Cuantia> cuantias = (List<Cuantia>) expedienteVista.getCuantiaDataModel().getWrappedData();
	 expediente.setCuantias(new ArrayList<Cuantia>());
	 for (Cuantia cuantia : cuantias)
		 if (cuantia != null)
			 expediente.addCuantia(cuantia);
	
	 List<Inculpado> inculpados = expedienteVista.getInculpados();
	 expediente.setInculpados(new ArrayList<Inculpado>());
	 for (Inculpado inculpado : inculpados)
		 if (inculpado != null)
			 expediente.addInculpado(inculpado);
	
	 GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>)SpringInit.getApplicationContext().getBean("genericoDao");
	 try {
		 Moneda monedabd = monedaDAO.buscarById(Moneda.class, expedienteVista.getMoneda());
		 expediente.setMoneda(monedabd);
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	
	 expediente.setMontoCautelar(expedienteVista.getMontoCautelar());
	 GenericDao<TipoCautelar, Object> tipoCautelarDAO =(GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
	 try {
		 TipoCautelar tipoCautelarbd = tipoCautelarDAO.buscarById(TipoCautelar.class, expedienteVista.getTipoCautelar());
		 expediente.setTipoCautelar(tipoCautelarbd);
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	 expediente.setDescripcionCautelar(expedienteVista.getDescripcionCautelar());
	
	 GenericDao<ContraCautela, Object> contraCautelaDAO =(GenericDao<ContraCautela, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
	 try {
		 ContraCautela contraCautelabd = contraCautelaDAO.buscarById(
		 ContraCautela.class, expedienteVista.getContraCautela());
		 expediente.setContraCautela(contraCautelabd);
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	
	 expediente.setImporteCautelar(expedienteVista.getImporteCautelar());
	
	 GenericDao<EstadoCautelar, Object> estadoCautelarDAO =(GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
	 try {
		 EstadoCautelar estadoCautelarbd = estadoCautelarDAO.buscarById(
		 EstadoCautelar.class, expedienteVista.getEstadoCautelar());
		 expediente.setEstadoCautelar(estadoCautelarbd);
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	
	 List<Provision> provisions = expedienteVista.getProvisiones();
	 expediente.setProvisions(new ArrayList<Provision>());
	 for (Provision provision : provisions)
		 if (provision != null)
			 expediente.addProvision(provision);
	
	 expediente.setFechaResumen(expedienteVista.getFechaResumen());
	 expediente.setTextoResumen(expedienteVista.getResumen());
	 // expediente.setAcumuladoResumen(getTodoResumen());
	
	 List<ActividadProcesal> actividadProcesals = expedienteVista.getActividadProcesales();
	 expediente.setActividadProcesals(new ArrayList<ActividadProcesal>());
	 for (ActividadProcesal actividadProcesal : actividadProcesals)
		 if (actividadProcesal != null)
			 expediente.addActividadProcesal(actividadProcesal);
	
	 List<Anexo> anexos = expedienteVista.getAnexos();
	 expediente.setAnexos(new ArrayList<Anexo>());
	 for (Anexo anexo : anexos)
		 if (anexo != null)
			 expediente.addAnexo(anexo);
	
	 GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>)SpringInit.getApplicationContext().getBean("genericoDao");
	 try {
		 Riesgo riesgobd = riesgoDAO.buscarById(Riesgo.class, expedienteVista.getRiesgo());
		 expediente.setRiesgo(riesgobd);
	 } catch (Exception e) {
		 e.printStackTrace();
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
			if (formaConclusion.getDescripcion().toLowerCase()
					.startsWith(query.toLowerCase())) {
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
			if (pers.getNombres().toUpperCase()
					.startsWith(query.toUpperCase()) ||
					pers.getApellidoPaterno().toUpperCase()
					.startsWith(query.toUpperCase()) ||
					pers.getApellidoMaterno().toUpperCase()
					.startsWith(query.toUpperCase())
					) {
				
				pers.setNombreCompletoMayuscula(pers.getNombres().toUpperCase() + " " +
						pers.getApellidoPaterno().toUpperCase() + " " +
						pers.getApellidoMaterno().toUpperCase());
				
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

			if (abog.getNombres().toUpperCase()
					.startsWith(query.toUpperCase()) ||
					abog.getApellidoPaterno().toUpperCase()
					.startsWith(query.toUpperCase()) ||
					abog.getApellidoMaterno().toUpperCase()
					.startsWith(query.toUpperCase())
					) {
				
				abog.setNombreCompletoMayuscula(abog.getNombres().toUpperCase() + " " +
						abog.getApellidoPaterno().toUpperCase() + " " +
						abog.getApellidoMaterno().toUpperCase());
				
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
						+ organo.getTerritorio().getDepartamento().toUpperCase() + ")";
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
	private void cargarCombos() {

		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(EstadoExpediente.class);
		try {
			estados = estadosExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Proceso.class);
		try {
			procesos = procesoDAO.buscarDinamico(filtro);
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

		tipoHonorariosString = new ArrayList<String>();
		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoHonorario.class);
		try {
			tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtro);
			for(TipoHonorario t:tipoHonorarios)
				tipoHonorariosString.add(t.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		monedasString= new ArrayList<String>();
		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Moneda.class);
		try {
			monedas = monedaDAO.buscarDinamico(filtro);
			for(Moneda m:monedas )
				monedasString.add(m.getSimbolo());
		} catch (Exception e) {
			e.printStackTrace();
		}

		situacionHonorariosString= new ArrayList<String>();
		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionHonorario.class);
		try {
			situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro);
			for(SituacionHonorario s:situacionHonorarios)
				situacionHonorariosString.add(s.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		situacionCuotasString= new ArrayList<String>();
		GenericDao<SituacionCuota, Object> situacionCuotasDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionCuota.class);
		try {
			situacionCuotas = situacionCuotasDAO.buscarDinamico(filtro);
			for(SituacionCuota s:situacionCuotas)
				situacionCuotasString.add(s.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		rolInvolucradosString = new ArrayList<String>();
		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(RolInvolucrado.class);
		try {
			rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtro);
			for(RolInvolucrado r:rolInvolucrados)
				rolInvolucradosString.add(r.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}

		tipoInvolucradosString = new ArrayList<String>();
		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoInvolucrado.class);
		try {
			tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtro);
			for(TipoInvolucrado t:tipoInvolucrados)
				tipoInvolucradosString.add(t.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}

		situacionInculpadosString = new ArrayList<String>();
		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionInculpado.class);
		try {
			situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro);
			for(SituacionInculpado s:situacionInculpados)
				situacionInculpadosString.add(s.getNombre());
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

	public void editHonor(RowEditEvent event) {
		
		Honorario honorario = ((Honorario) event.getObject());
		
	}

	public void editDetHonor(RowEditEvent event) {
		
		
		Cuota cuota = ((Cuota) event.getObject());
		//2 indica el estado pagado 
		if(cuota.getSituacionCuota().getDescripcion().equals("Pagado") || 
				cuota.getSituacionCuota().getDescripcion().equals("Baja")){
			
			for(Honorario honorario:getExpedienteVista().getHonorarios()){
				if( cuota.getHonorario().getIdHonorario() == honorario.getIdHonorario() ){
					double importe= cuota.getImporte();
					honorario.setMonto(honorario.getMonto() - importe);
					honorario.setMontoPagado(honorario.getMontoPagado() + importe);
				}
			}
			
		}
		
		
		FacesMessage msg = new FacesMessage("Honorario Editado",
				"Honorario Editado al pagar una cuota");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Editado","Honorario Editado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Cancelado","Honorario Cancelado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public Organo getSelectedOrgano() {
		return selectedOrgano;
	}

	public void setSelectedOrgano(Organo selectedOrgano) {
		this.selectedOrgano = selectedOrgano;
	}

	public Persona getSelectPersona() {
		return selectPersona;
	}

	public void setSelectPersona(Persona selectPersona) {
		this.selectPersona = selectPersona;
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

	public boolean isTabCaucion() {
		return tabCaucion;
	}

	public void setTabCaucion(boolean tabCaucion) {
		this.tabCaucion = tabCaucion;
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

	public List<Entidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidad> entidades) {
		this.entidades = entidades;
	}

	public List<TipoHonorario> getTipoHonorarios() {
		return tipoHonorarios;
	}

	public void setTipoHonorarios(List<TipoHonorario> tipoHonorarios) {
		this.tipoHonorarios = tipoHonorarios;
	}

	public List<SituacionHonorario> getSituacionHonorarios() {
		return situacionHonorarios;
	}

	public void setSituacionHonorarios(
			List<SituacionHonorario> situacionHonorarios) {
		this.situacionHonorarios = situacionHonorarios;
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

	public List<RolInvolucrado> getRolInvolucrados() {
		return rolInvolucrados;
	}

	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
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

	public List<TipoCautelar> getTipoCautelares() {
		return tipoCautelares;
	}

	public void setTipoCautelares(List<TipoCautelar> tipoCautelares) {
		this.tipoCautelares = tipoCautelares;
	}

	public List<ContraCautela> getContraCautelas() {
		return contraCautelas;
	}

	public void setContraCautelas(List<ContraCautela> contraCautelas) {
		this.contraCautelas = contraCautelas;
	}

	public Estudio getEstudio() {
		return estudio;
	}

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public ActSeguimientoExpedienteMB() {
		
		logger.debug("Inicializando Valores..");
		inicializarValores();
		logger.debug("Llenar hitos...");
		llenarHitos();
		logger.debug("Cargando combos...");
		cargarCombos();
		

	}
	
	public String reset(){
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "consultaExpediente.xhtml?faces-redirect=true";
	}
	
	
	public void llenarHitos(){
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession sessionhttp = (HttpSession) context.getSession(true);
		String numeroExpediente = (String) sessionhttp.getAttribute("numeroExpediente");
		
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Expediente.class);
		filtro.add(Expression.like("numeroExpediente", numeroExpediente));
		
		List<Expediente> expedientes= new ArrayList<Expediente>();
		
		try {
			expedientes = expedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		setExpedienteVistas(new ArrayList<ExpedienteVista>());
		
		
		//SpringInit.openSession();
		
	
		for (int i = 0; i < expedientes.size(); i++) {
		
			ExpedienteVista expedienteVistaNuevo= new ExpedienteVista();
			expedienteVistaNuevo.setFlagDeshabilitadoGeneral(true);
			
			if(i == expedientes.size()-1){
				
				setPosiExpeVista(i);
				setTabActivado(i);
				
				setExpedienteOrig(expedientes.get(i));
				
				expedienteVistaNuevo.setFlagColumnMostrar(true);
				expedienteVistaNuevo.setFlagHabilitadoModificar(false);
				actualizarDatosPagina(expedienteVistaNuevo, expedientes.get(i));
				getExpedienteVistas().add(expedienteVistaNuevo);
		
				setExpedienteVista(expedienteVistaNuevo);
				
			}else{
				
				expedienteVistaNuevo.setFlagColumnMostrar(false);
				expedienteVistaNuevo.setFlagHabilitadoModificar(true);
				actualizarDatosPagina(expedienteVistaNuevo, expedientes.get(i));
				getExpedienteVistas().add(expedienteVistaNuevo);
			}
			
		}

		
		
	}
	
	private void inicializarValores(){
		
		organo = new Organo();
		organoDataModel = new OrganoDataModel(new ArrayList<Organo>());
		selectedOrgano = new Organo();

		abogado = new Abogado();
		estudio = new Estudio();
		abogadoDataModel = new AbogadoDataModel(new ArrayList<Abogado>());

		persona = new Persona();
		personaDataModelBusq = new PersonaDataModel(new ArrayList<Persona>());
		selectPersona = new Persona();
	
		
		
	}

	public void actualizarDatosPagina(ExpedienteVista ex, Expediente e) {

		
		ex.setNroExpeOficial(e.getNumeroExpediente());
		ex.setInicioProceso(e.getFechaInicioProceso());
		ex.setEstado(e.getEstadoExpediente().getIdEstadoExpediente());
		ex.setProceso(e.getInstancia().getVia().getProceso().getIdProceso());

		GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Via.class);
		filtro.add(Expression.like("proceso.idProceso", ex.getProceso()));

		try {
			ex.setVias(viaDao.buscarDinamico(filtro));
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		ex.setVia(e.getInstancia().getVia().getIdVia());

		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
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
		
		String texto = e.getOficina().getCodigo() + " "
				+ e.getOficina().getNombre().toUpperCase() + " ("
				+ e.getOficina().getTerritorio().getDepartamento().toUpperCase()
				+ ")";
		
		e.getOficina().setNombreDetallado(texto);
		
		
		ex.setOficina(e.getOficina());
		ex.setTipo(e.getTipoExpediente().getIdTipoExpediente());
		
		String descripcion = e.getOrgano().getNombre().toUpperCase()
				+ " ("
				+ e.getOrgano().getTerritorio().getDistrito().toUpperCase()
				+ ", "
				+ e.getOrgano().getTerritorio().getProvincia().toUpperCase()
				+ ", "
				+ e.getOrgano().getTerritorio().getDepartamento().toUpperCase() + ")";
		
		e.getOrgano().setNombreDetallado(descripcion);
		
		ex.setOrgano1(e.getOrgano());
		
		ex.setSecretario(e.getSecretario());
		ex.setCalificacion(e.getCalificacion().getIdCalificacion());
		ex.setRecurrencia(e.getRecurrencia());

		ex.setHonorario(new Honorario());
		ex.getHonorario().setCantidad(0);
		ex.getHonorario().setMonto(0.0);
		ex.getHonorario().setMontoPagado(0.0);

		List<Honorario> honorarios = new ArrayList<Honorario>();
		List<Cuota> cuotas;
		
		GenericDao<Honorario, Object> honorarioDAO = (GenericDao<Honorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Cuota, Object> cuotaDAO = (GenericDao<Cuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		filtro = Busqueda.forClass(Honorario.class);
		filtro.add(Expression.like("expediente.idExpediente",e.getIdExpediente()));

		try {
			honorarios = honorarioDAO.buscarDinamico(filtro);
			
			for(Honorario h:honorarios){
				cuotas = new ArrayList<Cuota>();
				filtro = Busqueda.forClass(Cuota.class);
				filtro.add(Expression.like("honorario.idHonorario",h.getIdHonorario()));
				cuotas = cuotaDAO.buscarDinamico(filtro);
				h.setCuotas(cuotas);
			}
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		ex.setHonorarios(honorarios);

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Involucrado.class);
		filtro.add(Expression.like("expediente.idExpediente",e.getIdExpediente()));

		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(involucrados);
		ex.setInvolucradoDataModel(involucradoDataModel);
		ex.setInvolucrado(new Involucrado(new TipoInvolucrado(), new RolInvolucrado(), new Persona()));
		
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
		ex.setInculpado(new Inculpado(new SituacionInculpado(), new Moneda(), new Persona()));

		ex.setMoneda(e.getMoneda().getIdMoneda());
		ex.setMontoCautelar(e.getMontoCautelar());
		ex.setTipoCautelar(e.getTipoCautelar().getIdTipoCautelar());
		ex.setDescripcionCautelar(e.getDescripcionCautelar());
		ex.setContraCautela(e.getContraCautela().getIdContraCautela());
		ex.setImporteCautelar(e.getImporteCautelar());
		ex.setEstadoCautelar(e.getEstadoCautelar().getIdEstadoCautelar());
		
		List<Provision> provisions = new ArrayList<Provision>();
		GenericDao<Provision, Object> provisionDAO = (GenericDao<Provision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Provision.class);
		filtro.add(Expression.like("expediente.idExpediente",e.getIdExpediente()));

		try {
			provisions = provisionDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setProvisiones(provisions);
		ex.setProvision(new Provision(new Moneda(), new TipoProvision()));

		
		ex.setFechaResumen(e.getFechaResumen());
		ex.setResumen(e.getTextoResumen());
		// setTodoResumen(getSelectedExpediente().get)

		List<ActividadProcesal> actividadProcesals = new ArrayList<ActividadProcesal>();
		GenericDao<ActividadProcesal, Object> actividadProcesalDAO = (GenericDao<ActividadProcesal, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(ActividadProcesal.class);
		filtro.add(Expression.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			actividadProcesals = actividadProcesalDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setActividadProcesales(actividadProcesals);
		ex.setActividadProcesal(new ActividadProcesal(new Etapa(),new SituacionActProc(), new Actividad()));
		
		
		List<Anexo> anexos = new ArrayList<Anexo>();
		GenericDao<Anexo, Object> anexoDAO = (GenericDao<Anexo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
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
		if (ex.getProceso() == 2) {
			setTabCaucion(true);
		}
	}

//	public void actualizarDatosPagina(Expediente e) {
//
//		setNroExpeOficial(e.getNumeroExpediente());
//		setInicioProceso(e.getFechaInicioProceso());
//		setEstado(e.getEstadoExpediente().getIdEstadoExpediente());
//		setProceso(e.getInstancia().getVia().getProceso().getIdProceso());
//
//		GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		Busqueda filtro = Busqueda.forClass(Via.class);
//		filtro.add(Expression.like("proceso.idProceso", getProceso()));
//
//		try {
//			setVias(viaDao.buscarDinamico(filtro));
//		} catch (Exception exc) {
//			exc.printStackTrace();
//		}
//
//		setVia(e.getInstancia().getVia().getIdVia());
//
//		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Instancia.class);
//		filtro.add(Expression.like("via.idVia", getVia()));
//
//		try {
//			setInstancias(instanciaDao.buscarDinamico(filtro));
//		} catch (Exception exc) {
//			exc.printStackTrace();
//		}
//
//		setInstancia(e.getInstancia().getIdInstancia());
//
//		setResponsable(e.getUsuario());
//		
//		String texto = e.getOficina().getCodigo() + " "
//				+ e.getOficina().getNombre().toUpperCase() + " ("
//				+ e.getOficina().getTerritorio().getDepartamento().toUpperCase()
//				+ ")";
//		
//		e.getOficina().setNombreDetallado(texto);
//		
//		
//		setOficina(e.getOficina());
//		setTipo(e.getTipoExpediente().getIdTipoExpediente());
//		
//		String descripcion = e.getOrgano().getNombre().toUpperCase()
//				+ " ("
//				+ e.getOrgano().getTerritorio().getDistrito().toUpperCase()
//				+ ", "
//				+ e.getOrgano().getTerritorio().getProvincia().toUpperCase()
//				+ ", "
//				+ e.getOrgano().getTerritorio().getDepartamento().toUpperCase() + ")";
//		
//		e.getOrgano().setNombreDetallado(descripcion);
//		
//		setOrgano1(e.getOrgano());
//		
//		setSecretario(e.getSecretario());
//		setCalificacion(e.getCalificacion().getIdCalificacion());
//		setRecurrencia(e.getRecurrencia());
//
//		setHonorario(new Honorario());
//		getHonorario().setCantidad(0);
//		getHonorario().setMonto(0.0);
//		getHonorario().setMontoPagado(0.0);
//
//		List<Honorario> honorarios = new ArrayList<Honorario>();
//		List<Cuota> cuotas;
//		
//		GenericDao<Honorario, Object> honorarioDAO = (GenericDao<Honorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		GenericDao<Cuota, Object> cuotaDAO = (GenericDao<Cuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		
//		filtro = Busqueda.forClass(Honorario.class);
//		filtro.add(Expression.like("expediente.idExpediente",e.getIdExpediente()));
//
//		try {
//			honorarios = honorarioDAO.buscarDinamico(filtro);
//			
//			for(Honorario h:honorarios){
//				cuotas = new ArrayList<Cuota>();
//				filtro = Busqueda.forClass(Cuota.class);
//				filtro.add(Expression.like("honorario.idHonorario",h.getIdHonorario()));
//				cuotas = cuotaDAO.buscarDinamico(filtro);
//				h.setCuotas(cuotas);
//			}
//			
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//
//		setHonorarios(honorarios);
//
//		List<Involucrado> involucrados = new ArrayList<Involucrado>();
//		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Involucrado.class);
//		filtro.add(Expression.like("expediente.idExpediente",e.getIdExpediente()));
//
//		try {
//			involucrados = involucradoDAO.buscarDinamico(filtro);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//
//		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(involucrados);
//		setInvolucradoDataModel(involucradoDataModel);
//
//		List<Cuantia> cuantias = new ArrayList<Cuantia>();
//		GenericDao<Cuantia, Object> cuantiaDAO = (GenericDao<Cuantia, Object>) SpringInit
//				.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Cuantia.class);
//		filtro.add(Expression.like("expediente.idExpediente",
//				e.getIdExpediente()));
//
//		try {
//			cuantias = cuantiaDAO.buscarDinamico(filtro);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//
//		CuantiaDataModel cuantiaDataModel = new CuantiaDataModel(cuantias);
//		setCuantiaDataModel(cuantiaDataModel);
//
//		List<Inculpado> inculpados = new ArrayList<Inculpado>();
//		GenericDao<Inculpado, Object> inculpadoDAO = (GenericDao<Inculpado, Object>) SpringInit
//				.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Inculpado.class);
//		filtro.add(Expression.like("expediente.idExpediente",
//				e.getIdExpediente()));
//
//		try {
//			inculpados = inculpadoDAO.buscarDinamico(filtro);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//
//		setInculpados(inculpados);
//
//		setMoneda(e.getMoneda().getIdMoneda());
//		setMontoCautelar(e.getMontoCautelar());
//		setTipoCautelar(e.getTipoCautelar().getIdTipoCautelar());
//		setDescripcionCautelar(e.getDescripcionCautelar());
//		setContraCautela(e.getContraCautela().getIdContraCautela());
//		setImporteCautelar(e.getImporteCautelar());
//		setEstadoCautelar(e.getEstadoCautelar().getIdEstadoCautelar());
//		setFechaResumen(e.getFechaResumen());
//		setResumen(e.getTextoResumen());
//		// setTodoResumen(getSelectedExpediente().get)
//
//		List<Anexo> anexos = new ArrayList<Anexo>();
//		GenericDao<Anexo, Object> anexoDAO = (GenericDao<Anexo, Object>) SpringInit
//				.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Anexo.class);
//		filtro.add(Expression.like("expediente.idExpediente",
//				e.getIdExpediente()));
//
//		try {
//			anexos = anexoDAO.buscarDinamico(filtro);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//		setAnexos(anexos);
//		setRiesgo(e.getRiesgo().getIdRiesgo());
//
//		List<ActividadProcesal> actividadProcesals = new ArrayList<ActividadProcesal>();
//		GenericDao<ActividadProcesal, Object> actividadProcesalDAO = (GenericDao<ActividadProcesal, Object>) SpringInit
//				.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(ActividadProcesal.class);
//		filtro.add(Expression.like("expediente.idExpediente",
//				e.getIdExpediente()));
//
//		try {
//			actividadProcesals = actividadProcesalDAO.buscarDinamico(filtro);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//
//		setActividadProcesales(actividadProcesals);
//		setActividadProcesal(new ActividadProcesal(new Etapa(),
//				new SituacionActProc(), new Actividad()));
//		setProvision(new Provision(new Moneda(), new TipoProvision()));
//
//		setTabCaucion(false);
//		if (getProceso() == 2) {
//			setTabCaucion(true);
//		}
//	}
	public void onTabChange(TabChangeEvent event) {
		FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: "
				+ event.getTab().getTitle());

		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public List<TipoProvision> getTipoProvisiones() {
		return tipoProvisiones;
	}

	public void setTipoProvisiones(List<TipoProvision> tipoProvisiones) {
		this.tipoProvisiones = tipoProvisiones;
	}

	public List<String> getTipoHonorariosString() {
		return tipoHonorariosString;
	}

	public void setTipoHonorariosString(List<String> tipoHonorariosString) {
		this.tipoHonorariosString = tipoHonorariosString;
	}

	public List<String> getMonedasString() {
		return monedasString;
	}

	public void setMonedasString(List<String> monedasString) {
		this.monedasString = monedasString;
	}

	public List<String> getSituacionHonorariosString() {
		return situacionHonorariosString;
	}

	public void setSituacionHonorariosString(List<String> situacionHonorariosString) {
		this.situacionHonorariosString = situacionHonorariosString;
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

	public void setSituacionInculpadosString(List<String> situacionInculpadosString) {
		this.situacionInculpadosString = situacionInculpadosString;
	}

	public List<ExpedienteVista> getExpedienteVistas() {
		return expedienteVistas;
	}

	public void setExpedienteVistas(List<ExpedienteVista> expedienteVistas) {
		this.expedienteVistas = expedienteVistas;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ActSeguimientoExpedienteMB.logger = logger;
	}

	public int getTabActivado() {
		return tabActivado;
	}

	public void setTabActivado(int tabActivado) {
		this.tabActivado = tabActivado;
	}

	public ExpedienteVista getExpedienteVista() {
		return expedienteVista;
	}

	public void setExpedienteVista(ExpedienteVista expedienteVista) {
		this.expedienteVista = expedienteVista;
	}

	public int getPosiExpeVista() {
		return posiExpeVista;
	}

	public void setPosiExpeVista(int posiExpeVista) {
		this.posiExpeVista = posiExpeVista;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Expediente getExpedienteOrig() {
		return expedienteOrig;
	}

	public void setExpedienteOrig(Expediente expedienteOrig) {
		this.expedienteOrig = expedienteOrig;
	}

	public List<Instancia> getInstanciasProximas() {
		return instanciasProximas;
	}

	public void setInstanciasProximas(List<Instancia> instanciasProximas) {
		this.instanciasProximas = instanciasProximas;
	}

	public Date getFinInstancia() {
		return finInstancia;
	}

	public void setFinInstancia(Date finInstancia) {
		this.finInstancia = finInstancia;
	}

	public int getInstanciaProxima() {
		return instanciaProxima;
	}

	public void setInstanciaProxima(int instanciaProxima) {
		this.instanciaProxima = instanciaProxima;
	}

	public FormaConclusion getFormaConclusion2() {
		return formaConclusion2;
	}

	public void setFormaConclusion2(FormaConclusion formaConclusion2) {
		this.formaConclusion2 = formaConclusion2;
	}

	public int getNewInstancia() {
		return newInstancia;
	}

	public void setNewInstancia(int newInstancia) {
		this.newInstancia = newInstancia;
	}

	public Oficina getNewOficina() {
		return newOficina;
	}

	public void setNewOficina(Oficina newOficina) {
		this.newOficina = newOficina;
	}

	public Organo getNewOrgano1() {
		return newOrgano1;
	}

	public void setNewOrgano1(Organo newOrgano1) {
		this.newOrgano1 = newOrgano1;
	}

	public String getNewSecretario() {
		return newSecretario;
	}

	public void setNewSecretario(String newSecretario) {
		this.newSecretario = newSecretario;
	}

	public Recurrencia getNewRecurrencia() {
		return newRecurrencia;
	}

	public void setNewRecurrencia(Recurrencia newRecurrencia) {
		this.newRecurrencia = newRecurrencia;
	}

}
