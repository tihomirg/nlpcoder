package selection.scorers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import selection.parser.one.Word;

public class HitScorer extends Scorer {

	private Map<Integer, Set<Integer>> hits = new HashMap<Integer, Set<Integer>>();
	
	@Override
	public double getScore(Word key) {
		int constIndex = key.getConstIndex();
		if(!hits.containsKey(constIndex)){
			hits.put(constIndex, new HashSet<Integer>());
		}
		
		Set<Integer> set = hits.get(constIndex);
		set.add(key.getIndex());
		return set.size();
	}

	@Override
	public void clear() {
		hits.clear();
	}

	@Override
	public String toString() {
		return "HitScorer [hits=" + hits + "]";
	}
}
