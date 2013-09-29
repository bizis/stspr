package am.bizis.cds.dpfdp4;

/**
 * <p>Kod rozliseni typu DAP</p>
 * 
 * <p>Elektronický formulář pro přiznání k dani z příjmů fyzických osob nelze použít pro podání za část zdaňovacího období. EPO DAP s kódem I nebo G lze podat pouze za 
 * celé ZO; tzn., že došlo ke sjednocení lhůt pro podání DAP podle § 245 DŘ.</p>
 * 
 * <p>2012<br />
 * Vyberte příslušný kód rozlišení typu DAP a uveďte datum, kdy skutečnost nastala<br />
 * G. insolvence – za předcházející zdaňovací období, pokud nebylo DAP dosud podáno a lhůta pro jeho podání neuplynula (§ 245 daňového řádu)<br />
 * I. úmrtí – do 6 měsícůpo úmrtí poplatníka podle § 239 odst. 3 daňového řádu a za předcházející zdaňovací období, pokud DAP nebylo dosud podáno a lhůta pro jeho 
 * podání neuplynula, podle § 245 daňového řádu</p>
 * @author alex
 */
public enum DAPDuvod {
	G("G","insolvence"),I("I","úmrtí");
	
	String duvodpoddapdpf,popis;
	private DAPDuvod(String kod,String popis){
		this.duvodpoddapdpf=kod;
		this.popis=popis;
	}

}
