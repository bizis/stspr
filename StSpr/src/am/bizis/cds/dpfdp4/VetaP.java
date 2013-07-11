package am.bizis.cds.dpfdp4;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import am.bizis.stspr.fo.Adresa;
import am.bizis.stspr.fo.IPodnik;
import am.bizis.stspr.fo.Osoba;

/**
 * Vytvori element VetaD pisemnosti DPFDP4 - Zaznam o poplatnikovi
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#P
 * @author alex
 * @version 20130711
 */
public class VetaP implements IVeta {

	private String c_faxu, opr_postaveni;
	private Adresa adresa, dorucovani;
	private IPodnik osoba;
	private int c_pracufo;
	private Osoba opavnena;
	//private ?? zast_ev_cislo
	
	public VetaP() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Element getElement() throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

}
