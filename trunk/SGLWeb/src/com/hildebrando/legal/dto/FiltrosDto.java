package com.hildebrando.legal.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Usuario;

public class FiltrosDto {
  
	private int tipoReporte;
	private int proceso;
	private int via;
	private int instancia;
	private Usuario responsable;
	private Date fechaInicio;
	private Date fechaFin;
	
	private int banca;
	private int territorio;
	private Oficina oficina;
	
	private String departamento;
	private String provincia;
	private String distrito;
	
	private int tipoExpediente;
	private int calificacion;
	private Organo organo;
	private Recurrencia recurrencia;
	private int riesgo;
	private int actProcesal;
	private Materia materia;
	private int p_estado;
	
	private String tipoImporte;
	private int moneda;
	private BigDecimal importeMinimo;
	private BigDecimal importeMaximo;
	
	private Persona nombre;
	private int rol;
	
	private int estudio;
	private Abogado abogado;
	
	private boolean defecto;
	
	private Usuario usuarioLogueado;
	
	private int plazo;
	
	private int tipoUbigeo;
	
	public int getTipoUbigeo() {
		return tipoUbigeo;
	}
	public void setTipoUbigeo(int tipoUbigeo) {
		this.tipoUbigeo = tipoUbigeo;
	}
	public int getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(int tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	public int getProceso() {
		return proceso;
	}
	public void setProceso(int proceso) {
		this.proceso = proceso;
	}
	public int getVia() {
		return via;
	}
	public void setVia(int via) {
		this.via = via;
	}
	public int getInstancia() {
		return instancia;
	}
	public void setInstancia(int instancia) {
		this.instancia = instancia;
	}
	public Usuario getResponsable() {
		return responsable;
	}
	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getBanca() {
		return banca;
	}
	public void setBanca(int banca) {
		this.banca = banca;
	}
	public int getTerritorio() {
		return territorio;
	}
	public void setTerritorio(int territorio) {
		this.territorio = territorio;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public int getTipoExpediente() {
		return tipoExpediente;
	}
	public void setTipoExpediente(int tipoExpediente) {
		this.tipoExpediente = tipoExpediente;
	}
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	public Organo getOrgano() {
		return organo;
	}
	public void setOrgano(Organo organo) {
		this.organo = organo;
	}
	public Recurrencia getRecurrencia() {
		return recurrencia;
	}
	public void setRecurrencia(Recurrencia recurrencia) {
		this.recurrencia = recurrencia;
	}
	public int getRiesgo() {
		return riesgo;
	}
	public void setRiesgo(int riesgo) {
		this.riesgo = riesgo;
	}
	public int getActProcesal() {
		return actProcesal;
	}
	public void setActProcesal(int actProcesal) {
		this.actProcesal = actProcesal;
	}
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	public int getEstado() {
		return p_estado;
	}
	public void setEstado(int p_estado) {
		this.p_estado = p_estado;
	}
	public String getTipoImporte() {
		return tipoImporte;
	}
	public void setTipoImporte(String tipoImporte) {
		this.tipoImporte = tipoImporte;
	}
	public int getMoneda() {
		return moneda;
	}
	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getImporteMinimo() {
		return importeMinimo;
	}
	public void setImporteMinimo(BigDecimal importeMinimo) {
		this.importeMinimo = importeMinimo;
	}
	public BigDecimal getImporteMaximo() {
		return importeMaximo;
	}
	public void setImporteMaximo(BigDecimal importeMaximo) {
		this.importeMaximo = importeMaximo;
	}
	public Persona getNombre() {
		return nombre;
	}
	public void setNombre(Persona nombre) {
		this.nombre = nombre;
	}
	public int getRol() {
		return rol;
	}
	public void setRol(int rol) {
		this.rol = rol;
	}
	public int getEstudio() {
		return estudio;
	}
	public void setEstudio(int estudio) {
		this.estudio = estudio;
	}
	public Abogado getAbogado() {
		return abogado;
	}
	public void setAbogado(Abogado abogado) {
		this.abogado = abogado;
	}
	
	
	
	
	public boolean isDefecto() {
		return defecto;
	}
	public void setDefecto(boolean defecto) {
		this.defecto = defecto;
	}
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}
	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	
	
	public int getPlazo() {
		return plazo;
	}
	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}
	@Override
	public String toString() {
		return "FiltrosDto [tipoReporte=" + tipoReporte + ", proceso="
				+ proceso + ", via=" + via + ", instancia=" + instancia
				+ ", responsable=" + responsable + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", banca=" + banca
				+ ", territorio=" + territorio + ", oficina=" + oficina
				+ ", departamento=" + departamento + ", provincia=" + provincia
				+ ", distrito=" + distrito + ", tipoExpediente="
				+ tipoExpediente + ", calificacion=" + calificacion
				+ ", organo=" + organo + ", recurrencia=" + recurrencia
				+ ", riesgo=" + riesgo + ", actProcesal=" + actProcesal
				+ ", materia=" + materia + ", estado=" + p_estado
				+ ", tipoImporte=" + tipoImporte + ", moneda=" + moneda
				+ ", importeMinimo=" + importeMinimo + ", importeMaximo="
				+ importeMaximo + ", nombre=" + nombre + ", rol=" + rol
				+ ", estudio=" + estudio + ", abogado=" + abogado + "]";
	}
	
	

}
