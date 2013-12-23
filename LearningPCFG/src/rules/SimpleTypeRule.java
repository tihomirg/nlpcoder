package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SimpleType;

import symbol.Symbol;
import util.List;

public class SimpleTypeRule extends Rule {

	private Symbol name;

	public SimpleTypeRule(SimpleType node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.name);
	}

}
