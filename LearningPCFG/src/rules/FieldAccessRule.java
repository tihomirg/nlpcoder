package rules;

import org.eclipse.jdt.core.dom.FieldAccess;

import symbol.Symbol;
import symbol.SymbolFactory;
import util.NonTermainalIndex;

public class FieldAccessRule extends Rule{
	private Symbol exp;
	private Symbol name;
	
	public FieldAccessRule(FieldAccess node){
		super(SymbolFactory.getSymbol(new NonTermainalIndex(node)));
		this.exp = SymbolFactory.getSymbol(new NonTermainalIndex(node.getExpression()));
		this.name = SymbolFactory.getSymbol(new NonTermainalIndex(node.getName()));
	}

	@Override
	protected String rhs() {
		return this.exp +" . "+this.name;
	}
}
