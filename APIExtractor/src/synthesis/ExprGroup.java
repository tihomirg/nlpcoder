package synthesis;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import statistics.posttrees.Expr;
import synthesis.handlers.SearchKey;

public class ExprGroup {

	private List<Expr> exprs;
	public Set<ExprGroup> relatedGroups;
	
	public ExprGroup(final Expr expr) {
		this(new LinkedList<Expr>(){{add(expr);}});
	}
	
	public ExprGroup(List<Expr> exprs) {
		this.exprs = exprs;
	}	
	
	public List<PartialExpression> createPartialExprs(){
		List<PartialExpression> pexprs = new LinkedList<PartialExpression>();
		
		for (Expr expr : exprs) {
			pexprs.add(createPartialExpr(expr));
		}
		
		return pexprs;
	}

	private PartialExpression createPartialExpr(Expr expr) {
		return new PartialExpression(new Param(new SearchKey(expr), new RepKey()));
	}
	
	public Set<ExprGroup> relatedGroups(){
		return relatedGroups;
	}
	
	public void setRelatedGroups(Set<ExprGroup> relatedGroups) {
		this.relatedGroups = relatedGroups;
	}
	
	public boolean contains(Expr thatExpr){
		for(Expr expr:exprs){
			if (Expr.equalShort(expr, thatExpr)) return true;
		}
		return false;
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
