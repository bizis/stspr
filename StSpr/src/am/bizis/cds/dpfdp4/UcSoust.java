package am.bizis.cds.dpfdp4;

/**
 * Typ účetní soustavy
 * Uveďte 1, pokud vedete daňovou evidenci nebo 2 pokud vedete účetnictví.
 * Položka obsahuje kritické kontroly: položka může nabývat hodnot 1 nebo 2; 
 * pokud je vyplněna tabulka pro DE, musí položka uc_soust nabývat hodnoty 1; 
 * pokud jsou vyplněny vybrané údaje z ÚV, musí položka uc_soust nabývat hodnoty 2
 * @author alex
 *
 */
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
