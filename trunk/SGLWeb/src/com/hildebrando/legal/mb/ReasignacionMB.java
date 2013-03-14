package com.hildebrando.legal.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.ConsultaService;
import com.hildebrando.legal.util.SglConstantes;
import com.hildebrando.legal.view.ExpedienteDataModel;

/**
 * Clase que se encarga de manejar la reasignacion de expedientes a
 * otros responsable.
 * @author hildebrando 
 * **/

public class ReasignacionMB implements Serializable {
	
	public static Logger logger = Logger.getLogger(ReasignacionMB.class);
	
	private int idEstadoSelected;
	private List<EstadoExpediente> estadoExpedientes;
	private int idProceso;
	private List<Proceso> procesos;
	private int idVias;
	private List<Via> lstVias;
	private Oficina oficina;
	private String nroExpeOficial;
	private Usuario responsable;
	private Usuario nuevoResponsable;
	private EnvioMailMB envioMailMB;
	private ExpedienteDataModel expedientes;
	private Expediente[] selectedExpediente;

	private ConsultaService consultaService;

	public ReasignacionMB() {

		setIdEstadoSelected(0);
		setIdProceso(0);
		setIdVias(0);
		setOficina(new Oficina());
		setNroExpeOficial("");
		setResponsable(new Usuario());
		setNuevoResponsable(new Usuario());

		cargarCombos();
	}

	private void cargarCombos() {
		// Carga Estado Expediente
		GenericDao<EstadoExpediente, Object> estDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstPro = Busqueda.forClass(EstadoExpediente.class);

		try {
			estadoExpedientes = estDAO.buscarDinamico(filtroEstPro);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al cargar estadoExp: "+e);
		}

		// Carga Proceso
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);

