package instructions;

import java.util.List;

import selection.types.Type;
import util.Pair;

public class NullLiteral extends Expr {

	public NullLiteral(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return ExprConsts.NullLiteral;
	}

	@Override
	public String shortRep() {
		return ExprConsts.NullLiteral;
	}

	@Override
	protected String representation() {
		return ExprConsts.NullLiteral;
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}	
}
