package am.bizis.cds.dpfdp4;

import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import am.bizis.cds.IVeta;
import am.bizis.cds.Veta;
import am.bizis.exception.ConditionException;
import am.bizis.stspr.fo.ISEOOsoba;

/**
 * Vytvori element VetaA pisemnosti DPFDP4 - Záznam o vyživovaném dítěti, na které je uplatňováno daňové zvýhodnění
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#A
 * @author alex
 */
public class VetaA implements IVeta {
	
	private final int MAX=99;
	private final String ELEMENT="VetaA";
	private final Hashtable<String,String> attrs;
	
	/**
	 * Vytvori Vetu A s osobnimi udaji ditete, poctem mesicu uplatnovaneho zvyhodneni a kolik z toho mesicu bylo dane dite
	 * ZTP/P
	 * @param vyzdite osobni udaje ditete
	 * @param pocmes pocet mesicu uplatnovaneho zvyhodneni
	 * @param ztpp pocet mesicu ZTP/P
	 * @throws ConditionException pocet mesicu musi byt 0 az 12
	 */
	public VetaA(ISEOOsoba vyzdite,int pocmes, int ztpp) throws ConditionException{
		attrs=new Hashtable<String,String>();
		if(vyzdite.hasDruhe()) attrs.put("vyzdite_jmeno",vyzdite.getKrestni()+" "+vyzdite.getDruhe());
		else attrs.put("vyzdite_jmeno",vyzdite.getKrestni());
		attrs.put("vyzdite_prijmeni",vyzdite.getPrijmeni());
		attrs.put("vyzdite_r_cislo",vyzdite.getRodneCislo().toString());//TODO: textova reprezentace, nutno zachovat vodici nuly
		if((pocmes<0)||(pocmes>12)) throw new ConditionException("Pocet mesicu musi byt 0 az 12");
		else attrs.put("vyzdite_pocmes",pocmes+"");
		if((ztpp<0)||(ztpp>12)) throw new ConditionException("Pocet mesicu musi byt 0 az 12");
		if(ztpp>pocmes) ztpp=pocmes;
		attrs.put("vyzdite_ztpp",ztpp+"");
	}

	@Override
	public Element getElement() throws ParserConfigurationException {
		return Veta.getElement(ELEMENT, attrs);
	}

	@Override
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	public int getMaxPocet() {
		return MAX;
	}

	@Override
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	public String[] getDependency() {
		return null;
	}
	
	@Override
	public String toString(){
		return ELEMENT;
	}

}
