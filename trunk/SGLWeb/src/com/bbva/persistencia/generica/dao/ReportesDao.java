package com.bbva.persistencia.generica.dao;

import java.util.List;

import com.bbva.general.entities.Generico;
import com.hildebrando.legal.dto.ReporteLitigiosDto;
 
public interface ReportesDao<K, T> extends GenericDao<K, T>{  
	public List<ReporteLitigiosDto> obtenerStockAnterior() throws Exception;
	public  Generico obtenerTipoCambio();
}
