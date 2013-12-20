package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.CastExpression;

import symbol.Symbol;
import symbol.Tokens;

public class CastExpressionRule extends Rule {

	private Symbol type;
	private Symbol expression;
	private Symbol lparTerminal;
	private Symbol rparTerminal;

	public CastExpressionRule(CastExpression node) {
		super(node);
		
		this.type = nonTerminal(node.getType());
		this.expression = nonTerminal(node.getExpression());
		
		this.lparTerminal = terminal(Tokens.L_PAR, node);
		this.rparTerminal = terminal(Tokens.R_PAR, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.add(this.lparTerminal);
		list.add(this.type);
		list.add(this.rparTerminal);
		list.add(this.expression);
	}

}
