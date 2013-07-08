package am.bizis.stspr;

import taka.CountryCode;

/**
 * Misto a okres, jak je vedeno v Informacnim systemu evidence obyvatel (133/2000 Sb.)
 * Jedna se o zjednodusenou formu adresy. Adresa rozsiruje ISEOMistoOkres.
 * § 3, odst. 3 d,l,m, p 4, s
 * @author alex
 */
public class ISEOMistoOkres extends ISEOMisto {

	private final String OKRES;
	private final CountryCode STAT;
	private final String KOD_UZEMNIHO_PRVKU=null;//referencni vazba na referencni udaj v zakladnim registru uzemni identifikace, adres a nemovitosti
	
	/**
	 * @param obec nazev obce
	 * @param okres okres, kde obec lezi
	 */
	public ISEOMistoOkres(String obec, String okres) {
		super(obec);
		this.OKRES=okres;
		this.STAT=CountryCode.CZ;
	}
	
	public ISEOMistoOkres(String obec, CountryCode stat){
		super(obec);
		this.OKRES=null;
		this.STAT=stat;
	}
	
	@Override
	public String toString(){
		String retezec;
		if(STAT.equals(CountryCode.CZ)) retezec=super.toString()+", "+this.OKRES;
		else retezec=super.toString()+this.STAT;
		return retezec;
	}

	public String getOkres(){
		return this.OKRES;
	}
	
	public CountryCode getStat(){
		return this.STAT;
	}
}
