package am.bizis.stspr.fo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import am.bizis.stspr.IC;
import am.bizis.stspr.IPodnik;
import am.bizis.stspr.OsobaTyp;

public class OSVC extends ISEOOsoba implements IPodnik {

	private final long DIC;
	private final IC myIC;
	private String email;
	private int telefon,fax;
	
	public OSVC(String jmeno, String prijmeni, RodneCislo rc,
			ISEOMistoOkres narozeni, IC ic) {
		super(jmeno, prijmeni, rc, narozeni);
		this.myIC=ic;
		this.DIC=0;//TODO change
	}

	public OSVC(String jmeno, String druhe, String prijmeni, RodneCislo rc,
			ISEOMistoOkres narozeni, IC ic) {
		super(jmeno, druhe, prijmeni, rc, narozeni);
		this.myIC=ic;
		this.DIC=0;//TODO change
	}

	public OSVC(String jmeno, String druhe, String rodne, String prijmeni,
			RodneCislo rc, ISEOMistoOkres narozeni, IC ic) {
		super(jmeno, druhe, rodne, prijmeni, rc, narozeni);
		this.myIC=ic;
		this.DIC=0;//TODO change
	}
	
	public OSVC(String jmeno, String druhe, String rodne, String prijmeni,
			RodneCislo rc, ISEOMistoOkres narozeni, IC ic, long dic) {
		super(jmeno,druhe,rodne,prijmeni,rc,narozeni);
		this.myIC=ic;
		this.DIC=0;//TODO 
	}

	@Override
	public IC getIC() {
		return this.myIC;
	}

	@Override
	public long getDIC() {
		return this.DIC;
	}

	@Override
	public String getJmeno() {
		if(druhe!=null) return jmeno+" "+druhe+" "+prijmeni;//o.getKrestni()+" "+o.getDruhe());
		else return jmeno+" "+prijmeni;
	}
	@Override
	public OsobaTyp getTyp() {
		return OsobaTyp.FO;
	}

	public void setEmail(String email){
		String pattern="[a-zA-Z0-9.-_]+@[a-zA-Z0-9]+.[a-z]+";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(email);
		if(m.matches()) this.email=email;
		else throw new IllegalArgumentException("Not a valid e-mail address: "+email);
	}
	
	@Override
	public String getEmail(){
		return email;
	}
	
	public void setTelefon(int cislo){
		//TODO validate
		this.telefon=cislo;
	}
	@Override
	public int getTelefon(){
		return telefon;
	}

	public void setFax(int cislo){
		//TODO validate
		this.fax=cislo;
	}
	@Override
	public int getFax() {
		return this.fax;
	}
}
