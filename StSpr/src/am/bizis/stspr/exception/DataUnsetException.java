package am.bizis.stspr.exception;

public class DataUnsetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -856193838094411327L;
	private static final String MSG="Polozka nebyla uzivatelem vyplnena";

	public DataUnsetException() {
		super(MSG);
	}

	public DataUnsetException(String message) {
		super(MSG+": "+message);
	}

	public DataUnsetException(Throwable cause) {
		super(MSG,cause);
	}

	public DataUnsetException(String message, Throwable cause) {
		super(MSG+": "+message, cause);
	}

	public DataUnsetException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(MSG+": "+message, cause, enableSuppression, writableStackTrace);
	}

}
