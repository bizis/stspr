package am.bizis.stspr;

public class IllegalIDNumberException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4368657977546511644L;
	
	public IllegalIDNumberException(){
		super("Neplatne rodne cislo");
	}

}
