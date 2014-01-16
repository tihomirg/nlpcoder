package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.WhileStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class WhileStatementRules extends Rule {

	private Symbol expression;
	private Symbol body;

	public WhileStatementRules(WhileStatement node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
		this.body = nonTerminal(node.getBody());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(Terminals.WHILE).f(Terminals.L_PAR).f(this.expression).f(Terminals.R_PAR).f(this.body);
	}

}
