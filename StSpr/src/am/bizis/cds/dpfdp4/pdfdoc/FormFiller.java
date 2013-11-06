package am.bizis.cds.dpfdp4.pdfdoc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import am.bizis.cds.IVeta;
import am.bizis.cds.dpfdp4.VetaA;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class FormFiller{
	
	public static void populate(String formURI,String resultURI,IVeta[] vety) throws IOException, DocumentException{
		PdfReader reader=new PdfReader(formURI);
		PdfStamper stamper=new PdfStamper(reader, new FileOutputStream(resultURI));
		AcroFields form=stamper.getAcroFields();
		Set<String> fields=form.getFields().keySet();
		for(IVeta v:vety){
			if(v.getClass().getPackage().equals(VetaA.class.getPackage())){//je to sice nehezke, ale zabrani to pouziti jineho formulare
				for (String key:fields){
					Matcher ma=Pattern.compile("Pisemnost\\[0\\]\\.Page[0-9]\\[0\\]\\.([a-z0-9_])\\[0\\]").matcher(key);
					if (ma.matches()){
						String element=ma.group(0);
						if(v.getAttrs().containsKey(element)) form.setField(element, v.getAttrs().get(element));
						else System.err.println("Nenalezeno: "+element);
					}else System.err.println("Nekorektni radka vstupu: "+key);
				}
			} else System.err.println("Toto se aplikuje pouze na tridy z balicku "+VetaA.class.getPackage().getName());
		}
		reader.close();
		stamper.close();
	}
}
