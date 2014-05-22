package synthesis;

import java.util.LinkedList;
import java.util.List;

import statistics.posttrees.Expr;
import types.Substitution;

public class PartialExpression implements Cloneable {

	private LinkedList<Param> params;
	private LinkedList<Substitution> subs;
	private Representation rep;
	private double score;
	
	public PartialExpression(Param param) {
		this.params = new LinkedList<Param>();
		this.params.add(param);		
		this.subs = new LinkedList<Substitution>();
		this.rep = new Representation();
	}

//	public PartialExpression(double score) {
//		this()
//	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
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
			exp.params = (LinkedList<Param>) this.params.clone();
			exp.subs = (LinkedList<Substitution>) this.subs.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return exp;
	}

	public boolean isCompleted() {
		return params.isEmpty();
	}
	
	@Override
	public String toString() {
		return this.score+" "+rep.toString();
	}

	public PartialExpression instantiate(Param param, Expr expr, PartialExpressionScorer scorer) {
		PartialExpression newPexpr = this.clone();
		List<Param> params = newPexpr.getRep().instantiate(param, expr);
		
		newPexpr.removeParam(param);
		newPexpr.addAllParams(params);
		
		scorer.calculetScore(newPexpr, expr, param, params);
		
		return newPexpr;
	}

	private void addAllParams(List<Param> params) {
		this.params.addAll(params);
	}

	public Representation getRep() {
		return this.rep;
	}

	private void removeParam(Param param) {
		params.remove(param);
	}
}
