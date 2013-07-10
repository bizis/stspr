package am.bizis.cds.dpfdp4;

public enum DAPDuvod {
	G("G","insolvence"),I("I","úmrtí");
	
	String duvodpoddapdpf,popis;
	private DAPDuvod(String kod,String popis){
		this.duvodpoddapdpf=kod;
		this.popis=popis;
	}

}
