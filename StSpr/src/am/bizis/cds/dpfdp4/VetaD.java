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

import am.bizis.stspr.fo.Osoba;

import taka.CountryCode;

public class VetaD implements IVeta{
	
	private final String DOKUMENT="DP4";
	private final String K_ULADIS="DPF";
	private final int KC_OP15_1A=24840;
	private final DateFormat DF=new SimpleDateFormat("dd. MM. yyyy");
	private char audit;
	private int c_ufo_cil,kc_dztrata,da_celod13,kc_csprij,kc_op15_1c,kc_op15_1d,kc_op15_1e1,
	kc_op15_1e2,kc_pausal,kc_pzdp, kc_pzzt,kc_rozdil_dp,m_cinvduch,m_deti,m_detiztpp,m_invduch,
	m_manz,m_stud, rok;
	private Date d_uv, d_duvpod, zobd_do, zobd_od;
	private double da_slevy,da_slevy35ba,da_slevy35c,da_slezap,kc_danbonus,kc_dazvyhod,
	kc_konkurs,kc_manztpp, kc_rozdbonus, kc_rozdil_zt,kc_slevy35c,kc_sraz367,kc_sraz3810,
	kc_sraz385,kc_sraz_rezehp,kc_stud,kc_vyplbonus,kc_zalpred,kc_zalzavc,kc_zbyvpred,kc_zjidp,
	kc_zjizt,sleva_rp,uhrn_slevy35ba;
	private CountryCode kod_popl;
	private DAPTyp dap_typ;
	private DAPDuvod duvodpoddapdpf;
	private Osoba manz,uv_podpis;
	private boolean pln_moc, prop_zahr;
	private UVVyhl uv_vyhl;
	
