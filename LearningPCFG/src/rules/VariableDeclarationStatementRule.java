package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class VariableDeclarationStatementRule extends Rule {

	private Symbol type;
	private List<Symbol> fragments;

	public VariableDeclarationStatementRule(VariableDeclarationStatement node) {
		super(node);
		this.type = nonTerminal(node.getType());
		this.fragments = makeNonTerminalList(node.fragments());
	}

	private List<Symbol> toFragments(){
		return toInfixList(this.fragments, Terminals.COMMA);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.type).f(toFragments()).f(Terminals.SEMICOLON);
	}

}
