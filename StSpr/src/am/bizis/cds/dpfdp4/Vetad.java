/**
 * 
 */
package am.bizis.cds.dpfdp4;

import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.IVeta;
import am.bizis.cds.Veta;

import taka.CountryCode;

/**
 * Seznam pro poplatníky uplatňující nárok na vyloučení dvojího zdanění podle § 38f odst. 10 ZDP
 * @author alex
 *
 */
public class Vetad implements IVeta {

	private double dan_seznam, prijmy_seznam, zapl_dan;
	private CountryCode k_stat_zdroj;
	private String ident_udaje;
	private Hashtable<String,String> ht;
	/**
	 * 
	 */
	public Vetad() {
		ht=new Hashtable<String,String>();
	}

	/**
	 * @param dan_seznam 4. daň
	 * Uveďte částku daně zaplacené v tomto státě přepočtenou na Kč, nebo v případě, že nemáte k dispozici doklady 
	 * zahraničního správce daně, uveďte předpokládanou výši daně uplatněnou v daňovém přiznání.
	 */
	public void setDanSeznam(double dan_seznam){
		if(ht.containsKey("dan_seznam")) ht.remove("dan_seznam");
		ht.put("dan_seznam", dan_seznam+"");
	}
	
	/**
	 * @param prijmy_seznam 5. příjmy
	 * Uveďte výši příjmů ze zdrojů v tomto státě, stanovenou podle § 38f odst. 3 zákona, nebo v případě, že nemáte k 
	 * dispozici doklady zahraničního správce daně, uveďte odhadovanou výši příjmů, příjmy ze závislé činnosti uveďte v 
	 * souladu s § 6 odst.14 zákona
	 */
	public void setPrijmySeznam(double prijmy_seznam){
		if(ht.containsKey("prijmy_seznam")) ht.remove("prijmy_seznam");
		ht.put("prijmy_seznam",prijmy_seznam+"");
	}
	
	/**
	 * @param zapl_dan 3. zaplacená daň
	 * Uveďte částku daně zaplacené v tomto státě v místní měně.
	 */
	public void setZaplDan(double zapl_dan){
		if(ht.containsKey("zapl_dan")) ht.remove("zapl_dan");
		ht.put("zapl_dan",zapl_dan+"");
	}
	
	/**
	 * @param k_stat_zdroj 2. stát zdroje příjmů
	 * Uveďte stát zdroje zahraničních příjmů.
	 */
	public void setKStatZdroj(CountryCode k_stat_zdroj){
		if(ht.containsKey("k_stat_zdroj")) ht.remove("k_stat_zdroj");
		ht.put("k_stat_zdroj", k_stat_zdroj.getAlpha2());
	}
	
	/**
	 * @param ident_udaje 1. identifikační údaje (adresa)
	 * Uveďte údaje (včetně adresy) identifikující zahraničního správce daně nebo zahraničního plátce daně anebo 
	 * depozitáře, identifikační údaje uveďte i v případě, když nemáte doklady zahraničního správce daně ve lhůtě k 
	 * podání daňového přiznání k dispozici.
	 */
	public void setIdentUdaje(String ident_udaje){
		if(ht.containsKey("ident_udaje")) ht.remove("ident_udaje");
		ht.put("ident_udaje",ident_udaje);
	}
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		return Veta.getElement("Vetad", ht);
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
	
	@Override
	public String toString(){
		return "Vetad";
	}
	
	

}
