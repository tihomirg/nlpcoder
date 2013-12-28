package statistics;

import java.util.HashMap;
import java.util.Map;

import rules.Rule;
import symbol.Symbol;

public class RuleStatisticsBase {

	private static Map<Symbol, RuleGroupStatistics> headToRuleGroup = new HashMap<Symbol, RuleGroupStatistics>();

	private RuleGroupStatistics getRuleGroupStatistics(Rule rule){
		
		//TODO: Maybe we should group all symbols based on primary head non-terminal, not only based on non-terminal^parent^grandparent.
		//This way, we will have a better distribution.
		//This can be delay this until collecting statistics.
		Symbol head = rule.getHead();
		
		if (!headToRuleGroup.containsKey(head)){
			headToRuleGroup.put(head, new RuleGroupStatistics(head));
		}
		
		return headToRuleGroup.get(head);
	}
	
	public void inc(Rule rule){
		RuleGroupStatistics group = getRuleGroupStatistics(rule);
		group.inc(rule);
	}
	
	//TODO: printToFile
	
}
