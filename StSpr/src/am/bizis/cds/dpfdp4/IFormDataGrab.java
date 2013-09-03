package am.bizis.cds.dpfdp4;

/**
 * Rozhrani tridy, ktera obsahuje uzivatelem zadane hodnoty do DPDPF, pro vytazeni potrebnych udaju do XML
 * @author alex
 * @version 20130904
 */
public interface IFormDataGrab {
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
	 * 
	 */
	public DAPTyp getDap_typ();
	
	public boolean getPlnMoc();
}
