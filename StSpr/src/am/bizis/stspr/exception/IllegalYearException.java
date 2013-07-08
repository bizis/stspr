package am.bizis.stspr.exception;

public class IllegalYearException extends IllegalDateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6106371145586125240L;
	
	public IllegalYearException(){
		super("Neplatna hodnota polozky rok rodneho cisla");
	}

}
