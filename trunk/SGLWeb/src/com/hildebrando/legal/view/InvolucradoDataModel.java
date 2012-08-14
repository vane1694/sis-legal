package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.Involucrado;


public class InvolucradoDataModel extends ListDataModel<Involucrado> implements
SelectableDataModel<Involucrado> {

	@Override
	public Involucrado getRowData(String arg0) {
		 List<Involucrado> valores = (List<Involucrado>) getWrappedData();

	        for (Involucrado val : valores) {
	            if (val.getIdInvolucrado() == Integer.parseInt(arg0))
	                return val;
	        }
	        
	        return null;
	}

	@Override
	public Object getRowKey(Involucrado arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public InvolucradoDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvolucradoDataModel(List<Involucrado> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}


}
