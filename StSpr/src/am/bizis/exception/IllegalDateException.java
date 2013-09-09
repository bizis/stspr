package am.bizis.exception;

public class IllegalDateException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5709497305193874283L;

	public IllegalDateException(){
		super("Malformated date");
	}
	
	public IllegalDateException(String s){
		super("Malformated date: "+s);
	}
}
