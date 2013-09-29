package am.bizis.cds.dpfdp4;

import java.util.HashSet;
import java.util.Set;

/**
 * Druhy moznych predepsanych priloh
 * @author alex
 */
public enum PredepsanaPriloha {
	PP_DAR("PP_DAR"),PP_DALSIVZ("PP_DALSIVZ"),PP_POTPENZ("PP_POTPENZ"),PP_POTUVER("PP_POTUVER"),PP_POTZIVP("PP_POTZIVP");
	
	final String KOD;
	private PredepsanaPriloha(String nazev){
		KOD=nazev;
	}
	/**
	 * Vrati mnozinu moznych predepsanych priloh
	 * @return jake existuje predepsane prilohy
	 */
	public static Set<String> getNazvy(){
		HashSet<String> nazvy=new HashSet<String>();
		for(PredepsanaPriloha pp:PredepsanaPriloha.values()){
			nazvy.add(pp.KOD);
		}
		return nazvy;
	}
}
