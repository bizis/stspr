package am.bizis.stspr.fo;

import java.io.IOException;

import taka.CountryCode;

public class Adresa extends ISEOMistoOkres{
	private static final int NULLINT=-999;
	private final int POPISNE,PSC;
	private String ulice;
	private int orientacni;
	private CountryCode stat;
	private String cast, cizi_psc, cizi_stat;
	
	public Adresa(String ulice, int popisne, String obec, String PSC, String stat,CountryCode zeme) throws IllegalArgumentException, IOException{
		this(ulice,popisne,obec,PSC,zeme);
		this.cizi_stat=stat;
	}
	
	public Adresa(String ulice, int popisne, String obec, String PSC, CountryCode stat) throws IllegalArgumentException, IOException{
		this(popisne,obec,NULLINT);
		this.ulice=ulice;
		this.cizi_psc=PSC;
		this.stat=stat;
		this.cizi_stat=null;
	}
	
	public Adresa(int popisne, String obec, int PSC) throws IllegalArgumentException,IOException{
		this(popisne,obec,obec,PSC);
	}
	
	public Adresa(String ulice,int popisne,int orientacni, int PSC, String obec, String cast) throws IllegalArgumentException,IOException{
		this(popisne,obec,cast,PSC);
		this.orientacni=orientacni;
		this.ulice=ulice;
	}
	
	public Adresa(int popisne, String obec, String cast, int PSC) throws IllegalArgumentException,IOException{
		super(obec,PSCTools.getOkres(PSC));
		if((PSC>=10000)&&(PSC<=99999))this.PSC=PSC; 
		else throw new IllegalArgumentException("PSC ma 5 celosicelnych znaku");
		this.POPISNE=popisne;
		if(StatutM.getObec(cast).equals(StatutM.OBEC)) this.cast=obec;
		else this.cast=cast;
		this.stat=CountryCode.CZ;
		this.ulice=null;
		this.orientacni=Adresa.NULLINT;
		this.cizi_psc=null;
		this.cizi_stat=null;
	}
	
	@Override
	public String toString(){
		String adresa;
		if((this.stat)!=CountryCode.CZ){
			if(this.cizi_stat.equals(null)) adresa=ulice+" "+POPISNE+"\n"+super.getObec()+"\n"+cizi_psc+"\n"+stat.getName();
			else adresa=ulice+" "+POPISNE+"\n"+super.getObec()+" "+cizi_stat+"\n"+cizi_psc+"\n"+stat.getName();
		}else{
			if (this.ulice.equals(null)){
				if (this.cast.equals(super.getObec())) adresa="c.p. "+POPISNE+"\n"+PSC+" "+super.getObec();
				else adresa=cast+" "+POPISNE+"\n"+PSC+" "+super.getObec();
			}else{
				if(this.cast.equals(super.getObec())) adresa=ulice+" "+POPISNE+"/"+orientacni+"\n"+PSC+" "+super.getObec();
				else adresa=ulice+" "+POPISNE+"/"+orientacni+"\n"+cast+"\n"+PSC+" "+super.getObec();
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
		return super.getObec();
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
	
	public void setCast(String nazev){
		this.cast=nazev;
	}
	
	public void setStat(CountryCode stat){
		this.stat=stat;
	}
}
