package synthesis.trees;

import java.util.List;
import java.util.Map;

public abstract class Expr {
	
	
	public String getString() {
		throw new UnsupportedOperationException();
	}

	public String toString(Map<Integer, Expr> rep) {
		return null;
	}
	
}
