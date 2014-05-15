package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.List;
import org.eclipse.jdt.core.dom.Assignment.Operator;
import statistics.Names;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
import types.StabileTypeFactory;
import types.Type;
import util.Pair;

public class Assignment extends Expr {

	private Expr lexp;
	private Expr rexp;
	private Operator op;

	public Assignment(Operator operator, Expr leftExp, Expr rightExp) {
		super(leftExp.getType());

		this.op = operator;
		this.lexp = leftExp;
		this.rexp = rightExp;
		
	}

	@Override
	public String toString() {
		return lexp +" "+op+" "+ rexp;
	}

	@Override
	public String shortRep() {
		return Names.Assignment+Names.LPar+op+Names.RPar+Names.LPar+type.getPrefix()+Names.RPar;
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
		String rest = removeLPar(removeAssignment(string));
		StringResult result = parseStringTillRPar(rest);
		String op = result.getString();
		rest = removeLPar(removeRPar(result.getRest()));
		result = parseStringTillRPar(rest);
		String typePrefix = result.getString();
		Type type = tf.createTypeByTypePrefix(typePrefix);
		rest = removeRPar(result.getRest());
		return new SingleResult(createAssignment(op, type), rest);
	}
}
