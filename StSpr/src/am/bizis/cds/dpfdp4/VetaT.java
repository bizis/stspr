package am.bizis.cds.dpfdp4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Priloha c. 1
 * @author alex
 *
 */
public class VetaT implements IVeta {

	private final DateFormat DF=new SimpleDateFormat("dd.MM.yyyy");
	private int c_nace,m_podnik; 
	private UcSoust uc_soust;
	private PrSazba pr_sazba;
	private Date d_obnocin, d_precin, d_ukoncin, d_zahcin;
	private double celk_pr_prij7, celk_pr_vyd7, kc_cisobr, kc_hosp_rozd, kc_odpcelk, kc_odpnem, kc_pod_komp, kc_pod_so, kc_pod_vaso, kc_prij7, kc_uhsniz, kc_uhzvys, kc_vyd7, kc_vyd_so, kc_vyd_vaso, kc_zd7p, pr_prij7, pr_vyd7;
	private boolean vyd7proc=false;

	public VetaT() {
		// Zadne povinne polozky
	}
	
	/**
	 * @param kc_vyd7 Výdaje související s příjmy podle § 7 zákona
	 */
	public void setKc_vyd7(double kc_vyd7) {
		this.kc_vyd7 = kc_vyd7;
	}

	/**
	 * @param celk_pr_prij7 Celkem příjmy
	 */
	public void setCelk_pr_prij7(double celk_pr_prij7) {
		this.celk_pr_prij7 = celk_pr_prij7;
	}

	/**
	 * @param uc_soust Typ účetní soustavy
	 * Uveďte 1, pokud vedete daňovou evidenci nebo 2 pokud vedete účetnictví.
	 * Položka obsahuje kritické kontroly: položka může nabývat hodnot 1 nebo 2; pokud je vyplněna tabulka pro DE, 
	 * musí položka uc_soust nabývat hodnoty 1; pokud jsou vyplněny vybrané údaje z ÚV, musí položka uc_soust nabývat 
	 * hodnoty 2
	 */
	public void setUc_soust(UcSoust uc_soust) {
		this.uc_soust = uc_soust;
	}

	/**
	 * @param vyd7proc Uplatňuji výdaje procentem z příjmů
	 * Uveďte, zdali uplatňujete výdaje procentem z příjmů.
	 * Položka obsahuje kritické kontroly: položka může nabývat pouze hodnot A nebo N, 
	 * vede-li poplatník účetnictví nebo DE, nelze zároveň uplatnit výdaje procentem z příjmů
	 */
	public void setVyd7proc(boolean vyd7proc) {
		this.vyd7proc = vyd7proc;
	}

	/**
	 * @param kc_pod_komp Váš podíl jako společníka veřejné obchodní společnosti nebo komplementáře komanditní společnosti. 
	 * Vykáže-li společnost ztrátu, označte svůj podíl znaménkem mínus (-)
	 * Jako společník veřejné obchodní společnosti nebo komplementář komanditní společnosti zde uveďte část základu daně 
	 * (§ 7 zákona) veřejné obchodní společnosti nebo komanditní společnosti stanoveného podle § 23 - § 33 zákona. Tato 
	 * část základu daně se stanoví ve stejném poměru, jako je rozdělován zisk podle společenské smlouvy, jinak rovným 
	 * dílem. Vykáže-li veřejná obchodní společnost nebo komanditní společnost ztrátu, rozděluje se část této ztráty stejně
	 * jako základ daně. V tomto případě označte svůj podíl znaménkem mínus (–) tzn. v konečném součtu na ř. 113 částku 
	 * odečtete.
	 */
	public void setKc_pod_komp(double kc_pod_komp) {
		this.kc_pod_komp = kc_pod_komp;
	}

	/**
	 * @param kc_vyd_vaso 	Část výdajů nebo výsledku hospodaření před zdaněním (ztráta), která připadla na Vás jako na 
	 * spolupracující osobu podle § 13 zákona
	 * Uveďte část výdajů nebo výsledku hospodaření před zdaněním (ztráta), která připadla na Vás jako na spolupracující 
	 * osobu podle § 13 zákona. Údaje o osobě podnikatele, která na Vás rozděluje podíl na společných příjmech a výdajích 
	 * připadající na spolupracující osobu nebo podíl na výsledku hospodaření (zisk, ztráta), uveďte na str. (2) do oddílu
	 * H.
	 */
	public void setKc_vyd_vaso(double kc_vyd_vaso) {
		this.kc_vyd_vaso = kc_vyd_vaso;
	}
	
