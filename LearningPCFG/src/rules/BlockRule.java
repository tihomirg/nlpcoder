package rules;

import util.List;

import org.eclipse.jdt.core.dom.Block;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class BlockRule extends Rule {

	private List<Symbol> statements;

	public BlockRule(Block node) {
		super(node);
		
		this.statements = makeNonTerminalList(node.statements());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.L_CURLY_BRACKET).f(this.statements).f(Terminals.R_CURLY_BRACKET);
	}

}
