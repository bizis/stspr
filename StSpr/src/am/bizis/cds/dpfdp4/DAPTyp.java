package am.bizis.cds.dpfdp4;

public enum DAPTyp {
	B("B","radne"),O("O","radne-opravne"),D("D","dodatecne"),E("E","dodatecne-opravne");

	String dap_typ, desc;
	private DAPTyp(String dap_typ, String desc){
		this.dap_typ=dap_typ;
		this.desc=desc;
	}
}
