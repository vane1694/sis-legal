package com.hildebrando.legal.dto;

public class ReporteLitigiosDto {
 private String sproceso;
 private String sNumeroCasos;
 private String sImporte;
 private String sFechaStockAll;
 
 
 
public ReporteLitigiosDto() {
	super();
}
public ReporteLitigiosDto(String sproceso, String sNumeroCasos, String sImporte, String sFechaStockAll) {
	super();
	this.sproceso = sproceso;
	this.sNumeroCasos = sNumeroCasos;
	this.sImporte = sImporte;
	this.sFechaStockAll=sFechaStockAll;
}
public String getSproceso() {
	return sproceso;
}
public void setSproceso(String sproceso) {
	this.sproceso = sproceso;
}
public String getsNumeroCasos() {
	return sNumeroCasos;
}
public void setsNumeroCasos(String sNumeroCasos) {
	this.sNumeroCasos = sNumeroCasos;
}
public String getsImporte() {
	return sImporte;
}
public void setsImporte(String sImporte) {
	this.sImporte = sImporte;
}
public String getsFechaStockAll() {
	return sFechaStockAll;
}
public void setsFechaStockAll(String sFechaStockAll) {
	this.sFechaStockAll = sFechaStockAll;
}
 
 
 
 
}
