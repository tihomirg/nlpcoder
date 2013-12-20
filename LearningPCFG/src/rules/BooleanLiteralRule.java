package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BooleanLiteral;

import symbol.Symbol;
import symbol.SymbolFactory;

public class BooleanLiteralRule extends Rule {

	private Symbol value;

	public BooleanLiteralRule(BooleanLiteral node) {
		super(node);
		this.value = terminal(Boolean.toString(node.booleanValue()), node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(value);
	}

}
