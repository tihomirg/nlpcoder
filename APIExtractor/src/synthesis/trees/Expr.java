package synthesis.trees;

import synthesis.Representation;

public abstract class Expr {
	
	public static final Expr REP_HOLE = new RepHole();
	
	public abstract String toString(Representation rep);
}
