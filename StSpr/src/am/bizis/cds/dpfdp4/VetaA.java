package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.stspr.exception.ConditionException;
import am.bizis.stspr.fo.ISEOOsoba;

public class VetaA implements IVeta {
	
	private final int MAX=99;
	private final String vyzdite_jmeno, vyzdite_prijmeni, vyzdite_r_cislo;
	private final int vyzdite_pocmes, vyzdite_ztpp;
	
	public VetaA(ISEOOsoba vyzdite,int pocmes, int ztpp) throws ConditionException{
		if(vyzdite.hasDruhe()) this.vyzdite_jmeno=vyzdite.getKrestni()+" "+vyzdite.getDruhe();
		else this.vyzdite_jmeno=vyzdite.getKrestni();
		this.vyzdite_prijmeni=vyzdite.getPrijmeni();
		this.vyzdite_r_cislo=vyzdite.getRodneCislo().toString();//TODO: textova reprezentace, nutno zachovat vodici nuly
		if((pocmes<0)||(pocmes>12)) throw new ConditionException("Pocet mesicu musi byt 0 az 12");
		this.vyzdite_pocmes=pocmes;
		if((ztpp<0)||(ztpp>12)) throw new ConditionException("Pocet mesicu musi byt 0 az 12");
		if(ztpp>pocmes) ztpp=pocmes;
		this.vyzdite_ztpp=ztpp;
	}

	@Override
	public Element getElement() throws ParserConfigurationException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaA=EPO.createElement("VetaA");
		VetaA.setAttribute("vyzdite_jmeno", vyzdite_jmeno);
		VetaA.setAttribute("vyzdite_prijmeni",vyzdite_prijmeni);
		VetaA.setAttribute("vyzdite_r_cislo", vyzdite_r_cislo);
		VetaA.setAttribute("vyzdite_pocmes",vyzdite_pocmes+"");
		if(vyzdite_ztpp!=0) VetaA.setAttribute("vyzdite_ztpp", vyzdite_ztpp+"");
		return VetaA;
	}

	@Override
	public int getMaxPocet() {
		return MAX;
	}

}