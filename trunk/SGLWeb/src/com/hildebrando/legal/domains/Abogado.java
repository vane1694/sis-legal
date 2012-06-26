package com.hildebrando.legal.domains;

public class Abogado implements java.io.Serializable{
	
	private Estudio estudio;
	private String registroCA;
	private String dni;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreCompleto;
	private String telefono;
	private String correo;
	
	
	
	public Abogado() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Abogado(Estudio estudio, String registroCA, String dni,
			String nombre, String apellidoPaterno, String apellidoMaterno,
			String telefono, String correo) {
		super();
		this.estudio = estudio;
		this.registroCA = registroCA;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nombreCompleto = nombre +" "+ apellidoPaterno +" "+ apellidoMaterno;
		this.telefono = telefono;
		this.correo = correo;
	}
	public Estudio getEstudio() {
		if(estudio==null){
			estudio= new Estudio();
		}
		return estudio;
	}
	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}
	public String getRegistroCA() {
		return registroCA;
	}
	public void setRegistroCA(String registroCA) {
		this.registroCA = registroCA;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	

}