	/**
	 * @param kc_cisobr Roční úhrn čistého obratu
	 * Vedete-li účetnictví, uveďte roční úhrn čistého obratu podle § 20 odst. 1 písm. a) bod 2 zákona č. 563/1991 Sb., 
	 * o účetnictví, ve znění pozdějších předpisů.
	 */
	public void setKc_cisobr(double kc_cisobr) {
		this.kc_cisobr = kc_cisobr;
	}

	/**
	 * @param pr_sazba Sazba výdajů % z příjmů
	 * 2011 a vyšší
	 * Pro výpočet výdajů uváděných na tomto řádku použijte níže uvedenou tabulku „B. Druh činnosti“. Pro zdaňovací období 
	 * 2011 si můžete uplatnit výdaje ve výši 80 % z příjmů ze zemědělské výroby, lesního a vodního hospodářství (zákon č. 
	 * 252/1997 Sb.) a z příjmů ze živností řemeslných, 60 % z příjmů ze živnosti s výjimkou příjmů ze živností řemeslných 
	 * a 40 % v ostatních případech uvedených v ustanovení § 7 odst. 7 písm. c) zákona (např. autorské honoráře) a 30 % z 
	 * příjmu z pronájmu majetku zařazeného v obchodním majetku.
	 */
	public void setPr_sazba(PrSazba pr_sazba) {
		this.pr_sazba = pr_sazba;
	}

	/**
	 * @param kc_uhsniz Úhrn částek podle § 5, § 23 a ostatní úpravy podle zákona snižující - uveďte úhrn částek 
	 * snižujících výsledek hospodaření nebo rozdíl mezi příjmy a výdaji. Podkladem jsou částky uvedené v odd. E
	 * Uveďte úhrn částek snižujících výsledek hospodaření nebo rozdíl mezi příjmy a výdaji. Podkladem jsou částky uvedené 
	 * v odd. E na str. (2). Mezi těmito částkami mohou být např.: částky rozdílu mezi účetními a daňovými odpisy, částky 
	 * úprav při ukončení nebo přerušení činnosti a při změně způsobu uplatňování výdajů. Vedete-li účetnictví, vyčleňte na
	 * tento řádek příjmy z kapitálového majetku zahrnuté ve výsledku hospodaření nebo podle § 5 odst. 11 zákona.
	 * Příjmy z kapitálového majetku jsou dílčím základem daně podle § 8 zákona – uveďte na ř. 38, 2. oddílu základní části
	 * DAP na str. 2.
	 */
	public void setKc_uhsniz(double kc_uhsniz) {
		this.kc_uhsniz = kc_uhsniz;
	}

	/**
	 * @param kc_odpcelk Uplatněné odpisy celkem
	 * Uveďte uplatněné odpisy z obchodního majetku Vámi evidovaného.
	 */
	public void setKc_odpcelk(double kc_odpcelk) {
		this.kc_odpcelk = kc_odpcelk;
	}

	/**
	 * @param kc_pod_so Část příjmů nebo výsledku hospodaření před zdaněním (zisk), kterou rozdělujete na spolupracující 
	 * osobu (osoby) podle § 13 zákona
	 * Uveďte část příjmů nebo výsledku hospodaření (zisk), >kterou rozdělujete< na spolupracující osobu (osoby) podle 
	 * § 13 zákona
	 */
	public void setKc_pod_so(double kc_pod_so) {
		this.kc_pod_so = kc_pod_so;
	}
	
