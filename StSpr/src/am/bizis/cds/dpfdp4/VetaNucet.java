package am.bizis.cds.dpfdp4;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.IVeta;

/**
 * Vytvori element VetaN pisemnosti DPFDP4 - Zadost o vraceni preplatku, pro vraceni preplatku na ucet
 * popis polozek: https://adisepo.mfcr.cz/adistc/adis/idpr_pub/epo2_info/popis_struktury_detail.faces?zkratka=DPFDP4#N
 * @author alex
 */
public class VetaNucet extends VetaN implements IVeta{

	private final String ZVP_NAZ_BANK,ZVP_SPEC_SYMB;
	private final int ZVP_K_BANK,ZVP_PBU,C_KOMDS;
	
	/**
	 * Vytvori vetu N pro vraceni preplatku na ucet s povinnymi polozkami
	 * @param d veta D, ze ktere bereme vysi preplatku
	 * @param zvp_k_bank kod banky
	 * @param zvp_pbu Uveďte předčíslí bankovního účtu. Položka obsahuje kritickou kontrolu: předčíslí bankovního účtu musí být ve správném formátu
	 * @param c_komds Uveďte číslo komitenta. Položka obsahuje kritickou kontrolu: číslo komitenta musí být ve správném formátu
	 * @param zvp_naz_bank vedený u
	 * @param zvp_spec_symb specifický symbol
	 */
	public VetaNucet(VetaD d, int zvp_k_bank, int zvp_pbu, int c_komds, String zvp_naz_bank, String zvp_spec_symb) {
		//if(kc_preplatek<=0) throw new IllegalArgumentException("Částka uvedená v Žádosti o vrácení přeplatku musí být větší než 0.");
		super(d);
		this.C_KOMDS=c_komds;
		this.ZVP_K_BANK=zvp_k_bank;
		this.ZVP_PBU=zvp_pbu;
		this.ZVP_NAZ_BANK=zvp_naz_bank;
		this.ZVP_SPEC_SYMB=zvp_spec_symb;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaN=EPO.createElement(super.toString());
		VetaN.setAttribute("zp_vrac", "U");
		VetaN.setAttribute("kc_preplatek",super.getKcPreplatek()+"");
		
		VetaN.setAttribute("c_komds",C_KOMDS+"");
		VetaN.setAttribute("zvp_k_bank", ZVP_K_BANK+"");
		VetaN.setAttribute("zvp_pbu", ZVP_PBU+"");
		VetaN.setAttribute("zvp_naz_bank", ZVP_NAZ_BANK);
		VetaN.setAttribute("zvp_spec_symb", ZVP_SPEC_SYMB);
		
		return VetaN;
	}

}
