package am.bizis.cds.dpfdp4;

/**
 * <p>DAP<br />
 * Typ daňového přiznání.<br />
 * B - řádné<br />
 * O - řádné-opravné<br />
 * D - dodatečné<br />
 * E - dodatečné-opravné</p>
 * @author alex
 *
 */
public enum DAPTyp {
	B("B","radne"),O("O","radne-opravne"),D("D","dodatecne"),E("E","dodatecne-opravne");

	String dap_typ, desc;
	private DAPTyp(String dap_typ, String desc){
		this.dap_typ=dap_typ;
		this.desc=desc;
	}
}
