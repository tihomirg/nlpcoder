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
		this.nullTerminal = SymbolFactory.getTerminal(Tokens.NULL, node);
	}

	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.nullTerminal);
		return list;
	}

}
