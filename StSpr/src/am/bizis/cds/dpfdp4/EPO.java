package am.bizis.cds.dpfdp4;

import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	 * @return DPFDP4
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
		Element dpfdp4=EPO.createElement("DPFDP4");
		dpfdp4.setAttribute("verzePis", "04.01.01");
		rootElement.appendChild(dpfdp4);
		
		//mnozina vet, ktere musi byt v dokumentu obsazeny
		HashSet<String> reqs=new HashSet<String>();
		reqs.add("VetaD");
		reqs.add("VetaP");//Pro danovy subjekt musi byt vuplneno DIC nebo RC/IC
		
		//vety
		for(IVeta v:content){
			   if(v.getClass().equals(VetaT.class)||v.getClass().equals(Vetac.class)||v.getClass().equals(VetaU.class)||
					   v.getClass().equals(VetaU.class)||v.getClass().equals(VetaS.class)){
				   v=null;
				   //throw new IllegalArgumentException("Veta T nebyla ocekavana");
			   }
			   else if(v!=null){
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
		
		//if(j.getLength()>0){//je vlozena veta J - nastavim polozky vety V
			//NamedNodeMap nnm=dpfdp4.getElementsByTagName("VetaV").item(0).getAttributes();

				//najit VetaO, nastavit KcZd7 - uz mam udelany XML dokument a ted jej modifikuji
				/*NamedNodeMap nnm0=dpfdp4.getElementsByTagName("VetaO").item(0).getAttributes();
				Element zd7=EPO.createElement("kc_zd7");
				zd7.setNodeValue(nnm.getNamedItem("kc_zd7p").getNodeValue());
				nnm0.setNamedItem(zd7);*/
				//double uhrn=Double.parseDouble(nnm0.getNamedItem("kc_uhrn").getNodeValue())+Double.parseDouble(zd7.getNodeValue())+Double.parseDouble(zd9.getNodeValue());
				//nnm0.getNamedItem("kc_uhrn").setNodeValue(uhrn+"");
		//}
		
		//vytvorim mnozinu PredepsanychPriloh z dpfdp4
		HashSet<String> pps=new HashSet<String>();
		NodeList nl=dpfdp4.getElementsByTagName("PredepsanaPriloha");//vsechny predepsane prilohy
		for(int i=0;i<nl.getLength();i++){
			Node n=nl.item(i).getAttributes().getNamedItem("kod");//kod dane PP
			if(n!=null) pps.add(n.getNodeValue());//vlozim do mnoziny
		}
		
		//kontrola zavislosti
		int obecna=0;
		for(String s:reqs){
			if(PredepsanaPriloha.getNazvy().contains(s)&&!pps.contains(s))//zavislost je predepsana priloha a dana PP neni v mnozine PP obsazenych v dpfdp4 
				throw new MissingElementException(s);
			else if(s.equals("ObecnaPriloha")) obecna++;
			else if(dpfdp4.getElementsByTagName(s).getLength()<1) throw new MissingElementException(s);
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
	 * 
	 * vklada se "pocet listu dane prilohy"
	 * udela se, az se v praxi otestuje, co se tu vlastne s tema prilohama dela
	 * a vymysli se, jak ten pocet listu zjistit
	 * 
	 * @return VetaB
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
