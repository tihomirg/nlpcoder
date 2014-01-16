package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

//TODO: there is a constructor binding
public class ClassInstanceCreationRule extends Rule {

	private Symbol expression;
	private Symbol type;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;
	private Symbol annonimousClass;

	public ClassInstanceCreationRule(ClassInstanceCreation node) {
		super(node);
		
		ASTNode exp = node.getExpression();
		if (exp != null){
		  this.expression = nonTerminal(exp);		  
		}
		
		this.type = nonTerminal(node.getType());
		
		java.util.List<ASTNode> args = node.arguments();
		if(args != null && args.size() > 0)
		  this.arguments = makeNonTerminalList(args);
		
		
		java.util.List<ASTNode> targs = node.typeArguments();
		if (targs != null && targs.size() > 0){
		  this.typeArguments = makeNonTerminalList(targs);		  
		}
		
		ASTNode annon = node.getAnonymousClassDeclaration();
		if (annon != null){
		  this.annonimousClass = nonTerminal(annon);
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
		
		list.f(Terminals.NEW).f(this.type);
		
		if(this.typeArguments != null){
			list.f(Terminals.L_TARG).f(toTypeArguments()).f(Terminals.R_TARG);
		}
		
		list.f(Terminals.L_PAR);
		
		if(this.arguments != null){
		  list.f(toArguments());
		}
		list.f(Terminals.R_PAR);	
		
		if (this.annonimousClass != null){
			list.f(this.annonimousClass);
		}
	}

}
