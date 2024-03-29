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
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.util.SglConstantes;


@FacesConverter(value="tipoHonorarioConverter")
public class TipoHonorarioConverter implements Converter {
	
	public static Logger logger = Logger.getLogger(TipoHonorarioConverter.class);
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {

		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
                
        		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
        				.getApplicationContext().getBean("genericoDao");
        		try {
        			TipoHonorario tipoHonorario = tipoHonorarioDAO.buscarById(TipoHonorario.class, number);
        			return tipoHonorario;
        		} catch (Exception e) {
        			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en TipoHonorarioConverter: ",e);
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
            return String.valueOf(((TipoHonorario) value).getIdTipoHonorario());  
        } 
	}

}
