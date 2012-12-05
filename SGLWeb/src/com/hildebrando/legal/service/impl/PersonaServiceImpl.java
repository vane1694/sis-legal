package com.hildebrando.legal.service.impl;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.service.PersonaService;

public class PersonaServiceImpl implements PersonaService {

	@Override
	public Persona registrar(Persona persona) {
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		try {
			
			return personaDAO.insertar(persona);
			
		} catch (Exception e) {
			
			return null;
		}
	}

}
