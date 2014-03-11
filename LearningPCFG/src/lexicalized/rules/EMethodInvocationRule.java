package lexicalized.rules;

import interpreter.ENonTerminalFactory;
import lexicalized.symbol.LexicalizedNonTerminal;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import scopes.Scopes;
import scopes.SimpleScopes;
import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class EMethodInvocationRule extends ERule {

	private Symbol expression;
	private Symbol name;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;

	public EMethodInvocationRule(MethodInvocation node) {
		this(node, null);
	}
	
	public EMethodInvocationRule(MethodInvocation node, ENonTerminalFactory factory) {
		super(node, factory);
		
		ASTNode exp = node.getExpression();
		if (exp != null){
			this.expression = eNonTerminal(exp);
		}
				
		java.util.List<ASTNode> args = node.arguments();
		if(args != null && args.size() > 0)
		  this.arguments = makeLNonTerminalList(args);
		
		SimpleName name = node.getName();
		this.name = eNonTerminal(node);
		
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
	
	public boolean ommit(){
		return ((LexicalizedNonTerminal)  this.name).isUserDef();
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
