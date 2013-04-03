package com.hildebrando.legal.mb.reportes;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.exceptions.NonExecutableDocumentException;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.TestConnectionServiceProxy;
import it.eng.spagobi.services.common.SsoServiceInterface;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.general.entities.Generico;
import com.bbva.persistencia.generica.dao.EnvioMailDao;
import com.bbva.persistencia.generica.dao.InvolucradoDao;
import com.bbva.persistencia.generica.dao.ReportesDao;
import com.bbva.persistencia.generica.dao.impl.EnvioMailDaoImpl;
import com.bbva.persistencia.generica.dao.impl.ReportesDaoImpl;
import com.bbva.persistencia.generica.util.Utilitarios;
import com.hildebrando.legal.dto.ReporteLitigiosDto;
import com.hildebrando.legal.util.SglConstantes;



@ManagedBean(name = "reportesMB")
@SessionScoped
public class ReportesMB {
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
	private ParametrosReportesVistaDto paramRepVistaDto;
	private List<Generico> lstGenCalificacion;
	private List<Generico> lstGenInstancia;
	private List<ReporteLitigiosDto> lstStockAnterior;
	private Generico tipoCambioBean;

	public static Logger logger = Logger.getLogger(ReportesMB.class);
	

	
	//TODO Cambiar esta IP en un archivo properties o como parámetro en BD.	
	//private String ipBanco="http://172.31.9.41:9084";
	private String ipBanco="http://118.180.34.15:9084";
	//private String ipBanco="http://10.172.0.107:8080";
	public String getIframeUrlString() {
		return iframeUrlString;
	}

	public void setIframeUrlString(String iframeUrlString) {
		this.iframeUrlString = iframeUrlString;
	}

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
	
	
	public class ParametrosReportesVistaDto {
		
		private Date fechaDate;
		private String fechaString;
		private Double tipoCambioSoles;
		private Double tipoCambioDolares;
		
		
		
		
		public ParametrosReportesVistaDto() {
			super();
		}

		public ParametrosReportesVistaDto(Date fechaDate, String fechaString,
				Double tipoCambioSoles, Double tipoCambioDolares) {
			this.fechaDate = fechaDate;
			this.fechaString = fechaString;
			this.tipoCambioSoles = tipoCambioSoles;
			this.tipoCambioDolares = tipoCambioDolares;
		}
		
		public Date getFechaDate() {
			return fechaDate;
		}
		public void setFechaDate(Date fechaDate) {
			this.fechaDate = fechaDate;
		}
		public String getFechaString() {
			return fechaString;
		}
		public void setFechaString(String fechaString) {
			this.fechaString = fechaString;
		}
		public Double getTipoCambioSoles() {
			return tipoCambioSoles;
		}
		public void setTipoCambioSoles(Double tipoCambioSoles) {
			this.tipoCambioSoles = tipoCambioSoles;
		}
		public Double getTipoCambioDolares() {
			return tipoCambioDolares;
		}
		public void setTipoCambioDolares(Double tipoCambioDolares) {
			this.tipoCambioDolares = tipoCambioDolares;
		}
		
		
		
	
		
	}
	
	public ReportesMB() {
		logger.debug("==== ReportesMB() =====");
		ResourceBundle rb =ResourceBundle.getBundle("legal");
		String valor_ipBanco = rb.getString("ipBanco");
		String valor_userSpagoBI= rb.getString("userSpagoBI");
		String valor_passwordSpagoBI= rb.getString("passwordSpagoBI");
		ipBanco=valor_ipBanco;
		logger.debug(" ipBanco: "+valor_ipBanco);
		usuario = new Logueo(valor_userSpagoBI, valor_passwordSpagoBI);
		paramRepVistaDto=new ParametrosReportesVistaDto();
		paramRepVistaDto.setFechaString(Utilitarios.formatoFecha(new Date()));
	}


	public String action(){
		String parametro= Utilitarios.capturarParametro("param");
		String hidden=parametro.substring(parametro.lastIndexOf("=")+1);
		if(hidden!=null&&!hidden.equals("")){
		   validad(hidden);
		   return  parametro;
		   } else {//Para otras opciones
			   return "";   
		   }
		    
		}
	
