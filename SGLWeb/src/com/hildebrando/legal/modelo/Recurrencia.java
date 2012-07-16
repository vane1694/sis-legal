package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Recurrencia generated by hbm2java
 */
public class Recurrencia implements java.io.Serializable {

	private BigDecimal idRecurrencia;
	private String nombre;
	private Set expedientes = new HashSet(0);

	public Recurrencia() {
	}

	public Recurrencia(BigDecimal idRecurrencia) {
		this.idRecurrencia = idRecurrencia;
	}

	public Recurrencia(BigDecimal idRecurrencia, String nombre, Set expedientes) {
		this.idRecurrencia = idRecurrencia;
		this.nombre = nombre;
		this.expedientes = expedientes;
	}

	public BigDecimal getIdRecurrencia() {
		return this.idRecurrencia;
	}

	public void setIdRecurrencia(BigDecimal idRecurrencia) {
		this.idRecurrencia = idRecurrencia;
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

}
