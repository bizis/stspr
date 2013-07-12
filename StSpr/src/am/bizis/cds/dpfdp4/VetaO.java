package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VetaO implements IVeta {

	private final int MAX=1;
	
	private double celk_sl4, celk_sl5, kc_dan_zah, kc_prij6, kc_prij6zahr, kc_zakldan8, kc_zd10, kc_zd7, kc_zd9, kc_ztrata2;
	private int kc_poj6;
	private double vynato6=0.0;

	private double vynato710;
	
	public VetaO() {
		//nic povinneho
	}
	
	/**
	 * Dílčí základ daně nebo ztráta z podnikání a z jiné samostatné výdělečné činnosti 
	 * podle § 7 zákona (ř. 113 přílohy č. 1 DAP) TODO
	 * @param kc_zd7 
	 */
	public void setKcZd7(double kc_zd7){
		this.kc_zd7=kc_zd7;
	}
	
	/**
	 * Dílčí základ daně z kapitálového majetku podle § 8 zákona
	 * Vyplňte úhrn příjmů z kapitálového majetku podle § 8 zákona, které zahrnují příjmy ze
	 * zdrojů na území České republiky i příjmy ze zdrojů v zahraničí, a to přepočtené na 
	 * Kč, které nejsou zdaněny zvláštní sazbou daně podle § 36 zákona.
	 * @param kc_zakldan8
	 */
	public void setKcZakldan8(double kc_zakldan8){
		this.kc_zakldan8=kc_zakldan8;
	}
	
	/**
	 * Dílčí základ daně nebo ztráta z pronájmu podle § 9 zákona (ř. 206 přílohy č. 2 DAP) TODO
	 * Přeneste údaj z ř. 206 Přílohy č. 2 DAP.
	 * @param kc_zd9
	 */
	public void setKcZd9(double kc_zd9){
		this.kc_zd9=kc_zd9;
	}
	
	/**
	 * Dílčí základ daně z ostatních příjmů podle § 10 zákona (ř. 209 přílohy č. 2 DAP) TODO
	 * Přeneste údaj z ř. 209 Přílohy č. 2 DAP.
	 * @param kc_zd10
	 */
	public void setKcZd10(double kc_zd10){
		this.kc_zd10=kc_zd10;
	}
	
	/**
	 * Úhrn příjmů od všech zaměstnavatelů
	 * @param kc_prij6
	 */
	public void setKcPrij6(double kc_prij6){
		this.kc_prij6=kc_prij6;
	}
	
	/**
	 * Úhrn povinného pojistného podle § 6 odst. 13 zákona
	 * Uveďte pojistné na sociální zabezpečení a příspěvek na státní politiku zaměstnanosti
	 * a pojistné na všeobecné zdravotní pojištění, které je z příjmů uvedených na ř. 31
	 * povinen platit podle zvláštních právních předpisů (zákon č. 589/1992 Sb. a zákon 
	 * č. 592/1992 Sb.) zaměstnavatel (ve vzoru Potvrzení č. 20 (2011: Potvrzení č. 19; 
	 * 2010: Potvrzení č. 18; 2009: Potvrzení č. 17) se jedná o řádek 6. a 7.). Částka 
	 * odpovídající povinnému pojistnému se uvádí i u zaměstnance, u kterého povinnost 
	 * platit povinné pojistné zaměstnavatel nemá (např. jde-li o příjmy za zdrojů v 
	 * zahraničí). Povinné pojistné se zaokrouhluje na celé koruny směrem nahoru.
	 * 
	 * @param kc_poj6
	 */
	public void setKcPoj6(double kc_poj6){
		this.kc_poj6=(int) Math.round(kc_poj6);
	}
	
	/**
	 * Daň zaplacená v zahraničí podle § 6 odst. 14 zákona
	 * Jste-li poplatník podle § 2 odst. 2 zákona (daňový rezident) a máte příjmy ze zdrojů
	 * v zahraničí, uveďte na tento řádek daň zaplacenou z těchto příjmů, jak je uvedeno v 
	 * § 6 odst. 14 zákona
	 * 
	 * @param kc_dan_zah
	 */
	public void setKcDanZah(double kc_dan_zah){
		this.kc_dan_zah=kc_dan_zah;
	}
	
	/**
	 * Úhrn příjmů plynoucích ze zahraničí zvýšený o povinné pojistné podle § 6 odst. 13 
	 * zákona
	 * Uveďte na tento řádek část příjmů z ř. 31, u kterých neměl plátce daně povinnost 
	 * srazit zálohy na daň dle § 38h zákona zvýšený o povinné pojistné podle § 6 odst. 13 
	 * zákona (např. příjmy zaměstnanců zahraničních zastupitelských úřadů v tuzemsku dle 
	 * § 38c zákona, příjmy ze zdrojů v zahraničí). Úhrn příjmů je uváděn pro stanovení 
	 * záloh na daň z příjmů podle § 38a zákona. Jste-li poplatník podle § 2 odst. 2 zákona
	 * (daňový rezident) a máte příjmy ze zdrojů v zahraničí ze státu, s nímž Česká 
	 * republika neuzavřela smlouvu o zamezení dvojího zdanění, uveďte na tento řádek úhrn
	 * příjmů zvýšený o povinné pojistné a snížený o daň zaplacenou z tohoto příjmu v 
	 * zahraničí uvedenou na ř. 33.
	 */
	public void setKcPrij6zahr(double kc_prij6zahr){
		this.kc_prij6zahr=0;
	}
	
	/**
	 * úhrn vyňatých příjmů ze zdrojů v zahraničí podle § 6
	 * @param vynato6
	 */
	public void setVynato6(double vynato6){
		this.vynato6=vynato6;
	}
	
	/**
	 * Úhrn dílčích základů daně podle § 7 až § 10 zákona po vynětí (ř. 41 - úhrn vyňatých příjmů ze zdrojů v zahraničí podle § 7 až § 10 zákona
	 * nebo ř. 41)
	 * Na tomto řádku uveďte rozdíl úhrnu dílčích základů daně podle § 7 až § 10 zákona (ř. 41) a úhrnu vyňatých příjmů ze zdrojů v zahraničí 
	 * podle § 7 až § 10 zákona. Kladnou hodnotu lze dále použít pro odečet ztráty z předcházejících zdaňovacích období podle § 34 odst. 1 
	 * zákona. V případě, že nemáte příjmy ze zdrojů v zahraničí, které se vyjímají ze zdanění, přeneste údaj z ř. 41. Záporná částka je 
	 * ztrátou, kterou přeneste na ř. 61, 4. oddílu, základní části DAP na stranu 2.
	 * @param vynato710
	 */
	public void setVynato710(double vynato710){
		this.vynato710=vynato710;
	}
	
	/**
	 * Uplatňovaná výše ztráty - vzniklé a vyměřené za předcházející zdaňovací období maximálně do výše ř. 41a
	 * Uveďte úhrn uplatňované ztráty (za zdaňovací období 2012 lze uplatnit ztrátu vzniklou a vyměřenou pouze za zdaňovací období 2006, 2007,
	 * 2009, 2009, 2010, 2011; za zdaňovací období 2011 lze uplatnit ztrátu vzniklou a vyměřenou pouze za zdaňovací období 2006, 2007, 2009, 
	 * 2009, 2010; za zdaňovací období 2010 lze uplatnit ztrátu vzniklou a vyměřenou pouze za zdaňovací období 2003, 2005, 2006, 2007, 2008 a 
	 * 2009) maximálně však do výše částky uvedené na ř. 41a. Částka uplatňované ztráty, která převyšuje částku na ř. 41a, je část ztráty, 
	 * kterou nelze uplatnit v tomto zdaňovacím období a tuto částku můžete uplatnit v následujících zdaňovacích obdobích v souladu s 
	 * ustanovením § 34 odst. 1 zákona. Poplatník uplatňující ztrátu za předchozí zdaňovací období podle § 34 odst. 1 zákona uvede do 
	 * samostatné přílohy následující údaje: 1. Zdaňovací období, ve kterém daňová ztráta vznikla / byla uplatněna, 2. Celkovou výši daňové 
	 * ztráty vyměřené (vzniklé) nebo přiznané za zdaňovací období uvedené v bodu 1, 3. Část daňové ztráty odečtené v předcházejících 
	 * zdaňovacích obdobích. 4. Část daňové ztráty uplatněné v tomto zdaňovacím období (ř. 44, 2. oddílu základní části DAP, str. 2), 5. Část 
	 * daňové ztráty, kterou lze odečíst v následujících zdaňovacích obdobích. Doporučený vzor přílohy pro poplatníky uplatňující ztrátu z 
	 * příjmů je uveden na internetových stránkách na adrese http://cds.mfcr.cz. TODO
	 * 2009: Uveďte úhrn uplatňované ztráty (za zdaňovací období 2009 lze uplatnit ztrátu vzniklou a vyměřenou pouze za zdaňovací období 2002, 
	 * 2003, 2004, 2005, 2006, 2007 a 2008) maximálně však do výše částky uvedené na ř. 41a. Částka uplatňované ztráty, která převyšuje částku 
	 * na ř. 41a, je část ztráty, kterou nelze uplatnit v tomto zdaňovacím období a tuto částku můžete uplatnit v následujících zdaňovacích 
	 * obdobích v souladu s ustanovením § 34 odst. 1 zákona. Poplatník uplatňující ztrátu za předchozí zdaňovací období podle § 34 odst. 1 
	 * zákona uvede do samostatné přílohy následující údaje: 
	 * 1. Zdaňovací období, ve kterém daňová ztráta vznikla / byla uplatněna, 
	 * 2. Celkovou výši daňové ztráty vyměřené (vzniklé) nebo přiznané za zdaňovací období uvedené v bodu 1, 
	 * 3. Část daňové ztráty odečtené v předcházejících zdaňovacích obdobích. 
	 * 4. Část daňové ztráty uplatněné v tomto zdaňovacím období (ř. 44, 2. oddílu základní části DAP, str. 2), 
	 * 5. Část daňové ztráty, kterou lze odečíst v následujících zdaňovacích obdobích. Doporučený vzor přílohy pro poplatníky uplatňující 
	 * ztrátu z příjmů je uveden na internetových stránkách Ministerstva financí: http://www.mfcr.cz, v nabídce Daně a cla/Daně/Tiskopisy ke stažení.
	 * @param kc_ztrata2
	 */
	private void setKcZtrata2(double kc_ztrata2){
		this.kc_ztrata2=kc_ztrata2;
	}
	
	public void setCelkSl4(double celk_sl4){
		this.celk_sl4=celk_sl4;
		this.kc_ztrata2=celk_sl4;
	}

	@Override
	public Element getElement() throws ParserConfigurationException {
		double kc_zd6p=kc_prij6+kc_poj6-kc_dan_zah;
		double kc_vynprij_6=kc_zd6p-vynato6;
		if(kc_vynprij_6<0) kc_vynprij_6=0;
		//double kc_zd6=kc_zd6p;
		double kc_uhrn=kc_zd7+kc_zakldan8+kc_zd9+kc_zd10;
		double kc_vynprij=kc_uhrn-vynato710;
		double kc_zakldan23;
		if(kc_vynprij>0) kc_zakldan23=kc_vynprij_6-kc_vynprij;
		else kc_zakldan23=kc_vynprij;
		if(kc_ztrata2>kc_vynprij){
			kc_ztrata2=kc_vynprij;
			celk_sl5=celk_sl4-kc_ztrata2;
		}
		double kc_zakldan=kc_zakldan23-kc_ztrata2;
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaO=EPO.createElement("VetaO");
		
		if(celk_sl4!=0) VetaO.setAttribute("celk_sl4", celk_sl4+"");
		if(celk_sl5!=0) VetaO.setAttribute("celk_sl5", celk_sl5+"");
		if(kc_dan_zah!=0) VetaO.setAttribute("kc_dan_zah", kc_dan_zah+"");
		if(kc_poj6!=0) VetaO.setAttribute("kc_poj6", kc_poj6+"");
		if(kc_prij6!=0) VetaO.setAttribute("kc_prij5", kc_prij6+"");
		if(kc_prij6zahr!=0) VetaO.setAttribute("kc_prij6zahr", kc_prij6zahr+"");
		if(kc_uhrn!=0) VetaO.setAttribute("kc_uhrn", kc_uhrn+"");
		if(kc_vynprij!=0) VetaO.setAttribute("kc_vynprij", kc_vynprij+"");
		if(kc_vynprij_6>0) VetaO.setAttribute("kc_vynprij_6", kc_vynprij_6+"");
		if(kc_zakldan!=0) VetaO.setAttribute("kc_zakldan", kc_zakldan+"");
		if(kc_zakldan23!=0) VetaO.setAttribute("kc_zakldan23", kc_zakldan23+"");
		if(kc_zakldan8!=0) VetaO.setAttribute("kc_zakldan8",kc_zakldan8+"");
		if(kc_zd10!=0) VetaO.setAttribute("kc_zd10", kc_zd10+"");
		if(kc_zd6p!=0){
			VetaO.setAttribute("kc_zd6", kc_zd6p+"");
			VetaO.setAttribute("kc_zd6p",kc_zd6p+"");
		}
		if(kc_zd7!=0) VetaO.setAttribute("kc_zd7",kc_zd7+"");
		if(kc_zd9!=0) VetaO.setAttribute("kc_zd9", kc_zd9+"");
		if(kc_ztrata2!=0) VetaO.setAttribute("kc_ztrata2", kc_ztrata2+"");
		return VetaO;
	}

	@Override
	public int getMaxPocet() {
		return MAX;
	}

}
