package statistics.handlers;

import java.util.PriorityQueue;

import statistics.ExprComparator;
import statistics.posttrees.Expr;

public abstract class Handler {

	protected static final ExprComparator COMPARATOR = new ExprComparator();
	protected static final int DEFAULT_CAPACITY = 100;
	protected static final PriorityQueue<Expr> EMPTY_PQ = new PriorityQueue<Expr>();
	
	public abstract PriorityQueue<Expr> handle(Expr expr); 
	public abstract void add(Expr expr);

}