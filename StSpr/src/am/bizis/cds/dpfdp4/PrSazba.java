package am.bizis.cds.dpfdp4;

/**
 * Sazba výdajů % z příjmů
 * 2011 a vyšší
 * Pro výpočet výdajů uváděných na tomto řádku použijte níže uvedenou tabulku „B. Druh činnosti“. 
 * Pro zdaňovací období 2011 si můžete uplatnit výdaje ve výši: 
 * 80 % z příjmů ze zemědělské výroby, lesního a vodního hospodářství (zákon č. 252/1997 Sb.) a z příjmů ze živností řemeslných, 
 * 60 % z příjmů ze živnosti s výjimkou příjmů ze živností řemeslných, 
 * 40 % v ostatních případech uvedených v ustanovení § 7 odst. 7 písm. c) zákona (např. autorské honoráře) a 
 * 30 % z příjmu z pronájmu majetku zařazeného v obchodním majetku.
 * @author alex
 */
public enum PrSazba {
	ZEMEDELSTVI_REMESLO(80),ZIVNOST(60),OSTATNI(40),PRONAJEM(30);
	
	int sazba;
	PrSazba(int sazba){
		this.sazba=sazba;
	}
	
	int getSazba(){
		return this.sazba;
	}
}
