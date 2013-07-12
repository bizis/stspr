package am.bizis.cds.dpfdp4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.stspr.exception.ConditionException;
import am.bizis.stspr.fo.Osoba;

import taka.CountryCode;

/**
 * Vytvori element VetaD pisemnosti DPFDP4 - Zaznam o DAP
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#D
 * @author alex
 * @version 20130711
 */
public class VetaD implements IVeta{
	private final String DOKUMENT="DP4";
	private final String K_ULADIS="DPF";
	private final int KC_OP15_1A=24840,MAX=1;
	private final DateFormat DF=new SimpleDateFormat("dd.MM.yyyy");
	private char audit;
	private int c_ufo_cil,m_cinvduch,m_deti,m_detiztpp,m_invduch, m_manz,m_vyzmanzl,m_stud,m_ztpp, rok;
	private Date d_uv, d_duvpod, zdobd_do, zdobd_od, d_zjist;
	private double da_slevy,da_slevy35ba,da_slevy35c,da_slezap,kc_danbonus,kc_dazvyhod,kc_konkurs,kc_manztpp,kc_rozdbonus,kc_rozdil_zt,
	kc_slevy35c,kc_sraz367,kc_sraz3810,kc_sraz385,kc_sraz_rezehp,kc_stud,kc_vyplbonus,kc_zalpred,kc_zalzavc,kc_zbyvpred,sleva_rp,uhrn_slevy35ba,
	kc_rozdil_dp,kc_dztrata,da_celod13,kc_csprij,kc_op15_1c,kc_op15_1d,kc_op15_1e1,kc_op15_1e2,kc_pausal,kc_pzdp, kc_pzzt;
	private CountryCode kod_popl;
	private DAPTyp dap_typ;
	private DAPDuvod duvodpoddapdpf;
	private Osoba manz,uv_podpis;
	private boolean pln_moc, prop_zahr;
	private UVVyhl uv_vyhl;
	
