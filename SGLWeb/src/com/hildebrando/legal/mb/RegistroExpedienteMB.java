package com.hildebrando.legal.mb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;
import com.hildebrando.legal.modelo.Abogado;
import com.hildebrando.legal.modelo.AbogadoEstudio;
import com.hildebrando.legal.modelo.Actividad;
import com.hildebrando.legal.modelo.ActividadProcesal;
import com.hildebrando.legal.modelo.Anexo;
import com.hildebrando.legal.modelo.Calificacion;
import com.hildebrando.legal.modelo.Clase;
import com.hildebrando.legal.modelo.ContraCautela;
import com.hildebrando.legal.modelo.Cuantia;
import com.hildebrando.legal.modelo.Cuota;
import com.hildebrando.legal.modelo.Entidad;
import com.hildebrando.legal.modelo.EstadoCautelar;
import com.hildebrando.legal.modelo.EstadoExpediente;
import com.hildebrando.legal.modelo.Estudio;
import com.hildebrando.legal.modelo.Etapa;
import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.modelo.Honorario;
import com.hildebrando.legal.modelo.Inculpado;
import com.hildebrando.legal.modelo.Instancia;
import com.hildebrando.legal.modelo.Involucrado;
import com.hildebrando.legal.modelo.Materia;
import com.hildebrando.legal.modelo.Moneda;
import com.hildebrando.legal.modelo.Oficina;
import com.hildebrando.legal.modelo.Organo;
import com.hildebrando.legal.modelo.Persona;
import com.hildebrando.legal.modelo.Proceso;
import com.hildebrando.legal.modelo.Recurrencia;
import com.hildebrando.legal.modelo.Resumen;
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
import com.hildebrando.legal.modelo.Ubigeo;
import com.hildebrando.legal.modelo.Usuario;
import com.hildebrando.legal.modelo.Via;
import com.hildebrando.legal.service.RegistroExpedienteService;
import com.hildebrando.legal.util.Util;
import com.hildebrando.legal.view.AbogadoDataModel;
import com.hildebrando.legal.view.CuantiaDataModel;
import com.hildebrando.legal.view.InvolucradoDataModel;
import com.hildebrando.legal.view.OrganoDataModel;
import com.hildebrando.legal.view.PersonaDataModel;

@ManagedBean(name = "registExpe")
@SessionScoped
public class RegistroExpedienteMB {

	public static Logger logger = Logger.getLogger(RegistroExpedienteMB.class);

	private int proceso;
	private List<Proceso> procesos;
	private int via;
	private List<Via> vias;
	private int instancia;
	private List<Instancia> instancias;
	private Usuario responsable;
	private String nroExpeOficial;
	private Date inicioProceso;
	private int estado;
	private List<EstadoExpediente> estados;
	private Oficina oficina;
	private int tipo;
	private Organo organo1;
	private List<TipoExpediente> tipos;
	private List<Entidad> entidades;
	private String secretario;
	private int calificacion;
	private List<Calificacion> calificaciones;
	private Recurrencia recurrencia;
	private Abogado abogado;
	private Estudio estudio;
	private AbogadoDataModel abogadoDataModel;
	private List<TipoHonorario> tipoHonorarios;
	private List<String> tipoHonorariosString;
	private Honorario honorario;
	private int contadorHonorario=0;
	private List<Cuota> cuotas;
	private List<Moneda> monedas;
	private List<String> monedasString;
	private List<SituacionHonorario> situacionHonorarios;
	private List<String> situacionHonorariosString;
	private List<SituacionCuota> situacionCuotas;
	private List<String> situacionCuotasString;
	private Involucrado involucrado;
	private Persona persona;
	private List<RolInvolucrado> rolInvolucrados;
	private List<String> rolInvolucradosString;
	private List<TipoInvolucrado> tipoInvolucrados;
	private List<String> tipoInvolucradosString;
	private InvolucradoDataModel involucradoDataModel;
	private Involucrado selectedInvolucrado;
	private List<Clase> clases;
	private List<TipoDocumento> tipoDocumentos;
	private PersonaDataModel personaDataModelBusq;
	private Cuantia cuantia;
	private Cuantia selectedCuantia;
	private CuantiaDataModel cuantiaDataModel;
	private List<SituacionInculpado> situacionInculpados;
	private List<String> situacionInculpadosString;
	private Inculpado inculpado;
	private Inculpado selectedInculpado;
	private List<Inculpado> inculpados;
	private int moneda;
	private double montoCautelar;
	private int tipoCautelar;
	private List<TipoCautelar> tipoCautelares;
	private String descripcionCautelar;
	private int contraCautela;
	private double importeCautelar;
	private int estadoCautelar;
	private List<EstadoCautelar> estadosCautelares;
	private int riesgo;
	private List<Riesgo> riesgos;

	private UploadedFile file;
	private Anexo anexo;
	private List<Anexo> anexos;
	private Anexo selectedAnexo;
	
	private String todoResumen;
	private String resumen;
	private Date fechaResumen;
	private List<Resumen> resumens;

	private Organo organo;
	private OrganoDataModel organoDataModel;
	private Organo selectedOrgano;

	private List<Honorario> honorarios;
	private Honorario selectedHonorario;
	private Persona selectPersona;
	private Persona selectInvolucrado;
	private List<ContraCautela> contraCautelas;
	private boolean tabAsigEstExt;
	private boolean tabCaucion;
	private boolean tabCuanMat;
	
	private Abogado selectedAbogado;
	
	private boolean reqPenal;
	private boolean reqCabecera;

	@ManagedProperty(value = "#{registroService}")
	private RegistroExpedienteService expedienteService;

