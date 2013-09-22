package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VetaJ implements IVeta {

	private String	druh_prij10;
	private Kod10 kod10;
	private KodDrPrij10 kod_dr_prij10;
	private double prijmy10, rozdil10, vydaje10;
	public VetaJ(String druh_prij10,KodDrPrij10 kod_dr_prij10,double prijmy10,double vydaje10) throws IllegalArgumentException{
		if(druh_prij10.length()>50) throw new IllegalArgumentException("maximalni povolena velikost pro druh_prij10 je 50");
		this.druh_prij10=druh_prij10;
		this.kod_dr_prij10=kod_dr_prij10;
		this.prijmy10=prijmy10;
		this.vydaje10=vydaje10;
	}

	/**
	 * @param druh_prij10 	Slovní popis druhu příjmu podle § 10 odst. 1 zákona
	 */
	public void setDruhPrij10(String druh_prij10){
		this.druh_prij10=druh_prij10;
	}
	
	/**
	 * @param kod10 Kód
	 * Přípustné symboly: P, S, Z. 
	 * Kód "P" vyplňte pouze v případě, že máte příjmy ze zemědělské výroby a uplatňujete výdaje procentem z příjmů (80%).
	 * Pokud příjmy plynou z majetku, který je ve společném jmění manželů, uveďte kód "S". 
	 * Pokud příjmy plynou ze zdrojů v zahraničí, uveďte kód "Z".
	 */
	public void setKod10(Kod10 kod10){
		this.kod10=kod10;
	}
	
	/**
	 * @param kodDrPrij10 Označení druhu příjmů podle § 10 odst. 1 zákona
	 * A – příležitostná činnost
	 * B - prodej nemovitostí
	 * C - prodej movitých věcí
	 * D - prodej cenných papírů
	 * E - příjmy z převodu podle § 10 odst. 1, písm. c) zákona
	 * F - jiné ostatní příjmy
	 */
	public void setKodDrPrij10(KodDrPrij10 kodDrPrij10){
		this.kod_dr_prij10=kodDrPrij10;
	}
	
	/**
	 * @param prijmy10 Příjmy
	 * Vyplňte ostatní příjmy podle § 10 zákona, které zahrnují příjmy ze zdrojů na území České republiky i příjmy ze 
	 * zdrojů v zahraničí, a to přepočtené na Kč způsobem popsaným výše. Podle § 10 odst. 1 zákona jsou za ostatní příjmy 
	 * považovány takové příjmy, při kterých dochází ke zvýšení majetku a nejedná se přitom o příjmy podle § 6 až § 9 
	 * zákona. Každý jednotlivý druh příjmů se uvádí v tabulce samostatně. Jestliže jste ve zdaňovacím období prodal např. 
	 * dva obytné domy a současně několik cenných papírů, jedná se o dva druhy příjmů, z nichž se každý posuzuje samostatně.
	 * Za příjem podle § 10 odst. 1 zákona se považuje i příjem odstupného za uvolnění bytu, u kterého nebyly splněny 
	 * podmínky pro osvobození od daně podle § 4 odst. 1 písm. u) zákona.
	 */
	public void setPrijmy10(double prijmy10){
		this.prijmy10=prijmy10;
	}
	
	/**
	 * @param vydaje10 	Výdaje
	 * Uveďte výdaje prokazatelně vynaložené na dosažení příjmů, a to ve skutečné výši. Pouze u zemědělské výroby je možno 
	 * uplatnit výdaje procentem z příjmů ve výši 80%.
	 */
	public void setVydaje10(double vydaje10){
		this.vydaje10=vydaje10;
	}
	
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaJ=EPO.createElement("VetaJ");
		VetaJ.setAttribute("druh_prij10", druh_prij10);
		if(kod10!=null)VetaJ.setAttribute("kod10",kod10.kod);
		VetaJ.setAttribute("kod_dr_prij10", kod_dr_prij10.kod);
		VetaJ.setAttribute("prijmy10",prijmy10+"");
		rozdil10=prijmy10-vydaje10;
		VetaJ.setAttribute("rozdil10",rozdil10+"");
		VetaJ.setAttribute("vydaje10", vydaje10+"");
		return VetaJ;
	}

	@Override
	public int getMaxPocet() {
		return 99;
	}

	@Override
	public String[] getDependency() {
		return null;
	}

}
