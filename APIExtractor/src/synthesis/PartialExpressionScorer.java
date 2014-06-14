package synthesis;

import java.util.HashSet;
import java.util.List;

import statistics.posttrees.Expr;

public class PartialExpressionScorer {

	private double connectionReward;
	private double connectionPenalty;

	public PartialExpressionScorer(double connectionReward, double connectionPenalty) {
		this.connectionReward = connectionReward;
		this.connectionPenalty = connectionPenalty;
	}
	
	public void calculetScore(PartialExpression newPexpr, Expr expr, Param oldParam, List<Connection> connections, List<Param> newParams) {
		newPexpr.setScore(newPexpr.getScore() + expr.getLogProbability());		
	}

	public void calculetScore(PartialExpression newPexpr, PartialExpression pexpr) {
//		newPexpr.setScore(newPexpr.getScore() + pexpr.getScore() + this.connectionReward);
		
		HashSet<Integer> connectedTo = newPexpr.getConnectedTo();
		int groupIndex = pexpr.getGroupIndex();
		if (connectedTo.contains(groupIndex)){
			newPexpr.setScore(newPexpr.getScore() - this.connectionPenalty);
		} else {
			connectedTo.add(groupIndex);
			newPexpr.setScore(newPexpr.getScore() + pexpr.getScore() + this.connectionReward);
		}
	}
}
