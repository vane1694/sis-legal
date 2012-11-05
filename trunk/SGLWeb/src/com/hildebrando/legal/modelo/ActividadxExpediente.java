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
	private int id_rol_involucrado;
	private int id_demandante;
	private int id_organo;
	private String id_responsable;
	
	public ActividadxExpediente()
	{
		
	}
	
	public ActividadxExpediente(String nroExpediente, String hora, String organo,
			String actividad, Date fechaActividad, Date fechaVencimiento, Date fechaAtencion,
			String instancia, String colorFila, int id_rol_involucrado, int id_demandante, 
			int id_organo, String id_responsable)
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
		this.id_rol_involucrado=id_rol_involucrado;
		this.id_organo=id_organo;
		this.id_responsable=id_responsable;
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

	public int getId_rol_involucrado() {
		return id_rol_involucrado;
	}

	public void setId_rol_involucrado(int id_rol_involucrado) {
		this.id_rol_involucrado = id_rol_involucrado;
	}

	public int getId_demandante() {
		return id_demandante;
	}

	public void setId_demandante(int id_demandante) {
		this.id_demandante = id_demandante;
	}

	public int getId_organo() {
		return id_organo;
	}

	public void setId_organo(int id_organo) {
		this.id_organo = id_organo;
	}

	public String getId_responsable() {
		return id_responsable;
	}

	public void setId_responsable(String id_responsable) {
		this.id_responsable = id_responsable;
	}
}
