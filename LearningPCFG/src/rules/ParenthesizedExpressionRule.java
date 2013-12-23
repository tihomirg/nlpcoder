package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class ParenthesizedExpressionRule extends Rule {

	private Symbol expression;
	private Symbol lpar;
	private Symbol rpar;

	public ParenthesizedExpressionRule(ParenthesizedExpression node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
		this.lpar = terminal(Tokens.L_PAR, node);
		this.rpar = terminal(Tokens.R_PAR, node);		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.lpar).f(this.expression).f(this.rpar);
	}

}
