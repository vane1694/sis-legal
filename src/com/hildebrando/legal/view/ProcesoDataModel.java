package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import com.hildebrando.legal.modelo.Proceso;


public class ProcesoDataModel extends ListDataModel<Proceso>
implements SelectableDataModel<Proceso>  {

	@Override
	public Proceso getRowData(String arg0) {
		// TODO Auto-generated method stub
        List<Proceso> procesos = (List<Proceso>) getWrappedData();

        for (Proceso pr : procesos) {
            if (pr.getIdProceso() == Integer.parseInt(arg0)) {
                return pr;
            }
        }


        return null;
	}

	@Override
	public Object getRowKey(Proceso arg0) {
		// TODO Auto-generated method stub
		return arg0.getIdProceso();
	}
	
	public ProcesoDataModel(List<Proceso> data) {
        super(data);
    }
	
	public ProcesoDataModel() {

    }

}
