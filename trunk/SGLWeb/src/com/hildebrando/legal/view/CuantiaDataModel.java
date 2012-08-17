package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.Cuantia;


public class CuantiaDataModel extends ListDataModel<Cuantia> implements
        SelectableDataModel<Cuantia> {

    @SuppressWarnings("unchecked")
    @Override
    public Cuantia getRowData(String arg0) {
        // TODO Auto-generated method stub
        List<Cuantia> valores = (List<Cuantia>) getWrappedData();

        for (Cuantia val : valores) {
            if (val.getMateria().equals(arg0))
                return val;
        }

        return null;
    }

    @Override
    public Object getRowKey(Cuantia arg0) {
        // TODO Auto-generated method stub
        return arg0.getMateria();
    }

    public CuantiaDataModel(List<Cuantia> arg0) {
        // TODO Auto-generated method stub
        super(arg0);
    }
    
    public CuantiaDataModel() {
        // TODO Auto-generated method stub
        super();
    }


}
