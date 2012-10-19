package com.hildebrando.legal.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Persona;

@FacesConverter(value="personaCvtr")
public class PersonaConverter implements Converter {
	
	

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		
		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Persona.class);
		try {
			personas = personaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
  
                for (Persona p : personas) {  
                    if (p.getIdPersona() == number) {  
                    	
                    	p.setNombreCompletoMayuscula(p.getNombres().toUpperCase()
        						+ " " + p.getApellidoPaterno().toUpperCase() + " "
        						+ p.getApellidoMaterno().toUpperCase());
                    	
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre de Persona invalida", "Nombre de Persona invalida"));  
            }  
        }  
  
        return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		 if (value == null || value.equals("")) {  
	            return "";  
	        } else {  
	            return String.valueOf(((Persona) value).getIdPersona());  
	        }  
	}
	
	

}
