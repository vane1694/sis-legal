package com.hildebrando.legal.modelo;

import java.io.Serializable;

public class Parametros implements Serializable 
{
	private int idParam;
	private int puerto;
	private String host;
	
	public int getIdParam() {
		return idParam;
	}
	public void setIdParam(int idParam) {
		this.idParam = idParam;
	}
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
