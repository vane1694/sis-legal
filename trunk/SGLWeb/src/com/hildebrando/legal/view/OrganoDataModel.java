package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.Organo;


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
            if (o.getIdOrgano() == Integer.parseInt(arg0)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Organo arg0) {
        return arg0.getIdOrgano();
    }

}
