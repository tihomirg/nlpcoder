package core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import statistics.posttrees.Expr;
import synthesis.Param;
import synthesis.PartialExpression;
import synthesis.handlers.Handler;
import synthesis.handlers.SearchKey;
import util.Pair;

public class SimpleSynthesisGroup extends SynthesisGroup {
	private PriorityQueue<PartialExpression> pexprs;
	private PriorityQueue<PartialExpression> cpexprs;
	private int steps;
	
	public SimpleSynthesisGroup(PartialExpression pexpr, HandlerTable handlerTable, int steps) {
		super(pexpr, handlerTable);
		this.pexprs = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
		this.cpexprs = new PriorityQueue<PartialExpression>(DEFAULT_CAPACITY, COMPARATOR);
		this.steps = steps;
	}

	public PriorityQueue<PartialExpression> run(){
		if(pexpr.isCompleted()) this.cpexprs.add(pexpr);
		else this.pexprs.add(pexpr);
		
		for (int i = 0; i < steps && !pexprs.isEmpty(); i++) {
			Pair<List<PartialExpression>, List<PartialExpression>> newPexps = resolve(pexprs.remove());
			
			cpexprs.addAll(newPexps.getFirst());
			pexprs.addAll(newPexps.getSecond());
		}
			
		print();
		
		System.out.println("Number of partial expressions: "+pexprs.size());
		
		return cpexprs;
	}

	private void print() {
		System.out.println("Partial: ");
		for (PartialExpression pexpr : pexprs) {
			System.out.println(pexpr);
		}
		
		System.out.println("Completed: ");
		for (PartialExpression pexpr : cpexprs) {
			System.out.println(pexpr);
		}		
	}

	private Pair<List<PartialExpression>, List<PartialExpression>> resolve(PartialExpression pexp) {
		System.out.println(pexp);
		
		Param param = pexp.getParam();
		
		SearchKey searchKey = param.getSearchKey();
		Handler handler = searchKey.getHandler(handlerTable);
		
		PriorityQueue<Expr> queue = handler.handle(searchKey);
		
		return createNewPartialExprs(pexp, param, queue);		
	}

	private Pair<List<PartialExpression>,List<PartialExpression>> createNewPartialExprs(PartialExpression pexp, Param param, PriorityQueue<Expr> queue) {
		List<PartialExpression> noncompleted = new LinkedList<PartialExpression>();
		List<PartialExpression> completed = new LinkedList<PartialExpression>();
		for (Expr expr : queue) {
			PartialExpression newPexp = pexp.instantiate(param, expr);
			if(newPexp.isCompleted()) {
				completed.add(newPexp);
			} else {
				noncompleted.add(newPexp);				
			}
		}
		
		return new Pair<List<PartialExpression>,List<PartialExpression>>(completed, noncompleted);
	}

	public boolean hasEnded(){
		return pexprs.isEmpty();
	}

}
