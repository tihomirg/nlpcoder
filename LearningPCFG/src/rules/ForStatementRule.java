package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ForStatement;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class ForStatementRule extends Rule {

	private List<Symbol> initializers;
	private Symbol expression;
	private List<Symbol> updaters;
	private Symbol body;

	private Symbol forTerminal;
	private Symbol lParTerminal;
	private Symbol rParTerminal;
	private Symbol commaTerminal;
	private Symbol semicolonTerminal;
	
	public ForStatementRule(ForStatement node) {
		super(node);
		
		this.initializers = makeNonTerminalList(node.initializers());
		this.expression = nonTerminal(node.getExpression());
		this.updaters = makeNonTerminalList(node.updaters());
		this.body = nonTerminal(node.getBody());

		this.forTerminal = terminal(Tokens.FOR, node);
	    this.lParTerminal = terminal(Tokens.L_PAR, node);
	    this.rParTerminal = terminal(Tokens.R_PAR, node);
	    this.commaTerminal = terminal(Tokens.COMMA, node);
	    this.semicolonTerminal = terminal(Tokens.SEMICOLON, node);
		// TODO Auto-generated constructor stub
	}
	
	private List<Symbol> toUpdaters() {
		return toInfixList(this.updaters, this.commaTerminal);
	}

	private List<Symbol> toInitializers() {
		return toInfixList(this.initializers, this.commaTerminal);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.forTerminal).f(this.lParTerminal).f(toInitializers()).f(this.semicolonTerminal)
		.f(this.expression).f(this.semicolonTerminal).f(toUpdaters()).f(this.rParTerminal).f(this.body);
	}

}