	/*
	 * @param kc_hosp_rozd Rozdíl mezi příjmy a výdaji (ř. 101 - ř. 102) nebo výsledek hospodaření (zisk, ztráta)
	 * Uveďte podle údajů v tiskopisu. Poplatníci vedoucí daňovou evidenci a poplatníci, kteří nevedou účetnictví, uvedou 
	 * rozdíl mezi příjmy a výdaji a poplatníci, kteří vedou účetnictví, uvedou výsledek hospodaření před zdaněním. Údaje 
	 * jsou uváděny před úpravou podle § 5 a § 23 zákona. V případě, že výdaje přesahují příjmy nebo výsledek hospodaření 
	 * před zdaněním je ztráta, označte částku znaménkem mínus.
	 *
	public void setKc_hosp_rozd(double kc_hosp_rozd) {
		this.kc_hosp_rozd = kc_hosp_rozd;
	}*/

	/**
	 * @param kc_pod_vaso Část příjmů nebo výsledku hospodaření před zdaněním (zisk), která připadla na Vás jako na 
	 * spolupracující osobu podle § 13 zákona
	 * Uveďte část příjmů nebo výsledku hospodaření před zdaněním (zisk), která připadla na Vás jako na spolupracující 
	 * osobu podle § 13 zákona.
	 */
	public void setKc_pod_vaso(double kc_pod_vaso) {
		this.kc_pod_vaso = kc_pod_vaso;
	}

	/**
	 * @param c_nace Název hlavní (převažující) činnosti
	 * Uveďte kód nace.
	 * Pro hodnotu této položky použijte číselník Činností (okec). Z číselníku se vkládá položka c_nace.
	 * Položka obsahuje kritickou kontrolu: musí být vyplněn existující kód nace.
	 */
	public void setC_nace(int c_nace) {
		this.c_nace = c_nace;
	}

	/**
	 * @param kc_odpnem Z toho odpisy nemovitostí
	 * Uveďte z celkově uplatněných odpisů z obchodního majetku poplatníka odpisy nemovitostí.
	 */
	public void setKc_odpnem(double kc_odpnem) {
		this.kc_odpnem = kc_odpnem;
	}

	/**
	 * @param celk_pr_vyd7 Celkem výdaje
	 */
	public void setCelk_pr_vyd7(double celk_pr_vyd7) {
		this.celk_pr_vyd7 = celk_pr_vyd7;
	}

	/**
	 * @param kc_vyd_so Část výdajů nebo výsledku hospodaření před zdaněním (ztráta), kterou rozdělujete na spolupracující 
	 * osobu (osoby) podle § 13 zákona
	 * Uveďte část výdajů nebo výsledku hospodaření (ztráta), kterou rozdělujete na spolupracující osobu (osoby) podle 
	 * § 13 zákona.
	 * Údaje o osobách, na které rozdělujete podíl na společných příjmech a výdajích připadající na spolupracující osobu 
	 * (osoby) nebo podíl na výsledku hospodaření (zisk, ztráta), uveďte na str. (2) do oddílu G.
	 */
	public void setKc_vyd_so(double kc_vyd_so) {
		this.kc_vyd_so = kc_vyd_so;
	}

	/**
	 * @param m_podnik 	Počet měsíců činnosti
	 * Uveďte počet měsíců, ve kterých jste provozoval činnost podle § 7 odst.1 písm. a), b) nebo c) zákona.
	 */
	public void setM_podnik(int m_podnik) {
		this.m_podnik = m_podnik;
	}

	/**
	 * @param d_ukoncin Datum ukončení činnosti
	 * Uveďte datum skutečného ukončení činnosti.
	 */
	public void setD_ukoncin(Date d_ukoncin) {
		this.d_ukoncin = d_ukoncin;
	}

	/**
	 * @param kc_uhzvys Úhrn částek podle § 5, § 23 a ostatní úpravy podle zákona zvyšující - uveďte úhrn částek 
	 * zvyšujících výsledek hospodaření nebo rozdíl mezi příjmy a výdaji. Podkladem jsou částky uvedené v odd. E
	 * Uveďte úhrn částek zvyšujících výsledek hospodaření nebo rozdílmezi příjmy a výdaji. Podkladem jsou částky uvedené v
	 * odd. E na str. (2). Mezi těmito částkami mohou být např.: zvyšující částky při nesplnění podmínek 
	 * (§ 34 odst. 6 zákona) pro uplatnění částek podle § 34 odst. 3 zákona v platném znění do 31. 12. 2004, částky 
	 * sraženého pojistného neodvedeného do konce měsíce následujícího po uplynutí zdaňovacího období u zaměstnavatelů 
	 * vedoucích účetnictví, částky úprav při ukončení nebo přerušení činnosti a při změně způsobu uplatňování výdajů, nebo
	 * podle § 5 odst. 10 zákona apod.
	 */
	public void setKc_uhzvys(double kc_uhzvys) {
		this.kc_uhzvys = kc_uhzvys;
	}
	
