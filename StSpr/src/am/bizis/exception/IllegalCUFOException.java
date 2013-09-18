package am.bizis.exception;

public class IllegalCUFOException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1816402871494677680L;

	public IllegalCUFOException(String msg){
		super("Neexistujici Uzemni financni organ: "+msg);
	}
}
