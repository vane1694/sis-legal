package com.hildebrando.legal.modelo;

// Generated 22-oct-2012 12:21:21 by Hibernate Tools 3.4.0.CR1

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Feriado generated by hbm2java
 */
public class Feriado implements java.io.Serializable {

	private int idFeriado;
	private Ubigeo ubigeo;
	private Organo organo;
	private Date fechaInicio;
	private Date fechaFin;
	private Character tipo;
	private Character indicador;
	private Character estado;
	private String fechaInicioToString;
	private String fechaFinToString;

	public Feriado() {
	}

	public Feriado(int idFeriado) {
		this.idFeriado = idFeriado;
	}

	public Feriado(int idFeriado, Ubigeo ubigeo, Organo organo,
			Date fechaInicio, Date fechaFin, Character tipo, Character indicador) {
		this.idFeriado = idFeriado;
		this.ubigeo = ubigeo;
		this.organo = organo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tipo = tipo;
		this.indicador = indicador;
	}

	public int getIdFeriado() {
		return this.idFeriado;
	}

	public void setIdFeriado(int idFeriado) {
		this.idFeriado = idFeriado;
	}

	public Ubigeo getUbigeo() {
		return this.ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
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
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechaInicioToString(dateFormat.format(fechaInicio));

		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechaFinToString(dateFormat.format(fechaFin));
		
		this.fechaFin = fechaFin;
	}

	public Character getTipo() {
		return this.tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Character getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Character indicador) {
		this.indicador = indicador;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public String getFechaInicioToString() {
		return fechaInicioToString;
	}

	public void setFechaInicioToString(String fechaInicioToString) {
		this.fechaInicioToString = fechaInicioToString;
	}

	public String getFechaFinToString() {
		return fechaFinToString;
	}

	public void setFechaFinToString(String fechaFinToString) {
		this.fechaFinToString = fechaFinToString;
	}	
}
