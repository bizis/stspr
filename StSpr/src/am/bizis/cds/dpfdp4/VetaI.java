/**
 * 
 */
package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author alex
 * Záznam o veřejné obchodní společnosti nebo komanditní společnosti
 */
public class VetaI implements IVeta {

	private final String vos_ks_dic;
	private final double vos_ks_podil;
	
	/**
	 * Konstruktor vytvori element Veta I
	 * @param vos_kc_dic DIČ veřejné obchodní společnosti nebo komanditní společnosti
	 * @param vos_ks_podil 	Výše Vašeho podílu v %
	 */
	public VetaI(String vos_kc_dic,double vos_ks_podil) {
		this.vos_ks_dic=vos_kc_dic;
		this.vos_ks_podil=vos_ks_podil;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaI=EPO.createElement("VetaI");
		VetaI.setAttribute("vos_ks_podil",vos_ks_podil+"");
		VetaI.setAttribute("vos_ks_dic",vos_ks_dic);
		return VetaI;
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
