package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.IVeta;
import am.bizis.cds.ciselnik.Obce;
import am.bizis.stspr.fo.ISEOOsoba;

/**
 * Vytvori element VetaN pisemnosti DPFDP4 - Zadost o vraceni preplatku, pro vraceni preplatku na adresu
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#N
 * @author alex
 */
public class VetaNadresa extends VetaN implements IVeta {

	private final ISEOOsoba OSOBA;
	/**
	 * Vytvori vetu N pro vraceni preplatku na adresu se zadanymi povinnymi polozkami
	 * @param d VetaD, ze ktere bereme vysi preplatku
	 * @param o Osoba, ktere bude preplatek vyplacen
	 */
	public VetaNadresa(VetaD d, ISEOOsoba o) {
		super(d);
		this.OSOBA=o;
	}
	
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaN=EPO.createElement(super.toString());
		VetaN.setAttribute("zp_vrac", "A");
		VetaN.setAttribute("kc_preplatek",super.getKcPreplatek()+"");
		
		if(OSOBA.hasDruhe()) VetaN.setAttribute("zvp_jmeno",OSOBA.getKrestni()+" "+OSOBA.getDruhe());
		else VetaN.setAttribute("zvp_jmeno",OSOBA.getKrestni());
		VetaN.setAttribute("zvp_prijmeni", OSOBA.getPrijmeni());
		VetaN.setAttribute("zvp_titul", OSOBA.getTitul());
		if(OSOBA.getAdresa()!=null){
			VetaN.setAttribute("zvp_c_obce", Obce.getObec(OSOBA.getAdresa())+"");
			VetaN.setAttribute("zvp_c_orient", OSOBA.getAdresa().getOrientacni()+"");
			VetaN.setAttribute("zvp_c_pop", OSOBA.getAdresa().getPopisne()+"");
			VetaN.setAttribute("zvp_naz_obce", OSOBA.getAdresa().getObec());//TODO: mestska cast
			if(OSOBA.getAdresa().getUlice()==null) VetaN.setAttribute("zvp_ulice", OSOBA.getAdresa().getCast());
			else VetaN.setAttribute("zvp_ulice", OSOBA.getAdresa().getUlice());
			VetaN.setAttribute("zvp_psc", OSOBA.getAdresa().getPSC()+"");
		}	
		return VetaN;
	}
}
