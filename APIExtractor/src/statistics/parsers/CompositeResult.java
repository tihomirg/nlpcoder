package statistics.parsers;

import java.util.LinkedList;
import java.util.List;

import statistics.posttrees.Expr;

public class CompositeResult extends Result {

	private List<Expr> exprs = new LinkedList<Expr>();	

	public CompositeResult() {
	}
	
	public CompositeResult(String rest) {
		super(rest);
	}
	
	public void add(Expr expr) {
		this.exprs.add(expr);
	}
	
	public Expr get(int index) {
		return this.exprs.get(index);
	}	
	
	public List<Expr> getExprs() {
		return exprs;
	}

	public void setExprs(List<Expr> exprs) {
		this.exprs = exprs;
	}	
}
