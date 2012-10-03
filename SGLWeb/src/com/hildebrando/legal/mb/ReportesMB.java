package com.hildebrando.legal.mb;

import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import it.eng.spagobi.sdk.documents.bo.SDKDocument;
//import it.eng.spagobi.sdk.documents.bo.SDKDocumentParameter;
//import it.eng.spagobi.sdk.exceptions.NonExecutableDocumentException;
//import it.eng.spagobi.sdk.proxy.DocumentsServiceProxy;
//import it.eng.spagobi.sdk.proxy.TestConnectionServiceProxy;
//import it.eng.spagobi.services.common.SsoServiceInterface;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "reportesMB")
@SessionScoped
public class ReportesMB {
//	private Integer documentId ;
//	private String role;
//	//private SDKDocument document ;
//	//private SDKDocument[] documents ;
//	//private SDKDocumentParameter[] parameters;
//	private StringBuffer parameterValues ;
//	private String nombreReporte;
//	private String iframeUrl;
//	private String iframeStyle;
//	private String iframeUrlString;
//	
//	public String getIframeUrlString() {
//		return iframeUrlString;
//	}
//
//	public void setIframeUrlString(String iframeUrlString) {
//		this.iframeUrlString = iframeUrlString;
//	}
//
//	class Logueo {
//		
//		private String user;
//		private String password;
//		
//		public Logueo(String user, String password) {
//			this.user = user;
//			this.password = password;
//		}
//		public String getUser() {
//			return user;
//		}
//		public void setUser(String user) {
//			this.user = user;
//		}
//		public String getPassword() {
//			return password;
//		}
//		public void setPassword(String password) {
//			this.password = password;
//		}
//		
//		
//	}
//	
//	public ReportesMB() {
//		nombreReporte="Actividad Litigios";
//		ExecutarReporte();
//	}
//	
//	private void ExecutarReporte(){
//		Logueo usuario = new Logueo("biadmin", "biadmin");
//		try {
//			if(validarConexionSpaobi(usuario)){
//				obtenerDocumento(usuario,"SGL_BBVA");
//			}else{
//				System.out.println("No hay coneccion ...");
//			}
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
//	private boolean validarConexionSpaobi(Logueo usuario) throws RemoteException{
//		if (usuario.getUser() != null && usuario.getPassword() != null) {
//			TestConnectionServiceProxy proxy = new TestConnectionServiceProxy(usuario.getUser(), usuario.getPassword());
//		    proxy.setEndpoint("http://118.180.34.15:9084/SpagoBI/sdk/TestConnectionService");
//			return  proxy.connect();
//		}
//		return false;
//	}
//	private void obtenerDocumento(Logueo usuario,String nombreReporte) throws RemoteException{
//		
//		DocumentsServiceProxy proxy = new DocumentsServiceProxy(usuario.getUser(), usuario.getPassword());
//		proxy.setEndpoint("http://118.180.34.15:9084/SpagoBI/sdk/DocumentsService");
//		 documents = proxy.getDocumentsAsList(null, null, null);
//		//session.setAttribute("spagobi_documents", documents);
//		String documentIdStr =null;
//		for (int i = 0; i < documents.length; i++) {
//			SDKDocument aDoc = documents[i];
//			if(aDoc.getName().equals(nombreReporte)){
//				 documentIdStr = aDoc.getId()+"";
//				 break;
//			}  
//			
//		}
//		
//		 documentId = new Integer(documentIdStr);
//		//session.setAttribute("spagobi_documentId", documentId);
//		String[] validRoles = null;
//		 role =null;
//		try {
//			proxy.setEndpoint("http://118.180.34.15:9084/SpagoBI/sdk/DocumentsService");
//			validRoles =  proxy.getCorrectRolesForExecution(documentId);
//			if (validRoles.length == 0) {
//				System.out.println("Validacion 001  "+"En la validacion 001" );
//				System.out.println("Usuario no executo el Documento");
//			} else if (validRoles.length == 1) {
//				System.out.println("En el else if 1 ");
//				obtenerParametrosDocumento(usuario, validRoles[0]);
//				//response.sendRedirect("documentParameters.jsp?role=" + validRoles[0]);
//			} else {
//			   
//			
//					for (int i = 0; i < validRoles.length; i++) {
//						role =validRoles[i];
//					System.out.println("validRoles[i] " +validRoles[i]);
//					}
//			}
//		} catch (NonExecutableDocumentException e) {
//			System.out.println("Error "+e.getMessage());
//			System.out.println("User cannot execute document");
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//	}
//	
//	private void obtenerParametrosDocumento(Logueo usuario,String role) throws Exception{
//			DocumentsServiceProxy proxy = new DocumentsServiceProxy(usuario.getUser(), usuario.getPassword());
//			proxy.setEndpoint("http://118.180.34.15:9084/SpagoBI/sdk/DocumentsService");
//			//String role = request.getParameter("role");
//			//session.setAttribute("spagobi_role", role);
//			System.out.println("role " +role);
//			System.out.println("documentId " +documentId);
//			 parameters = proxy.getDocumentParameters(documentId, role);
//			//session.setAttribute("spagobi_document_parameters", parameters);
//			if (parameters == null || parameters.length == 0) {
//				System.out.println("Parametros Ninguno");
//				executionjsp(usuario, role);
//				//response.sendRedirect("execution.jsp");
//			} else {
//				  for (int i = 0; i < parameters.length; i++) {
//					   SDKDocumentParameter aDocParameter = parameters[i];
//						
//						HashMap values = proxy.getAdmissibleValues(aDocParameter.getId(), role);
//						if (values == null || values.isEmpty()) {
//							System.out.println("xxx aDocParameter.getUrlName() " +aDocParameter.getUrlName()); 
//						
//						} else {
//							
//							//<select name="<%= aDocParameter.getUrlName() %>">
//							System.out.println("aDocParameter.getUrlName() "+aDocParameter.getUrlName());
//							Set entries = values.entrySet();
//							Iterator it = entries.iterator();
//							while (it.hasNext()) {
//								Map.Entry entry = (Map.Entry) it.next();
//							System.out.println("entry.getKey() " + entry.getValue());
//								
//								
//							}
//							
//						}
//					}
//			
//			}
//		}
//		
//	private void executionjsp(Logueo usuario,String role) throws Exception{
//
//			for (int i = 0; i < documents.length; i++) {
//				SDKDocument aDocument = documents[i];
//				if (aDocument.getId().equals(documentId)) {
//					document = aDocument;
//				}
//			}
//			
//			 parameterValues = new StringBuffer();
//			if (parameters != null && parameters.length > 0) {
//				for (int i = 0; i < parameters.length; i++) {
//					SDKDocumentParameter aParameter = parameters[i];
//					System.out.println("aParameter.getUrlName() " +aParameter.getUrlName());
//					String value ="";// request.getParameter(aParameter.getUrlName());
//					if (value != null) {
//						aParameter.setValues(new String[]{value});
//						if (parameterValues.length() > 0) {
//							parameterValues.append("&");
//						}
//						parameterValues.append(aParameter.getUrlName() + "=" + value);
//					} else {
//						aParameter.setValues(null);
//					}
//				}
//			}
//			
//			executarTag(usuario);
//
//	}
//	private void executarTag(Logueo usuario) throws Exception{
//			String spagobiContext ="http://118.180.34.15:9084/SpagoBI/";
//			String userId = usuario.getUser();
//			String documentLabel=null;
//			String executionRole=role;
//			String parametersStr=parameterValues.toString();
//			System.out.println("parameterValues.toString() " +parameterValues.toString());
//			Map parametersMap=null;
//			Boolean displayToolbar=Boolean.TRUE;
//			Boolean displaySliders=Boolean.TRUE;
//			String iframeStyle="height:600px; width:100%";
//			String theme=null;
//			String authenticationTicket=null;
//		
//		StringBuffer iframeUrl = new StringBuffer();
//		iframeUrl.append(spagobiContext + "/servlet/AdapterHTTP?NEW_SESSION=true");
//		iframeUrl.append("&ACTION_NAME=EXECUTE_DOCUMENT_ACTION");
//		iframeUrl.append("&" + SsoServiceInterface.USER_ID + "=" + userId);
//
//		if (documentId == null && documentLabel == null) {
//			throw new Exception("Neither document id nor document label are specified!!");
//		}
//		if (documentId != null) {
//			iframeUrl.append("&OBJECT_ID=" + documentId);
//		} else {
//			iframeUrl.append("&OBJECT_LABEL=" + documentLabel);
//		}
//		if (parametersStr != null) iframeUrl.append("&PARAMETERS=" + URLEncoder.encode(parametersStr));
//		if (parametersMap != null && !parametersMap.isEmpty()) {
//			Set keys = parametersMap.keySet();
//			Iterator keysIt = keys.iterator();
//			while (keysIt.hasNext()) {
//				String urlName = (String) keysIt.next();
//				Object valueObj = parametersMap.get(urlName);
//				if (valueObj != null) {
//					iframeUrl.append("&" + URLEncoder.encode(urlName) + "=" + URLEncoder.encode(valueObj.toString()));
//				}
//			}
//		}
//		if (executionRole != null) iframeUrl.append("&ROLE=" + URLEncoder.encode(executionRole));
//		if (displayToolbar != null) iframeUrl.append("&TOOLBAR_VISIBLE=" + displayToolbar.toString());
//		if (displaySliders != null) iframeUrl.append("&SLIDERS_VISIBLE=" + displaySliders.toString());
//		if (theme != null)	iframeUrl.append("&theme=" + theme);
//		if (authenticationTicket != null) iframeUrl.append("&auth_ticket=" + URLEncoder.encode(authenticationTicket));
//		 //iframeUrlString="http://118.180.34.15:9084/SpagoBI//servlet/AdapterHTTP?NEW_SESSION=true&ACTION_NAME=EXECUTE_DOCUMENT_ACTION&user_id=biadmin&OBJECT_ID=253&PARAMETERS=&ROLE=%2Fspagobi%2Fadmin&TOOLBAR_VISIBLE=true&SLIDERS_VISIBLE=false".toString();
//		iframeUrlString=iframeUrl.toString();
//		System.out.println("iframeUrl " +iframeUrl );
//		
//		System.out.println( " iframeStyle " +iframeStyle);
//	}
//
//	public String getNombreReporte() {
//		return nombreReporte;
//	}
//
//	public void setNombreReporte(String nombreReporte) {
//		this.nombreReporte = nombreReporte;
//	}
//	
//	public String getIframeUrl() {
//		return iframeUrl;
//	}
//
//	public void setIframeUrl(String iframeUrl) {
//		this.iframeUrl = iframeUrl;
//	}
//
//	public String getIframeStyle() {
//		return iframeStyle;
//	}
//
//	public void setIframeStyle(String iframeStyle) {
//		this.iframeStyle = iframeStyle;
//	}
	}
	
		
	



