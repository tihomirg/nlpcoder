package symbol;

import org.eclipse.jdt.core.dom.PrefixExpression.Operator;

public class PrefixOperator extends Symbol {

	private Operator operator;
	private Symbol operand;

	public PrefixOperator(Operator operator, Symbol operand) {
		this.operator = operator;
		this.operand = operand;
	}

	@Override
	public String head() {
		return "PrefixOp("+operator+")";
	}

	@Override
	public String toString() {
		return operator + " " + operand;
	}
}
