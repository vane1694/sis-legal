package com.hildebrando.legal.modelo;

// Generated 10-ago-2012 17:25:04 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Proceso generated by hbm2java
 */
public class Proceso implements java.io.Serializable {

	private int idProceso;
	private String nombre;
	private Set avisos = new HashSet(0);
	private Set vias = new HashSet(0);
	private Set expedientes = new HashSet(0);
	
	public Proceso() {
	}

	public Proceso(int idProceso) {
		this.idProceso = idProceso;
	}

	public Proceso(int idProceso, String nombre, Set avisos, Set vias,
			Set expedientes) {
		this.idProceso = idProceso;
		this.nombre = nombre;
		this.avisos = avisos;
		this.vias = vias;
		this.expedientes = expedientes;
	}

	public int getIdProceso() {
		return this.idProceso;
	}

	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getVias() {
		return this.vias;
	}

	public void setVias(Set vias) {
		this.vias = vias;
	}

	public Set getAvisos() {
		return avisos;
	}

	public void setAvisos(Set avisos) {
		this.avisos = avisos;
	}

	public Set getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(Set expedientes) {
		this.expedientes = expedientes;
	}

}
