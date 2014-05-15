package definitions;

import java.util.Comparator;

public class PartialExpressionComparator implements Comparator<PartialExpression>{

	@Override
	public int compare(PartialExpression o1, PartialExpression o2) {
		double score1 = o1.getScore();
		double score2 = o2.getScore();
		
		if (score1 > score2) return 1;
		else if (score1 < score2) return -1;
		else return 0;
	}

}