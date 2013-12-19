package rules;

import java.util.LinkedList;
import java.util.List;

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
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.exp);
		list.add(this.dotTerminal);
		list.add(this.name);
		return list;
	}
}
