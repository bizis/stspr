package am.bizis.stspr;

import java.io.IOException;

public class IllegalContentTypeException extends IOException {

	private static final long serialVersionUID = 548421116285853369L;
	private static final String MSG="Obsah stranky neni text/html!";
	public IllegalContentTypeException() {
		super(MSG);
	}

	public IllegalContentTypeException(String message) {
		super(MSG+": "+message);
	}

	public IllegalContentTypeException(Throwable cause) {
		super(MSG,cause);
	}

	public IllegalContentTypeException(String message, Throwable cause) {
		super(MSG+":"+message, cause);
	}

}
