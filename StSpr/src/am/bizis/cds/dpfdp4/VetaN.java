package am.bizis.cds.dpfdp4;
import java.lang.Math;

import am.bizis.cds.IVeta;
/**
 * Zobecnena veta N - Žádost o vrácení přeplatku
 * @author alex
 */
public abstract class VetaN implements IVeta {

	private final int MAX=1;
	private final String ELEMENT="VetaN";
	//private final VetaD d;
	private final int kc_zbyvpred; 

	/**
	 * Obecny konstruktor pro vety N
	 * @param d Veta D, ze ktere bereme vysi preplatku
	 * @throws IllegalArgumentException Částka uvedená v Žádosti o vrácení přeplatku musí být větší než 0
	 */
	public VetaN(VetaD d) throws IllegalArgumentException{
		//this.d=d;
		this.kc_zbyvpred=(int) Math.round(d.getKc_zbyvpred());
		if(this.kc_zbyvpred<=0) throw new IllegalArgumentException("Částka uvedená v Žádosti o vrácení přeplatku musí být větší než 0.");
	}
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getMaxPocet()
	 */
	@Override
	public int getMaxPocet() {
		return MAX;
	}
	
	@Override
	public String toString(){
		return ELEMENT;
	}
	
	/* (non-Javadoc)
	 * @see am.bizis.cds.dpfdp4.IVeta#getDependency()
	 */
	@Override
	public String[] getDependency() {
		return null;
	}
	
	/**
	 * Preplatek
	 * @return vyse preplatku na dani z prijmu
	 */
	public int getKcPreplatek(){
		return kc_zbyvpred;
	}

}
