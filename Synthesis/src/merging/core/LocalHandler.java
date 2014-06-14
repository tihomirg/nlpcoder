package merging.core;

import java.util.PriorityQueue;

import statistics.posttrees.Expr;
import synthesis.handlers.Handler;
import synthesis.handlers.SearchKey;

public class LocalHandler extends Handler {

	@Override
	public PriorityQueue<Expr> handle(SearchKey key) {
		return null;
	}

	@Override
	public void add(Expr expr) {
	}
}
