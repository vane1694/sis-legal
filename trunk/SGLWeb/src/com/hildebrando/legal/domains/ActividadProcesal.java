package com.hildebrando.legal.domains;

public class ActividadProcesal {
	
	private String codigo;
	private String nroExpediente;
	private String demandante;
	private String organo;
	private String hora;
	private String actividad;
	private String fechaActividad;
	private String fechaVencimiento;
	private String fechaAtencion;
	private String observacion;
	private String prioridad;
	
	
	public ActividadProcesal() {
		super();
	}
	public ActividadProcesal(String codigo, String nroExpediente,
			String demandante, String organo, String hora, String actividad,
			String fechaActividad, String fechaVencimiento,
			String fechaAtencion, String observacion, String prioridad) {
		super();
		this.codigo = codigo;
		this.nroExpediente = nroExpediente;
		this.demandante = demandante;
		this.organo = organo;
		this.hora = hora;
		this.actividad = actividad;
		this.fechaActividad = fechaActividad;
		this.fechaVencimiento = fechaVencimiento;
		this.fechaAtencion = fechaAtencion;
		this.observacion = observacion;
		this.prioridad = prioridad;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public String getFechaActividad() {
		return fechaActividad;
	}
	public void setFechaActividad(String fechaActividad) {
		this.fechaActividad = fechaActividad;
	}
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getFechaAtencion() {
		return fechaAtencion;
	}
	public void setFechaAtencion(String fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	
	

}
