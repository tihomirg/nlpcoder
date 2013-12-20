package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BreakStatement;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class BreakStatementRule extends Rule {

	private Symbol label;
	private Symbol breakTerminal;
	private Symbol semicolon;

	public BreakStatementRule(BreakStatement node) {
		super(node);
		
		ASTNode lab = node.getLabel();
		if (lab != null) this.label = nonTerminal(lab);
		
		this.breakTerminal = terminal(Tokens.BREAK, node);
		this.semicolon = terminal(Tokens.SEMICOLON, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.breakTerminal);
		if (this.label != null) list.f(this.label);
		list.f(this.semicolon);
	}

}
