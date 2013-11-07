package com.hildebrando.legal.mb.reportes;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.exceptions.NonExecutableDocumentException;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.TestConnectionServiceProxy;
import it.eng.spagobi.services.common.SsoServiceInterface;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.general.entities.Generico;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.util.Utilitarios;
import com.hildebrando.legal.dto.FiltrosDto;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.GrupoBanca;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.Ubigeo;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;

@ManagedBean(name = "reportesUniMB")
@SessionScoped


public class ReportesUnificadoMB implements Serializable{
	
	public static Logger logger = Logger.getLogger(ReportesUnificadoMB.class);
	
	private Integer documentId ;
	private String role;
	private SDKDocument document ;
	private SDKDocument[] documents ;
	private SDKDocumentParameter[] parameters;
	private StringBuffer parameterValues ;
	private String nombreReporte;
	private String iframeUrl;
	private String iframeStyle;
	private String iframeUrlString;
	private Logueo usuario;
	private boolean detallado;
	private List<Instancia> instancias;
	private List<GrupoBanca> bancas;
	private List<Territorio> territorios;
    private FiltrosDto filtrosDto;
    private List<Via> vias;
    private List<Actividad> actividadesprocesales;
    private List<Estudio> estudios;
    private List<Generico> listaTiposImportes;
    private List<Generico> ubigeosDepartamento;
    private List<Generico> ubigeosProvincia;
    private List<Generico> ubigeosDistrito;
    private List<RolInvolucrado> rolInvolucrados;
    List<Moneda> monedas;
    @ManagedProperty(name="consultaService", value = "#{consultaServiceImpl}")
	private ConsultaService consultaService;
    
    //Everis
    private String idUbigeo;
    private ArrayList<Ubigeo> lstUbigeoAux;
    private List<Ubigeo> lstUbigeo;
    private boolean flagDeshUbigeos;
    
	public boolean isFlagDeshUbigeos() {
		return flagDeshUbigeos;
	}


	public void setFlagDeshUbigeos(boolean flagDeshUbigeos) {
		this.flagDeshUbigeos = flagDeshUbigeos;
	}


	public String getIdUbigeo() {
		return idUbigeo;
	}


	public void setIdUbigeo(String idUbigeo) {
		this.idUbigeo = idUbigeo;
	}


	public List<Ubigeo> getLstUbigeo() {
		return lstUbigeo;
	}


	public void setLstUbigeo(List<Ubigeo> lstUbigeo) {
		this.lstUbigeo = lstUbigeo;
	}


	public ArrayList<Ubigeo> getLstUbigeoAux() {
		return lstUbigeoAux;
	}


	public void setLstUbigeoAux(ArrayList<Ubigeo> lstUbigeoAux) {
		this.lstUbigeoAux = lstUbigeoAux;
	}


	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}
    
	
	private String ipBanco="";
	


	class Logueo {
		
		private String user;
		private String password;
		
		public Logueo(String user, String password) {
			this.user = user;
			this.password = password;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	

	public ReportesUnificadoMB() {
		logger.debug("==== ReportesUnificadoMB() =====");
		ResourceBundle rb =ResourceBundle.getBundle("legal");
		String valor_ipBanco = rb.getString("ipBanco");
		String valor_userSpagoBI= rb.getString("userSpagoBI");
		String valor_passwordSpagoBI= rb.getString("passwordSpagoBI");
		ipBanco=valor_ipBanco;
		logger.debug("[ReporteUnificado]-URL Spago:"+valor_ipBanco);
		usuario = new Logueo(valor_userSpagoBI, valor_passwordSpagoBI);
		detallado =true;
		
		Date fecha = new Date();
		fecha.setYear(new Date().getYear()-1);
		fecha.setMonth(11);
		fecha.setDate(31);
		fecha.setHours(0);
		fecha.setMinutes(0);
		fecha.setSeconds(0);
		//fecha.set
		logger.info("fecha ::: "+fecha);
		
		/**** Para el reporte Totalizado Instanciación ****/
		filtrosDto = new FiltrosDto();
		instancias=new ArrayList<Instancia>();
		this.listarTipoImportes();
	}


	public String action(){
		String parametro= Utilitarios.capturarParametro("param");
		String hidden=parametro.substring(parametro.lastIndexOf("=")+1);
		logger.debug("parametro:"+parametro +"  hidden:"+hidden);
		if(hidden!=null&&!hidden.equals("")){
		   validad(hidden);
		   return  parametro;
		   } else {//Para otras opciones
			   return "";   
		   }
		    
		}
	
	public void validad(String hidden){
		logger.debug("Accion Reporte ===> "+hidden);
		
	    if (hidden.equals("14")){
			nombreReporte="Reporte Totalizado";
			ExecutarReporte_Totalizado();
		}
      }
	
	
	public boolean isDetallado() {
		return detallado;
	}

	public void setDetallado(boolean detallado) {
		this.detallado = detallado;
	}
    
	public boolean validarSeleccionTipoReporte(ActionEvent e){
		boolean retorno =true;
		if(filtrosDto.getTipoReporte()!=1 && filtrosDto.getTipoReporte()!=2){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Seleccione el tipo de Reporte", "Seleccione el tipo de Reporte"));
			logger.debug("Seleccione el tipo de Reporte");
			detallado=false;
			return retorno=false;
		
		}
		if(filtrosDto.getTipoImporte()!=null && (!filtrosDto.getTipoImporte().equals(""))){
			if(filtrosDto.getImporteMinimo()==null){
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Ingrese el Importe Mínimo", "Ingrese el Importe Mínimo"));
				logger.debug("Ingrese el Importe Mínimo");
				detallado=false;
				return retorno=false;
			}else if(filtrosDto.getImporteMaximo().equals(new BigDecimal(0))||filtrosDto.getImporteMaximo()==null){
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Ingrese el Importe Máximo", "Ingrese el Importe Máximo"));
				logger.debug("Ingrese el Importe Máximo");
				detallado=false;
				return retorno=false;
			}
			
		}
	
		return retorno;
	}
	public String  ExecutarReporte_Totalizado_Buscar3(ActionEvent e){
		logger.info("ExecutarReporte_Totalizado:: " +filtrosDto.toString());
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.getSession(true).setAttribute("filtrosDto_TEMP", filtrosDto);
		if(validarSeleccionTipoReporte(e)){
		if(filtrosDto.getTipoReporte()==1){
			detallado=false;
			if(llamarProcedimientoTotalizado(filtrosDto)){
				this.ExecutarReporte_Totalizado();
				return "";
			}else{
					   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, 
			            		"No se encontraron Resultados para la busqueda ",""));
				}
			
		}else if(filtrosDto.getTipoReporte()==2){
			try {
				
				logger.info("ExecutarReporte_Totalizado_Buscar3:: " +filtrosDto.toString());
				if(llamarProcedimientoDetallado(filtrosDto)){
					detallado=true;
					logger.info("ExecutarReporte_Totalizado_Buscar3:: " +detallado);
					iframeUrlString="";
					 return "../../main/download/reportDetallado_V5.htm?faces-redirect=true";
				}else{
					   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, 
			            		"No se encontraron Resultados para la busqueda ",""));
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				
			}
			
		}
		}
		return null;
	}

