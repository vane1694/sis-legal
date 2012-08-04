package com.hildebrando.legal.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.hildebrando.legal.modelo.Oficina;


@FacesConverter(value="oficinaConverter")
public class OficinaConverter implements Converter {

	 
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue)
			throws ConverterException {
		
        return null;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value)
			throws ConverterException {
		if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Oficina) value));  
        } 
	}

}
