package selection.scorers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nlp.parser.one.Word;

import edu.mit.jwi.item.POS;

public class FrequencyScorer extends Scorer {
	
	private Word[] words;
	private Map<String, List<Word>> lemmas;
	private Map<Word, Double> frequency;
	
	private Map<Integer, Map<Integer, Double>> hits;
	
	public FrequencyScorer(Word[] words) {
		this.words = words;
		this.hits = new HashMap<Integer, Map<Integer,Double>>();
		this.lemmas = make(words);
		
	}
	
	public void make(Map<POS, Map<String, Integer>> frequency){
		this.frequency = make(words, frequency);
	}
	
	private Map<String, List<Word>> make(Word[] words) {
		HashMap<String, List<Word>> map = new HashMap<String, List<Word>>();
		for (Word word : words) {

			String lemma = word.getLemma();
			if (!map.containsKey(lemma)) {
				map.put(lemma, new LinkedList<Word>());
			}

			List<Word> list = map.get(lemma);
			list.add(word);
		}
		return map;
	}
	
	private Map<Word, Double> make(Word[] words, Map<POS, Map<String, Integer>> frequency) {
		HashMap<Word, Double> map = new HashMap<Word, Double>();
		for (Word word : words) {
			String lemma = word.getLemma();
			POS pos = word.getPos();
			
			double d = frequency.get(pos).get(lemma);
			
			map.put(word, 1/d);
		}
		
		return map;
	}

	private List<Word> find(Word key) {
		return lemmas.get(key.getLemma());
	}	
	
	@Override
	public double getScore(Word key) {
		List<Word> words = find(key);
		
		assert words != null;

		int constIndex = key.getConstIndex();

		int wordIndex = key.getIndex();
		
		if (!hits.containsKey(constIndex)){
			hits.put(constIndex, new HashMap<Integer, Double>());
		}

		Map<Integer, Double> constHits = hits.get(constIndex);

		for (Word word : words) {

			double freq = frequency.get(word);

			if (!constHits.containsKey(wordIndex)) {
				constHits.put(wordIndex, 0.0);
			}
						
			double oldFre = constHits.get(wordIndex);
			
			if(freq > oldFre) {
				constHits.put(wordIndex, freq);
			}
		}
		
		return getResult(key.getConstIndex());
	}

	private double getResult(int constIndex) {
		double total = 0;

		Map<Integer, Double> values = hits.get(constIndex);
		for (Entry<Integer, Double> entry : values.entrySet()) {
			double ferq = entry.getValue();
			total += ferq;
		}

		return total;
	}
	@Override
	public void clear() {
		hits.clear();
	}
	
	@Override
	public String toString() {
		return "FreqScorer [hits=" + hits + "]";
	}

	@Override
	public String toString(int contextIndex) {
		return Double.toString(getResult(contextIndex));
	}	
}
