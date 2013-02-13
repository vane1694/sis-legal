package com.hildebrando.legal.quartz.jobs;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bbva.common.listener.SpringInit.SpringInit;

public class QuartzJob_ETL implements Job  {
	
	public static Logger logger = Logger.getLogger(QuartzJob_ETL.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ETL();
		
	}
	public void ETL(){
		OnlyEjecucion n =new OnlyEjecucion();
		llenarFacExpediente(n);
		llenarFacExpedienteADM(n);
		/*Session session =SpringInit.devolverSession();
		llenarLitigios(session);
	    llenarFacExpediente(session);
		llenarFacExpedienteADM(session);
		session.close();*/
	}
	
	public void llenarFacExpediente(OnlyEjecucion n){
		n.llenarFacExpediente();
	}

	public void  llenarFacExpedienteADM(OnlyEjecucion n){
		n.llenarFacExpedienteADM();
	}
	
/*	public void llenarFacExpediente(Session session){
		String hql =" call GESLEG.SP_INSERT_FACT_EXPEDIENTE() ";
		logger.debug("Query consulta : " +hql);
		try {
			   Transaction tx = session.beginTransaction();
			   Query query = session.createSQLQuery(hql);                                                                        
			   int respuesta = query.executeUpdate();  
			   System.out.println(" Resultado : "+respuesta);
			   session.flush();
			   session.clear();
			   tx.commit();
			} catch (Exception e) {
				logger.error(" ERROR :: llenarFacExpediente" + e.toString());
			}
	}

	public void llenarFacExpedienteADM(Session session){
		String hql =" call GESLEG.SP_INSERT_FACT_EXPEDIENTE_ADM() ";
		logger.debug("Query consulta : " +hql);
		try {  
			   Transaction tx = session.beginTransaction();
			   Query query = session.createSQLQuery(hql);                                                                        
			   int respuesta = query.executeUpdate();  
			   System.out.println(" Resultado : "+respuesta);
			   session.flush();
			   session.clear();
			   tx.commit();
			} catch (Exception e) {
				logger.error(" ERROR :: llenarFacExpedienteADM" + e.toString());
			}
	   
		
	}*/
	
	
}