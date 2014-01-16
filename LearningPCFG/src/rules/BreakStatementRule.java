package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BreakStatement;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class BreakStatementRule extends Rule {

	private Symbol label;

	public BreakStatementRule(BreakStatement node) {
		super(node);
		
		ASTNode lab = node.getLabel();
		if (lab != null) this.label = nonTerminal(lab);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.BREAK);
		if (this.label != null) list.f(this.label);
		list.f(Terminals.SEMICOLON);
	}

}
