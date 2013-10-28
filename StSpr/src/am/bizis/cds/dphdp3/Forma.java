package am.bizis.cds.dphdp3;

public enum Forma {
	B("B"), O("O"), D("O"), E("E");
	
	String kod;
	private Forma(String f){
		kod=f;
	}
	
	String getKod(){
		return this.kod;
	}
}
