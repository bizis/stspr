package am.bizis.cds.dpfdp4;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

public interface IVeta {
	Element getElement() throws ParserConfigurationException;
	int getMaxPocet();
}
