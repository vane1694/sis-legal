package com.hildebrando.legal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.AbogadoEstudio;
import com.hildebrando.legal.modelo.ActividadProcesal;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Clase;
import com.hildebrando.legal.modelo.ContraCautela;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoCautelar;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Riesgo;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.SituacionCuota;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.TipoCautelar;
import com.hildebrando.legal.modelo.TipoDocumento;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.TipoInvolucrado;
import com.hildebrando.legal.modelo.Ubigeo;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;

public class ConsultaServiceImpl implements ConsultaService {
	
	public static Logger logger = Logger.getLogger(ConsultaServiceImpl.class);

	@Override
	public List getProcesos() {
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		filtroProceso.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		
		try {
			List<Proceso> procesos = procesoDAO.buscarDinamico(filtroProceso);
			return procesos;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getProcesos():"+e);
			return null;
		}
	}
	
	@Override
	public List<Proceso> getProcesos(int idProceso) {
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		filtroProceso.add(Restrictions.eq("idProceso", idProceso));
		filtroProceso.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Proceso> procesos = procesoDAO.buscarDinamico(filtroProceso);
			return procesos;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getProcesos(idProceso):"+e);
			return null;
		}
	}

	@Override
	public List getEstadoExpedientes() {		
		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstadoExpediente = Busqueda.forClass(EstadoExpediente.class);
		filtroEstadoExpediente.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		
		try {
			List<EstadoExpediente> estadoExpedientes = estadosExpedienteDAO.buscarDinamico(filtroEstadoExpediente);
			return estadoExpedientes;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getEstadoExpedientes():"+e);		
			return null;
		}
	}

	@Override
	public List getOrganos() {
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		try {
			List<Organo> organos = organoDAO.buscarDinamico(filtro);
			return organos;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getOrganos():"+e);
			return null;
		}
	}

	@Override
	public List getRecurrencias() {
		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Recurrencia.class);
		filtro.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Recurrencia> recurrencias = recurrenciaDAO.buscarDinamico(filtro);
			return recurrencias;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getRecurrencias():"+e);
			return null;
		}
	}

	@Override
	public List getMaterias() {
		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Materia.class);
		filtro.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Materia>  materias = materiaDAO.buscarDinamico(filtro);
			return materias;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getMaterias():"+e);
			return null;
		}
	}

	@Override
	public List getViasByProceso(int proceso) {
		GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Via.class);
		filtro.add(Restrictions.like("proceso.idProceso", proceso));
		filtro.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Via> vias = viaDao.buscarDinamico(filtro);
			return vias;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getViasByProceso(proceso):"+e);
			return null;
		}
	}

	@Override
	public List getPersonas() {
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Persona.class);
		try {
			List<Persona>  personas = personaDAO.buscarDinamico(filtro);
			return personas;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getPersonas():"+e);
			return null;
		}
	}

	@Override
	public List getOficinas() {
		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Oficina.class);
		filtro.setMaxResults(SglConstantes.CANTIDAD_REGISTROS_MAX);
		try {
			List<Oficina> oficinas = oficinaDAO.buscarDinamico(filtro);
			return oficinas;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getOficinas():"+e);
			return null;
		}
	}

	@Override
	public List getEstudios() {
		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Estudio.class);
		try {
			List<Estudio> estudios = estudioDAO.buscarDinamico(filtro);
			return estudios;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getEstudios():"+e);
			return null;
		}
	}

	@Override
	public List getAbogados() {
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		try {
			List<Abogado> abogados = abogadoDAO.buscarDinamico(filtro);
			return abogados;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getAbogados():"+e);
			return null;
		}
	}

	@Override
	public List getUbigeos() {		
		GenericDao<Ubigeo, Object> ubigeoDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Ubigeo.class);
		filtro.setMaxResults(SglConstantes.CANTIDAD_UBIGEOS);		
		try {
			List<Ubigeo> ubigeos = ubigeoDAO.buscarDinamico(filtro);
			return ubigeos;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getUbigeos():"+e);
			return null;
		}
	}

	@Override
	public List getUsuarios() {
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Usuario.class);		
		try {			
			List<Usuario> usuarios = usuarioDAO.buscarDinamico(filtro);
			return usuarios;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getUsuarios():"+e);
			return null;
		}
	}

	@Override
	public List getInstanciasByVia(int via) {		
		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Instancia.class);
		filtro.add(Restrictions.like("via.idVia", via));
		//filtro.add(Restrictions.eq("prioridad", SglConstantes.PRIORIDAD_DEFAULT_INSTANCIA));
		filtro.add(Restrictions.eq("estado", SglConstantes.ACTIVO)).addOrder(Order.asc("prioridad"));;
		try {
			List<Instancia> instancias = instanciaDao.buscarDinamico(filtro);
			return instancias;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getInstanciasByVia(via):"+e);
			return null;
		}
	}

	@Override
	public List getAbogadosByAbogadoEstudio(Abogado abogado, Estudio estudio) {
		List<AbogadoEstudio> abogadoEstudioBD = new ArrayList<AbogadoEstudio>();
		List<Abogado> results = new ArrayList<Abogado>();
		
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		Busqueda filtro2 = Busqueda.forClass(AbogadoEstudio.class);

		filtro2.add(Restrictions.eq("estudio", estudio));
		filtro2.add(Restrictions.like("estado",SglConstantes.ACTIVO));		
		
		try {
			
			abogadoEstudioBD = abogadoEstudioDAO.buscarDinamico(filtro2);
			if(abogadoEstudioBD!=null){
				logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA+"AbogEstudio es:["+abogadoEstudioBD.size()+"]");
			}
			
			List<Integer> idAbogados= new ArrayList<Integer>();
			
			if(abogadoEstudioBD.size() > 0){
				for(AbogadoEstudio abogadoEstudio: abogadoEstudioBD){
					//logger.debug("idabogado "+ abogadoEstudio.getAbogado().getIdAbogado());
					idAbogados.add(abogadoEstudio.getAbogado().getIdAbogado());
				}
				filtro.add(Restrictions.in("idAbogado",idAbogados));
			}			
		} catch (Exception e1) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getAbogadosByAbogadoEstudio(abg, estud):"+e1);
		}		
		
		logger.debug("1");
		if(abogado.getRegistroca().compareTo("")!=0){			
			logger.debug("[FILTRO]-abogado.getRegistroca():"+ abogado.getRegistroca());
			filtro.add(Restrictions.eq("registroca", abogado.getRegistroca()));			
		}
		logger.debug("2");
		if(abogado.getDni()!=null){ //TODO [22.03] Linea agregada, sino sale NullPointerException
			if(abogado.getDni()!=0){
				logger.debug("[FILTRO]-abogado.getDni():"+ abogado.getDni());			
				filtro.add(Restrictions.eq("dni", abogado.getDni()));			
			}	
		}
		
		logger.debug("3");
		if(abogado.getNombres().compareTo("")!=0){			
			logger.debug("[FILTRO]-abogado.getNombres():"+ abogado.getNombres());
			filtro.add(Restrictions.like("nombres", "%"+abogado.getNombres()+"%").ignoreCase());
		}
		logger.debug("4");
		if(abogado.getApellidoPaterno().compareTo("")!=0){			
			logger.debug("[FILTRO]-abogado.getApellidoPaterno():"+ abogado.getApellidoPaterno());
			filtro.add(Restrictions.like("apellidoPaterno", "%"+abogado.getApellidoPaterno()+"%").ignoreCase());
		}
		logger.debug("5");
		if(abogado.getApellidoMaterno().compareTo("")!=0){			
			logger.debug("[FILTRO]-abogado.getApellidoMaterno():"+abogado.getApellidoMaterno());
			filtro.add(Restrictions.like("apellidoMaterno", "%"+abogado.getApellidoMaterno()+"%"));
		}
		logger.debug("6");
		if(abogado.getTelefono().compareTo("")!=0){			
			logger.debug("[FILTRO]-abogado.getTelefono():"+ abogado.getTelefono());
			filtro.add(Restrictions.eq("telefono", abogado.getTelefono()));
		}
		logger.debug("7");
		if(abogado.getCorreo().compareTo("")!=0){			
			logger.debug("[FILTRO]-abogado.getCorreo():"+ abogado.getCorreo());
			filtro.add(Restrictions.like("correo", "%"+abogado.getCorreo()+"%").ignoreCase());
		}
		logger.debug("8");
		try {
			results= abogadoDAO.buscarDinamico(filtro);	
			if(results!=null){
				logger.debug("Si se encontraron AbogadoEstudio en BD: "+results.size());
			}
			return results;
			
		} catch (Exception e1) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getAbogadosByAbogadoEstudio():"+e1);
			return null;
		}	
		
	}

	@Override
	public List getPersonasByPersona(Persona persona) {
		
		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Persona.class);
		
		if(persona.getClase().getIdClase()!= 0){			
			logger.debug("filtro "+ persona.getClase().getIdClase()  +" persona - clase");
			filtro.add(Restrictions.eq("clase.idClase", persona.getClase().getIdClase()));
		}
		
		if(persona.getTipoDocumento().getIdTipoDocumento()!= 0){			
			logger.debug("filtro "+ persona.getTipoDocumento().getIdTipoDocumento() +" persona - tipo documento");
			filtro.add(Restrictions.eq("tipoDocumento.idTipoDocumento", persona.getTipoDocumento().getIdTipoDocumento()));
		}
		
		if(persona.getNumeroDocumento()!= 0){			
			logger.debug("filtro "+ persona.getNumeroDocumento()  +" persona - numero documento");
			filtro.add(Restrictions.eq("numeroDocumento", persona.getNumeroDocumento()));
		}
		
		if(persona.getCodCliente()!= 0){			
			logger.debug("filtro "+ persona.getCodCliente()  +" persona - cod cliente");
			filtro.add(Restrictions.eq("codCliente", persona.getCodCliente()));
		}
		
		if(persona.getNombres().compareTo("")!=0){			
			logger.debug("filtro "+ persona.getNombres() +" persona - nombres");
			filtro.add(Restrictions.like("nombres","%"+persona.getNombres()+"%").ignoreCase());
		}
		
		if(persona.getApellidoPaterno().compareTo("")!=0){			
			logger.debug("filtro "+ persona.getApellidoPaterno()  +" persona - apellido paterno");
			filtro.add(Restrictions.like("apellidoPaterno", "%"+persona.getApellidoPaterno()+"%").ignoreCase());
		}
		
		if(persona.getApellidoMaterno().compareTo("")!=0){			
			logger.debug("filtro "+ persona.getApellidoMaterno()  +" persona - apellido materno");
			filtro.add(Restrictions.like("apellidoMaterno",  "%"+persona.getApellidoMaterno()+"%").ignoreCase());
		}
		
		try {
			personas = personaDAO.buscarDinamico(filtro);
			return personas;
		} catch (Exception e2) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getPersonasByPersona(pers):"+e2);
			return null;
		}
	}

	@Override
	public List getOrganosByOrgano(Organo organo) 
	{
		logger.debug("=== getOrganosByOrgano() ===");
		List<Organo> organos = new ArrayList<Organo>();		
		//organos = expedienteService.buscarOrganos(organo);
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		if(organo.getEntidad().getIdEntidad()!= 0)
		{
				logger.debug("[FILTRO-ORG]-Entidad:"+ organo.getEntidad().getIdEntidad());			
				filtro.add(Restrictions.eq("entidad.idEntidad", organo.getEntidad().getIdEntidad()));
		}
				
		if(organo.getNombre().compareTo("")!=0)
		{
				logger.debug("[FILTRO-ORG]-Nombre:"+ organo.getNombre());
				filtro.add(Restrictions.like("nombre", "%"+organo.getNombre()+"%").ignoreCase());
		}	
		
		if(organo.getUbigeo()!= null)
		{
			logger.debug("[FILTRO-ORG]-UbigeoDistrito:"+organo.getUbigeo().getCodDist());
			filtro.add(Restrictions.eq("ubigeo.codDist", organo.getUbigeo().getCodDist()));
		}
				
		try {
			organos = organoDAO.buscarDinamico(filtro);
			if(organos!=null){
				logger.debug("Si se encontraron organos en BD: "+organos.size());
			}
			return organos;
			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getOrganosByOrgano(org):"+e);
			return null;
		}
	}
	
	@Override
	public List getOrganosByOrganoEstricto(Organo organo) {
		List<Organo> organos = new ArrayList<Organo>();
		
		//organos = expedienteService.buscarOrganos(organo);
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		if(organo.getEntidad().getIdEntidad()!= 0){
			logger.debug("filtro "+ organo.getEntidad().getIdEntidad()  +" organo - entidad");			
			filtro.add(Restrictions.eq("entidad.idEntidad", organo.getEntidad().getIdEntidad()));
		}
		
		if(organo.getNombre().compareTo("")!=0){
			logger.debug("filtro "+ organo.getNombre() +" organo - nombre");
			filtro.add(Restrictions.eq("nombre", organo.getNombre()));
		}
		
		if(organo.getUbigeo()!= null){
			logger.debug("filtro "+organo.getUbigeo().getCodDist()+" organo - territorio");
			filtro.add(Restrictions.eq("ubigeo.codDist", organo.getUbigeo().getCodDist()));
		}
		
		try {
			organos = organoDAO.buscarDinamico(filtro);
			return organos;
			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getOrganosByOrganoEstricto(org):"+e);
			return null;
		}
	}

	@Override
	public List getAbogadosByAbogado(Abogado abogado) {
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		filtro.add(Restrictions.eq("dni", abogado.getDni()));
		filtro.add(Restrictions.eq("nombres", abogado.getNombres()));
		filtro.add(Restrictions.eq("apellidoPaterno", abogado.getApellidoPaterno()));
		filtro.add(Restrictions.eq("apellidoMaterno", abogado.getApellidoMaterno()));
		
		try {
			List<Abogado> abogadosBD = abogadoDAO.buscarDinamico(filtro);
			return abogadosBD;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getAbogadosByAbogado(abog):"+e);
			return null;
		}
	}

	@Override
	public List getPersonasByPersonaEstricto(Persona persona) {
		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Persona.class);
		
		if(persona.getClase().getIdClase()!= 0){
			logger.debug("filtro "+ persona.getClase().getIdClase()  +" persona - clase");
			filtro.add(Restrictions.eq("clase.idClase", persona.getClase().getIdClase()));
		}
		
		if(persona.getTipoDocumento().getIdTipoDocumento()!= 0){
			logger.debug("filtro "+ persona.getTipoDocumento().getIdTipoDocumento() +" persona - tipo documento");
			filtro.add(Restrictions.eq("tipoDocumento.idTipoDocumento", persona.getTipoDocumento().getIdTipoDocumento()));
		}
		
		if(persona.getNumeroDocumento()!= 0){
			logger.debug("filtro "+ persona.getNumeroDocumento()  +" persona - numero documento");
			filtro.add(Restrictions.eq("numeroDocumento", persona.getNumeroDocumento()));
		}
		
		if(persona.getCodCliente()!= 0){
			logger.debug("filtro "+ persona.getCodCliente()  +" persona - cod cliente");
			filtro.add(Restrictions.eq("codCliente", persona.getCodCliente()));
		}
		
		if(persona.getNombres().compareTo("")!=0){
			logger.debug("filtro "+ persona.getNombres() +" persona - nombres");
			filtro.add(Restrictions.eq("nombres", persona.getNombres()));
		}
		
		if(persona.getApellidoPaterno().compareTo("")!=0){
			logger.debug("filtro "+ persona.getApellidoPaterno()  +" persona - apellido paterno");
			filtro.add(Restrictions.eq("apellidoPaterno", persona.getApellidoPaterno()));
		}
		
		if(persona.getApellidoMaterno().compareTo("")!=0){
			logger.debug("filtro "+ persona.getApellidoMaterno()  +" persona - apellido materno");
			filtro.add(Restrictions.eq("apellidoMaterno", persona.getApellidoMaterno()));
		}
		
		try {
			personas = personaDAO.buscarDinamico(filtro);
			return personas;
		} catch (Exception e2) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getPersonasByPersonaEstricto(pers):"+e2);
			return null;
		}
	}

	@Override
	public List getTipoExpedientes() {
		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoExpediente = Busqueda.forClass(TipoExpediente.class);
		filtroTipoExpediente.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<TipoExpediente> tipoExpedientes = tipoExpedienteDAO.buscarDinamico(filtroTipoExpediente);
			return tipoExpedientes;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getTipoExpedientes():"+e);		
			return null;
		}
	}

	@Override
	public List getEntidads() {
		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEntidad = Busqueda.forClass(Entidad.class);
		filtroEntidad.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Entidad> entidads = entidadDAO.buscarDinamico(filtroEntidad);
			return entidads;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getEntidads():"+e);
			return null;
		}
	}

	@Override
	public List getCalificacions() {
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroCalificacion = Busqueda.forClass(Calificacion.class);
		filtroCalificacion.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Calificacion> calificacions = calificacionDAO.buscarDinamico(filtroCalificacion);
			return calificacions;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getCalificacions():"+e);
			return null;
		}

	}

	@Override
	public List getTipoHonorarios() {
		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoHonorario = Busqueda.forClass(TipoHonorario.class);
		filtroTipoHonorario.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<TipoHonorario> tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtroTipoHonorario);
			return tipoHonorarios;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getTipoHonorarios():"+e);
			return null;
		}
	}

	@Override
	public List getMonedas() {
		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroMoneda = Busqueda.forClass(Moneda.class);
		filtroMoneda.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Moneda> monedas = monedaDAO.buscarDinamico(filtroMoneda);
			return monedas;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getMonedas():"+e);
			return null;
		}
	}

	@Override
	public List getSituacionHonorarios() {
		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionHonorario = Busqueda.forClass(SituacionHonorario.class);
		filtroSituacionHonorario.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<SituacionHonorario> situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtroSituacionHonorario);
			return situacionHonorarios;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getSituacionHonorarios():"+e);
			return null;
		}
	}

	@Override
	public List getSituacionCuota() {
		GenericDao<SituacionCuota, Object> situacionCuotasDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionCuota = Busqueda.forClass(SituacionCuota.class);
		filtroSituacionCuota.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<SituacionCuota> situacionCuotas = situacionCuotasDAO.buscarDinamico(filtroSituacionCuota);
			return situacionCuotas;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getSituacionCuota():"+e);
			return null;
		}
	}

	@Override
	public List getSituacionInculpados() {
		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionInculpado = Busqueda.forClass(SituacionInculpado.class);
		filtroSituacionInculpado.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<SituacionInculpado> situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtroSituacionInculpado);
			return situacionInculpados;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getSituacionInculpados():"+e);		
			return null;
		}
	}

	@Override
	public List getRolInvolucrados() {
		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRolInvolucrado = Busqueda.forClass(RolInvolucrado.class);
		filtroRolInvolucrado.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<RolInvolucrado> rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtroRolInvolucrado);
			return rolInvolucrados;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getRolInvolucrados():"+e);		
			return null;
		}
	}

	@Override
	public List getTipoInvolucrados() {
		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoInvolucrado = Busqueda.forClass(TipoInvolucrado.class);
		filtroTipoInvolucrado.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<TipoInvolucrado> tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtroTipoInvolucrado);
			return tipoInvolucrados;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getTipoInvolucrados():"+e);		
			return null;
		}
	}

	@Override
	public List getClases() {
		GenericDao<Clase, Object> claseDAO = (GenericDao<Clase, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroClase = Busqueda.forClass(Clase.class);
		//filtroClase.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Clase> clases = claseDAO.buscarDinamico(filtroClase);
			return clases;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getClases():"+e);
			return null;
		}
	}

	@Override
	public List getTipoDocumentos() {
		GenericDao<TipoDocumento, Object> tipoDocumentoDAO = (GenericDao<TipoDocumento, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoDocumento = Busqueda.forClass(TipoDocumento.class);
		filtroTipoDocumento.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<TipoDocumento> tipoDocumentos = tipoDocumentoDAO.buscarDinamico(filtroTipoDocumento);
			return tipoDocumentos;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getTipoDocumentos():"+e);
			return null;
		}
	}

	@Override
	public List getTipoCautelars() {
		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoCautelar = Busqueda.forClass(TipoCautelar.class);
		filtroTipoCautelar.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<TipoCautelar> tipoCautelars = tipoCautelarDAO.buscarDinamico(filtroTipoCautelar);
			return tipoCautelars;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getTipoCautelars():"+e);		
			return null;
		}
	}

	@Override
	public List getContraCautelas() {
		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroContraCautela = Busqueda.forClass(ContraCautela.class);
		try {
			List<ContraCautela> contraCautelas = contraCautelaDAO.buscarDinamico(filtroContraCautela);
			return contraCautelas;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getContraCautelas():"+e);
			return null;
		}
	}

	@Override
	public List getEstadoCautelars() {
		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstadoCautelar = Busqueda.forClass(EstadoCautelar.class);
		filtroEstadoCautelar.add(Restrictions.eq("estado", SglConstantes.ACTIVO));		
		try {
			List<EstadoCautelar> estadoCautelars = estadoCautelarDAO.buscarDinamico(filtroEstadoCautelar);
			return estadoCautelars;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getEstadoCautelars():"+e);		
			return null;
		}
	}

	@Override
	public List getRiesgos() {
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRiesgo = Busqueda.forClass(Riesgo.class);
		filtroRiesgo.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		try {
			List<Riesgo> riesgos = riesgoDAO.buscarDinamico(filtroRiesgo);
			return riesgos;
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getRiesgos():"+e);		
			return null;
		}
	}

	@Override
	public List getAbogadoEstudioByAbogado(Abogado abogado) {
		
		List<AbogadoEstudio> abogadoEstudios= new ArrayList<AbogadoEstudio>();
		GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(AbogadoEstudio.class);
		filtro.add(Restrictions.like("abogado", abogado));
		filtro.add(Restrictions.like("estado", SglConstantes.ACTIVO));

		try {
			abogadoEstudios = abogadoEstudioDAO.buscarDinamico(filtro);
			return abogadoEstudios;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getAbogadoEstudioByAbogado():"+e);
			return null;
		}		
	}

	@Override
	public List getExpedienteByNroExpediente(String numeroExpediente) {
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Expediente.class);
		filtro.add(Restrictions.like("numeroExpediente", numeroExpediente));		
		try {
			List<Expediente> expedientes= expedienteDAO.buscarDinamico(filtro);		
			return expedientes;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getExpedienteByNroExpediente():"+e);
			return null;
		}

	}

	@Override
	public int getCantidadActPendientes(long idExpediente) {
		
		GenericDao<ActividadProcesal, Object> actividadProcesalDAO = (GenericDao<ActividadProcesal, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(ActividadProcesal.class);
		filtro.add(Restrictions.like("expediente.idExpediente", idExpediente));
		int cont=0;
		try {
			List<ActividadProcesal> actividadProcesals= actividadProcesalDAO.buscarDinamico(filtro);
			
			if(actividadProcesals!=null){				
				if(actividadProcesals.size() != 0){					
					for (int i = 0; i < actividadProcesals.size(); i++) {						
						if (actividadProcesals.get(i).getSituacionActProc().getNombre().equalsIgnoreCase(SglConstantes.SITUACION_ACT_PROC_PENDIENTE)) {
							cont++;
						}
					}					
					return cont;					
				}else{
					return 0;
				}
			}else{
				return 0;
			}			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_OBTENER+"getCantidadActPendientes():"+e);
			return 0;
		}		
	}

	

}
