package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeParameter;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class TypeParameterRule extends Rule{

	private Symbol name;
	private List<Symbol> bounds;

	public TypeParameterRule(TypeParameter node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		java.util.List<ASTNode> bounds = node.typeBounds();
		
		if(bounds != null && bounds.size() > 0){
			this.bounds = makeNonTerminalList(bounds);
		}
	}

	private List<Symbol> toBounds(){
		return toInfixList(this.bounds, Terminals.AND);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.name);
		
		if(this.bounds != null){
			list.f(Terminals.EXTENDS).f(toBounds());
		}
		
	}

}
