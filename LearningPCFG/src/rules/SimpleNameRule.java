package rules;

import org.eclipse.jdt.core.dom.SimpleName;

import symbol.Symbol;
import symbol.SymbolFactory;

//TODO: This one should keep more info, like, local, class, API decl, type and etc.
public class SimpleNameRule extends Rule {

	private Symbol name;

	public SimpleNameRule(SimpleName node) {
		super(node);
		this.name = SymbolFactory.getTerminal(node.getIdentifier(), node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String rhs() {
		// TODO Auto-generated method stub
		return this.name.toString();
	}

}
