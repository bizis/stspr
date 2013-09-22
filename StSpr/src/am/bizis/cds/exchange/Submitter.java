package am.bizis.cds.exchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import am.bizis.exception.ResultException;
import am.bizis.security.crypto.KeyStoreAPI;

/**
 * Odeslani elektronickeho podani na Generalni Financni Reditelstvi
 * prozatimni pouziti: Submitter.submit(new EPOFactory(IFormDataGrab).getEPO(getContent()), <URI keystore>, <heslo keystore>, <alias klice>, <heslo klice>);
 * V KeyStoreAPI je popsano, jak uznavany certifikat (napr. Postsignum) ulozit do KeyStore  
 * toto se zavola mezirozhranim mezi formou a Submitterem - to overi odpoved a vrati zpravu uzivateli
 * @author alex
 * @version 20140918
 */
public class Submitter {

	private static final String INTERFACE="https://adisepo.mfcr.cz/adistc/epo_podani";
	
	/**
	 * Podepise a odesle EPO na server
	 * @param epo XML dokument vytvoreny EPOFactory
	 * @param cert kvalifikovany certifikat s identifikatorem MPSV
	 * @param key soukromy klic - ziskam z am.bizis/security.crypto.KeyStoreAPI (viz komentar tridy)
	 * @param ksuri URI keystore s ulozenym certifikatem adisepo.mfcr.cz - jelikoz prasata pouzivaji I.CA, ktera neni standardni
	 * @param kpass heslo keystore
	 *
	 * @throws TransformerConfigurationException chyba nastaveni prevadece Documentu na String
	 * @throws TransformerException chyba pri vytvareni stringu z Documentu
	 * @throws OperatorCreationException
	 * @throws CMSException chyba pri podpisu dat
	 * @throws IOException chyba pri odesilani
	 * @throws NoSuchProviderException 
	 * @throws NullPointerException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException  
	 */
	public static Document submit(Document epo,X509CertificateHolder cert, PrivateKey key,String ksuri,char[] kpass) throws TransformerConfigurationException, TransformerException, OperatorCreationException, CMSException, IOException, KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, NullPointerException, NoSuchProviderException{
		//udelam z XML dokumentu String
		String doc=convertDoc(epo);
		System.out.println(doc);
		
		//vytvorim PKCS7 podepsanou zpravu
		PKCS7 podani=new PKCS7(key);
		byte[] signed=podani.sign(doc.getBytes(), cert);
		
		//upload
		String result=upload(signed,null,true,ksuri,kpass);
		
		//pokusim se vytvorit XML dokument
		Document res=null;
		try {
			res=convertDoc(result);
		} catch (ParserConfigurationException | SAXException e) {
			//nejde o XML dokument - hodim vyjimku a prilozim vystup
			throw new ResultException(result);
		} /*finally{
			//overeni podpisu
			KeyStore ks=KeyStoreAPI.loadKS(ksuri, kpass);
			X509Certificate mfcr=(X509Certificate) ks.getCertificate("mfcr");
			if(PKCS7.verify(result.getBytes(), mfcr)) System.out.println("Podpis overen");
			else System.out.print("Podpis neoveren");
		}*/
		return res;
	}
	
