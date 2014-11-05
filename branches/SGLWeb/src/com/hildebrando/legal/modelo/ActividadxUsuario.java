package com.hildebrando.legal.modelo;

import java.io.Serializable;

public class ActividadxUsuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long ROWID;
	private String numeroExpediente;
	private String apellidoPaterno;
	private String correo;
	private String actividad;
	private String fechaVencimiento;
	private String color;
	private String colorDiaAnterior;
	
	public ActividadxUsuario()
	{
		
	}
	
	public ActividadxUsuario(Long uniqueRow, String numeroExpediente, String apellidoPaterno,
			String correo, String actividad, String fechaVenc, String color, String colorDAnterior)
	{
		this.ROWID=uniqueRow;
		this.numeroExpediente=numeroExpediente;
		this.apellidoPaterno=apellidoPaterno;
		this.correo=correo;
		this.actividad=actividad;
		this.fechaVencimiento=fechaVenc;
		this.color=color;
		this.colorDiaAnterior=colorDAnterior;
	}
	
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public Long getROWID() {
		return ROWID;
	}

	public void setROWID(Long rOWID) {
		ROWID = rOWID;
	}

	public String getColorDiaAnterior() {
		return colorDiaAnterior;
	}

	public void setColorDiaAnterior(String colorDiaAnterior) {
		this.colorDiaAnterior = colorDiaAnterior;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
}
