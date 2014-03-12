package symbol;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;

public class InfixOperator extends Symbol {
	
	private Operator operator;
	private Symbol[] opreands;

	public InfixOperator(Operator operator, Symbol[] operands) {
		this.operator = operator;
		this.opreands = operands;
	}

	@Override
	public String head() {
		return operator.toString();
	}

}
