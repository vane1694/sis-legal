package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * ContraCautela generated by hbm2java
 */
public class ContraCautela implements java.io.Serializable {

	private BigDecimal idContraCautela;
	private String descripcion;
	private Set expedientes = new HashSet(0);

	public ContraCautela() {
	}

	public ContraCautela(BigDecimal idContraCautela) {
		this.idContraCautela = idContraCautela;
	}

	public ContraCautela(BigDecimal idContraCautela, String descripcion,
			Set expedientes) {
		this.idContraCautela = idContraCautela;
		this.descripcion = descripcion;
		this.expedientes = expedientes;
	}

	public BigDecimal getIdContraCautela() {
		return this.idContraCautela;
	}

	public void setIdContraCautela(BigDecimal idContraCautela) {
		this.idContraCautela = idContraCautela;
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
