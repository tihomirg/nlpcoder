package core;

import java.util.PriorityQueue;

import statistics.HandlerTable;

import definitions.PartialExpression;
import definitions.PartialExpressionComparator;

public class Synthesis {
	
	private static final int DEFAULT_CAPACITY = 100;

	private HandlerTable handlerTable;

	private static final PartialExpressionComparator COMPARATOR = new PartialExpressionComparator();
	
	public Synthesis(HandlerTable handlerTable) {
		this.handlerTable = handlerTable;
	}

	public void run(){
		
//		PartialExpression init_expr = new PartialExpression(1.0);
//		PriorityQueue<PartialExpression> exprs = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
//		
//		exprs.add(init_expr);
//		
//		while (!exprs.isEmpty()) {
//			PartialExpression expr = exprs.remove();
//			
//			handlerTable.get(expr.get)
//		}
	}
}
