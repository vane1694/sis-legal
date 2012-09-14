package com.hildebrando.legal.modelo;

import java.io.Serializable;
import java.util.Date;

public class ActividadxExpediente implements Serializable{
	private Long ROWID;
	private String nroExpediente;
	private String instancia;
	private String hora;
	private String actividad;
	private String organo;
	private Date fechaActividad;
	private Date fechaVencimiento;
	private Date fechaAtencion;
	private String colorFila;
	
	public ActividadxExpediente()
	{
		
	}
	
	public ActividadxExpediente(String nroExpediente, String hora, String organo,
			String actividad, Date fechaActividad, Date fechaVencimiento, Date fechaAtencion,String instancia, String colorFila)
	{
		this.nroExpediente=nroExpediente;
		this.hora=hora;
		this.actividad=actividad;
		this.fechaActividad=fechaActividad;
		this.fechaVencimiento=fechaVencimiento;
		this.fechaAtencion=fechaAtencion;
		this.instancia=instancia;
		this.organo=organo;
		this.colorFila=colorFila;
	}

	public Long getROWID() {
		return ROWID;
	}

	public void setROWID(Long rOWID) {
		ROWID = rOWID;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public Date getFechaActividad() {
		return fechaActividad;
	}

	public void setFechaActividad(Date fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaAtencion() {
		return fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public String getOrgano() {
		return organo;
	}

	public void setOrgano(String organo) {
		this.organo = organo;
	}

	public String getColorFila() {
		return colorFila;
	}

	public void setColorFila(String colorFila) {
		this.colorFila = colorFila;
	}
}
