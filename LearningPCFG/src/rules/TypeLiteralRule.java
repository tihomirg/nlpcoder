package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeLiteral;

import symbol.Symbol;
import symbol.Terminal;
import symbol.Terminals;
import util.List;

public class TypeLiteralRule extends Rule {

	private Symbol type;

	public TypeLiteralRule(TypeLiteral node) {
		super(node);
		// TODO Auto-generated constructor stub
		ASTNode type = node.getType();
		if(type != null)
		  this.type = nonTerminal(type);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		if(this.type != null){
			list.f(this.type);
		} else list.f(Terminals.VOID);

		list.f(Terminals.DOT).f(Terminals.CLASS);
	}

}
