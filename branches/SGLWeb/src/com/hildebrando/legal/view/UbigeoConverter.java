package com.hildebrando.legal.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Ubigeo;
import com.hildebrando.legal.util.SglConstantes;

@FacesConverter(value="ubigeoConverter")
public class UbigeoConverter implements Converter {

	public static Logger logger = Logger.getLogger(UbigeoConverter.class);	
	
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
        			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en UbigeoConverter:"+e);
        		}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ubigeo inválido", "Ubigeo inválido"));  
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
