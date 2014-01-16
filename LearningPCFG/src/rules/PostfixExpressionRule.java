package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.PostfixExpression;

import symbol.Symbol;
import util.List;

public class PostfixExpressionRule extends Rule {

	private Symbol operator;
	private Symbol operand;

	public PostfixExpressionRule(PostfixExpression node) {
		super(node);
		
		this.operand = nonTerminal(node.getOperand());
		this.operator = terminal(node.getOperator().toString());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.operand).f(this.operator);
	}

}
