package com.hildebrando.legal.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoCautelar;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Etapa;
import com.hildebrando.legal.modelo.FormaConclusion;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.SituacionActProc;
import com.hildebrando.legal.modelo.SituacionCuota;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.TipoCautelar;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.TipoInvolucrado;
import com.hildebrando.legal.modelo.TipoProvision;
import com.hildebrando.legal.modelo.Via;

@ManagedBean(name = "mnt")
@SessionScoped
public class MantenimientoMB {
	
	public static Logger logger = Logger.getLogger(MantenimientoMB.class);
	
	private int idProceso;
	private String nombreProceso;
	private String abrevProceso;
	private List<Proceso> procesos;
	private String nombreVia;
	private String nombreInstancia;
	private String nombreActividad;
	private String nombreMoneda;
	private String abrevMoneda;
	private String  rucEstudio;
	private String  nombreEstudio;
	private String  direccionEstudio;
	private String  telefEstudio;
	private String  correoEstudio;
	private String nombreEstCaut;
	private String nombreEstExpe;
	private String nombreEtapa;
	private String nombreEntidad;
	private String nombreFormConc;
	private String nombreRecurrencia;
	private String nombreSitActPro;
	private String nombreSitCuota;
	private String nombreSitHonor;
	private String nombreSitIncul;
	private String nombreTipoCaut;
	private String nombreTipoExpe;
	private String nombreTipoHonor;
	private String nombreTipoInv;
	private String nombreTipoPro;
	private String nombreRolInvol;
	
	public MantenimientoMB() {

		logger.debug("Inicializando Valores..");
		inicializarValores();
		
		cargarCombos();
	}
	
	
	private void inicializarValores() {
		
		setNombreProceso("");
		setAbrevProceso("");
		setNombreVia("");
	}

	private void cargarCombos() {
		
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		
		try {
			procesos = procesoDAO.buscarDinamico(filtroProceso);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void agregarProceso(ActionEvent e) {

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Proceso proceso= new Proceso();
		proceso.setNombre(getNombreProceso());
		proceso.setAbreviatura(getAbrevProceso());
		
		try {
			procesoDAO.insertar(proceso);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el proceso"));
			logger.debug("guardo el proceso exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el proceso"));
			logger.debug("no guardo el proceso por "+ ex.getMessage());
		}
		
	}
	
	public void agregarVia(ActionEvent e) {

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Via via = new Via();
		via.setNombre(getNombreVia());
		
		try {
			
			via.setProceso(procesoDAO.buscarById(Proceso.class, getIdProceso()));
			viaDAO.insertar(via);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la via"));
			logger.debug("guardo la via exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la via"));
			logger.debug("no guardo la via por "+ ex.getMessage());
		}
		
	}
	
