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
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.util.Utilitarios;


@FacesConverter(value="recurrenciaConverter")
public class RecurrenciaConverter implements Converter {

	public static Logger logger = Logger.getLogger(RecurrenciaConverter.class);
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
            	boolean datoValido= Utilitarios.isNumeric(value);
            	if (datoValido)
            	{
	            	int number = Integer.parseInt(value);  
	                
	        		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit
	        				.getApplicationContext().getBean("genericoDao");
	        		try {
	        			Recurrencia recurrencia= recurrenciaDAO.buscarById(Recurrencia.class, number);
	        			return recurrencia;
	        		} catch (Exception e) {
	        			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en RecurrenciaConverter:"+e);
	        		}
  
            	}
            	else
            	{
            		logger.debug("Dato invalido");
            	}
            	
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Recurrencia inválida", "Recurrencia inválida"));  
            }  
        }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Recurrencia) value).getIdRecurrencia());  
        } 
	}

}
