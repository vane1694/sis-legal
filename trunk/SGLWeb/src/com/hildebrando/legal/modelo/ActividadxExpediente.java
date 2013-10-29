package com.hildebrando.legal.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ActividadxExpediente implements Serializable{
	private Long ROWID;
	private String nroExpediente;
	private String instancia;
	private String actividad;
	private String organo;
	private String apellidoPaterno;
	private String correo;
	private Timestamp fechaActividad;
	private Date fechaVencimiento;
	private Date fechaAtencion;
	private String colorFila;
	private int id_rol_involucrado;
	private int id_demandante;
	private int id_organo;
	private int id_responsable;
	private int id_proceso;
	private int id_oficina;
	private String usuario;
	
	public ActividadxExpediente()
	{
		
	}
	
	
	
	
	
	public ActividadxExpediente(Long rOWID, String nroExpediente,
			String instancia, String actividad, String organo,
			String apellidoPaterno, String correo, Timestamp fechaActividad,
			Date fechaVencimiento, Date fechaAtencion, String colorFila,
			int id_rol_involucrado, int id_demandante, int id_organo,
			int id_responsable, int id_proceso, int id_oficina, String usuario) {
		super();
		ROWID = rOWID;
		this.nroExpediente = nroExpediente;
		this.instancia = instancia;
		this.actividad = actividad;
		this.organo = organo;
		this.apellidoPaterno = apellidoPaterno;
		this.correo = correo;
		this.fechaActividad = fechaActividad;
		this.fechaVencimiento = fechaVencimiento;
		this.fechaAtencion = fechaAtencion;
		this.colorFila = colorFila;
		this.id_rol_involucrado = id_rol_involucrado;
		this.id_demandante = id_demandante;
		this.id_organo = id_organo;
		this.id_responsable = id_responsable;
		this.id_proceso = id_proceso;
		this.id_oficina = id_oficina;
		this.usuario = usuario;
	}





	public int getId_proceso() {
		return id_proceso;
	}

	public void setId_proceso(int id_proceso) {
		this.id_proceso = id_proceso;
	}

	public int getId_oficina() {
		return id_oficina;
	}

	public void setId_oficina(int id_oficina) {
		this.id_oficina = id_oficina;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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


	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
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

	public int getId_responsable() {
		return id_responsable;
	}

	public void setId_responsable(int id_responsable) {
		this.id_responsable = id_responsable;
	}

	public Timestamp getFechaActividad() {
		return fechaActividad;
	}

	public void setFechaActividad(Timestamp fechaActividad) {
		this.fechaActividad = fechaActividad;
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
	
	
}
