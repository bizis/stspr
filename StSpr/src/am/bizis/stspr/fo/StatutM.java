package am.bizis.stspr.fo;

public enum StatutM {
	BRNO("Brno"),BUDEJOVICE("Ceske Budejovice"),HAVIROV("Havirov"),HRADEC("Hradec Kralove"),
	VARY("Karlovy Vary"),LIBEREC("Liberec"),OLOMOUC("Olomouc"),OPAVA("Opava"),OSTRAVA("Ostrava"),
	PARDUBICE("Pardubice"),PLZEN("Plzen"), PRAHA("Praha"), USTI("Usti nad Labem"), ZLIN("Zlin"), OBEC;
	
	String obec;
	private StatutM(String obec){
		this.obec=obec;
	}
	private StatutM(){
		this.obec=null;
	}
	
	public static StatutM getObec(String obec){
		StatutM ret=StatutM.OBEC;
		for(StatutM s:StatutM.values()){
			if(s.obec.equals(obec)) ret=s;
		}
		return ret;
	}
	
	public String getJmeno(){
		return this.obec;
	}
}	