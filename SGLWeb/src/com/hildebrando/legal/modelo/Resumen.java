package com.hildebrando.legal.modelo;

// Generated 28-sep-2012 16:17:56 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Resumen generated by hbm2java
 */
public class Resumen implements java.io.Serializable {

	private int idResumen;
	private Expediente expediente;
	private Date fecha;
	private String texto;

	public Resumen() {
	}

	public Resumen(int idResumen) {
		this.idResumen = idResumen;
	}

	public Resumen(int idResumen, Expediente expediente, Date fecha,
			String texto) {
		this.idResumen = idResumen;
		this.expediente = expediente;
		this.fecha = fecha;
		this.texto = texto;
	}

	public int getIdResumen() {
		return this.idResumen;
	}

	public void setIdResumen(int idResumen) {
		this.idResumen = idResumen;
	}

	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
