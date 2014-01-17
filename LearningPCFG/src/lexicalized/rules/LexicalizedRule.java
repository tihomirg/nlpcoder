package lexicalized.rules;

import lexicalized.info.FieldInfo;
import lexicalized.info.LexicalizedInfo;
import lexicalized.info.MethodInfo;
import lexicalized.info.SimpleNameInfo;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import config.Config;

import rules.Rule;
import scopes.Scopes;
import symbol.Symbol;
import util.List;

public abstract class LexicalizedRule extends Rule {

	protected Scopes scopes;
	
	public LexicalizedRule(ASTNode node, Scopes scopes) {
		super(node);
		this.scopes = scopes;
	}
	
	public LexicalizedRule(ASTNode node, LexicalizedInfo info) {
		assert node != null && info != null;
		this.head = Config.getFactory().getLexicalizedNonTerminal(node, info);
	}
	
    protected Symbol lNonTerminal(ASTNode node){
		if (node instanceof MethodInvocation){
			return lMethodNonTerminal((MethodInvocation) node);
		} if (node instanceof FieldAccess){
			return lFieldNonTerminal((FieldAccess) node);				
		} if (node instanceof SimpleName){
			return lSimpleNameNonTerminal((SimpleName) node);					
		} if (node instanceof ArrayCreation){
			
		} if (node instanceof ClassInstanceCreation){
			
		}  if (node instanceof InfixExpression){
			
		}  if (node instanceof PostfixExpression){
			
		}  if (node instanceof SuperMethodInvocation){
			return lMethodNonTerminal((MethodInvocation) node);			
		}  if (node instanceof SuperFieldAccess){
			return lFieldNonTerminal((FieldAccess) node);				
		}
		
		return nonTerminal(node);
    }

	protected Symbol lSimpleNameNonTerminal(SimpleName name) {
		return lNonTerminal0(name, new SimpleNameInfo(name.getIdentifier()));
	}

	protected Symbol lFieldNonTerminal(FieldAccess field) {
		SimpleName name = field.getName();
		return lNonTerminal0(field, new FieldInfo(name.getIdentifier()));
	}

	protected Symbol lMethodNonTerminal(MethodInvocation method) {
		SimpleName name = method.getName();
		return lNonTerminal0(method, new MethodInfo(name.getIdentifier(), method.arguments().size()));
	}
    
    private Symbol lNonTerminal0(ASTNode node, LexicalizedInfo info){
    	if (this.scopes != null){
    		if(this.scopes.contains(info.getName())){
    			info.setUserDef(true);
    		}
    	}
    	return Config.getFactory().getLexicalizedNonTerminal(node, info);
    }
    
	protected List<Symbol> makeLNonTerminalList(java.util.List<ASTNode> nodes) {
		return makeLNonTerminalList(nodes.toArray(new ASTNode[0]));
	}
	
	protected List<Symbol> makeLNonTerminalList(ASTNode[] nodes) {
		List<Symbol> list = new List<Symbol>();
		for(ASTNode node: nodes){
			list.add(lNonTerminal(node));
		}
		return list;
	}    
	
}