	public void setExpedienteService(RegistroExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public void agregarTodoResumen(ActionEvent e) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		
		if(getFechaResumen() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha de Resumen Requerido", "Fecha de Resumen Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(getResumen() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Resumen Requerido", "Resumen Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				if(getTodoResumen()==null){
					
					setTodoResumen( "Jorge Guzman"+ "\n" +
									"\t" + getResumen() + "\n" +
									"\t" + "\t" + "\t" + "\t" + "\t" +
									"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(getFechaResumen()));
					
				}else{
					
					setTodoResumen( "Jorge Guzman" + "\n" +
									"\t" + getResumen() + "\n" +
									"\t" + "\t" + "\t" + "\t" + "\t" +
									"\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + format.format(getFechaResumen()) + "\n" +
									"---------------------------------------------------------------------------" + "\n" +
									getTodoResumen());
					
				}
				
				if (getResumens() == null) {
					setResumens(new ArrayList<Resumen>());
				}

				Resumen resumen= new Resumen();
				resumen.setTexto(getResumen());
				resumen.setFecha(getFechaResumen());
				
				getResumens().add(resumen);
				
				setResumen("");
				setFechaResumen(null);
				
			}
			
			
		}
		
		
		
		

	}

	public void deleteHonorario() {

		getHonorarios().remove(selectedHonorario);

	}

	public void deleteAnexo() {

		getAnexos().remove(selectedAnexo);

	}

	public void deleteInvolucrado() {

		List<Involucrado> involucrados = (List<Involucrado>) getInvolucradoDataModel().getWrappedData();
		involucrados.remove(getSelectedInvolucrado());

		involucradoDataModel = new InvolucradoDataModel(involucrados);
	}

	public void deleteCuantia() {

		List<Cuantia> cuantias = (List<Cuantia>) getCuantiaDataModel()
				.getWrappedData();
		cuantias.remove(getSelectedCuantia());
		cuantiaDataModel = new CuantiaDataModel(cuantias);
	}

	public void deleteInculpado() {

		inculpados.remove(getSelectedInculpado());

	}

	public void buscarAbogado(ActionEvent e) {
		
		logger.debug("Ingreso al Buscar Abogado..");
		List<Abogado> results = new ArrayList<Abogado>();
		List<AbogadoEstudio> abogadoEstudioBD = new ArrayList<AbogadoEstudio>();
		
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		Busqueda filtro2 = Busqueda.forClass(AbogadoEstudio.class);
		
		if(getEstudio() != null ){
			
			
			logger.debug("filtro "+ getEstudio().getIdEstudio()  +" abogado - estudio");
			
			filtro2.add(Restrictions.eq("estudio", getEstudio()));
			filtro2.add(Restrictions.like("estado", 'A'));
			
			try {
				
				abogadoEstudioBD = abogadoEstudioDAO.buscarDinamico(filtro2);
				logger.debug("hay "+ abogadoEstudioBD.size() +" estudios");
				
				List<Integer> idAbogados= new ArrayList<Integer>();
				
				for(AbogadoEstudio abogadoEstudio: abogadoEstudioBD){
					
					logger.debug("idabogado "+ abogadoEstudio.getAbogado().getIdAbogado());
					idAbogados.add(abogadoEstudio.getAbogado().getIdAbogado());
					
				}
		
				filtro.add(Restrictions.in("idAbogado",idAbogados));
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
			
		if(getAbogado().getRegistroca().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getRegistroca()   +" abogado - registro ca");
			
			filtro.add(Restrictions.eq("registroca", getAbogado().getRegistroca()));
			
		}

		if(getAbogado().getDni()!=0){
			

			logger.debug("filtro "+ getAbogado().getDni() +" abogado - dni");
			
			filtro.add(Restrictions.eq("dni", getAbogado().getDni()));
			
		}
		
		if(getAbogado().getNombres().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getNombres() +" abogado - nombres");
			filtro.add(Restrictions.like("nombres", "%"+getAbogado().getNombres()+"%").ignoreCase());
			
		}
		
		if(getAbogado().getApellidoPaterno().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getApellidoPaterno()  +" abogado - apellido paterno");
			filtro.add(Restrictions.like("apellidoPaterno", "%"+getAbogado().getApellidoPaterno()+"%").ignoreCase());
		}
		
		if(getAbogado().getApellidoMaterno().compareTo("")!=0){
			
			logger.debug("filtro "+getAbogado().getApellidoMaterno()+" abogado - apellido materno");
			filtro.add(Restrictions.like("apellidoMaterno", "%"+getAbogado().getApellidoMaterno()+"%"));
		}
		
		if(getAbogado().getTelefono().compareTo("")!=0){
			
			logger.debug("filtro "+ getAbogado().getTelefono()  +" abogado - telefono");
			filtro.add(Restrictions.eq("telefono", getAbogado().getTelefono()));
		}
		
		if(getAbogado().getCorreo().compareTo("")!=0){
			
			
			logger.debug("filtro "+ getAbogado().getCorreo()  +" abogado - correo");
			filtro.add(Restrictions.like("correo", "%"+getAbogado().getCorreo()+"%").ignoreCase());
		}
		
		try {
			
			results= abogadoDAO.buscarDinamico(filtro);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		logger.debug("trajo.."+ results.size() +" abogados");
		
		abogadoDataModel = new AbogadoDataModel(results);

	}

	public void agregarHonorario(ActionEvent e2) {
	
		
		if(honorario.getAbogado() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Abogado Requerido", "Abogado Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(honorario.getTipoHonorario().getDescripcion() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Honorario Requerido", "Honorario Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				if(honorario.getCantidad() == 0){
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cuotas Requerido", "Cuotas Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else{
					if(honorario.getMoneda().getSimbolo() == ""){
						
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Moneda Requerido", "Moneda Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
					}else{
						
						if(honorario.getMonto() == 0.0){
							
							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Monto Requerido", "Monto Requerido");
							FacesContext.getCurrentInstance().addMessage(null, msg);
				
						}else{
							
							if(honorario.getSituacionHonorario().getDescripcion() == ""){
								
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Situacion Requerido", "Situacion Requerido");
								FacesContext.getCurrentInstance().addMessage(null, msg);
				
							}else{
								
								for (TipoHonorario tipo : getTipoHonorarios()) {
									if (tipo.getDescripcion().compareTo(honorario.getTipoHonorario().getDescripcion()) == 0 ) {
										honorario.setTipoHonorario(tipo);
										break;
									}
								}
								for (Moneda moneda : getMonedas()) {
									if (moneda.getSimbolo().compareTo(honorario.getMoneda().getSimbolo()) == 0 ) {
										honorario.setMoneda(moneda);
										break;
									}

								}
								for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
									if (situacionHonorario.getDescripcion().compareTo(honorario.getSituacionHonorario().getDescripcion()) == 0) {
										honorario.setSituacionHonorario(situacionHonorario);
										break;
									}

								}
								
								List<AbogadoEstudio> abogadoEstudios= new ArrayList<AbogadoEstudio>();
								GenericDao<AbogadoEstudio, Object> abogadoEstudioDAO = (GenericDao<AbogadoEstudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
								Busqueda filtro = Busqueda.forClass(AbogadoEstudio.class);
								filtro.add(Restrictions.like("abogado", getHonorario().getAbogado()));
								filtro.add(Restrictions.like("estado", 'A'));

								try {
									abogadoEstudios = abogadoEstudioDAO.buscarDinamico(filtro);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								if(abogadoEstudios != null ){
									if(abogadoEstudios.size()!=0){
										honorario.setEstudio(abogadoEstudios.get(0).getEstudio().getNombre());
									}
								}
								
								
								//situacion pendiente
								if(honorario.getSituacionHonorario().getIdSituacionHonorario()==1){
									
									double importe = getHonorario().getMonto() / getHonorario().getCantidad().intValue();

									importe = Math.rint(importe*100)/100;

									SituacionCuota situacionCuota = getSituacionCuotas().get(0);
									
									honorario.setMontoPagado(0.0);
									
									honorario.setCuotas(new ArrayList<Cuota>());
									

									Calendar cal = Calendar.getInstance();
									for (int i = 1; i <= getHonorario().getCantidad().intValue(); i++) {
										Cuota cuota = new Cuota();
										cuota.setNumero(i);
										cuota.setMoneda(honorario.getMoneda().getSimbolo());
										cuota.setNroRecibo("000" + i);
										cuota.setImporte(importe);
										cal.add(Calendar.MONTH, 1);
										Date date = cal.getTime();
										cuota.setFechaPago(date);
										
										cuota.setSituacionCuota(new SituacionCuota());
										cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
										cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
										cuota.setFlagPendiente(true);
										
										honorario.addCuota(cuota);

									}
									
									honorario.setFlagPendiente(true);
									
								}else{
									
									honorario.setMontoPagado(honorario.getMonto());
									honorario.setFlagPendiente(false);
									
									
								}
								
								contadorHonorario++;
								honorario.setNumero(contadorHonorario);
								
								honorarios.add(honorario);

								honorario = new Honorario();
								honorario.setCantidad(0);
								honorario.setMonto(0.0);
				
							}
				
						}
						
					}
					
				}
				
			}
			
		}
		
		
	}

	public void agregarAnexo(ActionEvent en) {
		
		if(file == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cargar Archivo", "Cargar Archivo");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(anexo.getTitulo() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Titulo Requerido", "Titulo Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				if(anexo.getComentario() == ""){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Comentario Requerido", "Comentario Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else{
					
					if(anexo.getFechaInicio() == null){
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Inicio Requerido", "Fecha Inicio Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
					}else{
						
						byte[] fileBytes = getFile().getContents();
						//Blob b = Hibernate.createBlob(fileBytes);
						getAnexo().setBytes(fileBytes);
						getAnexo().setUbicacion(getFile().getFileName());
						getAnexo().setFormato(getFile().getFileName().substring(getFile().getFileName().lastIndexOf(".")+1).toUpperCase());
						
						getAnexos().add(getAnexo());
						setAnexo(new Anexo());
						setFile(null);
						
					}
					
				}
			}
		}

	}
	
	public void handleFileUpload(FileUploadEvent event){
		FacesMessage msg = new FacesMessage("Archivo ", event.getFile().getFileName() + " almacenado correctamente.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
		setFile(event.getFile());
		
	}

	public void agregarAbogado(ActionEvent e2) {

		logger.info("Ingreso al Agregar Abogado..");
		
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		List<Abogado> abogadosBD = new ArrayList<Abogado>();
		
		
		if(getAbogado().getDni() == 0 ||
				getAbogado().getNombres() == "" ||
					getAbogado().getApellidoPaterno() == "" ||
						getAbogado().getApellidoMaterno() == "" ){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Nro Documento, Nombres, Apellido Paterno, Apellido Materno", "Datos Requeridos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			Busqueda filtro = Busqueda.forClass(Abogado.class);
			filtro.add(Restrictions.eq("dni", getAbogado().getDni()));
			filtro.add(Restrictions.eq("nombres", getAbogado().getNombres()));
			filtro.add(Restrictions.eq("apellidoPaterno", getAbogado().getApellidoPaterno()));
			filtro.add(Restrictions.eq("apellidoMaterno", getAbogado().getApellidoMaterno()));
			
			try {
				abogadosBD = abogadoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Abogado abogadobd= new Abogado();
			
			if(abogadosBD.size() == 0){
				
				try {
					
					getAbogado().setNombreCompleto(getAbogado().getNombres() +" "+
												   getAbogado().getApellidoPaterno()+" "+
												   getAbogado().getApellidoMaterno());
					
					abogadobd = abogadoDAO.insertar(getAbogado());
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Abogado agregado", "Abogado agregado");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Abogado Existente", "Abogado Existente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			

			List<Abogado> abogados= new ArrayList<Abogado>();
			abogados.add(abogadobd);
			abogadoDataModel = new AbogadoDataModel(abogados);
		}
		
		
		

	}

	public void buscarPersona(ActionEvent e) {

		logger.debug("entro al buscar persona");
		
		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		
		Busqueda filtro = Busqueda.forClass(Persona.class);
		
		if(getPersona().getClase().getIdClase()!= 0){
			
			logger.debug("filtro "+ getPersona().getClase().getIdClase()  +" persona - clase");
			filtro.add(Restrictions.eq("clase.idClase", getPersona().getClase().getIdClase()));
		}
		
		if(getPersona().getTipoDocumento().getIdTipoDocumento()!= 0){
			
			logger.debug("filtro "+ getPersona().getTipoDocumento().getIdTipoDocumento() +" persona - tipo documento");
			filtro.add(Restrictions.eq("tipoDocumento.idTipoDocumento", getPersona().getTipoDocumento().getIdTipoDocumento()));
		}
		
		if(getPersona().getNumeroDocumento()!= 0){
			
			logger.debug("filtro "+ getPersona().getNumeroDocumento()  +" persona - numero documento");
			filtro.add(Restrictions.eq("numeroDocumento", getPersona().getNumeroDocumento()));
		}
		
		if(getPersona().getCodCliente()!= 0){
			
			logger.debug("filtro "+ getPersona().getCodCliente()  +" persona - cod cliente");
			filtro.add(Restrictions.eq("codCliente", getPersona().getCodCliente()));
		}
		
		if(getPersona().getNombres().compareTo("")!=0){
			
			logger.debug("filtro "+ getPersona().getNombres() +" persona - nombres");
			filtro.add(Restrictions.like("nombres","%"+getPersona().getNombres()+"%").ignoreCase());
		}
		
		if(getPersona().getApellidoPaterno().compareTo("")!=0){
			
			logger.debug("filtro "+ getPersona().getApellidoPaterno()  +" persona - apellido paterno");
			filtro.add(Restrictions.like("apellidoPaterno", "%"+getPersona().getApellidoPaterno()+"%").ignoreCase());
		}
		
		if(getPersona().getApellidoMaterno().compareTo("")!=0){
			
			logger.debug("filtro "+ getPersona().getApellidoMaterno()  +" persona - apellido materno");
			filtro.add(Restrictions.like("apellidoMaterno",  "%"+getPersona().getApellidoMaterno()+"%").ignoreCase());
		}
		
		try {
			personas = personaDAO.buscarDinamico(filtro);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		logger.debug("trajo .."+ personas.size());

		personaDataModelBusq = new PersonaDataModel(personas);

	}

	public void buscarOrganos(ActionEvent actionEvent) {


		logger.debug("entro al buscar organos");
		
		List<Organo> organos = new ArrayList<Organo>();
		
		//organos = expedienteService.buscarOrganos(getOrgano());
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		if(getOrgano().getEntidad().getIdEntidad()!= 0){
			
			logger.debug("filtro "+ getOrgano().getEntidad().getIdEntidad()  +" organo - entidad");			
			filtro.add(Restrictions.eq("entidad.idEntidad", getOrgano().getEntidad().getIdEntidad()));
		}
		
		if(getOrgano().getNombre().compareTo("")!=0){
			
			logger.debug("filtro "+ getOrgano().getNombre() +" organo - nombre");
			filtro.add(Restrictions.like("nombre", "%"+getOrgano().getNombre()+"%").ignoreCase());
		}
		
		if(getOrgano().getUbigeo()!= null){

			logger.debug("filtro "+getOrgano().getUbigeo().getCodDist()+" organo - territorio");
			filtro.add(Restrictions.eq("ubigeo.codDist", getOrgano().getUbigeo().getCodDist()));
			
		}
		
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.debug("trajo .."+ organos.size());
		
		organoDataModel = new OrganoDataModel(organos);

	}

	public void agregarOrgano(ActionEvent e2) {

		List<Organo> organos = new ArrayList<Organo>();
		List<Territorio> territorios = new ArrayList<Territorio>();

		//organos = expedienteService.buscarOrganos(getOrgano());
	
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		
		
		if (getOrgano().getEntidad().getIdEntidad() == 0
				|| getOrgano().getNombre() == ""
				|| getOrgano().getUbigeo() == null) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Entidad, Organo, Distrito", "Datos Requeridos: Entidad, Organo, Distrito");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		}else{
			
			filtro.add(Restrictions.eq("entidad.idEntidad", getOrgano().getEntidad().getIdEntidad()));
			filtro.add(Restrictions.eq("nombre", getOrgano().getNombre()));
			filtro.add(Restrictions.eq("ubigeo.codDist", getOrgano().getUbigeo().getCodDist()));
			
			try {
				organos = organoDAO.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			Organo organobd=new Organo();
			
			if(organos.size() == 0){
				
				try {
					
					organobd= organoDAO.insertar(getOrgano());
					
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Organo Agregado", "Organo Agregado"));
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Organo Existente", "Organo Existente"));
			}
		
			List<Organo> organos2= new ArrayList<Organo>();
			organos2.add(organobd);
			organoDataModel = new OrganoDataModel(organos2);
			
		}
		

	}

	public void agregarCuantia(ActionEvent e) {

		
			
			if(cuantia.getMoneda().getSimbolo() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Moneda Requerido", "Moneda Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				if(cuantia.getPretendido() == 0.0){
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Pretendido Requerido", "Pretendido Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else{
						
						for (Moneda m : getMonedas()) {
							if (m.getSimbolo().equals(getCuantia().getMoneda().getSimbolo())) {
								cuantia.setMoneda(m);
								break;
							}

						}

						List<Cuantia> cuantias;
						if (cuantiaDataModel == null) {
							cuantias = new ArrayList<Cuantia>();
						} else {
							cuantias = (List<Cuantia>) cuantiaDataModel.getWrappedData();
						}

						cuantias.add(getCuantia());

						cuantiaDataModel = new CuantiaDataModel(cuantias);

						cuantia = new Cuantia();
					
				}
				
			}
		
	}

	public void agregarInvolucrado(ActionEvent e) {

		
		if(involucrado.getPersona() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre Requerido", "Nombre Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(involucrado.getRolInvolucrado().getNombre() == ""){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Rol Requerido", "Abogado Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				
				for (RolInvolucrado rol : getRolInvolucrados()) {
					if (rol.getNombre() == getInvolucrado().getRolInvolucrado()
							.getNombre()) {
						involucrado.setRolInvolucrado(rol);
						break;
					}
				}

				for (TipoInvolucrado tipo : getTipoInvolucrados()) {
					if (tipo.getNombre() == getInvolucrado().getTipoInvolucrado()
							.getNombre()) {
						involucrado.setTipoInvolucrado(tipo);
						break;
					}
				}

				List<Involucrado> involucrados;
				if (involucradoDataModel == null) {
					involucrados = new ArrayList<Involucrado>();
				} else {
					involucrados = (List<Involucrado>) involucradoDataModel
							.getWrappedData();
				}

				involucrados.add(getInvolucrado());
				involucradoDataModel = new InvolucradoDataModel(involucrados);

				involucrado = new Involucrado();
				
			}
			
			
		}
		

	}

	public void agregarInculpado(ActionEvent e) {

		if(inculpado.getPersona() == null){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Inculpado Requerido", "Inculpado Requerido");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			if(	inculpado.getFecha()  == null){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fecha Requerido", "Fecha Requerido");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				
			}else{
				
				if(inculpado.getMoneda().getSimbolo() == ""){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Moneda Requerido", "Materia Requerido");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
					
				}else{
					
					if(inculpado.getMonto() == 0.0){
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Monto Requerido", "Monto Requerido");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						
					}else{
						
						if(inculpado.getNrocupon() == 0){

							FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Numero Cupon Requerido", "Numero Cupon Requerido");
							FacesContext.getCurrentInstance().addMessage(null, msg);
							
							
						}else{
							
							if(inculpado.getSituacionInculpado().getNombre() == ""){
								
								FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Situacion Requerido", "Situacion Requerido");
								FacesContext.getCurrentInstance().addMessage(null, msg);
								
							}else{
								

								for (Moneda moneda : getMonedas()) {
									if (moneda.getSimbolo().equals(
											getInculpado().getMoneda().getSimbolo()))
										inculpado.setMoneda(moneda);
								}

								for (SituacionInculpado situac : getSituacionInculpados()) {
									if (situac.getNombre().equals(
											getInculpado().getSituacionInculpado().getNombre()))
										inculpado.setSituacionInculpado(situac);
								}

								if (inculpados == null) {

									inculpados = new ArrayList<Inculpado>();
								}

								inculpados.add(getInculpado());

								inculpado = new Inculpado();
								
							}
							
						}
						
					}
				}
				
			}
			
			
		}
		

	}

	public void agregarPersona(ActionEvent e) {

		logger.info("Ingreso a agregarDetallePersona..");

		if(getPersona().getClase().getIdClase()==0 || 
					getPersona().getTipoDocumento().getIdTipoDocumento()==0 ||
						getPersona().getNumeroDocumento() ==0 ||
							getPersona().getNombres() == "" ||
								getPersona().getApellidoMaterno() == "" ||
									getPersona().getApellidoPaterno() == ""){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Datos Requeridos: Clase, Tipo Doc, Nro Documento, Nombre, Apellido Paterno, Apellido Materno", "Datos Requeridos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else{
			
			List<Persona> personas= new ArrayList<Persona>();
			GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
			List<Persona> personaBD = new ArrayList<Persona>();
			
			Busqueda filtro = Busqueda.forClass(Persona.class);
			filtro.add(Restrictions.eq("clase.idClase", getPersona().getClase().getIdClase()));
			
			if(getPersona().getCodCliente() != 0)
				filtro.add(Restrictions.eq("codCliente", getPersona().getCodCliente()));
			
			filtro.add(Restrictions.eq("tipoDocumento.idTipoDocumento", getPersona().getTipoDocumento().getIdTipoDocumento()));
			filtro.add(Restrictions.eq("numeroDocumento", getPersona().getNumeroDocumento()));
			filtro.add(Restrictions.eq("nombres", getPersona().getNombres()));
			filtro.add(Restrictions.eq("apellidoPaterno", getPersona().getApellidoPaterno()));
			filtro.add(Restrictions.eq("apellidoMaterno", getPersona().getApellidoMaterno()));
			
			try {
				personas = personaDAO.buscarDinamico(filtro);
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			Persona personabd=  new Persona();
			
			if(personas.size() == 0){
				
				try {
					getPersona().setNombreCompleto(getPersona().getNombres()+" "+
												    getPersona().getApellidoPaterno()+" "+
												    getPersona().getApellidoMaterno());
					personabd = personaDAO.insertar(getPersona());
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Persona agregada", "Persona agregada");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Persona Existente", "Persona Existente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			List<Persona> personas2= new ArrayList<Persona>();
			personas2.add(personabd);
			personaDataModelBusq = new PersonaDataModel(personas2);
			
		}
		

	}

	public String agregarDetalleInculpado(ActionEvent e) {

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Inculpado Agregado", "Inculpado Agregado");

		FacesContext.getCurrentInstance().addMessage(null, msg);

		List<Persona> personas = new ArrayList<Persona>();
		personaDataModelBusq = new PersonaDataModel(personas);

		return null;

	}

	public void seleccionarOrgano() {

		String descripcion = getSelectedOrgano().getNombre().toUpperCase() + " ("
							+ getSelectedOrgano().getUbigeo().getDistrito().toUpperCase() + ", "
							+ getSelectedOrgano().getUbigeo().getProvincia().toUpperCase()+ ", "
							+ getSelectedOrgano().getUbigeo().getDepartamento().toUpperCase()
							+ ")";

		getSelectedOrgano().setNombreDetallado(descripcion);
			
		setOrgano1(getSelectedOrgano());

	}
	
	public void seleccionarAbogado() {

		getSelectedAbogado().setNombreCompletoMayuscula(getSelectedAbogado().getNombres().toUpperCase()
				+ " " + getSelectedAbogado().getApellidoPaterno().toUpperCase() + " "
				+ getSelectedAbogado().getApellidoMaterno().toUpperCase());
		
		getHonorario().setAbogado(getSelectedAbogado());

	}
	
	public void seleccionarPersona() {

		getSelectPersona().setNombreCompletoMayuscula(
				getSelectPersona().getNombres().toUpperCase()+ " " + 
				getSelectPersona().getApellidoPaterno().toUpperCase() + " "+ 
				getSelectPersona().getApellidoMaterno().toUpperCase());
		
		getInvolucrado().setPersona(getSelectPersona());
		

	}
	
	public void seleccionarInvolucrado() {

		getSelectInvolucrado().setNombreCompletoMayuscula(
						getSelectInvolucrado().getNombres().toUpperCase()+ " " + 
						getSelectInvolucrado().getApellidoPaterno().toUpperCase() + " "+ 
						getSelectInvolucrado().getApellidoMaterno().toUpperCase());
		
		getInculpado().setPersona(getSelectInvolucrado());
		

	}
	
	public void limpiarAnexo(ActionEvent e) {

		setAnexo(new Anexo());

	}
	
	public void limpiarOrgano(ActionEvent e) {

		setOrgano(new Organo());
		getOrgano().setEntidad(new Entidad());
		getOrgano().setUbigeo(new Ubigeo());
		
		organoDataModel = new OrganoDataModel(new ArrayList<Organo>());
		
	}
	
	public void limpiarAbogado(ActionEvent e) {

		setAbogado(new Abogado());
		getAbogado().setDni(null);
		
		setEstudio(new Estudio());
		
		abogadoDataModel = new AbogadoDataModel(new ArrayList<Abogado>());
	}

	public void limpiarPersona(ActionEvent e) {

		setPersona(new Persona());
		getPersona().setClase(new Clase());
		getPersona().setCodCliente(null);
		getPersona().setTipoDocumento(new TipoDocumento());
		getPersona().setNumeroDocumento(null);
		
		personaDataModelBusq = new PersonaDataModel(new ArrayList<Persona>());
	}
	
	public void limpiar(ActionEvent e) {

		logger.debug("limpiando los valores de la pantalla principal del expediente");
		Calendar calendar= Calendar.getInstance();
		
		setNroExpeOficial("");
		setInicioProceso(null);
		setEstado(0);
		setProceso(0);
		setVia(0);
		setInstancia(0);
		setResponsable(new Usuario());
		setOficina(new Oficina());
		setTipo(0);
		setOrgano1(new Organo());
		setSecretario("");
		setCalificacion(0);
		setRecurrencia(new Recurrencia());

		setHonorario(new Honorario());
		setHonorarios(new ArrayList<Honorario>());

		setInvolucrado(new Involucrado());
		setInvolucradoDataModel(new InvolucradoDataModel());

		setCuantia(new Cuantia());
		setCuantiaDataModel(new CuantiaDataModel());

		setInculpado(new Inculpado());
		setInculpados(new ArrayList<Inculpado>());

		setMoneda(0);
		setMontoCautelar(0.0);
		setTipoCautelar(0);
		setDescripcionCautelar("");
		setContraCautela(0);
		setImporteCautelar(0.0);
		setEstadoCautelar(0);

		setFechaResumen(null);
		setResumen("");
		setTodoResumen("");
		setResumens(new ArrayList<Resumen>());

		setAnexo(new Anexo());
		setAnexos(new ArrayList<Anexo>());
		
		setRiesgo(0);

		/*FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		com.grupobbva.seguridad.client.domain.Usuario usuarioAux= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(true);
		session.setAttribute("usuario", usuarioAux);*/
		
	}

	@SuppressWarnings("unchecked")
	public String home() {

		
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		com.grupobbva.seguridad.client.domain.Usuario usuarioAux= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(true);
		session.setAttribute("usuario", usuarioAux);
		

		return "consultaExpediente.xhtml?faces-redirect=true";
	}
	
	@SuppressWarnings("unchecked")
	public void guardar(ActionEvent event) {

		GenericDao<Expediente, Object> expedienteDAO = 
				(GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");

		Busqueda filtro = Busqueda.forClass(Expediente.class);
		filtro.add(Restrictions.like("numeroExpediente", getNroExpeOficial()));

		try {
			List<Expediente> expedientes= expedienteDAO.buscarDinamico(filtro);
			
			if(expedientes.size()==0){
				
				logger.debug("No existe expediente con "+ getNroExpeOficial());
				
				
				Expediente expediente = new Expediente();
				
				// expedienteService.registrar(expediente);

				GenericDao<EstadoExpediente, Object> estadoExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<Via, Object> viaDAO = (GenericDao<Via, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<Instancia, Object> instanciaDAO = (GenericDao<Instancia, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");

				EstadoExpediente estadoExpedientebd = new EstadoExpediente();
				Proceso procesobd= new Proceso();
				Via viabd= new Via();
				Instancia instanciabd = new Instancia();
				TipoExpediente tipoExpedientebd = new TipoExpediente();
				Calificacion calificacionbd = new Calificacion();
				try {
					estadoExpedientebd = estadoExpedienteDAO.buscarById(
							EstadoExpediente.class, getEstado());
					procesobd = procesoDAO.buscarById(
							Proceso.class, getProceso());
					viabd = viaDAO.buscarById(
							Via.class, getVia());
					instanciabd = instanciaDAO.buscarById(Instancia.class,
							getInstancia());
					tipoExpedientebd = tipoExpedienteDAO.buscarById(
							TipoExpediente.class, getTipo());
					calificacionbd = calificacionDAO.buscarById(Calificacion.class,
							getCalificacion());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				expediente.setNumeroExpediente(getNroExpeOficial());
				expediente.setFechaInicioProceso(getInicioProceso());
				expediente.setEstadoExpediente(estadoExpedientebd);
				expediente.setProceso(procesobd);
				expediente.setVia(viabd);
				expediente.setInstancia(instanciabd);
				expediente.setUsuario(getResponsable());
				expediente.setOficina(getOficina());
				expediente.setTipoExpediente(tipoExpedientebd);
				expediente.setOrgano(getOrgano1());
				expediente.setSecretario(getSecretario());
				expediente.setCalificacion(calificacionbd);
				expediente.setRecurrencia(getRecurrencia());

				List<Honorario> honorarios = getHonorarios();
				expediente.setHonorarios(new ArrayList<Honorario>());
				for (Honorario honorario : honorarios) {
					if (honorario != null) {
						for (TipoHonorario tipo : getTipoHonorarios()) {
							if (honorario.getTipoHonorario().getDescripcion()
									.equals(tipo.getDescripcion())) {
								honorario.setTipoHonorario(tipo);
								break;
							}
						}

						for (Moneda moneda : getMonedas()) {
							if (honorario.getMoneda().getSimbolo()
									.equals(moneda.getSimbolo())) {
								honorario.setMoneda(moneda);
								break;
							}

						}

						for (SituacionHonorario situacionHonorario : getSituacionHonorarios()) {
							if (honorario.getSituacionHonorario().getDescripcion()
									.equals(situacionHonorario.getDescripcion())) {
								honorario.setSituacionHonorario(situacionHonorario);
								break;
							}

						}

						expediente.addHonorario(honorario);
					}

				}

				List<Involucrado> involucrados = (List<Involucrado>) getInvolucradoDataModel()
						.getWrappedData();
				expediente.setInvolucrados(new ArrayList<Involucrado>());
				for (Involucrado involucrado : involucrados) {

					if (involucrado != null) {

						for (RolInvolucrado rol : getRolInvolucrados()) {
							if (rol.getNombre().equals(involucrado.getRolInvolucrado().getNombre())) {
								involucrado.setRolInvolucrado(rol);
								break;
							}
						}

						for (TipoInvolucrado tipo : getTipoInvolucrados()) {
							if (tipo.getNombre().equals(involucrado.getTipoInvolucrado().getNombre())) {
								involucrado.setTipoInvolucrado(tipo);
								break;
							}
						}

						expediente.addInvolucrado(involucrado);
					}
				}

				List<Cuantia> cuantias = (List<Cuantia>) getCuantiaDataModel()
						.getWrappedData();
				expediente.setCuantias(new ArrayList<Cuantia>());
				for (Cuantia cuantia : cuantias) {
					if (cuantia != null) {

						for (Moneda m : getMonedas()) {
							if (m.getSimbolo().equals(cuantia.getMoneda().getSimbolo())) {
								cuantia.setMoneda(m);
								break;
							}

						}

						expediente.addCuantia(cuantia);
					}
				}

				List<Inculpado> inculpados = getInculpados();
				expediente.setInculpados(new ArrayList<Inculpado>());
				for (Inculpado inculpado : inculpados) {
					if (inculpado != null) {

						for (Moneda moneda : getMonedas()) {
							if (moneda.getSimbolo().equals(inculpado.getMoneda().getSimbolo())){
								inculpado.setMoneda(moneda);
								break;
							}
								
						}

						for (SituacionInculpado s : getSituacionInculpados()) {
							if (s.getNombre().equals(inculpado.getSituacionInculpado().getNombre())){
								inculpado.setSituacionInculpado(s);
								break;
							}
								
						}

						expediente.addInculpado(inculpado);
					}
				}

				GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");

				Moneda monedabd = new Moneda();
				TipoCautelar tipoCautelarbd = new TipoCautelar();
				ContraCautela contraCautelabd = new ContraCautela();
				EstadoCautelar estadoCautelarbd = new EstadoCautelar();

				try {
					monedabd = monedaDAO.buscarById(Moneda.class, getMoneda());
					tipoCautelarbd = tipoCautelarDAO.buscarById(TipoCautelar.class,
							getTipoCautelar());
					contraCautelabd = contraCautelaDAO.buscarById(ContraCautela.class,
							getContraCautela());
					estadoCautelarbd = estadoCautelarDAO.buscarById(
							EstadoCautelar.class, getEstadoCautelar());
				} catch (Exception e) {

				}

				expediente.setMoneda(monedabd);
				expediente.setMontoCautelar(getMontoCautelar());
				expediente.setTipoCautelar(tipoCautelarbd);
				expediente.setDescripcionCautelar(getDescripcionCautelar());
				expediente.setContraCautela(contraCautelabd);
				expediente.setImporteCautelar(getImporteCautelar());
				expediente.setEstadoCautelar(estadoCautelarbd);
				
				List<Resumen> resumens = getResumens();
				expediente.setResumens(new ArrayList<Resumen>());
				
				for (Resumen resumen : resumens)
					if (resumen != null)
						expediente.addResumen(resumen);

				
						
				List<Anexo> anexos = getAnexos();
				expediente.setAnexos(new ArrayList<Anexo>());
				
				if(anexos!=null){
					if(anexos.size()!=0){
						
						File fichUbicacion;
						String ubicacion="";
						
						if(expediente.getInstancia() == null){
							
							ubicacion= Util.getMessage("ruta_documento") 
									+ File.separator + expediente.getNumeroExpediente() 
									+ File.separator + "sin-instancia" ;
							
						}else{
							
							ubicacion= Util.getMessage("ruta_documento") 
									+ File.separator + expediente.getNumeroExpediente() 
									+ File.separator + expediente.getInstancia().getNombre();
						}
						
						fichUbicacion= new File(ubicacion);
						fichUbicacion.mkdirs();
						
						for (Anexo anexo : anexos)
							if (anexo != null){
								
								anexo.setUbicacion(ubicacion + File.separator + anexo.getUbicacion());
								
								byte b[]= anexo.getBytes();
								File fichSalida= new File(anexo.getUbicacion());
								try {
									FileOutputStream canalSalida = new FileOutputStream(fichSalida);
									canalSalida.write(b);
									canalSalida.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								expediente.addAnexo(anexo);
							}		
					}
				}
				
				

				GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<Actividad, Object> actividadDAO = (GenericDao<Actividad, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<SituacionActProc, Object> situacionActProcDAO = (GenericDao<SituacionActProc, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");
				GenericDao<Etapa, Object> etapaDAO = (GenericDao<Etapa, Object>) SpringInit
						.getApplicationContext().getBean("genericoDao");

				filtro = Busqueda.forClass(Actividad.class);

				Riesgo riesgobd = new Riesgo();
				List<Actividad> actividades = new ArrayList<Actividad>();
				SituacionActProc situacionActProc = new SituacionActProc();
				Etapa etapabd= new Etapa();

				try {
					riesgobd = riesgoDAO.buscarById(Riesgo.class, getRiesgo());
					actividades = actividadDAO.buscarDinamico(filtro);
					situacionActProc = situacionActProcDAO.buscarById(SituacionActProc.class, 1);
					etapabd = etapaDAO.buscarById(Etapa.class,1);
					
				} catch (Exception e) {

				}

				expediente.setRiesgo(riesgobd);
				expediente.setActividadProcesals(new ArrayList<ActividadProcesal>());

				// si es un proceso civil
				if (procesobd.getIdProceso() == 1) {

					if (actividades != null) {

						for (Actividad actividad : actividades) {

							ActividadProcesal actividadProcesal = new ActividadProcesal();
							actividadProcesal.setSituacionActProc(situacionActProc);
							actividadProcesal.setEtapa(etapabd);
							actividadProcesal.setFechaActividad(getInicioProceso());
							
							Calendar calendar = Calendar.getInstance();  
							calendar.setTime(getInicioProceso());  
							
							
							if (actividad.getIdActividad() == 1) {
								
								actividadProcesal.setActividad(actividad);
								actividadProcesal.setPlazoLey("5");
					
								calendar.add(Calendar.DAY_OF_MONTH, 5);

								actividadProcesal.setFechaVencimiento(calendar.getTime());
								expediente.addActividadProcesal(actividadProcesal);
							}
							if (actividad.getIdActividad() == 2) {

								actividadProcesal.setActividad(actividad);		
								actividadProcesal.setPlazoLey("9");
								
								calendar.add(Calendar.DAY_OF_MONTH, 9);
								
								actividadProcesal.setFechaVencimiento(calendar.getTime());
								expediente.addActividadProcesal(actividadProcesal);
							}
							if ( actividad.getIdActividad() == 4) {

								actividadProcesal.setActividad(actividad);
								actividadProcesal.setPlazoLey("7");
								
								calendar.add(Calendar.DAY_OF_MONTH, 7);
								
								actividadProcesal.setFechaVencimiento(calendar.getTime());
								expediente.addActividadProcesal(actividadProcesal);
							}

						}
					}

				}
				
//				// si es un proceso penal
//				if (procesobd.getIdProceso() == 2) {
//					
//					
//					
//				}

				try {
					expedienteDAO.insertar(expediente);
					FacesContext.getCurrentInstance().addMessage("growl",new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso","Registro el expediente"));
					logger.debug("Registro el expediente exitosamente!");
					
				} catch (Exception e) {

					FacesContext.getCurrentInstance().addMessage("growl",new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Exitoso","No Registro el expediente "));
					logger.debug("No registro el expediente!"+ e.getMessage());
				}
				
			}else{
				
				FacesContext.getCurrentInstance().addMessage("growl",new FacesMessage(FacesMessage.SEVERITY_ERROR,"Existe expediente","Expediente ya existe con numero "));
				logger.debug("Ya existe expediente con "+ getNroExpeOficial());
				
			}
			

		} catch (Exception e) {

			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growl",new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!","Error al buscar el expediente!"));
			logger.debug("Error al buscar el expediente!");
		}
		
		
		

	}

	public List<Recurrencia> completeRecurrencia(String query) {

		List<Recurrencia> recurrencias = new ArrayList<Recurrencia>();
		GenericDao<Recurrencia, Object> recurrenciaDAO = (GenericDao<Recurrencia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Recurrencia.class);
		try {
			recurrencias = recurrenciaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Recurrencia> results = new ArrayList<Recurrencia>();

		for (Recurrencia rec : recurrencias) {
			if (rec.getNombre().toUpperCase().contains(query.toUpperCase())) {
				results.add(rec);
			}
		}

		return results;
	}

	public List<Materia> completeMaterias(String query) {
		List<Materia> results = new ArrayList<Materia>();

		List<Materia> listMateriasBD = new ArrayList<Materia>();
		GenericDao<Materia, Object> materiaDAO = (GenericDao<Materia, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Materia.class);
		try {
			listMateriasBD = materiaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Materia mat : listMateriasBD) {
			if (mat.getDescripcion().toLowerCase()
					.startsWith(query.toLowerCase())) {
				results.add(mat);
			}
		}

		return results;
	}

	public List<Persona> completePersona(String query) {
		List<Persona> results = new ArrayList<Persona>();

		List<Persona> personas = new ArrayList<Persona>();
		GenericDao<Persona, Object> personaDAO = (GenericDao<Persona, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Persona.class);
		try {
			personas = personaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Persona pers : personas) {
			if (pers.getNombres().toUpperCase().startsWith(query.toUpperCase())
					|| pers.getApellidoPaterno().toUpperCase()
							.startsWith(query.toUpperCase())
					|| pers.getApellidoMaterno().toUpperCase()
							.startsWith(query.toUpperCase())) {

				pers.setNombreCompletoMayuscula(pers.getNombres().toUpperCase()
						+ " " + pers.getApellidoPaterno().toUpperCase() + " "
						+ pers.getApellidoMaterno().toUpperCase());

				results.add(pers);
			}

		}

		return results;
	}

	public List<Oficina> completeOficina(String query) {

		List<Oficina> results = new ArrayList<Oficina>();

		List<Oficina> oficinas = new ArrayList<Oficina>();
		GenericDao<Oficina, Object> oficinaDAO = (GenericDao<Oficina, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Oficina.class);
		try {
			oficinas = oficinaDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Oficina oficina : oficinas) {
			
			if(oficina.getUbigeo() != null){
				
				String texto = oficina.getCodigo() + " " + 
							   oficina.getNombre().toUpperCase() + 
						       " (" + oficina.getUbigeo().getDepartamento().toUpperCase() + ")";
				
				if (texto.contains(query.toUpperCase())) {
					oficina.setNombreDetallado(texto);
					results.add(oficina);
				}
				
			}
			
		}

		return results;
	}

	public List<Estudio> completeEstudio(String query) {

		List<Estudio> estudios = new ArrayList<Estudio>();
		GenericDao<Estudio, Object> estudioDAO = (GenericDao<Estudio, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Estudio.class);
		
		try {
			
			estudios = estudioDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Estudio> results = new ArrayList<Estudio>();

		for (Estudio est : estudios) {
			if (est.getNombre().toUpperCase().contains(query.toUpperCase())) {
				results.add(est);
			}
		}

		return results;
	}

	public List<Abogado> completeAbogado(String query) {

		List<Abogado> abogados = new ArrayList<Abogado>();
		GenericDao<Abogado, Object> abogadoDAO = (GenericDao<Abogado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Abogado.class);
		try {
			abogados = abogadoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Abogado> results = new ArrayList<Abogado>();

		for (Abogado abog : abogados) {

			if (abog.getNombres().toUpperCase().startsWith(query.toUpperCase())
					|| abog.getApellidoPaterno().toUpperCase()
							.startsWith(query.toUpperCase())
					|| abog.getApellidoMaterno().toUpperCase()
							.startsWith(query.toUpperCase())) {

				abog.setNombreCompletoMayuscula(abog.getNombres().toUpperCase()
						+ " " + abog.getApellidoPaterno().toUpperCase() + " "
						+ abog.getApellidoMaterno().toUpperCase());

				results.add(abog);
			}

		}

		return results;
	}

	public List<Organo> completeOrgano(String query) {
		List<Organo> results = new ArrayList<Organo>();

		List<Organo> organos = new ArrayList<Organo>();
		GenericDao<Organo, Object> organoDAO = (GenericDao<Organo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Organo.class);
		try {
			organos = organoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Organo organo : organos) {
			String descripcion = organo.getNombre().toUpperCase() + " ("
					+ organo.getUbigeo().getDistrito().toUpperCase() + ", "
					+ organo.getUbigeo().getProvincia().toUpperCase()
					+ ", "
					+ organo.getUbigeo().getDepartamento().toUpperCase()
					+ ")";

			if (descripcion.toUpperCase().contains(query.toUpperCase())) {

				organo.setNombreDetallado(descripcion);
				results.add(organo);
			}
		}

		return results;
	}

	public List<Ubigeo> completeDistrito(String query) {
		List<Ubigeo> results = new ArrayList<Ubigeo>();

		List<Ubigeo> ubigeos = new ArrayList<Ubigeo>();
		GenericDao<Ubigeo, Object> ubigeoDAO = (GenericDao<Ubigeo, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Ubigeo.class);
		
		try {
			ubigeos = ubigeoDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Ubigeo ubig : ubigeos) {
			if (ubig.getDistrito().toUpperCase().startsWith(query.toUpperCase())) {
				String texto = ubig.getDistrito() + "," + ubig.getProvincia()+ "," + ubig.getDepartamento();
				ubig.setDescripcionDistrito(texto);
				results.add(ubig);
			}
		}

		return results;
	}

	// autocompletes
	public List<Usuario> completeResponsable(String query) {
		List<Usuario> results = new ArrayList<Usuario>();

		List<Usuario> usuarios = new ArrayList<Usuario>();
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Usuario.class);
		
		//filtro.add(Restrictions.like("rol.proceso.idProceso", getProceso()));
		
		try {
			
			usuarios = usuarioDAO.buscarDinamico(filtro);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Usuario usuario : usuarios) {

				if (usuario.getNombres().toUpperCase().contains(query.toUpperCase())
						|| usuario.getApellidoPaterno().toUpperCase().contains(query.toUpperCase())
								|| usuario.getApellidoMaterno().toUpperCase().contains(query.toUpperCase())
									|| usuario.getCodigo().toUpperCase().contains(query.toUpperCase())) {

					usuario.setNombreDescripcion(
							usuario.getCodigo() + " - " +
							usuario.getNombres() + " " +
							usuario.getApellidoPaterno() + " " +
							usuario.getApellidoMaterno());

					results.add(usuario);
				}
			
		}

		return results;
	}

	// listener cada vez que se modifica el proceso
	public void cambioProceso() {

		setTabAsigEstExt(false);
		setTabCuanMat(false);
		setTabCaucion(false);

		if (getProceso() != 0) {

			if (getProceso() == 1 || getProceso() == 3) {

				setTabCaucion(true);
				setReqPenal(true);
				setReqCabecera(true);
			}

			if (getProceso() == 2) {

				setTabAsigEstExt(true);
				setTabCuanMat(true);
				
				setReqPenal(true);
				setReqCabecera(false);
			}

			GenericDao<Via, Object> viaDao = (GenericDao<Via, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Via.class);
			filtro.add(Restrictions.like("proceso.idProceso", getProceso()));

			try {
				vias = viaDao.buscarDinamico(filtro);
			} catch (Exception e) {
				e.printStackTrace();
			}

			instancias = new ArrayList<Instancia>();

		} else {
			vias = new ArrayList<Via>();

		}

	}

	// listener cada vez que se modifica la via
	public void cambioVia() {

		if (getVia() != 0) {

			GenericDao<Instancia, Object> instanciaDao = (GenericDao<Instancia, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			Busqueda filtro = Busqueda.forClass(Instancia.class);
			filtro.add(Restrictions.like("via.idVia", getVia()));

			try {
				instancias = instanciaDao.buscarDinamico(filtro);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			instancias = new ArrayList<Instancia>();

		}

	}

	public RegistroExpedienteMB() {
		logger.debug("Inicializando valores registro expediente");

		inicializarValores();

		cargarCombos();

	}

	@SuppressWarnings("unchecked")
	private void cargarCombos() {

		logger.debug("Cargando combos para registro expediente");
		GenericDao<EstadoExpediente, Object> estadosExpedienteDAO = (GenericDao<EstadoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstadoExpediente = Busqueda
				.forClass(EstadoExpediente.class);

		GenericDao<Proceso, Object> procesoDAO = (GenericDao<Proceso, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroProceso = Busqueda.forClass(Proceso.class);

		GenericDao<TipoExpediente, Object> tipoExpedienteDAO = (GenericDao<TipoExpediente, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoExpediente = Busqueda.forClass(TipoExpediente.class);

		GenericDao<Entidad, Object> entidadDAO = (GenericDao<Entidad, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEntidad = Busqueda.forClass(Entidad.class);

		GenericDao<Calificacion, Object> calificacionDAO = (GenericDao<Calificacion, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroCalificacion = Busqueda.forClass(Calificacion.class);

		GenericDao<TipoHonorario, Object> tipoHonorarioDAO = (GenericDao<TipoHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoHonorario = Busqueda.forClass(TipoHonorario.class);

		GenericDao<Moneda, Object> monedaDAO = (GenericDao<Moneda, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroMoneda = Busqueda.forClass(Moneda.class);

		GenericDao<SituacionHonorario, Object> situacionHonorarioDAO = (GenericDao<SituacionHonorario, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionHonorario = Busqueda
				.forClass(SituacionHonorario.class);

		GenericDao<SituacionCuota, Object> situacionCuotasDAO = (GenericDao<SituacionCuota, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionCuota = Busqueda.forClass(SituacionCuota.class);

		GenericDao<SituacionInculpado, Object> situacionInculpadoDAO = (GenericDao<SituacionInculpado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroSituacionInculpado = Busqueda
				.forClass(SituacionInculpado.class);

		GenericDao<RolInvolucrado, Object> rolInvolucradoDAO = (GenericDao<RolInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRolInvolucrado = Busqueda.forClass(RolInvolucrado.class);

		GenericDao<TipoInvolucrado, Object> tipoInvolucradoDAO = (GenericDao<TipoInvolucrado, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoInvolucrado = Busqueda
				.forClass(TipoInvolucrado.class);

		GenericDao<Clase, Object> claseDAO = (GenericDao<Clase, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroClase = Busqueda.forClass(Clase.class);

		GenericDao<TipoDocumento, Object> tipoDocumentoDAO = (GenericDao<TipoDocumento, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoDocumento = Busqueda.forClass(TipoDocumento.class);

		GenericDao<TipoCautelar, Object> tipoCautelarDAO = (GenericDao<TipoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroTipoCautelar = Busqueda.forClass(TipoCautelar.class);

		GenericDao<ContraCautela, Object> contraCautelaDAO = (GenericDao<ContraCautela, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroContraCautela = Busqueda.forClass(ContraCautela.class);

		GenericDao<EstadoCautelar, Object> estadoCautelarDAO = (GenericDao<EstadoCautelar, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroEstadoCautelar = Busqueda.forClass(EstadoCautelar.class);

		GenericDao<Riesgo, Object> riesgoDAO = (GenericDao<Riesgo, Object>) SpringInit
				.getApplicationContext().getBean("genericoDao");
		Busqueda filtroRiesgo = Busqueda.forClass(Riesgo.class);

		try {
			estados = estadosExpedienteDAO.buscarDinamico(filtroEstadoExpediente);
			procesos = procesoDAO.buscarDinamico(filtroProceso);
			tipos = tipoExpedienteDAO.buscarDinamico(filtroTipoExpediente);
			entidades = entidadDAO.buscarDinamico(filtroEntidad);
			calificaciones = calificacionDAO.buscarDinamico(filtroCalificacion);
			tipoHonorarios = tipoHonorarioDAO
					.buscarDinamico(filtroTipoHonorario);
			monedas = monedaDAO.buscarDinamico(filtroMoneda);
			situacionHonorarios = situacionHonorarioDAO
					.buscarDinamico(filtroSituacionHonorario);
			situacionCuotas = situacionCuotasDAO
					.buscarDinamico(filtroSituacionCuota);
			situacionInculpados = situacionInculpadoDAO
					.buscarDinamico(filtroSituacionInculpado);
			rolInvolucrados = rolInvolucradoDAO
					.buscarDinamico(filtroRolInvolucrado);
			tipoInvolucrados = tipoInvolucradoDAO
					.buscarDinamico(filtroTipoInvolucrado);
			clases = claseDAO.buscarDinamico(filtroClase);
			tipoDocumentos = tipoDocumentoDAO
					.buscarDinamico(filtroTipoDocumento);
			tipoCautelares = tipoCautelarDAO.buscarDinamico(filtroTipoCautelar);
			contraCautelas = contraCautelaDAO
					.buscarDinamico(filtroContraCautela);
			estadosCautelares = estadoCautelarDAO
					.buscarDinamico(filtroEstadoCautelar);
			riesgos = riesgoDAO.buscarDinamico(filtroRiesgo);

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("error al cargar combos en registro de expediente..."+ e.toString());
		}

		vias = new ArrayList<Via>();
		instancias = new ArrayList<Instancia>();

		tipoHonorariosString = new ArrayList<String>();
		for (TipoHonorario t : tipoHonorarios)
			tipoHonorariosString.add(t.getDescripcion());

		monedasString = new ArrayList<String>();
		for (Moneda m : monedas)
			monedasString.add(m.getSimbolo());

		situacionHonorariosString = new ArrayList<String>();
		for (SituacionHonorario s : situacionHonorarios)
			situacionHonorariosString.add(s.getDescripcion());

		situacionCuotasString = new ArrayList<String>();
		for (SituacionCuota s : situacionCuotas)
			situacionCuotasString.add(s.getDescripcion());

		situacionInculpadosString = new ArrayList<String>();
		for (SituacionInculpado s : situacionInculpados)
			situacionInculpadosString.add(s.getNombre());

		rolInvolucradosString = new ArrayList<String>();
		for (RolInvolucrado r : rolInvolucrados)
			rolInvolucradosString.add(r.getNombre());

		tipoInvolucradosString = new ArrayList<String>();
		for (TipoInvolucrado t : tipoInvolucrados)
			tipoInvolucradosString.add(t.getNombre());

	}

	private void inicializarValores() {

		Calendar cal = Calendar.getInstance();
		inicioProceso = cal.getTime();
		fechaResumen = cal.getTime();

		selectedOrgano = new Organo();
		
		FacesContext fc = FacesContext.getCurrentInstance(); 
		ExternalContext exc = fc.getExternalContext(); 
		HttpSession session1 = (HttpSession) exc.getSession(true);
		
		logger.debug("Recuperando usuario..");
		com.grupobbva.seguridad.client.domain.Usuario usuario= (com.grupobbva.seguridad.client.domain.Usuario) session1.getAttribute("usuario");
		
		GenericDao<Usuario, Object> usuarioDAO = (GenericDao<Usuario, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
		Busqueda filtro = Busqueda.forClass(Usuario.class);
		filtro.add(Restrictions.eq("codigo", usuario.getUsuarioId()));
		List<Usuario> usuarios= new ArrayList<Usuario>();
				
		try {
			usuarios = usuarioDAO.buscarDinamico(filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(usuarios!= null){
			
			if(usuarios.size()!=0){
				
				usuarios.get(0).setNombreDescripcion(
						usuarios.get(0).getCodigo() + " - " +
								usuarios.get(0).getNombres() + " " +
								usuarios.get(0).getApellidoPaterno() + " " +
								usuarios.get(0).getApellidoMaterno());
				
				setResponsable(usuarios.get(0));
			}
			
		}
		
		oficina = new Oficina();

		honorario = new Honorario();
		honorario.setCantidad(0);
		honorario.setMonto(0.0);
		setHonorarios(new ArrayList<Honorario>());

		involucrado = new Involucrado();
		involucradoDataModel = new InvolucradoDataModel(new ArrayList<Involucrado>());

		cuantia = new Cuantia();
		cuantia.setPretendido(0.0);
		cuantiaDataModel = new CuantiaDataModel(new ArrayList<Cuantia>());

		inculpado = new Inculpado();
		inculpado.setFecha(cal.getTime());
		inculpado.setMonto(0.0);
		inculpado.setNrocupon(0000);
		inculpados = new ArrayList<Inculpado>();

		resumens = new ArrayList<Resumen>();
		
		anexo = new Anexo();
		anexo.setFechaInicio(cal.getTime());
		anexos = new ArrayList<Anexo>();

		organo = new Organo();
		organo.setEntidad(new Entidad());
		organo.setUbigeo(new Ubigeo());
		
		organoDataModel = new OrganoDataModel(new ArrayList<Organo>());

		persona = new Persona();
		persona.setClase(new Clase());
		persona.setCodCliente(null);
		persona.setTipoDocumento(new TipoDocumento());
		persona.setNumeroDocumento(null);
		
		selectPersona = new Persona();

		abogado = new Abogado();
		abogado.setDni(null);
		
		estudio = new Estudio();
		abogadoDataModel = new AbogadoDataModel(new ArrayList<Abogado>());

		personaDataModelBusq = new PersonaDataModel(new ArrayList<Persona>());
		
		setReqPenal(false);
		setReqCabecera(false);
	
	}

	public void editHonor(RowEditEvent event) {

		Honorario honorarioModif = ((Honorario) event.getObject());
		
		for (Honorario honorario : honorarios) {
			if (honorarioModif.getNumero() == honorario.getNumero()) {
				
				//situacion pendiente
				if(honorario.getSituacionHonorario().getIdSituacionHonorario()==1){
					
					double importe = honorarioModif.getMonto() / honorarioModif.getCantidad().intValue();

					importe = Math.rint(importe*100)/100;

					SituacionCuota situacionCuota = getSituacionCuotas().get(0);

					//honorario.setMontoPagado(0.0);
					honorario.setCuotas(new ArrayList<Cuota>());

					Calendar cal = Calendar.getInstance();
					
					for (int i = 1; i <= honorarioModif.getCantidad().intValue(); i++) {
						Cuota cuota = new Cuota();
						cuota.setNumero(i);
						cuota.setMoneda(honorarioModif.getMoneda().getSimbolo());
						cuota.setNroRecibo("000" + i);
						cuota.setImporte(importe);
						cal.add(Calendar.MONTH, 1);
						Date date = cal.getTime();
						cuota.setFechaPago(date);
						
						cuota.setSituacionCuota(new SituacionCuota());
						cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
						cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
						cuota.setFlagPendiente(true);
						
						honorario.addCuota(cuota);

					}
					
				}
				
			}
		}
		
		FacesMessage msg = new FacesMessage("Honorario Editado",
				"Honorario Editado al modificar algunos campos");
		FacesContext.getCurrentInstance().addMessage("growl", msg);

	}

	public void editDetHonor(RowEditEvent event) {

		Cuota cuotaModif = ((Cuota) event.getObject());
		
		double importe = cuotaModif.getImporte();
		double importeRestante = cuotaModif.getHonorario().getMonto() - importe;
		
		double importeNuevo=0.0;
		
		if(cuotaModif.getHonorario().getCantidad().intValue()>1){
			importeNuevo = importeRestante / (cuotaModif.getHonorario().getCantidad().intValue() - 1);
			importeNuevo = Math.rint(importeNuevo*100)/100;
			
		}else{
			
			importeNuevo = importe;
		}
		
		
		for(Honorario honorario: honorarios){
			
			if (cuotaModif.getHonorario().getNumero() == honorario.getNumero()) {
				
				
				for(Cuota cuota:cuotas){
					
					if(cuota.getNumero() == cuotaModif.getNumero() ){
						
						if (cuotaModif.getSituacionCuota().getDescripcion().equals("Pagado")
								|| cuotaModif.getSituacionCuota().getDescripcion().equals("Baja")) {
							
							//honorario.setMonto(importeRestante);
							honorario.setMontoPagado(honorario.getMontoPagado()+ importe);
							
							
							if(honorario.getMonto().compareTo(honorario.getMontoPagado())==0){
								
								SituacionHonorario situacionHonorario = getSituacionHonorarios().get(1);
								honorario.setSituacionHonorario(situacionHonorario);
								honorario.setFlagPendiente(false);
							}
							
							
							cuota.setFlagPendiente(false);
							
						}
						
						cuota.setImporte(importe);
						
					}else{
						
						cuota.setImporte(importeNuevo);
					}
					
				}
				
				honorario.setCuotas(cuotas);
				break;
			}
			
		}
		
		
		
	/*	
		for(Honorario honorario: honorarios){
			
			if (cuotaModif.getHonorario().getNumero() == honorario.getNumero()) {
				
				
				
				double importe = cuotaModif.getImporte();
				double importeRestante = honorario.getMonto() - importe;
				double importeNuevo = importeRestante / honorario.getCantidad().intValue();
				importeNuevo = Math.rint(importeNuevo*100)/100;
				
				honorario.setMonto(importeRestante);
				honorario.setMontoPagado(honorario.getMontoPagado() + importe);
				
				
				SituacionCuota situacionCuota = getSituacionCuotas().get(0);

				honorario.setMontoPagado(0.0);
				honorario.setCuotas(new ArrayList<Cuota>());

				Calendar cal = Calendar.getInstance();
				
				for (int i = 1; i <= honorario.getCantidad().intValue(); i++) {
					Cuota cuota = new Cuota();
					cuota.setNumero(i);
					cuota.setMoneda(honorario.getMoneda().getSimbolo());
					cuota.setNroRecibo("000" + i);
					cuota.setImporte(importe);
					cal.add(Calendar.MONTH, 1);
					Date date = cal.getTime();
					cuota.setFechaPago(date);
					
					cuota.setSituacionCuota(new SituacionCuota());
					cuota.getSituacionCuota().setIdSituacionCuota(situacionCuota.getIdSituacionCuota());
					cuota.getSituacionCuota().setDescripcion(situacionCuota.getDescripcion());
					
					honorario.addCuota(cuota);

				}
				
				break;
			}
			
		}*/
			

		FacesMessage msg = new FacesMessage("Cuota Editada",
				"Cuota Editada");
		FacesContext.getCurrentInstance().addMessage("growl", msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Honorario Cancelado",
				"Honorario Cancelado");

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<Inculpado> getInculpados() {
		return inculpados;
	}

	public void setInculpados(List<Inculpado> inculpados) {
		this.inculpados = inculpados;
	}

	public CuantiaDataModel getCuantiaDataModel() {
		return cuantiaDataModel;
	}

	public void setCuantiaDataModel(CuantiaDataModel cuantiaDataModel) {
		this.cuantiaDataModel = cuantiaDataModel;
	}

	public String getNroExpeOficial() {
		return nroExpeOficial;
	}

	public void setNroExpeOficial(String nroExpeOficial) {
		this.nroExpeOficial = nroExpeOficial;
	}

	public Organo getSelectedOrgano() {
		return selectedOrgano;
	}

	public void setSelectedOrgano(Organo selectedOrgano) {
		this.selectedOrgano = selectedOrgano;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Persona getSelectPersona() {
		return selectPersona;
	}

	public void setSelectPersona(Persona selectPersona) {
		this.selectPersona = selectPersona;
	}

	public Inculpado getInculpado() {
		return inculpado;
	}

	public void setInculpado(Inculpado inculpado) {
		this.inculpado = inculpado;
	}

	public Abogado getAbogado() {
		return abogado;
	}

	public void setAbogado(Abogado abogado) {
		this.abogado = abogado;
	}

	public AbogadoDataModel getAbogadoDataModel() {
		return abogadoDataModel;
	}

	public void setAbogadoDataModel(AbogadoDataModel abogadoDataModel) {
		this.abogadoDataModel = abogadoDataModel;
	}

	public boolean isTabAsigEstExt() {
		return tabAsigEstExt;
	}

	public void setTabAsigEstExt(boolean tabAsigEstExt) {
		this.tabAsigEstExt = tabAsigEstExt;
	}

	public boolean isTabCaucion() {
		return tabCaucion;
	}

	public void setTabCaucion(boolean tabCaucion) {
		this.tabCaucion = tabCaucion;
	}

	public boolean isTabCuanMat() {
		return tabCuanMat;
	}

	public void setTabCuanMat(boolean tabCuanMat) {
		this.tabCuanMat = tabCuanMat;
	}

	public String getSecretario() {
		return secretario;
	}

	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}

	public Cuantia getCuantia() {
		return cuantia;
	}

	public void setCuantia(Cuantia cuantia) {
		this.cuantia = cuantia;
	}

	public Organo getOrgano() {
		return organo;
	}

	public void setOrgano(Organo organo) {
		this.organo = organo;
	}

	public OrganoDataModel getOrganoDataModel() {
		return organoDataModel;
	}

	public void setOrganoDataModel(OrganoDataModel organoDataModel) {
		this.organoDataModel = organoDataModel;
	}

	public PersonaDataModel getPersonaDataModelBusq() {
		return personaDataModelBusq;
	}

	public void setPersonaDataModelBusq(PersonaDataModel personaDataModelBusq) {
		this.personaDataModelBusq = personaDataModelBusq;
	}

	public Cuantia getSelectedCuantia() {
		return selectedCuantia;
	}

	public void setSelectedCuantia(Cuantia selectedCuantia) {
		this.selectedCuantia = selectedCuantia;
	}

	public Inculpado getSelectedInculpado() {
		return selectedInculpado;
	}

	public void setSelectedInculpado(Inculpado selectedInculpado) {
		this.selectedInculpado = selectedInculpado;
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	public List<EstadoExpediente> getEstados() {
		return estados;
	}

	public void setEstados(List<EstadoExpediente> estados) {
		this.estados = estados;
	}

	public List<TipoExpediente> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoExpediente> tipos) {
		this.tipos = tipos;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}

	public List<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<Via> getVias() {
		return vias;
	}

	public void setVias(List<Via> vias) {
		this.vias = vias;
	}

	public List<Instancia> getInstancias() {
		return instancias;
	}

	public void setInstancias(List<Instancia> instancias) {
		this.instancias = instancias;
	}

	public int getProceso() {
		return proceso;
	}

	public void setProceso(int proceso) {
		this.proceso = proceso;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public List<Entidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidad> entidades) {
		this.entidades = entidades;
	}

	public Organo getOrgano1() {
		return organo1;
	}

	public void setOrgano1(Organo organo1) {
		this.organo1 = organo1;
	}

	public Recurrencia getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(Recurrencia recurrencia) {
		this.recurrencia = recurrencia;
	}

	public List<TipoHonorario> getTipoHonorarios() {
		return tipoHonorarios;
	}

	public void setTipoHonorarios(List<TipoHonorario> tipoHonorarios) {
		this.tipoHonorarios = tipoHonorarios;
	}

	public Honorario getHonorario() {
		return honorario;
	}

	public void setHonorario(Honorario honorario) {
		this.honorario = honorario;
	}

	public Honorario getSelectedHonorario() {
		return selectedHonorario;
	}

	public void setSelectedHonorario(Honorario selectedHonorario) {
		this.selectedHonorario = selectedHonorario;
	}

	public List<Honorario> getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(List<Honorario> honorarios) {
		this.honorarios = honorarios;
	}

	public List<SituacionCuota> getSituacionCuotas() {
		return situacionCuotas;
	}

	public void setSituacionCuotas(List<SituacionCuota> situacionCuotas) {
		this.situacionCuotas = situacionCuotas;
	}

	public List<SituacionInculpado> getSituacionInculpados() {
		return situacionInculpados;
	}

	public void setSituacionInculpados(
			List<SituacionInculpado> situacionInculpados) {
		this.situacionInculpados = situacionInculpados;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public Involucrado getInvolucrado() {
		return involucrado;
	}

	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}

	public List<RolInvolucrado> getRolInvolucrados() {
		return rolInvolucrados;
	}

	public void setRolInvolucrados(List<RolInvolucrado> rolInvolucrados) {
		this.rolInvolucrados = rolInvolucrados;
	}

	public InvolucradoDataModel getInvolucradoDataModel() {
		return involucradoDataModel;
	}

	public void setInvolucradoDataModel(
			InvolucradoDataModel involucradoDataModel) {
		this.involucradoDataModel = involucradoDataModel;
	}

	public Involucrado getSelectedInvolucrado() {
		return selectedInvolucrado;
	}

	public void setSelectedInvolucrado(Involucrado selectedInvolucrado) {
		this.selectedInvolucrado = selectedInvolucrado;
	}

	public List<TipoInvolucrado> getTipoInvolucrados() {
		return tipoInvolucrados;
	}

	public void setTipoInvolucrados(List<TipoInvolucrado> tipoInvolucrados) {
		this.tipoInvolucrados = tipoInvolucrados;
	}

	public List<Clase> getClases() {
		return clases;
	}

	public void setClases(List<Clase> clases) {
		this.clases = clases;
	}

	public List<TipoDocumento> getTipoDocumentos() {
		return tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	public List<Riesgo> getRiesgos() {
		return riesgos;
	}

	public void setRiesgos(List<Riesgo> riesgos) {
		this.riesgos = riesgos;
	}

	public List<EstadoCautelar> getEstadosCautelares() {
		return estadosCautelares;
	}

	public void setEstadosCautelares(List<EstadoCautelar> estadosCautelares) {
		this.estadosCautelares = estadosCautelares;
	}

	public int getVia() {
		return via;
	}

	public void setVia(int via) {
		this.via = via;
	}

	public int getInstancia() {
		return instancia;
	}

	public void setInstancia(int instancia) {
		this.instancia = instancia;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public Date getInicioProceso() {
		return inicioProceso;
	}

	public void setInicioProceso(Date inicioProceso) {
		this.inicioProceso = inicioProceso;
	}

	public int getMoneda() {
		return moneda;
	}

	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}

	public double getMontoCautelar() {
		return montoCautelar;
	}

	public void setMontoCautelar(double montoCautelar) {
		this.montoCautelar = montoCautelar;
	}

	public double getImporteCautelar() {
		return importeCautelar;
	}

	public void setImporteCautelar(double importeCautelar) {
		this.importeCautelar = importeCautelar;
	}

	public String getDescripcionCautelar() {
		return descripcionCautelar;
	}

	public void setDescripcionCautelar(String descripcionCautelar) {
		this.descripcionCautelar = descripcionCautelar;
	}

	public List<TipoCautelar> getTipoCautelares() {
		return tipoCautelares;
	}

	public void setTipoCautelares(List<TipoCautelar> tipoCautelares) {
		this.tipoCautelares = tipoCautelares;
	}

	public int getTipoCautelar() {
		return tipoCautelar;
	}

	public void setTipoCautelar(int tipoCautelar) {
		this.tipoCautelar = tipoCautelar;
	}

	public List<ContraCautela> getContraCautelas() {
		return contraCautelas;
	}

	public void setContraCautelas(List<ContraCautela> contraCautelas) {
		this.contraCautelas = contraCautelas;
	}

	public int getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(int riesgo) {
		this.riesgo = riesgo;
	}

	public int getContraCautela() {
		return contraCautela;
	}

	public void setContraCautela(int contraCautela) {
		this.contraCautela = contraCautela;
	}

	public Estudio getEstudio() {
		return estudio;
	}

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public int getEstadoCautelar() {
		return estadoCautelar;
	}

	public void setEstadoCautelar(int estadoCautelar) {
		this.estadoCautelar = estadoCautelar;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public Anexo getSelectedAnexo() {
		return selectedAnexo;
	}

	public void setSelectedAnexo(Anexo selectedAnexo) {
		this.selectedAnexo = selectedAnexo;
	}

	public List<SituacionHonorario> getSituacionHonorarios() {
		return situacionHonorarios;
	}

	public void setSituacionHonorarios(
			List<SituacionHonorario> situacionHonorarios) {
		this.situacionHonorarios = situacionHonorarios;
	}

	public List<String> getSituacionHonorariosString() {
		return situacionHonorariosString;
	}

	public void setSituacionHonorariosString(
			List<String> situacionHonorariosString) {
		this.situacionHonorariosString = situacionHonorariosString;
	}

	public List<String> getMonedasString() {
		return monedasString;
	}

	public void setMonedasString(List<String> monedasString) {
		this.monedasString = monedasString;
	}

	public List<String> getTipoHonorariosString() {
		return tipoHonorariosString;
	}

	public void setTipoHonorariosString(List<String> tipoHonorariosString) {
		this.tipoHonorariosString = tipoHonorariosString;
	}

	public List<String> getSituacionCuotasString() {
		return situacionCuotasString;
	}

	public void setSituacionCuotasString(List<String> situacionCuotasString) {
		this.situacionCuotasString = situacionCuotasString;
	}

	public List<String> getRolInvolucradosString() {
		return rolInvolucradosString;
	}

	public void setRolInvolucradosString(List<String> rolInvolucradosString) {
		this.rolInvolucradosString = rolInvolucradosString;
	}

	public List<String> getTipoInvolucradosString() {
		return tipoInvolucradosString;
	}

	public void setTipoInvolucradosString(List<String> tipoInvolucradosString) {
		this.tipoInvolucradosString = tipoInvolucradosString;
	}

	public List<String> getSituacionInculpadosString() {
		return situacionInculpadosString;
	}

	public void setSituacionInculpadosString(
			List<String> situacionInculpadosString) {
		this.situacionInculpadosString = situacionInculpadosString;
	}

	public boolean isReqPenal() {
		return reqPenal;
	}

	public void setReqPenal(boolean reqPenal) {
		this.reqPenal = reqPenal;
	}

	public boolean isReqCabecera() {
		return reqCabecera;
	}

	public void setReqCabecera(boolean reqCabecera) {
		this.reqCabecera = reqCabecera;
	}

	public List<Resumen> getResumens() {
		return resumens;
	}

	public void setResumens(List<Resumen> resumens) {
		this.resumens = resumens;
	}

	public Date getFechaResumen() {
		return fechaResumen;
	}

	public void setFechaResumen(Date fechaResumen) {
		this.fechaResumen = fechaResumen;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getTodoResumen() {
		return todoResumen;
	}

	public void setTodoResumen(String todoResumen) {
		this.todoResumen = todoResumen;
	}

	public Abogado getSelectedAbogado() {
		return selectedAbogado;
	}

	public void setSelectedAbogado(Abogado selectedAbogado) {
		this.selectedAbogado = selectedAbogado;
	}

	public Persona getSelectInvolucrado() {
		return selectInvolucrado;
	}

	public void setSelectInvolucrado(Persona selectInvolucrado) {
		this.selectInvolucrado = selectInvolucrado;
	}

	public int getContadorHonorario() {
		return contadorHonorario;
	}

	public void setContadorHonorario(int contadorHonorario) {
		this.contadorHonorario = contadorHonorario;
	}

}
