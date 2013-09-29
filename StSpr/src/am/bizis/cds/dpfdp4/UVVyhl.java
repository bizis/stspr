package am.bizis.cds.dpfdp4;

/**
 * Vyhláška č.
 * Vyplňte číslo vyhlášky (první část), podle které byly účetní výkazy a následně vybrané 
 * údaje sestaveny. Uveďte následující označení:
 * 
 * 500 pro
 * Vyhlášku č. 500/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., 
 * o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, které jsou podnikateli 
 * účtujícími v soustavě podvojného účetnictví, v platném znění.
 * 
 * 501 pro
 * Vyhlášku č.501/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., 
 * o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, které jsou bankami a 
 * jinými finančními institucemi, v platném znění.
 * 
 * 502 pro
 * Vyhlášku č.502/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., 
 * o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, které jsou pojišťovnami,
 * v platném znění.
 * 
 * 503 pro
 * Vyhlášku č.503/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., 
 * o účetnictví, ve znění pozdějších předpisů, pro zdravotní pojišťovny, v platném znění.
 * 
 * 504 pro
 * Vyhlášku č. 504/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., 
 * o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky, u kterých hlavním 
 * předmětem činnosti není podnikání, pokud účtují v soustavě podvojného účetnictví, v 
 * platném znění.
 * 
 * 507 pro
 * Vyhlášku č. 507/2002 Sb., kterou se provádějí některá ustanovení zákona č. 563/1991 Sb., 
 * o účetnictví, ve znění pozdějších předpisů, pro účetní jednotky účtující v soustavě 
 * jednoduchého účetnictví, ve znění účinném do 31. prosince 2003, podle níž mohou doposud 
 * postupovat účetní jednotky vymezené § 38a zákona č. 563/1991 Sb., o účetnictví, v platném
 * znění.
 * 
 * 410 pro
 * Vyhlášku č. 410/2009 Sb., pro vybrané účetní jednotky, v platném znění, nebo jste účetní 
 * jednotkou podle § 38a zákona č. 563/1991 Sb., o účetnictví, ve znění pozdějších předpisů,
 * která využívá možnost vést účetnictví podle zákona č. 563/1991 Sb., o účetnictví, ve znění
 * zákona č. 117/1994 Sb., zákona č. 227/1997 Sb., zákona č. 492/2000 Sb., zákona č. 
 * 353/2001 Sb. a zákona č. 437/2003 Sb., a na kterou se vztahují ustanovení zákona č. 
 * 563/1991 Sb., o účetnictví, a jeho prováděcích právních předpisů, která upravují účtování
 * v soustavě jednoduchého účetnictví, ve znění účinném k 31. prosinci 2003.
 * 
 * Položka obsahuje kritické kontroly: nesmí být vyplněn současně druhový a účelový výkaz 
 * zisků a ztrát, pokud je zadána hodnota musí odpovídat masce položky, může být použito 
 * pouze číslo vyhlášky korespondující s danou písemností, může být použito pouze číslo 
 * vyhlášky korespondující s daným typem subjektu, pro zadané číslo vyhlášky musí být použity
 * odpovídající výkazy, pro vyhlášky kromě 500 musí být vyplněny všechny výkazy, číslo 
 * vyhlášky musí být vyplněno je-li zadán alespoň 1 výkaz.
 * 
 * @author alex
 */
public enum UVVyhl {
	C500(500),C501(501),C502(502),C503(503),C504(504),C507(507),C410(410);
	
	int vyhlaska;
	UVVyhl(int vyhlaska){
		this.vyhlaska=vyhlaska;
	}
}
