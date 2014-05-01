package sequences.one.exprs;

import java.util.List;

import selection.types.Type;
import util.Pair;

public class StringLiteral extends Expr {

	private String value;

	public StringLiteral(String value, Type type) {
		super(type);
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public String shortRep() {
		return ExprConsts.StringLiteral;
	}

	@Override
	protected String representation() {
		return "("+value+")";
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}	
}
