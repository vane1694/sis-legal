package com.hildebrando.legal.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.SituacionCuota;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.Usuario;

public class ContextoListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public void contextInitialized(ServletContextEvent contextEvent) {
//		
//		SpringInit.openSession();
//		
//		List<Proceso> procesos= new ArrayList<Proceso>();
//		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		Busqueda filtro = Busqueda.forClass(Proceso.class);
//		try {
//			procesos = procesoDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("procesos", procesos);
//		
//		List<Instancia> instancias= new ArrayList<Instancia>();
//		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Instancia.class);
//		try {
//			instancias = instanciaDao.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("instancias", instancias);
//		
//
//		List<Usuario> usuarios= new ArrayList<Usuario>();
//		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Usuario.class);
//		try {
//			usuarios = usuarioDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("usuarios", usuarios);
//		
//		
//		List<EstadoExpediente> estados= new ArrayList<EstadoExpediente>();
//		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(EstadoExpediente.class);
//		try {
//			estados = estadosExpedienteDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("estados", estados);
//		
//		List<TipoExpediente> tipos= new ArrayList<TipoExpediente>();
//		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(TipoExpediente.class);
//		try {
//			tipos = tipoExpedienteDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("tipos", tipos);
//		
//		List<Entidad> entidades= new ArrayList<Entidad>();
//		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Entidad.class);
//		try {
//			entidades = entidadDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("entidades", entidades);
//		
//	
//		List<Oficina> oficinas = new ArrayList<Oficina>();
//		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Oficina.class);
//		try {
//			oficinas = oficinaDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("oficinas", oficinas);
//		
//		List<Organo> organos = new ArrayList<Organo>();
//		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Organo.class);
//		try {
//			organos = organoDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("organos", organos);
//		
//		List<Calificacion> calificaciones= new ArrayList<Calificacion>();
//		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Calificacion.class);
//		try {
//			calificaciones = calificacionDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("calificaciones", calificaciones);
//		
//		List<Territorio> territorios= new ArrayList<Territorio>();
//		GenericDao<Territorio, Object> territorioDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Territorio.class);
//		try {
//			territorios = territorioDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("territorios", territorios);
//		
//		
//		List<Recurrencia> recurrencias = new ArrayList<Recurrencia>();
//		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Recurrencia.class);
//		try {
//			recurrencias = recurrenciaDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("recurrencias", recurrencias);
//		
//		List<Estudio> estudios= new ArrayList<Estudio>();
//		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Estudio.class);
//		try {
//			estudios = estudioDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("estudios", estudios);
//		
//		List<Abogado> abogados = new ArrayList<Abogado>();
//		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit
//				.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Abogado.class);
//		try {
//			abogados = abogadoDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("abogados", abogados);
//		
//		List<TipoHonorario> tipohonorarios= new ArrayList<TipoHonorario>();
//		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(TipoHonorario.class);
//		try {
//			tipohonorarios = tipoHonorarioDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("tipohonorarios", tipohonorarios);
//		
//		List<Moneda> monedas= new ArrayList<Moneda>();
//		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(Moneda.class);
//		try {
//			monedas = monedaDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("monedas", monedas);
//		
//		List<SituacionHonorario> situacionHonorarios= new ArrayList<SituacionHonorario>();
//		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(SituacionHonorario.class);
//		try {
//			situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("situacionHonorarios", situacionHonorarios);
//	
//		List<SituacionCuota> situacionCuotas= new ArrayList<SituacionCuota>();
//		GenericDao<SituacionCuota, Object> situacionCuotasDAO = (GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(SituacionCuota.class);
//		try {
//			situacionCuotas = situacionCuotasDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("situacionCuotas", situacionCuotas);
//	
//		List<SituacionInculpado> situacionInculpados= new ArrayList<SituacionInculpado>();
//		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
//		filtro = Busqueda.forClass(SituacionInculpado.class);
//		try {
//			situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("situacionInculpados", situacionInculpados);
//		
//	}

}
