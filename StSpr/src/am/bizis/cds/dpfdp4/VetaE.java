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
 * Záznam úpravy podle § 23, § 5 zákona snižující VH nebo rozdíl mezi P a V
 */
public class VetaE implements IVeta {

	private final int kc_uprsniz_235;
	private final String uprsniz_235;
	
	/**
	 * Konstruktor vytvori element Veta E
	 * @param kc_uprsniz_235	Částka úpravy podle § 5, § 23 snižující výsledek hospodaření nebo rozdíl mezi příjmy a výdaji
	 * @param uprsniz_235	Popis úpravy podle § 5, § 23 zákona snižující výsledek hospodaření nebo rozdíl mezi příjmy a výdaji.
	 */
	public VetaE(int kc_uprsniz_235,String uprsniz_235) {
		this.kc_uprsniz_235=kc_uprsniz_235;
		this.uprsniz_235=uprsniz_235;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaE=EPO.createElement("VetaE");
		VetaE.setAttribute("kc_uprsniz_235", kc_uprsniz_235+"");
		VetaE.setAttribute("uprsniz_235", uprsniz_235);
		return VetaE;
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
		return "VetaE";
	}
}
