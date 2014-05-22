package synthesis;

import java.util.List;

import statistics.posttrees.Expr;

public class PartialExpressionScorer {

	public void calculetScore(PartialExpression newPexpr, Expr expr, Param oldParam, List<Param> newParams) {
		newPexpr.setScore(newPexpr.getScore() + expr.getLogProbability());		
	}

}
