package com.hildebrando.legal.domains;

import java.io.Serializable;

public class Cuantia implements Serializable {

    private String moneda;
    private String pretendido;
    private String materia;
    
    public Cuantia(){
    	
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getPretendido() {
        return pretendido;
    }

    public void setPretendido(String pretendido) {
        this.pretendido = pretendido;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Cuantia(String moneda, String pretendido, String materia) {
        super();
        this.moneda = moneda;
        this.pretendido = pretendido;
        this.materia = materia;
    }

}

