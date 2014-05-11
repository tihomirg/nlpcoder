package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.List;
import org.eclipse.jdt.core.dom.PostfixExpression.Operator;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
import types.StabileTypeFactory;
import types.Type;
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
		return Names.PostfixOperator+Names.LPar+op+Names.RPar+Names.LPar+type.getPrefix()+Names.RPar;		
	}

	@Override
	protected String argReps() {
		return shortReps(expr);
	}
	
	@Override
	protected void longReps(List<Pair<String, String>> list) {
		list.addAll(expr.longReps());
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removePostfixOperator(string));
		StringResult result = parseStringTillRPar(rest);
		String op = result.getString();
		rest = removeLPar(removeRPar(result.getRest()));
		result = parseStringTillRPar(rest);
		String typePrefix = result.getString();
		Type type = tf.createTypeByTypePrefix(typePrefix);
		rest = removeRPar(result.getRest());
		return new SingleResult(Parser.createPostfixOperator(op, type), rest);
	}	
}
