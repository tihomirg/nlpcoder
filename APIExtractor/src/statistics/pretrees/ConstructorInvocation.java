package statistics.pretrees;

import java.util.Arrays;
import java.util.List;

import definitions.Declaration;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
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
	protected String representation() {
		return shortReps(args);
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
		for (Expr arg : args) {
			list.addAll(arg.longReps());	
		}
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
