package com.hildebrando.legal.domains;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String codigo;
    private String tipo;
    private String apellidos;
    private String nombres;
    private String nombresApellidos;
    private String descripcionResponsable;
    



    public Usuario() {

    }

    public Usuario(String codigo, String tipo, String apellidos, String nombres) {
        super();
        this.codigo = codigo;
        this.tipo = tipo;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.nombresApellidos = nombres + " " + apellidos;
        this.descripcionResponsable = codigo + " - " + nombres + " " + apellidos; 

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombresApellidos() {
        return nombresApellidos;
    }

    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getDescripcionResponsable() {
		return descripcionResponsable;
	}

	public void setDescripcionResponsable(String descripcionResponsable) {
		this.descripcionResponsable = descripcionResponsable;
	}

}
