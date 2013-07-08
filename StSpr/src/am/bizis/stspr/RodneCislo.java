package am.bizis.stspr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import am.bizis.stspr.exception.IllegalDateException;
import am.bizis.stspr.exception.IllegalDayException;
import am.bizis.stspr.exception.IllegalIDNumberException;
import am.bizis.stspr.exception.IllegalMonthException;
import am.bizis.stspr.exception.IllegalYearException;

public class RodneCislo {
	private static final String VZOR="([0-9]{2})([0-9]{2})([0-9]{2})/?([0-9]{3,4})";
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
		try{
		Pattern vzor=Pattern.compile(VZOR);
		Matcher ma=vzor.matcher(rc);
		if (ma.matches()){//dostali jsme neco, co pripomina rodne cislo
			//rozstrihame na kousky			
			String yy=ma.group(1);
			String mm=ma.group(2);
			String dd=ma.group(3);
			String control=ma.group(4);
			
			//soucasny rok
			Date dnes=new Date();
			DateFormat df=new SimpleDateFormat("yyyy");
			int rok_nyni=Integer.parseInt(df.format(dnes));
			
			//udelame z yy yyyy
			df=new SimpleDateFormat("yy");
			Date d1;
			try {
				d1 = (Date)df.parse(yy);
				df=new SimpleDateFormat("yyyy");
				int rok=Integer.parseInt(df.format(d1));
				
				//kontrola spravnosti roku
				if ((rok>=0)&&(rok<=rok_nyni)){
					//kontrola spravnosti dne
					int den=Integer.parseInt(dd);
					if ((den!=0)&&(den<32)){
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
						String cislo=yy+mm+dd+control;
						int suda=0,licha=0;
						for(int i=0;i<cislo.length();i=i+2){
							licha=licha+Integer.parseInt(""+cislo.charAt(i));
						}
						for(int i=1;i<cislo.length();i=i+2){
							suda=suda+Integer.parseInt(""+cislo.charAt(i));
						}
						if((Math.abs(suda-licha))%11==0){
							this.RODNE_CISLO=yy+mm+dd+"/"+control;
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
			} catch(ParseException e){
				throw new IllegalYearException();
			}
		} else{
			this.POHLAVI=CHYBA.getPohlavi();
			this.RODNE_CISLO=CHYBA.toString();
			this.NAROZENI=CHYBA.getNarozeni();
			throw new IllegalIDNumberException();
		}
		}catch(NullPointerException e){
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
		if(rc==null) ret=false;
		else{
		Pattern vzor=Pattern.compile(VZOR);
		Matcher ma=vzor.matcher(rc);
		if (ma.matches()){//dostali jsme neco, co pripomina rodne cislo
			//rozstrihame na kousky			
			String yy=ma.group(1);
			String mm=ma.group(2);
			String dd=ma.group(3);
			String control=ma.group(4);
			
			//soucasny rok
			Date dnes=new Date();
			DateFormat df=new SimpleDateFormat("yyyy");
			int rok_nyni=Integer.parseInt(df.format(dnes));
			
			//udelame z yy yyyy
			df=new SimpleDateFormat("yy");
			Date d1;
			try {
				d1 = (Date)df.parse(yy);
				df=new SimpleDateFormat("yyyy");
				int rok=Integer.parseInt(df.format(d1));
				
				//kontrola spravnosti roku
				if ((rok>=0)&&(rok<=rok_nyni)){
					//kontrola spravnosti dne
					int den=Integer.parseInt(dd);
					if ((den!=0)&&(den<32)){
						//kontrola spravnosti mesice
						int mesic=Integer.parseInt(mm);
						if(!(((mesic>0)&&(mesic<13))||((mesic>20)&&(mesic<33))||((mesic>50)&&(mesic<63))||
								((mesic>70)&&(mesic<83)))) ret=false;
						//kontrola delitelnosti jedenacti
						String cislo=yy+mm+dd+control;
						int suda=0,licha=0;
						for(int i=0;i<cislo.length();i=i+2){
							licha=licha+Integer.parseInt(""+cislo.charAt(i));
						}
						for(int i=1;i<cislo.length();i=i+2){
							suda=suda+Integer.parseInt(""+cislo.charAt(i));
						}
						if(!((Math.abs(suda-licha))%11==0)) ret=false;
					}else ret=false;
				}else ret=false;
			} catch(ParseException e){
				ret=false;
			}
		} else ret=false;
		}
		return ret;
	}
}
