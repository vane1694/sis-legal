package com.hildebrando.legal.service;

import java.util.List;

import com.bbva.general.entities.Generico;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Ubigeo;

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
	List getOficinas(String valor);
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
	
	//Samira 01/08/2013
	List getGrupoBancas();
	List  getTerritorios(int banca);
	List getOficinas(int territorio,String valor);
	 List<Generico>  getDepartamentos();
	 List<Generico>   getProvincias(String departamento);
	 List<Generico>  getDistritos(String provincia);
	 List  getActividadesProcesales();
	 List getAbogados(int estudio) ;
	 List getTerritorios();
	List<Persona> getPersonasByPersonaMant(Persona per);
	List<Abogado> getAbogadosByAbogadoMant(Abogado abg);
	List getRoles();
}
