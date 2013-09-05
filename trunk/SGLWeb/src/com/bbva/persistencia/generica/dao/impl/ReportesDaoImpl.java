package com.bbva.persistencia.generica.dao.impl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bbva.general.entities.Generico;
import com.bbva.persistencia.generica.dao.ReportesDao;
import com.hildebrando.legal.dto.ReporteLitigiosDto;
import com.hildebrando.legal.modelo.ActividadxUsuario;

public abstract class ReportesDaoImpl<K, T extends Serializable> 
extends GenericDaoImpl<K, Serializable> implements ReportesDao<K, Serializable>   {
 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ActividadxUsuario> obtenerActividadxUsuarioDeActProc(String sCadena) 
	{	
		
		List<ActividadxUsuario> tmpLista = new ArrayList<ActividadxUsuario>();
		try {
			
			EnvioMailDaoImpl dao=new EnvioMailDaoImpl();
			String hql ="SELECT exp.numero_expediente,usu.apellido_paterno,usu.correo," +
					"act.nombre ,a.fecha_vencimiento," +
					dao.queryColor(1) + "," + dao.queryColor(3) + 
					"FROM GESLEG.expediente exp " +
					"LEFT OUTER JOIN GESLEG.usuario usu ON exp.id_usuario=usu.id_usuario " +
					"LEFT OUTER JOIN GESLEG.actividad_procesal a ON exp.id_expediente=a.id_expediente " +
					"LEFT OUTER JOIN instancia ins ON exp.id_instancia=ins.id_instancia " +
					"INNER JOIN GESLEG.actividad act ON a.id_actividad=act.id_actividad " +
					"LEFT OUTER JOIN GESLEG.via vi ON ins.id_via = vi.id_via " +
					"LEFT OUTER JOIN GESLEG.proceso c ON vi.id_proceso = c.id_proceso " +
					"WHERE a.id_actividad_procesal in ("+ sCadena +")" +
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
			
			/***
			 * List<Object> = getSession().createSQLQuery(sqlQueryString)
             .addScalar("field_1", Hibernate.INTEGER).addScalar("field_2", Hibernate.INTEGER) .........addScalar("field_n", Hibernate.INTEGER)
             .setResultTransformer(Transformers.aliasToBean(Target.class)).list(); 
			 */
			
			
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
	
	
	
	@SuppressWarnings("unchecked")
	public List<ReporteLitigiosDto> obtenerStockAnterior() throws Exception{
		List<ReporteLitigiosDto> lstTemp = new ArrayList<ReporteLitigiosDto>();
		final String sql = "  select * from  ( select d.nombre,nvl(queryAll.numero_casos,0)numero_casos,nvl(queryAll.importe,0)importe "+
				           "  from  "+
				           " (select dim_procesos.nombre_tipo_proceso,dim_procesos.numero_tipo_proceso, nvl(sum(numero_casos),0)numero_casos,"+ 
				           " nvl(sum(importe),0)importe "+
				           " from (select proceso_id, numero_casos,importe,tiempo_id "+
				           " from (select numero_casos, importe ,tiempo_id, proceso_id "+
				           " from GESLEG.fact_actividad_litigio "+
				           " where proceso_id in(4,8,12))XA) XB"+
				           " inner join GESLEG.dim_tiempo d on XB.tiempo_id =d.tiempo_id "+
				           " right join GESLEG.dim_procesos on dim_procesos.proceso_id=XB.proceso_id "+
				           "  where d.anio != extract(year from sysdate) and  dim_procesos.numero_tipo_proceso in(1,2,3) "+
				           "  group by dim_procesos.nombre_tipo_proceso,numero_tipo_proceso)queryAll "+
				           "  right JOIN GESLEG.proceso d on queryAll.numero_tipo_proceso = d.id_proceso "+
                   " order by d.id_proceso asc ) where  rownum<=3"; 
  
		
		List ResultList=new ArrayList();
		try {
			ResultList = (ArrayList<ReporteLitigiosDto>) getHibernateTemplate().execute(
					new HibernateCallback() {
						public List doInHibernate(Session session)throws HibernateException {
							SQLQuery sq = session.createSQLQuery(sql);
							return sq.list();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
		ReporteLitigiosDto data;
		
		 if(ResultList.size()>0)
		 {
			logger.info("ResultList.size "+ResultList.size());
			for(int i=0;i<=ResultList.size()-1;i++)
			{
			    Object[] row =  (Object[]) ResultList.get(i);
			    data = new ReporteLitigiosDto();
			    
			    data.setSproceso(row[0].toString());
			    data.setsNumeroCasos(row[1].toString());
			    data.setsImporte("S/. " +customFormat("###,###.###", new Double(row[2].toString())));
			    
			    lstTemp.add(data);
			}
			
			logger.info("Tamanio Lista "+lstTemp.size());
		 }
		
		
		return lstTemp;
	}
	

	 static public String customFormat(String pattern, double value ) {
	    DecimalFormat myFormatter = new DecimalFormat(pattern);
	    String output = myFormatter.format(value);
	    return output;
	 }
	@SuppressWarnings("unchecked")
	public  Generico obtenerTipoCambio(){
		Generico generico=new Generico();
		
		final String sql = "  select  sum(DOLAR)DOLAR, sum(EURO)EURO from (" +
			               "  select " +
			               " (CASE WHEN ID_MONEDA =2 THEN VALOR_TIPO_CAMBIO ELSE 0 END) AS DOLAR," +
			               " (CASE WHEN ID_MONEDA =3 THEN VALOR_TIPO_CAMBIO ELSE 0 END) AS EURO" +
			               " from( select  id_moneda,valor_tipo_cambio" +
			               " from(select id_moneda,  valor_tipo_cambio " +
			               " from GESLEG.tipo_cambio  where id_moneda=2  and estado='A'and fecha =" +
			               " (select max(fecha) from GESLEG.tipo_cambio  where id_moneda=2 and estado='A'))" +
			               " union all " +
			               " (select id_moneda, valor_tipo_cambio  from GESLEG.tipo_cambio" +
			               " where id_moneda=3  and estado='A' " +
			               " and fecha = (select max(fecha) from GESLEG.tipo_cambio" +
			               " where id_moneda=3 and estado='A'))) ) ";
		
		List ResultList = (ArrayList<Generico>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public List doInHibernate(Session session)
							throws HibernateException {
						SQLQuery sq = session.createSQLQuery(sql);
						return sq.list();
					}
				});
		
		 if(ResultList.size()>0)
		 {
			logger.info("ResultList.size "+ResultList.size());
		
			    Object[] row =  (Object[]) ResultList.get(0);
			    generico = new Generico();
			    
			    generico.setDescripcion(row[0].toString());
			    generico.setCantidad(row[1].toString());
			  
			}
			
		
		 return generico;
	
	}
	
}
