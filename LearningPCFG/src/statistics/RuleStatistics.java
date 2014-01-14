package statistics;

import rules.Rule;

public class RuleStatistics {
	
	private Rule rule;
	private int count;
	
	public RuleStatistics(Rule rule){
		this.rule = rule;
	}
	
	public RuleStatistics(Rule rule, int count){
		this.rule = rule;
		this.count = count;
	}
	
	public void incCount(){
		count++;
	}
	
	public Rule getRule() {
		return rule;
	}

	public int getCount() {
		return count;
	}
}