	/**
	 * @param kc_prij7 Příjmy podle § 7 zákona
	 * Do řádku vyplňte úhrn příjmů z podnikání a z jiné samostatné výdělečné činnosti (§ 7 zákona) ovlivňujících základ 
	 * daně z příjmů fyzických osob podle zákona k poslednímu dni zdaňovacího obdbobí (podle druhu Vaší podnikatelské 
	 * činnosti se nezahrnují např. i příjmy podle § 8 zákona). Vedete-li daňovou evidenci, jsou podkladem o příjmech údaje
	 * z této evidence.
	 * Neuplatňujete-li výdaje v prokázané výši, uveďte v tomto řádku úhrn zdanitelných příjmů podle § 7 zákona evidovaných
	 * v záznamech o příjmech podle § 7 odst. 8 zákona. V příjmech uvedených na tomto řádku bude i Váš podíl na příjmech 
	 * účastníka sdružení, které není právnickou osobou, ve výši stanovené smlouvou o sdružení nebo rovným dílem.
	 * Na tomto ř. 101 neuvádějte Váš podíl na příjmech osoby samostatně výdělečně činné podle § 13 zákona, který máte jako
	 * spolupracující osoba (uveďte na ř. 109), a Váš podíl společníka veřejné obchodní společnosti nebo komplementáře 
	 * komanditní společnosti na zisku (uveďte na ř. 112). Vedete-li účetnictví, vyplňte výsledek hospodaření před zdaněním
	 *  – (zisk, ztráta) do ř. 104. Částky uvádějte před úpravou podle § 5 a § 23 zákona.
	 */
	public void setKc_prij7(double kc_prij7) {
		this.kc_prij7 = kc_prij7;
	}

	/**
	 * @param d_zahcin Datum zahájení činnosti
	 * Uveďte datum skutečného zahájení činnosti.
	 */
	public void setD_zahcin(Date d_zahcin) {
		this.d_zahcin = d_zahcin;
	}
	
	/**
	 * @param pr_vyd7 Výdaje související s příjmy podle § 7 zákona
	 */
	public void setPr_vyd7(double pr_vyd7) {
		this.pr_vyd7 = pr_vyd7;
	}

	/*
	 * @param kc_zd7p Dílčí základ daně (ztráta) z příjmů podle § 7 zákona
	 * (ř. 104 + ř. 105 - ř. 106 - ř. 107 + ř. 108 + ř. 109 - ř. 110 (- ř. 111) + ř. 112)
	 * Vypočtěte částku podle pokynů. Rozdíl menší než nula je dílčí ztrátou podle § 7 zákona. Údaj přeneste na ř. 37, 
	 * 2. oddílu, základní části DAP na stranu 2.
	 *
	public void setKc_zd7p(double kc_zd7p) {
		this.kc_zd7p = kc_zd7p;
	}*/
	
	/**
	 * @param d_precin 	Datum přerušení činnosti
	 * Uveďte datum přerušení činnosti.
	 */
	public void setD_precin(Date d_precin) {
		this.d_precin = d_precin;
	}

	/**
	 * @param d_obnocin Datum obnovení činnosti
	 * Uveďte datum obnovení činnosti.
	 */
	public void setD_obnocin(Date d_obnocin) {
		this.d_obnocin = d_obnocin;
	}

	/**
	 * @param pr_prij7 	Příjmy
	 */
	public void setPr_prij7(double pr_prij7) {
		this.pr_prij7 = pr_prij7;
	}
	
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaT=EPO.createElement("VetaT");
		
