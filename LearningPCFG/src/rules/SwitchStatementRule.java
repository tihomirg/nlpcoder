package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SwitchStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class SwitchStatementRule extends Rule {

	private Symbol expression;
	private Symbol lpar;
	private Symbol rpar;
	private Symbol lcurly;
	private Symbol rcurly;
	private List<Symbol> statements;
	private Symbol switchTerminal;

	public SwitchStatementRule(SwitchStatement node) {
		super(node);
		
		this.switchTerminal = terminal(Tokens.SWITCH, node);
		this.expression = nonTerminal(node.getExpression());
		this.lpar = terminal(Tokens.L_PAR, node);
		this.rpar = terminal(Tokens.R_PAR, node);
		this.lcurly = terminal(Tokens.L_CURLY_BRACKET, node);
		this.rcurly = terminal(Tokens.R_CURLY_BRACKET, node);
		
		this.statements = makeNonTerminalList(node.statements());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.switchTerminal).f(this.lpar).f(this.expression).f(this.rpar)
		.f(this.lcurly).f(this.statements).f(this.rcurly);
	}

}
