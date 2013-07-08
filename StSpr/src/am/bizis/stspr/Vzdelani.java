package am.bizis.stspr;

public enum Vzdelani {
	ZAKLADNI(1), 
	STREDNI(2), VYUCEN(2), MATURITA(2),
	VYSSI_ODBORNE(2.5),
	BAKALAR(3),
	MAGISTR(4),RIGOROZA(4.5),
	DOKTOR(5),AKADEMIA(5.5);
	
	private final double level;
	Vzdelani(double level){
		this.level=level;
	}
	
	double getLevel(){
		return level;
	}
}
