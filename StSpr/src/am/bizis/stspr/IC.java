package am.bizis.stspr;

import am.bizis.exception.InvalidICException;

public class IC {
	private final int ICO[];
	
	@Deprecated
	public IC() throws Exception{
		this((int[])null);
	}
	public IC(int[] ic) throws InvalidICException{
		if (ic.length==8){
			this.ICO=ic;
			if(!IC.valid(this)) throw new InvalidICException("nespravny format IC");
		}else throw new InvalidICException("delka pole");
		
	}
	public IC(char[] ic)throws InvalidICException{
		this.ICO=new int[8];
		int delka=ic.length;
		int i;//kolik nul musime doplnit na zacatek
		if(delka<8) i=8-delka;
		else if(delka==0) i=0;
		else throw new InvalidICException("delka pole");
		for(int j=0;j<delka;j++){
			ICO[i+j]=Integer.parseInt(""+ic[j]);
		}
	}
	public IC (String IC) throws InvalidICException{
		this(IC.toCharArray());
		
	}
	public int[] getArray(){
		return this.ICO;
	}
	@Override
	public String toString(){
		String ico="";
		for(int i=0;i<7;i++){
			ico+=ICO[i]+"";
		}
		return ico;
	}
	public static boolean valid(IC ico){
		int sum=0,mod,kontrol;
		for(int i=0;i<7;i++){
			sum+=ico.ICO[i]*i;
		}
		mod=sum%11;
		if(mod==1) kontrol=0;
		if((mod==0)||(mod==10)) kontrol=1;
		else kontrol=11-mod;
		if (ico.ICO[7]==kontrol) return true;
		else return false;
	}
}
