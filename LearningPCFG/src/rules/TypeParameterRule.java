package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeParameter;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class TypeParameterRule extends Rule{

	private Symbol name;
	private List<Symbol> bounds;
	private Symbol extendsTerminal;
	private Symbol andTerminal;

	public TypeParameterRule(TypeParameter node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		java.util.List<ASTNode> bounds = node.typeBounds();
		
		if(bounds != null && bounds.size() > 0){
			this.bounds = makeNonTerminalList(bounds);
			this.extendsTerminal = terminal(Tokens.EXTENDS, node);
			this.andTerminal = terminal(Tokens.AND, node);
		}
		
		// TODO Auto-generated constructor stub
	}

	private List<Symbol> toBounds(){
		return toInfixList(this.bounds, this.andTerminal);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.name);
		
		if(this.bounds != null){
			list.f(this.extendsTerminal).f(toBounds());
		}
		
	}

}
