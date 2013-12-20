package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.DoStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class DoStatementRule extends Rule {

	private Symbol expression;
	private Symbol body;
	private Symbol doTerminal;
	private Symbol whileTerminal;
	private Symbol lparTerminal;
	private Symbol rparTerminal;

	public DoStatementRule(DoStatement node) {
		super(node);
		this.body = nonTerminal(node.getBody());
		this.expression = nonTerminal(node.getExpression());
		// TODO Auto-generated constructor stub
		this.doTerminal = terminal(Tokens.DO, node);
		this.whileTerminal = terminal(Tokens.WHILE, node);
		
		this.lparTerminal = terminal(Tokens.L_PAR, node);
		this.rparTerminal = terminal(Tokens.R_PAR, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.doTerminal).f(this.body).f(this.whileTerminal).f(this.lparTerminal).f(this.expression).f(this.rparTerminal);

	}

}
