package synthesis.handlers;

import java.util.PriorityQueue;

import statistics.posttrees.Expr;

public class LiteralHandler extends Handler {

	@Override
	public PriorityQueue<Expr> handle(SearchKey key) {
		// TODO Auto-generated method stub
		PriorityQueue<Expr> priorityQueue = new PriorityQueue<Expr>();
		
		priorityQueue.add(key.getExpr());
		
		return priorityQueue;
	}

	@Override
	public void add(Expr expr) {
		// TODO Auto-generated method stub

	}

}
