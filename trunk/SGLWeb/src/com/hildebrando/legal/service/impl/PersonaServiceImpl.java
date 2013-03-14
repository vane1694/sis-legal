package com.hildebrando.legal.service.impl;

import org.apache.log4j.Logger;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.service.PersonaService;
import com.hildebrando.legal.util.SglConstantes;

public class PersonaServiceImpl implements PersonaService {

	public static Logger logger = Logger.getLogger(PersonaServiceImpl.class);
	
	@Override
	public Persona registrar(Persona persona) {
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {			
			return personaDAO.insertar(persona);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"la persona :" +e);
			return null;
		}
	}

}
