package statistics;

import java.util.Comparator;
import java.util.Map.Entry;

import rules.Rule;

public class EntryComparator implements Comparator<Entry<Rule, RuleStatistics>>
{
    @Override
    public int compare(Entry<Rule, RuleStatistics> e1, Entry<Rule, RuleStatistics> e2)
    {
    	int x = e1.getValue().getCount();
    	int y = e2.getValue().getCount();
    	
        if (x < y)
        {
            return -1;
        }
        if (x > y)
        {
            return 1;
        }
        return 0;
    }
}	