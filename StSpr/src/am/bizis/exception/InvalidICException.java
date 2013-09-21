package am.bizis.exception;

public class InvalidICException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidICException(String s){
		super("Neplatne IC:"+s);
	}
}
