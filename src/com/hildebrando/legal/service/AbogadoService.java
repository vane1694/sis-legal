package com.hildebrando.legal.service;

import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.AbogadoEstudio;

public interface AbogadoService {
	
	
	Abogado registrar(Abogado abogado);
	AbogadoEstudio registrarAbogadoEstudio(AbogadoEstudio abogEstudio);
}
