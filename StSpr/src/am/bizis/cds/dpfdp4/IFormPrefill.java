package am.bizis.cds.dpfdp4;

/**
 * Rozhrani popisujici prednaplneni graficke formy DPDPF
 * @author alex
 */
public interface IFormPrefill {
	/**
	 * nastaveni financnimu uradu pro/specializovanemu fin. uradu
	 * a asi i uzemnimu pracovisti v,ve,pro 
	 * 
	 * pro GUI: am.bizis.cds.ciselnik.UFO.getNazuUFO() ti vrati pole, ze ktereho user neco vybere
	 * akorat se musi vymyslet, jak se chyti to cislo - asi nejake upravy metod tridy UFO
	 * @param FO
	 */
	public void setUFO(String FO);
	
	/**
	 * nastaveni DIC (mozno vymenit int za String - spojeno se zmenou rozhrani)
	 * DIC je soucast objektu Osoby (am.bizis.stspr.fo.OSVC)
	 * @param DIC
	 */
	public void setDIC(int DIC);
	
	/**
	 * nastaveni rodneho cisla
	 * vytvori objekt am.bizis.stspr.fo.RodneCislo
	 * odchyta vyjimky, pri kterych dostane uzivatel rozumnou hlasku a program nespadne -> kontrola spravnosti
	 */
	public void setRC(String rc);
	
	/**
	 * Nastaveni DAP: radne, opravne, dodatecne
	 */
	public void setDAPTyp(DAPTyp d);
	
	/**
	 * Duvody pro podani dodatecneho DAP zjisteny dne - GUI kontroluje, ze je vyplneno, pokud jde o dodatecne
	 * @param d
	 */
	public void setDuvodDodatecne(java.util.Date d);
	
	//TODO: kod rozliseni typu DAP
	
	public void setDatum(java.util.Date d);
	
	public void setPoradce(boolean poradce);
	public void setAuditor(boolean auditor);
}
