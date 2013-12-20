package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ExpressionStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class ExpressionStatementRule extends Rule {

	private Symbol expression;
	private Symbol semicolon;

	public ExpressionStatementRule(ExpressionStatement node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
		this.semicolon = terminal(Tokens.SEMICOLON, node);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.expression).f(this.semicolon);
	}

}
