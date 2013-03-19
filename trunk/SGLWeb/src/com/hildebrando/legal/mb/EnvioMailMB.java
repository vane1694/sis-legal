package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import pe.com.bbva.bean.CorreoBean;
import pe.com.bbva.enviarCorreoService.EnviarCorreoService;
import pe.com.bbva.enviarCorreoService.EnviarCorreoServiceImpl;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.EnvioMailDao;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.impl.EnvioMailDaoImpl;
import com.hildebrando.legal.modelo.ActividadxExpediente;
import com.hildebrando.legal.modelo.ActividadxExpedienteAyer;
import com.hildebrando.legal.modelo.ActividadxUsuario;
import com.hildebrando.legal.modelo.Correo;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Parametros;
import com.hildebrando.legal.modelo.Usuario;

@ManagedBean(name = "envioMail")
@SessionScoped
public class EnvioMailMB 
{
	public static Logger logger = Logger.getLogger(EnvioMailMB.class);
	private static int puerto;
	private static String host;
	private List<ActividadxExpediente> resultado;
	private List<ActividadxUsuario> resultado2;
	private Boolean envioMailAllDay;
	private static Correo envioCorreoBean;
	
	
	public static int getPuerto() {
		return puerto;
	}
	public static void setPuerto(int puerto) {
		EnvioMailMB.puerto = puerto;
	}
	public static String getHost() {
		return host;
	}
	public static void setHost(String host) {
		EnvioMailMB.host = host;
	}
	public Boolean getEnvioMailAllDay() {
		return envioMailAllDay;
	}
	public void setEnvioMailAllDay(Boolean envioMailAllDay) {
		this.envioMailAllDay = envioMailAllDay;
	}
	public static Correo getEnvioCorreoBean() {
		return envioCorreoBean;
	}
	public static void setEnvioCorreoBean(Correo envioCorreoBean) {
		EnvioMailMB.envioCorreoBean = envioCorreoBean;
	}
	public EnvioMailMB(){
		inicializarParametrosBD();
	}
	@SuppressWarnings("unchecked")
	private void inicializarParametrosBD()
	{
		logger.debug("=== inicializarParametrosBD() ====");
		//Variables puerto y host que se obtiene de la tabla parámetros. 
		List<Parametros> parametros = new ArrayList<Parametros>();
		GenericDao<Parametros, Object> parametrosDAO = (GenericDao<Parametros, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Parametros.class);
			
		try {
			parametros = parametrosDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			logger.debug("Ocurrio una excepcion al inicializarParametrosBD(): "+e);
		}

		for (Parametros param : parametros) 
		{
			host=param.getHost();
			puerto = param.getPuerto();
			
			logger.debug("[PARAMETROS]-> host:"+host +" puerto:"+puerto);
		}
		envioCorreoBean = new Correo();
	}
	public void enviarCorreoCambioColor() 
	{
		prepararCorreoCambioColor();
	}
	public void enviarCorreoCambioFechas() 
	{
		prepararCorreoCambioFechas();
	}
	@SuppressWarnings("unchecked")
	public void prepararCorreoCambioFechas()
	{
		logger.debug("==== prepararCorreoCambioFechas() ====");
		boolean error =false;

		GenericDao<ActividadxExpediente, Object> busqDAO = (GenericDao<ActividadxExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(ActividadxExpediente.class);
		List<ActividadxExpediente> resultado = new ArrayList<ActividadxExpediente>();	
		
		try {
			resultado = busqDAO.buscarDinamico(filtro);
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.debug("Error al obtener los resultados de la busqueda de eventos de la agenda");
		}

		
		//Cambiar correo destino en hardcode por correo del destinatario (usuario responsable)
		for (ActividadxExpediente acxUsu: resultado)
		{
			if (acxUsu.getFechaVencimiento()!=null)
			{
				envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
						acxUsu.getNroExpediente(),acxUsu.getFechaVencimiento().toString(),
						acxUsu.getCorreo(),2);
			
				logger.debug("--------------------------------------");
				logger.debug("-------------DATOS CORREO-------------");
				logger.debug("Apellido: " +acxUsu.getApellidoPaterno());
				logger.debug("Actividad: " +acxUsu.getActividad());
				logger.debug("NumeroExpediente: " +acxUsu.getNroExpediente());
				logger.debug("Fecha Vencimiento: " +acxUsu.getFechaVencimiento().toString());
				logger.debug("--------------------------------------");
			}
			else
			{
				error=true;
				logger.debug("-----------------------------------------------------------");
				logger.debug("-------------DATOS CORREO QUE NO SE PUDO ENVIAR-----------");
				logger.debug("Apellido: " +acxUsu.getApellidoPaterno());
				logger.debug("Actividad: " +acxUsu.getActividad());
				logger.debug("NumeroExpediente: " +acxUsu.getNroExpediente());
				logger.debug("----------------------------------------------------------");
				logger.error("No se pudo enviar correo debido a que la fecha de vencimiento no es valida");
			}
			
			if (!error)
			{
				if (acxUsu.getCorreo()!=null && acxUsu.getCorreo().trim().length()>0)
				{
					logger.debug("-- Listo para enviar correo --");
					enviarCorreo(envioCorreoBean);
				}
			}
		}		
	}
	
