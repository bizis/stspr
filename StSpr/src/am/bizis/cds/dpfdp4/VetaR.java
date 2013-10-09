package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * VetaR - zaznam o priloze
 * @author alex
 *
 */
public class VetaR implements IVeta {

	private final KodSekce kodSekce;
	private final int poradi;
	private final char[] t_prilohy;
	
	/**
	 * Vytvori element VetaR - Zaznam o priloze
	 * @param kodSekce Sekce textové přílohy
	 * Označení oddílu, ke kterému se příloha vztahuje: O - obecná příloha, D - bližší specifikace důvodů podání DoDAP
	 * @param poradi Číslo řádku přílohy
	 * @param text 	Jeden řádek textové přílohy, max. 72 znaků
	 */
	public VetaR(KodSekce kodSekce,int poradi, String text){
		this.kodSekce=kodSekce;
		this.poradi=poradi;
		this.t_prilohy=text.substring(0, 72).toCharArray();//max. 72 znaku
	}
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaR=EPO.createElement("VetaR");
		VetaR.setAttribute("kod_sekce", kodSekce.kod);
		VetaR.setAttribute("poradi", poradi+"");
		VetaR.setAttribute("t_prilohy",new String(t_prilohy));
		return VetaR;
	}

	@Override
	public int getMaxPocet() {
		return 999;
	}

	@Override
	public String[] getDependency() {
		return null;
	}

}
