package am.bizis.cds.ciselnik;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import am.bizis.stspr.fo.Adresa;

/**
 * Ciselnik Uzemni financni organy
 */
public class UFO {
	
	private static final String URL_CISELNIK="http://adisepo.mfcr.cz/adistc/epo_ciselnik?C=ufo";

	/**
	 * TODO funguje jen pro Prahu
	 * @param a
	 * @return
	 */
	public static int getUFO(Adresa a){
		int c_ufo=0;
		int p=a.getPSC()/100;
		
		//ujezd, seberov, 11, kunratice
		if(p==148||p==149) c_ufo=2011;
		//libus
		else if((p==142&&a.getCast().equals("Libuš"))||p==143) c_ufo=2012;
		//Benice, 22, Dolni mecholupy, kolovraty, kralovice, nedvezi, 15, petrovice, dubec, sterboholy
		//10, kreslice
		else if(p>=100&&p<110){
			int d=p/10;
			c_ufo=2000+d;
		}
		//PRAHA 1..20
		else if(p>=110&&p<200){
			int d=(p/10)-10;
			c_ufo=2000+d;
		}
		else return 0;
		return c_ufo;
	}
	
	private static Document getCiselnik(){
			Document XMLdoc=null;
			try{
				URL u=new URL(URL_CISELNIK);
				URLConnection c=u.openConnection();
				
				DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
				//TODO: validace
				
				DocumentBuilder db=dbf.newDocumentBuilder();
				XMLdoc=db.parse(c.getInputStream());
				XMLdoc.getDocumentElement().normalize();
			}catch(MalformedURLException e){
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
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