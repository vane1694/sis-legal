package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import pe.com.bbva.bean.CorreoBean;
import pe.com.bbva.enviarCorreoService.EnviarCorreoService;
import pe.com.bbva.enviarCorreoService.EnviarCorreoServiceImpl;
import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.ActividadxUsuario;
import com.hildebrando.legal.modelo.Correo;
import com.hildebrando.legal.modelo.Parametros;

@ManagedBean(name = "envioMail")
public class EnvioMailMB 
{
	public static Logger logger = Logger.getLogger(EnvioMailMB.class);
	private static int puerto;
	private static String host;
	private List<ActividadxUsuario> resultado;
	private Boolean envioMailAllDay;
	private static Correo envioCorreoBean;
	
	public List<ActividadxUsuario> getResultado() {
		return resultado;
	}
	public void setResultado(List<ActividadxUsuario> resultado) {
		this.resultado = resultado;
	}
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
		//Variables puerto y host que se obtiene de la tabla parámetros. 
		List<Parametros> parametros = new ArrayList<Parametros>();
		GenericDao<Parametros, Object> parametrosDAO = (GenericDao<Parametros, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Parametros.class);
			
		try {
			parametros = parametrosDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Parametros param : parametros) 
		{
			host=param.getHost();
			puerto = param.getPuerto();
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
		boolean error =false;
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
				"ORDER BY 1";
		
		logger.debug("Query correo: " +hql);
		
		Query query = SpringInit.devolverSession().createSQLQuery(hql)
		.addEntity(ActividadxUsuario.class);

		resultado = query.list();
		
		//Cambiar correo destino en hardcode por correo del destinatario (usuario responsable)
		for (ActividadxUsuario acxUsu: resultado)
		{
			if (acxUsu.getFechaVencimiento()!=null)
			{
				envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
						acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
						"larosac@hildebrando.com",2);
			
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
			
			//if (acxUsu.getCorreo()!=null && acxUsu.getCorreo().trim().length()>0)
			//{
				enviarCorreo(envioCorreoBean);
			//}
		}		
	}
	@SuppressWarnings("unchecked")
	public void prepararCorreoCambioColor()
	{
		boolean error =false;
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
				"ORDER BY 1";
		
		logger.debug("Query correo: " +hql);
		
		Query query = SpringInit.devolverSession().createSQLQuery(hql)
		.addEntity(ActividadxUsuario.class);

		resultado = query.list();
		
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
				logger.debug("--------------------------------------");
				
				if (acxUsu.getColor().equals("N"))
				{
					envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
							acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
							"larosac@hildebrando.com",1);
									
				}
				if (acxUsu.getColor().equals("R"))
				{
					envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
							acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
							"larosac@hildebrando.com",3);
					
				}
				if (!acxUsu.getColor().equalsIgnoreCase(acxUsu.getColorDiaAnterior())
				   && acxUsu.getColor()!="R" && acxUsu.getColor()!="N")
				{
					envioCorreoBean = SeteoBeanUsuario(acxUsu.getApellidoPaterno(),acxUsu.getActividad(),
							acxUsu.getNumeroExpediente(),acxUsu.getFechaVencimiento().toString(),
							"larosac@hildebrando.com",1);
					
				}			
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
				logger.debug("----------------------------------------------------------");
				logger.error("No se pudo enviar correo debido a que la fecha de vencimiento no es valida");
			}
			
			if (!error)
			{
				//if (acxUsu.getCorreo()!=null && acxUsu.getCorreo().trim().length()>0)
				//{
					enviarCorreo(envioCorreoBean);
				//}
			}
		}
				
	}	
	public void enviarCorreo(Correo envioCorreoBean)
	{
		EnviarCorreoService enviaCorreo = new EnviarCorreoServiceImpl();

		// Instanciar el Bean de javaMailJAR.jar. 
		CorreoBean correoB = new CorreoBean(); 
		
		//Envía el puerto que obtiene de la consulta obtenida de la tabla parámetros.
		correoB.setPuertoBco(String.valueOf(puerto)); 
		
		//Envía el host que obtiene de la consulta obtenida de la tabla parámetros. 
		correoB.setHostBco(host); 
		
		//Envía valores recogidos de la clase EnvioCorreoDto mapeado en la tabla ENVIO_CORREO 
		// Correo de. Ejm: sitemaAlmacenes@grubobbva.com.pe 
		correoB.setFrom(envioCorreoBean.getFrom()); 
		// Correo para. 
		correoB.setTo(envioCorreoBean.getTo()); 
		// Mensaje que se desea enviar en el correo. 
		correoB.setMensaje(envioCorreoBean.getCorreoCuerpo()); 
		// Título del correo a enviar. 
		correoB.setSubject(envioCorreoBean.getAsunto()); 
		// Invocar el servicio que realiza el envío de correo. 
		enviaCorreo.MailUno (correoB);
	}	
	private static Correo SeteoBeanUsuario(String apellido,String nombreActividad, String expediente, 
			String fechaVencimiento, String correo, int modo)
	{
		envioCorreoBean.setFrom("sistemaLegal@grupobbva.com.pe");
		envioCorreoBean.setTo(correo);
		envioCorreoBean.setCorreoCuerpo(textoMensajeMail(apellido,nombreActividad,expediente,
				fechaVencimiento,modo));
		
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
		return texto;
	}
	private String queryColor(int modo)
	{
		String cadena="";
		
		if (modo==1)
		{
			cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE " +
					"WHEN NVL( " +
				    "CASE " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				    "(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' " +
				    "WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				    "AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
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
				        "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
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
				          "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
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
				            "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
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
				                    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
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
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+  
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
							"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
						"END "+ 
				"END END AS COLOR " ;
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
				    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
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
				        "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
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
				          "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
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
				            "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
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
				                    "WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
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
			                         "DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+  
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
							"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
							"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
								"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
						"END "+ 
				"END END = " ;
		}
		if (modo==3)
		{
			cadena = "case when days(SYSDATE,a.fecha_vencimiento) < 0 then 'R' else CASE " +
					"WHEN NVL( " +
				    "CASE " +
				    "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<=0 AND (DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				    "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' " +
				    "AND id_via=vi.id_via) THEN 'R' " +
				    "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				    "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				    "WHEN DAYS(a.fecha_actividad,to_date(SYSDATE-1))=0 AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				    "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " +
				        "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) " +
				        "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				    "END,' ') = ' ' "+ 
				    "THEN " + 
				      "CASE WHEN NVL( " +
				        "CASE " +
				        "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<=0 AND " + 
				        "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				        "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=null " +
				        "and id_proceso=null) THEN 'R' " +
				        "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null " +
				        "and id_proceso=null) AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				        "WHEN DAYS(a.fecha_actividad,to_date(SYSDATE-1))=0 AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				        "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=null and id_proceso=null) " +
				          "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+
				        "END,' ') = ' ' THEN " +
				          "CASE WHEN NVL( " +
				          "CASE " +
				          "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<=0 AND " + 
				          "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= a.plazo_ley AND " + 
				          "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='R' AND id_via=vi.id_via and " +
				          "id_proceso=null) THEN 'R' WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' " +
				          "AND id_via=vi.id_via and id_proceso=null) AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				          "WHEN DAYS(a.fecha_actividad,to_date(SYSDATE-1))=0 AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				          "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=null AND color='N' AND id_via=vi.id_via and " +
				          "id_proceso=null) AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=null AND color='A' AND id_via=vi.id_via " +
				          "and id_proceso=null) AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				          "END,' ') = ' ' THEN " + 
				            "CASE WHEN NVL( "+ 
				            "CASE "+ 
				            "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<=0 AND "+  
				            "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				            "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= "+  
				            "(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' "+ 
				            "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+  
				            "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				            "WHEN DAYS(a.fecha_actividad,to_date(SYSDATE-1))=0 AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				            "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') "+ 
				              "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') "+ 
				              "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				            "END,' ') = ' ' THEN "+  
				              "CASE WHEN NVL( "+ 
				                  "CASE "+ 
				                    "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<=0 AND "+  
				                    "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				                    "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
				                    "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) " + 
				                    "AND  DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
				                    "WHEN DAYS(a.fecha_actividad,to_date(SYSDATE-1))=0 AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
				                    "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
				                      "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
				                  "END,' ') = ' ' THEN "+  
				                  "CASE "+ 
				                  "WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= 0 AND "+  
			                        "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
			                        "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= 3 THEN 'R' "+ 
			                      "WHEN (DAYS(to_date(SYSDATE-1),a.fecha_actividad)*-1) >=(a.plazo_ley/2) AND "+  
			                         "DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'N' "+ 
			                      "WHEN DAYS(a.fecha_actividad,to_date(SYSDATE-1))=0 AND "+ 
			                         "DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+  
			                      "WHEN (DAYS(to_date(SYSDATE-1),a.fecha_actividad)*-1)<=(a.plazo_ley/2) AND "+ 
			                         "DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<=a.plazo_ley THEN 'A' "+
			                      "WHEN DAYS(SYSDATE,a.fecha_actividad)=0 then 'V' " +
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
							"WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<=0 AND (DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= a.plazo_ley AND "+  
				        "(DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' "+ 
							"WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+  
				        "AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento)<= a.plazo_ley THEN 'N' "+ 
							"WHEN DAYS(a.fecha_actividad,to_date(SYSDATE-1))=0 AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley THEN 'V' "+ 
							"WHEN DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='N' AND id_via=vi.id_via) "+ 
								"AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=act.id_actividad AND color='A' AND id_via=vi.id_via) "+ 
								"AND DAYS(to_date(SYSDATE-1),a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' "+ 
						"END "+ 
				"END END AS COLOR_DIA_ANTERIOR " ;
		}
		return cadena;
	}
}
