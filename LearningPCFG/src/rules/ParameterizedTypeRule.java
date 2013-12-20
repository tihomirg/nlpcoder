package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ParameterizedType;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class ParameterizedTypeRule extends Rule {

	private Symbol type;
	private List<Symbol> typeArguments;
	private Symbol comma;
	private Symbol rtarg;
	private Symbol ltarg;

	public ParameterizedTypeRule(ParameterizedType node) {
		super(node);
		this.type = nonTerminal(node.getType());
		
		java.util.List<ASTNode> targs = node.typeArguments(); 
		if (targs != null && targs.size() > 0){
		  this.typeArguments = makeNonTerminalList(targs);
		  this.ltarg = terminal(Tokens.L_TARG, node);
		  this.rtarg = terminal(Tokens.R_TARG, node);
		  this.comma = terminal(Tokens.COMMA, node);
		}
		// TODO Auto-generated constructor stub
	}
	
	private List<Symbol> toTypeArguments(){
		return toInfixList(this.typeArguments, this.comma);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.type);
		if (this.typeArguments != null) list.f(this.ltarg).f(toTypeArguments()).f(this.rtarg);

	}

}
