package am.bizis.security.crypto;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Umozni uzivateli vytahnout PrivateKey a X509Certificate ze zadaneho KeyStore
 * Postup:
 * 1) ziskat uznavany certifikat - napriklad na poste
 * 2) vytvorit keystore: $ keytool -v -importkeystore -srckeystorePrivate <cesta k cert_sign.p12> -srcstoretype PKCS12 -deststoretype <adresa vytvareneho jks> -deststoretype JKS
 * 3) zjistit alias: $ keytool -list -v -keystore <adresa vytvoreneho jks>|grep Alias (signkey#1)
 * 4) KeyStore ks=loadKS(<adresa vytvoreneho jks>, <heslo>);
 * 5a) PrivateKey pk=getPKfromKS(ks,<pkalias, napr."signkey#1">,<heslo klice ?>)
 * 5b) X509Certificate cert=getCertFromKS(ks,<pkalias, napr."signkey#1">)
 * @author alex
 * @version 20130917
 */
public class KeyStoreAPI {

	/**
	 * Nacte KeyStore ze souboru
	 * 
	 * @param URI keystore
	 * @param pass heslo
	 * @return KeyStore
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyStoreException
	 */
	public static KeyStore loadKS(String URI,char[] pass) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException{
		KeyStore ks=KeyStore.getInstance("JKS");
		FileInputStream ksfis=new FileInputStream(URI);
		BufferedInputStream ksbufin=new BufferedInputStream(ksfis);
		ks.load(ksbufin,pass);
		return ks;
	}
	
	/**
	 * Ziska PrivateKey z KeyStore
	 * http://docs.oracle.com/javase/tutorial/security/apisign/enhancements.html
	 * 
	 * @param ks Loaded keystore
	 * @param pkalias Alias soukromeho klice
	 * @param pass Heslo keystore
	 * 
	 * @return soukromy klic pro dany alias
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	public static PrivateKey getPKfromKS(KeyStore ks,String pkalias,char[] pass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		return (PrivateKey) ks.getKey(pkalias, pass);
	}
	
	/**
	 * Ziska certifikat z KeyStore
	 * http://stackoverflow.com/questions/16970302/reading-pkcs12-certificate-informatio
	 * @param ks Loaded keystore
	 * @param pkalias Alias klice
	 * @return certifikat pro dany alias
	 * @throws KeyStoreException
	 */
	public static X509Certificate getCertFromKS(KeyStore ks, String pkalias) throws KeyStoreException{
		return (X509Certificate) ks.getCertificate(pkalias);
	}
}
