package core;

import java.util.List;
import java.util.PriorityQueue;

import comparators.PartialExpressionComparatorDesc;

import synthesis.PartialExpression;

public abstract class SynthesisGroup {
	protected static final int DEFAULT_CAPACITY = 100;
	protected static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();
	
	protected HandlerTable handlerTable;
	protected List<PartialExpression> pexprs;
	
	public SynthesisGroup(List<PartialExpression> pexprs, HandlerTable handlerTable) {
		this.pexprs = pexprs;
		this.handlerTable = handlerTable;
	}

	public abstract PriorityQueue<PartialExpression> run();
}