	public void validad(String hidden){
		logger.debug("Accion Reporte ---> "+hidden);
		
		if (hidden.equals("1")) {
			obtenerTipoCambio();
			listarStockAnterior();
			nombreReporte = "Actividad Litigios";
			ExecutarReporteActividadLitigio();
			
		} else if (hidden.equals("2")) {
			nombreReporte = "Movimiento Provisiones";
			obtenerTipoCambio();
			ExecutarReporteMovimientoProvisiones();
			
		} else if (hidden.equals("3")) {
			nombreReporte = "Consolidado de Organizaciones";
			ExecutarReporteConsolidadoOrganizaciones();
		} else if (hidden.equals("4")) {
			nombreReporte = "Reportes Civil Contra";
			ExecutarReporteProcesosCivilesContra();
		} else if (hidden.equals("5")) {
			nombreReporte = "Reporte Civil Favor";
			ExecutarReporteProcesosCivilesFavor();
		} else if (hidden.equals("6")) {
			nombreReporte = "Reporte Penal Contra";
			ExecutarReporteProcesosPenalesContra();
		} else if (hidden.equals("7")){
			nombreReporte="Reporte Penal Favor";
			ExecutarReporteProcesosPenalesFavor();
		}else if (hidden.equals("8")){
			nombreReporte="Reporte Administrativo Favor";
			ExecutarReporteAdministrativoFavor();
		}else if (hidden.equals("9")){
			nombreReporte="Reporte Administrativo Contra";
			ExecutarReporteAdministrativoContra();
		}else if (hidden.equals("10")){
			nombreReporte="DashBoard";
			ExecutarReporte_DASHBBVA_CD();
		}else if (hidden.equals("11")){
			nombreReporte="Reporte Indecopi";
			ExecutarReporte_INDECOPI();
		}else if (hidden.equals("12")){
			nombreReporte="Organización Civil";
			  consultaCalificacion();
		      consultaInstancia();
			ExecutarReporteOrganizacionesCivil();
			
		}
	      
}
	public void ExecutarReporte_DASHBBVA_CD(){
		logger.debug("ExecutarReporte_DASHBBVA_CD");
		try {
			if(validarConexionSpaobi(usuario)){
				obtenerDocumento(usuario,"DASHBBVA_CD");
			}else{
				logger.debug("No hay coneccion ...");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public void ExecutarReporteAdministrativoContra(){
		logger.debug("ExecutarReporteAdministrativoContra");
		try {
			if(validarConexionSpaobi(usuario)){
				obtenerDocumento(usuario,"REP_ADM_CONTRA");
			}else{
				logger.debug("No hay coneccion ...");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
public void ExecutarReporteActividadLitigio(){
	logger.debug("=== ExecutarReporteActividadLitigio ===");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"RPT_ConContencioso");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		logger.error("Ha ocurrido una excepcion en ExecutarReporteActividadLitigio(): "+e);
	}
}
private void ExecutarReporteMovimientoProvisiones(){
	logger.debug("==== ExecutarReporteMovimientoProvisiones() ======");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"RPT_ConMovProvision");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		logger.error("Ha ocurrido una excepcion en ExecutarReporteMovimientoProvisiones(): "+e);
	}
}
private void ExecutarReporteConsolidadoOrganizaciones(){
	logger.debug("ExecutarReporteConsolidadoOrganizaciones");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"RPT_Organizacion");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		logger.error("Ha ocurrido una excepcion en ExecutarReporteConsolidadoOrganizaciones(): "+e);
	}
}


private void ExecutarReporteProcesosCivilesContra(){
	logger.debug("ExecutarReporteConsolidadoOrganizaciones");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"proCivilContra");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		logger.error("Ha ocurrido una excepcion en ExecutarReporteProcesosCivilesContra(): "+e);
	}
}
private void ExecutarReporteProcesosCivilesFavor(){
	logger.debug("ExecutarReporteConsolidadoOrganizaciones");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"proCivilFavor");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		logger.error("Ha ocurrido una excepcion en ExecutarReporteProcesosCivilesFavor(): "+e);
	}
}

