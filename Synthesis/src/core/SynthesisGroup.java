package core;

import java.util.PriorityQueue;

import synthesis.ExprGroup;
import synthesis.PartialExpression;
import synthesis.comparators.PartialExpressionComparatorDesc;

public abstract class SynthesisGroup {
	protected static final int DEFAULT_CAPACITY = 100;
	protected static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();
	
	protected HandlerTable handlerTable;
	protected ExprGroup egroup;
	
	public SynthesisGroup(ExprGroup egroup, HandlerTable handlerTable) {
		this.egroup = egroup;
		this.handlerTable = handlerTable;
	}

	public abstract PriorityQueue<PartialExpression> run();
}
