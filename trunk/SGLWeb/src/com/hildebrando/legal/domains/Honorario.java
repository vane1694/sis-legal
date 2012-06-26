package com.hildebrando.legal.domains;

public class Honorario {
	
	private String cuota;
	private String nroRecibo;
	private String moneda;
	private String importe;
	private String fechaPago;
	private String situacion;
	
	
	public Honorario() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Honorario(String cuota, String nroRecibo, String moneda,
			String importe, String fechaPago, String situacion) {
		super();
		this.cuota = cuota;
		this.nroRecibo = nroRecibo;
		this.moneda = moneda;
		this.importe = importe;
		this.fechaPago = fechaPago;
		this.situacion = situacion;
	}
	public String getCuota() {
		return cuota;
	}
	public void setCuota(String cuota) {
		this.cuota = cuota;
	}
	public String getNroRecibo() {
		return nroRecibo;
	}
	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	

}
