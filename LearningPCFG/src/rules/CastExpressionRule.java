package rules;

import util.List;

import org.eclipse.jdt.core.dom.CastExpression;

import symbol.Symbol;
import symbol.Terminals;

public class CastExpressionRule extends Rule {

	private Symbol type;
	private Symbol expression;

	public CastExpressionRule(CastExpression node) {
		super(node);
		
		this.type = nonTerminal(node.getType());
		this.expression = nonTerminal(node.getExpression());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.L_PAR).f(this.type).f(Terminals.R_PAR).f(this.expression);
	}

}
