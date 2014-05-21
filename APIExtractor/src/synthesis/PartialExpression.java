package synthesis;

import java.util.LinkedList;
import java.util.List;

import statistics.posttrees.Expr;
import types.Substitution;

public class PartialExpression implements Cloneable {

	private LinkedList<Param> params;
	private LinkedList<Substitution> subs;
	private Representation rep;
	private double score = 1.0;
	
	public PartialExpression(Param param, double score) {
		this.params = new LinkedList<Param>();
		this.params.add(param);
		
		this.score = score;
		this.subs = new LinkedList<Substitution>();
		this.rep = new Representation();
	}

//	public PartialExpression(double score) {
//		this()
//	}
	
	public double getScore() {
		return score;
	}
	
	public Param getParam() {
		return params.get(0);
	}

	@Override
	public PartialExpression clone() {
		PartialExpression exp = null;
		try {
			exp = (PartialExpression) super.clone();
			exp.rep = this.rep.clone();
			exp.subs = (LinkedList<Substitution>) this.subs.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return exp;
	}

	public boolean isComplete() {
		return subs.isEmpty();
	}
	
	@Override
	public String toString() {
		return rep.toString();
	}

	public PartialExpression createPartialExpr(Param param, Expr expr) {
		PartialExpression newPexpr = this.clone();
		List<Param> params = newPexpr.getRep().substitute(param, expr);
		
		newPexpr.removeParam(param);
		newPexpr.addAllParams(params);
		
		return newPexpr;
	}

	private void addAllParams(List<Param> params) {
		this.params.addAll(params);
	}

	private Representation getRep() {
		return this.rep;
	}

	private void removeParam(Param param) {
		params.remove(param);
	}
}
