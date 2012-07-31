package com.hildebrando.legal.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.Usuario;

public class ContextoListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		
		SpringInit.openSession();
		
		List<Proceso> procesos= new ArrayList<Proceso>();
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Proceso.class);
		try {
			procesos = procesoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("procesos", procesos);
		
		List<Instancia> instancias= new ArrayList<Instancia>();
		GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Instancia.class);
		try {
			instancias = instanciaDao.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("instancias", instancias);
		
		List<EstadoExpediente> estados= new ArrayList<EstadoExpediente>();
		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(EstadoExpediente.class);
		try {
			estados = estadosExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("estados", estados);
		
		List<TipoExpediente> tipos= new ArrayList<TipoExpediente>();
		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(TipoExpediente.class);
		try {
			tipos = tipoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("tipos", tipos);
		
		List<Entidad> entidades= new ArrayList<Entidad>();
		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Entidad.class);
		try {
			entidades = entidadDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("entidades", entidades);
		
		List<Calificacion> calificaciones= new ArrayList<Calificacion>();
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Calificacion.class);
		try {
			calificaciones = calificacionDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("calificaciones", calificaciones);
		
		List<Moneda> monedas= new ArrayList<Moneda>();
		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Moneda.class);
		try {
			monedas = monedaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("monedas", monedas);
		
		List<Usuario> usuarios= new ArrayList<Usuario>();
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Usuario.class);
		try {
			usuarios = usuarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("usuarios", usuarios);
		
		List<Oficina> oficinas = new ArrayList<Oficina>();
		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Oficina.class);
		try {
			oficinas = oficinaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("oficinas", oficinas);
		
		List<Organo> organos = new ArrayList<Organo>();
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		filtro = Busqueda.forClass(Organo.class);
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("organos", organos);
		
		
	}

}
