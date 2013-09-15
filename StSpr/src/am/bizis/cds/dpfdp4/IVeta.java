package am.bizis.cds.dpfdp4;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

public interface IVeta {
	/**
	 * Vrati XML element dane vety obsahujici jiz nastavene jednotlive atributy (nebo nenastavene)
	 * @return XML Element dane vety
	 * @throws ParserConfigurationException
	 */
	Element getElement() throws ParserConfigurationException;
	
	/**
	 * Nejvyse kolikrat se dany element muze v podani vyskytovat
	 * @return nejvyssi pocet vyskytu
	 */
	int getMaxPocet();
	
	/**
	 * Na zaklade nastaveni atributu tohoto elementu je vhodne pripojit dalsi elementy
	 * @return ktere dalsi elementy je potreba pripojit k podani
	 */
	String[] getDependency();
	
	/**
	 * Textova reprezentace tagu elementu
	 * @return nazev elementu (napr. VetaD)
	 */
	@Override
	String toString();
}
