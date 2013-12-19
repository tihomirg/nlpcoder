package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.NumberLiteral;

import symbol.Symbol;
import symbol.SymbolFactory;

public class NumberLiteralRule extends Rule{

	private Symbol number;
	
	public NumberLiteralRule(NumberLiteral node) {
		super(node);
		this.number = SymbolFactory.getTerminal(node.getToken(), node);
	}

	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.number);
		return list;
	}

}
