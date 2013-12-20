package rules;

import org.eclipse.jdt.core.dom.CatchClause;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class CatchClauseRule extends Rule {

	private Symbol exception;
	private Symbol body;
	
	private Symbol catchTerminal;
	private Symbol rparTerminal;
	private Symbol lparTerminal;

	public CatchClauseRule(CatchClause node) {
		super(node);

		this.exception = nonTerminal(node.getException());
		this.body = nonTerminal(node.getBody());

		this.catchTerminal = terminal(Tokens.CATCH, node);
		this.lparTerminal = terminal(Tokens.L_PAR, node);
		this.rparTerminal = terminal(Tokens.R_PAR, node);		
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.catchTerminal).f(this.lparTerminal).f(this.exception).f(this.rparTerminal).f(this.body);
	}

}
