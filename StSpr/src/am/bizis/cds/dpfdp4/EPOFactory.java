package am.bizis.cds.dpfdp4;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import am.bizis.exception.DataUnsetException;

/**
 * Trida EPOFactory vytvori EPO na zaklade dat z uzivatelskeho rozhrani
 * pouziti: new EPOFactory(IFormDataGrab).getEPO(getContent()); vrati XML dokument -> ten poslat na CDS MF
 * @author alex
 * @version 20130909
 */
public class EPOFactory {

	private final IFormDataGrab FORM;
	private final EPO EPOdoc;
	private VetaD d;
	
	/**
	 * Konstruktor vytvori objekt EPO a ulozi si formu
	 * @param form GUI DPDPF
	 */
	public EPOFactory(IFormDataGrab form) {
		this.FORM=form;
		this.EPOdoc=new EPO();
	}
	
	/**
	 * Stane-li se chyba, vypsat uzivateli a do logu
	 * @param user - zprava pro uzivatele
	 * @param log - zprava do logu
	 */
	private void error(String user,String log){
		FORM.showMessage(user);
	}
	
	/**
	 * Neni-li nastavena nepovinna polozka, zaznamename to do logu
	 * @param msg zprava
	 */
	private void note(String msg){
		
	}
	
	/**
	 * Vytvori XML dokument na zaklade predvytvorenych vet
	 * @param content obsah XML dokumentu
	 * @return EPO DPDPF
	 */
	public Document getEPO(IVeta[] content){
		Document doc=null;
		try{
			doc=EPOdoc.makeXML(content);
		}catch(ParserConfigurationException e){
			error("Stala se chyba pri vytvareni XML dokumentu","Stala se chyba pri vytvareni XML dokumentu\n"+e.getMessage()+"\n"+e.getStackTrace().toString());
		}
		return doc;
	}
	
	public IVeta[] getContent(){
		List<IVeta> seznam=new LinkedList<IVeta>();
		try{
			d=new VetaD(FORM.getAudit(), FORM.getCufo(), FORM.getDap_typ(), FORM.getPlnMoc(), FORM.getRok());
			try{
				d.setD_uv(FORM.getDen());
			}catch(DataUnsetException e){
				error("Neni vyplneno, ke kteremu dni se udaje z tabulek vztahuji","Neni vyplneno, ke kteremu dni se udaje z tabulek vztahuji\n"+e.getMessage()+"\n"+e.getStackTrace().toString());
			}
			try{
				d.setDuvodpoddapdpf(FORM.getDuvod(), FORM.getDuvodDate());
			}
			catch(DataUnsetException e){
				note("Neni nastaven kod rozliseni nebo datum");
			}
			try{
				d.setDa_celod13(FORM.getDanCelkem());
			}catch(DataUnsetException e){
				error("Neni vyplnena dan celkem","Neni vyplnena dan celkem\n"+e.getMessage()+"\n"+e.getStackTrace().toString());
			}
			try{
				d.setKc_dazvyhod(FORM.getZvyhodDite());
			}catch(DataUnsetException e){
				note("Danove zvyhodneni na vyzivovane dite neni nastaveno");
			}
			try{
				d.setKc_dztrata(FORM.getDztrata());
			}catch(DataUnsetException e){
				note("Danova ztrata neni nastavena");
			}
			try{
				d.setKc_konkurs(FORM.getZaloha());
			}catch(DataUnsetException e){
				note("Zaplacena danova povinnost neni nastavena");
			}
			try{
				d.setKc_op15_1c(FORM.getManz());
				try{
					d.setManz(FORM.getManzID());
				}catch(DataUnsetException e){
					error("Je uplatnovana sleva na manzela/ku, ale nevime o koho jde","Neni udaj o manzelovi\n"+e.getMessage()+"\n"+e.getStackTrace());
				}
				try{
					d.setM_manz(FORM.getManzMes());
				}catch(DataUnsetException e){
					error("Je uplatnovana sleva na manzela/-ku, ale neni uveden pocet mesicu","Neni pocet mesicu manzelstvi: "+e.getMessage()+"\n"+e.getStackTrace());
				}
				try{
					d.setKc_manztpp(FORM.getManZTP());
				}catch(DataUnsetException e){
					note("Manzel/ka neni drzitelem ZTP/P");
				}
			}catch(DataUnsetException e){
				note("Sleva na manzela/-ku se neuplatnuje");
			}
			try{
				d.setKc_op15_1d(FORM.getInvalid());
			}catch(DataUnsetException e){
				note("Sleva na pozivatele invalidniho duchodu pro invaliditu prvniho nebo druheho stupne se neuplatnuje");
			}
			try{
				d.setKc_op15_1e1(FORM.getInvalid3());
			}catch(DataUnsetException e){
				note("Sleva na pozivatele invalidniho duchodu pro invaliditu tretiho stupne se neuplatnuje");
			}
			try{
				d.setKc_op15_1e2(FORM.getZTP());
			}catch(DataUnsetException e){
				note("Sleva na drzitele pruazu ZTP/P se neuplatnuje");
			}
			try{
				d.setKc_pausal(FORM.getPausal());
			}catch(DataUnsetException e){
				note("Neni zaplacena dan pausalni castkou");
			}
			try{
				d.setKc_pzdp(FORM.getPosledni());
			}catch(DataUnsetException e){
				note("Neni znama posledni dan");
			}
			try{
				d.setKc_sraz367(FORM.getDluhopisy());
			}catch(DataUnsetException e){
				note("Neni srazena dan za statni dluhopisy");
			}
			try{
				d.setKc_sraz3810(FORM.getSraz3810());
			}catch(DataUnsetException e){
				note("Neni srazena dan dle 38f odst. 12");
			}
			try{
				d.setKc_sraz385(FORM.getZajistena());
			}catch(DataUnsetException e){
				note("Neni zajistena dan platcem podle 38e");
			}
			try{
				d.setKc_sraz_rezehp(FORM.getSraz387());
			}catch(DataUnsetException e){
				note("Neni srazena dan podle 36 odst. 7");
			}
			//setKc_stud
			seznam.add(d);
		}catch(ParseException e){
			error("Neplatny rok: "+FORM.getRok(),e.getMessage()+"\n"+e.getStackTrace().toString());
		}
		return (IVeta[])seznam.toArray();
	}

}
