package am.bizis.stspr;

public class IllegalMonthException extends IllegalDateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9145353693611737323L;

	public IllegalMonthException(){
		super("Neplatna hodnota polozky mesic rodneho cisla");
	}
}
