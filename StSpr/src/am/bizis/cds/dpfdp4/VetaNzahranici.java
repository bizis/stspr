package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.stspr.fo.Adresa;

public class VetaNzahranici implements IVeta {
	private final double KC_PREPLATEK;
	private final String IBAN,BIC,MENA,NAZ_ADR_BANKY,NAZEV_PRIJ,SYM_PLVMPV;
	private final Adresa BANKA,PRIJEMCE;
	public VetaNzahranici(double kc_preplatek,String IBAN,String BIC,String mena,String naz_adr_banky,String nazev_prij,String sym_plvmpv,Adresa banka, Adresa prijemce) {
		this.BANKA=banka;
		this.BIC=BIC;//TODO validate
		this.IBAN=IBAN;//TODO validate
		this.KC_PREPLATEK=kc_preplatek;
		this.MENA=mena;//TODO ciselnik
		this.NAZ_ADR_BANKY=naz_adr_banky;
		this.NAZEV_PRIJ=nazev_prij;
		this.PRIJEMCE=prijemce;
		this.SYM_PLVMPV=sym_plvmpv;
	}

	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaN=EPO.createElement("VetaN");
		VetaN.setAttribute("zp_vrac", "Z");
		VetaN.setAttribute("kc_preplatek",KC_PREPLATEK+"");
		
		VetaN.setAttribute("c_nest_uctu",IBAN);
		VetaN.setAttribute("id_banky", BIC);
		VetaN.setAttribute("k_meny_uctu", MENA);
		VetaN.setAttribute("k_stat_banky", BANKA.getStat().getAlpha2());
		VetaN.setAttribute("mesto_banky",BANKA.getObec());
		VetaN.setAttribute("naz_adr_banky", NAZ_ADR_BANKY);
		VetaN.setAttribute("psc_banky", BANKA.getPSC()+"");
		VetaN.setAttribute("region_banky",BANKA.getOkres());
		VetaN.setAttribute("ulice_banky",BANKA.getUlice()+" "+BANKA.getPopisne());
		
		VetaN.setAttribute("mesto_prij", PRIJEMCE.getObec());
		VetaN.setAttribute("nazev_prij", NAZEV_PRIJ);
		VetaN.setAttribute("psc_prij", PRIJEMCE.getPSC()+"");
		VetaN.setAttribute("region_prij",PRIJEMCE.getOkres());
		VetaN.setAttribute("stat_prij",PRIJEMCE.getStat().getAlpha2());
		VetaN.setAttribute("ulice_prij", PRIJEMCE.getUlice()+" "+PRIJEMCE.getOrientacni());
		
		VetaN.setAttribute("sym_plvmpv", SYM_PLVMPV);
		return VetaN;
	}

}
