package com.hildebrando.legal.modelo;

// Generated 10-ago-2012 17:25:04 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Instancia generated by hbm2java
 */
public class Instancia implements java.io.Serializable {

	private int idInstancia;
	private Via via;
	private String nombre;
	private Set expedientes = new HashSet(0);

	public Instancia() {
	}

	public Instancia(int idInstancia) {
		this.idInstancia = idInstancia;
	}

	public Instancia(int idInstancia, Via via, String nombre, Set expedientes) {
		this.idInstancia = idInstancia;
		this.via = via;
		this.nombre = nombre;
		this.expedientes = expedientes;
	}

	public int getIdInstancia() {
		return this.idInstancia;
	}

	public void setIdInstancia(int idInstancia) {
		this.idInstancia = idInstancia;
	}

	public Via getVia() {
		return this.via;
	}

	public void setVia(Via via) {
		this.via = via;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(Set expedientes) {
		this.expedientes = expedientes;
	}

}