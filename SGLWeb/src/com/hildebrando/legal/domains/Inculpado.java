package com.hildebrando.legal.domains;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Inculpado implements Serializable {

    private String inculpado;
    private String moneda;
    private String monto;
    private String nroCupon;
    private Date fecha;
    private String fechaToString;
    private String situacion;
    private Persona persona;
    

    public Inculpado(){
    	
    }
    
    public Inculpado(String inculpado, String moneda, String monto, String nroCupon, Date fecha,
            String situacion) {
        super();
        this.inculpado = inculpado;
        this.moneda = moneda;
        this.monto = monto;
        this.nroCupon = nroCupon;
        this.fecha = fecha;
        this.situacion = situacion;
        this.persona = new Persona();

    }

    public String getInculpado() {
        return inculpado;
    }

    public void setInculpado(String inculpado) {
        this.inculpado = inculpado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getNroCupon() {
        return nroCupon;
    }

    public void setNroCupon(String nroCupon) {
        this.nroCupon = nroCupon;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

	public Persona getPersona() {
		if(this.persona==null){
			this.persona = new Persona();
		}
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	
	public String getFechaToString() {
		return fechaToString;
	}

	public void setFechaToString(String fechaToString) {
		this.fechaToString = fechaToString;
	}

	public Date getFecha() {
		if(fecha == null){
			Calendar cal = Calendar.getInstance();
			fecha = cal.getTime();
		}
		return fecha;
	}

	public void setFecha(Date fecha) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechaToString(dateFormat.format(fecha));

		this.fecha = fecha;
	}

}
