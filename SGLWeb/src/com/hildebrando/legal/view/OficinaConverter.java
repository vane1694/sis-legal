package com.hildebrando.legal.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Usuario;


@FacesConverter(value="oficinaConverter")
public class OficinaConverter implements Converter {

	 
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
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oficina Invalida", "Oficina Invalida"));  
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
