package com.hildebrando.legal.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hildebrando.legal.mb.JobsMB;

public class QuartzJob_Oficinas implements Job
{
	@SuppressWarnings("static-access")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobsMB job = new JobsMB();
		job.cargarOficinas();		
	}
}
