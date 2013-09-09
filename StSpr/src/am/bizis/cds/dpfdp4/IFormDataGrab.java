package am.bizis.cds.dpfdp4;

import java.util.Date;

import am.bizis.stspr.exception.DataUnsetException;
import am.bizis.stspr.fo.Osoba;
import taka.CountryCode;

/**
 * Rozhrani tridy, ktera obsahuje uzivatelem zadane hodnoty do DPDPF, pro vytazeni potrebnych udaju do XML
 * @author alex
 * @version 20130909
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
	
	public double getCelosvet() throws DataUnsetException;
	
	public double getZvyhodDite() throws DataUnsetException;
	
	public double getDztrata() throws DataUnsetException;
	
	public double getZaloha() throws DataUnsetException;
	
	public double getManZTP() throws DataUnsetException;
	
	public int getManz() throws DataUnsetException;
	
	public int getInvalid() throws DataUnsetException;
	
	public int getInvalid3() throws DataUnsetException;
	
	public int getZTP() throws DataUnsetException;
	
	public int getPausal() throws DataUnsetException;
	
	public int getPosledni() throws DataUnsetException;
	
	public double getDluhopisy() throws DataUnsetException;
	
	public double getSraz3810() throws DataUnsetException;
	
	public double getZajistena() throws DataUnsetException;
	
	public double getSraz387() throws DataUnsetException;
	
	public double getStudium() throws DataUnsetException;
	
	public double getBonusy() throws DataUnsetException;
	
	public double getZalohyZaplacene() throws DataUnsetException;
	
	public double getZalohySrazene() throws DataUnsetException;
	
	public CountryCode getKodPopl() throws DataUnsetException;
	
	public int getCinDuch() throws DataUnsetException;
	
	public int getDeti() throws DataUnsetException;
	
	public int getDetiZTPP() throws DataUnsetException;
	
	public int getInvDuch() throws DataUnsetException;
	
	public int getManzMes() throws DataUnsetException;
	
	public int getStudMes() throws DataUnsetException;
	
	public Osoba getManzID() throws DataUnsetException;
	
	public boolean getPropZahr() throws DataUnsetException;
	
	public double getSlevaRP() throws DataUnsetException;
	
	public Osoba getUVpodpis() throws DataUnsetException;
	
	public UVVyhl getVyhlaska() throws DataUnsetException;
	
	public double zbyva() throws DataUnsetException;
}
