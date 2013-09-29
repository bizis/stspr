package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.stspr.fo.Adresa;

/**
 * Vytvori element VetaN pisemnosti DPFDP4 - Zadost o vraceni preplatku, pro vraceni preplatku na zahranicni ucet
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#N
 * @author alex
 */
public class VetaNzahranici extends VetaN implements IVeta {
	private final String IBAN,BIC,MENA,NAZ_ADR_BANKY,NAZEV_PRIJ,SYM_PLVMPV;
	private final Adresa BANKA,PRIJEMCE;
	
	/**
	 * Vytvori vetu N pro vraceni preplatku na zahranicni ucet
	 * @param d Veta D, ze ktere bereme vysi preplatku
	 * @param IBAN číslo zahraničního účtu ve formátu IBAN
	 * @param BIC ID banky (měl by být uveden BIC kód - swiftový kód banky).
	 * @param mena Kód měny, ve které je účet veden
	 * Uveďte kód měny, ve které je zahraniční účet veden.
	 * Pro hodnotu této položky použijte číselník Země (zeme). Z číselníku se vkládá položka k_meny.
	 * Položka obsahuje kritickou kontrolu: hodnota musí být platnou hodnotou z číselníku měn.
	 * @param naz_adr_banky Název zahraniční banky Uveďte název finanční instituce, u které je zahraniční účet veden.
	 * @param nazev_prij Název vlastníka účtu.
	 * @param sym_plvmpv Informace od plátce pro příjemce platby
	 * @param banka adresa banky
	 * @param prijemce adresa prijemce
	 */
	public VetaNzahranici(VetaD d,String IBAN,String BIC,String mena,String naz_adr_banky,String nazev_prij,String sym_plvmpv,Adresa banka, Adresa prijemce) {
		super(d);
		this.BANKA=banka;
		this.BIC=BIC;//TODO validate
		this.IBAN=IBAN;//TODO validate
		this.MENA=mena;//TODO ciselnik
		this.NAZ_ADR_BANKY=naz_adr_banky;
		this.NAZEV_PRIJ=nazev_prij;
		this.PRIJEMCE=prijemce;
		this.SYM_PLVMPV=sym_plvmpv;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaN=EPO.createElement(super.toString());
		VetaN.setAttribute("zp_vrac", "Z");
		VetaN.setAttribute("kc_preplatek",super.getKcPreplatek()+"");
		
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
