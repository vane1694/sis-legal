
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
import com.hildebrando.legal.modelo.Cuantia;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Rol;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.view.ExpedienteDataModel;
import com.hildebrando.legal.view.InvolucradoDataModel;

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
	private int territorio;
	private List<Territorio> territorios;
	private Recurrencia recurrencia;
	private Materia materia;
	private String claveBusqueda;
	private ExpedienteDataModel expedienteDataModel;
	private Expediente selectedExpediente;


	private Involucrado demandante;
	
	private List<Involucrado> involucradosTodos;
	
	private ConsultaService consultaService;
	
//	Cambios everis
	private InvolucradoDataModel involucradoDataModel;
	private com.hildebrando.legal.modelo.Usuario responsable;
	private Oficina oficina;
	private int rol;
	private List<RolInvolucrado> rolInvolucrados;
	private List<Rol> roles;
	private List<String> rolInvolucradosString;
	private Involucrado involucrado;
	private Persona persona;
	private String contador;
	
	
	
	public List<Rol> getRoles() {
		return roles;
	}


	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}


	public String getContador() {
		return contador;
	}


	public void setContador(String contador) {
		this.contador = contador;
	}


	public int getRol() {
		return rol;
	}


	public void setRol(int rol) {
		this.rol = rol;
	}


	public int getTerritorio() {
		return territorio;
	}


	public void setTerritorio(int territorio) {
		this.territorio = territorio;
	}

	
	public List<Territorio> getTerritorios() {
		return territorios;
	}


	public void setTerritorios(List<Territorio> territorios) {
		this.territorios = territorios;
	}


	public InvolucradoDataModel getInvolucradoDataModel() {
		return involucradoDataModel;
	}


	public void setInvolucradoDataModel(InvolucradoDataModel involucradoDataModel) {
		this.involucradoDataModel = involucradoDataModel;
	}
	
	public Involucrado getInvolucrado() {
		return involucrado;
	}


	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}


	public Persona getPersona() {
		return persona;
	}


	public void setPersona(Persona persona) {
		this.persona = persona;
	}


	public Oficina getOficina() {
		return oficina;
	}


	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}


	public List<RolInvolucrado> getRolInvolucrados() {
		return rolInvolucrados;
	}


	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
	}


	public List<String> getRolInvolucradosString() {
		return rolInvolucradosString;
	}


	public void setRolInvolucradosString(List<String> rolInvolucradosString) {
		this.rolInvolucradosString = rolInvolucradosString;
	}


	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}
	
	
	public com.hildebrando.legal.modelo.Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(com.hildebrando.legal.modelo.Usuario responsable) {
		this.responsable = responsable;
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
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+" los involucrados: ",e);
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
		setTerritorio(0);
		setResponsable(null);
		setOficina(null);
		setOrgano(null);
		setRol(0);
		setRecurrencia(null);
		setMateria(null);
		setRolInvolucrados(null);
		setResponsable(null);
		setTerritorio(0);
		setOficina(null);
		setRol(0);
	}
	
	public String reset(){		
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		logger.debug("Recuperando usuario..");
		
		Usuario usuario= (Usuario) session1.getAttribute("usuario");
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
		territorios = consultaService.getTerritorios();
		involucrado = new Involucrado();
		involucradoDataModel = new InvolucradoDataModel(new ArrayList<Involucrado>());
		rolInvolucrados = consultaService.getRolInvolucrados();
//		roles = consultaService.getRoles();
		
//		rolInvolucradosString = new ArrayList<String>();
//		for (RolInvolucrado r : rolInvolucrados)
//			rolInvolucradosString.add(r.getNombre());
//		
//		
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
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los usuarios en verExpediente(): ",ex);
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
		logger.debug("========== editarExpediente() ===========");
		logger.debug("[EDIT_EXP]-NroExpediente:" + getSelectedExpediente().getNumeroExpediente());
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
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"los usuarios en editarExpediente(): ",ex);
		}

		if (usuarios != null) {
			if (usuarios.size() != 0) {	
				logger.debug("[EDIT_EXP]-Usuario: "+usuario.getPerfil().getNombre());
				logger.debug("[EDIT_EXP]-Usuario: "+usuarios.get(0).getIdUsuario());
			}
		}		  
		 
		if(!usuario.getPerfil().getNombre().equalsIgnoreCase(SglConstantes.ADMINISTRADOR))
		{
			if(usuarios.get(0).getIdUsuario() == getSelectedExpediente().getUsuario().getIdUsuario())
			{	
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			    HttpSession session = (HttpSession) context.getSession(true);
			    session.setAttribute("numeroExpediente", getSelectedExpediente().getNumeroExpediente());
			    session.setAttribute("usuario", usuario);
			    session.setAttribute("modo", SglConstantes.MODO_EDICION);
			    logger.debug("=> redirect actualSeguiExpediente");
			    return "actualSeguiExpediente.xhtml?faces-redirect=true";
				
			}else{				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Información", "No es responsable del expediente " + getSelectedExpediente().getNumeroExpediente() +"!!!" );
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
		logger.debug("=== completeOrgano === ");
		List<Organo> results = new ArrayList<Organo>();
		List<Organo> organos = consultaService.getOrganos();
		if(organos!=null){
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"organos en ConsultarExp: "+organos.size());
		}
		
		for (Organo organo : organos) 
		{ 
			String descripcion = "".concat(organo.getNombre() != null ? organo.getNombre().toUpperCase() : "")
					.concat("(").concat(organo.getUbigeo() != null ? organo
					.getUbigeo().getDistrito() : "")
					.concat(", ").concat(organo.getUbigeo() != null ? organo
					.getUbigeo().getProvincia().toUpperCase() : "")
					.concat(", ").concat(organo.getUbigeo() != null ? organo
					.getUbigeo().getDepartamento().toUpperCase() : "").concat(")");
			
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
	 * Metodo usado para mostrar un filtro autocompletable de Responsable
	 * @param query Representa el query
	 * @return List Representa la lista de responsable
	 * **/
	public List<com.hildebrando.legal.modelo.Usuario> completeResponsable(String query) {
		List<com.hildebrando.legal.modelo.Usuario> results = new ArrayList<com.hildebrando.legal.modelo.Usuario>();

		List<com.hildebrando.legal.modelo.Usuario> usuarios = consultaService.getUsuarios();

		if (usuarios != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "usuarios es:["+ usuarios.size() + "]. ");
		}

		for (com.hildebrando.legal.modelo.Usuario usuario : usuarios) {

			if (usuario.getNombres().toUpperCase()
					.contains(query.toUpperCase())
					|| usuario.getApellidoPaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getApellidoMaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getCodigo().toUpperCase()
							.contains(query.toUpperCase())) {

				usuario.setNombreDescripcion(usuario.getCodigo() + " - "
						+ usuario.getNombres() + " "
						+ usuario.getApellidoPaterno() + " "
						+ usuario.getApellidoMaterno());

				results.add(usuario);
			}
		}
		return results;
	}
	
	
	/**
	 * Metodo encargado de consultar la lista de oficinas
	 * para ser mostrados en un campo autocompletable
	 * @param query Valor a consultar
	 * @return results Lista de resultado del tipo {@link Oficina}
	 * **/	
	public List<Oficina> completeOficina(String query) {
		List<Oficina> results = new ArrayList<Oficina>();
		List<Oficina> oficinas = consultaService.getOficinas(null);

		if (oficinas != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "oficinas es:["+ oficinas.size() + "]. ");
		}

		for (Oficina oficina : oficinas) {
			if (oficina.getTerritorio() != null) {
				String texto = oficina.getCodigo()
						.concat(" ").concat(oficina.getNombre() != null ? oficina.getNombre().toUpperCase() : "").concat(" (")
						.concat(oficina.getTerritorio().getDescripcion() != null ? oficina.getTerritorio().getDescripcion().toUpperCase(): "").concat(")");
			
				if (texto.contains(query.toUpperCase())) {
					oficina.setNombreDetallado(texto);
					results.add(oficina);
				}
			}
		}
		return results;
	}
	
	
	/**
	 * Metodo encargado de consultar la lista de personas
	 * para ser mostrados en un campo autocompletable
	 * @param query Valor a consultar
	 * @return results Lista de resultado del tipo {@link Persona}
	 * **/
	public List<Persona> completePersona(String query) {
		logger.debug("=== completePersona ===");
		List<Persona> results = new ArrayList<Persona>();
		List<Persona> personas = consultaService.getPersonas();
		if (personas != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "personas es:["+ personas.size() + "]. ");
		}
		
		for (Persona pers : personas) {
			String nombreCompletoMayuscula = ""
					.concat(pers.getNombres() != null ? pers.getNombres().toUpperCase() : "").concat(" ")
					.concat(pers.getApellidoPaterno() != null ? pers.getApellidoPaterno().toUpperCase() : "")
					.concat(" ").concat(pers.getApellidoMaterno() != null ? pers.getApellidoMaterno().toUpperCase() : "");

			if (nombreCompletoMayuscula.contains(query.toUpperCase())) {
				pers.setNombreCompletoMayuscula(nombreCompletoMayuscula);
				results.add(pers);
			}
		}
		return results;
	}

	/**
	 * Metodo que se ejecuta al seleccionar el proceso en el formulario de 
	 * consulta de expedientes.
	 * */
	public void cambioProceso() {
		try{
			if (getProceso() != 0) {
				vias = consultaService.getViasByProceso(getProceso());
			} 
			else {	
				vias = new ArrayList<Via>();
			}
		}catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en cambioProceso(): ",e);
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
		//Nro de expediente
		if (getNroExpeOficial().compareTo("")!=0){
			logger.debug("[BUSQ_EXP]-NroExp: "+ getNroExpeOficial());
			filtro.add(Restrictions.like("numeroExpediente","%" + getNroExpeOficial().trim() + "%").ignoreCase());
		}
		//Proceso
		if(getProceso()!=0)
		{
			logger.debug("[BUSQ_EXP]-Proceso: " + getProceso());
			filtro.add(Restrictions.eq("proceso.idProceso", getProceso()));
		}
		//Via
		if(getVia()!=0)
		{
			logger.debug("[BUSQ_EXP]-Via: "+ getVia());				
			filtro.add(Restrictions.eq("via.idVia", getVia()));
		}
		//Responsable
		if(getResponsable()!=null){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getResponsable()="+getResponsable().getIdUsuario());
			filtro.add(Restrictions.eq("usuario",getResponsable()));
		}
		//Territorio
		Busqueda filtroOficina = Busqueda.forClass(Oficina.class);
		GenericDao<Oficina, Object> oficinaDao = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		if(getTerritorio()!=0){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getTerritorio()="+getTerritorio());
			filtroOficina.add(Restrictions.eq("territorio.idTerritorio",getTerritorio()));
			List<Oficina> oficinas = new ArrayList<Oficina>();
			try{
				oficinas = oficinaDao.buscarDinamico(filtroOficina);
			}catch(Exception e3){
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Oficinas-Territorios: ",e3);
			}
			filtro.add(Restrictions.in("oficina", oficinas));
			
			
		}
		//Oficina
		if(getOficina()!=null){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getOficina()="+getOficina().getIdOficina());
			filtro.add(Restrictions.eq("oficina",getOficina()));
		}
		//RolInvolucrado
		Busqueda filtroRolInv = Busqueda.forClass(Involucrado.class);
		GenericDao<Involucrado, Object> usuarioDao = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		if(getRol()!=0){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getRolInvolucrados() ="+getRol());
			filtroRolInv.add(Restrictions.eq("rolInvolucrado.idRolInvolucrado",getRol()));
			List<Involucrado> involucrados = new ArrayList<Involucrado>();
			try{
				involucrados = usuarioDao.buscarDinamico(filtroRolInv);
			}catch(Exception e4){
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Usuario-Rol: ",e4);
			}
			if(involucrados.size()>0){
				List<Long> idExpe= new ArrayList<Long>();
				for (Involucrado inv: involucrados) {
					idExpe.add(inv.getExpediente().getIdExpediente());
				}
				filtro.add(Restrictions.in("idExpediente", idExpe));
			}
		}
		
		//Persona
		Busqueda filtroRolInvol = Busqueda.forClass(Involucrado.class);
		GenericDao<Involucrado, Object> usuarioInvolDao = (GenericDao<Involucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		if(getPersona()!=null){
			logger.info("ConsultaExpedienteMB-->buscarExpedientes(ActionEvent e): getPersona() ="+getPersona().getIdPersona());
			filtroRolInvol.add(Restrictions.eq("persona.idPersona",getPersona().getIdPersona()));
			List<Involucrado> involucrados = new ArrayList<Involucrado>();
			try{
				involucrados = usuarioInvolDao.buscarDinamico(filtroRolInvol);
			}catch(Exception e4){
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Usuario-Rol: ",e4);
			}
			if(involucrados.size()>0){
				List<Long> idExpe= new ArrayList<Long>();
				for (Involucrado inv: involucrados) {
					idExpe.add(inv.getExpediente().getIdExpediente());
				}
				filtro.add(Restrictions.in("idExpediente", idExpe));
			}
		}
		//Demandante
//		if(demandante!= null)
//		{	
//			List<Integer> idInvolucradosEscojidos = new ArrayList<Integer>();
//			
//			for(Involucrado inv: involucradosTodos)
//			{	
//				if(inv.getPersona().getIdPersona() == demandante.getPersona().getIdPersona())
//				{	
//					idInvolucradosEscojidos.add(inv.getIdInvolucrado());
//				}
//			}
//			
//			List<Long> idExpedientes = new ArrayList<Long>();
//			InvolucradoDao<Object, Object> service = (InvolucradoDao<Object, Object>) SpringInit.getApplicationContext().getBean("involEspDao");
//			
//			try {
//				idExpedientes = service.obtenerExpedientes(idInvolucradosEscojidos);
//			} catch (Exception ex) {
//			
//				ex.printStackTrace();
//			}
//			
//			filtro.add(Restrictions.in("idExpediente", idExpedientes));
//		}
		//Organo
		if(getOrgano()!= null)
		{
			logger.debug("[BUSQ_EXP]-Organo: "+ getOrgano().getIdOrgano());	
			filtro.add(Restrictions.eq("organo", getOrgano()));
		}
		//Estado del expediente
		if(getEstado()!=0)
		{
			logger.debug("[BUSQ_EXP]-EstadoExp:  "+ getEstado());	
			filtro.add(Restrictions.eq("estadoExpediente.idEstadoExpediente", getEstado()));
		}
		//Recurrencia
		if(getRecurrencia()!=null)
		{
			logger.debug("[BUSQ_EXP]-Recurrencia:  "+  getRecurrencia().getIdRecurrencia());
			filtro.add(Restrictions.eq("recurrencia", getRecurrencia()));
		}
		//Materia
		if(materia!=null)
		{	
			List<Cuantia> cuantias= new ArrayList<Cuantia>();
			try {
				cuantias = cuantiaDAO.buscarDinamico(filtro2.add(Restrictions.eq("materia.idMateria", materia.getIdMateria())));
			} catch (Exception e1) {
				logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Cuantias: ",e1);
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
			contador = String.valueOf(expedientes.size());
			contador += " Expediente(es) encontrado(os)";
			if(expedientes!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+" de expedientes encontrados es: [ "+ expedientes.size()+" ]");
			}
		} catch (Exception e1) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"expedientes: ",e1);
		}

		expedienteDataModel = new ExpedienteDataModel(expedientes);
		logger.debug("== saliendo de buscarExpedientes() ===");

		//Limpiar campos de busqueda
		//limpiar();		
	}
	
    public void limpiar(){
    	logger.debug("=== limpiando filtros() consultaExpediente ==");
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
