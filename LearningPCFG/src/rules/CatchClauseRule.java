package rules;

import org.eclipse.jdt.core.dom.CatchClause;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class CatchClauseRule extends Rule {

	private Symbol exception;
	private Symbol body;

	public CatchClauseRule(CatchClause node) {
		super(node);

		this.exception = nonTerminal(node.getException());
		this.body = nonTerminal(node.getBody());		
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.CATCH).f(Terminals.L_PAR).f(this.exception).f(Terminals.R_PAR).f(this.body);
	}

}
