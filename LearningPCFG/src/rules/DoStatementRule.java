package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.DoStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class DoStatementRule extends Rule {

	private Symbol expression;
	private Symbol body;

	public DoStatementRule(DoStatement node) {
		super(node);
		this.body = nonTerminal(node.getBody());
		this.expression = nonTerminal(node.getExpression());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.DO).f(this.body).f(Terminals.WHILE).f(Terminals.L_PAR).f(this.expression).f(Terminals.R_PAR);

	}

}
