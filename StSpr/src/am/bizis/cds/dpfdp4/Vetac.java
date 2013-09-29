/**
 * 
 */
package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Tabulka dalších činností § 7 ZDP
 * @author alex
 */
public class Vetac implements IVeta {

	private int c_nace_dal=-1;
	private double prijmy7, vydaje7;
	private PrSazba sazba_dal;
	/**
	 * 
	 */
	public Vetac() {
		//zadne povinne polozky
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element Vetac=EPO.createElement("Vetac");
		
		if(c_nace_dal!=-1) Vetac.setAttribute("c_nace_dal", c_nace_dal+"");
		if(prijmy7!=0) Vetac.setAttribute("prijmy7",prijmy7+"");
		if(vydaje7!=0) Vetac.setAttribute("vydaje7", vydaje7+"");
		if(sazba_dal!=null) Vetac.setAttribute("sazba_dal", sazba_dal.getSazba()+"");
		
		return Vetac;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 99;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}

	/**
	 * @param c_nace_dal Název dalších činností
	 * Uveďte kód nace.
	 * Pro hodnotu této položky použijte číselník Činností (okec). Z číselníku se vkládá položka c_nace.
	 * Položka obsahuje kritickou kontrolu: musí být vyplněn existující kód nace.
	 */
	public void setC_nace_dal(int c_nace_dal) {
		this.c_nace_dal = c_nace_dal;
	}

	/**
	 * @param prijmy7 Příjmy
	 */
	public void setPrijmy7(double prijmy7) {
		this.prijmy7 = prijmy7;
	}

	/**
	 * @param vydaje7 Výdaje
	 */
	public void setVydaje7(double vydaje7){
		this.vydaje7 = vydaje7;
	}

	/**
	 * @param sazba_dal Sazba výdajů % z příjmů
	 * 2011 a vyšší
	 * Pro výpočet výdajů uváděných na tomto řádku použijte níže uvedenou tabulku „B. Druh činnosti“. 
	 * Pro zdaňovací období 2011 si můžete uplatnit výdaje ve výši 80 % z příjmů ze zemědělské výroby, lesního a vodního 
	 * hospodářství (zákon č. 252/1997 Sb.) a z příjmů ze živností řemeslných, 60 % z příjmů ze živnosti s výjimkou příjmů 
	 * ze živností řemeslných a 40 % v ostatních případech uvedených v ustanovení § 7 odst. 7 písm. c) zákona (např. 
	 * autorské honoráře) a 30 % z příjmu z pronájmu majetku zařazeného v obchodním majetku.
	 */
	public void setSazba_dal(PrSazba sazba_dal) {
		this.sazba_dal = sazba_dal;
	}
	
	@Override
	public String toString(){
		return "Vetac";
	}
}
