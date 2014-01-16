package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ForStatement;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class ForStatementRule extends Rule {

	private List<Symbol> initializers;
	private Symbol expression;
	private List<Symbol> updaters;
	private Symbol body;
	
	public ForStatementRule(ForStatement node) {
		super(node);
		
		java.util.List initializers = node.initializers();
		if (initializers != null && initializers.size() > 0)
		  this.initializers = makeNonTerminalList(initializers);
		
		ASTNode expr = node.getExpression();
		if (expr != null)
		  this.expression = nonTerminal(expr);
		
		java.util.List updaters = node.updaters();
		if (updaters != null && updaters.size() > 0)
		  this.updaters = makeNonTerminalList(updaters);
		
		this.body = nonTerminal(node.getBody());
	}
	
	private List<Symbol> toUpdaters() {
		return toInfixList(this.updaters, Terminals.COMMA);
	}

	private List<Symbol> toInitializers() {
		return toInfixList(this.initializers, Terminals.COMMA);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.FOR).f(Terminals.L_PAR);
		
		if(this.initializers != null)
		  list.f(toInitializers());
		
		list.f(Terminals.SEMICOLON);
		
		if(this.expression != null) list.f(this.expression);
		
		list.f(Terminals.SEMICOLON);
		
		if(this.updaters != null) list.f(toUpdaters());
		
		list.f(Terminals.R_PAR).f(this.body);
	}

}
