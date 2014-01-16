package rules;

import util.List;

import org.eclipse.jdt.core.dom.NullLiteral;

import symbol.Symbol;
import symbol.Terminals;

public class NullLiteralRule extends Rule {

	private Symbol nullTerminal;
	
	public NullLiteralRule(NullLiteral node) {
		super(node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.NULL);
	}

}
