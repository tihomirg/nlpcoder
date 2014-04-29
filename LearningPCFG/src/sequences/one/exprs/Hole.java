package sequences.one.exprs;

import selection.types.Type;

public class Hole extends Expr{

	public Hole(){
		super(null);
	}
	
	public Hole(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return "hole("+type+")";
	}

	@Override
	public String shortRep() {
		return ExprConsts.Hole;
	}

	@Override
	protected String representation() {
		return "";
	}	
	
}