public void ExecutarReporte_Totalizado_Buscar(ActionEvent e){
	logger.info("=== ExecutarReporte_Totalizado_Buscar3() ==" +filtrosDto.toString());
	logger.info("[getFechaInicio]-TipoReporte():"+filtrosDto.getFechaInicio());
	logger.info("[getFechaFin]-TipoReporte():"+filtrosDto.getFechaFin());
	
	if(filtrosDto.getTipoReporte()==1){
		detallado=false;
		if(llamarProcedimientoTotalizado(filtrosDto)){
			this.ExecutarReporte_Totalizado();
		}else{
			logger.info("Hubo un error en el procedimiento");
		}
	}else if(filtrosDto.getTipoReporte()==2){
		/**** Si funcionara el Motor del Jasper del SpagoBI ****/
		/*detallado=true;
		if(llamarProcedimientoDetallado(filtrosDto)){
			this.ExecutarReporte_Detallado();
		}else{
			logger.info("Hubo un error en el procedimiento");
		}*/
		try {
			
			logger.info("ExecutarReporte_Totalizado_Buscar3:: " +filtrosDto.toString());
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			request.getSession(true).setAttribute("filtrosDto_TEMP", filtrosDto);	
			detallado=true;
			logger.info("ExecutarReporte_Totalizado_Buscar3:: " +detallado);
			iframeUrlString="";
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		
		
	}
	
}
private JdbcTemplate jdbcTemplate; 

public boolean llamarProcedimientoDetallado(FiltrosDto filtrosDto) {
	logger.info(" INFO :: llamarProcedimientoDetallado" +filtrosDto.toString());
	boolean retorno=true;
	DataSource dataSource=null;
	try {
		dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
		Object[] objecto=	new Object[28];
		objecto[0] =filtrosDto.getProceso();
		objecto[1] =filtrosDto.getVia();
		objecto[2] =filtrosDto.getInstancia();
		if(filtrosDto.getResponsable()!=null){
		objecto[3] =filtrosDto.getResponsable().getIdUsuario();
		logger.info("filtrosDto.getResponsable().getIdUsuario() " +filtrosDto.getResponsable().getIdUsuario());
		}
		objecto[4] =filtrosDto.getFechaInicio();
		objecto[5] =filtrosDto.getFechaFin();
		
		objecto[6] =filtrosDto.getBanca();
		objecto[7] =filtrosDto.getTerritorio();
		if(filtrosDto.getOficina()!=null){
		objecto[8] =filtrosDto.getOficina().getIdOficina();
		logger.info("filtrosDto.getOficina().getIdOficina() " +filtrosDto.getOficina().getIdOficina());
		}
		objecto[9] =filtrosDto.getDepartamento();
		objecto[10] =filtrosDto.getProvincia();
		objecto[11] =filtrosDto.getDistrito();
		
		objecto[12] =filtrosDto.getTipoExpediente();
		objecto[13] =filtrosDto.getCalificacion();
		if(filtrosDto.getOrgano()!=null){
		objecto[14] =filtrosDto.getOrgano().getIdOrgano();
		logger.info("filtrosDto.getOrgano().getIdOrgano() " +filtrosDto.getOrgano().getIdOrgano());
		}
		if(filtrosDto.getRecurrencia()!=null){
		objecto[15] =filtrosDto.getRecurrencia().getIdRecurrencia();
		logger.info("filtrosDto.getRecurrencia().getIdRecurrencia() " +filtrosDto.getRecurrencia().getIdRecurrencia());
		}
		objecto[16] =filtrosDto.getRiesgo();
		objecto[17] =filtrosDto.getActProcesal();
		if(filtrosDto.getMateria()!=null){
		objecto[18] =filtrosDto.getMateria().getIdMateria();
		logger.info("filtrosDto.getMateria().getIdMateria() " +filtrosDto.getMateria().getIdMateria());
		}
		objecto[19] =filtrosDto.getEstado();
		
		objecto[20] =filtrosDto.getTipoImporte();
		objecto[21] =filtrosDto.getMoneda();
		objecto[22] =filtrosDto.getImporteMinimo();
		objecto[23] =filtrosDto.getImporteMaximo();
		
		if(filtrosDto.getNombre()!=null){
		objecto[24] =filtrosDto.getNombre().getIdPersona();
		logger.info("filtrosDto.getNombre().getIdPersona() " +filtrosDto.getNombre().getIdPersona());
		}
		
		objecto[25] =filtrosDto.getRol();
		
		objecto[26] =filtrosDto.getEstudio();
		if(filtrosDto.getAbogado()!=null){
		objecto[27] =filtrosDto.getAbogado().getIdAbogado();
		logger.info("filtrosDto.getAbogado().getIdAbogado() " +filtrosDto.getAbogado().getIdAbogado());
		}
		objecto[28] =filtrosDto.getTipoUbigeo();
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	    String sql = "call GESLEG.SP_ETL_DETALLADO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		this.jdbcTemplate.update(sql,objecto);
		int cantidadInsertada = this.jdbcTemplate.queryForInt(" select count(*)CANTIDAD  from  GESLEG.FACT_DETALLADO");
		logger.info("cantidadInsertada  " +cantidadInsertada);
		if(cantidadInsertada==0){
		retorno=false;
		detallado=false;
		logger.info("No se encontro Resueltado  para la busqueda" +cantidadInsertada);
		}
		dataSource.getConnection().close();
	} catch (Exception e) {
		try {
			dataSource.getConnection().close();
			retorno=false;
		} catch (SQLException e1) {
			logger.error(" ERROR :: llamarProcedimientoDetallado", e);
		}
		logger.error(" ERROR :: llamarProcedimientoDetallado", e);
		
	}
	dataSource=null;
	System.gc();
	return retorno;
}
@SuppressWarnings("unchecked")
public boolean llamarProcedimientoTotalizado(FiltrosDto filtrosDto) {
	logger.debug(" === lamarProcedimientoTotalizado() ===");
	logger.info("[llamarSP]-Filtros:" + filtrosDto.toString());
	boolean retorno=true;
	DataSource dataSource=null;
	try {
		dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
		Object[] objecto=	new Object[28];
		
		objecto[0] =filtrosDto.getProceso();
		objecto[1] =filtrosDto.getVia();
		objecto[2] =filtrosDto.getInstancia();
		if(filtrosDto.getResponsable()!=null){
		objecto[3] =filtrosDto.getResponsable().getIdUsuario();
		logger.info("filtrosDto.getResponsable().getIdUsuario() " +filtrosDto.getResponsable().getIdUsuario());
		}
		if(filtrosDto.getFechaInicio()!=null){
			objecto[4] = new java.sql.Date(filtrosDto.getFechaInicio().getTime());
		}
		if(filtrosDto.getFechaFin()!=null){
			objecto[5] = new java.sql.Date(filtrosDto.getFechaFin().getTime());
		}
		
		objecto[6] =filtrosDto.getBanca();
		objecto[7] =filtrosDto.getTerritorio();
		if(filtrosDto.getOficina()!=null){
		objecto[8] =filtrosDto.getOficina().getIdOficina();
		logger.info("filtrosDto.getOficina().getIdOficina() " +filtrosDto.getOficina().getIdOficina());
		}
		objecto[9] =filtrosDto.getDepartamento();
		objecto[10] =filtrosDto.getProvincia();
		objecto[11] =filtrosDto.getDistrito();
		
		objecto[12] =filtrosDto.getTipoExpediente();
		objecto[13] =filtrosDto.getCalificacion();
		if(filtrosDto.getOrgano()!=null){
		objecto[14] =filtrosDto.getOrgano().getIdOrgano();
		logger.info("filtrosDto.getOrgano().getIdOrgano() " +filtrosDto.getOrgano().getIdOrgano());
		}
		if(filtrosDto.getRecurrencia()!=null){
		objecto[15] =filtrosDto.getRecurrencia().getIdRecurrencia();
		logger.info("filtrosDto.getRecurrencia().getIdRecurrencia() " +filtrosDto.getRecurrencia().getIdRecurrencia());
		}
		objecto[16] =filtrosDto.getRiesgo();
		objecto[17] =filtrosDto.getActProcesal();
		if(filtrosDto.getMateria()!=null){
		objecto[18] =filtrosDto.getMateria().getIdMateria();
		logger.info("filtrosDto.getMateria().getIdMateria() " +filtrosDto.getMateria().getIdMateria());
		}
		objecto[19] =filtrosDto.getEstado();
		
		objecto[20] =filtrosDto.getTipoImporte();
		objecto[21] =filtrosDto.getMoneda();
		objecto[22] =filtrosDto.getImporteMinimo();
		objecto[23] =filtrosDto.getImporteMaximo();
		
		if(filtrosDto.getNombre()!=null){
		objecto[24] =filtrosDto.getNombre().getIdPersona();
		logger.info("filtrosDto.getNombre().getIdPersona() " +filtrosDto.getNombre().getIdPersona());
		}
		
		objecto[25] =filtrosDto.getRol();
		
		objecto[26] =filtrosDto.getEstudio();
		if(filtrosDto.getAbogado()!=null){
		objecto[27] =filtrosDto.getAbogado().getIdAbogado();
		logger.info("filtrosDto.getAbogado().getIdAbogado() " +filtrosDto.getAbogado().getIdAbogado());
		}
		
		objecto[28] =filtrosDto.getTipoUbigeo();
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	    String sql = "call GESLEG.SP_ETL_TOTALIZADO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    int cantidadInsertada = this.jdbcTemplate.queryForInt(" select count(*)CANTIDAD  from  GESLEG.FACT_TOTALIZADO");
		logger.info("cantidadInsertada  " +cantidadInsertada);
	 
	    
	   this.jdbcTemplate.update (sql,objecto);
		logger.debug("[llamarSP]-Despues de llamar al Store Procedure");
		
		if(cantidadInsertada==0){
			retorno=false;
			detallado=false;
			logger.info("No se encontro Resueltado  para la busqueda" +cantidadInsertada);
			}
		
		dataSource.getConnection().close();
	} catch (Exception e) {
		try {
			dataSource.getConnection().close();
		 retorno=false;
		} catch (SQLException e1) {
			logger.error(" ERROR :: llamarProcedimientoTotalizado", e);
		}
		logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al llamarProcedimientoTotalizado: ", e);
	}
	dataSource=null;
	System.gc();
	
	logger.debug("[llamarSP]-retorno:"+retorno);
	
	return retorno;
}

public void limpiar(){
	logger.info("limpiar");
	filtrosDto=new FiltrosDto();
	iframeUrlString="";
	detallado=false;
}
public void listarTipoImportes(){
	 listaTiposImportes=new ArrayList<Generico>();
	Generico tipo1=new Generico();
	tipo1.setKey("1");
	tipo1.setDescripcion("Caución");
	Generico tipo2=new Generico();
	tipo2.setKey("2");
	tipo2.setDescripcion("Medida cautelar");
	Generico tipo3=new Generico();
	tipo3.setKey("3");
	tipo3.setDescripcion("Cuantias");
	
	listaTiposImportes.add(tipo1);
	listaTiposImportes.add(tipo2);
	listaTiposImportes.add(tipo3);

}
public void listarMonedas(){
  monedas = consultaService.getMonedas();
	
}

public void ExecutarReporte_Totalizado(){
	logger.info(" === ExecutarReporte_Totalizado_Buscar() ====");
	try {
		logger.info("[REPORTE_TOTALIZADO]-Filtros: "+filtrosDto.toString());
		logger.debug("[REPORTE_TOTALIZADO]-usuario:"+usuario);
		if(validarConexionSpaobi(usuario)){
			logger.debug("[REPORTE_TOTALIZADO]-Despues de validarConexionSpaobi");
			obtenerDocumento(usuario,"REP_TOTALIZADO");
		}else{
			logger.debug("No hay coneccion ...");
			Utilitarios.mensajeInfo("Info ",SglConstantes.MSJ_NO_CONECCION_SPAGOBI);
		}
	} catch (RemoteException e) {
		logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al ExecutarReporte_Totalizado:"+e);
	}
}
public void ExecutarReporte_Detallado(){
	logger.info("ExecutarReporte_Detallado");
	try {
		logger.info(""+filtrosDto.toString());
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"REP_DETALLADO");
		}else{
			logger.debug("No hay coneccion ...");
			Utilitarios.mensajeInfo("Info ",SglConstantes.MSJ_NO_CONECCION_SPAGOBI);
		}
	} catch (RemoteException e) {
		logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al ExecutarReporte_Detallado: ",e);
	}
}
@SuppressWarnings("unchecked")
public void obtenerActividadesProcesales(){
	try {
	actividadesprocesales=new ArrayList<Actividad>();
	actividadesprocesales=consultaService.getActividadesProcesales();
	} catch (Exception e) {
		logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"ActividadesProcesales:"+e);
	}
}

