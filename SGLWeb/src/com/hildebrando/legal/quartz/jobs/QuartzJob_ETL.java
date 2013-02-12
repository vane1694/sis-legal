package com.hildebrando.legal.quartz.jobs;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.bbva.common.listener.SpringInit.SpringInit;

public class QuartzJob_ETL implements Job  {
	
	public static Logger logger = Logger.getLogger(QuartzJob_ETL.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ETL();
		
	}
	public void ETL(){
	    llenarFacExpediente();
		llenarFacExpedienteADM();
		llenarLitigios();
	}
	
	

	
	
	public void llenarLitigios(){
		String hql =" call GESLEG.SP_ETL_LITIGIOS_DETALLE() ";
		logger.info("Query consulta : " +hql);
		try {
			   Session session =SpringInit.devolverSession();
			   //Transaction tx = session.beginTransaction();
			   Query query = session.createSQLQuery(hql);                                                                        
			   int respuesta = query.executeUpdate();  
			   System.out.println(" Resultado : "+respuesta);
			   session.flush();
			   session.clear();
			   //tx.commit();
			   session.close();
			} catch (Exception e) {
				logger.error("llenarLitigios");
			}

	}

	
	public void llenarFacExpediente(){
		String hql =" call GESLEG.SP_INSERT_FACT_EXPEDIENTE() ";
		logger.info("Query consulta : " +hql);
		try {
			   Session session =SpringInit.devolverSession();
			   //Transaction tx = session.beginTransaction();
			   Query query = session.createSQLQuery(hql);                                                                        
			   int respuesta = query.executeUpdate();  
			   System.out.println(" Resultado : "+respuesta);
			   session.flush();
			   session.clear();
			   //tx.commit();
			   session.close();
			} catch (Exception e) {
				logger.error("llenarFacExpediente");
			}
	}

	public void llenarFacExpedienteADM(){
		//boolean error =false;
		String hql =" call GESLEG.SP_INSERT_FACT_EXPEDIENTE_ADM() ";
		logger.info("Query consulta : " +hql);
		try {
			   Session session =SpringInit.devolverSession();
			   //Transaction tx = session.beginTransaction();
			   Query query = session.createSQLQuery(hql);                                                                        
			   int respuesta = query.executeUpdate();  
			   System.out.println(" Resultado : "+respuesta);
			   session.flush();
			   session.clear();
			   //tx.commit();
			   session.close();
			} catch (Exception e) {
				logger.error("llenarFacExpedienteADM");
			}
	   
		
	}
}