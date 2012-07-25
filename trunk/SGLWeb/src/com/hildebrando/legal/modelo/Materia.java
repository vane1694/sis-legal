package com.hildebrando.legal.modelo;

// Generated 24-jul-2012 17:43:23 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Materia generated by hbm2java
 */
public class Materia implements java.io.Serializable {

	private int idMateria;
	private String descripcion;
	private Set cuantias = new HashSet(0);

	public Materia() {
	}

	public Materia(int idMateria) {
		this.idMateria = idMateria;
	}

	public Materia(int idMateria, String descripcion, Set cuantias) {
		this.idMateria = idMateria;
		this.descripcion = descripcion;
		this.cuantias = cuantias;
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

}
