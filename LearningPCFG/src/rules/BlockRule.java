package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.Block;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class BlockRule extends Rule {

	private List<Symbol> statements;
	private Symbol lcurlyTerminal;
	private Symbol rcurlyTerminal;

	public BlockRule(Block node) {
		super(node);
		
		this.statements = makeNonTerminalList(node.statements());
		this.lcurlyTerminal = terminal(Tokens.L_CURLY_BRACKET, node);
		this.rcurlyTerminal = terminal(Tokens.R_CURLY_BRACKET, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.add(this.lcurlyTerminal);
		list.addAll(this.statements);
		list.add(this.rcurlyTerminal);
	}

}
