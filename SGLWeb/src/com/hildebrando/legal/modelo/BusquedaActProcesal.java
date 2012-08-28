package com.hildebrando.legal.modelo;

import java.io.Serializable;
import java.util.Date;

public class BusquedaActProcesal implements Serializable {
	private Long ROWID;
	private String nroExpediente;
	private String demandante;
	private String organo;
	private Date hora;
	private String actividad;
	private Date fechaActividad;
	private Date fechaVencimiento;
	private Date fechaAtencion;
	private String observacion;
	private String colorFila;
	private int id_actividad;
	private int id_via;
	private int id_proceso;	
	private int id_instancia;
	private String plazo_ley;
	
	public BusquedaActProcesal()
	{
		
	}

	public BusquedaActProcesal(String nroExpediente, String demandante,
			String organo, Date hora, String actividad, Date fechaActividad,
			Date fechaVencimiento, Date fechaAtencion, String observacion, String colorFila,
			Long uniqueRow,int id_actividad, int id_via, int id_proceso, int id_instancia,String plazo_ley) 
	{
		this.nroExpediente=nroExpediente;
		this.demandante=demandante;
		this.organo=organo;
		this.hora=hora;
		this.actividad=actividad;
		this.fechaActividad=fechaActividad;
		this.fechaVencimiento=fechaVencimiento;
		this.fechaAtencion=fechaAtencion;
		this.observacion=observacion;
		this.colorFila=colorFila;
		this.ROWID=uniqueRow;
		this.id_actividad=id_actividad;
		this.id_via=id_via;
		this.id_proceso=id_proceso;
		this.id_instancia=id_instancia;
		this.plazo_ley=plazo_ley;
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

	public String getDemandante() {
		return demandante;
	}

	public void setDemandante(String demandante) {
		this.demandante = demandante;
	}

	public String getOrgano() {
		return organo;
	}

	public void setOrgano(String organo) {
		this.organo = organo;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getColorFila() {
		return colorFila;
	}

	public void setColorFila(String colorFila) {
		this.colorFila = colorFila;
	}

	public int getId_via() {
		return id_via;
	}

	public void setId_via(int id_via) {
		this.id_via = id_via;
	}

	public int getId_proceso() {
		return id_proceso;
	}

	public void setId_proceso(int id_proceso) {
		this.id_proceso = id_proceso;
	}

	public int getId_actividad() {
		return id_actividad;
	}

	public void setId_actividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}

	public int getId_instancia() {
		return id_instancia;
	}

	public void setId_instancia(int id_instancia) {
		this.id_instancia = id_instancia;
	}

	public String getPlazo_ley() {
		return plazo_ley;
	}

	public void setPlazo_ley(String plazo_ley) {
		this.plazo_ley = plazo_ley;
	}
	
}
