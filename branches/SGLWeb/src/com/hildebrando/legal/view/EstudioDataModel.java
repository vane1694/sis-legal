package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.*;

public class EstudioDataModel extends ListDataModel<Estudio> implements
		SelectableDataModel<Estudio> {

	public EstudioDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EstudioDataModel(List<Estudio> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public Estudio getRowData(String arg0) {
		// TODO Auto-generated method stub
		 List<Estudio> valores = (List<Estudio>) getWrappedData();

	        for (Estudio val : valores) {
	            if (val.getRuc().equals(arg0))
	                return val;
	        }
	        
	        return null;
	}

	@Override
	public Object getRowKey(Estudio arg0) {
		// TODO Auto-generated method stub
		return arg0.getRuc();
	}

}
