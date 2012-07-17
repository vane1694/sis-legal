package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * FormaConclusion generated by hbm2java
 */
public class FormaConclusion implements java.io.Serializable {

	private BigDecimal idFormaConclusion;
	private String descripcion;
	private Set hitos = new HashSet(0);

	public FormaConclusion() {
	}

	public FormaConclusion(BigDecimal idFormaConclusion) {
		this.idFormaConclusion = idFormaConclusion;
	}

	public FormaConclusion(BigDecimal idFormaConclusion, String descripcion,
			Set hitos) {
		this.idFormaConclusion = idFormaConclusion;
		this.descripcion = descripcion;
		this.hitos = hitos;
	}

	public BigDecimal getIdFormaConclusion() {
		return this.idFormaConclusion;
	}

	public void setIdFormaConclusion(BigDecimal idFormaConclusion) {
		this.idFormaConclusion = idFormaConclusion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getHitos() {
		return this.hitos;
	}

	public void setHitos(Set hitos) {
		this.hitos = hitos;
	}

}