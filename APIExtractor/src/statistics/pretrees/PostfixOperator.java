package statistics.pretrees;

import java.util.List;

import org.eclipse.jdt.core.dom.PostfixExpression.Operator;

import statistics.Names;
import statistics.parsers.Result;
import statistics.parsers.SingleResult;
import util.Pair;

public class PostfixOperator extends Expr {

	private Expr expr;
	private Operator op;
	
	public PostfixOperator(Operator operator, Expr expr) {
		super(expr.getType());
		this.expr = expr;
		this.op = operator;
	}

	@Override
	public String toString() {
		return expr + " " + op;
	}

	@Override
	public String shortRep() {
		return Names.PostfixOperator+Names.LPar+op+Names.RPar+Names.LPar+type+Names.RPar;		
	}

	@Override
	protected String representation() {
		return expr.shortRep();
	}
	
	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(expr.longReps());
	}

	public static SingleResult parseShort(String string) {
		// TODO Auto-generated method stub
		return null;
	}	
}
