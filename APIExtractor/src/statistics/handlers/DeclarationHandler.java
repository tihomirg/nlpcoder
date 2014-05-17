package statistics.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import statistics.posttrees.Expr;

public class DeclarationHandler extends Handler {

	private Map<Integer, PriorityQueue<Expr>> pqs; 
	
	public DeclarationHandler() {
		this.pqs = new HashMap<Integer, PriorityQueue<Expr>>();
	}
	
	@Override
	public PriorityQueue<Expr> handle(SearchKey key) {
		return pqs.get(key.getExpr().getDecl().getId());
	}
	
	private PriorityQueue<Expr> getEnsure(Expr expr) {
		int id = expr.getDecl().getId();
		
		if(!pqs.containsKey(id)){
			pqs.put(id, new PriorityQueue<Expr>(DEFAULT_CAPACITY, COMPARATOR));
		}
		
		return pqs.get(id);
	}
	
	public void add(Expr expr) {
		PriorityQueue<Expr> pq = getEnsure(expr);
		pq.add(expr);
	}
}
