package com.bbva.persistencia.generica.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public interface GenericDao<K, T> {
	
	Connection getConnection() throws Exception;
	
	void eliminar(K objeto)  throws Exception;

	K insertar(K objeto) throws Exception;
	
	K save(K objeto) throws Exception;

	K modificar(K objeto) throws Exception;
	
	List<K> buscarDinamico(final Busqueda filtro) throws Exception;
	
	List<Integer> buscarDinamicoInteger(final Busqueda filtro) throws Exception;

	@SuppressWarnings("unchecked")
	K buscarById(Class clazz, Serializable id) throws Exception;

	
}
