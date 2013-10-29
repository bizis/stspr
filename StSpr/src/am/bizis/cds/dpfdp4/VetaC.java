/**
 * 
 */
package am.bizis.cds.dpfdp4;

import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import am.bizis.cds.IVeta;

/**
 * @author alex
 * Záznam úpravy podle § 23,§ 5 zákona zvyšující VH nebo rozdíl mezi P a V
 */
public class VetaC implements IVeta {

	private final Hashtable<String,String> ht;
	/**
	 * Konstruktor vytvori element Veta C
	 * @param kc_uprzvys_235 Částka úpravy podle § 5, § 23 zvyšující výsledek hospodaření nebo rozdíl mezi příjmy a výdaji
	 * @param uprzvys_235 Popis úpravy podle § 5, § 23 zákona zvyšující výsledek hospodaření nebo rozdíl mezi příjmy a výdaji.
	 */
	public VetaC(int kc_uprzvys_235,String uprzvys_235) {
		ht=new Hashtable<String,String>();
		ht.put("kc_uprzvys_235",kc_uprzvys_235+"");
		ht.put("uprzvys_235", uprzvys_235);
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		return Veta.getElement("VetaC", ht);
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
