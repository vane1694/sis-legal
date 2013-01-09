
package com.hildebrando.legal.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
	
	public String editarExpediente() {
		
		
		  logger.debug("editando expediente " + getSelectedExpediente().getNumeroExpediente());
		  
		  FacesContext fc = FacesContext.getCurrentInstance(); 
		  ExternalContext exc = fc.getExternalContext(); 
		  HttpSession session1 = (HttpSession) exc.getSession(true);
		
		  Usuario usuario= (Usuario) session1.getAttribute("usuario");
		  
		  FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		  ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		  HttpSession session = (HttpSession) context.getSession(true);
		  session.setAttribute("numeroExpediente", getSelectedExpediente().getNumeroExpediente());
		  session.setAttribute("usuario", usuario);
        
		  return "actualSeguiExpediente.xhtml?faces-redirect=true";
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
			
			logger.debug("Buscando expedientes...");
			
			List<Expediente> expedientes = new ArrayList<Expediente>();
			GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(Expediente.class);
			filtro.add(Restrictions.isNull("expediente.idExpediente")).addOrder(Order.desc("idExpediente"));
			
			if(!usuario.getPerfil().getNombre().equalsIgnoreCase("Administrador")){
				
				logger.debug("filtro "+ usuario.getPerfil().getNombre()  +" expedientes - proceso");
				
			   /*	
				if(usuario.getPerfil().getNombre().equalsIgnoreCase("Abogado Civil")){
					
					filtro.add(Restrictions.neq("proceso.idProceso", 1));
				}
				
				if(usuario.getPerfil().getNombre().equalsIgnoreCase("Abogado Penal")){
					
					filtro.add(Restrictions.eq("proceso.idProceso", 2));
				}
					
				if(usuario.getPerfil().getNombre().equalsIgnoreCase("Abogado Administrativo")){
					
					filtro.add(Restrictions.eq("proceso.idProceso",3));
				}*/
				
				filtro.add(Restrictions.eq("usuario.idUsuario",usuarios.get(0).getIdUsuario()));
				
			}

			if (getNroExpeOficial().compareTo("")!=0){
				
				logger.debug("filtro "+ getNroExpeOficial()  +" expedientes - numero expediente");
				filtro.add(Restrictions.eq("numeroExpediente", getNroExpeOficial()));
			}

			if(getProceso()!=0){
				
				logger.debug("filtro " + getProceso() + "expedientes - proceso");
				filtro.add(Restrictions.eq("proceso.idProceso", getProceso()));
			}
			
			if(getVia()!=0){
				
				logger.debug("filtro "+ getVia() +" expedientes - via");				
				filtro.add(Restrictions.eq("via.idVia", getVia()));
			}
			
			if(getOrgano()!= null){
				
				logger.debug("filtro "+ getOrgano().getIdOrgano()  +"expedientes - organo");	
				filtro.add(Restrictions.eq("organo", getOrgano()));
				
			}
			
			if(getEstado()!=0){
				
				logger.debug("filtro "+ getEstado()  +" expedientes - estado");	
				filtro.add(Restrictions.eq("estadoExpediente.idEstadoExpediente", getEstado()));
			}
			
			if(getRecurrencia()!=null){
				
				logger.debug("filtro "+ getRecurrencia().getIdRecurrencia()   +" expedientes - recurrencia");
				filtro.add(Restrictions.eq("recurrencia", getRecurrencia()));
				
			}
			
			try {
				
				expedientes = expedienteDAO.buscarDinamico(filtro);
				
				logger.debug("total de expedientes encontrados: "+ expedientes.size());
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
				logger.debug("error al buscar expedientes: "+ e1.toString());
				
				
			}

			expedienteDataModel = new ExpedienteDataModel(expedientes);

			
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
