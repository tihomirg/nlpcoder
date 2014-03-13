package symbol;

import java.util.Arrays;

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
		return "InfixOp("+operator.toString()+")";
	}
	
	public String toString(){
		return operator.toString()+" "+operandsToString();
	}

	private String operandsToString() {
		return symbolHeadsToString(opreands);
	}

}
