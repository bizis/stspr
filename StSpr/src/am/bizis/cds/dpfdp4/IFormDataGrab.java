package am.bizis.cds.dpfdp4;

import java.util.Date;

import am.bizis.exception.DataUnsetException;
import am.bizis.stspr.fo.Adresa;
import am.bizis.stspr.fo.OSVC;
import am.bizis.stspr.fo.Osoba;
import taka.CountryCode;

/**
 * Rozhrani tridy, ktera obsahuje uzivatelem zadane hodnoty do DPDPF, pro vytazeni potrebnych udaju do XML
 * Pokud neni nejaka polozka vyplnena vraci se vyjimka DataUnsetException, ktera zajisti, ze se dany element v XML danovem
 * priznani nevytvori (dojde k uspore objemu prenasenych dat apod.)
 * @author alex
 * @version 20130914
 */
public interface IFormDataGrab {
	//technikalie
	/**
	 * Zobrazi uzivateli zpravu - pouzije se v pripade, ze doslo k chybe
	 * @param msg zprava pro uzivatele
	 */
	public void showMessage(String msg);
	
	//VETA D - povinne polozky
	/**
	 * Vrati c_ufo_cil podle zadaneho financniho uradu/uzemniho pracoviste
	 * @return c_ufo z ciselniku Uzemni financni organy
	 */
	public int getCufo();
	
	/**
	 * Zakonna povinnost overeni ucetni zaverky auditorem A/N
	 * @return 'A' nebo 'N'
	 */
	public char getAudit();
	
	/**
	 * Typ danoveho priznani
	 * @return B O D E
	 */
	public DAPTyp getDap_typ();
	
	/**
	 * DAP zpracoval a predklada danovy poradce na zaklade plne moci
	 * @return ano/ne
	 */
	public boolean getPlnMoc();
	
	/**
	 * Zdanovaci obdobi
	 * @return 2009,2010, nelze vlozit rok vyssi nez je rok z data umrti
	 */
	public int getRok();
	
	//Veta D - nepovinne polozky - z hlediska XML struktury
	/**
	 * @return Udaje ke dni
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public Date getDen() throws DataUnsetException;
	
	/**
	 * @return Dan celkem zaokrouhlena na cele Kc nahoru (r. 58)
	 * "forma" by mela pocitat automaticky
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public int getDanCelkem() throws DataUnsetException;
	
	/**
	 * Kod rozliseni typu DAP
	 * @return G H I
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public DAPDuvod getDuvod() throws DataUnsetException;
	/**
	 * @return Datum, kdy skutecnost nastala
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public Date getDuvodDate() throws DataUnsetException;
	
	/**
	 * Vyse celosvetovych prijmu
	 * @return Jste-li poplatníkem podle § 2 odst. 3 zákona a uplatňujete nezdanitelné části základu daně
	 * podle § 15 (do roku 2011 odst. 3 a 4) zákona nebo slevu na dani podle § 35ba odst. 1 písm.
	 * b) až e) nebo daňové zvýhodnění podle § 35c zákona, uveďte úhrn všech příjmů ze zdrojů na 
	 * území České republiky a ze zdrojů v zahraničí v celých Kč. Cizí měnu přepočtěte podle § 38
	 * odst. 1 zákona.
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public double getCelosvet() throws DataUnsetException;
	
	/**
	 * Danove zvyhodneni na vyzivovane dite
	 * @return Uveďte výši daňového zvýhodnění podle § 35c zákona. Pokud byly splněny podmínky po celé 
	 * zdaňovací období 2012, máte nárok na daňové zvýhodnění ve výši 13 404 Kčna jedno dítě. 
	 * Jedná-li se o dítě, které je držitelem průkazu ZTP/P, zvyšuje se na ně částka daňového 
	 * zvýhodnění na dvojnásobek. Vyživuje-li dítěv jedné domácnosti více poplatníků, může 
	 * daňové zvýhodnění uplatnit ve zdaňovacím období nebo v tomtéž kalendářním měsíci 
	 * zdaňovacího období jen jeden z nich.
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public double getZvyhodDite() throws DataUnsetException;
	
	/**
	 * Daňová ztráta
	 * @return Daňová ztráta - zaokrouhlená na celé Kč nahoru bez znaménka mínus
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public double getDztrata() throws DataUnsetException;
	
	/**
	 * Zaplacená daňová povinnost (záloha) podle § 38gb odst. 2 zákona
	 * Uveďte, podáváte-li DAP, výši daně tvořící zálohu na daň daňové povinnosti podle podmínek
	 * uvedených v § 38gb zákona.
	 * @return zaplacena zaloha na dani z prijmu
	 * @throws DataUnsetException pokud neni vyplneno
	 */
	public double getZaloha() throws DataUnsetException;
	
