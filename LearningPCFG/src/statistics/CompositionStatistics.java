package statistics;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import util.Pair;

public class CompositionStatistics {
	
	private Map<String, Map<String, Integer>> ruleToStatistics = new HashMap<String, Map<String,Integer>>();

	private Map<String, Integer> ruleToCount = new HashMap<String, Integer>();
	
	private Map<String, Integer> getRuleStatistics(Pair<String, String> pair){
		String leftSide = pair.getFirst();
		if(!ruleToStatistics.containsKey(leftSide)){
			ruleToStatistics.put(leftSide, new HashMap<String, Integer>());
		}

		return ruleToStatistics.get(leftSide);
	}

	public void inc(List<Pair<String, String>> list){
		for (Pair<String, String> pair : list) {
			inc(pair);
		}
	}

	public void inc(Pair<String, String> pair){
		Map<String, Integer> statistics = getRuleStatistics(pair);
		
		String second = pair.getSecond();
		if(! statistics.containsKey(second)){
			statistics.put(second, 0);
		}
		
		int val = statistics.get(second);
		statistics.put(second, val + 1);
		
		incrementCount(pair.getFirst());
	}

	private void incrementCount(String leftSide) {
		if(!ruleToCount.containsKey(leftSide)){
			ruleToCount.put(leftSide, 1);
		} else {
			ruleToCount.put(leftSide, ruleToCount.get(leftSide) + 1);
		}
	}

	public void print(PrintStream out){
		for (Entry<String, Map<String, Integer>> entry : ruleToStatistics.entrySet()) {
			String key = entry.getKey();
			
			int count = ruleToCount.get(key);
			
			if (Names.HumanReadable){
				out.println("-----------------------------------------------------------------------------------------------");
				out.println("Head: "+key);
				out.println();
			}
			
			Map<String, Integer> value = entry.getValue();

			PriorityQueue<Entry<String, Integer>> pq = new PriorityQueue<Map.Entry<String, Integer>>(100, EntryComparator1.DESC);
			pq.addAll(value.entrySet());		

			int length = value.size();

			for(int i=0; i< length; i++){
				Entry<String, Integer> curr = pq.remove();
				String name = curr.getKey();
				double stat = ((double) curr.getValue())/count;
				out.println(stat +Names.Colon+ name);
			}
			
			if (Names.HumanReadable){
				out.println();
				out.println();
			}
		}
	}

	//TODO: Improve this version.
	public Pair<Integer, Integer> releaseUnder(int percentage){
		return null;
	}
}

class EntryComparator1 implements Comparator<Entry<String, Integer>>
{
	private static interface CmpStragegy {
		public int cmp(int x, int y);
	}

	private static class AscStrategy implements CmpStragegy{

		@Override
		public int cmp(int x, int y) {
			if (x < y)
			{
				return -1;
			}
			if (x > y)
			{
				return 1;
			}
			return 0;
		}

	}

	private static class DescStrategy implements CmpStragegy{

		@Override
		public int cmp(int x, int y) {
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

	private CmpStragegy strategy;

	public static final EntryComparator1 ASC = new EntryComparator1(new AscStrategy());
	public static final EntryComparator1 DESC = new EntryComparator1(new DescStrategy());	

	public EntryComparator1(CmpStragegy strategy){
		this.strategy = strategy;
	}

	@Override
	public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2)
	{
		int x = e1.getValue();
		int y = e2.getValue();

		return strategy.cmp(x, y);
	}
}	
