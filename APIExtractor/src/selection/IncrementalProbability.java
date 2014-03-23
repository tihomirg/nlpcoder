package selection;

import java.util.HashMap;
import java.util.Map;

public class IncrementalProbability {

	private Map<Integer, RichConstituentProbability> map = new HashMap<Integer, RichConstituentProbability>();

	public double inc(int consIndex, int wordIndex, double addProb) {
		if (!map.containsKey(consIndex)){
			RichConstituentProbability cProb = new RichConstituentProbability();
			map.put(consIndex, cProb);
			return cProb.inc(wordIndex, addProb);
		} else {
			return map.get(consIndex).inc(wordIndex, addProb);
		}
	}

	public void clear() {
		map.clear();
	}
}
