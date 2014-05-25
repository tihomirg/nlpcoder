package core;

import java.util.List;
import java.util.PriorityQueue;

import synthesis.ExprGroup;
import synthesis.PartialExpression;
import synthesis.comparators.PartialExpressionComparatorDesc;

public abstract class SynthesisGroup {
	protected static final int DEFAULT_CAPACITY = 100;
	protected static final PartialExpressionComparatorDesc COMPARATOR = new PartialExpressionComparatorDesc();
	
	protected HandlerTable handlerTable;
	protected List<ExprGroup> egroups;
	
	public SynthesisGroup(List<ExprGroup> egroups, HandlerTable handlerTable) {
		this.egroups = egroups;
		this.handlerTable = handlerTable;
	}

	public abstract PriorityQueue<PartialExpression> run();
}
