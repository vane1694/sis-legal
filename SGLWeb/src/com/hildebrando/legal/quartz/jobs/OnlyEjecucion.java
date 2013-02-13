package com.hildebrando.legal.quartz.jobs;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import com.bbva.common.listener.SpringInit.SpringInit;


public class OnlyEjecucion {
	
	public static Logger logger = Logger.getLogger(OnlyEjecucion.class);
	
	private JdbcTemplate jdbcTemplate;
   
    
    public void llenarLitigios() {
    	logger.info(" INFO :: llenarLitigios" );
    	DataSource dataSource=null;
    	try {
    		dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
    		this.jdbcTemplate = new JdbcTemplate(dataSource);
    		this.jdbcTemplate.update("call GESLEG.SP_ETL_LITIGIOS_DETALLE()");
    		dataSource.getConnection().close();
		} catch (Exception e) {
			try {
				dataSource.getConnection().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(" ERROR :: llenarLitigios" + e.toString());
			e.printStackTrace();
		}
    	
    }
    public void llenarDimensiones(){
    	logger.info(" INFO :: llenarDimensiones" );
    	try {
    		DataSource dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
    		this.jdbcTemplate = new JdbcTemplate(dataSource);
    		this.jdbcTemplate.update("call GESLEG.SP_INSERT_DIMENSIONES()");
		} catch (Exception e) {
			logger.error(" ERROR :: llenarDimensiones" + e.toString());
			e.printStackTrace();
		}
    	
    }
    public void llenarFacExpediente(){
    	logger.info(" INFO :: llenarFacExpediente" );
    	try {
    		DataSource dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
    		this.jdbcTemplate = new JdbcTemplate(dataSource);
    		this.jdbcTemplate.update("call GESLEG.SP_INSERT_FACT_EXPEDIENTE()");
		} catch (Exception e) {
			logger.error(" ERROR :: llenarFacExpediente" + e.toString());
			e.printStackTrace();
		}
    	
    }
    public void llenarFacExpedienteADM(){
    	logger.info(" INFO :: llenarFacExpedienteADM" );
    	try {
    		DataSource dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
    		this.jdbcTemplate = new JdbcTemplate(dataSource);
    		this.jdbcTemplate.update("call GESLEG.SP_INSERT_FACT_EXPEDIENTE_ADM()");
		} catch (Exception e) {
			logger.error(" ERROR :: llenarFacExpedienteADM" + e.toString());
			e.printStackTrace();
		}
    	
    }
    
    
}
