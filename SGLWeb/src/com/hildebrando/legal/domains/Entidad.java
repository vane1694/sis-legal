package com.hildebrando.legal.domains;

public class Entidad {

	
	private String codigo;
	private String nombre;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Entidad(String codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}
	public Entidad() {
		super();
	}
	
	
}
