package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.ciselnik.Obce;
import am.bizis.stspr.fo.Adresa;
import am.bizis.stspr.fo.IPodnik;
import am.bizis.stspr.fo.OSVC;
import am.bizis.stspr.fo.Osoba;
import am.bizis.stspr.fo.OsobaTyp;

/**
 * Vytvori element VetaD pisemnosti DPFDP4 - Zaznam o poplatnikovi
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#P
 * @author alex
 * @version 20130711
 */
public class VetaP implements IVeta {
	private final int C_PRACUFO_UNINIT=-1;
	private String opr_postaveni;
	private Adresa dorucovani;
	private IPodnik osoba,zastupce;
	private int c_pracufo=C_PRACUFO_UNINIT;
	private Osoba opravnena;
	private ZastKod zast_kod;
	//private ?? zast_ev_cislo
	
	public VetaP() {
		//zadne povinne polozky
	}

	public void setPoplatnik(IPodnik osoba){
		this.osoba=osoba;
	}
	
	public void setCpracufo(int c_pracufo){
		this.c_pracufo=c_pracufo;
	}
	
	public void setOpravnena(Osoba o,String opr_postaveni){
		this.opravnena=o;
		this.opr_postaveni=opr_postaveni;
	}
	
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaP=EPO.createElement("VetaP");
		
		if(osoba.getFax()!=0) VetaP.setAttribute("c_faxu", osoba.getFax()+"");
		if(osoba.getAdresa()!=null){
			VetaP.setAttribute("c_obce", Obce.getObec(osoba.getAdresa())+"");
			VetaP.setAttribute("krok_c_obce", Obce.getObec(osoba.getAdresa())+"");
			VetaP.setAttribute("c_orient", osoba.getAdresa().getOrientacni()+"");
			VetaP.setAttribute("krok_c_orient",osoba.getAdresa().getOrientacni()+"");
			VetaP.setAttribute("c_popisne",osoba.getAdresa().getPopisne()+"");
			VetaP.setAttribute("krok_c_pop",osoba.getAdresa().getPopisne()+"");
			VetaP.setAttribute("naz_obce",osoba.getAdresa().getObec());//TODO mestska cast
			VetaP.setAttribute("krok_naz_obce", osoba.getAdresa().getObec());//TODO mestska cast
			VetaP.setAttribute("krok_psc",osoba.getAdresa().getPSC()+"");
			VetaP.setAttribute("krok_ulice",osoba.getAdresa().getUlice());
			VetaP.setAttribute("k_stat", osoba.getAdresa().getStat().getAlpha2());
		}
		if(osoba.getTyp().equals(OsobaTyp.FO)){
			OSVC o=(OSVC)osoba;
			if(!o.getPasy().isEmpty()) VetaP.setAttribute("c_pasu",o.getPasy().toArray()[0]+"");//a co kdyz chce jiny? TODO
		}
		if(c_pracufo!=C_PRACUFO_UNINIT) VetaP.setAttribute("c_pracufo", c_pracufo+"");
		if(osoba.getTelefon()!=0) VetaP.setAttribute("c_telef", osoba.getTelefon()+"");
		if(osoba.getDIC()!=0) VetaP.setAttribute("dic",osoba.getDIC()+"");//TODO textova reprezentace nutno zachovat vodici nuly
		if(osoba.getEmail()!=null) VetaP.setAttribute("email",osoba.getEmail());
		if(osoba.getTyp().equals(OsobaTyp.PO)) VetaP.setAttribute("jmeno", osoba.getJmeno());//TODO toto asi ne
		else{
			OSVC o=(OSVC)osoba;
			//>jmeno<, prijmeni, rodne prijmeni
		}
		if(opravnena!=null) {
			if(opravnena.hasDruhe()) VetaP.setAttribute("opr_jmeno", opravnena.getKrestni()+" "+opravnena.getDruhe());
			else VetaP.setAttribute("opr_jmeno", opravnena.getKrestni());
			VetaP.setAttribute("opr_prijmeni", opravnena.getPrijmeni());
			VetaP.setAttribute("opr_postaveni",opr_postaveni);
		}
		return VetaP;
	}

}
