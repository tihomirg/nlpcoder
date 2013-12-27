package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class SuperMethodInvocationRule extends Rule {

	private Symbol qualifier;
	private Symbol name;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;
	private Symbol lpar;
	private Symbol rpar;
	private Symbol comma;
	private Symbol rtarg;
	private Symbol ltarg;
	private Symbol dot;
	private Symbol superTermianl;	
	
	public SuperMethodInvocationRule(SuperMethodInvocation node) {
		super(node);
		
		ASTNode qualifier = node.getQualifier();
		if (qualifier != null){
			this.qualifier = nonTerminal(qualifier);
		}
		
		this.superTermianl = terminal(Tokens.SUPER, node);
		this.dot = terminal(Tokens.DOT, node);		
		
		this.name = nonTerminal(node.getName());
		this.arguments = makeNonTerminalList(node.arguments());
		
		java.util.List<ASTNode> targs = node.typeArguments();
		
		if (targs != null && targs.size() > 0){
			this.typeArguments = makeNonTerminalList(targs);
			this.ltarg = terminal(Tokens.L_TARG, node);
			this.rtarg = terminal(Tokens.R_TARG, node);
		}
		
		this.lpar = terminal(Tokens.L_PAR, node);
		this.rpar = terminal(Tokens.R_PAR, node);
		
		this.comma = terminal(Tokens.COMMA, node);
		// TODO Auto-generated constructor stub
	}

	private List<Symbol> toTypeArguments(){
		return toInfixList(this.typeArguments, this.comma);
	}	
	
	private List<Symbol> toArguments(){
		return toInfixList(this.arguments, this.comma);
	}	
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		if (this.qualifier != null){
			list.f(this.qualifier).f(this.dot);
		}
		
		list.f(this.superTermianl).f(this.dot);
		
		if(this.typeArguments != null){
			list.f(this.ltarg).f(toTypeArguments()).f(this.rtarg);
		}		
		
		list.f(this.name).f(this.lpar).f(toArguments()).f(this.rpar);	
	}

}
