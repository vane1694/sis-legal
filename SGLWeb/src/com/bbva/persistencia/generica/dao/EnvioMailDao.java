package com.bbva.persistencia.generica.dao;

import java.util.List;

import com.hildebrando.legal.modelo.ActividadxUsuario;


public interface EnvioMailDao {
	
	List<ActividadxUsuario> obtenerActividadxUsuarioDeActProc(String sCadena) ;

}
