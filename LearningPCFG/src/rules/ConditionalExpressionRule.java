package rules;

import util.List;

import org.eclipse.jdt.core.dom.ConditionalExpression;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class ConditionalExpressionRule extends Rule {

	private Symbol questionTerminal;
	private Symbol colonTerminal;
	private Symbol condition;
	private Symbol thenExpression;
	private Symbol elseExpression;

	public ConditionalExpressionRule(ConditionalExpression node) {
		super(node);
		// TODO Auto-generated constructor stub
		this.condition = nonTerminal(node.getExpression());
		this.thenExpression = nonTerminal(node.getThenExpression());
		this.elseExpression = nonTerminal(node.getElseExpression());
		
		this.questionTerminal = terminal(Tokens.QUESTION_MARK, node);
		this.colonTerminal = terminal(Tokens.COLON, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.condition).f(this.questionTerminal).f(this.thenExpression).f(this.colonTerminal).f(this.elseExpression);
	}

}
