package search.nlp.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;

public class WordPosCorrector {

	private IDictionary dict;
	
	public WordPosCorrector() {
		// construct the dictionary object and open it
		try {
			dict = new Dictionary(new URL("file", null , "C:/Program Files/WordNet/3.1/dict"));
			dict.open();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public String correctPOS(String lemma, String pos){
		String newPos = Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)) : pos;
		if (newPos.equals("N")){
			if(isAjective(lemma)){
				if (isVerb(lemma)) return "V";
				else return "J";
			}
			if(isAdverb(lemma)) {
				if (isVerb(lemma)) return "V";
				else return "R";
			}
		} else if(newPos.equals("J") || newPos.equals("R")){
			if (isVerb(lemma)) return "V";			
		}
		
		return newPos;
	}

	private boolean isAdverb(String lemma) {
		return dict.getIndexWord(lemma, POS.ADVERB) != null;
	}

	private boolean isAjective(String lemma) {
		return dict.getIndexWord(lemma, POS.ADJECTIVE) != null;
	}

	private boolean isVerb(String lemma) {
		return dict.getIndexWord(lemma, POS.VERB) != null;
	}
}
