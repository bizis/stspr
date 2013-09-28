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
 * @author alex
 * Tabulka pro poplatníky vedoucí daňovou evidenci
 */
public class VetaU implements IVeta {

	private double kc_dpfmz02, kc_dpfmz03, kc_dpfmz04, kc_dpfmz05a, kc_dpfmz06, kc_dpfmz08, kc_dpfmz10, kc_dpfmz11, kc_dpfmz18, kc_z_dpfmz02, kc_z_dpfmz03, kc_z_dpfmz04, kc_z_dpfmz05a, kc_z_dpfmz06, kc_z_dpfmz08, kc_z_dpfmz10, kc_z_dpfmz11;
	/**
	 * 
	 */
	public VetaU() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaU=EPO.createElement("VetaU");
		if(kc_dpfmz02!=0)VetaU.setAttribute("kc_dpfmz02", kc_dpfmz02+"");
		if(kc_dpfmz03!=0)VetaU.setAttribute("kc_dpfmz03",kc_dpfmz03+"");
		if(kc_dpfmz04!=0)VetaU.setAttribute("kc_dpfmz04",kc_dpfmz04+"");
		if(kc_dpfmz05a!=0)VetaU.setAttribute("kc_dpfmz05a", kc_dpfmz05a+"");
		if(kc_dpfmz06!=0)VetaU.setAttribute("kc_dpfmz06", kc_dpfmz06+"");
		if(kc_dpfmz08!=0)VetaU.setAttribute("kc_dpfmz08",kc_dpfmz08+"");
		if(kc_dpfmz10!=0)VetaU.setAttribute("kc_dpfmz10",kc_dpfmz10+"");
		if(kc_dpfmz11!=0)VetaU.setAttribute("kc_dpfmz11",kc_dpfmz11+"");
		if(kc_dpfmz18!=0)VetaU.setAttribute("kc_dpfmz18",kc_dpfmz18+"");
		if(kc_z_dpfmz02!=0)VetaU.setAttribute("kc_z_dpfmz02",kc_z_dpfmz02+"");
		if(kc_z_dpfmz03!=0)VetaU.setAttribute("kc_z_dpfmz03",kc_z_dpfmz03+"");
		if(kc_z_dpfmz04!=0)VetaU.setAttribute("kc_z_dpfmz04",kc_z_dpfmz04+""); 
		if(kc_z_dpfmz05a!=0)VetaU.setAttribute("kc_z_dpfmz05a",kc_z_dpfmz05a+""); 
		if(kc_z_dpfmz06!=0)VetaU.setAttribute("kc_z_dpfmz06",kc_z_dpfmz06+""); 
		if(kc_z_dpfmz08!=0)VetaU.setAttribute("kc_z_dpfmz08",kc_z_dpfmz08+""); 
		if(kc_z_dpfmz10!=0)VetaU.setAttribute("kc_z_dpfmz10",kc_z_dpfmz10+""); 
		if(kc_z_dpfmz11!=0)VetaU.setAttribute("kc_z_dpfmz11",kc_z_dpfmz11+"");
		return VetaU;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}

	/**
	 * @param kc_z_dpfmz05a Peněžní prostředky v hotovosti na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí podle § 7b zákona.
	 */
	public void setKc_z_dpfmz05a(double kc_z_dpfmz05a) {
		this.kc_z_dpfmz05a = kc_z_dpfmz05a;
	}

	/**
	 * @param kc_dpfmz04 Pohledávky včetně poskytnutých úvěrů a půjček na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona.
	 */
	public void setKc_dpfmz04(double kc_dpfmz04) {
		this.kc_dpfmz04 = kc_dpfmz04;
	}

	/**
	 * @param kc_z_dpfmz04 	Pohledávky včetně poskytnutých úvěrů a půjček na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona.
	 */
	public void setKc_z_dpfmz04(double kc_z_dpfmz04) {
		this.kc_z_dpfmz04 = kc_z_dpfmz04;
	}

	/**
	 * @param kc_dpfmz10 Závazky včetně přijatých úvěrů a půjček na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona.
	 */
	public void setKc_dpfmz10(double kc_dpfmz10) {
		this.kc_dpfmz10 = kc_dpfmz10;
	}

	/**
	 * @param kc_dpfmz08 Ostatní majetek na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona.
	 */
	public void setKc_dpfmz08(double kc_dpfmz08) {
		this.kc_dpfmz08 = kc_dpfmz08;
	}

	/**
	 * @param kc_dpfmz03 Zásoby na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona.
	 */
	public void setKc_dpfmz03(double kc_dpfmz03) {
		this.kc_dpfmz03 = kc_dpfmz03;
	}

	/**
	 * @param kc_dpfmz18 Mzdy
	 * Údaje o mzdách se přebírají ze mzdové agendy (mzdové list, rekapitulace mezd apod.). Uveďte celkový objem 
	 * zúčtovaných mezd za zdaňovací období.
	 */
	public void setKc_dpfmz18(double kc_dpfmz18) {
		this.kc_dpfmz18 = kc_dpfmz18;
	}

	/**
	 * @param kc_z_dpfmz02 	Hmotný majetek na konci ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí podle § 7b zákona. Na konci zdaňovacího období se uvádí zůstatková cena hmotného 
	 * majetku podle § 29 zákona definovaného podle § 26 odst. 2 zákona.
	 */
	public void setKc_z_dpfmz02(double kc_z_dpfmz02) {
		this.kc_z_dpfmz02 = kc_z_dpfmz02;
	}

	/**
	 * @param kc_z_dpfmz08 	Ostatní majetek na konci ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona. Na konci zdaňovacího období uveďte stav ostatního majetku podle 
	 * § 7b zákona. 
	 */
	public void setKc_z_dpfmz08(double kc_z_dpfmz08) {
		this.kc_z_dpfmz08 = kc_z_dpfmz08;
	}
	
	/**
	 * @param kc_z_dpfmz06 Peněžní prostředky na bankovních účtech na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí podle § 7b zákona.
	 */
	public void setKc_z_dpfmz06(double kc_z_dpfmz06) {
		this.kc_z_dpfmz06 = kc_z_dpfmz06;
	}
	
	/**
	 * @param kc_dpfmz05a Rezervy na poč. ZO
	 * Údaje o rezervách definovaných v zákoně č. 593/1992 Sb., o rezervách pro zjištění základu daně z příjmů, ve znění 
	 * pozdějších předpisů, se přebírají z karet zákonných rezerv.
	 */
	public void setKc_dpfmz05a(double kc_dpfmz05a) {
		this.kc_dpfmz05a = kc_dpfmz05a;
	}
	
	/**
	 * @param kc_dpfmz11 Rezervy na poč. ZO
	 * Údaje o rezervách definovaných v zákoně č. 593/1992 Sb., o rezervách pro zjištění základu daně z příjmů, ve znění 
	 * pozdějších předpisů, se přebírají z karet zákonných rezerv.
	 */
	public void setKc_dpfmz11(double kc_dpfmz11) {
		this.kc_dpfmz11 = kc_dpfmz11;
	}

	/**
	 * @param kc_z_dpfmz11 Rezervy na konci ZO
	 * Údaje o rezervách definovaných v zákoně č. 593/1992 Sb., o rezervách pro zjištění základu daně z příjmů, ve znění 
	 * pozdějších předpisů, se přebírají z karet zákonných rezerv.
	 */
	public void setKc_z_dpfmz11(double kc_z_dpfmz11) {
		this.kc_z_dpfmz11 = kc_z_dpfmz11;
	}

	/**
	 * @param kc_z_dpfmz10 Závazky včetně přijatých úvěrů a půjček na konci ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona. Na konci zdaňovacího období se uvádí zjištěný skutečný stav 
	 * závazků k poslednímu dni zdaňovacího období.
	 */
	public void setKc_z_dpfmz10(double kc_z_dpfmz10) {
		this.kc_z_dpfmz10 = kc_z_dpfmz10;
	}
	
	/**
	 * @param kc_z_dpfmz03 	Zásoby na konci ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona. Na konci zdaňovacího období se uvádí zjištěný skutečný stav zásob 
	 * k poslednímu dni zdaňovacího období.
	 */
	public void setKc_z_dpfmz03(double kc_z_dpfmz03) {
		this.kc_z_dpfmz03 = kc_z_dpfmz03;
	}

	/**
	 * @param kc_dpfmz06 Ostatní majetek na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí dle § 7b zákona.
	 */
	public void setKc_dpfmz06(double kc_dpfmz06) {
		this.kc_dpfmz06 = kc_dpfmz06;
	}

	/**
	 * @param kc_dpfmz02 Hmotný majetek na poč. ZO
	 * Údaje na ř. 1 až ř. 7 se uvádějí podle § 7b zákona.
	 */
	public void setKc_dpfmz02(double kc_dpfmz02) {
		this.kc_dpfmz02 = kc_dpfmz02;
	}

}
