package am.bizis.cds.exchange;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.w3c.dom.Document;

import am.bizis.security.crypto.KeyStoreAPI;

/**
 * Odeslani elektronickeho podani na Generalni Financni Reditelstvi
 * prozatimni pouziti: Submitter.submit(new EPOFactory(IFormDataGrab).getEPO(getContent()), <URI keystore>, <heslo keystore>, <alias klice>, <heslo klice>);
 * V KeyStoreAPI je popsano, jak uznavany certifikat (napr. Postsignum) ulozit do KeyStore  
 * toto se zavola na kliknuti cudliku >odeslat< (nebo podobne) v GUI
 * @author alex
 * @version 20140917
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
	public static void submit(Document epo,X509CertificateHolder cert, PrivateKey key,String ksuri,char[] kpass) throws TransformerConfigurationException, TransformerException, OperatorCreationException, CMSException, IOException, KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, NullPointerException, NoSuchProviderException{
		//udelam z XML dokumentu String
		String doc=convertDoc(epo);
		
		//vytvorim PKCS7 podepsanou zpravu
		PKCS7 podani=new PKCS7(key);
		byte[] signed=podani.sign(doc.getBytes(), cert);
		
		//upload
		byte[] result=upload(signed,null,true,ksuri,kpass);
		
		//testovaci rezim
		System.out.print(new String(result,Charset.forName("UTF-8")));
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
	public static void submit(Document epo,String ksuri,char[] kspass,String alias,char[] pkpass,String sslksuri,char[] sslkpass) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException, UnrecoverableKeyException, TransformerConfigurationException, OperatorCreationException, TransformerException, CMSException, NullPointerException, KeyManagementException, NoSuchProviderException{
		//ziskam KeyStore
		KeyStore ks=KeyStoreAPI.loadKS(ksuri, kspass);
		//ziskam objekt X509Certificate od Oracle
		X509Certificate suncert=KeyStoreAPI.getCertFromKS(ks, alias);
		//prevedu X509Certificate do X509CertificateHolderu od Bouncy Castle
		X509CertificateHolder cert=new JcaX509CertificateHolder(suncert);
		//ziskam privatekey
		PrivateKey pk=KeyStoreAPI.getPKfromKS(ks, alias, pkpass);
		//submitnu epo
		Submitter.submit(epo,cert,pk,sslksuri,sslkpass);
		
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
	private static byte[] upload(byte[] data,String mail,boolean test,String ksuri, char[] kspass) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, NullPointerException, NoSuchProviderException, KeyManagementException{
		String urlparams="";
		if(mail!=null) urlparams+="&email="+mail;
		if(test) urlparams+="&test=1";
		
		//Vytvorim SSLSocketFactory rucne a vlozim keystore s certifikatem adisepo
		//http://stackoverflow.com/questions/859111/how-do-i-accept-a-self-signed-certificate-with-a-java-httpsurlconnection
		KeyStore ks=KeyStoreAPI.loadKS(ksuri, kspass);
		TrustManagerFactory tmf=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);
		SSLContext ctx=SSLContext.getInstance("TLS");
		ctx.init(null, tmf.getTrustManagers(), null);
		SSLSocketFactory sslFactory=ctx.getSocketFactory();
		
		//navazu spojeni
		URL u=new URL(INTERFACE);
		HttpsURLConnection c=(HttpsURLConnection)u.openConnection();
		c.setSSLSocketFactory(sslFactory);
		c.setDoOutput(true);
		c.setDoInput(true);
		c.setRequestMethod("POST");
		c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		c.setRequestProperty("charset", "utf-8");
		c.setRequestProperty("Content-Length", "" + Integer.toString(urlparams.getBytes().length+data.length));
		c.setUseCaches (false);

		DataOutputStream out=new DataOutputStream(c.getOutputStream());
		out.writeBytes(urlparams);
		out.write(data);
		out.flush();
		out.close();
		
		DataInputStream in=new DataInputStream(c.getInputStream());
		int contentLength=in.available();
		byte[] inbuf=new byte[contentLength];
		in.read(inbuf);
		in.close();

		return inbuf;
	}
}
