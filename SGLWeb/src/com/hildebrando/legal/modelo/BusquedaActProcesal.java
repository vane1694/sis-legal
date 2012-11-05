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
	private int id_expediente;
	private int id_rol_involucrado;
	private int id_demandante;
	private int id_organo;
	private String id_responsable;
	private String plazo_ley;
	private String usuario;
	
	public BusquedaActProcesal()
	{
		
	}

	public BusquedaActProcesal(String nroExpediente, String demandante,
			String organo, Date hora, String actividad, Date fechaActividad,
			Date fechaVencimiento, Date fechaAtencion, String observacion, String colorFila,
			Long uniqueRow,int id_actividad, int id_via, int id_proceso, int id_instancia, 
			int id_rol_involucrado, int id_involucrado,int id_organo, String id_responsable,
			int id_expediente,String plazo_ley, String usuario) 
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
		this.id_expediente=id_expediente;
		this.usuario=usuario;
		this.id_rol_involucrado=id_rol_involucrado;
		this.id_demandante=id_involucrado;
		this.id_organo=id_organo;
		this.id_responsable=id_responsable;
	}

	public int getId_expediente() {
		return id_expediente;
	}

	public void setId_expediente(int id_expediente) {
		this.id_expediente = id_expediente;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
