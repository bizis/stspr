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
import am.bizis.stspr.fo.OSVC;

/**
 * @author alex
 * Věta H - Záznam o osobě, která rozděluje příjmy
 */
public class VetaH implements IVeta {

	private final String rozdos_dic, rozdos_jmeno, rozdos_prijmeni;
	private final double rozdos_podil;
	
	/**
	 * Konstruktor vytvori element vety H
	 * @param rozdos	   osoba, která rozděluje příjmy a výdaje
	 * @param rozdos_podil Podíl na příjmech a výdajích v %
	 */
	public VetaH(OSVC rozdos,double rozdos_podil) {
		this.rozdos_dic=rozdos.getDIC()+"";
		if(rozdos.hasDruhe()) this.rozdos_jmeno=rozdos.getKrestni()+" "+rozdos.getDruhe();
		else this.rozdos_jmeno=rozdos.getKrestni();
		this.rozdos_prijmeni=rozdos.getPrijmeni();
		this.rozdos_podil=rozdos_podil;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaH=EPO.createElement("VetaH");
		VetaH.setAttribute("rozdos_dic", rozdos_dic);
		VetaH.setAttribute("rozdos_jmeno", rozdos_jmeno);
		VetaH.setAttribute("rozdos_prijmeni",rozdos_prijmeni);
		VetaH.setAttribute("rozdos_podil", rozdos_podil+"");
		return VetaH;
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

}
