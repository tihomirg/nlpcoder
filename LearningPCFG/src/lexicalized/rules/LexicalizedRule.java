package lexicalized.rules;

import lexicalized.info.ArrayCreationInfo;
import lexicalized.info.ClassInstanceCreationInfo;
import lexicalized.info.FieldInfo;
import lexicalized.info.InfixExpressionInfo;
import lexicalized.info.LexicalizedInfo;
import lexicalized.info.MethodInfo;
import lexicalized.info.PostfixExpressionInfo;
import lexicalized.info.PrefixExpressionInfo;
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
import org.eclipse.jdt.core.dom.ThisExpression;

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
		} else
		if (node instanceof FieldAccess){
			return lFieldNonTerminal((FieldAccess) node);				
		} else
		if (node instanceof SimpleName){
			return lSimpleNameNonTerminal((SimpleName) node);					
		} else
		if (node instanceof ArrayCreation){
			return lArrayCreationNonTerminal((ArrayCreation) node);
		} else
		if (node instanceof ClassInstanceCreation){
			return lClassInstanceCreationNonTerminal((ClassInstanceCreation) node);
		} else
		if (node instanceof InfixExpression){
			return lInfixExpressionNonTerminal((InfixExpression) node);
		} else 
		if (node instanceof PostfixExpression){
			return lPostfixExpressionNonTerminal((PostfixExpression) node);	
		} else
		if (node instanceof PrefixExpression){
			return lPrefixExpressionNonTerminal((PrefixExpression) node);	
		} else
		if (node instanceof SuperMethodInvocation){
			return lSuperMethodNonTerminal((SuperMethodInvocation) node);			
		} else
		if (node instanceof SuperFieldAccess){
			return lSuperFieldNonTerminal((SuperFieldAccess) node);				
		}
		
		return nonTerminal(node);
    }

	private Symbol lSuperFieldNonTerminal(SuperFieldAccess field) {
		SimpleName name = field.getName();
		return lNonTerminal0(field, new FieldInfo(name.getIdentifier()));
	}

	private Symbol lSuperMethodNonTerminal(SuperMethodInvocation method) {
		SimpleName name = method.getName();
		return lNonTerminal0(method, new MethodInfo(name.getIdentifier(), method.arguments().size()));
	}

	private Symbol lPrefixExpressionNonTerminal(PrefixExpression node) {
		return lNonTerminal0(node, new PrefixExpressionInfo(node.getOperator().toString()));
	}

	private Symbol lPostfixExpressionNonTerminal(PostfixExpression node) {
		return lNonTerminal0(node, new PostfixExpressionInfo(node.getOperator().toString()));
	}

	private Symbol lClassInstanceCreationNonTerminal(ClassInstanceCreation node) {
		return lNonTerminal0(node, new ClassInstanceCreationInfo(node.getType().toString()));
	}

	private Symbol lInfixExpressionNonTerminal(InfixExpression node) {
		return lNonTerminal0(node, new InfixExpressionInfo(node.getOperator().toString()));
	}

	private Symbol lArrayCreationNonTerminal(ArrayCreation node) {
		return lNonTerminal0(node, new ArrayCreationInfo(node.getType().toString(), node.dimensions().size()));
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
    	
    	//We assume that if a method has no receiver, it is local.
		if (node instanceof MethodInvocation){
			MethodInvocation method = (MethodInvocation) node;
			info.setUserDef(method.getExpression() == null 
					     || method.getExpression() instanceof ThisExpression);
		} else
		if (node instanceof SuperMethodInvocation){
			info.setUserDef(true);
		} else
		if (node instanceof SuperFieldAccess){
			info.setUserDef(true);		
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
