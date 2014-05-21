package core;

import java.util.List;
import java.util.PriorityQueue;

import synthesis.PartialExpression;
import util.Pair;

import comparators.PartialExpressionComparatorDesc;

public class SaturationSynthesisGroup extends SynthesisGroup {

	private static final int DEFAULT_CAPACITY = 100;
	private static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();

	private int numOfLevels;
	private int maxNumOfPexprPerLevel;

	public SaturationSynthesisGroup(PartialExpression pexpr, HandlerTable handlerTable, int numOfLevels, int maxNumOfPexprPerLevel) {
		super(pexpr, handlerTable);
		this.numOfLevels = numOfLevels;
		this.maxNumOfPexprPerLevel = maxNumOfPexprPerLevel;
	}

	public PriorityQueue<PartialExpression> run(){
		PriorityQueue<PartialExpression> completed = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);

		if (pexpr.isCompleted()){
			completed.add(pexpr);
		} else {
			SynthesisLevel level = new SynthesisLevel(handlerTable, maxNumOfPexprPerLevel);
			level.add(pexpr);

			for(int i = 0; i< this.numOfLevels; i++){
				Pair<List<PartialExpression>, List<PartialExpression>> newPexprs = level.resolveAll();
				completed.addAll(newPexprs.getFirst());
				level = new SynthesisLevel(handlerTable, maxNumOfPexprPerLevel);
				level.addAll(newPexprs.getSecond());
			}
		}

		return completed;
	}
}
