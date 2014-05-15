package core;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

import statistics.HandlerTable;
import definitions.PartialExpression;
import definitions.PartialExpressionComparator;

public class SynthesisGroup {
	private static final int DEFAULT_CAPACITY = 100;
	private HandlerTable handlerTable;
	private static final PartialExpressionComparator COMPARATOR = new PartialExpressionComparator();
	private PriorityQueue<PartialExpression> pexprs = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
	private int steps;


	public SynthesisGroup(PartialExpression expr, HandlerTable handlerTable, int steps) {
		this.handlerTable = handlerTable;
		this.pexprs = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
		this.pexprs.add(expr);
		this.steps = steps;
	}

	public void run(){
		for (int i = 0; i < steps && !pexprs.isEmpty(); i++) {
			pexprs.addAll(resolve(pexprs.remove()));
		}
	}

	private List<PartialExpression> resolve(PartialExpression remove) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasEnded(){
		return pexprs.isEmpty();
	}

}
