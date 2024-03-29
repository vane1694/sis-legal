package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Materia generated by hbm2java
 */
public class Materia implements java.io.Serializable {

	private int idMateria;
	private String descripcion;
	private Character estado;
	private Set cuantias = new HashSet(0);

	public Materia() {
	}

	public Materia(int idMateria) {
		this.idMateria = idMateria;
	}

	public Materia(int idMateria, String descripcion, Set cuantias, Character estado) {
		this.idMateria = idMateria;
		this.descripcion = descripcion;
		this.cuantias = cuantias;
		this.estado=estado;
	}

	public int getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getCuantias() {
		return this.cuantias;
	}

	public void setCuantias(Set cuantias) {
		this.cuantias = cuantias;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}
}
