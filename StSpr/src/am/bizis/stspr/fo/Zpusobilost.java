package am.bizis.stspr.fo;

public enum Zpusobilost {
	PLNA, OMEZENA, ODNATA;
	
	private String rozsah;
	private ISEOOsoba zastupce;
	
	public void setRozsah(String s){
		if(this.equals(OMEZENA)) this.rozsah=s;
	}
	
	public String getRozsah(){
		if(this.equals(OMEZENA)) return this.rozsah;
		else return null;
	}
	
	public void setZastupce(ISEOOsoba z){
		if(!this.equals(PLNA)) zastupce=z;
	}
	
	public ISEOOsoba getZastupce(){
		if(this.equals(PLNA)) return null;
		else return zastupce;
	}
}
