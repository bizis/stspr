package am.bizis.exception;

public class IllegalIDNumberException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4368657977546511644L;
	
	public IllegalIDNumberException(){
		super("Neplatne rodne cislo");
	}
	
	public IllegalIDNumberException(String msg){
		super("Neplatne rodne cislo: "+msg);
	}

}
