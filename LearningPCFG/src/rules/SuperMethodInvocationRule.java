package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class SuperMethodInvocationRule extends Rule {

	private Symbol qualifier;
	private Symbol name;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;
	private Symbol superTermianl;	
	
	public SuperMethodInvocationRule(SuperMethodInvocation node) {
		super(node);
		
		ASTNode qualifier = node.getQualifier();
		if (qualifier != null){
			this.qualifier = nonTerminal(qualifier);
		}		
		
		this.name = nonTerminal(node.getName());
		
		java.util.List<ASTNode> args = node.arguments();
		if(args != null && args.size() > 0)
		  this.arguments = makeNonTerminalList(args);
		
		java.util.List<ASTNode> targs = node.typeArguments();
		
		if (targs != null && targs.size() > 0){
			this.typeArguments = makeNonTerminalList(targs);
		}
	}

	private List<Symbol> toTypeArguments(){
		return toInfixList(this.typeArguments, Terminals.COMMA);
	}	
	
	private List<Symbol> toArguments(){
		return toInfixList(this.arguments, Terminals.COMMA);
	}	
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		if (this.qualifier != null){
			list.f(this.qualifier).f(Terminals.DOT);
		}
		
		list.f(this.superTermianl).f(Terminals.DOT);
		
		if(this.typeArguments != null){
			list.f(Terminals.L_TARG).f(toTypeArguments()).f(Terminals.R_TARG);
		}		
		
		list.f(this.name).f(Terminals.L_PAR);
		
		if(this.arguments != null){
		  list.f(toArguments());
		}
		list.f(Terminals.R_PAR);
	}

}