	public VetaD(char audit,int c_ufo_cil,DAPTyp dap_typ, boolean pln_moc,int rok) throws ParseException {
		this.audit=audit;
		this.c_ufo_cil=c_ufo_cil;
		this.dap_typ=dap_typ;
		this.pln_moc=pln_moc;
		this.rok=rok;
		zdobd_do=DF.parse("31.12."+rok);
		zdobd_od=DF.parse("1.1."+rok);
	}
	/**
	 * @param audit Zákonná povinnost ověření účetní závěrky auditorem
	 * Hodnota může být pouze:
	 * A - zákonná povinnost ověřit účetní závěrku auditorem, 
	 * N - není zákonná povinnost ověřit účetní závěrku auditorem.
	 */
	public void setAudit(char audit) {
		if (audit=='A'|audit=='N') this.audit = audit;
		else throw new IllegalArgumentException("Povolene hodnoty jsou 'A' a 'N'");
	}
	/**
	 * @param c_ufo_cil Sidlo mistne prislusneho spravce dane
	 * Místní příslušnost se řídí u fyzické osoby místem pobytu v České republice, jinak místem,
	 * kde se převážně zdržuje, tj. v němž pobývá nejvíce dnů v roce. Místem pobytu fyzické 
	 * osoby se rozumí adresa místa trvalého pobytu. Nelze-li takto určit místní příslušnost, 
	 * postupuje se podle ustanovení § 13 zákona č. 280/2009 Sb., daňový řád, ve znění pozdějších
	 * předpisů. Vyplňte číslo FÚ podle registrace daňového přiznání, nebo výběrem ze seznamu.
	 * Pro hodnotu této položky použijte číselník Územní finanční orgány (ufo). Z číselníku se 
	 * vkládá položka c_ufo.
	 * Položka obsahuje kritické kontroly: musí být vyplněno číslo existujícího FÚ (-> beru z ciselniku), nesmí se 
	 * jednat o zaniklý FÚ. TODO
	 */
	public void setC_ufo_cil(int c_ufo_cil) {
		this.c_ufo_cil = c_ufo_cil;
	}
	/**
	 * @param d_uv Údaje ke dni:
	 * Vyplňte datum, ke kterému se údaje tabulek vztahují.
	 * Položka obsahuje kritickou kontrolu: hodnota musí být zadána pokud je vyplněn alespoň 
	 * jeden výkaz.
	 */
	public void setD_uv(Date d_uv) {
		this.d_uv = d_uv;
	}
	/**
	 * @param da_celod13 Daň celkem zaokrouhlená na celé Kč nahoru (ř. 58)
	 */
	public void setDa_celod13(double da_celod13) {
		this.da_celod13 = (int)Math.round(da_celod13);
	}
	/**
	 * @param da_slevy Slevy celkem podle § 35 odst. 1 zákona
	 */
	public void setDa_slevy(double da_slevy) {
		this.da_slevy = da_slevy;
	}
	/**
	 * @param da_slevy35ba Daň po uplatnění slev podle § 35, § 35a, 35b, a § 35ba zákona (ř. 60 - ř. 70)
	 */
	public void setDa_slevy35ba(double da_slevy35ba) {
		this.da_slevy35ba = da_slevy35ba;
	}
	/**
	 * @param da_slezap Daň podle § 16 zákona (ř. 57) nebo částka z ř. 330 přílohy č. 3 DAP
	 */
	public void setDa_slezap(double da_slezap) {
		this.da_slezap = da_slezap;
	}
	/**
	 * @param dap_typ DAP
	 * Typ daňového přiznání.
	 * B - řádné
	 * O - řádné-opravné
	 * D - dodatečné
	 * E - dodatečné-opravné
	 * Položka obsahuje kritickou kontrolu kontrolující platnou hodnotu z uvedeného seznamu.
	 */
	public void setDap_typ(DAPTyp dap_typ) {
		this.dap_typ = dap_typ;
	}
	/**
	 * @param duvodpoddapdpf Kód rozlišení typu DAP
	 * Elektronický formulář pro přiznání k dani z příjmů fyzických osob nelze použít pro podání
	 * za část zdaňovacího období. EPO DAP s kódem I nebo G lze podat pouze za celé ZO; tzn., že
	 * došlo ke sjednocení lhůt pro podání DAP podle § 245 DŘ.
	 * 
	 * 2012
	 * Vyberte příslušný kód rozlišení typu DAP a uveďte datum, kdy skutečnost nastala
	 * G. insolvence – za předcházející zdaňovací období, pokud nebylo DAP dosud podáno a lhůta 
	 * pro jeho podání neuplynula (§ 245 daňového řádu)
	 * H. pro roky 2010 až 2012 nelze uplatnit
	 * I. úmrtí – do 6 měsícůpo úmrtí poplatníka podle § 239 odst. 3 daňového řádu a za předcházející 
	 * zdaňovací období, pokud DAP nebylo dosud podáno a lhůta pro jeho podání neuplynula, podle § 245 
	 * daňového řádu
	 * 
	 * @param d_duvpod Datum 
	 * Vyplňte datum kdy skutečnost nastala.
	 * Položka obsahuje kritické kontroly:
	 * 
	 * V případě DAP DPF podávaném za zemřelý DS je nutné do položky Datum v záhlaví DAP uvést 
	 * datum úmrtí
	 * 
	 * V případě DAP DPF podávaného v průběhu insolvence za bezprostředně předcházející 
	 * zdaňovací období, za které nebylo DAP dosud podáno, je nutné do položky Datum v záhlaví 
	 * DAP uvést datum skutečnosti, ke které se DAP váže
	 * 
	 * V případě DAP DPF podávaného v průběhu insolvence za zdaňovací období, u něhož již 
	 * uplynula lhůta pro podání DAP, ale nebylo dosud podáno, je nutné do položky Datum v 
	 * záhlaví DAP uvést datum skutečnosti, ke které se DAP váže
	 * 
	 * V případě, že DAP není podáváno s kódem rozlišení = G nebo I, položka Datum v záhlaví DAP
	 * DPF nesmí být vyplněna
	 * V případě DAP s kódem rozlišení G musí Datum spadat do období bezprostředně následujícího
	 * či vyššího než za které je DAP podáváno
	 */
	public void setDuvodpoddapdpf(DAPDuvod duvodpoddapdpf, Date d_duvpod) {
		this.duvodpoddapdpf = duvodpoddapdpf;
	}
	/**
	 * @param d_duvpod the d_duvpod to set
	 */
	public void setD_duvpod(Date d_duvpod) {
		this.d_duvpod = d_duvpod;
	}
	/**
	 * @param kc_csprij Výše celosvětových příjmů
	 * Jste-li poplatníkem podle § 2 odst. 3 zákona a uplatňujete nezdanitelné části základu daně
	 * podle § 15 (do roku 2011 odst. 3 a 4) zákona nebo slevu na dani podle § 35ba odst. 1 písm.
	 * b) až e) nebo daňové zvýhodnění podle § 35c zákona, uveďte úhrn všech příjmů ze zdrojů na 
	 * území České republiky a ze zdrojů v zahraničí v celých Kč. Cizí měnu přepočtěte podle § 38
	 * odst. 1 zákona.
	 */
	public void setKc_csprij(double kc_csprij) {
		this.kc_csprij = (int)Math.round(kc_csprij);
	}
	/**
	 * @param kc_dazvyhod Daňové zvýhodnění na vyživované dítě
	 * Uveďte výši daňového zvýhodnění podle § 35c zákona. Pokud byly splněny podmínky po celé 
	 * zdaňovací období 2012, máte nárok na daňové zvýhodnění ve výši 13 404 Kčna jedno dítě. 
	 * Jedná-li se o dítě, které je držitelem průkazu ZTP/P, zvyšuje se na ně částka daňového 
	 * zvýhodnění na dvojnásobek. Vyživuje-li dítěv jedné domácnosti více poplatníků, může 
	 * daňové zvýhodnění uplatnit ve zdaňovacím období nebo v tomtéž kalendářním měsíci 
	 * zdaňovacího období jen jeden z nich.
	 */
	public void setKc_dazvyhod(double kc_dazvyhod) {
		this.kc_dazvyhod = kc_dazvyhod;
	}
	/**
	 * @param kc_dztrata Daňová ztráta - zaokrouhlená na celé Kč nahoru bez znaménka mínus
	 */
	public void setKc_dztrata(double kc_dztrata) {
		this.kc_dztrata = (int) Math.round(Math.abs(kc_dztrata));
	}
	/**
	 * @param kc_konkurs Zaplacená daňová povinnost (záloha) podle § 38gb odst. 2 zákona
	 * Uveďte, podáváte-li DAP, výši daně tvořící zálohu na daň daňové povinnosti podle podmínek
	 * uvedených v § 38gb zákona.
	 */
	public void setKc_konkurs(double kc_konkurs) {
		this.kc_konkurs = kc_konkurs;
	}
	/**
	 * @param kc_manztpp b písm. b) zákona (na manželku/manžela, která/který je držitelem ZTP/P)
	 */
	public void setKc_manztpp(double kc_manztpp) {
		this.kc_manztpp = kc_manztpp;
	}
	/**
	 * @param kc_op15_1c a písm. b) zákona (na manželku/manžela)
	 */
	public void setKc_op15_1c(int kc_op15_1c) {
		this.kc_op15_1c = kc_op15_1c;
	}
	/**
	 * @param kc_op15_1d písm. c) zákona (na poživatele invalidního důchodu pro invaliditu prvního nebo druhého stupně
	 */
	public void setKc_op15_1d(int kc_op15_1d) {
		this.kc_op15_1d = kc_op15_1d;
	}
	/**
	 * @param kc_op15_1e1	písm. d) zákona (na poživatele invalidního důchodu pro invaliditu třetího stupně
	 */
	public void setKc_op15_1e1(int kc_op15_1e1) {
		this.kc_op15_1e1 = kc_op15_1e1;
	}
	/**
	 * @param kc_op15_1e2 písm. e) zákona (na držitele průkazu ZTP/P)
	 */
	public void setKc_op15_1e2(int kc_op15_1e2) {
		this.kc_op15_1e2 = kc_op15_1e2;
	}
	/**
	 * @param kc_pausal Zaplacená daň stanovená paušální částkou podle § 7a zákona
	 * Uveďte částku daně stanovené paušální částkou podle § 7a zákona, kterou započtete na 
	 * výslednou daňovou povinnost, podáváte-li DAP podle § 7a odst. 5 zákona tj. v případě, že 
	 * jste dosáhl (dosáhla) jiných příjmů než předpokládaných.
	 */
	public void setKc_pausal(int kc_pausal) {
		this.kc_pausal = kc_pausal;
	}
	/**
	 * @param kc_pzdp Poslední známá daň
	 */
	public void setKc_pzdp(int kc_pzdp) {
		this.kc_pzdp = kc_pzdp;
	}
	/**
	 * @param kc_pzzt Poslední známá daň
	 */
	public void setKc_pzzt(int kc_pzzt) {
		this.kc_pzzt = kc_pzzt;
	}
	/**
	 * Sražená daň podle § 36 odst. 6 zákona (státní dluhopisy)
	 * @param kc_sraz367 the kc_sraz367 to set
	 */
	public void setKc_sraz367(double kc_sraz367) {
		this.kc_sraz367 = kc_sraz367;
	}
	/**
	 * @param kc_sraz3810 Sražená daň dle § 38f odst. 12 zákona
	 */
	public void setKc_sraz3810(double kc_sraz3810) {
		this.kc_sraz3810 = kc_sraz3810;
	}
	/**
	 * @param kc_sraz385 Zajištěná daň plátcem podle § 38e zákona
	 * Uveďte částku, kterou Vám jako poplatníkovi podle § 2 odst. 3 zákona, plátce daně podle
	 * § 38e zákona srazil na zajištění daně. Jste-li společník veřejné obchodní společnosti 
	 * nebo komplementář komanditní společnosti, bude částka uvedená na tomto řádku zahrnovat 
	 * zajištění daně sražené Vám veřejnou obchodní společností nebo komanditní společností 
	 * podle § 38e odst. 3 písm. a) zákona vztahující se ke zdaňovacímu období 2012 nebo k části
	 * zdaňovacího období 2012, za něž je podáváno DAP. Obdobně se postupuje ve zdaňovacích 
	 * obdobích 2009, 2010 a 2011.
	 */
	public void setKc_sraz385(double kc_sraz385) {
		this.kc_sraz385 = kc_sraz385;
	}
	/**
	 * @param kc_sraz_rezehp a Sražená daň podle § 36 odst. 7 zákona
	 */
	public void setKc_sraz_rezehp(double kc_sraz_rezehp) {
		this.kc_sraz_rezehp = kc_sraz_rezehp;
	}
	/**
	 * @param kc_stud písm. f) zákona (studium)
	 */
	public void setKc_stud(double kc_stud) {
		this.kc_stud = kc_stud;
	}
	/**
	 * @param kc_vyplbonus Úhrn vyplacených měsíčních daňových bonusů podle § 35d zákona 
	 * (včetně případného doplatku na daňovém bonusu)
	 * Uveďte úhrn měsíčních daňových bonusů, které Vám jako zaměstnanci byly zaměstnavatelem 
	 * vyplaceny za zdaňovací období 2012, resp. 2011, 2010. Údaje zjistíte z „Potvrzení“ 
	 * vystaveného jednotlivými zaměstnavateli. Pokud podáváte daňové přiznání a již Vám bylo 
	 * provedeno roční zúčtování u zaměstnavatele, pak se v Potvrzení vzor č. 20 (2011: vzor č. 
	 * 19; 2010: vzor č. 18) jedná o součet řádků 13 a doplatku na daňovém bonusu z řádku 17.
	 */
	public void setKc_vyplbonus(double kc_vyplbonus) {
		this.kc_vyplbonus = kc_vyplbonus;
	}
	/**
	 * @param kc_zalpred Na zbývajících zálohách zaplaceno poplatníkem celkem
	 * Uveďte souhrn záloh, které jste zaplatil (zaplatila), v průběhu zdaňovacího období 2012
	 * nebo části zdaňovacího období 2012, za něž je podáváno DAP, včetně přeplatku použitého
	 * jako záloha na daň podle § 154 a § 155 daňového řádu. Obdobně se postupuje ve zdaňovacích
	 * obdobích 2009, 2010 a 2011.
	 */
	public void setKc_zalpred(double kc_zalpred) {
		this.kc_zalpred = kc_zalpred;
	}
	/**
	 * @param kc_zalzavc Úhrn sražených záloh na daň z příjmů fyzických osob ze závislé činnosti
	 * a z funkčních požitků (po slevách na dani)
	 * Uveďte úhrn sražených záloh na daň z příjmů ze závislé činnosti a z funkčních požitků 
	 * (po slevách na dani), které Vám byly sraženy všemi zaměstnavateli. Zálohy na daň z příjmů
	 * ze závislé činnosti a z funkčních požitků uveďte v souladu s § 5 odst. 4 zákona (ve vzoru
	 * Potvrzení č.20 (2011: č.19; 2010: č.18; 2009: č.17) se jedná o údaj uvedený na řádku 12). 
	 * V případě, že Vám bylo provedeno roční zúčtování, uveďte částku sražených záloh sníženou 
	 * o vrácený přeplatek z ročního zúčtování.
	 */
	public void setKc_zalzavc(double kc_zalzavc) {
		this.kc_zalzavc = kc_zalzavc;
	}
	/**
	 * @param kod_popl Kód státu - vyplní jen daňový nerezident
	 * Jste-li poplatníkem podle § 2 odst. 3 zákona, tj. daňový nerezident v České republice, 
	 * který má daňovou povinnost z příjmů ze zdrojů na území České republiky, uveďte písmenný 
	 * kód státu, ve kterém jste rezidentem. Pro hodnotu této položky použijte číselník Země 
	 * (zeme). Z číselníku se vkládá položka kod2 (CZ,CA).
	 * Položka obsahuje kritické kontroly: hodnota musí obsahovat kód existujícího státu a nesmí
	 * obsahovat kód CZ - Česko
	 */
	public void setKod_popl(CountryCode kod_popl) {
		if(kod_popl!=CountryCode.CZ) this.kod_popl = kod_popl;
	}
	/**
	 * @param m_cinvduch Počet měsíců
	 */
	public void setM_cinvduch(int m_cinvduch) {
		this.m_cinvduch = m_cinvduch;
	}
	/**
	 * @param m_deti Celkem počet měsíců
	 */
	public void setM_deti(int m_deti) {
		this.m_deti = m_deti;
	}
	/**
	 * @param m_detiztpp Celkem počet měsíců se ZTP/P
	 */
	public void setM_detiztpp(int m_detiztpp) {
		this.m_detiztpp = m_detiztpp;
	}
	/**
	 * @param m_invduch Počet měsíců
	 */
	public void setM_invduch(int m_invduch) {
		this.m_invduch = m_invduch;
	}

