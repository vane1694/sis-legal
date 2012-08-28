package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.*;

public class OficinaDataModel extends ListDataModel<Oficina> implements
        SelectableDataModel<Oficina> {

    public OficinaDataModel() {

    }

    public OficinaDataModel(List<Oficina> data) {
        super(data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Oficina getRowData(String arg0) {
        List<Oficina> ofic = (List<Oficina>) getWrappedData();
        for (Oficina o : ofic) {
            if (Integer.parseInt(o.getCodigo()) == Integer.parseInt(arg0)) {
                return o;
            }

        }
        return null;
    }

    @Override
    public Object getRowKey(Oficina arg0) {
        // TODO Auto-generated method stub
        return arg0.getCodigo();
    }

}
