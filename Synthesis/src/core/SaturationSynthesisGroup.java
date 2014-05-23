package core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import synthesis.ExprGroup;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.comparators.PartialExpressionComparatorDesc;
import util.Pair;


public class SaturationSynthesisGroup extends SynthesisGroup {

	private static final int DEFAULT_CAPACITY = 100;
	private static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();

	private int maxNumOfPexprPerLevel;
	private SynthesisLevel[] levels;
	private PartialExpressionScorer scorer;

	public SaturationSynthesisGroup(ExprGroup egroup, HandlerTable handlerTable, int numOfLevels, int maxNumOfPexprPerLevel, PartialExpressionScorer scorer) {
		super(egroup, handlerTable);
		this.maxNumOfPexprPerLevel = maxNumOfPexprPerLevel;
		this.levels = new SynthesisLevel[numOfLevels+1];
		this.scorer = scorer;
	}

	public PriorityQueue<PartialExpression> run(){
		PriorityQueue<PartialExpression> completed = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
		List<PartialExpression> starters = new LinkedList<PartialExpression>();

		List<PartialExpression> pexprs = egroup.createPartialExprs();
		for (PartialExpression pexpr : pexprs) {
			if (pexpr.isCompleted()){
				completed.add(pexpr);
			} else {
				starters.add(pexpr);
			}
		}
		
		if (!starters.isEmpty()){
			
			levels[0] = new SynthesisLevel(0, handlerTable, egroup, maxNumOfPexprPerLevel, scorer);
			levels[0].addAll(starters);

			for(int i = 1; i < this.levels.length; i++){
				Pair<List<PartialExpression>, List<PartialExpression>> newPexprs = levels[i-1].resolveAll();
				completed.addAll(newPexprs.getFirst());
				levels[i] = new SynthesisLevel(i, handlerTable, egroup, maxNumOfPexprPerLevel, scorer);
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
