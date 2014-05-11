package statistics.pretrees;

import static statistics.parsers.Parser.*;
import java.util.List;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
import types.StabileTypeFactory;
import types.Type;
import util.Pair;

public class InfixOperator extends Expr{
	private Operator op;
	private Expr lexp;
	private Expr rexp;

	public InfixOperator(Operator operator, Expr leftExpr, Expr rightExpr, Type type) {
		super(type);
		this.op = operator;
		this.lexp = leftExpr;
		this.rexp = rightExpr;
	}

	@Override
	public String toString() {
		return lexp + " " + op + " " + rexp;
	}

	@Override
	public String shortRep() {
		return Names.InfixOperator+Names.LPar+op+Names.RPar+Names.LPar+type.getPrefix()+Names.RPar;
	}

	@Override
	protected String argReps() {
		return shortReps(lexp, rexp);
	}

	@Override
	protected void longReps(List<Pair<String, String>> list) {
		list.addAll(lexp.longReps());
		list.addAll(rexp.longReps());
	}
	
	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removeInfixOperator(string));
		StringResult result = parseStringTillRPar(rest);
		String op = result.getString();
		rest = removeLPar(removeRPar(result.getRest()));
		result = parseStringTillRPar(rest);
		String typePrefix = result.getString();
		Type type = tf.createTypeByTypePrefix(typePrefix);
		rest = removeRPar(result.getRest());
		return new SingleResult(Parser.createInfixOperator(op, type), rest);
	}
}
