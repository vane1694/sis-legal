package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * TipoInvolucrado generated by hbm2java
 */
public class TipoInvolucrado implements java.io.Serializable {

	private int idTipoInvolucrado;
	private String nombre;
	private Character estado;
	private Set involucrados = new HashSet(0);

	public TipoInvolucrado() {
	}

	public TipoInvolucrado(int idTipoInvolucrado) {
		this.idTipoInvolucrado = idTipoInvolucrado;
	}

	public TipoInvolucrado(int idTipoInvolucrado, String nombre,
			Set involucrados) {
		this.idTipoInvolucrado = idTipoInvolucrado;
		this.nombre = nombre;
		this.involucrados = involucrados;
	}

	public int getIdTipoInvolucrado() {
		return this.idTipoInvolucrado;
	}

	public void setIdTipoInvolucrado(int idTipoInvolucrado) {
		this.idTipoInvolucrado = idTipoInvolucrado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getInvolucrados() {
		return this.involucrados;
	}

	public void setInvolucrados(Set involucrados) {
		this.involucrados = involucrados;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

}
