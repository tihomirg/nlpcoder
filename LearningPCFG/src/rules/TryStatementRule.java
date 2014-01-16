package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TryStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class TryStatementRule extends Rule {

	private Symbol body;
	private List<Symbol> catchClauses;
	private Symbol finallyBody;

	public TryStatementRule(TryStatement node) {
		super(node);
		
		this.body = nonTerminal(node.getBody());
		this.catchClauses = makeNonTerminalList(node.catchClauses());
		
		ASTNode fin = node.getFinally();
		
		if(fin != null){
			this.finallyBody = nonTerminal(fin);
		}
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(Terminals.TRY).f(this.body).f(this.catchClauses);
		
		if(this.finallyBody != null){
			list.f(Terminals.FINALLY).f(this.finallyBody);
		}

	}

}
