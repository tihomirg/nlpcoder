package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

//TODO: there is a constructor binding
public class ClassInstanceCreationRule extends Rule {

	private Symbol expression;
	private Symbol type;
	private List<Symbol> arguments;
	private List<Symbol> typeArguments;
	private Symbol rparTerminal;
	private Symbol lparTerminal;
	private Symbol newTerminal;
	private Symbol rtargTerminal;
	private Symbol ltargTerminal;
	private Symbol annonimousClass;
	private Symbol comma;
	private Symbol dot;

	public ClassInstanceCreationRule(ClassInstanceCreation node) {
		super(node);
		
		ASTNode exp = node.getExpression();
		if (exp != null){
		  this.expression = nonTerminal(exp);
		  this.dot = terminal(Tokens.DOT, node);		  
		}
		
		this.type = nonTerminal(node.getType());
		
		java.util.List<ASTNode> args = node.arguments();
		if(args != null && args.size() > 0)
		  this.arguments = makeNonTerminalList(args);
		
		
		java.util.List<ASTNode> targs = node.typeArguments();
		if (targs != null && targs.size() > 0){
		  this.typeArguments = makeNonTerminalList(targs);
			this.ltargTerminal = terminal(Tokens.L_TARG, node);
			this.rtargTerminal = terminal(Tokens.R_TARG, node);		  
		}
		
		ASTNode annon = node.getAnonymousClassDeclaration();
		if (annon != null){
		  this.annonimousClass = nonTerminal(annon);
		}
		
		this.newTerminal = terminal(Tokens.NEW, node);
		this.lparTerminal = terminal(Tokens.L_PAR, node);
		this.rparTerminal = terminal(Tokens.R_PAR, node);

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
		
		list.f(this.newTerminal).f(this.type);
		
		if(this.typeArguments != null){
			list.f(this.ltargTerminal).f(toTypeArguments()).f(this.rtargTerminal);
		}
		
		list.f(this.lparTerminal);
		
		if(this.arguments != null){
		  list.f(toArguments());
		}
		list.f(this.rparTerminal);	
		
		if (this.annonimousClass != null){
			list.f(this.annonimousClass);
		}
	}

}
