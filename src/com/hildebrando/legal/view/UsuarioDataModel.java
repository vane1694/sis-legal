package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.*;

public class UsuarioDataModel extends ListDataModel<Usuario> implements
        SelectableDataModel<Usuario> {

    public UsuarioDataModel() {

    }

    public UsuarioDataModel(List<Usuario> data) {
        super(data);
    }


    @SuppressWarnings("unchecked")
    @Override
    public Usuario getRowData(String arg0) {
        List<Usuario> usu = (List<Usuario>) getWrappedData();
        for (Usuario u : usu) {
            if (u.getIdUsuario() == Integer.parseInt(arg0)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Usuario arg0) {
        // TODO Auto-generated method stub
        return arg0.getIdUsuario();
    }

}
