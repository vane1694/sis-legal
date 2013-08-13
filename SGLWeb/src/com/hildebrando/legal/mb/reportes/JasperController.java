package com.hildebrando.legal.mb.reportes;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.hildebrando.legal.dto.FiltrosDto;

@Controller
@RequestMapping("/main")
public class JasperController {
	
	private static Logger logger =  Logger.getLogger(JasperController.class);
	
	public JasperController() {

	}
	private JdbcTemplate jdbcTemplate; 
	
	@RequestMapping(value="/download/reportDetallado_V5.htm", method=RequestMethod.GET)
	public String generarReporteDetallado(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request){
		logger.info("==== generarReporteDetallado ==== ");
		try {
			response.setHeader("Content-type", "application/xls");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"Reporte_Detallado.xls\"");
		
			/*response.setHeader("Content-type", "application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"Reporte_Detallado.pdf\"");*/

			FiltrosDto filtrosDto_TEMP = (FiltrosDto) request.getSession(true).getAttribute("filtrosDto_TEMP");
			logger.info(" filtrosDto_TEMP "+filtrosDto_TEMP) ;
			llamarProcedimientoDetallado(filtrosDto_TEMP);
			
			DataSource dataSource=null;
			dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
		
			modelMap.put("REPORT_CONNECTION", dataSource);

			
			OutputStream os = response.getOutputStream();
			os.flush();
		} catch (IOException e) {
			logger.info("" + "al generar el archivo: " , e);
		} catch (Exception e) {
			logger.info("" + "al generar el archivo: " , e);
		}
		return ("reportDetallado_V5");
	}
	

	public boolean llamarProcedimientoDetallado(FiltrosDto filtrosDto) {
		logger.info(" INFO :: llamarProcedimientoDetallado" +filtrosDto.toString());
		boolean retorno=true;
		DataSource dataSource=null;
		try {
			dataSource = (DataSource) SpringInit.getApplicationContext().getBean("jndiDataSourceOnly");
			
			Object[] objecto=	new Object[28];
			objecto[0] =filtrosDto.getProceso();
			objecto[1] =filtrosDto.getVia();
			objecto[2] =filtrosDto.getInstancia();
			if(filtrosDto.getResponsable()!=null){
			objecto[3] =filtrosDto.getResponsable().getIdUsuario();
			}
			objecto[4] =filtrosDto.getFechaInicio();
			objecto[5] =filtrosDto.getFechaFin();
			
			objecto[6] =filtrosDto.getBanca();
			objecto[7] =filtrosDto.getTerritorio();
			if(filtrosDto.getOficina()!=null){
			objecto[8] =filtrosDto.getOficina().getIdOficina();
			}
			objecto[9] =filtrosDto.getDepartamento();
			objecto[10] =filtrosDto.getProvincia();
			objecto[11] =filtrosDto.getDistrito();
			
			objecto[12] =filtrosDto.getTipoExpediente();
			objecto[13] =filtrosDto.getCalificacion();
			if(filtrosDto.getOrgano()!=null){
			objecto[14] =filtrosDto.getOrgano().getIdOrgano();
			}
			if(filtrosDto.getRecurrencia()!=null){
			objecto[15] =filtrosDto.getRecurrencia().getIdRecurrencia();
			}
			objecto[16] =filtrosDto.getRiesgo();
			objecto[17] =filtrosDto.getActProcesal();
			if(filtrosDto.getMateria()!=null){
			objecto[18] =filtrosDto.getMateria().getIdMateria();
			}
			objecto[19] =filtrosDto.getEstado();
			
			objecto[20] =filtrosDto.getTipoImporte();
			objecto[21] =filtrosDto.getMoneda();
			objecto[22] =filtrosDto.getImporteMinimo();
			objecto[23] =filtrosDto.getImporteMaximo();
			
			objecto[24] =filtrosDto.getNombre();
			objecto[25] =filtrosDto.getRol();
			
			objecto[26] =filtrosDto.getEstudio();
			if(filtrosDto.getAbogado()!=null){
			objecto[27] =filtrosDto.getAbogado().getIdAbogado();
			}
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		    String sql = "call GESLEG.SP_ETL_DETALLADO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			this.jdbcTemplate.update(sql,objecto);
			dataSource.getConnection().close();
		} catch (Exception e) {
			try {
				dataSource.getConnection().close();
			 retorno=false;
			} catch (SQLException e1) {
				logger.error(" ERROR :: llamarProcedimientoDetallado", e);
				e1.printStackTrace();
			}
			logger.error(" ERROR :: llamarProcedimientoDetallado", e);
			e.printStackTrace();
		}
		dataSource=null;
		System.gc();
		return retorno;
	}
    
}
