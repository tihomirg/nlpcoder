package symbol;

import org.eclipse.jdt.core.dom.PostfixExpression.Operator;

public class PostfixOperator extends Symbol {
	
	private Operator operator;
	private Symbol operand;

	public PostfixOperator(Operator operator, Symbol operand) {
		this.operator = operator;
		this.operand = operand;
	}

	@Override
	public String head() {
		return operator.toString();
	}

}