	/**
	 * b písm. b) zákona (na manželku/manžela, která/který je držitelem ZTP/P)
	 * @return (nejaka konstanta ?)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getManZTP() throws DataUnsetException;
	
	/**
	 * a písm. b) zákona (na manželku/manžela)
	 * @return (nejaka konstanta ?)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getManz() throws DataUnsetException;
	
	/**
	 * písm. c) zákona (na poživatele invalidního důchodu pro invaliditu prvního nebo druhého stupně
	 * @return (nejaka konstanta ?)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getInvalid() throws DataUnsetException;
	
	/**
	 * písm. d) zákona (na poživatele invalidního důchodu pro invaliditu třetího stupně
	 * @return (nejaka konstanta ?)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getInvalid3() throws DataUnsetException;
	
	/**
	 * písm. e) zákona (na držitele průkazu ZTP/P)
	 * @return (nejaka konstanta ?)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getZTP() throws DataUnsetException;
	
	/**
	 * Zaplacená daň stanovená paušální částkou podle § 7a zákona
	 *
	 * @return Uveďte částku daně stanovené paušální částkou podle § 7a zákona, kterou započtete na 
	 * výslednou daňovou povinnost, podáváte-li DAP podle § 7a odst. 5 zákona tj. v případě, že 
	 * jste dosáhl (dosáhla) jiných příjmů než předpokládaných.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getPausal() throws DataUnsetException;
	
	/**
	 * @return Poslední známá daň
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getPosledni() throws DataUnsetException;
	
	/**
	 * @return Sražená daň podle § 36 odst. 6 zákona (státní dluhopisy)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getDluhopisy() throws DataUnsetException;
	
	/**
	 * Sražená daň dle § 38f odst. 12 zákona
	 * kod 89 formulare danoveho priznani
	 * @return (wtf?)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getSraz3810() throws DataUnsetException;
	
	/**
	 * Zajištěná daň plátcem podle § 38e zákona
	 * @return Uveďte částku, kterou Vám jako poplatníkovi podle § 2 odst. 3 zákona, plátce daně podle
	 * § 38e zákona srazil na zajištění daně. Jste-li společník veřejné obchodní společnosti 
	 * nebo komplementář komanditní společnosti, bude částka uvedená na tomto řádku zahrnovat 
	 * zajištění daně sražené Vám veřejnou obchodní společností nebo komanditní společností 
	 * podle § 38e odst. 3 písm. a) zákona vztahující se ke zdaňovacímu období 2012 nebo k části
	 * zdaňovacího období 2012, za něž je podáváno DAP. Obdobně se postupuje ve zdaňovacích 
	 * obdobích 2009, 2010 a 2011.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getZajistena() throws DataUnsetException;
	
	/**
	 * Sražená daň podle § 36 odst. 7 zákona
	 * kod 87 formulare danoveho priznani
	 * @return (wut?)
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getSraz387() throws DataUnsetException;
	
	/**
	 * f) zákona (studium)
	 * @return nejaka konstanta
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getStudium() throws DataUnsetException;
	
	/**
	 * Pocet mesicu studentem
	 * EPOFactory hodi vyjimku, pokud getStudium nehodi vyjimku a getStudMes ano!!
	 * @return cislo 0 az 12 vc.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getStudMes() throws DataUnsetException;
	
	/**
	 * Úhrn vyplacených měsíčních daňových bonusů podle § 35d zákona 
	 * (včetně případného doplatku na daňovém bonusu)
	 * @return Uveďte úhrn měsíčních daňových bonusů, které Vám jako zaměstnanci byly zaměstnavatelem 
	 * vyplaceny za zdaňovací období 2012, resp. 2011, 2010. Údaje zjistíte z „Potvrzení“ 
	 * vystaveného jednotlivými zaměstnavateli. Pokud podáváte daňové přiznání a již Vám bylo 
	 * provedeno roční zúčtování u zaměstnavatele, pak se v Potvrzení vzor č. 20 (2011: vzor č. 
	 * 19; 2010: vzor č. 18) jedná o součet řádků 13 a doplatku na daňovém bonusu z řádku 17.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getBonusy() throws DataUnsetException;
	
	/**
	 * Na zbývajících zálohách zaplaceno poplatníkem celkem
	 * @return Uveďte souhrn záloh, které jste zaplatil (zaplatila), v průběhu zdaňovacího období 2012
	 * nebo části zdaňovacího období 2012, za něž je podáváno DAP, včetně přeplatku použitého
	 * jako záloha na daň podle § 154 a § 155 daňového řádu. Obdobně se postupuje ve zdaňovacích
	 * obdobích 2009, 2010 a 2011.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getZalohyZaplacene() throws DataUnsetException;
	
	/**
	 * Úhrn sražených záloh na daň z příjmů fyzických osob ze závislé činnosti
	 * a z funkčních požitků (po slevách na dani)
	 * @return Uveďte úhrn sražených záloh na daň z příjmů ze závislé činnosti a z funkčních požitků 
	 * (po slevách na dani), které Vám byly sraženy všemi zaměstnavateli. Zálohy na daň z příjmů
	 * ze závislé činnosti a z funkčních požitků uveďte v souladu s § 5 odst. 4 zákona (ve vzoru
	 * Potvrzení č.20 (2011: č.19; 2010: č.18; 2009: č.17) se jedná o údaj uvedený na řádku 12). 
	 * V případě, že Vám bylo provedeno roční zúčtování, uveďte částku sražených záloh sníženou 
	 * o vrácený přeplatek z ročního zúčtování.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getZalohySrazene() throws DataUnsetException;
	
	/**
	 * Kód státu - vyplní jen daňový nerezident
	 * Položka obsahuje kritické kontroly: hodnota musí obsahovat kód existujícího státu a nesmí
	 * obsahovat kód CZ - Česko
	 * Uzij vycet taka.CountryCode
	 * @return Jste-li poplatníkem podle § 2 odst. 3 zákona, tj. daňový nerezident v České republice, 
	 * který má daňovou povinnost z příjmů ze zdrojů na území České republiky, uveďte písmenný 
	 * kód státu, ve kterém jste rezidentem.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public CountryCode getKodPopl() throws DataUnsetException;
	
	/**
	 * Kod 66 (na formulari danoveho priznani)
	 * @return pocet mesicu
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getCinDuch() throws DataUnsetException;
	
	/**
	 * Asi pocet mesicu deti (nejaka sleva)
	 * @return celkem pocet mesicu
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getDeti() throws DataUnsetException;
	
	/**
	 * Asi pocet mesicu deti se ZTP/P (nejaka sleva)
	 * @return celkem pocet mesicu
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getDetiZTPP() throws DataUnsetException;
	
	/**
	 * Kod 67 (na formulari danoveho priznani)
	 * @return Pocet mesicu
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getInvDuch() throws DataUnsetException;
	
	/**
	 * kod 65 na formulari danoveho priznani
	 * @return b) pocet mesicu
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public int getManzMes() throws DataUnsetException;
	
	/**
	 * Z tohoto se potom vytvori manz_jmeno, manz_prijmeni, manz_r_cislo, manz_titul
	 * Udaje o manzelovi/manzelce
	 * @return Objekt Osoba pro manzela/manzelku
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public Osoba getManzID() throws DataUnsetException;
	
	/**
	 * Spojeni se zahranicnimi osobami
	 * @return A|N
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public boolean getPropZahr() throws DataUnsetException;
	
	/**
	 * Sleva podle § 35a nebo 35b zákona
	 * Kod 63 na formulari danoveho priznani
	 * @return nejaka konstanta?
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double getSlevaRP() throws DataUnsetException;
	
	/**
	 * Osoba, jejíž podpisový záznam byl připojen k účetní závěrce, která byla podkladem pro zpracování této přílohy
	 * @return Objekt Osoba
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public Osoba getUVpodpis() throws DataUnsetException;
	
	/**
	 * 	Vyhláška č.
	 * Vyplňte číslo vyhlášky (první část), podle které byly účetní výkazy a následně vybrané údaje sestaveny. Uveďte následující označení:
	 * 500 pro
	 * Vyhlášku č. 500/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, které jsou podnikateli účtujícími v soustavě podvojného účetnictví, v platném znění.
	 * 
	 * 501 pro
	 * Vyhlášku č.501/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, které jsou bankami a jinými finančními institucemi, v platném znění.
	 * 
	 * 502 pro
	 * Vyhlášku č.502/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, které jsou pojišťovnami, v platném znění.
	 * 
	 * 503 pro
	 * Vyhlášku č.503/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů, pro zdravotní pojišťovny, v platném znění.
	 * 
	 * 504 pro
	 * Vyhlášku č. 504/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, u kterých hlavním předmětem činnosti není podnikání, pokud účtují v soustavě podvojného účetnictví, v platném znění.
	 * 
	 * 507 pro
	 * Vyhlášku č. 507/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky účtující v soustavě jednoduchého účetnictví, ve znění účinném do 31. prosince 2003, podle níž mohou doposud postupovat účetní jednotky vymezené § 38a zákona č. 563/1991 Sb., o účetnictví, v platném znění.
	 * 
	 * 410 pro
	 * Vyhlášku č. 410/2009 Sb., pro vybrané účetní jednotky, v platném znění, nebo jste účetní jednotkou podle § 38a zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů, která využívá možnost vést účetnictví podle zákona č. 563/1991 Sb., o účetnictví, ve znění zákona č. 117/1994 Sb., zákona č. 227/1997 Sb., zákona č. 492/2000 Sb., zákona č. 353/2001 Sb. a zákona č. 437/2003 Sb., a na kterou se vztahují ustanovení zákona č. 563/1991 Sb., o účetnictví, a jeho prováděcích právních předpisů, která upravují účtování v soustavě jednoduchého účetnictví, ve znění účinném k 31. prosinci 2003.
	 * 
	 * Položka obsahuje kritické kontroly: nesmí být vyplněn současně druhový a účelový výkaz zisků a ztrát, pokud je zadána hodnota musí odpovídat masce položky, může být použito pouze číslo vyhlášky korespondující s danou písemností, může být použito pouze číslo vyhlášky korespondující s daným typem subjektu, pro zadané číslo vyhlášky musí být použity odpovídající výkazy, pro vyhlášky kromě 500 musí být vyplněny všechny výkazy, číslo vyhlášky musí být vyplněno je-li zadán alespoň 1 výkaz.
	 * @return objekt vyctu UVVyhl
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public UVVyhl getVyhlaska() throws DataUnsetException;
	
	/**
	 * Zbývá doplatit (ř. 74 - ř. 77 - ř. 84 - ř. 85 - ř. 86 - ř. 87 - ř. 87a - ř. 88 - ř. 89 - ř. 90): (+) zbývá doplatit, (-) zaplaceno více.
	 * @return Kladná částka znamená, že zbývá na dani doplatit vypočtenou částku. Záporná částka znamená, že bylo za zdaňovací období 2012 zaplaceno více. O přeplatek je možné požádat příslušného správce daně formou žádosti, která je součástí DAP. Obdobně se postupuje ve zdaňovacích obdobích 2011, 2010 a 2009.
	 * @throws DataUnsetException pokud se neuplatnuje
	 */
	public double zbyva() throws DataUnsetException;
	
