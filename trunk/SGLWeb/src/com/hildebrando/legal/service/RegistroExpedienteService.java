package com.hildebrando.legal.service;

import java.util.List;

import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Territorio;

public interface RegistroExpedienteService {
	
	void registrar(Expediente expediente);

	List<Organo> buscarOrganos(Organo organo);
	
	List<Territorio> buscarTerritorios(String descripcionDistrito);
	
	void insertarOrgano(Organo organo);
	
	
}
