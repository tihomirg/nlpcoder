package core;

import synthesis.PartialExpression;

public class SimpleGroupBuilder extends GroupBuilder<SimpleSynthesisGroup>{

	private HandlerTable handlerTable;
	private int steps;

	public SimpleGroupBuilder(HandlerTable handlerTable, int steps) {
		this.handlerTable = handlerTable;
		this.steps = steps;
	}

	@Override
	public SimpleSynthesisGroup build(PartialExpression pexpr) {
		return new SimpleSynthesisGroup(pexpr, handlerTable, steps);
	}

}
