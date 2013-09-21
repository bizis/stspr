package am.bizis.cds.exchange;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import am.bizis.exception.ResponseException;

/**
 * ResponseParser overi vystup podatelny EPO
 * @author alex
 * @version 20130918
 */
public class ResponseParser {

	private final Document RESPONSE;
	public ResponseParser(Document response){
		this.RESPONSE=response;
	}

	/**
	 * Zjistime, zda jsou v nasem podani chyby
	 * @return jsou-li v nasem podani chyby (true - podani je s chybou)
	 */
	private boolean error(){
		if(RESPONSE.getDocumentElement().getNodeName().equals("Chyby")) return true; else return false;
	}
	
	/**
	 * Vrati vyjimku s obsah vsech chyb v podani
	 * @throws ResponseException chyby v podani naparsovane do jednoho stringu
	 */
	private void reportErr() throws ResponseException{
		NodeList nl=RESPONSE.getElementsByTagName("Chyba");
		String report="";
		for(int i=0;i<nl.getLength();i++){
			Node n=nl.item(i);
			report+="Polozka: "+n.getNodeValue()+", ";
			Node m=n.getChildNodes().item(0);
			if(m.getNodeName().equals("Text")) report+="Chyba: "+m.getNodeValue()+"\n";
		}
		throw new ResponseException(report);
	}
}
