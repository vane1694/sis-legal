package com.hildebrando.legal.view;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

import com.hildebrando.legal.modelo.Persona;


public class PersonaDataModel extends ListDataModel<Persona>
        implements SelectableDataModel<Persona> {

    @Override
    public Object getRowKey(Persona arg0) {
        // TODO Auto-generated method stub
        return arg0.getIdPersona();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Persona getRowData(String arg0) {
        // TODO Auto-generated method stub
        List<Persona> estudios = (List<Persona>) getWrappedData();

        for (Persona est : estudios) {
            if (est.getIdPersona() == Integer.parseInt(arg0)) {
                return est;
            }
        }


        return null;
    }

    public PersonaDataModel(List<Persona> data) {
        super(data);
    }
}
