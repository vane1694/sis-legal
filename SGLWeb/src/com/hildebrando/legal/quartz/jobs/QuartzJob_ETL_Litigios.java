package com.hildebrando.legal.quartz.jobs;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bbva.common.listener.SpringInit.SpringInit;

public class QuartzJob_ETL_Litigios implements Job  {
	
	public static Logger logger = Logger.getLogger(QuartzJob_ETL_Litigios.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ETL();
		
	}
	public void ETL(){
		llenarLitigios();
	}
	
	public void llenarLitigios(){
		OnlyEjecucion n =new OnlyEjecucion();
			n.llenarLitigios();
		
	}

}