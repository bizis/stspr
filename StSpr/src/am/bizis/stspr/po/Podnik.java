package am.bizis.stspr.po;

import am.bizis.stspr.IC;
import am.bizis.stspr.IPodnik;
import am.bizis.stspr.OsobaTyp;
import am.bizis.stspr.fo.Adresa;

public class Podnik implements IPodnik {

	public Podnik() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IC getIC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDIC() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJmeno() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OsobaTyp getTyp() {
		return OsobaTyp.PO;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTelefon() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getFax() {
		return 0;
	}

	@Override
	public Adresa getAdresa() {
		// TODO Auto-generated method stub
		return null;
	}

}
