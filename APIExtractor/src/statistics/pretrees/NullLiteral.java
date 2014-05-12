package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.List;

import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.StabileTypeFactory;
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
	protected String argReps() {
		return "";
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		return new SingleResult(Parser.createNullLiteral(), removeNullLiteral(string));
	}
	
	@Override
	public boolean isLiteral() {
		return true;
	}
		
}
