package statistics;

import java.util.Comparator;
import java.util.Map.Entry;

import rules.Rule;

public class EntryComparator implements Comparator<Entry<String, RuleStatistics>>
{
	private static interface CmpStragegy {
		public int cmp(int x, int y);
	}
	
	private static class AscStrategy implements CmpStragegy{

		@Override
		public int cmp(int x, int y) {
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
	
	private static class DescStrategy implements CmpStragegy{

		@Override
		public int cmp(int x, int y) {
	        if (x < y)
	        {
	            return 1;
	        }
	        if (x > y)
	        {
	            return -1;
	        }
	        return 0;
		}
		
	}	

	private CmpStragegy strategy;
	
	public static final EntryComparator ASC = new EntryComparator(new AscStrategy());
	public static final EntryComparator DESC = new EntryComparator(new DescStrategy());	
	
	public EntryComparator(CmpStragegy strategy){
		this.strategy = strategy;
	}
	
    @Override
    public int compare(Entry<String, RuleStatistics> e1, Entry<String, RuleStatistics> e2)
    {
    	int x = e1.getValue().getCount();
    	int y = e2.getValue().getCount();
    	
    	return strategy.cmp(x, y);
    }
}	