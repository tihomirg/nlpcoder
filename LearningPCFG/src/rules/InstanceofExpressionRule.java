package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InstanceofExpression;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class InstanceofExpressionRule extends Rule {

	private Symbol right;
	private Symbol left;
	private Symbol instanceofTerminal;

	public InstanceofExpressionRule(InstanceofExpression node) {
		super(node);
		
		this.left = nonTerminal(node.getLeftOperand());
		this.right = nonTerminal(node.getRightOperand());
		
		this.instanceofTerminal = terminal(Tokens.INSTANCEOF, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.left).f(this.instanceofTerminal).f(this.right);
	}

}
