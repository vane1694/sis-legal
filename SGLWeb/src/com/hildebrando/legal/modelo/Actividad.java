package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Actividad generated by hbm2java
 */
public class Actividad implements java.io.Serializable {

	private int idActividad;
	private String nombre;
	private Character estado;
	private Set actividadProcesals = new HashSet(0);
	private Set actividadProcesalMans = new HashSet(0);
	
	public Actividad() {
	}

	public Actividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public Actividad(int idActividad, String nombre, Set actividadProcesals) {
		this.idActividad = idActividad;
		this.nombre = nombre;
		this.actividadProcesals = actividadProcesals;
	}

	public int getIdActividad() {
		return this.idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public Set getActividadProcesals() {
		return this.actividadProcesals;
	}

	public void setActividadProcesals(Set actividadProcesals) {
		this.actividadProcesals = actividadProcesals;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getActividadProcesalMans() {
		return actividadProcesalMans;
	}

	public void setActividadProcesalMans(Set actividadProcesalMans) {
		this.actividadProcesalMans = actividadProcesalMans;
	}

	

}
