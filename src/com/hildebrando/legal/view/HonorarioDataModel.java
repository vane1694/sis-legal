package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.Honorario;


public class HonorarioDataModel extends ListDataModel<Honorario> implements
SelectableDataModel<Honorario> {

	public HonorarioDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HonorarioDataModel(List<Honorario> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Honorario getRowData(String arg0) {
		 List<Honorario> valores = (List<Honorario>) getWrappedData();

	        for (Honorario val : valores) {
	            if (val.getIdHonorario() == Integer.parseInt(arg0))
	                return val;
	        }
	        
	        return null;
	}


	@Override
	public Object getRowKey(Honorario arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
