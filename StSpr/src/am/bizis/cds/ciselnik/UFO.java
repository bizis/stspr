package am.bizis.cds.ciselnik;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import am.bizis.exception.IllegalCUFOException;

/**
 * Ciselnik Uzemni financni organy
 */
public class UFO {
	
	//private static final String URL_CISELNIK="http://adisepo.mfcr.cz/adistc/epo_ciselnik?C=ufo";
	private static final File CISELNIK=new File("/home/alex/ufo.xml");
	private static final DateFormat DF=new SimpleDateFormat("yyyy-MM-dd");
	
	private static Date getToday() throws ParseException{
		return DF.parse(DF.format(Calendar.getInstance().getTime()));
	}
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
	 * @return cufo
	 * @throws ParseException 
	 * @throws DOMException 
	 * @throws NumberFormatException 
	 */
	public static int getCUFO(String nazu_ufo) throws NumberFormatException, DOMException, ParseException{
		int cufo=-255;
		Document ciselnik=getCiselnik();
		NodeList vety=ciselnik.getElementsByTagName("Veta");
		for(int i=0;i<vety.getLength();i++){
			NamedNodeMap attr=vety.item(i).getAttributes();
			if(attr.getNamedItem("nazu_ufo").getNodeValue().equals(nazu_ufo)&&
					DF.parse(attr.getNamedItem("d_vzniku").getNodeValue()).before(getToday())&&
					DF.parse(attr.getNamedItem("d_zaniku").getNodeValue()).after(getToday())) 
				cufo=Integer.parseInt(attr.getNamedItem("c_ufo").getNodeValue());
			else throw new IllegalCUFOException(nazu_ufo);
		}
		return cufo;
	}
	
	/**
	 * UI dostane pole pro vyber prislusneho UFO
	 * @return pole nazvu UFO
	 * @throws ParseException 
	 * @throws DOMException 
	 * @throws NumberFormatException
	 */
	public static String[] getNazuUFO() throws DOMException,ParseException{
		Document ciselnik=getCiselnik();
		NodeList vety=ciselnik.getElementsByTagName("Veta");
		//String[] pole=new String[vety.getLength()];
		List<String> seznam=new ArrayList<String>();
		for(int i=0;i<vety.getLength();i++){
			NamedNodeMap attr=vety.item(i).getAttributes();
			if(DF.parse(attr.getNamedItem("d_vzniku").getNodeValue()).before(getToday())){
				if(attr.getNamedItem("d_zaniku").getNodeValue().equals("")||DF.parse(attr.getNamedItem("d_zaniku").getNodeValue()).after(getToday())) 
				seznam.add(attr.getNamedItem("nazu_ufo").getNodeValue());
			}
			//jinak se nejedna o aktivni UFO
		}
		return seznam.toArray(new String[seznam.size()]);
	}

}