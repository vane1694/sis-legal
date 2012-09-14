package com.hildebrando.legal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.criterion.Expression;
import org.springframework.stereotype.Component;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.GenericDaoImpl;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.service.RegistroExpedienteService;


public class RegistroExpedienteServiceImpl implements RegistroExpedienteService{

	
	@Override
	public void registrar(Expediente expediente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Organo> buscarOrganos(Organo organo) {
		
		List<Organo> organos = new ArrayList<Organo>();
		
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return organos;
	}

	@Override
	public List<Territorio> buscarTerritorios(String descripcionDistrito) {
		List<Territorio> territorios = new ArrayList<Territorio>();
		GenericDaoImpl<Territorio, Integer> territorioDao = (GenericDaoImpl<Territorio, Integer>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Territorio.class);
		filtro.add(Expression.like("distrito", descripcionDistrito.split(",")[0]));
		filtro.add(Expression.like("provincia",descripcionDistrito.split(",")[1]));
		filtro.add(Expression.like("departamento",descripcionDistrito.split(",")[2]));
		try {
			territorios = territorioDao.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return territorios;
	}

	@Override
	public void insertarOrgano(Organo organo) {
		// TODO Auto-generated method stub
		GenericDaoImpl<Organo, Integer> genericoDao = (GenericDaoImpl<Organo, Integer>) SpringInit.getApplicationContext().getBean("genericoDao");
		try {
			genericoDao.insertar(organo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	

}
