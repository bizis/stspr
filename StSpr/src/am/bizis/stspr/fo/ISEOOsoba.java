package am.bizis.stspr.fo;

import java.util.HashMap;
import java.util.HashSet;

import taka.CountryCode;

public class ISEOOsoba extends Osoba {
	
	private final ISEOMistoOkres NAROZENI;
	private HashSet<CountryCode> obcanstvi;
	private Adresa adresa;
	private int cisloOP;
	private Stav stav;
	private Osoba asociace;
	//private HashSet<String> cisloPasu;
	private HashMap<CountryCode, String> cisloPasu;
	private Zpusobilost zp;

	/**
	 * Vytvori osobu s danym jmenem, prijmenim, rodnym cislem, mistem narozeni a adresou
	 * Osoba bude mit nastaveno jedine obcanstvi a to Ceske republiky
	 * @param jmeno Krestni jmeno
	 * @param prijmeni Prijmeni
	 * @param rc Rodne cislo
	 * @param narozeni Misto a okres narozeni
	 * @param adr Adresa trvaleho pobytu
	 */
	public ISEOOsoba(String jmeno, String prijmeni, RodneCislo rc, ISEOMistoOkres narozeni, Adresa adr) {
		super(jmeno, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashMap<CountryCode,String>();
		this.zp=Zpusobilost.PLNA;
		this.adresa=adr;
		obcanstvi.add(CountryCode.CZ);
	}
	
	/**
	 * Vytvori osobu s danym jmenem, prijmenim, rodnym cislem, mistem narozeni, adresou trvaleho pobytu, obcanstvim a cislem pasu
	 * 
	 * @param jmeno
	 * @param prijmeni
	 * @param rc
	 * @param narozeni
	 * @param adr
	 * @param obc
	 * @param pas
	 */
	public ISEOOsoba(String jmeno, String prijmeni, RodneCislo rc, ISEOMistoOkres narozeni, Adresa adr, CountryCode obc, String pas) {
		super(jmeno, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashMap<CountryCode,String>();
		this.zp=Zpusobilost.PLNA;
		this.adresa=adr;
		obcanstvi.add(obc);
		cisloPasu.put(obc, pas);
	}

	/**
	 * Vytvori osobu s danym jmenem, druhym jmenem, prijmenim, rodnym cislem, mistem narozeni a adresou trvaleho pobytu
	 * Jako jedine obcanstvi nastavi Ceskou republiku
	 * 
	 * @param jmeno
	 * @param druhe
	 * @param prijmeni
	 * @param rc
	 * @param narozeni
	 * @param adr
	 */
	public ISEOOsoba(String jmeno, String druhe, String prijmeni, RodneCislo rc,ISEOMistoOkres narozeni, Adresa adr) {
		super(jmeno, druhe, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashMap<CountryCode,String>();
		this.zp=Zpusobilost.PLNA;
		this.adresa=adr;
		obcanstvi.add(CountryCode.CZ);
	}
	
	/**
	 * Vytvori osobu s danym jmenem, druhym jmenem, prijmenim, rodnym cislem, mistem narozeni, adresou trvaleho pobytu obcanstvim
	 * a cislem pasu
	 *  
	 * @param jmeno
	 * @param druhe
	 * @param prijmeni
	 * @param rc
	 * @param narozeni
	 * @param adr
	 * @param obc
	 * @param pas
	 */
	public ISEOOsoba(String jmeno, String druhe, String prijmeni, RodneCislo rc,ISEOMistoOkres narozeni, Adresa adr, CountryCode obc,String pas) {
		super(jmeno, druhe, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashMap<CountryCode,String>();
		this.zp=Zpusobilost.PLNA;
		this.adresa=adr;
		obcanstvi.add(obc);
		cisloPasu.put(obc, pas);
	}

	public ISEOOsoba(String jmeno, String druhe, String rodne, String prijmeni,
			RodneCislo rc,ISEOMistoOkres narozeni, Adresa adr) {
		super(jmeno, druhe, rodne, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashMap<CountryCode,String>();
		this.zp=Zpusobilost.PLNA;
		this.adresa=adr;
		this.obcanstvi.add(CountryCode.CZ);
	}
	
	public ISEOOsoba(String jmeno, String druhe, String rodne, String prijmeni,
			RodneCislo rc,ISEOMistoOkres narozeni, Adresa adr, CountryCode obc, String pas) {
		super(jmeno, druhe, rodne, prijmeni, rc);
		this.NAROZENI=narozeni;
		this.obcanstvi=new HashSet<CountryCode>();
		this.cisloPasu=new HashMap<CountryCode,String>();
		this.zp=Zpusobilost.PLNA;
		this.adresa=adr;
		this.obcanstvi.add(obc);
		this.cisloPasu.put(obc, pas);
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
	public HashMap<CountryCode,String> getPasy() {
		return cisloPasu;
	}

	/**
	 * @param cisloPasu the cisloPasu to set
	 */
	public void setCislaPasu(HashMap<CountryCode, String> cisloPasu) {
		this.cisloPasu = cisloPasu;
	}
	
	public void addPas(String cislo,CountryCode obcanstvi){
		//cisloPasu.add(obcanstvi,cislo);
		cisloPasu.put(obcanstvi,cislo);
	}
	
	public boolean hasPas(String cislo){
		if(cisloPasu.containsValue(cislo)) return true;
		else return false;
	}
	
	
	/*public void remPas(String cislo){
		//cisloPasu.remove(cislo);
		cisloPasu.get(cislo);
	}*/ //TODO spojit s HashMap
	
	public String getPas(CountryCode obcanstvi){
		return cisloPasu.get(obcanstvi);
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
	
	public static ISEOOsoba toISEO(Osoba o,ISEOMistoOkres narozeni,Adresa adr){
		ISEOOsoba io = new ISEOOsoba(o.getKrestni(),o.getDruhe(),o.getRodnePrijmeni(),o.getPrijmeni(),o.getRodneCislo(),narozeni,adr);
		io.setDatovaSchranka(o.getDatovaSchranka());
		for(Titul t:o.getTituly()){
			io.addTitul(t);
		}
		return io;
	}
	
	public static ISEOOsoba toISEO(Osoba o,ISEOMistoOkres narozeni,Adresa adr,CountryCode obcan, String pas){
		ISEOOsoba io = new ISEOOsoba(o.getKrestni(),o.getDruhe(),o.getRodnePrijmeni(),o.getPrijmeni(),o.getRodneCislo(),narozeni,adr,obcan,pas);
		io.setDatovaSchranka(o.getDatovaSchranka());
		for(Titul t:o.getTituly()){
			io.addTitul(t);
		}
		return io;
	}
	
	public Zpusobilost getZpusobilost(){
		return this.zp;
	}
	
	public void setZpusobilost(Zpusobilost nova){
		this.zp=nova;
	}
}
