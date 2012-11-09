package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.RowEditEvent;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.Aviso;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoCautelar;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Etapa;
import com.hildebrando.legal.modelo.Feriado;
import com.hildebrando.legal.modelo.FormaConclusion;
import com.hildebrando.legal.modelo.GrupoBanca;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Riesgo;
import com.hildebrando.legal.modelo.RolInvolucrado;
import com.hildebrando.legal.modelo.SituacionActProc;
import com.hildebrando.legal.modelo.SituacionCuota;
import com.hildebrando.legal.modelo.SituacionHonorario;
import com.hildebrando.legal.modelo.SituacionInculpado;
import com.hildebrando.legal.modelo.Territorio;
import com.hildebrando.legal.modelo.TipoCautelar;
import com.hildebrando.legal.modelo.TipoDocumento;
import com.hildebrando.legal.modelo.TipoExpediente;
import com.hildebrando.legal.modelo.TipoHonorario;
import com.hildebrando.legal.modelo.TipoInvolucrado;
import com.hildebrando.legal.modelo.TipoProvision;
import com.hildebrando.legal.modelo.Ubigeo;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.view.InvolucradoDataModel;
import com.hildebrando.legal.view.OrganoDataModel;
import com.hildebrando.legal.view.PersonaDataModel;
import com.hildebrando.legal.view.ProcesoDataModel;

@ManagedBean(name = "mnt")
@SessionScoped
public class MantenimientoMB {

	public static Logger logger = Logger.getLogger(MantenimientoMB.class);

	private int idProceso;
	private String nombreProceso;
	private String abrevProceso;
	private List<Proceso> procesos;
	private List<Proceso> procesos2;
	private Proceso selectProceso;

	private String nombreVia;
	private Via selectVia;
	private List<Via> vias;
	
	private String nombreInstancia;
	private Instancia selectInstancia;
	private List<Instancia> instancias;
	
	private String nombreActividad;
	private Actividad selectActividad;
	private List<Actividad> actividads;
	
	private String nombreMoneda;
	private Moneda selectMoneda;
	private List<Moneda> monedas;
	
	private String abrevMoneda;
	private Long rucEstudio;
	private String nombreEstudio;
	private String direccionEstudio;
	private String telefEstudio;
	private String correoEstudio;
	private Estudio selectEstudio;
	private List<Estudio> estudios;
	
	private String nombreEstCaut;
	private EstadoCautelar selectEstCaut;
	private List<EstadoCautelar> estadosCautelars;
	
	private String nombreEstExpe;
	private EstadoExpediente selectEstExpe;
	private List<EstadoExpediente> estadoExpedientes;
	
	private String nombreEtapa;
	private Etapa selectEtapa;
	private List<Etapa> etapas;
	
	private String nombreEntidad;
	private Entidad selectEntidad;
	private List<Entidad> entidads;
	
	private String nombreFormConc;
	private FormaConclusion selectFormConc;
	private List<FormaConclusion> formaConclusions;
	
	private String nombreRecurrencia;
	private Recurrencia selectRecurrencia;
	private List<Recurrencia> recurrencias;
		
	private String nombreSitActPro;
	private SituacionActProc selectSitActPro;
	private List<SituacionActProc> situacionActProcs;
	
	private String nombreSitCuota;
	private SituacionCuota selectSitCuota;
	private List<SituacionCuota> situacionCuotas;
	
	private String nombreSitHonor;
	private SituacionHonorario selectSitHonor;
	private List<SituacionHonorario> situacionHonorarios;
	
	private String nombreSitIncul;
	private SituacionInculpado selectSitInc;
	private List<SituacionInculpado> situacionInculpados;
	
	private String nombreTipoCaut;
	private TipoCautelar selectTipCaut;
	private List<TipoCautelar> tipoCautelars;
	
	private String nombreTipoExpe;
	private TipoExpediente selectTipExpe;
	private List<TipoExpediente> tipoExpedientes;
	
	private String nombreTipoHonor;
	private TipoHonorario selectTipHonor;
	private List<TipoHonorario> tipoHonorarios;
	
	private String nombreTipoInv;
	private TipoInvolucrado selectTipInv;
	private List<TipoInvolucrado> tipoInvolucrados;
	
	private String nombreTipoPro;
	private TipoProvision selectTipProv;
	private List<TipoProvision> tipoProvisions;
	
	private String nombreRolInvol;
	private RolInvolucrado selectRolInv;
	private List<RolInvolucrado> rolInvolucrados;
	
	private String nombreMateria;
	private String nombreRiesgo;
	private String tipoDocumento;
	private String descrCalificacion;
	private FormaConclusion formaConclusion;
	private String descrFormaConclusion;
	private String codigoDistrito;
	private String nomDistrito;
	private String codigoProvincia;
	private String nomProvincia;
	private String codigoDepartamento;
	private String nomDepartamento;
	private String nomGrupoBanca;
	private String codTerritorio;
	private String nomTerritorio;
	private List<GrupoBanca> lstGrupoBanca;
	private int idGrupoBanca;
	private Date fechaInicio;
	private Date fechaFin;
	private List<Organo> lstOrgano;
	private List<Ubigeo> lstUbigeo;
	private int idOrganos;
	private String idUbigeo;
	private String tipoFeriado;
	private Character indFeriado;
	private Oficina oficina;
	private List<Territorio> lstTerritorio;
	private String codigoOficina;
	private String nomOficina;
	private List<Via> lstVias;
	private List<Actividad> lstActividad;
	private List<Aviso> lstAviso;
	private List<Materia> lstMateria;
	private List<Riesgo> lstRiesgo;
	private List<TipoDocumento> lstTipoDoc;
	private List<Calificacion> lstCalificacion;
	private List<Oficina> lstOficina;
	private String idUbigeoLst;
	private List<Feriado> lstFeriado;
	private int idVia;
	private int idActividad;
	private int numDiasRojoEst1;
	private int numNaraEst1;
	private int numAmaEst1;
	private int numDiasRojoEst2;
	private int numNaraEst2;
	private int numAmaEst2;
	private int numDiasRojoEst3;
	private int numNaraEst3;
	private int numAmaEst3;
	private int idProcesoEstado;
	
	private Aviso objAviso;
	private Materia selectedMateria;
	private Riesgo selectedRiesgo;
	private TipoDocumento selectedTipoDoc;
	private Calificacion selectedCalificacion;
	private GrupoBanca selectedGrupoBanca;
	private Territorio selectedTerritorio;
	private Oficina selectedOficina;
	private Ubigeo selectedUbigeo;
	private Feriado selectedFeriado;
	

	public MantenimientoMB() {

		logger.debug("Inicializando Valores..");
		inicializarValores();

		cargarCombos();
	}

	private void inicializarValores() {

		setNombreProceso("");
		setAbrevProceso("");
		setNombreVia("");
		setNombreMateria("");
		setCodigoOficina("");
		setNomOficina("");
		Territorio newTerr = new Territorio();
		newTerr.setGrupoBanca(new GrupoBanca());
	}

	public void limpiarMateria(ActionEvent e) {

		setNombreMateria("");
	}

	public void limpiarRiesgo(ActionEvent e) {

		setNombreRiesgo("");
	}

	public void limpiarTipoDoc(ActionEvent e) {

		setTipoDocumento("");
	}

	public void limpiarTerritorio(ActionEvent e) {

		setCodTerritorio("");
		setNomTerritorio("");
		
		setIdGrupoBanca(0);
	}

	public void limpiarGrupoBanca(ActionEvent e) {

		setNomGrupoBanca("");
	}

	public void limpiarOficina(ActionEvent e) {

		setCodigoOficina("");
		setNomOficina("");
		setCodTerritorio("Seleccione");
		setIdUbigeo("Seleccione");
	}

	public void limpiarUbigeo(ActionEvent e) {

		setCodigoDepartamento("");
		setNomDepartamento("");
		setCodigoProvincia("");
		setNomProvincia("");
		setCodigoDistrito("");
		setNomDistrito("");
	}

	public void limpiarFeriado(ActionEvent e) {

		setIdOrganos(0);
		setIdUbigeo("Seleccione");
		setFechaInicio(null);
		setFechaFin(null);
		setIndFeriado('T');
	}

	public void limpiarEstados(ActionEvent e) {
		setNumDiasRojoEst1(0);
		setNumNaraEst1(0);
		setNumAmaEst1(0);
		setNumDiasRojoEst2(0);
		setNumNaraEst2(0);
		setNumAmaEst2(0);
		setNumDiasRojoEst3(0);
		setNumNaraEst3(0);
		setNumAmaEst3(0);
		setIdProcesoEstado(0);
		setIdActividad(0);
		setIdVia(0);
	}
	
	public void limpiarFiltrosEstados(ActionEvent e) {
		setIdProcesoEstado(0);
		setIdActividad(0);
		setIdVia(0);
	}

	private void cargarCombos() {

		// Carga Proceso
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);

		try {
			procesos = procesoDAO.buscarDinamico(filtroProceso);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de procesos");
		}

		// Carga Grupo Banca
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca = Busqueda.forClass(GrupoBanca.class);

		try {
			lstGrupoBanca = grupoBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de grupo banca");
		}

		// Carga Organos
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOrgano = Busqueda.forClass(Organo.class);

		try {
			lstOrgano = organoDAO.buscarDinamico(filtroOrgano);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de organos");
		}

		// Carga Ubigeos
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbigeo = Busqueda.forClass(Ubigeo.class);
		filtroUbigeo.addOrder(Order.asc("codDist"));

		try {
			lstUbigeo = ubiDAO.buscarDinamico(filtroUbigeo);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de ubigeos");
		}

		// Carga Territorio
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerr = Busqueda.forClass(Territorio.class);

		try {
			lstTerritorio = terrDAO.buscarDinamico(filtroTerr);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de territorio");
		}

