package com.hildebrando.legal.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Estudio;


@FacesConverter(value="estudioConverter")
public class EstudioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		
		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
                
        		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
        		
        		try {
        			
        			Estudio estudio = estudioDAO.buscarById(Estudio.class, number);
        			
        			return estudio;
        			
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Estudio invalido", "Estudio invalido"));  
            }  
        }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value){
		 if (value == null || value.equals("")) {  
	            return "";  
	     }else{  
	            return String.valueOf(((Estudio) value).getIdEstudio());  
	     } 
	}

}
