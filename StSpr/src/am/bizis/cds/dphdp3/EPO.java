package am.bizis.cds.dphdp3;

import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.IVeta;
import am.bizis.exception.ConditionException;
import am.bizis.exception.MissingElementException;
import am.bizis.exception.MultipleElementsOfSameTypeException;

/**
 * Trida EPO tvori z vet Pisemnost
 * @author alex
 */
public class EPO {

	public EPO() {
	}
	
	/**
	 * Vytvori EPO xml dokument ze zadanych vet
	 * @param content pole vet. Kazda veta ma stanoveno, nejvyse a nejmene kolikrat se muze v dokumentu vyskytovat
	 * @return DPHDP3
	 * @throws ParserConfigurationException
	 * @throws MissingElementException nesplnena zavislost: jeden element pozaduje vlozeni jineho (napr. predepsane prilohy, 
	 * obecne prilohy, ...) - pozadovany element ovsem nebyl vlozen
	 * @throws MultipleElementsOfSameTypeException element se vystytuje vicekrat, nez je maximalni dovoleny pocet jeho vyskytu
	 * @throws ConditionException nesplnena podminka v nektere vete
	 */
	public Document makeXML(IVeta[] content) throws ParserConfigurationException,MultipleElementsOfSameTypeException,MissingElementException,ConditionException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		
		//<Pisemnost>
		Element rootElement=EPO.createElement("Pisemnost");
		rootElement.setAttribute("nazevSW", "BizIS");
		rootElement.setAttribute("verzeSW", "1.00.000");
		EPO.appendChild(rootElement);
		
		//<DPFDP4>
		Element dpfdp4=EPO.createElement("DPHDP3");
		dpfdp4.setAttribute("verzePis", "01.02.04");
		rootElement.appendChild(dpfdp4);
		
		//mnozina vet, ktere musi byt v dokumentu obsazeny
		HashSet<String> reqs=new HashSet<String>();
		reqs.add("VetaD");
		reqs.add("VetaP");
		
		//vety
		for(IVeta v:content){
			   if(v!=null){
					Element n=(Element)EPO.adoptNode(v.getElement());
					//kontrola, ze pocet vet jednotlivych typu nepresahuje maximalni hodnoty
					if(dpfdp4.getElementsByTagName(n.getTagName()).getLength()<v.getMaxPocet()) dpfdp4.appendChild(n);
					else throw new MultipleElementsOfSameTypeException(n.getTagName());
					//pokud ma veta zavislosti, vlozime do seznamu
					if (v.getDependency()!=null) for(String dep:v.getDependency()){
						reqs.add(dep.toString());
					}
				}
		}
		
		//kontrola zavislosti
		int obecna=0;
		//kontrola dostatecneho poctu obecnych priloh, tady je problem, pokud se jedna OP pozaduje dvakrat
		if(dpfdp4.getElementsByTagName("ObecnaPriloha").getLength()<obecna) throw new MissingElementException("ObecnaPriloha");
		
		return EPO;
	}

}
