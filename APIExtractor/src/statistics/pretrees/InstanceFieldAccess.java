package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.List;

import definitions.Declaration;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
import types.StabileTypeFactory;
import types.Type;
import util.Pair;

public class InstanceFieldAccess extends Expr{
	private Declaration field;
	private Expr exp;
	
	public InstanceFieldAccess(Declaration field, Expr exp) {
		super(field.getRetType());
		this.field = field;
		this.exp = exp;
	}

	@Override
	public String toString() {
		return exp +"."+field.getLongName();
	}

	@Override
	public String shortRep() {
		return Names.InstanceFieldAccess+Names.LPar+field.getLongName()+Names.RPar;
	}

	@Override
	protected String argReps() {
		return shortReps(exp);
	}

	@Override
	protected void longReps(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removeInstanceFieldAccess(string));
		StringResult result = parseStringTillRPar(rest);
		String fieldName = result.getString();
		rest = removeRPar(result.getRest());
		return new SingleResult(Parser.createInstanceFieldAccess(fieldName), rest);		
	}
}
