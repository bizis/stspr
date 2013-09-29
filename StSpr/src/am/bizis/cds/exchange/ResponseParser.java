package am.bizis.cds.exchange;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import am.bizis.exception.ResponseException;

/**
 * ResponseParser overi vystup podatelny EPO
 * @author alex
 */
public class ResponseParser {

	private final Document RESPONSE;
	
	/**
	 * Vytvori response parser s XML docem, ktery jsme ziskali od serveru
	 * @param response odpoved CDS MFCR
	 */
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
	public void reportErr() throws ResponseException{
		if(error()){
			NodeList nl=RESPONSE.getElementsByTagName("Chyba");
			String report="";
			for(int i=0;i<nl.getLength();i++){
				Node n=nl.item(i);
				NamedNodeMap attr=n.getAttributes();
				Node polozka=attr.getNamedItem("Polozka");
				if(polozka!=null)report+="Polozka: "+polozka.getNodeValue()+", ";
				Node m=n.getChildNodes().item(0);
				if(m.getNodeName().equals("Text")) report+="Chyba: "+m.getTextContent()+"\n";
			}
			throw new ResponseException(report);
		}
	}
	
	
}
