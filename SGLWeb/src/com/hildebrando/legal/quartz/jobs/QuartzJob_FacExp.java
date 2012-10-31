package com.hildebrando.legal.quartz.jobs;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bbva.common.listener.SpringInit.SpringInit;

public class QuartzJob_FacExp implements Job{
	public static Logger logger = Logger.getLogger(QuartzJob_FacExp.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		llenarFacExpediente();
	}
	
	@SuppressWarnings("deprecation")
	public void llenarFacExpediente(){
		//boolean error =false;
		String hql ="{ call GESLEG.SP_INSERT_FACT_EXPEDIENTE() }";
		try {
			CallableStatement call = SpringInit.devolverSession().connection().prepareCall(hql);
			call.execute();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
		logger.debug("Query consulta : " +hql);
	}
}
