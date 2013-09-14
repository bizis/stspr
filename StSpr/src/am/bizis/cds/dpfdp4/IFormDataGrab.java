package am.bizis.cds.dpfdp4;

import java.util.Date;

import am.bizis.exception.DataUnsetException;
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
	
	//VETA D - povinne polozkyList
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
	 * @return (wtf?)
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
	
	public double getBonusy() throws DataUnsetException;
	
	public double getZalohyZaplacene() throws DataUnsetException;
	
	public double getZalohySrazene() throws DataUnsetException;
	
	public CountryCode getKodPopl() throws DataUnsetException;
	
	public int getCinDuch() throws DataUnsetException;
	
	public int getDeti() throws DataUnsetException;
	
	public int getDetiZTPP() throws DataUnsetException;
	
	public int getInvDuch() throws DataUnsetException;
	
	public int getManzMes() throws DataUnsetException;
	
	public Osoba getManzID() throws DataUnsetException;
	
	public boolean getPropZahr() throws DataUnsetException;
	
	public double getSlevaRP() throws DataUnsetException;
	
	public Osoba getUVpodpis() throws DataUnsetException;
	
	public UVVyhl getVyhlaska() throws DataUnsetException;
	
	public double zbyva() throws DataUnsetException;
}
