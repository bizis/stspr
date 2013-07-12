package am.bizis.cds.dpfdp4;

import am.bizis.stspr.OsobaTyp;

public enum ZastKod {
	FOZAZA("1","Zakonny zastupce",OsobaTyp.FO), 
	FOUZA("2","Ustanoveny zastupce",OsobaTyp.FO), 
	FOSPOZA("3","spolecny zastupce - smluvni zastupce",OsobaTyp.FO),
	FOOBEZMO("4a","obecny zmocnenec - fyzicka osoba i pravnicka osoba",OsobaTyp.FO),
	FODAPOAD("4b","fyzicka osoba - danovy poradce nebo advokat",OsobaTyp.FO),
	POUZA("2","ustanoveny zastupce",OsobaTyp.PO), 
	POSPOZA("3","spolecny zastupce - smluvni zastupce",OsobaTyp.PO), 
	POOBEZMO("4a","obecny zmocnenec - fyzicka osoba i pravnicka osoba",OsobaTyp.PO), 
	PODAPO("4c","pravnicka osoba vykonavajici danove poradenstvi",OsobaTyp.PO);
	
	//ciselny kod, popis, rozdeleni FOPO pres OsobaTyp
	String kod; String popis; OsobaTyp typ;
	private ZastKod(String kod, String popis, OsobaTyp typ){
		this.kod=kod;
		this.popis=popis;
		this.typ=typ;
	}
}
