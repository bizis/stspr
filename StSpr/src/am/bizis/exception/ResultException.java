package am.bizis.exception;

import java.io.IOException;

public class ResultException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8470156113638973158L;
	
	public ResultException(String msg){
		super(msg);
	}
}
