package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ThisExpression;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class ThisExpressionRule extends Rule {

	private Symbol qualifier;
	
	private Symbol dotTerminal;
	private Symbol thisTerminal;
	
	public ThisExpressionRule(ThisExpression node) {
		super(node);
		// TODO Auto-generated constructor stub
		ASTNode qualifier = node.getQualifier();
		if (qualifier != null){
			this.qualifier = nonTerminal(qualifier);
			this.dotTerminal = terminal(Tokens.DOT, node);
		}
		this.thisTerminal = terminal(Tokens.THIS, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		if(this.qualifier != null){
			list.add(this.qualifier);
			list.add(this.dotTerminal);
		}
		
		list.add(this.thisTerminal);
	}

}
