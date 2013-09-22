package am.bizis.cds.dpfdp4;

public enum UcSoust {
	EVIDENCE(1), UCETNICTVI(2);
	int uc_soust;
	private UcSoust(int uc_soust){
		this.uc_soust=uc_soust;
	}
	int getUc_soust(){
		return this.uc_soust;
	}
}