	public VetaD(char audit,int c_ufo_cil,DAPTyp dap_typ, boolean pln_moc) {
		this.audit=audit;
		this.c_ufo_cil=c_ufo_cil;
		this.dap_typ=dap_typ;
		this.setPln_moc(pln_moc);
		
	}
	/**
	 * @return the audit
	 */
	public char getAudit() {
		return audit;
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
	 * @return the c_ufo_cil
	 */
	public int getC_ufo_cil() {
		return c_ufo_cil;
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
	 * Položka obsahuje kritické kontroly: musí být vyplněno číslo existujícího FÚ, nesmí se 
	 * jednat o zaniklý FÚ. TODO
	 */
	public void setC_ufo_cil(int c_ufo_cil) {
		this.c_ufo_cil = c_ufo_cil;
	}
	/**
	 * @return the d_uv
	 */
	public Date getD_uv() {
		return d_uv;
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
	 * @return the da_celod13
	 */
	public double getDa_celod13() {
		return da_celod13;
	}
	/**
	 * @param da_celod13 Daň celkem zaokrouhlená na celé Kč nahoru (ř. 58)
	 */
	public void setDa_celod13(double da_celod13) {
		this.da_celod13 = (int)Math.round(da_celod13);
	}
	/**
	 * @return the da_slevy
	 */
	public double getDa_slevy() {
		return da_slevy;
	}
	/**
	 * @param da_slevy Slevy celkem podle § 35 odst. 1 zákona
	 */
	public void setDa_slevy(double da_slevy) {
		this.da_slevy = da_slevy;
	}
	/**
	 * @return the da_slevy35ba
	 */
	public double getDe_slevy35ba() {
		return da_slevy35ba;
	}
	/**
	 * @param da_slevy35ba Daň po uplatnění slev podle § 35, § 35a, 35b, a § 35ba zákona (ř. 60 - ř. 70)
	 */
	public void setDa_slevy35ba(double da_slevy35ba) {
		this.da_slevy35ba = da_slevy35ba;
	}
	/**
	 * @return the da_slezap
	 */
	public double getDa_slezap() {
		return da_slezap;
	}
	/**
	 * @param da_slezap Daň podle § 16 zákona (ř. 57) nebo částka z ř. 330 přílohy č. 3 DAP
	 */
	public void setDa_slezap(double da_slezap) {
		this.da_slezap = da_slezap;
	}
	/**
	 * @return the da_slevy35c
	 */
	public double getDa_slevy35c() {
		return da_slevy35c;
	}
	/**
	 * @param da_slevy35c Daň po uplatnění slevy podle § 35c zákona (ř. 71 - ř. 73)
	 */
	public void setDa_slevy35c(double da_slevy35c) {
		this.da_slevy35c = da_slevy35c;
	}
	/**
	 * @return the dap_typ
	 */
	public DAPTyp getDap_typ() {
		return dap_typ;
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
	 * @return 	Typ dokumentu
	 * Název dokumentu, musí být uvedeno DP4.
	 */
	public String getDokument() {
		return DOKUMENT;
	}
	/**
	 * @return the duvodpoddapdpf
	 */
	public DAPDuvod getDuvodpoddapdpf() {
		return duvodpoddapdpf;
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
	 * @return the d_duvpod
	 */
	public Date getD_duvpod() {
		return d_duvpod;
	}
	/**
	 * @param d_duvpod the d_duvpod to set
	 */
	public void setD_duvpod(Date d_duvpod) {
		this.d_duvpod = d_duvpod;
	}
	/**
	 * @return the kUladis
	 */
	public String getkUladis() {
		return K_ULADIS;
	}
	/**
	 * @return the kc_csprij
	 */
	public int getKc_csprij() {
		return kc_csprij;
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
	 * @return the kc_danbonus
	 */
	public double getKc_danbonus() {
		return kc_danbonus;
	}
	/**
	 * @param kc_danbonus Daňový bonus (ř. 72 - ř. 73)
	 * Uveďte rozdíl daňového zvýhodnění a slevy na dani, jehož výsledkem je výše daňového bonusu. 
	 * Daňový bonus můžete uplatnit při splnění podmínek stanovených v §35c zákona, pokud jeho výše 
	 * činí alespoň 100 Kč, maximálně však do výše 60 300 Kč (resp. 52 200 Kč pro 2011 a starší) ročně.
	 */
	public void setKc_danbonus(double kc_danbonus) {
		this.kc_danbonus = kc_danbonus;
	}
	/**
	 * @return the kc_dazvyhod
	 */
	public double getKc_dazvyhod() {
		return kc_dazvyhod;
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
	 * @return the kc_dztrata
	 */
	public int getKc_dztrata() {
		return kc_dztrata;
	}
	/**
	 * @param kc_dztrata Daňová ztráta - zaokrouhlená na celé Kč nahoru bez znaménka mínus
	 */
	public void setKc_dztrata(double kc_dztrata) {
		this.kc_dztrata = (int) Math.round(Math.abs(kc_dztrata));
	}
	/**
	 * @return the kc_konkurs
	 */
	public double getKc_konkurs() {
		return kc_konkurs;
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
	 * @return the kc_manztpp
	 */
	public double getKc_manztpp() {
		return kc_manztpp;
	}
	/**
	 * @param kc_manztpp b písm. b) zákona (na manželku/manžela, která/který je držitelem ZTP/P)
	 */
	public void setKc_manztpp(double kc_manztpp) {
		this.kc_manztpp = kc_manztpp;
	}
	/**
	 * @return the kcOp151a písm. a) zákona (na poplatníka)
	 */
	public int getKcOp151a() {
		return KC_OP15_1A;
	}
	/**
	 * @return the kc_op15_1c
	 */
	public int getKc_op15_1c() {
		return kc_op15_1c;
	}
	/**
	 * @param kc_op15_1c a písm. b) zákona (na manželku/manžela)
	 */
	public void setKc_op15_1c(int kc_op15_1c) {
		this.kc_op15_1c = kc_op15_1c;
	}
	/**
	 * @return the kc_op15_1d
	 */
	public int getKc_op15_1d() {
		return kc_op15_1d;
	}
	/**
	 * @param kc_op15_1d písm. c) zákona (na poživatele invalidního důchodu pro invaliditu prvního nebo druhého stupně
	 */
	public void setKc_op15_1d(int kc_op15_1d) {
		this.kc_op15_1d = kc_op15_1d;
	}
	/**
	 * @return the kc_op15_1e1
	 */
	public int getKc_op15_1e1() {
		return kc_op15_1e1;
	}
	/**
	 * @param kc_op15_1e1	písm. d) zákona (na poživatele invalidního důchodu pro invaliditu třetího stupně
	 */
	public void setKc_op15_1e1(int kc_op15_1e1) {
		this.kc_op15_1e1 = kc_op15_1e1;
	}
	/**
	 * @return the kc_op15_1e2
	 */
	public int getKc_op15_1e2() {
		return kc_op15_1e2;
	}
	/**
	 * @param kc_op15_1e2 písm. e) zákona (na držitele průkazu ZTP/P)
	 */
	public void setKc_op15_1e2(int kc_op15_1e2) {
		this.kc_op15_1e2 = kc_op15_1e2;
	}
	/**
	 * @return the kc_pausal
	 */
	public int getKc_pausal() {
		return kc_pausal;
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
	 * @return the kc_pzdp
	 */
	public int getKc_pzdp() {
		return kc_pzdp;
	}
	/**
	 * @param kc_pzdp Poslední známá daň
	 */
	public void setKc_pzdp(int kc_pzdp) {
		this.kc_pzdp = kc_pzdp;
	}
	/**
	 * @return the kc_pzzt
	 */
	public int getKc_pzzt() {
		return kc_pzzt;
	}
	/**
	 * @param kc_pzzt Poslední známá daň
	 */
	public void setKc_pzzt(int kc_pzzt) {
		this.kc_pzzt = kc_pzzt;
	}
	/**
	 * @return the kc_rozdbonus
	 */
	public double getKc_rozdbonus() {
		return kc_rozdbonus;
	}
	/**
	 * Rozdíl na daňovém bonusu (ř. 75 - ř. 76)
	 * @param kc_rozdbonus the kc_rozdbonus to set
	 */
	public void setKc_rozdbonus(double kc_rozdbonus) {
		this.kc_rozdbonus = kc_rozdbonus;
	}
	/**
	 * @return the kc_rozdil_dp
	 */
	public int getKc_rozdil_dp() {
		return kc_rozdil_dp;
	}
	/**
	 * @param kc_rozdil_dp Rozdíl řádků (ř. 79 - ř. 78) : 
	 * zvýšení (+) - částka daně se zvyšuje, snížení (-) - částka daně se snižuje
	 */
	public void setKc_rozdil_dp(int kc_rozdil_dp) {
		this.kc_rozdil_dp = kc_rozdil_dp;
	}
	/**
	 * @return the kc_rozdil_zt
	 */
	public double getKc_rozdil_zt() {
		return kc_rozdil_zt;
	}
	/**
	 * @param kc_rozdil_zt Rozdíl řádků (ř. 82 - ř. 81) : 
	 * zvýšení (+) - daňová ztráta se zvyšuje, snížení (-) - daňová ztráta se snižuje
	 */
	public void setKc_rozdil_zt(double kc_rozdil_zt) {
		this.kc_rozdil_zt = kc_rozdil_zt;
	}
	/**
	 * @return the kc_slevy35c
	 */
	public double getKc_slevy35c() {
		return kc_slevy35c;
	}
	/**
	 * @param kc_slevy35c Sleva na dani (částka z ř. 72, uplatněná maximálně do výše daně na ř. 71)
	 */
	public void setKc_slevy35c(double kc_slevy35c) {
		this.kc_slevy35c = kc_slevy35c;
		//TODO: maximalne do vyse ...
	}
	/**
	 * @return the kc_sraz367
	 */
	public double getKc_sraz367() {
		return kc_sraz367;
	}
	/**
	 * Sražená daň podle § 36 odst. 6 zákona (státní dluhopisy)
	 * @param kc_sraz367 the kc_sraz367 to set
	 */
	public void setKc_sraz367(double kc_sraz367) {
		this.kc_sraz367 = kc_sraz367;
	}
	/**
	 * @return the kc_sraz3810
	 */
	public double getKc_sraz3810() {
		return kc_sraz3810;
	}
	/**
	 * @param kc_sraz3810 Sražená daň dle § 38f odst. 12 zákona
	 */
	public void setKc_sraz3810(double kc_sraz3810) {
		this.kc_sraz3810 = kc_sraz3810;
	}
	/**
	 * @return the kc_sraz385
	 */
	public double getKc_sraz385() {
		return kc_sraz385;
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
	 * @return the kc_sraz_rezehp
	 */
	public double getKc_sraz_rezehp() {
		return kc_sraz_rezehp;
	}
	/**
	 * @param kc_sraz_rezehp a Sražená daň podle § 36 odst. 7 zákona
	 */
	public void setKc_sraz_rezehp(double kc_sraz_rezehp) {
		this.kc_sraz_rezehp = kc_sraz_rezehp;
	}
	/**
	 * @return the kc_stud
	 */
	public double getKc_stud() {
		return kc_stud;
	}
	/**
	 * @param kc_stud písm. f) zákona (studium)
	 */
	public void setKc_stud(double kc_stud) {
		this.kc_stud = kc_stud;
	}
	/**
	 * @return the kc_vyplbonus
	 */
	public double getKc_vyplbonus() {
		return kc_vyplbonus;
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
	 * @return the kc_zalpred
	 */
	public double getKc_zalpred() {
		return kc_zalpred;
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
	 * @return the kc_zalzavc
	 */
	public double getKc_zalzavc() {
		return kc_zalzavc;
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
	 * @return the kc_zbyvpred
	 */
	public double getKc_zbyvpred() {
		return kc_zbyvpred;
	}
	/**
	 * @param kc_zbyvpred Zbývá doplatit 
	 * (ř. 74 - ř. 77 - ř. 84 - ř. 85 - ř. 86 - ř. 87 - ř. 87a - ř. 88 - ř. 89 - ř. 90):
	 * (+) zbývá doplatit, (-) zaplaceno více.
	 * Kladná částka znamená, že zbývá na dani doplatit vypočtenou částku. 
	 * Záporná částka znamená, že bylo za zdaňovací období 2012 zaplaceno více. 
	 * O přeplatek je možné požádat příslušného správce daně formou žádosti, která je součástí 
	 * DAP. Obdobně se postupuje ve zdaňovacích obdobích 2011, 2010 a 2009.
	 */
	public void setKc_zbyvpred(double kc_zbyvpred) {
		this.kc_zbyvpred = kc_zbyvpred;
	}
	/**
	 * @return the kc_zjidp
	 */
	public double getKc_zjidp() {
		return kc_zjidp;
	}
	/**
	 * @param kc_zjidp Zjištěná daň podle § 141 zákona č. 280/2009 Sb., daňového řádu (ř.74)
	 * Zjištěná daňová povinnost podle § 41 zákona č.337/1992 Sb., o správě dani a poplatků, 
	 * ve znění pozdějších předpisů (ř. 74)
	 */
	public void setKc_zjidp(double kc_zjidp) {
		this.kc_zjidp = kc_zjidp;
	}
	/**
	 * @return the kc_zjizt
	 */
	public double getKc_zjizt() {
		return kc_zjizt;
	}
	/**
	 * @param kc_zjizt Zjištěná ztráta podle § 141 zákona č. 280/2009 Sb., daňového řádu (ř.61) 
	 * (2009: Zjištěná ztráta podle § 41 zákona č. 337/1992 Sb., o správě daní a poplatků, 
	 * ve znění pozdějších předpisů (ř.61))
	 */
	public void setKc_zjizt(double kc_zjizt) {
		this.kc_zjizt = kc_zjizt;
	}
	/**
	 * @return the kod_popl
	 */
	public CountryCode getKod_popl() {
		return kod_popl;
	}
	/**
	 * @param kod_popl Kód státu - vyplní jen daňový nerezident
	 * Jste-li poplatníkem podle § 2 odst. 3 zákona, tj. daňový nerezident v České republice, 
	 * který má daňovou povinnost z příjmů ze zdrojů na území České republiky, uveďte písmenný 
	 * kód státu, ve kterém jste rezidentem. Pro hodnotu této položky použijte číselník Země 
	 * (zeme). Z číselníku se vkládá položka kod2. TODO
	 * Položka obsahuje kritické kontroly: hodnota musí obsahovat kód existujícího státu a nesmí
	 * obsahovat kód CZ - Česko
	 */
	public void setKod_popl(CountryCode kod_popl) {
		if(kod_popl!=CountryCode.CZ) this.kod_popl = kod_popl;
	}
	/**
	 * @return the m_cinvduch
	 */
	public int getM_cinvduch() {
		return m_cinvduch;
	}
	/**
	 * @param m_cinvduch Počet měsíců
	 */
	public void setM_cinvduch(int m_cinvduch) {
		this.m_cinvduch = m_cinvduch;
	}
	/**
	 * @return the m_deti
	 */
	public int getM_deti() {
		return m_deti;
	}
	/**
	 * @param m_deti Celkem počet měsíců
	 */
	public void setM_deti(int m_deti) {
		this.m_deti = m_deti;
	}
	/**
	 * @return the m_detiztpp
	 */
	public int getM_detiztpp() {
		return m_detiztpp;
	}
	/**
	 * @param m_detiztpp Celkem počet měsíců se ZTP/P
	 */
	public void setM_detiztpp(int m_detiztpp) {
		this.m_detiztpp = m_detiztpp;
	}
	/**
	 * @return the m_invduch
	 */
	public int getM_invduch() {
		return m_invduch;
	}
	/**
	 * @param m_invduch Počet měsíců
	 */
	public void setM_invduch(int m_invduch) {
		this.m_invduch = m_invduch;
	}
	/**
	 * @return the m_manz
	 */
	public int getM_manz() {
		return m_manz;
	}
	/**
	 * @param m_manz b) Počet měsíců
	 */
	public void setM_manz(int m_manz) {
		this.m_manz = m_manz;
	}
	/**
	 * @return the m_stud
	 */
	public int getM_stud() {
		return m_stud;
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
	 * manz_r_cislo
	 * textová reprezentace čísla (nutno zachovat vodící nuly) TODO
	 */
	public String getManzRCislo(){
		return this.manz.getRodneCislo().toString();
	}
	
	/**
	 * manz_titul
	 */
	public String getManzTitul(){
		return this.manz.getTituly().toString();//TODO testme!!
	}
	/**
	 * @return the pln_moc
	 */
	public boolean isPln_moc() {
		return pln_moc;
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
	 * @return the prop_zahr
	 */
	public boolean isProp_zahr() {
		return prop_zahr;
	}
	/**
	 * @param prop_zahr Spojení se zahraničními osobami
	 */
	public void setProp_zahr(boolean prop_zahr) {
		this.prop_zahr = prop_zahr;
	}
	/**
	 * @return the rok
	 */
	public int getRok() {
		return rok;
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
	 * @return the sleva_rp
	 */
	public double getSleva_rp() {
		return sleva_rp;
	}
	/**
	 * @param sleva_rp Sleva podle § 35a nebo 35b zákona 
	 * (2009: Sleva podle § 35 odst. 6 až 8, § 35a nebo § 35b zákona)
	 */
	public void setSleva_rp(double sleva_rp) {
		this.sleva_rp = sleva_rp;
	}
	/**
	 * @return the uhrn_slevy35ba
	 */
	public double getUhrn_slevy35ba() {
		return uhrn_slevy35ba;
	}
	/**
	 * @param uhrn_slevy35ba Úhrn slev na dani podle § 35, § 35a, § 35b a § 35ba zákona
	 * (ř. 62 + ř. 63 + ř. 64 + ř. 65a + ř. 65b + ř. 66 + ř. 67 + ř. 68 + ř. 69)
	 */
	public void setUhrn_slevy35ba(double uhrn_slevy35ba) {
		this.uhrn_slevy35ba = uhrn_slevy35ba;
	}
	/**
	 * @return the uv_podpis
	 */
	public Osoba getUv_podpis() {
		return uv_podpis;
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
	 * @return the uv_vyhl
	 */
	public UVVyhl getUv_vyhl() {
		return uv_vyhl;
	}
	/**
	 * @param uv_vyhl 	Vyhláška č.: Vyplňte číslo vyhlášky (první část), podle které byly 
	 * účetní výkazy a následně vybrané údaje sestaveny.
	 */
	public void setUv_vyhl(UVVyhl uv_vyhl) {
		this.uv_vyhl = uv_vyhl;
	}
	
	public String getZobdDo() throws ParseException{
		zobd_do=DF.parse("31.12."+rok);
		return DF.format(zobd_do);
	}
	
	public String getZobdOd() throws ParseException{
		zobd_od=DF.parse("1.1."+rok);
		return DF.format(zobd_od);
	}
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaD=EPO.createElement("VetaD");
		//TODO atributy
		return VetaD;
	}
}
