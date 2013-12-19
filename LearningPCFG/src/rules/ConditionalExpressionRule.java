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
		this.condition = SymbolFactory.getNonTerminal(node.getExpression());
		this.thenExpression = SymbolFactory.getNonTerminal(node.getThenExpression());
		this.elseExpression = SymbolFactory.getNonTerminal(node.getElseExpression());
		
		this.questionTerminal = SymbolFactory.getTerminal(Tokens.QUESTION_MARK, node);
		this.colonTerminal = SymbolFactory.getTerminal(Tokens.COLON, node);
	}

	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.condition);
		list.add(this.questionTerminal);
		list.add(this.thenExpression);
		list.add(this.colonTerminal);
		list.add(this.elseExpression);
		return list;
	}

}
