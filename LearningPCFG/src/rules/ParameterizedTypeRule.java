package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ParameterizedType;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class ParameterizedTypeRule extends Rule {

	private Symbol type;
	private List<Symbol> typeArguments;

	public ParameterizedTypeRule(ParameterizedType node) {
		super(node);
		this.type = nonTerminal(node.getType());
		
		java.util.List<ASTNode> targs = node.typeArguments(); 
		if (targs != null && targs.size() > 0){
		  this.typeArguments = makeNonTerminalList(targs);
		}
	}
	
	private List<Symbol> toTypeArguments(){
		return toInfixList(this.typeArguments, Terminals.COMMA);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.type);
		if (this.typeArguments != null) list.f(Terminals.L_TARG).f(toTypeArguments()).f(Terminals.R_TARG);

	}

}
