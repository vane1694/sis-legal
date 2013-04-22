package com.bbva.persistencia.generica.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.general.entities.Generico;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.bbva.persistencia.generica.dao.InvolucradoDao;
import com.bbva.persistencia.generica.dao.ReportesDao;
import com.hildebrando.legal.dto.ReporteLitigiosDto;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.TipoCambio;
import com.hildebrando.legal.util.SglConstantes;

public abstract class ReportesDaoImpl<K, T extends Serializable> 
extends GenericDaoImpl<K, Serializable> implements ReportesDao<K, Serializable>   {

	@SuppressWarnings("unchecked")
	public List<ReporteLitigiosDto> obtenerStockAnterior() throws Exception{
		List<ReporteLitigiosDto> lstTemp = new ArrayList<ReporteLitigiosDto>();
		final String sql = " select * from (select d.nombre,nvl(queryAll.numero_casos,0)numero_casos,nvl(queryAll.importe,0)importe" +
				           " from  " +
				           " (select dim_procesos.nombre_tipo_proceso,dim_procesos.numero_tipo_proceso, nvl(sum(numero_casos),0)numero_casos, " +
				           " nvl(sum(importe),0)importe " +
				           " from (select proceso_id, numero_casos,importe,tiempo_id " +
				           " from (select numero_casos, importe ,tiempo_id, proceso_id " +
				           " from fact_actividad_litigio " +
				           " where proceso_id in(4,8,12))XA) XB" +
				           " inner join dim_tiempo d on XB.tiempo_id =d.tiempo_id" +
				           " right join dim_procesos on dim_procesos.proceso_id=XB.proceso_id" +
				           " where d.anio != extract(year from sysdate)" +
				           " group by dim_procesos.nombre_tipo_proceso,numero_tipo_proceso)queryAll " +
				           " right JOIN proceso d on queryAll.numero_tipo_proceso = d.id_proceso "+
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
			    data.setsImporte(row[2].toString());
			   
			    
			    lstTemp.add(data);
			}
			
			logger.info("Tamanio Lista "+lstTemp.size());
		 }
		
		
		return lstTemp;
	}
	
	@SuppressWarnings("unchecked")
	public  Generico obtenerTipoCambio(){
		Generico generico=new Generico();
		
		final String sql = "select (case when moneda =2 then valor_tipo_cambio else 0 end)DOLAR," +
				           " (case when moneda =3 then valor_tipo_cambio else 0 end)EURO " +
				           " from tipo_cambio";
		
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
