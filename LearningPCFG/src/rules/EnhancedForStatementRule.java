package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.EnhancedForStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class EnhancedForStatementRule extends Rule {

	private Symbol body;
	private Symbol expression;
	private Symbol parameter;
	private Symbol colon;
	private Symbol rpar;
	private Symbol lpar;
	private Symbol forTerminal;

	public EnhancedForStatementRule(EnhancedForStatement node) {
		super(node);
		this.body = nonTerminal(node.getBody());
		this.expression = nonTerminal(node.getExpression());
		this.parameter = nonTerminal(node.getParent());
		
		this.forTerminal = terminal(Tokens.FOR, node);
		this.lpar = terminal(Tokens.L_PAR, node);
		this.rpar = terminal(Tokens.R_PAR, node);
		this.colon = terminal(Tokens.COLON, node);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.forTerminal).f(this.lpar).f(this.parameter).f(this.colon).f(this.expression).f(this.rpar).f(this.body);
	}

}
