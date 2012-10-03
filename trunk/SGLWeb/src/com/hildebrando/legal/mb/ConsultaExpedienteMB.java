package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.expr.NewArray;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Projections;

import com.bbva.common.listener.SpringInit.SpringInit;
import com.bbva.persistencia.generica.dao.Busqueda;
import com.bbva.persistencia.generica.dao.GenericDao;

import com.hildebrando.legal.modelo.Expediente;
import com.hildebrando.legal.view.ExpedienteDataModel;


@ManagedBean(name="consultaExpe")
@SessionScoped
public class ConsultaExpedienteMB {
	
	private String nroExpeOficial;
	private String proceso;
	private Map<String, String> procesos;
	private String via;
	private Map<String, String> vias;
	private String demandante;
	private String organo;
	private String estado;
	private Map<String, String> estados;
	private String recurrencia;
	private String materia;
	private String claveBusqueda;
	private ExpedienteDataModel expedienteDataModel;
	private Expediente selectedExpediente;
	
	
	public String reset(){
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/faces/paginas/registroExpediente.xhtml";
	}
	
	public ConsultaExpedienteMB() {
		
		super();
		//SpringInit.openSession();
		
		
	}
	
	public String editarExpediente() {
		
		  FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		  ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		  HttpSession session = (HttpSession) context.getSession(true);
		  session.setAttribute("numeroExpediente", getSelectedExpediente().getNumeroExpediente());
        
		  return "actualSeguiExpediente.xhtml?faces-redirect=true";
    }  
	
	public List<String> completeOrgano(String query) {
		List<String> results = new ArrayList<String>();

		return results;
	}
	
	public List<String> completeRecurrencia(String query) {
		List<String> results = new ArrayList<String>();

		List<String> listRecurrencia = new ArrayList<String>();
		listRecurrencia.add("reclamo");
		listRecurrencia.add("demora en pago");
		listRecurrencia.add("revision de caso");
		
		for (String rec : listRecurrencia) {
			if (rec.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(rec.toUpperCase());
			}
		}

		return results;
	}

	public List<String> completeMaterias(String query) {
		List<String> results = new ArrayList<String>();

		List<String> listMateriasBD = new ArrayList<String>();
		listMateriasBD.add("materia 1");
		listMateriasBD.add("materia 2");
		listMateriasBD.add("materia 3");
		listMateriasBD.add("materia 4");

		for (String mat : listMateriasBD) {
			if (mat.toLowerCase().startsWith(query.toLowerCase())) {
				results.add(mat.toUpperCase());
			}
		}

		return results;
	}

	
	// listener cada vez que se modifica el proceso
		public void cambioProceso() {

			if (getProceso() != null && !getProceso().equals("")) {
				
				Map<String, String> vias1BD = new HashMap<String, String>();
				vias1BD.put("Abreviado", "001");
				vias1BD.put("Conocimiento", "002");
				vias1BD.put("Sumarisimo", "003");

				Map<String, String> vias2BD = new HashMap<String, String>();
				vias2BD.put("Conocimiento", "002");
				vias2BD.put("Sumarisimo", "003");

				Map<String, String> vias3BD = new HashMap<String, String>();
				vias3BD.put("Conocimiento", "002");
				vias3BD.put("Sumarisimo", "003");

				Map<String, Map<String, String>> viaBD = new HashMap<String, Map<String, String>>();
				viaBD.put("001", vias1BD);
				viaBD.put("002", vias2BD);
				viaBD.put("003", vias3BD);
				
				setVias(viaBD.get(getProceso()));
				

			} else {
				vias = new HashMap<String, String>();
				
			}

		}
		
