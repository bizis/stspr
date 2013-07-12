package am.bizis.cds.dpfdp4;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VetaNucet implements IVeta {

	private final String ZVP_NAZ_BANK,ZVP_SPEC_SYMB;
	private final double KC_PREPLATEK;
	private final int ZVP_K_BANK,ZVP_PBU,C_KOMDS;
	
	public VetaNucet(double kc_preplatek, int zvp_k_bank, int zvp_pbu, int c_komds, String zvp_naz_bank, String zvp_spec_symb) {
		this.C_KOMDS=c_komds;
		this.KC_PREPLATEK=kc_preplatek;
		this.ZVP_K_BANK=zvp_k_bank;
		this.ZVP_PBU=zvp_pbu;
		this.ZVP_NAZ_BANK=zvp_naz_bank;
		this.ZVP_SPEC_SYMB=zvp_spec_symb;
	}

	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaN=EPO.createElement("VetaN");
		VetaN.setAttribute("zp_vrac", "U");
		VetaN.setAttribute("kc_preplatek",KC_PREPLATEK+"");
		
		VetaN.setAttribute("c_komds",C_KOMDS+"");
		VetaN.setAttribute("zvp_k_bank", ZVP_K_BANK+"");
		VetaN.setAttribute("zvp_pbu", ZVP_PBU+"");
		VetaN.setAttribute("zvp_naz_bank", ZVP_NAZ_BANK);
		VetaN.setAttribute("zvp_spec_symb", ZVP_SPEC_SYMB);
		
		return VetaN;
	}

}
