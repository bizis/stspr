/**
 * 
 */
package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import taka.CountryCode;

/**
 * @author alex
 * Metoda prostého zápočtu daně
 */
public class VetaL implements IVeta {

	private final VetaS s;
	private final VetaO o;
	private final CountryCode kod_statu;
	private final double kc_prijzap;
	private double da_uznzap,kc_k_zapzahr,proczahr, roz_od12;
	private int da_zahr, kc_vydzap;
	
	
	/**
	 * @param kc_prijzap Příjmy ze zdrojů v zahraničí, u nichž se použije metoda zápočtu
	 * Na tomto řádku uveďte úhrn příjmů ze zdrojů v zahraničí, na které se podle smluv o zamezení dvojího zdanění uplatňuje metoda zápočtu. Příjmy podle § 6 zákona
	 * uveďte v souladu s § 6 odst.14 zákona.
	 * @param kod_statu 	Kód státu
	 * Při použití metody prostého zápočtu se podle § 38f odst.8 zákona metoda provádí za každý stát samostatně. Proto v případě, že Vám plynou příjmy z více států, 
	 * použijte k výpočtu za každý stát Samostatný list Přílohy č.3 zveřejněný na http//www.mfcr.cz, v nabídce Daně a cla, Daně, Tiskopisy ke stažení.
	 * Pro hodnotu této položky použijte číselník Země (zeme). Z číselníku se vkládá položka kod2.
	 * Položka obsahuje kritické kontroly: hodnota musí obsahovat kód existujícího státu a pro každý stát musí být jedna věta L.
	 */
	public VetaL(VetaS s,VetaO o,CountryCode kod_statu, double kc_prijzap) {
		this.s=s;
		this.o=o;
		this.kod_statu=kod_statu;
		this.kc_prijzap=kc_prijzap;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaL=EPO.createElement("VetaL");
		if(o.getKcZakldan23()!=0) proczahr=(kc_prijzap-kc_vydzap)/o.getKcZakldan23()*100;
		else proczahr=100;//pokud nemam prijmy (ani ze zahranici, mam 100% prijmu ze zahranici
		if(proczahr<0) proczahr=0;
		if(proczahr>100) proczahr=100;
		kc_k_zapzahr=s.getDaDan16()*proczahr/100;
		if(kc_k_zapzahr>da_zahr) da_uznzap=kc_k_zapzahr;
		else da_uznzap=da_zahr;
		roz_od12=da_zahr-da_uznzap;
		if(da_uznzap!=0)VetaL.setAttribute("da_uznzap",da_uznzap+"");
		if(kc_k_zapzahr!=0)VetaL.setAttribute("kc_k_zapzahr",kc_k_zapzahr+"");
		VetaL.setAttribute("proczahr",proczahr+"");
		if(roz_od12!=0)VetaL.setAttribute("roz_od12",roz_od12+"");
		if(da_zahr!=0)VetaL.setAttribute("da_zahr",da_zahr+"");
		VetaL.setAttribute("kc_prijzap",kc_prijzap+"");
		if(kc_vydzap!=0)VetaL.setAttribute("kc_vydzap",kc_vydzap+"");
		VetaL.setAttribute("kod_statu",kod_statu.getAlpha2()+"");
		return VetaL;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 999;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}
	
	/**
	 * @param da_zahr Daň zaplacená v zahraničí
	 * Do tohoto řádku uveďte částku daně zaplacené ve státě zdroje ze zdaněných příjmů, a to pouze do výše, která mohla být v tomto státě vybrána v souladu s 
	 * příslušnými ustanoveními smlouvy o zamezení dvojího zdanění. Částka daně uplatňovaná k zápočtu musí být prokázána potvrzením zahraničního správce daně o 
	 * zaplacení daně (§ 38f odst. 5 zákona).
	 */
	public void setDa_zahr(int da_zahr) {
		this.da_zahr = da_zahr;
	}

	/**
	 * @param kc_vydzap Výdaje
	 * Na tomto řádku uveďte úhrn výdajů související s příjmy uvedenými na ř. 321.
	 */
	public void setKc_vydzap(int kc_vydzap) {
		this.kc_vydzap = kc_vydzap;
	}
}
