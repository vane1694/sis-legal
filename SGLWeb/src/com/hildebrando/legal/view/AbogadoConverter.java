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
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Persona;


@FacesConverter(value="abogadoConverter")
public class AbogadoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		

		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
                
        		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit
        				.getApplicationContext().getBean("genericoDao");
        		try {
        			Abogado abogado = abogadoDAO.buscarById(Abogado.class, number);
        			return abogado;
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));  
            }  
        }  
		 return null;  
		
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		 if (value == null || value.equals("")) {  
	            return "";  
	        } else {  
	            return String.valueOf(((Abogado) value).getIdAbogado());  
	        }  
	}

}
