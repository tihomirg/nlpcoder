package synthesis.trees;

import java.util.Map;

public class Pointer extends Expr {

	private int id;

	public String toString(Map<Integer, Expr> rep) {
		return rep.get(id).toString(rep);
	}
}
