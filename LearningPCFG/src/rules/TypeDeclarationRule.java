package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class TypeDeclarationRule extends Rule {

	private boolean isInterface;
	private Symbol interfaceTerminal;
	private Symbol classTerminal;
	private List<Symbol> fields;
	private List<Symbol> methods;
	private Symbol name;
	private Symbol superClass;
	private Symbol extendsTermminal;
	private List<Symbol> typeParameters;
	private List<Symbol> superInterfaces;
	private Symbol implementsTerminal;
	private Symbol ltargs;
	private Symbol rtargs;
	private Symbol comma;
	private Symbol rcurly;
	private Symbol lcurly;

	public TypeDeclarationRule(TypeDeclaration node) {
		super(node);
		
		this.isInterface = node.isInterface();
		
		if(this.isInterface){
			this.interfaceTerminal = terminal(Tokens.INTERFACE, node);
		} else {
			this.classTerminal = terminal(Tokens.CLASS, node);
		}
		
		this.name = nonTerminal(node.getName());		
		
		java.util.List<ASTNode> typeParameters = node.typeParameters();
		
		if(typeParameters != null && typeParameters.size() > 0){
			this.typeParameters = makeNonTerminalList(typeParameters);
			this.ltargs= terminal(Tokens.L_TARG, node);
			this.rtargs = terminal(Tokens.R_TARG, node);
		}
		
		ASTNode superClass = node.getSuperclassType();
		if (superClass != null){
			this.superClass = nonTerminal(superClass);	
		}
		
		this.extendsTermminal = terminal(Tokens.EXTENDS, node);		
		this.implementsTerminal = terminal(Tokens.IMPLEMENTS, node);
		this.comma = terminal(Tokens.COMMA, node);
		
		java.util.List<ASTNode> superInterface = node.superInterfaceTypes();
		if(superInterface != null && superInterface.size() > 0){
			this.superInterfaces = makeNonTerminalList(superInterface);
		}
		
		this.fields = makeNonTerminalList(node.getFields());
		this.methods = makeNonTerminalList(node.getMethods());
		
		this.lcurly = terminal(Tokens.L_CURLY_BRACKET, node);
		this.rcurly = terminal(Tokens.R_CURLY_BRACKET, node);
		
		// TODO Auto-generated constructor stub
	}
	
	private List<Symbol> toTypeParameters(){
		return toInfixList(this.typeParameters, this.comma);
	}
	
	private List<Symbol> toSuperInterfaces(){
		return toInfixList(this.superInterfaces, this.comma);
	}	

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		if(this.isInterface){
			list.f(this.interfaceTerminal);
		} else list.f(this.classTerminal);
		
		list.f(this.name);
		
		if(this.typeParameters != null){
			list.f(this.ltargs).f(toTypeParameters()).f(this.rtargs);
		}
		
		if(this.isInterface){
			if(this.superClass != null){
				list.f(this.extendsTermminal).f(this.superClass);
			}
			
			if(this.superInterfaces != null){
				list.f(this.extendsTermminal).f(toSuperInterfaces());
			}
		} else {
			if(this.superInterfaces != null){
				list.f(this.interfaceTerminal).f(toSuperInterfaces());
			}			
		}
		
		list.f(this.lcurly).f(this.fields).f(this.methods).f(this.rcurly);
		
	}

}
