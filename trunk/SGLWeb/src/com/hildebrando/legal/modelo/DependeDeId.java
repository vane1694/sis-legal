package com.hildebrando.legal.modelo;

// Generated 30-jul-2012 11:32:16 by Hibernate Tools 3.4.0.CR1

/**
 * DependeDeId generated by hbm2java
 */
public class DependeDeId implements java.io.Serializable {

	private int idProceso;
	private int idInstancia;

	public DependeDeId() {
	}

	public DependeDeId(int idProceso, int idInstancia) {
		this.idProceso = idProceso;
		this.idInstancia = idInstancia;
	}

	public int getIdProceso() {
		return this.idProceso;
	}

	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}

	public int getIdInstancia() {
		return this.idInstancia;
	}

	public void setIdInstancia(int idInstancia) {
		this.idInstancia = idInstancia;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DependeDeId))
			return false;
		DependeDeId castOther = (DependeDeId) other;

		return (this.getIdProceso() == castOther.getIdProceso())
				&& (this.getIdInstancia() == castOther.getIdInstancia());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdProceso();
		result = 37 * result + this.getIdInstancia();
		return result;
	}

}
