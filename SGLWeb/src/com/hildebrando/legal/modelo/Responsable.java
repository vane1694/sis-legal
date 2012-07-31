package com.hildebrando.legal.modelo;

// Generated 30-jul-2012 11:32:16 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Responsable generated by hbm2java
 */
public class Responsable implements java.io.Serializable {

	private int idResponsable;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private Set expedientes = new HashSet(0);

	public Responsable() {
	}

	public Responsable(int idResponsable) {
		this.idResponsable = idResponsable;
	}

	public Responsable(int idResponsable, String apellidoPaterno,
			String apellidoMaterno, String nombres, Set expedientes) {
		this.idResponsable = idResponsable;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nombres = nombres;
		this.expedientes = expedientes;
	}

	public int getIdResponsable() {
		return this.idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Set getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(Set expedientes) {
		this.expedientes = expedientes;
	}

}