	/**
	 * Podepise a odesle EPO na server pouzitim klice z KeyStore
	 * 
	 * @param epo XML dokument vytvoreny EPOFactory
	 * @param ksuri URI souboru keystore
	 * @param kspass heslo keystore
	 * @param alias alias klice v keystore
	 * @param pkpass heslo soukromeho klice
	 * @param sslksuri URI keystore s certifikatem adisepo
	 * @param sslkpass heslo keystore s certifikatem adisepo
	 *
	 * @throws IOException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 * @throws CMSException 
	 * @throws TransformerException 
	 * @throws OperatorCreationException 
	 * @throws TransformerConfigurationException
	 * @throws NullPointerException 
	 * @throws NoSuchProviderException 
	 * @throws KeyManagementException 
	 */
	public static Document submit(Document epo,String ksuri,char[] kspass,String alias,char[] pkpass,String sslksuri,char[] sslkpass) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException, UnrecoverableKeyException, TransformerConfigurationException, OperatorCreationException, TransformerException, CMSException, NullPointerException, KeyManagementException, NoSuchProviderException{
		//ziskam KeyStore
		KeyStore ks=KeyStoreAPI.loadKS(ksuri, kspass);
		//ziskam objekt X509Certificate od Oracle
		X509Certificate suncert=KeyStoreAPI.getCertFromKS(ks, alias);
		//prevedu X509Certificate do X509CertificateHolderu od Bouncy Castle
		X509CertificateHolder cert=new JcaX509CertificateHolder(suncert);
		//ziskam privatekey
		PrivateKey pk=KeyStoreAPI.getPKfromKS(ks, alias, pkpass);
		//submitnu epo
		return Submitter.submit(epo,cert,pk,sslksuri,sslkpass);
		
	}
	
	//http://stackoverflow.com/questions/5456680/xml-document-to-string
	/**
	 * Prevede Document na String
	 * @param epo XML Dokument idealne vytvoreny EPOFactory
	 * @return Stringova reprezentace epo
	 * @throws TransformerConfigurationException chyba v nastaveni prevadece
	 * @throws TransformerException chyba pri prevodu
	 */
	private static String convertDoc(Document epo) throws TransformerConfigurationException, TransformerException{
		String doc=new String();
 		TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	    StringWriter writer=new StringWriter();
	    transformer.transform(new DOMSource(epo), new StreamResult(writer));
	    doc=writer.getBuffer().toString();
		return doc;
	}
	
	/**
	 * Prevede String na Document
	 * @param res dokument, vraceny podatelnou EPO
	 * @return dokument, vraceny podatelnou epo jako instance tridy Document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static Document convertDoc(String res) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		return db.parse(new InputSource(new StringReader(res)));
	}
	
	/**
	 * Nahraje data na server GFR MF CR
	 * @param data podepsana data v PKCS7
	 * @param mail volitelne - sem budou zaslany informace o stavu podani
	 * @param test zapnout/vypnout testovaci rezim
	 * @return odpoved serveru
	 * @throws IOException chyba pri prenosu dat
	 * @throws NullPointerException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchProviderException 
	 * @throws KeyManagementException 
	 */
	private static String upload(byte[] data,String mail,boolean test,String ksuri, char[] kspass) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, NullPointerException, IOException, KeyManagementException{
		//Content-Type application/pkcs7-signature
		ContentType pkcs7sig=ContentType.create("application/pkcs7-signature",Charset.forName("UTF-8"));

		//url parametry
		String url=INTERFACE;
		if(test) url+="?test=1";
		
		//HTTP POST request
		ByteArrayEntity bae=new ByteArrayEntity(data,pkcs7sig);
		HttpPost post=new HttpPost(url);
		post.setEntity(bae);

		//VytvorimKeyManagementException SSLSocketFactory rucne a vlozim keystore s certifikatem adisepo
		//http://stackoverflow.com/questions/859111/how-do-i-accept-a-self-signed-certificate-with-a-java-httpsurlconnection
		KeyStore ks=KeyStoreAPI.loadKS(ksuri, kspass);
		TrustManagerFactory tmf=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);
		SSLContext ctx=SSLContext.getInstance("TLS");
		ctx.init(null, tmf.getTrustManagers(), null);
		SSLSocketFactory sslFactory=ctx.getSocketFactory();
		
		//vytvorim HTTP Clienta pres ktereho se pripojim k SSL Socketu, odeslu POST request a ziskam response
		HttpClientBuilder hcb=HttpClientBuilder.create();
		SSLConnectionSocketFactory scsf=new SSLConnectionSocketFactory(sslFactory,new StrictHostnameVerifier());
		hcb.setSSLSocketFactory(scsf);
		HttpClient client=hcb.build();
		HttpResponse response=client.execute(post);
		
		//ctu response do Stringu
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String result="",line;

		while((line=rd.readLine())!=null){
			result+=line;
		}
		return result;
	}
}
