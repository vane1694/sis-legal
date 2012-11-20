package com.hildebrando.legal.mb;

import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.AbogadoEstudio;
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
import com.hildebrando.legal.modelo.Resumen;
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
import com.hildebrando.legal.modelo.Ubigeo;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.RegistroExpedienteService;
import com.hildebrando.legal.util.Util;
import com.hildebrando.legal.view.AbogadoDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.InvolucradoDataModel;
import com.hildebrando.legal.view.OrganoDataModel;
import com.hildebrando.legal.view.PersonaDataModel;

@ManagedBean(name = "actSeguiExpe")
@SessionScoped
public class ActSeguimientoExpedienteMB{

	public static Logger logger = Logger.getLogger(ActSeguimientoExpedienteMB.class);

	private List<Proceso> procesos;
	private List<EstadoExpediente> estados;
	private List<TipoExpediente> tipos;
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
	private Persona selectInvolucrado;
	private PersonaDataModel personaDataModelBusq;

	private List<SituacionInculpado> situacionInculpados;
	private List<String> situacionInculpadosString;
	private List<TipoCautelar> tipoCautelares;
	private List<EstadoCautelar> estadosCautelares;
	private List<Riesgo> riesgos;

	private List<TipoProvision> tipoProvisiones;
	private List<String> tipoProvisionesString;

	private List<Actividad> actividades;
	private List<String> actividadesString;

	private List<Etapa> etapas;
	private List<String> etapasString;

	private List<SituacionActProc> situacionActProcesales;
	private List<String> situacionActProcesalesString;

	private List<ContraCautela> contraCautelas;

	private List<Cuota> cuotas;
	
	private UploadedFile file;
	private Anexo anexo;
	
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

	private Abogado selectedAbogado;
	
	private boolean flagGuardarInstancia;
	private boolean flagGuardarOficina;
	private boolean flagGuardarRecurrencia;
	private boolean flagGuardarOrgano1;
	private boolean flagGuardarSecretario;

	private boolean flagModificadoHonor;
	private boolean flagModificadoInv;
	private boolean flagModificadoCua;
	private boolean flagModificadoInc;

	private boolean flagGuardarMoneda;
	private boolean flagGuardarMonto;
	private boolean flagGuardarTipoMediaCautelar;
	private boolean flagGuardarDescripcionCautelar;
	private boolean flagGuardarContraCautela;
	private boolean flagGuardarImporteCautela;
	private boolean flagGuardarEstado;

	private boolean flagModificadoProv;

	private boolean flagGuardarResumen;

	private boolean flagModificadoActPro;
	private boolean flagModificadoAnexo;

	private boolean flagGuardarRiesgo;

	public void setExpedienteService(RegistroExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public void agregarTodoResumen(ActionEvent e) {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		if(getExpedienteVista().getFechaResumen() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha de Resumen Requerido", "Fecha de Resumen Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(getExpedienteVista().getResumen() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Resumen Requerido", "Resumen Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{

				getExpedienteVista().setDeshabilitarBotonGuardar(false);
				getExpedienteVista().setDeshabilitarBotonFinInst(true);
				flagGuardarResumen = true;

				
				if(getExpedienteVista().getTodoResumen()==null){
					
					getExpedienteVista().setTodoResumen( "Jorge Guzman"+ "\n" +
									"\t" + getExpedienteVista().getResumen() + "\n" +
									"\t" + "\t" + "\t" + "\t" + "\t" +
									"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(getExpedienteVista().getFechaResumen()));
					
				}else{
					
					getExpedienteVista().setTodoResumen( "Jorge Guzman" + "\n" +
									"\t" + getExpedienteVista().getResumen() + "\n" +
									"\t" + "\t" + "\t" + "\t" + "\t" +
									"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(getExpedienteVista().getFechaResumen()) + "\n" +
									"---------------------------------------------------------------------------" + "\n" +
									getExpedienteVista().getTodoResumen());
					
				}
				
				if (getExpedienteVista().getResumens() == null) {
					getExpedienteVista().setResumens(new ArrayList<Resumen>());
				}

				Resumen resumen= new Resumen();
				resumen.setTexto(getExpedienteVista().getResumen());
				resumen.setFecha(getExpedienteVista().getFechaResumen());
				
				getExpedienteVista().getResumens().add(resumen);
				
				getExpedienteVista().setResumen("");
				getExpedienteVista().setFechaResumen(null);
				
				
			}
			
		}
		
		

	}

	// listener cada vez que se modifica la via
	public void cambioVia() {

		if (getExpedienteVista().getVia() != 0) {

			GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Instancia.class);
			filtro.add(Restrictions.like("via.idVia", getExpedienteVista()
					.getVia()));

			try {
				getExpedienteVista().setInstancias(
						instanciaDao.buscarDinamico(filtro));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			getExpedienteVista().setInstancias(new ArrayList<Instancia>());

		}

	}

	public void crearProximaInstancia(ActionEvent e) {

		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Expediente expedienteSiguiente = new Expediente();
		Expediente expediente = getExpedienteOrig();
		
		expedienteSiguiente = (Expediente) expediente.clone();
		expedienteSiguiente.setIdExpediente(0);
		expedienteSiguiente.setExpediente(null);

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			Instancia instanciaProximaBD = instanciaDAO.buscarById(Instancia.class, getInstanciaProxima());
			expedienteSiguiente.setInstancia(instanciaProximaBD);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		try {
			expedienteDAO.insertar(expedienteSiguiente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		actualizarExpedienteListas(expedienteSiguiente, getExpedienteVista());
		
		List<ActividadProcesal> actividadProcesals = expedienteSiguiente.getActividadProcesals();
		expedienteSiguiente.setActividadProcesals(new ArrayList<ActividadProcesal>());

		// para obtener la ultima actividad procesal
		long mayor = 0;
		int posi = 0;
		
		if(actividadProcesals!=null){
			
			if(actividadProcesals.size() != 0){
				for (int i = 0; i < actividadProcesals.size(); i++) {
					if (actividadProcesals.get(i).getIdActividadProcesal() > mayor) {
						mayor = actividadProcesals.get(i).getIdActividadProcesal();
						posi = i;
					}
				}
				
				ActividadProcesal actividadProcesal = actividadProcesals.get(posi);
				actividadProcesal.setIdActividadProcesal(0);
				expedienteSiguiente.addActividadProcesal(actividadProcesal);
			}
		}
		
		try {
			expedienteDAO.modificar(expedienteSiguiente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		expediente.setFechaFinProceso(getFinInstancia());
		expediente.setFormaConclusion(getFormaConclusion2());
		expediente.setExpediente(expedienteSiguiente);
		try {
			expedienteDAO.modificar(expediente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		llenarHitos();
	}

	public void cambioEstadoCautela() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarEstado = true;
	}

	public void cambioImporteCautela() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarImporteCautela = true;
	}

	public void cambioContraCautela() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarContraCautela = true;
	}

	public void cambioDescripcion() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarDescripcionCautelar = true;
	}

	public void cambioTipo() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarTipoMediaCautelar = true;
	}

	public void cambioMonto() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarMonto = true;
	}

	public void cambioMoneda() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarMoneda = true;
	}

	public void cambioInstancia() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarInstancia = true;
	}

	public void cambioOficina(ValueChangeEvent e) {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarOficina = true;
	}

	public void cambioOrgano(ValueChangeEvent e) {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarOrgano1 = true;
	}

	public void cambioSecretario() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarSecretario = true;
	}

	public void cambioRecurrencia(ValueChangeEvent e) {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarRecurrencia = true;
	}

	public void cambioRiesgo() {

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		flagGuardarRiesgo = true;
	}

