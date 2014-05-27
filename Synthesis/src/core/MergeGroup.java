package core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;

import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.comparators.PartialExpressionComparatorDesc;
import util.Pair;

public class MergeGroup implements Callable<PriorityQueue<PartialExpression>>{

	private static final int DEFAULT_CAPACITY = 100;
	private static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();	
	
	private List<PartialExpression> pexprs;
	
	private MergeLevel[] levels;

	private int maxNumOfPexprPerLevel;
	private PartialExpressionScorer scorer;
	
	public MergeGroup(int numOfLevels, int maxNumOfPexprPerLevel, PartialExpressionScorer scorer) {
		this.pexprs = new LinkedList<PartialExpression>();
		this.levels = new MergeLevel[numOfLevels+1];
		this.maxNumOfPexprPerLevel = maxNumOfPexprPerLevel;
		this.scorer = scorer;
	}
	
	public void addPexpr(PartialExpression pexpr) {
		this.pexprs.add(pexpr);
	}

	public PriorityQueue<PartialExpression> call(){
		PriorityQueue<PartialExpression> completed = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
				
		levels[0] = new MergeLevel(0, maxNumOfPexprPerLevel, scorer);
		levels[0].addAll(pexprs);
		
		for(int i = 1; i < this.levels.length; i++){
			Pair<List<PartialExpression>, List<PartialExpression>> newPexprs = levels[i-1].resolveAll();
			completed.addAll(newPexprs.getFirst());
			levels[i] = new MergeLevel(i, maxNumOfPexprPerLevel, scorer);
			levels[i].addAll(newPexprs.getSecond());
		}
		
		return completed;
	}
	
}
