package com.hildebrando.legal.domains;

import java.io.Serializable;

public class Oficina implements Serializable {

    private String territorio;
    private String codigo;
    private String nombre;
    private String descripcion;

    public Oficina() {

    }

    public Oficina(String territorio, String codigo, String nombre) {
        super();
        this.territorio = territorio;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = codigo + " " + nombre + " (" + territorio + ")";
    }

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

   
}
