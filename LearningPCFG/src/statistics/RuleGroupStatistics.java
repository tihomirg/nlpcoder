package statistics;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import symbol.Symbol;
import util.Pair;

public class RuleGroupStatistics {
	
	private int totalCount; 
	private int realCount;
		
	private Map<String, RuleStatistics> ruleToStatistics = new HashMap<String, RuleStatistics>();
	private String head;

	public RuleGroupStatistics(String head) {
		this.head = head;
	}
	
	private RuleStatistics getRuleStatistics(Symbol symbol){
		String ruleString = symbol.toString();
		if(!ruleToStatistics.containsKey(ruleString)){
			ruleToStatistics.put(ruleString, new RuleStatistics(symbol));
		}
		
		return ruleToStatistics.get(ruleString);
	}
	
	public void inc(Symbol symbol){
		RuleStatistics statistics = getRuleStatistics(symbol);
		statistics.incCount();
		this.incCount();
	}

	public void incCount(){
		totalCount++;
		realCount++;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int getRealCount() {
		return this.realCount;
	}
	
	public void print(PrintStream out){
		out.println("----------------------------------------------------------------------------------------------------------------------");
		out.println("Head: "+this.realCount +" out of "+this.totalCount+"  "+this.head);
		out.println();
		
		PriorityQueue<Entry<String, RuleStatistics>> pq = new PriorityQueue<Map.Entry<String,RuleStatistics>>(100, EntryComparator.DESC);
		pq.addAll(ruleToStatistics.entrySet());		
		
		int length = ruleToStatistics.size();
		
		for(int i=0; i< length; i++){
			RuleStatistics stat = pq.remove().getValue();
			out.println(stat);
		}
		out.println();
		out.println();
	}
	
	//TODO: Improve this version.
	public Pair<Integer, Integer> releaseUnder(int percentage){
		PriorityQueue<Entry<String, RuleStatistics>> pq = new PriorityQueue<Map.Entry<String,RuleStatistics>>(100, EntryComparator.ASC);
		pq.addAll(ruleToStatistics.entrySet());
		
		int count = Math.round(((float) this.realCount) * percentage /100);
		
		int length = ruleToStatistics.size();
		
		int totalCount = 0;
		int released = 0;
		
		int currCount = 0;
		for(int i=0; i< length; i++){
			//Take the smallest and remove it from the queue
			Entry<String, RuleStatistics> curr = pq.remove();
			
			currCount = curr.getValue().getCount();
			totalCount += currCount;
			
			if(totalCount <= count){
				ruleToStatistics.remove(curr.getKey());
				released++;
			} else {
				break;
			}
		}
	
		if (totalCount > count) this.realCount -= (totalCount - currCount);
		else this.realCount -= totalCount;
		
		assert realCount > 0; 
		
		return new Pair(released, ruleToStatistics.size());
		
	}
}
