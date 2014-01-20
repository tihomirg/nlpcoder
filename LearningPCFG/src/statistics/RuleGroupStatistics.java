package statistics;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import rules.Rule;
import symbol.Symbol;

public class RuleGroupStatistics {

	private static final EntryComparator comparator = new EntryComparator();
	
	private Symbol head;
	private int count; 
	
	private Map<Rule, RuleStatistics> ruleToStatistics = new HashMap<Rule, RuleStatistics>();
	
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
		count++;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void print(PrintStream out){
		StringBuffer sb = new StringBuffer();
		out.println("----------------------------------------------------------------------------------------------------------------------");
		out.println("Head: "+this.count+"  "+this.head);
		out.println();
		for(RuleStatistics stat: ruleToStatistics.values()){
			Rule rule = stat.getRule();
			int count = stat.getCount();
			
			out.println(count+"  "+rule);
		}
		out.println();
		out.println();
	}
	
	//TODO: Improve this version.
	//This one will only remove statistic respect to the overall count, not to the count respect to the current count. 
	public int releaseUnder(int percentage){
		PriorityQueue<Entry<Rule, RuleStatistics>> pq = new PriorityQueue<Map.Entry<Rule,RuleStatistics>>(100, comparator);
		pq.addAll(ruleToStatistics.entrySet());
		
		int count = Math.round(((float) this.count) * percentage /100);
		
		int length = ruleToStatistics.size();
		
		int totalCount = 0;
		int released = 0;
		
		for(int i=0; i< length; i++){
			//Take the smallest and remove it from the queue
			Entry<Rule, RuleStatistics> curr = pq.remove();
			
			int currCount = curr.getValue().getCount();
			totalCount += currCount;
			
			if(totalCount <= count){
				ruleToStatistics.remove(curr.getKey());
				released++;
			} else {
				break;
			}
		}
		return released;
		
	}
}
