package rules;

import util.List;

import org.eclipse.jdt.core.dom.NullLiteral;

import symbol.Symbol;
import symbol.Tokens;

public class NullLiteralRule extends Rule {

	private Symbol nullTerminal;
	
	public NullLiteralRule(NullLiteral node) {
		super(node);
		this.nullTerminal = terminal(Tokens.NULL, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.nullTerminal);
	}

}
