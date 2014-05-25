package synthesis;

import java.util.LinkedList;
import java.util.List;
import statistics.posttrees.Expr;
import types.Substitution;
import util.Pair;

public class PartialExpression implements Cloneable {

	private LinkedList<Param> params;
	private LinkedList<Substitution> subs;
	private Representation rep;
	private double score;
	private LinkedList<Connection> connections;
	private ExprGroup egroup;

	public PartialExpression(Param param, ExprGroup egroup) {
		this.params = new LinkedList<Param>();
		this.params.add(param);		
		this.subs = new LinkedList<Substitution>();
		this.rep = new Representation();
		this.connections = new LinkedList<Connection>();
		this.egroup = egroup;
	}

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
			exp.connections = (LinkedList<Connection>) this.connections.clone();
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
		return this.score+" "+rep.toString() +tryConnections();
	}

	private String tryConnections() {
		StringBuilder sb = new StringBuilder(" ");

		if (!connections.isEmpty()){

			for (Connection con: connections) {
				sb.append(con+" ");
			}
		}

		return sb.toString();
	}

	public PartialExpression instantiate(Param param, Expr expr, PartialExpressionScorer scorer) {
		PartialExpression newPexpr = this.clone();
		List<Param> newParams = newPexpr.getRep().instantiate(param, expr);

		//remove old param
		newPexpr.removeParam(param);

		Pair<List<Connection>, List<Param>> connectionsAndParams = findConnections(newParams, this.egroup);

		//Add connections
		List<Connection> connections = connectionsAndParams.getFirst();
		newPexpr.addAllConnections(connections);

		//Add params
		List<Param> params = connectionsAndParams.getSecond();
		newPexpr.addAllParams(params);

		scorer.calculetScore(newPexpr, expr, param, connections, params);

		return newPexpr;
	}

	private void addAllConnections(List<Connection> connections) {
		this.connections.addAll(connections);
	}

	public LinkedList<Connection> getConnections() {
		return connections;
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

	private Pair<List<Connection>, List<Param>> findConnections(List<Param> params, ExprGroup egroup){
		List<Connection> connections = new LinkedList<Connection>();
		List<Param> rests = new LinkedList<Param>();
		for (Param param : params) {
			Expr expr = param.getSearchKey().getExpr();

			List<ExprGroup> relatedGroups = egroup.tryFindRelatedGroups(expr);

			if (!relatedGroups.isEmpty()){
				connections.add(new Connection(param, relatedGroups));				
			} else {
				rests.add(param);
			}
		}

		return new Pair<List<Connection>, List<Param>>(connections, rests);
	}
}
