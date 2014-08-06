package synthesis;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import search.config.SearchConfig;
import statistics.posttrees.Expr;
import statistics.posttrees.InputExpr;

public class PartialExpressionScorer {

	private double mergeReward;
	private double badMergePenalty;
	private int mergedSize;
	private double mergedSizePenalty;
	private int individualSize;
	private double individualSizePenalty;
	private double inputExprRepetitionPenalty;

	public PartialExpressionScorer(int mergedSize) {
		this.mergeReward = SearchConfig.getPartialExpressionMergeReward();
		this.badMergePenalty = SearchConfig.getPartialExpressionBadMergePenalty();
		this.inputExprRepetitionPenalty = SearchConfig.getInputExprRepetitionPenalty();
		this.mergedSizePenalty = SearchConfig.getPartialExpressionMergedSizePenalty();
		this.mergedSize = (mergedSize / 2) + 1;
		this.individualSize = SearchConfig.getPartialExpressionIndividualSize();
		this.individualSizePenalty = SearchConfig.getPartialExpressionIndividualSizePenalty();
	}

	public void calculetScore(PartialExpression newPexpr, Expr expr, Param oldParam, List<Connection> connections, List<Param> newParams) {
		PartialExpressionScore score = newPexpr.getScore();
		score.addCompositionScore(expr.getScore());

		int size = newPexpr.getSize();
		if(size + 1 > this.individualSize){
			int deltaSize = (size + 1) - this.individualSize;
			score.addIndividualSizeScore(-(Math.pow(2, deltaSize) * this.individualSizePenalty));
		}

		if (expr.isInputExpr()){
			LinkedList<InputExpr> inputExprs = newPexpr.getInputExprs();
			if(inputExprs.contains(expr)){
				score.addInputExprScore(-this.inputExprRepetitionPenalty);
			}
		}
	}

	public void calculetScore(PartialExpression newPexpr, PartialExpression pexpr) {
		PartialExpressionScore score = mergeScores(newPexpr, pexpr);

		LinkedList<Integer> connectedTo = newPexpr.getConnectedTo();
		if (connectedTo.contains(pexpr.getGroupIndex())){
			score.addMergeScore(-this.badMergePenalty);
		} else {
			score.addMergeScore(this.mergeReward);
			
			int size = connectedTo.size();
			if (size + 1> this.mergedSize){
				int deltaSize = (size + 1) - this.mergedSize;
				score.addMergeSizeScore(-(Math.pow(2, deltaSize) * this.mergedSizePenalty));
			}
		}
		
		LinkedList<InputExpr> inputExprs = newPexpr.getInputExprs();
		for (InputExpr expr: pexpr.getInputExprs()) {
			if(inputExprs.contains(expr)){
				score.addInputExprScore(-this.inputExprRepetitionPenalty);
			}
		}
		
		newPexpr.setScore(score);
	}

	private PartialExpressionScore mergeScores(PartialExpression newPexpr, PartialExpression pexpr) {
		PartialExpressionScore score = new PartialExpressionScore();
		score.add(newPexpr.getScore());
		score.add(pexpr.getScore());
		return score;
	}
}
