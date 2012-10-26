package com.hildebrando.legal.modelo;

import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.hildebrando.legal.view.AbogadoDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.InvolucradoDataModel;
import com.hildebrando.legal.view.PersonaDataModel;



public class ExpedienteVista implements java.io.Serializable{

	private String descripcionTitulo;
	
	private String nroExpeOficial;
	private Date inicioProceso;
	private int estado;
	private int proceso;
	private int via;
	private List<Via> vias;
	private int instancia;
	
	private String nombreInstancia;
	
	private List<Instancia> instancias;
	private Usuario responsable;
	private Oficina oficina;
	private Organo organo1;
	private int calificacion;
	private Recurrencia recurrencia;
	private String secretario;
	private FormaConclusion formaConclusion;
	private Date finProceso;
	private int tipo;
	private Honorario honorario;
	
	private List<Honorario> honorarios;
	private Honorario selectedHonorario;
	
	private Involucrado involucrado;
	private InvolucradoDataModel involucradoDataModel;
	private Involucrado selectedInvolucrado;
	
	private String descripcionCuantia;
	private Cuantia cuantia;
	private Cuantia selectedCuantia;
	private CuantiaDataModel cuantiaDataModel;
	
	private Inculpado inculpado;
	private Inculpado selectedInculpado;
	private List<Inculpado> inculpados;
	
	private int moneda;
	private double montoCautelar;
	private int tipoCautelar;
	private String descripcionCautelar;
	private int contraCautela;
	private double importeCautelar;
	private int estadoCautelar;
	
	private Provision provision;
	private Provision selectedProvision;
	private List<TipoProvision> tipoProvisiones;
	private List<Provision> provisiones;

	private String resumen;
	private Date fechaResumen;
	private String todoResumen;
	private List<Resumen> resumens;
	
	private List<ActividadProcesal> actividadProcesales;
	private ActividadProcesal selectedActPro;
	private ActividadProcesal actividadProcesal;

	private UploadedFile file;
	private Anexo anexo;
	private List<Anexo> anexos;
	
	private Anexo selectedAnexo;

	private int riesgo;
	
	private boolean flagDeshabilitadoGeneral;
	private boolean flagHabilitadoModificar;
	private boolean flagHabilitadoCuantiaModificar;
	
	private boolean flagColumnCuantia;
	private boolean flagColumnGeneral;
	
	private boolean flagBotonFinInst;
	private boolean flagBotonGuardar;
	private boolean flagBotonLimpiar;
	private boolean flagBotonHome;
	
	private boolean deshabilitarBotonFinInst;
	private boolean deshabilitarBotonGuardar;
	
