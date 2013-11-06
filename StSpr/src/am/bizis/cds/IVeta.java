package am.bizis.cds;

import java.util.Hashtable;

/**
 * Veta Pisemnosti
 * @author alex
 */
public interface IVeta {
	
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
	
	/**
	 * Vrati hashovaci tabulku nazev polozky - hodnota
	 * @return atributy vety
	 */
	Hashtable<String,String> getAttrs();
}
