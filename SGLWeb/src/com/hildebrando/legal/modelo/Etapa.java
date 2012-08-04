package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Etapa generated by hbm2java
 */
public class Etapa implements java.io.Serializable {

	private int idEtapa;
	private String nombre;
	private Set actividadProcesals = new HashSet(0);

	public Etapa() {
	}

	public Etapa(int idEtapa) {
		this.idEtapa = idEtapa;
	}

	public Etapa(int idEtapa, String nombre, Set actividadProcesals) {
		this.idEtapa = idEtapa;
		this.nombre = nombre;
		this.actividadProcesals = actividadProcesals;
	}

	public int getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(int idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getActividadProcesals() {
		return this.actividadProcesals;
	}

	public void setActividadProcesals(Set actividadProcesals) {
		this.actividadProcesals = actividadProcesals;
	}

}
