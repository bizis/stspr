package am.bizis.cds.exchange;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

/**
 * Podepise data (PKCS#7) uzitim BouncyCastle
 * @author alex
 * @version 20130914
 *
 */
public class PKCS7 {
	private final PrivateKey PK;
	public PKCS7(PrivateKey pk){
		this.PK=pk;
	}
	
	//http://www.bouncycastle.org/docs/pkixdocs1.5on/org/bouncycastle/cms/CMSSignedDataGenerator.html
	/**
	 * Podepise pole bajtu
	 * @param data
	 * @param signCert
	 * @return
	 * @throws CertificateEncodingException
	 * @throws OperatorCreationException
	 * @throws CMSException
	 * @throws IOException
	 */
	public byte[] sign(byte[] data, X509CertificateHolder signCert) throws CertificateEncodingException, OperatorCreationException, CMSException, IOException{
		List<X509CertificateHolder> certList = new ArrayList<X509CertificateHolder>();
		CMSTypedData msg = new CMSProcessableByteArray(data);

		certList.add(signCert);

		Store certs = new JcaCertStore(certList);

		CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
		ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(PK);

		gen.addSignerInfoGenerator(
              new JcaSignerInfoGeneratorBuilder(
                   new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
                   .build(sha1Signer, signCert));

		gen.addCertificates(certs);

		CMSSignedData sigData = gen.generate(msg, true);
		return sigData.getEncoded();
	}
}
