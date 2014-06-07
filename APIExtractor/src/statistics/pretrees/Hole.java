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

public class Hole extends Expr{

	public Hole(){
		super(null);
	}
	
	public Hole(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return "hole("+type+")";
	}

	@Override
	public String shortRep() {
		return Names.Hole;
	}

	@Override
	protected String argReps() {
		return "";
	}

	@Override
	protected void longReps(List<Pair<String, String>> list) {
	}
	
	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		return new SingleResult(Parser.createHole(), removeHole(string));
	}

	@Override
	protected Declaration extractDecl() {
		return null;
	}

	@Override
	protected void extractDecls(List<Declaration> list) {
	}	
}
