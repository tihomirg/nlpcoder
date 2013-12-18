package rules;

import org.eclipse.jdt.core.dom.FieldAccess;

import symbol.Symbol;
import symbol.SymbolFactory;

public class FieldAccessRule extends Rule{
	private Symbol exp;
	private Symbol dotTerminal;	
	private Symbol name;
	
	public FieldAccessRule(FieldAccess node){
		super(SymbolFactory.getNonTerminal(node));
		this.exp = SymbolFactory.getNonTerminal(node.getExpression());
		this.dotTerminal = SymbolFactory.getTerminal(".", node);
		this.name = SymbolFactory.getNonTerminal(node.getName());
	}

	@Override
	protected String rhs() {
		return this.exp +" "+this.dotTerminal+" "+this.name;
	}
}
