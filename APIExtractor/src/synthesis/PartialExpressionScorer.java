package synthesis;

import java.util.HashSet;
import java.util.List;

import statistics.posttrees.Expr;

public class PartialExpressionScorer {

	private double connectionReward;
	private double connectionPenalty;
	private int inputSize;
	private double sizePenalty;

	public PartialExpressionScorer(double connectionReward, double connectionPenalty, int inputSize, double sizePenalty) {
		this.connectionReward = connectionReward;
		this.connectionPenalty = connectionPenalty;
		this.sizePenalty = sizePenalty;
		this.inputSize = inputSize;
	}
	
	public void calculetScore(PartialExpression newPexpr, Expr expr, Param oldParam, List<Connection> connections, List<Param> newParams) {
		newPexpr.setScore(newPexpr.getScore() + expr.getLogProbability());		
	}

	public void calculetScore(PartialExpression newPexpr, PartialExpression pexpr) {
		HashSet<Integer> connectedTo = newPexpr.getConnectedTo();
		int groupIndex = pexpr.getGroupIndex();
		if (connectedTo.contains(groupIndex)){
			newPexpr.setScore(newPexpr.getScore() + pexpr.getScore() - this.connectionPenalty);
		} else {
			connectedTo.add(groupIndex);
			newPexpr.setScore(newPexpr.getScore() + pexpr.getScore() + this.connectionReward);
		}
		
		int size = newPexpr.getSize() + pexpr.getSize();
		if (size > inputSize){
			newPexpr.setScore(newPexpr.getScore() - this.sizePenalty * (size - inputSize));
		}
	}
}
