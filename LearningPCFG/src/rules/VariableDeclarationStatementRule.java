package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class VariableDeclarationStatementRule extends Rule {

	private Symbol type;
	private List<Symbol> fragments;
	private Symbol commaTerminal;
	private Symbol semicolonTerminal;

	public VariableDeclarationStatementRule(VariableDeclarationStatement node) {
		super(node);
		this.type = nonTerminal(node.getType());
		this.fragments = makeNonTerminalList(node.fragments());
		this.commaTerminal = terminal(Tokens.COMMA, node);
		this.semicolonTerminal = terminal(Tokens.SEMICOLON, node);
		// TODO Auto-generated constructor stub
	}

	private List<Symbol> toFragments(){
		return toInfixList(this.fragments, this.commaTerminal);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.type).f(toFragments()).f(this.semicolonTerminal);
	}

}
