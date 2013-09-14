package am.bizis.cds.dpfdp4;

import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import am.bizis.exception.MissingElementException;
import am.bizis.exception.MultipleElementsOfSameTypeException;

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
		
		//mnozina vet, ktere musi byt v dokumentu obsazeny
		HashSet<String> reqs=new HashSet<String>();
		reqs.add("VetaD");
		
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
		try{
		for(String s:reqs){
			if(!PredepsanaPriloha.valueOf(s).equals(null)){//zavislost je predepsana priloha
				NodeList nl = dpfdp4.getElementsByTagName("PredepsanaPriloha");//vezmu vsechny PP z dpdpf4
				boolean gotit=false;
				int i=0;//TODO: prepsat!! casova slozitost!!
				while((gotit==false)||(i<nl.getLength())){//projdu, kontroluji atribut kod
					Node n=nl.item(i).getAttributes().getNamedItem("kod");
					if(n!=null) if(n.getNodeValue().equals(s)) gotit=true;//nalezeno
					i++;
				}
				if(!gotit) throw new MissingElementException(s);//nenalezeno
			}
			else if(s.equals("ObecnaPriloha")) obecna++;
			else if(dpfdp4.getElementsByTagName(s).getLength()<1) throw new MissingElementException(s);
		}
		}catch(IllegalArgumentException e){
			//no enum constant
		}
		//kontrola dostatecneho poctu obecnych priloh, tady je problem, pokud se jedna OP pozaduje dvakrat
		if(dpfdp4.getElementsByTagName("ObecnaPriloha").getLength()<obecna) throw new MissingElementException("ObecnaPriloha");

		//TODO: VetaB
		Element VetaB=getVetaB();
		//EPO.appendChild(VetaB);
		
		return EPO;
	}

	/**
	 * Vytvori element VetaB zaznam o prilohach prikladanych k DAP DPF
	 * @return
	 * @throws ParserConfigurationException
	 */
	private Element getVetaB() throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaB=EPO.createElement("VetaB");
		
		return VetaB;
	}

}
