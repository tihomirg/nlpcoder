package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ThrowStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class ThrowStatementRule extends Rule {

	private Symbol expression;

	public ThrowStatementRule(ThrowStatement node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(Terminals.THROWS).f(this.expression).f(Terminals.SEMICOLON);

	}

}
