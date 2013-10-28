package am.bizis.cds.dpfdp4;

import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.IVeta;
import am.bizis.exception.ConditionException;

/**
 * Vytvori element VetaS pisemnosti DPFDP4 - Záznam III. Oddílu
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#S
 * @author alex
 */
public class VetaS implements IVeta {

	private final int MAX=1,MAXUROKY=300000,MAXZPPP=12000;
	private final double SAZBA=0.15;
	private final double ZAKLAD,ZAKLAD23;
	private final String ELEMENT="VetaS";
	
	private double da_dan16,kc_odcelk,kc_zdsniz,kc_zdzaokr;
	private double kc_dalsivzd,kc_op15_12,kc_op15_13,kc_op15_14,kc_op15_8,kc_op28_5,kc_op34_4,kc_op_dal;
	private String text_op_dal;
	private int m_uroky,m_dalsi, int_kc_zdzaokr;
	
	/**
	 * Vytvori vetu O a vyplni povinne polozky
	 * @param o Veta O, ze ktere bereme zaklad dane po odecteni ztraty a zaklad dane
	 */
	public VetaS(VetaO o){
		this.ZAKLAD=o.getKcZakldan();
		this.ZAKLAD23=o.getKcZakldan23();
	}
	/**
	 * Odst. 8 zákona (úhrada za další vzdělávání)
	 * @param kc Uveďte uplatňovanou výši úhrady za zkoušky ověřující výsledky dalšího vzdělávání podle zákona č.179/2006 Sb., max.
	 * však do výše 10 000 Kč za zdaňovací období 2012 (resp. 2009, 2010, 2011) (u poplatníka se zdravotním postižením max.
	 * 13 000 Kč a s těžším zdravotním postižením max. 15 000 Kč).
	 * 
	 */
	public void setKcDalsiVzd(double kc){
		this.kc_dalsivzd=kc;
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
		if(kc>MAXZPPP) kc=MAXZPPP;
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
		if(kc>MAXZPPP) kc=MAXZPPP;
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
		//TODO: kontrola castky
		this.kc_op15_14=kc;
	}
	/**
	 * Odst. 1 zákona (hodnota daru/darů)
	 * @param kc Uveďte uplatňovanou hodnotu daru (darů), který (které) jste poskytl (poskytla) podle § 15 odst. 1 zákona.
	 * Úhrnná hodnota daru (darů) ve zdaňovacím období musí přesáhnout 2 % ze základu daně ř. 42 anebo činit alespoň 
	 * 1 000 Kč. V úhrnu lze odečíst nejvýše 10 % ze základu daně ř. 42.
	 */
	public void setKcOp158(double kc){
		if((kc<1000)||(kc<(ZAKLAD23*0.02))) kc=0;
		if(kc>(ZAKLAD23*0.1)) kc=(ZAKLAD23*0.1);
		this.kc_op15_8=kc;
	}
	/**
	 * Odst. 3 a 4 zákona (odečet úroků)
	 * @param kc Uveďte uplatňovanou výši úroků zaplacených ve zdaňovacím období 2012 (resp. 2011, 2010, 2009) z 
	 * poskytnutého úvěru ze stavebního spoření nebo z hypotečního úvěru uvedenou v potvrzení stavební spořitelny nebo 
	 * banky nebo pobočky zahraniční banky anebo zahraniční banky. Úhrnná částka úroků, o které lze snížit základ daně 
	 * podle těchto odst. ze všech úvěrů u poplatníků v téže domácnosti nesmí překročit 300 000 Kč. Při placení úroků jen 
	 * po část roku nesmí uplatňovaná částka překročit jednu dvanáctinu této maximální částky za každý měsíc placení úroků.
	 * @param mesicu Počet měsíců: Uveďte počet měsíců placení úroků.
	 * @throws ConditionException pokud je pocet mesicu vetsi nez 12 nebo mensi nez 0
	 */
	public void setKcOp285(double kc, int mesicu) throws ConditionException{
		if((mesicu<0)||(mesicu>12)) throw new ConditionException("Pocet mesicu musi byt 0 az 12");
		this.m_uroky=mesicu;
		if(kc<0) kc=0;
		if(kc>mesicu/12*MAXUROKY) kc=mesicu/12*MAXUROKY;
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
	
	/**
	 * Další částky
	 * @param kc Uveďte např. uplatňovanou výši odpočtu podle § 34 odst. 9, 10 zákona. 
	 * @param text Do bílého pole tohoto řádku uveďte název uplatňované částky. Další částky - název uplatňované částky
	 * @param mesicu Počet měsíců.
	 * @throws IllegalArgumentException pokud neni vyplnen nazev castky
	 * @throws ConditionException pokud je pocet mesicu vetsi nez 12 nebo mensi nez 0
	 */
	public void setKcOpDal(double kc, String text, int mesicu){
		this.kc_op_dal=kc;
		if(text.equals(null)||text.equals("")) throw new IllegalArgumentException("Nazev castky musi byt vyplnen!");
		else this.text_op_dal=text;
		if((mesicu<0)||(mesicu>12)) throw new ConditionException("Pocet mesicu musi byt 0 az 12");
		this.m_dalsi=mesicu;
	}
	
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		this.kc_odcelk=this.kc_op15_8+this.kc_op28_5+this.kc_op15_12+this.kc_op15_13+this.kc_op15_14+this.kc_dalsivzd+this.kc_op34_4+this.kc_op_dal;
		this.kc_zdsniz=ZAKLAD-this.kc_odcelk;
		if(this.kc_zdsniz<0) this.kc_zdsniz=0;
		this.int_kc_zdzaokr=(int)Math.round(kc_zdsniz);
		if(int_kc_zdzaokr%100>=50) this.int_kc_zdzaokr=(this.int_kc_zdzaokr/100+1)*100;
		else this.kc_zdzaokr=(this.kc_zdzaokr/100);//TODO: TESTME
		this.da_dan16=this.kc_zdzaokr*SAZBA;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaS=EPO.createElement(ELEMENT);
		if(this.da_dan16!=0) VetaS.setAttribute("da_dan16", da_dan16+"");
		if(this.kc_dalsivzd!=0) VetaS.setAttribute("kc_dalsivzd", kc_dalsivzd+"");
		if(this.kc_odcelk!=0) VetaS.setAttribute("kc_odcelk", kc_odcelk+"");
		if(this.kc_op15_12>0) VetaS.setAttribute("kc_op15_12", kc_op15_12+"");
		if(this.kc_op15_13>0) VetaS.setAttribute("kc_op15_13", kc_op15_13+"");
		if(this.kc_op15_14>0) VetaS.setAttribute("kc_op15_13", kc_op15_14+"");
		if(this.kc_op15_8>0) VetaS.setAttribute("kc_op15_8", kc_op15_8+"");
		if(this.kc_op28_5>0){
			VetaS.setAttribute("kc_op28_5", kc_op28_5+"");
			VetaS.setAttribute("m_uroky", m_uroky+"");
		}
		if(this.kc_op34_4>0)VetaS.setAttribute("kc_op34_4", kc_op34_4+"");
		if(this.kc_op_dal!=0){
			VetaS.setAttribute("kc_op_dal", kc_op_dal+"");
			VetaS.setAttribute("m_dalsi", m_dalsi+"");
			VetaS.setAttribute("text_op_dal", text_op_dal);
		}
		if(this.kc_zdsniz!=0)VetaS.setAttribute("kc_zdsniz", kc_zdsniz+"");
		if(this.kc_zdzaokr!=0)VetaS.setAttribute("kc_zdzaokr", kc_zdzaokr+"");
		return VetaS;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return this.MAX;
	}
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		/*
		 * 													PredepsanaPriloha
		 * posytnuty dar									PP_DAR
		 * uhrada na dalsi vzdelavani						PP_DALSIVZ
		 * penzijni pripojisteni							PP_POTPENZ
		 * uver na bytove potereby a vyse uroku				PP_POTUVER
		 * zaplacene castky na soukrome zivotni pojisteni	PP_POTZIVP
		 * dalsi prilohy									ObecnaPriloha
		 */
		HashSet<String> dep=new HashSet<String>();
		if(kc_op15_8>0) dep.add("PP_DAR");
		if(kc_dalsivzd>0) dep.add("PP_DALSIVZ");
		if(kc_op15_12>0) dep.add("PP_POTPENZ");
		if(kc_op28_5>0) dep.add("PP_POTUVER");
		if(kc_op15_13>0) dep.add("PP_POTZIVP");
		if(kc_op_dal>0) dep.add("OBECNA PRILOHA: Dalsi odpocty");
		return dep.toArray(new String[0]);
	}
	@Override
	public String toString(){
		return ELEMENT;
	}

	/**
	 * Daň podle § 16 zákona
	 * @return Daň podle § 16 zákona činí 15 % ze základu daně uvedeného na ř. 56.
	 */
	public double getDaDan16(){
		this.kc_odcelk=this.kc_op15_8+this.kc_op28_5+this.kc_op15_12+this.kc_op15_13+this.kc_op15_14+this.kc_dalsivzd+this.kc_op34_4+this.kc_op_dal;
		this.kc_zdsniz=ZAKLAD-this.kc_odcelk;
		if(this.kc_zdsniz<0) this.kc_zdsniz=0;
		this.int_kc_zdzaokr=(int)Math.round(kc_zdsniz);
		if(int_kc_zdzaokr%100>=50) this.int_kc_zdzaokr=(this.int_kc_zdzaokr/100+1)*100;
		else this.kc_zdzaokr=(this.kc_zdzaokr/100);//TODO: TESTME
		return this.kc_zdzaokr*SAZBA;
	}
}
