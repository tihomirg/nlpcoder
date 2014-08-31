package synthesis.handlers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;

import definitions.Declaration;
import statistics.posttrees.Expr;
import types.StabileTypeFactory;
import types.Type;
import types.Unifier;

public class HoleHandler extends Handler {

	private List<Expr> exprs;
	private double holeWeight; 
	private StabileTypeFactory stf;

	public HoleHandler(double holeWeight, StabileTypeFactory stf) {
		this.holeWeight = holeWeight;
		this.stf = stf;
		this.exprs = new LinkedList<Expr>();
	}

	@Override
	public PriorityQueue<Expr> handle(SearchKey key) {
		Type retType = key.getType();

		PriorityQueue<Expr> priorityQueue = new PriorityQueue<Expr>(DEFAULT_CAPACITY, COMPARATOR);
		Expr expr = key.getExpr();
		expr.setScore(holeWeight);
		priorityQueue.add(expr);

		if (retType != null){
			priorityQueue.addAll(findAllCompatible(retType));
		}

		return priorityQueue;
	}

	private List<Expr> findAllCompatible(Type retType) {
		List<Expr> list = new LinkedList<Expr>();
		for (Expr expr: exprs) {
			Unifier unifier = retType.checkCompatible(expr.getReturnType(), stf);
			if(unifier.isSuccess()) list.add(expr);
		}
		return list;
	}

	@Override
	public void add(Expr expr) {
	}

	public void addAllHoleReplacements(List<Expr> holeReplacements) {
		exprs.addAll(holeReplacements);
	}
}
