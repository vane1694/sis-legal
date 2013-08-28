package com.hildebrando.legal.mb.reportes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.util.Utilitarios;
import com.hildebrando.legal.util.SglConstantes;

@Controller
@RequestMapping("/main")
public class JasperController {
	
	private static Logger logger =  Logger.getLogger(JasperController.class);
	
	public JasperController() {

	}
	private JdbcTemplate jdbcTemplate; 
	
	@RequestMapping(value="/download/reportDetallado_V5.htm", method=RequestMethod.GET)
	public String generarReporteDetallado(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request){
		
				try{
					
				String nombreArchivo= "Reporte_Detallado_"+Utilitarios.formatoFechaHora(new Date())+ ".xls";
				logger.debug("nombreArchivo: "+nombreArchivo);
				
				response.setHeader("Content-type", "application/xls");
				response.setHeader("Content-Disposition","attachment; filename=\"" + nombreArchivo + "\"");
					
					DataSource dataSource=null;
					dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
				
					modelMap.put("REPORT_CONNECTION", dataSource);
				
					OutputStream os = response.getOutputStream();
					os.flush();
					return "reportDetallado_V5";
				} catch (IOException e) {
					logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"IOException al generar el archivo: "+e);
				} catch (Exception e) {
					logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+" al generar el archivo: "+e);
				}
			
		
		return "";
	}
	

	
}
