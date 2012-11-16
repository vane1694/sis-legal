
package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

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
import com.hildebrando.legal.view.ExpedienteDataModel;


@ManagedBean(name="consultaExpe")
@SessionScoped
public class ConsultaExpedienteMB {

	public static Logger logger = Logger
			.getLogger(ConsultaExpedienteMB.class);
	
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
		
		super();
		
		cargarCombos();
		
	}
	
	@SuppressWarnings("unchecked")
	private void cargarCombos() {

		logger.debug("Cargando combos para consulta de expediente");
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		
		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstadoExpediente = Busqueda
				.forClass(EstadoExpediente.class);
		
		try {
			procesos = procesoDAO.buscarDinamico(filtroProceso);
			estados = estadosExpedienteDAO.buscarDinamico(filtroEstadoExpediente);
			
		} catch (Exception e) {
			
			logger.debug("error al cargar combos en consulta de expediente..."+ e.toString());
		}
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
	
	public List<Recurrencia> completeRecurrencia(String query) {

		List<Recurrencia> recurrencias = new ArrayList<Recurrencia>();
		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Recurrencia.class);
		try {
			recurrencias = recurrenciaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	
	// listener cada vez que se modifica el proceso
		public void cambioProceso() {

			if (getProceso() != 0) {

				GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
				Busqueda filtro = Busqueda.forClass(Via.class);
				filtro.add(Restrictions.like("proceso.idProceso", getProceso()));

				try {
					vias = viaDao.buscarDinamico(filtro);
				} catch (Exception e) {
					e.printStackTrace();
				}

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
			
			
			logger.debug("Buscando expedientes...");
			
			List<Expediente> expedientes = new ArrayList<Expediente>();
			GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(Expediente.class);
			filtro.add(Restrictions.isNull("expediente.idExpediente")).addOrder(Order.desc("idExpediente"));
			
			if(!usuario.getPerfil().getNombre().equalsIgnoreCase("Administrador")){
				
				logger.debug("filtro "+ usuario.getPerfil().getNombre()  +" expedientes - proceso");
				
				
				if(usuario.getPerfil().getNombre().equalsIgnoreCase("Abogado Civil")){
					
					filtro.add(Restrictions.eq("proceso.idProceso", 1));
				}
				
				if(usuario.getPerfil().getNombre().equalsIgnoreCase("Abogado Penal")){
					
					filtro.add(Restrictions.eq("proceso.idProceso", 2));
				}
				
				
				if(usuario.getPerfil().getNombre().equalsIgnoreCase("Abogado Administrativo")){
					
					filtro.add(Restrictions.eq("proceso.idProceso",3));
				}
				
			}

			if(getNroExpeOficial().compareTo("")!=0){
				
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
