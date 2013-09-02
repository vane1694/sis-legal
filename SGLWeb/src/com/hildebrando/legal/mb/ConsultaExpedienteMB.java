
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
import com.bbva.persistencia.generica.dao.InvolucradoDao;
import com.grupobbva.seguridad.client.domain.Usuario;
import com.hildebrando.legal.modelo.Cuantia;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.view.ExpedienteDataModel;

/**
 * Clase encargada de consultar expediente registrados en la aplicación
 * en base a filtros ingresados y/o seleccionados en el formulario de 
 * búsqueda de expedientes como: Nro Expediente, Organo, Tipo Proceso, etc.
 * Implementa la interface {@link Serializable}
 * @author hildebrando
 * @version 1.0
 */
public class ConsultaExpedienteMB implements Serializable {

	public static Logger logger = Logger.getLogger(ConsultaExpedienteMB.class);
		
	private String nroExpeOficial;
	private int proceso;
	private List<Proceso> procesos;
	private int via;
	private List<Via> vias;
	private Organo organo;
	private int estado;
	private List<EstadoExpediente> estados;
	private Recurrencia recurrencia;
	private Materia materia;
	private String claveBusqueda;
	private ExpedienteDataModel expedienteDataModel;
	private Expediente selectedExpediente;
	
	private Involucrado demandante;
	
	private List<Involucrado> involucradosTodos;
	
