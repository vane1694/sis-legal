package com.hildebrando.legal.quartz.jobs;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bbva.common.listener.SpringInit.SpringInit;

public class QuartzJob_Dimensiones implements Job {
	public static Logger logger = Logger.getLogger(QuartzJob_Dimensiones.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		llenarDimensiones();
	}
	

	public void llenarDimensiones(){
		
		try {
			SessionFactory sessionFactory = (SessionFactory) SpringInit.getApplicationContext().getBean("sessionFactory");
			sessionFactory.getCurrentSession().getNamedQuery("dimensiones");
			} catch (Exception e) {
				logger.error("llenarDimensiones" + e.toString());
			}
		
		/*String hql =" call GESLEG.SP_INSERT_DIMENSIONES() ";
		logger.info("Query consulta : " +hql);
		try{
		   Session session =SpringInit.devolverSession();
		   Transaction tx = session.beginTransaction();
		   Query query = session.createSQLQuery(hql);                                                                        
		   int respuesta = query.executeUpdate();  
		   System.out.println(" Resultado : "+respuesta);
		   session.flush();
		   session.clear();
		   tx.commit();
		   session.close();
		} catch (Exception e) {
			logger.error("llenarDimensiones " +e.toString());
		}*/
		
	   


	}

}
