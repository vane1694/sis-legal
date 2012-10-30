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
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Via;

@ManagedBean(name = "mnt")
@SessionScoped
public class MantenimientoMB {
	
	public static Logger logger = Logger.getLogger(MantenimientoMB.class);
	
	private Proceso proceso;
	private String nombreProceso;
	private String abrevProceso;
	private List<Proceso> procesos;
	private String nombreVia;
	private String nombreInstancia;
	private String nombreMoneda;
	private String abrevMoneda;
	
	public MantenimientoMB() {

		logger.debug("Inicializando Valores..");
		inicializarValores();
		
		cargarCombos();
	}
	
	
	private void inicializarValores() {
		
		setNombreProceso("");
		setAbrevProceso("");
		setNombreVia("");
		proceso= new Proceso();
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
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el proceso"));
			logger.debug("guardo el proceso exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el proceso"));
			logger.debug("no guardo el proceso por "+ ex.getMessage());
		}
		
	}
	
	public void agregarVia(ActionEvent e) {

		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Via via = new Via();
		via.setNombre(getNombreVia());
		via.setProceso(getProceso());
		
		try {
			viaDAO.insertar(via);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la via"));
			logger.debug("guardo la via exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la via"));
			logger.debug("no guardo la via por "+ ex.getMessage());
		}
		
	}
	
	public void agregarInstancia(ActionEvent e) {

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
		Instancia instancia= new Instancia();
		instancia.setNombre(getNombreInstancia());
	
		try {
			instanciaDAO.insertar(instancia);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego la instancia"));
			logger.debug("guardo la instancia exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la instancia"));
			logger.debug("no guardo la instancia por "+ ex.getMessage());
		}
		
	}
	
	public void agregarMoneda(ActionEvent e) {

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
		Moneda moneda= new Moneda();
		moneda.setDescripcion(getNombreMoneda());
		moneda.setSimbolo(getAbrevMoneda());
	
		try {
			monedaDAO.insertar(moneda);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego el tipo de moneda"));
			logger.debug("guardo el tipo de moneda exitosamente");
			
		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego el tipo de moneda"));
			logger.debug("no guardo el tipo de moneda por "+ ex.getMessage());
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


	public Proceso getProceso() {
		return proceso;
	}


	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
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
}
