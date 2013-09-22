package am.bizis.cds.dpfdp4;

public enum KodDrPrij10 {
	A("A","příležitostná činnost"),
	B("B","prodej nemovitostí"),
	C("C","prodej movitých věcí"),
	D("D","prodej cenných papírů"),
	E("E","příjmy z převodu podle § 10 odst. 1, písm. c) zákona"),
	F("F","jiné ostatní příjmy");
	
	String kod,popis;
	KodDrPrij10(String kod,String popis){
		this.kod=kod;
		this.popis=popis;
	}
}
