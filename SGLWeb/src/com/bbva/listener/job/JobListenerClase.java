package com.bbva.listener.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class JobListenerClase implements JobListener{

	@Override
	public String getName() {
		System.out.println("getName" );
		return "Caramba";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		System.out.println("JobExecutionContext" +arg0.getPreviousFireTime());
		
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		System.out.println("JobExecutionContext" +arg0.getPreviousFireTime());
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0, JobExecutionException arg1) {
		System.out.println("jobWasExecuted " + arg0.getPreviousFireTime());
		
	}

}
