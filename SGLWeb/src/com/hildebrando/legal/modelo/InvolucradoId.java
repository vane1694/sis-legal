package com.hildebrando.legal.modelo;

// Generated 24-jul-2012 17:43:23 by Hibernate Tools 3.4.0.CR1

/**
 * InvolucradoId generated by hbm2java
 */
public class InvolucradoId implements java.io.Serializable {

	private int idPersona;
	private int idInvolucrado;

	public InvolucradoId() {
	}

	public InvolucradoId(int idPersona, int idInvolucrado) {
		this.idPersona = idPersona;
		this.idInvolucrado = idInvolucrado;
	}

	public int getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public int getIdInvolucrado() {
		return this.idInvolucrado;
	}

	public void setIdInvolucrado(int idInvolucrado) {
		this.idInvolucrado = idInvolucrado;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InvolucradoId))
			return false;
		InvolucradoId castOther = (InvolucradoId) other;

		return (this.getIdPersona() == castOther.getIdPersona())
				&& (this.getIdInvolucrado() == castOther.getIdInvolucrado());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdPersona();
		result = 37 * result + this.getIdInvolucrado();
		return result;
	}

}
