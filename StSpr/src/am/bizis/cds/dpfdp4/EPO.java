package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.stspr.exception.MissingElementException;
import am.bizis.stspr.exception.MultipleElementsOfSameTypeException;

public class EPO {

	public EPO() {
	}
	
	/**
	 * Vytvori EPO xml dokument ze zadanych vet
	 * @param content pole vet. Kazda veta ma stanoveno, nejvyse a nejmene kolikrat se muze v dokumentu vyskytovat
	 * @return
	 * @throws ParserConfigurationException
	 */
	public Document makeXML(IVeta[] content) throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		
		//<Pisemnost>
		Element rootElement=EPO.createElement("Pisemnost");
		rootElement.setAttribute("nazevSW", "BizIS");
		rootElement.setAttribute("verzeSW", "1.00.000");
		EPO.appendChild(rootElement);
		
		//<DPFDP4>
		Element dpfdp4=EPO.createElement("DPFDP4");
		dpfdp4.setAttribute("verzePis", "04.01.01");
		rootElement.appendChild(dpfdp4);
		
		//vety
		for(IVeta v:content){
				if(v!=null){
					Element n=(Element)EPO.adoptNode(v.getElement());
					//kontrola, ze pocet vet jednotlivych typu nepresahuje maximalni hodnoty
					if(dpfdp4.getElementsByTagName(n.getTagName()).getLength()<v.getMaxPocet()) dpfdp4.appendChild(n);
					else throw new MultipleElementsOfSameTypeException(n.getTagName());
				}
		}
		
		if(dpfdp4.getElementsByTagName("VetaD").getLength()<1) throw new MissingElementException("VetaD");
		
		return EPO;
	}

}
