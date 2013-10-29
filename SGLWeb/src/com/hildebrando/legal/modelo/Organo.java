package com.hildebrando.legal.modelo;

// Generated 22-oct-2012 13:27:46 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Organo generated by hbm2java
 */
public class Organo implements java.io.Serializable {

	private int idOrgano;
	private Entidad entidad;
	private Ubigeo ubigeo;
	private String nombre;
	private String codigo;
	private Set feriados = new HashSet(0);
	private Set expedientes = new HashSet(0);
	private Character estado;
	

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}
	
	private String nombreDetallado;

	public Organo() {
	}

	public Organo(int idOrgano) {
		this.idOrgano = idOrgano;
	}

	public Organo(int idOrgano, Entidad entidad, Ubigeo ubigeo, String nombre,
			String codigo, Set feriados, Set expedientes, String nombreDetallado) {
		this.idOrgano = idOrgano;
		this.entidad = entidad;
		this.ubigeo = ubigeo;
		this.nombre = nombre;
		this.codigo = codigo;
		this.feriados = feriados;
		this.expedientes = expedientes;
		this.nombreDetallado = nombreDetallado;
	}

	public int getIdOrgano() {
		return this.idOrgano;
	}

	public void setIdOrgano(int idOrgano) {
		this.idOrgano = idOrgano;
	}

	public Entidad getEntidad() {
		return this.entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public Ubigeo getUbigeo() {
		return this.ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Set getFeriados() {
		return this.feriados;
	}

	public void setFeriados(Set feriados) {
		this.feriados = feriados;
	}

	public Set getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(Set expedientes) {
		this.expedientes = expedientes;
	}

	public String getNombreDetallado() {
		return nombreDetallado;
	}

	public void setNombreDetallado(String nombreDetallado) {
		this.nombreDetallado = nombreDetallado;
	}

}
