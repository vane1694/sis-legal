package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Moneda generated by hbm2java
 */
public class Moneda implements java.io.Serializable {

	private int idMoneda;
	private String simbolo;
	private String descripcion;
	private String divisa;
	private Character estado;
	private Set expedientes = new HashSet(0);
	private Set provisions = new HashSet(0);
	private Set cuantias = new HashSet(0);
	private Set inculpados = new HashSet(0);
	private Set honorarios = new HashSet(0);
	private Set tipoCambio = new HashSet(0);

	public Moneda() {
	}

	public Moneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}

	public Moneda(int idMoneda, String simbolo, String descripcion,
			Set expedientes, Set provisions, Set cuantias, Set inculpados,
			Set honorarios, Set tipoCambio) {
		this.idMoneda = idMoneda;
		this.simbolo = simbolo;
		this.descripcion = descripcion;
		this.expedientes = expedientes;
		this.provisions = provisions;
		this.cuantias = cuantias;
		this.inculpados = inculpados;
		this.honorarios = honorarios;
		this.tipoCambio=tipoCambio;
	}

	public int getIdMoneda() {
		return this.idMoneda;
	}

	public void setIdMoneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getExpedientes() {
		return this.expedientes;
	}

	public void setExpedientes(Set expedientes) {
		this.expedientes = expedientes;
	}

	public Set getProvisions() {
		return this.provisions;
	}

	public void setProvisions(Set provisions) {
		this.provisions = provisions;
	}

	public Set getCuantias() {
		return this.cuantias;
	}

	public void setCuantias(Set cuantias) {
		this.cuantias = cuantias;
	}

	public Set getInculpados() {
		return this.inculpados;
	}

	public void setInculpados(Set inculpados) {
		this.inculpados = inculpados;
	}

	public Set getHonorarios() {
		return this.honorarios;
	}

	public void setHonorarios(Set honorarios) {
		this.honorarios = honorarios;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public Set getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(Set tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	
}