	public String getNroExpeOficial() {
		return nroExpeOficial;
	}
	public void setNroExpeOficial(String nroExpeOficial) {
		this.nroExpeOficial = nroExpeOficial;
	}
	public Date getInicioProceso() {
		return inicioProceso;
	}
	public void setInicioProceso(Date inicioProceso) {
		this.inicioProceso = inicioProceso;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
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
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Organo getOrgano1() {
		return organo1;
	}
	public void setOrgano1(Organo organo1) {
		this.organo1 = organo1;
	}
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	public Recurrencia getRecurrencia() {
		return recurrencia;
	}
	public void setRecurrencia(Recurrencia recurrencia) {
		this.recurrencia = recurrencia;
	}
	public String getSecretario() {
		return secretario;
	}
	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}
	public FormaConclusion getFormaConclusion() {
		return formaConclusion;
	}
	public void setFormaConclusion(FormaConclusion formaConclusion) {
		this.formaConclusion = formaConclusion;
	}
	public Date getFinProceso() {
		return finProceso;
	}
	public void setFinProceso(Date finProceso) {
		this.finProceso = finProceso;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public List<Via> getVias() {
		return vias;
	}
	public void setVias(List<Via> vias) {
		this.vias = vias;
	}
	public List<Instancia> getInstancias() {
		return instancias;
	}
	public void setInstancias(List<Instancia> instancias) {
		this.instancias = instancias;
	}
	public Honorario getHonorario() {
		return honorario;
	}
	public void setHonorario(Honorario honorario) {
		this.honorario = honorario;
	}
	public String getNombreInstancia() {
		return nombreInstancia;
	}
	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}
	public List<Honorario> getHonorarios() {
		return honorarios;
	}
	public void setHonorarios(List<Honorario> honorarios) {
		this.honorarios = honorarios;
	}
	public Involucrado getInvolucrado() {
		return involucrado;
	}
	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}
	public InvolucradoDataModel getInvolucradoDataModel() {
		return involucradoDataModel;
	}
	public void setInvolucradoDataModel(InvolucradoDataModel involucradoDataModel) {
		this.involucradoDataModel = involucradoDataModel;
	}
	public Involucrado getSelectedInvolucrado() {
		return selectedInvolucrado;
	}
	public void setSelectedInvolucrado(Involucrado selectedInvolucrado) {
		this.selectedInvolucrado = selectedInvolucrado;
	}
	public String getDescripcionCuantia() {
		return descripcionCuantia;
	}
	public void setDescripcionCuantia(String descripcionCuantia) {
		this.descripcionCuantia = descripcionCuantia;
	}
	public Cuantia getCuantia() {
		return cuantia;
	}
	public void setCuantia(Cuantia cuantia) {
		this.cuantia = cuantia;
	}
	public Cuantia getSelectedCuantia() {
		return selectedCuantia;
	}
	public void setSelectedCuantia(Cuantia selectedCuantia) {
		this.selectedCuantia = selectedCuantia;
	}
	public CuantiaDataModel getCuantiaDataModel() {
		return cuantiaDataModel;
	}
	public void setCuantiaDataModel(CuantiaDataModel cuantiaDataModel) {
		this.cuantiaDataModel = cuantiaDataModel;
	}
	public Inculpado getInculpado() {
		return inculpado;
	}
	public void setInculpado(Inculpado inculpado) {
		this.inculpado = inculpado;
	}
	public Inculpado getSelectedInculpado() {
		return selectedInculpado;
	}
	public void setSelectedInculpado(Inculpado selectedInculpado) {
		this.selectedInculpado = selectedInculpado;
	}
	public List<Inculpado> getInculpados() {
		return inculpados;
	}
	public void setInculpados(List<Inculpado> inculpados) {
		this.inculpados = inculpados;
	}
	public int getMoneda() {
		return moneda;
	}
	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}
	public double getMontoCautelar() {
		return montoCautelar;
	}
	public void setMontoCautelar(double montoCautelar) {
		this.montoCautelar = montoCautelar;
	}
	public int getTipoCautelar() {
		return tipoCautelar;
	}
	public void setTipoCautelar(int tipoCautelar) {
		this.tipoCautelar = tipoCautelar;
	}
	public String getDescripcionCautelar() {
		return descripcionCautelar;
	}
	public void setDescripcionCautelar(String descripcionCautelar) {
		this.descripcionCautelar = descripcionCautelar;
	}
	public int getContraCautela() {
		return contraCautela;
	}
	public void setContraCautela(int contraCautela) {
		this.contraCautela = contraCautela;
	}
	public double getImporteCautelar() {
		return importeCautelar;
	}
	public void setImporteCautelar(double importeCautelar) {
		this.importeCautelar = importeCautelar;
	}
	public int getEstadoCautelar() {
		return estadoCautelar;
	}
	public void setEstadoCautelar(int estadoCautelar) {
		this.estadoCautelar = estadoCautelar;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public Date getFechaResumen() {
		return fechaResumen;
	}
	public void setFechaResumen(Date fechaResumen) {
		this.fechaResumen = fechaResumen;
	}
	public String getTodoResumen() {
		return todoResumen;
	}
	public void setTodoResumen(String todoResumen) {
		this.todoResumen = todoResumen;
	}
	public int getRiesgo() {
		return riesgo;
	}
	public void setRiesgo(int riesgo) {
		this.riesgo = riesgo;
	}
	public Provision getProvision() {
		return provision;
	}
	public void setProvision(Provision provision) {
		this.provision = provision;
	}
	public List<TipoProvision> getTipoProvisiones() {
		return tipoProvisiones;
	}
	public void setTipoProvisiones(List<TipoProvision> tipoProvisiones) {
		this.tipoProvisiones = tipoProvisiones;
	}
	public List<Provision> getProvisiones() {
		return provisiones;
	}
	public void setProvisiones(List<Provision> provisiones) {
		this.provisiones = provisiones;
	}
	
	public List<ActividadProcesal> getActividadProcesales() {
		return actividadProcesales;
	}
	public void setActividadProcesales(List<ActividadProcesal> actividadProcesales) {
		this.actividadProcesales = actividadProcesales;
	}
	public ActividadProcesal getSelectedActPro() {
		return selectedActPro;
	}
	public void setSelectedActPro(ActividadProcesal selectedActPro) {
		this.selectedActPro = selectedActPro;
	}
	public ActividadProcesal getActividadProcesal() {
		return actividadProcesal;
	}
	public void setActividadProcesal(ActividadProcesal actividadProcesal) {
		this.actividadProcesal = actividadProcesal;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public Anexo getAnexo() {
		return anexo;
	}
	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}
	public List<Anexo> getAnexos() {
		return anexos;
	}
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}
	public Anexo getSelectedAnexo() {
		return selectedAnexo;
	}
	public void setSelectedAnexo(Anexo selectedAnexo) {
		this.selectedAnexo = selectedAnexo;
	}
	public Honorario getSelectedHonorario() {
		return selectedHonorario;
	}
	public void setSelectedHonorario(Honorario selectedHonorario) {
		this.selectedHonorario = selectedHonorario;
	}
	public boolean isFlagDeshabilitadoGeneral() {
		return flagDeshabilitadoGeneral;
	}
	public void setFlagDeshabilitadoGeneral(boolean flagDeshabilitadoGeneral) {
		this.flagDeshabilitadoGeneral = flagDeshabilitadoGeneral;
	}
	public boolean isFlagHabilitadoModificar() {
		return flagHabilitadoModificar;
	}
	public void setFlagHabilitadoModificar(boolean flagHabilitadoModificar) {
		this.flagHabilitadoModificar = flagHabilitadoModificar;
	}
	public String getDescripcionTitulo() {
		return descripcionTitulo;
	}
	public void setDescripcionTitulo(String descripcionTitulo) {
		this.descripcionTitulo = descripcionTitulo;
	}
	public Provision getSelectedProvision() {
		return selectedProvision;
	}
	public void setSelectedProvision(Provision selectedProvision) {
		this.selectedProvision = selectedProvision;
	}
	public boolean isFlagBotonFinInst() {
		return flagBotonFinInst;
	}
	public void setFlagBotonFinInst(boolean flagBotonFinInst) {
		this.flagBotonFinInst = flagBotonFinInst;
	}
	public boolean isFlagBotonGuardar() {
		return flagBotonGuardar;
	}
	public void setFlagBotonGuardar(boolean flagBotonGuardar) {
		this.flagBotonGuardar = flagBotonGuardar;
	}
	public boolean isFlagBotonLimpiar() {
		return flagBotonLimpiar;
	}
	public void setFlagBotonLimpiar(boolean flagBotonLimpiar) {
		this.flagBotonLimpiar = flagBotonLimpiar;
	}
	public boolean isDeshabilitarBotonFinInst() {
		return deshabilitarBotonFinInst;
	}
	public void setDeshabilitarBotonFinInst(boolean deshabilitarBotonFinInst) {
		this.deshabilitarBotonFinInst = deshabilitarBotonFinInst;
	}
	public boolean isDeshabilitarBotonGuardar() {
		return deshabilitarBotonGuardar;
	}
	public void setDeshabilitarBotonGuardar(boolean deshabilitarBotonGuardar) {
		this.deshabilitarBotonGuardar = deshabilitarBotonGuardar;
	}
	public List<Resumen> getResumens() {
		return resumens;
	}
	public void setResumens(List<Resumen> resumens) {
		this.resumens = resumens;
	}
	public boolean isFlagHabilitadoCuantiaModificar() {
		return flagHabilitadoCuantiaModificar;
	}
	public void setFlagHabilitadoCuantiaModificar(
			boolean flagHabilitadoCuantiaModificar) {
		this.flagHabilitadoCuantiaModificar = flagHabilitadoCuantiaModificar;
	}
	public boolean isFlagColumnCuantia() {
		return flagColumnCuantia;
	}
	public void setFlagColumnCuantia(boolean flagColumnCuantia) {
		this.flagColumnCuantia = flagColumnCuantia;
	}
	public boolean isFlagColumnGeneral() {
		return flagColumnGeneral;
	}
	public void setFlagColumnGeneral(boolean flagColumnGeneral) {
		this.flagColumnGeneral = flagColumnGeneral;
	}
	public boolean isFlagBotonHome() {
		return flagBotonHome;
	}
	public void setFlagBotonHome(boolean flagBotonHome) {
		this.flagBotonHome = flagBotonHome;
	}

}
