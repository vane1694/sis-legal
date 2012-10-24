package com.hildebrando.legal.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Ubigeo;

@FacesConverter(value="ubigeoConverter")
public class UbigeoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {

		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                
        		GenericDao<Ubigeo, Object> ubigeoDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
        		try {
        			Ubigeo ubigeo = ubigeoDAO.buscarById(Ubigeo.class, value);
        			
        			if(ubigeo != null){
        				
        				String texto = ubigeo.getDistrito() + "," + ubigeo.getProvincia()+ "," + ubigeo.getDepartamento();

        				ubigeo.setDescripcionDistrito(texto);
        				
        			}
        			
        			
        			return ubigeo;
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ubigeo invalido", "Ubigeo invalido"));  
            }  
        }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		 if (value == null || value.equals("")) {  
	            return "";  
	     } else {  
	            return String.valueOf(((Ubigeo) value).getCodDist());  
	     }  
	}

}
