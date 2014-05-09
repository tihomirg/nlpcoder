package statistics.pretrees;

import java.util.List;

import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression.Operator;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import types.Type;
import util.Pair;


public class PrefixOperator extends Expr {
	private Operator op;
	private Expr exp;
	
	public PrefixOperator(PrefixExpression.Operator operator, Expr expr, Type type) {
		super(type);
		this.op = operator;
		this.exp = expr;
	}

	@Override
	public String toString() {
		return op +" "+exp;
	}

	@Override
	public String shortRep() {
		return Names.PrefixOperator+Names.LPar+op+Names.RPar+Names.LPar+type+Names.RPar;
	}

	@Override
	protected String representation() {
		return exp.shortRep();
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
