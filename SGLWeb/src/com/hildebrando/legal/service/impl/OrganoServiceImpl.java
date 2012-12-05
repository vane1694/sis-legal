package com.hildebrando.legal.service.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.service.OrganoService;

public class OrganoServiceImpl implements OrganoService {

	@Override
	public Organo registrar(Organo organo) {
		
		Organo organobd=new Organo();
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		
		try {
			
			organobd= organoDAO.insertar(organo);
			
			return organobd;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}

}
