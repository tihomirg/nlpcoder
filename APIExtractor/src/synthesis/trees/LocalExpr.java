package synthesis.trees;

import synthesis.Representation;


public class LocalExpr extends Expr {

	private String name;

	public LocalExpr(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(Representation rep) {
		return name;
	}
}
