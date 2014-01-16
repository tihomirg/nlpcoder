package lexicalized.rules;

import lexicalized.info.MethodInfo;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import scopes.Scopes;
import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class LexicalizedMethodInvocationRule extends LexicalizedRule {

	private Symbol expression;
	private Symbol name;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;

	public LexicalizedMethodInvocationRule(MethodInvocation node) {
		this(node, null);
	}
	
	public LexicalizedMethodInvocationRule(MethodInvocation node, Scopes scopes) {
		super(node, scopes);
		
		ASTNode exp = node.getExpression();
		if (exp != null){
			this.expression = nonTerminal(exp);
		}
				
		java.util.List<ASTNode> args = node.arguments();
		if(args != null && args.size() > 0)
		  this.arguments = makeNonTerminalList(args);
		
		SimpleName name = node.getName();
		this.name = lexicalizedNonTerminal(name, new MethodInfo(name.getIdentifier(), args.size()));		
		
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
