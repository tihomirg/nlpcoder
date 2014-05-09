package statistics.pretrees;

import java.util.List;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.Type;
import util.Pair;

public class NullLiteral extends Expr {

	public NullLiteral(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return Names.NullLiteral;
	}

	@Override
	public String shortRep() {
		return Names.NullLiteral;
	}

	@Override
	protected String representation() {
		return Names.NullLiteral;
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
