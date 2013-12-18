package rules;

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
	protected String rhs() {
		// TODO Auto-generated method stub
		return this.colonTerminal+" "+this.colonTerminal+" "+this.thenExpression+" "+this.colonTerminal+" "+this.elseExpression;
	}

}
