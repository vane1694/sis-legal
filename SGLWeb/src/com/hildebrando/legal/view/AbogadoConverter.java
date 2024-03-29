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
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.util.SglConstantes;


@FacesConverter(value="abogadoConverter")
public class AbogadoConverter implements Converter {

	public static Logger logger = Logger.getLogger(AbogadoConverter.class);
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		

		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
                
        		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
        		try {
        			Abogado abogado = abogadoDAO.buscarById(Abogado.class, number);
        			
        			if(abogado != null){
        				
        				abogado.setNombreCompletoMayuscula(abogado.getNombres().toUpperCase()
								  + " " + abogado.getApellidoPaterno().toUpperCase() 
								  + " " + abogado.getApellidoMaterno().toUpperCase());
        				
        			}
        			
        			
        			return abogado;
        		} catch (Exception e) {
        			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en AbogadoConverter:"+e);
                }
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Abogado inv�lido", "Abogado inv�lido"));  
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
