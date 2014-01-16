package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.PrefixExpression;

import symbol.Symbol;
import util.List;

public class PrefixExpressionRule extends Rule {

	private Symbol operand;
	private Symbol operator;

	public PrefixExpressionRule(PrefixExpression node) {
		super(node);
		
		this.operand = nonTerminal(node.getOperand());
		this.operator = terminal(node.getOperator().toString());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.operator).f(this.operand);

	}

}
