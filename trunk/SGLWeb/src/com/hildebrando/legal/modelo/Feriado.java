package com.hildebrando.legal.modelo;

// Generated 10-ago-2012 17:25:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Feriado generated by hbm2java
 */
public class Feriado implements java.io.Serializable {

	private int idFeriado;
	private Organo organo;
	private Date fechaInicio;
	private Date fechaFin;
	private Character tipo;

	public Feriado() {
	}

	public Feriado(int idFeriado) {
		this.idFeriado = idFeriado;
	}

	public Feriado(int idFeriado, Organo organo, Date fechaInicio,
			Date fechaFin, Character tipo) {
		this.idFeriado = idFeriado;
		this.organo = organo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tipo = tipo;
	}

	public int getIdFeriado() {
		return this.idFeriado;
	}

	public void setIdFeriado(int idFeriado) {
		this.idFeriado = idFeriado;
	}

	public Organo getOrgano() {
		return this.organo;
	}

	public void setOrgano(Organo organo) {
		this.organo = organo;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Character getTipo() {
		return this.tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

}
