package search.comparators;

import java.util.Comparator;

import search.DeclarationSelectionEntry;
import search.RichDeclaration;

public class RichDeclarationComparatorAsce implements Comparator<RichDeclaration>{

	@Override
	public int compare(RichDeclaration rd1, RichDeclaration rd2) {
		double score1 = rd1.getScore().getSum();
		double score2 = rd2.getScore().getSum();
		
		if (score1 > score2) return 1;
		else if (score1 < score2) return -1;
		else return 0;
	}
}
