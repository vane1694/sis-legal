package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.BusquedaActProcesal;

public class BusquedaActividadProcesalDataModel extends ListDataModel<BusquedaActProcesal> implements SelectableDataModel<BusquedaActProcesal> 
{

	public BusquedaActividadProcesalDataModel() {
		// TODO Auto-generated constructor stub
	}

	public BusquedaActividadProcesalDataModel(List<BusquedaActProcesal> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public BusquedaActProcesal getRowData(String arg0) {
		
		 List<BusquedaActProcesal> valores = (List<BusquedaActProcesal>) getWrappedData();

		 if(valores != null){
			 for (BusquedaActProcesal bus : valores) {
		            if (bus.getNroExpediente().equals(arg0))
		                return bus;
		     } 
		 }
		 return null;
	}

	@Override
	public Object getRowKey(BusquedaActProcesal arg0) {
		// TODO Auto-generated method stub
		return arg0.getNroExpediente();
	}

}