	private ConsultaService consultaService;
	
	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}
	
	/**
	 * Metodo usado para mostrar un filtro autocompletable de demandantes
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Involucrado}
	 * **/
	@SuppressWarnings("unchecked")
	public List<Involucrado> completeDemandante(String query) {
		List<Involucrado> resultsInvs = new ArrayList<Involucrado>();
		List<Persona> resultsPers = new ArrayList<Persona>();

		List<Involucrado> involucradosTemp = new ArrayList<Involucrado>();
		GenericDao<Involucrado, Object> involucradoDAO = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Involucrado.class);
		filtro.add(Restrictions.eq("rolInvolucrado.idRolInvolucrado", SglConstantes.COD_ROL_INVOLUCRADO_DEMANDANTE));
		
		try {
			involucradosTemp = involucradoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			logger.debug("Error al obtener los datos de involucrados");
		}

		involucradosTodos = new ArrayList<Involucrado>();
		
		for (Involucrado inv : involucradosTemp) {

			if (inv.getPersona().getNombreCompleto().toUpperCase().contains(query.toUpperCase())) {
				
				involucradosTodos.add(inv);
				
				if(!resultsPers.contains(inv.getPersona())){
					
					
					resultsInvs.add(inv);
					resultsPers.add(inv.getPersona());
					
				}
			}
		}

		return resultsInvs;
	}
	
	public void limpiarCampos(ActionEvent ae){		
		setNroExpeOficial("");
		setProceso(0);
		setVia(0);
		setDemandante(null);
		setOrgano(null);
		setEstado(0);
		setRecurrencia(null);
		setMateria(null);		
	}
	
	public String reset(){		
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
	
	public String btnNuevo()
	{
		return "/faces/paginas/registroExpediente.xhtml";
	}
	
	public ConsultaExpedienteMB() {
		
	}
	
	@PostConstruct
	@SuppressWarnings("unchecked")
	private void cargarCombos() {
		logger.debug("Cargando combos para consulta de expediente");		
		demandante = new Involucrado();
		materia = new Materia();		
		involucradosTodos = new ArrayList<Involucrado>();		
		procesos = consultaService.getProcesos();
		estados = consultaService.getEstadoExpedientes();
		
	}
	
	/**
	 * Metodo usado para que al hacer click derecho en un expediente pueda ser  
	 * seleccionado y mostrado MODO LECTURA (Read Only) con la información completa.
	 * */
	public String verExpediente() {		
		logger.debug("[Ver-Expediente]:" + getSelectedExpediente().getNumeroExpediente());
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);		
		Usuario usuario= (Usuario) session1.getAttribute("usuario");		
		GenericDao<com.hildebrando.legal.modelo.Usuario, Object> usuarioDAO = (GenericDao<com.hildebrando.legal.modelo.Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroIni = Busqueda.forClass(com.hildebrando.legal.modelo.Usuario.class);
		filtroIni.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
		List<com.hildebrando.legal.modelo.Usuario> usuarios = new ArrayList<com.hildebrando.legal.modelo.Usuario>();

		try {
			usuarios = usuarioDAO.buscarDinamico(filtroIni);
		} catch (Exception ex) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los usuarios en verExpediente(): "+ex);
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
	
	/**
	 * Metodo usado para que al hacer click derecho en un expediente pueda ser  
	 * seleccionado y mostrado MODO EDICIÓN  con la información completa.
	 * */
	public String editarExpediente() {		
		logger.debug("[Editar-expediente]:" + getSelectedExpediente().getNumeroExpediente());
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);		
		Usuario usuario= (Usuario) session1.getAttribute("usuario");
		GenericDao<com.hildebrando.legal.modelo.Usuario, Object> usuarioDAO = (GenericDao<com.hildebrando.legal.modelo.Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroIni = Busqueda.forClass(com.hildebrando.legal.modelo.Usuario.class);
		filtroIni.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
		List<com.hildebrando.legal.modelo.Usuario> usuarios = new ArrayList<com.hildebrando.legal.modelo.Usuario>();

		try {
			usuarios = usuarioDAO.buscarDinamico(filtroIni);
		} catch (Exception ex) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los usuarios en editarExpediente(): "+ex);
		}

		if (usuarios != null) {
			if (usuarios.size() != 0) {				
			}
		}		  
		 
		if(!usuario.getPerfil().getNombre().equalsIgnoreCase("Administrador"))
		{
			if(usuarios.get(0).getIdUsuario() == getSelectedExpediente().getUsuario().getIdUsuario())
			{	
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
	 
	/**
	 * Metodo usado para consultar un listado de organos que serán
	 * utilizados para mostrar un filtro autocompletable de organos
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Organo}
	 * **/
	public List<Organo> completeOrgano(String query) {
		List<Organo> results = new ArrayList<Organo>();
		List<Organo> organos = consultaService.getOrganos();
		if (organos != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "organos d es:["+ organos.size() + "]. ");
		}
		
		for (Organo organo : organos) 
		{
			//String descripcion = organo.getNombre().toUpperCase() + " (" + organo.getUbigeo().getDistrito().toUpperCase() + ", "
			//		+ organo.getUbigeo().getProvincia().toUpperCase() + ", " + organo.getUbigeo().getDepartamento().toUpperCase() + ")";

			String descripcion = "".concat(organo.getNombre()!=null ? organo.getNombre().toUpperCase():"").concat("(")
					.concat(organo.getUbigeo().getDistrito()!=null ? organo.getUbigeo().getDistrito().toUpperCase():"") 
					.concat(", ").concat(organo.getUbigeo().getProvincia()!=null ?organo.getUbigeo().getProvincia().toUpperCase():"")
					.concat(", ").concat(organo.getUbigeo().getDepartamento()!=null ? organo.getUbigeo().getDepartamento().toUpperCase(): "").concat( ")");
			
			if (descripcion.toUpperCase().contains(query.toUpperCase())) 
			{
				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}
		return results;
	}
	
	/**
	 * Metodo usado para mostrar un filtro autocompletable de recurrencias
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Recurrencia}
	 * **/
	public List<Recurrencia> completeRecurrencia(String query) {
		List<Recurrencia> recurrencias = consultaService.getRecurrencias();
		List<Recurrencia> results = new ArrayList<Recurrencia>();
		for (Recurrencia rec : recurrencias) 
		{
			if (rec.getNombre().toUpperCase().contains(query.toUpperCase())) 
			{
				results.add(rec);
			}
		}
		return results;
	}
	
	/**
	 * Metodo usado para mostrar un filtro autocompletable de materias
	 * @param query Representa el query
	 * @return List Representa la lista de {@link Materia}
	 * **/
	public List<Materia> completeMaterias(String query) {	
		List<Materia> results = new ArrayList<Materia>();
		List<Materia> materias = consultaService.getMaterias();
		for (Materia mat : materias) 
		{	
			String descripcion = " " + mat.getDescripcion();			
			if (descripcion.toLowerCase().contains(query.toLowerCase())) 
			{
				results.add(mat);
			}
		}
		return results;
	}

	/**
	 * Metodo que se ejecuta al seleccionar el proceso en el formulario de 
	 * consulta de expedientes.
	 * */
	public void cambioProceso() {
		if (getProceso() != 0) {
			vias = consultaService.getViasByProceso(getProceso());
		} 
		else {	
			vias = new ArrayList<Via>();
		}
	}
		
	@SuppressWarnings("unchecked")
	public void buscarExpedientes(ActionEvent e)
	{	
		logger.debug(" === Buscando expedientes ===");
		
		List<Expediente> expedientes = new ArrayList<Expediente>();
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Expediente.class);
		filtro.add(Restrictions.isNull("expediente.idExpediente")).addOrder(Order.desc("idExpediente"));
		
		GenericDao<Cuantia, Object> cuantiaDAO = (GenericDao<Cuantia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Cuantia.class);

		if (getNroExpeOficial().compareTo("")!=0){
			logger.debug("[BUSQ_EXP]-NroExp: "+ getNroExpeOficial());
			filtro.add(Restrictions.like("numeroExpediente","%" + getNroExpeOficial().trim() + "%").ignoreCase());
		}

		if(getProceso()!=0)
		{
			logger.debug("[BUSQ_EXP]-Proceso: " + getProceso());
			filtro.add(Restrictions.eq("proceso.idProceso", getProceso()));
		}
		
		if(getVia()!=0)
		{
			logger.debug("[BUSQ_EXP]-Via: "+ getVia());				
			filtro.add(Restrictions.eq("via.idVia", getVia()));
		}
		
		if(demandante!= null)
		{	
			List<Integer> idInvolucradosEscojidos = new ArrayList<Integer>();
			
			for(Involucrado inv: involucradosTodos)
			{	
				if(inv.getPersona().getIdPersona() == demandante.getPersona().getIdPersona())
				{	
					idInvolucradosEscojidos.add(inv.getIdInvolucrado());
				}
			}
			
			List<Long> idExpedientes = new ArrayList<Long>();
			InvolucradoDao<Object, Object> service = (InvolucradoDao<Object, Object>) SpringInit.getApplicationContext().getBean("involEspDao");
			
			try {
				idExpedientes = service.obtenerExpedientes(idInvolucradosEscojidos);
			} catch (Exception ex) {
			
				ex.printStackTrace();
			}
			
			filtro.add(Restrictions.in("idExpediente", idExpedientes));
		}
		
		if(getOrgano()!= null)
		{
			logger.debug("[BUSQ_EXP]-Organo: "+ getOrgano().getIdOrgano());	
			filtro.add(Restrictions.eq("organo", getOrgano()));
		}
		
		if(getEstado()!=0)
		{
			logger.debug("[BUSQ_EXP]-EstadoExp:  "+ getEstado());	
			filtro.add(Restrictions.eq("estadoExpediente.idEstadoExpediente", getEstado()));
		}
		
		if(getRecurrencia()!=null)
		{
			logger.debug("[BUSQ_EXP]-Recurrencia:  "+  getRecurrencia().getIdRecurrencia());
			filtro.add(Restrictions.eq("recurrencia", getRecurrencia()));
		}
		
		if(materia!=null)
		{	
			List<Cuantia> cuantias= new ArrayList<Cuantia>();
			try {
				cuantias = cuantiaDAO.buscarDinamico(filtro2.add(Restrictions.eq("materia.idMateria", materia.getIdMateria())));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if(cuantias.size()>0)
			{	
				List<Long> idExpe= new ArrayList<Long>();
				for(Cuantia c: cuantias)
				{	
					idExpe.add(c.getExpediente().getIdExpediente());		
				}
				filtro.add(Restrictions.in("idExpediente", idExpe));
			}
		}
		
		try {
			expedientes = expedienteDAO.buscarDinamico(filtro);
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+" de expedientes encontrados es: [ "+ expedientes.size()+" ]");
			
		} catch (Exception e1) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"expedientes: "+e1);
		}

		expedienteDataModel = new ExpedienteDataModel(expedientes);
		logger.debug("== saliendo de buscarExpedientes() ===");

		//Limpiar campos de busqueda
		setNroExpeOficial("");
		setProceso(0);
		setVia(0);
		setDemandante(null);
		setOrgano(null);
		setEstado(0);
		setRecurrencia(null);
		setMateria(null);
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

	public Involucrado getDemandante() {
		return demandante;
	}

	public void setDemandante(Involucrado demandante) {
		this.demandante = demandante;
	}

	public List<Involucrado> getInvolucradosTodos() {
		return involucradosTodos;
	}

	public void setInvolucradosTodos(List<Involucrado> involucradosTodos) {
		this.involucradosTodos = involucradosTodos;
	}
}
