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
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.util.SglConstantes;


@FacesConverter(value="oficinaConverter")
public class OficinaConverter implements Converter {

	public static Logger logger = Logger.getLogger(OficinaConverter.class);
	 
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value){
		
		if (value.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(value);  
                
        		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit
        				.getApplicationContext().getBean("genericoDao");
        		try {
        			Oficina oficina = oficinaDAO.buscarById(Oficina.class, number);
        			
        			String texto = "";
        			
        			if(oficina!= null){
        				texto = oficina.getCodigo() + " "
        						+ oficina.getNombre().toUpperCase() + " ("
        						+ oficina.getUbigeo().getDepartamento().toUpperCase()
        						+ ")";

            			oficina.setNombreDetallado(texto);
        			}

        			return oficina;
        		} catch (Exception e) {
        			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en OficinaConverter:"+e);
            	}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oficina Inválida", "Oficina Inválida"));  
            }  
        }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value)
			throws ConverterException {
		if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Oficina) value).getIdOficina());  
        } 
	}

}
