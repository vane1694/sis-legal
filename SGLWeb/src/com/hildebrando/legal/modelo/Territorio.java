package com.hildebrando.legal.modelo;

// Generated 22-oct-2012 12:21:21 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Territorio generated by hbm2java
 */
public class Territorio implements java.io.Serializable {

	private int idTerritorio;
	private GrupoBanca grupoBanca;
	private String codigo;
	private String descripcion;
	private Character estado;
	private Set oficinas = new HashSet(0);

	public Territorio() {
	}

	public Territorio(int idTerritorio) {
		this.idTerritorio = idTerritorio;
	}

	public Territorio(int idTerritorio, GrupoBanca grupoBanca, String codigo,
			String descripcion, Set oficinas) {
		this.idTerritorio = idTerritorio;
		this.grupoBanca = grupoBanca;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.oficinas = oficinas;
	}

	public int getIdTerritorio() {
		return this.idTerritorio;
	}

	public void setIdTerritorio(int idTerritorio) {
		this.idTerritorio = idTerritorio;
	}

	public GrupoBanca getGrupoBanca() {
		return this.grupoBanca;
	}

	public void setGrupoBanca(GrupoBanca grupoBanca) {
		this.grupoBanca = grupoBanca;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getOficinas() {
		return this.oficinas;
	}

	public void setOficinas(Set oficinas) {
		this.oficinas = oficinas;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}
}
