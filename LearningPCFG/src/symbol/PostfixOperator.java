package symbol;

import java.util.Set;

import org.eclipse.jdt.core.dom.PostfixExpression.Operator;

import types.Type;
import definitions.Declaration;

public class PostfixOperator extends Symbol {
	
	private Operator operator;
	private Symbol operand;

	public PostfixOperator(Operator operator, Symbol operand) {
		this.operator = operator;
		this.operand = operand;
	}

	@Override
	public String head() {
		return "PostfixOp("+operator+")";
	}

	@Override
	public String toString() {
		return operator+" "+operand;
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
