package synthesis.handlers;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import statistics.posttrees.Expr;

public class LiteralHandler extends Handler {

	private List<Expr> literals; 

	public LiteralHandler() {
		this.literals = new LinkedList<Expr>();
	}

	@Override
	public PriorityQueue<Expr> handle(SearchKey key) {
		PriorityQueue<Expr> priorityQueue = new PriorityQueue<Expr>(DEFAULT_CAPACITY, COMPARATOR);
		priorityQueue.add(key.getExpr());
		priorityQueue.addAll(literals);
		return priorityQueue;
	}

	public void addLiteral(Expr expr) {
		literals.add(expr);
	}

	@Override
	public void add(Expr expr) {
	}

	public void addAllLiterals(List<Expr> literal) {
		for (Expr expr : literal) {
			addLiteral(expr);
		}
	}
}
