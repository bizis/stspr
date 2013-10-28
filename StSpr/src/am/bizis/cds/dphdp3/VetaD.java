/**
 * 
 */
package am.bizis.cds.dphdp3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import am.bizis.cds.IVeta;

/**
 * @author alex
 * Věta D - Záznam nesoucí základní údaje o přiznání k DPH.
 */
public class VetaD implements IVeta {

	private static final String DOKUMENT="DP3";
	private static final String K_ULADIS="DPH";
	private final DateFormat DF=new SimpleDateFormat("dd.MM.yyyy");

	
	private int c_okec,ctvrt,mesic,rok;
	private Date d_zjist, zdobd_do, zdobd_od;
	private Forma dapdph_forma;
	private ZO kod_zo;
	private boolean trans;
	private TPlatce typ_platce;
	
	/**
	 * 
	 */
	public VetaD(Forma forma,int rok, TPlatce platce) {
		this.dapdph_forma=forma;
		this.rok=rok;
		this.typ_platce=platce;
	}

	public void setCokec(int c){
		c_okec=c;
	}
	
	public void setCtvrt(int c){
		if(c>=1&&c<=4) ctvrt=c;
	}
	
	public void setMesic(int m){
		if(m>=1&&m<=12) mesic=m;
	}
	
	public void setRok(int r){
		if(r>=2011) rok=r;
	}
	
	public void setDzjist(Date d){
		d_zjist=d;
	}
	
	public void setZdobdDo(Date d){
		zdobd_do=d;
	}
	
	public void setZdobdOd(Date d){
		zdobd_od=d;
	}
	
	public void setDapDphForma(Forma f){
		dapdph_forma=f;
	}
	
	public void setKodZo(ZO z){
		kod_zo=z;
	}
	
	public void setTrans(boolean trans){
		this.trans=trans;
	}
	
	public void setTypPlatce(TPlatce t){
		typ_platce=t;
	}
	
	/* (non-Javadoc)
	 * @see am.bizis.cds.IVeta#getElement()
	 */
	@Override
	public Element getElement() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element VetaD=EPO.createElement("VetaD");
		VetaD.setAttribute("c_okec", c_okec+"");
		VetaD.setAttribute("d_poddp",DF.format(new Date()));
		VetaD.setAttribute("dapdph_forma",dapdph_forma.kod);
		if(dapdph_forma.equals(Forma.D)||dapdph_forma.equals(Forma.E)){
			VetaD.setAttribute("d_zjist",DF.format(d_zjist));
			
		}
		VetaD.setAttribute("dokument",DOKUMENT);
		VetaD.setAttribute("k_uladis",K_ULADIS);
		VetaD.setAttribute("typ_platce", typ_platce.typ);
		if(!kod_zo.equals(null)) VetaD.setAttribute("kod_zo",kod_zo.kod);
		if(trans) VetaD.setAttribute("trans", "A");
			else VetaD.setAttribute("trans","N");
		if (ctvrt>=1&&ctvrt<=4) VetaD.setAttribute("ctvrt", ctvrt+"");
		else if(mesic>=1&&mesic<=12){
			VetaD.setAttribute("mesic",mesic+"");
		}
		if(rok>=2011) VetaD.setAttribute("rok", rok+"");
		if(!zdobd_do.equals(null)) VetaD.setAttribute("zdobd_do", DF.format(zdobd_do));
		if(!zdobd_od.equals(null)) VetaD.setAttribute("zdobd_od", DF.format(zdobd_od));
		return VetaD;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see am.bizis.cds.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}

}
