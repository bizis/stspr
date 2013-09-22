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
 * Priloha c. 2
 * @author alex
 */
public class VetaV implements IVeta {

	private final double kc_prij10, kc_snizukon9, kc_zvysukon9,kc_prij9, kc_vyd9;
	private double kc_rozdil9,kc_vyd10,kc_rezerv_k, kc_rezerv_z, kc_zd10p, kc_zd9p, uhrn_prijmy10, uhrn_rozdil10, uhrn_vydaje10;
	private boolean vyd9proc=false, spol_jm_manz=false;
	
	/**
	 * @param kc_prij10 Příjmy podle § 10 zákona
	 * Uveďte součet částek z tabulky ze sloupce 2 podle jednotlivých druhů příjmů.
	 * @param kc_snizukon9 Úhrn částek podle § 5, § 23 zákona a ostatní úpravy podle zákona snižující rozdíl mezi příjmy a 
	 * výdaji nebo výsledkem hospodaření před zdaněním - (zisk, ztráta)
	 * Uveďte úhrn částek snižujících rozdíl mezi příjmy a výdaji nebo výsledek hospodaření před zdaněním.
	 * @param kc_zvysukon9 Úhrn částek podle § 5, § 23 zákona a ostatní úpravy podle zákona zvyšující rozdíl mezi příjmy a 
	 * výdaji nebo výsledek hospodaření před zdaněním - (zisk, ztráta)
	 * Uveďte úhrn částek zvyšujících rozdíl mezi příjmy a výdaji nebo výsledek hospodaření před zdaněním.
	 * @param kc_prij9 Příjmy podle § 9 zákona
	 * Uveďte na ř. 201 příjmy z pronájmu evidované podle § 9 odst. 6 zákona v záznamech o příjmech a výdajích případně v 
	 * účetnictví.
	 * @param kc_vyd9 Výdaje podle § 9 zákona
	 * Uveďte na ř. 202 výdaje z pronájmu evidované podle § 9 odst. 6 zákona v záznamech o příjmech a výdajích případně v 
	 * účetnictví.
	 * V případě, že se jedná o příjmy dosažené dvěma a více poplatníky z titulu spoluvlastnictví k věci, potom společné 
	 * výdaje vynaložené na jejich dosažení, zajištění a udržení se rozdělují mezi poplatníky podle jejich 
	 * spoluvlastnických podílů nebo podle poměru dohodnutého ve smlouvě. Pokud příjmy z pronájmu plynou manželům ze 
	 * společného jmění manželů, zdaňují se jen u jednoho z nich a ten je uvede ve svém DAP. Údaje se uvádějí před úpravou 
	 * o položky podle § 5, § 23 zákona a ostatní úpravy podle zákona.
	 */
	public VetaV(double kc_prij10,double kc_snizukon9,double kc_zvysukon9,double kc_prij9,double kc_vyd9) {
		this.kc_prij10=kc_prij10;
		this.kc_snizukon9=kc_snizukon9;
		this.kc_zvysukon9=kc_zvysukon9;
		this.kc_prij9=kc_prij9;
		this.kc_vyd9=kc_vyd9;
	}
	
	

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaV=EPO.createElement("VetaV");
		
