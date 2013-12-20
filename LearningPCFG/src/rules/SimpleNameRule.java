package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.SimpleName;

import symbol.Symbol;
import symbol.SymbolFactory;

//TODO: This one should keep more info, like, local, class, API decl, type and etc.
public class SimpleNameRule extends Rule {

	private Symbol name;

	public SimpleNameRule(SimpleName node) {
		super(node);
		this.name = terminal(node.getIdentifier(), node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.add(this.name);
	}

}
