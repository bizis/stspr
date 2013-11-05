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
 * Záznam o spolupracující osobě
 */
public class VetaG implements IVeta {

	private final String spolos_dic, spolos_jmeno, spolos_prijmeni;
	private final double spolos_podil; 
	
	/**
	 * Konstruktor vytvori element Veta G
	 * @param spolos Spolupracujici osoba
	 * @param spolos_podil Podíl na příjmech a výdajích v %
	 */
	public VetaG(OSVC spolos,double spolos_podil) {
		this.spolos_podil=spolos_podil;
		this.spolos_dic=spolos.getDIC()+""; 
		if(spolos.hasDruhe()) this.spolos_jmeno=spolos.getKrestni()+" "+spolos.getDruhe();
		else this.spolos_jmeno=spolos.getKrestni();
		this.spolos_prijmeni=spolos.getPrijmeni();
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaG=EPO.createElement("VetaG");
		VetaG.setAttribute("spolos_dic",spolos_dic);
		VetaG.setAttribute("spolos_jmeno",spolos_jmeno);
		VetaG.setAttribute("spolos_prijmeni", spolos_prijmeni);
		VetaG.setAttribute("spolos_podil", spolos_podil+"");
		return VetaG;
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
		return "VetaG";
	}
}
