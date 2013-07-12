package am.bizis.cds.ciselnik;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import am.bizis.stspr.fo.Adresa;

public class Obce {

	private static final String URL_CISELNIK="http://adisepo.mfcr.cz/adistc/epo_ciselnik?C=obce";
	private static final File CISELNIK=new File("/home/alex/obce.xml");
	public Obce() {
	}
	
	public static int getObec(Adresa a){
		int obec=0;
		String obecOkres=a.getObec().toUpperCase()+" (OKRES "+a.getOkres().toUpperCase()+")";
		//int psc=a.getPSC();
		Document ciselnik=loadCiselnik(CISELNIK);
		NodeList vety=ciselnik.getElementsByTagName("Veta");
		for(int i=0;i<vety.getLength();i++){
			NamedNodeMap attr=vety.item(i).getAttributes();
			if(attr.getNamedItem("obec_okres").getNodeValue().equals(obecOkres)) obec=Integer.parseInt(attr.getNamedItem("c_obce").getNodeValue());
		}
		return obec;
	}
	
	public static Document loadCiselnik(File fXmlFile){
		Document doc=null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	public static Document downloadCiselnik(){
		Document XMLdoc=null;
		try{
			URL u=new URL(URL_CISELNIK);
			URLConnection c=u.openConnection();
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder db=dbf.newDocumentBuilder();
			XMLdoc=db.parse(c.getInputStream());
			XMLdoc.getDocumentElement().normalize();
			SchemaFactory sf=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			URL schemaURL = new URL("http://adisepo.mfcr.cz/adis/jepo/epo/ciselnik.xsd");
			Schema schema = sf.newSchema(schemaURL);
			Validator validator=schema.newValidator();
			DOMSource source=new DOMSource(XMLdoc);
			validator.validate(source);
		}catch(MalformedURLException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return XMLdoc;
}

}
