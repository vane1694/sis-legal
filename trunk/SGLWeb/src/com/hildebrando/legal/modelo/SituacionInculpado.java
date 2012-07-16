package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * SituacionInculpado generated by hbm2java
 */
public class SituacionInculpado implements java.io.Serializable {

	private BigDecimal idSituacionInculpado;
	private String nombre;
	private Set inculpados = new HashSet(0);

	public SituacionInculpado() {
	}

	public SituacionInculpado(BigDecimal idSituacionInculpado) {
		this.idSituacionInculpado = idSituacionInculpado;
	}

	public SituacionInculpado(BigDecimal idSituacionInculpado, String nombre,
			Set inculpados) {
		this.idSituacionInculpado = idSituacionInculpado;
		this.nombre = nombre;
		this.inculpados = inculpados;
	}

	public BigDecimal getIdSituacionInculpado() {
		return this.idSituacionInculpado;
	}

	public void setIdSituacionInculpado(BigDecimal idSituacionInculpado) {
		this.idSituacionInculpado = idSituacionInculpado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getInculpados() {
		return this.inculpados;
	}

	public void setInculpados(Set inculpados) {
		this.inculpados = inculpados;
	}

}
