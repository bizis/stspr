package am.bizis.stspr.fo;

import java.util.HashSet;

import taka.CountryCode;

public class ISEOOsoba extends Osoba {
	
	private final ISEOMistoOkres NAROZENI;
	private HashSet<CountryCode> obcanstvi;
	private Adresa adresa;
	private int cisloOP;
	private Stav stav;
	private Osoba asociace;
	private HashSet<String> cisloPasu;
	//TODO: pravni zpusobilost, opatrovnik

	public ISEOOsoba(String jmeno, String prijmeni, RodneCislo rc, ISEOMistoOkres narozeni) {
		super(jmeno, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashSet<String>();
	}

	public ISEOOsoba(String jmeno, String druhe, String prijmeni, RodneCislo rc,ISEOMistoOkres narozeni) {
		super(jmeno, druhe, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashSet<String>();
	}

	public ISEOOsoba(String jmeno, String druhe, String rodne, String prijmeni,
			RodneCislo rc,ISEOMistoOkres narozeni) {
		super(jmeno, druhe, rodne, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashSet<String>();
	}

	public ISEOMistoOkres getMistoNarozeni() {
		return NAROZENI;
	}
	

	/**
	 * @return the obcanstvi
	 */
	public HashSet<CountryCode> getObcanstvi() {
		return obcanstvi;
	}

	/**
	 * @param obcanstvi the obcanstvi to set
	 */
	public void setObcanstvi(HashSet<CountryCode> obcanstvi) {
		this.obcanstvi = obcanstvi;
	}
	
	public void addObcanstvi(CountryCode obcanstvi){
		this.obcanstvi.add(obcanstvi);
	}
	
	public boolean hasObcanstvi(CountryCode obcanstvi){
		if(this.obcanstvi.contains(obcanstvi)) return true;
		else return false;
	}
	
	public void remObcanstvi(CountryCode obcanstvi){
		this.obcanstvi.remove(obcanstvi);
	}
	

	/**
	 * @return the adresa
	 */
	public Adresa getAdresa() {
		return adresa;
	}

	/**
	 * @param adresa the adresa to set
	 */
	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	/**
	 * @return the cisloOP
	 */
	public int getCisloOP() {
		return cisloOP;
	}

	/**
	 * @param cisloOP the cisloOP to set
	 */
	public void setCisloOP(int cisloOP) {
		if(obcanstvi.contains(CountryCode.CZ)) this.cisloOP = cisloOP;
	}

	/**
	 * @return the stav
	 */
	public Stav getStav() {
		return stav;
	}

	/**
	 * @param stav the stav to set
	 */
	public void setStav(Stav stav) {
		this.stav = stav;
	}

	/**
	 * @return the asociace
	 */
	public Osoba getAsociace() {
		return asociace;
	}

	/**
	 * @param asociace the asociace to set
	 */
	public void setAsociace(Osoba o) throws IllegalArgumentException{
		if((this.stav==Stav.MANZELSTVI)&&o.getPohlavi()!=this.getPohlavi()) this.asociace=o;
		else if((this.stav==Stav.PARTNERSTVI)&&o.getPohlavi()==this.getPohlavi()) this.asociace=o;
		else throw new IllegalArgumentException("Tyto osoby nemohou vstoupit v dany obcansky svazek");
	}
	
	/**
	 * @return the cisloPasu
	 */
	public HashSet<String> getPasy() {
		return cisloPasu;
	}

	/**
	 * @param cisloPasu the cisloPasu to set
	 */
	public void setCislaPasu(HashSet<String> cisloPasu) {
		this.cisloPasu = cisloPasu;
	}
	
	public void addPas(String cislo){
		cisloPasu.add(cislo);
	}
	
	public boolean hasPas(String cislo){
		if(cisloPasu.contains(cislo)) return true;
		else return false;
	}
	
	
	public void remPas(String cislo){
		cisloPasu.remove(cislo);
	}
	
	/**
	 * Plna podoba jmena vcetne titulu a rodneho jmena
	 */
	@Override
	public String toString(){
		String jmeno="";
		super.getTitul();
		Titul nyni=getNyni();
		if(nyni!=null&&nyni.predJmenem) jmeno+=nyni.zkratka+" ";
		jmeno+=this.jmeno+" ";
		if(this.druhe!=null) jmeno+=this.druhe+" ";
		jmeno+=this.prijmeni;
		if(!this.prijmeni.equals(super.getRodnePrijmeni())) jmeno+=" roz. "+super.getRodnePrijmeni();
		
		return jmeno;
	}
	
	public static ISEOOsoba toISEO(Osoba o,ISEOMistoOkres narozeni){
		ISEOOsoba io = new ISEOOsoba(o.getKrestni(),o.getDruhe(),o.getRodnePrijmeni(),o.getPrijmeni(),o.getRodneCislo(),narozeni);
		io.setDatovaSchranka(o.getDatovaSchranka());
		for(Titul t:o.getTituly()){
			io.addTitul(t);
		}
		return io;
	}

}
