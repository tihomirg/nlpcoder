package statistics;

import symbol.Symbol;

public class RuleStatistics {
	
	private String rule;
	private int count;
	
	public RuleStatistics(Symbol symbol){
		this(symbol.toString());
	}
	
	public RuleStatistics(String rule){
		this.rule = rule;
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