		try {
			procesos = procesoDAO.buscarDinamico(filtroProceso);
		} catch (Exception e) {
			logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al cargar Procesos: "+e);
		}
	}

	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}

	public void cambioProceso() {
		if (getIdProceso() != 0) {
			lstVias = consultaService.getViasByProceso(getIdProceso());
		} else {
			lstVias = new ArrayList<Via>();
		}

	}

	@SuppressWarnings("unchecked")
	public void buscarExpedientes(ActionEvent e) {

		List<Expediente> expedientesTMP = new ArrayList<Expediente>();
		GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Expediente.class);
		filtro.add(Restrictions.isNull("expediente.idExpediente")).addOrder(
				Order.desc("idExpediente"));

		if (getNroExpeOficial().compareTo("") != 0) {
			filtro.add(Restrictions.like("numeroExpediente",
					"%" + getNroExpeOficial().trim() + "%").ignoreCase());
		}

		if (getIdProceso() != 0) {

			filtro.add(Restrictions.eq("proceso.idProceso", getIdProceso()));
		}

		if (getIdVias() != 0) {

			filtro.add(Restrictions.eq("via.idVia", getIdVias()));
		}

		if (getIdEstadoSelected() != 0) {

			filtro.add(Restrictions.eq("estadoExpediente.idEstadoExpediente",
					getIdEstadoSelected()));
		}

		if (getOficina() != null) {
			filtro.add(Restrictions.eq("oficina", getOficina()));
		}

		if (getResponsable() != null) {
			filtro.add(Restrictions.eq("usuario", getResponsable()));
		}

		try {

			expedientesTMP = expedienteDAO.buscarDinamico(filtro);

		} catch (Exception e1) {
			logger.error(SglConstantes.MSJ_ERROR_CONSULTAR+"expedientes reasignacion: "+e);
		}

		expedientes = new ExpedienteDataModel(expedientesTMP);
	}

	public void limpiarReasignacion(ActionEvent e) {
		setIdEstadoSelected(0);
		setIdProceso(0);
		setIdVias(0);
		setOficina(new Oficina());
		setNroExpeOficial("");
		setResponsable(new Usuario());
		setNuevoResponsable(new Usuario());

	}

	public List<Usuario> completeResponsable(String query) {
		List<Usuario> results = new ArrayList<Usuario>();

		List<Usuario> usuarios = consultaService.getUsuarios();

		for (Usuario usuario : usuarios) {

			if (usuario.getNombres().toUpperCase()
					.contains(query.toUpperCase())
					|| usuario.getApellidoPaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getApellidoMaterno().toUpperCase()
							.contains(query.toUpperCase())
					|| usuario.getCodigo().toUpperCase()
							.contains(query.toUpperCase())) {

				usuario.setNombreDescripcion(usuario.getCodigo() + " - "
						+ usuario.getNombres() + " "
						+ usuario.getApellidoPaterno() + " "
						+ usuario.getApellidoMaterno());

				results.add(usuario);
			}

		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public void reasignarUsuario(ActionEvent e) {
		logger.debug("=== inicia reasignarUsuario() ==== ");
		int ind = 0;

		if (getNuevoResponsable() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Requerido",
							"Responsable"));
		} else {
			for (Expediente tmp : selectedExpediente) {
				ind++;

				List<Expediente> lstExp = new ArrayList<Expediente>();
				GenericDao<Expediente, Object> expDAO = (GenericDao<Expediente, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				Busqueda filtro = Busqueda.forClass(Expediente.class);

				try {

					filtro.add(Restrictions.eq("idExpediente",
							tmp.getIdExpediente()));

					lstExp = expDAO.buscarDinamico(filtro);

					if (lstExp.size() == 1) {
						lstExp.get(0).setUsuario(getNuevoResponsable());

						
						
						for (Expediente exp : lstExp) {
							expDAO.modificar(exp);
							envioMailMB=new EnvioMailMB();
							envioMailMB.enviarCorreoCambioResponsable(exp, getNuevoResponsable());

						}
						
						FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										"Exitoso",
										"Se cambio el responsable del expediente"));

					}

				} catch (Exception ex) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"No exitoso",
									"No cambio el responsable del expediente"));
					logger.error(SglConstantes.MSJ_ERROR_EXCEPTION+"al reasignar responsable: "+e);
				}
			}

			if (selectedExpediente.length > 0) {
				selectedExpediente = new Expediente[0];
				buscarExpedientes(e);
			}
		}		
		logger.debug("=== saliendo de reasignarUsuario() ==== ");
	}

	public List<Oficina> completeOficina(String query) {

		List<Oficina> results = new ArrayList<Oficina>();
		List<Oficina> oficinas = consultaService.getOficinas();

		for (Oficina oficina : oficinas) {

			if (oficina.getUbigeo() != null) {

				String texto = oficina.getCodigo() + " "
						+ oficina.getNombre().toUpperCase() + " ("
						+ oficina.getUbigeo().getDepartamento().toUpperCase()
						+ ")";

				if (texto.contains(query.toUpperCase())) {
					oficina.setNombreDetallado(texto);
					results.add(oficina);
				}

			}

		}

		return results;
	}

	public int getIdEstadoSelected() {
		return idEstadoSelected;
	}

	public void setIdEstadoSelected(int idEstadoSelected) {
		this.idEstadoSelected = idEstadoSelected;
	}

	public List<EstadoExpediente> getEstadoExpedientes() {
		return estadoExpedientes;
	}

	public void setEstadoExpedientes(List<EstadoExpediente> estadoExpedientes) {
		this.estadoExpedientes = estadoExpedientes;
	}

	public int getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public int getIdVias() {
		return idVias;
	}

	public void setIdVias(int idVias) {
		this.idVias = idVias;
	}

	public List<Via> getLstVias() {
		return lstVias;
	}

	public void setLstVias(List<Via> lstVias) {
		this.lstVias = lstVias;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public String getNroExpeOficial() {
		return nroExpeOficial;
	}

	public void setNroExpeOficial(String nroExpeOficial) {
		this.nroExpeOficial = nroExpeOficial;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public Usuario getNuevoResponsable() {
		return nuevoResponsable;
	}

	public void setNuevoResponsable(Usuario nuevoResponsable) {
		this.nuevoResponsable = nuevoResponsable;
	}

	public ExpedienteDataModel getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(ExpedienteDataModel expedientes) {
		this.expedientes = expedientes;
	}

	public Expediente[] getSelectedExpediente() {
		return selectedExpediente;
	}

	public void setSelectedExpediente(Expediente[] selectedExpediente) {
		this.selectedExpediente = selectedExpediente;
	}

	public EnvioMailMB getEnvioMailMB() {
		return envioMailMB;
	}

	public void setEnvioMailMB(EnvioMailMB envioMailMB) {
		this.envioMailMB = envioMailMB;
	}
}
