package com.hildebrando.legal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import pe.com.bbva.util.Constantes;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.AbogadoEstudio;
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
			logger.debug("operacion proceso todos correcto");
			return procesos;
			
		} catch (Exception e) {
			logger.debug("operacion proceso todos incorrecto");
		
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
			logger.debug("operacion estado expediente todos correcto");
			return estadoExpedientes;
			
		} catch (Exception e) {
			logger.debug("operacion estado expediente incorrecto" + e.toString());
		
			return null;
		}
	}

	@Override
	public List getOrganos() {
		
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		try {
			List<Organo> organos = organoDAO.buscarDinamico(filtro);
			logger.debug("operacion organos todos correcto");
			return organos;
		} catch (Exception e) {
			logger.debug("operacion organos todos  incorrecto" + e.toString());
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
			logger.debug("operacion recurrencias correcto");
			return recurrencias;
		} catch (Exception e) {
			logger.debug("operacion recurrencias incorrecto" + e.toString());
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
			logger.debug("operacion materias correcto");
			return materias;
		} catch (Exception e) {
			logger.debug("operacion materias incorrecto" + e.toString());
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
			logger.debug("operacion vias correcto");
			return vias;
		} catch (Exception e) {
			logger.debug("operacion vias incorrecto" + e.toString());
			return null;
		}
	}

	@Override
	public List getPersonas() {
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Persona.class);
		try {
			List<Persona>  personas = personaDAO.buscarDinamico(filtro);
			logger.debug("operacion personas correcto");
			return personas;
		} catch (Exception e) {
			logger.debug("operacion personas incorrecto" + e.toString());
			return null;
		}
	}

	@Override
	public List getOficinas() {

		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Oficina.class);
		try {
			List<Oficina> oficinas = oficinaDAO.buscarDinamico(filtro);
			logger.debug("operacion oficinas correcto");
			return oficinas;
		} catch (Exception e) {
			logger.debug("operacion oficinas incorrecto" + e.toString());
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
			return null;
		}
	}

	@Override
	public List getAbogados() {
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		try {
			List<Abogado> abogados = abogadoDAO.buscarDinamico(filtro);
			return abogados;
		} catch (Exception e) {
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
			return null;
		}
	}

	@Override
	public List getUsuarios() {
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Usuario.class);
		
		//filtro.add(Restrictions.like("rol.proceso.idProceso", getProceso()));
		
		try {
			
			List<Usuario> usuarios = usuarioDAO.buscarDinamico(filtro);
			return usuarios;
			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List getInstanciasByVia(int via) {
		
		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Instancia.class);
		filtro.add(Restrictions.like("via.idVia", via));
		filtro.add(Restrictions.eq("prioridad", SglConstantes.PRIORIDAD_DEFAULT_INSTANCIA));
		filtro.add(Restrictions.eq("estado", SglConstantes.ACTIVO));

		try {
			List<Instancia> instancias = instanciaDao.buscarDinamico(filtro);
			logger.debug("operacion instancias correcto");
			return instancias;
		} catch (Exception e) {
			logger.debug("operacion instancias incorrecto" + e.toString());
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
		
		if(abogado.getRegistroca().compareTo("")!=0){
			
			logger.debug("filtro "+ abogado.getRegistroca()   +" abogado - registro ca");
			
			filtro.add(Restrictions.eq("registroca", abogado.getRegistroca()));
			
		}

		if(abogado.getDni()!=0){
			

			logger.debug("filtro "+ abogado.getDni() +" abogado - dni");
			
			filtro.add(Restrictions.eq("dni", abogado.getDni()));
			
		}
		
		if(abogado.getNombres().compareTo("")!=0){
			
			logger.debug("filtro "+ abogado.getNombres() +" abogado - nombres");
			filtro.add(Restrictions.like("nombres", "%"+abogado.getNombres()+"%").ignoreCase());
			
		}
		
		if(abogado.getApellidoPaterno().compareTo("")!=0){
			
			logger.debug("filtro "+ abogado.getApellidoPaterno()  +" abogado - apellido paterno");
			filtro.add(Restrictions.like("apellidoPaterno", "%"+abogado.getApellidoPaterno()+"%").ignoreCase());
		}
		
		if(abogado.getApellidoMaterno().compareTo("")!=0){
			
			logger.debug("filtro "+abogado.getApellidoMaterno()+" abogado - apellido materno");
			filtro.add(Restrictions.like("apellidoMaterno", "%"+abogado.getApellidoMaterno()+"%"));
		}
		
		if(abogado.getTelefono().compareTo("")!=0){
			
			logger.debug("filtro "+ abogado.getTelefono()  +" abogado - telefono");
			filtro.add(Restrictions.eq("telefono", abogado.getTelefono()));
		}
		
		if(abogado.getCorreo().compareTo("")!=0){
			
			
			logger.debug("filtro "+ abogado.getCorreo()  +" abogado - correo");
			filtro.add(Restrictions.like("correo", "%"+abogado.getCorreo()+"%").ignoreCase());
		}
		
		try {
			
			results= abogadoDAO.buscarDinamico(filtro);
			
			return results;
		} catch (Exception e1) {
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
			return null;
		}
	}

	@Override
	public List getOrganosByOrgano(Organo organo) {
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
			filtro.add(Restrictions.like("nombre", "%"+organo.getNombre()+"%").ignoreCase());
		}
		
		if(organo.getUbigeo()!= null){

			logger.debug("filtro "+organo.getUbigeo().getCodDist()+" organo - territorio");
			filtro.add(Restrictions.eq("ubigeo.codDist", organo.getUbigeo().getCodDist()));
			
		}
		
		try {
			organos = organoDAO.buscarDinamico(filtro);

			return organos;
		} catch (Exception e) {

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
			logger.debug("operacion getTipoExpediente correcto");
			return tipoExpedientes;
			
		} catch (Exception e) {
			logger.debug("operacion getTipoExpediente incorrecto");
		
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
			logger.debug("operacion entidads correcto");
			return entidads;
			
		} catch (Exception e) {
			logger.debug("operacion entidads incorrecto");
		
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
			logger.debug("operacion calificacions correcto");
			return calificacions;
			
		} catch (Exception e) {
			logger.debug("operacion calificacions incorrecto");
		
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
			logger.debug("operacion tipoHonorarios correcto");
			return tipoHonorarios;
			
		} catch (Exception e) {
			logger.debug("operacion tipoHonorarios incorrecto");
		
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
			logger.debug("operacion monedas correcto");
			return monedas;
			
		} catch (Exception e) {
			logger.debug("operacion monedas incorrecto");
		
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
			logger.debug("operacion situacionHonorarios correcto");
			return situacionHonorarios;
			
		} catch (Exception e) {
			logger.debug("operacion situacionHonorarios incorrecto");
		
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
			logger.debug("operacion situacionCuotas correcto");
			return situacionCuotas;
			
		} catch (Exception e) {
			logger.debug("operacion situacionCuotas incorrecto");
		
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
			logger.debug("operacion situacionInculpados correcto");
			return situacionInculpados;
			
		} catch (Exception e) {
			logger.debug("operacion situacionInculpados incorrecto");
		
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
			logger.debug("operacion rolInvolucrados correcto");
			return rolInvolucrados;
			
		} catch (Exception e) {
			logger.debug("operacion rolInvolucrados incorrecto");
		
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
			logger.debug("operacion tipoInvolucrados correcto");
			return tipoInvolucrados;
			
		} catch (Exception e) {
			logger.debug("operacion tipoInvolucrados incorrecto");
		
			return null;
		}

	}

	@Override
	public List getClases() {
		GenericDao<Clase, Object> claseDAO = (GenericDao<Clase, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroClase = Busqueda.forClass(Clase.class);
		filtroClase.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		
		try {
			List<Clase> clases = claseDAO.buscarDinamico(filtroClase);
			logger.debug("operacion tipoInvolucrados correcto");
			return clases;
			
		} catch (Exception e) {
			logger.debug("operacion tipoInvolucrados incorrecto");
		
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
			logger.debug("operacion tipoDocumentos correcto");
			return tipoDocumentos;
			
		} catch (Exception e) {
			logger.debug("operacion tipoDocumentos incorrecto");
		
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
			logger.debug("operacion tipoCautelars correcto");
			return tipoCautelars;
			
		} catch (Exception e) {
			logger.debug("operacion tipoCautelars incorrecto");
		
			return null;
		}
	}

	@Override
	public List getContraCautelas() {
		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroContraCautela = Busqueda.forClass(ContraCautela.class);
		filtroContraCautela.add(Restrictions.eq("estado", SglConstantes.ACTIVO));
		
		try {
			List<ContraCautela> contraCautelas = contraCautelaDAO.buscarDinamico(filtroContraCautela);
			logger.debug("operacion contraCautelas correcto");
			return contraCautelas;
			
		} catch (Exception e) {
			logger.debug("operacion contraCautelas incorrecto");
		
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
			logger.debug("operacion estadoCautelars correcto");
			return estadoCautelars;
			
		} catch (Exception e) {
			logger.debug("operacion estadoCautelars incorrecto");
		
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
			logger.debug("operacion riesgos correcto");
			return riesgos;
			
		} catch (Exception e) {
			logger.debug("operacion riesgos incorrecto");
		
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
			e.printStackTrace();
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

			logger.debug("Error al buscar el expediente!");
			return null;
		}

	}

}
