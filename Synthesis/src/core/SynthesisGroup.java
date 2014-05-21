package core;

import java.util.PriorityQueue;

import comparators.PartialExpressionComparatorDesc;

import synthesis.PartialExpression;

public abstract class SynthesisGroup {
	protected static final int DEFAULT_CAPACITY = 100;
	protected static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();
	
	protected HandlerTable handlerTable;
	protected PartialExpression pexpr;
	
	public SynthesisGroup(PartialExpression pexp, HandlerTable handlerTable) {
		this.pexpr = pexp;
		this.handlerTable = handlerTable;
	}

	public abstract PriorityQueue<PartialExpression> run();
}
