package am.bizis.stspr;

import taka.CountryCode;

public class Adresa {
	private final int NULLINT=-999;
	private final int POPISNE,PSC;
	private String ulice;
	private int orientacni;
	private CountryCode stat;
	private String obec, cast, cizi_psc, cizi_stat;
	private String kod_ulice, kod_adresniho_mista;//TODO ziskavani kodu z ciselniku dle zadanych udaju a jejich vraceni - nutne pro XML ficury
	
	public Adresa(String ulice,int popisne,int orientacni, int PSC, String obec, String cast) throws IllegalArgumentException{
		this(popisne,obec,cast,PSC);
		this.orientacni=orientacni;
		this.ulice=ulice;
	}
	
	public Adresa(int popisne, String obec, int PSC) throws IllegalArgumentException{
		this(popisne,obec,obec,PSC);
	}
	
	public Adresa(int popisne, String obec, String cast, int PSC) throws IllegalArgumentException{
		this.obec=obec;
		this.POPISNE=popisne;
		if((PSC>=10000)&&(PSC<=99999))this.PSC=PSC; 
		else throw new IllegalArgumentException("PSC ma 5 celosicelnych znaku");
		this.cast=cast;
		this.stat=CountryCode.CZ;
		this.ulice=null;
		this.orientacni=this.NULLINT;
		this.cizi_psc=null;
	}
	
	public Adresa(String ulice, int popisne, String obec, String PSC, CountryCode stat){
		this.ulice=ulice;
		this.POPISNE=popisne;
		this.obec=obec;
		this.cizi_psc=PSC;
		this.PSC=NULLINT;
		this.stat=stat;
		this.cizi_stat=null;
	}
	
	public Adresa(String ulice, int popisne, String obec, String PSC, String stat,CountryCode zeme){
		this(ulice,popisne,obec,PSC,zeme);
		this.cizi_stat=stat;
	}
	
	@Override
	public String toString(){
		String adresa;
		if((this.stat)!=CountryCode.CZ){
			if(this.cizi_stat.equals(null)) adresa=ulice+" "+POPISNE+"\n"+obec+"\n"+cizi_psc+"\n"+stat.getName();
			else adresa=ulice+" "+POPISNE+"\n"+obec+" "+cizi_stat+"\n"+cizi_psc+"\n"+stat.getName();
		}else{
			if (this.ulice.equals(null)){
				if (this.cast.equals(this.obec)) adresa="c.p. "+POPISNE+"\n"+PSC+" "+obec;
				else adresa=cast+" "+POPISNE+"\n"+PSC+" "+obec;
			}else{
				if(this.cast.equals(this.obec)) adresa=ulice+" "+POPISNE+"/"+orientacni+"\n"+PSC+" "+obec;
				else adresa=ulice+" "+POPISNE+"/"+orientacni+"\n"+cast+"\n"+PSC+" "+obec;
			}
		}
		return adresa;
	}
	
	public String getUlice(){
		return this.ulice;
	}
	
	public int getPopisne(){
		return this.POPISNE;
	}
	
	public int getOrientacni(){
		return this.orientacni;
	}
	
	public int getPSC(){
		return this.PSC;
	}
	
	public String getObec(){
		return this.obec;
	}
	
	public String getCast(){
		return this.cast;
	}
	
	public CountryCode getStat(){
		return this.stat;
	}
	
	public void setUlice(String nazev){
		this.ulice=nazev;
	}
	
	public void setOrientacni(int cislo){
		this.orientacni=cislo;
	}
	
	public void setObec(String nazev){
		this.obec=nazev;
	}
	
	public void setCast(String nazev){
		this.cast=nazev;
	}
	
	public void setStat(CountryCode stat){
		this.stat=stat;
	}
}
