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
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.util.SglConstantes;


@FacesConverter(value="situacionHonorarioConverter")
public class SituacionHonorarioConverter implements Converter {
	
	public static Logger logger = Logger.getLogger(SituacionHonorarioConverter.class);
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		
		if(value == null){
			
			return null;
			
		}else{
			if (value.equals("")) {  
	            return null;  
	        } else {  
	            try {  
	                int number = Integer.parseInt(value);  
	                
	        		GenericDao<SituacionHonorario, Object> objectDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
	        				.getApplicationContext().getBean("genericoDao");
	        		try {
	        			SituacionHonorario situacionHonorario= objectDAO.buscarById(SituacionHonorario.class, number);
	        			return situacionHonorario;
	        		} catch (Exception e) {
	        			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en SituacionHonorarioConverter: ",e);
	        		}
	  
	            } catch(NumberFormatException exception) {  
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "No es correcto"));  
	            }  
	        }  
			
		}
		
		
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((SituacionHonorario) value).getIdSituacionHonorario());  
        } 
	}

}
