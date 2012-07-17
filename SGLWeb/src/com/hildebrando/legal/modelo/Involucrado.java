package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Involucrado generated by hbm2java
 */
public class Involucrado implements java.io.Serializable {

	private InvolucradoId id;
	private TipoInvolucrado tipoInvolucrado;
	private RolInvolucrado rolInvolucrado;
	private Persona persona;
	private Expediente expediente;
	private BigDecimal idClase;
	private BigDecimal idTipoDocumento;
	private BigDecimal numeroDocumento;
	private BigDecimal codCliente;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String usuarioCreacion;
	private String usuarioModificacion;
	private Serializable fechaCreacion;
	private Serializable fechaModificacion;
	private String referencia;

	public Involucrado() {
	}

	public Involucrado(InvolucradoId id, Persona persona) {
		this.id = id;
		this.persona = persona;
	}

	public Involucrado(InvolucradoId id, TipoInvolucrado tipoInvolucrado,
			RolInvolucrado rolInvolucrado, Persona persona,
			Expediente expediente, BigDecimal idClase,
			BigDecimal idTipoDocumento, BigDecimal numeroDocumento,
			BigDecimal codCliente, String apellidoPaterno,
			String apellidoMaterno, String nombres, String usuarioCreacion,
			String usuarioModificacion, Serializable fechaCreacion,
			Serializable fechaModificacion, String referencia) {
		this.id = id;
		this.tipoInvolucrado = tipoInvolucrado;
		this.rolInvolucrado = rolInvolucrado;
		this.persona = persona;
		this.expediente = expediente;
		this.idClase = idClase;
		this.idTipoDocumento = idTipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.codCliente = codCliente;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nombres = nombres;
		this.usuarioCreacion = usuarioCreacion;
		this.usuarioModificacion = usuarioModificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.referencia = referencia;
	}

	public InvolucradoId getId() {
		return this.id;
	}

	public void setId(InvolucradoId id) {
		this.id = id;
	}

	public TipoInvolucrado getTipoInvolucrado() {
		return this.tipoInvolucrado;
	}

	public void setTipoInvolucrado(TipoInvolucrado tipoInvolucrado) {
		this.tipoInvolucrado = tipoInvolucrado;
	}

	public RolInvolucrado getRolInvolucrado() {
		return this.rolInvolucrado;
	}

	public void setRolInvolucrado(RolInvolucrado rolInvolucrado) {
		this.rolInvolucrado = rolInvolucrado;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public BigDecimal getIdClase() {
		return this.idClase;
	}

	public void setIdClase(BigDecimal idClase) {
		this.idClase = idClase;
	}

	public BigDecimal getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(BigDecimal idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public BigDecimal getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(BigDecimal numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public BigDecimal getCodCliente() {
		return this.codCliente;
	}

	public void setCodCliente(BigDecimal codCliente) {
		this.codCliente = codCliente;
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

	public String getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Serializable getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Serializable fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Serializable getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Serializable fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}