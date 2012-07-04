package com.hildebrando.legal.domains;

import java.io.Serializable;

public class Expediente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3273530882210865668L;
	private String numero;
	private String proceso;
	private String via;
	private String demandante;
	
	
	
	public Expediente() {
		super();
	}

	public Expediente(String numero, String proceso, String via,
			String demandante) {
		super();
		this.numero = numero;
		this.proceso = proceso;
		this.via = via;
		this.demandante = demandante;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getDemandante() {
		return demandante;
	}
	public void setDemandante(String demandante) {
		this.demandante = demandante;
	}

	
}
