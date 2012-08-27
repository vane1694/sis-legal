package com.hildebrando.legal.modelo;

// Generated 01-ago-2012 12:12:34 by Hibernate Tools 3.4.0.CR1

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ActividadProcesal generated by hbm2java
 */
public class ActividadProcesal implements java.io.Serializable {

	private long idActividadProcesal;
	private Etapa etapa;
	private SituacionActProc situacionActProc;
	private Actividad actividad;
	private Hito hito;
	private String plazoLey;
	private Date fechaActividad;
	private Date fechaVencimiento;
	private Date fechaAtencion;
	private String responsable;
	private String observacion;
	private Character estado;
	private Date hora;
	
	private String fechaActividadToString;
	private String fechaVencimientoToString;
	private String fechaAtencionToString;
	

	public ActividadProcesal() {
	}

	public ActividadProcesal(Etapa etapa, SituacionActProc situacionActProc,
			Actividad actividad) {
		super();
		this.etapa = etapa;
		this.situacionActProc = situacionActProc;
		this.actividad = actividad;
	}

	public ActividadProcesal(long idActividadProcesal) {
		this.idActividadProcesal = idActividadProcesal;
	}

	public ActividadProcesal(long idActividadProcesal, Etapa etapa,
			SituacionActProc situacionActProc, Actividad actividad, Hito hito,
			String plazoLey, Date fechaActividad, Date fechaVencimiento,
			Date fechaAtencion, String responsable, String observacion,
			Character estado, Date hora) {
		this.idActividadProcesal = idActividadProcesal;
		this.etapa = etapa;
		this.situacionActProc = situacionActProc;
		this.actividad = actividad;
		this.hito = hito;
		this.plazoLey = plazoLey;
		this.fechaActividad = fechaActividad;
		this.fechaVencimiento = fechaVencimiento;
		this.fechaAtencion = fechaAtencion;
		this.responsable = responsable;
		this.observacion = observacion;
		this.estado = estado;
		this.hora = hora;
	}

	public long getIdActividadProcesal() {
		return this.idActividadProcesal;
	}

	public void setIdActividadProcesal(long idActividadProcesal) {
		this.idActividadProcesal = idActividadProcesal;
	}

	public Etapa getEtapa() {
		return this.etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public SituacionActProc getSituacionActProc() {
		return this.situacionActProc;
	}

	public void setSituacionActProc(SituacionActProc situacionActProc) {
		this.situacionActProc = situacionActProc;
	}

	public Actividad getActividad() {
		return this.actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Hito getHito() {
		return this.hito;
	}

	public void setHito(Hito hito) {
		this.hito = hito;
	}

	public String getPlazoLey() {
		return this.plazoLey;
	}

	public void setPlazoLey(String plazoLey) {
		this.plazoLey = plazoLey;
	}

	public Date getFechaActividad() {
		return this.fechaActividad;
	}

	public void setFechaActividad(Date fechaActividad) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechaActividadToString(dateFormat.format(fechaActividad));

		this.fechaActividad = fechaActividad;
	}

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechaVencimientoToString(dateFormat.format(fechaVencimiento));

		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaAtencion() {
		return this.fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechaAtencionToString(dateFormat.format(fechaAtencion));

		this.fechaAtencion = fechaAtencion;
	}

	public String getResponsable() {
		return this.responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Character getEstado() {
		return this.estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getFechaActividadToString() {
		return fechaActividadToString;
	}

	public void setFechaActividadToString(String fechaActividadToString) {
		this.fechaActividadToString = fechaActividadToString;
	}

	public String getFechaVencimientoToString() {
		return fechaVencimientoToString;
	}

	public void setFechaVencimientoToString(String fechaVencimientoToString) {
		this.fechaVencimientoToString = fechaVencimientoToString;
	}

	public String getFechaAtencionToString() {
		return fechaAtencionToString;
	}

	public void setFechaAtencionToString(String fechaAtencionToString) {
		this.fechaAtencionToString = fechaAtencionToString;
	}

}
