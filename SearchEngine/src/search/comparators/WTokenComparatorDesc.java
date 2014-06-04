package search.comparators;

import java.util.Comparator;

import search.RichDeclaration;
import search.WToken;

public class WTokenComparatorDesc implements Comparator<WToken>{

	@Override
	public int compare(WToken wt1, WToken wt2) {
		double score1 = wt1.getScore();
		double score2 = wt2.getScore();
		
		if (score1 > score2) return -1;
		else if (score1 < score2) return 1;
		else return 0;
	}
}
