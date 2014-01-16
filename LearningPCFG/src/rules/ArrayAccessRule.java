package rules;

import util.List;

import org.eclipse.jdt.core.dom.ArrayAccess;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class ArrayAccessRule extends Rule {

	private Symbol array;
	private Symbol index;

	public ArrayAccessRule(ArrayAccess node) {
		super(node);
		this.array = nonTerminal(node.getArray());
		this.index = nonTerminal(node.getIndex());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.array).f(Terminals.L_SQUARE_BRACKET).f(this.index).f(Terminals.R_SQUARE_BRACKET);
	}

}
