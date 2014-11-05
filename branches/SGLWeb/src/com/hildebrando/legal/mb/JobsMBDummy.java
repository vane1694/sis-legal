/**
 * 
 */
package com.hildebrando.legal.mb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bbva.general.entities.Oficina;
import com.bbva.general.entities.Territorio;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author P018697
 *
 */
public class JobsMBDummy {

	/**
	 * 
	 */
	public JobsMBDummy() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			//cargarTerr();
			cargarOficinas();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void cargarTerr() throws ParserConfigurationException, SAXException, IOException{
		NodeList nList = null;
		String archivoXML="D:\\territorio_listado.xml";
		nList = obtenerNodos(archivoXML,"Territorio");
		List<com.bbva.general.entities.Territorio> listaTerritorios = new ArrayList<Territorio>();
		com.bbva.general.entities.Territorio terr = null;
		
		for (int temp = 0; temp < nList.getLength(); temp++) {	
			Node nNode = nList.item(temp);	 
			System.out.println("\nElemento: " + nNode.getNodeName());	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				terr = new Territorio();
				Element eElement = (Element) nNode;	 
				System.out.println("\tCodTerritorio: " + eElement.getElementsByTagName("codigoTerritorio").item(0).getTextContent());
				System.out.println("\tDescripcionTerrit: " + eElement.getElementsByTagName("descripcionTerritorio").item(0).getTextContent());
				
				terr.setCodigoTerritorio(eElement.getElementsByTagName("codigoTerritorio").item(0).getTextContent());
				terr.setDescripcionTerritorio(eElement.getElementsByTagName("descripcionTerritorio").item(0).getTextContent());
				
				listaTerritorios.add(terr);
			}
		}
		for(com.bbva.general.entities.Territorio terri: listaTerritorios){
			System.out.println("Cod:"+terri.getCodigoTerritorio() + " -> "+terri.getDescripcionTerritorio());
		}
		System.out.println("Size lista territ: "+listaTerritorios.size());
	}
	
	public static void cargarOficinas() throws ParserConfigurationException, SAXException, IOException{
		NodeList nList = null;
		String archivoXML="D:\\oficina_listado.xml";
		nList = obtenerNodos(archivoXML,"getCentroListadoReturn");
		
		List<com.bbva.general.entities.Oficina> listaOficinas = new ArrayList<Oficina>();
		com.bbva.general.entities.Oficina ofi = null;
		System.out.println("nList.getLength():"+nList.getLength());
		for (int temp = 0; temp < nList.getLength(); temp++) {	
			Node nNode = nList.item(temp);	 
			System.out.println("\nElemento: " + nNode.getNodeName());	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("----asasas");
				ofi = new Oficina();
				Element eElement = (Element) nNode;	 
				System.out.println("\tCodOfi: " + eElement.getElementsByTagName("codigoOficina").item(0).getTextContent());
				//System.out.println("\tDescrOficina: " + eElement.getElementsByTagName("nombre").item(0).getTextContent());
				
				//ofi.setCodigoTerritorio(eElement.getElementsByTagName("codigoTerritorio").item(0).getTextContent());
				//ofi.setNombre(eElement.getElementsByTagName("nombre").item(0).getTextContent());
				
				
				listaOficinas.add(ofi);
			}
		}
		for(com.bbva.general.entities.Oficina of: listaOficinas){
			System.out.println("->Oficina:"+of.getNombre());
		}
		System.out.println("Size lista oficinas: "+listaOficinas.size());
	}
	
	private static NodeList obtenerNodos(String archivoXML,String tag) throws ParserConfigurationException, SAXException, IOException{
		NodeList nList=null;
		File fXmlFile = new File(archivoXML);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();		 
		System.out.println("Root element:" + doc.getDocumentElement().getNodeName());	 
		nList = doc.getElementsByTagName(tag);
		//NodeList nList = doc.getElementsByTagName("Territorio");
		return nList;
	}

	 static Territorio[] loadTerritoriosWS(){
		com.bbva.general.entities.Territorio[] terr = null;
		
		return null;
	}
}
