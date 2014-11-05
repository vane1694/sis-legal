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
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.util.Utilitarios;


@FacesConverter(value="organoConverter")
public class OrganoConverter implements Converter {

	public static Logger logger = Logger.getLogger(OrganoConverter.class);
	
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
                    
            		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
            				.getApplicationContext().getBean("genericoDao");
            		try {
            			Organo organo = organoDAO.buscarById(Organo.class, number);
            			
            			String descripcion = "";
            			
            			if(organo!= null){
            				descripcion = organo.getNombre().toUpperCase() + " ("
                					+ organo.getUbigeo().getDistrito().toUpperCase() + ", "
                					+ organo.getUbigeo().getProvincia().toUpperCase()
                					+ ", "
                					+ organo.getUbigeo().getDepartamento().toUpperCase()
                					+ ")";
            				

                			organo.setNombreDetallado(descripcion);
            				
            			}
            			
            			

            			return organo;
            		} catch (Exception e) {
            			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en OrganoConverter: ",e);
            		}
            	}
            	
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Órgano inválido", "Órgano inválido"));  
            }  
        }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		// TODO Auto-generated method stub
		if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Organo) value).getIdOrgano());  
        } 
	}

}
