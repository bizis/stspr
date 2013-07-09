package am.bizis.cds.ciselnik;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Error implements ErrorHandler {

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		System.out.print("ERROR: "+arg0.getMessage()+"\n");

	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		System.out.print("FATAL ERROR: "+arg0.getMessage()+"\n");

	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		System.out.print("WARNING: "+arg0.getMessage()+"\n");

	}

}
