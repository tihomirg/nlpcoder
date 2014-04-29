package sequences.one.exprs;

import selection.types.Type;

public class NullLitera extends Expr {

	public NullLitera(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return "null";
	}
}
