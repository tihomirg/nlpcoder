package statistics;

import rules.Rule;
import symbol.Symbol;

public class RuleStatistics {
	
	private String rule;
	private int count;
	
	public RuleStatistics(Symbol symbol){
		this.rule = symbol.toString();
	}
	
	public void incCount(){
		count++;
	}
	
	public int getCount() {
		return count;
	}

	public String toString() {
		return count+"  "+rule;
	}
}
