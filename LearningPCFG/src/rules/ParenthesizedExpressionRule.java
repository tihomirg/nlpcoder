package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class ParenthesizedExpressionRule extends Rule {

	private Symbol expression;

	public ParenthesizedExpressionRule(ParenthesizedExpression node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.L_PAR).f(this.expression).f(Terminals.R_PAR);
	}

}
