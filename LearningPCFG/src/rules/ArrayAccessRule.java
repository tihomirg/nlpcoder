package rules;

import util.List;

import org.eclipse.jdt.core.dom.ArrayAccess;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class ArrayAccessRule extends Rule {

	private Symbol array;
	private Symbol index;
	private Symbol lsquare;
	private Symbol rsquare;

	public ArrayAccessRule(ArrayAccess node) {
		super(node);
		this.array = nonTerminal(node.getArray());
		this.index = nonTerminal(node.getIndex());
		
		this.lsquare = terminal(Tokens.L_SQUARE_BRACKET, node);
		this.rsquare = terminal(Tokens.R_SQUARE_BRACKET, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.array).f(this.lsquare).f(this.index).f(this.rsquare);
	}

}
