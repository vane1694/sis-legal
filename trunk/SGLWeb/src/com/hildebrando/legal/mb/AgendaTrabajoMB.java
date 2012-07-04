package com.hildebrando.legal.mb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.hildebrando.legal.domains.ActividadProcesal;
import com.hildebrando.legal.domains.Organo;
import com.hildebrando.legal.view.ActividadProcesalDataModel;

@ManagedBean(name="agendaTrab")
public class AgendaTrabajoMB {

	private List<Organo> organos;
	private ActividadProcesal procesal;
	private ActividadProcesalDataModel procesalDataModel;
	private ScheduleModel agendaModel;
	private ScheduleEvent evento;
	
	public List<Organo> getOrganos() {
		List<Organo> listOrganoBD = new ArrayList<Organo>();

		listOrganoBD.add(new Organo("Comisaria PNP de PRO", "Comas", "Lima",
				"Lima", "2345", "Ministerio"));
		listOrganoBD.add(new Organo("Juzgado Penal del Callao", "Callao",
				"Lima", "Lima", "4323", "Poder Judicial"));
		listOrganoBD.add(new Organo("Comisaria de San Miguel", "San Miguel",
				"Lima", "Lima", "4567", "Ministerio"));
		listOrganoBD.add(new Organo("Comisaria de Magdalena del Mar",
				"Magdalena del Mar", "Lima", "Lima", "3452", "Ministerio"));
		listOrganoBD.add(new Organo("Juzgado Penal de Chorrillos",
				"Chorrillos", "Lima", "Lima", "2343", "Poder Judicial"));

		organos = listOrganoBD;
		return organos;
	}

	public void setOrganos(List<Organo> organos) {
		this.organos = organos;
	}
	
	public ActividadProcesalDataModel getProcesalDataModel() {

		if (procesalDataModel == null) {

			List<ActividadProcesal> listProcesalBD = new ArrayList<ActividadProcesal>();
			procesalDataModel = new ActividadProcesalDataModel(listProcesalBD);
		}

		return procesalDataModel;
	}

	public void setProcesalDataModel(
			ActividadProcesalDataModel procesalDataModel) {
		this.procesalDataModel = procesalDataModel;
	}
	
	public ActividadProcesal getProcesal() {
		if (procesal == null) {
			procesal = new ActividadProcesal();
		}
		return procesal;
	}

	public void setProcesal(ActividadProcesal procesal) {
		this.procesal = procesal;
	}

	public ScheduleModel getAgendaModel() {
		if (agendaModel == null) {

			agendaModel = new DefaultScheduleModel();

			agendaModel.addEvent(new DefaultScheduleEvent("Contestacion ",
					modifDate(-1), modifDate(-1)));
			agendaModel.addEvent(new DefaultScheduleEvent(
					"Audiencia Proceso Penal", modifDate(0), modifDate(0)));
			agendaModel.addEvent(new DefaultScheduleEvent("Sistema Gestion",
					modifDate(1), modifDate(1)));
			agendaModel.addEvent(new DefaultScheduleEvent(
					"Tachas y Excepciones", modifDate(1), modifDate(3)));

		}

		return agendaModel;

	}
	
	public void addEvent(ActionEvent actionEvent) {
		if (evento.getId() == null)
			agendaModel.addEvent(evento);
		else
			agendaModel.updateEvent(evento);

		evento = new DefaultScheduleEvent();
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		evento = selectEvent.getScheduleEvent();
	}

	public void onDateSelect(DateSelectEvent selectEvent) {
		evento = new DefaultScheduleEvent("",
				selectEvent.getDate(), selectEvent.getDate());
	}

	public void setAgendaModel(ScheduleModel agendaModel) {
		this.agendaModel = agendaModel;
	}

	public ScheduleEvent getEvento() {
		if (evento == null) {
			evento = new DefaultScheduleEvent();

		}
		return evento;
	}

	public void setEvento(ScheduleEvent evento) {
		this.evento = evento;
	}
	
	public void buscarActProc(ActionEvent e) {
		List<ActividadProcesal> listProcesalBD = new ArrayList<ActividadProcesal>();

		listProcesalBD
				.add(new ActividadProcesal("001", "EXP-1234-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "1"));
		listProcesalBD
				.add(new ActividadProcesal("002", "EXP-1235-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "2"));

		listProcesalBD
				.add(new ActividadProcesal("003", "EXP-1236-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "3"));

		listProcesalBD
				.add(new ActividadProcesal("004", "EXP-1237-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "1"));

		listProcesalBD
				.add(new ActividadProcesal("005", "EXP-1238-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "2"));

		listProcesalBD
				.add(new ActividadProcesal("006", "EXP-1239-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "3"));

		listProcesalBD
				.add(new ActividadProcesal("007", "EXP-1240-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "1"));

		listProcesalBD
				.add(new ActividadProcesal("008", "EXP-1241-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "2"));

		listProcesalBD
				.add(new ActividadProcesal("009", "EXP-1242-2012",
						"Julio Correa Mellado", "Comisaria 1", "15:00",
						"Tachas u Excepciones", "12/04/2012", "18/04/2012", "",
						"", "3"));

		procesalDataModel = new ActividadProcesalDataModel(listProcesalBD);

	}
	
	public Date modifDate(int dias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dias);

		return cal.getTime();

	}
	


}
