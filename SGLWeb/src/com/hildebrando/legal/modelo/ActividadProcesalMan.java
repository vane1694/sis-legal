package com.hildebrando.legal.modelo;

// Generated 08/08/2013 04:28:40 AM by Hibernate Tools 3.4.0.CR1

/**
 * ActividadProcesalMan generated by hbm2java
 */
public class ActividadProcesalMan implements java.io.Serializable {

	private int idActividadProcesalMan;
	private Via via;
	private Proceso proceso;
	private Actividad actividad;
	private char defecto;
	private boolean defectoBoolean;
	private Short plazo;
	private Character estado;

	public ActividadProcesalMan() {
	}

	public ActividadProcesalMan(int idActividadProcesalMan) {
		this.idActividadProcesalMan = idActividadProcesalMan;
	}

	public ActividadProcesalMan(int idActividadProcesalMan, Via via,
			Proceso proceso, Actividad actividad, char defecto, Short plazo) {
		this.idActividadProcesalMan = idActividadProcesalMan;
		this.via = via;
		this.proceso = proceso;
		this.actividad = actividad;
		this.defecto = defecto;
		this.plazo = plazo;
	}

	public int getIdActividadProcesalMan() {
		return this.idActividadProcesalMan;
	}

	public void setIdActividadProcesalMan(int idActividadProcesalMan) {
		this.idActividadProcesalMan = idActividadProcesalMan;
	}

	public Via getVia() {
		return this.via;
	}

	public void setVia(Via via) {
		this.via = via;
	}

	public Proceso getProceso() {
		return this.proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public char getDefecto() {
		return this.defecto;
	}

	public void setDefecto(char defecto) {
		this.defecto = defecto;
	}

	public Short getPlazo() {
		return this.plazo;
	}

	public void setPlazo(Short plazo) {
		this.plazo = plazo;
	}

	public boolean isDefectoBoolean() {
		return defectoBoolean;
	}

	public void setDefectoBoolean(boolean defectoBoolean) {
		this.defectoBoolean = defectoBoolean;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}
	

}
