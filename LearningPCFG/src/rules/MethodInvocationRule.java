package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class MethodInvocationRule extends Rule {

	private Symbol expression;
	private Symbol name;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;
	private Symbol lpar;
	private Symbol rpar;
	private Symbol comma;
	private Symbol rtarg;
	private Symbol ltarg;
	private Symbol dot;

	public MethodInvocationRule(MethodInvocation node) {
		super(node);
		
		ASTNode exp = node.getExpression();
		if (exp != null){
			this.expression = nonTerminal(exp);
			this.dot = terminal(Tokens.DOT, node);
		}
		
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
		if (this.expression != null){
			list.f(this.expression).f(this.dot);
		}
		
		list.f(this.name);
		
		if(this.typeArguments != null){
			list.f(this.ltarg).f(toTypeArguments()).f(this.rtarg);
		}
		
		list.f(this.lpar).f(toArguments()).f(this.rpar);	
	}

}
