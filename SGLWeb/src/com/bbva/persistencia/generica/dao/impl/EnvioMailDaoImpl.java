package com.bbva.persistencia.generica.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.EnvioMailDao;
import com.bbva.persistencia.generica.dao.ReportesDao;
import com.hildebrando.legal.modelo.ActividadxUsuario;

//public class EnvioMailDaoImpl extends GenericDaoImpl implements EnvioMailDao {
	public  class EnvioMailDaoImpl 
	extends GenericDaoImpl implements EnvioMailDao   {
	
	public List<ActividadxUsuario> obtenerActividadxUsuarioDeActProc(String sCadena) 
		{	
			List<ActividadxUsuario> tmpLista = new ArrayList<ActividadxUsuario>();
			ReportesDao<Object, Object> service = (ReportesDao<Object, Object>) SpringInit.getApplicationContext().getBean("reportesEspDao");
			tmpLista=service.obtenerActividadxUsuarioDeActProc(sCadena);
			
			logger.info("Tamanio Lista "+tmpLista.size());
			return tmpLista;
		}
	@SuppressWarnings("unchecked")
	/*public List<ActividadxUsuario> obtenerActividadxUsuarioDeActProc(String sCadena) 
	{	
		
		List<ActividadxUsuario> tmpLista = new ArrayList<ActividadxUsuario>();
		try {
			
			
			String hql ="SELECT exp.numero_expediente,usu.apellido_paterno,usu.correo," +
					"act.nombre ,a.fecha_vencimiento," +
					queryColor(1) + "," + queryColor(3) + 
					"FROM GESLEG.expediente exp " +
					"LEFT OUTER JOIN GESLEG.usuario usu ON exp.id_usuario=usu.id_usuario " +
					"LEFT OUTER JOIN GESLEG.actividad_procesal a ON exp.id_expediente=a.id_expediente " +
					"LEFT OUTER JOIN instancia ins ON exp.id_instancia=ins.id_instancia " +
					"INNER JOIN GESLEG.actividad act ON a.id_actividad=act.id_actividad " +
					"LEFT OUTER JOIN GESLEG.via vi ON ins.id_via = vi.id_via " +
					"LEFT OUTER JOIN GESLEG.proceso c ON vi.id_proceso = c.id_proceso " +
					"WHERE a.id_actividad_procesal in ("  + sCadena + ")" +
					"ORDER BY 1";
			
			
			 
			
			 final String sSQL=hql;
			 logger.info("SQL : "+sSQL);
			 
			 ActividadxUsuario nuevo;
		
			 List<Object> ResultList=new ArrayList<Object>();
				try {
					ResultList = (ArrayList) getHibernateTemplate().execute(
							new HibernateCallback() {
								public List doInHibernate(Session session)throws HibernateException {
									logger.info(" xx  " +sSQL);
									SQLQuery sq = session.createSQLQuery(sSQL);
									sq.addScalar("numero_expediente",Hibernate.STRING);
									sq.addScalar("apellido_paterno",Hibernate.STRING);
									sq.addScalar("correo",Hibernate.STRING);
									sq.addScalar("actividad",Hibernate.STRING);
									sq.addScalar("fecha_vencimiento",Hibernate.TIMESTAMP);
									sq.addScalar("color",Hibernate.STRING);
									sq.addScalar("colorDiaAnterior",Hibernate.STRING);
									 //sq.setResultTransformer(Transformers.aliasToBean(Target.class)).list();
									return sq.list();
									
									
								}
							});
				} catch (Exception e) {
					e.printStackTrace();
				}
			 
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
			 logger.info("Tamanio de la lista : " +tmpLista);
		} catch (Exception e) {
			logger.error("Error en la consulta : " +e.toString());
			e.printStackTrace();
		}
		return tmpLista;
	}
	*/
	
	protected String queryColor(int modo)
	{
		String cadena="";
		
		if (modo==1)
		{
			
			cadena= " CASE"+
					" WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <= 0"+
					" THEN 'R'"+
					" WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <="+
					" (SELECT dias"+
					" FROM GESLEG.aviso"+
					" WHERE id_actividad=a.id_actividad"+
					" AND id_via=vi.id_via"+
					" AND id_proceso= c.id_proceso"+
					" AND color ='R'"+
					" AND estado = 'A')"+
					" THEN 'R'"+
					" WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <="+
					" (SELECT dias"+
					" FROM GESLEG.aviso	"+
					" WHERE id_actividad IS NULL		    "+
					" AND id_via          =vi.id_via		    "+
					" AND id_proceso      = c.id_proceso	    "+
					" AND color           ='R'		    "+
					" AND estado          = 'A'		    "+
					" )					    "+
					" THEN 'R'					    "+
					" WHEN GESLEG.DAYS(SYSDATE,a.fecha_vencimiento) <=    "+
					" (SELECT dias				    "+
					" FROM GESLEG.aviso				    "+
					" WHERE id_actividad IS NULL		    "+
					" AND id_via         IS NULL		    "+
					" AND id_proceso      = c.id_proceso	    "+
					" AND color           ='R'		    "+
					" AND estado          = 'A'		    "+
					" )					    "+
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
}
