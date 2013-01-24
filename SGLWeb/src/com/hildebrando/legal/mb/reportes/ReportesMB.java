package com.hildebrando.legal.mb.reportes;

import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import it.eng.spagobi.sdk.documents.bo.SDKDocument;
import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
import it.eng.spagobi.sdk.exceptions.NonExecutableDocumentException;
import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
import it.eng.spagobi.sdk.proxy.TestConnectionServiceProxy;
import it.eng.spagobi.services.common.SsoServiceInterface;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.event.MenuListener;

import org.apache.log4j.Logger;



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
	private String repor1;
	
	public static Logger logger = Logger.getLogger(ReportesMB.class);
	
	public String getRepor1() {
		return repor1;
	}

	public void setRepor1(String repor1) {
		this.repor1 = repor1;
	}
	
	//TODO Cambiar esta IP en un archivo properties o como parámetro en BD.	
	private String ipBanco="http://172.31.9.41:9084";
	//private String ipBanco="http://118.180.34.15:9084";
	//private String ipBanco="http://localhost:8080";
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
	
	public ReportesMB() {
		logger.debug("==== ReportesMB() =====");
		ResourceBundle rb =ResourceBundle.getBundle("legal");
		String valor = rb.getString("ipBanco");
		ipBanco=valor;
		logger.debug(" ipBanco: "+valor);
		validad();
	}
	public String action(){
		   return (String)FacesContext.getCurrentInstance().
			getExternalContext().getRequestParameterMap().get("hidden");
		}
	public void validad(){
		logger.debug("Accion Reporte --->"+action());
		
	      if(action().equals("1")){
		nombreReporte="Actividad Litigios";
		ExecutarReporteActividadLitigio();
	}else if(action().equals("2")){ 
		nombreReporte="Movimiento Provisiones";
		ExecutarReporteMovimientoProvisiones();
	}else if(action().equals("3")){
		nombreReporte="Reportes de Organización";
		ExecutarReporteConsolidadoOrganizaciones();
	}else if(action().equals("4")){
		nombreReporte="Reportes de Organización";
		ExecutarReporteProcesosCivilesContra();
	}else if (action().equals("5")){
		nombreReporte="Reportes de Organización";
		ExecutarReporteProcesosCivilesFavor();
	}else if (action().equals("6")){
		nombreReporte="Reportes de Organización";
		ExecutarReporteProcesosPenalesContra();
	}else if (action().equals("7")){
		nombreReporte="Reportes de Organización";
		ExecutarReporteProcesosPenalesFavor();
	}
	      
	      
}
public void ExecutarReporteActividadLitigio(){
	logger.debug("=== ExecutarReporteActividadLitigio ===");
	//TODO Cambiar los datos:  "biadmin" y ponerlos en un properties o como parámetro 
	//en la base de datos.
	Logueo usuario = new Logueo("biadmin", "biadmin");
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
	Logueo usuario = new Logueo("biadmin", "biadmin");
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
	Logueo usuario = new Logueo("biadmin", "biadmin");
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
	Logueo usuario = new Logueo("biadmin", "biadmin");
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
	Logueo usuario = new Logueo("biadmin", "biadmin");
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
	Logueo usuario = new Logueo("biadmin", "biadmin");
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
	Logueo usuario = new Logueo("biadmin", "biadmin");
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
							//<select name="<%= aDocParameter.getUrlName() %>">
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
	}
	
		
	



