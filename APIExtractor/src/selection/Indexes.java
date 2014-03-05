package selection;

import java.util.HashMap;
import java.util.Map;

import selection.parser.one.Word;

public class Indexes {

	public Map<String, Word> lemmaToWord;
	public Word[] words;

	public Indexes(Word[] words){
		this.lemmaToWord = make(words);
		this.words = words;
	}
	
	private Map<String, Word> make(Word[] words2) {
		Map<String, Word> words = new HashMap<String, Word>();
		
		for(Word word:words2){
			String lemma = word.getLemma();			
			assert(!words.containsKey(lemma));
			words.put(lemma, word);
		}
		
		return words;
	}

	public Word find(Word key) {
		return lemmaToWord.get(key.getLemma());
	}

	//TODO: Change the way we calculate probabilities.
	public double getProbability(Word key) {
		Word word = lemmaToWord.get(key.getLemma());
		assert word != null;
	    return key.getProbability();
	}

	public Word[] getWords() {
		return words;
	}
}
