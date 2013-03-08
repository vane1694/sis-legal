
package com.hildebrando.legal.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.grupobbva.seguridad.client.domain.Usuario;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.view.ExpedienteDataModel;

public class ConsultaExpedienteMB implements Serializable {

	public static Logger logger = Logger.getLogger(ConsultaExpedienteMB.class);
	
	private String nroExpeOficial;
	private int proceso;
	private List<Proceso> procesos;
	private int via;
	private List<Via> vias;
	private String demandante;
	private Organo organo;
	private int estado;
	private List<EstadoExpediente> estados;
	private Recurrencia recurrencia;
	private Materia materia;
	private String claveBusqueda;
	private ExpedienteDataModel expedienteDataModel;
	private Expediente selectedExpediente;
	
	private ConsultaService consultaService;
	
	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}
	
	public String reset(){
		
		logger.debug("Nuevo Expediente..");
		
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		logger.debug("Recuperando usuario..");
		
		Usuario usuario= (Usuario) session1.getAttribute("usuario");
		
		logger.debug("Invalidado la sesion..");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(true);
		
		logger.debug("Registrando el usuario..");
		session.setAttribute("usuario", usuario);
	      
		return "/faces/paginas/registroExpediente.xhtml";
	}
	
	public ConsultaExpedienteMB() {
		
	}
	
	@PostConstruct
	@SuppressWarnings("unchecked")
	private void cargarCombos() {

		logger.debug("Cargando combos para consulta de expediente");
		procesos = consultaService.getProcesos();
		estados = consultaService.getEstadoExpedientes();
		
	}
	
	public String verExpediente() {
		
		
		logger.debug("editando expediente " + getSelectedExpediente().getNumeroExpediente());
		
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		logger.debug("Recuperando usuario..");
		
		Usuario usuario= (Usuario) session1.getAttribute("usuario");
		
		GenericDao<com.hildebrando.legal.modelo.Usuario, Object> usuarioDAO = 
			(GenericDao<com.hildebrando.legal.modelo.Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroIni = Busqueda.forClass(com.hildebrando.legal.modelo.Usuario.class);
		filtroIni.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
		List<com.hildebrando.legal.modelo.Usuario> usuarios = new ArrayList<com.hildebrando.legal.modelo.Usuario>();

		try {
			usuarios = usuarioDAO.buscarDinamico(filtroIni);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (usuarios != null) {

			if (usuarios.size() != 0) {
				
			}

		}
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
	    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	    HttpSession session = (HttpSession) context.getSession(true);
	    session.setAttribute("numeroExpediente", getSelectedExpediente().getNumeroExpediente());
	    session.setAttribute("usuario", usuario);
	    session.setAttribute("modo", SglConstantes.MODO_LECTURA);

		return "actualSeguiExpediente.xhtml?faces-redirect=true";

	}  
	
	public String editarExpediente() {
		
		
		logger.debug("editando expediente " + getSelectedExpediente().getNumeroExpediente());
		
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		logger.debug("Recuperando usuario..");
		
		Usuario usuario= (Usuario) session1.getAttribute("usuario");
		
		GenericDao<com.hildebrando.legal.modelo.Usuario, Object> usuarioDAO = 
			(GenericDao<com.hildebrando.legal.modelo.Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroIni = Busqueda.forClass(com.hildebrando.legal.modelo.Usuario.class);
		filtroIni.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
		List<com.hildebrando.legal.modelo.Usuario> usuarios = new ArrayList<com.hildebrando.legal.modelo.Usuario>();

		try {
			usuarios = usuarioDAO.buscarDinamico(filtroIni);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (usuarios != null) {

			if (usuarios.size() != 0) {
				
			}

		}
		  
		 
		if(!usuario.getPerfil().getNombre().equalsIgnoreCase("Administrador")){
		
			
			if(usuarios.get(0).getIdUsuario() == getSelectedExpediente().getUsuario().getIdUsuario()){
				
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				
			    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			    HttpSession session = (HttpSession) context.getSession(true);
			    session.setAttribute("numeroExpediente", getSelectedExpediente().getNumeroExpediente());
			    session.setAttribute("usuario", usuario);
			    session.setAttribute("modo", SglConstantes.MODO_EDICION);
			    
			    return "actualSeguiExpediente.xhtml?faces-redirect=true";
				
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Informacion", "No es responsable del expediente " + getSelectedExpediente().getNumeroExpediente() +"!!!" );
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				return null;
			}
		
		
		}else{
			
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			
		    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		    HttpSession session = (HttpSession) context.getSession(true);
		    session.setAttribute("numeroExpediente", getSelectedExpediente().getNumeroExpediente());
		    session.setAttribute("usuario", usuario);
		    session.setAttribute("modo", SglConstantes.MODO_EDICION);

			return "actualSeguiExpediente.xhtml?faces-redirect=true";
		}

	}  
	 
	
	public List<Organo> completeOrgano(String query) {
		List<Organo> results = new ArrayList<Organo>();
		List<Organo> organos = consultaService.getOrganos();

		for (Organo organo : organos) {
			String descripcion = organo.getNombre().toUpperCase() + " ("
					+ organo.getUbigeo().getDistrito().toUpperCase() + ", "
					+ organo.getUbigeo().getProvincia().toUpperCase() + ", "
					+ organo.getUbigeo().getDepartamento().toUpperCase() + ")";

			if (descripcion.toUpperCase().contains(query.toUpperCase())) {

				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}

		return results;
	}
	
	public List<Recurrencia> completeRecurrencia(String query) {

		List<Recurrencia> recurrencias = consultaService.getRecurrencias();
		List<Recurrencia> results = new ArrayList<Recurrencia>();

		for (Recurrencia rec : recurrencias) {
			if (rec.getNombre().toUpperCase().contains(query.toUpperCase())) {
				results.add(rec);
			}
		}

		return results;
	}

	public List<Materia> completeMaterias(String query) {
		
		List<Materia> results = new ArrayList<Materia>();
		List<Materia> materias = consultaService.getMaterias();

		for (Materia mat : materias) {
			if (mat.getDescripcion().toLowerCase()
					.startsWith(query.toLowerCase())) {
				results.add(mat);
			}
		}

		return results;
	}

	
	public void cambioProceso() {

		if (getProceso() != 0) {

			vias = consultaService.getViasByProceso(getProceso());

		} else {
			
			vias = new ArrayList<Via>();

		}

	}
		
		@SuppressWarnings("unchecked")
		public void buscarExpedientes(ActionEvent e){
			
			/*FacesContext fc = FacesContext.getCurrentInstance(); 
			ExternalContext exc = fc.getExternalContext(); 
			HttpSession session1 = (HttpSession) exc.getSession(true);
			
			logger.debug("Recuperando usuario..");
			
			Usuario usuario= (Usuario) session1.getAttribute("usuario");
			*/
			/*GenericDao<com.hildebrando.legal.modelo.Usuario, Object> usuarioDAO = 
				(GenericDao<com.hildebrando.legal.modelo.Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtroIni = Busqueda.forClass(com.hildebrando.legal.modelo.Usuario.class);
			filtroIni.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
			List<com.hildebrando.legal.modelo.Usuario> usuarios = new ArrayList<com.hildebrando.legal.modelo.Usuario>();

			try {
				usuarios = usuarioDAO.buscarDinamico(filtroIni);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (usuarios != null) {

				if (usuarios.size() != 0) {
					
				}

			}*/
			
			logger.debug(" === Buscando expedientes ===");
			
			List<Expediente> expedientes = new ArrayList<Expediente>();
			GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(Expediente.class);
			filtro.add(Restrictions.isNull("expediente.idExpediente")).addOrder(Order.desc("idExpediente"));
			
			/*if(!usuario.getPerfil().getNombre().equalsIgnoreCase("Administrador")){
				
				logger.debug("filtro "+ usuario.getPerfil().getNombre()  +" expedientes - proceso");
				
				filtro.add(Restrictions.eq("usuario.idUsuario",usuarios.get(0).getIdUsuario()));
				
			}*/

			if (getNroExpeOficial().compareTo("")!=0){
				logger.debug("[BUSQ_EXP]-NroExp: "+ getNroExpeOficial());
				filtro.add(Restrictions.like("numeroExpediente","%" + getNroExpeOficial().trim() + "%").ignoreCase());
			}

			if(getProceso()!=0){
				logger.debug("[BUSQ_EXP]-Proceso: " + getProceso());
				filtro.add(Restrictions.eq("proceso.idProceso", getProceso()));
			}
			
			if(getVia()!=0){
				logger.debug("[BUSQ_EXP]-Via: "+ getVia());				
				filtro.add(Restrictions.eq("via.idVia", getVia()));
			}
			
			if(getOrgano()!= null){
				logger.debug("[BUSQ_EXP]-Organo: "+ getOrgano().getIdOrgano());	
				filtro.add(Restrictions.eq("organo", getOrgano()));
			}
			
			if(getEstado()!=0){
				logger.debug("[BUSQ_EXP]-EstadoExp:  "+ getEstado());	
				filtro.add(Restrictions.eq("estadoExpediente.idEstadoExpediente", getEstado()));
			}
			
			if(getRecurrencia()!=null){
				logger.debug("[BUSQ_EXP]-Recurrencia:  "+  getRecurrencia().getIdRecurrencia());
				filtro.add(Restrictions.eq("recurrencia", getRecurrencia()));
			}
			//TODO [08.03.2013] No se esta considerando los filtros: Demandante y Materia en 
			//la busqueda de expedientes (Inc 185 y 186)
			
			try {
				expedientes = expedienteDAO.buscarDinamico(filtro);
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+" de expedientes encontrados es: [ "+ expedientes.size()+" ]");
				
			} catch (Exception e1) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"expedientes: "+e1);
			}

			expedienteDataModel = new ExpedienteDataModel(expedientes);
			logger.debug("== saliendo de buscarExpedientes() ===");
			
		}
		

		public String getNroExpeOficial() {
			return nroExpeOficial;
		}

		public void setNroExpeOficial(String nroExpeOficial) {
			this.nroExpeOficial = nroExpeOficial;
		}

		public String getClaveBusqueda() {
			return claveBusqueda;
		}

		public void setClaveBusqueda(String claveBusqueda) {
			this.claveBusqueda = claveBusqueda;
		}

		public ExpedienteDataModel getExpedienteDataModel() {
			
			return expedienteDataModel;
		}
		
		public String getDemandante() {
			return demandante;
		}

		public void setDemandante(String demandante) {
			this.demandante = demandante;
		}

		public Expediente getSelectedExpediente() {
			return selectedExpediente;
		}

		public void setSelectedExpediente(Expediente selectedExpediente) {
			this.selectedExpediente = selectedExpediente;
		}

		public List<Proceso> getProcesos() {
			return procesos;
		}

		public void setProcesos(List<Proceso> procesos) {
			this.procesos = procesos;
		}

		public int getProceso() {
			return proceso;
		}

		public void setProceso(int proceso) {
			this.proceso = proceso;
		}

		public List<Via> getVias() {
			return vias;
		}

		public void setVias(List<Via> vias) {
			this.vias = vias;
		}

		public int getVia() {
			return via;
		}

		public void setVia(int via) {
			this.via = via;
		}

		public int getEstado() {
			return estado;
		}

		public void setEstado(int estado) {
			this.estado = estado;
		}

		public List<EstadoExpediente> getEstados() {
			return estados;
		}

		public void setEstados(List<EstadoExpediente> estados) {
			this.estados = estados;
		}

		public void setOrgano(Organo organo) {
			this.organo = organo;
		}

		public Organo getOrgano() {
			return organo;
		}

		public Recurrencia getRecurrencia() {
			return recurrencia;
		}

		public void setRecurrencia(Recurrencia recurrencia) {
			this.recurrencia = recurrencia;
		}

		public Materia getMateria() {
			return materia;
		}

		public void setMateria(Materia materia) {
			this.materia = materia;
		}
}
