package rules;

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
	protected String rhs() {
		return this.number.toString();
	}

}
