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
	public EnvioMailMB()
	{
		//inicializarParametrosBD();
		//enviarEmailCopiaAUno();
	}
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
		
		/*puerto = "SELECT * FROM TABLA_PAREMTROS WHERE PUERTO = nroPuertoBco"; 
		host = "SELECT * FROM TABLA_PAREMTROS WHERE HOST = hostBco";*/
		
		//Obtener correo y datos a mostrar de BD
		String hql ="SELECT exp.numero_expediente,usu.apellido_paterno,usu.correo," +
				"ac.nombre actividad,a.fecha_vencimiento," +
				"CASE " +
				"WHEN NVL( " +
				"CASE " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R'" +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='A' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				"END,' ') = ' ' " +
				"THEN " +
				"CASE WHEN NVL( " +
				"CASE " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='R' AND id_via=null and id_proceso=null) THEN 'R' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=null and id_proceso=null) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='A' AND id_via=null and id_proceso=null) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
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
				"CASE WHEN NVL( " +
				"CASE " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= " +
				"(SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='R') THEN 'R' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='N') " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_proceso=pro.id_proceso and id_actividad=null and id_via=null AND color='A') " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				"END,' ') = ' ' THEN " +
				"CASE WHEN NVL( " +
				"CASE " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=vi.id_via) " +
				"AND  DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='A' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				"END,' ') = ' ' THEN  " +
				"CASE " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 0 AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= 3 THEN 'R' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= 3 AND " +
				"DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'N' " +
				"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND " +
				"DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				"WHEN DAYS(a.fecha_actividad,SYSDATE)<=(a.plazo_ley/2) AND " +
				"DAYS(SYSDATE,a.fecha_vencimiento)<=a.plazo_ley THEN 'A' " +
				"END " +
				"END " +
				"END " +
				"END " +
				"END " +
				"ELSE " +
				"CASE " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento)<=0 AND (DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= a.plazo_ley AND " +
				"(DAYS(SYSDATE,a.fecha_vencimiento)*-1) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='R' AND id_via=vi.id_via) THEN 'R' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento)<= a.plazo_ley THEN 'N' " +
				"WHEN DAYS(a.fecha_actividad,SYSDATE)=0 AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley THEN 'V' " +
				"WHEN DAYS(SYSDATE,a.fecha_vencimiento) > (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='N' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= (SELECT dias FROM aviso WHERE id_actividad=a.id_actividad AND color='A' AND id_via=vi.id_via) " +
				"AND DAYS(SYSDATE,a.fecha_vencimiento) <= a.plazo_ley  THEN 'A' " +
				"END " +
				"END COLOR " +
				"FROM expediente exp " +
				"LEFT OUTER JOIN usuario usu ON exp.id_usuario=usu.id_usuario " +
				"LEFT OUTER JOIN actividad_procesal a ON exp.id_expediente=a.id_expediente " +
				"LEFT OUTER JOIN instancia ins ON exp.id_instancia=ins.id_instancia " +
				"INNER JOIN actividad ac ON a.id_actividad=ac.id_actividad " +
				"LEFT OUTER JOIN via vi ON ins.id_via = vi.id_via " +
				"LEFT OUTER JOIN proceso pro ON vi.id_proceso = pro.id_proceso " +
				"ORDER BY 1";
		
		Query query = SpringInit.devolverSession().createSQLQuery(hql)
				.addEntity(ActividadxUsuario.class);
		
		resultado = query.list();
		
	}
	public void enviarEmailCopiaAUno() 
	{
		inicializarParametrosBD();
		Correo envioCorreoBean = SeteoBeanUsuario();
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
	private static Correo SeteoBeanUsuario()
	{
		Correo envioCorreoBean = new Correo();
		envioCorreoBean.setFrom("sistemaLegal@grupobbva.com.pe");
		envioCorreoBean.setTo("larosac@hildebrando.com");
		envioCorreoBean.setCorreoCuerpo(textoMensajeMail("La Rosa","Tachas","PC0-001","13/09/2012"));
		envioCorreoBean.setAsunto("ACTIVIDAD PROCESAL - EXP PC0-001 VENCE 13/09/2012");
		return envioCorreoBean;
	}
	public static String textoMensajeMail(String apellido,String nombreActividad, String expediente, String fechaVencimiento)
	{
		String texto = "Estimado Doc. "+apellido+":\n" + "    La actividad procesal " + nombreActividad +
				" del expediente " + expediente  + ", esta proxima a vencer el dia " + fechaVencimiento +
				"\n\nPor favor tomar las medidas del caso." + 
				"\n\nAtte." + 
				"\n" +
				"\nSISTEMA DE GESTION LEGAL"; 
		return texto;
	}
}
