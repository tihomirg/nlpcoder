package rules;

import util.List;

import org.eclipse.jdt.core.dom.ConditionalExpression;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class ConditionalExpressionRule extends Rule {
	private Symbol condition;
	private Symbol thenExpression;
	private Symbol elseExpression;

	public ConditionalExpressionRule(ConditionalExpression node) {
		super(node);
		// TODO Auto-generated constructor stub
		this.condition = nonTerminal(node.getExpression());
		this.thenExpression = nonTerminal(node.getThenExpression());
		this.elseExpression = nonTerminal(node.getElseExpression());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.condition).f(Terminals.QUESTION_MARK).f(this.thenExpression).f(Terminals.COLON).f(this.elseExpression);
	}

}
