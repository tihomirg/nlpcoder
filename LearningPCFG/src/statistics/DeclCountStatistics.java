package statistics;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import definitions.Declaration;

public class DeclCountStatistics {

	private static final int InitialCap = 100;
	private static final EComparator COMPARATOR = new EComparator();
	private Map<Integer, Integer> decls;

	public DeclCountStatistics() {
		this.decls = new HashMap<Integer, Integer>();
	}
	
	public void inc(Set<Declaration> decls){
		
		for (Declaration decl : decls) {
			
			int id = decl.getId();
			if (!this.decls.containsKey(id)) {
				this.decls.put(id, 0);
			}
			this.decls.put(id, this.decls.get(id)+1);
		}
	}
	
	public void inc(List<Declaration> decls){
		
		for (Declaration decl : decls) {
			
			int id = decl.getId();
			if (!this.decls.containsKey(id)) {
				this.decls.put(id, 0);
			}
			this.decls.put(id, this.decls.get(id)+1);
		}
	}
	
	public void print(PrintStream out){
		PriorityQueue<Entry<Integer, Integer>> pq = new PriorityQueue<Map.Entry<Integer,Integer>>(InitialCap, COMPARATOR);
		pq.addAll(decls.entrySet());
		while (!pq.isEmpty()) {
			Entry<Integer, Integer> first = pq.remove();
			out.println(first.getValue() +" : "+first.getKey());
		}
	}
}

class EComparator implements Comparator<Entry<Integer, Integer>> {

	@Override
	public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
		// TODO Auto-generated method stub
		double probability1 = o1.getValue();
		double probability2 = o2.getValue();
		if (probability1 > probability2) return -1;
		else if (probability1 < probability2) return 1;
		else return 0;
	}
}	