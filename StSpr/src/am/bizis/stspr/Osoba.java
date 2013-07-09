package am.bizis.stspr;

import java.util.Date;
import java.util.HashSet;

import am.bizis.stspr.exception.IllegalIDNumberException;
import taka.CountryCode;

public class Osoba {
	
	private String jmeno,druhe,prijmeni;
	private final String RODNE_PRIJMENI;
	private final RodneCislo RODNE_CISLO;
	private HashSet<CountryCode> obcanstvi;
	private Adresa adresa;
	private final ISEOMistoOkres narozeni;
	private HashSet<Titul> tituly;
	private Vzdelani nejvyssiDosazene;
	private int cisloOP;
	private Stav stav;
	private Osoba asociace;
	private String datovaschrannka;
	private HashSet<String> cisloPasu;
	//pravni zpusobilost, opatrovnik

	/* KONSTRUKTORY */
	public Osoba(String jmeno,String prijmeni){
		this(jmeno,prijmeni,"");
	}
	
	public Osoba(RodneCislo rc){
		this(null,null,rc,null);
	}
	
	public Osoba(String jmeno,String prijmeni,String rc) throws IllegalIDNumberException{
		this(jmeno,prijmeni,new RodneCislo(rc),null);
	}
	
	public Osoba(String jmeno,String prijmeni,String rc,ISEOMistoOkres narozeni) throws IllegalIDNumberException{
		this(jmeno,prijmeni,new RodneCislo(rc),narozeni);
	}
	
	public Osoba(String jmeno,String prijmeni,RodneCislo rc){
		this(jmeno,null,prijmeni,rc,null);
	}
	
	public Osoba(String jmeno,String prijmeni,RodneCislo rc,ISEOMistoOkres narozeni){
		this(jmeno,null,prijmeni,rc,narozeni);
	}
	
	public Osoba(String jmeno,String druhe,String prijmeni,RodneCislo rc){
		this(jmeno,druhe,prijmeni,rc,null);
	}
	
	public Osoba(String jmeno,String druhe,String prijmeni,RodneCislo rc,ISEOMistoOkres narozeni){
		this.jmeno=jmeno;
		this.prijmeni=prijmeni;
		this.RODNE_PRIJMENI=prijmeni;
		this.RODNE_CISLO=rc;
		this.obcanstvi=new HashSet<CountryCode>();
		this.druhe=druhe;
		this.narozeni=narozeni;
	}

	/* matody */
	public String getKrestni(){
		return jmeno;
	}
	
	public String getPrijmeni(){
		return prijmeni;
	}
	
	public RodneCislo getRodneCislo(){
		return RODNE_CISLO;	
	}
	
	public Date getNarozeni(){
		return RODNE_CISLO.getNarozeni();
	}
	
	public Pohlavi getPohlavi(){
		return RODNE_CISLO.getPohlavi();
	}
	
	/**
	 * zaregistruje obcanstvi dane zeme
	 */
	public void addObcanstvi(CountryCode cc){
		obcanstvi.add(cc);
	}
	
	/**
	 * @param cc kod zeme
	 * @return ma obcanstvi zadane zeme?
	 */
	public boolean hasObcanstvi(CountryCode cc){
		if (obcanstvi.contains(cc)) return true;
		else return false;
	}
	
	/**
	 * odebere obcanstvi
	 * @param cc kod zeme
	 */
	public void remObcanstvi(CountryCode cc){
		obcanstvi.remove(cc);
	}
	
	/**
	 * @return druhe jmeno
	 */
	public String getDruhe(){
		return druhe;
	}
	
	/**
	 * @return zda ma druhe jmeno
	 */
	public boolean hasDruhe(){
		if (druhe==null) return false;
		else return true;
	}
	
	/**
	 * Zmena jmena
	 */
	public void setJmeno(String krestni,String druhe,String prijmeni){
		this.jmeno=krestni;
		this.druhe=druhe;
		this.prijmeni=prijmeni;
	}
	
