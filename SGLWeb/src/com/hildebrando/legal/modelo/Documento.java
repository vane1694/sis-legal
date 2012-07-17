package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Documento generated by hbm2java
 */
public class Documento implements java.io.Serializable {

	private BigDecimal idDocumento;
	private Hito hito;
	private String titulo;
	private String comentario;
	private Serializable fechaInicio;
	private String referencia;

	public Documento() {
	}

	public Documento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Documento(BigDecimal idDocumento, Hito hito, String titulo,
			String comentario, Serializable fechaInicio, String referencia) {
		this.idDocumento = idDocumento;
		this.hito = hito;
		this.titulo = titulo;
		this.comentario = comentario;
		this.fechaInicio = fechaInicio;
		this.referencia = referencia;
	}

	public BigDecimal getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Hito getHito() {
		return this.hito;
	}

	public void setHito(Hito hito) {
		this.hito = hito;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Serializable getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Serializable fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}