package com.hildebrando.legal.modelo;

// Generated 24-jul-2012 17:43:23 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Inculpado generated by hbm2java
 */
public class Inculpado implements java.io.Serializable {

	private InculpadoId id;
	private SituacionInculpado situacionInculpado;
	private Moneda moneda;
	private Persona persona;
	private Expediente expediente;
	private Integer idClase;
	private Integer idTipoDocumento;
	private Integer numeroDocumento;
	private Integer codCliente;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String usuarioCreacion;
	private String usuarioModificacion;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Date fecha;
	private Integer nrocupon;
	private Double monto;

	public Inculpado() {
	}

	public Inculpado(InculpadoId id, Persona persona) {
		this.id = id;
		this.persona = persona;
	}

	public Inculpado(InculpadoId id, SituacionInculpado situacionInculpado,
			Moneda moneda, Persona persona, Expediente expediente,
			Integer idClase, Integer idTipoDocumento, Integer numeroDocumento,
			Integer codCliente, String apellidoPaterno, String apellidoMaterno,
			String nombres, String usuarioCreacion, String usuarioModificacion,
			Date fechaCreacion, Date fechaModificacion, Date fecha,
			Integer nrocupon, Double monto) {
		this.id = id;
		this.situacionInculpado = situacionInculpado;
		this.moneda = moneda;
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
		this.fecha = fecha;
		this.nrocupon = nrocupon;
		this.monto = monto;
	}

	public InculpadoId getId() {
		return this.id;
	}

	public void setId(InculpadoId id) {
		this.id = id;
	}

	public SituacionInculpado getSituacionInculpado() {
		return this.situacionInculpado;
	}

	public void setSituacionInculpado(SituacionInculpado situacionInculpado) {
		this.situacionInculpado = situacionInculpado;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
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

	public Integer getIdClase() {
		return this.idClase;
	}

	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}

	public Integer getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public Integer getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Integer getCodCliente() {
		return this.codCliente;
	}

	public void setCodCliente(Integer codCliente) {
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

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getNrocupon() {
		return this.nrocupon;
	}

	public void setNrocupon(Integer nrocupon) {
		this.nrocupon = nrocupon;
	}

	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

}
