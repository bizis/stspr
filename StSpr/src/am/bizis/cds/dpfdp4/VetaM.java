/**
 * 
 */
package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Věta M - Příloha pro poplatníky uplatňující odčitatelnou položku dle § 34 odst.1 zákona
 * @author alex
 */
public class VetaM implements IVeta {

	private String prilztr_sl1;
	private double prilztr_sl2, prilztr_sl3, prilztr_sl4, prilztr_sl5;
	/**
	 * 
	 */
	public VetaM() {
		// zadne povinne polozky
	}
	
	/**
	 * @param prilztr_sl1 Zdaňovací období, ve kterém daňová ztráta vznikla
	 */
	public void setPrilztrSl1(String prilztr_sl1){
		this.prilztr_sl1=prilztr_sl1;
	}
	
	/**
	 * @param prilztr_sl2 Celková výše daňové ztráty vyměřené (vzniklé) nebo přiznané za zdaňovací období uvedené ve sl. 1
	 */
	public void setPrilztrSl2(double prilztr_sl2){
		this.prilztr_sl2=prilztr_sl2;
	}
	
	/**
	 * @param prilztr_sl3 Část daňové ztráty odečtené již v předcházejících zdaňovacích obdobích
	 */
	public void setPrilztrSl3(double prilztr_sl3){
		this.prilztr_sl3=prilztr_sl3;
	}
	
	/**
	 * @param prilztr_sl4 Část daňové ztráty uplatněné v tomto zdaňovacím období
	 */
	public void setPrilztrSl4(double prilztr_sl4){
		this.prilztr_sl4=prilztr_sl4;
	}
	
	/**
	 * @param prilztr_sl5 Část daňové ztráty, kterou lze odečíst v následujících zdaňovacích obdobích
	 */
	public void setPrilztrSl5(double prilztr_sl5){
		this.prilztr_sl5=prilztr_sl5;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaR=EPO.createElement("VetaR");
		VetaR.setAttribute("prilztr_sl1",prilztr_sl1);
		VetaR.setAttribute("prilztr_sl2",prilztr_sl2+"");
		VetaR.setAttribute("prilztr_sl3", prilztr_sl3+"");
		VetaR.setAttribute("prilztr_sl4",prilztr_sl4+"");
		VetaR.setAttribute("prilztr_sl5", prilztr_sl5+"");
		return VetaR;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 8;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}

}
