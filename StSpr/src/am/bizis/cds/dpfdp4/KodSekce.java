package am.bizis.cds.dpfdp4;

public enum KodSekce {
	O("O","obecna priloha"),D("D","blizsi specifikace duvodu podani DoDAP");
	
	String kod,popis;
	
	KodSekce(String kod,String popis){
		this.kod=kod;
		this.popis=popis;
	}
}
