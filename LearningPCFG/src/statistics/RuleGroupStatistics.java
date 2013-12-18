package statistics;

import java.util.HashMap;
import java.util.Map;

import rules.Rule;
import symbol.Symbol;

public class RuleGroupStatistics {

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
}
