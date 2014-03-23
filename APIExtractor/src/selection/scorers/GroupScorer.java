package selection.scorers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import selection.parser.one.Word;

public class GroupScorer extends Scorer {

	private Map<String, List<Word>> lemmas;
	private Map<Integer, Map<Integer, Set<Integer>>> hits; 
	private Map<Integer, Double> scores;

	public GroupScorer(Word[] words, Map<Integer, Double> scores) {
		this.lemmas = make(words);
		this.hits = new HashMap<Integer, Map<Integer,Set<Integer>>>();
		this.scores = scores;
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

	@Override
	public double getScore(Word key) {
		updateHits(key);
		return getResult(key.getConstIndex());
	}

	private double getResult(int constIndex) {
		double total = 0;

		Map<Integer, Set<Integer>> values = hits.get(constIndex);
		for (Entry<Integer, Set<Integer>> entry : values.entrySet()) {
			Double score = scores.get(entry.getKey());
			int size = entry.getValue().size();
			total += score * size;
		}

		return total;
	}

	private void updateHits(Word key) {
		List<Word> words = find(key);

		assert words != null;

		int constIndex = key.getConstIndex();

		if (!hits.containsKey(constIndex)){
			hits.put(constIndex, new HashMap<Integer, Set<Integer>>());
		}

		Map<Integer, Set<Integer>> constHits = hits.get(constIndex);

		for (Word word : words) {

			int group = word.getGroup();

			if (!constHits.containsKey(group)) {
				HashSet<Integer> set = new HashSet<Integer>();
				constHits.put(group, set);
			}
			Set<Integer> set = constHits.get(group);

			set.add(key.getIndex());
		}
	}

	private List<Word> find(Word key) {
		return lemmas.get(key.getLemma());
	}

	@Override
	public void clear() {
		hits.clear();
	}

	@Override
	public String toString() {
		return "GroupScorer [hits=" + hits + "]";
	}
}
