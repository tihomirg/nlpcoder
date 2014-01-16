package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class TypeDeclarationRule extends Rule {

	private boolean isInterface;
	private List<Symbol> fields;
	private List<Symbol> methods;
	private Symbol name;
	private Symbol superClass;
	private List<Symbol> typeParameters;
	private List<Symbol> superInterfaces;

	public TypeDeclarationRule(TypeDeclaration node) {
		super(node);
		
		this.isInterface = node.isInterface();
		
		this.name = nonTerminal(node.getName());		
		
		java.util.List<ASTNode> typeParameters = node.typeParameters();
		
		if(typeParameters != null && typeParameters.size() > 0){
			this.typeParameters = makeNonTerminalList(typeParameters);
		}
		
		ASTNode superClass = node.getSuperclassType();
		if (superClass != null){
			this.superClass = nonTerminal(superClass);	
		}
		
		java.util.List<ASTNode> superInterface = node.superInterfaceTypes();
		if(superInterface != null && superInterface.size() > 0){
			this.superInterfaces = makeNonTerminalList(superInterface);
		}
		
		this.fields = makeNonTerminalList(node.getFields());
		this.methods = makeNonTerminalList(node.getMethods());
	}
	
	private List<Symbol> toTypeParameters(){
		return toInfixList(this.typeParameters, Terminals.COMMA);
	}
	
	private List<Symbol> toSuperInterfaces(){
		return toInfixList(this.superInterfaces, Terminals.COMMA);
	}	

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		if(this.isInterface){
			list.f(Terminals.INTERFACE);
		} else list.f(Terminals.CLASS);
		
		list.f(this.name);
		
		if(this.typeParameters != null){
			list.f(Terminals.L_TARG).f(toTypeParameters()).f(Terminals.R_TARG);
		}
		
		if(this.isInterface){			
			if(this.superInterfaces != null){
				list.f(Terminals.EXTENDS).f(toSuperInterfaces());
			}
		} else {
			if(this.superClass != null){
				list.f(Terminals.EXTENDS).f(this.superClass);
			}			
			
			if(this.superInterfaces != null){
				list.f(Terminals.IMPLEMENTS).f(toSuperInterfaces());
			}			
		}
		
		list.f(Terminals.L_CURLY_BRACKET).f(this.fields).f(this.methods).f(Terminals.R_CURLY_BRACKET);
		
	}

}
