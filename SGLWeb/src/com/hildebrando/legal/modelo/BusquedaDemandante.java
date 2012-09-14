package com.hildebrando.legal.modelo;

import java.io.Serializable;

public class BusquedaDemandante implements Serializable {
	private int id;
	private String demandante;
	private String rol;
	private String tipo;
	
	public BusquedaDemandante()
	{
		
	}
	
	public BusquedaDemandante(String rol,int id,String demandante,String tipo)
	{
		this.id=id;
		this.demandante=demandante;
		this.tipo=tipo;
		this.rol=rol;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDemandante() {
		return demandante;
	}
	public void setDemandante(String demandante) {
		this.demandante = demandante;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
