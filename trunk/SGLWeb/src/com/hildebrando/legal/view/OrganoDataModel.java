package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.domains.Organo;

public class OrganoDataModel extends ListDataModel<Organo> implements SelectableDataModel<Organo> {

    public OrganoDataModel() {

    }

    public OrganoDataModel(List<Organo> data) {
        super(data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Organo getRowData(String arg0) {
        List<Organo> org = (List<Organo>) getWrappedData();
        for (Organo o : org) {
            if (Integer.parseInt(o.getCodigo()) == Integer.parseInt(arg0)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Organo arg0) {
        // TODO Auto-generated method stub
        return arg0.getCodigo();
    }

}
