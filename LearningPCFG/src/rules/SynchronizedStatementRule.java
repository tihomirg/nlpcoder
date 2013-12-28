package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SynchronizedStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class SynchronizedStatementRule extends Rule {

	private Symbol body;
	private Symbol expression;
	private Symbol synchronizedTelrminal;
	private Symbol lpar;
	private Symbol rpar;

	public SynchronizedStatementRule(SynchronizedStatement node) {
		super(node);
		
		this.body = nonTerminal(node.getBody());
		this.expression = nonTerminal(node.getExpression());
		this.synchronizedTelrminal = terminal(Tokens.SYNCHRONIZED, node);
		this.lpar = terminal(Tokens.L_PAR, node);
		this.rpar = terminal(Tokens.R_PAR, node);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.synchronizedTelrminal).f(this.lpar).f(this.expression).f(this.rpar).f(this.body);

	}

}
