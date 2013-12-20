package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.NullLiteral;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class NullLiteralRule extends Rule {

	private Symbol nullTerminal;
	
	public NullLiteralRule(NullLiteral node) {
		super(node);
		this.nullTerminal = terminal(Tokens.NULL, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.add(this.nullTerminal);
	}

}
