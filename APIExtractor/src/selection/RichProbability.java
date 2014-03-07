package selection;

import java.util.HashMap;
import java.util.Map;

public class RichProbability {

	private Map<Integer, RichConstituentProbability> map;

	private Map<Integer, Integer> stat;	
	
	public RichProbability() {
		this.map = new HashMap<Integer, RichConstituentProbability>();
		this.stat = new HashMap<Integer, Integer>();
	}
	
	private void statistics(int consIndex) {
		if (!stat.containsKey(consIndex)){
			stat.put(consIndex, 1);
		} else {
			int val = stat.get(consIndex);
			stat.put(consIndex, val+1);
		}
	}	

	public double inc(int consIndex, int wordIndex, double addProb) {
		if (!map.containsKey(consIndex)){
			RichConstituentProbability cProb = new RichConstituentProbability();
			map.put(consIndex, cProb);
			statistics(consIndex);
			return cProb.inc(wordIndex, addProb);
		} else {
			RichConstituentProbability cProb = map.get(consIndex);

			double prob = cProb.inc(wordIndex, addProb);
			if (prob != -1){
				statistics(consIndex); 
			}

			return prob;
		}
	}

	public void clear() {
		map.clear();
		stat.clear();
	}

	public Map<Integer, Integer> getStat() {
		return this.stat;
	}
}
