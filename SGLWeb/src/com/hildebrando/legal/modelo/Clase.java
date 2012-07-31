package com.hildebrando.legal.modelo;

// Generated 30-jul-2012 11:32:16 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Clase generated by hbm2java
 */
public class Clase implements java.io.Serializable {

	private int idClase;
	private String descripcion;
	private Set personas = new HashSet(0);

	public Clase() {
	}

	public Clase(int idClase) {
		this.idClase = idClase;
	}

	public Clase(int idClase, String descripcion, Set personas) {
		this.idClase = idClase;
		this.descripcion = descripcion;
		this.personas = personas;
	}

	public int getIdClase() {
		return this.idClase;
	}

	public void setIdClase(int idClase) {
		this.idClase = idClase;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set personas) {
		this.personas = personas;
	}

}
