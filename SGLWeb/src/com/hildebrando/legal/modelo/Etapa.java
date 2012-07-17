package com.hildebrando.legal.modelo;

// Generated 12-jul-2012 17:35:13 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Etapa generated by hbm2java
 */
public class Etapa implements java.io.Serializable {

	private BigDecimal idEtapa;
	private String nombre;
	private Set actividadProcesals = new HashSet(0);

	public Etapa() {
	}

	public Etapa(BigDecimal idEtapa) {
		this.idEtapa = idEtapa;
	}

	public Etapa(BigDecimal idEtapa, String nombre, Set actividadProcesals) {
		this.idEtapa = idEtapa;
		this.nombre = nombre;
		this.actividadProcesals = actividadProcesals;
	}

	public BigDecimal getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(BigDecimal idEtapa) {
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