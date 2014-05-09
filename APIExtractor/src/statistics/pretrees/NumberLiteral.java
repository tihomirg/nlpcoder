package statistics.pretrees;

import java.util.List;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.Type;
import util.Pair;

public class NumberLiteral extends Expr {

	private String number;

	public NumberLiteral(String number, Type type) {
		super(type);
		this.number = number;
	}

	@Override
	public String toString() {
		return number;
	}

	@Override
	public String shortRep() {
		return Names.NumberLiteral;
	}

	@Override
	protected String representation() {
		return number;
	}	
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
