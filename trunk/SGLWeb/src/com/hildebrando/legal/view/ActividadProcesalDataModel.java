package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import com.hildebrando.legal.domains.ActividadProcesal;

public class ActividadProcesalDataModel extends
		ListDataModel<ActividadProcesal> implements
		SelectableDataModel<ActividadProcesal> {
	
	public ActividadProcesalDataModel() {

    }

    public ActividadProcesalDataModel(List<ActividadProcesal> data) {
        super(data);
    }
    

	@SuppressWarnings("unchecked")
	@Override
	public ActividadProcesal getRowData(String arg0) {
		 List<ActividadProcesal> act = (List<ActividadProcesal>) getWrappedData();
	        for (ActividadProcesal a : act) {
	            if (Integer.parseInt(a.getCodigo()) == Integer.parseInt(arg0)) {
	                return a;
	            }
	        }
	        return null;
	}

	@Override
	public Object getRowKey(ActividadProcesal arg0) {
		// TODO Auto-generated method stub
		return arg0.getCodigo();
	}

}
