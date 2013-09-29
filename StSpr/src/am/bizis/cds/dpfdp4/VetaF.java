/**
 * 
 */
package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.stspr.fo.OSVC;

/**
 * @author alex
 * Věta F - Záznam o účastníku sdružení
 */
public class VetaF implements IVeta {

	private final String ucsdruz_dic, ucsdruz_jmeno, ucsdruz_prijmeni;
	private final double ucsdruz_podprij, ucsdruz_podvyd;
	
	/**
	 * Konstruktor vytvori vetu F
	 * @param ucsdruz účastník sdružení
	 * @param ucsdruz_podprij Podíl na příjmech v %
	 * @param ucsdruz_podvyd Podíl na výdajích v %
	 */
	public VetaF(OSVC ucsdruz, double ucsdruz_podprij, double ucsdruz_podvyd) {
		this.ucsdruz_dic=ucsdruz.getDIC()+"";
		if(ucsdruz.hasDruhe()) this.ucsdruz_jmeno=ucsdruz.getKrestni()+" "+ucsdruz.getDruhe();
		else this.ucsdruz_jmeno=ucsdruz.getKrestni();
		this.ucsdruz_prijmeni=ucsdruz.getPrijmeni();
		this.ucsdruz_podprij=ucsdruz_podprij;
		this.ucsdruz_podvyd=ucsdruz_podvyd;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaF=EPO.createElement("VetaF");
		VetaF.setAttribute("ucsdruz_dic", ucsdruz_dic);
		VetaF.setAttribute("ucsdruz_jmeno", ucsdruz_jmeno);
		VetaF.setAttribute("ucsdruz_prijmeni", ucsdruz_prijmeni);
		VetaF.setAttribute("ucsdruz_podprij", ucsdruz_podprij+"");
		VetaF.setAttribute("ucsdruz_podvyd", ucsdruz_podvyd+"");
		return VetaF;
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
		return "VetaF";
	}
}
