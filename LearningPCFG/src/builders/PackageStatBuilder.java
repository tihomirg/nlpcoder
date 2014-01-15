package builders;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import org.eclipse.jdt.core.dom.ImportDeclaration;


public class PackageStatBuilder extends IBuilder {

	private Map<String, Integer> stat = new HashMap<String, Integer>();
	
	public void inc(String name){
		if(!stat.containsKey(name)){
			stat.put(name, 0);
		}
		
		int val = stat.get(name);
		stat.put(name, val+1);
	}
	
	public boolean visit(ImportDeclaration node) {
		this.inc(node.getName().toString()+(node.isOnDemand()?".*":""));
		return false;
	}

	public static class EntryComparator implements Comparator<Entry<String, Integer>>
	{
	    @Override
	    public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2)
	    {
	    	int x = e1.getValue();
	    	int y = e2.getValue();
	    	
	        if (x < y)
	        {
	            return 1;
	        }
	        if (x > y)
	        {
	            return -1;
	        }
	        return 0;
	    }
	}	
	
	public void print(PrintStream out){
		PriorityQueue<Entry<String, Integer>> pq = new PriorityQueue<Map.Entry<String,Integer>>(100, new EntryComparator());
		for(Entry<String, Integer> entry:stat.entrySet()){
			pq.add(entry);
		}
		
		int length = pq.size();
		for(int i=0; i <length; i++){
			Entry<String, Integer> entry = pq.remove();
			String name = entry.getKey();
			int frequency = entry.getValue();
			
			out.println(frequency+"  "+name);
		}
	}
	
}
