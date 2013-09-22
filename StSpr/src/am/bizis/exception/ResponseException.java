package am.bizis.exception;

import java.io.IOException;

public class ResponseException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5966082806730384281L;

	public ResponseException(String msg){
		super(msg);
	}
}
