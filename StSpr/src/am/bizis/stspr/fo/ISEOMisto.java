package am.bizis.stspr.fo;

/**
 * Misto, jak je vedeno v Informacnim systemu evidence obyvatel (133/2000 Sb.)
 * Jedna se o zjednodusenou formu adresy. ISEOMistoOkres rozsiruje ISEOMisto
 * § 3, odst. 2
 * @author alex
 *
 */
public class ISEOMisto {
	private final String OBEC;
	
	/**
	 * @param obec Nazev obce
	 */
	public ISEOMisto(String obec){
		this.OBEC=obec;
	}
	
	@Override
	public String toString(){
		return this.OBEC;
	}
	
	public String getObec(){
		return this.OBEC;
	}
}
