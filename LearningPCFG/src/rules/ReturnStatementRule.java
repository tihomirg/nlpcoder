package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ReturnStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class ReturnStatementRule extends Rule {

	private Symbol expression;
	private Symbol returnTerminal;
	private Symbol semicolon;

	public ReturnStatementRule(ReturnStatement node) {
		super(node);
		
		ASTNode exp = node.getExpression();
		
		if(exp != null) this.expression = nonTerminal(exp);
		
		this.returnTerminal = terminal(Tokens.RETURN, node);
		this.semicolon = terminal(Tokens.SEMICOLON, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.returnTerminal);
		
		if (this.expression != null) list.f(this.expression);
		
		list.f(this.semicolon);
	}

}
