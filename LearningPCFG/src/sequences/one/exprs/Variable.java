package sequences.one.exprs;

import selection.types.Type;

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
}
