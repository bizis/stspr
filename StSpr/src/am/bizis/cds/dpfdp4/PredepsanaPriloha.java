package am.bizis.cds.dpfdp4;

public enum PredepsanaPriloha {
	PP_DAR("PP_DAR"),PP_DALSIVZ("PP_DALSIVZ"),PP_POTPENZ("PP_POTPENZ"),PP_POTUVER("PP_POTUVER"),PP_POTZIVP("PP_POTZIVP");
	
	final String KOD;
	private PredepsanaPriloha(String nazev){
		KOD=nazev;
	}
}
