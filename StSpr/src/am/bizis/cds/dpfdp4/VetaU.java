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
 * @author alex
 * Tabulka pro poplatníky vedoucí daňovou evidenci
 */
public class VetaU implements IVeta {

	private double kc_dpfmz02, kc_dpfmz03, kc_dpfmz04, kc_dpfmz05a, kc_dpfmz06, kc_dpfmz08, kc_dpfmz10, kc_dpfmz11, kc_dpfmz18, kc_z_dpfmz02, kc_z_dpfmz03, kc_z_dpfmz04, kc_z_dpfmz05a, kc_z_dpfmz06, kc_z_dpfmz08, kc_z_dpfmz10, kc_z_dpfmz11;
	/**
	 * 
	 */
	public VetaU() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaU=EPO.createElement("VetaU");
		VetaU.setAttribute("kc_dpfmz02", kc_dpfmz02+"");
		VetaU.setAttribute("kc_dpfmz03",kc_dpfmz03+"");
		VetaU.setAttribute("kc_dpfmz04",kc_dpfmz04+"");
		VetaU.setAttribute("kc_dpfmz05a", kc_dpfmz05a+"");
		VetaU.setAttribute("kc_dpfmz06", kc_dpfmz06+"");
		VetaU.setAttribute("kc_dpfmz08",kc_dpfmz08+"");
		VetaU.setAttribute("kc_dpfmz10",kc_dpfmz10+"");
		VetaU.setAttribute("kc_dpfmz11",kc_dpfmz11+"");
		VetaU.setAttribute("kc_dpfmz18",kc_dpfmz18+"");
		VetaU.setAttribute("kc_z_dpfmz02",kc_z_dpfmz02+"");
		VetaU.setAttribute("kc_z_dpfmz03",kc_z_dpfmz03+"");
		VetaU.setAttribute("kc_z_dpfmz04",kc_z_dpfmz04+""); 
		VetaU.setAttribute("kc_z_dpfmz05a",kc_z_dpfmz05a+""); 
		VetaU.setAttribute("kc_z_dpfmz06",kc_z_dpfmz06+""); 
		VetaU.setAttribute("kc_z_dpfmz08",kc_z_dpfmz08+""); 
		VetaU.setAttribute("kc_z_dpfmz10",kc_z_dpfmz10+""); 
		VetaU.setAttribute("kc_z_dpfmz11",kc_z_dpfmz11+"");
		return VetaU;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}

	/**
	 * @param kc_z_dpfmz05a the kc_z_dpfmz05a to set
	 */
	public void setKc_z_dpfmz05a(double kc_z_dpfmz05a) {
		this.kc_z_dpfmz05a = kc_z_dpfmz05a;
	}

	/**
	 * @param kc_dpfmz04 the kc_dpfmz04 to set
	 */
	public void setKc_dpfmz04(double kc_dpfmz04) {
		this.kc_dpfmz04 = kc_dpfmz04;
	}

	/**
	 * @param kc_z_dpfmz04 the kc_z_dpfmz04 to set
	 */
	public void setKc_z_dpfmz04(double kc_z_dpfmz04) {
		this.kc_z_dpfmz04 = kc_z_dpfmz04;
	}

	/**
	 * @param kc_dpfmz10 the kc_dpfmz10 to set
	 */
	public void setKc_dpfmz10(double kc_dpfmz10) {
		this.kc_dpfmz10 = kc_dpfmz10;
	}

	/**
	 * @param kc_dpfmz08 the kc_dpfmz08 to set
	 */
	public void setKc_dpfmz08(double kc_dpfmz08) {
		this.kc_dpfmz08 = kc_dpfmz08;
	}

	/**
	 * @param kc_dpfmz03 the kc_dpfmz03 to set
	 */
	public void setKc_dpfmz03(double kc_dpfmz03) {
		this.kc_dpfmz03 = kc_dpfmz03;
	}

	/**
	 * @param kc_dpfmz18 the kc_dpfmz18 to set
	 */
	public void setKc_dpfmz18(double kc_dpfmz18) {
		this.kc_dpfmz18 = kc_dpfmz18;
	}

	/**
	 * @param kc_z_dpfmz02 the kc_z_dpfmz02 to set
	 */
	public void setKc_z_dpfmz02(double kc_z_dpfmz02) {
		this.kc_z_dpfmz02 = kc_z_dpfmz02;
	}

	/**
	 * @param kc_z_dpfmz08 the kc_z_dpfmz08 to set
	 */
	public void setKc_z_dpfmz08(double kc_z_dpfmz08) {
		this.kc_z_dpfmz08 = kc_z_dpfmz08;
	}
	
	/**
	 * @param kc_z_dpfmz06 the kc_z_dpfmz06 to set
	 */
	public void setKc_z_dpfmz06(double kc_z_dpfmz06) {
		this.kc_z_dpfmz06 = kc_z_dpfmz06;
	}
	
	/**
	 * @param kc_dpfmz05a the kc_dpfmz05a to set
	 */
	public void setKc_dpfmz05a(double kc_dpfmz05a) {
		this.kc_dpfmz05a = kc_dpfmz05a;
	}
	
	/**
	 * @param kc_dpfmz11 the kc_dpfmz11 to set
	 */
	public void setKc_dpfmz11(double kc_dpfmz11) {
		this.kc_dpfmz11 = kc_dpfmz11;
	}

	/**
	 * @param kc_z_dpfmz11 the kc_z_dpfmz11 to set
	 */
	public void setKc_z_dpfmz11(double kc_z_dpfmz11) {
		this.kc_z_dpfmz11 = kc_z_dpfmz11;
	}

	/**
	 * @param kc_z_dpfmz10 the kc_z_dpfmz10 to set
	 */
	public void setKc_z_dpfmz10(double kc_z_dpfmz10) {
		this.kc_z_dpfmz10 = kc_z_dpfmz10;
	}
	
	/**
	 * @param kc_z_dpfmz03 the kc_z_dpfmz03 to set
	 */
	public void setKc_z_dpfmz03(double kc_z_dpfmz03) {
		this.kc_z_dpfmz03 = kc_z_dpfmz03;
	}

	/**
	 * @param kc_dpfmz06 the kc_dpfmz06 to set
	 */
	public void setKc_dpfmz06(double kc_dpfmz06) {
		this.kc_dpfmz06 = kc_dpfmz06;
	}

	/**
	 * @param kc_dpfmz02 the kc_dpfmz02 to set
	 */
	public void setKc_dpfmz02(double kc_dpfmz02) {
		this.kc_dpfmz02 = kc_dpfmz02;
	}

}
