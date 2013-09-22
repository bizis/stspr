package am.bizis.cds.dpfdp4;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VetaT implements IVeta {

	private double c_nace, celk_pr_prij7, celk_pr_vyd7, d_obnocin, d_precin, d_ukoncin, d_zahcin, kc_cisobr, kc_hosp_rozd, kc_odpcelk, kc_odpnem, kc_pod_komp, kc_pod_so, kc_pod_vaso, kc_prij7, kc_uhsniz, kc_uhzvys, kc_vyd7, kc_vyd_so, kc_vyd_vaso, kc_zd7p, m_podnik, pr_prij7, pr_sazba, pr_vyd7, uc_soust, vyd7proc;
	public VetaT() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaT=EPO.createElement("VetaT");
		
		
		
		return VetaT;
	}

	@Override
	public int getMaxPocet() {
		return 1;
	}

	@Override
	public String[] getDependency() {
		return null;
	}

}
