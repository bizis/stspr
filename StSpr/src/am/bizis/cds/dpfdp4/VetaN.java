package am.bizis.cds.dpfdp4;
import java.lang.Math;
public abstract class VetaN implements IVeta {

	private final int MAX=1;
	private final String ELEMENT="VetaN";
	//private final VetaD d;
	private final int kc_zbyvpred; 

	public VetaN(VetaD d) throws IllegalArgumentException{
		//this.d=d;
		this.kc_zbyvpred=(int) Math.round(d.getKc_zbyvpred());
		if(this.kc_zbyvpred<=0) throw new IllegalArgumentException("Částka uvedená v Žádosti o vrácení přeplatku musí být větší než 0.");
	}
	@Override
	public int getMaxPocet() {
		return MAX;
	}
	
	@Override
	public String toString(){
		return ELEMENT;
	}
	
	@Override
	public String[] getDependency() {
		return null;
	}
	
	public int getKcPreplatek(){
		return kc_zbyvpred;
	}

}
