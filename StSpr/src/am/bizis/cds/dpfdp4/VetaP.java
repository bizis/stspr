package am.bizis.cds.dpfdp4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import taka.CountryCode;
import am.bizis.cds.ciselnik.Obce;
import am.bizis.exception.ConditionException;
import am.bizis.stspr.IPodnik;
import am.bizis.stspr.OsobaTyp;
import am.bizis.stspr.fo.Adresa;
import am.bizis.stspr.fo.ISEOOsoba;
import am.bizis.stspr.fo.OSVC;
import am.bizis.stspr.fo.Zpusobilost;

/**
 * Vytvori element VetaP pisemnosti DPFDP4 - Zaznam o poplatnikovi
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#P
 * @author alex
 * @version 20130803
 */
public class VetaP implements IVeta {
	private final Set<CountryCode> EU=Collections.emptySet();
	private final int C_PRACUFO_UNINIT=-1,MAX=1;
	private final String ZZST="Zakonny zastupce";
	private final String ELEMENT="VetaP";
	private String opr_postaveni;
	private final DateFormat DF=new SimpleDateFormat("dd.MM.yyyy");
	/*
	 * krok ... Pobyt k poslednimu dni kalendarniho roku, za ktery se dan vymeruje (pokud ruzne od bydliste)
	 * zdrz ... Adresa mista pobytu na uzemi CR, kde se poplatnik obvykle ve zdanovacim obdobi zdrzoval - pokud neni bydliste na uzemi CR
	 */
	private Adresa krok=null,zdrz=null;
	private IPodnik zastupce;
	private OSVC osoba;
	private int c_pracufo=C_PRACUFO_UNINIT;
	private ISEOOsoba opravnena;
	private ZastKod zast_kod;
	private String zast_ev_cislo=null;
	
	public VetaP() {
		//zadne povinne polozky
		
		//definujeme staty EU
		EU.add(CountryCode.BE);
		EU.add(CountryCode.BG);
		EU.add(CountryCode.CZ);
		EU.add(CountryCode.DK);
		EU.add(CountryCode.ES);
		EU.add(CountryCode.FI);
		EU.add(CountryCode.FR);
		EU.add(CountryCode.CR);
		EU.add(CountryCode.IR);
		EU.add(CountryCode.IT);
		EU.add(CountryCode.CR);
		EU.add(CountryCode.LT);
		EU.add(CountryCode.LA);
		EU.add(CountryCode.LT);
		EU.add(CountryCode.LU);
		EU.add(CountryCode.HU);
		EU.add(CountryCode.MT);
		EU.add(CountryCode.DE);
		EU.add(CountryCode.NL);
		EU.add(CountryCode.PL);
		EU.add(CountryCode.PG);
		EU.add(CountryCode.AT);
		EU.add(CountryCode.RO);
		EU.add(CountryCode.GR);
		EU.add(CountryCode.SK);
		EU.add(CountryCode.SI);
		EU.add(CountryCode.GB);
		EU.add(CountryCode.ES);
		EU.add(CountryCode.SE);
	}

	/**
	 * Poplatnik dane z prijmu FO
	 * @param osoba osobni udaje poplatnika
	 */
	public void setPoplatnik(OSVC osoba){
		this.osoba=osoba;
		this.krok=osoba.getAdresa();
		this.zdrz=osoba.getAdresa();
		if(!this.osoba.getZpusobilost().equals(Zpusobilost.PLNA)){
			this.opravnena=this.osoba.getZpusobilost().getZastupce();
			this.opr_postaveni=ZZST;
		}
	}
	
	/**
	 * Územní pracoviště v, ve, pro);
	 * @param c_pracufo sídlo územního pracoviště, na němž je nebo bude umístěn spis daňového subjektu (§ 13 zákona o Finanční správě České republiky)
	 */
	public void setCpracufo(int c_pracufo){
		this.c_pracufo=c_pracufo;
	}
	
	/**
	 * Osoba opravnena podat k subjektu - pokud neni zpusobily k pravnm ukonum
	 * Pokud ovsem OSVC neni zpusobily, opravnena osoba se nastavi v setPoplatnik automaticky
	 * @param o opravnena osoba
	 * @param opr_postaveni postaveni osoby k subjektu
	 */
	public void setOpravnena(ISEOOsoba o,String opr_postaveni){
		this.opravnena=o;
		this.opr_postaveni=opr_postaveni;
	}
	
