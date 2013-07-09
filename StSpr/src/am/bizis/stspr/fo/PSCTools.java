package am.bizis.stspr.fo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import am.bizis.stspr.exception.IllegalContentTypeException;
import am.bizis.stspr.exception.PostNotFoundException;

public class PSCTools {
	/**
	 * Nalezne okres k zadanemu PSC s vyuzitim stranek Ceske posty, s.p.
	 * @param PSC Postovni smerovaci cislo
	 * @return Okres, do ktereho patri dane PSC
	 * @throws IOException chyba pri spojeni, nebo neplatny content-type (musi byt text/html; charset=UTF-8)
	 * @throws PostNotFoundException posta s danym PSC nebyla nalezena
	 */
	public static String getOkres(int PSC) throws IOException,PostNotFoundException{
		String html=null;
		
		String zero;
		java.text.DecimalFormat nft = new  
		java.text.DecimalFormat("#00.###");  
		nft.setDecimalSeparatorAlwaysShown(false);
		zero=nft.format(PSC%100);
		
		String url="http://iop.ceskaposta.cz/AdvancedShowPostaDetail.action?psc="+(PSC/100)+"+"+zero+"#posty";
		URL u=new URL(url);
		URLConnection c=u.openConnection(); 
		if(c.getContentType().equals("text/html;charset=UTF-8")){
			BufferedReader in=new BufferedReader(new InputStreamReader(c.getInputStream()));
			Pattern vzor=Pattern.compile("^.*<strong>Okres:</strong><br />");
			html="";
			String line;
			int matches=0;
			while((line=in.readLine()) != null){
				Matcher ma=vzor.matcher(line);
				if(ma.matches()){
					Pattern p2=Pattern.compile("					(.*)");
					Matcher m2=p2.matcher(in.readLine());
					if (m2.matches()) html=m2.group(1);
					html=StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeXml(html));
					matches++;
				}
			}
			if (matches==0) throw new PostNotFoundException(""+PSC);
			in.close();
		} else throw new IllegalContentTypeException(url+"\t"+c.getContentType());
		return html;
	}
	
}