	/**
	 * @param m_manz b) Počet měsíců
	 */
	public void setM_manz(int m_manz) {
		this.m_manz = m_manz;
	}
	/**
	 * @param m_stud Počet měsíců
	 */
	public void setM_stud(int m_stud) {
		this.m_stud = m_stud;
	}	
	/**
	 * @param manz Manzel/ka
	 */
	public void setManz(Osoba manz) {
		this.manz = manz;
	}	
	/**
	 * manz_jmeno
	 */
	public String getManzJmeno(){
		if (this.manz.hasDruhe()) return this.manz.getKrestni()+" "+this.manz.getDruhe();
		else return this.manz.getKrestni();
	}	
	/**
	 * manz_prijmeni
	 */
	public String getManzPrijmeni(){
		return this.manz.getPrijmeni();
	}	
	/**
	 * manz_titul
	 */
	public String getManzTitul(){
		return manz.getTitul();
	}	
	/**
	 * manz_r_cislo
	 * textová reprezentace čísla (nutno zachovat vodící nuly) TODO
	 */
	public String getManzRCislo(){
		return this.manz.getRodneCislo().toString();
	}	
	/**
	 * @param pln_moc DAP zpracoval a předkládá daňový poradce na základě plné moci k 
	 * zastupování, která byla podána správci daně před uplynutím neprodloužené lhůty
	 * Uveďte, zda DAP zpracoval a předkládá daňový poradce na základě plné moci.
	 */
	public void setPln_moc(boolean pln_moc) {
		this.pln_moc = pln_moc;
	}
	/**
	 * @param prop_zahr Spojení se zahraničními osobami
	 */
	public void setProp_zahr(boolean prop_zahr) {
		this.prop_zahr = prop_zahr;
	}
	/**
	 * @param rok Zdaňovací období
	 * Uveďte rok, za který je DAP podáváno.
	 * Položka obsahuje kritické kontroly: rok může nabývat hodnot 2009 nebo 2010 a dále nelze 
	 * vložit DAP na rok vyšší než je rok z data úmrtí poplatníka.
	 */
	public void setRok(int rok) {
		if(rok>=2009&&rok<=2013) this.rok = rok;
	}
	/**
	 * @param sleva_rp Sleva podle § 35a nebo 35b zákona 
	 * (2009: Sleva podle § 35 odst. 6 až 8, § 35a nebo § 35b zákona)
	 */
	public void setSleva_rp(double sleva_rp) {
		this.sleva_rp = sleva_rp;
	}
	/**
	 * @param uv_podpis Osoba, jejíž podpisový záznam byl připojen k účetní závěrce, která byla
	 * podkladem pro zpracování této přílohy
	 * Osoba, jejíž podpisový záznam byl připojen k účetní závěrce, která byla podkladem pro 
	 * zpracování této přílohy.
	 */
	public void setUv_podpis(Osoba uv_podpis) {
		this.uv_podpis = uv_podpis;
	}
	/**
	 * @param uv_vyhl 	Vyhláška č.: Vyplňte číslo vyhlášky (první část), podle které byly 
	 * účetní výkazy a následně vybrané údaje sestaveny.
	 */
	public void setUv_vyhl(UVVyhl uv_vyhl) {
		this.uv_vyhl = uv_vyhl;
	}	
	
