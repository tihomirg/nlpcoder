package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Initializer;

import symbol.Symbol;
import util.List;

public class InitializerRule extends Rule {

	private Symbol body;

	public InitializerRule(Initializer node) {
		super(node);
		
		this.body = nonTerminal(node.getBody());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(body);
	}

}
