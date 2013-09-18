package am.bizis.cds.exchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
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
		String result=upload(signed,null,true,ksuri,kpass);
		
		//testovaci rezim
		System.out.print(result);
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
	private static String upload(byte[] data,String mail,boolean test,String ksuri, char[] kspass) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, NullPointerException, NoSuchProviderException, KeyManagementException{
		//url parametry
		List<NameValuePair> urlPar=new ArrayList<NameValuePair>();
		if(mail!=null)urlPar.add(new BasicNameValuePair("email",mail));
		if(test)urlPar.add(new BasicNameValuePair("test","1"));
		
		//HTTP POST request
		ByteArrayEntity bae=new ByteArrayEntity(data,ContentType.APPLICATION_FORM_URLENCODED);
		HttpPost post=new HttpPost(INTERFACE);
		post.setEntity(new UrlEncodedFormEntity(urlPar));
		post.setEntity(bae);

		//Vytvorim SSLSocketFactory rucne a vlozim keystore s certifikatem adisepo
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
			System.out.print(line);
		}
		//DataInputStream in= new DataInputStream(response.getEntity().getContent());
		//byte[] inbuf=new byte[in.available()];
		//in.read(inbuf);
		//in.close();
		return result;
	}
}
