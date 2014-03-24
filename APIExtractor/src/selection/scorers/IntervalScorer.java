package selection.scorers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import selection.parser.one.Word;

public class IntervalScorer extends Scorer{

	private Map<Integer, Map<Integer, Double>> hits = new HashMap<Integer, Map<Integer, Double>>();

	@Override
	public double getScore(Word key) {
		int constIndex = key.getConstIndex();
		if(!hits.containsKey(constIndex)){
			hits.put(constIndex, new HashMap<Integer, Double>());
		}
		Map<Integer, Double> map = hits.get(constIndex);
		int index = key.getIndex();
		//We assume that words with the same index have the same probability.
		if (!map.containsKey(index)) {
			map.put(index, key.getProbability());
		}

		return getResult(map);
	}

	private double getResult(Map<Integer, Double> map) {
		Collection<Double> values = map.values();
		double total = 0;
		for (Double val : values) {
			total += val;
		}
		return total;
	}

	@Override
	public void clear() {
		hits.clear();
	}

}
