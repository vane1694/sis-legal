package com.hildebrando.legal.service.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.service.AbogadoService;

public class AbogadoServiceImpl implements AbogadoService {

	@Override
	public Abogado registrar(Abogado abogado) {
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		try {
			
			return abogadoDAO.insertar(abogado);
			
		} catch (Exception e) {
			
			return null;
		}
		
	}

}
