package rules;

import org.eclipse.jdt.core.dom.CharacterLiteral;

import symbol.Symbol;
import util.List;

public class CharacterLiteralRule extends Rule {

	private Symbol value;

	public CharacterLiteralRule(CharacterLiteral node) {
		super(node);
		this.value = terminal(Character.toString(node.charValue()));
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.value);
	}

}
