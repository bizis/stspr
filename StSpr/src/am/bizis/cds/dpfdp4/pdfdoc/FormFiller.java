package am.bizis.cds.dpfdp4.pdfdoc;

import java.io.FileOutputStream;
import java.io.IOException;

import am.bizis.cds.dpfdp4.VetaD;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class FormFiller{
	
	private final AcroFields FORM;
	private PdfStamper stamper;
	private PdfReader reader;
	
	public FormFiller(String formURI,String resultURI) throws IOException,DocumentException{
		PdfReader reader=new PdfReader(formURI);
		PdfStamper stamper=new PdfStamper(reader, new FileOutputStream(resultURI));
		this.FORM=stamper.getAcroFields();
	}
	
	@Override
	public void finalize() throws Throwable{
		reader.close();
		stamper.close();
		super.finalize();
	}

	public void PopulateD(VetaD d) throws DocumentException,IOException{
		FORM.setField("Pisemnost[0].Page1[0].audit[0]",d.getAudit()+"");
		//c_ufo_cil
		FORM.setField("Pisemnost[0].Page1[0].d_duvpod[0]",d.getDduvpod());
		FORM.setField("Pisemnost[0].Page4[0].d_uv[0]",d.getDuv());
		FORM.setField("Pisemnost[0].Page1[0].d_zjist[0]", d.getDzjist());
		FORM.setField("Pisemnost[0].Page3[0].TableManzelka[0].Row1[0].manz_jmeno[0]", d.getManzJmeno());
		FORM.setField("Pisemnost[0].Page2[0].Table58-61[0].Row60[0].da_celod13[0]",d.getDaCelod13());
	}
}
