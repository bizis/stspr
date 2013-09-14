package am.bizis.cds.exchange;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.w3c.dom.Document;

public class Submitter {

	private static final String INTERFACE="https://adisepo.mfcr.cz/adistc/epo_podani";
	
	public static void submit(Document epo,X509CertificateHolder cert, PrivateKey key) throws TransformerConfigurationException, TransformerException, CertificateEncodingException, OperatorCreationException, CMSException, IOException{
		//udelam z XML dokumentu String
		String doc=convertDoc(epo);
		
		//vytvorim PKCS7 podepsanou zpravu
		PKCS7 podani=new PKCS7(key);
		byte[] signed=podani.sign(doc.getBytes(), cert);
		
		//upload
		byte[] result=upload(signed,null,true);
		
		//testovaci rezim
		System.out.print(new String(result,Charset.forName("UTF-8")));
	}
	
	
	//http://stackoverflow.com/questions/5456680/xml-document-to-string
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
	
	private static byte[] upload(byte[] data,String mail,boolean test) throws IOException{
		String urlparams="";
		if(mail!=null) urlparams+="&email="+mail;
		if(test) urlparams+="&test=1";
		
		URL u=new URL(INTERFACE);
		URLConnection c=u.openConnection();
		c.setDoOutput(true);
		c.setDoInput(true);
		
		DataInputStream in=new DataInputStream(c.getInputStream());
		DataOutputStream out=new DataOutputStream(c.getOutputStream());
		
		out.writeBytes(urlparams);
		out.write(data);
		
		int contentLength=in.available();
		byte[] inbuf=new byte[contentLength];
		in.read(inbuf);
		in.close();
		
		out.flush();
		out.close();
		return inbuf;
	}
}
