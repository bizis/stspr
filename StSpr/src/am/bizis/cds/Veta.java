/**
 * 
 */
package am.bizis.cds;

import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.javaspecialists.hmk.IterableEnumeration;

/**
 * @author alex
 *
 */
public class Veta{
	public static Element getElement(String root,Hashtable<String, String> attrs) throws ParserConfigurationException{
		//vytvorim element - kus kodu, ktery se kopiroval mezi vetami
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		Document EPO=docBuilder.newDocument();
		Element element=EPO.createElement(root);
		
		//naplnim atributy dle hashtable
		for(String attr:new IterableEnumeration<String>(attrs.keys())){
			element.setAttribute(attr, attrs.get(attr));
		}
		
		//vratim vytvoreny element
		return element;
	}
}
