package selection;

import java.util.ArrayList;
import java.util.List;

import selection.parser.one.WordCorrector;
import selection.parser.one.WordNet;
import selection.parser.one.WordTagger;

import edu.mit.jwi.item.POS;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class WordProcessor {
	
	private WordCorrector corrector;
	private WordTagger tagger;
	private WordNet wordNet;
	
	public WordProcessor(){
		this.setCorrector(new WordCorrector());
		this.tagger = new WordTagger();
		this.wordNet = new WordNet();
	}
	
	public WordProcessor(WordCorrector checker, WordTagger tagger, WordNet wordNet){
		this.setCorrector(checker);
		this.tagger = tagger;
		this.wordNet = wordNet;
	}

	public List<String> decompose(String cWord){
		List<String> words = new ArrayList<String>();
		//System.out.println("In decompose (1): "+cWord);
		
		for(String word: slice(cWord)){
			//System.out.println("In decompose (2): "+word);
			
			String s = corrector.correct(word);
			if (s != null) words.add(s);
		}
		return words;
	}

	private List<String> slice(String sentence) {
		String word ="";
		List<String> words = new ArrayList<String>();
		
	    boolean prevSep = true;
	    
	    boolean prevLower = true;
	    
	    boolean lastAdded = true;
		for(char c: sentence.toCharArray()){
			
		  if (Character.isLetter(c)) {
			if (Character.isUpperCase(c)){
		    	if ((prevSep || prevLower) && !word.equals("")){
		    	  words.add(word);
		    	  word=Character.toString(Character.toLowerCase(c));
		    	} else {
		    	  word+=Character.toLowerCase(c);
		    	}
		    	prevSep = false;
		    	prevLower = false;	    	
		    } else {
		    	prevLower = true;
		    	word+=c;
		    }
	    	lastAdded = false;
		  } else {
			  if (!lastAdded){
				  lastAdded = true;
				  words.add(word);
				  word = "";
			  }
			  prevSep = true;
		  }
		}
		
		if (!lastAdded) words.add(word);
		
		return words;
	}

	public MaxentTagger getTagger() {
		return tagger.getTagger();
	}
	
	public String steam(String word, POS pos){
		return wordNet.getSteam(word, pos);
	}

	public WordNet getWordNet() {
		return wordNet;
	}

	public WordCorrector getCorrector() {
		return corrector;
	}

	public void setCorrector(WordCorrector corrector) {
		this.corrector = corrector;
	}
}
