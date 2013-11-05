package com.hildebrando.legal.modelo;

import java.io.Serializable;
import java.util.Date;

public class BusquedaActProcesal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8258065341483333190L;
	private Long ROWID;
	private String nroExpediente;
	private String demandante;
	private String organo;
	private String actividad;
	private String hora;
	private Date fechaActividad;
	private Date fechaVencimiento;
	private Date fechaAtencion;
	private String observacion;
	private String colorFila;
	private int id_actividad;
	private int id_via;
	private int id_proceso;	
	private int id_instancia;
	private long id_expediente;
	//private int id_rol_involucrado;
	private int id_demandante;
	private int id_organo;
	private int id_responsable;
	private String plazo_ley;
	private String usuario;
	private long id_actividad_procesal;
	private int id_oficina;
	private int id_recurrencia;
	private int id_estado_expediente;
	
	

	public int getId_estado_expediente() {
		return id_estado_expediente;
	}

	public void setId_estado_expediente(int id_estado_expediente) {
		this.id_estado_expediente = id_estado_expediente;
	}

	public int getId_oficina() {
		return id_oficina;
	}

	public void setId_oficina(int id_oficina) {
		this.id_oficina = id_oficina;
	}

	public int getId_recurrencia() {
		return id_recurrencia;
	}

	public void setId_recurrencia(int id_recurrencia) {
		this.id_recurrencia = id_recurrencia;
	}

	public BusquedaActProcesal()
	{
		
	}
	
	//int id_rol_involucrado,

	

	public int getId_responsable() {
		return id_responsable;
	}

	public BusquedaActProcesal(Long rOWID, String nroExpediente,
			String demandante, String organo, String actividad, String hora,
			Date fechaActividad, Date fechaVencimiento, Date fechaAtencion,
			String observacion, String colorFila, int id_actividad, int id_via,
			int id_proceso, int id_instancia, long id_expediente,
			int id_demandante, int id_organo, int id_responsable,
			String plazo_ley, String usuario, long id_actividad_procesal,
			int id_oficina, int id_recurrencia) {
		super();
		ROWID = rOWID;
		this.nroExpediente = nroExpediente;
		this.demandante = demandante;
		this.organo = organo;
		this.actividad = actividad;
		this.hora = hora;
		this.fechaActividad = fechaActividad;
		this.fechaVencimiento = fechaVencimiento;
		this.fechaAtencion = fechaAtencion;
		this.observacion = observacion;
		this.colorFila = colorFila;
		this.id_actividad = id_actividad;
		this.id_via = id_via;
		this.id_proceso = id_proceso;
		this.id_instancia = id_instancia;
		this.id_expediente = id_expediente;
		this.id_demandante = id_demandante;
		this.id_organo = id_organo;
		this.id_responsable = id_responsable;
		this.plazo_ley = plazo_ley;
		this.usuario = usuario;
		this.id_actividad_procesal = id_actividad_procesal;
		this.id_oficina = id_oficina;
//		this.id_recurrencia = id_recurrencia;
	}

	public void setId_responsable(int id_responsable) {
		this.id_responsable = id_responsable;
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

//	public int getId_rol_involucrado() {
//		return id_rol_involucrado;
//	}
//
//	public void setId_rol_involucrado(int id_rol_involucrado) {
//		this.id_rol_involucrado = id_rol_involucrado;
//	}

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

	

	public long getId_actividad_procesal() {
		return id_actividad_procesal;
	}

	public void setId_actividad_procesal(long id_actividad_procesal) {
		this.id_actividad_procesal = id_actividad_procesal;
	}

	public long getId_expediente() {
		return id_expediente;
	}

	public void setId_expediente(long id_expediente) {
		this.id_expediente = id_expediente;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

}
