package selection;

import java.util.ArrayList;
import java.util.List;

public class WordProcessor {
	
	private WordCorrector corrector;
	
	public WordProcessor(WordCorrector checker) {
		this.corrector = checker;
	}

	public List<String> sliceComplexWord(String cWord){
		List<String> words = new ArrayList<String>();
		for(String word: slice(cWord)){
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
}
