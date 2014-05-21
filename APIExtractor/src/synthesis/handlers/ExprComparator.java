package synthesis.handlers;

import java.util.Comparator;

import statistics.posttrees.Expr;

public class ExprComparator implements Comparator<Expr> {

	@Override
	public int compare(Expr o1, Expr o2) {
		int f1 = o1.getFrequency();
		int f2 = o2.getFrequency();
		return f1 - f2;
	}

}
