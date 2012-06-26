package com.hildebrando.legal.domains;

import java.util.Calendar;
import java.util.Date;

public class Documento {
	
	private String codigo;
	private String ruta;
	private String titulo;
	private String comentario;
	private Date fecha;
	
	
	
	public Documento() {
		super();
	}
	public Documento(String codigo, String ruta, String titulo,
			String comentario, Date fecha) {
		super();
		this.codigo = codigo;
		this.ruta = ruta;
		this.titulo = titulo;
		this.comentario = comentario;
		this.fecha = fecha;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Date getFecha() {
		if(fecha == null){
			Calendar cal = Calendar.getInstance();
			fecha = cal.getTime();
		}
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	

}
