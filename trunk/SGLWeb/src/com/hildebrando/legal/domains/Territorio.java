package com.hildebrando.legal.domains;

import java.io.Serializable;

public class Territorio implements Serializable {

    private String nombre;
    private String codigo;

    public Territorio(String nombre, String codigo) {
        super();
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
