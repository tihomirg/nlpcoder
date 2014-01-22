package statistics;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import rules.Rule;
import symbol.Symbol;
import util.Pair;

public class RuleGroupStatistics {
	
	private Symbol head;
	private int totalCount; 
	
	private Map<Rule, RuleStatistics> ruleToStatistics = new HashMap<Rule, RuleStatistics>();

	private int realCount;
	
	public RuleGroupStatistics(Symbol head) {
		this.head = head;
	}
	
	private RuleStatistics getRuleStatistics(Rule rule){
		if(!ruleToStatistics.containsKey(rule)){
			ruleToStatistics.put(rule, new RuleStatistics(rule));
		}
		
		return ruleToStatistics.get(rule);
	}
	
	public void inc(Rule rule){
		RuleStatistics statistics = getRuleStatistics(rule);
		statistics.incCount();
		this.incCount();
	}
	
	public Symbol getHead() {
		return head;
	}

	public void setHead(Symbol head) {
		this.head = head;
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
		
		PriorityQueue<Entry<Rule, RuleStatistics>> pq = new PriorityQueue<Map.Entry<Rule,RuleStatistics>>(100, EntryComparator.DESC);
		pq.addAll(ruleToStatistics.entrySet());		
		
		int length = ruleToStatistics.size();
		
		for(int i=0; i< length; i++){
			RuleStatistics stat = pq.remove().getValue();
			Rule rule = stat.getRule();
			int count = stat.getCount();
			
			out.println(count+"  "+rule);
		}
		out.println();
		out.println();
	}
	
	//TODO: Improve this version.
	//This one will only remove statistic respect to the overall count, not to the count respect to the current count. 
	public Pair<Integer, Integer> releaseUnder(int percentage){
		PriorityQueue<Entry<Rule, RuleStatistics>> pq = new PriorityQueue<Map.Entry<Rule,RuleStatistics>>(100, EntryComparator.ASC);
		pq.addAll(ruleToStatistics.entrySet());
		
		int count = Math.round(((float) this.realCount) * percentage /100);
		
		int length = ruleToStatistics.size();
		
		int totalCount = 0;
		int released = 0;
		
		int currCount = 0;
		for(int i=0; i< length; i++){
			//Take the smallest and remove it from the queue
			Entry<Rule, RuleStatistics> curr = pq.remove();
			
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
