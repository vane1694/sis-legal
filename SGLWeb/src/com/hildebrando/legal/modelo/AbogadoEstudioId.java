package com.hildebrando.legal.modelo;

// Generated 10-ago-2012 17:25:04 by Hibernate Tools 3.4.0.CR1

/**
 * AbogadoEstudioId generated by hbm2java
 */
public class AbogadoEstudioId implements java.io.Serializable {

	private int idEstudio;
	private int idAbogado;

	public AbogadoEstudioId() {
	}

	public AbogadoEstudioId(int idEstudio, int idAbogado) {
		this.idEstudio = idEstudio;
		this.idAbogado = idAbogado;
	}

	public int getIdEstudio() {
		return this.idEstudio;
	}

	public void setIdEstudio(int idEstudio) {
		this.idEstudio = idEstudio;
	}

	public int getIdAbogado() {
		return this.idAbogado;
	}

	public void setIdAbogado(int idAbogado) {
		this.idAbogado = idAbogado;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AbogadoEstudioId))
			return false;
		AbogadoEstudioId castOther = (AbogadoEstudioId) other;

		return (this.getIdEstudio() == castOther.getIdEstudio())
				&& (this.getIdAbogado() == castOther.getIdAbogado());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdEstudio();
		result = 37 * result + this.getIdAbogado();
		return result;
	}

}
