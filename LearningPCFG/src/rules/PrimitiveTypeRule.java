package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.PrimitiveType;

import symbol.Symbol;
import util.List;

public class PrimitiveTypeRule extends Rule {

	private Symbol type;

	public PrimitiveTypeRule(PrimitiveType node) {
		super(node);
		
		this.type = terminal(node.getPrimitiveTypeCode().toString());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.type);
	}

}
