package com.hildebrando.legal.modelo;

// Generated 24-jul-2012 17:43:23 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Persona generated by hbm2java
 */
public class Persona implements java.io.Serializable {

	private int idPersona;
	private TipoDocumento tipoDocumento;
	private Clase clase;
	private Integer numeroDocumento;
	private Integer codCliente;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String usuarioCreacion;
	private String usuarioModificacion;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Set involucrados = new HashSet(0);
	private Set inculpados = new HashSet(0);

	public Persona() {
	}

	public Persona(int idPersona) {
		this.idPersona = idPersona;
	}

	public Persona(int idPersona, TipoDocumento tipoDocumento, Clase clase,
			Integer numeroDocumento, Integer codCliente,
			String apellidoPaterno, String apellidoMaterno, String nombres,
			String usuarioCreacion, String usuarioModificacion,
			Date fechaCreacion, Date fechaModificacion, Set involucrados,
			Set inculpados) {
		this.idPersona = idPersona;
		this.tipoDocumento = tipoDocumento;
		this.clase = clase;
		this.numeroDocumento = numeroDocumento;
		this.codCliente = codCliente;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nombres = nombres;
		this.usuarioCreacion = usuarioCreacion;
		this.usuarioModificacion = usuarioModificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.involucrados = involucrados;
		this.inculpados = inculpados;
	}

	public int getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public TipoDocumento getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Clase getClase() {
		return this.clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
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

	public Set getInvolucrados() {
		return this.involucrados;
	}

	public void setInvolucrados(Set involucrados) {
		this.involucrados = involucrados;
	}

	public Set getInculpados() {
		return this.inculpados;
	}

	public void setInculpados(Set inculpados) {
		this.inculpados = inculpados;
	}

}
