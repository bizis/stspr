package am.bizis.stspr.fo;

public class OSVC extends ISEOOsoba implements IPodnik {

	private final int IC,DIC;
	public OSVC(String jmeno, String prijmeni, RodneCislo rc,
			ISEOMistoOkres narozeni, int ic) {
		super(jmeno, prijmeni, rc, narozeni);
		this.IC=ic;
		this.DIC=ic;
	}

	public OSVC(String jmeno, String druhe, String prijmeni, RodneCislo rc,
			ISEOMistoOkres narozeni, int ic) {
		super(jmeno, druhe, prijmeni, rc, narozeni);
		this.IC=ic;
		this.DIC=ic;
	}

	public OSVC(String jmeno, String druhe, String rodne, String prijmeni,
			RodneCislo rc, ISEOMistoOkres narozeni, int ic) {
		super(jmeno, druhe, rodne, prijmeni, rc, narozeni);
		this.IC=ic;
		this.DIC=ic;
	}
	
	public OSVC(String jmeno, String druhe, String rodne, String prijmeni,
			RodneCislo rc, ISEOMistoOkres narozeni, int ic, int dic) {
		super(jmeno,druhe,rodne,prijmeni,rc,narozeni);
		this.IC=ic;
		this.DIC=dic;
	}

	@Override
	public int getIC() {
		return this.IC;
	}

	@Override
	public int getDIC() {
		return this.DIC;
	}

	@Override
	public String getJmeno() {
		return super.jmeno+" "+super.prijmeni;
	}

	@Override
	public OsobaTyp getTyp() {
		return OsobaTyp.FO;
	}

}
