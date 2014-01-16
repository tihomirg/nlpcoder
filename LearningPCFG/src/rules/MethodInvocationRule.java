package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class MethodInvocationRule extends Rule {

	private Symbol expression;
	private Symbol name;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;

	public MethodInvocationRule(MethodInvocation node) {
		super(node);
		
		ASTNode exp = node.getExpression();
		if (exp != null){
			this.expression = nonTerminal(exp);
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
		return toInfixList(this.typeArguments, Terminals.COLON);
	}	
	
	private List<Symbol> toArguments(){
		return toInfixList(this.arguments, Terminals.COLON);
	}	
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		if (this.expression != null){
			list.f(this.expression).f(Terminals.DOT);
		}

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
