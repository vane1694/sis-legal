package com.hildebrando.legal.modelo;

// Generated 10-ago-2012 17:25:04 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Honorario generated by hbm2java
 */
public class Honorario implements java.io.Serializable {

	private long idHonorario;
	private Abogado abogado;
	private Moneda moneda;
	private Expediente expediente;
	private TipoHonorario tipoHonorario;
	private SituacionHonorario situacionHonorario;
	private Integer cantidad;
	private Double monto;
	private Double montoPagado;
	private String instancia;
	private List<Cuota> cuotas;
	private int numero;
	private int totalCuotas;
	
	private String estudio;
	
	private boolean flagPendiente=true;

	public Honorario() {
	}

	public Honorario(long idHonorario) {
		this.idHonorario = idHonorario;
	}

	public Honorario(long idHonorario, Abogado abogado, Moneda moneda,
			Expediente expediente, TipoHonorario tipoHonorario,
			SituacionHonorario situacionHonorario, Integer cantidad,
			Double monto, Double montoPagado, String instancia, List cuotas) {
		this.idHonorario = idHonorario;
		this.abogado = abogado;
		this.moneda = moneda;
		this.expediente = expediente;
		this.tipoHonorario = tipoHonorario;
		this.situacionHonorario = situacionHonorario;
		this.cantidad = cantidad;
		this.monto = monto;
		this.montoPagado = montoPagado;
		this.instancia = instancia;
		this.cuotas = cuotas;
	}

	public long getIdHonorario() {
		return this.idHonorario;
	}

	public void setIdHonorario(long idHonorario) {
		this.idHonorario = idHonorario;
	}

	public Abogado getAbogado() {
		return this.abogado;
	}

	public void setAbogado(Abogado abogado) {
		this.abogado = abogado;
	}

	public Moneda getMoneda() {
		if(moneda==null)
			moneda= new Moneda();
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public TipoHonorario getTipoHonorario() {
		if(tipoHonorario == null)
			tipoHonorario = new TipoHonorario();
		return this.tipoHonorario;
	}

	public void setTipoHonorario(TipoHonorario tipoHonorario) {
		this.tipoHonorario = tipoHonorario;
	}

	public SituacionHonorario getSituacionHonorario() {
		if(situacionHonorario == null)
			situacionHonorario = new SituacionHonorario();
		return this.situacionHonorario;
	}

	public void setSituacionHonorario(SituacionHonorario situacionHonorario) {
		this.situacionHonorario = situacionHonorario;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Double getMontoPagado() {
		return this.montoPagado;
	}

	public void setMontoPagado(Double montoPagado) {
		this.montoPagado = montoPagado;
	}

	public String getInstancia() {
		return this.instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}
	
	public void addCuota(Cuota cuota){
		cuota.setHonorario(this);
		cuotas.add(cuota);
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isFlagPendiente() {
		return flagPendiente;
	}

	public void setFlagPendiente(boolean flagPendiente) {
		this.flagPendiente = flagPendiente;
	}

	public int getTotalCuotas() {
		return totalCuotas;
	}

	public void setTotalCuotas(int totalCuotas) {
		this.totalCuotas = totalCuotas;
	}
}
