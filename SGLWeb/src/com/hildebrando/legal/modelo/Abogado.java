package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Abogado generated by hbm2java
 */
public class Abogado implements java.io.Serializable {

	private BigDecimal idAbogado;
	private Estudio estudio;
	private String registroca;
	private BigDecimal dni;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private BigDecimal telefono;
	private String correo;
	private Set honorarios = new HashSet(0);

	public Abogado() {
	}

	public Abogado(BigDecimal idAbogado) {
		this.idAbogado = idAbogado;
	}

	public Abogado(BigDecimal idAbogado, Estudio estudio, String registroca,
			BigDecimal dni, String nombres, String apellidoPaterno,
			String apellidoMaterno, BigDecimal telefono, String correo,
			Set honorarios) {
		this.idAbogado = idAbogado;
		this.estudio = estudio;
		this.registroca = registroca;
		this.dni = dni;
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.telefono = telefono;
		this.correo = correo;
		this.honorarios = honorarios;
	}

	public BigDecimal getIdAbogado() {
		return this.idAbogado;
	}

	public void setIdAbogado(BigDecimal idAbogado) {
		this.idAbogado = idAbogado;
	}

	public Estudio getEstudio() {
		return this.estudio;
	}

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public String getRegistroca() {
		return this.registroca;
	}

	public void setRegistroca(String registroca) {
		this.registroca = registroca;
	}

	public BigDecimal getDni() {
		return this.dni;
	}

	public void setDni(BigDecimal dni) {
		this.dni = dni;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
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

	public BigDecimal getTelefono() {
		return this.telefono;
	}

	public void setTelefono(BigDecimal telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Set getHonorarios() {
		return this.honorarios;
	}

	public void setHonorarios(Set honorarios) {
		this.honorarios = honorarios;
	}

}