package core;

import synthesis.PartialExpression;

public class SaturationGroupBuilder extends GroupBuilder<SaturationSynthesisGroup>{

	private HandlerTable handlerTable;
	private int numOfLevels;
	private int maxNumOfPexprPerLevel;
	
	public SaturationGroupBuilder(HandlerTable handlerTable, int numOfLevels, int maxNumOfPexprPerLevel) {
		this.handlerTable = handlerTable;
		this.numOfLevels = numOfLevels;
		this.maxNumOfPexprPerLevel = maxNumOfPexprPerLevel;
	}

	@Override
	public SaturationSynthesisGroup build(PartialExpression pexpr) {
		return new SaturationSynthesisGroup(pexpr, handlerTable, numOfLevels, maxNumOfPexprPerLevel);
	}

}
