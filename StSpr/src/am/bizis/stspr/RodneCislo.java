package am.bizis.stspr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RodneCislo {
	private static final String VZOR="([0-9]{2})([0-9]{2})([0-9]{2})/([0-9]{3,4})"; //TODO lomeno neni povinne
	private final String RODNE_CISLO;
	private final Date NAROZENI;
	private final Pohlavi POHLAVI;
	private static final RodneCislo CHYBA=new RodneCislo();
	
	private RodneCislo(){
		this.RODNE_CISLO="#";
		this.NAROZENI=new Date();
		this.POHLAVI=Pohlavi.MUZ;
	}
	
	public RodneCislo(String rc) throws IllegalDateException,IllegalIDNumberException{
		/* pomoci regulernich vyrazu se zjisti zda vstupni String ma
		 * podobu rodneho cisla, dale se z nej vykousou jednotlive casti,
		 * ktere se nasledne zvaliduji
		 * 
		 * Je-li rodne cislo platne, naplnime kontejnery, jinak vyplivneme
		 * vyjimku
		 */
		Pattern vzor=Pattern.compile(VZOR);
		Matcher ma=vzor.matcher(rc);
		if (ma.matches()){//dostali jsme neco, co pripomina rodne cislo
			//rozstrihame na kousky
			String yy=ma.group(0);
			String mm=ma.group(1);
			String dd=ma.group(2);
			String control=ma.group(3);
			
			//soucasny rok
			Date dnes=new Date();
			DateFormat df=new SimpleDateFormat("yy");
			int rok_nyni=Integer.parseInt(df.format(dnes));
			
			//try{
				//kontrola spravnosti roku
				int rok=Integer.parseInt(yy);
				if ((rok>=0)&&(rok<=rok_nyni)){
					//kontrola spravnosti dne
					int den=Integer.parseInt(dd);
					if ((den>0)&&(den<32)){
						//kontrola spravnosti mesice, navic ziskame pohlavi
						int mesic=Integer.parseInt(mm);
						if((mesic>0)&&(mesic<13)){
							this.POHLAVI=Pohlavi.MUZ;
						}
						else if((mesic>20)&&(mesic<33)){
							this.POHLAVI=Pohlavi.MUZ;
							mesic-=20;
						}
						else if ((mesic>50)&&(mesic<63)){
							this.POHLAVI=Pohlavi.ZENA;
							mesic-=50;
						}
						else if ((mesic>70)&&(mesic<83)){
							this.POHLAVI=Pohlavi.ZENA;
							mesic-=70;
						} else{
							this.POHLAVI=CHYBA.getPohlavi();
							this.RODNE_CISLO=CHYBA.toString();
							this.NAROZENI=CHYBA.getNarozeni();
							throw new IllegalMonthException();
						}
						
						df=new SimpleDateFormat("dd/MM/yy");
						try{
							this.NAROZENI=df.parse(den+"/"+mesic+"/"+rok);
						}catch (ParseException e){
							this.RODNE_CISLO=CHYBA.toString();
							throw new IllegalDateException(e.getMessage());
						}
						
						//kontrola delitelnosti jedenacti
						int cislo=Integer.parseInt(yy+mm+dd+control);
						if(cislo%11==0){
							this.RODNE_CISLO=rc;
						} else{
							this.RODNE_CISLO=CHYBA.toString();
							throw new IllegalIDNumberException("Delitelnost 11");
						}
					}else{
						this.POHLAVI=CHYBA.getPohlavi();
						this.RODNE_CISLO=CHYBA.toString();
						this.NAROZENI=CHYBA.getNarozeni();
						throw new IllegalDayException();
					}
				}else{
					this.POHLAVI=CHYBA.getPohlavi();
					this.RODNE_CISLO=CHYBA.toString();
					this.NAROZENI=CHYBA.getNarozeni();
					throw new IllegalYearException();
				}
			//}	
		} else{
			this.POHLAVI=CHYBA.getPohlavi();
			this.RODNE_CISLO=CHYBA.toString();
			this.NAROZENI=CHYBA.getNarozeni();
			throw new IllegalIDNumberException();
		}
		
	}
	@Override
	public String toString(){
		return this.RODNE_CISLO;
	}
	
	public Date getNarozeni(){
		return this.NAROZENI;
	}
	
	public Pohlavi getPohlavi(){
		return this.POHLAVI;
	}
	
	/**
	 * Prevzata kontrola spravnosti z konstruktoru, zjednodusena pro odpoved ano/ne
	 * @param rc
	 * @return
	 */
	public static boolean jePlatne(String rc){
		boolean ret=true;
		Pattern vzor=Pattern.compile(VZOR);
		Matcher ma=vzor.matcher(rc);
		if (ma.matches()){//dostali jsme neco, co pripomina rodne cislo
			//rozstrihame na kousky
			String yy=ma.group(0);
			String mm=ma.group(1);
			String dd=ma.group(2);
			String control=ma.group(3);
			
			//soucasny rok
			Date dnes=new Date();
			DateFormat df=new SimpleDateFormat("yy");
			int rok_nyni=Integer.parseInt(df.format(dnes));
			
			//kontrola spravnosti roku
			int rok=Integer.parseInt(yy);
			if ((rok>=0)&&(rok<rok_nyni)){
				//kontrola spravnosti dne
				int den=Integer.parseInt(dd);
				if ((den>0)&&(den<32)){
					//kontrola spravnosti mesice
					int mesic=Integer.parseInt(mm);
					if(!(((mesic>0)&&(mesic<13))||((mesic>20)&&(mesic<33))||((mesic>50)&&(mesic<63))||
							((mesic>70)&&(mesic<83)))) ret=false;
					else{
						//kontrola delitelnosti jedenacti
						int cislo=Integer.parseInt(yy+mm+dd+control);
						if(!(cislo%11==0))ret=false;						
					}
				}else ret=false;
			}else ret=false;
		} else ret=false;
		return ret;
	}
}
