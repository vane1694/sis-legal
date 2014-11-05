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
import com.hildebrando.legal.modelo.FormaConclusion;
import com.hildebrando.legal.util.SglConstantes;

@FacesConverter(value="conclusionConverter")
public class ConclusionConverter  implements Converter {
	
	public static Logger logger = Logger.getLogger(ConclusionConverter.class);

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
                
        		GenericDao<FormaConclusion, Object> formaConclusionDAO = (GenericDao<FormaConclusion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
        		try {
        			FormaConclusion formaConclusion= formaConclusionDAO.buscarById(FormaConclusion.class, number);
        			return formaConclusion;
        		} catch (Exception e) {
        			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en ConclusionConverter:"+e);
        		}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Conversión", "No es una conclusión válida"));  
            }  
        }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((FormaConclusion) value).getIdFormaConclusion());  
        } 
	}

}
