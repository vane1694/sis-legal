package com.hildebrando.legal.quartz.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hildebrando.legal.mb.JobsMB;

public class QuartzJob_TipoCambio implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobsMB job = new JobsMB();
		String patron = "yyyy-MM-dd";
		SimpleDateFormat formato = new SimpleDateFormat(patron);
		//System.out.println("Fecha: "+formato.format(new Date()));
		String fechaString  = formato.format(new Date());   
		job.cargarTipoCambio(fechaString, "S", "");
	}
}
