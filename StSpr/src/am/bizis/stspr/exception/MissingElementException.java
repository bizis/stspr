package am.bizis.stspr.exception;

public class MissingElementException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3562305208248471077L;

	private static final String MSG="Chybejici veta podani";
	public MissingElementException() {
		super(MSG);
	}

	public MissingElementException(String s) {
		super(MSG+": "+s);
	}

	public MissingElementException(Throwable cause) {
		super(MSG,cause);
	}

	public MissingElementException(String message, Throwable cause) {
		super(MSG+": "+message, cause);
	}

}
