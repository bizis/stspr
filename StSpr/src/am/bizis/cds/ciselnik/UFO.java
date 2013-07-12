package am.bizis.cds.ciselnik;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

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

/**
 * Ciselnik Uzemni financni organy
 */
public class UFO {
	
	//private static final String URL_CISELNIK="http://adisepo.mfcr.cz/adistc/epo_ciselnik?C=ufo";
	private static final File CISELNIK=new File("/home/alex/ufo.xml");
	
	private static Document getCiselnik(){
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
	 * Pro zadane UFO vrati cufo do XML priznani
	 * @param nazu_ufo
	 * @return
	 */
	public static int getCUFO(String nazu_ufo){
		int cufo=0;
		Document ciselnik=getCiselnik();
		NodeList vety=ciselnik.getElementsByTagName("Veta");
		for(int i=0;i<vety.getLength();i++){
			NamedNodeMap attr=vety.item(i).getAttributes();
			if(attr.getNamedItem("nazu_ufo").getNodeValue().equals(nazu_ufo)) cufo=Integer.parseInt(attr.getNamedItem("c_ufo").getNodeValue());
		}
		return cufo;
	}
	
	/**
	 * UI dostane pole pro vyber prislusneho UFO
	 * @return
	 */
	public static String[] getNazuUFO(){
		Document ciselnik=getCiselnik();
		NodeList vety=ciselnik.getElementsByTagName("Veta");
		String[] pole=new String[vety.getLength()];
		for(int i=0;i<vety.getLength();i++){
			NamedNodeMap attr=vety.item(i).getAttributes();
			pole[i]=attr.getNamedItem("nazu_ufo").getNodeValue();
		}
		return pole;
	}

}