		VetaV.setAttribute("kc_prij10",kc_prij10+"");
		VetaV.setAttribute("kc_prij9",kc_prij9+""); 
		if(kc_rezerv_k!=0) VetaV.setAttribute("kc_rezerv_k",kc_rezerv_k+"");
		if(kc_rezerv_z!=0) VetaV.setAttribute("kc_rezerv_z",kc_rezerv_z+"");
		kc_rozdil9=kc_prij9-kc_vyd9;
		VetaV.setAttribute("kc_rozdil9",kc_rozdil9+"");
		VetaV.setAttribute("kc_snizukon9",kc_snizukon9+"");
		if (kc_vyd10>kc_prij10) kc_vyd10=kc_prij10;
		VetaV.setAttribute("kc_vyd10",kc_vyd10+"");
		VetaV.setAttribute("kc_vyd9",kc_vyd9+"");
		kc_zd10p=kc_prij10-kc_vyd10;//soucet kladnych rozdilu prijmu a vydaju
		VetaV.setAttribute("kc_zd10p",kc_zd10p+"");
		kc_zd9p=kc_rozdil9+kc_zvysukon9-kc_snizukon9;
		VetaV.setAttribute("kc_zd9p",kc_zd9p+"");
		VetaV.setAttribute("kc_zvysukon9",kc_zvysukon9+"");
		if(uhrn_prijmy10!=0) VetaV.setAttribute("uhrn_prijmy10",uhrn_prijmy10+"");
		if(uhrn_rozdil10!=0) VetaV.setAttribute("uhrn_rozdil10",uhrn_rozdil10+"");
		if(uhrn_vydaje10!=0) VetaV.setAttribute("uhrn_vydaje10",uhrn_vydaje10+"");
		if(vyd9proc) VetaV.setAttribute("vyd9proc", "A");
		if(spol_jm_manz) VetaV.setAttribute("spol_jm_manz","A");
		return VetaV;
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
		return new String[]{"VetaJ"};
	}

	/**
	 * @param spol_jm_manz Dosáhl jsem příjmů ze společného jmění manželů
	 * Máte-li příjmy z pronájmu, které jste dosáhl ze společného jmění manželů (bez podílového spoluvlastnictví manželů),
	 * označte křížkem v předtištěném rámečku. V opačném případě nevyplňujte.
	 */
	public void setSpol_jm_manz(boolean spol_jm_manz) {
		this.spol_jm_manz = spol_jm_manz;
	}
	
	/**
	 * @param vyd9proc 	Uplatňuji výdaje procentem z příjmů (30 %)
	 * Uplatňujete-li výdaje procentem z příjmů z pronájmu podle § 9 odst. 4 zákona (30 %), uveďte A, jinak uveďte N.
	 * Položka obsahuje kritickou kontrolu: hodnota položky může být pouze A nebo N.
	 */
	public void setVyd9proc(boolean vyd9proc) {
		this.vyd9proc = vyd9proc;
	}

	/**
	 * @param uhrn_vydaje10 Úhrn jednotlivých výdajů dle § 10 zákona
	 */
	public void setUhrn_vydaje10(double uhrn_vydaje10) {
		this.uhrn_vydaje10 = uhrn_vydaje10;
	}

	/**
	 * @param uhrn_rozdil10 Úhrn kladných rozdílů jednotlivých druhů příjmů
	 */
	public void setUhrn_rozdil10(double uhrn_rozdil10) {
		this.uhrn_rozdil10 = uhrn_rozdil10;
	}

	/**
	 * @return Dílčí základ daně, daňová ztráta z pronájmu podle § 9 zákona (ř. 203 + ř. 204 - ř. 205)
	 * Vypočtěte částku podle pokynů na řádku. Rozdíl menší než nula je dílčí ztrátou podle § 9 zákona. 
	 * Údaj přeneste na ř. 39, 2. oddílu, základní části DAP na str. 2.
	 */
	public double getKc_zd9p() {
		return this.kc_zd9p;
	}

	/**
	 * @return Dílčí základ daně připadající na ostatní příjmy podle § 10 zákona (ř. 207 - ř. 208)
	 * Proveďte výpočet podle údajů v tiskopisu, uvedená částka by se měla rovnat úhrnu kladných rozdílů jednotlivých 
	 * příjmů v tabulce ve sloupci 4. Údaj přeneste do ř. 40, 2. oddílu, základní části DAP na str. 2
	 */
	public double getKc_zd10p() {
		return this.kc_zd10p;
	}

	/**
	 * @param uhrn_prijmy10 Úhrn jednotlivých příjmů dle § 10 zákona
	 */
	public void setUhrn_prijmy10(double uhrn_prijmy10) {
		this.uhrn_prijmy10 = uhrn_prijmy10;
	}
	
	/**
	 * @param kc_rezerv_k Rezervy na konci zdaňovacího období
	 */
	public void setKc_rezerv_k(double kc_rezerv_k) {
		this.kc_rezerv_k = kc_rezerv_k;
	}
	
	/*
	 * Rozdíl mezi příjmy a výdaji (ř. 201 - ř. 202) nebo výsledek hospodaření před zdaněním - (zisk, ztráta)
	 * Uveďte výpočet podle údajů v tiskopisu. Údaje jsou uváděny před úpravou podle § 5, § 23 zákona a ostatní úpravy 
	 * podle zákona. V případě, že výdaje přesahují příjmy nebo výsledek hospodaření před zdaněním je ztráta, částku 
	 * označte znaménkem mínus.
	 */
	/*public void setKc_rozdil9(int kc_rozdil9) {
		this.kc_rozdil9 = kc_rozdil9;
	}*/
	
	/**
	 * @param kc_rezerv_z Rezervy na začátku zdaňovacího období
	 */
	public void setKc_rezerv_z(double kc_rezerv_z) {
		this.kc_rezerv_z = kc_rezerv_z;
	}
	
	/**
	 * @param kc_vyd10 	Výdaje podle § 10 zákona (maximálně do výše příjmů)
	 * Uveďte součet částek z téže tabulky ze sloupce 3 podle jednotlivých druhů příjmů. Pokud u některého druhu příjmů 
	 * převyšují výdaje příjmy, zahrňte do součtu výdaje maximálně do výše příjmů. Jsou-li výdaje spojené s jednotlivým 
	 * druhem příjmů (kategorie „ostatní příjmy“) vyšší než příjem, k rozdílu se podle § 10 odst. 4 zákona nepřihlíží.
	 */
	public void setKc_vyd10(double kc_vyd10) {
		this.kc_vyd10 = kc_vyd10;
	}
}
