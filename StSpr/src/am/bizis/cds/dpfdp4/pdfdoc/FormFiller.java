package am.bizis.cds.dpfdp4.pdfdoc;

import java.io.IOException;

import am.bizis.cds.dpfdp4.VetaD;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

public class FormFiller{
	
	private final AcroFields FORM;
	
	public FormFiller(String formURI) throws IOException{
		PdfReader reader=new PdfReader(formURI);
		this.FORM=reader.getAcroFields();
	}

	public void PopulateD(VetaD d) throws DocumentException,IOException{
		FORM.setField("Pisemnost[0].Page1[0].audit[0]",d.getAudit()+"");
		FORM.setField("Pisemnost[0].Page3[0].TableManzelka[0].Row1[0].manz_jmeno[0]", d.getManzJmeno());
		
	}
}
