package com.hildebrando.legal.domains;

import java.io.Serializable;

public class Organo implements Serializable {
	
    private String codigo;
    private String entidad;
    private String nombre;
    private String distrito;
    private String provincia;
    private String departamento;
    private String descripcion;
    private String descripcion2;
    

    public Organo() {

    }

    public Organo(String nombre, String distrito, String provincia, String departamento, String codigo, String entidad) {
        super();
        this.nombre = nombre;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
        this.codigo = codigo;
        this.descripcion = nombre + " (" + distrito.toUpperCase() + "," 
        						         + provincia.toUpperCase() + "," 
        						         + departamento.toUpperCase()+ ")";
        this.descripcion2 = distrito+","+provincia+","+ departamento;
        this.entidad = entidad;
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion2() {
		return descripcion2;
	}

	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

}
