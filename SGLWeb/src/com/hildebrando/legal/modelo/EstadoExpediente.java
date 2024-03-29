package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * EstadoExpediente generated by hbm2java
 */
public class EstadoExpediente implements java.io.Serializable {

	private int idEstadoExpediente;
	private String nombre;
	private Character estado;
	private Set expedientes = new HashSet(0);

	public EstadoExpediente() {
	}

	public EstadoExpediente(int idEstadoExpediente) {
		this.idEstadoExpediente = idEstadoExpediente;
	}

	public EstadoExpediente(int idEstadoExpediente, String nombre,
			Set expedientes) {
		this.idEstadoExpediente = idEstadoExpediente;
		this.nombre = nombre;
		this.expedientes = expedientes;
	}

	public int getIdEstadoExpediente() {
		return this.idEstadoExpediente;
	}

	public void setIdEstadoExpediente(int idEstadoExpediente) {
		this.idEstadoExpediente = idEstadoExpediente;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(Set expedientes) {
		this.expedientes = expedientes;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

}
