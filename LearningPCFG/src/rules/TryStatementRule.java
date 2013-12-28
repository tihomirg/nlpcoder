package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TryStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class TryStatementRule extends Rule {

	private Symbol tryTerminal;
	private Symbol body;
	private List<Symbol> catchClauses;
	private Symbol finallyTerminal;
	private Symbol finallyBody;

	public TryStatementRule(TryStatement node) {
		super(node);
		
		this.tryTerminal = terminal(Tokens.TRY, node);
		this.body = nonTerminal(node.getBody());
		this.catchClauses = makeNonTerminalList(node.catchClauses());
		
		ASTNode fin = node.getFinally();
		
		if(fin != null){
			this.finallyTerminal = terminal(Tokens.FINALLY, node);
			this.finallyBody = nonTerminal(fin);
		}
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.tryTerminal).f(this.body).f(this.catchClauses);
		
		if(this.finallyBody != null){
			list.f(this.finallyTerminal).f(this.finallyBody);
		}

	}

}
