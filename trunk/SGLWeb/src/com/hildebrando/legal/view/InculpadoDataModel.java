package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.*;

public class InculpadoDataModel extends ListDataModel<Inculpado> implements
        SelectableDataModel<Inculpado> {

    @Override
    public Inculpado getRowData(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getRowKey(Inculpado arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public InculpadoDataModel(List<Inculpado> arg) {
        super(arg);

    }

}
