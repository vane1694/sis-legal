package com.hildebrando.legal.quartz.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hildebrando.legal.mb.JobsMB;

public class QuartzJob_TipoCambio implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobsMB job = new JobsMB();
		Date fechaDate = new Date();
		job.cargarTipoCambio("27/05/2013", "S", "");
		
	}

}
