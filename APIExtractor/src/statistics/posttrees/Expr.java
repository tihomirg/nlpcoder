package statistics.posttrees;

import java.util.List;

public abstract class Expr {

	public abstract void addArgs(List<Expr> args);
	
	public String getString() {
		throw new UnsupportedOperationException();
	}

}
