package rules;

import util.List;

import org.eclipse.jdt.core.dom.FieldAccess;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class FieldAccessRule extends Rule{
	private Symbol exp;
	private Symbol name;
	
	public FieldAccessRule(FieldAccess node){
		super(node);
		this.exp = nonTerminal(node.getExpression());
		this.name = nonTerminal(node.getName());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.exp).f(Terminals.DO).f(this.name);
	}
}
