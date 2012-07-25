package com.hildebrando.legal.modelo;

// Generated 24-jul-2012 17:43:23 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Estudio generated by hbm2java
 */
public class Estudio implements java.io.Serializable {

	private int idEstudio;
	private int ruc;
	private String direccion;
	private String telefono;
	private String correo;
	private Set abogados = new HashSet(0);

	public Estudio() {
	}

	public Estudio(int idEstudio) {
		this.idEstudio = idEstudio;
	}

	public Estudio(int idEstudio, int ruc, String direccion,
			String telefono, String correo, Set abogados) {
		this.idEstudio = idEstudio;
		this.ruc = ruc;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
		this.abogados = abogados;
	}

	public int getIdEstudio() {
		return this.idEstudio;
	}

	public void setIdEstudio(int idEstudio) {
		this.idEstudio = idEstudio;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Set getAbogados() {
		return this.abogados;
	}

	public void setAbogados(Set abogados) {
		this.abogados = abogados;
	}

	public int getRuc() {
		return ruc;
	}

	public void setRuc(int ruc) {
		this.ruc = ruc;
	}

}
