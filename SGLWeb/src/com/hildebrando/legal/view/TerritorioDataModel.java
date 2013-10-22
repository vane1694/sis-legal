/**
 * 
 */
package com.hildebrando.legal.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;
import com.hildebrando.legal.modelo.Territorio;



/**
 * @author rguerrem
 *
 */
public class TerritorioDataModel extends ListDataModel<Territorio>
implements SelectableDataModel<Territorio> {
	
	@Override
	public Territorio getRowData(String arg0) {
		 List<Territorio> valores = (List<Territorio>) getWrappedData();

	        for (Territorio val : valores) {
	            if (val.getIdTerritorio() == Integer.parseInt(arg0))
	                return val;
	        }
	        
	        return null;
	}

	@Override
	public Object getRowKey(Territorio arg0) {
		// TODO Auto-generated method stub
		return arg0.getIdTerritorio();
	}

	public TerritorioDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TerritorioDataModel(List<Territorio> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

}