	//Veta P
	/**
	 * Poplatnik dane z prijmu FO
	 * @return osoba osobni udaje poplatnika
	 */
	public OSVC getPoplatnik();
	
	/**
	 * Územní pracoviště v, ve, pro)
	 * Uzivatel vybere jedno ze seznamu
	 * Seznam (stringy) se ziska zavolanim am.bizis.cds.ciselnik.UFO.getNazuUFO();
	 * Z vybraneho stringu se ziska int zavolanim am.bizis.cds.ciselnik.UFO.getCUFO();
	 * pokud getCUFO() vrati 0 nejspise doslo k chybe ...
	 * @return sídlo územního pracoviště, na němž je nebo bude umístěn spis daňového subjektu (§ 13 zákona o Finanční správě České republiky)
	 */
	public int getCpracufo();
	
	/**
	 * Pobyt k poslednimu dni kalendarniho roku, za ktery se dan vymeruje (pokud ruzne od bydliste!)
	 * @return objekt Adresa pro dane misto pobytu
	 * @throws DataUnsetException pokud neni nastaveno (k poslednimu dni roku bydlel na soucasne adrese bydliste)
	 */
	public Adresa getKrok() throws DataUnsetException;
	
	/**
	 * Adresa mista pobytu na uzemi CR, kde se poplatnik obvykle ve zdanovacim obdobi zdrzoval - pokud neni bydliste na uzemi CR
	 * @return objekt Adresa pro dane misto pobytu, kod statu nesmi byt CZ
	 * @throws DataUnsetException pokud neni nastaveno (poplatnik ma bydliste v CR)
	 */
	public Adresa getZdrz() throws DataUnsetException;
	
	/**
	 * @return evidencni cislo danoveho poradce
	 * @throws DataUnsetException pokud neni nastaveno (nema danoveho poradce)
	 */
	public String getEVcislo() throws DataUnsetException;
}
