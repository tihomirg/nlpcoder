package statistics.pretrees;

import java.util.List;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.Type;
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
		return Names.StringLiteral;
	}

	@Override
	protected String representation() {
		return value;
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
