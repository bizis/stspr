package am.bizis.stspr.exception;

public class IllegalDayException extends IllegalDateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -323685560513168049L;
	
	public IllegalDayException(){
		super("Neplatna hodnota polozky datum rodneho cisla");
	}

}
