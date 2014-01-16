package lexicalized.rules;

import lexicalized.info.LexicalizedInfo;

import org.eclipse.jdt.core.dom.SimpleName;

import symbol.Symbol;
import util.List;

public class LexicalizedSimpleNameRule extends LexicalizedRule {

	private Symbol name;

	public LexicalizedSimpleNameRule(SimpleName node, LexicalizedInfo info) {
		super(node, info);
		this.name = terminal(node.getIdentifier());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.name);
	}

}
