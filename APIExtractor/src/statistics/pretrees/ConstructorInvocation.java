package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.Arrays;
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

public class ConstructorInvocation extends Expr{
	private Expr[] args;
	private Declaration cons;
	
	public ConstructorInvocation(Declaration cons, Expr[] args) {
		super(cons.getRetType());
		this.cons = cons;
		this.args = args;
	}

	@Override
	public String toString() {
		return "new " + type + "("+ Arrays.toString(args) + ")";
	}

	@Override
	public String shortRep() {
		return Names.ConstructorInvocation+Names.LPar+cons.getLongName()+Names.RPar;
	}
	
	@Override
	protected String argReps() {
		return shortReps(args);
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
		for (Expr arg : args) {
			list.addAll(arg.longReps());	
		}
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removeConstructorInvocation(string));
		StringResult result = parseStringTillRPar(rest);
		String name = result.getString();
		rest = removeRPar(result.getRest());
		return new SingleResult(Parser.createConstructorInvocation(name), rest);
	}	
}
