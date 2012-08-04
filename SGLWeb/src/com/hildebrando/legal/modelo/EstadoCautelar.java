package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * EstadoCautelar generated by hbm2java
 */
public class EstadoCautelar implements java.io.Serializable {

	private int idEstadoCautelar;
	private String descripcion;
	private Set expedientes = new HashSet(0);

	public EstadoCautelar() {
	}

	public EstadoCautelar(int idEstadoCautelar) {
		this.idEstadoCautelar = idEstadoCautelar;
	}

	public EstadoCautelar(int idEstadoCautelar, String descripcion,
			Set expedientes) {
		this.idEstadoCautelar = idEstadoCautelar;
		this.descripcion = descripcion;
		this.expedientes = expedientes;
	}

	public int getIdEstadoCautelar() {
		return this.idEstadoCautelar;
	}

	public void setIdEstadoCautelar(int idEstadoCautelar) {
		this.idEstadoCautelar = idEstadoCautelar;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(Set expedientes) {
		this.expedientes = expedientes;
	}

}
