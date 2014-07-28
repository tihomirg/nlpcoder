package synthesis.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import definitions.Declaration;
import statistics.posttrees.Expr;
import types.Type;

public class HoleHandler extends Handler {

	private Map<String, PriorityQueue<Expr>> pqs; 

	public HoleHandler() {
		this.pqs = new HashMap<String, PriorityQueue<Expr>>();
	}

	@Override
	public PriorityQueue<Expr> handle(SearchKey key) {
		Type retType = key.getType();

		PriorityQueue<Expr> priorityQueue = new PriorityQueue<Expr>(DEFAULT_CAPACITY, COMPARATOR);
		priorityQueue.add(key.getExpr());		

		if (retType != null){
			PriorityQueue<Expr> pq = pqs.get(retType.getPrefix());
			if (pq != null){
				priorityQueue.addAll(pq);
			}
		}

		return priorityQueue;
	}

	private PriorityQueue<Expr> getEnsure(Expr expr) {
		Type retType = expr.getReturnType();

		String prefix = retType.getPrefix();

		if(!pqs.containsKey(prefix)){
			pqs.put(prefix, new PriorityQueue<Expr>(DEFAULT_CAPACITY, COMPARATOR));
		}

		return pqs.get(prefix);
	}

	public void addLocal(Expr expr) {
		PriorityQueue<Expr> pq = getEnsure(expr);
		pq.add(expr);
	}

	@Override
	public void add(Expr expr) {
	}

	public void addAllHoleReplacements(List<Expr> holeReplacements) {
		for (Expr expr : holeReplacements) {
			addLocal(expr);
		}
	}
}
