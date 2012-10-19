package com.hildebrando.legal.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Usuario;


@FacesConverter(value="usuarioConverter")
public class UsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {

		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
                
        		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
        				.getApplicationContext().getBean("genericoDao");
        		try {
        			Usuario usuario = usuarioDAO.buscarById(Usuario.class, number);
        			
        			
        			if(usuario!= null){
        				usuario.setNombreDescripcion(
    							usuario.getCodigo() + " - " +
    							usuario.getNombres() + " " +
    							usuario.getApellidoPaterno() + " " +
    							usuario.getApellidoMaterno());
        				
        			}
        			return usuario;
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Responsable invalido", "Responsable invalido"));  
            }  
        }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value)
			throws ConverterException {
		// TODO Auto-generated method stub
		if (value == null || value.equals("")) {  
            return "";  
        } else { 
        	
            return String.valueOf(((Usuario) value).getIdUsuario());  
        } 
	}

}
