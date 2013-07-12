package am.bizis.stspr;

import am.bizis.stspr.fo.Adresa;

public interface IPodnik {
	int getIC();
	int getDIC();
	String getJmeno();
	OsobaTyp getTyp();
	String getEmail();
	int getTelefon();
	int getFax();
	Adresa getAdresa();
}
