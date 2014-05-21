package synthesis.handlers;

import java.util.PriorityQueue;
import statistics.posttrees.Expr;

public class LocalsHandler extends Handler {

	@Override
	public PriorityQueue<Expr> handle(SearchKey key) {	
		PriorityQueue<Expr> priorityQueue = new PriorityQueue<Expr>();
		
		System.out.println("Hoooooooooooooooooooooooooooooooooooooool");
		
		priorityQueue.add(key.getExpr());
		
		return priorityQueue;
	}

	@Override
	public void add(Expr expr) {

	}
}
