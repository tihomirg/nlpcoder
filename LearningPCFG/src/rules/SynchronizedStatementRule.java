package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SynchronizedStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class SynchronizedStatementRule extends Rule {

	private Symbol body;
	private Symbol expression;

	public SynchronizedStatementRule(SynchronizedStatement node) {
		super(node);
		
		this.body = nonTerminal(node.getBody());
		this.expression = nonTerminal(node.getExpression());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(Terminals.SYNCHRONIZED).f(Terminals.L_PAR).f(this.expression).f(Terminals.R_PAR).f(this.body);

	}

}
