package rules;

import org.eclipse.jdt.core.dom.NullLiteral;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class NullLiteralRule extends Rule {

	private Symbol nullTerminal;
	
	public NullLiteralRule(NullLiteral node) {
		super(node);
		this.nullTerminal = SymbolFactory.getTerminal(Tokens.NULL, node);
	}

	@Override
	protected String rhs() {
		return this.nullTerminal.toString();
	}

}