	/**
	 * @param Pobyt k poslednimu dni kalendarniho roku, za ktery se dan vymeruje (pokud ruzne od bydliste)
	 */
	public void setKrok(Adresa a){
		this.krok=a;
	}
	
	/**
	 * @param Adresa mista pobytu na uzemi CR, kde se poplatnik obvykle ve zdanovacim obdobi zdrzoval - pokud neni bydliste na uzemi CR
	 */
	public void setZdrz(Adresa a){
		this.zdrz=a;
	}
	
	/**
	 * Evidencni cislo osvedceni danoveho poradce
	 * @param evc
	 */
	public void setEvCislo(String evc){
		this.zast_ev_cislo=evc;
	}
	
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaP=EPO.createElement(ELEMENT);
		
		if(osoba.getFax()!=0) VetaP.setAttribute("c_faxu", osoba.getFax()+"");
		if(osoba.getAdresa()!=null){
			VetaP.setAttribute("c_obce", Obce.getObec(osoba.getAdresa())+"");
			VetaP.setAttribute("c_orient", osoba.getAdresa().getOrientacni()+"");
			VetaP.setAttribute("c_pop",osoba.getAdresa().getPopisne()+"");
			if(osoba.getAdresa().getCast().equals(osoba.getAdresa().getObec())) VetaP.setAttribute("naz_obce",osoba.getAdresa().getCast());
			else VetaP.setAttribute("naz_obce",osoba.getAdresa().getObec());
			VetaP.setAttribute("stat", osoba.getAdresa().getStat().getAlpha2());
			VetaP.setAttribute("psc",osoba.getAdresa().getPSC()+"");
			VetaP.setAttribute("ulice",osoba.getAdresa().getUlice());
		}
		if(krok!=null&&krok!=osoba.getAdresa()){
			VetaP.setAttribute("krok_c_obce",Obce.getObec(krok)+""); 
			VetaP.setAttribute("krok_c_orient",krok.getOrientacni()+""); 
			VetaP.setAttribute("krok_c_pop",krok.getPopisne()+"");
			if(krok.getCast().equals(krok.getObec())) VetaP.setAttribute("krok_naz_obce",krok.getObec());
			else VetaP.setAttribute("krok_naz_obce",krok.getCast());
			VetaP.setAttribute("krok_psc",krok.getPSC()+"");
			VetaP.setAttribute("krok_ulice",krok.getUlice());
			VetaP.setAttribute("k_stat",krok.getStat().getAlpha2());
		}
		if(osoba.getAdresa().getStat()!=CountryCode.CZ){
			if(zdrz==null) throw new ConditionException("Bydliste neni na uzemi CR a mista pobytu na uzemi CR, kde se poplatnik obvykle ve zdanovacim obdobi zdrzoval nani zadano");
			else{
				if(osoba.getFax()!=0)VetaP.setAttribute("z_c_faxu",osoba.getFax()+""); 
				VetaP.setAttribute("z_c_obce",Obce.getObec(zdrz)+"");
				VetaP.setAttribute("z_c_orient", zdrz.getOrientacni()+"");
				VetaP.setAttribute("z_c_pop", zdrz.getPopisne()+"");
				if(osoba.getTelefon()!=0) VetaP.setAttribute("z_c_telef",osoba.getTelefon()+""); 
				if(osoba.getEmail()!=null)VetaP.setAttribute("z_email",osoba.getEmail()); 
				VetaP.setAttribute("z_naz_obce", zdrz.getObec());
				VetaP.setAttribute("z_psc",zdrz.getPSC()+""); 
				VetaP.setAttribute("z_ulice",zdrz.getUlice()+"");
			}
		}
		if(c_pracufo!=C_PRACUFO_UNINIT) VetaP.setAttribute("c_pracufo", c_pracufo+"");
		if(osoba.getTelefon()!=0) VetaP.setAttribute("c_telef", osoba.getTelefon()+"");
		if(osoba.getDIC()!=0) VetaP.setAttribute("dic",osoba.getDIC()+"");//TODO textova reprezentace nutno zachovat vodici nuly
		if(osoba.getEmail()!=null) VetaP.setAttribute("email",osoba.getEmail());
		if(osoba.hasDruhe()) VetaP.setAttribute("jmeno", osoba.getKrestni()+" "+osoba.getDruhe());
		else VetaP.setAttribute("jmeno",osoba.getKrestni());
		VetaP.setAttribute("prijmeni", osoba.getPrijmeni());
		VetaP.setAttribute("rodnepr",osoba.getRodnePrijmeni());
		VetaP.setAttribute("rod_c",osoba.getRodneCislo().toString());//TODO Textov������ reprezentace ������������sla (nutno zachovat vod������c������ nuly)
		CountryCode obc;
		if(osoba.getObcanstvi().contains(CountryCode.CZ)) {
			VetaP.setAttribute("st_prislus","cz");
			obc=CountryCode.CZ;
		}
		//ma obcanstvi EU a ma obcanstvi zeme, kde zije
		else if(getResidence(osoba)!=null){
			obc=getResidence(osoba);
			VetaP.setAttribute("st_prislus", obc.getAlpha2()); 
		}
		//ma obcanstvi EU a nema obcanstvi zeme, kde zije - vezmu libovolne jine EU
		else if(getEU(osoba)!=null){
			obc=getResidence(osoba);
			VetaP.setAttribute("st_prislus",obc.getAlpha2());
		}
		else if(osoba.getObcanstvi().contains(osoba.getAdresa().getStat())){//ma obcanstvi statu, ve kterem zije
			obc=osoba.getAdresa().getStat();
			VetaP.setAttribute("st_prislus", obc.getAlpha2());
		}
		else {
			obc=(CountryCode)osoba.getObcanstvi().toArray()[0];//v dany moment je jedno, ktere obcanstvi zvolime
			VetaP.setAttribute("st_prislus", obc.getAlpha2());
		}
		if(osoba.getAdresa().getStat()!=CountryCode.CZ){//nerezident vyplni cislo pasu
			VetaP.setAttribute("c_pasu",osoba.getPas(osoba.getAdresa().getStat()));
		}
		if(osoba.getTitul()!=null) VetaP.setAttribute("titul", osoba.getTitul());
		if(opravnena!=null) {
			if(opravnena.hasDruhe()) VetaP.setAttribute("opr_jmeno", opravnena.getKrestni()+" "+opravnena.getDruhe());
			else VetaP.setAttribute("opr_jmeno", opravnena.getKrestni());
			VetaP.setAttribute("opr_prijmeni", opravnena.getPrijmeni());
			VetaP.setAttribute("opr_postaveni",opr_postaveni);
		}
		if(zastupce!=null){
			VetaP.setAttribute("zast_kod", zast_kod.kod);
			VetaP.setAttribute("zast_typ",zast_kod.typ.getTyp()+"");
			if(zast_kod.typ.equals(OsobaTyp.FO)){
				OSVC o=(OSVC)zastupce;
				VetaP.setAttribute("zast_jmeno", zastupce.getJmeno());
				if(zast_kod.kod.equals(ZastKod.FODAPOAD)){
					if(zast_ev_cislo!=null)VetaP.setAttribute("zast_ev_cislo", zast_ev_cislo);
					else VetaP.setAttribute("zast_dat_nar",DF.format(o.getNarozeni()));
				}else VetaP.setAttribute("zast_dat_nar", DF.format(o.getNarozeni()));
			}else{
				VetaP.setAttribute("zast_nazev", zastupce.getJmeno());
				VetaP.setAttribute("zast_ic",zastupce.getIC()+"");
			}
		}
		return VetaP;
	}

	@Override
	public int getMaxPocet() {
		return MAX;
	}
	
	private CountryCode getEU(ISEOOsoba o){
		for(CountryCode c:o.getObcanstvi()){
			if (EU.contains(c)) return c;
		}
		return null;
	}
	
	private CountryCode getResidence(ISEOOsoba o){
		for(CountryCode c:o.getObcanstvi()){
			if(o.getAdresa().getStat().equals(c)&&EU.contains(o)) return c;
		}
		return null;
	}

	@Override
	public String[] getDependency() {
		return null;
	}
	
	@Override
	public String toString(){
		return ELEMENT;
	}

}
