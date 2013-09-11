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
 * 
 * @author alex
 * @version 20130911
 */

/*
 * Zvlastnosti kodu:
 * Jelikoz puvodni implementace metody getContent() zahrnovala dlouhou nudli 
 * try{}catch(){}finally{
 * 	try{}catch(){}finally{
 * 		try{}catch(){}finally{
 * 			atd.
 * 		}
 * 	}
 * }
 * zpusobovalo to chybu, kdy JVM povoluje delku kodu metody pouze 65kB
 * problem byl vyresen tak, ze kazde finally vola novou metodu, ktera obsahuje try{}catch(){}finally{}
 * toto reseni vsak teoreticky muze zpusobit preteceni zasobniku volani
 * pokud toto nastane, lze spojit nekolik try{}catch(){}finally{try{}catch(){}finally{}} do sebe a nalezt optimalni 
 * kompromis mezi delkou stacku volani a delkou jednotlivych metod 
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
		}catch(ParseException e){
			error("Neplatny rok: "+FORM.getRok(),e.getMessage()+"\n"+e.getStackTrace().toString());
		}finally{
			d=set0(d);
			seznam.add(d);
		}
		return (IVeta[])seznam.toArray();
	}
	
	private VetaD set0(VetaD d){
		try{
			d.setD_uv(FORM.getDen());
		}catch(DataUnsetException e){
			error("Neni vyplneno, ke kteremu dni se udaje z tabulek vztahuji",e.getMessage()+"\n"+e.getStackTrace().toString());
		}
		finally {
			d=set1(d);
		}
		return d;
	}
	
	private VetaD set1(VetaD d){
		try{
			d.setDuvodpoddapdpf(FORM.getDuvod(), FORM.getDuvodDate());
		}
		catch(DataUnsetException e){
			note("Neni nastaven kod rozliseni nebo datum");
		}
		finally {
			d=set2(d);
		}
		return d;
	}
	
	private VetaD set2(VetaD d){
		try{
			d.setDa_celod13(FORM.getDanCelkem());
		}catch(DataUnsetException e){
			error("Neni vyplnena dan celkem",e.getMessage()+"\n"+e.getStackTrace().toString());
		}
		finally{
			d=set3(d);
		}
		return d;
	}
	
	private VetaD set3(VetaD d){
		try{
			d.setKc_dazvyhod(FORM.getZvyhodDite());
		}catch(DataUnsetException e){
			note("Danove zvyhodneni na vyzivovane dite neni nastaveno");
		}
		finally{
			d=set4(d);
		}
		return d;
	}
	
	private VetaD set4(VetaD d){
		try{
			d.setKc_dztrata(FORM.getDztrata());
		}catch(DataUnsetException e){
			note("Danova ztrata neni nastavena");
		}
		finally{
			d=set5(d);
		}
		return d;
	}
	
	private VetaD set5(VetaD d){
		try{
			d.setKc_konkurs(FORM.getZaloha());
		}catch(DataUnsetException e){
			note("Zaplacena danova povinnost neni nastavena");
		}
		finally{
			d=set6(d);
		}
		return d;
	}

	private VetaD set6(VetaD d){
		 try{
			d.setKc_op15_1c(FORM.getManz());
			try{
				d.setManz(FORM.getManzID());
			}catch(DataUnsetException e){
				error("Je uplatnovana sleva na manzela/ku, ale nevime o koho jde",e.getMessage()+"\n"+e.getStackTrace());
			}
			try{
				d.setM_manz(FORM.getManzMes());
			}catch(DataUnsetException e){
				error("Je uplatnovana sleva na manzela/-ku, ale neni uveden pocet mesicu",e.getMessage()+"\n"+e.getStackTrace());
			}
			try{
				d.setKc_manztpp(FORM.getManZTP());
			}catch(DataUnsetException e){
				note("Manzel/ka neni drzitelem ZTP/P");
			}
		}catch(DataUnsetException e){
				note("Sleva na manzela/-ku se neuplatnuje");
		}
		finally {
			d=set7(d);
			
		}
		 return d;
	}
	private VetaD set7(VetaD d){
		try{
			d.setKc_op15_1d(FORM.getInvalid());
		}catch(DataUnsetException e){
			note("Sleva na pozivatele invalidniho duchodu pro invaliditu prvniho nebo druheho stupne se neuplatnuje");
		}
		finally{
			d=set8(d);
		}
		return d;
	}
	private VetaD set8(VetaD d){
		try{
			d.setKc_op15_1e1(FORM.getInvalid3());
		}catch(DataUnsetException e){
			note("Sleva na pozivatele invalidniho duchodu pro invaliditu tretiho stupne se neuplatnuje");
		}finally{
			d=set9(d);
		}
		return d;
	}
	private VetaD set9(VetaD d){
		try{
			d.setKc_op15_1e2(FORM.getZTP());
		}catch(DataUnsetException e){
			note("Sleva na drzitele pruazu ZTP/P se neuplatnuje");
		}finally{
			d=set10(d);
		}
		return d;
	}
	private VetaD set10(VetaD d){
		try{
			d.setKc_pausal(FORM.getPausal());
		}catch(DataUnsetException e){
			note("Neni zaplacena dan pausalni castkou");
		}finally{
			d=set11(d);
		}
		return d;
	}
	private VetaD set11(VetaD d){
		try{
			d.setKc_pzdp(FORM.getPosledni());
		}catch(DataUnsetException e){
			note("Neni znama posledni dan");
		}finally{
			d=set12(d);
		}
		return d;
	}
	private VetaD set12(VetaD d){
		try{
			d.setKc_sraz367(FORM.getDluhopisy());
		}catch(DataUnsetException e){
			note("Neni srazena dan za statni dluhopisy");
		}finally{
			d=set13(d);
		}
		return d;
	}
	private VetaD set13(VetaD d){
		try{
			d.setKc_sraz3810(FORM.getSraz3810());
		}catch(DataUnsetException e){
			note("Neni srazena dan dle 38f odst. 12");
		}finally{
			d=set14(d);
		}
		return d;
	}
	private VetaD set14(VetaD d){
		try{
			d.setKc_sraz385(FORM.getZajistena());
		}catch(DataUnsetException e){
			note("Neni zajistena dan platcem podle 38e");
		}finally{
			d=set15(d);
		}
		return d;
	}
	private VetaD set15(VetaD d){
		try{
			d.setKc_sraz_rezehp(FORM.getSraz387());
		}catch(DataUnsetException e){
			note("Neni srazena dan podle 36 odst. 7");
		}finally{
			d=set16(d);
		}
		return d;
	}
	private VetaD set16(VetaD d){
		try{
			d.setKc_stud(FORM.getStudium());
		}catch(DataUnsetException e){
			note("Neni sleva na studenta");
		}
		finally{
			try{
				d.setKc_vyplbonus(FORM.getBonusy());
			}catch(DataUnsetException e){
				note("Nejsou vyplacene zadne mesicni danove bonusy");
			}finally{
				d=set17(d);
			}
		}
		return d;
	}
	
	private VetaD set17(VetaD d){
		try{
			d.setKc_zalpred(FORM.getZalohyZaplacene());
		}catch(DataUnsetException e){
			note("Nezaplaceny zadne zalohy");
		}finally{
			d=set18(d);
		}
		return d;
	}
	
	private VetaD set18(VetaD d){
		try{
			d.setKc_zalzavc(FORM.getZalohySrazene());
		}catch(DataUnsetException e){
			note("Nebyly srazene zadne zalohy");
		}finally{
			d=set19(d);
		}
		return d;
	}
	
	private VetaD set19(VetaD d){
		return d;
	}
}