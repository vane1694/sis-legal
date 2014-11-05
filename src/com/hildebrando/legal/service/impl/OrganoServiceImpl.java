package com.hildebrando.legal.service.impl;

import org.apache.log4j.Logger;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.service.OrganoService;
import com.hildebrando.legal.util.SglConstantes;

public class OrganoServiceImpl implements OrganoService {

	public static Logger logger = Logger.getLogger(OrganoServiceImpl.class);
	
	@Override
	public Organo registrar(Organo organo) {		
		Organo organobd=new Organo();
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		try {			
			organobd= organoDAO.insertar(organo);			
			return organobd;			
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"el organo :" +e);			
			return null;
		}
	}

}
