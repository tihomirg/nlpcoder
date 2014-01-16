package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InstanceofExpression;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class InstanceofExpressionRule extends Rule {

	private Symbol right;
	private Symbol left;

	public InstanceofExpressionRule(InstanceofExpression node) {
		super(node);
		
		this.left = nonTerminal(node.getLeftOperand());
		this.right = nonTerminal(node.getRightOperand());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.left).f(Terminals.INSTANCEOF).f(this.right);
	}

}
