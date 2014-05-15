package definitions;

import java.util.List;

import statistics.posttrees.Expr;
import types.Substitution;

public class Param {
	private int id;
	private Expr exp;
	private List<Substitution> subs;
	
	public Param(int id, Expr exp) {
		this.id = id;
		this.exp = exp;
		this.subs = createSubs(this.exp);
	}

	public Param(Expr exp){
		this(0, exp);
	}
	
	private List<Substitution> createSubs(Expr expr) {
		//expr.getArg
		
		return null;
	}

	public int getId() {
		return id;
	}
	
	public Expr getExp() {
		return exp;
	}

	public List<Substitution> getSubs() {
		return subs;
	}
}