package com.hildebrando.legal.domains;

import java.io.Serializable;

public class Persona implements Serializable{

    private String codigo;
    private String clase;
    private String codCliente;
    private String tipoDoc;
    private String nroDoc;
    private String razonSocial;//nombre tambien aplica
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    
    
    private String rol;
    private String tipo;
    private String referencia;
    
    public Persona() {

    }

    public Persona(String codigo, String rol, String codCliente, String clase,
            String tipoDoc,
            String nroDoc, String razonSocial, String apellidoPaterno, String apellidoMaterno,
            String referencia) {
        super();
        this.codigo = codigo;
        this.rol = rol;
        this.codCliente = codCliente;
        this.clase = clase;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.razonSocial = razonSocial;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.referencia = referencia;
        this.nombreCompleto = razonSocial + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

   

}
