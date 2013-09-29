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
 * Věta W - Příloha č. 3
 * 
 * TODO uhrn_neuzndan lze pouzit v nasledujicim zdanovacim obdobi 
 * (Částku daně můžete uplatnit podle § 24 odst. 2 písm. ch) zákona jako výdaj (náklad) v následujícím zdaňovacím období.)
 */
public class VetaW implements IVeta {

	private double da_zazahr,uhrn_neuzndan,uhrn_uzndan;
	
	/**
	 * Konstruktor vytvori element veta W
	 * @param s Zaznam III. oddilu pro zjisteni dane podle § 16 zákona
	 * @param l Metoda prostého zápočtu daně
	 */
	public VetaW(VetaS s,VetaL[] l) {
		uhrn_neuzndan=0;
		uhrn_uzndan=0;
		for(VetaL vl:l){
			uhrn_neuzndan+=vl.getRozOd12();
			uhrn_uzndan+=vl.getDaUznZap();
		}
		if(uhrn_uzndan>s.getDaDan16()) uhrn_uzndan=s.getDaDan16();
		da_zazahr=s.getDaDan16()-uhrn_uzndan;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaW=EPO.createElement("VetaW");
		VetaW.setAttribute("da_zazahr",da_zazahr+"");
		VetaW.setAttribute("uhrn_uzndan", uhrn_uzndan+"");
		VetaW.setAttribute("uhrn_neuzndan", uhrn_neuzndan+"");
		return VetaW;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return new String[]{"VetaS","VetaL"};
	}

}
