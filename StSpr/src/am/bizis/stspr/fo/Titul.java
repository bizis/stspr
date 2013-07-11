package am.bizis.stspr.fo;

public enum Titul {
	DiS(Vzdelani.VYSSI_ODBORNE,"diplomovany specialista","DiS.",false),
	Bc(Vzdelani.BAKALAR,"bakalar","Bc.",true), BcA(Vzdelani.BAKALAR,"bakalar umeni","BcA.",true),
	Ing(Vzdelani.MAGISTR,"inzenyr","Ing.",true), IngA(Vzdelani.MAGISTR,"inzenyr architekt","Ing. arch.",true),
	MUDr(Vzdelani.MAGISTR,"doktor mediciny","MUDr.",true), MVDr(Vzdelani.MAGISTR,"doktor veterinarni mediciny","MVDr.",true),
	MgA(Vzdelani.MAGISTR,"magistr umeni","MgA.",true), Mgr(Vzdelani.MAGISTR,"magistr","Mgr.",true),
	Judr(Vzdelani.RIGOROZA,"doktor prav","JUDr.",true), Phdr(Vzdelani.RIGOROZA,"doktor filozofie","PhDr.",true),
	Rndr(Vzdelani.RIGOROZA,"doktor prirodnich ved","RNDr.",true),Pharmdr(Vzdelani.RIGOROZA,"doktor farmacie","PharmDr.",true),
	ThLic(Vzdelani.RIGOROZA,"licenciat teologie","ThLic.",true), ThDr(Vzdelani.RIGOROZA,"doktor teologie","ThDr.",true),
	Phd(Vzdelani.DOKTOR,"doktor","Ph.D.",false),Thd(Vzdelani.DOKTOR,"doktor teologie","Th.D.",false),
	Doc(Vzdelani.AKADEMIA,"docent","Doc.",true), Prof(Vzdelani.AKADEMIA,"profesor","Prof.",true);
	
	Vzdelani level;
	String titul,zkratka;
	boolean predJmenem;
	
	Titul(Vzdelani uroven,String titul,String zkratka, boolean predJmenem){
		this.level=uroven;
		this.titul=titul;
		this.zkratka=zkratka;
		this.predJmenem=predJmenem;
	}
	
	public String getZkratka(){
		return zkratka;
	}
	
	public boolean uvadenPredJmenem(){
		return predJmenem;
	}
	
	public Vzdelani getLevel(){
		return level;
	}
}
