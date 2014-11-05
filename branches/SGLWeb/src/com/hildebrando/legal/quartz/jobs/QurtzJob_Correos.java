package com.hildebrando.legal.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hildebrando.legal.mb.EnvioMailMB;

public class QurtzJob_Correos implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EnvioMailMB mail = new EnvioMailMB();
		mail.enviarCorreoCambioColor();	
		//10-07-13 Se agrega la tarea al job de correo para ejecutar la tarea de cambios de fechas
		mail.enviarCorreoCambioFechas();
	}

}
