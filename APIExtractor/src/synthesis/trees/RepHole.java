package synthesis.trees;

import synthesis.Representation;

public class RepHole extends Expr {
	
	private static final String REP_HOLE = "RepHole";

	@Override
	public String toString() {
		return REP_HOLE;
	}

	@Override
	public String toString(Representation rep) {
		return REP_HOLE;
	}
}
