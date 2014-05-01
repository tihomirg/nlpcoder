package sequences.one.exprs;

import java.util.List;

import selection.types.Type;
import util.Pair;

public class BooleanLiteral extends Expr {

	private boolean value;

	public BooleanLiteral(boolean value, Type type) {
		super(type);
		this.value = value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public String shortRep() {
		return ExprConsts.BooleanLiteral;
	}

	@Override
	protected String representation() {
		return Boolean.toString(this.value);
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
	}
	
}
