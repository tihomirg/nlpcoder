package core;

import java.util.List;

import synthesis.ExprGroup;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;

public class SaturationGroupBuilder extends GroupBuilder<SaturationSynthesisGroup>{

	private HandlerTable handlerTable;
	private int numOfLevels;
	private int maxNumOfPexprPerLevel;
	private PartialExpressionScorer scorer;
	
	public SaturationGroupBuilder(HandlerTable handlerTable, PartialExpressionScorer scorer, int numOfLevels, int maxNumOfPexprPerLevel) {
		this.handlerTable = handlerTable;
		this.scorer = scorer;
		this.numOfLevels = numOfLevels;
		this.maxNumOfPexprPerLevel = maxNumOfPexprPerLevel;
	}

	@Override
	public SaturationSynthesisGroup build(ExprGroup egroup) {
		return new SaturationSynthesisGroup(egroup, handlerTable, numOfLevels, maxNumOfPexprPerLevel, scorer);
	}

}
