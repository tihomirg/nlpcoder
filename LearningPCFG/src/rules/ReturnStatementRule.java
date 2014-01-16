package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ReturnStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class ReturnStatementRule extends Rule {

	private Symbol expression;

	public ReturnStatementRule(ReturnStatement node) {
		super(node);
		
		ASTNode exp = node.getExpression();
		
		if(exp != null) this.expression = nonTerminal(exp);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.RETURN);
		
		if (this.expression != null) list.f(this.expression);
		
		list.f(Terminals.SEMICOLON);
	}

}
