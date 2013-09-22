package am.bizis.cds.dpfdp4;

public enum PrSazba {
	ZEMEDELSTVI_REMESLO(80),ZIVNOST(40),OSTATNI(40),PRONAJEM(30);
	
	int sazba;
	PrSazba(int sazba){
		this.sazba=sazba;
	}
	
	int getSazba(){
		return this.sazba;
	}
}
