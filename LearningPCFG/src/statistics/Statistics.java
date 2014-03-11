package statistics;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import rules.Rule;
import symbol.Symbol;
import util.Pair;

public class Statistics {

	private static Map<String, RuleGroupStatistics> headToRuleGroup = new HashMap<String, RuleGroupStatistics>();

	private RuleGroupStatistics getRuleGroupStatistics(Symbol symbol){
		String headString = symbol.head();
		
		if (!headToRuleGroup.containsKey(headString)){
			headToRuleGroup.put(headString, new RuleGroupStatistics(headString));
		}
		
		return headToRuleGroup.get(headString);
	}
	
	public void inc(Symbol symbol){
		RuleGroupStatistics group = getRuleGroupStatistics(symbol);
		group.inc(symbol);
	}
	
	public void print(PrintStream out){
		for(RuleGroupStatistics stat: this.headToRuleGroup.values()){
			stat.print(out);
		}
	}

	public void incCounter(Symbol symbol) {
		RuleGroupStatistics group = getRuleGroupStatistics(symbol);
		group.incCount();
	}
	
	public void releaseUnder(int percentage){
		int released = 0;
		int remaining = 0;
		for(RuleGroupStatistics group: headToRuleGroup.values()){
			Pair<Integer, Integer> pair = group.releaseUnder(percentage);
			released += pair.getFirst();
			remaining += pair.getSecond();
		}
		
		System.out.println("Released: "+released+" ruels.  Remaining: "+remaining+" rules");
	}
}
