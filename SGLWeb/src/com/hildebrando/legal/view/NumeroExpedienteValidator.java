package com.hildebrando.legal.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.log4j.Logger;

@FacesValidator("numeroExpedienteValidator")
public class NumeroExpedienteValidator implements Validator {

	public static Logger logger = Logger.getLogger(NumeroExpedienteValidator.class);
	
	private static final String ALFANUMERICO = "[A-Za-z0-9-_/*]+";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public NumeroExpedienteValidator(){
		  pattern = Pattern.compile(ALFANUMERICO);
	}
 
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value)
			throws ValidatorException {
		
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage("Inválido");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			logger.debug("Hay un error en ExpedienteValidator:"+msg);
			throw new ValidatorException(msg);
		}
	}

}
