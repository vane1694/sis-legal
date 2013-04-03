package com.hildebrando.legal.quartz.jobs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob_ETL implements Job  {
	
	public static Logger logger = Logger.getLogger(QuartzJob_ETL.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ETL();
		
	}
	public void ETL(){
		OnlyEjecucion n =new OnlyEjecucion();
		n.llenarFacExpediente();
		n.llenarFacExpedienteADM();
		n.llenarLitigios();
	}
	
	
}