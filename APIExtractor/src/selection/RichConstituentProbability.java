package selection;

import java.util.HashMap;
import java.util.Map;

public class RichConstituentProbability {

	private double val;
	
	private Map<Integer, Double> map; 
	
	public RichConstituentProbability() {
		this.map = new HashMap<Integer, Double>();
	}

	public double inc(int wordIndex, double addProb) {
		if(!map.containsKey(wordIndex)){
			val += addProb;
			map.put(wordIndex, addProb);
		} else {
			Double wordVal = map.get(wordIndex);
			if (addProb > wordVal){
				val += (addProb - wordVal);
			    map.put(wordIndex, addProb);
			} else {
				return -1;
			}
		}
		
		return val;
	}

}
