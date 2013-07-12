package am.bizis.stspr;

public enum OsobaTyp {
	FO('F'), PO('P');
	char typ;
	OsobaTyp(char typ){
		this.typ=typ;
	}
	public char getTyp(){
		return this.typ;
	}
}
