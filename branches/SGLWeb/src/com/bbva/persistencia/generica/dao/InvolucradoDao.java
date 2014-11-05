package com.bbva.persistencia.generica.dao;

import java.util.List;

import com.hildebrando.legal.modelo.Involucrado;

public interface InvolucradoDao<K, T> extends GenericDao<K, T>{ 
	
	
	List<Long> obtenerExpedientes(List<Integer> idInvs) throws Exception;

}