		@SuppressWarnings("unchecked")
		public void buscarExpedientes(ActionEvent e){
			
			List<Expediente> expedientesAux = new ArrayList<Expediente>();
			List<Expediente> expedientes = new ArrayList<Expediente>();
			GenericDao<Expediente, Object> expedienteDAO = (GenericDao<Expediente, Object>) SpringInit.getApplicationContext().getBean("genericoDao");
			
			Busqueda filtro = Busqueda.forClass(Expediente.class);
			
			try {
				
				expedientes = expedienteDAO.buscarDinamico(filtro);
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			
			HashMap<String, Long> maps = new HashMap<String, Long>();
			
			for(Expediente ex: expedientes){
				
				if(!maps.containsKey(ex.getNumeroExpediente())){
					
					maps.put(ex.getNumeroExpediente(), ex.getIdExpediente());
				}
			
			} 
			
			for (Map.Entry<String, Long> elemento : maps.entrySet()) {
				
				try {
					expedientesAux.add(expedienteDAO.buscarById(Expediente.class,elemento.getValue()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			
			List<Expediente> sublistExpediente = new ArrayList<Expediente>();

			if(getNroExpeOficial() == ""){
				
				sublistExpediente= expedientesAux;
				
			}else{
				for (Expediente expe : expedientesAux) {

					if (expe.getNumeroExpediente().trim()
							.equalsIgnoreCase(getNroExpeOficial())) {
						sublistExpediente.add(expe);
					}
				}
				
			}

			expedienteDataModel = new ExpedienteDataModel(sublistExpediente);

			
		}
		

		public String getNroExpeOficial() {
			return nroExpeOficial;
		}

		public void setNroExpeOficial(String nroExpeOficial) {
			this.nroExpeOficial = nroExpeOficial;
		}

		public String getProceso() {
			return proceso;
		}

		public void setProceso(String proceso) {
			this.proceso = proceso;
		}

		

		public String getRecurrencia() {
			return recurrencia;
		}

		public void setRecurrencia(String recurrencia) {
			this.recurrencia = recurrencia;
		}


		public Map<String, String> getProcesos() {
			if (procesos == null) {
				procesos = new HashMap<String, String>();
				procesos.put("Civil", "001");
				procesos.put("Penal", "002");
				procesos.put("Administrativo", "003");

			}
			return procesos;
		}

		public void setProcesos(Map<String, String> procesos) {
			this.procesos = procesos;
		}

		public Map<String, String> getVias() {
			
			if(vias== null){
				
				vias = new HashMap<String, String>();
			}
			
			Set<String> set = vias.keySet();

		    Iterator<String> itr = set.iterator();
		    
		    while (itr.hasNext()) {
		      String str = itr.next();
		      System.out.println(str + ": " + vias.get(str));
		    }
					
			return vias;
		}

		public void setVias(Map<String, String> vias) {
			this.vias = vias;
		}

		public String getVia() {
			return via;
		}

		public void setVia(String via) {
			this.via = via;
		}

		public Map<String, String> getEstados() {
			if (estados == null) {
				estados = new HashMap<String, String>();
				estados.put("En giro", "001");
				estados.put("Concluido", "002");

			}

			return estados;
		}

		public void setEstados(Map<String, String> estados) {
			this.estados = estados;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getOrgano() {
			return organo;
		}

		public void setOrgano(String organo) {
			this.organo = organo;
		}

		public String getMateria() {
			return materia;
		}

		public void setMateria(String materia) {
			this.materia = materia;
		}

		public String getClaveBusqueda() {
			return claveBusqueda;
		}

		public void setClaveBusqueda(String claveBusqueda) {
			this.claveBusqueda = claveBusqueda;
		}

		public ExpedienteDataModel getExpedienteDataModel() {
			
			return expedienteDataModel;
		}
		
		public String getDemandante() {
			return demandante;
		}

		public void setDemandante(String demandante) {
			this.demandante = demandante;
		}

		public Expediente getSelectedExpediente() {
			return selectedExpediente;
		}

		public void setSelectedExpediente(Expediente selectedExpediente) {
			this.selectedExpediente = selectedExpediente;
		}

	

}
