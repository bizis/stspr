/**
 * 
 */
package am.bizis.cds.dpfdp4;

import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import am.bizis.cds.IVeta;

/**
 * Věta b - Příjmy podle § 6 zákona - údaje dle jednotlivých zaměstnavatelů
 * @author alex
 */
public class Vetab implements IVeta {

	private Hashtable<String,String> ht;
	/**
	 * 
	 */
	public Vetab() {
		ht=new Hashtable<String,String>();
		// zadne povinne polozky
	}
	
	/**
	 * @param kc_poj6p Úhrn povinného pojistného podle § 6 odst. 13 zákona
	 */
	public void setKcPoj6p(double kc_poj6p){
		if(ht.containsKey("kc_poj6p")) ht.remove("kc_poj6p");
		ht.put("kc_poj6p",kc_poj6p+"");
	}
	
	/**
	 * @param kc_prij6p Úhrn příjmů ze závislé činnosti a funkčních požitků
	 */
	public void setKcPrij6p(double kc_prij6p){
		if(ht.containsKey("kc_prij6p")) ht.remove("kc_prij6p");
		ht.put("kc_prij6p", kc_prij6p+"");
	}
	
	/**
	 * @param kc_vyplbonusp Úhrn vyplacených měsíčních daňových bonusů podle § 35d ZDP
	 */
	public void setKcVyplbonusp(double kc_vyplbonusp){
		if(ht.containsKey("kc_vyplbonusp")) ht.remove("kc_vyplbonusp");
		ht.put("kc_vyplbonusp",kc_vyplbonusp+"");
	}
	
	/**
	 * @param kc_zalzavcp Sražená záloha na daň v úhrnné výši
	 */
	public void setKcZalzavcp(double kc_zalzavcp){
		if(ht.containsKey("kc_zalzavcp")) ht.remove("kc_zalzavcp");
		ht.put("kc_zalzavcp",kc_zalzavcp+"");
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		return Veta.getElement("Vetab", ht);
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
