package statistics.pretrees;

import java.util.List;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.PrimitiveType;
import util.Pair;

public class CharacterLiteral extends Expr {

	private char value;

	public CharacterLiteral(char value, PrimitiveType type) {
		super(type);
		this.value = value;
	}

	@Override
	public String toString() {
		return Character.toString(value);
	}

	@Override
	public String shortRep() {
		return Names.CharacterLiteral;
	}

	@Override
	protected String representation() {
		return Character.toString(value);
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
