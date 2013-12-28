package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ThrowStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class ThrowStatementRule extends Rule {

	private Symbol expression;
	private Symbol throwsTerminal;
	private Symbol semicolon;

	public ThrowStatementRule(ThrowStatement node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
		this.throwsTerminal = terminal(Tokens.THROWS, node);
		this.semicolon = terminal(Tokens.SEMICOLON, node);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.throwsTerminal).f(this.expression).f(this.semicolon);

	}

}
