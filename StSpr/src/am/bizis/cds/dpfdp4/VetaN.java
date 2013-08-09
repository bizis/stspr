package am.bizis.cds.dpfdp4;

public abstract class VetaN implements IVeta {

	private final int MAX=1;
	private final String ELEMENT="VetaN";

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

}
