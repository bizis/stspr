package am.bizis.exception;

import java.io.IOException;

public class ResponseException extends IOException {
	public ResponseException(String msg){
		super(msg);
	}
}
