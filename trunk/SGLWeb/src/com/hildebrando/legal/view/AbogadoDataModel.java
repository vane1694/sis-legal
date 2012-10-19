package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.Abogado;




public class AbogadoDataModel extends ListDataModel<Abogado> implements
		SelectableDataModel<Abogado> {

	public AbogadoDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbogadoDataModel(List<Abogado> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public Abogado getRowData(String arg0) {
		// TODO Auto-generated method stub
		 List<Abogado> valores = (List<Abogado>) getWrappedData();

	        for (Abogado val : valores) {
	            if (val.getIdAbogado() == Integer.parseInt(arg0))
	                return val;
	        }
	        return  null;
	}

	@Override
	public Object getRowKey(Abogado arg0) {
		// TODO Auto-generated method stub
		return arg0.getIdAbogado();
	}

}
