package statistics;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class DeclarationStatistics {

	public static final int MAX_SIZE = 500;
	public static final int MAX_PRINT_SIZE = 5;	
	public static final EntryComparator comparator = new EntryComparator();
	
	private Map<String, Integer> map = new HashMap<String, Integer>();
	
	//Dummy way!	
	public void inc(String name){
		if (map.containsKey(name)){
			int val = map.get(name);
			map.put(name, val+1);
		} else{
			if (map.size() < 500){
				map.put(name, 1);	
			}
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
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		PriorityQueue<Entry<String, Integer>> pq = new PriorityQueue<Map.Entry<String, Integer>>(100, comparator);
		pq.addAll(map.entrySet());
		
		int length = Math.min(pq.size(), MAX_PRINT_SIZE);
		for(int i = 0; i <length; i++){
			Map.Entry<String, Integer> entry = pq.remove();
			String name = entry.getKey();
			int val = entry.getValue();
			sb.append(" ("+name +", "+val+"), ");
		}
		
		return sb.toString();
		
	}
	
}
