package com.hildebrando.legal.quartz.jobs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob_PRUEBA implements Job {
	public static Logger logger = Logger.getLogger(QuartzJob_PRUEBA.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(" Executando ....... ");
	}
	


}
