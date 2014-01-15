package lexicalized.rules;

import lexicalized.info.MethodInfo;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class LexicalizedMethodInvocationRule extends LexicalizedRule {

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
	//private IMethodBinding binding;

	public LexicalizedMethodInvocationRule(MethodInvocation node) {
		super(node);
		
		ASTNode exp = node.getExpression();
		if (exp != null){
			this.expression = nonTerminal(exp);
			this.dot = terminal(Tokens.DOT, node);
		}
				
		java.util.List<ASTNode> args = node.arguments();
		if(args != null && args.size() > 0)
		  this.arguments = makeNonTerminalList(args);
		
		SimpleName name = node.getName();
		this.name = lexicalizedNonTerminal(name, new MethodInfo(name.getIdentifier(), args.size()));		
		
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
		//this.binding = node.resolveMethodBinding();
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

		if(this.typeArguments != null){
			list.f(this.ltarg).f(toTypeArguments()).f(this.rtarg);
		}		
		
		list.f(this.name).f(this.lpar);
		
		if(this.arguments != null){
		  list.f(toArguments());
		}
		list.f(this.rpar);	
	}

}
