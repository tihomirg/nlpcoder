package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.EnhancedForStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class EnhancedForStatementRule extends Rule {

	private Symbol body;
	private Symbol expression;
	private Symbol parameter;

	public EnhancedForStatementRule(EnhancedForStatement node) {
		super(node);
		this.body = nonTerminal(node.getBody());
		this.expression = nonTerminal(node.getExpression());
		this.parameter = nonTerminal(node.getParent());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.FOR).f(Terminals.L_PAR).f(this.parameter).f(Terminals.COLON).f(this.expression).f(Terminals.R_PAR).f(this.body);
	}

}
