package com.hildebrando.legal.quartz.jobs;

import org.apache.log4j.Logger;
import org.hibernate.Query;
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
	llenarFacExpediente();
		llenarFacExpedienteADM();
		llenarLitigios();
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void llenarLitigios(){
		String hql =" call GESLEG.SP_ETL_LITIGIOS_DETALLE() ";
		logger.info("Query consulta : " +hql);
		try {
	       Query query = SpringInit.devolverSession().createSQLQuery(hql);                                                                        
		   int respuesta = query.executeUpdate();  
		    System.out.println(" Resultado : "+respuesta);
		} catch (Exception e) {
			logger.error("llenarLitigios");
		}
		

	}

	@SuppressWarnings("deprecation")
	public void llenarFacExpediente(){
		String hql =" call GESLEG.SP_INSERT_FACT_EXPEDIENTE() ";
		logger.info("Query consulta : " +hql);
		try {
	       Query query = SpringInit.devolverSession().createSQLQuery(hql);                                                                        
		   int respuesta = query.executeUpdate();  
		    System.out.println(" Resultado : "+respuesta);
		} catch (Exception e) {
			logger.error("llenarFacExpediente");
		}
	   
		
	}
	
	@SuppressWarnings("deprecation")
	public void llenarFacExpedienteADM(){
		//boolean error =false;
		String hql =" call GESLEG.SP_INSERT_FACT_EXPEDIENTE_ADM() ";
		logger.info("Query consulta : " +hql);
		try {
	       Query query = SpringInit.devolverSession().createSQLQuery(hql);                                                                        
		   int respuesta = query.executeUpdate();  
		    System.out.println(" Resultado : "+respuesta);
		} catch (Exception e) {
			logger.error("llenarFacExpedienteADM");
		}
	   
		
	}
}
