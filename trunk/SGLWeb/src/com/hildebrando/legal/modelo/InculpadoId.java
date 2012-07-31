package com.hildebrando.legal.modelo;

// Generated 30-jul-2012 11:32:16 by Hibernate Tools 3.4.0.CR1

/**
 * InculpadoId generated by hbm2java
 */
public class InculpadoId implements java.io.Serializable {

	private int idPersona;
	private int idInculpado;

	public InculpadoId() {
	}

	public InculpadoId(int idPersona, int idInculpado) {
		this.idPersona = idPersona;
		this.idInculpado = idInculpado;
	}

	public int getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public int getIdInculpado() {
		return this.idInculpado;
	}

	public void setIdInculpado(int idInculpado) {
		this.idInculpado = idInculpado;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InculpadoId))
			return false;
		InculpadoId castOther = (InculpadoId) other;

		return (this.getIdPersona() == castOther.getIdPersona())
				&& (this.getIdInculpado() == castOther.getIdInculpado());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdPersona();
		result = 37 * result + this.getIdInculpado();
		return result;
	}

}
