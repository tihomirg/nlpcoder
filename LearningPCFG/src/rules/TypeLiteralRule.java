package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeLiteral;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class TypeLiteralRule extends Rule {

	private Symbol type;
	private Symbol voidTerminal;
	private Symbol classTermainal;

	public TypeLiteralRule(TypeLiteral node) {
		super(node);
		// TODO Auto-generated constructor stub
		ASTNode type = node.getType();
		if(type != null)
		  this.type = nonTerminal(type);
		else
		  this.voidTerminal = terminal(Tokens.VOID, node);
		
		this.classTermainal = terminal(Tokens.CLASS, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		if(this.type != null){
			list.f(this.type);
		} else list.f(this.voidTerminal);

		list.f(this.classTermainal);
	}

}
