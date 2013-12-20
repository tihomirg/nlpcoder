package rules;

import java.util.LinkedList;
import java.util.List;

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
		
		this.label = nonTerminal(node.getLabel());
		this.breakTerminal = terminal(Tokens.BREAK, node);
		this.semicolon = terminal(Tokens.SEMICOLON, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.add(this.breakTerminal);
		list.add(this.label);
		list.add(this.semicolon);
	}

}
