package com.bbva.persistencia.generica.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bbva.persistencia.generica.dao.InvolucradoDao;
import com.hildebrando.legal.dto.InvolucradoDto;
import com.hildebrando.legal.modelo.Involucrado;

public abstract class InvolucradoDaoImpl<K, T extends Serializable> 
	extends GenericDaoImpl<K, Serializable> implements InvolucradoDao<K, Serializable> {
	
	@SuppressWarnings("unchecked")
	public List<Long> obtenerExpedientes(List<Integer> idInvs) throws Exception{
		
		String idInvsS=""; 
		
		for (int i = 0; i < idInvs.size(); i++) {
			
			if(idInvs.size()==1){
				
				idInvsS= idInvsS + "("+idInvs.get(i).intValue()+")";
				
			}else{
				
				if(idInvs.size()>1 && i <(idInvs.size()-1)){
					
					if(i==0){
						
						idInvsS= idInvsS + "("+idInvs.get(i).intValue();
						
					}else{
						
						idInvsS= idInvsS + "," + idInvs.get(i).intValue();
					}
					
				}else{
					
					if(i==idInvs.size() -1){
						
						idInvsS= idInvsS +","+ idInvs.get(i).intValue() +")";
					}
					
				}
				
			}
			
			
		}
		
		
		
		final String sql = "select inv.id_expediente, inv.id_involucrado"
				+ " from GESLEG.involucrado inv "
				+ " where  "
				+ " inv.id_involucrado IN  " + idInvsS;

		
		List ResultList = (ArrayList<InvolucradoDto>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public List doInHibernate(Session session)
							throws HibernateException {
						SQLQuery sq = session.createSQLQuery(sql);
						return sq.list();
					}
				});
		
		List<InvolucradoDto> idExpedientes = new ArrayList<InvolucradoDto>();
		
		InvolucradoDto involucradoDto;
		
		if (ResultList.size() > 0) {
			for(int i=0;i<ResultList.size();i++)
			{
			    involucradoDto = new InvolucradoDto();
			    Object[] row = (Object[]) ResultList.get(i);
				involucradoDto.setIdExpediente(Long.parseLong(row[0].toString()));

			    idExpedientes.add(involucradoDto);
				
			}
		}
		
		List<Long> list = new ArrayList<Long>();
		
		for(InvolucradoDto inv:idExpedientes){
			list.add(inv.getIdExpediente());
		}
		
		
		return list;
	}

}