@SuppressWarnings("unchecked")
public void obtenerEstudios(){
	try {
		estudios=new ArrayList<Estudio>();
		estudios=consultaService.getEstudios();
	} catch (Exception e) {
		logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Estudios:"+e);
	}
}

@SuppressWarnings("unchecked")
public void obtenerBancas(){
	try {
		bancas=new ArrayList<GrupoBanca>();
		bancas=consultaService.getGrupoBancas();
	} catch (Exception e) {
		logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"Bancas:"+e);
	}
}
@SuppressWarnings("unchecked")
public void cambioTerritorio(){
	
	if (filtrosDto.getBanca() != 0 ) {  
		territorios=consultaService.getTerritorios(filtrosDto.getBanca());
	 } else {	
		 territorios = new ArrayList<Territorio>();
     }
}
@SuppressWarnings("unchecked")
public void obtenerTerritorio(){
	logger.info("filtro.getTerritorio : "+filtrosDto.getTerritorio());
}
@SuppressWarnings("unchecked")
public void cambiarAbogado(){
	logger.info("filtro.cambiarAbogado : "+filtrosDto.getEstudio());
}

public List<Oficina> completeOficina(String query) {
	logger.debug("=== completeOficina ===");

	List<Oficina> results = new ArrayList<Oficina>();
	List<Oficina> oficinas;
	if(filtrosDto.getTerritorio()!=0){
		oficinas=consultaService.getOficinas(filtrosDto.getTerritorio(),null);
	}else{
		oficinas=  consultaService.getOficinas(null); 
	}
	

	if (oficinas != null) {
		logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "oficinas es:["+ oficinas.size() + "]. ");
	}

	for (Oficina oficina : oficinas) {

		if (oficina.getTerritorio() != null) {

			String texto = oficina.getCodigo()
					.concat(" ").concat(oficina.getNombre() != null ? oficina.getNombre().toUpperCase() : "").concat(" (")
					.concat(oficina.getTerritorio().getDescripcion() != null ? oficina.getTerritorio().getDescripcion().toUpperCase(): "").concat(")");
			//logger.debug("Texto: " + texto);

			if (texto.contains(query.toUpperCase())) {
				oficina.setNombreDetallado(texto);
				results.add(oficina);
			}
		}
	}
	return results;
}
@SuppressWarnings("unchecked")
public List<Abogado> completeAbogado(String query) {
	List<Abogado> abogados = consultaService.getAbogados();
	if(filtrosDto.getEstudio()!=0){
		abogados=consultaService.getAbogados(filtrosDto.getEstudio());
	}else{
		abogados=  consultaService.getAbogados();
	}
	
	List<Abogado> results = new ArrayList<Abogado>();

	if (abogados != null) {
		logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "abogados es:["+ abogados.size() + "]. ");
	}

	for (Abogado abog : abogados) {
		String nombreCompletoMayuscula = ""	.concat(abog.getNombres() != null ? 
				abog.getNombres().toUpperCase() : "").concat(" ").concat(abog.getApellidoPaterno() != null ? abog
				.getApellidoPaterno().toUpperCase() : "").concat(" ").concat(abog.getApellidoMaterno() != null ? abog
				.getApellidoMaterno().toUpperCase() : "");
		if (nombreCompletoMayuscula.contains(query.toUpperCase())) {
			abog.setNombreCompletoMayuscula(nombreCompletoMayuscula);
			results.add(abog);
		}
	}

	return results;
}
@SuppressWarnings("unchecked")
public void cambioProceso() {
	if (filtrosDto.getProceso() != 0 ) {  
		try {
	     vias = consultaService.getViasByProceso(filtrosDto.getProceso());
	    } catch (Exception e) {
	    	logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"en cambioProceso:"+e);
	    }
	 } else {	
		vias = new ArrayList<Via>();
	}
}
public void cambioVia() {

	if (filtrosDto.getVia() != 0) {
		instancias = consultaService.getInstanciasByVia(filtrosDto.getVia());
	} else {
		instancias = new ArrayList<Instancia>();
	}

}
public void obtenerDepartamentos() {
	ubigeosDepartamento=new ArrayList<Generico>();
	ubigeosDepartamento=consultaService.getDepartamentos();
}
public void cambiarProvincias() {
	if (filtrosDto.getDepartamento() != "") {
		ubigeosProvincia=consultaService.getProvincias(filtrosDto.getDepartamento());
	}else{
		ubigeosProvincia=new ArrayList<Generico>();
	}
}
public void cambiarDistrito() {
	if (filtrosDto.getProvincia() != "") {
		ubigeosDistrito=consultaService.getDistritos(filtrosDto.getProvincia());
	}else{
		ubigeosDistrito=new ArrayList<Generico>();
	}
}


	private boolean validarConexionSpaobi(Logueo usuario) throws RemoteException{
		if (usuario.getUser() != null && usuario.getPassword() != null) {
			TestConnectionServiceProxy proxy = new TestConnectionServiceProxy(usuario.getUser(), usuario.getPassword());
		    proxy.setEndpoint(ipBanco+"/SpagoBI/sdk/TestConnectionService");
			return  proxy.connect();
		}
		return false;
	}
	private void obtenerDocumento(Logueo usuario,String nombreReporte) throws RemoteException{
		logger.debug("=== inicia obtenerDocumento() ====");
		logger.debug("nombreReporte: "+nombreReporte);
		DocumentsServiceProxy proxy = new DocumentsServiceProxy(usuario.getUser(), usuario.getPassword());
		proxy.setEndpoint(ipBanco+"/SpagoBI/sdk/DocumentsService");
		 documents = proxy.getDocumentsAsList(null, null, null);
		 logger.info("Tamanio al obtener los Documentos::: "+documents.length);
		//session.setAttribute("spagobi_documents", documents);
		String documentIdStr =null;
		for (int i = 0; i < documents.length; i++) {
			SDKDocument aDoc = documents[i];
			//logger.debug("documents["+i+"] -->" +documents[i].getName());
			if(aDoc.getName().equals(nombreReporte)){
				 documentIdStr = aDoc.getId()+"";
				 logger.debug("Se encontro el -> id:"+documentIdStr + " Nombre:"+aDoc.getName());
				 break;
			}  
			
		}
		
		 documentId = new Integer(documentIdStr);
		//session.setAttribute("spagobi_documentId", documentId);
		String[] validRoles = null;
		 role =null;
		try {
			proxy.setEndpoint(ipBanco+"/SpagoBI/sdk/DocumentsService");
			validRoles =  proxy.getCorrectRolesForExecution(documentId);
			if (validRoles.length == 0) {
				logger.debug("Validacion 001  "+"En la validacion 001" );
				logger.debug("Usuario no executo el Documento");
			} else if (validRoles.length == 1) {
				logger.debug("Hay rol valido -> "+validRoles[0]);
				obtenerParametrosDocumento(usuario, validRoles[0]);
				//response.sendRedirect("documentParameters.jsp?role=" + validRoles[0]);
			} else {
					for (int i = 0; i < validRoles.length; i++) {
						role = validRoles[i];
						logger.debug("validRoles[i] " +validRoles[i]);
					}
			}
		} catch (NonExecutableDocumentException e) {
			logger.error("Usuario no puede ejecutar el documento "+ e);
		} catch (Exception e) {
			logger.debug("Ha ocurrido una excepcion al obtenerDocumento(): "+e);
		}
	}
	
	private void obtenerParametrosDocumento(Logueo usuario,String role) throws Exception{
			DocumentsServiceProxy proxy = new DocumentsServiceProxy(usuario.getUser(), usuario.getPassword());
			proxy.setEndpoint(ipBanco+"/SpagoBI/sdk/DocumentsService");
			//String role = request.getParameter("role");
			//session.setAttribute("spagobi_role", role);
			logger.debug("role " +role);
			logger.debug("documentId " +documentId);
			 parameters = proxy.getDocumentParameters(documentId, role);
			//session.setAttribute("spagobi_document_parameters", parameters);
			if (parameters == null || parameters.length == 0) {
				logger.debug("No hay parametros ...");
				executionjsp(usuario, role);
				//response.sendRedirect("execution.jsp");
			} else {
				  for (int i = 0; i < parameters.length; i++) {
					   SDKDocumentParameter aDocParameter = parameters[i];
						
						HashMap values = proxy.getAdmissibleValues(aDocParameter.getId(), role);
						if (values == null || values.isEmpty()) {
							logger.debug("xxx aDocParameter.getUrlName() " +aDocParameter.getUrlName()); 
						
						} else {
							logger.debug("aDocParameter.getUrlName() "+aDocParameter.getUrlName());
							Set entries = values.entrySet();
							Iterator it = entries.iterator();
							while (it.hasNext()) {
								Map.Entry entry = (Map.Entry) it.next();
								logger.debug("entry.getKey() " + entry.getValue());
							}
						}
					}
			
			}
		}
		
	@SuppressWarnings("unused")
	private void executionjsp(Logueo usuario,String role) throws Exception{
		logger.debug("== inicia executionjsp() === ");
			for (int i = 0; i < documents.length; i++) {
				SDKDocument aDocument = documents[i];
				if (aDocument.getId().equals(documentId)) {
					document = aDocument;
				}
			}
			
			parameterValues = new StringBuffer();
			if (parameters != null && parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					SDKDocumentParameter aParameter = parameters[i];
					logger.debug("aParameter.getUrlName() " +aParameter.getUrlName());
					String value ="";// request.getParameter(aParameter.getUrlName());
					if (value != null) {
						aParameter.setValues(new String[]{value});
						if (parameterValues.length() > 0) {
							parameterValues.append("&");
						}
						parameterValues.append(aParameter.getUrlName() + "=" + value);
					} else {
						aParameter.setValues(null);
					}
				}
			}
			
			executarTag(usuario);

	}
	@SuppressWarnings({ "rawtypes", "unused", "deprecation" })
	private void executarTag(Logueo usuario) throws Exception{
			String spagobiContext =ipBanco+"/SpagoBI";
			String userId = usuario.getUser();
			String documentLabel=null;
			String executionRole=role;
			String parametersStr=parameterValues.toString();
			logger.debug("parameterValues.toString() " +parameterValues.toString());
			Map parametersMap=null;
			Boolean displayToolbar=Boolean.TRUE;
			Boolean displaySliders=Boolean.TRUE;
			String iframeStyle="height:600px; width:100%";
			String theme=null;
			String authenticationTicket=null;
		
		StringBuffer iframeUrl = new StringBuffer();
		iframeUrl.append(spagobiContext + "/servlet/AdapterHTTP?NEW_SESSION=true");
		iframeUrl.append("&ACTION_NAME=EXECUTE_DOCUMENT_ACTION");
		iframeUrl.append("&" + SsoServiceInterface.USER_ID + "=" + userId);

		if (documentId == null && documentLabel == null) {
			throw new Exception("Neither document id nor document label are specified!!");
		}
		if (documentId != null) {
			iframeUrl.append("&OBJECT_ID=" + documentId);
		} else {
			iframeUrl.append("&OBJECT_LABEL=" + documentLabel);
		}
		if (parametersStr != null) iframeUrl.append("&PARAMETERS=" + URLEncoder.encode(parametersStr));
		if (parametersMap != null && !parametersMap.isEmpty()) {
			Set keys = parametersMap.keySet();
			Iterator keysIt = keys.iterator();
			while (keysIt.hasNext()) {
				String urlName = (String) keysIt.next();
				Object valueObj = parametersMap.get(urlName);
				if (valueObj != null) {
					iframeUrl.append("&" + URLEncoder.encode(urlName) + "=" + URLEncoder.encode(valueObj.toString()));
				}
			}
		}
		if (executionRole != null) iframeUrl.append("&ROLE=" + URLEncoder.encode(executionRole));
		if (displayToolbar != null) iframeUrl.append("&TOOLBAR_VISIBLE=" + displayToolbar.toString());
		if (displaySliders != null) iframeUrl.append("&SLIDERS_VISIBLE=" + displaySliders.toString());
		if (theme != null)	iframeUrl.append("&theme=" + theme);
		if (authenticationTicket != null) iframeUrl.append("&auth_ticket=" + URLEncoder.encode(authenticationTicket));
		 //iframeUrlString="http://118.180.34.15:9084/SpagoBI//servlet/AdapterHTTP?NEW_SESSION=true&ACTION_NAME=EXECUTE_DOCUMENT_ACTION&user_id=biadmin&OBJECT_ID=253&PARAMETERS=&ROLE=%2Fspagobi%2Fadmin&TOOLBAR_VISIBLE=true&SLIDERS_VISIBLE=false".toString();
		iframeUrlString=iframeUrl.toString();
		
		logger.debug("[ejecutarTag]->iframeStyle " +iframeStyle);
		logger.debug("[ejecutarTag]->iframeUrl: " +iframeUrl );
		logger.debug("[ejecutarTag]->iframeUrlString: " +iframeUrlString );
		logger.info("-------------------------------------------------------");
		
		/*//import it.eng.spagobi.jpivotaddins.bean.ToolbarBean;
		it.eng.spagobi.jpivotaddins.bean.ToolbarBean tb= new ToolbarBean();
		<% if(tb.getButtonFatherMembVisibleB().booleanValue()){%>
	  	<wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{table01.extensions.axisStyle.levelStyle}"/>
	  <% } %>
	  
	  <% if(tb.getButtonHideEmptyVisibleB().booleanValue()){%>
	  	<wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{table01.extensions.nonEmpty.buttonPressed}"/>
	  <% } % /> */
	  
	  
	  
	}
	

	
	
	

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}
	
	public String getIframeUrl() {
		return iframeUrl;
	}

	public void setIframeUrl(String iframeUrl) {
		this.iframeUrl = iframeUrl;
	}

	public String getIframeStyle() {
		return iframeStyle;
	}

	public void setIframeStyle(String iframeStyle) {
		this.iframeStyle = iframeStyle;
	}
	

	public FiltrosDto getFiltrosDto() {
		return filtrosDto;
	}

	public void setFiltrosDto(FiltrosDto filtrosDto) {
		this.filtrosDto = filtrosDto;
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

	public List<GrupoBanca> getBancas() {
		bancas=consultaService.getGrupoBancas();
		return bancas;
	}

	public void setBancas(List<GrupoBanca> bancas) {
		this.bancas = bancas;
	}

	public List<Territorio> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(List<Territorio> territorios) {
		this.territorios = territorios;
	}

	
	public List<Generico> getUbigeosDepartamento() {
		this.obtenerDepartamentos();
		return ubigeosDepartamento;
	}

	public void setUbigeosDepartamento(List<Generico> ubigeosDepartamento) {
		this.ubigeosDepartamento = ubigeosDepartamento;
	}

	public List<Generico> getUbigeosProvincia() {
		return ubigeosProvincia;
	}

	public void setUbigeosProvincia(List<Generico> ubigeosProvincia) {
		this.ubigeosProvincia = ubigeosProvincia;
	}

	public List<Generico> getUbigeosDistrito() {
		return ubigeosDistrito;
	}

	public void setUbigeosDistrito(List<Generico> ubigeosDistrito) {
		this.ubigeosDistrito = ubigeosDistrito;
	}

	public List<Actividad> getActividadesprocesales() {
		this.obtenerActividadesProcesales();
		return actividadesprocesales;
	}

	public void setActividadesprocesales(List<Actividad> actividadesprocesales) {
		this.actividadesprocesales = actividadesprocesales;
	}

	public List<Estudio> getEstudios() {
		this.obtenerEstudios();
		return estudios;
	}

	public void setEstudios(List<Estudio> estudios) {
		this.estudios = estudios;
	}
	
	
	public List<Usuario> completeResponsable(String query) {
		List<Usuario> results = new ArrayList<Usuario>();

		List<Usuario> usuarios = consultaService.getUsuarios();

		if (usuarios != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "usuarios es:["+ usuarios.size() + "]. ");
		}

		for (Usuario usuario : usuarios) {

			if (usuario.getNombres().toUpperCase()
					.contains(query.toUpperCase())
					|| usuario.getApellidoPaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getApellidoMaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getCodigo().toUpperCase()
							.contains(query.toUpperCase())) {

				usuario.setNombreDescripcion(usuario.getCodigo() + " - "
						+ usuario.getNombres() + " "
						+ usuario.getApellidoPaterno() + " "
						+ usuario.getApellidoMaterno());

				results.add(usuario);
			}

		}

		return results;
	}
	@SuppressWarnings("unchecked")
	public List<Organo> completeOrgano(String query) 
	{
		List<Organo> results = new ArrayList<Organo>();
		List<Organo> organos = consultaService.getOrganos();

		for (Organo organo : organos) 
		{ 
			String descripcion = "".concat(organo.getNombre() != null ? organo.getNombre().toUpperCase() : "")
					.concat("(").concat(organo.getUbigeo() != null ? organo
					.getUbigeo().getDistrito() : "")
					.concat(", ").concat(organo.getUbigeo() != null ? organo
					.getUbigeo().getProvincia().toUpperCase() : "")
					.concat(", ").concat(organo.getUbigeo() != null ? organo
					.getUbigeo().getDepartamento().toUpperCase() : "").concat(")");
			
			if (descripcion.toUpperCase().contains(query.toUpperCase())) 
			{
				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}
	
		return results;
	}

	public List<Generico> getListaTiposImportes() {
		return listaTiposImportes;
	}

	public void setListaTiposImportes(List<Generico> listaTiposImportes) {
		this.listaTiposImportes = listaTiposImportes;
	}

	public List<Moneda> getMonedas() {
		this.listarMonedas();
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}
	
	
	public List<Persona> completePersona(String query) {
		logger.debug("=== completePersona ===");
		List<Persona> results = new ArrayList<Persona>();
		List<Persona> personas = consultaService.getPersonas();
		if (personas != null) {
			logger.debug(SglConstantes.MSJ_TAMANHIO_LISTA + "personas es:["+ personas.size() + "]. ");
		}

		for (Persona pers : personas) {
			String nombreCompletoMayuscula = ""
					.concat(pers.getNombres() != null ? pers.getNombres().toUpperCase() : "").concat(" ")
					.concat(pers.getApellidoPaterno() != null ? pers.getApellidoPaterno().toUpperCase() : "")
					.concat(" ").concat(pers.getApellidoMaterno() != null ? pers.getApellidoMaterno().toUpperCase() : "");

			if (nombreCompletoMayuscula.contains(query.toUpperCase())) {
				pers.setNombreCompletoMayuscula(nombreCompletoMayuscula);
				results.add(pers);
			}
		}
		return results;
	}

	public List<RolInvolucrado> getRolInvolucrados() {
		rolInvolucrados = consultaService.getRolInvolucrados();
		return rolInvolucrados;
	}

	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
	}
	
	
	public String getIframeUrlString() {
		return iframeUrlString;
	}

	public void setIframeUrlString(String iframeUrlString) {
		this.iframeUrlString = iframeUrlString;
	}
	
	
	}
	
		
	



