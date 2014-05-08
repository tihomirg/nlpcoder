package instructions;

import java.util.List;

import selection.types.Type;
import util.Pair;

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

	@Override
	protected void representations(List<Pair<String, String>> list) {
	}	
}
