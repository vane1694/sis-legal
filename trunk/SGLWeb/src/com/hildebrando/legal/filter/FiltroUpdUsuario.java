package com.hildebrando.legal.filter;

import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Restrictions;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Rol;
import com.hildebrando.legal.modelo.Usuario;


/**
 * Servlet Filter implementation class UpdUsuario
 */
//@WebFilter("/UpdUsuario")
public class FiltroUpdUsuario implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroUpdUsuario() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		try {
			if(sesion.getAttribute("usuario") != null){				
				com.grupobbva.seguridad.client.domain.Usuario usuario = (com.grupobbva.seguridad.client.domain.Usuario) sesion.getAttribute("usuario");
				if(usuario != null){
					//System.out.println("upd usuario: " + usuario.getUsuarioId());
					GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
					Busqueda filtro2 = Busqueda.forClass(Usuario.class);
					filtro2.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
					List<Usuario> usuarios= new ArrayList<Usuario>();
							
					try {
						usuarios = usuarioDAO.buscarDinamico(filtro2);
					} catch (Exception e) {
						e.printStackTrace();
						//logger.debug("Error al obtener los datos de usuario de la session");
						//System.out.println("Error al obtener los datos de usuario de la session");
					}
			
					if(usuarios.size()==0) {

						//System.out.println("Usuario inexistente en sgl, insert datos ");
												
						//GenericDao<Usuario, Object> usuario2DAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
						
						
						Usuario user= new Usuario();
						user.setCodigo(usuario.getUsuarioId());
						user.setNombreCompleto(usuario.getNombres());
						user.setApellidoPaterno(usuario.getApePat());
						user.setApellidoMaterno(usuario.getApeMat());
						user.setCorreo(usuario.getEmail());
						user.setNombres(usuario.getNombre());
						user.setEstado('A');
						
						Rol rol= new Rol();
						
						if(usuario.getPerfil().getNombre().equalsIgnoreCase("Administrador")){
							
							rol.setIdRol(1);
							
						}else{
							
							rol.setIdRol(5);
						}
						
						user.setRol(rol);
						
						try {
							//usuario2DAO.insertar(user);
							 usuarioDAO.insertar(user);
							//System.out.println("guardó el usuario exitosamente");
							
						} catch (Exception ex) {
							ex.printStackTrace();							
							//System.out.println("no guardo el usuario por "+ ex.getMessage());
						}
					}					
				} 				
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}				
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
