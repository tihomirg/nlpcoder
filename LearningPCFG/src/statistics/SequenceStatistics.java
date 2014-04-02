package statistics;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import scopes.ScopeKeyValue;

public class SequenceStatistics {

	private Map<String, Integer> sequences = new HashMap<String, Integer>();

	public static final EntryComparator comparator = new EntryComparator();

	private static final int InitialCap = 100;
	
	public void inc(ScopeKeyValue<String, List<String>> variables) {
		
		Map<String, List<String>> map = variables.getVariables();
		
		for (Entry<String, List<String>> entry : map.entrySet()) {
			//String variable = entry.getKey();
			List<String> value = entry.getValue();
			String sequence = value.toString();
			if (!sequences.containsKey(sequence)) {
				sequences.put(sequence, 0);
			}
			
			sequences.put(sequence, sequences.get(sequence) + 1);
		}
		
	}
	
	private static class EntryComparator implements Comparator<Map.Entry<String, Integer>>{

		@Override
		public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
			int x1 = o1.getValue();
			int x2 = o2.getValue();
			
			if (x1 < x2) return 1;
			else if (x1 > x2) return -1;
			else return 0;
		}
		
	}	
	
	public void print(PrintStream out){
		PriorityQueue<Entry<String, Integer>> pq = new PriorityQueue<Map.Entry<String,Integer>>(InitialCap, comparator);
		pq.addAll(sequences.entrySet());
		while (!pq.isEmpty()) {
			Entry<String, Integer> first = pq.remove();
			out.println(first.getValue() +" : "+first.getKey());
		}
	}	

}
