package am.bizis.cds.ciselnik;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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

import am.bizis.exception.IllegalCUFOException;

/**
 * Ciselnik cinnosti (okec)
 * @author alex
 *
 */
public class Okec {

	private static final File CISELNIK=new File("/home/alex/okec.xml");
	
	public Okec() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Nacte ciselnik ze souboru
	 * @return XML dokument - ciselnik cinnosti
	 */
	private Document getCiselnik(){
		Document XMLdoc=null;
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			XMLdoc = db.parse(CISELNIK);
			XMLdoc.getDocumentElement().normalize();
			SchemaFactory sf=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			File schemaURL = new File("/home/alex/ciselnik.xsd");
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
	
	/**
	 * Vrati pole Stringu s nazvy cinnosti
	 * @return nazvy cinnosti pro zobrazeni v GUI - uzivatel si vybere, metoda get Cnace pro dany String vrati c_nace
	 */
	public String[] getNazvy(){
		Document ciselnik=getCiselnik();
		NodeList vety=ciselnik.getElementsByTagName("Veta");
		List<String> seznam=new ArrayList<String>();
		for(int i=0;i<vety.getLength();i++){
			NamedNodeMap attr=vety.item(i).getAttributes();
			seznam.add(attr.getNamedItem("naz_nace").getNodeValue());
		}
		return seznam.toArray(new String[seznam.size()]);
	}
	
	/**
	 * Vrati c_nace z ciselniku cinnosti pro dany String
	 * @param nazev odpovida polozce z pole vraceneho getNazvy()
	 * @return c_nace
	 */
	public int getCnace(String nazev){
		int cnace=-255;
		Document ciselnik=getCiselnik();
		NodeList vety=ciselnik.getElementsByTagName("Veta");
		for(int i=0;i<vety.getLength();i++){
			NamedNodeMap attr=vety.item(i).getAttributes(); 
			if(attr.getNamedItem("nazu_ufo").getNodeValue().equals(nazev)) 
			cnace=Integer.parseInt(attr.getNamedItem("c_nace").getNodeValue());
		}
		return cnace;
	}

}
