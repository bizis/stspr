package am.bizis.cds.ciselnik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import am.bizis.stspr.fo.Adresa;

/**
 * Ciselnik Uzemni financni organy
 * 
 * Pri vytvoreni:
 * Stahne seznam ciselniku, nabidne pole financnich uradu
 * k dispozici metoda, ktera pro zvoleny UFO vrati c_ufo pro DPFDP4
 */
public class UFO {
	
	private static final String URL_CISELNIK="http://adisepo.mfcr.cz/adistc/epo_ciselnik?C=ufo";
	private static final String URL_CDS_UFO_LOOKUP="https://cds.mfcr.cz/cps/rde/xchg/cds/xsl/vyhledavani_ufo.html/papp/cds_general/?year=0";
	
	private final Document ciselnik;
	private final String sidlo;
	
	/**
	 * Stahne XML z http://adisepo.mfcr.cz/adistc/epo_ciselnik?C=ufo
	 * 		NEZvaliduje proti http://adisepo.mfcr.cz/adis/jepo/epo/ciselnik.xsd
	 * 		NEPokud neni validni zvaliduje proti http://adisepo.mfcr.cz/adis/jepo/epo/chyby.xsd
	 * 		NE-> Vrati chybu
	 * Pokud je ciselnik validni, nacte jej do pameti
	 */
	public UFO(Adresa a){
		//this.ciselnik=getCiselnik();
		this.ciselnik=null;
		if(a.getObec().equals("Hlavní město Praha")) this.sidlo="Praha";
		else this.sidlo=a.getObec();
		getUFOname(sidlo);
	}
	
	private Document getCiselnik(){
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
	
	public Document getDocument(){
		return ciselnik;
	}
	
	private String getUFOname(String sidlo){
		String name=null;
		try {
			URL u=new URL(URL_CDS_UFO_LOOKUP);
			HttpURLConnection c=(HttpURLConnection) u.openConnection();
			c.setRequestMethod("POST");
			c.setDoOutput(true);
			c.setRequestProperty("txtSPD", sidlo);
			BufferedReader r=new BufferedReader(new InputStreamReader(c.getInputStream()));
			String line,content="";
			while((line = r.readLine())!=null){
				//content+=line;
				System.out.print(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

}
