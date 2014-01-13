package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.WhileStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class WhileStatementRules extends Rule {

	private Symbol expression;
	private Symbol body;
	private Symbol whileTerminal;
	private Symbol rpar;
	private Symbol lpar;

	public WhileStatementRules(WhileStatement node) {
		super(node);
		
		this.expression = nonTerminal(node.getExpression());
		this.body = nonTerminal(node.getBody());
		
		this.whileTerminal = terminal(Tokens.WHILE, node);
		this.lpar = terminal(Tokens.L_PAR, node);
		this.rpar = terminal(Tokens.R_PAR, node);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.whileTerminal).f(this.lpar).f(this.expression).f(this.rpar).f(this.body);
	}

}
