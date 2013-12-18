package rules;

import java.util.LinkedList;
import java.util.List;

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
		this.expression = SymbolFactory.getNonTerminal(node.getExpression());
		this.updaters = makeNonTerminalList(node.updaters());
		this.body = SymbolFactory.getNonTerminal(node.getBody());

		this.forTerminal = SymbolFactory.getTerminal(Tokens.FOR, node);
	    this.lParTerminal = SymbolFactory.getTerminal(Tokens.L_PAR, node);
	    this.rParTerminal = SymbolFactory.getTerminal(Tokens.R_PAR, node);
	    this.commaTerminal = SymbolFactory.getTerminal(Tokens.COMMA, node);
	    this.semicolonTerminal = SymbolFactory.getTerminal(Tokens.SEMICOLON, node);
		// TODO Auto-generated constructor stub
	}
	
	private String printUpdaters() {
		return printInfixList(this.updaters, this.commaTerminal);
	}

	private String printInitializers() {
		return printInfixList(this.initializers, this.commaTerminal);
	}

	@Override
	protected String rhs() {
		// TODO Auto-generated method stub
		return this.forTerminal+" "+this.lParTerminal+" "+printInitializers()+" "+this.semicolonTerminal+" "+this.expression+" "+this.semicolonTerminal+" "+printUpdaters()+" "+this.rParTerminal+" "+this.body;
	}

}
