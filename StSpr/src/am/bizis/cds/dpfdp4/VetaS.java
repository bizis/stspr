package am.bizis.cds.dpfdp4;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

public class VetaS implements IVeta {

	private double da_dan16,kc_odcelk;
	private double kc_dalvzd,kc_op15_12,kc_op15_13,kc_op15_14,kc_op15_8,kc_op28_5,kc_op34_4;
	
	public VetaS(){
		
	}
	/**
	 * Odst. 8 zákona (úhrada za další vzdělávání)
	 * @param kc Uveďte uplatňovanou výši úhrady za zkoušky ověřující výsledky dalšího vzdělávání podle zákona č.179/2006 Sb., max.
	 * však do výše 10 000 Kč za zdaňovací období 2012 (resp. 2009, 2010, 2011) (u poplatníka se zdravotním postižením max.
	 * 13 000 Kč a s těžším zdravotním postižením max. 15 000 Kč).
	 * 
	 */
	public void setKcDalVzd(double kc){
		this.kc_dalvzd=kc;
	}
	/**
	 * 	Odst. 5 zákona (penzijní připojištění)
	 * @param kc Uveďte uplatňovanou výši plateb příspěvků, které jste zaplatil (zaplatila) na své penzijní připojištění se státním
	 * příspěvkem uvedenou v potvrzení penzijního fondu o zaplacených příspěvcích na penzijní připojištění se státním 
	 * příspěvkem na zdaňovací období 2012 (resp. 2009, 2010, 2011) sníženou o 6 000 Kč. Maximální částka, kterou lze 
	 * takto odečíst za zdaňovací období 2012 (resp. 2009, 2010, 2011), činí 12 000 Kč.
	 * 
	 */
	public void setKcOp1512(double kc){
		//penzijni pripojisteni
		if(kc<0) kc=0;
		this.kc_op15_12=kc;
	}
	/**
	 * 	Odst. 6 zákona (životní pojištění)
	 * @param kc Uveďte uplatňovanou výši pojistného, které jste zaplatil (zaplatila) na své soukromé životní pojištění uvedenou v 
	 * potvrzení pojišťovny o zaplaceném pojistném na soukromé životní pojištění ve zdaňovacím období 2012 (resp. 2009, 
	 * 2010, 2011). Maximální částka, kterou lze odečíst za zdaňovací období 2012 (resp. 2009, 2010, 2011), činí v úhrnu 
	 * 12 000 Kč.
	 * 
	 */
	public void setKcOp1513(double kc){
		//zivotni pojisteni
		if(kc<0) kc=0;
		this.kc_op15_13=kc;
	}
	/**
	 * Odst. 7 zákona (odborové příspěvky)
	 * @param kc Uveďte uplatňovanou výši zaplacených členských příspěvků ve zdaňovacím období 2012 (resp. 2009, 2010, 2011) členem 
	 * odborové organizace odborové organizaci, která podle svých stanov obhajuje hospodářské a sociální zájmy zaměstnanců 
	 * v rozsahu vymezeném zvláštním právním předpisem (§ 146 a násl. zákoníku práce; pro ZO 2011 a starší § 18). Takto lze
	 * odečíst částku do výše 1,5 % zdanitelných příjmů podle § 6, s výjimkou příjmů podle § 6 zdaněných srážkou podle 
	 * zvláštní sazby daně, maximálně však do výše 3 000 Kč za zdaňovací období 2012 (resp. 2009, 2010, 2011).
	 */
	public void setKcOp1514(double kc){
		if(kc<0) kc=0;
		this.kc_op15_14=kc;
	}
	/**
	 * Odst. 1 zákona (hodnota daru/darů)
	 * @param kc Uveďte uplatňovanou hodnotu daru (darů), který (které) jste poskytl (poskytla) podle § 15 odst. 1 zákona.
	 * Úhrnná hodnota daru (darů) ve zdaňovacím období musí přesáhnout 2 % ze základu daně ř. 42 anebo činit alespoň 
	 * 1 000 Kč. V úhrnu lze odečíst nejvýše 10 % ze základu daně ř. 42.
	 */
	public void setKcOp158(double kc){
		if(kc<0) kc=0;
		this.kc_op15_8=kc;
	}
	/**
	 * Odst. 3 a 4 zákona (odečet úroků)
	 * @param kc Uveďte uplatňovanou výši úroků zaplacených ve zdaňovacím období 2012 (resp. 2011, 2010, 2009) z 
	 * poskytnutého úvěru ze stavebního spoření nebo z hypotečního úvěru uvedenou v potvrzení stavební spořitelny nebo 
	 * banky nebo pobočky zahraniční banky anebo zahraniční banky. Úhrnná částka úroků, o které lze snížit základ daně 
	 * podle těchto odst. ze všech úvěrů u poplatníků v téže domácnosti nesmí překročit 300 000 Kč. Při placení úroků jen 
	 * po část roku nesmí uplatňovaná částka překročit jednu dvanáctinu této maximální částky za každý měsíc placení úroků.
	 */
	public void setKcOp285(double kc){
		if(kc<0) kc=0;
		this.kc_op28_5=kc;
	}
	/**
	 * Částka podle § 34 odst. 4 zákona (výzkum a vývoj)
	 * @param kc Uveďte uplatňovanou výši výdajů (nákladů) vynaložených při realizaci výzkumu a vývoje (Pokyn D-288).
	 */
	public void setKcOp344(double kc){
		if(kc<0) kc=0;
		this.kc_op34_4=kc;
	}
	
	@Override
	public Element getElement() throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxPocet() {
		// TODO Auto-generated method stub
		return 0;
	}

}