private void ExecutarReporteProcesosPenalesContra(){
	logger.debug("ExecutarReporteConsolidadoOrganizaciones");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"procPenalContra");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		logger.error("Ha ocurrido una excepcion en ExecutarReporteProcesosPenalesContra(): "+e);
	}
}
private void ExecutarReporteProcesosPenalesFavor (){
	logger.debug("ExecutarReporteProcesosPenalesFavor ");
	
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"procPenalFavor");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		logger.error("Ha ocurrido una excepcion en ExecutarReporteProcesosPenalesFavor(): "+e);
	}
}
public void ExecutarReporteAdministrativoFavor(){
	logger.debug("ExecutarReporteAdministrativoFavor");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"REP_ADM_FAVOR");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		e.printStackTrace();
	}
}
private void ExecutarReporteOrganizacionesCivil(){
	logger.debug("ExecutarReporteOrganizacionesCivil");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"RPT_Organizacion_Civil");
		}else{
			logger.debug("No hay coneccion ...");
		}
	} catch (RemoteException e) {
		e.printStackTrace();
	}
} 
public void ExecutarReporte_INDECOPI(){
	logger.debug("ExecutarReporte_INDECOPI");
	try {
		if(validarConexionSpaobi(usuario)){
			obtenerDocumento(usuario,"INVINDECOPI");
		}else{
			logger.debug("No hay coneccion ...");
			Utilitarios.mensajeInfo("Info ",SglConstantes.MSJ_NO_CONECCION_SPAGOBI);
		}
	} catch (RemoteException e) {
		e.printStackTrace();
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
		logger.debug("Inicia nombreReporte() ==> "+nombreReporte);
		DocumentsServiceProxy proxy = new DocumentsServiceProxy(usuario.getUser(), usuario.getPassword());
		proxy.setEndpoint(ipBanco+"/SpagoBI/sdk/DocumentsService");
		 documents = proxy.getDocumentsAsList(null, null, null);
		 logger.info("Tamanio al obtener los Documentos::: "+documents.length);
		//session.setAttribute("spagobi_documents", documents);
		String documentIdStr =null;
		for (int i = 0; i < documents.length; i++) {
			SDKDocument aDoc = documents[i];
			logger.debug("documents["+i+"] -->" +documents[i].getName());
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
		
		logger.debug("[ejecutarTag]->iframeUrl: " +iframeUrl );
		logger.debug("[ejecutarTag]->iframeUrlString: " +iframeUrlString );
		
		logger.debug( " iframeStyle " +iframeStyle);
		/*//import it.eng.spagobi.jpivotaddins.bean.ToolbarBean;
		it.eng.spagobi.jpivotaddins.bean.ToolbarBean tb= new ToolbarBean();
		<% if(tb.getButtonFatherMembVisibleB().booleanValue()){%>
	  	<wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{table01.extensions.axisStyle.levelStyle}"/>
	  <% } %>
	  
	  <% if(tb.getButtonHideEmptyVisibleB().booleanValue()){%>
	  	<wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{table01.extensions.nonEmpty.buttonPressed}"/>
	  <% } % /> */
	  
	  
	  
	}
	
	@SuppressWarnings("unchecked")
	public void consultaCalificacion(){
		String hql ="select * from (select calificacion.nombre AS CALIFICACION,count(expediente.id_expediente)CANTIDAD" +
				" from GESLEG.expediente inner join GESLEG.calificacion on calificacion.id_calificacion=expediente.id_calificacion" +
				" where exp_id_expediente is null and id_proceso=1 " +
				" group by  calificacion.nombre)xx";
		List lstCalificacion=new ArrayList<Generico>();
	    lstCalificacion =(ArrayList<Generico>) SpringInit.devolverSession().createSQLQuery(hql).list();
	   
	logger.debug("lstGenCalificacion: "+lstCalificacion.size());
	Iterator it = lstCalificacion.iterator();
	Generico genericoDto=new Generico();
	lstGenCalificacion=new ArrayList<Generico>();
    while (it.hasNext( )) {
       Object[] result = (Object[])it.next();  
       genericoDto=new Generico();
       genericoDto.setDescripcion((String)result[0]);
       genericoDto.setCantidad((BigDecimal)result[1]+"");
       lstGenCalificacion.add(genericoDto);
    }
	}
	
	public void consultaInstancia(){
   	 String hql =" select * from ( select  instancia.nombre AS INSTANCIA,count(expediente.id_expediente)CANTIDAD " +
   	 		" from GESLEG.expediente  inner join GESLEG.instancia on instancia.id_instancia=expediente.id_instancia" +
   	 		" where exp_id_expediente is null and id_proceso=1 group by  instancia.nombre)xx";
   	 List lstIstancia=(List<Generico>) SpringInit.devolverSession().createSQLQuery(hql).list();
   	 logger.debug("lstGenCalificacion: "+lstIstancia.size());
   		Iterator it = lstIstancia.iterator();
   		Generico genericoDto=new Generico();
   		lstGenInstancia=new ArrayList<Generico>();
   	    while (it.hasNext( )) {
   	       Object[] result = (Object[])it.next();  
   	       genericoDto=new Generico();
   	       genericoDto.setDescripcion((String)result[0]);
   	       genericoDto.setCantidad((BigDecimal)result[1]+"");
   	       lstGenInstancia.add(genericoDto);
   	    }
	}
	
	
	
	public void listarStockAnterior(){
	ReportesDao<Object, Object> service = (ReportesDao<Object, Object>) SpringInit.getApplicationContext().getBean("reportesEspDao");
		
	 try {
		 lstStockAnterior= service.obtenerStockAnterior();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public void obtenerTipoCambio(){
		ReportesDao<Object, Object> service = (ReportesDao<Object, Object>) SpringInit.getApplicationContext().getBean("reportesEspDao");
		
	 try {
		 tipoCambioBean= service.obtenerTipoCambio();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public ParametrosReportesVistaDto getParamRepVistaDto() {
		return paramRepVistaDto;
	}

	public void setParamRepVistaDto(ParametrosReportesVistaDto paramRepVistaDto) {
		this.paramRepVistaDto = paramRepVistaDto;
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
	public List<Generico> getLstGenCalificacion() {
		return lstGenCalificacion;
	}

	public void setLstGenCalificacion(List<Generico> lstGenCalificacion) {
		this.lstGenCalificacion = lstGenCalificacion;
	}

	public List<Generico> getLstGenInstancia() {
		return lstGenInstancia;
	}

	public void setLstGenInstancia(List<Generico> lstGenInstancia) {
		this.lstGenInstancia = lstGenInstancia;
	}

	public List<ReporteLitigiosDto> getLstStockAnterior() {
		return lstStockAnterior;
	}

	public void setLstStockAnterior(List<ReporteLitigiosDto> lstStockAnterior) {
		this.lstStockAnterior = lstStockAnterior;
	}

	public Generico getTipoCambioBean() {
		return tipoCambioBean;
	}

	public void setTipoCambioBean(Generico tipoCambioBean) {
		this.tipoCambioBean = tipoCambioBean;
	}
	
	
	
	
	
	
	}
	
		
	