	public void finalizarProceso(ActionEvent e) {

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Expediente expediente = getExpedienteOrig();
		expediente.setFechaFinProceso(getFinInstancia());
		expediente.setFormaConclusion(getFormaConclusion2());

		try {
			EstadoExpediente estadoExpedienteConcluido = estadoExpedienteDAO.buscarById(EstadoExpediente.class, 2);
			expediente.setEstadoExpediente(estadoExpedienteConcluido);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		try {
			expedienteDAO.modificar(expediente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		llenarHitos();
	}

	@SuppressWarnings("unchecked")
	public String home() {

		
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		com.grupobbva.seguridad.client.domain.Usuario usuarioAux= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(true);
		session.setAttribute("usuario", usuarioAux);
		

		return "consultaExpediente.xhtml?faces-redirect=true";
	}
	
	public void actualizar(ActionEvent e) {

		getExpedienteVista().setDeshabilitarBotonGuardar(true);
		getExpedienteVista().setDeshabilitarBotonFinInst(false);

		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Expediente expediente = getExpedienteOrig();
		actualizarExpedienteActual(expediente, getExpedienteVista());
		
		try {
			expedienteDAO.modificar(expediente);
			FacesContext.getCurrentInstance().addMessage("growl",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Actualizo el expediente"));
			logger.debug("Actualizo el expediente exitosamente");
			
		} catch (Exception ex) {

			FacesContext.getCurrentInstance().addMessage("growl",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Actualizo el expediente"));
			logger.debug("No Actualizo el expediente "+ ex.getMessage());
		}

		
		llenarHitos();
		
		setFlagGuardarInstancia(false);
		setFlagGuardarOficina(false);
		setFlagGuardarRecurrencia(false);
		setFlagGuardarOrgano1(false);
		setFlagGuardarSecretario(false);

		setFlagModificadoHonor(false);
		setFlagModificadoInv(false);
		setFlagModificadoInc(false);

		setFlagGuardarMoneda(false);
		setFlagGuardarMonto(false);
		setFlagGuardarTipoMediaCautelar(false);
		setFlagGuardarDescripcionCautelar(false);
		setFlagGuardarContraCautela(false);
		setFlagGuardarImporteCautela(false);
		setFlagGuardarEstado(false);

		setFlagModificadoProv(false);
		setFlagModificadoActPro(false);
		setFlagModificadoAnexo(false);
		setFlagGuardarRiesgo(false);

	}

	public void deleteHonorario() {

		setFlagModificadoHonor(true);

		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

		getExpedienteVista().getHonorarios().remove(
				getExpedienteVista().getSelectedHonorario());

	}

	public void deleteAnexo() {

		setFlagModificadoAnexo(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

		getExpedienteVista().getAnexos().remove(
				getExpedienteVista().getSelectedAnexo());

	}

	public void deleteInvolucrado() {

		setFlagModificadoInv(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

		List<Involucrado> involucrados = (List<Involucrado>) getExpedienteVista()
				.getInvolucradoDataModel().getWrappedData();
		involucrados.remove(getExpedienteVista().getSelectedInvolucrado());

		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(
				involucrados);
		getExpedienteVista().setInvolucradoDataModel(involucradoDataModel);
	}

	public void deleteCuantia() {

		setFlagModificadoCua(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		
		List<Cuantia> cuantias = (List<Cuantia>) getExpedienteVista()
				.getCuantiaDataModel().getWrappedData();
		cuantias.remove(getExpedienteVista().getSelectedCuantia());

		CuantiaDataModel cuantiaDataModel = new CuantiaDataModel(cuantias);
		getExpedienteVista().setCuantiaDataModel(cuantiaDataModel);
	}

	public void deleteInculpado() {

		setFlagModificadoInc(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);
		getExpedienteVista().getInculpados().remove(
				getExpedienteVista().getSelectedInculpado());

	}

	public void deleteActividadProcesal() {

		setFlagModificadoActPro(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

		getExpedienteVista().getActividadProcesales().remove(
				getExpedienteVista().getSelectedActPro());

	}

	public void deleteProvision() {

		setFlagModificadoProv(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

		getExpedienteVista().getProvisiones().remove(
				getExpedienteVista().getSelectedProvision());

	}

	public void buscarAbogado(ActionEvent e) {
		
		logger.debug("Ingreso al Buscar Abogado..");
		List<Abogado> results = new ArrayList<Abogado>();
		List<AbogadoEstudio> abogadoEstudioBD = new ArrayList<AbogadoEstudio>();
		
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		Busqueda filtro2 = Busqueda.forClass(AbogadoEstudio.class);
		
		if(getEstudio() != null ){
			
			
			logger.debug("filtro "+ getEstudio().getIdEstudio()  +" abogado - estudio");
			
			filtro2.add(Restrictions.eq("estudio", getEstudio()));
			filtro2.add(Restrictions.like("estado", 'A'));
			
			try {
				
				abogadoEstudioBD = abogadoEstudioDAO.buscarDinamico(filtro2);
				logger.debug("hay "+ abogadoEstudioBD.size() +" estudios");
				
				List<Integer> idAbogados= new ArrayList<Integer>();
				
				for(AbogadoEstudio abogadoEstudio: abogadoEstudioBD){
					
					logger.debug("idabogado "+ abogadoEstudio.getAbogado().getIdAbogado());
					idAbogados.add(abogadoEstudio.getAbogado().getIdAbogado());
					
				}
		
				filtro.add(Restrictions.in("idAbogado",idAbogados));
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
			
		if(getAbogado().getRegistroca().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getRegistroca()   +" abogado - registro ca");
			
			filtro.add(Restrictions.eq("registroca", getAbogado().getRegistroca()));
			
		}

		if(getAbogado().getDni()!=0){
			

			logger.debug("filtro "+ getAbogado().getDni() +" abogado - dni");
			
			filtro.add(Restrictions.eq("dni", getAbogado().getDni()));
			
		}
		
		if(getAbogado().getNombres().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getNombres() +" abogado - nombres");
			filtro.add(Restrictions.like("nombres", "%"+getAbogado().getNombres()+"%").ignoreCase());
			
		}
		
		if(getAbogado().getApellidoPaterno().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getApellidoPaterno()  +" abogado - apellido paterno");
			filtro.add(Restrictions.like("apellidoPaterno", "%"+getAbogado().getApellidoPaterno()+"%").ignoreCase());
		}
		
		if(getAbogado().getApellidoMaterno().compareTo("")!=0){
			
			logger.debug("filtro "+getAbogado().getApellidoMaterno()+" abogado - apellido materno");
			filtro.add(Restrictions.like("apellidoMaterno", "%"+getAbogado().getApellidoMaterno()+"%"));
		}
		
		if(getAbogado().getTelefono().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getTelefono()  +" abogado - telefono");
			filtro.add(Restrictions.eq("telefono", getAbogado().getTelefono()));
		}
		
		if(getAbogado().getCorreo().compareTo("")!=0){
			
			
			logger.debug("filtro "+ getAbogado().getCorreo()  +" abogado - correo");
			filtro.add(Restrictions.like("correo", "%"+getAbogado().getCorreo()+"%").ignoreCase());
		}
		
		try {
			
			results= abogadoDAO.buscarDinamico(filtro);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		logger.debug("trajo.."+ results.size() +" abogados");
		
		abogadoDataModel = new AbogadoDataModel(results);

	}

	public void agregarHonorario(ActionEvent en) {

		if(getExpedienteVista().getHonorario().getAbogado() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Abogado Requerido", "Abogado Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(getExpedienteVista().getHonorario().getTipoHonorario().getDescripcion() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Honorario Requerido", "Honorario Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				if(getExpedienteVista().getHonorario().getCantidad() == 0){
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuotas Requerido", "Cuotas Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else{
					if(getExpedienteVista().getHonorario().getMoneda().getSimbolo() == ""){
						
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Moneda Requerido", "Moneda Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
					}else{
						
						if(getExpedienteVista().getHonorario().getMonto() == 0.0){
							
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Monto Requerido", "Monto Requerido");
							FacesContext.getCurrentInstance().addMessage(null, msg);
				
						}else{
							
							if(getExpedienteVista().getHonorario().getSituacionHonorario().getDescripcion() == ""){
								
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Situacion Requerido", "Situacion Requerido");
								FacesContext.getCurrentInstance().addMessage(null, msg);
				
							}else{
								
								
								setFlagModificadoHonor(true);
								getExpedienteVista().setDeshabilitarBotonGuardar(false);
								getExpedienteVista().setDeshabilitarBotonFinInst(true);

								
								for (TipoHonorario tipo : getTipoHonorarios()) {
									if (tipo.getDescripcion().compareTo(getExpedienteVista().getHonorario()
											.getTipoHonorario().getDescripcion())==0) {
										getExpedienteVista().getHonorario().setTipoHonorario(tipo);
										break;
									}
								}
								for (Moneda moneda : getMonedas()) {
									if (moneda.getSimbolo().compareTo(getExpedienteVista().getHonorario()
											.getMoneda().getSimbolo())==0) {
										getExpedienteVista().getHonorario().setMoneda(moneda);
										break;
									}

								}
								for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
									if (situacionHonorario.getDescripcion().compareTo(getExpedienteVista()
											.getHonorario().getSituacionHonorario().getDescripcion())==0) {
										getExpedienteVista().getHonorario().setSituacionHonorario(situacionHonorario);
										break;
									}

								}
								
								List<AbogadoEstudio> abogadoEstudios= new ArrayList<AbogadoEstudio>();
								GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
								Busqueda filtro = Busqueda.forClass(AbogadoEstudio.class);
								filtro.add(Restrictions.like("abogado",getExpedienteVista().getHonorario().getAbogado()));
								filtro.add(Restrictions.like("estado", 'A'));

								try {
									abogadoEstudios = abogadoEstudioDAO.buscarDinamico(filtro);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								if(abogadoEstudios != null ){
									if(abogadoEstudios.size()!=0){
										getExpedienteVista().getHonorario().setEstudio(abogadoEstudios.get(0).getEstudio().getNombre());
									}
								}
								
								//situacion pendiente
								if(getExpedienteVista().getHonorario().getSituacionHonorario().getIdSituacionHonorario()==1){
									
									double importe = getExpedienteVista().getHonorario().getMonto()
											/ getExpedienteVista().getHonorario().getCantidad().intValue();

									SituacionCuota situacionCuota = getSituacionCuotas().get(0);

									getExpedienteVista().getHonorario().setMontoPagado(0.0);
									getExpedienteVista().getHonorario().setCuotas(new ArrayList<Cuota>());

									Calendar cal = Calendar.getInstance();
									for (int i = 1; i <= getExpedienteVista().getHonorario().getCantidad()
											.intValue(); i++) {
										Cuota cuota = new Cuota();
										cuota.setNumero(i);
										cuota.setMoneda(getExpedienteVista().getHonorario().getMoneda().getSimbolo());
										cuota.setNroRecibo("000" + i);
										cuota.setImporte(importe);
										cal.add(Calendar.MONTH, 1);
										Date date = cal.getTime();
										cuota.setFechaPago(date);
										cuota.setSituacionCuota(new SituacionCuota());
										cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
										cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
										cuota.setFlagPendiente(true);
										getExpedienteVista().getHonorario().addCuota(cuota);

									}
									
									getExpedienteVista().getHonorario().setFlagPendiente(true);
									
								}else{
									
									getExpedienteVista().getHonorario().setMontoPagado(getExpedienteVista().getHonorario().getMonto());
									getExpedienteVista().getHonorario().setFlagPendiente(false);
									
								}
								
								getExpedienteVista().getHonorario().setNumero(getExpedienteVista().getHonorarios().size()+1);

								getExpedienteVista().getHonorarios().add(getExpedienteVista().getHonorario());

								getExpedienteVista().setHonorario(new Honorario());
								getExpedienteVista().getHonorario().setCantidad(0);
								getExpedienteVista().getHonorario().setMonto(0.0);
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		
	}

	public void agregarAnexo(ActionEvent en) {

		if(file == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cargar Archivo", "Cargar Archivo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(anexo.getTitulo() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Titulo Requerido", "Titulo Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				if(anexo.getComentario() == ""){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Comentario Requerido", "Comentario Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else{
					
					if(anexo.getFechaInicio() == null){
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Inicio Requerido", "Fecha Inicio Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
					}else{
						
						setFlagModificadoAnexo(true);
						getExpedienteVista().setDeshabilitarBotonGuardar(false);
						getExpedienteVista().setDeshabilitarBotonFinInst(true);

						byte[] fileBytes = getFile().getContents();
						//Blob b = Hibernate.createBlob(fileBytes);
						getAnexo().setBytes(fileBytes);
						getAnexo().setUbicacion(getFile().getFileName());
						getAnexo().setFormato(getFile().getFileName().substring(getFile().getFileName().lastIndexOf(".")+1).toUpperCase());
						
						
						if (getExpedienteVista().getAnexos() == null) {
							getExpedienteVista().setAnexos(new ArrayList<Anexo>());
						}

						getExpedienteVista().getAnexos().add(getAnexo());
						setAnexo(new Anexo());
						setFile(null);
					}
					
				}
			}
		}
		

	}

	public void agregarActividadProcesal(ActionEvent en) {

		
		if(getExpedienteVista().getActividadProcesal().getActividad().getNombre() == ""){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Actividad Requerido", "Actividad Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(	getExpedienteVista().getActividadProcesal().getEtapa().getNombre() == ""){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Etapa Requerido", "Etapa Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				
			}else{
				
				if(getExpedienteVista().getActividadProcesal().getPlazoLey() == ""){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Plazo Ley Requerido", "Plazo Ley Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
					
				}else{
					
					
					if(getExpedienteVista().getActividadProcesal().getFechaActividad() == null){
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Actividad Requerido", "Fecha Actividad Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
						
					}else{
						
						
						if(getExpedienteVista().getActividadProcesal().getFechaVencimiento() == null){
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Vencimiento Requerido", "Fecha Vencimiento Requerido");
							FacesContext.getCurrentInstance().addMessage(null, msg);
							
							
						}else{
							
							if(getExpedienteVista().getActividadProcesal().getSituacionActProc().getNombre() == ""){
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Situacion Actividad Requerido", "Situacion Actividad Requerido");
								FacesContext.getCurrentInstance().addMessage(null, msg);
								
								
							}else{
								
								
								if(getExpedienteVista().getActividadProcesal().getFechaActividad().compareTo(getExpedienteVista().getActividadProcesal().getFechaVencimiento()) > 0){
									
									FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Actividad mayor a Fecha Vencimiento", "Fecha Actividad mayor a Fecha Vencimiento");
									FacesContext.getCurrentInstance().addMessage(null, msg);
									
								}else{
									
									setFlagModificadoActPro(true);
									getExpedienteVista().setDeshabilitarBotonGuardar(false);
									getExpedienteVista().setDeshabilitarBotonFinInst(true);

									for (Actividad act : getActividades()) {
										if (act.getIdActividad() == getExpedienteVista()
												.getActividadProcesal().getActividad().getIdActividad()) {
											getExpedienteVista().getActividadProcesal().setActividad(act);
											break;
										}
									}
									for (Etapa et : getEtapas()) {
										if (et.getIdEtapa() == getExpedienteVista().getActividadProcesal()
												.getEtapa().getIdEtapa()) {
											getExpedienteVista().getActividadProcesal().setEtapa(et);
											break;
										}

									}
									for (SituacionActProc situacionActProc : getSituacionActProcesales()) {
										if (situacionActProc.getIdSituacionActProc() == getExpedienteVista()
												.getActividadProcesal().getSituacionActProc()
												.getIdSituacionActProc()) {
											getExpedienteVista().getActividadProcesal().setSituacionActProc(situacionActProc);
											break;
										}

									}

									getExpedienteVista().getActividadProcesales().add(getExpedienteVista().getActividadProcesal());
									getExpedienteVista().setActividadProcesal(new ActividadProcesal(new Etapa(), new SituacionActProc(),new Actividad()));
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}

	}

	public void agregarProvision(ActionEvent en) {

		if(getExpedienteVista().getProvision().getFechaSentencia() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Sentencia Requerido", "Fecha Sentencia Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(getExpedienteVista().getProvision().getFechaProvision() == null){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Provision Requerido", "Fecha Provision Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				if(getExpedienteVista().getProvision().getTipoProvision().getDescripcion() == ""){
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Tipo Provision Requerido", "Tipo Provision Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else{
					
					if(getExpedienteVista().getProvision().getMoneda().getSimbolo() == ""){
						
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Moneda Requerido", "Moneda Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
					}else{
						
						if(getExpedienteVista().getProvision().getMonto() == 0){
							
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Monto Requerido", "Monto Requerido");
							FacesContext.getCurrentInstance().addMessage(null, msg);
							
						}else{
							
							if(getExpedienteVista().getProvision().getDescripcion() == ""){
								
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Descripcion Requerido", "Descripcion Requerido");
								FacesContext.getCurrentInstance().addMessage(null, msg);
								
							}else{
								
								setFlagModificadoProv(true);
								getExpedienteVista().setDeshabilitarBotonGuardar(false);
								getExpedienteVista().setDeshabilitarBotonFinInst(true);

								for (TipoProvision provision : getTipoProvisiones()) {
									if (provision.getDescripcion().equals(
											getExpedienteVista().getProvision().getTipoProvision()
													.getDescripcion()))
										getExpedienteVista().getProvision().setTipoProvision(provision);
								}

								for (Moneda moneda : getMonedas()) {
									if (moneda.getSimbolo().equals(
											getExpedienteVista().getProvision().getMoneda()
													.getSimbolo()))
										getExpedienteVista().getProvision().setMoneda(moneda);
								}

								getExpedienteVista().getProvisiones().add(getExpedienteVista().getProvision());
								getExpedienteVista().setProvision(new Provision(new Moneda(), new TipoProvision()));
							
							}
						}
					}
				}
			}
		}

	}

	public void agregarAbogado(ActionEvent e2) {

		logger.info("Ingreso al Agregar Abogado..");
		
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		List<Abogado> abogadosBD = new ArrayList<Abogado>();
		
		
		if(getAbogado().getDni() == 0 ||
				getAbogado().getNombres() == "" ||
					getAbogado().getApellidoPaterno() == "" ||
						getAbogado().getApellidoMaterno() == "" ){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nro Documento, Nombres, Apellido Paterno, Apellido Materno", "Datos Requeridos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			Busqueda filtro = Busqueda.forClass(Abogado.class);
			filtro.add(Restrictions.eq("dni", getAbogado().getDni()));
			filtro.add(Restrictions.eq("nombres", getAbogado().getNombres()));
			filtro.add(Restrictions.eq("apellidoPaterno", getAbogado().getApellidoPaterno()));
			filtro.add(Restrictions.eq("apellidoMaterno", getAbogado().getApellidoMaterno()));
			
			try {
				abogadosBD = abogadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Abogado abogadobd= new Abogado();
			
			if(abogadosBD.size() == 0){
				
				try {
					
					getAbogado().setNombreCompleto(getAbogado().getNombres() +" "+
												   getAbogado().getApellidoPaterno()+" "+
												   getAbogado().getApellidoMaterno());
					
					abogadobd = abogadoDAO.insertar(getAbogado());
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Abogado agregado", "Abogado agregado");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Abogado Existente", "Abogado Existente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			

			List<Abogado> abogados= new ArrayList<Abogado>();
			abogados.add(abogadobd);
			abogadoDataModel = new AbogadoDataModel(abogados);
		}
		
		
		

	}

	public void buscarPersona(ActionEvent e) {

		logger.debug("entro al buscar persona");
		
		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Persona.class);
		
		if(getPersona().getClase().getIdClase()!= 0){
			
			logger.debug("filtro "+ getPersona().getClase().getIdClase()  +" persona - clase");
			filtro.add(Restrictions.eq("clase.idClase", getPersona().getClase().getIdClase()));
		}
		
		if(getPersona().getTipoDocumento().getIdTipoDocumento()!= 0){
			
			logger.debug("filtro "+ getPersona().getTipoDocumento().getIdTipoDocumento() +" persona - tipo documento");
			filtro.add(Restrictions.eq("tipoDocumento.idTipoDocumento", getPersona().getTipoDocumento().getIdTipoDocumento()));
		}
		
		if(getPersona().getNumeroDocumento()!= 0){
			
			logger.debug("filtro "+ getPersona().getNumeroDocumento()  +" persona - numero documento");
			filtro.add(Restrictions.eq("numeroDocumento", getPersona().getNumeroDocumento()));
		}
		
		if(getPersona().getCodCliente()!= 0){
			
			logger.debug("filtro "+ getPersona().getCodCliente()  +" persona - cod cliente");
			filtro.add(Restrictions.eq("codCliente", getPersona().getCodCliente()));
		}
		
		if(getPersona().getNombres().compareTo("")!=0){
			
			logger.debug("filtro "+ getPersona().getNombres() +" persona - nombres");
			filtro.add(Restrictions.like("nombres","%"+getPersona().getNombres()+"%").ignoreCase());
		}
		
		if(getPersona().getApellidoPaterno().compareTo("")!=0){
			
			logger.debug("filtro "+ getPersona().getApellidoPaterno()  +" persona - apellido paterno");
			filtro.add(Restrictions.like("apellidoPaterno", "%"+getPersona().getApellidoPaterno()+"%").ignoreCase());
		}
		
		if(getPersona().getApellidoMaterno().compareTo("")!=0){
			
			logger.debug("filtro "+ getPersona().getApellidoMaterno()  +" persona - apellido materno");
			filtro.add(Restrictions.like("apellidoMaterno",  "%"+getPersona().getApellidoMaterno()+"%").ignoreCase());
		}
		
		try {
			personas = personaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		logger.debug("trajo .."+ personas.size());

		personaDataModelBusq = new PersonaDataModel(personas);

	}

	public void buscarOrganos(ActionEvent actionEvent) {


		logger.debug("entro al buscar organos");
		
		List<Organo> organos = new ArrayList<Organo>();
		
		//organos = expedienteService.buscarOrganos(getOrgano());
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		if(getOrgano().getEntidad().getIdEntidad()!= 0){
			
			logger.debug("filtro "+ getOrgano().getEntidad().getIdEntidad()  +" organo - entidad");			
			filtro.add(Restrictions.eq("entidad.idEntidad", getOrgano().getEntidad().getIdEntidad()));
		}
		
		if(getOrgano().getNombre().compareTo("")!=0){
			
			logger.debug("filtro "+ getOrgano().getNombre() +" organo - nombre");
			filtro.add(Restrictions.like("nombre", "%"+getOrgano().getNombre()+"%").ignoreCase());
		}
		
		if(getOrgano().getUbigeo()!= null){

			logger.debug("filtro "+getOrgano().getUbigeo().getCodDist()+" organo - territorio");
			filtro.add(Restrictions.eq("ubigeo.codDist", getOrgano().getUbigeo().getCodDist()));
			
		}
		
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.debug("trajo .."+ organos.size());
		
		organoDataModel = new OrganoDataModel(organos);

	}

	public void agregarOrgano(ActionEvent e2) {

		List<Organo> organos = new ArrayList<Organo>();
		List<Territorio> territorios = new ArrayList<Territorio>();

		//organos = expedienteService.buscarOrganos(getOrgano());
	
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		
		if (getOrgano().getEntidad().getIdEntidad() == 0
				|| getOrgano().getNombre() == ""
				|| getOrgano().getUbigeo().getDescripcionDistrito() == "") {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Entidad, Organo, Distrito", "Datos Requeridos: Entidad, Organo, Distrito");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			filtro.add(Restrictions.eq("entidad.idEntidad", getOrgano().getEntidad().getIdEntidad()));
			filtro.add(Restrictions.eq("nombre", getOrgano().getNombre()));
			filtro.add(Restrictions.eq("ubigeo.codDist", getOrgano().getUbigeo().getCodDist()));
			
			try {
				organos = organoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			Organo organobd=new Organo();
			
			if(organos.size() == 0){
				
				try {
					
					organobd= organoDAO.insertar(getOrgano());
					
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Organo Agregado", "Organo Agregado"));
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Organo Existente", "Organo Existente"));
			}
		
			List<Organo> organos2= new ArrayList<Organo>();
			organos2.add(organobd);
			organoDataModel = new OrganoDataModel(organos2);
			
		}
		

	}
	
	public void seleccionarPersona() {

		getSelectPersona().setNombreCompletoMayuscula(
				getSelectPersona().getNombres().toUpperCase()+ " " + 
				getSelectPersona().getApellidoPaterno().toUpperCase() + " "+ 
				getSelectPersona().getApellidoMaterno().toUpperCase());
		
		getExpedienteVista().getInvolucrado().setPersona(getSelectPersona());
		

	}
	
	public void seleccionarAbogado() {

		getSelectedAbogado().setNombreCompletoMayuscula(getSelectedAbogado().getNombres().toUpperCase()
				+ " " + getSelectedAbogado().getApellidoPaterno().toUpperCase() + " "
				+ getSelectedAbogado().getApellidoMaterno().toUpperCase());
		
		getExpedienteVista().getHonorario().setAbogado(getSelectedAbogado());

	}
	
	public void seleccionarInvolucrado() {

		getSelectInvolucrado().setNombreCompletoMayuscula(
						getSelectInvolucrado().getNombres().toUpperCase()+ " " + 
						getSelectInvolucrado().getApellidoPaterno().toUpperCase() + " "+ 
						getSelectInvolucrado().getApellidoMaterno().toUpperCase());
		
		getExpedienteVista().getInculpado().setPersona(getSelectInvolucrado());
		

	}
	
	public void seleccionarOrgano() {

		String descripcion = getSelectedOrgano().getNombre().toUpperCase() + " ("
				+ getSelectedOrgano().getUbigeo().getDistrito().toUpperCase() + ", "
				+ getSelectedOrgano().getUbigeo().getProvincia().toUpperCase()
				+ ", "
				+ getSelectedOrgano().getUbigeo().getDepartamento().toUpperCase()
				+ ")";

		getSelectedOrgano().setNombreDetallado(descripcion);
			
		getExpedienteVista().setOrgano1(getSelectedOrgano());

	}
	
	public void limpiarOrgano(ActionEvent e) {

		setOrgano(new Organo());
		getOrgano().setEntidad(new Entidad());
		getOrgano().setUbigeo(new Ubigeo());
		
		organoDataModel = new OrganoDataModel(new ArrayList<Organo>());
	}

	public void agregarCuantia(ActionEvent e) {

		if(getExpedienteVista().getCuantia().getMoneda().getSimbolo() == ""){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Moneda Requerido", "Moneda Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(getExpedienteVista().getCuantia().getPretendido() == 0.0){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Pretendido Requerido", "Pretendido Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				for (Moneda m : getMonedas()) {
					if (m.getSimbolo().equals(
							getExpedienteVista().getCuantia().getMoneda().getSimbolo())) {
						getExpedienteVista().getCuantia().setMoneda(m);
						break;
					}

				}

				setFlagModificadoCua(true);
				List<Cuantia> cuantias;
				if (getExpedienteVista().getCuantiaDataModel() == null) {
					cuantias = new ArrayList<Cuantia>();
				} else {
					cuantias = (List<Cuantia>) getExpedienteVista()
							.getCuantiaDataModel().getWrappedData();
				}

				cuantias.add(getExpedienteVista().getCuantia());

				CuantiaDataModel cuantiaDataModel = new CuantiaDataModel(cuantias);

				getExpedienteVista().setCuantiaDataModel(cuantiaDataModel);

				getExpedienteVista().setCuantia(new Cuantia());
			}
			
		}
		
		
	

	}

	public void agregarInvolucrado(ActionEvent e) {
		
		
		
		if(getExpedienteVista().getInvolucrado().getPersona() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre Requerido", "Nombre Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(getExpedienteVista().getInvolucrado().getRolInvolucrado().getNombre() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Rol Requerido", "Abogado Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				setFlagModificadoInv(true);
				getExpedienteVista().setDeshabilitarBotonGuardar(false);
				getExpedienteVista().setDeshabilitarBotonFinInst(true);

				for (RolInvolucrado rol : getRolInvolucrados()) {
					if (rol.getNombre() == getExpedienteVista().getInvolucrado()
							.getRolInvolucrado().getNombre()) {
						getExpedienteVista().getInvolucrado().setRolInvolucrado(rol);
						break;
					}
				}

				for (TipoInvolucrado tipo : getTipoInvolucrados()) {
					if (tipo.getNombre() == getExpedienteVista().getInvolucrado()
							.getTipoInvolucrado().getNombre()) {
						getExpedienteVista().getInvolucrado().setTipoInvolucrado(tipo);
						break;
					}
				}

				List<Involucrado> involucrados;
				if (getExpedienteVista().getInvolucradoDataModel() == null) {
					involucrados = new ArrayList<Involucrado>();
				} else {
					involucrados = (List<Involucrado>) getExpedienteVista()
							.getInvolucradoDataModel().getWrappedData();
				}

				involucrados.add(getExpedienteVista().getInvolucrado());
				InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(
						involucrados);
				getExpedienteVista().setInvolucradoDataModel(involucradoDataModel);

				getExpedienteVista().setInvolucrado(new Involucrado());
				
				
			}
			
		}


	}

	public void agregarInculpado(ActionEvent e) {

		if(getExpedienteVista().getInculpado().getPersona() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Inculpado Requerido", "Inculpado Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(	getExpedienteVista().getInculpado().getFecha()  == null){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Requerido", "Fecha Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				
			}else{
				
				if(getExpedienteVista().getInculpado().getMoneda().getSimbolo() == ""){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Moneda Requerido", "Materia Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
					
				}else{
					
					if(getExpedienteVista().getInculpado().getMonto() == 0.0){
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Monto Requerido", "Monto Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
					}else{
						
						if(getExpedienteVista().getInculpado().getNrocupon() == 0){

							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Numero Cupon Requerido", "Numero Cupon Requerido");
							FacesContext.getCurrentInstance().addMessage(null, msg);
							
							
						}else{
							
							if(getExpedienteVista().getInculpado().getSituacionInculpado().getNombre() == ""){
								
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Situacion Requerido", "Situacion Requerido");
								FacesContext.getCurrentInstance().addMessage(null, msg);
								
							}else{
								
								setFlagModificadoInc(true);
								getExpedienteVista().setDeshabilitarBotonGuardar(false);
								getExpedienteVista().setDeshabilitarBotonFinInst(true);

								for (Moneda moneda : getMonedas()) {
									if (moneda.getSimbolo().equals(
											getExpedienteVista().getInculpado().getMoneda()
													.getSimbolo()))
										getExpedienteVista().getInculpado().setMoneda(moneda);
								}

								for (SituacionInculpado situac : getSituacionInculpados()) {
									if (situac.getNombre().equals(
											getExpedienteVista().getInculpado().getSituacionInculpado()
													.getNombre()))
										getExpedienteVista().getInculpado().setSituacionInculpado(
												situac);
								}

								if (getExpedienteVista().getInculpados() == null) {

									getExpedienteVista().setInculpados(new ArrayList<Inculpado>());
								}

								getExpedienteVista().getInculpados().add(
										getExpedienteVista().getInculpado());

								getExpedienteVista().setInculpado(new Inculpado());
								
							}
						}
						
					}
				}
				
			}
				
		}

	}

	public void agregarPersona(ActionEvent e) {

		logger.info("Ingreso a agregarDetallePersona..");

		if(getPersona().getClase().getIdClase()==0 || 
					getPersona().getTipoDocumento().getIdTipoDocumento()==0 ||
						getPersona().getNumeroDocumento() ==0 ||
							getPersona().getNombres() == "" ||
								getPersona().getApellidoMaterno() == "" ||
									getPersona().getApellidoPaterno() == ""){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Clase, Tipo Doc, Nro Documento, Nombre, Apellido Paterno, Apellido Materno", "Datos Requeridos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			List<Persona> personas= new ArrayList<Persona>();
			GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
			List<Persona> personaBD = new ArrayList<Persona>();
			
			Busqueda filtro = Busqueda.forClass(Persona.class);
			filtro.add(Restrictions.eq("clase.idClase", getPersona().getClase().getIdClase()));
			
			if(getPersona().getCodCliente() != 0)
				filtro.add(Restrictions.eq("codCliente", getPersona().getCodCliente()));
			
			filtro.add(Restrictions.eq("tipoDocumento.idTipoDocumento", getPersona().getTipoDocumento().getIdTipoDocumento()));
			filtro.add(Restrictions.eq("numeroDocumento", getPersona().getNumeroDocumento()));
			filtro.add(Restrictions.eq("nombres", getPersona().getNombres()));
			filtro.add(Restrictions.eq("apellidoPaterno", getPersona().getApellidoPaterno()));
			filtro.add(Restrictions.eq("apellidoMaterno", getPersona().getApellidoMaterno()));
			
			try {
				personas = personaDAO.buscarDinamico(filtro);
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			Persona personabd=  new Persona();
			
			if(personas.size() == 0){
				
				try {
					getPersona().setNombreCompleto(getPersona().getNombres()+" "+
												    getPersona().getApellidoPaterno()+" "+
												    getPersona().getApellidoMaterno());
					personabd = personaDAO.insertar(getPersona());
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Persona agregada", "Persona agregada");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Persona Existente", "Persona Existente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			List<Persona> personas2= new ArrayList<Persona>();
			personas2.add(personabd);
			personaDataModelBusq = new PersonaDataModel(personas2);
			
		}
		

	}

	public String agregarDetalleInculpado(ActionEvent e) {

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Inculpado Agregado", "Inculpado Agregado");

		FacesContext.getCurrentInstance().addMessage(null, msg);

		List<Persona> personas = new ArrayList<Persona>();
		personaDataModelBusq = new PersonaDataModel(personas);

		return null;

	}
	
	public void limpiarAbogado(ActionEvent e) {

		setAbogado(new Abogado());
		getAbogado().setDni(null);
		
		setEstudio(new Estudio());
		
		abogadoDataModel = new AbogadoDataModel(new ArrayList<Abogado>());
	}
	
	public void limpiarPersona(ActionEvent e) {

		setPersona(new Persona());
		getPersona().setClase(new Clase());
		getPersona().setCodCliente(null);
		getPersona().setTipoDocumento(new TipoDocumento());
		getPersona().setNumeroDocumento(null);
		
		personaDataModelBusq = new PersonaDataModel(new ArrayList<Persona>());
	}

	public void limpiarAnexo(ActionEvent e) {

		setAnexo(new Anexo());

	}
	
	public void limpiarActividadProcesal(ActionEvent e) {

		getExpedienteVista().setActividadProcesal(new ActividadProcesal(new Etapa(), new SituacionActProc(),new Actividad()));
		
	}

	public void limpiarProvision(ActionEvent e) {

		getExpedienteVista().setProvision(new Provision(new Moneda(), new TipoProvision()));
		
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
		getExpedienteVista()
				.setInvolucradoDataModel(new InvolucradoDataModel());

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
	public void actualizarExpedienteActual(Expediente expediente,ExpedienteVista expedienteVista) {

		if (isFlagGuardarInstancia()) {

			GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			try {
				Instancia instanciabd = instanciaDAO.buscarById(Instancia.class, expedienteVista.getInstancia());
				expediente.setInstancia(instanciabd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			try {
				Via viabd = viaDAO.buscarById(Via.class, expedienteVista.getVia());
				expediente.setVia(viabd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		if (isFlagGuardarOficina()) {
			expediente.setOficina(expedienteVista.getOficina());
		}

		if (isFlagGuardarRecurrencia()) {
			expediente.setRecurrencia(expedienteVista.getRecurrencia());
		}

		if (isFlagGuardarOrgano1()) {
			expediente.setOrgano(expedienteVista.getOrgano1());
		}

		if (isFlagGuardarSecretario()) {
			expediente.setSecretario(expedienteVista.getSecretario());
		}

		if (isFlagModificadoHonor()) {

			List<Honorario> honorarios = expedienteVista.getHonorarios();
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
		}

		if (isFlagModificadoInv()) {

			List<Involucrado> involucrados = (List<Involucrado>) expedienteVista
					.getInvolucradoDataModel().getWrappedData();

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

		}
		
		if (isFlagModificadoCua()) {
			
			List<Cuantia> cuantias = (List<Cuantia>) expedienteVista.
					getCuantiaDataModel().getWrappedData();
			
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
		}

		if (expedienteVista.getProceso() != 2 && isFlagModificadoInc()) {

			List<Inculpado> inculpados = expedienteVista.getInculpados();
			expediente.setInculpados(new ArrayList<Inculpado>());

			for (Inculpado inculpado : inculpados) {

				if (inculpado != null) {

					for (Moneda moneda : getMonedas()) {
						if (moneda.getSimbolo().equals(
								inculpado.getMoneda().getSimbolo())) {
							inculpado.setMoneda(moneda);
							break;
						}

					}

					for (SituacionInculpado s : getSituacionInculpados()) {
						if (s.getNombre().equals(
								inculpado.getSituacionInculpado().getNombre())) {
							inculpado.setSituacionInculpado(s);
							break;
						}

					}

					expediente.addInculpado(inculpado);
				}
			}
		}

		if (isFlagGuardarMoneda()) {
			GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");

			try {
				Moneda monedabd = monedaDAO.buscarById(Moneda.class,
						expedienteVista.getMoneda());
				expediente.setMoneda(monedabd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (isFlagGuardarMonto()) {
			expediente.setMontoCautelar(expedienteVista.getMontoCautelar());
		}

		if (isFlagGuardarTipoMediaCautelar()) {
			GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			try {
				TipoCautelar tipoCautelarbd = tipoCautelarDAO.buscarById(
						TipoCautelar.class, expedienteVista.getTipoCautelar());
				expediente.setTipoCautelar(tipoCautelarbd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (isFlagGuardarDescripcionCautelar()) {
			expediente.setDescripcionCautelar(expedienteVista
					.getDescripcionCautelar());

		}

		if (isFlagGuardarContraCautela()) {
			GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			try {
				ContraCautela contraCautelabd = contraCautelaDAO
						.buscarById(ContraCautela.class,
								expedienteVista.getContraCautela());
				expediente.setContraCautela(contraCautelabd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (isFlagGuardarImporteCautela()) {
			expediente.setImporteCautelar(expedienteVista.getImporteCautelar());
		}

		if (isFlagGuardarEstado()) {

			GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			try {
				EstadoCautelar estadoCautelarbd = estadoCautelarDAO.buscarById(
						EstadoCautelar.class,
						expedienteVista.getEstadoCautelar());
				expediente.setEstadoCautelar(estadoCautelarbd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (isFlagModificadoProv()) {

			List<Provision> provisions = expedienteVista.getProvisiones();
			expediente.setProvisions(new ArrayList<Provision>());
			for (Provision provision : provisions) {
				if (provision != null) {

					for (TipoProvision tipoProvision : getTipoProvisiones()) {
						if (tipoProvision.getDescripcion().equals(
								provision.getTipoProvision().getDescripcion())) {
							provision.setTipoProvision(tipoProvision);
							break;
						}
					}

					for (Moneda moneda : getMonedas()) {
						if (moneda.getSimbolo().equals(
								provision.getMoneda().getSimbolo())) {
							provision.setMoneda(moneda);
							break;
						}

					}

					expediente.addProvision(provision);
				}
			}
		}

		if(isFlagGuardarResumen()){
			
			List<Resumen> resumens = expedienteVista.getResumens();
			expediente.setResumens(new ArrayList<Resumen>());
			for(Resumen res:resumens){
				
				if(res!=null){
					expediente.addResumen(res);
				}
				
			}
			
		}
		
		if (isFlagModificadoActPro()) {

			List<ActividadProcesal> actividadProcesals = expedienteVista
					.getActividadProcesales();
			expediente
					.setActividadProcesals(new ArrayList<ActividadProcesal>());
			for (ActividadProcesal actividadProcesal : actividadProcesals) {
				if (actividadProcesal != null) {

					for (Actividad actividad : getActividades()) {
						if (actividad.getNombre().equals(
								actividadProcesal.getActividad().getNombre())) {
							actividadProcesal.setActividad(actividad);
							break;
						}
					}

					for (Etapa etapa : getEtapas()) {
						if (etapa.getNombre().equals(
								actividadProcesal.getEtapa().getNombre())) {
							actividadProcesal.setEtapa(etapa);
							break;
						}
					}

					for (SituacionActProc situacionActProc : getSituacionActProcesales()) {
						if (situacionActProc.getNombre().equals(
								actividadProcesal.getSituacionActProc()
										.getNombre())) {
							actividadProcesal
									.setSituacionActProc(situacionActProc);
							break;
						}
					}

					expediente.addActividadProcesal(actividadProcesal);
				}
			}
		}

		if (isFlagModificadoAnexo()) {

			List<Anexo> anexos = expedienteVista.getAnexos();
			expediente.setAnexos(new ArrayList<Anexo>());
			
			if(anexos != null){
				if(anexos.size() != 0){
					
					File fichUbicacion;
					String ubicacion="";
					
					if(expediente.getInstancia() == null){
						
						ubicacion= Util.getMessage("ruta_documento") 
								+ File.separator + expediente.getNumeroExpediente() 
								+ File.separator + "sin-instancia" ;
						
					}else{
						
						ubicacion= Util.getMessage("ruta_documento") 
								+ File.separator + expediente.getNumeroExpediente() 
								+ File.separator + expediente.getInstancia().getNombre();
					}
					
					fichUbicacion= new File(ubicacion);
					fichUbicacion.mkdirs();
					
					for (Anexo anexo : anexos)
						if (anexo != null){
							
							anexo.setUbicacion(ubicacion + File.separator + anexo.getUbicacion());
							
							byte b[]= anexo.getBytes();
							File fichSalida= new File(anexo.getUbicacion());
							
							try {
								FileOutputStream canalSalida = new FileOutputStream( fichSalida );
								canalSalida.write(b);
								canalSalida.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							expediente.addAnexo(anexo);
						}
					
				}
			}
		
		}

		if (isFlagGuardarRiesgo()) {
			GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			try {

				Riesgo riesgobd = riesgoDAO.buscarById(Riesgo.class,
						expedienteVista.getRiesgo());
				expediente.setRiesgo(riesgobd);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// @SuppressWarnings("unchecked")
	public void actualizarExpedienteListas(Expediente expediente,
			ExpedienteVista expedienteVista) {

		List<Honorario> honorarios = expedienteVista.getHonorarios();
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
				
				honorario.setIdHonorario(0);
				expediente.addHonorario(honorario);
			}
		}

		List<Involucrado> involucrados = (List<Involucrado>) expedienteVista.getInvolucradoDataModel().getWrappedData();

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
				
				involucrado.setIdInvolucrado(0);
				expediente.addInvolucrado(involucrado);
			}
		}

		List<Cuantia> cuantias = (List<Cuantia>) expedienteVista.getCuantiaDataModel()
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

				cuantia.setIdCuantia(0);
				expediente.addCuantia(cuantia);
			}
		}
		
		List<Inculpado> inculpados = expedienteVista.getInculpados();
		expediente.setInculpados(new ArrayList<Inculpado>());

		for (Inculpado inculpado : inculpados) {

			if (inculpado != null) {

				for (Moneda moneda : getMonedas()) {
					if (moneda.getSimbolo().equals(
							inculpado.getMoneda().getSimbolo())) {
						inculpado.setMoneda(moneda);
						break;
					}

				}

				for (SituacionInculpado s : getSituacionInculpados()) {
					if (s.getNombre().equals(
							inculpado.getSituacionInculpado().getNombre())) {
						inculpado.setSituacionInculpado(s);
						break;
					}

				}

				inculpado.setIdInculpado(0);
				expediente.addInculpado(inculpado);
			}
		}

		List<Provision> provisions = expedienteVista.getProvisiones();
		expediente.setProvisions(new ArrayList<Provision>());
		for (Provision provision : provisions) {
			if (provision != null) {

				for (TipoProvision tipoProvision : getTipoProvisiones()) {
					if (tipoProvision.getDescripcion().equals(
							provision.getTipoProvision().getDescripcion())) {
						provision.setTipoProvision(tipoProvision);
						break;
					}
				}

				for (Moneda moneda : getMonedas()) {
					if (moneda.getSimbolo().equals(
							provision.getMoneda().getSimbolo())) {
						provision.setMoneda(moneda);
						break;
					}

				}

				provision.setIdProvision(0);
				expediente.addProvision(provision);
			}
		}

		List<ActividadProcesal> actividadProcesals = expedienteVista.getActividadProcesales();
		expediente.setActividadProcesals(new ArrayList<ActividadProcesal>());
		for (ActividadProcesal actividadProcesal : actividadProcesals) {
			if (actividadProcesal != null) {

				for (Actividad actividad : getActividades()) {
					if (actividad.getNombre().equals(
							actividadProcesal.getActividad().getNombre())) {
						actividadProcesal.setActividad(actividad);
						break;
					}
				}

				for (Etapa etapa : getEtapas()) {
					if (etapa.getNombre().equals(
							actividadProcesal.getEtapa().getNombre())) {
						actividadProcesal.setEtapa(etapa);
						break;
					}
				}

				for (SituacionActProc situacionActProc : getSituacionActProcesales()) {
					if (situacionActProc.getNombre()
							.equals(actividadProcesal.getSituacionActProc()
									.getNombre())) {
						actividadProcesal.setSituacionActProc(situacionActProc);
						break;
					}
				}

				actividadProcesal.setIdActividadProcesal(0);
				expediente.addActividadProcesal(actividadProcesal);
			}
		}

		List<Anexo> anexos = expedienteVista.getAnexos();
		expediente.setAnexos(new ArrayList<Anexo>());
		for (Anexo anexo : anexos){
			if (anexo != null){
				anexo.setIdDocumento(0);
				expediente.addAnexo(anexo);
			}
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
			
			if(oficina.getUbigeo() != null){
				
				String texto = oficina.getCodigo() + " " + 
							   oficina.getNombre().toUpperCase() + 
						       " (" + oficina.getUbigeo().getDepartamento().toUpperCase() + ")";
				
				if (texto.contains(query.toUpperCase())) {
					oficina.setNombreDetallado(texto);
					results.add(oficina);
				}
				
			}
			
		}

		return results;
	}

	public List<Estudio> completeEstudio(String query) {

		List<Estudio> estudios = new ArrayList<Estudio>();
		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Estudio.class);
		
		try {
			
			estudios = estudioDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Estudio> results = new ArrayList<Estudio>();

		for (Estudio est : estudios) {
			if (est.getNombre().toUpperCase().contains(query.toUpperCase())) {
				results.add(est);
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
					+ organo.getUbigeo().getDistrito().toUpperCase() + ", "
					+ organo.getUbigeo().getProvincia().toUpperCase()
					+ ", "
					+ organo.getUbigeo().getDepartamento().toUpperCase()
					+ ")";
			
			if (descripcion.toUpperCase().contains(query.toUpperCase())) {

				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}

		return results;
	}

	public List<Ubigeo> completeDistrito(String query) {
		List<Ubigeo> results = new ArrayList<Ubigeo>();

		List<Ubigeo> ubigeos = new ArrayList<Ubigeo>();
		GenericDao<Ubigeo, Object> ubigeoDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Ubigeo.class);
		
		try {
			ubigeos = ubigeoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Ubigeo ubig : ubigeos) {
			String texto = ubig.getDistrito() + "," + ubig.getProvincia()+ "," + ubig.getDepartamento();

			if (texto.toUpperCase().contains(query.toUpperCase())) {

				ubig.setDescripcionDistrito(texto);
				results.add(ubig);
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
			for (TipoHonorario t : tipoHonorarios)
				tipoHonorariosString.add(t.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		monedasString = new ArrayList<String>();
		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Moneda.class);
		try {
			monedas = monedaDAO.buscarDinamico(filtro);
			for (Moneda m : monedas)
				monedasString.add(m.getSimbolo());
		} catch (Exception e) {
			e.printStackTrace();
		}

		situacionHonorariosString = new ArrayList<String>();
		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionHonorario.class);
		try {
			situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro);
			for (SituacionHonorario s : situacionHonorarios)
				situacionHonorariosString.add(s.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		situacionCuotasString = new ArrayList<String>();
		GenericDao<SituacionCuota, Object> situacionCuotasDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionCuota.class);
		try {
			situacionCuotas = situacionCuotasDAO.buscarDinamico(filtro);
			for (SituacionCuota s : situacionCuotas)
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
			for (RolInvolucrado r : rolInvolucrados)
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
			for (TipoInvolucrado t : tipoInvolucrados)
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
			for (SituacionInculpado s : situacionInculpados)
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

		tipoProvisionesString = new ArrayList<String>();
		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoProvision.class);
		try {
			tipoProvisiones = tipoProvisionDAO.buscarDinamico(filtro);
			for (TipoProvision t : tipoProvisiones)
				tipoProvisionesString.add(t.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		actividadesString = new ArrayList<String>();
		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Actividad.class);
		try {
			actividades = actividadDAO.buscarDinamico(filtro);
			for (Actividad a : actividades)
				actividadesString.add(a.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}

		etapasString = new ArrayList<String>();
		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Etapa.class);
		try {
			etapas = etapaDAO.buscarDinamico(filtro);
			for (Etapa et : etapas)
				etapasString.add(et.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}

		situacionActProcesalesString = new ArrayList<String>();
		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(SituacionActProc.class);
		try {
			situacionActProcesales = situacionActProcDAO.buscarDinamico(filtro);
			for (SituacionActProc st : situacionActProcesales)
				situacionActProcesalesString.add(st.getNombre());
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

		Honorario honorarioModif = ((Honorario) event.getObject());
		
		for (Honorario honorario : getExpedienteVista().getHonorarios()) {
			if (honorarioModif.getNumero() == honorario.getNumero()) {
				
				//situacion pendiente
				if(honorario.getSituacionHonorario().getIdSituacionHonorario()==1){
					
					double importe = honorarioModif.getMonto() / honorarioModif.getCantidad().intValue();

					importe = Math.rint(importe*100)/100;

					SituacionCuota situacionCuota = getSituacionCuotas().get(0);

					//honorario.setMontoPagado(0.0);
					honorario.setCuotas(new ArrayList<Cuota>());

					Calendar cal = Calendar.getInstance();
					
					for (int i = 1; i <= honorarioModif.getCantidad().intValue(); i++) {
						Cuota cuota = new Cuota();
						cuota.setNumero(i);
						cuota.setMoneda(honorarioModif.getMoneda().getSimbolo());
						cuota.setNroRecibo("000" + i);
						cuota.setImporte(importe);
						cal.add(Calendar.MONTH, 1);
						Date date = cal.getTime();
						cuota.setFechaPago(date);
						
						cuota.setSituacionCuota(new SituacionCuota());
						cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
						cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
						cuota.setFlagPendiente(true);
						
						honorario.addCuota(cuota);

					}
					
				}
				
			}
		}
		
		FacesMessage msg = new FacesMessage("Honorario Editado",
				"Honorario Editado al modificar algunos campos");
		FacesContext.getCurrentInstance().addMessage("growl", msg);
		
		setFlagModificadoHonor(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

	}

	public void editInv(RowEditEvent event) {

		setFlagModificadoInv(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

	}

	public void editCua(RowEditEvent event) {

		setFlagModificadoCua(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

	}
	
	public void editInc(RowEditEvent event) {

		setFlagModificadoInc(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

	}

	public void editProv(RowEditEvent event) {

		setFlagModificadoProv(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

	}

	public void editActPro(RowEditEvent event) {

		setFlagModificadoActPro(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

	}

	public void editAnexo(RowEditEvent event) {

		setFlagModificadoAnexo(true);
		getExpedienteVista().setDeshabilitarBotonGuardar(false);
		getExpedienteVista().setDeshabilitarBotonFinInst(true);

	}

	public void editDetHonor(RowEditEvent event) {

		Cuota cuotaModif = ((Cuota) event.getObject());
		
		double importe = cuotaModif.getImporte();
		double importeRestante = cuotaModif.getHonorario().getMonto() - importe;
		
		double importeNuevo=0.0;
		
		if(cuotaModif.getHonorario().getCantidad().intValue()>1){
			importeNuevo = importeRestante / (cuotaModif.getHonorario().getCantidad().intValue() - 1);
			importeNuevo = Math.rint(importeNuevo*100)/100;
			
		}else{
			
			importeNuevo = importe;
		}
		
		
		for(Honorario honorario: getExpedienteVista().getHonorarios()){
			
			if (cuotaModif.getHonorario().getNumero() == honorario.getNumero()) {
				
				
				for(Cuota cuota:cuotas){
					
					if(cuota.getNumero() == cuotaModif.getNumero() ){
						
						if (cuotaModif.getSituacionCuota().getDescripcion().equals("Pagado")
								|| cuotaModif.getSituacionCuota().getDescripcion().equals("Baja")) {
							
							//honorario.setMonto(importeRestante);
							honorario.setMontoPagado(honorario.getMontoPagado()+ importe);
							
							
							if(honorario.getMonto().compareTo(honorario.getMontoPagado())==0){
								
								SituacionHonorario situacionHonorario = getSituacionHonorarios().get(1);
								honorario.setSituacionHonorario(situacionHonorario);
								honorario.setFlagPendiente(false);
							}
							
							
							cuota.setFlagPendiente(false);
							
						}
						
						cuota.setImporte(importe);
						
					}else{
						
						cuota.setImporte(importeNuevo);
					}
					
				}
				
				honorario.setCuotas(cuotas);
				break;
			}
			
		}
		
		FacesMessage msg = new FacesMessage("Cuota Editada",
				"Cuota Editada");
		FacesContext.getCurrentInstance().addMessage("growl", msg);
		
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

	public String reset() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "consultaExpediente.xhtml?faces-redirect=true";
	}

	public ActSeguimientoExpedienteMB() {

		logger.debug("Inicializando Valores..");
		inicializarValores();
		logger.debug("Llenar hitos...");
		llenarHitos();
		logger.debug("Cargando combos...");
		cargarCombos();

	}

	private void inicializarValores() {

		Calendar cal = Calendar.getInstance();
		
		setFlagGuardarInstancia(false);
		setFlagGuardarOficina(false);
		setFlagGuardarOrgano1(false);
		setFlagGuardarRecurrencia(false);
		setFlagGuardarSecretario(false);

		setFlagModificadoHonor(false);
		setFlagModificadoInv(false);
		setFlagModificadoInc(false);
		setFlagModificadoProv(false);
		setFlagModificadoActPro(false);
		setFlagModificadoAnexo(false);

		organo = new Organo();
		organo.setEntidad(new Entidad());
		organo.setUbigeo(new Ubigeo());
		organoDataModel = new OrganoDataModel(new ArrayList<Organo>());
		selectedOrgano = new Organo();

		abogado = new Abogado();
		abogado.setDni(null);
	
		anexo = new Anexo();
		anexo.setFechaInicio(cal.getTime());
		
		estudio = new Estudio();
		abogadoDataModel = new AbogadoDataModel(new ArrayList<Abogado>());

		persona = new Persona();
		persona.setClase(new Clase());
		persona.setCodCliente(null);
		persona.setTipoDocumento(new TipoDocumento());
		persona.setNumeroDocumento(null);
		
		personaDataModelBusq = new PersonaDataModel(new ArrayList<Persona>());
		selectPersona = new Persona();

	}

	public void llenarHitos() {

		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession sessionhttp = (HttpSession) context.getSession(true);
		String numeroExpediente = (String) sessionhttp.getAttribute("numeroExpediente");

		Busqueda filtro = Busqueda.forClass(Expediente.class);
		filtro.add(Restrictions.like("numeroExpediente", numeroExpediente)).addOrder(Order.asc("idExpediente"));

		List<Expediente> expedientes = new ArrayList<Expediente>();

		try {
			expedientes = expedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		setExpedienteVistas(new ArrayList<ExpedienteVista>());
		
		for (int i = 0; i < expedientes.size(); i++) {

			ExpedienteVista expedienteVistaNuevo = new ExpedienteVista();
			expedienteVistaNuevo.setFlagDeshabilitadoGeneral(true);

			if(expedientes.size() == 1){

				expedienteVistaNuevo.setFlagHabilitadoCuantiaModificar(false);
				expedienteVistaNuevo.setFlagColumnCuantia(true);
				
				
			}else{
				
				expedienteVistaNuevo.setFlagHabilitadoCuantiaModificar(true);
				expedienteVistaNuevo.setFlagColumnCuantia(false);
				
			}
			
			if (i == expedientes.size() - 1) {

				setPosiExpeVista(i);
				setTabActivado(i);

				setExpedienteOrig(expedientes.get(i));

				expedienteVistaNuevo.setFlagColumnGeneral(true);
				expedienteVistaNuevo.setFlagHabilitadoModificar(false);

				expedienteVistaNuevo.setFlagBotonFinInst(true);
				expedienteVistaNuevo.setFlagBotonGuardar(true);
				expedienteVistaNuevo.setFlagBotonLimpiar(true);
				expedienteVistaNuevo.setFlagBotonHome(true);

				expedienteVistaNuevo.setDeshabilitarBotonGuardar(true);
				expedienteVistaNuevo.setDeshabilitarBotonFinInst(false);

				actualizarDatosPagina(expedienteVistaNuevo, expedientes.get(i));
				getExpedienteVistas().add(expedienteVistaNuevo);

				setExpedienteVista(expedienteVistaNuevo);

			} else {

				expedienteVistaNuevo.setFlagColumnGeneral(false);
				expedienteVistaNuevo.setFlagHabilitadoModificar(true);

				expedienteVistaNuevo.setFlagBotonFinInst(false);
				expedienteVistaNuevo.setFlagBotonGuardar(false);
				expedienteVistaNuevo.setFlagBotonLimpiar(false);
				expedienteVistaNuevo.setFlagBotonHome(false);

				expedienteVistaNuevo.setDeshabilitarBotonGuardar(true);
				expedienteVistaNuevo.setDeshabilitarBotonFinInst(false);

				actualizarDatosPagina(expedienteVistaNuevo, expedientes.get(i));
				getExpedienteVistas().add(expedienteVistaNuevo);
			}

		}

	}

	public void actualizarDatosPagina(ExpedienteVista ex, Expediente e) {

		String mensaje="";
		
		ex.setNroExpeOficial(e.getNumeroExpediente());
		ex.setInicioProceso(e.getFechaInicioProceso());
		if(e.getEstadoExpediente() != null)
			ex.setEstado(e.getEstadoExpediente().getIdEstadoExpediente());
		
		
		
		if(e.getInstancia() != null){
			ex.setProceso(e.getInstancia().getVia().getProceso().getIdProceso());

			GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Via.class);
			filtro.add(Restrictions.like("proceso.idProceso", ex.getProceso()));

			try {
				ex.setVias(viaDao.buscarDinamico(filtro));
			} catch (Exception exc) {
				exc.printStackTrace();
			}

			ex.setVia(e.getInstancia().getVia().getIdVia());

			GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit
					.getApplicationContext().getBean("genericoDao");
			filtro = Busqueda.forClass(Instancia.class);
			filtro.add(Restrictions.like("via.idVia", ex.getVia()));

			try {
				ex.setInstancias(instanciaDao.buscarDinamico(filtro));
				setInstanciasProximas(ex.getInstancias());
			} catch (Exception exc) {
				exc.printStackTrace();
			}

			ex.setInstancia(e.getInstancia().getIdInstancia());
			ex.setNombreInstancia(e.getInstancia().getNombre());
		}
		
		if(e.getUsuario() != null)
			
			e.getUsuario().setNombreDescripcion(
					e.getUsuario().getCodigo() + " - " +
					e.getUsuario().getNombres() + " " +
					e.getUsuario().getApellidoPaterno() + " " +
					e.getUsuario().getApellidoMaterno());
		
			ex.setResponsable(e.getUsuario());

		if(e.getOficina() != null){
			String texto = e.getOficina().getCodigo()
				+ " "
				+ e.getOficina().getNombre().toUpperCase()
				+ " ("
				+ e.getOficina().getUbigeo().getDepartamento()
						.toUpperCase() + ")";
			e.getOficina().setNombreDetallado(texto);
			ex.setOficina(e.getOficina());
		}

		
		if(e.getTipoExpediente() != null)
			ex.setTipo(e.getTipoExpediente().getIdTipoExpediente());

		if(e.getOrgano() != null ){
			
			mensaje += "Organo: " + e.getOrgano().getNombre() + "\n";
			
			String descripcion = e.getOrgano().getNombre().toUpperCase() + " ("
					+ e.getOrgano().getUbigeo().getDistrito().toUpperCase()
					+ ", "
					+ e.getOrgano().getUbigeo().getProvincia().toUpperCase()
					+ ", "
					+ e.getOrgano().getUbigeo().getDepartamento().toUpperCase()
					+ ")";
			e.getOrgano().setNombreDetallado(descripcion);
			ex.setOrgano1(e.getOrgano());
		}
		

		if(e.getCalificacion() != null)
			ex.setCalificacion(e.getCalificacion().getIdCalificacion());
		
		if(e.getRecurrencia() != null)
			ex.setRecurrencia(e.getRecurrencia());

		ex.setSecretario(e.getSecretario());
		
		if(e.getFormaConclusion() != null)
			mensaje += "Forma de Conclusion: " + e.getFormaConclusion().getDescripcion() + "\n";
			ex.setFormaConclusion(e.getFormaConclusion());
		
		if(e.getFechaFinProceso() != null){
			mensaje += "Fecha Fin de Instancia: " + e.getFechaFinProceso() + "\n";
			ex.setFinProceso(e.getFechaFinProceso());
		}
			

		ex.setHonorario(new Honorario());
		ex.getHonorario().setCantidad(0);
		ex.getHonorario().setMonto(0.0);
		ex.getHonorario().setMontoPagado(0.0);

		List<Honorario> honorarios = new ArrayList<Honorario>();
		List<Cuota> cuotas;
		List<AbogadoEstudio> abogadoEstudios= new ArrayList<AbogadoEstudio>();
		
		GenericDao<Honorario, Object> honorarioDAO = (GenericDao<Honorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Cuota, Object> cuotaDAO = (GenericDao<Cuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		

		Busqueda filtro = Busqueda.forClass(Honorario.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente()));
		
		try {
			honorarios = honorarioDAO.buscarDinamico(filtro);

			for (Honorario h : honorarios) {
				cuotas = new ArrayList<Cuota>();
				
				Busqueda filtro2 = Busqueda.forClass(Cuota.class);
				filtro2.add(Restrictions.like("honorario.idHonorario",h.getIdHonorario()));
				cuotas = cuotaDAO.buscarDinamico(filtro2);
				
				int i=1;
				for (Cuota cuota:cuotas) {
					cuota.setNumero(i);
					cuota.setMoneda(h.getMoneda().getSimbolo());
					
					SituacionCuota situacionCuota= cuota.getSituacionCuota();
					cuota.setSituacionCuota(new SituacionCuota());
					cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
					cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
					i++;
				}
				
				h.setCuotas(cuotas);
				
				Busqueda filtro3 = Busqueda.forClass(AbogadoEstudio.class);
				filtro3.add(Restrictions.like("estado", 'A'));
				filtro3.add(Restrictions.like("abogado", h.getAbogado()));
				abogadoEstudios = abogadoEstudioDAO.buscarDinamico(filtro3);
				
				h.setEstudio(abogadoEstudios.get(0).getEstudio().getNombre());
			
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

		ex.setHonorarios(honorarios);

		List<Involucrado> involucrados = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Involucrado.class);
		filtro.add(Restrictions.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			involucrados = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		InvolucradoDataModel involucradoDataModel = new InvolucradoDataModel(
				involucrados);
		ex.setInvolucradoDataModel(involucradoDataModel);
		ex.setInvolucrado(new Involucrado(new TipoInvolucrado(),new RolInvolucrado(), new Persona()));

		List<Cuantia> cuantias = new ArrayList<Cuantia>();
		GenericDao<Cuantia, Object> cuantiaDAO = (GenericDao<Cuantia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Cuantia.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente()));

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
		filtro.add(Restrictions.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			inculpados = inculpadoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setInculpados(inculpados);
		ex.setInculpado(new Inculpado(new SituacionInculpado(), new Moneda(),
				new Persona()));

		if(e.getMoneda() != null)
			ex.setMoneda(e.getMoneda().getIdMoneda());
		
		ex.setMontoCautelar(e.getMontoCautelar());

		if(e.getTipoCautelar() != null)
			ex.setTipoCautelar(e.getTipoCautelar().getIdTipoCautelar());
		
		
		ex.setDescripcionCautelar(e.getDescripcionCautelar());
		
		if(e.getContraCautela() != null)
			ex.setContraCautela(e.getContraCautela().getIdContraCautela());
		
		
		ex.setImporteCautelar(e.getImporteCautelar());
		
		if(e.getEstadoCautelar() != null)
		ex.setEstadoCautelar(e.getEstadoCautelar().getIdEstadoCautelar());

		List<Provision> provisions = new ArrayList<Provision>();
		GenericDao<Provision, Object> provisionDAO = (GenericDao<Provision, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Provision.class);
		filtro.add(Restrictions.like("expediente.idExpediente",
				e.getIdExpediente()));

		try {
			provisions = provisionDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setProvisiones(provisions);
		ex.setProvision(new Provision(new Moneda(), new TipoProvision()));

		List<Resumen> resumens = new ArrayList<Resumen>();
		GenericDao<Resumen, Object> resumenDAO = (GenericDao<Resumen, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Resumen.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente())).addOrder(Order.asc("idResumen"));

		
		try {
			resumens = resumenDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		ex.setResumens(resumens);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		if(resumens!=null){
			if(resumens.size()!=0){
				for(Resumen res: resumens){
					
					if(res != null){
						
						if(ex.getTodoResumen()==null){
							
							ex.setTodoResumen( "Jorge Guzman"+ "\n" +
											"\t" + res.getTexto() + "\n" +
											"\t" + "\t" + "\t" + "\t" + "\t" +
											"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(res.getFecha()));
							
						}else{
							
							ex.setTodoResumen( "Jorge Guzman" + "\n" +
											"\t" + res.getTexto() + "\n" +
											"\t" + "\t" + "\t" + "\t" + "\t" +
											"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(res.getFecha()) + "\n" +
											"---------------------------------------------------------------------------" + "\n" +
											ex.getTodoResumen());
							
							
						}
					}
					
					
				}
			}	
		}
		
		//ex.setFechaResumen(e.getFechaResumen());
		//ex.setResumen(e.getTextoResumen());
		// setTodoResumen(getSelectedExpediente().get)

		List<ActividadProcesal> actividadProcesals = new ArrayList<ActividadProcesal>();
		GenericDao<ActividadProcesal, Object> actividadProcesalDAO = (GenericDao<ActividadProcesal, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(ActividadProcesal.class);
		filtro.add(Restrictions.like("expediente.idExpediente",
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
		GenericDao<Anexo, Object> anexoDAO = (GenericDao<Anexo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Anexo.class);
		filtro.add(Restrictions.like("expediente.idExpediente",e.getIdExpediente()));

		try {
			anexos = anexoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		ex.setAnexos(anexos);
		ex.setAnexo(new Anexo());
		
		if(e.getRiesgo()!=null)
			ex.setRiesgo(e.getRiesgo().getIdRiesgo());

		setTabCaucion(false);
		
		if (ex.getProceso() == 1 || ex.getProceso() == 3) {
			setTabCaucion(true);
		}
		
		ex.setDescripcionTitulo(mensaje);
		
		
	}

	public void handleFileUpload(FileUploadEvent event){
		FacesMessage msg = new FacesMessage("Archivo ", event.getFile().getFileName() + " almacenado correctamente.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
		setFile(event.getFile());
		
	}

	public void onTabChange(TabChangeEvent event) {
		logger.debug("Active Tab: "+ event.getTab().getTitle());
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

	public void setSituacionHonorariosString(
			List<String> situacionHonorariosString) {
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

	public void setSituacionInculpadosString(
			List<String> situacionInculpadosString) {
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

	public List<String> getTipoProvisionesString() {
		return tipoProvisionesString;
	}

	public void setTipoProvisionesString(List<String> tipoProvisionesString) {
		this.tipoProvisionesString = tipoProvisionesString;
	}

	public List<String> getActividadesString() {
		return actividadesString;
	}

	public void setActividadesString(List<String> actividadesString) {
		this.actividadesString = actividadesString;
	}

	public List<String> getEtapasString() {
		return etapasString;
	}

	public void setEtapasString(List<String> etapasString) {
		this.etapasString = etapasString;
	}

	public List<String> getSituacionActProcesalesString() {
		return situacionActProcesalesString;
	}

	public void setSituacionActProcesalesString(
			List<String> situacionActProcesalesString) {
		this.situacionActProcesalesString = situacionActProcesalesString;
	}

	public boolean isFlagGuardarInstancia() {
		return flagGuardarInstancia;
	}

	public void setFlagGuardarInstancia(boolean flagGuardarInstancia) {
		this.flagGuardarInstancia = flagGuardarInstancia;
	}

	public boolean isFlagGuardarOficina() {
		return flagGuardarOficina;
	}

	public void setFlagGuardarOficina(boolean flagGuardarOficina) {
		this.flagGuardarOficina = flagGuardarOficina;
	}

	public boolean isFlagGuardarOrgano1() {
		return flagGuardarOrgano1;
	}

	public void setFlagGuardarOrgano1(boolean flagGuardarOrgano1) {
		this.flagGuardarOrgano1 = flagGuardarOrgano1;
	}

	public boolean isFlagGuardarSecretario() {
		return flagGuardarSecretario;
	}

	public void setFlagGuardarSecretario(boolean flagGuardarSecretario) {
		this.flagGuardarSecretario = flagGuardarSecretario;
	}

	public boolean isFlagGuardarRecurrencia() {
		return flagGuardarRecurrencia;
	}

	public void setFlagGuardarRecurrencia(boolean flagGuardarRecurrencia) {
		this.flagGuardarRecurrencia = flagGuardarRecurrencia;
	}

	public boolean isFlagModificadoHonor() {
		return flagModificadoHonor;
	}

	public void setFlagModificadoHonor(boolean flagModificadoHonor) {
		this.flagModificadoHonor = flagModificadoHonor;
	}

	public boolean isFlagModificadoProv() {
		return flagModificadoProv;
	}

	public void setFlagModificadoProv(boolean flagModificadoProv) {
		this.flagModificadoProv = flagModificadoProv;
	}

	public boolean isFlagModificadoActPro() {
		return flagModificadoActPro;
	}

	public void setFlagModificadoActPro(boolean flagModificadoActPro) {
		this.flagModificadoActPro = flagModificadoActPro;
	}

	public boolean isFlagModificadoAnexo() {
		return flagModificadoAnexo;
	}

	public void setFlagModificadoAnexo(boolean flagModificadoAnexo) {
		this.flagModificadoAnexo = flagModificadoAnexo;
	}

	public boolean isFlagModificadoInv() {
		return flagModificadoInv;
	}

	public void setFlagModificadoInv(boolean flagModificadoInv) {
		this.flagModificadoInv = flagModificadoInv;
	}

	public boolean isFlagModificadoInc() {
		return flagModificadoInc;
	}

	public void setFlagModificadoInc(boolean flagModificadoInc) {
		this.flagModificadoInc = flagModificadoInc;
	}

	public boolean isFlagGuardarMoneda() {
		return flagGuardarMoneda;
	}

	public void setFlagGuardarMoneda(boolean flagGuardarMoneda) {
		this.flagGuardarMoneda = flagGuardarMoneda;
	}

	public boolean isFlagGuardarMonto() {
		return flagGuardarMonto;
	}

	public void setFlagGuardarMonto(boolean flagGuardarMonto) {
		this.flagGuardarMonto = flagGuardarMonto;
	}

	public boolean isFlagGuardarTipoMediaCautelar() {
		return flagGuardarTipoMediaCautelar;
	}

	public void setFlagGuardarTipoMediaCautelar(
			boolean flagGuardarTipoMediaCautelar) {
		this.flagGuardarTipoMediaCautelar = flagGuardarTipoMediaCautelar;
	}

	public boolean isFlagGuardarDescripcionCautelar() {
		return flagGuardarDescripcionCautelar;
	}

	public void setFlagGuardarDescripcionCautelar(
			boolean flagGuardarDescripcionCautelar) {
		this.flagGuardarDescripcionCautelar = flagGuardarDescripcionCautelar;
	}

	public boolean isFlagGuardarContraCautela() {
		return flagGuardarContraCautela;
	}

	public void setFlagGuardarContraCautela(boolean flagGuardarContraCautela) {
		this.flagGuardarContraCautela = flagGuardarContraCautela;
	}

	public boolean isFlagGuardarImporteCautela() {
		return flagGuardarImporteCautela;
	}

	public void setFlagGuardarImporteCautela(boolean flagGuardarImporteCautela) {
		this.flagGuardarImporteCautela = flagGuardarImporteCautela;
	}

	public boolean isFlagGuardarEstado() {
		return flagGuardarEstado;
	}

	public void setFlagGuardarEstado(boolean flagGuardarEstado) {
		this.flagGuardarEstado = flagGuardarEstado;
	}


	public boolean isFlagGuardarRiesgo() {
		return flagGuardarRiesgo;
	}

	public void setFlagGuardarRiesgo(boolean flagGuardarRiesgo) {
		this.flagGuardarRiesgo = flagGuardarRiesgo;
	}

	public boolean isFlagGuardarResumen() {
		return flagGuardarResumen;
	}

	public void setFlagGuardarResumen(boolean flagGuardarResumen) {
		this.flagGuardarResumen = flagGuardarResumen;
	}

	public Abogado getSelectedAbogado() {
		return selectedAbogado;
	}

	public void setSelectedAbogado(Abogado selectedAbogado) {
		this.selectedAbogado = selectedAbogado;
	}

	public Persona getSelectInvolucrado() {
		return selectInvolucrado;
	}

	public void setSelectInvolucrado(Persona selectInvolucrado) {
		this.selectInvolucrado = selectInvolucrado;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
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

	public boolean isFlagModificadoCua() {
		return flagModificadoCua;
	}

	public void setFlagModificadoCua(boolean flagModificadoCua) {
		this.flagModificadoCua = flagModificadoCua;
	}

}
