package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;

import symbol.Symbol;
import symbol.Factory;
import symbol.Terminals;

public class ArrayInitializerRule extends Rule {

	private List<Symbol> expressions;

	public ArrayInitializerRule(ArrayInitializer node) {
		super(node);
		
		java.util.List exprs = node.expressions();
		if (exprs != null && exprs.size() > 0)
		this.expressions = makeNonTerminalList(exprs);
	}

	private List<Symbol> toExpressions(){
		return null;//toInfixList(this.expressions, Terminals.COMMA);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		//list.f(Terminals.L_CURLY_BRACKET);
		
		if (this.expressions != null)
		  list.f(toExpressions());
		
		//list.f(Terminals.R_CURLY_BRACKET);
	}

}
