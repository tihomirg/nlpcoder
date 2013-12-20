package rules;

import util.List;

import org.eclipse.jdt.core.dom.NumberLiteral;

import symbol.Symbol;

public class NumberLiteralRule extends Rule{

	private Symbol number;
	
	public NumberLiteralRule(NumberLiteral node) {
		super(node);
		this.number = terminal(node.getToken(), node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.number);
	}

}
