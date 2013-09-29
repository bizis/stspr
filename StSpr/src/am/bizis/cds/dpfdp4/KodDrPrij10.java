package am.bizis.cds.dpfdp4;

/**
 * <p>Označení druhu příjmů podle § 10 odst. 1 zákona<br />
 * A – příležitostná činnost<br />
 * B - prodej nemovitostí<br />
 * C - prodej movitých věcí<br />
 * D - prodej cenných papírů<br />
 * E - příjmy z převodu podle § 10 odst. 1, písm. c) zákona<br />
 * F - jiné ostatní příjmy</p>
 * @author alex
 */
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
