package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.Expediente;

public class ExpedienteDataModel extends ListDataModel<Expediente> implements
		SelectableDataModel<Expediente> {

	public ExpedienteDataModel() {
		// TODO Auto-generated constructor stub
	}

	public ExpedienteDataModel(List<Expediente> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public Expediente getRowData(String arg0) {
		
		 List<Expediente> valores = (List<Expediente>) getWrappedData();

		 if(valores != null){
			 for (Expediente exp : valores) {
		            if (exp.getIdExpediente() == Integer.parseInt(arg0))
		                return exp;
		     } 
		 }
		 return null;
	}

	@Override
	public Object getRowKey(Expediente arg0) {
		// TODO Auto-generated method stub
		return arg0.getIdExpediente();
	}

}
