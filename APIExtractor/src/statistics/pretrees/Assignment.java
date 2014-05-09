package statistics.pretrees;

import static statistics.parsers.Parser.*;

import java.util.List;

import org.eclipse.jdt.core.dom.Assignment.Operator;
import statistics.Names;
import statistics.parsers.SingleResult;
import statistics.parsers.StringResult;
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
		return Names.Assignment+Names.LPar+op+Names.RPar;
	}

	@Override
	protected String representation() {
		return shortReps(lexp, rexp);
	}

	@Override
	protected void representations(List<Pair<String, String>> list) {
		list.addAll(lexp.longReps());
		list.addAll(rexp.longReps());
	}

	public static SingleResult parseShort(String string) {
		String rest = removeLPar(removeAssignment(string));
		StringResult result = parseStringTillRPar(rest);
		String op = result.getString();
		rest = removeRPar(result.getRest());
		return new SingleResult(new statistics.posttrees.Assignment(op), rest);
	}
}
