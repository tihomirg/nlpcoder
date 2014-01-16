package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ThisExpression;

import symbol.Symbol;
import symbol.Terminals;

public class ThisExpressionRule extends Rule {

	private Symbol qualifier;
	
	public ThisExpressionRule(ThisExpression node) {
		super(node);
		// TODO Auto-generated constructor stub
		ASTNode qualifier = node.getQualifier();
		if (qualifier != null){
			this.qualifier = nonTerminal(qualifier);
		}
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		if(this.qualifier != null){
			list.f(this.qualifier).f(Terminals.DOT);
		}
		
		list.f(Terminals.THIS);
	}

}
