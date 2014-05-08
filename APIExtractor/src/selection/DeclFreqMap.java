package selection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DeclFreqMap {

	private Map<Integer, Double> freq; 
	private double smoothValue;
	
	public DeclFreqMap(){
	}
	
	public DeclFreqMap(Map<Integer, Integer> freq, int totalDeclNum, double factor) {
		this.freq = make(freq, totalDeclNum, factor);	
	}
	
	public void create(Map<Integer, Integer> freq, int totalDeclNum, double factor){
		this.freq = make(freq, totalDeclNum, factor);
	}

	private Map<Integer, Double> make(Map<Integer, Integer> freq, int totalDeclNum, double factor) {
		double total = findTotal(freq.values());
		int numOfRest = totalDeclNum - freq.size();
		
		double sTotal = total * (1 + factor);
		
		this.smoothValue = (total * factor) / (numOfRest * sTotal);
		
		System.out.println("Smooth value: "+this.smoothValue + "    "+ numOfRest);
		
		Map<Integer, Double> map = new HashMap<Integer, Double>();
			
		for (Entry<Integer, Integer> fEntry : freq.entrySet()) {
			map.put(fEntry.getKey(), fEntry.getValue() / sTotal);
		}
		
		return map;
	}

	private double findTotal(Collection<Integer> values) {
		double total = 0;
		for (int v : values) {
			total += v;
		}
		return total;
	}

	public double getProbability(int id) {
		if (this.freq.containsKey(id)) {
			return this.freq.get(id);
		} else {
			return this.smoothValue;
		}
	}
}
