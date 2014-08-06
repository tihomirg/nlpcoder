package synthesis.comparators;

import java.util.Comparator;

import synthesis.PartialExpression;

public class PartialExpressionComparatorAsce  implements Comparator<PartialExpression>{
	@Override
	public int compare(PartialExpression o1, PartialExpression o2) {
		double score1 = o1.getScore().getValue();
		double score2 = o2.getScore().getValue();
		
		if (score1 > score2) return 1;
		else if (score1 < score2) return -1;
		else return 0;
	}
}
