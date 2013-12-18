package rules;

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
			this.qualifier = SymbolFactory.getNonTerminal(qualifier);
			this.dotTerminal = SymbolFactory.getTerminal(Tokens.DOT, node);
		}
		this.thisTerminal = SymbolFactory.getTerminal(Tokens.THIS, node);
	}

	@Override
	protected String rhs() {
		// TODO Auto-generated method stub
		return (qualifier != null? this.qualifier+" "+this.dotTerminal+" ":"")+this.thisTerminal;
	}

}
