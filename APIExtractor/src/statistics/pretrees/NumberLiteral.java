package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.List;

import definitions.Declaration;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import types.StabileTypeFactory;
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
	protected String argReps() {
		return number;
	}	
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		return new SingleResult(Parser.createNumberLiteral(), removeNumberLiteral(string));
	}	

	@Override
	public boolean isLiteral() {
		return true;
	}

	@Override
	protected Declaration extractDecl() {
		return null;
	}

	@Override
	protected void extractDecls(List<Declaration> list) {
	}
	
}
