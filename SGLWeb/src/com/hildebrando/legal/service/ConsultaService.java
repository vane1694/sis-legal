package com.hildebrando.legal.service;

import java.util.List;

import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;

public interface ConsultaService {
	
	List getProcesos();
	List<Proceso> getProcesos(int idProceso);	
	List getTipoExpedientes();
	List getTipoHonorarios();
	List getMonedas();
	List getSituacionHonorarios();
	List getSituacionCuota();
	List getSituacionInculpados();
	List getRolInvolucrados();
	List getTipoInvolucrados();
	List getClases();
	List getTipoDocumentos();
	List getTipoCautelars();
	List getContraCautelas();
	List getEstadoCautelars();
	List getRiesgos();
	List getEntidads();
	List getCalificacions();
	List getEstadoExpedientes();
	List getOrganos();
	List getRecurrencias();
	List getMaterias();
	List getViasByProceso(int proceso);
	List getPersonas();
	List getOficinas();
	List getEstudios();
	List getAbogados();
	List getUbigeos();
	List getUsuarios();
	List getInstanciasByVia(int via);
	
	//
	List getAbogadosByAbogadoEstudio(Abogado abogado, Estudio estudio);
	List getPersonasByPersona(Persona persona);
	List getOrganosByOrgano(Organo organo);
	List getOrganosByOrganoEstricto(Organo organo);
	
	
	List getAbogadosByAbogado(Abogado abogado);
	
	List getPersonasByPersonaEstricto(Persona persona);
	
	List getAbogadoEstudioByAbogado(Abogado abogado);
	
	List getExpedienteByNroExpediente(String numeroExpediente);
	
	int getCantidadActPendientes(long idExpediente);
	
	

}
