package statistics.pretrees;

import static statistics.parsers.Parser.*;
import java.util.List;
import statistics.Names;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
import types.Type;
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
		return Names.BooleanLiteral;
	}

	@Override
	protected String representation() {
		return Boolean.toString(this.value);
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string) {
		return new SingleResult(new statistics.posttrees.BooleanLiteral(), removeBooleanLiteral(string));
	}
	
}
