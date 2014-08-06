package synthesis.handlers;

import java.util.Comparator;

import statistics.posttrees.Expr;

public class ExprComparator implements Comparator<Expr> {

	@Override
	public int compare(Expr o1, Expr o2) {
		double f1 = o1.getScore();
		double f2 = o2.getScore();
		
		if (f1 > f2) return -1;
		else if (f2 > f1) return 1;
		else return 0;		
	}

}
