package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.StringLiteral;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class StringLiteralRule extends Rule {

	private Symbol value;

	public StringLiteralRule(StringLiteral node) {
		super(node);
		
		//this.value = terminal(node.getLiteralValue(), node);
		this.value = terminal(node.getEscapedValue());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.value);
	}

}
