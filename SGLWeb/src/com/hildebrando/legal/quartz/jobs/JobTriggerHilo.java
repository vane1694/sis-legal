package com.hildebrando.legal.quartz.jobs;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bbva.common.listener.SpringInit.SpringInit;

public class JobTriggerHilo {

	
public static Logger logger = Logger.getLogger(JobTriggerHilo.class);
	/*
	public static void main(String []args){
		JobTriggerHilo job = new JobTriggerHilo(); 
		System.out.println("main");
		job.iniciar("Jobs");
	}
	*/
	/*public void iniciar(String name){
		
		System.out.println("name:"+name);
		Runnable run = new Runnable(){

			@Override
			public void run() {
				
				Timer time = new Timer();
				TimerTask timerTask = new TimerTask() {			
					@Override
					public void run() {
						
						
						llenarFacExpedienteADM();
						llenarLitigios();
						llenarFacExpediente();
						llenarDimensiones();
						
					}
				};
				
				time.schedule(timerTask, 1000,1000*5);				
				
			}
			
		};
		
		Thread th = new Thread(run);
		
		th.start();

		
	}*/
	
	@SuppressWarnings("deprecation")
	public void llenarLitigios(){
		String hql ="{ call GESLEG.SP_ETL_LITIGIOS_DETALLE() }";
		try {
			CallableStatement call = SpringInit.devolverSession().connection().prepareCall(hql);
			call.execute();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
		logger.info("Query consulta : " +hql);

	}

	@SuppressWarnings("deprecation")
	public void llenarFacExpediente(){
		String hql ="{ call GESLEG.SP_INSERT_FACT_EXPEDIENTE() }";
		try {
			CallableStatement call = SpringInit.devolverSession().connection().prepareCall(hql);
			call.execute();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
		logger.info("Query consulta : " +hql);
	}
	
	@SuppressWarnings("deprecation")
	public void llenarFacExpedienteADM(){
		//boolean error =false;
		String hql ="{ call GESLEG.SP_INSERT_FACT_EXPEDIENTE_ADM() }";
		try {
			CallableStatement call = SpringInit.devolverSession().connection().prepareCall(hql);
			call.execute();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
		logger.info("Query consulta : " +hql);
	}
	@SuppressWarnings("deprecation")
	public void llenarDimensiones(){
		//boolean error =false;
		String hql ="{ call GESLEG.SP_INSERT_DIMENSIONES() }";
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
