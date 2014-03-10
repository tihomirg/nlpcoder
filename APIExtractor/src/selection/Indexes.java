package selection;

import java.util.HashMap;
import java.util.Map;

import selection.parser.one.Word;

public class Indexes {

	public Map<String, Word> lemmaToWord;
	public Word[] words;
	private double nullProb;
	private double prbability;
	
	public Indexes(Word[] words, double nullProb){
		this.lemmaToWord = make(words);
		this.words = words;
		this.nullProb = nullProb; 
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

	//TODO: Change the way we calculate probabilities, such that a probability of a containig wor is taken into account.
	public double getProbability(Word key, int constLength) {
		Word word = lemmaToWord.get(key.getLemma());
		assert word != null;
		
		return key.getProbability() * (1 - Math.abs(constLength - words.length)*nullProb) * word.getProbability() * this.prbability;		
		
		//return key.getProbability() * (1 - Math.abs(constLength - words.length)*nullProb) / constLength;
		
		//return key.getProbability() * (1 - Math.max(0, constLength - words.length)*nullProb) / Math.max(words.length, constLength);
		
	    //return key.getProbability() * (1 - Math.abs(constLength - words.length)*nullProb) / words.length;
	}

	public Word[] getWords() {
		return words;
	}

	public void setProbability(double d) {
		this.prbability = d;
	}
}