	public String getRodnePrijmeni(){
		return this.RODNE_PRIJMENI;
	}
	
	/**
	 * @return bylo-li zmeneno prijmeni
	 */
	public boolean zmenaPrijmeni(){
		if(this.prijmeni.equals(this.RODNE_PRIJMENI)) return true;
		else return false;
	}
	
	/**
	 * Zmeni adresu
	 * @param adr nove misto bydliste
	 */
	public void setAdresa(Adresa adr){
		this.adresa=adr;
	}
	
	/**
	 * @return soucasna adresa
	 */
	public Adresa getAdresa(){
		return this.adresa;
	}
	
	/**
	 * @return Misto a okres narozeni
	 */
	public ISEOMistoOkres getMistoNarozeni(){
		return this.narozeni;
	}
	
	/**
	 * Zaregistruje dalsi akademicky titul
	 * @param t
	 */
	public void addTitul(Titul t){
		this.tituly.add(t);
		if(t.getLevel().getLevel()<this.nejvyssiDosazene.getLevel()) this.nejvyssiDosazene=t.getLevel();
	}
	
	/**
	 * Slouzi k zadani zakladniho a stredniho vzdelani, vyssi urovne se zadavaji pres titul
	 * @param v uroven nejvyssiho dosazeneho vzdelani
	 * @throws IllegalArgumentException pokud zadavana uroven vzdelani je vyssi nez stredni
	 */
	public void setNejvyssiVzdelani(Vzdelani v){
		if(v.getLevel()<=Vzdelani.STREDNI.getLevel()) this.nejvyssiDosazene=v;
		else throw new IllegalArgumentException("Vyssi nez stredni vzdelani se zadava pres titul!");
	}
		
	/**
	 * @return nejvyssi dosazene vzdelani
	 */
	public Vzdelani getNejvyssiVzdelani(){
		return this.nejvyssiDosazene;
	}
	
	/**
	 * Plna podoba jmena vcetne titulu
	 */
	@Override
	public String toString(){
		String jmeno="";
		for(Titul t:tituly){
			if(t.uvadenPredJmenem()) jmeno+=t.getZkratka()+" "; 
		}
		jmeno+=this.jmeno+" ";
		if(this.druhe!=null) jmeno+=this.druhe+" ";
		jmeno+=this.prijmeni;
		for(Titul t:tituly){
			if(!t.uvadenPredJmenem()) jmeno+=" "+t.getZkratka();
		}
		return jmeno;
	}
	
	/**
	 * Novy obcansky prukaz
	 * @param OP cislo
	 */
	public void setCisloOP(int OP){
		this.cisloOP=OP;
	}
	
	/**
	 * Cislo platneho OP
	 * @return
	 */
	public int getCisloOP(){
		return this.cisloOP;
	}
	
	public void setStav(Stav s){
		this.stav=s;
	}
	
	public Stav getStav(){
		return this.stav;
	}
	
	public void setAsociace(Osoba o) throws IllegalArgumentException{
		if((this.stav==Stav.MANZELSTVI)&&o.getPohlavi()!=this.getPohlavi()) this.asociace=o;
		else if((this.stav==Stav.PARTNERSTVI)&&o.getPohlavi()==this.getPohlavi()) this.asociace=o;
		else throw new IllegalArgumentException("Tyto osoby nemohou vstoupit v dany obcansky svazek");
	}
	
	public Osoba getAsociace(){
		return this.asociace;
	}

	public void setDatovaSchranka(String ds){
		this.datovaschrannka=ds;
	}
	
	public String getDatovaSchranka(){
		return this.datovaschrannka;
	}
	
	public void addPas(String cislo){
		cisloPasu.add(cislo);
	}
	
	public boolean hasPas(String cislo){
		if(cisloPasu.contains(cislo)) return true;
		else return false;
	}
	
	public HashSet<String> getPasy(){
		return cisloPasu;
	}
	
	public void remPas(String cislo){
		cisloPasu.remove(cislo);
	}
}
