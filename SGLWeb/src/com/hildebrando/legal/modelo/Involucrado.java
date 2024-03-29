package com.hildebrando.legal.modelo;

// Generated 10-ago-2012 17:25:04 by Hibernate Tools 3.4.0.CR1

/**
 * Involucrado generated by hbm2java
 */
public class Involucrado implements java.io.Serializable {

	private int idInvolucrado;
	private TipoInvolucrado tipoInvolucrado;
	private RolInvolucrado rolInvolucrado;
	private Persona persona;
	private Expediente expediente;
	private String referencia;
	
	private int numero;

	public Involucrado(TipoInvolucrado tipoInvolucrado,
			RolInvolucrado rolInvolucrado, Persona persona) {
		super();
		this.tipoInvolucrado = tipoInvolucrado;
		this.rolInvolucrado = rolInvolucrado;
		this.persona = persona;
	}

	public Involucrado() {
	}

	public Involucrado(int idInvolucrado) {
		this.idInvolucrado = idInvolucrado;
	}

	public Involucrado(int idInvolucrado, TipoInvolucrado tipoInvolucrado,
			RolInvolucrado rolInvolucrado, Persona persona,
			Expediente expediente, String referencia) {
		this.idInvolucrado = idInvolucrado;
		this.tipoInvolucrado = tipoInvolucrado;
		this.rolInvolucrado = rolInvolucrado;
		this.persona = persona;
		this.expediente = expediente;
		this.referencia = referencia;
	}

	public int getIdInvolucrado() {
		return this.idInvolucrado;
	}

	public void setIdInvolucrado(int idInvolucrado) {
		this.idInvolucrado = idInvolucrado;
	}

	public TipoInvolucrado getTipoInvolucrado() {
		if(tipoInvolucrado==null)
			tipoInvolucrado = new TipoInvolucrado();
		return this.tipoInvolucrado;
	}

	public void setTipoInvolucrado(TipoInvolucrado tipoInvolucrado) {
		this.tipoInvolucrado = tipoInvolucrado;
	}

	public RolInvolucrado getRolInvolucrado() {
		if(rolInvolucrado==null)
			rolInvolucrado = new RolInvolucrado();
		return this.rolInvolucrado;
	}

	public void setRolInvolucrado(RolInvolucrado rolInvolucrado) {
		this.rolInvolucrado = rolInvolucrado;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
