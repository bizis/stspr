/**
 * 
 */
package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.IVeta;

/**
 * @author alex
 * Záznam úpravy podle § 23,§ 5 zákona zvyšující VH nebo rozdíl mezi P a V
 */
public class VetaC implements IVeta {

	private final int kc_uprzvys_235;
	private final String uprzvys_235;
	
	/**
	 * Konstruktor vytvori element Veta C
	 * @param kc_uprzvys_235 Částka úpravy podle § 5, § 23 zvyšující výsledek hospodaření nebo rozdíl mezi příjmy a výdaji
	 * @param uprzvys_235 Popis úpravy podle § 5, § 23 zákona zvyšující výsledek hospodaření nebo rozdíl mezi příjmy a výdaji.
	 */
	public VetaC(int kc_uprzvys_235,String uprzvys_235) {
		this.kc_uprzvys_235=kc_uprzvys_235;
		this.uprzvys_235=uprzvys_235;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaC=EPO.createElement("VetaC");
		VetaC.setAttribute("kc_uprzvys_235", kc_uprzvys_235+"");
		VetaC.setAttribute("uprzvys_235",uprzvys_235);
		return VetaC;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 99;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}
	
	@Override
	public String toString(){
		return "VetaC";
	}

}
