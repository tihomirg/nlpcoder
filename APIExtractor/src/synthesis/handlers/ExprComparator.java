package synthesis.handlers;

import java.util.Comparator;

import statistics.posttrees.Expr;

public class ExprComparator implements Comparator<Expr> {

	@Override
	public int compare(Expr o1, Expr o2) {
		double f1 = o1.getLogProbability();
		double f2 = o2.getLogProbability();
		
		if (f1 > f2) return -1;
		else if (f2 > f1) return 1;
		else return 0;		
	}

}
