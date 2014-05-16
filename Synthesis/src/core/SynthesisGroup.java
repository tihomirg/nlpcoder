package core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import statistics.HandlerTable;
import statistics.posttrees.Expr;
import definitions.Param;
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

	private List<PartialExpression> resolve(PartialExpression pexp) {
		Param param = pexp.getParam();
		
		PriorityQueue<Expr> queue = handlerTable.get(param.getSearchKey());
		
		return createNewPartialExprs(pexp, param, queue);		
	}

	private List<PartialExpression> createNewPartialExprs(PartialExpression pexp, Param param, PriorityQueue<Expr> queue) {
		List<PartialExpression> list = new LinkedList<PartialExpression>();
		for (Expr expr : queue) {
			list.add(pexp.createPartialExpr(param, expr));
		}
		return list;
	}

	public boolean hasEnded(){
		return pexprs.isEmpty();
	}

}
