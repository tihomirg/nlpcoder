package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SwitchStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class SwitchStatementRule extends Rule {

	private Symbol expression;
	private List<Symbol> statements;

	public SwitchStatementRule(SwitchStatement node) {
		super(node);

		this.expression = nonTerminal(node.getExpression());		
		this.statements = makeNonTerminalList(node.statements());
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(Terminals.SWITCH).f(Terminals.L_PAR).f(this.expression).f(Terminals.R_PAR)
		.f(Terminals.L_CURLY_BRACKET).f(this.statements).f(Terminals.R_CURLY_BRACKET);
	}

}
