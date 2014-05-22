package core;

import java.util.List;
import java.util.PriorityQueue;

import synthesis.PartialExpression;
import util.Pair;

import comparators.PartialExpressionComparatorDesc;

public class SaturationSynthesisGroup extends SynthesisGroup {

	private static final int DEFAULT_CAPACITY = 100;
	private static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();

	private int maxNumOfPexprPerLevel;
	private SynthesisLevel[] levels;

	public SaturationSynthesisGroup(PartialExpression pexpr, HandlerTable handlerTable, int numOfLevels, int maxNumOfPexprPerLevel) {
		super(pexpr, handlerTable);
		this.maxNumOfPexprPerLevel = maxNumOfPexprPerLevel;
		this.levels = new SynthesisLevel[numOfLevels+1];
	}

	public PriorityQueue<PartialExpression> run(){
		PriorityQueue<PartialExpression> completed = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);

		if (pexpr.isCompleted()){
			completed.add(pexpr);
		} else {
			
			levels[0] = new SynthesisLevel(0, handlerTable, maxNumOfPexprPerLevel);
			levels[0].add(pexpr);

			for(int i = 1; i < this.levels.length; i++){
				Pair<List<PartialExpression>, List<PartialExpression>> newPexprs = levels[i-1].resolveAll();
				completed.addAll(newPexprs.getFirst());
				levels[i] = new SynthesisLevel(i, handlerTable, maxNumOfPexprPerLevel);
				levels[i].addAll(newPexprs.getSecond());
			}
		}

		return completed;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SynthesisLevel level: levels) {
			sb.append(level+"\n\n");
		}
		return sb.toString();
	}
}
