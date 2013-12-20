package rules;

import java.util.LinkedList;
import java.util.List;

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
		list.add(this.condition);
		list.add(this.questionTerminal);
		list.add(this.thenExpression);
		list.add(this.colonTerminal);
		list.add(this.elseExpression);
	}

}
