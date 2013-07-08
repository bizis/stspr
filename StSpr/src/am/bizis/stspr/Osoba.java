package am.bizis.stspr;

import java.util.Date;
import java.util.HashSet;
import taka.CountryCode;

public class Osoba {
	
	private String jmeno,druhe,prijmeni;
	private final String RODNE_PRIJMENI;
	private final RodneCislo RODNE_CISLO;
	private HashSet<CountryCode> obcanstvi;
	//misto narozeni, adresa, tituly, stav, cislo OP, cislo pasu, cislo datove schranky
	//pravni zpusobilost, opatrovnik

	public Osoba(String jmeno,String prijmeni){
		this(jmeno,prijmeni,"");
	}
	
	public Osoba(RodneCislo rc){
		this(null,null,rc);
	}
	
	public Osoba(String jmeno,String prijmeni,String rc) throws IllegalIDNumberException{
		this(jmeno,prijmeni,new RodneCislo(rc));
	}
	
	public Osoba(String jmeno,String prijmeni,RodneCislo rc){
		this(jmeno,null,prijmeni,rc);
	}
	
	public Osoba(String jmeno,String druhe,String prijmeni,RodneCislo rc){
		this.jmeno=jmeno;
		this.prijmeni=prijmeni;
		this.RODNE_PRIJMENI=prijmeni;
		this.RODNE_CISLO=rc;
		this.obcanstvi=new HashSet<CountryCode>();
		this.druhe=druhe;
	}

	public String getKrestni(){
		return jmeno;
	}
	
	public String getPrijmeni(){
		return prijmeni;
	}
	
	public RodneCislo getRodneCislo(){
		return RODNE_CISLO;	
	}
	
	public Date getNarozeni(){
		return RODNE_CISLO.getNarozeni();
	}
	
	public Pohlavi getPohlavi(){
		return RODNE_CISLO.getPohlavi();
	}
	
	public void addObcanstvi(CountryCode cc){
		obcanstvi.add(cc);
	}
	
	public boolean hasObcanstvi(CountryCode cc){
		if (obcanstvi.contains(cc)) return true;
		else return false;
	}
	
	public void remObcanstvi(CountryCode cc){
		obcanstvi.remove(cc);
	}
	
	public String getDruhe(){
		return druhe;
	}
	
	public boolean hasDruhe(){
		if (druhe==null) return false;
		else return true;
	}
	
	public void setJmeno(String krestni,String druhe,String prijmeni){
		this.jmeno=krestni;
		this.druhe=druhe;
		this.prijmeni=prijmeni;
	}
	
	public String getRodnePrijmeni(){
		return this.RODNE_PRIJMENI;
	}
	
	public boolean zmenaPrijmeni(){
		if(this.prijmeni.equals(this.RODNE_PRIJMENI)) return true;
		else return false;
	}
}
