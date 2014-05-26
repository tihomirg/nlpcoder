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

	public SaturationSynthesisGroup(List<ExprGroup> egroups, HandlerTable handlerTable, int numOfLevels, int maxNumOfPexprPerLevel, PartialExpressionScorer scorer) {
		super(egroups, handlerTable);
		this.maxNumOfPexprPerLevel = maxNumOfPexprPerLevel;
		this.levels = new SynthesisLevel[numOfLevels+1];
		this.scorer = scorer;
	}
	
	private List<PartialExpression> createPartialExprs() {
		List<PartialExpression> list = new LinkedList<PartialExpression>();
		
		for (ExprGroup egroup: this.egroups) {
			list.add(egroup.createPartialExpr());
		}
		
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SynthesisLevel level: levels) {
			sb.append(level+"\n\n");
		}
		return sb.toString();
	}

	@Override
	public PriorityQueue<PartialExpression> call() throws Exception {
		PriorityQueue<PartialExpression> completed = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
		List<PartialExpression> starters = new LinkedList<PartialExpression>();

		List<PartialExpression> pexprs = createPartialExprs();
		for (PartialExpression pexpr : pexprs) {
			if (pexpr.isCompleted()){
				completed.add(pexpr);
			} else {
				starters.add(pexpr);
			}
		}
		
		if (!starters.isEmpty()){
			
			levels[0] = new SynthesisLevel(0, handlerTable, maxNumOfPexprPerLevel, scorer);
			levels[0].addAll(starters);

			for(int i = 1; i < this.levels.length; i++){
				Pair<List<PartialExpression>, List<PartialExpression>> newPexprs = levels[i-1].resolveAll();
				completed.addAll(newPexprs.getFirst());
				levels[i] = new SynthesisLevel(i, handlerTable, maxNumOfPexprPerLevel, scorer);
				levels[i].addAll(newPexprs.getSecond());
			}
		}

		return completed;
	}
}
