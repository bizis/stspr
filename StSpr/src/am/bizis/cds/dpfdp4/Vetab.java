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
 * Věta b - Příjmy podle § 6 zákona - údaje dle jednotlivých zaměstnavatelů
 * @author alex
 */
public class Vetab implements IVeta {

	private double kc_poj6p, kc_prij6p, kc_vyplbonusp, kc_zalzavcp;
	/**
	 * 
	 */
	public Vetab() {
		// zadne povinne polozky
	}
	
	/**
	 * @param kc_poj6p Úhrn povinného pojistného podle § 6 odst. 13 zákona
	 */
	public void setKcPoj6p(double kc_poj6p){
		this.kc_poj6p=kc_poj6p;
	}
	
	/**
	 * @param kc_prij6p Úhrn příjmů ze závislé činnosti a funkčních požitků
	 */
	public void setKcPrij6p(double kc_prij6p){
		this.kc_prij6p=kc_prij6p;
	}
	
	/**
	 * @param kc_vyplbonusp Úhrn vyplacených měsíčních daňových bonusů podle § 35d ZDP
	 */
	public void setKcVyplbonusp(double kc_vyplbonusp){
		this.kc_vyplbonusp=kc_vyplbonusp;
	}
	
	/**
	 * @param kc_zalzavcp Sražená záloha na daň v úhrnné výši
	 */
	public void setKcZalzavcp(double kc_zalzavcp){
		this.kc_zalzavcp=kc_zalzavcp;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element Vetab=EPO.createElement("Vetab");
		Vetab.setAttribute("kc_poj6p", kc_poj6p+"");
		Vetab.setAttribute("kc_prij6p", kc_prij6p+"");
		Vetab.setAttribute("kc_vyplbonus",kc_vyplbonusp+"");
		Vetab.setAttribute("kc_zalzavcp",kc_zalzavcp+"");
		return Vetab;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 999;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}

}