	/**
	 * Element kc_zbyvpred (kolik zbyva doplatit)
	 * @return pokud je zaporne, je zahodno vlozit vetu N (zadost o vraceni preplatku)
	 */
	public double getKc_zbyvpred(){
		return this.kc_zbyvpred;
	}
	
	@Override
	public Element getElement() throws ParserConfigurationException {
		int MINDANBONUS=100;
		int MAXDANBONUS=60300;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaD=EPO.createElement("VetaD");
		VetaD.setAttribute("dokument", DOKUMENT);
		VetaD.setAttribute("k_uladis", K_ULADIS);
		VetaD.setAttribute("audit", audit+"");
		VetaD.setAttribute("c_ufo_cil", c_ufo_cil+"");
		VetaD.setAttribute("dap_typ", dap_typ.dap_typ);
		if(dap_typ.equals(DAPTyp.D)||dap_typ.equals(DAPTyp.E)) VetaD.setAttribute("d_zjist", DF.format(d_zjist));
		if(duvodpoddapdpf!=null){
			VetaD.setAttribute("duvodpoddapdpf", duvodpoddapdpf.duvodpoddapdpf);
			if(this.duvodpoddapdpf.equals(DAPDuvod.G) && !d_duvpod.after(zdobd_do)) throw new ConditionException("V případě DAP s kódem rozlišení G musí Datum spadat do období bezprostředně následujícího či vyššího než za které je DAP podáváno");
			VetaD.setAttribute("d_duvpod", DF.format(d_duvpod));
		}
		VetaD.setAttribute("d_uv", DF.format(d_uv));
		if(pln_moc) VetaD.setAttribute("pln_moc", "A");
		else VetaD.setAttribute("pln_moc","N");
		if(da_celod13!=0) VetaD.setAttribute("da_celod13", da_celod13+"");
		if(da_slevy!=0) VetaD.setAttribute("da_slevy", da_slevy+"");
		if(da_slevy35ba!=0) VetaD.setAttribute("da_slevy35ba",da_slevy35ba+"");
		if(da_slezap!=0) VetaD.setAttribute("da_slezap", da_slezap+"");
		if(kod_popl!=null&&kod_popl!=CountryCode.CZ) {
			VetaD.setAttribute("kc_csprij",kc_csprij+"");
			VetaD.setAttribute("kod_popl", kod_popl.getAlpha2());
		}
		if(kc_dazvyhod>0){
			VetaD.setAttribute("kc_dazvyhod", kc_dazvyhod+"");
			if(this.kc_dazvyhod>this.da_slevy35ba) this.kc_slevy35c=this.da_slevy35ba;
			else this.kc_slevy35c=this.da_slevy35ba;
			
			if (kc_slevy35c>0) VetaD.setAttribute("kc_slevy35c", kc_slevy35c+"");
			this.kc_danbonus=this.kc_dazvyhod-this.kc_slevy35c;
			if(kc_danbonus>MINDANBONUS){
				if(kc_danbonus>MAXDANBONUS) {
					kc_danbonus=MAXDANBONUS;
					VetaD.setAttribute("kc_danbonus", ""+MAXDANBONUS);
				}
				else VetaD.setAttribute("kc_danbonus", kc_danbonus+"");
				if(kc_vyplbonus>=0){
					VetaD.setAttribute("kc_vyplbonus", "kc_vyplbonus");
					this.kc_rozdbonus=kc_danbonus-kc_vyplbonus;
					VetaD.setAttribute("kc_rozdbonus", kc_rozdbonus+"");
				}
			}
		}
		da_slevy35c=da_slevy35ba-kc_slevy35c;
		VetaD.setAttribute("da_slevy35c", da_slevy35c+"");
		if(kc_dztrata!=0) VetaD.setAttribute("kc_dztrata", kc_dztrata+"");
		if(kc_konkurs!=0) VetaD.setAttribute("kc_konkurs", kc_konkurs+"");
		if(kc_manztpp!=0){
			VetaD.setAttribute("kc_manztpp", kc_manztpp+"");
			VetaD.setAttribute("m_vyzmanzl", m_vyzmanzl+"");
		}
		VetaD.setAttribute("kc_op15_1a", KC_OP15_1A+"");
		if(kc_op15_1c>0){
			VetaD.setAttribute("kc_op15_1c", kc_op15_1c+"");
			VetaD.setAttribute("m_manz", m_manz+"");
		}
		if(kc_op15_1c>0||kc_manztpp!=0){
			VetaD.setAttribute("manz_jmeno",getManzJmeno());
			VetaD.setAttribute("manz_prijmeni", getManzPrijmeni());
			VetaD.setAttribute("manz_r_cislo", getManzRCislo());
			VetaD.setAttribute("manz_titul", getManzTitul());
		}
		if(kc_op15_1d>0&&m_cinvduch>0){
			VetaD.setAttribute("kc_op15_1d", kc_op15_1d+"");
			VetaD.setAttribute("m_cinvduch", m_cinvduch+"");
		}
		if(kc_op15_1e1>0){
			VetaD.setAttribute("kc_op15_1e1", kc_op15_1e1+"");
			VetaD.setAttribute("m_invduch", m_invduch+"");
		}
		if(kc_op15_1e2>0){
			VetaD.setAttribute("kc_op15_1e2", kc_op15_1e2+"");
			VetaD.setAttribute("m_ztpp", m_ztpp+"");
		}
		if(kc_pausal!=0) VetaD.setAttribute("kc_pausal", kc_pausal+"");
		
		//Dodatecne DAP - overit i presto, ze popis struktury toto nebere jako podminku
		if(this.dap_typ==DAPTyp.D|this.dap_typ==DAPTyp.E){
			if(kc_pzdp!=0) VetaD.setAttribute("kc_pzdp", kc_pzdp+"");
			if(kc_pzzt!=0) VetaD.setAttribute("kc_pzzt", kc_pzzt+"");
			kc_rozdil_dp=da_slevy35c-kc_pzdp;
			VetaD.setAttribute("kc_rozdil_dp", kc_rozdil_dp+"");
			kc_rozdil_zt=kc_dztrata-kc_pzzt;
			VetaD.setAttribute("kc_rozdil_zt", kc_rozdil_zt+"");
		}
		if(kc_sraz367!=0) VetaD.setAttribute("kc_sraz367", kc_sraz367+"");
		if(kc_sraz3810!=0) VetaD.setAttribute("kc_sraz3810", kc_sraz3810+"");
		if(kc_sraz385!=0) VetaD.setAttribute("kc_sraz385", kc_sraz385+"");
		if(kc_sraz_rezehp!=0) VetaD.setAttribute("kc_sraz_rezehp", kc_sraz_rezehp+"");
		if(kc_stud!=0){
			VetaD.setAttribute("kc_stud", kc_stud+"");
			VetaD.setAttribute("m_stud", m_stud+"");
		}
		VetaD.setAttribute("kc_zalpred", kc_zalpred+"");
		kc_zbyvpred=da_slevy35c-kc_rozdbonus-kc_zalzavc-kc_zalpred-kc_pausal-kc_sraz_rezehp-kc_sraz367-kc_sraz385-kc_sraz3810-kc_konkurs;
		VetaD.setAttribute("kc_zbyvpred",kc_zbyvpred+"");
		//pokud je zaporne - zadost o preplatek - vykonava tvurce EPO
		VetaD.setAttribute("kc_zjidp", da_slevy35c+"");
		VetaD.setAttribute("kc_zjizt", kc_dztrata+"");
		if(m_deti!=0) VetaD.setAttribute("m_deti",m_deti+"");
		if(m_detiztpp!=0) VetaD.setAttribute("m_detiztpp", m_detiztpp+"");
		if(prop_zahr) VetaD.setAttribute("prop_zahr", "A");
		else VetaD.setAttribute("prop_zahr","N");
		VetaD.setAttribute("rok",rok+"");
		if(sleva_rp!=0) VetaD.setAttribute("sleva_rp", sleva_rp+"");
		uhrn_slevy35ba=da_slevy+sleva_rp+KC_OP15_1A+kc_op15_1c+kc_manztpp+kc_op15_1d+kc_op15_1e1+kc_op15_1e2+kc_stud;
		if(uhrn_slevy35ba!=0) VetaD.setAttribute("uhrn_slevy35ba", uhrn_slevy35ba+"");
		if(uv_vyhl!=null){
			VetaD.setAttribute("uv_vyhl", uv_vyhl.vyhlaska+"");
			VetaD.setAttribute("uv_podpis", uv_podpis.getPrijmeni()+", "+uv_podpis.getKrestni());
		}
		if(this.duvodpoddapdpf!=null&&duvodpoddapdpf.equals(DAPDuvod.I)){ 
			if(zdobd_do.before(d_duvpod)) VetaD.setAttribute("zdobd_do",DF.format(zdobd_do));
			else throw new ConditionException("datum konce zdaňovacího období nemůže být větší než datum úmrtí poplatníka");
		}else VetaD.setAttribute("zdobd_do", DF.format(zdobd_do));
		
		VetaD.setAttribute("zdobd_od", DF.format(zdobd_od));
		return VetaD;
	}
	@Override
	public int getMaxPocet() {
		return MAX;
	}
}
