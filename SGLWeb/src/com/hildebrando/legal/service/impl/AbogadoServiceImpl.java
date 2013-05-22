package com.hildebrando.legal.service.impl;

import org.apache.log4j.Logger;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.AbogadoEstudio;
import com.hildebrando.legal.service.AbogadoService;
import com.hildebrando.legal.util.SglConstantes;

public class AbogadoServiceImpl implements AbogadoService {
	
	public static Logger logger = Logger.getLogger(AbogadoServiceImpl.class);
	
	@Override
	public Abogado registrar(Abogado abogado) {
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			return abogadoDAO.insertar(abogado);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"el abogado :" +e);
			return null;
		}
	}
	
	@Override
	public AbogadoEstudio registrarAbogadoEstudio(AbogadoEstudio abogEstudio) {
		GenericDao<AbogadoEstudio, Object> abogadoDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			return abogadoDAO.insertar(abogEstudio);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(SglConstantes.MSJ_ERROR_REGISTR+"el abogado :" +e);
			return null;
		}
	}
	
}
