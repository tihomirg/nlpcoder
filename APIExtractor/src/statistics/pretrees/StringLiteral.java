package statistics.pretrees;

import static statistics.parsers.Parser.*;
import java.util.List;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import types.StabileTypeFactory;
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
	protected String argReps() {
		return value;
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		return new SingleResult(Parser.createStringLiteral(), removeStringLiteral(string));
	}	

	@Override
	public boolean isLiteral() {
		return true;
	}
	
}
