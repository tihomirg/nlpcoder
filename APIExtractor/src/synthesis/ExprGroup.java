package synthesis;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import statistics.posttrees.Expr;
import synthesis.handlers.SearchKey;

public class ExprGroup {

	//TODO: Maybe it is better to have ExprGroup per one expr!
	//Than we have way slower first stage of the algorithm.
	private Expr expr;
	private List<ExprGroup> relatedGroups;	
	
	private List<PartialExpression> completedExprs;
	
	public ExprGroup(Expr expr) {
		this.expr = expr;
		this.completedExprs = new LinkedList<PartialExpression>();
	}
	
	public void addCompletedExpr(PartialExpression pexpr){
		completedExprs.add(pexpr);
	}
	
	public List<PartialExpression> getCompletedExprs() {
		return completedExprs;
	}

	public PartialExpression createPartialExpr() {
		return new PartialExpression(new Param(new SearchKey(expr), new RepKey()), this);
	}
	
	public List<ExprGroup> relatedGroups(){
		return relatedGroups;
	}
	
	public void setRelatedGroups(List<ExprGroup> relatedGroups) {
		this.relatedGroups = relatedGroups;
	}
	
	public boolean contains(Expr thatExpr){
	    return Expr.equalShort(expr, thatExpr);
	}

	public List<ExprGroup> tryFindRelatedGroups(Expr expr) {
		List<ExprGroup> rgroups = new LinkedList<ExprGroup>();
		
		for (ExprGroup rgroup : relatedGroups) {
			if(rgroup.contains(expr)){
				rgroups.add(rgroup);
			}
		}
		
		return rgroups;
	}
}