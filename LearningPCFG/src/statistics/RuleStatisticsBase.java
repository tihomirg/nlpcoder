package statistics;

import java.util.HashMap;
import java.util.Map;

import rules.Rule;
import symbol.Symbol;

public class RuleStatisticsBase {

	private static Map<Symbol, RuleGroupStatistics> headToRuleGroup = new HashMap<Symbol, RuleGroupStatistics>();

	private RuleGroupStatistics getRuleGroupStatistics(Rule rule){
		Symbol head = rule.getHead();
		
		if (!headToRuleGroup.containsKey(head)){
			headToRuleGroup.put(head, new RuleGroupStatistics(head));
		}
		
		return headToRuleGroup.get(head);
	}
	
	public void incCount(Rule rule){
		RuleGroupStatistics group = getRuleGroupStatistics(rule);
		group.incCount(rule);
	}
	
	//TODO: printToFile
	
}
