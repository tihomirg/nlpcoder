package symbol;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.jdt.core.dom.InfixExpression.Operator;

import selection.types.Type;
import definitions.Declaration;

public class InfixOperator extends Symbol {
	
	private Operator operator;
	private Symbol[] opreands;

	public InfixOperator(Operator operator, Symbol[] operands) {
		this.operator = operator;
		this.opreands = operands;
	}

	@Override
	public String head() {
		return "InfixOp("+operator+")";
	}
	
	public String toString(){
		return operator+" "+operandsToString();
	}

	private String operandsToString() {
		return symbolHeadsToString(opreands);
	}

	@Override
	public Type retType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRetType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVariable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Declaration> getDecls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasDecls() {
		// TODO Auto-generated method stub
		return false;
	}

}
