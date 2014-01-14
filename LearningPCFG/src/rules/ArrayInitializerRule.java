package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class ArrayInitializerRule extends Rule {

	private List<Symbol> expressions;
	private Symbol rcurley;
	private Symbol lcurley;
	private Symbol comma;

	public ArrayInitializerRule(ArrayInitializer node) {
		super(node);
		
		java.util.List exprs = node.expressions();
		if (exprs != null && exprs.size() > 0)
		this.expressions = makeNonTerminalList(exprs);
		
		
		this.rcurley = terminal(Tokens.R_CURLY_BRACKET, node);
		this.lcurley = terminal(Tokens.L_CURLY_BRACKET, node);
		this.comma = terminal(Tokens.COMMA, node);
		// TODO Auto-generated constructor stub
	}

	private List<Symbol> toExpressions(){
		return toInfixList(this.expressions, this.comma);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.lcurley);
		
		if (this.expressions != null)
		  list.f(toExpressions());
		
		list.f(this.rcurley);
	}

}
