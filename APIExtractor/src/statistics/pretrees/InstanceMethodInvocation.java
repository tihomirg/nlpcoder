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

public class InstanceMethodInvocation extends Expr{

	private Declaration method;
	private Expr exp;
	private Expr[] args;
	
	public InstanceMethodInvocation(Declaration method, Expr exp, Expr[] args) {
		super(method.getRetType());
		this.method = method;
		this.exp = exp;
		this.args = args;
	}

	@Override
	public String toString() {
		return exp+"."+method.getLongName()+ "("+Arrays.toString(args) + ")";
	}

	@Override
	public String shortRep() {
		return Names.InstanceMethodInvocation+Names.LPar+method.getLongName()+Names.RPar;
	}

	@Override
	protected String argReps() {
		Expr[] exprs = new Expr[args.length+1];
		exprs[0] = exp;
		System.arraycopy(args, 0, exprs, 1, args.length);
		return shortReps(exprs);
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
		
		for (Expr arg : args) {
			list.addAll(arg.longReps());
		}
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removeInstanceMethodInvocation(string));
		StringResult result = parseStringTillRPar(rest);
		String methodName = result.getString();
		rest = removeRPar(result.getRest());
		return new SingleResult(Parser.createInstanceMethodInvocation(methodName), rest);		
	}
}
