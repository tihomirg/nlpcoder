package rules;

import org.eclipse.jdt.core.dom.FieldAccess;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class FieldAccessRule extends Rule{
	private Symbol exp;
	private Symbol dotTerminal;	
	private Symbol name;
	
	public FieldAccessRule(FieldAccess node){
		super(node);
		this.exp = SymbolFactory.getNonTerminal(node.getExpression());
		this.dotTerminal = SymbolFactory.getTerminal(Tokens.DOT, node);
		this.name = SymbolFactory.getNonTerminal(node.getName());
	}

	@Override
	protected String rhs() {
		return this.exp +" "+this.dotTerminal+" "+this.name;
	}
}