		// Carga Vias
		GenericDao<Via, Object> viasDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class);

		try {
			lstVias = viasDAO.buscarDinamico(filtroVia);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de vias");
		}

		// Carga Actividades
		GenericDao<Actividad, Object> actDAO = (GenericDao<Actividad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAct = Busqueda.forClass(Actividad.class);

		try {
			lstActividad = actDAO.buscarDinamico(filtroAct);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de actividades");
		}
		//Carga Aviso
		GenericDao<Aviso, Object> avisDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAv = Busqueda.forClass(Aviso.class);
		
		try {
			lstAviso =  avisDAO.buscarDinamico(filtroAv);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de notificaciones");
		}
		
		//Carga Materia
		GenericDao<Materia, Object> matDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroMat = Busqueda.forClass(Materia.class);
		filtroMat.addOrder(Order.asc("idMateria"));
		
		try {
			lstMateria =  matDAO.buscarDinamico(filtroMat);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de materias");
		}
		
		//Carga Riesgos
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRiesgo = Busqueda.forClass(Riesgo.class);
		filtroRiesgo.addOrder(Order.asc("idRiesgo"));
		
		try {
			lstRiesgo =  riesgoDAO.buscarDinamico(filtroRiesgo);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de riesgos");
		}
		
		//Carga Tipos de Documento
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoDoc = Busqueda.forClass(TipoDocumento.class);
		filtroTipoDoc.addOrder(Order.asc("idTipoDocumento"));
		
		try {
			lstTipoDoc =  tipoDocDAO.buscarDinamico(filtroTipoDoc);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de tipos de documento");
		}
		
		//Carga Calificacion
		GenericDao<Calificacion, Object> califDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroCalif = Busqueda.forClass(Calificacion.class);
		filtroCalif.addOrder(Order.asc("idCalificacion"));
		
		try {
			lstCalificacion =  califDAO.buscarDinamico(filtroCalif);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de calificaciones");
		}
		
		//Carga Oficinas
		GenericDao<Oficina, Object> oficDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOfc= Busqueda.forClass(Oficina.class);
		filtroOfc.addOrder(Order.asc("idOficina"));
		
		try {
			lstOficina =  oficDAO.buscarDinamico(filtroOfc);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de oficinas");
		}
		
		//Carga Feriados
		GenericDao<Feriado, Object> ferDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroFer= Busqueda.forClass(Feriado.class);
		filtroFer.addOrder(Order.asc("idFeriado"));
		
		try {
			lstFeriado=  ferDAO.buscarDinamico(filtroFer);
		} catch (Exception e) {
			logger.debug("Error al cargar el listado de feriados");
		}
		
		setIndFeriado('T');
	}

	public void deleteMateria() {

		logger.debug("eliminando la materia... ");

		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Materia.class);
		
		getSelectedMateria().setEstado('I');
		
		try {
			materiaDAO.modificar(getSelectedMateria());
			logger.debug("elimino el proceso ");
			
			lstMateria= materiaDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("no elimino la materia");
		}

	}
	
	public void buscarMateria(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getNombreMateria());
		
		GenericDao<Materia, Object> matDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroMat = Busqueda.forClass(Materia.class);
		String filtroNuevo="%" + getNombreRiesgo().concat("%");
		filtroMat.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstMateria =  matDAO.buscarDinamico(filtroMat);
		} catch (Exception ex) {
			logger.debug("Error al buscar las materias");
		}
		//materias = new MateriaDataModel(lstMateria);			
		
	}
	
	public void editarMateria(RowEditEvent event)
	{
		Materia mat = ((Materia) event.getObject());
		logger.debug("modificando materia " + mat.getDescripcion());
		
		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			materiaDAO.modificar(mat);
			logger.debug("actualizo la materia exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la materia exitosamente");
		}
	}

	public Organo buscarOrgano(int idOrgano) {
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOrgano = Busqueda.forClass(Organo.class);
		filtroOrgano.add(Restrictions.eq("idOrgano", idOrgano));
		Organo tmpOrg = new Organo();

		try {
			lstOrgano = organoDAO.buscarDinamico(filtroOrgano);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Organo org : lstOrgano) {
			if (lstOrgano.size() == 1) {
				tmpOrg.setIdOrgano(org.getIdOrgano());
				tmpOrg.setNombre(org.getNombre());
			}
		}

		return tmpOrg;
	}

	public Via buscarViasPorProceso(ValueChangeEvent e) {
		logger.debug("Buscando vias por proceso: " + e.getNewValue());
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class).createAlias(
				"proceso", "pro");
		filtroVia.add(Restrictions.eq("pro.idProceso", e.getNewValue()));
		Via tmpVia = new Via();

		try {
			lstVias = viaDAO.buscarDinamico(filtroVia);
		} catch (Exception exp) {
			logger.debug("No se pudo encontrar las vias del proceso seleccionado");
		}

		for (Via via : lstVias) {
			if (lstVias.size() == 1) {
				tmpVia.setIdVia(via.getIdVia());
				tmpVia.setNombre(via.getNombre());
			}
		}

		return tmpVia;
	}

	public Ubigeo buscarUbigeo(String ubigeo) {
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbigeo = Busqueda.forClass(Ubigeo.class);
		filtroUbigeo.add(Restrictions.eq("codDist", ubigeo));
		Ubigeo tmpUbi = new Ubigeo();

		try {
			lstUbigeo = ubiDAO.buscarDinamico(filtroUbigeo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Ubigeo tmpUbigeo : lstUbigeo) {
			if (lstUbigeo.size() == 1) {
				tmpUbi.setCodDist(tmpUbigeo.getCodDist());
				tmpUbi.setDistrito(tmpUbigeo.getDistrito());
				tmpUbi.setCodDep(tmpUbigeo.getCodDep());
				tmpUbi.setDepartamento(tmpUbigeo.getDepartamento());
				tmpUbi.setCodProv(tmpUbigeo.getCodProv());
				tmpUbi.setProvincia(tmpUbigeo.getProvincia());
			}
		}

		return tmpUbi;
	}

	public GrupoBanca buscarGrupoBanca(int idGrupoBanca) {
		GenericDao<GrupoBanca, Object> gBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca = Busqueda.forClass(GrupoBanca.class);
		filtroGrupoBanca.add(Restrictions.eq("idGrupoBanca", idGrupoBanca));
		GrupoBanca tmpGBanca = new GrupoBanca();

		try {
			lstGrupoBanca = gBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (GrupoBanca tmpGrupoBanca : lstGrupoBanca) {
			if (lstGrupoBanca.size() == 1) {
				tmpGBanca.setIdGrupoBanca(tmpGrupoBanca.getIdGrupoBanca());
				tmpGBanca.setDescripcion(tmpGrupoBanca.getDescripcion());
			}
		}

		return tmpGBanca;
	}

	public Territorio buscarTerritorio(String codTerr) {
		GenericDao<Territorio, Object> ubiDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerritorio = Busqueda.forClass(Territorio.class);
		filtroTerritorio.add(Restrictions.eq("codigo", codTerr));
		Territorio tmpTerr = new Territorio();

		try {
			lstTerritorio = ubiDAO.buscarDinamico(filtroTerritorio);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Territorio terr : lstTerritorio) {
			if (lstTerritorio.size() == 1) {
				tmpTerr.setCodigo(terr.getCodigo());
				tmpTerr.setDescripcion(terr.getDescripcion());
			}
		}

		return tmpTerr;
	}

	public Proceso buscarProceso(int codProceso) {
		GenericDao<Proceso, Object> ubiDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);
		filtroProceso.add(Restrictions.eq("idProceso", codProceso));
		Proceso tmpProc = new Proceso();

		try {
			procesos = ubiDAO.buscarDinamico(filtroProceso);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Proceso pr : procesos) {
			if (procesos.size() == 1) {
				tmpProc.setIdProceso(pr.getIdProceso());
				tmpProc.setNombre(pr.getNombre());
			}
		}

		return tmpProc;
	}

	public Via buscarVia(int codVia) {
		GenericDao<Via, Object> ubiDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroVia = Busqueda.forClass(Via.class);
		filtroVia.add(Restrictions.eq("idVia", codVia));
		Via tmpVi = new Via();

		try {
			lstVias = ubiDAO.buscarDinamico(filtroVia);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Via vi : lstVias) {
			if (lstVias.size() == 1) {
				tmpVi.setIdVia(vi.getIdVia());
				tmpVi.setNombre(vi.getNombre());
			}
		}

		return tmpVi;
	}

	public Actividad buscarActividad(int codAct) {
		GenericDao<Actividad, Object> actDAO = (GenericDao<Actividad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAct = Busqueda.forClass(Actividad.class);
		filtroAct.add(Restrictions.eq("idActividad", codAct));
		Actividad tmpAct = new Actividad();

		try {
			lstActividad = actDAO.buscarDinamico(filtroAct);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Actividad act : lstActividad) {
			if (lstActividad.size() == 1) {
				tmpAct.setIdActividad(act.getIdActividad());
				tmpAct.setNombre(act.getNombre());
			}
		}

		return tmpAct;
	}

	public void agregarMateria(ActionEvent e) 
	{
		List<Materia> materias = new ArrayList<Materia>();
		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Materia.class);
		Busqueda filtro2 = Busqueda.forClass(Materia.class);

		
		if ( getNombreMateria().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreMateria()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				materias = materiaDAO.buscarDinamico(filtro);

				if (materias.size() == 0) 
				{
					Materia mat = new Materia();
					mat.setDescripcion(getNombreMateria());
					mat.setEstado('A');
					
					try {
						materiaDAO.insertar(mat);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego la materia"));
						logger.debug("guardo la materia exitosamente");
						
						lstMateria = materiaDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego la materia"));
						logger.debug("no guardo la materia por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Materia Existente", "Materia Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si materia existe en BD");
			}
		}
	}
	
	public void agregarNotificacion(ActionEvent e) {
		/*GenericDao<Aviso, Object> avisoDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		int numDiasRojoEst1 = getNumDiasRojoEst1();
		int numDiasAmaEst1 = getNumAmaEst1();
		int numDiasNaraEst1 = getNumNaraEst1();
		
		int numDiasRojoEst2 = getNumDiasRojoEst2();
		int numDiasAmaEst2 = getNumAmaEst2();
		int numDiasNaraEst2 = getNumNaraEst2();
		
		int numDiasRojoEst3 = getNumDiasRojoEst3();
		int numDiasAmaEst3 = getNumAmaEst3();
		int numDiasNaraEst3 = getNumNaraEst3();
		
		Aviso avis = new Aviso();
		if (getIdProcesoEstado()!=0)
		{
			avis.setProceso(buscarProceso(getIdProcesoEstado()));
		}
		else
		{
			avis.setProceso(null);
		}
		
		if (getIdVia()!=0)
		{
			avis.setVia(buscarVia(getIdVia()));
		}
		else
		{
			avis.setVia(null);
		}
		
		if (getIdActividad()!=0)
		{
			avis.setActividad(buscarActividad(getIdActividad()));
		}
		else
		{
			avis.setActividad(null);
		}
		
		avis.setColor('R');
		avis.setDias(numDiasRojoEst1);
		
		try {
			avisoDAO.insertar(avis);
			FacesContext.getCurrentInstance().addMessage("msjResul",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
			logger.debug("guardo configuracion de notificaciones");

		} catch (Exception ex) {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							"msjResul",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"No Exitoso",
									"No Agrego la configuracion de notificaciones"));
			logger.debug("no guardo la configuracion de notificaciones por "
					+ ex.getMessage());
		}*/
		
		
		/*if (idProcesoEstado != 0) 
		{
			logger.debug("Buscando configuracion para proceso: "+ idProcesoEstado);

			int numDiasRojoEst1 = getNumDiasRojoEst1();
			int numDiasAmaEst1 = getNumAmaEst1();
			int numDiasNaraEst1 = getNumNaraEst1();

			logger.debug("Numero de dias en rojo: " + numDiasRojoEst1);
			logger.debug("Numero de dias en amarillo: " + numDiasAmaEst1);
			logger.debug("Numero de dias en naranja: " + numDiasNaraEst1);

			if (numDiasRojoEst1 < numDiasNaraEst1 && numDiasNaraEst1 < numDiasAmaEst1) 
			{
				Aviso avis = new Aviso();
				avis.setProceso(buscarProceso(getIdProcesoEstado()));
				avis.setColor('R');
				avis.setDias(numDiasRojoEst1);

				try {
					avisoDAO.insertar(avis);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Exitoso",
									"Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msjResul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso",
											"No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "
							+ ex.getMessage());
				}

				Aviso avis2 = new Aviso();
				avis2.setProceso(buscarProceso(getIdProcesoEstado()));
				avis2.setColor('N');
				avis2.setDias(numDiasNaraEst1);

				try {
					avisoDAO.insertar(avis2);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",	new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage("msjResul",	new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "+ ex.getMessage());
				}

				Aviso avis3 = new Aviso();
				avis3.setProceso(buscarProceso(getIdProcesoEstado()));
				avis3.setColor('A');
				avis3.setDias(numDiasAmaEst1);

				try {
					avisoDAO.insertar(avis3);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",	new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage("msjResul",	new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por " + ex.getMessage());
				}

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msjResul",	new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso",
										"No agrego configuracion de notificaciones debido a una mala configuracion de parametros"));
				logger.debug("No agrego configuracion de notificaciones debido a una mala configuracion de parametros");
			}

		}

		if (idVia != 0) {
			logger.debug("Buscando configuracion para via: " + idVia);

			int numDiasRojoEst2 = getNumDiasRojoEst2();
			int numDiasAmaEst2 = getNumAmaEst2();
			int numDiasNaraEst2 = getNumNaraEst2();

			logger.debug("Numero de dias en rojo: " + numDiasRojoEst2);
			logger.debug("Numero de dias en amarillo: " + numDiasAmaEst2);
			logger.debug("Numero de dias en naranja: " + numDiasNaraEst2);

			if (numDiasRojoEst2 < numDiasNaraEst2 && numDiasNaraEst2 < numDiasAmaEst2) 
			{
				Aviso avis = new Aviso();
				avis.setVia(buscarVia(getIdVia()));
				avis.setColor('R');
				avis.setDias(numDiasRojoEst2);

				try {
					avisoDAO.insertar(avis);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",	new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msjResul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso",
											"No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "
							+ ex.getMessage());
				}

				Aviso avis2 = new Aviso();
				avis2.setVia(buscarVia(getIdVia()));
				avis2.setColor('N');
				avis2.setDias(numDiasNaraEst2);

				try {
					avisoDAO.insertar(avis2);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Exitoso",
									"Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msjResul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso",
											"No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "
							+ ex.getMessage());
				}

				Aviso avis3 = new Aviso();
				avis3.setVia(buscarVia(getIdVia()));
				avis3.setColor('A');
				avis3.setDias(numDiasAmaEst2);

				try {
					avisoDAO.insertar(avis3);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Exitoso",
									"Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msjResul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso",
											"No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "
							+ ex.getMessage());
				}

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msjResul",
								new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										"Exitoso",
										"No agrego configuracion de notificaciones debido a una mala configuracion de parametros"));
				logger.debug("No agrego configuracion de notificaciones debido a una mala configuracion de parametros");
			}

		}

		if (idActividad != 0) {
			logger.debug("Buscando configuracion para actividad: "
					+ idActividad);

			int numDiasRojoEst3 = getNumDiasRojoEst3();
			int numDiasAmaEst3 = getNumAmaEst3();
			int numDiasNaraEst3 = getNumNaraEst3();

			logger.debug("Numero de dias en rojo: " + numDiasRojoEst3);
			logger.debug("Numero de dias en amarillo: " + numDiasAmaEst3);
			logger.debug("Numero de dias en naranja: " + numDiasNaraEst3);

			if (numDiasRojoEst3 < numDiasNaraEst3
					&& numDiasNaraEst3 < numDiasAmaEst3) {
				Aviso avis = new Aviso();
				avis.setActividad(buscarActividad(getIdActividad()));
				avis.setColor('R');
				avis.setDias(numDiasRojoEst3);

				try {
					avisoDAO.insertar(avis);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Exitoso",
									"Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msjResul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso",
											"No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "
							+ ex.getMessage());
				}

				Aviso avis2 = new Aviso();
				avis2.setActividad(buscarActividad(getIdActividad()));
				avis2.setColor('N');
				avis2.setDias(numDiasNaraEst3);

				try {
					avisoDAO.insertar(avis2);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Exitoso",
									"Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msjResul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso",
											"No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "
							+ ex.getMessage());
				}

				Aviso avis3 = new Aviso();
				avis3.setActividad(buscarActividad(getIdActividad()));
				avis3.setColor('A');
				avis3.setDias(numDiasAmaEst3);

				try {
					avisoDAO.insertar(avis3);
					FacesContext.getCurrentInstance().addMessage(
							"msjResul",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Exitoso",
									"Agrego configuracion de notificaciones"));
					logger.debug("guardo configuracion de notificaciones");

				} catch (Exception ex) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"msjResul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"No Exitoso",
											"No Agrego la configuracion de notificaciones"));
					logger.debug("no guardo la configuracion de notificaciones por "
							+ ex.getMessage());
				}

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"msjResul",
								new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										"Exitoso",
										"No agrego configuracion de notificaciones debido a una mala configuracion de parametros"));
				logger.debug("No agrego configuracion de notificaciones debido a una mala configuracion de parametros");
			}

		}*/

	}
	
	public void editarNotificacion(RowEditEvent event)
	{
		Aviso av = ((Aviso) event.getObject());
		logger.debug("modificando aviso para la actividad: " + av.getActividad().getNombre());
		
		GenericDao<Aviso, Object> avDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			avDAO.modificar(av);
			logger.debug("actualizo el aviso exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el aviso exitosamente");
		}
	}
	
	public void deleteRiesgo() 
	{
		logger.debug("eliminando el riesgo... ");

		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Riesgo.class);
		
		getSelectedRiesgo().setEstado('I');
		
		try {
			riesgoDAO.modificar(getSelectedRiesgo());
			logger.debug("elimino el riesgo ");
			
			lstRiesgo= riesgoDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("no elimino el riesgo");
		}

	}
	
	public void buscarRiesgo(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getNombreRiesgo());
		
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRiesgo = Busqueda.forClass(Riesgo.class);
		String filtroNuevo="%" + getNombreRiesgo().concat("%");
		filtroRiesgo.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstRiesgo =  riesgoDAO.buscarDinamico(filtroRiesgo);
		} catch (Exception ex) {
			logger.debug("Error al buscar los riesgos");
		}
	}
	
	public void editarRiesgo(RowEditEvent event)
	{
		Riesgo riesgo = ((Riesgo) event.getObject());
		logger.debug("modificando riesgo " + riesgo.getDescripcion());
		
		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			riesgoDAO.modificar(riesgo);
			logger.debug("actualizo el riesgo exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el riesgo exitosamente");
		}
	}
	
	public void agregarRiesgo(ActionEvent e) 
	{
		List<Riesgo> riesgos = new ArrayList<Riesgo>();
		GenericDao<Riesgo, Object> riesgosDAO = (GenericDao<Riesgo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Riesgo.class);
		Busqueda filtro2 = Busqueda.forClass(Riesgo.class);

		
		if ( getNombreRiesgo().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreRiesgo()).ignoreCase());
				
				riesgos = riesgosDAO.buscarDinamico(filtro);

				if (riesgos.size() == 0) 
				{
					Riesgo riesg = new Riesgo();
					riesg.setDescripcion(getNombreRiesgo());
					riesg.setEstado('A');
					
					try {
						riesgosDAO.insertar(riesg);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego el riesgo"));
						logger.debug("guardo el riesgo exitosamente");
						
						lstRiesgo = riesgosDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego el riesgo"));
						logger.debug("no guardo el riesgo por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Riesgo Existente", "Riesgo Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si riesgo existe en BD");
			}
		}
	}

	public void agregarTipoDocumento(ActionEvent e) {
		List<TipoDocumento> tipoDocs = new ArrayList<TipoDocumento>();
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(TipoDocumento.class);
		Busqueda filtro2 = Busqueda.forClass(TipoDocumento.class);

		
		if ( getTipoDocumento().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Descripcion", "Datos Requeridos: Descripcion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getTipoDocumento()).ignoreCase());
				
				tipoDocs = tipoDocDAO.buscarDinamico(filtro);

				if (tipoDocs.size() == 0) 
				{
					TipoDocumento tipoDoc = new TipoDocumento();
					tipoDoc.setDescripcion(getTipoDocumento());
					tipoDoc.setEstado('A');
					
					try {
						tipoDocDAO.insertar(tipoDoc);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego el tipo de documento"));
						logger.debug("guardo el tipo de documento exitosamente");
						
						lstTipoDoc = tipoDocDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego el tipo de documento"));
						logger.debug("no guardo el tipo de documento por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Tipo de Documento Existente", "Tipo de Documento Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si tipo de documento existe en BD");
			}
		}
	}
	
	public void deleteTipoDocumento() 
	{
		logger.debug("eliminando el tipo de documento... ");

		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(TipoDocumento.class);
		
		getSelectedTipoDoc().setEstado('I');
		
		try {
			tipoDocDAO.modificar(getSelectedTipoDoc());
			logger.debug("elimino el tipo de documento");
			
			lstTipoDoc= tipoDocDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			logger.debug("no elimino el tipo de documento");
		}

	}
	
	public void buscarTipoDoc(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getTipoDocumento());
		
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoDoc = Busqueda.forClass(TipoDocumento.class);
		String filtroNuevo="%" + getTipoDocumento().concat("%");
		filtroTipoDoc.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstTipoDoc =  tipoDocDAO.buscarDinamico(filtroTipoDoc);
		} catch (Exception ex) {
			logger.debug("Error al buscar el tipo de documento");
		}
	}
	
	public void editarTipoDoc(RowEditEvent event)
	{
		TipoDocumento tipoDoc = ((TipoDocumento) event.getObject());
		logger.debug("modificando tipo de documento " + tipoDoc.getDescripcion());
		
		GenericDao<TipoDocumento, Object> tipoDocDAO = (GenericDao<TipoDocumento, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoDocDAO.modificar(tipoDoc);
			logger.debug("actualizo el tipo de documento exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el tipo de documento exitosamente");
		}
	}

	public void limpiarTipoDocumento(ActionEvent e) {
		setTipoDocumento("");

	}

	public void agregarCalificacion(ActionEvent e) 
	{
		List<Calificacion> califs = new ArrayList<Calificacion>();
		GenericDao<Calificacion, Object> califDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Calificacion.class);
		Busqueda filtro2 = Busqueda.forClass(Calificacion.class);

		
		if ( getDescrCalificacion().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre Calificacion", "Datos Requeridos: Nombre Calificacion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getDescrCalificacion()).ignoreCase());
				
				califs = califDAO.buscarDinamico(filtro);

				if (califs.size() == 0) 
				{
					Calificacion calif = new Calificacion();
					calif.setNombre(getDescrCalificacion());
					calif.setEstado('A');
					
					try {
						califDAO.insertar(calif);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego la calificacion"));
						logger.debug("guardo la calificacion exitosamente");
						
						lstCalificacion = califDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego la calificacion"));
						logger.debug("no guardo la calificacion por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Calificacion Existente", "Calificacion Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si calificacion existe en BD");
			}
		}
	}
	
	public void deleteCalificacion() 
	{
		logger.debug("eliminando calificacion... ");

		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Calificacion.class);
		
		getSelectedCalificacion().setEstado('I');
		
		try {
			calificacionDAO.modificar(getSelectedCalificacion());
			logger.debug("elimino calificacion");
			
			lstCalificacion= calificacionDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			logger.debug("no elimino calificacion");
		}

	}
	
	public void buscarCalificacion(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getDescrCalificacion());
		
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroCalif = Busqueda.forClass(Calificacion.class);
		String filtroNuevo="%" + getDescrCalificacion().concat("%");
		filtroCalif.add(Restrictions.sqlRestriction("lower({alias}.nombre) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstCalificacion =  calificacionDAO.buscarDinamico(filtroCalif);
		} catch (Exception ex) {
			logger.debug("Error al buscar calificacion");
		}
	}
	
	public void editarCalificacion(RowEditEvent event)
	{
		Calificacion calif = ((Calificacion) event.getObject());
		logger.debug("modificando calificacion: " + calif.getNombre());
		
		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			calificacionDAO.modificar(calif);
			logger.debug("actualizo calificacion exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo calificacion exitosamente");
		}
	}

	public void limpiarCalificacion(ActionEvent e) {

		setDescrCalificacion("");

	}

	public void agregarFormaConclusion(ActionEvent e) {
		GenericDao<FormaConclusion, Object> formaDAO = (GenericDao<FormaConclusion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		FormaConclusion fc = new FormaConclusion();
		fc.setDescripcion(getDescrFormaConclusion());

		try {
			formaDAO.insertar(fc);
			FacesContext.getCurrentInstance().addMessage(
					"msjResul",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
							"Agrego la forma de conclusion"));
			logger.debug("guardo la forma de conclusion exitosamente");

		} catch (Exception ex) {

			FacesContext.getCurrentInstance().addMessage(
					"msjResul",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
							"No Agrego la forma de conclusion"));
			logger.debug("no guardo la forma de conclusion por "
					+ ex.getMessage());
		}
	}

	public void limpiarFormaConclusion(ActionEvent e) {
		setDescrFormaConclusion("");
	}

	public void agregarGrupoBanca(ActionEvent e) {
		List<GrupoBanca> gb = new ArrayList<GrupoBanca>();
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(GrupoBanca.class);
		Busqueda filtro2 = Busqueda.forClass(GrupoBanca.class);

		
		if ( getNomGrupoBanca().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Descripcion", "Datos Requeridos: Descripcion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNomGrupoBanca()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				gb = grupoBancaDAO.buscarDinamico(filtro);

				if (gb.size() == 0) 
				{
					GrupoBanca gBanca = new GrupoBanca();
					gBanca.setDescripcion(getNomGrupoBanca());
					gBanca.setEstado('A');
					
					try {
						grupoBancaDAO.insertar(gBanca);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego grupo banca"));
						logger.debug("guardo grupo banca exitosamente");
						
						lstGrupoBanca = grupoBancaDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego grupo banca"));
						logger.debug("no guardo grupo banca por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Grupo Banca Existente", "Grupo Banca Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si grupo banca existe en BD");
			}
		}
	}
	
	public void deleteGrupoBanca() 
	{
		logger.debug("eliminando grupo banca... ");

		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(GrupoBanca.class);
		
		getSelectedGrupoBanca().setEstado('I');
		
		try {
			grupoBancaDAO.modificar(getSelectedGrupoBanca());
			logger.debug("elimino grupo banca");
			
			lstGrupoBanca= grupoBancaDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			logger.debug("no elimino grupo banca");
		}

	}
	
	public void buscarGrupoBanca(ActionEvent e)
	{
		logger.debug("Parametro a buscar: " + getNomGrupoBanca());
		
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroGrupoBanca= Busqueda.forClass(GrupoBanca.class);
		String filtroNuevo="%" + getNomGrupoBanca().concat("%");
		filtroGrupoBanca.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		
		try {
			lstGrupoBanca =  grupoBancaDAO.buscarDinamico(filtroGrupoBanca);
		} catch (Exception ex) {
			logger.debug("Error al buscar grupo banca");
		}
	}
	
	public void editarGrupoBanca(RowEditEvent event)
	{
		GrupoBanca gb = ((GrupoBanca) event.getObject());
		logger.debug("modificando grupo banca: " + gb.getDescripcion());
		
		GenericDao<GrupoBanca, Object> grupoBancaDAO = (GenericDao<GrupoBanca, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			grupoBancaDAO.modificar(gb);
			logger.debug("actualizo grupo banca exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo grupo banca exitosamente");
		}
	}

	public void agregarTerritorio(ActionEvent e) {
		List<Territorio> terr = new ArrayList<Territorio>();
		GenericDao<Territorio, Object> terraDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Territorio.class);
		Busqueda filtro2 = Busqueda.forClass(Territorio.class);

		
		if ( getNomTerritorio().compareTo("") == 0 || getCodTerritorio().compareTo("")==0 || getIdGrupoBanca()==0) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Codigo Territorio, Descripcion, Grupo Banca", "Datos Requeridos: Codigo Territorio, Descripcion, Grupo Banca");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNomTerritorio()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				terr = terraDAO.buscarDinamico(filtro);

				if (terr.size() == 0) 
				{
					Territorio terri = new Territorio();
					terri.setCodigo(getCodTerritorio());
					terri.setDescripcion(getNomTerritorio());
					terri.setGrupoBanca(buscarGrupoBanca(getIdGrupoBanca()));
					terri.setEstado('A');
					
					try {
						terraDAO.insertar(terri);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego territorio"));
						logger.debug("guardo territorio exitosamente");
						
						lstTerritorio = terraDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego territorio"));
						logger.debug("no guardo territorio por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Territorio Existente", "Territorio Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si territorio existe en BD");
			}
		}
		
	}
	
	public void deleteTerritorio() 
	{
		logger.debug("eliminando territorio... ");

		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Territorio.class);
		
		getSelectedTerritorio().setEstado('I');
		
		try {
			terrDAO.modificar(getSelectedTerritorio());
			logger.debug("elimino territorio");
			
			lstTerritorio= terrDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			logger.debug("no elimino territorio");
		}

	}
	
	public void busquedaTerritorio(ActionEvent e)
	{
		logger.debug("Parametro a buscar IdTerritorio: " + getCodTerritorio());
		logger.debug("Parametro a buscar Territorio: " + getNomTerritorio());
		logger.debug("Parametro a buscar Grupo_Banca:" + getIdGrupoBanca());	
		
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTerr= Busqueda.forClass(Territorio.class);
		
		if (getNomTerritorio().compareTo("")!=0)
		{
			String filtroNuevo="%" + getNomTerritorio().concat("%");
			filtroTerr.add(Restrictions.sqlRestriction("lower({alias}.descripcion) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodTerritorio().compareTo("")!=0)
		{
			filtroTerr.add(Restrictions.eq("codigo", getCodTerritorio()));
		}
		
		if (getIdGrupoBanca()!=0)
		{
			filtroTerr.createAlias("grupoBanca", "gb");
			filtroTerr.add(Restrictions.eq("gb.idGrupoBanca", getIdGrupoBanca()));
		}
		
		try {
			lstTerritorio =  terrDAO.buscarDinamico(filtroTerr);
		} catch (Exception ex) {
			logger.debug("Error al buscar territorio");
		}
	}
	
	public void busquedaNotificacion(ActionEvent e)
	{
		GenericDao<Aviso, Object> avisDAO = (GenericDao<Aviso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroAv= Busqueda.forClass(Aviso.class);
		
		if (getIdProcesoEstado()!=0)
		{
			filtroAv.createAlias("proceso", "pro");
			filtroAv.add(Restrictions.eq("pro.idProceso", getIdProcesoEstado()));
		}
		
		if (getIdVia()!=0)
		{
			filtroAv.createAlias("via", "vi");
			filtroAv.add(Restrictions.eq("vi.idVia", getIdVia()));
		}
		
		if (getIdActividad()!=0)
		{
			filtroAv.createAlias("actividad", "act");
			filtroAv.add(Restrictions.eq("act.idActividad", getIdActividad()));
		}
		
		try {
			lstAviso =  avisDAO.buscarDinamico(filtroAv);
		} catch (Exception ex) {
			logger.debug("Error al buscar notificaciones");
		}
	}
	
	public void editarTerritorio(RowEditEvent event)
	{
		Territorio terr = ((Territorio) event.getObject());
		logger.debug("modificando territorio: " + terr.getDescripcion());
		
		GenericDao<Territorio, Object> terrDAO = (GenericDao<Territorio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			terrDAO.modificar(terr);
			logger.debug("actualizo territorio exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo territorio exitosamente");
		}
	}

	public void agregarFeriado(ActionEvent e) 
	{
		List<Feriado> fer = new ArrayList<Feriado>();
		GenericDao<Feriado, Object> ferDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Feriado.class);
		Busqueda filtro2 = Busqueda.forClass(Feriado.class);
		
		logger.debug("Parametros a grabar:");
		logger.debug("Fecha Inicio: " + getFechaInicio());
		logger.debug("Fecha Fin: " + getFechaFin());
		logger.debug("Ubigeo: " + getIdUbigeo());
		
		if ( getFechaInicio().equals(null)|| getFechaFin().equals(null) || getIdUbigeo().compareTo("")==0) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Fecha Inicio, Fecha Fin, Ubigeo", "Datos Requeridos: Fecha Inicio, Fecha Fin, Ubigeo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {
				
				/*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String tmpFechaInicio=dateFormat.format(getFechaInicio());
				logger.debug("Nueva fecha a buscar: " + tmpFechaInicio);*/
				filtro.add(Restrictions.eq("fechaInicio", getFechaInicio()));
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				fer = ferDAO.buscarDinamico(filtro);

				if (fer.size() == 0) 
				{
					if (!getIndFeriado().equals('T') && !getIndFeriado().equals('X'))
					{
						Feriado tmpFer = new Feriado();
						tmpFer.setFechaInicio(getFechaInicio());
						tmpFer.setFechaFin(getFechaFin());
						tmpFer.setEstado('A');
						
						if (getIdOrganos()!=0)
						{
							tmpFer.setOrgano(buscarOrgano(getIdOrganos()));
							tmpFer.setTipo('O');
							
							if (getIndFeriado().equals('N'))
							{
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Los organos deban grabarse como feriados locales no nacionales. Se grabaran los datos como feriado local", "Mensaje");
								FacesContext.getCurrentInstance().addMessage(null, msg);
								tmpFer.setIndicador('L');
							}
							else
							{
								tmpFer.setIndicador(getIndFeriado());
							}
							tmpFer.setUbigeo(buscarUbigeo(getIdUbigeo()));
						}
						else
						{
							tmpFer.setTipo('C');
							tmpFer.setOrgano(null);
							tmpFer.setIndicador(getIndFeriado());
							
							if (getIndFeriado().equals('N') && getIdOrganos()==0)
							{
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"No es necesario grabar ubigeo cuando es feriado nacional. Se grabaran los datos sin ubigeo", "Mensaje");
								FacesContext.getCurrentInstance().addMessage(null, msg);
							}
							else
							{
								tmpFer.setUbigeo(buscarUbigeo(getIdUbigeo()));
							}
						}
						
						try {
							ferDAO.insertar(tmpFer);
							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego feriado"));
							logger.debug("guardo feriado exitosamente");
							
							lstFeriado = ferDAO.buscarDinamico(filtro2);
							//procesoDataModel = new ProcesoDataModel(procesos);

						} catch (Exception ex) {

							FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego feriado"));
							logger.debug("no guardo feriado por " + ex.getMessage());
						}
					}

				} else {
					logger.debug("Entro al ELSE");
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Feriado Existente", "Feriado Existente"));
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.debug("Error al buscar si feriado existe en BD");
			}
		}
	}
	
	public void deleteFeriado() 
	{
		logger.debug("eliminando feriado... ");

		GenericDao<Feriado, Object> ferDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Feriado.class);
		
		getSelectedFeriado().setEstado('I');
		
		try {
			ferDAO.modificar(getSelectedFeriado());
			logger.debug("elimino feriado");
			
			lstFeriado= ferDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			logger.debug("no elimino feriado");
		}

	}
	
	public void busquedaFeriado(ActionEvent e)
	{
		GenericDao<Feriado, Object> ubiDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroFer= Busqueda.forClass(Feriado.class);
		
		logger.debug("Parametro a buscar: " + getIndFeriado());
		logger.debug("Parametro a buscar2: " + getFechaInicio());
		logger.debug("Parametro a buscar3: " + getFechaFin());
		logger.debug("Parametro a buscar4: " + getIdUbigeo());
		
		if (getFechaInicio()!=null)
		{
			logger.debug("Entro 1");
			filtroFer.add(Restrictions.eq("fechaInicio", getFechaInicio()));
		}
		
		if (getFechaFin()!=null)
		{
			logger.debug("Entro 2");
			filtroFer.add(Restrictions.eq("fechaFin", getFechaFin()));
		}
		
		if (!getIndFeriado().equals('X'))
		{
			logger.debug("Entro 3");
			if (!getIndFeriado().equals('T'))
			{
				filtroFer.add(Restrictions.eq("indicador", getIndFeriado()));
			}
		}
		
		if (getIdUbigeo().compareTo("")!=0)
		{
			logger.debug("Entro 4");
			filtroFer.createAlias("ubigeo", "ubi");
			filtroFer.add(Restrictions.eq("ubi.codDist", getIdUbigeo()));
		}
		
		try {
			lstFeriado=  ubiDAO.buscarDinamico(filtroFer);
		} catch (Exception ex) {
			logger.debug("Error al buscar feriado");
		}
	}
	
	public void editarFeriado(RowEditEvent event)
	{
		Feriado fer = ((Feriado) event.getObject());
		logger.debug("modificando feriado: " + fer.getFechaInicio());
		
		GenericDao<Feriado, Object> ferDAO = (GenericDao<Feriado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			ferDAO.modificar(fer);
			logger.debug("actualizo feriado exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo feriado exitosamente");
		}
	}

	public void agregarOficina(ActionEvent e) 
	{
		List<Oficina> ofi = new ArrayList<Oficina>();
		GenericDao<Oficina, Object> ofiDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Oficina.class);
		Busqueda filtro2 = Busqueda.forClass(Oficina.class);
		
		if ( getNomOficina().compareTo("") == 0 || getCodigoOficina().compareTo("")==0 || getIdUbigeo().compareTo("")==0) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Codigo Oficina, Nombre, Ubigeo", "Datos Requeridos: Codigo Oficina, Nombre, Ubigeo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNomOficina()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				ofi = ofiDAO.buscarDinamico(filtro);

				if (ofi.size() == 0) 
				{
					Oficina ofic = new Oficina();
					ofic.setCodigo(getCodigoOficina());
					ofic.setNombre(getNomOficina());
					if (getCodTerritorio()!=null)
					{
						ofic.setTerritorio(buscarTerritorio(getCodTerritorio()));
					}
					else
					{
						ofic.setTerritorio(null);
					}
					ofic.setUbigeo(buscarUbigeo(getIdUbigeo()));
					ofic.setEstado('A');
					
					try {
						ofiDAO.insertar(ofic);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego oficina"));
						logger.debug("guardo oficina exitosamente");
						
						lstOficina = ofiDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego oficina"));
						logger.debug("no guardo oficina por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Oficina Existente", "Oficina Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si oficina existe en BD");
			}
		}
	}
	
	public void deleteOficina() 
	{
		logger.debug("eliminando oficina... ");

		GenericDao<Oficina, Object> oficDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Oficina.class);
		
		getSelectedOficina().setEstado('I');
		
		try {
			oficDAO.modificar(getSelectedOficina());
			logger.debug("elimino oficina");
			
			lstOficina= oficDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			logger.debug("no elimino oficina");
		}

	}
	
	public void busquedaOficina(ActionEvent e)
	{
		GenericDao<Oficina, Object> oficDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroOfi= Busqueda.forClass(Oficina.class);
		
		if (getNomOficina().compareTo("")!=0)
		{
			String filtroNuevo="%" + getNomOficina().concat("%");
			filtroOfi.add(Restrictions.sqlRestriction("lower({alias}.nombre) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodigoOficina().compareTo("")!=0)
		{
			filtroOfi.add(Restrictions.eq("codigo", getCodigoOficina()));
		}
		
		if (getCodTerritorio().compareTo("")!=0)
		{
			filtroOfi.createAlias("territorio", "terr");
			logger.debug("Codigo Territorio:" + getCodTerritorio());
			filtroOfi.add(Restrictions.eq("terr.codigo", getCodTerritorio()));
		}
		
		if (getIdUbigeo().compareTo("")!=0)
		{
			filtroOfi.createAlias("ubigeo", "ubi");
			logger.debug("Codigo Ubigeo:" + getIdUbigeo());
			filtroOfi.add(Restrictions.eq("ubi.codDist", getIdUbigeo()));
		}
		
		try {
			lstOficina =  oficDAO.buscarDinamico(filtroOfi);
		} catch (Exception ex) {
			logger.debug("Error al buscar oficina");
		}
	}
	
	//OJO!!!!!!!! No graba cambio de Ubigeo cuando se edita una oficina
	public void editarOficina(RowEditEvent event)
	{
		Oficina ofi = ((Oficina) event.getObject());
		logger.debug("modificando oficina: " + ofi.getNombre());
		logger.debug("codigo ubigeo: " + ofi.getUbigeo().getCodDist());
		
		GenericDao<Oficina, Object> ofiDAO = (GenericDao<Oficina, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			ofiDAO.modificar(ofi);
			logger.debug("actualizo oficina exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo oficina exitosamente");
		}
	}
	

	public void agregarUbigeo(ActionEvent e) 
	{
		List<Ubigeo> ubi = new ArrayList<Ubigeo>();
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Ubigeo.class);
		Busqueda filtro2 = Busqueda.forClass(Ubigeo.class);

		
		if ( getCodigoDistrito().compareTo("") == 0 || getNomDistrito().compareTo("")==0 || getCodigoProvincia().compareTo("")==0 ||
			 getNomProvincia().compareTo("")==0 || getCodigoDepartamento().compareTo("")==0 || getNomDepartamento().compareTo("")==0 ) 
		{
			
			FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,"Datos Requeridos: " +
								"Codigo Distrito, Distrito, Codigo Provincia, Provincia, Codigo Departamento, Departamento", 
								"Datos Requeridos: Codigo Distrito, Distrito, Codigo Provincia, Provincia, Codigo Departamento, Departamento");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else
		{
			try {

				filtro.add(Restrictions.eq("codDist", getCodigoDistrito()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				ubi = ubiDAO.buscarDinamico(filtro);

				if (ubi.size() == 0) 
				{
					Ubigeo ubig = new Ubigeo();
					ubig.setCodDist(getCodigoDistrito());
					ubig.setDistrito(getNomDistrito());
					ubig.setCodDep(getCodigoDepartamento());
					ubig.setDepartamento(getNomDepartamento());
					ubig.setCodProv(getCodigoProvincia());
					ubig.setProvincia(getNomProvincia());
					ubig.setEstado('A');
					
					try {
						ubiDAO.insertar(ubig);
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Agrego ubigeo"));
						logger.debug("guardo ubigeo exitosamente");
						
						lstUbigeo = ubiDAO.buscarDinamico(filtro2);
						//procesoDataModel = new ProcesoDataModel(procesos);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso", "No Agrego ubigeo"));
						logger.debug("no guardo ubigeo por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Ubigeo Existente", "Ubigeo Existente"));
				}

			} catch (Exception ex) {
				logger.debug("Error al buscar si ubigeo existe en BD");
			}
		}
	}
	
	public void deleteUbigeo() 
	{
		logger.debug("eliminando ubigeo... ");

		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Ubigeo.class);
		
		getSelectedUbigeo().setEstado('I');
		
		try {
			ubiDAO.modificar(getSelectedUbigeo());
			logger.debug("elimino ubigeo");
			
			lstUbigeo= ubiDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			logger.debug("no elimino ubigeo");
		}

	}
	
	public void busquedaUbigeo(ActionEvent e)
	{
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtroUbi= Busqueda.forClass(Ubigeo.class);
		
		if (getCodigoDistrito().compareTo("")!=0)
		{
			String filtroNuevo="%" + getCodigoDistrito().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.cod_dist) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getNomDistrito().compareTo("")!=0)
		{
			String filtroNuevo="%" + getNomDistrito().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.distrito) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodigoProvincia().compareTo("")!=0)
		{
			String filtroNuevo="%" + getCodigoProvincia().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.cod_prov) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getNomProvincia().compareTo("")!=0)
		{
			String filtroNuevo="%" + getNomProvincia().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.provincia) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getCodigoDepartamento().compareTo("")!=0)
		{
			String filtroNuevo="%" + getCodigoDepartamento().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.cod_dep) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		if (getNomDepartamento().compareTo("")!=0)
		{
			String filtroNuevo="%" + getNomDepartamento().concat("%");
			filtroUbi.add(Restrictions.sqlRestriction("lower({alias}.departamento) like lower(?)", filtroNuevo, Hibernate.STRING) );
		}
		
		try {
			lstUbigeo =  ubiDAO.buscarDinamico(filtroUbi);
		} catch (Exception ex) {
			logger.debug("Error al buscar ubigeo");
		}
	}
	
	public void editarUbigeo(RowEditEvent event)
	{
		Ubigeo ubi = ((Ubigeo) event.getObject());
		logger.debug("modificando ubigeo: " + ubi.getDescripcionDistrito());
		
		GenericDao<Ubigeo, Object> ubiDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			ubiDAO.modificar(ubi);
			logger.debug("actualizo ubigeo exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo ubigeo exitosamente");
		}
	}

	public void buscarProceso(ActionEvent e) {

		logger.debug("entro al buscar proceso");

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Proceso.class);

		if (getNombreProceso().compareTo("") != 0) {

			logger.debug("filtro " + getNombreProceso() + " proceso - nombre");
			filtro.add(Restrictions.like("nombre",
					"%" + getNombreProceso() + "%").ignoreCase());
		}

		if (getAbrevProceso().compareTo("") != 0) {

			logger.debug("filtro " + getAbrevProceso()
					+ " proceso - abreviatura");
			filtro.add(Restrictions.like("abreviatura",
					"%" + getAbrevProceso() + "%").ignoreCase());
		}

		try {
			procesos2 = procesoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + procesos2.size());

	}

	public void agregarProceso(ActionEvent e) {

		List<Proceso> procesos_ = new ArrayList<Proceso>();
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Proceso.class);
		Busqueda filtro2 = Busqueda.forClass(Proceso.class);

		
		if ( getNombreProceso().compareTo("") == 0  || getAbrevProceso().compareTo("") ==  0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Entidad, Organo, Distrito", "Datos Requeridos: Entidad, Organo, Distrito");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			Proceso proceso = new Proceso();
			proceso.setNombre(getNombreProceso());
			proceso.setAbreviatura(getAbrevProceso());
			proceso.setEstado('A');
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreProceso()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				procesos_ = procesoDAO.buscarDinamico(filtro);

				if (procesos_.size() == 0) {

					try {

						Proceso procesobd = new Proceso();
						procesobd = procesoDAO.insertar(proceso);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Exitoso", "Agrego el proceso"));
						logger.debug("guardo el proceso exitosamente");
						
						
						procesos2 = procesoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"No Exitoso", "No Agrego el proceso"));
						logger.debug("no guardo el proceso por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Proceso Existente", "Proceso Existente"));
				}

			} catch (Exception ex) {

			}
			
			
		}

		

	}

	public void limpiarProceso(ActionEvent e) {

		setNombreProceso("");
		setAbrevProceso("");
		procesos2 = new ArrayList<Proceso>();

	}

	public void editProceso(RowEditEvent event) {

		Proceso proceso = ((Proceso) event.getObject());
		logger.debug("modificando proceso " + proceso.getNombre());
		
		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			procesoDAO.modificar(proceso);
			logger.debug("actualizo el proceso exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el proceso exitosamente");
		}
	}

	public void deleteProceso() {

		logger.debug("eliminando el proceso... ");

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Proceso.class);
		
		getSelectProceso().setEstado('I');
		
		try {
			procesoDAO.modificar(getSelectProceso());
			logger.debug("elimino el proceso ");
			
			procesos2 = procesoDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el proceso ");
		}

	}

	public void buscarVia(ActionEvent e) {

		logger.debug("entro al buscar via");
		
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Via.class);

		if (getIdProceso() != 0) {

			logger.debug("filtro " + getIdProceso() + " proceso - idproceso");
			filtro.add(Restrictions.eq("proceso.idProceso",getIdProceso()));
		}

		if (getNombreVia().compareTo("") != 0) {

			logger.debug("filtro " + getNombreVia() + " proceso - via");
			filtro.add(Restrictions.like("nombre","%" + getNombreVia() + "%").ignoreCase());
		}

		try {
			vias = viaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + vias.size());

	}
	
	public void agregarVia(ActionEvent e) {

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		List<Via> vias_= new ArrayList<Via>();
		
		Busqueda filtro = Busqueda.forClass(Via.class);
		Busqueda filtro2 = Busqueda.forClass(Via.class);
		
		if ( getIdProceso() == 0  || getNombreVia().compareTo("") ==  0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Proceso, Via", "Datos Requeridos: Proceso, Via");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreVia()).ignoreCase());
				filtro.add(Restrictions.eq("proceso.idProceso",getIdProceso()));

				vias_ = viaDAO.buscarDinamico(filtro);

				if (vias_.size() == 0) {

					try {
						Via via = new Via();
						via.setNombre(getNombreVia());
						via.setEstado('A');
						via.setProceso(procesoDAO.buscarById(Proceso.class, getIdProceso()));
						
						viaDAO.insertar(via);
						FacesContext.getCurrentInstance().addMessage(null,
															new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso","Agrego la via"));
						logger.debug("guardo la via exitosamente");
						
						vias = viaDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(null,
															new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso","No Agrego la via"));
						logger.debug("no guardo la via por " + ex.getMessage());
					}

				} else {

					FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Via Existente", "Via Existente"));
				}

			} catch (Exception ex) {

			}

			
			
		}
		
		

	}

	public void limpiarVia(ActionEvent e) {
		setNombreVia("");
		setIdProceso(0);
		
		vias= new ArrayList<Via>();
	}
	
	public void editVia(RowEditEvent event) {

		Via via = ((Via) event.getObject());
		logger.debug("modificando la via " + via.getNombre());
		
		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			viaDAO.modificar(via);
			logger.debug("actualizo la via exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la via exitosamente");
		}
	}

	public void deleteVia() {

		logger.debug("eliminando la via... ");

		GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Via.class);
		
		getSelectVia().setEstado('I');
		
		try {
			viaDAO.modificar(getSelectVia());
			logger.debug("elimino la via.. ");
			
			vias = viaDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la via.. ");
		}

	}
	
	public void buscarInstancia(ActionEvent e) {

		logger.debug("entro al buscar instancia");

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Instancia.class);

		if (getNombreInstancia().compareTo("") != 0) {

			logger.debug("filtro " + getNombreInstancia() + " instancia - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreInstancia() + "%").ignoreCase());
		}

		try {
			instancias = instanciaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + instancias.size());

	}
	
	public void agregarInstancia(ActionEvent e) {

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Instancia.class);
		Busqueda filtro2 = Busqueda.forClass(Instancia.class);
		
		List<Instancia> instancias_=new ArrayList<Instancia>();

		if ( getNombreInstancia().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Instancia", "Datos Requeridos: Instancia");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreInstancia()).ignoreCase());

				instancias_ = instanciaDAO.buscarDinamico(filtro);

				if (instancias_.size() == 0) {
					
					Instancia instancia = new Instancia();
					instancia.setNombre(getNombreInstancia());
					instancia.setEstado('A');
					
					try {
						instanciaDAO.insertar(instancia);
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la instancia"));
						logger.debug("guardo la instancia exitosamente");
			
						instancias = instanciaDAO.buscarDinamico(filtro2);
						
					} catch (Exception ex) {
			
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la instancia"));
						logger.debug("no guardo la instancia por " + ex.getMessage());
					}
					
				}else{
					

					FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Instancia Existente", "Instancia Existente"));
					
				}
			

			} catch (Exception ex) {
	
				
			}
	
		}
			

	}
	
	public void editInstancia(RowEditEvent event) {

		Instancia instancia = ((Instancia) event.getObject());
		logger.debug("modificando instancia " + instancia.getNombre());
		
		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			instanciaDAO.modificar(instancia);
			logger.debug("actualizo la instancia exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la instancia exitosamente");
		}
	}

	public void limpiarInstancia(ActionEvent e) {
		setNombreInstancia("");
		
		instancias = new ArrayList<Instancia>();
	}
	
	public void deleteInstancia() {

		logger.debug("eliminando la instancia... ");

		GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Instancia.class);
		
		getSelectInstancia().setEstado('I');
		
		try {
			instanciaDAO.modificar(getSelectInstancia());
			logger.debug("elimino la instancia.. ");
			
			instancias = instanciaDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la instancia.. ");
		}

	}


	public void buscarActividad(ActionEvent e) {

		logger.debug("entro al buscar actividad");

		GenericDao<Actividad, Object> actividadDAO = 
				(GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Actividad.class);

		if (getNombreActividad().compareTo("") != 0) {

			logger.debug("filtro " + getNombreActividad() + " actividad - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreActividad() + "%").ignoreCase());
		}

		try {
			actividads = actividadDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + actividads.size());

	}
	
	public void agregarActividad(ActionEvent e) {

		GenericDao<Actividad, Object> actividadDAO = 
				(GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Actividad.class);
		Busqueda filtro2 = Busqueda.forClass(Actividad.class);
		
		List<Actividad> actividads_ = new ArrayList<Actividad>();
		

		if ( getNombreActividad().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Actividad", "Datos Requeridos: Actividad");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreActividad()).ignoreCase());

				actividads_ = actividadDAO.buscarDinamico(filtro);

				if (actividads_.size() == 0) {
					
					Actividad actividad = new Actividad();
					actividad.setNombre(getNombreActividad());
					actividad.setEstado('A');

					try {
						actividadDAO.insertar(actividad);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la actividad"));
						logger.debug("guardo la actividad exitosamente");
						
						actividads = actividadDAO.buscarDinamico(filtro2);
						
						
					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la actividad"));
						logger.debug("no guardo la actividad por " + ex.getMessage());
					}
					
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Actividad Existente", "Actividad Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		
	}

	public void editActividad(RowEditEvent event) {

		Actividad actividad = ((Actividad) event.getObject());
		logger.debug("modificando actividad " + actividad.getNombre());
		
		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			actividadDAO.modificar(actividad);
			logger.debug("actualizo la actividad exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la actividad exitosamente");
		}
	}
	
	public void limpiarActividad(ActionEvent e) {
		setNombreActividad("");
		
		actividads = new ArrayList<Actividad>();
	}
	
	public void deleteActividad() {

		logger.debug("eliminando la actividad... ");

		GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Actividad.class);
		
		getSelectActividad().setEstado('I');
		
		try {
			actividadDAO.modificar(getSelectActividad());
			logger.debug("elimino la actividad.. ");
			
			actividads = actividadDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la actividad.. ");
		}

	}
	
	public void buscarMoneda(ActionEvent e) {

		logger.debug("entro al buscar moneda");

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Moneda.class);

		if (getNombreMoneda().compareTo("") != 0) {

			logger.debug("filtro " + getNombreMoneda() + " moneda - nombre");
			filtro.add(Restrictions.like("descripcion",
					"%" + getNombreMoneda() + "%").ignoreCase());
		}

		if (getAbrevMoneda().compareTo("") != 0) {

			logger.debug("filtro " + getAbrevMoneda()
					+ " moneda - abreviatura");
			filtro.add(Restrictions.like("simbolo",
					"%" + getAbrevMoneda() + "%").ignoreCase());
		}

		try {
			monedas = monedaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + monedas.size());

	}
	
	public void agregarMoneda(ActionEvent e) {

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Moneda.class);
		Busqueda filtro2 = Busqueda.forClass(Moneda.class);
		
		List<Moneda> monedas_= new ArrayList<Moneda>();

		
		if ( getNombreMoneda().compareTo("") == 0  || getAbrevMoneda().compareTo("") ==  0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Descripcion, Abreviatura", "Datos Requeridos: Descripcion, Abreviatura");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreMoneda()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevMoneda()));

				monedas_ = monedaDAO.buscarDinamico(filtro);

				if (monedas_.size() == 0) {
					
				
					Moneda moneda = new Moneda();
					moneda.setDescripcion(getNombreMoneda());
					moneda.setSimbolo(getAbrevMoneda());
					moneda.setEstado('A');

					try {
						monedaDAO.insertar(moneda);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la moneda"));
						logger.debug("guardo la moneda exitosamente");
						
						monedas = monedaDAO.buscarDinamico(filtro2);
						
						
					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la moneda"));
						logger.debug("no guardo la moneda por " + ex.getMessage());
					}
					
				}else{
					
				
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Moneda Existente", "Moneda Existente"));
				}
				
			} catch (Exception ex) {

			}

		}		
					
		

	}

	public void editMoneda(RowEditEvent event) {

		Moneda moneda = ((Moneda) event.getObject());
		logger.debug("modificando moneda " + moneda.getDescripcion());
		
		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			monedaDAO.modificar(moneda);
			logger.debug("actualizo la moneda exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la moneda exitosamente");
		}
	}
	
	
	public void limpiarMoneda(ActionEvent e) {
		setNombreMoneda("");
		setAbrevMoneda("");
		
		monedas = new ArrayList<Moneda>();
	}
	
	public void deleteMoneda() {

		logger.debug("eliminando la moneda... ");

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Moneda.class);
		
		getSelectMoneda().setEstado('I');
		
		try {
			monedaDAO.modificar(getSelectMoneda());
			logger.debug("elimino la Moneda ");
			
			monedas = monedaDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la Moneda ");
		}

	}
	public void buscarEstudio(ActionEvent e) {

		logger.debug("entro al buscar estudio");

		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Estudio.class);

		if (getRucEstudio() != 0) {

			logger.debug("filtro " + getRucEstudio() + " estudio - ruc");
			filtro.add(Restrictions.eq("ruc",getRucEstudio()));
		}

		if (getNombreEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEstudio() + " estudio - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEstudio() + "%").ignoreCase());
		}
		if (getDireccionEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getDireccionEstudio() + " estudio - direccion");
			filtro.add(Restrictions.like("direccion","%" + getDireccionEstudio() + "%").ignoreCase());
		}
		if (getTelefEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getTelefEstudio() + " estudio - telefono");
			filtro.add(Restrictions.like("telefono","%" + getTelefEstudio() + "%").ignoreCase());
		}
		if (getCorreoEstudio().compareTo("") != 0) {

			logger.debug("filtro " + getCorreoEstudio() + " estudio - correo");
			filtro.add(Restrictions.like("correo","%" + getCorreoEstudio() + "%").ignoreCase());
		}

		try {
			estudios = estudioDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + estudios.size());

	}
	
	public void agregarEstudio(ActionEvent e) {

		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		List<Estudio> estudios_= new ArrayList<Estudio>();
		
		Busqueda filtro = Busqueda.forClass(Estudio.class);
		Busqueda filtro2 = Busqueda.forClass(Estudio.class);

		
		if ( getRucEstudio() == 0  
				|| getNombreEstudio().compareTo("") ==  0
					|| getDireccionEstudio().compareTo("") ==  0
						|| getTelefEstudio().compareTo("") ==  0
							|| getCorreoEstudio().compareTo("") ==  0) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Ruc, Nombre, Direccion, Telefono, Correo", "Datos Requeridos: Ruc, Nombre, Direccion, Telefono, Correo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEstudio()).ignoreCase());
				// filtro.add(Restrictions.eq("abreviatura", getAbrevProceso()));

				estudios_ = estudioDAO.buscarDinamico(filtro);

				if (estudios_.size() == 0) {
					
					Estudio estudio = new Estudio();

					estudio.setRuc(getRucEstudio());
					estudio.setCorreo(getCorreoEstudio());
					estudio.setDireccion(getDireccionEstudio());
					estudio.setNombre(getNombreEstudio());
					estudio.setTelefono(getTelefEstudio());
					estudio.setEstado('A');
					
					try {
						estudioDAO.insertar(estudio);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el estudio"));
						logger.debug("guardo el estudio exitosamente");
						
						estudios = estudioDAO.buscarDinamico(filtro2);
						
					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el estudio"));
						logger.debug("no guardo el estudio por " + ex.getMessage());
					}
					
				} else {

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Estudio Existente", "Estudio Existente"));
				}

			} catch (Exception ex) {

			}

		
		}
						
		

	}
	
	public void editEstudio(RowEditEvent event) {

		Estudio estudio = ((Estudio) event.getObject());
		logger.debug("modificando estudio " + estudio.getNombre());
		
		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		try {
			estudioDAO.modificar(estudio);
			logger.debug("actualizo el estudio exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el estudio");
		}
	}

	public void limpiarEstudio(ActionEvent e) {

		setRucEstudio((long) 0);
		setCorreoEstudio("");
		setDireccionEstudio("");
		setNombreEstudio("");
		setTelefEstudio("");
		
		estudios= new ArrayList<Estudio>();
	}
	
	public void deleteEstudio() {

		logger.debug("eliminando el estudio... ");

		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Estudio.class);
		
		getSelectEstudio().setEstado('I');
		
		try {
			estudioDAO.modificar(getSelectEstudio());
			logger.debug("elimino el estudio ");
			
			estudios = estudioDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el estudio ");
		}

	}
	
	public void buscarRolInv(ActionEvent e) {

		logger.debug("entro al buscar rol inv");

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = 
				(GenericDao<RolInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(RolInvolucrado.class);

		if (getNombreRolInvol().compareTo("") != 0) {

			logger.debug("filtro " + getNombreRolInvol() + " rol inv - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreRolInvol() + "%").ignoreCase());
		}

		try {
			rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + rolInvolucrados.size());

	}
	
	public void agregarRolInv(ActionEvent e) {

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(RolInvolucrado.class);
		Busqueda filtro2 = Busqueda.forClass(RolInvolucrado.class);
		
		List<RolInvolucrado> rolInvolucrados_ = new ArrayList<RolInvolucrado>();
		

		if ( getNombreRolInvol().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreRolInvol()).ignoreCase());

				rolInvolucrados_ = rolInvolucradoDAO.buscarDinamico(filtro);

				if (rolInvolucrados_.size() == 0) {
					
					RolInvolucrado rolInvolucrado = new RolInvolucrado();
					rolInvolucrado.setNombre(getNombreRolInvol());
					rolInvolucrado.setEstado('A');
					
					try {
						rolInvolucradoDAO.insertar(rolInvolucrado);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el rol involucrado"));
						logger.debug("guardo el rol involucrado exitosamente");
						
						rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el rol involucrado"));
						logger.debug("no guardo el rol involucrado por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Rol Inv Existente", "Rol Inv Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}							
					
		

	}
	
	public void editRolInv(RowEditEvent event) {

		RolInvolucrado rolInvolucrado = ((RolInvolucrado) event.getObject());
		logger.debug("modificando Rol Involucrado " + rolInvolucrado.getNombre());
		
		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			rolInvolucradoDAO.modificar(rolInvolucrado);
			logger.debug("actualizo el rol inv exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el rol inv exitosamente");
		}
	}
	
	public void limpiarRolInv(ActionEvent e) {

		setNombreRolInvol("");
		
		rolInvolucrados = new ArrayList<RolInvolucrado>();
	}
	
	public void deleteRolInv() {

		logger.debug("eliminando el rol inv... ");

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(RolInvolucrado.class);
		
		getSelectRolInv().setEstado('I');
		
		try {
			rolInvolucradoDAO.modificar(getSelectRolInv());
			logger.debug("elimino el rol inv.. ");
			
			rolInvolucrados = rolInvolucradoDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el rol inv.. ");
		}

	}
	
	public void buscarEstCaut(ActionEvent e) {

		logger.debug("entro al buscar est caut");

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = 
				(GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(EstadoCautelar.class);

		if (getNombreEstCaut().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEstCaut() + " est caut - nombre");
			filtro.add(Restrictions.like("descripcion","%" + getNombreEstCaut() + "%").ignoreCase());
		}

		try {
			estadosCautelars = estadoCautelarDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + estadosCautelars.size());

	}

	public void agregarEstCaut(ActionEvent e) {

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(EstadoCautelar.class);
		Busqueda filtro2 = Busqueda.forClass(EstadoCautelar.class);
		
		List<EstadoCautelar> estadoCautelars_ = new ArrayList<EstadoCautelar>();
		

		if ( getNombreEstCaut().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreEstCaut()).ignoreCase());

				estadoCautelars_ = estadoCautelarDAO.buscarDinamico(filtro);

				if (estadoCautelars_.size() == 0) {
					
					EstadoCautelar estadoCautelar = new EstadoCautelar();
					estadoCautelar.setDescripcion(getNombreEstCaut());
					estadoCautelar.setEstado('A');
					try {
						estadoCautelarDAO.insertar(estadoCautelar);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el estado cautelar"));
						logger.debug("guardo el estado cautelar exitosamente");
						
						estadosCautelars = estadoCautelarDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el estado cautelar"));
						logger.debug("no guardo el estado cautelar por " + ex.getMessage());
					}
					
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Est Caut Existente", "Est Caut Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}	

	}
	

	public void editEstCaut(RowEditEvent event) {

		EstadoCautelar estadoCautelar = ((EstadoCautelar) event.getObject());
		logger.debug("modificando estadoCautelar " + estadoCautelar.getDescripcion());
		
		GenericDao<EstadoCautelar, Object> estadoCautelardao = (GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			estadoCautelardao.modificar(estadoCautelar);
			logger.debug("actualizo el estadoCautelar exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el estadoCautelar exitosamente");
		}
	}

	public void limpiarEstCaut(ActionEvent e) {

		setNombreEstCaut("");
		estadosCautelars = new ArrayList<EstadoCautelar>();
	}
	
	public void deleteEstCaut() {

		logger.debug("eliminando el est caut... ");

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(EstadoCautelar.class);
		
		getSelectEstCaut().setEstado('I');
		
		try {
			estadoCautelarDAO.modificar(getSelectEstCaut());
			logger.debug("elimino el est caut.. ");
			
			estadosCautelars = estadoCautelarDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el est caut.. ");
		}

	}
	
	public void buscarEstExpe(ActionEvent e) {

		logger.debug("entro al buscar est expe");

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = 
				(GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(EstadoExpediente.class);

		if (getNombreEstExpe().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEstExpe() + " est expe - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEstExpe() + "%").ignoreCase());
		}

		try {
			estadoExpedientes = estadoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + estadoExpedientes.size());

	}


	public void agregarEstExpe(ActionEvent e) {

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(EstadoExpediente.class);
		Busqueda filtro2 = Busqueda.forClass(EstadoExpediente.class);
		
		List<EstadoExpediente> estadoExpedientes_ = new ArrayList<EstadoExpediente>();
		
		if ( getNombreEstExpe().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEstExpe()).ignoreCase());

				estadoExpedientes_ = estadoExpedienteDAO.buscarDinamico(filtro);

				if (estadoExpedientes_.size() == 0) {
					
					EstadoExpediente estadoExpediente = new EstadoExpediente();
					estadoExpediente.setNombre(getNombreEstExpe());
					estadoExpediente.setEstado('A');
					try {
						estadoExpedienteDAO.insertar(estadoExpediente);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el estado expediente"));
						logger.debug("guardo el estado expediente exitosamente");
						
						estadoExpedientes = estadoExpedienteDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el estado expediente"));
						logger.debug("no guardo el estado expediente por "
								+ ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Est Expe Existente", "Est Expe Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}	
		
		
		

	}
	
	public void editEstExpe(RowEditEvent event) {

		EstadoExpediente estadoExpediente= ((EstadoExpediente) event.getObject());
		logger.debug("modificando estadoCautelar " + estadoExpediente.getNombre());
		
		GenericDao<EstadoExpediente, Object> estadoExpedientedao = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			estadoExpedientedao.modificar(estadoExpediente);
			logger.debug("actualizo el estadoExpediente exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el estadoExpediente exitosamente");
		}
	}

	public void limpiarEstExpe(ActionEvent e) {
		setNombreEstExpe("");

	}
	
	public void deleteEstExpe() {

		logger.debug("eliminando el est expe... ");

		GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(EstadoExpediente.class);
		
		getSelectEstExpe().setEstado('I');
		
		try {
			estadoExpedienteDAO.modificar(getSelectEstExpe());
			logger.debug("elimino el est expe.. ");
			
			estadoExpedientes = estadoExpedienteDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el est expe.. ");
		}

	}
	
	public void buscarEtapa(ActionEvent e) {

		logger.debug("entro al buscar etapa");

		GenericDao<Etapa, Object> estadoExpedienteDAO = 
				(GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Etapa.class);

		if (getNombreEtapa().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEtapa() + " etapa - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEtapa() + "%").ignoreCase());
		}

		try {
			etapas = estadoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + etapas.size());

	}
	
	public void agregarEtapa(ActionEvent e) {

		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Etapa.class);
		Busqueda filtro2 = Busqueda.forClass(Etapa.class);
		
		List<Etapa> etapas_ = new ArrayList<Etapa>();
		

		if ( getNombreEtapa().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEtapa()).ignoreCase());
				etapas_ = etapaDAO.buscarDinamico(filtro);

				if (etapas_.size() == 0) {
					
					Etapa etapa = new Etapa();
					etapa.setNombre(getNombreEtapa());
					etapa.setEstado('A');

					try {
						etapaDAO.insertar(etapa);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la etapa"));
						logger.debug("guardo la etapa exitosamente");
						
						etapas = etapaDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la etapa"));
						logger.debug("no guardo la etapa por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Etapa Existente", "Etapa Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
				
		

	}
	
	public void editEtapa(RowEditEvent event) {

		Etapa etapa = ((Etapa) event.getObject());
		logger.debug("modificando etapa " + etapa.getNombre());
		
		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			etapaDAO.modificar(etapa);
			logger.debug("actualizo la etapa exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la etapa exitosamente");
		}
	}
	
	public void limpiarEtapa(ActionEvent e) {
		setNombreEtapa("");
		
		etapas = new ArrayList<Etapa>();
	}
	
	public void deleteEtapa() {

		logger.debug("eliminando la etapa... ");

		GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Etapa.class);
		
		getSelectEtapa().setEstado('I');
		
		try {
			etapaDAO.modificar(getSelectEtapa());
			logger.debug("elimino la etapa.. ");
			
			etapas = etapaDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la etapa.. ");
		}

	}

	public void buscarEntidad(ActionEvent e) {

		logger.debug("entro al buscar entidad");

		GenericDao<Entidad, Object> entidadDAO = 
				(GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Entidad.class);

		if (getNombreEntidad().compareTo("") != 0) {

			logger.debug("filtro " + getNombreEntidad() + " entidad - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreEntidad() + "%").ignoreCase());
		}

		try {
			entidads = entidadDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + entidads.size());

	}
	
	
	public void agregarEntidad(ActionEvent e) {

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Entidad.class);
		Busqueda filtro2 = Busqueda.forClass(Entidad.class);
		
		List<Entidad> entidads_ = new ArrayList<Entidad>();
		

		if ( getNombreEntidad().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreEntidad()).ignoreCase());

				entidads_ = entidadDAO.buscarDinamico(filtro);

				if (entidads_.size() == 0) {
					
					Entidad entidad = new Entidad();
					entidad.setNombre(getNombreEntidad());

					try {
						entidadDAO.insertar(entidad);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la entidad"));
						logger.debug("guardo la entidad exitosamente");
						
						entidads = entidadDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la entidad"));
						logger.debug("no guardo la entidad por " + ex.getMessage());
					}
					
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Entidad Existente", "Entidad Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}	

	}
	
	public void editEntidad(RowEditEvent event) {

		Entidad entidad = ((Entidad) event.getObject());
		logger.debug("modificando entidad " + entidad.getNombre());
		
		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			entidadDAO.modificar(entidad);
			logger.debug("actualizo la entidad exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la entidad exitosamente");
		}
	}

	public void limpiarEntidad(ActionEvent e) {
		setNombreEntidad("");
		entidads= new ArrayList<Entidad>();
	}
	
	public void deleteEntidad() {

		logger.debug("eliminando la actividad... ");

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Entidad.class);
		
		getSelectEntidad().setEstado('I');
		
		try {
			entidadDAO.modificar(getSelectEntidad());
			logger.debug("elimino la entidad.. ");
			
			entidads = entidadDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la entidad.. ");
		}

	}
	
	public void buscarRecurrencia(ActionEvent e) {

		logger.debug("entro al buscar recurrencia");

		GenericDao<Recurrencia, Object> recurrenciaDAO = 
				(GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Recurrencia.class);

		if (getNombreRecurrencia().compareTo("") != 0) {

			logger.debug("filtro " + getNombreRecurrencia() + " recurrencia - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreRecurrencia() + "%").ignoreCase());
		}

		try {
			recurrencias = recurrenciaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + recurrencias.size());

	}

	public void agregarRecurrencia(ActionEvent e) {

		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Recurrencia.class);
		Busqueda filtro2 = Busqueda.forClass(Recurrencia.class);
		
		List<Recurrencia> recurrencias_ = new ArrayList<Recurrencia>();
		

		if ( getNombreRecurrencia().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreRecurrencia()).ignoreCase());

				recurrencias_ = recurrenciaDAO.buscarDinamico(filtro);

				if (recurrencias_.size() == 0) {
					
				
					Recurrencia recurrencia = new Recurrencia();
					recurrencia.setNombre(getNombreRecurrencia());
					recurrencia.setEstado('A');

					try {
						recurrenciaDAO.insertar(recurrencia);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la recurrencia"));
						logger.debug("guardo la recurrencia exitosamente");
						
						recurrencias =  recurrenciaDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la recurrencia"));
						logger.debug("no guardo la recurrencia por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Recurrencia Existente", "Recurrencia Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarRecurrencia(ActionEvent e) {
		setNombreRecurrencia("");
		recurrencias = new ArrayList<Recurrencia>();
	}
	

	public void editRecurrencia(RowEditEvent event) {

		Recurrencia recurrencia = ((Recurrencia) event.getObject());
		logger.debug("modificando recurrencia " + recurrencia.getNombre());
		
		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			recurrenciaDAO.modificar(recurrencia);
			logger.debug("actualizo la recurrencia exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la recurrencia exitosamente");
		}
	}
	
	public void deleteRecurrencia() {

		logger.debug("eliminando la recurrencia... ");

		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(Recurrencia.class);
		
		getSelectRecurrencia().setEstado('I');
		
		try {
			recurrenciaDAO.modificar(getSelectRecurrencia());
			logger.debug("elimino la recurrencia.. ");
			
			recurrencias = recurrenciaDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la recurrencia.. ");
		}

	}
	
	public void buscarSitActPro(ActionEvent e) {

		logger.debug("entro al buscar sit act pro");

		GenericDao<SituacionActProc, Object> situacionActProcDAO = 
				(GenericDao<SituacionActProc, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionActProc.class);

		if (getNombreSitActPro().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitActPro() + " sit act - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreSitActPro() + "%").ignoreCase());
		}

		try {
			situacionActProcs = situacionActProcDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + situacionActProcs.size());

	}
	
	public void agregarSitActPro(ActionEvent e) {

		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionActProc.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionActProc.class);
		
		List<SituacionActProc> situacionActProcs_ = new ArrayList<SituacionActProc>();
		

		if ( getNombreSitActPro().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Sit Act Pro", "Datos Requeridos: Sit Act Pro");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreSitActPro()).ignoreCase());

				situacionActProcs_ = situacionActProcDAO.buscarDinamico(filtro);

				if (situacionActProcs_.size() == 0) {
					
					SituacionActProc situacionActProc = new SituacionActProc();
					situacionActProc.setNombre(getNombreSitActPro());
					situacionActProc.setEstado('A');
					
					try {
						situacionActProcDAO.insertar(situacionActProc);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac act pro"));
						logger.debug("guardo la situac act pro exitosamente");
						
						situacionActProcs = situacionActProcDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac act pro"));
						logger.debug("no guardo la situac act pro por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Act Pro Existente", "Sit Act Pro Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarSitActPro(ActionEvent e) {
		setNombreSitActPro("");
		situacionActProcs = new ArrayList<SituacionActProc>();
	}
	

	public void editSitActPro(RowEditEvent event) {

		SituacionActProc situacionActProc = ((SituacionActProc) event.getObject());
		logger.debug("modificando situacionActProc " + situacionActProc.getNombre());
		
		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionActProcDAO.modificar(situacionActProc);
			logger.debug("actualizo la situacionActProc exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la situacionActProc exitosamente");
		}
	}
	
	public void deleteSitActPro() {

		logger.debug("eliminando la situacionActProc... ");

		GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(SituacionActProc.class);
		
		getSelectSitActPro().setEstado('I');
		
		try {
			situacionActProcDAO.modificar(getSelectSitActPro());
			logger.debug("elimino la situacionActProc.. ");
			
			situacionActProcs = situacionActProcDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la situacionActProc.. ");
		}

	}
	
	public void buscarSitCuota(ActionEvent e) {

		logger.debug("entro al buscar sit cuota");

		GenericDao<SituacionCuota, Object> situacionCuotaDAO = 
				(GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionCuota.class);

		if (getNombreSitCuota().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitCuota() + " sit cuota - nombre");
			filtro.add(Restrictions.like("descripcion","%" + getNombreSitCuota() + "%").ignoreCase());
		}

		try {
			situacionCuotas = situacionCuotaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + situacionCuotas.size());

	}
	
	public void agregarSitCuota(ActionEvent e) {

		GenericDao<SituacionCuota, Object> situacionCuotaDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionCuota.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionCuota.class);
		
		List<SituacionCuota> situacionCuotas_ = new ArrayList<SituacionCuota>();
		

		if ( getNombreSitCuota().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreSitCuota()).ignoreCase());

				situacionCuotas_ = situacionCuotaDAO.buscarDinamico(filtro);

				if (situacionCuotas_.size() == 0) {
					
					SituacionCuota situacionCuota = new SituacionCuota();
					situacionCuota.setDescripcion(getNombreSitCuota());
					situacionCuota.setEstado('A');

					try {
						situacionCuotaDAO.insertar(situacionCuota);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac cuota"));
						logger.debug("guardo la situac cuota exitosamente");
						
						situacionCuotas = situacionCuotaDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac cuota"));
						logger.debug("no guardo la situac cuota por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Cuota Existente", "Sit Cuota Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
				
		

	}

	public void limpiarSitCuota(ActionEvent e) {
		setNombreSitCuota("");
		situacionCuotas = new ArrayList<SituacionCuota>();
	}

	public void editSitCuota(RowEditEvent event) {

		SituacionCuota situacionCuota = ((SituacionCuota) event.getObject());
		logger.debug("modificando situacionCuota " + situacionCuota.getDescripcion());
		
		GenericDao<SituacionCuota, Object> situacionCuotaDAO = (GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionCuotaDAO.modificar(situacionCuota);
			logger.debug("actualizo la situacionCuota exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la situacionCuota exitosamente");
		}
	}
	
	public void deleteSitCuota() {

		logger.debug("eliminando la situacionCuota... ");

		GenericDao<SituacionCuota, Object> situacionCuotaDAO = (GenericDao<SituacionCuota, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(SituacionCuota.class);
		
		getSelectSitCuota().setEstado('I');
		
		try {
			situacionCuotaDAO.modificar(getSelectSitCuota());
			logger.debug("elimino la situacionCuota.. ");
			
			situacionCuotas = situacionCuotaDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la situacionCuota.. ");
		}

	}
	
	public void buscarSitHonor(ActionEvent e) {

		logger.debug("entro al buscar sit honor");

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = 
				(GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionHonorario.class);

		if (getNombreSitHonor().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitHonor() + " sit honor - nombre");
			filtro.add(Restrictions.like("descripcion","%" + getNombreSitHonor() + "%").ignoreCase());
		}

		try {
			situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + situacionHonorarios.size());

	}
	
	public void agregarSitHonor(ActionEvent e) {

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionHonorario.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionHonorario.class);
		
		List<SituacionHonorario> situacionHonorarios_ = new ArrayList<SituacionHonorario>();
		

		if ( getNombreSitHonor().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreSitHonor()).ignoreCase());

				situacionHonorarios_ = situacionHonorarioDAO.buscarDinamico(filtro);

				if (situacionHonorarios_.size() == 0) {
					
				
					SituacionHonorario situacionHonorario = new SituacionHonorario();
					situacionHonorario.setDescripcion(getNombreSitHonor());
					situacionHonorario.setEstado('A');

					try {
						situacionHonorarioDAO.insertar(situacionHonorario);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac honor"));
						logger.debug("guardo la situac honor exitosamente");
						
						situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac honor"));
						logger.debug("no guardo la situac honor por " + ex.getMessage());
					}
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Honor Existente", "Sit Honor Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		

	}

	public void limpiarSitHonor(ActionEvent e) {
		setNombreSitHonor("");
		situacionHonorarios = new ArrayList<SituacionHonorario>();
	}
	
	public void editSitHonor(RowEditEvent event) {

		SituacionHonorario situacionHonorario = ((SituacionHonorario) event.getObject());
		logger.debug("modificando situacionHonorario " + situacionHonorario.getDescripcion());
		
		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionHonorarioDAO.modificar(situacionHonorario);
			logger.debug("actualizo la situacionHonorario exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la situacionHonorario exitosamente");
		}
	}
	
	public void deleteSitHonor() {

		logger.debug("eliminando la situacionHonorario... ");

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(SituacionHonorario.class);
		
		getSelectSitHonor().setEstado('I');
		
		try {
			situacionHonorarioDAO.modificar(getSelectSitHonor());
			logger.debug("elimino la situacionHonorario.. ");
			
			situacionHonorarios = situacionHonorarioDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la situacionHonorario.. ");
		}

	}
	
	public void buscarSitIncul(ActionEvent e) {

		logger.debug("entro al buscar sit inc");

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = 
				(GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(SituacionInculpado.class);

		if (getNombreSitIncul().compareTo("") != 0) {

			logger.debug("filtro " + getNombreSitIncul() + " sit inc - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreSitIncul() + "%").ignoreCase());
		}

		try {
			situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + situacionInculpados.size());

	}
	
	public void agregarSitIncul(ActionEvent e) {

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(SituacionInculpado.class);
		Busqueda filtro2 = Busqueda.forClass(SituacionInculpado.class);
		
		List<SituacionInculpado> situacionInculpados_ = new ArrayList<SituacionInculpado>();
		

		if ( getNombreSitIncul().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreSitIncul()).ignoreCase());

				situacionInculpados_ = situacionInculpadoDAO.buscarDinamico(filtro);

				if (situacionInculpados_.size() == 0) {
			
					SituacionInculpado situacionInculpado = new SituacionInculpado();
					situacionInculpado.setNombre(getNombreSitIncul());
					situacionInculpado.setEstado('A');

					try {
						situacionInculpadoDAO.insertar(situacionInculpado);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego la situac inculp"));
						logger.debug("guardo la situac inculp exitosamente");
						
						situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego la situac inculp"));
						logger.debug("no guardo la situac inculp por " + ex.getMessage());
					}
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sit Inc Existente", "Sit Inc Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarSitIncul(ActionEvent e) {
		setNombreSitIncul("");
		
		situacionInculpados = new ArrayList<SituacionInculpado>();
		
	}
	
	public void editSitIncul(RowEditEvent event) {

		SituacionInculpado situacionInculpado = ((SituacionInculpado) event.getObject());
		logger.debug("modificando situacionInculpado " + situacionInculpado.getNombre());
		
		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			situacionInculpadoDAO.modificar(situacionInculpado);
			logger.debug("actualizo la situacionInculpado exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo la situacionInculpado exitosamente");
		}
	}
	
	public void deleteSitIncul() {

		logger.debug("eliminando la situacionIncul... ");

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(SituacionInculpado.class);
		
		getSelectSitInc().setEstado('I');
		
		try {
			situacionInculpadoDAO.modificar(getSelectSitInc());
			logger.debug("elimino la situacionIncul.. ");
			
			situacionInculpados = situacionInculpadoDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino la situacionIncul.. ");
		}

	}
	
	public void buscarTipoCaut(ActionEvent e) {

		logger.debug("entro al buscar TipoCaut");

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = 
				(GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoCautelar.class);

		if (getNombreTipoCaut().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoCaut() + " TipoCaut - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreTipoCaut() + "%").ignoreCase());
		}

		try {
			tipoCautelars = tipoCautelarDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + tipoCautelars.size());

	}

	public void agregarTipoCaut(ActionEvent e) {

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoCautelar.class);
		Busqueda filtro2 = Busqueda.forClass(TipoCautelar.class);
		
		List<TipoCautelar> tipoCautelars_ = new ArrayList<TipoCautelar>();
		

		if ( getNombreTipoCaut().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreTipoCaut()).ignoreCase());

				tipoCautelars_ = tipoCautelarDAO.buscarDinamico(filtro);

				if (tipoCautelars_.size() == 0) {
					
					TipoCautelar tipoCautelar = new TipoCautelar();
					tipoCautelar.setDescripcion(getNombreTipoCaut());
					tipoCautelar.setEstado('A');
					try {
						tipoCautelarDAO.insertar(tipoCautelar);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo cautelar"));
						logger.debug("guardo el tipo cautelar exitosamente");
						
						tipoCautelars = tipoCautelarDAO.buscarDinamico(filtro2);
						

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo cautelar"));
						logger.debug("no guardo el tipo cautelar por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Caut Existente", "Tip Caut Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
		

	}

	public void limpiarTipoCaut(ActionEvent e) {
		setNombreTipoCaut("");
		tipoCautelars = new ArrayList<TipoCautelar>();
	}
	
	public void editTipCaut(RowEditEvent event) {

		TipoCautelar tipoCautelar = ((TipoCautelar) event.getObject());
		logger.debug("modificando tipoCautelar " + tipoCautelar.getDescripcion());
		
		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoCautelarDAO.modificar(tipoCautelar);
			logger.debug("actualizo el tipoCautelar exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el tipoCautelar exitosamente");
		}
	}
	
	public void deleteTipCaut() {

		logger.debug("eliminando el tipo caut... ");

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(TipoCautelar.class);
		
		getSelectTipCaut().setEstado('I');
		
		try {
			tipoCautelarDAO.modificar(getSelectTipCaut());
			logger.debug("elimino el tipo caut.. ");
			
			tipoCautelars = tipoCautelarDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el tipo caut.. ");
		}

	}
	
	public void buscarTipoExpe(ActionEvent e) {

		logger.debug("entro al buscar tip expe");

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = 
				(GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoExpediente.class);

		if (getNombreTipoExpe().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoExpe() + " TipoExpe - nombre");
			filtro.add(Restrictions.like("nombre","%" + getNombreTipoExpe() + "%").ignoreCase());
		}

		try {
			tipoExpedientes = tipoExpedienteDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + tipoExpedientes.size());

	}
	
	public void agregarTipoExpe(ActionEvent e) {

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoExpediente.class);
		Busqueda filtro2 = Busqueda.forClass(TipoExpediente.class);
		
		List<TipoExpediente> tipoExpedientes_ = new ArrayList<TipoExpediente>();
		

		if ( getNombreTipoExpe().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreTipoExpe()).ignoreCase());

				tipoExpedientes_ = tipoExpedienteDAO.buscarDinamico(filtro);

				if (tipoExpedientes_.size() == 0) {
					
					TipoExpediente tipoExpediente = new TipoExpediente();
					tipoExpediente.setNombre(getNombreTipoExpe());
					tipoExpediente.setEstado('A');

					try {
						tipoExpedienteDAO.insertar(tipoExpediente);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo expediente"));
						logger.debug("guardo el tipo expediente exitosamente");
					
						tipoExpedientes = tipoExpedienteDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo expediente"));
						logger.debug("no guardo el tipo expediente por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Expe Existente", "Tip Expe Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
				
				
		

	}

	public void limpiarTipoExpe(ActionEvent e) {
		setNombreTipoExpe("");
		tipoExpedientes = new ArrayList<TipoExpediente>();
	}
	
	public void editTipoExpe(RowEditEvent event) {

		TipoExpediente tipoExpediente = ((TipoExpediente) event.getObject());
		logger.debug("modificando tipoExpediente " + tipoExpediente.getNombre());
		
		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoExpedienteDAO.modificar(tipoExpediente);
			logger.debug("actualizo el tipoExpediente exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el tipoExpediente exitosamente");
		}
	}
	public void deleteTipoExpe() {

		logger.debug("eliminando el tipo expe... ");

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(TipoExpediente.class);
		
		getSelectTipExpe().setEstado('I');
		
		try {
			tipoExpedienteDAO.modificar(getSelectTipExpe());
			logger.debug("elimino el tipo expe.. ");
			
			tipoExpedientes = tipoExpedienteDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el tipo expe.. ");
		}

	}
	
	public void buscarTipoHonor(ActionEvent e) {

		logger.debug("entro al buscar TipoHonor");

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = 
				(GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoHonorario.class);

		if (getNombreTipoHonor().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoHonor() + " TipoHonor - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreTipoHonor() + "%").ignoreCase());
		}

		try {
			tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + tipoHonorarios.size());

	}

	
	public void agregarTipoHonor(ActionEvent e) {

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoHonorario.class);
		Busqueda filtro2 = Busqueda.forClass(TipoHonorario.class);
		
		List<TipoHonorario> tipoHonorarios_ = new ArrayList<TipoHonorario>();
		

		if ( getNombreTipoHonor().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreTipoHonor()).ignoreCase());

				tipoHonorarios_ = tipoHonorarioDAO.buscarDinamico(filtro);

				if (tipoHonorarios_.size() == 0) {
					
					TipoHonorario tipoHonorario = new TipoHonorario();
					tipoHonorario.setDescripcion(getNombreTipoHonor());
					tipoHonorario.setEstado('A');

					try {
						tipoHonorarioDAO.insertar(tipoHonorario);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo honorario"));
						logger.debug("guardo el tipo honorario exitosamente");
						
						tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo honorario"));
						logger.debug("no guardo el tipo honorario por " + ex.getMessage());
					}
					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Honor Existente", "Tip Honor Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		

	}

	public void limpiarTipoHonor(ActionEvent e) {

		setNombreTipoHonor("");
		tipoHonorarios = new ArrayList<TipoHonorario>();
	}
	
	public void editTipoHonor(RowEditEvent event) {

		TipoHonorario tipoHonorario = ((TipoHonorario) event.getObject());
		logger.debug("modificando tipoHonorario " + tipoHonorario.getDescripcion());
		
		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoHonorarioDAO.modificar(tipoHonorario);
			logger.debug("actualizo el tipoHonorario exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el tipoHonorario exitosamente");
		}
	}
	public void deleteTipoHonor() {

		logger.debug("eliminando el tipo honor... ");

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(TipoHonorario.class);
		
		getSelectTipHonor().setEstado('I');
		
		try {
			tipoHonorarioDAO.modificar(getSelectTipHonor());
			logger.debug("elimino el tipo honor.. ");
			
			tipoHonorarios = tipoHonorarioDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el tipo honor.. ");
		}

	}

	public void buscarTipInv(ActionEvent e) {

		logger.debug("entro al buscar TipInv");

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = 
				(GenericDao<TipoInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoInvolucrado.class);

		if (getNombreTipoInv().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoInv() + " TipoHonor - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreTipoInv() + "%").ignoreCase());
		}

		try {
			tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + tipoInvolucrados.size());

	}

	public void agregarTipoInv(ActionEvent e) {

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoInvolucrado.class);
		Busqueda filtro2 = Busqueda.forClass(TipoInvolucrado.class);
		
		List<TipoInvolucrado> tipoInvolucrados_ = new ArrayList<TipoInvolucrado>();
		

		if ( getNombreTipoInv().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("nombre", getNombreTipoInv()).ignoreCase());

				tipoInvolucrados_ = tipoInvolucradoDAO.buscarDinamico(filtro);

				if (tipoInvolucrados_.size() == 0) {
					
					TipoInvolucrado tipoInvolucrado = new TipoInvolucrado();
					tipoInvolucrado.setNombre(getNombreTipoInv());
					tipoInvolucrado.setEstado('A');
					try {
						tipoInvolucradoDAO.insertar(tipoInvolucrado);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo honorario"));
						logger.debug("guardo el tipo honorario exitosamente");
						
						tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo honorario"));
						logger.debug("no guardo el tipo honorario por " + ex.getMessage());
					}

					
				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Inv Existente", "Tip Inv Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		

	
	}

	public void limpiarTipoInv(ActionEvent e) {
		setNombreTipoInv("");
		tipoInvolucrados = new ArrayList<TipoInvolucrado>();
	}
	
	public void editTipInv(RowEditEvent event) {

		TipoInvolucrado tipoInvolucrado = ((TipoInvolucrado) event.getObject());
		logger.debug("modificando tipoInvolucrado " + tipoInvolucrado.getNombre());
		
		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoInvolucradoDAO.modificar(tipoInvolucrado);
			logger.debug("actualizo el tipoInvolucrado exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el tipoInvolucrado exitosamente");
		}
	}
	
	public void deleteTipInv() {

		logger.debug("eliminando el tipo inv... ");

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(TipoInvolucrado.class);
		
		getSelectTipInv().setEstado('I');
		
		try {
			tipoInvolucradoDAO.modificar(getSelectTipInv());
			logger.debug("elimino el tipo inv.. ");
			
			tipoInvolucrados = tipoInvolucradoDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el tipo inv.. ");
		}

	}
	
	public void buscarTipoPro(ActionEvent e) {

		logger.debug("entro al buscar TipPro");

		GenericDao<TipoProvision, Object> tipoProvisionDAO = 
				(GenericDao<TipoProvision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(TipoProvision.class);

		if (getNombreTipoPro().compareTo("") != 0) {

			logger.debug("filtro " + getNombreTipoPro() + " TipoPro - descripcion");
			filtro.add(Restrictions.like("descripcion","%" + getNombreTipoPro() + "%").ignoreCase());
		}

		try { 
			tipoProvisions = tipoProvisionDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		logger.debug("trajo .." + tipoProvisions.size());

	}
	
	public void agregarTipoPro(ActionEvent e) {

		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(TipoProvision.class);
		Busqueda filtro2 = Busqueda.forClass(TipoProvision.class);
		
		List<TipoProvision> tipoProvisions_ = new ArrayList<TipoProvision>();
		

		if ( getNombreTipoPro().compareTo("") == 0 ) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nombre", "Datos Requeridos: Nombre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			try {

				filtro.add(Restrictions.eq("descripcion", getNombreTipoPro()).ignoreCase());

				tipoProvisions_ = tipoProvisionDAO.buscarDinamico(filtro);

				if (tipoProvisions_.size() == 0) {
					
					TipoProvision tipoProvision = new TipoProvision();
					tipoProvision.setDescripcion(getNombreTipoPro());
					tipoProvision.setEstado('A');
					
					try {
						tipoProvisionDAO.insertar(tipoProvision);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso",
										"Agrego el tipo provision"));
						logger.debug("guardo el tipo provision exitosamente");
						
						tipoProvisions = tipoProvisionDAO.buscarDinamico(filtro2);

					} catch (Exception ex) {

						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Exitoso",
										"No Agrego el tipo provision"));
						logger.debug("no guardo el tipo provision por " + ex.getMessage());
					}

				}else{
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Tip Prov Existente", "Tip Prov Existente"));
					
				}
				
			} catch (Exception ex) {

			}
				
		}		
		
	}

	public void limpiarTipoPro(ActionEvent e) {
		setNombreTipoPro("");
		tipoProvisions = new ArrayList<TipoProvision>();
	}
	
	public void editTipPro(RowEditEvent event) {

		TipoProvision tipoProvision = ((TipoProvision) event.getObject());
		logger.debug("modificando tipoInvolucrado " + tipoProvision.getDescripcion());
		
		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		try {
			tipoProvisionDAO.modificar(tipoProvision);
			logger.debug("actualizo el tipoProvision exitosamente");
		} catch (Exception e) {
			logger.debug("no actualizo el tipoProvision exitosamente");
		}
	}
	
	public void deleteTipProv() {

		logger.debug("eliminando el tipo pro... ");

		GenericDao<TipoProvision, Object> tipoProvisionDAO = (GenericDao<TipoProvision, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro2 = Busqueda.forClass(TipoProvision.class);
		
		getSelectTipProv().setEstado('I');
		
		try {
			tipoProvisionDAO.modificar(getSelectTipProv());
			logger.debug("elimino el tipo pro.. ");
			
			tipoProvisions = tipoProvisionDAO.buscarDinamico(filtro2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("no elimino el tipo pro.. ");
		}

	}
	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getAbrevProceso() {
		return abrevProceso;
	}

	public void setAbrevProceso(String abrevProceso) {
		this.abrevProceso = abrevProceso;
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNombreInstancia() {
		return nombreInstancia;
	}

	public void setNombreInstancia(String nombreInstancia) {
		this.nombreInstancia = nombreInstancia;
	}

	public String getNombreMoneda() {
		return nombreMoneda;
	}

	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}

	public String getAbrevMoneda() {
		return abrevMoneda;
	}

	public void setAbrevMoneda(String abrevMoneda) {
		this.abrevMoneda = abrevMoneda;
	}

	public String getNombreEstudio() {
		return nombreEstudio;
	}

	public void setNombreEstudio(String nombreEstudio) {
		this.nombreEstudio = nombreEstudio;
	}

	public String getDireccionEstudio() {
		return direccionEstudio;
	}

	public void setDireccionEstudio(String direccionEstudio) {
		this.direccionEstudio = direccionEstudio;
	}

	public String getTelefEstudio() {
		return telefEstudio;
	}

	public void setTelefEstudio(String telefEstudio) {
		this.telefEstudio = telefEstudio;
	}

	public String getCorreoEstudio() {
		return correoEstudio;
	}

	public void setCorreoEstudio(String correoEstudio) {
		this.correoEstudio = correoEstudio;
	}

	

	public String getNombreEstCaut() {
		return nombreEstCaut;
	}

	public void setNombreEstCaut(String nombreEstCaut) {
		this.nombreEstCaut = nombreEstCaut;
	}

	public String getNombreEstExpe() {
		return nombreEstExpe;
	}

	public void setNombreEstExpe(String nombreEstExpe) {
		this.nombreEstExpe = nombreEstExpe;
	}

	public String getNombreEtapa() {
		return nombreEtapa;
	}

	public void setNombreEtapa(String nombreEtapa) {
		this.nombreEtapa = nombreEtapa;
	}

	public String getNombreFormConc() {
		return nombreFormConc;
	}

	public void setNombreFormConc(String nombreFormConc) {
		this.nombreFormConc = nombreFormConc;
	}

	public String getNombreRecurrencia() {
		return nombreRecurrencia;
	}

	public void setNombreRecurrencia(String nombreRecurrencia) {
		this.nombreRecurrencia = nombreRecurrencia;
	}

	public String getNombreSitActPro() {
		return nombreSitActPro;
	}

	public void setNombreSitActPro(String nombreSitActPro) {
		this.nombreSitActPro = nombreSitActPro;
	}

	public String getNombreSitCuota() {
		return nombreSitCuota;
	}

	public void setNombreSitCuota(String nombreSitCuota) {
		this.nombreSitCuota = nombreSitCuota;
	}

	public String getNombreSitHonor() {
		return nombreSitHonor;
	}

	public void setNombreSitHonor(String nombreSitHonor) {
		this.nombreSitHonor = nombreSitHonor;
	}

	public String getNombreSitIncul() {
		return nombreSitIncul;
	}

	public void setNombreSitIncul(String nombreSitIncul) {
		this.nombreSitIncul = nombreSitIncul;
	}

	public String getNombreTipoCaut() {
		return nombreTipoCaut;
	}

	public void setNombreTipoCaut(String nombreTipoCaut) {
		this.nombreTipoCaut = nombreTipoCaut;
	}

	public String getNombreTipoExpe() {
		return nombreTipoExpe;
	}

	public void setNombreTipoExpe(String nombreTipoExpe) {
		this.nombreTipoExpe = nombreTipoExpe;
	}

	public String getNombreTipoHonor() {
		return nombreTipoHonor;
	}

	public void setNombreTipoHonor(String nombreTipoHonor) {
		this.nombreTipoHonor = nombreTipoHonor;
	}

	public String getNombreTipoInv() {
		return nombreTipoInv;
	}

	public void setNombreTipoInv(String nombreTipoInv) {
		this.nombreTipoInv = nombreTipoInv;
	}

	public String getNombreTipoPro() {
		return nombreTipoPro;
	}

	public void setNombreTipoPro(String nombreTipoPro) {
		this.nombreTipoPro = nombreTipoPro;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getNombreRolInvol() {
		return nombreRolInvol;
	}

	public void setNombreRolInvol(String nombreRolInvol) {
		this.nombreRolInvol = nombreRolInvol;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public int getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getNombreRiesgo() {
		return nombreRiesgo;
	}

	public void setNombreRiesgo(String nombreRiesgo) {
		this.nombreRiesgo = nombreRiesgo;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDescrCalificacion() {
		return descrCalificacion;
	}

	public void setDescrCalificacion(String descrCalificacion) {
		this.descrCalificacion = descrCalificacion;
	}

	public FormaConclusion getFormaConclusion() {
		return formaConclusion;
	}

	public void setFormaConclusion(FormaConclusion formaConclusion) {
		this.formaConclusion = formaConclusion;
	}

	public String getDescrFormaConclusion() {
		return descrFormaConclusion;
	}

	public void setDescrFormaConclusion(String descrFormaConclusion) {
		this.descrFormaConclusion = descrFormaConclusion;
	}

	public String getCodigoDistrito() {
		return codigoDistrito;
	}

	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}

	public String getNomDistrito() {
		return nomDistrito;
	}

	public void setNomDistrito(String nomDistrito) {
		this.nomDistrito = nomDistrito;
	}

	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public String getNomProvincia() {
		return nomProvincia;
	}

	public void setNomProvincia(String nomProvincia) {
		this.nomProvincia = nomProvincia;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public String getNomDepartamento() {
		return nomDepartamento;
	}

	public void setNomDepartamento(String nomDepartamento) {
		this.nomDepartamento = nomDepartamento;
	}

	public String getNomGrupoBanca() {
		return nomGrupoBanca;
	}

	public void setNomGrupoBanca(String nomGrupoBanca) {
		this.nomGrupoBanca = nomGrupoBanca;
	}

	public String getCodTerritorio() {
		return codTerritorio;
	}

	public void setCodTerritorio(String codTerritorio) {
		this.codTerritorio = codTerritorio;
	}

	public String getNomTerritorio() {
		return nomTerritorio;
	}

	public void setNomTerritorio(String nomTerritorio) {
		this.nomTerritorio = nomTerritorio;
	}

	public List<GrupoBanca> getLstGrupoBanca() {
		return lstGrupoBanca;
	}

	public void setLstGrupoBanca(List<GrupoBanca> lstGrupoBanca) {
		this.lstGrupoBanca = lstGrupoBanca;
	}

	public int getIdGrupoBanca() {
		return idGrupoBanca;
	}

	public void setIdGrupoBanca(int idGrupoBanca) {
		this.idGrupoBanca = idGrupoBanca;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Organo> getLstOrgano() {
		return lstOrgano;
	}

	public void setLstOrgano(List<Organo> lstOrgano) {
		this.lstOrgano = lstOrgano;
	}

	public List<Ubigeo> getLstUbigeo() {
		return lstUbigeo;
	}

	public void setLstUbigeo(List<Ubigeo> lstUbigeo) {
		this.lstUbigeo = lstUbigeo;
	}

	public int getIdOrganos() {
		return idOrganos;
	}

	public void setIdOrganos(int idOrganos) {
		this.idOrganos = idOrganos;
	}

	public String getIdUbigeo() {
		return idUbigeo;
	}

	public void setIdUbigeo(String idUbigeo) {
		this.idUbigeo = idUbigeo;
	}

	public String getTipoFeriado() {
		return tipoFeriado;
	}

	public void setTipoFeriado(String tipoFeriado) {
		this.tipoFeriado = tipoFeriado;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public List<Territorio> getLstTerritorio() {
		return lstTerritorio;
	}

	public void setLstTerritorio(List<Territorio> lstTerritorio) {
		this.lstTerritorio = lstTerritorio;
	}

	public String getCodigoOficina() {
		return codigoOficina;
	}

	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	public String getNomOficina() {
		return nomOficina;
	}

	public void setNomOficina(String nomOficina) {
		this.nomOficina = nomOficina;
	}

	public List<Via> getLstVias() {
		return lstVias;
	}

	public void setLstVias(List<Via> lstVias) {
		this.lstVias = lstVias;
	}

	public List<Actividad> getLstActividad() {
		return lstActividad;
	}

	public void setLstActividad(List<Actividad> lstActividad) {
		this.lstActividad = lstActividad;
	}

	public int getIdVia() {
		return idVia;
	}

	public void setIdVia(int idVia) {
		this.idVia = idVia;
	}

	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public int getNumDiasRojoEst1() {
		return numDiasRojoEst1;
	}

	public void setNumDiasRojoEst1(int numDiasRojoEst1) {
		this.numDiasRojoEst1 = numDiasRojoEst1;
	}

	public int getNumNaraEst1() {
		return numNaraEst1;
	}

	public void setNumNaraEst1(int numNaraEst1) {
		this.numNaraEst1 = numNaraEst1;
	}

	public int getNumAmaEst1() {
		return numAmaEst1;
	}

	public void setNumAmaEst1(int numAmaEst1) {
		this.numAmaEst1 = numAmaEst1;
	}

	public int getNumDiasRojoEst2() {
		return numDiasRojoEst2;
	}

	public void setNumDiasRojoEst2(int numDiasRojoEst2) {
		this.numDiasRojoEst2 = numDiasRojoEst2;
	}

	public int getNumNaraEst2() {
		return numNaraEst2;
	}

	public void setNumNaraEst2(int numNaraEst2) {
		this.numNaraEst2 = numNaraEst2;
	}

	public int getNumAmaEst2() {
		return numAmaEst2;
	}

	public void setNumAmaEst2(int numAmaEst2) {
		this.numAmaEst2 = numAmaEst2;
	}

	public int getNumDiasRojoEst3() {
		return numDiasRojoEst3;
	}

	public void setNumDiasRojoEst3(int numDiasRojoEst3) {
		this.numDiasRojoEst3 = numDiasRojoEst3;
	}

	public int getNumNaraEst3() {
		return numNaraEst3;
	}

	public void setNumNaraEst3(int numNaraEst3) {
		this.numNaraEst3 = numNaraEst3;
	}

	public int getNumAmaEst3() {
		return numAmaEst3;
	}

	public void setNumAmaEst3(int numAmaEst3) {
		this.numAmaEst3 = numAmaEst3;
	}

	public int getIdProcesoEstado() {
		return idProcesoEstado;
	}

	public void setIdProcesoEstado(int idProcesoEstado) {
		this.idProcesoEstado = idProcesoEstado;
	}

	public Proceso getSelectProceso() {
		return selectProceso;
	}

	public void setSelectProceso(Proceso selectProceso) {
		this.selectProceso = selectProceso;
	}

	public Via getSelectVia() {
		return selectVia;
	}

	public void setSelectVia(Via selectVia) {
		this.selectVia = selectVia;
	}

	public List<Via> getVias() {
		return vias;
	}

	public void setVias(List<Via> vias) {
		this.vias = vias;
	}

	public Instancia getSelectInstancia() {
		return selectInstancia;
	}

	public void setSelectInstancia(Instancia selectInstancia) {
		this.selectInstancia = selectInstancia;
	}

	public List<Instancia> getInstancias() {
		return instancias;
	}

	public void setInstancias(List<Instancia> instancias) {
		this.instancias = instancias;
	}

	public Actividad getSelectActividad() {
		return selectActividad;
	}

	public void setSelectActividad(Actividad selectActividad) {
		this.selectActividad = selectActividad;
	}

	public List<Actividad> getActividads() {
		return actividads;
	}

	public void setActividads(List<Actividad> actividads) {
		this.actividads = actividads;
	}

	public Moneda getSelectMoneda() {
		return selectMoneda;
	}

	public void setSelectMoneda(Moneda selectMoneda) {
		this.selectMoneda = selectMoneda;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}

	public Estudio getSelectEstudio() {
		return selectEstudio;
	}

	public void setSelectEstudio(Estudio selectEstudio) {
		this.selectEstudio = selectEstudio;
	}

	public List<Estudio> getEstudios() {
		return estudios;
	}

	public void setEstudios(List<Estudio> estudios) {
		this.estudios = estudios;
	}

	public EstadoCautelar getSelectEstCaut() {
		return selectEstCaut;
	}

	public void setSelectEstCaut(EstadoCautelar selectEstCaut) {
		this.selectEstCaut = selectEstCaut;
	}

	public List<EstadoCautelar> getEstadosCautelars() {
		return estadosCautelars;
	}

	public void setEstadosCautelars(List<EstadoCautelar> estadosCautelars) {
		this.estadosCautelars = estadosCautelars;
	}

	public EstadoExpediente getSelectEstExpe() {
		return selectEstExpe;
	}

	public void setSelectEstExpe(EstadoExpediente selectEstExpe) {
		this.selectEstExpe = selectEstExpe;
	}

	public List<EstadoExpediente> getEstadoExpedientes() {
		return estadoExpedientes;
	}

	public void setEstadoExpedientes(List<EstadoExpediente> estadoExpedientes) {
		this.estadoExpedientes = estadoExpedientes;
	}

	public Etapa getSelectEtapa() {
		return selectEtapa;
	}

	public void setSelectEtapa(Etapa selectEtapa) {
		this.selectEtapa = selectEtapa;
	}

	public List<Etapa> getEtapas() {
		return etapas;
	}

	public void setEtapas(List<Etapa> etapas) {
		this.etapas = etapas;
	}

	public Entidad getSelectEntidad() {
		return selectEntidad;
	}

	public void setSelectEntidad(Entidad selectEntidad) {
		this.selectEntidad = selectEntidad;
	}

	public List<Entidad> getEntidads() {
		return entidads;
	}

	public void setEntidads(List<Entidad> entidads) {
		this.entidads = entidads;
	}

	public FormaConclusion getSelectFormConc() {
		return selectFormConc;
	}

	public void setSelectFormConc(FormaConclusion selectFormConc) {
		this.selectFormConc = selectFormConc;
	}

	public List<FormaConclusion> getFormaConclusions() {
		return formaConclusions;
	}

	public void setFormaConclusions(List<FormaConclusion> formaConclusions) {
		this.formaConclusions = formaConclusions;
	}

	public Recurrencia getSelectRecurrencia() {
		return selectRecurrencia;
	}

	public void setSelectRecurrencia(Recurrencia selectRecurrencia) {
		this.selectRecurrencia = selectRecurrencia;
	}

	public List<Recurrencia> getRecurrencias() {
		return recurrencias;
	}

	public void setRecurrencias(List<Recurrencia> recurrencias) {
		this.recurrencias = recurrencias;
	}

	public SituacionActProc getSelectSitActPro() {
		return selectSitActPro;
	}

	public void setSelectSitActPro(SituacionActProc selectSitActPro) {
		this.selectSitActPro = selectSitActPro;
	}

	public List<SituacionActProc> getSituacionActProcs() {
		return situacionActProcs;
	}

	public void setSituacionActProcs(List<SituacionActProc> situacionActProcs) {
		this.situacionActProcs = situacionActProcs;
	}

	public SituacionCuota getSelectSitCuota() {
		return selectSitCuota;
	}

	public void setSelectSitCuota(SituacionCuota selectSitCuota) {
		this.selectSitCuota = selectSitCuota;
	}

	public List<SituacionCuota> getSituacionCuotas() {
		return situacionCuotas;
	}

	public void setSituacionCuotas(List<SituacionCuota> situacionCuotas) {
		this.situacionCuotas = situacionCuotas;
	}

	public SituacionHonorario getSelectSitHonor() {
		return selectSitHonor;
	}

	public void setSelectSitHonor(SituacionHonorario selectSitHonor) {
		this.selectSitHonor = selectSitHonor;
	}

	public List<SituacionHonorario> getSituacionHonorarios() {
		return situacionHonorarios;
	}

	public void setSituacionHonorarios(List<SituacionHonorario> situacionHonorarios) {
		this.situacionHonorarios = situacionHonorarios;
	}

	public SituacionInculpado getSelectSitInc() {
		return selectSitInc;
	}

	public void setSelectSitInc(SituacionInculpado selectSitInc) {
		this.selectSitInc = selectSitInc;
	}

	public List<SituacionInculpado> getSituacionInculpados() {
		return situacionInculpados;
	}

	public void setSituacionInculpados(List<SituacionInculpado> situacionInculpados) {
		this.situacionInculpados = situacionInculpados;
	}

	public TipoCautelar getSelectTipCaut() {
		return selectTipCaut;
	}

	public void setSelectTipCaut(TipoCautelar selectTipCaut) {
		this.selectTipCaut = selectTipCaut;
	}

	public List<TipoCautelar> getTipoCautelars() {
		return tipoCautelars;
	}

	public void setTipoCautelars(List<TipoCautelar> tipoCautelars) {
		this.tipoCautelars = tipoCautelars;
	}

	public TipoExpediente getSelectTipExpe() {
		return selectTipExpe;
	}

	public void setSelectTipExpe(TipoExpediente selectTipExpe) {
		this.selectTipExpe = selectTipExpe;
	}

	public List<TipoExpediente> getTipoExpedientes() {
		return tipoExpedientes;
	}

	public void setTipoExpedientes(List<TipoExpediente> tipoExpedientes) {
		this.tipoExpedientes = tipoExpedientes;
	}

	public TipoHonorario getSelectTipHonor() {
		return selectTipHonor;
	}

	public void setSelectTipHonor(TipoHonorario selectTipHonor) {
		this.selectTipHonor = selectTipHonor;
	}

	public List<TipoHonorario> getTipoHonorarios() {
		return tipoHonorarios;
	}

	public void setTipoHonorarios(List<TipoHonorario> tipoHonorarios) {
		this.tipoHonorarios = tipoHonorarios;
	}

	public TipoInvolucrado getSelectTipInv() {
		return selectTipInv;
	}

	public void setSelectTipInv(TipoInvolucrado selectTipInv) {
		this.selectTipInv = selectTipInv;
	}

	public List<TipoInvolucrado> getTipoInvolucrados() {
		return tipoInvolucrados;
	}

	public void setTipoInvolucrados(List<TipoInvolucrado> tipoInvolucrados) {
		this.tipoInvolucrados = tipoInvolucrados;
	}

	public TipoProvision getSelectTipProv() {
		return selectTipProv;
	}

	public void setSelectTipProv(TipoProvision selectTipProv) {
		this.selectTipProv = selectTipProv;
	}

	public List<TipoProvision> getTipoProvisions() {
		return tipoProvisions;
	}

	public void setTipoProvisions(List<TipoProvision> tipoProvisions) {
		this.tipoProvisions = tipoProvisions;
	}

	public RolInvolucrado getSelectRolInv() {
		return selectRolInv;
	}

	public void setSelectRolInv(RolInvolucrado selectRolInv) {
		this.selectRolInv = selectRolInv;
	}

	public List<RolInvolucrado> getRolInvolucrados() {
		return rolInvolucrados;
	}

	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
	}

	public List<Proceso> getProcesos2() {
		return procesos2;
	}

	public void setProcesos2(List<Proceso> procesos2) {
		this.procesos2 = procesos2;
	}

	public Long getRucEstudio() {
		return rucEstudio;
	}

	public void setRucEstudio(Long rucEstudio) {
		this.rucEstudio = rucEstudio;
	}

	public Aviso getObjAviso() {
		return objAviso;
	}

	public void setObjAviso(Aviso objAviso) {
		this.objAviso = objAviso;
	}

	public Materia getSelectedMateria() {
		return selectedMateria;
	}

	public void setSelectedMateria(Materia selectedMateria) {
		this.selectedMateria = selectedMateria;
	}

	public Riesgo getSelectedRiesgo() {
		return selectedRiesgo;
	}

	public void setSelectedRiesgo(Riesgo selectedRiesgo) {
		this.selectedRiesgo = selectedRiesgo;
	}

	public TipoDocumento getSelectedTipoDoc() {
		return selectedTipoDoc;
	}

	public void setSelectedTipoDoc(TipoDocumento selectedTipoDoc) {
		this.selectedTipoDoc = selectedTipoDoc;
	}

	public Calificacion getSelectedCalificacion() {
		return selectedCalificacion;
	}

	public void setSelectedCalificacion(Calificacion selectedCalificacion) {
		this.selectedCalificacion = selectedCalificacion;
	}

	public GrupoBanca getSelectedGrupoBanca() {
		return selectedGrupoBanca;
	}

	public void setSelectedGrupoBanca(GrupoBanca selectedGrupoBanca) {
		this.selectedGrupoBanca = selectedGrupoBanca;
	}

	public Territorio getSelectedTerritorio() {
		return selectedTerritorio;
	}

	public void setSelectedTerritorio(Territorio selectedTerritorio) {
		this.selectedTerritorio = selectedTerritorio;
	}

	public Oficina getSelectedOficina() {
		return selectedOficina;
	}

	public void setSelectedOficina(Oficina selectedOficina) {
		this.selectedOficina = selectedOficina;
	}

	public Ubigeo getSelectedUbigeo() {
		return selectedUbigeo;
	}

	public void setSelectedUbigeo(Ubigeo selectedUbigeo) {
		this.selectedUbigeo = selectedUbigeo;
	}

	public Feriado getSelectedFeriado() {
		return selectedFeriado;
	}

	public void setSelectedFeriado(Feriado selectedFeriado) {
		this.selectedFeriado = selectedFeriado;
	}

	public Character getIndFeriado() {
		return indFeriado;
	}

	public void setIndFeriado(Character indFeriado) {
		this.indFeriado = indFeriado;
	}

	public List<Aviso> getLstAviso() {
		return lstAviso;
	}

	public void setLstAviso(List<Aviso> lstAviso) {
		this.lstAviso = lstAviso;
	}

	public List<Materia> getLstMateria() {
		return lstMateria;
	}

	public void setLstMateria(List<Materia> lstMateria) {
		this.lstMateria = lstMateria;
	}

	public List<Riesgo> getLstRiesgo() {
		return lstRiesgo;
	}

	public void setLstRiesgo(List<Riesgo> lstRiesgo) {
		this.lstRiesgo = lstRiesgo;
	}

	public List<TipoDocumento> getLstTipoDoc() {
		return lstTipoDoc;
	}

	public void setLstTipoDoc(List<TipoDocumento> lstTipoDoc) {
		this.lstTipoDoc = lstTipoDoc;
	}

	public List<Calificacion> getLstCalificacion() {
		return lstCalificacion;
	}

	public void setLstCalificacion(List<Calificacion> lstCalificacion) {
		this.lstCalificacion = lstCalificacion;
	}

	public List<Oficina> getLstOficina() {
		return lstOficina;
	}

	public void setLstOficina(List<Oficina> lstOficina) {
		this.lstOficina = lstOficina;
	}

	public String getIdUbigeoLst() {
		return idUbigeoLst;
	}

	public void setIdUbigeoLst(String idUbigeoLst) {
		this.idUbigeoLst = idUbigeoLst;
	}

	public List<Feriado> getLstFeriado() {
		return lstFeriado;
	}

	public void setLstFeriado(List<Feriado> lstFeriado) {
		this.lstFeriado = lstFeriado;
	}
	
}