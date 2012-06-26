package com.hildebrando.legal.domains;

public class Estudio implements java.io.Serializable{
	
	private String ruc;
	private String nombre;
	private String direccion;
	private String telefono;
	private String correo;
	
	private String tipoHonorario;
	private String moneda;
	private int nroCuotas;
	private String montoPagar;
	private String situacion;
	
	
	
	
	public Estudio() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Estudio(String ruc, String nombre, String direccion,
			String telefono, String correo) {
		super();
		this.ruc = ruc;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTipoHonorario() {
		return tipoHonorario;
	}
	public void setTipoHonorario(String tipoHonorario) {
		this.tipoHonorario = tipoHonorario;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	public String getMontoPagar() {
		return montoPagar;
	}
	public void setMontoPagar(String montoPagar) {
		this.montoPagar = montoPagar;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public int getNroCuotas() {
		return nroCuotas;
	}
	public void setNroCuotas(int nroCuotas) {
		this.nroCuotas = nroCuotas;
	}
	

}
