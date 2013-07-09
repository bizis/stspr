package am.bizis.cds.dpfdp4;

public class VetaD {
	
	private char audit;
	public VetaD() {
		
	}
	/**
	 * @return the audit
	 */
	public char getAudit() {
		return audit;
	}
	/**
	 * @param audit Zákonná povinnost ověření účetní závěrky auditorem
	 * Hodnota může být pouze:
	 * A - zákonná povinnost ověřit účetní závěrku auditorem, 
	 * N - není zákonná povinnost ověřit účetní závěrku auditorem.
	 */
	public void setAudit(char audit) {
		if (audit=='A'|audit=='N') this.audit = audit;
		else throw new IllegalArgumentException("Povolene hodnoty jsou 'A' a 'N'");
	}
	
	
	

}
