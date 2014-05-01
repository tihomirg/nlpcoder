package sequences.one.exprs;

import java.util.List;

import selection.types.Type;
import util.Pair;

public class Variable extends Expr {
	private String name;

	public Variable(String name, Type type) {
		super(type);
		this.name = name;
	}

	@Override
	public String toString() {
		return "v("+name+", "+type+")";
	}

	@Override
	public String shortRep() {
		return name;
	}

	@Override
	protected String representation() {
		return "";
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
			
	}
}
