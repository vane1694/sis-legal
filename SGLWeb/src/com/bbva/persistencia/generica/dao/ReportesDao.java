package com.bbva.persistencia.generica.dao;

import java.util.List;

import com.bbva.general.entities.Generico;
import com.hildebrando.legal.dto.ReporteLitigiosDto;
import com.hildebrando.legal.modelo.ActividadxUsuario;
 
public interface ReportesDao<K, T> extends GenericDao<K, T>{  
	public List<ReporteLitigiosDto> obtenerStockAnterior() throws Exception;
	public  Generico obtenerTipoCambio();
	
	public List<ActividadxUsuario> obtenerActividadxUsuarioDeActProc(String sCadena) ;
		
	
}