		if(c_nace!=0) VetaT.setAttribute("c_nace", c_nace+"");
		if(m_podnik!=0) VetaT.setAttribute("m_podnik",m_podnik+"");
		if(uc_soust!=null) VetaT.setAttribute("uc_soust",uc_soust.getUc_soust()+"");
		if(pr_sazba!=null) VetaT.setAttribute("pr_sazba",pr_sazba.getSazba()+"");
		if(d_obnocin!=null) VetaT.setAttribute("d_obnocin",DF.format(d_obnocin));
		if(d_precin!=null) VetaT.setAttribute("d_precin", DF.format(d_precin));
		if(d_ukoncin!=null) VetaT.setAttribute("d_ukoncin",DF.format(d_ukoncin));
		if(d_zahcin!=null) VetaT.setAttribute("d_zahcin", DF.format(d_zahcin));
		if(vyd7proc) VetaT.setAttribute("vyd7proc", "A");
		if(celk_pr_prij7!=0) VetaT.setAttribute("celk_pr_prij7",celk_pr_prij7+"");
		if(celk_pr_vyd7!=0) VetaT.setAttribute("celk_pr_vyd7", celk_pr_vyd7+"");
		if(kc_cisobr!=0) VetaT.setAttribute("kc_cisobr",kc_cisobr+"");
		kc_hosp_rozd=kc_prij7-kc_vyd7;
		if(kc_hosp_rozd!=0) VetaT.setAttribute("kc_hosp_rozd",kc_hosp_rozd+"");
		if(kc_odpcelk!=0) VetaT.setAttribute("kc_odpcelk", kc_odpcelk+"");
		if(kc_odpnem!=0) VetaT.setAttribute("kc_odpnem", kc_odpnem+"");
		if(kc_pod_komp!=0) VetaT.setAttribute("kc_pod_komp", kc_pod_komp+"");
		if(kc_pod_so!=0) VetaT.setAttribute("kc_pod_so",kc_pod_so+"");
		if(kc_pod_vaso!=0) VetaT.setAttribute("kc_pod_vaso",kc_pod_vaso+"");
		if(kc_prij7!=0) VetaT.setAttribute("kc_prij7",kc_prij7+"");
		if(kc_uhsniz!=0) VetaT.setAttribute("kc_uhsniz",kc_uhsniz+"");
		if(kc_uhzvys!=0) VetaT.setAttribute("kc_uhzvys",kc_uhzvys+"");
		if(kc_vyd7!=0) VetaT.setAttribute("kc_vyd7",kc_vyd7+"");
		if(kc_vyd_so!=0) VetaT.setAttribute("kc_vyd_so",kc_vyd_so+"");
		if(kc_vyd_vaso!=0) VetaT.setAttribute("kc_vyd_vaso",kc_vyd_vaso+"");
		kc_zd7p=kc_hosp_rozd+kc_uhzvys-kc_uhsniz-kc_pod_so+kc_vyd_so+kc_pod_vaso-kc_vyd_vaso+kc_pod_komp;
		if(kc_zd7p!=0) VetaT.setAttribute("kc_zd7p",kc_zd7p+"");
		if(pr_prij7!=0) VetaT.setAttribute("pr_prij7",pr_prij7+"");
		if(pr_vyd7!=0) VetaT.setAttribute("pr_vyd7",pr_vyd7+"");
		
		return VetaT;
	}

	@Override
	public int getMaxPocet() {
		return 1;
	}

	@Override
	public String[] getDependency() {
		return null;
	}
	
	/**
	 * @return Dílčí základ daně (ztráta) z příjmů podle § 7 zákona
	 * (ř. 104 + ř. 105 - ř. 106 - ř. 107 + ř. 108 + ř. 109 - ř. 110 - ř. 111 + ř. 112)
	 * Vypočtěte částku podle pokynů. Rozdíl menší než nula je dílčí ztrátou podle § 7 zákona. Údaj přeneste na ř. 37, 
	 * 2. oddílu, základní části DAP na stranu 2.
	 */
	public double getKcZd7p(){
		return kc_zd7p=(kc_prij7-kc_vyd7)+kc_uhzvys-kc_uhsniz-kc_pod_so+kc_vyd_so+kc_pod_vaso-kc_vyd_vaso+kc_pod_komp;
	}
}