	public void agregarInstancia(ActionEvent e) {

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
		Instancia instancia= new Instancia();
		instancia.setNombre(getNombreInstancia());
	
		try {
			instanciaDAO.insertar(instancia);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la instancia"));
			logger.debug("guardo la instancia exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la instancia"));
			logger.debug("no guardo la instancia por "+ ex.getMessage());
		}
		
	}
	
	public void agregarActividad(ActionEvent e) {

		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Actividad actividad= new Actividad();
		actividad.setNombre(getNombreActividad());
	
		try {
			actividadDAO.insertar(actividad);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la actividad"));
			logger.debug("guardo la actividad exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la actividad"));
			logger.debug("no guardo la actividad por "+ ex.getMessage());
		}
		
	}
	
	public void agregarMoneda(ActionEvent e) {

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
		Moneda moneda= new Moneda();
		moneda.setDescripcion(getNombreMoneda());
		moneda.setSimbolo(getAbrevMoneda());
	
		try {
			monedaDAO.insertar(moneda);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo de moneda"));
			logger.debug("guardo el tipo de moneda exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo de moneda"));
			logger.debug("no guardo el tipo de moneda por "+ ex.getMessage());
		}
		
	}
	
	public void agregarEstudio(ActionEvent e) {

		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Estudio  estudio= new Estudio();
		
		estudio.setRuc(Long.parseLong(getRucEstudio()));
		estudio.setCorreo(getCorreoEstudio());
		estudio.setDireccion(getDireccionEstudio());
		estudio.setNombre(getNombreEstudio());
		estudio.setTelefono(getTelefEstudio());
		
	
		try {
			estudioDAO.insertar(estudio);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el estudio"));
			logger.debug("guardo el estudio exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el estudio"));
			logger.debug("no guardo el estudio por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarRolInv(ActionEvent e) {

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		RolInvolucrado rolInvolucrado= new RolInvolucrado();
		rolInvolucrado.setNombre(getNombreRolInvol());
		
		
		try {
			rolInvolucradoDAO.insertar(rolInvolucrado);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el rol involucrado"));
			logger.debug("guardo el rol involucrado exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el rol involucrado"));
			logger.debug("no guardo el rol involucrado por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEstCaut(ActionEvent e) {

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		EstadoCautelar estadoCautelar= new EstadoCautelar();
		
		estadoCautelar.setDescripcion(getNombreEstCaut());
		
		try {
			estadoCautelarDAO.insertar(estadoCautelar);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el estado cautelar"));
			logger.debug("guardo el estado cautelar exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el estado cautelar"));
			logger.debug("no guardo el estado cautelar por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEstExpe(ActionEvent e) {

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		EstadoExpediente estadoExpediente= new EstadoExpediente();
		
		estadoExpediente.setNombre(getNombreEstExpe());
		
		try {
			estadoExpedienteDAO.insertar(estadoExpediente);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el estado expediente"));
			logger.debug("guardo el estado expediente exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el estado expediente"));
			logger.debug("no guardo el estado expediente por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEtapa(ActionEvent e) {

		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Etapa etapa= new Etapa();
		etapa.setNombre(getNombreEtapa());
		
		try {
			etapaDAO.insertar(etapa);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la etapa"));
			logger.debug("guardo la etapa exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la etapa"));
			logger.debug("no guardo la etapa por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarEntidad(ActionEvent e) {

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Entidad entidad= new Entidad();
		entidad.setNombre(getNombreEntidad());
		
		try {
			entidadDAO.insertar(entidad);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la entidad"));
			logger.debug("guardo la entidad exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la entidad"));
			logger.debug("no guardo la entidad por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarFormConc(ActionEvent e) {

		GenericDao<FormaConclusion, Object> formaConclusionDAO = (GenericDao<FormaConclusion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		FormaConclusion formaConclusion= new FormaConclusion();
		formaConclusion.setDescripcion(getNombreFormConc());
		
		try {
			formaConclusionDAO.insertar(formaConclusion);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la forma conclusion"));
			logger.debug("guardo la forma conclusion exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la forma conclusion"));
			logger.debug("no guardo la forma conclusion por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarRecurrencia(ActionEvent e) {

		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Recurrencia recurrencia= new Recurrencia();
		recurrencia.setNombre(getNombreRecurrencia());
			
		try {
			recurrenciaDAO.insertar(recurrencia);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la recurrencia"));
			logger.debug("guardo la recurrencia exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la recurrencia"));
			logger.debug("no guardo la recurrencia por "+ ex.getMessage());
		}
		
	}
	
	public void  agregarSitActPro(ActionEvent e) {

		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		SituacionActProc situacionActProc= new SituacionActProc();
		situacionActProc.setNombre(getNombreSitActPro());
			
		try {
			situacionActProcDAO.insertar(situacionActProc);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac act pro"));
			logger.debug("guardo la situac act pro exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac act pro"));
			logger.debug("no guardo la situac act pro por "+ ex.getMessage());
		}
		
	}

	public void agregarSitCuota(ActionEvent e) {

		GenericDao<SituacionCuota, Object> situacionCuotaDAO = (GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		SituacionCuota situacionCuota= new SituacionCuota();
		situacionCuota.setDescripcion(getNombreSitCuota());
			
		try {
			situacionCuotaDAO.insertar(situacionCuota);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac cuota"));
			logger.debug("guardo la situac cuota exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac cuota"));
			logger.debug("no guardo la situac cuota por "+ ex.getMessage());
		}
		
	}
	
	public void agregarSitHonor(ActionEvent e) {

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		SituacionHonorario situacionHonorario= new SituacionHonorario();
		situacionHonorario.setDescripcion(getNombreSitHonor());
		
			
		try {
			situacionHonorarioDAO.insertar(situacionHonorario);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac honor"));
			logger.debug("guardo la situac honor exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac honor"));
			logger.debug("no guardo la situac honor por "+ ex.getMessage());
		}
		
	}
	
	public void agregarSitIncul(ActionEvent e) {

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		
		SituacionInculpado situacionInculpado= new SituacionInculpado();
		situacionInculpado.setNombre(getNombreSitIncul());
			
		try {
			situacionInculpadoDAO.insertar(situacionInculpado);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la situac inculp"));
			logger.debug("guardo la situac inculp exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la situac inculp"));
			logger.debug("no guardo la situac inculp por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoCaut(ActionEvent e) {

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoCautelar tipoCautelar= new TipoCautelar();
		tipoCautelar.setDescripcion(getNombreTipoCaut());
			
		try {
			tipoCautelarDAO.insertar(tipoCautelar);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo cautelar"));
			logger.debug("guardo el tipo cautelar exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo cautelar"));
			logger.debug("no guardo el tipo cautelar por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoExpe(ActionEvent e) {

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoExpediente tipoExpediente= new TipoExpediente();
		tipoExpediente.setNombre(getNombreTipoExpe());
			
		try {
			tipoExpedienteDAO.insertar(tipoExpediente);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo expediente"));
			logger.debug("guardo el tipo expediente exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo expediente"));
			logger.debug("no guardo el tipo expediente por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoHonor(ActionEvent e) {

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoHonorario tipoHonorario= new TipoHonorario();
		tipoHonorario.setDescripcion(getNombreTipoHonor());
			
		try {
			tipoHonorarioDAO.insertar(tipoHonorario);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo honorario"));
			logger.debug("guardo el tipo honorario exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo honorario"));
			logger.debug("no guardo el tipo honorario por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoInv(ActionEvent e) {

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoInvolucrado tipoInvolucrado= new TipoInvolucrado();
		tipoInvolucrado.setNombre(getNombreTipoInv());
		
			
		try {
			tipoInvolucradoDAO.insertar(tipoInvolucrado);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo honorario"));
			logger.debug("guardo el tipo honorario exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo honorario"));
			logger.debug("no guardo el tipo honorario por "+ ex.getMessage());
		}
		
	}
	
	public void agregarTipoPro(ActionEvent e) {

		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		TipoProvision tipoProvision= new TipoProvision();
		tipoProvision.setDescripcion(getNombreTipoPro());
		
			
		try {
			tipoProvisionDAO.insertar(tipoProvision);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo provision"));
			logger.debug("guardo el tipo provision exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo provision"));
			logger.debug("no guardo el tipo provision por "+ ex.getMessage());
		}
		
	}
	
	public String getNombreProceso() {
		return nombreProceso;
	}


	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}


	public String getAbrevProceso() {
		return abrevProceso;
	}


	public void setAbrevProceso(String abrevProceso) {
		this.abrevProceso = abrevProceso;
	}


	public List<Proceso> getProcesos() {
		return procesos;
	}


	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public String getNombreVia() {
		return nombreVia;
	}


	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}


	public String getNombreInstancia() {
		return nombreInstancia;
	}


	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}


	public String getNombreMoneda() {
		return nombreMoneda;
	}


	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}


	public String getAbrevMoneda() {
		return abrevMoneda;
	}


	public void setAbrevMoneda(String abrevMoneda) {
		this.abrevMoneda = abrevMoneda;
	}


	public String getNombreEstudio() {
		return nombreEstudio;
	}


	public void setNombreEstudio(String nombreEstudio) {
		this.nombreEstudio = nombreEstudio;
	}


	public String getDireccionEstudio() {
		return direccionEstudio;
	}


	public void setDireccionEstudio(String direccionEstudio) {
		this.direccionEstudio = direccionEstudio;
	}


	public String getTelefEstudio() {
		return telefEstudio;
	}


	public void setTelefEstudio(String telefEstudio) {
		this.telefEstudio = telefEstudio;
	}


	public String getCorreoEstudio() {
		return correoEstudio;
	}


	public void setCorreoEstudio(String correoEstudio) {
		this.correoEstudio = correoEstudio;
	}


	public String getRucEstudio() {
		return rucEstudio;
	}


	public void setRucEstudio(String rucEstudio) {
		this.rucEstudio = rucEstudio;
	}


	public String getNombreEstCaut() {
		return nombreEstCaut;
	}


	public void setNombreEstCaut(String nombreEstCaut) {
		this.nombreEstCaut = nombreEstCaut;
	}


	public String getNombreEstExpe() {
		return nombreEstExpe;
	}


	public void setNombreEstExpe(String nombreEstExpe) {
		this.nombreEstExpe = nombreEstExpe;
	}


	public String getNombreEtapa() {
		return nombreEtapa;
	}


	public void setNombreEtapa(String nombreEtapa) {
		this.nombreEtapa = nombreEtapa;
	}


	public String getNombreFormConc() {
		return nombreFormConc;
	}


	public void setNombreFormConc(String nombreFormConc) {
		this.nombreFormConc = nombreFormConc;
	}


	public String getNombreRecurrencia() {
		return nombreRecurrencia;
	}


	public void setNombreRecurrencia(String nombreRecurrencia) {
		this.nombreRecurrencia = nombreRecurrencia;
	}


	public String getNombreSitActPro() {
		return nombreSitActPro;
	}


	public void setNombreSitActPro(String nombreSitActPro) {
		this.nombreSitActPro = nombreSitActPro;
	}


	public String getNombreSitCuota() {
		return nombreSitCuota;
	}


	public void setNombreSitCuota(String nombreSitCuota) {
		this.nombreSitCuota = nombreSitCuota;
	}


	public String getNombreSitHonor() {
		return nombreSitHonor;
	}


	public void setNombreSitHonor(String nombreSitHonor) {
		this.nombreSitHonor = nombreSitHonor;
	}


	public String getNombreSitIncul() {
		return nombreSitIncul;
	}


	public void setNombreSitIncul(String nombreSitIncul) {
		this.nombreSitIncul = nombreSitIncul;
	}


	public String getNombreTipoCaut() {
		return nombreTipoCaut;
	}


	public void setNombreTipoCaut(String nombreTipoCaut) {
		this.nombreTipoCaut = nombreTipoCaut;
	}


	public String getNombreTipoExpe() {
		return nombreTipoExpe;
	}


	public void setNombreTipoExpe(String nombreTipoExpe) {
		this.nombreTipoExpe = nombreTipoExpe;
	}


	public String getNombreTipoHonor() {
		return nombreTipoHonor;
	}


	public void setNombreTipoHonor(String nombreTipoHonor) {
		this.nombreTipoHonor = nombreTipoHonor;
	}


	public String getNombreTipoInv() {
		return nombreTipoInv;
	}


	public void setNombreTipoInv(String nombreTipoInv) {
		this.nombreTipoInv = nombreTipoInv;
	}


	public String getNombreTipoPro() {
		return nombreTipoPro;
	}


	public void setNombreTipoPro(String nombreTipoPro) {
		this.nombreTipoPro = nombreTipoPro;
	}


	public String getNombreActividad() {
		return nombreActividad;
	}


	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}


	public String getNombreRolInvol() {
		return nombreRolInvol;
	}


	public void setNombreRolInvol(String nombreRolInvol) {
		this.nombreRolInvol = nombreRolInvol;
	}


	public String getNombreEntidad() {
		return nombreEntidad;
	}


	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}


	public int getIdProceso() {
		return idProceso;
	}


	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}


	
}
