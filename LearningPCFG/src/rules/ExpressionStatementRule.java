package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ExpressionStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class ExpressionStatementRule extends Rule {

	private Symbol expression;

	public ExpressionStatementRule(ExpressionStatement node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.expression).f(Terminals.SEMICOLON);
	}

}
