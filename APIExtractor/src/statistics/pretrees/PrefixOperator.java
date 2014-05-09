package statistics.pretrees;

import static statistics.parsers.Parser.*;
import java.util.List;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression.Operator;
import statistics.Names;
import statistics.parsers.Parser;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
import types.StabileTypeFactory;
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
	protected String argReps() {
		return exp.shortRep();
	}

	@Override
	protected void longReps(List<Pair<String, String>> list) {
		list.addAll(exp.longReps());
	}

	public static SingleResult parseShort(String string, StabileTypeFactory tf) {
		String rest = removeLPar(removePrefixOperator(string));
		StringResult result = parseStringTillRPar(rest);
		String op = result.getString();
		rest = removeLPar(removeRPar(result.getRest()));
		result = parseStringTillRPar(rest);
		String typePrefix = result.getString();
		Type type = tf.createTypeByTypePrefix(typePrefix);
		rest = removeRPar(result.getRest());
		return new SingleResult(Parser.createPrefixOperator(op, type), rest);
	}	
}
