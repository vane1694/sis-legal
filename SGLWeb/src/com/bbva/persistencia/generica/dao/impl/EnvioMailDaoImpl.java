package com.bbva.persistencia.generica.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bbva.persistencia.generica.dao.EnvioMailDao;
import com.hildebrando.legal.modelo.ActividadxUsuario;

public class EnvioMailDaoImpl extends GenericDaoImpl implements EnvioMailDao {

	
	public List<ActividadxUsuario> obtenerActividadxUsuarioDeActProc(String sCadena) throws Exception
	{	
		
		List<ActividadxUsuario> tmpLista = new ArrayList<ActividadxUsuario>();
		
		String hql ="SELECT exp.numero_expediente,usu.apellido_paterno,usu.correo," +
				"act.nombre ,a.fecha_vencimiento," +
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
		
		 logger.info("SQL : "+hql);
		 
		 final String sSQL=hql;
		
		 ActividadxUsuario nuevo;
		 List ResultList = (ArrayList<ActividadxUsuario>)getHibernateTemplate().execute(new HibernateCallback() 
		 {
				public List<Object> doInHibernate(Session session) throws HibernateException 
				{
					SQLQuery sq =session.createSQLQuery(sSQL);
					return sq.list();
				}
		 });
		 
		 if(ResultList.size()>0)
		 {
			logger.info("ResultList.size "+ResultList.size());
			for(int i=0;i<=ResultList.size()-1;i++)
			{
			    Object[] row =  (Object[]) ResultList.get(i);
			    nuevo = new ActividadxUsuario();
			    
			    nuevo.setNumeroExpediente(row[0].toString());
			    nuevo.setApellidoPaterno(row[1].toString());
			    nuevo.setCorreo(row[2].toString());
			    nuevo.setActividad(row[3].toString());
			    nuevo.setFechaVencimiento(row[4].toString());
			    nuevo.setColor(row[5].toString());
			    nuevo.setColorDiaAnterior(row[6].toString());
			    
			    tmpLista.add(nuevo);
			}
			
			logger.info("Tamanio Lista "+tmpLista.size());
		 }
		
		return tmpLista;
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

		/*	
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
}
