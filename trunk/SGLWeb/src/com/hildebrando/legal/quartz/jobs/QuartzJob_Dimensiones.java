package com.hildebrando.legal.quartz.jobs;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bbva.common.listener.SpringInit.SpringInit;

public class QuartzJob_Dimensiones implements Job {
	public static Logger logger = Logger.getLogger(QuartzJob_Dimensiones.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		llenarDimensiones();
	}
	

	public void llenarDimensiones(){
		//boolean error =false;
		String hql =" call GESLEG.SP_INSERT_DIMENSIONES() ";
		logger.info("Query consulta : " +hql);
		try {
	       Query query = SpringInit.devolverSession().createSQLQuery(hql);                                                                        
		   int respuesta = query.executeUpdate();  
		    System.out.println(" Resultado : "+respuesta);
		} catch (Exception e) {
			logger.error("llenarDimensiones");
		}
		
	   


	}

}
