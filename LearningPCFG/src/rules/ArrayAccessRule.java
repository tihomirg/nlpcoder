package rules;

import java.util.LinkedList;
import java.util.List;

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
		this.array = SymbolFactory.getNonTerminal(node.getArray());
		this.index = SymbolFactory.getNonTerminal(node.getIndex());
		
		this.lsquare = SymbolFactory.getTerminal(Tokens.L_SQUARE_BRACKET, node);
		this.rsquare = SymbolFactory.getTerminal(Tokens.R_SQUARE_BRACKET, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.array);
		list.add(this.lsquare);
		list.add(this.index);
		list.add(this.rsquare);
		return list;
	}

}
