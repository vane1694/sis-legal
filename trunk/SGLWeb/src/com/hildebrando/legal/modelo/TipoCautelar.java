package com.hildebrando.legal.modelo;

// Generated 24-jul-2012 17:43:23 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * TipoCautelar generated by hbm2java
 */
public class TipoCautelar implements java.io.Serializable {

	private int idTipoCautelar;
	private String descripcion;
	private Set expedientes = new HashSet(0);

	public TipoCautelar() {
	}

	public TipoCautelar(int idTipoCautelar) {
		this.idTipoCautelar = idTipoCautelar;
	}

	public TipoCautelar(int idTipoCautelar, String descripcion, Set expedientes) {
		this.idTipoCautelar = idTipoCautelar;
		this.descripcion = descripcion;
		this.expedientes = expedientes;
	}

	public int getIdTipoCautelar() {
		return this.idTipoCautelar;
	}

	public void setIdTipoCautelar(int idTipoCautelar) {
		this.idTipoCautelar = idTipoCautelar;
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
