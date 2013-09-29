package am.bizis.cds.dpfdp4;

/**
 * <p>Kód<br />
 * Přípustné symboly: P, S, Z.<br /> 
 * Kód "P" vyplňte pouze v případě, že máte příjmy ze zemědělské výroby a uplatňujete výdaje procentem z příjmů (80%).<br /> 
 * Pokud příjmy plynou z majetku, který je ve společném jmění manželů, uveďte kód "S". <br />
 * Pokud příjmy plynou ze zdrojů v zahraničí, uveďte kód "Z".</p>
 * @author alex
 */
public enum Kod10 {
	P("P"), S("S"), Z("Z");
	
	String kod;
	Kod10(String kod){
		this.kod=kod;
	}
}
