package synthesis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import statistics.posttrees.Expr;
import statistics.posttrees.InputExpr;
import types.Substitution;
import util.Pair;

public class PartialExpression implements Cloneable {

	private LinkedList<Param> params;
	private LinkedList<Substitution> subs;
	private Representation rep;
	private PartialExpressionScore score;
	private LinkedList<Connection> connections;
	private ExprGroup egroup;
	private LinkedList<Integer> connectedTo;
	private int size;
	private LinkedList<InputExpr> inputExprs;

	public PartialExpression(Param param, ExprGroup egroup) {
		this.params = new LinkedList<Param>();
		this.params.add(param);		
		this.subs = new LinkedList<Substitution>();
		this.rep = new SimpleRepresentation();
		this.connections = new LinkedList<Connection>();
		this.egroup = egroup;
		this.score = new PartialExpressionScore();
		this.score.addDeclarationScore(egroup.getDeclarationScore());
		this.connectedTo = new LinkedList<Integer>();
		this.inputExprs = new LinkedList<InputExpr>();
		this.size = 1;
	}

	public PartialExpressionScore getScore() {
		return score;
	}
	
	public Param getParam() {
		return params.element();
	}

	@Override
	public PartialExpression clone() {
		PartialExpression exp = null;
		try {
			exp = (PartialExpression) super.clone();
			exp.rep = (Representation) this.rep.clone();
			exp.params = (LinkedList<Param>) this.params.clone();
			exp.connections = (LinkedList<Connection>) this.connections.clone();
			exp.subs = (LinkedList<Substitution>) this.subs.clone();
			exp.connectedTo = (LinkedList<Integer>) this.connectedTo.clone();
			exp.inputExprs = (LinkedList<InputExpr>) this.getInputExprs().clone();
			exp.score = this.score.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return exp;
	}

	public boolean isCompleted() {
		return params.isEmpty();
	}
	
	public boolean isCompletelyConnected() {
		return connections.isEmpty();
	}	

	@Override
	public String toString() {
		return this.score+"   size="+size+"    "+rep.toString() +tryConnections();
	}
	
	public String repToString(){
		return rep.toString();
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

		//Parameters used for scoring
		newPexpr.incSize();
		if(expr.isInputExpr()){
			newPexpr.addInputExpr(expr.asInputExpr());
		}
		
		return newPexpr;
	}

	private void addInputExpr(InputExpr expr) {
		this.getInputExprs().add(expr);
	}

	private void addAllConnections(List<Connection> connections) {
		this.connections.addAll(connections);
	}

	public List<Connection> getConnections() {
		return connections;
	}
	
	public Connection getConnection() {
		return connections.element();
	}
	
	private void removeConnection(Connection con) {
		connections.remove(con);
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

	public PartialExpression connect(Connection connection, PartialExpression pexpr, PartialExpressionScorer scorer) {
		PartialExpression newPexpr = this.clone();
		
		int index = newPexpr.getRep().connect(connection, pexpr.getRep());

		newPexpr.removeConnection(connection);
		newPexpr.addAllConnections(getConnections(pexpr.getConnections(), index));
		
		scorer.calculetScore(newPexpr, pexpr);
		
		//parameters used for scoring
		newPexpr.size = newPexpr.size + pexpr.size;
		newPexpr.connectedTo.add(pexpr.getGroupIndex());
		newPexpr.inputExprs.addAll(pexpr.inputExprs);
		
		return newPexpr;
	}

	private List<Connection> getConnections(List<Connection> connections, int index) {
		Connection[] cons = new Connection[connections.size()];
		
		for(int i=0; i < cons.length; i++){
			Connection newConnection = connections.get(i).clone();
			newConnection.setIndex(index);
			
			cons[i] = newConnection;
		}
		
		return Arrays.asList(cons);
	}
	
	public void prepareForMearging(){
		this.rep = this.rep.asComplexRepresentation();
		this.egroup.addCompletedExpr(this);
	}

	public LinkedList<Integer> getConnectedTo() {
		return this.connectedTo;
	}

	public int getGroupIndex() {
		return this.egroup.getIndex();
	}

	public void setScore(PartialExpressionScore score) {
		this.score = score;
	}

	public String getStatistics() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.repToString()+"\n");
		sb.append("                                                                    ");
		sb.append(this.score +"    connectedTo: "+this.connectedTo);
		sb.append("\n");
		return sb.toString();
	}

	public void incSize() {
		this.size++;
	}
	
	public int getSize(){
		return this.size;
	}

	public LinkedList<InputExpr> getInputExprs() {
		return inputExprs;
	}

	public void setInputExprs(LinkedList<InputExpr> inputExprs) {
		this.inputExprs = inputExprs;
	}
}
