package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import pe.com.bbva.bean.CorreoBean;
import pe.com.bbva.enviarCorreoService.EnviarCorreoService;
import pe.com.bbva.enviarCorreoService.EnviarCorreoServiceImpl;
import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.ActividadProcesal;
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
		//prepararCorreoCambioFechas();
	}
	@SuppressWarnings("unchecked")
	/*public void prepararCorreoCambioFechas()
	{
		logger.debug("==== prepararCorreoCambioFechas() ====");
		boolean error =false;
		//Obtener correo y datos a mostrar de BD
		String hql ="SELECT ROW_NUMBER() OVER (ORDER BY exp.numero_expediente) as ROW_ID," +
				"exp.numero_expediente ,usu.apellido_paterno,usu.correo," +
				"act.nombre,a.fecha_vencimiento," +
				queryColor(1) + "," + queryColor(3) + 
				"FROM expediente exp " +
				"LEFT OUTER JOIN usuario usu ON exp.id_usuario=usu.id_usuario " +
				"LEFT OUTER JOIN actividad_procesal a ON exp.id_expediente=a.id_expediente " +
				"LEFT OUTER JOIN instancia ins ON exp.id_instancia=ins.id_instancia " +
				"INNER JOIN actividad act ON a.id_actividad=act.id_actividad " +
				"LEFT OUTER JOIN via vi ON ins.id_via = vi.id_via " +
				"LEFT OUTER JOIN proceso c ON vi.id_proceso = c.id_proceso " +
				"ORDER BY 1";
		
		logger.debug("Query correo: " +hql);
		
		Query query = SpringInit.devolverSession().createSQLQuery(hql)
		.addEntity(ActividadxUsuario.class);

		resultado = query.list();
		logger.debug("Resultado Query-size: "+resultado.size());
		
		//Cambiar correo destino en hardcode por correo del destinatario (usuario responsable)
		for (ActividadxUsuario acxUsu: resultado)
		{
			if (acxUsu.getFechaVencimiento()!=null)
			{
				envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
						acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
						acxUsu.getCorreo(),2);
			
				logger.debug("--------------------------------------");
				logger.debug("-------------DATOS CORREO-------------");
				logger.debug("Apellido: " +acxUsu.getApellidoPaterno());
				logger.debug("Actividad: " +acxUsu.getActividad());
				logger.debug("NumeroExpediente: " +acxUsu.getNumeroExpediente());
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
				logger.debug("NumeroExpediente: " +acxUsu.getNumeroExpediente());
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
	}*/
	
	public void enviarCorreoCambioActivadadExpediente(List<Long> lstIdActividad)
	{
		/*logger.debug("=== enviarCorreoCambioActivadadExpediente() ===");
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
			//Obtener correo y datos a mostrar de BD
			String hql ="SELECT ROW_NUMBER() OVER (ORDER BY exp.numero_expediente) as ROW_ID," +
					"exp.numero_expediente,usu.apellido_paterno,usu.correo," +
					"act.nombre actividad,a.fecha_vencimiento," +
					queryColor(1) + "," + queryColor(3) + 
					"FROM expediente exp " +
					"LEFT OUTER JOIN usuario usu ON exp.id_usuario=usu.id_usuario " +
					"LEFT OUTER JOIN actividad_procesal a ON exp.id_expediente=a.id_expediente " +
					"LEFT OUTER JOIN instancia ins ON exp.id_instancia=ins.id_instancia " +
					"INNER JOIN actividad act ON a.id_actividad=act.id_actividad " +
					"LEFT OUTER JOIN via vi ON ins.id_via = vi.id_via " +
					"LEFT OUTER JOIN proceso pro ON vi.id_proceso = pro.id_proceso " +
					"WHERE a.id_actividad_procesal in ("  + sCadena + ")" +
					"ORDER BY 1";
			
			logger.debug("Query correo: " +hql);
			
			Query query = SpringInit.devolverSession().createSQLQuery(hql)
			.addEntity(ActividadxUsuario.class);

			logger.debug("Antes resultado --");
			resultado = query.list(); 
			
			logger.debug("Hay resultados -> "+resultado.size());
			
			//Cambiar correo destino en hardcode por correo del destinatario (usuario responsable)
			for (ActividadxUsuario acxUsu: resultado)
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
			
		}	*/
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
		
		//Obtener correo y datos a mostrar de BD
		/*String hql ="SELECT ROW_NUMBER() OVER (ORDER BY exp.numero_expediente) as ROW_ID," +
				"exp.numero_expediente,usu.apellido_paterno,usu.correo," +
				"act.nombre,a.fecha_vencimiento," +
				queryColor(1) + "," + queryColor(3) + 
				"FROM expediente exp " +
				"LEFT OUTER JOIN usuario usu ON exp.id_usuario=usu.id_usuario " +
				"LEFT OUTER JOIN actividad_procesal a ON exp.id_expediente=a.id_expediente " +
				"LEFT OUTER JOIN instancia ins ON exp.id_instancia=ins.id_instancia " +
				"INNER JOIN actividad act ON a.id_actividad=act.id_actividad " +
				"LEFT OUTER JOIN via vi ON ins.id_via = vi.id_via " +
				"LEFT OUTER JOIN proceso c ON vi.id_proceso = c.id_proceso " +
				"ORDER BY 1";*/
		
		//logger.debug("Query correo: " +hql);
		
		/*Query query = SpringInit.devolverSession().createSQLQuery(hql)
		.addEntity(ActividadxExpediente.class);*/
		
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
				logger.debug("--------------------------------------");
				logger.debug("-------------DATOS CORREO-------------");
				//logger.debug("Apellido: " +acxUsu.getApellidoPaterno());
				logger.debug("Actividad: " +resultado.get(i).getActividad());
				logger.debug("NumeroExpediente: " +resultado.get(i).getNroExpediente());
				logger.debug("Fecha Vencimiento: " +resultado.get(i).getFechaVencimiento().toString());
				logger.debug("Color Actividad: " + resultado.get(i).getColorFila());
				//logger.debug("Color Dia Anterior : " + acxUsu.getColorDiaAnterior());
				logger.debug("--------------------------------------");
				
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
				if (!resultado.get(i).getColorFila().equalsIgnoreCase(resultadoAyer.get(i).getColorFila()))
				{
					if(resultado.get(i).getColorFila().equals("R")){
						
						envioCorreoBean = SeteoBeanUsuario(""/*acxUsu.getApellidoPaterno()*/,resultado.get(i).getActividad(),
								resultado.get(i).getNroExpediente(),resultado.get(i).getFechaVencimiento().toString(),
								""/*acxUsu.getCorreo()*/,3);
						
					}else{
						envioCorreoBean = SeteoBeanUsuario(""/*acxUsu.getApellidoPaterno()*/,resultado.get(i).getActividad(),
								resultado.get(i).getNroExpediente(),resultado.get(i).getFechaVencimiento().toString(),
								""/*acxUsu.getCorreo()*/,1);
						
					}
					
					
				}			
			}
			else
			{
				error=true;
				/*logger.debug("----------------------------------------------------------");
				logger.debug("-------------DATOS CORREO QUE NO SE PUDO ENVIAR-----------");
				logger.debug("Apellido: " +acxUsu.getApellidoPaterno());
				logger.debug("Actividad: " +acxUsu.getActividad());
				logger.debug("NumeroExpediente: " +acxUsu.getNumeroExpediente());
				logger.debug("Color Actividad: " + acxUsu.getColor());
				logger.debug("Color Dia Anterior : " + acxUsu.getColorDiaAnterior());
				logger.debug("----------------------------------------------------------");
				logger.error("No se pudo enviar correo debido a que la fecha de vencimiento no es valida");*/
			}
			
			if (!error)
			{
				/*if (resultado.get(i).getCorreo()!=null && resultado.get(i).getCorreo().trim().length()>0)
				{
					logger.debug("Antes de enviar correo -enviarCorreo ");
					enviarCorreo(envioCorreoBean);
				}*/
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
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0  "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='R'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"       AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='N'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          = vi.id_via	    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='A'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN DAYS(SYSDATE,a.fecha_vencimiento) > 0   "+
					"      THEN 'V'					    "+
					"      WHEN DAYS(a.fecha_actividad,SYSDATE) <= 0    "+
					"      THEN 'V'					    "+
					"      ELSE 'E'					    "+
					"    END AS COLOR				    ";

			
			/*cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE " +
					"WHEN NVL( " +
				    "CASE " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				    "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				    "END,' ') = ' ' "+ 
				    "THEN " + 
				      "CASE WHEN NVL( " +
				        "CASE " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=null and id_proceso=null) THEN 'R' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				        "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+
				        "END,' ') = ' ' THEN " +
				          "CASE WHEN NVL( " +
				          "CASE " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='R' AND id_via=vi.id_via and id_proceso=null) THEN 'R' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " + 
				          "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				          "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='A' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				          "END,' ') = ' ' THEN " + 
				            "CASE WHEN NVL( "+ 
				            "CASE "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= "+  
				            "(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+  
				            "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				            "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				            "END,' ') = ' ' THEN "+  
				              "CASE WHEN NVL( "+ 
				                  "CASE "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				                    "AND  DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				                    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				                  "END,' ') = ' ' THEN "+  
				                  "CASE "+ 
				                  "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0 AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= 3 THEN 'R' "+ 
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1) >=(a.plazo_ley/2) AND "+  
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "+ 
			                      "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "+ 
			                         "DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+  
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1)<=(a.plazo_ley/2) AND "+ 
			                         "DAYS(SYSDATE,a.fecha_vencimiento)<=a.plazo_ley THEN 'A' "+
			                      "ELSE "+ 
		   						  "case when a.plazo_ley is null then 'ERROR_LOGICA_PLAZO_LEY' "+ 
		   						  "else case when days(a.fecha_actividad,a.fecha_vencimiento)=a.plazo_ley then 'ERROR_PROG' ELSE 'ERROR_LOGICA_FECHAS' end end " +
				                  "END "+ 
				              "END "+ 
				             "END "+ 
				          "END "+ 
				    "END "+ 
				"ELSE "+ 
						"CASE "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+  
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
							"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
						"END "+ 
				"END END AS COLOR " ;*/
		}
		if (modo==2)
		{
			cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE " +
					"WHEN NVL( " +
				    "CASE " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				    "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				    "END,' ') = ' ' "+ 
				    "THEN " + 
				      "CASE WHEN NVL( " +
				        "CASE " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=null and id_proceso=null) THEN 'R' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				        "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				        "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+
				        "END,' ') = ' ' THEN " +
				          "CASE WHEN NVL( " +
				          "CASE " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				          "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='R' AND id_via=vi.id_via and id_proceso=null) THEN 'R' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " + 
				          "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				          "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				          "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='A' AND id_via=vi.id_via and id_proceso=null) " +
				            "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				          "END,' ') = ' ' THEN " + 
				            "CASE WHEN NVL( "+ 
				            "CASE "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				            "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= "+  
				            "(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+  
				            "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				            "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				            "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') "+ 
				              "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				            "END,' ') = ' ' THEN "+  
				              "CASE WHEN NVL( "+ 
				                  "CASE "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				                    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				                    "AND  DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				                    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				                    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				                  "END,' ') = ' ' THEN "+  
				                  "CASE "+ 
				                  "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0 AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
			                        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= 3 THEN 'R' "+ 
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1) >=(a.plazo_ley/2) AND "+  
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "+ 
			                      "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND "+ 
			                         "DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+  
			                      "WHEN (DAYS(SYSDATE,a.fecha_actividad)*-1)<=(a.plazo_ley/2) AND "+ 
			                         "DAYS(SYSDATE,a.fecha_vencimiento)<=a.plazo_ley THEN 'A' "+ 
			                      "ELSE "+ 
		   						  "case when a.plazo_ley is null then 'ERROR_LOGICA_PLAZO_LEY' "+ 
		   						  "else case when days(a.fecha_actividad,a.fecha_vencimiento)=a.plazo_ley then 'ERROR_PROG' ELSE 'ERROR_LOGICA_FECHAS' end end " +
				                  "END "+ 
				              "END "+ 
				             "END "+ 
				          "END "+ 
				    "END "+ 
				"ELSE "+ 
						"CASE "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				        "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+  
				        "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
							"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(a.fecha_actividad,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
						"END "+ 
				"END END = " ;
		}
		if (modo==3)
		{
			cadena ="	CASE					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <= 0  "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='R'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"       AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='R'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'R'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='N'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          = vi.id_via	    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='N'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'N'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad=a.id_actividad	    "+
					"        AND id_via        =vi.id_via		    "+
					"        AND id_proceso    = c.id_proceso	    "+
					"        AND color         ='A'			    "+
					"        AND estado        = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via          =vi.id_via		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) <=    "+
					"        (SELECT dias				    "+
					"        FROM aviso				    "+
					"        WHERE id_actividad IS NULL		    "+
					"        AND id_via         IS NULL		    "+
					"        AND id_proceso      = c.id_proceso	    "+
					"        AND color           ='A'		    "+
					"        AND estado          = 'A'		    "+
					"        )					    "+
					"      THEN 'A'					    "+
					"      WHEN DAYS(SYSDATE-1,a.fecha_vencimiento) > 0   "+
					"      THEN 'V'					    "+
					"      WHEN DAYS(a.fecha_actividad,SYSDATE-1) <= 0    "+
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
}
