package am.bizis.exception;

public class PostNotFoundException extends IllegalArgumentException {

	private static final long serialVersionUID = -4089090719368921610L;
	private static final String MSG="Nenalezena posta se zadanym PSC!";

	public PostNotFoundException() {
		super(MSG);
	}

	public PostNotFoundException(String s) {
		super(MSG+" : "+s);
	}

	public PostNotFoundException(Throwable cause) {
		super(MSG,cause);
	}

	public PostNotFoundException(String message, Throwable cause) {
		super(MSG+" : "+message, cause);
	}

}