	public void enviarCorreoCambioActivadadExpediente(List<Long> lstIdActividad)
	{
		logger.debug("=== enviarCorreoCambioActivadadExpediente() ===");
		boolean error =false;		
		String sCadena="";
		logger.debug("lstIdActividad.size(): "+lstIdActividad.size());
		if (lstIdActividad.size()>0)
		{
			int j=0;
			int cont=1;
			
			for (;j<=lstIdActividad.size()-1;j++)
			{
				if (lstIdActividad.size()>1)
				{
					if (cont==lstIdActividad.size())
					{
						sCadena=sCadena.concat(lstIdActividad.get(j).toString());
					}
					else
					{
						sCadena=sCadena.concat(lstIdActividad.get(j).toString().concat(","));
						cont++;
					}
				}
				else
				{
					sCadena = lstIdActividad.get(j).toString();
				}		
			}
			
		}
		
		logger.debug("Parametro filtro in: " + sCadena);
		
		if (sCadena.length()>0)
		{
			
			EnvioMailDao mailDao = new EnvioMailDaoImpl();
		
			logger.debug("Antes resultado --");
			
			resultado2= new ArrayList<ActividadxUsuario>();
			
			try {
				resultado2 = mailDao.obtenerActividadxUsuarioDeActProc(sCadena);
			} catch (Exception e) {
				
				logger.debug("error al obtener lista de act x usuario de act proc"+ e.getMessage());
			} 
			
			logger.debug("Hay resultados -> "+resultado2.size());
			
			
			//Cambiar correo destino en hardcode por correo del destinatario (usuario responsable)
			for (ActividadxUsuario acxUsu: resultado2)
			{
				if (acxUsu.getFechaVencimiento()!=null)
				{
					logger.debug("--------------------------------------");
					logger.debug("-------------DATOS CORREO-------------");
					logger.debug("Apellido: " +acxUsu.getApellidoPaterno());
					logger.debug("Actividad: " +acxUsu.getActividad());
					logger.debug("NumeroExpediente: " +acxUsu.getNumeroExpediente());
					logger.debug("Fecha Vencimiento: " +acxUsu.getFechaVencimiento().toString());
					logger.debug("Color Actividad: " + acxUsu.getColor());
					logger.debug("Color Dia Anterior : " + acxUsu.getColorDiaAnterior());
					logger.debug("Email: " + acxUsu.getCorreo());
					logger.debug("--------------------------------------");
					
					envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
								acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
								acxUsu.getCorreo(),2);			
				}
				else
				{
					error=true;
					logger.debug("----------------------------------------------------------");
					logger.debug("-------------DATOS CORREO QUE NO SE PUDO ENVIAR-----------");
					logger.debug("Apellido: " +acxUsu.getApellidoPaterno());
					logger.debug("Actividad: " +acxUsu.getActividad());
					logger.debug("NumeroExpediente: " +acxUsu.getNumeroExpediente());
					logger.debug("Color Actividad: " + acxUsu.getColor());
					logger.debug("Color Dia Anterior : " + acxUsu.getColorDiaAnterior());
					logger.debug("Email: " + acxUsu.getCorreo());
					logger.debug("----------------------------------------------------------");
					logger.error("No se pudo enviar correo debido a que la fecha de vencimiento no es valida");
				}
				
				if (!error)
				{
					if (acxUsu.getCorreo()!=null && acxUsu.getCorreo().trim().length()>0)
					{
						logger.debug("--=== SE ENVIARA CORREO == -- ");
						enviarCorreo(envioCorreoBean);
					}
				}
			}
			
			logger.debug("valor --> "+error);
			
			logger.debug("Saliendo de este m�todo ....");
			
		}
	}
	
	public boolean enviarCorreoCambioResponsable(Expediente expediente, Usuario usu)
	{
		boolean error=false;
		boolean envioExitoso =false;
		
		if (usu == null || expediente ==null)
		{
			error=true;
		}
		
		if(!error)
		{
			envioCorreoBean = SeteoBeanUsuario(usu.getApellidoPaterno(),"",
				expediente.getNumeroExpediente(),null,
				usu.getCorreo(),4);
			
			if (usu.getCorreo()!=null && usu.getCorreo().trim().length()>0)
			{
				logger.debug("--=== SE ENVIARA CORREO == -- ");
				enviarCorreo(envioCorreoBean);
			}
			
			envioExitoso=true;
		}
		else
		{
			logger.debug("Error al obtener los datos para enviar correo");
		}
		
		return envioExitoso;
	}
	
	@SuppressWarnings("unchecked")
	public void prepararCorreoCambioColor()
	{
		logger.debug("==== prepararCorreoCambioColor() ==");
		
		boolean error =false;
		
		GenericDao<ActividadxExpediente, Object> busqDAO = (GenericDao<ActividadxExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(ActividadxExpediente.class);
		List<ActividadxExpediente> resultado = new ArrayList<ActividadxExpediente>();	
		
		GenericDao<ActividadxExpedienteAyer, Object> busqDAOAyer = (GenericDao<ActividadxExpedienteAyer, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtroAyer = Busqueda.forClass(ActividadxExpedienteAyer.class);
		List<ActividadxExpedienteAyer> resultadoAyer = new ArrayList<ActividadxExpedienteAyer>();	
		
		try {
			resultado = busqDAO.buscarDinamico(filtro);
			resultadoAyer = busqDAOAyer.buscarDinamico(filtroAyer);
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.debug("Error al obtener los resultados de la busqueda de eventos de la agenda");
		}
		
		//Cambiar correo destino en hardcode por correo del destinatario (usuario responsable)
		for (int i = 0; i < resultado.size(); i++)
		{		
			
			if (resultado.get(i).getFechaVencimiento()!=null)
			{
				/*logger.debug("--------------------------------------");
				logger.debug("-------------DATOS CORREO-------------");
				logger.debug("Apellido: " +resultado.get(i).getApellidoPaterno());
				logger.debug("Actividad: " +resultado.get(i).getActividad());
				logger.debug("NumeroExpediente: " +resultado.get(i).getNroExpediente());
				logger.debug("Fecha Vencimiento: " +resultado.get(i).getFechaVencimiento().toString());
				logger.debug("Color Actividad: " + resultado.get(i).getColorFila());
				logger.debug("Color Dia Anterior : " + resultadoAyer.get(i).getColorFila());
				logger.debug("--------------------------------------");
				*/
			/*	if (acxUsu.getColor().equals("N"))
				{
					envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
							acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
							acxUsu.getCorreo(),1);
									
				}
				if (acxUsu.getColor().equals("R"))
				{
					envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
							acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
							acxUsu.getCorreo(),3);
					
				}*/
				
				if(resultadoAyer!=null){
					
					if(resultadoAyer.size()>0){
						
						if (!resultado.get(i).getColorFila().equalsIgnoreCase(resultadoAyer.get(i).getColorFila()))
						{
							if(resultado.get(i).getColorFila().equals("R")){
								
								envioCorreoBean = SeteoBeanUsuario(resultado.get(i).getApellidoPaterno(),resultado.get(i).getActividad(),
										resultado.get(i).getNroExpediente(),resultado.get(i).getFechaVencimiento().toString(),
										resultado.get(i).getCorreo(),3);
								
							}else{
								envioCorreoBean = SeteoBeanUsuario(resultado.get(i).getApellidoPaterno(),resultado.get(i).getActividad(),
										resultado.get(i).getNroExpediente(),resultado.get(i).getFechaVencimiento().toString(),
										resultado.get(i).getCorreo(),1);
								
							}
							
							
						}else{
							
							error = true;
						}	
						
					}else{
						
						error = true;
					}
					
				}else{
					
					error = true;
				}
				

			}
			else
			{
				error=true;
				/*logger.debug("----------------------------------------------------------");
				logger.debug("-------------DATOS CORREO QUE NO SE PUDO ENVIAR-----------");
				logger.debug("Apellido: " +resultado.get(i).getApellidoPaterno());
				logger.debug("Actividad: " +resultado.get(i).getActividad());
				logger.debug("NumeroExpediente: " +resultado.get(i).getNroExpediente());
				logger.debug("Color Actividad: " + resultado.get(i).getColorFila());
				logger.debug("Color Dia Anterior : " + resultado.get(i).getColorFila());
				logger.debug("----------------------------------------------------------");
				logger.error("No se pudo enviar correo debido a que la fecha de vencimiento no es valida");*/
			}
			
			if (!error)
			{
				if (resultado.get(i).getCorreo()!=null && resultado.get(i).getCorreo().trim().length()>0)
				{
					logger.debug("Antes de enviar correo -enviarCorreo ");
					enviarCorreo(envioCorreoBean);
				}
			}
			else
			{
				logger.debug("No se ha enviado correo debido a que no se cumplio las condiciones de cambio de color de las actividades procesales");
			}
		}
				
	}	
	
	public void enviarCorreo(Correo envioCorreoBean)
	{
		logger.debug("=== inicia el enviarCorreo() ===");
		EnviarCorreoService enviaCorreo = new EnviarCorreoServiceImpl();

		// Instanciar el Bean de javaMailJAR.jar. 
		CorreoBean correoB = new CorreoBean(); 
		
		//Envía el puerto que obtiene de la consulta obtenida de la tabla parámetros.
		correoB.setPuertoBco(String.valueOf(puerto)); 
		
		//Envía el host que obtiene de la consulta obtenida de la tabla parámetros. 
		correoB.setHostBco(host); 
		
		//Envía valores recogidos de la clase EnvioCorreoDto mapeado en la tabla ENVIO_CORREO 
		// Correo de. Ejm: sitemaAlmacenes@grubobbva.com.pe 
		logger.debug("envioCorreoBean.getFrom(): "+envioCorreoBean.getFrom());
		correoB.setFrom(envioCorreoBean.getFrom()); 
		// Correo para. 
		logger.debug("envioCorreoBean.getTo(): "+envioCorreoBean.getTo());
		correoB.setTo(envioCorreoBean.getTo()); 
		// Mensaje que se desea enviar en el correo. 
		correoB.setMensaje(envioCorreoBean.getCorreoCuerpo()); 
		// Título del correo a enviar. 
		correoB.setSubject(envioCorreoBean.getAsunto()); 
		// Invocar el servicio que realiza el envío de correo. 
		try {
			enviaCorreo.MailUno (correoB);
		} catch (Exception e) {
			logger.error("Ocurrio una excepcion: "+e);
		}
		
		logger.error("Saliendo de ==enviarCorreo()== ");
		
		
	}	
	
	private static Correo SeteoBeanUsuario(String apellido,String nombreActividad, String expediente, 
			String fechaVencimiento, String correo, int modo)
	{
		envioCorreoBean.setFrom("sistemaLegal@grupobbva.com.pe");
		envioCorreoBean.setTo(correo);
		
		if (modo==4)
		{
			envioCorreoBean.setCorreoCuerpo(textoMensajeMail(apellido,nombreActividad,expediente,null,modo));
		}
		else
		{
			envioCorreoBean.setCorreoCuerpo(textoMensajeMail(apellido,nombreActividad,expediente,fechaVencimiento,modo));
		}
			
		
		if (modo==1)
		{
			envioCorreoBean.setAsunto("ACTIVIDAD PROCESAL - " + 
			"EXP:" + expediente + " VENCE EL " + fechaVencimiento);
		}
		if (modo==2)
		{
			envioCorreoBean.setAsunto("CAMBIO DE FECHAS DE ACTIVIDADES PROCESALES");
		}
		if (modo==3)
		{
			envioCorreoBean.setAsunto("ACTIVIDAD PROCESAL - " + 
			"EXP:" + expediente + " VENCIO EL " + fechaVencimiento);
		}
		if (modo==4)
		{
			envioCorreoBean.setAsunto("CAMBIO DE USUARIO RESPONSABLE DEL EXP - " + expediente);
		}
		
		logger.debug("AsuntoCorreo: "+envioCorreoBean.getAsunto());
		return envioCorreoBean;
	}
	
	public static String textoMensajeMail(String apellido,String nombreActividad, String expediente, 
			String fechaVencimiento, int modo)
	{
		//Modo=1: Mensaje informativo que la actividad esta proxima a vencer
		//Modo=2: Mensaje que la fecha de vencimiento de la actividad procesal ha cambiado.
		String texto ="";
		if (modo==1)
		{
			texto = "Estimado Doc. "+apellido+":\n" + "    La actividad procesal: " + nombreActividad +
					" del expediente: " + expediente  + ", esta proxima a vencer el dia " + fechaVencimiento +
					"\n\nPor favor tomar las medidas del caso." + 
					"\n\nAtte." + 
					"\n" +
					"\nSISTEMA DE GESTION LEGAL";
		}
		if (modo==2)
		{
			texto = "Estimado Doc. "+apellido+":\n" + "    La actividad procesal: " + nombreActividad +
					" del expediente: " + expediente  + " ha cambiado. La nueva Fecha de Vencimiento es: " +
					fechaVencimiento +
					"\n\nPor favor tomar las medidas del caso." + 
					"\n\nAtte." + 
					"\n" +
					"\nSISTEMA DE GESTION LEGAL";
		}
		if (modo==3)
		{
			texto = "Estimado Doc. "+apellido+":\n" + "    La actividad procesal: " + nombreActividad +
					" del expediente: " + expediente  + " ha vencido." +
					"\n\nPor favor tomar las medidas del caso." + 
					"\n\nAtte." + 
					"\n" +
					"\nSISTEMA DE GESTION LEGAL";
		}
		if (modo==4)
		{
			texto = "Estimado Doc. "+apellido+":\n" + "    El expediente " + expediente + " ha sido asignado a Ud como responsable." + 
					"\n\nAtte." + 
					"\n" +
					"\nSISTEMA DE GESTION LEGAL";
		}
		
		logger.debug("texto: "+texto);
		return texto;
	}
	private String queryColor(int modo)
	{
		String cadena="";
		
		if (modo==1)
		{
			
			cadena= "	CASE					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <= 0  "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='R'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"       AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='N'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          = vi.id_via	    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='A'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) > 0   "+
					"      THEN 'V'					    "+
					"      WHEN GESLEG.DAYS(a.fecha_actividad,SYSDATE) <= 0    "+
					"      THEN 'V'					    "+
					"      ELSE 'E'					    "+
					"    END AS COLOR				    ";

	
		}
		if (modo==3)
		{
			cadena ="	CASE					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <= 0  "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='R'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"       AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='N'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          = vi.id_via	    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='A'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM GESLEG.aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN GESLEG.DAYS(SYSDATE-1,a.fecha_vencimiento) > 0   "+
					"      THEN 'V'					    "+
					"      WHEN GESLEG.DAYS(a.fecha_actividad,SYSDATE-1) <= 0    "+
					"      THEN 'V'					    "+
					"      ELSE 'E'					    "+
					"    END AS COLORDIAANTERIOR	    ";
		}
		
		logger.debug("cadena --> "+cadena);
		return cadena;
	}
	public List<ActividadxExpediente> getResultado() {
		return resultado;
	}
	public void setResultado(List<ActividadxExpediente> resultado) {
		this.resultado = resultado;
	}
	public List<ActividadxUsuario> getResultado2() {
		return resultado2;
	}
	public void setResultado2(List<ActividadxUsuario> resultado2) {
		this.resultado2 = resultado2;
	}
}
